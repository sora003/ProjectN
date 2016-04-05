package com.sora.projectn.gc.dataservice.impl;

import android.content.Context;

import com.sora.projectn.model.dao.impl.MatchDAOImpl;
import com.sora.projectn.gc.dataservice.MatchDS;
import com.sora.projectn.gc.po.MatchInfoPo;
import com.sora.projectn.gc.po.MatchPrimaryInfoPo;
import com.sora.projectn.gc.po.PlayerMatchInfoPo;
import com.sora.projectn.gc.po.TeamMatchInfoPo;

import java.util.List;

/**
 * Created by Sora on 2016/2/3.
 */
public class MatchDSImpl implements MatchDS {

    @Override
    public List<MatchPrimaryInfoPo> getMatchList(Context context) {
        return new MatchDAOImpl().getMatchList(context);
    }

    @Override
    public List<PlayerMatchInfoPo> getPlayerMatchList(Context context,String name) {
        return new MatchDAOImpl().getPlayerMatchList(context,name);
    }

    @Override
    public List<TeamMatchInfoPo> getTeamMatchList(Context context,String teamName) {
        return new MatchDAOImpl().getTeamMatchList(context,teamName);
    }

    @Override
    public List<MatchInfoPo> getDateMatchList(Context context, String date) {
        return new MatchDAOImpl().getDateMatchList(context,date);
    }

    @Override
    public MatchInfoPo getNoMatch(Context context, int no) {
        return new MatchDAOImpl().getNoMatch(context,no);
    }

    @Override
    public void setMatchInfo(Context context, String startDay, String endDay) {
        new MatchDAOImpl().setMatchInfo(context,startDay,endDay);
    }


}
