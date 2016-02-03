package com.sora.projectn.WebService.webDS;

/**
 * Created by Sora on 2016/2/3.
 */
public interface MatchWDS {

    /**
     * 获取某一天的比赛列表.html
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 某一天的比赛列表.html
     */
    public StringBuffer getMatchList(int year,int month,int day);


    /**
     * 获取某一场比赛的具体信息
     *
     * @param matchPath 比赛的网络路径
     * @return 比赛的具体信息.html
     */
    public StringBuffer getMatchInfo(String matchPath);
}
