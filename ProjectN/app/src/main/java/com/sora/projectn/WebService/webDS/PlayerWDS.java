package com.sora.projectn.WebService.webDS;

import java.util.List;

/**
 * Created by Sora on 2016/1/27.
 */
public interface PlayerWDS {

    /**
     * 根据球队缩写列表和当前赛季年份 爬取球员基本数据.html表
     *
     * @param list 球队缩写列表
     * @param year  当前赛季年份
     * @return  List<StringBuffer> 爬取球员基本数据.html表
     */
    public List<StringBuffer>  getPlayerInfo(List<String> list,int year);

    /**
     * 根据路径 爬取球员职业生涯的数据
     *
     * @param path 球员数据路径
     * @return  StringBuffer 爬取球员职业生涯的数据
     */
    public StringBuffer getPlayerCareerInfo(String path);
}
