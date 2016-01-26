package com.sora.projectn.dataservice;

import android.content.Context;
import android.graphics.Bitmap;

import com.sora.projectn.po.TeamPo;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/25.
 */
public interface TeamDS {



    /**
     * 读取存储在SQL的球队基本数据 返回List<String>
     *@param context
     * @return List<String>
     * List<String> ：
     *      TeamPo.abbr
     */
    public List<String> getTeamAbbr(Context context);


    /**
     * 读取存储在SQL的球队基本数据 返回List<TeamPo>
     *@param context
     * @return List<TeamPo>
     */
    public List<TeamPo> getTeamList(Context context);

    /**
     * 读取存储在SQL的球队所在分区和缩略名信息 返回Map<String,String>
     * @param context
     * @return Map<String,String>
     */
    public Map<String,String> getTeamSNameAndConference(Context context);

    /**
     * 查找球队缩略名和缩写信息  返回Map<String,String>
     * @param context
     * @return Map<String,String>
     */
    public Map<String,String> getTeamSNameAndAbbr(Context context);


    /**
     * 根据球队缩略名查询球队缩写
     * @param context
     * @param sName
     * @return String
     */
    public String getTeamAbbr(Context context,String sName);

    /**
     * 根据球队缩略名获取球队logo
     * @param context
     * @param abbr
     * @return Bitmap
     */
    public Bitmap getTeamLogo(Context context,String abbr);

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
     * @param context
     */
    public void setTeamLogo(Context context);


}
