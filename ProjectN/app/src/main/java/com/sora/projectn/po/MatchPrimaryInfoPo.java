package com.sora.projectn.po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/2/5.
 *
 * 代码复用
 * id  比赛编号
 * season 赛季
 * time 比赛时间
 * teamAName 比赛队伍A名字
 * teamBName 比赛队伍B名字
 * scoreA 比赛队伍A总分数
 * scoreB 比赛队伍B总分数
 * periodScoreA 比赛队伍A小节分数
 * periodScoreB 比赛队伍B小节分数
 * TeamAList 比赛队伍A的球员名单以及数据
 * TeamBList 比赛队伍B的球员名单以及数据
 */
public class MatchPrimaryInfoPo {

    private int id;
    private String season;
    private String time;
    private String teamAName;
    private String teamBName;
    private short scoreA;
    private short scoreB;
    private List<Short> periodScoreA;
    private List<Short> periodScoreB;
    private List<PlayerMatchInfoPo> teamAList;
    private List<PlayerMatchInfoPo> teamBList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Short> getPeriodScoreA() {
        return periodScoreA;
    }

    public void setPeriodScoreA(List<Short> periodScoreA) {
        this.periodScoreA = periodScoreA;
    }

    public List<Short> getPeriodScoreB() {
        return periodScoreB;
    }

    public void setPeriodScoreB(List<Short> periodScoreB) {
        this.periodScoreB = periodScoreB;
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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public List<PlayerMatchInfoPo> getTeamAList() {
        return teamAList;
    }

    public void setTeamAList(List<PlayerMatchInfoPo> teamAList) {
        this.teamAList = teamAList;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

    public List<PlayerMatchInfoPo> getTeamBList() {
        return teamBList;
    }

    public void setTeamBList(List<PlayerMatchInfoPo> teamBList) {
        this.teamBList = teamBList;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
