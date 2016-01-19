package com.sora.projectn.Web.ioservice;

import android.content.Context;

import com.sora.projectn.po.TeamPo;

import java.util.List;

/**
 * Created by Sora on 2016/1/17.
 */
public interface TeamIO {

    /**
     * 获取从网页爬取并分析得到的球队基本数据 存储在SQL
     *
     * List<TeamPo> ：
     *      TeamPo.abbr
     *      TeamPo.name
     *      TeamPo.founded
     */
    public void setTeamListToSql(Context context);

    /**
     * 读取存储在SQL的球队基本数据 返回List<TeamPo>
     *
     * @return List<TeamPo>
     * List<TeamPo> ：
     *      TeamPo.abbr
     *      TeamPo.name
     *      TeamPo.founded
     */
    public List<TeamPo> getTeamListFromSql(Context context);
}
