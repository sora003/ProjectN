package com.sora.projectn.dataservice;

import android.content.Context;

import java.util.List;

/**
 * Created by Sora on 2016/1/25.
 */
public interface TeamDS {



    /**
     * 读取存储在SQL的球队基本数据 返回List<String>
     *
     * @return List<String>
     * List<String> ：
     *      TeamPo.abbr
     */
    public List<String> getTeamAbbrFromSql(Context context);



}
