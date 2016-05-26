package com.sora.projectn.gc.model.dao.impl;

import android.content.Context;
import android.util.Log;

import com.sora.projectn.gc.WebService.parser.MatchParser;
import com.sora.projectn.gc.WebService.parser.impl.MatchParserImpl;
import com.sora.projectn.gc.WebService.webDS.MatchWDS;
import com.sora.projectn.gc.WebService.webDS.impl.MatchWDSImpl;
import com.sora.projectn.gc.model.dao.MatchDAO;
import com.sora.projectn.gc.database.DBManager.MatchDBManager;
import com.sora.projectn.gc.po.MatchInfoPo;
import com.sora.projectn.gc.po.MatchPrimaryInfoPo;
import com.sora.projectn.gc.po.PlayerMatchInfoPo;
import com.sora.projectn.gc.po.TeamMatchInfoPo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Sora on 2016/2/3.
 */
public class MatchDAOImpl implements MatchDAO {

    Context context;
    MatchDataWatcher w;


    @Override
    public List<MatchPrimaryInfoPo> getMatchList(Context context) {
        this.context = context;
        w = MatchDataWatcher.getInstance(context);
        return w.getMatchPrimaryInfoPoList();
    }

    @Override
    public List<PlayerMatchInfoPo> getPlayerMatchList(Context context,String name) {
        this.context = context;
        w = MatchDataWatcher.getInstance(context);
        List<PlayerMatchInfoPo> list = w.getPlayerMatchInfoMap().get(name);
        if (list == null) {
            //TODO 考虑球员姓名后带.的情况  实际不确定是否有这种情况  后续考虑
            list = w.getPlayerMatchInfoMap().get(name+".");
            if(list == null) {
                Log.e("Data Error","#Can't find this player: " + name);
                System.out.println();
                list = new ArrayList<PlayerMatchInfoPo>();
            }
        }
        return list;
    }

    @Override
    public List<TeamMatchInfoPo> getTeamMatchList(Context context,String teamName) {
        this.context = context;
        w = MatchDataWatcher.getInstance(context);
        List<TeamMatchInfoPo> list = w.getTeamMatchInfoMap().get(teamName);
        if (list == null) {
            Log.e("Data Error", "#Can't find this team: " + teamName);
            list = new ArrayList<TeamMatchInfoPo>();
        }
        return list;
    }

    @Override
    public List<MatchInfoPo> getDateMatchList(Context context, String date) {
        //调用数据库
        MatchDBManager db = new MatchDBManager(context);
        //根据日期获取原始数据类
        List<MatchInfoPo> list = db.queryMatchInfo(date);
        //关闭数据库
        db.closeDB();


        return list;
    }

    @Override
    public MatchInfoPo getNoMatch(Context context, int no) {
        //调用数据库
        MatchDBManager db = new MatchDBManager(context);
        //根据比赛编号获取原始数据类
        MatchInfoPo po = db.queryMatchInfo(no);
        //关闭数据库
        db.closeDB();


        return po;
    }

    @Override
    public void setMatchInfo(Context context, String startDay, String endDay) {

        //调用数据爬取接口
        MatchWDS matchWDS = new MatchWDSImpl();
        MatchParser matchParser = new MatchParserImpl();
        MatchDBManager db = new MatchDBManager(context);

        Calendar startCal = new GregorianCalendar();
        Calendar endCal = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            Date startDate = df.parse(startDay);
            Date endDate = df.parse(endDay);
            startCal.setTime(startDate);
            endCal.setTime(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //遍历给定的两个日期之间的所有比赛 包括首尾
        while (startCal.compareTo(endCal) <= 0){

            //为年月日定值  注意月需要+1  Calendar的月从0计数
            int year = startCal.get(Calendar.YEAR);
            int month = startCal.get(Calendar.MONTH) + 1;
            int day = startCal.get(Calendar.DATE);

            //为比赛日定值
            String date = df.format(startCal.getTime());
            //调用matchWDS接口 获取比赛日数据
            StringBuffer result = matchWDS.getMatchList(year, month, day);
            ////matchParser接口 解析比赛日数据
            List<String> list = matchParser.parseMatchList(result);

            Log.i("读取比赛日数据",date);

            for (String matchPath : list){
                //调用matchWDS接口 获取比赛数据
                result = matchWDS.getMatchInfo(matchPath);
                //调用matchWDS接口 解析比赛数据
                MatchInfoPo po = matchParser.parseMatchInfo(result);

                Log.i("读取比赛数据",matchPath);

                //给定date值
                po.setDate(date);
                //调用SQL  存储
                db.add(po);
            }


            startCal.add(Calendar.DAY_OF_YEAR,1);
        }

        db.closeDB();

    }


}
