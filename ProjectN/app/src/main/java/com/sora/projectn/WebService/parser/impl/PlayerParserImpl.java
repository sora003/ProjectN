package com.sora.projectn.WebService.parser.impl;

import com.sora.projectn.WebService.parser.PlayerParser;
import com.sora.projectn.po.PlayerPo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sora on 2016/1/27.
 */
public class PlayerParserImpl implements PlayerParser {

    private Pattern pattern;
    private Matcher matcher;


    @Override
    public List<PlayerPo> parsePlayerInfo(List<StringBuffer> rlist) {
        List<PlayerPo> plist = new ArrayList<>();

        for (StringBuffer result : rlist){

            pattern = Pattern.compile("(<tbody>)(.*?)(</tbody>)(.*)");
            matcher = pattern.matcher(result);
            if (matcher.find()){
                /**
                 *  group(2) 球队球员信息
                 */
                String str1 = matcher.group(2);

                pattern = Pattern.compile("(<tr  class=\"\">)(.*?)(</tr>)");
                matcher = pattern.matcher(str1);

                while (matcher.find()){
                    /**
                     *  group(2) 球员信息
                     */
                    String str2 = matcher.group(2);

                    Pattern p1 = Pattern.compile("(   <td align=\"center\" >)(.*?)(</td>   <td align=\"left\")(.*)(\"><a href=\")(.*?)(\">)(.*?)(</a></td>   <td align=\"center\" >)(.*?)" +
                            "(</td>   <td align=\"right\"  csk=)(.*)(\">)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"left\"  csk=\")(.*?)(\">)(.*)(<td align=\"right\" >)(.*?)" +
                            "(</td>   <td align=\"left\" ><a href)(.*)(\">)(.*?)(</a></td>)");
                    Matcher m1 = p1.matcher(str2);

                    if (m1.matches()){

                        //取对应参数
                        int no = Integer.parseInt(m1.group(2));
                        String name = m1.group(8);
                        String pos = m1.group(10);
                        String ht = m1.group(14);
                        int wt = Integer.parseInt(m1.group(16));
                        int birth = Integer.parseInt(m1.group(18));
                        int exp;
                        if (m1.group(22).equals("R")){
                            exp = 0;
                        }
                        else {
                            exp = Integer.parseInt(m1.group(22));
                        }
                        String collage = m1.group(26);

                        String path = m1.group(6);

                    }
                    else{
                        Pattern p2 = Pattern.compile("(   <td align=\"center\" >)(.*?)(</td>   <td align=\"left\")(.*)(\"><a href=\")(.*?)(\">)(.*?)(</a></td>   <td align=\"center\" >)(.*?)" +
                                "(</td>   <td align=\"right\"  csk=)(.*)(\">)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>   <td align=\"left\"  csk=\")(.*?)(\">)(.*)(<td align=\"right\" >)(.*?)" +
                                "(</td>   <td align=\"left\" ></td>)");
                        Matcher m2 = p2.matcher(str2);
                        if (m2.matches()){
                            //取对应参数
                            int no = Integer.parseInt(m2.group(2));
                            String name = m2.group(8);
                            String pos = m2.group(10);
                            String ht = m2.group(14);
                            int wt = Integer.parseInt(m2.group(16));
                            int birth = Integer.parseInt(m2.group(18));
                            int exp;
                            if (m2.group(22).equals("R")){
                                exp = 0;
                            }
                            else {
                                exp = Integer.parseInt(m2.group(22));
                            }
                            String collage = "";

                            String path = m2.group(6);
                        }
                    }
                }
            }
        }

        return null;
    }
}
