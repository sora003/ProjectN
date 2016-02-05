package com.sora.projectn.dao.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.sora.projectn.WebService.parser.PlayerParser;
import com.sora.projectn.WebService.parser.impl.PlayerParserImpl;
import com.sora.projectn.WebService.webDS.PlayerWDS;
import com.sora.projectn.WebService.webDS.impl.PlayerWDSImpl;
import com.sora.projectn.dao.PlayerDAO;
import com.sora.projectn.dao.TeamDAO;
import com.sora.projectn.database.DBManager.PlayerDBManager;
import com.sora.projectn.po.PlayerPo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/2/2.
 */
public class PlayerDAOImpl implements PlayerDAO {

    //爬取网页最外层路径
    private static String MAIN_URL = "http://www.basketball-reference.com";


    @Override
    public List<PlayerPo> getPlayerList(Context context, String abbr) {
        PlayerDBManager db = new PlayerDBManager(context);

        List<PlayerPo> list = db.queryPlayerList(abbr);

        db.closeDB();

        return list;
    }

    @Override
    public Map<String,String> getPlayerImg(Context context) {
        PlayerDBManager db = new PlayerDBManager(context);

        Map<String,String> map = db.queryPlayerImg();

        db.closeDB();

        return map;

    }

    @Override
    public void setPlayInfo(Context context) {

        //调用TeamDS接口 获取球队缩写列表
        TeamDAO teamDAO = new TeamDAOImpl();
        List<String> abbrList = teamDAO.getTeamAbbr(context);
        PlayerDBManager db = new PlayerDBManager(context);

        for (String abbr : abbrList){

            Log.i("爬取球队球员数据",abbr);

            int year = teamDAO.getTeamSeasonGameYear(context,abbr);

            //调用PlayerWDS接口 获取球员数据原始网页
            PlayerWDS playerWDS = new PlayerWDSImpl();
            StringBuffer result = playerWDS.getPlayerInfo(abbr,year);

            //PlayerParser 读取球员信息
            PlayerParser playerParser = new PlayerParserImpl();
            List<PlayerPo> playerPoList = playerParser.parsePlayerInfo(result,abbr);

            db.add(playerPoList);

        }

        //关闭数据库
        db.closeDB();
    }

    @Override
    public void setPlayerImg(Context context) {

        //判断SDCard是否存在
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }


        //获取球队球员具体信息列表 包含球员照片
        Map<String,String> map = this.getPlayerImg(context);

        //调用PlayerWDS接口
        PlayerWDS playerWDS = new PlayerWDSImpl();

        //调用PlayerParser接口
        PlayerParser playerParser = new PlayerParserImpl();




        //获取map的key 的首个对象
        Iterator iterator = map.keySet().iterator();

        //遍历set(key)
        while (iterator.hasNext()) {
            Object key = iterator.next();
            String name = map.get(key);
            String imgPath = key.toString();

            StringBuffer result = playerWDS.getPlayerImg(imgPath);

            Bitmap bmp = playerParser.parsePlayerImg(result);


            //设置文件路径  SDCard/NBADATA/TeamLogo
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NBADATA/PlayerImg" ;

            String fileName = name + ".png";

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
