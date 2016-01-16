package com.sora.projectn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

/**
 * Created by Sora on 2016/1/16.
 */
public class WebScrape {

    //爬取网页最外层
    private static String MAIN_URL = "http://www.basketball-reference.com/";
    //爬取内容保存路径
    private static String PATH = "D:/data";
    //获取球队基本数据
    private Pattern pattern_getTeamList = Pattern.compile("(.*)(<div class=\"table_container p402_hide \" id=\"div_defunct\">)(.*?)(</div>)(.*)");

    public void main(String args[]){
        getTeamList();
    }

    /**
     *获取球队基本数据
     *
     */
    private  void getTeamList() {
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
        System.out.println(result.toString());
    }

}


//f = open('data/teams/teams.txt', 'w')
//url = main_url + 'teams/'
//response = urllib2.urlopen(url)
//html = response.read()
//result = re.search(r'<div class="table_container p402_hide " id="div_active">([\s\S]*?)</div>', html)
//result = result.groups()[0].replace('\n', '')
//result = re.findall(r'<td align="left" ><a href="/teams/(.*?)/">(.*?)</a></td>[\s\S]*?'
//                    r'<td align="right" >(.*?)</td>', result)
//for item in result:
//    f.write(item[1] + ';' + item[0] + ';' + item[2] + ';\n')
//f.close()
//return result