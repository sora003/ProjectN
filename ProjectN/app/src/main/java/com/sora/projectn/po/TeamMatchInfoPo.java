package com.sora.projectn.po;

import java.util.List;

/**
 * Created by Sora on 2016/2/6.
 *
 * 代码复用
 */
public class TeamMatchInfoPo {

    /**
     * 比赛时间
     */
    private String time;
    /**
     * 队伍名
     */
    private String name;
    /**
     * 对手队伍名
     */
    private String rivalName;
    /**
     * 比赛状态 >0赢了，=0平局，<0输了TAT，數值為分差
     */
    private int status;
    /**
     * 分节比分，长度为4
     */
    private short[] periodScore = new short[4];
    /**
     * 总分
     */
    private short totalScore;
    /**
     * 参赛成员列表
     * @see PlayerMatchInfoPo
     */
    private List<PlayerMatchInfoPo> playerList;

    /**
     * 对方参赛成员列表
     * @see PlayerMatchInfoPo
     */
    private List<PlayerMatchInfoPo> rivalPlayerList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short[] getPeriodScore() {
        return periodScore;
    }

    public void setPeriodScore(short[] periodScore) {
        this.periodScore = periodScore;
    }

    public List<PlayerMatchInfoPo> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<PlayerMatchInfoPo> playerList) {
        this.playerList = playerList;
    }

    public String getRivalName() {
        return rivalName;
    }

    public void setRivalName(String rivalName) {
        this.rivalName = rivalName;
    }

    public List<PlayerMatchInfoPo> getRivalPlayerList() {
        return rivalPlayerList;
    }

    public void setRivalPlayerList(List<PlayerMatchInfoPo> rivalPlayerList) {
        this.rivalPlayerList = rivalPlayerList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public short getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(short totalScore) {
        this.totalScore = totalScore;
    }
}
