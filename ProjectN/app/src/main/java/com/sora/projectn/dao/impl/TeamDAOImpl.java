package com.sora.projectn.dao.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.sora.projectn.WebService.parser.TeamParser;
import com.sora.projectn.WebService.parser.impl.TeamParserImpl;
import com.sora.projectn.WebService.webDS.TeamWDS;
import com.sora.projectn.WebService.webDS.impl.TeamWDSImpl;
import com.sora.projectn.dao.TeamDAO;
import com.sora.projectn.database.DBManager.TeamDBManager;
import com.sora.projectn.database.DBManager.TeamSeasonGameDBManager;
import com.sora.projectn.po.TeamPo;
import com.sora.projectn.po.TeamSeasonGamePo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/25.
 */
public class TeamDAOImpl implements TeamDAO {


    @Override
    public List<String> getTeamAbbr(Context context) {
        TeamDBManager db = new TeamDBManager(context);
        List<String> list = db.queryTeamAbbr();
        db.closeDB();
        return list;
    }

    @Override
    public String getTeamLeague(Context context, String abbr) {
        TeamDBManager db = new TeamDBManager(context);
        String league = db.queryTeamLeague(abbr);
        db.closeDB();
        return league;
    }

    @Override
    public TeamPo getTeamInfo(Context context,String abbr) {
        TeamDBManager db = new TeamDBManager(context);
        TeamPo po = db.queryTeamInfo(abbr);
        db.closeDB();
        return po;
    }

    @Override
    public Map<String, String> getTeamSNameAndConference(Context context) {
        TeamDBManager db = new TeamDBManager(context);
        Map<String, String> map = db.queryTeamSNameAndConference();
        db.closeDB();
        return map;
    }

    @Override
    public Map<String, String> getTeamSNameAndAbbr(Context context) {
        TeamDBManager db = new TeamDBManager(context);
        Map<String, String> map = db.queryTeamSNameAndAbbr();
        db.closeDB();
        return map;
    }

    @Override
    public String getTeamAbbr(Context context, String sName) {
        return this.getTeamSNameAndAbbr(context).get(sName);
    }

    @Override
    public Bitmap getTeamLogo(Context context, String abbr) {

        Bitmap bmp;

        //判断SDCard是否存在
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        //设置文件路径  SDCard/NBADATA/TeamLogo
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NBADATA/TeamLogo/" + abbr + ".png" ;

        File file = new File(filePath);

        //判断文件是否存在 若不存在 返回
        //TODO 后续考虑文件不存在的情况
        if (! file.exists()){
            return null;
        }

        bmp = BitmapFactory.decodeFile(filePath);


        return bmp;
    }



    @Override
    public int getTeamSeasonGameYear(Context context, String abbr) {
        TeamSeasonGameDBManager db = new TeamSeasonGameDBManager(context);
        int year = db.queryYear(abbr);
        db.closeDB();
        return year;
    }

    @Override
    public TeamSeasonGamePo getTeamSeasonGameInfo(Context context, String abbr) {
        TeamSeasonGameDBManager db = new TeamSeasonGameDBManager(context);
        TeamSeasonGamePo po = db.queryTeamSeasonGameInfo(abbr);
        db.closeDB();
        return po;
    }

    @Override
    public void setTeamList(Context context) {

        //调用TeamWDS接口 获取爬取数据
        TeamWDS teamWDS = new TeamWDSImpl();
        StringBuffer result = teamWDS.getTeamList();


        //调用TeamParser接口 获取球队基本数据 List
        TeamParser teamParser = new TeamParserImpl();
        List<TeamPo> list = teamParser.parseTeamList(result);


        TeamDBManager db = new TeamDBManager(context);
        db.add(list);
        db.closeDB();
    }



    @Override
    public void setTeamListInfo(Context context) {

        //调用TeamWDS接口 获取爬取数据
        TeamWDS teamWDS = new TeamWDSImpl();
        StringBuffer result = teamWDS.getTeamLeague();

        //调用TeamParser接口 获取球队基本数据 List
        TeamParser teamParser = new TeamParserImpl();
        List<TeamPo> list = teamParser.parseTeamLeague(result);

        //添加城市信息
        for (TeamPo teamPo : list) {
            //调用TeamWDS接口 获取爬取数据
            TeamWDS teamWDS1 = new TeamWDSImpl();
            StringBuffer result1 = teamWDS1.getTeamInfo(teamPo.getAbbr());

            //调用TeamParser接口 获取球队基本数据 List
            TeamParser teamParser1 = new TeamParserImpl();
            String city = teamParser1.parseTeamCity(result1);

            teamPo.setCity(city);

//            Log.i("db_Conference",teamPo.getConference());
        }


        TeamDBManager db = new TeamDBManager(context);
        db.update(list);
        db.closeDB();


    }


    @Override
    public void setTeamLogo(Context context) {

        //获取球队缩写列表
        List<String> list = this.getTeamAbbr(context);

        //调用TeamWDS接口 获取(k,v)=(球队缩写,球队logo)的Map
        TeamWDS teamWDS = new TeamWDSImpl();
        Map<String,Bitmap> map = teamWDS.getTeamLogo(list);




        //判断SDCard是否存在
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        //获取map的key 的首个对象
        Iterator iterator = map.keySet().iterator();

        //遍历set(key)
        while (iterator.hasNext()) {
            Object key = iterator.next();
            String abbr = key.toString();
            Bitmap bmp = map.get(key);

            //设置文件路径  SDCard/NBADATA/TeamLogo
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NBADATA/TeamLogo" ;

            String fileName = abbr + ".png";

            File pFile = new File(filePath);

            //判断文件是否存在 若不存在 则创建文件
            //这里使用mkdirs 因为创建的是多级文件夹
            if (! pFile.exists()){
                pFile.mkdirs();
            }

            File file = new File(filePath + "/" + fileName);





            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);

                //存储图片
                bmp.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);

                //关闭文件
                fileOutputStream.flush();
                fileOutputStream.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }



    @Override
    public void setTeamSeasonGame(Context context,int year) {



        //获取球队缩写列表
        TeamDAO teamDAO = new TeamDAOImpl();
        List<String> abbrList = teamDAO.getTeamAbbr(context);

        TeamSeasonGameDBManager db = new TeamSeasonGameDBManager(context);

        for (String abbr : abbrList){
            //调用TeamWDS接口 获取爬取数据
            TeamWDS teamWDS = new TeamWDSImpl();
            StringBuffer result = teamWDS.getTeamSeasonGame(abbr,year);

            //调用TeamParser接口 解析球队最新赛季比赛数据
            TeamParser teamParser = new TeamParserImpl();
            TeamSeasonGamePo po = teamParser.parseTeamSeasonGame(result,abbr, year);


            db.add(po);
        }

        db.closeDB();
    }
}

