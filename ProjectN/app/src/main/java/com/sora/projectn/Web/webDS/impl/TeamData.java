package com.sora.projectn.Web.webDS.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.sora.projectn.Web.webDS.TeamWDS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/16.
 */
public class TeamData implements TeamWDS {


    //爬取网页最外层路径
    private static String MAIN_URL = "http://www.basketball-reference.com/";
    //球队logo 网址路径
    private static String LOGO_PATH = "http://a.espncdn.com/combiner/i?img=/i/teamlogos/nba/500/";
    //球队联盟信息网址路径
    private static String LEAGUE_PATH = "http://www.nba.com/teams/";


    @Override
    public StringBuffer getTeamListFromWeb() {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(MAIN_URL + "teams");
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            //Reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
                //必须在一行中 否则用正则表达式取值时会出错
                result.append(new String(line.getBytes(),"utf-8"));
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Bitmap> getTeamLogoFromWeb(List<String> list) {
        Map<String,Bitmap> map = new HashMap<String,Bitmap>();
        for (String abbr : list) {
            try {
                String webPath = LOGO_PATH + abbr + ".png";
                //String相同  用equals判断
                if (abbr.equals("NOH")) {
                    webPath = LOGO_PATH + "no.png";
                }
                if (abbr.equals("UTA")) {
                    webPath = LOGO_PATH + "utah.png";
                }
                //从Web获取图片 存储为bmp
                URL url = new URL(webPath);
                URLConnection conn = url.openConnection();
                conn.connect();
                InputStream in = conn.getInputStream();
                Bitmap bmp = BitmapFactory.decodeStream(in);

                //abbr作为key bmp作为值 存入HashMap
                map.put(abbr,bmp);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public StringBuffer getTeamInfoFromWeb(String abbr) {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(MAIN_URL+ "teams/"+abbr);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            //Reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
                //必须在一行中 否则用正则表达式取值时会出错
                result.append(new String(line.getBytes(),"utf-8"));
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public StringBuffer getTeamLeagueFromWeb() {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(LEAGUE_PATH);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            //Reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
                //必须在一行中 否则用正则表达式取值时会出错
                result.append(new String(line.getBytes(),"utf-8"));
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


//        //设置文件路径  SDCard/NBADATA/Team/TeamList
//        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "NBADATA" + File.separator + "Team" + File.separator + "TeamList");
//        //判断文件是否存在 若不存在 则创建文件
//        if (!file.getParentFile().exists()){
//            file.getParentFile().mkdir();
//        }
//        //打印流
//        PrintStream out = null;
//        //输出JSONObject到文件中
//        try {
//            out = new PrintStream(new FileOutputStream(file));
//            out.print(jsonObject.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            //如果打印流不为空 关闭打印流
//            if (out != null){
//                out.close();
//            }
//        }
//


//    response = urllib2.urlopen(url)
//    f_logo = open('data/teams/logo/' + abbr + '.png', 'wb')
//    f_logo.write(response.read())
//            f_logo.close()
//            time.sleep(0.2)
}
