package com.sora.projectn.gc.dataservice;

import android.content.Context;

import com.sora.projectn.gc.po.MatchInfoPo;
import com.sora.projectn.gc.po.MatchPrimaryInfoPo;
import com.sora.projectn.gc.po.PlayerMatchInfoPo;
import com.sora.projectn.gc.po.TeamMatchInfoPo;

import java.util.List;

/**
 * Created by Sora on 2016/2/3.
 */
public interface MatchDS {

    /**
     * 导出所有比赛数据
     *
     * @param context
     * @return
     */
    public List<MatchPrimaryInfoPo> getMatchList(Context context);

    /**
     * 根据球员导出所有相关比赛数据
     *
     * @param name
     * @return
     */
    public List<PlayerMatchInfoPo> getPlayerMatchList(Context context,String name);

    /**
     * 根据球队导出所有相关比赛数据
     *
     * @param teamName
     * @return
     */
    public List<TeamMatchInfoPo> getTeamMatchList(Context context,String teamName);

    /**
     * 根据日期导出所有相关比赛数据
     *
     * @param context
     * @param date
     * @return
     */
    public List<MatchInfoPo> getDateMatchList(Context context,String date);


    public MatchInfoPo getNoMatch(Context context,int no);

    /**
     * 导入从startDay到endDay的所有比赛数据
     *
     * @param context
     * @param startDay
     * @param endDay
     */
    public void setMatchInfo(Context context, String startDay, String endDay);
}
