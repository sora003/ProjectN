package com.sora.projectn.Web.parser.impl;

import android.util.Log;

import com.sora.projectn.Web.parser.TeamParser;
import com.sora.projectn.po.TeamInfoPo;
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
            matcher = pattern.matcher(str1);
            //查找所有符合pattern的数据
            while (matcher.find()){
                String name = matcher.group(4);
                TeamPo teamPo = new TeamPo();
                teamPo.setAbbr(matcher.group(2));
                teamPo.setName(name);
                teamPo.setFounded(Integer.parseInt(matcher.group(8)));

                //查询球队缩略名
                //新建Pattern Matcher对象
                Pattern pattern1 = Pattern.compile("(.*)( )(\\w+)");
                Matcher matcher1 = pattern1.matcher(name);
                if (matcher1.matches()){
                    teamPo.setsName(matcher1.group(3));
                }


                list.add(teamPo);
            }
        }

        return list;
    }

    @Override
    public String parseTeamCity(StringBuffer result) {
        String city = null;

        /**
         *  group(3) city 球队所在城市
         */
        pattern = Pattern.compile("(.*)(Location:</span> )(.*?)(<br><span class)");
        matcher = pattern.matcher(result);
        if (matcher.matches()){
            city = matcher.group(3);
        }

        return city;
    }

    @Override
    public List<TeamPo> parseTeamLeague(StringBuffer result) {
        List<TeamPo> list = new ArrayList<TeamPo>();

        /**
         * group(3) 东部球队数据
         */
        pattern = Pattern.compile("(.*)(<span class=\"nbaNavSubheading\">Eastern Conference</span>)(.*?)(</div>)(.*)");
        matcher = pattern.matcher(result);
        if (matcher.matches()){
            String strE = matcher.group(3);
            /**
             * group(3) 球队分区
             * group(5) 该分区包含的球队
             *
             * 正则表达式 前后不可以加(.*)  否则无法匹配到所有满足条件的子序列
             */
            pattern = Pattern.compile("(<span class=\"nbaDivision\">)(.*?)(</span>)(.*?)(</ul>)");
            matcher = pattern.matcher(strE);
            //查找所有符合pattern的数据
            while (matcher.find()){
                String conference = matcher.group(2);

                //该分区包含的球队
                String str = matcher.group(4);
                /**
                 * 源码示例：<a href="/celtics?ls=iref:nba:gnav" title="Link to Boston Celtics" class="nbaTeamBOS" target=>Boston Celtics</a>
                 * group(4) 球队缩写
                 *
                 * 正则表达式 前后不可以加(.*)  否则无法匹配到所有满足条件的子序列
                 *
                 * 且必须新建Pattern Matcher对象 否则会导致外循环引用对象丢失
                 */
                Pattern pattern1 = Pattern.compile("(<li><a href=)(.*?)(nbaTeam)(.*?)(\" target=>)");
                Matcher matcher1 = pattern1.matcher(str);
                while (matcher1.find()){
                    String abbr = matcher1.group(4);
                    //添加特殊情况判断
                    if (abbr.equals("BKN")){
                        abbr = "NJN";
                    }
                    if (abbr.equals("PHX")){
                        abbr = "PHO";
                    }

                    TeamPo teamPo = new TeamPo();
                    teamPo.setAbbr(abbr);
                    teamPo.setLeague("E");
                    teamPo.setConference(conference);
//                    Log.i("db_Conference", conference);
                    list.add(teamPo);
                }
            }
        }



        /**
         * group(3) 西部球队数据
         */
        pattern = Pattern.compile("(.*)(<span class=\"nbaNavSubheading\">Western Conference</span>)(.*?)(</div>)(.*)");
        matcher = pattern.matcher(result);
        if (matcher.matches()){
            String strW = matcher.group(3);
            /**
             * group(3) 球队分区
             * group(5) 该分区包含的球队
             *
             * 正则表达式 前后不可以加(.*)  否则无法匹配到所有满足条件的子序列
             */
            pattern = Pattern.compile("(<span class=\"nbaDivision\">)(.*?)(</span>)(.*?)(</ul>)");
            matcher = pattern.matcher(strW);
            //查找所有符合pattern的数据
            while (matcher.find()){
                String conference = matcher.group(2);
                //该分区包含的球队
                String str = matcher.group(4);
                /**
                 * 源码示例：<a href="/celtics?ls=iref:nba:gnav" title="Link to Boston Celtics" class="nbaTeamBOS" target=>Boston Celtics</a>
                 * group(4) 球队缩写
                 *
                 * 正则表达式 前后不可以加(.*)  否则无法匹配到所有满足条件的子序列
                 *
                 * 且必须新建Pattern Matcher对象 否则会导致外循环引用对象丢失
                 */
                Pattern pattern2 = Pattern.compile("(<li><a href=)(.*?)(nbaTeam)(.*?)(\" target=>)");
                Matcher matcher2 = pattern2.matcher(str);
                while (matcher2.find()){
                    String abbr = matcher2.group(4);
                    //添加特殊情况判断
                    if (abbr.equals("BKN")){
                        abbr = "NJN";
                    }
                    if (abbr.equals("PHX")){
                        abbr = "PHO";
                    }

                    TeamPo teamPo = new TeamPo();
                    teamPo.setAbbr(abbr);
                    teamPo.setLeague("W");
                    teamPo.setConference(conference);
//                    Log.i("db_Conference", conference);
                    list.add(teamPo);
                }
            }
        }

        return list;
    }


}
