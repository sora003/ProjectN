package com.sora.projectn.gc.model.vo;

import android.graphics.Bitmap;

/**
 * Created by Sora on 2016/1/28.
 *
 * 球队基本数据
 *
 * abbr         球队缩写
 * bmp          球队Logo
 * name         球队全名
 * season       当前赛季年
 * win_lose     球队最新赛季胜负情况
 * rank         球队排名
 */
public class TeamInfoVo {
    private String abbr;
    private Bitmap bmp;
    private String name;
    private String season;
    private String win_lose;
    private String rank;


    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getWin_lose() {
        return win_lose;
    }

    public void setWin_lose(String win_lose) {
        this.win_lose = win_lose;
    }
}
