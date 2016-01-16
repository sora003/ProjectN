package com.sora.projectn.Web.parser.impl;

import com.sora.projectn.Web.parser.TeamParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sora on 2016/1/16.
 */
public class TeamParserImpl implements TeamParser{

    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void parseTeamList(StringBuffer result) {
        // 取出<div class="table_container p402_hide " id="div_defunct"> 和 </div> 之间的部分
        pattern = Pattern.compile("(.*)(<div class=\"table_container p402_hide \" id=\"div_defunct\">)(.*?)(</div>)(.*)");
        matcher = pattern.matcher(result);
        if (matcher.matches()){
            String str1 = matcher.group(3);
            pattern = Pattern.compile("");
        }
    }
}
