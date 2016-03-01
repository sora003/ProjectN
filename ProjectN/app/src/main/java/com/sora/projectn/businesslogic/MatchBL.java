package com.sora.projectn.businesslogic;

import android.content.Context;

import com.sora.projectn.businesslogicservice.MatchBLS;
import com.sora.projectn.dataservice.TeamDS;
import com.sora.projectn.dataservice.impl.TeamDSImpl;
import com.sora.projectn.po.MatchInfoPo;
import com.sora.projectn.po.MatchPrimaryInfoPo;
import com.sora.projectn.vo.MatchInfoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/2/6.
 */
public class MatchBL implements MatchBLS {

    TeamDS teamDS = new TeamDSImpl();

    @Override
    public List<MatchInfoVo> getMatchInfoVo(Context context,List<MatchInfoPo> poList) {
        List<MatchInfoVo> voList = new ArrayList<>();



        //遍历原始表
        for (MatchInfoPo po : poList){
            MatchInfoVo vo = new MatchInfoVo();

            //比赛序列号
            vo.setId(po.getId());

            //球队A名称
            vo.setTeamAName(teamDS.getTeamSName(context, po.getAbbr1()));
            //球队B名称
            vo.setTeamBName(teamDS.getTeamSName(context, po.getAbbr2()));


            String[] scoring1 = po.getScoring1().split("-");
            //球队A总分
            short scoreA = 0;
            //计算球队A总分
            for (int i = 0; i < scoring1.length; i++) {
                scoreA += Short.parseShort(scoring1[i]);
            }
            vo.setScoreA(scoreA);

            String[] scoring2 = po.getScoring2().split("-");
            //球队B总分
            short scoreB = 0;
            //计算球队B总分
            for (int i = 0; i < scoring2.length; i++) {
                scoreB += Short.parseShort(scoring2[i]);
            }
            vo.setScoreB(scoreB);

            //加入表中
            voList.add(vo);
        }

        return voList;
    }


}
