package com.sora.projectn.WebService.parser.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.sora.projectn.WebService.parser.TeamParser;
import com.sora.projectn.po.TeamPo;
import com.sora.projectn.po.TeamSeasonGamePo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

                Log.i("爬取球队基本信息",matcher.group(2));

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

                    Log.i("爬取球队联盟信息", abbr);


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

                    Log.i("爬取球队联盟信息", abbr);


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

    @Override
    public TeamSeasonGamePo parseTeamSeasonGame(StringBuffer result,String abbr,int year) {
        TeamSeasonGamePo po = new TeamSeasonGamePo();



        po.setYear(year);

        /**
         * 源码示例：<span class="bold_text">Expected W-L</span></a>: 9-38 (29th of 30)<p>
         *
         * group(2) 胜场
         * group(4) 负场
         * group(6) 排名
         *
         */
        pattern = Pattern.compile("(<span class=\"bold_text\">Expected W-L</span></a>: )(.*?)(-)(.*?)( \\()(.*?)( of 30\\)<p>)");
        matcher = pattern.matcher(result);
        if (matcher.find()){
            int win = Integer.parseInt(matcher.group(2));
            int lose = Integer.parseInt(matcher.group(4));

            po.setWin(win);
            po.setLose(lose);

            String sRank = matcher.group(6);

            pattern = Pattern.compile("[^0-9]");
            matcher = pattern.matcher(sRank);
            int rank = Integer.parseInt(matcher.replaceAll(""));

            po.setRank(rank);
        }




        /**
         *  group(2) city 球队最新赛季比赛数据信息
         *
         *  查询符合该正则表达式的第1串数据
         */
        pattern = Pattern.compile("(<tr  class=\"\">   <td align=\"left\" >Team</td>)(.*?)(</tr>)");
        matcher = pattern.matcher(result);
        if (matcher.find()){
            String str1 = matcher.group(2);
            /**
             * group(4) 比赛时长
             * group(6) 命中
             * group(8) 出手
             * group(12) 3分命中
             * group(14) 3分出手
             * group(18) 2分命中
             * group(20) 2分出手
             * group(24) 罚球命中
             * group(26) 罚球出手
             * group(30) 进攻
             * group(32) 防守
             * group(34) 篮板
             * group(36) 助攻
             * group(38) 抢断
             * group(40) 盖帽
             * group(42) 失误
             * group(44) 犯规
             * group(46) 得分
             */
            pattern = Pattern.compile("(   <td align=\"right\" >)(.*)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)" +
                    "(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)" +
                    "(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)" +
                    "(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)" +
                    "(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>)");
            matcher = pattern.matcher(str1);
            if (matcher.matches()){
                //取对应参数
                int mp = Integer.parseInt(matcher.group(4));
                int fg = Integer.parseInt(matcher.group(6));
                int fga = Integer.parseInt(matcher.group(8));
                int p3 = Integer.parseInt(matcher.group(12));
                int p3a = Integer.parseInt(matcher.group(14));
                int p2 = Integer.parseInt(matcher.group(18));
                int p2a = Integer.parseInt(matcher.group(20));
                int ft = Integer.parseInt(matcher.group(24));
                int fta = Integer.parseInt(matcher.group(26));
                int orb = Integer.parseInt(matcher.group(30));
                int drb = Integer.parseInt(matcher.group(32));
                int trb = Integer.parseInt(matcher.group(34));
                int ast = Integer.parseInt(matcher.group(36));
                int stl = Integer.parseInt(matcher.group(38));
                int blk = Integer.parseInt(matcher.group(40));
                int tov = Integer.parseInt(matcher.group(42));
                int pf = Integer.parseInt(matcher.group(44));
                int pts = Integer.parseInt(matcher.group(46));



                //为po对象添加值
                po.setMp(mp);
                po.setFg(fg);
                po.setFga(fga);
                po.setP3(p3);
                po.setP3a(p3a);
                po.setP2(p2);
                po.setP2a(p2a);
                po.setFt(ft);
                po.setFta(fta);
                po.setOrb(orb);
                po.setDrb(drb);
                po.setTrb(trb);
                po.setAst(ast);
                po.setStl(stl);
                po.setBlk(blk);
                po.setTov(tov);
                po.setPf(pf);
                po.setPts(pts);
                po.setAbbr(abbr);
            }

        }


        Log.i("爬取球队赛季数据",abbr);
        return po;

    }


}
