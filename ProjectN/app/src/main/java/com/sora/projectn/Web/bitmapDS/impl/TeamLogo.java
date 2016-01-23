package com.sora.projectn.Web.bitmapDS.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.sora.projectn.Web.bitmapDS.TeamBDS;
import com.sora.projectn.Web.sqlDS.TeamSDS;
import com.sora.projectn.Web.sqlDS.impl.Teamimpl;
import com.sora.projectn.Web.webDS.TeamWDS;
import com.sora.projectn.Web.webDS.impl.TeamData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/23.
 */
public class TeamLogo implements TeamBDS {


    @Override
    public void setTeamLogoToSDCard(Context context) {

        //调用TeamSDS接口 获取球队缩写列表
        TeamSDS teamSDS = new Teamimpl();
        List list = teamSDS.getTeamAbbrFromSql(context);

        //调用TeamWDS接口 获取(k,v)=(球队缩写,球队logo)的Map
        TeamWDS teamWDS = new TeamData();
        Map<String,Bitmap> map = teamWDS.getTeamLogoFromWeb(list);




        //判断SDCard是否存在
        if (!Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)) {
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
            File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "NBADATA" + File.separator + "TeamLogo" + File.separator + abbr + ".png");


            //判断文件是否存在 若不存在 则创建文件
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdir();
            }

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
