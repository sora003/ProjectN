package com.sora.projectn.Web.dataservice.impl.parser.impl;

import com.sora.projectn.Web.dataservice.impl.parser.TeamParser;

import java.util.regex.Pattern;

/**
 * Created by Sora on 2016/1/16.
 */
public class TeamParserImpl implements TeamParser{

    @Override
    public void parseTeamList(StringBuffer result) {
        Pattern p = Pattern.compile("(.*)(<div class=\"table_container p402_hide \" id=\"div_defunct\">)(.*?)(</div>)(.*)");

    }
}
