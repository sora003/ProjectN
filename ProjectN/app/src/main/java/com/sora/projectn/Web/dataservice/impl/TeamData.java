package com.sora.projectn.Web.dataservice.impl;

import com.sora.projectn.Web.dataservice.TeamDS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sora on 2016/1/16.
 */
public class TeamData implements TeamDS{


    //爬取网页最外层路径
    private static String MAIN_URL = "http://www.basketball-reference.com/";


    @Override
    public StringBuffer getTeamList() {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(MAIN_URL + "/teams");
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            //Reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
                result.append(new String(line.getBytes(),"utf-8") + "\n");
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
