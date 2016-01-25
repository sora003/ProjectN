package com.sora.projectn.Web.sqlDS.impl;

import android.content.Context;

import com.sora.projectn.Web.webDS.TeamWDS;
import com.sora.projectn.Web.webDS.impl.TeamData;
import com.sora.projectn.Web.sqlDS.TeamSDS;
import com.sora.projectn.Web.parser.TeamParser;
import com.sora.projectn.Web.parser.impl.TeamParserImpl;
import com.sora.projectn.db.DBManager;
import com.sora.projectn.po.TeamPo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/1/17.
 *
 */
public class Teamimpl implements TeamSDS {




    @Override
    public void setTeamListToSql(Context context) {

        //调用TeamWDS接口 获取爬取数据
        TeamWDS teamWDS = new TeamData();
        StringBuffer result = teamWDS.getTeamListFromWeb();


        //调用TeamParser接口 获取球队基本数据 List
        TeamParser teamParser = new TeamParserImpl();
        List<TeamPo> list = teamParser.parseTeamList(result);

        //调用数据库
        DBManager dbManager = new DBManager(context);

        //将球队基本数据存储到SQL
        dbManager.add(list);

    }

    @Override
    public List<String> getTeamAbbrFromSql(Context context) {

        List<String> list = new ArrayList<String>();

        //调用数据库
        DBManager dbManager = new DBManager(context);

        //查找球队基本数据 并返回 list
        list = dbManager.queryTeamAbbr();

        return list;
    }

    @Override
    public void setTeamListInfoToSql(Context context) {

        //调用TeamWDS接口 获取爬取数据
        TeamWDS teamWDS = new TeamData();
        StringBuffer result = teamWDS.getTeamLeagueFromWeb();

        //调用TeamParser接口 获取球队基本数据 List
        TeamParser teamParser = new TeamParserImpl();
        List<TeamPo> list = teamParser.parseTeamLeague(result);

        //添加城市信息
        for (int i = 0; i < list.size(); i++) {
            //调用TeamWDS接口 获取爬取数据
            TeamWDS teamWDS1 = new TeamData();
            StringBuffer result1 = teamWDS1.getTeamInfoFromWeb(list.get(i).getAbbr());

            //调用TeamParser接口 获取球队基本数据 List
            TeamParser teamParser1 = new TeamParserImpl();
            String city = teamParser1.parseTeamCity(result1);

            list.get(i).setCity(city);
        }

        //调用数据库
        DBManager dbManager = new DBManager(context);

        //将球队基本数据存储到SQL
        dbManager.update(list);


    }

    @Override
    public void setTeamLogoToLocal(Context context) {

    }


    //改变数据存储方式  考虑到数据量过大及数据存储格式的统一性 将使用SQL代替保存为JSON对象本地存储
//    @Override
//    public void setTeamListToSql() {
//
//        //调用TeamDS接口 获取爬取数据
//        TeamWDS teamWDS = new TeamData();
//        StringBuffer result = teamWDS.getTeamListFromWeb();
//
//
//        //调用TeamParser接口 获取球队基本数据 List
//        TeamParser teamParser = new TeamParserImpl();
//        List<TeamPo> list = teamParser.parseTeamList(result);
//
//
//        JSONObject jsonObject= new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//
//        for (int i = 0; i < list.size(); i++) {
//            JSONObject temp = new JSONObject();
//            try {
//                temp.put("abbr",list.get(i).getAbbr());
//                temp.put("name",list.get(i).getName());
//                temp.put("founded",list.get(i).getFounded());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            jsonArray.put(temp);
//        }
//        try {
//            jsonObject.put("TeamList",jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //判断SD卡是否存在
//        if (!Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
//            return;
//        }
//
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
//    }
}
