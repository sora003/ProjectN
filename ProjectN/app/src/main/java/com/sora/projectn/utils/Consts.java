package com.sora.projectn.utils;

/**
 * Created by Sora on 2016-04-26.
 */
public class Consts {

    /**
     * Server地址
     */
    
    private static final  String url = "http://192.168.31.225:8080/NBADataSystem/";

    public static final  String getTeams = url + "getTeams.do";

    public static final  String getTeamInfos = url + "getTeamInfos.do";

    public static final  String getTeamPlayerList = url + "getTeamPlayerList.do";

    public static final  String getTeamSeasonStatistics = url + "getTeamSeasonStatistics.do";

    public static final  String getTeamSeasonRanks = url + "getTeamSeasonRanks.do";

    public static final String teamrank = url + "getTeamSeasonRanks.do";

    public static final String playerrank = url + "getPlayerRanks.do";

    public static final String dayrank = url + "getPlayerRanks.do?date=2016-03-21";


    /**
     * Helper参数
     */
    public static final  String resourceFromServer = "server";

    public static final  String resourceFromCache = "cache";

    public static final  String ToastMessage01 = "无法获取数据";


    /**
     * Handler参数
     */
    public static final int SET_VIEW = 0xF01;

    public static final int RES_ERROR = 0xF02;

    public static final int TURN_ACTIVITY = 0xF03;

    /**
     * 分区参数
     */
    public static final String Southwest = "西南区";

    public static final String Pacific = "太平洋区";

    public static final String Northwest = "西北区";

    public static final String Southeast = "东南区";

    public static final String Central = "中部区";

    public static final String Atlantic = "大西洋区";



}
