package com.sora.projectn.Web.ioservice;

import com.sora.projectn.po.TeamPo;

import java.util.List;

/**
 * Created by Sora on 2016/1/17.
 */
public interface TeamIO {

    /**
     * 获取球队基本数据 存储在本地SDCard
     *
     * 用json封装数据并存储
     * List<TeamPo> ：
     *      TeamPo.abbr
     *      TeamPo.name
     *      TeamPo.founded
     */
    public void saveTeamList();


}
