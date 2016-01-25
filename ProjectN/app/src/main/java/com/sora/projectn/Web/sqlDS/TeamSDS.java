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
     *      TeamPo.sName
     */
    public void setTeamListToSql(Context context);








    /**
     * 根据球队名 获取从网页爬取并分析得到的球队具体数据 存储在SQL
     *
     * @param context
     */
    public void setTeamListInfoToSql(Context context);


}
