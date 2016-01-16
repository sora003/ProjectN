package com.sora.projectn.Web.parser;

/**
 * Created by Sora on 2016/1/16.
 */
public interface TeamParser {

    /**
     * 读取 球队基本数据.html有效部分
     * @param result 球队基本数据.html
     */
    public void parseTeamList(StringBuffer result);


}
