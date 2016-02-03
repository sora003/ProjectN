package com.sora.projectn.dataservice.impl;

import android.content.Context;
import android.util.Log;

import com.sora.projectn.WebService.parser.MatchParser;
import com.sora.projectn.WebService.parser.impl.MatchParserImpl;
import com.sora.projectn.WebService.webDS.MatchWDS;
import com.sora.projectn.WebService.webDS.impl.MatchWDSImpl;
import com.sora.projectn.database.DBManager.MatchDBManager;
import com.sora.projectn.dataservice.MatchDS;
import com.sora.projectn.po.MatchPo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Sora on 2016/2/3.
 */
public class MatchDSImpl implements MatchDS {

    @Override
    public void setMatchInfo(Context context, String startDay, String endDay) {

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
                MatchPo po = matchParser.parMatchInfo(result);

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
