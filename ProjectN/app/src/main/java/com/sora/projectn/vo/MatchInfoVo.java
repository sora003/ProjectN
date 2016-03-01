package com.sora.projectn.vo;

/**
 * Created by Sora on 2016/2/7.
 *
 * 显示某日比赛列表的每一项比赛的基本信息
 * 用于MatchListActivity
 */
public class MatchInfoVo {
    /**
     * 比赛序列号
     */
    private int id;
    /**
     * 球队A名字
     */
    private String teamAName;
    /**
     * 球队B名字
     */
    private String teamBName;
    /**
     * 球队A总分
     */
    private short scoreA;
    /**
     * 球队B总分
     */
    private short scoreB;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getScoreA() {
        return scoreA;
    }

    public void setScoreA(short scoreA) {
        this.scoreA = scoreA;
    }

    public short getScoreB() {
        return scoreB;
    }

    public void setScoreB(short scoreB) {
        this.scoreB = scoreB;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }
}
