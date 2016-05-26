package com.sora.projectn.gc.WebService.parser;

import com.sora.projectn.gc.po.TeamPo;
import com.sora.projectn.gc.po.TeamSeasonGamePo;

import java.util.List;

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
     * @param result
     * @param abbr
     * @param year
     * @return TeamSeasonGamePo
     */
    public TeamSeasonGamePo parseTeamSeasonGame(StringBuffer result,String abbr,int year);


}
