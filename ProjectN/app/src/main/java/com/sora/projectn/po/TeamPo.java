package com.sora.projectn.po;

/**
 * Created by Sora on 2016/1/17.
 *
 * 代码复用
 *
 * 球队基本数据
 *
 * name         球队名字
 * abbr         球队缩写
 * city         球队所在城市
 * league       球队所在赛区
 * conference   球队所在分区
 * arena        球队所在主场
 * founded      球队成立时间
 */

public class TeamPo {



    private String name;
    private String abbr;
    private String city;
    private String league;
    private String conference;
    private String arena;
    private int founded;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getArena() {
        return arena;
    }

    public void setArena(String arena) {
        this.arena = arena;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public int getFounded() {
        return founded;
    }

    public void setFounded(int founded) {
        this.founded = founded;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
