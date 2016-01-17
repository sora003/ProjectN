package com.sora.projectn.Web.parser.impl;

import com.sora.projectn.Web.parser.TeamParser;
import com.sora.projectn.po.TeamPo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sora on 2016/1/16.
 */
public class TeamParserImpl implements TeamParser{

    private Pattern pattern;
    private Matcher matcher;

    @Override
    public List<TeamPo> parseTeamList(StringBuffer result) {

        List<TeamPo> list = new ArrayList<TeamPo>();

        /**
         * group(3) 球队信息
         */
        pattern = Pattern.compile("(.*)(<div class=\"table_container p402_hide \" id=\"div_active\">)(.*?)(</div>)(.*)");
        matcher = pattern.matcher(result);
        if (matcher.matches()){
            String str1 = matcher.group(3);
            /**
             * 源码示例：<td align="left" ><a href="/teams/ATL/">Atlanta Hawks</a></td>   <td align="left" >NBA</td>   <td align="right" >1950</td>
             * group(2) 球队缩写
             * group(4) 球队名
             * group(8) 球队创建时间
             *
             * 正则表达式 前后不可以加(.*)  否则无法匹配到所有满足条件的子序列
             */
            pattern = Pattern.compile("(<td align=\"left\" ><a href=\"/teams/)(.*?)(/\">)(.*?)(</a></td>   <td align=\"left\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>)");
            //
            matcher = pattern.matcher(str1);
            if (matcher.matches()){
                TeamPo teamPo = new TeamPo();
                teamPo.setAbbr(matcher.group(2));
                teamPo.setName(matcher.group(4));
                teamPo.setFounded(Integer.parseInt(matcher.group(8)));
                list.add(teamPo);
            }
        }

        return list;
    }
}
