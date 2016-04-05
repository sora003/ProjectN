package com.sora.projectn.model.vo;

import java.util.List;

/**
 * Created by Sora on 2016/2/9.
 */
public class MatchVo {

    private String time;
    private String teamAName;
    private String teamBName;
    private short scoreA;
    private short scoreB;
    private List<Short> periodScoreA;
    private List<Short> periodScoreB;
    private List<MatchPlayerVo> teamAList;
    private List<MatchPlayerVo> teamBList;
}
