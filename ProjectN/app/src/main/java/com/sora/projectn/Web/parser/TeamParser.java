package com.sora.projectn.Web.parser;

import com.sora.projectn.po.TeamPo;

import java.util.List;

/**
 * Created by Sora on 2016/1/16.
 */
public interface TeamParser {

    /**
     * 读取 球队基本数据.html有效部分
     * @param result 球队基本数据.html
     * @return List<TeamPo>
     *  List<TeamPo> ：
     *      TeamPo.abbr
     *      TeamPo.name
     *      TeamPo.founded
     */
    public List<TeamPo> parseTeamList(StringBuffer result);


}
