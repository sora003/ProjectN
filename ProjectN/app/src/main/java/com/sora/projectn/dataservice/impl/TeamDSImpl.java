package com.sora.projectn.dataservice.impl;

import android.content.Context;

import com.sora.projectn.database.DBManager;
import com.sora.projectn.dataservice.TeamDS;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Sora on 2016/1/25.
 */
public class TeamDSImpl implements TeamDS{

    Pattern pattern = Pattern.compile("(.*)(\\w+)(.*?)");
    @Override
    public List<String> getTeamAbbrFromSql(Context context) {

        List<String> list = new ArrayList<String>();

        //调用数据库
        DBManager dbManager = new DBManager(context);

        //查找球队基本数据 并返回 list
        list = dbManager.queryTeamAbbr();

        return list;
    }
}

