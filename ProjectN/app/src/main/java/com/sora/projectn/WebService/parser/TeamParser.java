package com.sora.projectn.WebService.parser;

import com.sora.projectn.po.TeamPo;
import com.sora.projectn.po.TeamSeasonGamePo;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/16.
 */
public interface TeamParser {

    /**
     * 读取 球队基本数据.html有效部分
     *
     * @param result 球队基本数据.html
     * @return List<TeamPo>
     *  List<TeamPo> ：
     *      TeamPo.abbr
     *      TeamPo.name
     *      TeamPo.founded
     *      TeamPo.sName
     */
    public List<TeamPo> parseTeamList(StringBuffer result);


    /**
     * 读取 球队详细数据.html有效部分
     *
     * @param result  球队详细数据.html
     * @return String city 球队所在城市
     */
    public String parseTeamCity(StringBuffer result);


    /**
     * 读取 球队联盟数据.html有效部分
     *
     * @param result 球队联盟数据.html
     * @return List<TeamPo>
     *  List<TeamPo> ：
     *      TeamPo.abbr
     *      TeamPo.league
     *      TeamPo.conference
     */
    public List<TeamPo> parseTeamLeague(StringBuffer result);


    /**
     * 读取 球队最新赛季比赛数据.html有效部分
     *
     * @param result  球队最新赛季比赛数据.html
     * @param year  最新赛季年
     * @return List<TeamSeasonGamePo>
     *      TeamSeasonGamePo.abbr
     *      TeamSeasonGamePo.year
     *      TeamSeasonGamePo.win
     *      TeamSeasonGamePo.lose
     *      TeamSeasonGamePo.mp
     *      TeamSeasonGamePo.fg
     *      TeamSeasonGamePo.fga
     *      TeamSeasonGamePo.p3
     *      TeamSeasonGamePo.p3a
     *      TeamSeasonGamePo.p2
     *      TeamSeasonGamePo.p2a
     *      TeamSeasonGamePo.ft
     *      TeamSeasonGamePo.fta
     *      TeamSeasonGamePo.orb
     *      TeamSeasonGamePo.drb
     *      TeamSeasonGamePo.trb
     *      TeamSeasonGamePo.ast
     *      TeamSeasonGamePo.stl
     *      TeamSeasonGamePo.blk
     *      TeamSeasonGamePo.tov
     *      TeamSeasonGamePo.pf
     *      TeamSeasonGamePo.pts
     *      TeamSeasonGamePo.rank
     */
    public TeamSeasonGamePo parseTeamSeasonGame(StringBuffer result,String abbr,int year);


}
