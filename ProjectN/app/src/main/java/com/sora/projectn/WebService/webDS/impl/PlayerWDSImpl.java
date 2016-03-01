package com.sora.projectn.WebService.webDS.impl;

import com.sora.projectn.WebService.webDS.PlayerWDS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/1/27.
 */
public class PlayerWDSImpl implements PlayerWDS {


    //爬取网页最外层路径
    private static String MAIN_URL = "http://www.basketball-reference.com";

    @Override
    public StringBuffer  getPlayerInfo(String abbr,int year) {

        //特殊情况
        switch (abbr){
            case "CHA":
                abbr = "CHO";
                break;
            case "NOH":
                abbr = "NOP";
                break;
            case "NJN":
                abbr = "BRK";
                break;
        }

        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(MAIN_URL + "/teams/" + abbr + "/" + year + ".html");
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
    public StringBuffer getPlayerImg(String path) {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(MAIN_URL + path);
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
}
