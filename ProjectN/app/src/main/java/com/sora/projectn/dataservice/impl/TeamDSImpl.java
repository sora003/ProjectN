package com.sora.projectn.dataservice.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.sora.projectn.Web.parser.TeamParser;
import com.sora.projectn.Web.parser.impl.TeamParserImpl;
import com.sora.projectn.Web.webDS.TeamWDS;
import com.sora.projectn.Web.webDS.impl.TeamData;
import com.sora.projectn.database.DBManager.TeamDBManager;
import com.sora.projectn.dataservice.TeamDS;
import com.sora.projectn.po.TeamPo;

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
public class TeamDSImpl implements TeamDS{


    @Override
    public List<String> getTeamAbbr(Context context) {
        return new TeamDBManager(context).queryTeamAbbr();
    }

    @Override
    public List<TeamPo> getTeamList(Context context) {
        return new TeamDBManager(context).queryTeamList();
    }

    @Override
    public Map<String, String> getTeamSNameAndConference(Context context) {
        return new TeamDBManager(context).queryTeamSNameAndConference();
    }

    @Override
    public Map<String, String> getTeamSNameAndAbbr(Context context) {
        return new TeamDBManager(context).queryTeamSNameAndAbbr();
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
    public void setTeamList(Context context) {

        //调用TeamWDS接口 获取爬取数据
        TeamWDS teamWDS = new TeamData();
        StringBuffer result = teamWDS.getTeamListFromWeb();


        //调用TeamParser接口 获取球队基本数据 List
        TeamParser teamParser = new TeamParserImpl();
        List<TeamPo> list = teamParser.parseTeamList(result);


        new TeamDBManager(context).add(list);
    }



    @Override
    public void setTeamListInfo(Context context) {

        //调用TeamWDS接口 获取爬取数据
        TeamWDS teamWDS = new TeamData();
        StringBuffer result = teamWDS.getTeamLeagueFromWeb();

        //调用TeamParser接口 获取球队基本数据 List
        TeamParser teamParser = new TeamParserImpl();
        List<TeamPo> list = teamParser.parseTeamLeague(result);

        //添加城市信息
        for (TeamPo teamPo : list) {
            //调用TeamWDS接口 获取爬取数据
            TeamWDS teamWDS1 = new TeamData();
            StringBuffer result1 = teamWDS1.getTeamInfoFromWeb(teamPo.getAbbr());

            //调用TeamParser接口 获取球队基本数据 List
            TeamParser teamParser1 = new TeamParserImpl();
            String city = teamParser1.parseTeamCity(result1);

            teamPo.setCity(city);

//            Log.i("db_Conference",teamPo.getConference());
        }


        new TeamDBManager(context).update(list);



    }


    @Override
    public void setTeamLogo(Context context) {

        //调用TeamDS接口 获取球队缩写列表
        TeamDS teamDS = new TeamDSImpl();
        List list = teamDS.getTeamAbbr(context);

        //调用TeamWDS接口 获取(k,v)=(球队缩写,球队logo)的Map
        TeamWDS teamWDS = new TeamData();
        Map<String,Bitmap> map = teamWDS.getTeamLogoFromWeb(list);




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
}

