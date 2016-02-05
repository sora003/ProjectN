package com.sora.projectn.WebService.parser.impl;

import com.sora.projectn.WebService.parser.MatchParser;
import com.sora.projectn.po.MatchInfoPo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sora on 2016/2/3.
 */
public class MatchParserImpl implements MatchParser {

    private Pattern pattern;
    private Matcher matcher;


    @Override
    public List<String> parseMatchList(StringBuffer result) {

        List<String> list = new ArrayList<>();

        /**
         * group(2) 比赛链接网址
         */
        pattern = Pattern.compile("(<td class=\"align_right bold_text\"><a href=\")(.*?)(\">Final)");
        matcher = pattern.matcher(result);

        while (matcher.find()){
            list.add(matcher.group(2));
        }

        return list;
    }

    @Override
    public MatchInfoPo parseMatchInfo(StringBuffer result) {

        MatchInfoPo po = new MatchInfoPo();

        /**
         * group(2) scoring信息
         */
        pattern = Pattern.compile("(<td><a href=\"/teams/)(.*?)(</tr>)");
        matcher = pattern.matcher(result);

        int scoringCount = 0;

        while (matcher.find()){
            /**
             * group(3) 球队缩写
             * group(5) group(7) group(9) group(11) 球队四节比赛的分数
             */
            Pattern scoringPattern = Pattern.compile("(.*)(html\">)(.*?)(</a></td><td class=\"align_right\">)(.*?)(</td><td class=\"align_right\">)(.*?)(</td><td class=\"align_right\">)(.*?)(</td><td class=\"align_right\">)(.*?)(</td><td class=\"align_right bold_text\">)(.*)");
            Matcher scoringMatcher = scoringPattern.matcher(matcher.group(2));



            if (scoringMatcher.matches()){
                String abbr = scoringMatcher.group(3);
                String scoring = scoringMatcher.group(5) + "-" + scoringMatcher.group(7) + "-" + scoringMatcher.group(9) + "-" + scoringMatcher.group(11);


                if (scoringCount == 0){
                    po.setAbbr1(abbr);
                    po.setScoring1(scoring);
                    scoringCount++;
                }
                else {
                    po.setAbbr2(abbr);
                    po.setScoring2(scoring);
                }
            }
        }

        /**
         * group(2) 比赛数据
         *
         * 注意该正则表达式中 + 需要转义
         */
        pattern = Pattern.compile("(tip=\"Plus/Minus\">\\+/-</th></tr></thead><tbody>)(.*?)(</tbody>)");
        matcher = pattern.matcher(result);

        //计数两支球队的比赛数据
        int infoCount = 0;

        while (matcher.find()){

            String info = matcher.group(2);

            /**
             * group(2) 球员数据
             */
            Pattern infoPattern = Pattern.compile("(<tr  class=\"\">)(.*?)(</tr>)");
            Matcher infoMatcher = infoPattern.matcher(info);

            String player = "";
            String mp = "";
            String fg = "";
            String fga = "";
            String p3 = "";
            String p3a = "";
            String ft = "";
            String fta = "";
            String orb = "";
            String drb = "";
            String trb = "";
            String ast = "";
            String stl = "";
            String blk = "";
            String tov = "";
            String pf = "";
            String pts = "";


            while (infoMatcher.find()){

                /**
                 * group(3) 球员姓名
                 * group(7) 比赛时长
                 * group(9) 命中
                 * group(11) 出手
                 * group(15) 3分命中
                 * group(17) 3分出手
                 * group(21) 罚球命中
                 * group(23) 罚球出手
                 * group(27) 进攻
                 * group(29) 防守
                 * group(31) 篮板
                 * group(33) 助攻
                 * group(35) 抢断
                 * group(47) 盖帽
                 * group(39) 失误
                 * group(41) 犯规
                 * group(43) 得分
                 */
                Pattern playerPattern = Pattern.compile("(.*)(.html\">)(.*?)(</a></td>   <td align=\"right\"  csk=\")(.*)(\">)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)" +                   //1-10
                        "(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)" +            //11-20
                        "(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)" +            //21-30
                        "(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)" +            //31-40
                        "(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"right\" >)(.*)(</td>)");                                                                                                             //41-46
                Matcher playerMatcher = playerPattern.matcher(infoMatcher.group(2));



                if (playerMatcher.matches()){

                    player += playerMatcher.group(3) + "-";
                    mp += playerMatcher.group(7) + "-";
                    fg += playerMatcher.group(9) + "-";
                    fga += playerMatcher.group(11) + "-";
                    p3 += playerMatcher.group(15) + "-";
                    p3a += playerMatcher.group(17) + "-";
                    ft += playerMatcher.group(21) + "-";
                    fta += playerMatcher.group(23) + "-";
                    orb += playerMatcher.group(27) + "-";
                    drb += playerMatcher.group(29) + "-";
                    trb += playerMatcher.group(31) + "-";
                    ast += playerMatcher.group(33) + "-";
                    stl += playerMatcher.group(35) + "-";
                    blk += playerMatcher.group(37) + "-";
                    tov += playerMatcher.group(39) + "-";
                    pf += playerMatcher.group(41) + "-";
                    pts += playerMatcher.group(43) + "-";

                }

            }


            if (infoCount == 0){

                po.setPlayer1(player);
                po.setMp1(mp);
                po.setFg1(fg);
                po.setFga1(fga);
                po.setP31(p3);
                po.setP3a1(p3a);
                po.setFt1(ft);
                po.setFta1(fta);
                po.setOrb1(orb);
                po.setDrb1(drb);
                po.setTrb1(trb);
                po.setAst1(ast);
                po.setStl1(stl);
                po.setBlk1(blk);
                po.setTov1(tov);
                po.setPf1(pf);
                po.setPts1(pts);

                infoCount++;
            }
            else {
                po.setPlayer2(player);
                po.setMp2(mp);
                po.setFg2(fg);
                po.setFga2(fga);
                po.setP32(p3);
                po.setP3a2(p3a);
                po.setFt2(ft);
                po.setFta2(fta);
                po.setOrb2(orb);
                po.setDrb2(drb);
                po.setTrb2(trb);
                po.setAst2(ast);
                po.setStl2(stl);
                po.setBlk2(blk);
                po.setTov2(tov);
                po.setPf2(pf);
                po.setPts2(pts);
            }

        }

        return po;
    }
}
