package com.sora.projectn.dataservice.impl;

import android.content.Context;

import com.sora.projectn.Web.parser.TeamParser;
import com.sora.projectn.Web.parser.impl.TeamParserImpl;
import com.sora.projectn.Web.webDS.TeamWDS;
import com.sora.projectn.Web.webDS.impl.TeamData;
import com.sora.projectn.database.DBManager.TeamDBManager;
import com.sora.projectn.dataservice.TeamDS;
import com.sora.projectn.po.TeamPo;

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
        for (int i = 0; i < list.size(); i++) {
            //调用TeamWDS接口 获取爬取数据
            TeamWDS teamWDS1 = new TeamData();
            StringBuffer result1 = teamWDS1.getTeamInfoFromWeb(list.get(i).getAbbr());

            //调用TeamParser接口 获取球队基本数据 List
            TeamParser teamParser1 = new TeamParserImpl();
            String city = teamParser1.parseTeamCity(result1);

            list.get(i).setCity(city);
        }


        new TeamDBManager(context).update(list);



    }


}

