package com.sora.projectn.dataservice;

import android.content.Context;
import android.graphics.Bitmap;

import com.sora.projectn.po.TeamPo;
import com.sora.projectn.po.TeamSeasonGamePo;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/25.
 */
public interface TeamDS {



    /**
     * 读取存储在SQL的球队基本数据 返回List<String>
     *
     * @param context
     * @return List<String>
     * List<String> ：
     *      TeamPo.abbr
     */
    public List<String> getTeamAbbr(Context context);


    /**
     * 读取存储在SQL的球队基本数据 返回List<TeamPo>
     *
     * @param context
     * @return List<TeamPo>
     */
    public List<TeamPo> getTeamList(Context context);

    /**
     * 读取存储在SQL的球队所在分区和缩略名信息 返回Map<String,String>
     *
     * @param context
     * @return Map<String,String>
     */
    public Map<String,String> getTeamSNameAndConference(Context context);

    /**
     * 查找球队缩略名和缩写信息  返回Map<String,String>
     *
     * @param context
     * @return Map<String,String>
     */
    public Map<String,String> getTeamSNameAndAbbr(Context context);


    /**
     * 根据球队缩略名查询球队缩写
     *
     * @param context
     * @param sName
     * @return String
     */
    public String getTeamAbbr(Context context,String sName);

    /**
     * 根据球队缩略名获取球队logo
     *
     * @param context
     * @param abbr
     * @return Bitmap
     */
    public Bitmap getTeamLogo(Context context,String abbr);

//    /**
//     * 根据球队缩略名获取最新赛季球队数据是否存储
//     *
//     * @param context
//     * @param abbr  球队缩写
//     * @return int 新赛季球队数据是否存取
//     */
//    public int getTeamSeasonGameHasData(Context context,String abbr);
//
//    /**
//     * 根据球队缩略名获取最新赛季球队数据的存储时间
//     *
//     * @param context
//     * @param abbr  球队缩写
//     * @return 最新赛季球队数据的存储时间
//     */
//    public int getTeamSeasonGameSetTime(Context context,String abbr);


    /**
     * 根据球队缩略名获取最新赛季年份
     *
     * @param context
     * @param abbr  球队缩写
     * @return 最新赛季年份
     */
    public int getTeamSeasonGameYear(Context context,String abbr);


    /**
     * 根据球队缩略名获取最新赛季球队数据
     *
     * @param context
     * @param abbr 球队缩写
     * @return 最新赛季球队数据
     */
    public TeamSeasonGamePo getTeamSeasonGameInfo(Context context,String abbr);


    /**
     * 获取从网页爬取并分析得到的球队基本数据 存储在SQL
     *
     * @param context
     * List<TeamPo> ：
     *      TeamPo.abbr
     *      TeamPo.name
     *      TeamPo.founded
     *      TeamPo.sName
     */
    public void setTeamList(Context context);


    /**
     * 根据球队名 获取从网页爬取并分析得到的球队具体数据 存储在SQL
     *
     * @param context
     */
    public void setTeamListInfo(Context context);


    /**
     * 将bitmap存入本地
     *
     * @param context
     */
    public void setTeamLogo(Context context);


    /**
     * 获取从网页爬取并分析得到的球队基本数据 取得Abbr值 存储在SQL
     *
     * @param context
     */
    public void setTeamSeasonGameAbbr(Context context);

    /**
     * 获取从网页爬取并分析得到的球队最新赛季比赛数据 存储在SQL
     *
     * @param context
     */
    public void setTeamSeasonGame(Context context);

}
