package com.sora.projectn.gc.WebService.webDS;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/16.
 */
public interface TeamWDS {

    /**
     * 爬取球队基本数据
     *
     * @return 球队基本数据.html
     */
    public StringBuffer getTeamList();


    /**
     * 根据球队缩写 爬取球队logo 返回Map<String, Bitmap>
     *
     * @param list abbr 球队缩写
     * @return Map<String,Bitmap>
     *     key      -   abbr 球队缩写
     *     value    -   Bitmap 球队Logo
     */
    public Map<String, Bitmap> getTeamLogo(List<String> list);

    /**
     *
     * @param abbr 球队缩写
     * @return 球队具体数据.html
     */
    public StringBuffer getTeamInfo(String abbr);

    /**
     *
     * @return  球队联盟数据.html
     */
    public StringBuffer getTeamLeague();

    /**
     *
     * @param abbr 球队缩写
     * @param year  最新赛季年
     * @return 球队最新赛季数据.html
     */
    public StringBuffer  getTeamSeasonGame(String abbr , int year);
}
