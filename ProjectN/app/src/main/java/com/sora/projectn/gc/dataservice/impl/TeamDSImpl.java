package com.sora.projectn.gc.dataservice.impl;

import android.content.Context;
import android.graphics.Bitmap;

import com.sora.projectn.gc.model.dao.impl.TeamDAOImpl;
import com.sora.projectn.gc.database.DBManager.TeamDBManager;
import com.sora.projectn.gc.dataservice.TeamDS;
import com.sora.projectn.gc.po.TeamPo;
import com.sora.projectn.gc.po.TeamSeasonGamePo;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/25.
 */
public class TeamDSImpl implements TeamDS{


    @Override
    public List<String> getTeamAbbr(Context context) {
        return new TeamDAOImpl().getTeamAbbr(context);
    }

    @Override
    public String getTeamLeague(Context context, String abbr) {
        return new TeamDAOImpl().getTeamLeague(context,abbr);
    }

    @Override
    public TeamPo getTeamInfo(Context context,String abbr) {
        return new TeamDAOImpl().getTeamInfo(context, abbr);
    }

    @Override
    public Map<String, String> getTeamSNameAndConference(Context context) {
        return new TeamDAOImpl().getTeamSNameAndConference(context);
    }

    @Override
    public Map<String, String> getTeamSNameAndAbbr(Context context) {
        return new TeamDAOImpl().getTeamSNameAndAbbr(context);
    }

    @Override
    public String getTeamSName(Context context, String abbr) {
        TeamDBManager db = new TeamDBManager(context);
        String sName = db.queryTeamSName(abbr);
        db.closeDB();
        return sName;
    }

    @Override
    public String getTeamAbbr(Context context, String sName) {
        return new TeamDAOImpl().getTeamAbbr(context, sName);
    }

    @Override
    public Bitmap getTeamLogo(Context context, String abbr) {
        return new TeamDAOImpl().getTeamLogo(context,abbr);
    }



    @Override
    public int getTeamSeasonGameYear(Context context, String abbr) {
        return new TeamDAOImpl().getTeamSeasonGameYear(context, abbr);
    }

    @Override
    public TeamSeasonGamePo getTeamSeasonGameInfo(Context context, String abbr) {
        return new TeamDAOImpl().getTeamSeasonGameInfo(context, abbr);
    }

    @Override
    public void setTeamList(Context context) {
        new TeamDAOImpl().setTeamList(context);
    }



    @Override
    public void setTeamListInfo(Context context) {
        new TeamDAOImpl().setTeamListInfo(context);
    }


    @Override
    public void setTeamLogo(Context context) {
        new TeamDAOImpl().setTeamLogo(context);
    }



    @Override
    public void setTeamSeasonGame(Context context,int year) {

        new TeamDAOImpl().setTeamSeasonGame(context,year);

    }
}

