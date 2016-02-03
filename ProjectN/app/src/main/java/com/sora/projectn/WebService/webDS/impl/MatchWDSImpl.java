package com.sora.projectn.WebService.webDS.impl;

import com.sora.projectn.WebService.webDS.MatchWDS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sora on 2016/2/3.
 */

public class MatchWDSImpl implements MatchWDS {


    //爬取网页最外层路径
    private static String MAIN_URL = "http://www.basketball-reference.com";

    @Override
    public StringBuffer getMatchList(int year, int month, int day) {

        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(MAIN_URL + "/boxscores/index.cgi?month=" + month + "&day=" + day + "&year=" + year);
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
    public StringBuffer getMatchInfo(String matchPath) {

        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(MAIN_URL + matchPath);
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
