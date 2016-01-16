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
        /**
         * group(3) 球队信息
         */
        pattern = Pattern.compile("(.*)(<div class=\"table_container p402_hide \" id=\"div_active\">)(.*?)(</div>)(.*)");
        matcher = pattern.matcher(result);
        if (matcher.matches()){
            String str1 = matcher.group(3);
            /**
             * group(3) 球队缩写
             * group(5) 球队名
             * group(9) 球队创建时间
             */
            pattern = Pattern.compile("(.*)(<td align=\"left\" ><a href=\"/teams/)(.*?)(/\">)(.*?)(</a></td>   <td align=\"left\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)()");
            matcher = pattern.matcher(str1);

        }
    }
}
