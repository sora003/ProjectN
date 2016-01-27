package com.sora.projectn.WebService.parser.impl;

import com.sora.projectn.WebService.parser.TeamParser;
import com.sora.projectn.po.TeamPo;
import com.sora.projectn.po.TeamSeasonGamePo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @Override
    public TeamSeasonGamePo parseTeamSeasonGame(StringBuffer result) {
        TeamSeasonGamePo po = new TeamSeasonGamePo();

        //查询符合该正则表达式的第一串数据
        pattern = Pattern.compile("(<tbody><tr  class=\"\">)(.*?)(</tr>)(.*)");
        matcher = pattern.matcher(result);
        if (matcher.find()){
            String str = matcher.group(2);
            /**
             * group(3) 赛季年份
             * group(7) 胜场
             * group(9) 负场
             * group(21) 比赛时长
             * group(23) 命中
             * group(25) 出手
             * group(29) 3分命中
             * group(31) 3分出手
             * group(35) 2分命中
             * group(37) 2分出手
             * group(41) 罚球命中
             * group(43) 罚球出手
             * group(47) 进攻
             * group(49) 防守
             * group(51) 篮板
             * group(53) 助攻
             * group(55) 抢断
             * group(57) 盖帽
             * group(59) 失误
             * group(61) 犯规
             * group(63) 得分
             */
            pattern = Pattern.compile("(.*)(<a href=\"/leagues/NBA_)(.*?)(.html\">NBA</a>)(.*)(</a></td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*)" +
                    "(</td>   <td align=\"\" ></td>   <td align=\"right\" >)(.*)(</td>   <td align=\"right\" >)(.*)(</td>   <td align=\"right\" >)(.*)(</td>   <td align=\"\" ></td>   <td align=\"right\" >)(.*)" +
                    "(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)" +                //group(20-29)
                    "(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)" +                //group(30-39)
                    "(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)" +                //group(40-49)
                    "(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)" +                //group(50-59)
                    "(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>)");                                                                                                                         //group(60-63)
            matcher = pattern.matcher(str);

            if (matcher.matches()){

                //取对应参数
                int year = Integer.parseInt(matcher.group(3));
                int win = Integer.parseInt(matcher.group(7));
                int lose = Integer.parseInt(matcher.group(9));
                int mp = Integer.parseInt(matcher.group(21));
                int fg = Integer.parseInt(matcher.group(23));
                int fga = Integer.parseInt(matcher.group(25));
                int p3 = Integer.parseInt(matcher.group(29));
                int p3a = Integer.parseInt(matcher.group(31));
                int p2 = Integer.parseInt(matcher.group(35));
                int p2a = Integer.parseInt(matcher.group(37));
                int ft = Integer.parseInt(matcher.group(41));
                int fta = Integer.parseInt(matcher.group(43));
                int orb = Integer.parseInt(matcher.group(47));
                int drb = Integer.parseInt(matcher.group(49));
                int trb = Integer.parseInt(matcher.group(51));
                int ast = Integer.parseInt(matcher.group(53));
                int stl = Integer.parseInt(matcher.group(55));
                int blk = Integer.parseInt(matcher.group(57));
                int tov = Integer.parseInt(matcher.group(59));
                int pf = Integer.parseInt(matcher.group(61));
                int pts = Integer.parseInt(matcher.group(63));

                //为po对象添加值
                po.setYear(year);
                po.setWin(win);
                po.setLose(lose);
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
            }
        }

        //有数据存入 设置hasData为1
        po.setHasData(1);

        //获取当前时间 获取格式如20160127 转化为int类型
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        int setTime = Integer.parseInt(dateFormat.format(new Date()));

        po.setSetTime(setTime);

        return po;
    }


}
