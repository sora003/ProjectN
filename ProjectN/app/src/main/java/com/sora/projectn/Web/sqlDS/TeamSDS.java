package com.sora.projectn.Web.sqlDS;

import android.content.Context;

import java.util.List;

/**
 * Created by Sora on 2016/1/17.
 */
public interface TeamSDS {

    /**
     * 获取从网页爬取并分析得到的球队基本数据 存储在SQL
     *
     * @param context
     * List<TeamPo> ：
     *      TeamPo.abbr
     *      TeamPo.name
     *      TeamPo.founded
     */
    public void setTeamListToSql(Context context);




    /**
     * 读取存储在SQL的球队基本数据 返回List<String>
     *
     * @return List<String>
     * List<String> ：
     *      TeamPo.abbr
     */
    public List<String> getTeamAbbrFromSql(Context context);




    /**
     * 根据球队名 获取从网页爬取并分析得到的球队具体数据 存储在SQL
     *
     * @param context
     */
    public void setTeamInfoToSql(Context context);

    /**
     * 获取球队logo存储的本地路径 存储在SQL
     *
     * @param context
     */
    public void setTeamLogoToLocal(Context context);
}
