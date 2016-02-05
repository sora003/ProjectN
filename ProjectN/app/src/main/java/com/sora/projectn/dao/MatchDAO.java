package com.sora.projectn.dao;

import android.content.Context;

import com.sora.projectn.po.MatchPrimaryInfoPo;

import java.util.List;

/**
 * Created by Sora on 2016/2/3.
 */
public interface MatchDAO {


    public List<MatchPrimaryInfoPo> getMatchList(Context context);


    /**
     * 导入从startDay到endDay的所有比赛数据
     *
     * @param context
     * @param startDay
     * @param endDay
     */
    public void setMatchInfo(Context context, String startDay, String endDay);
}
