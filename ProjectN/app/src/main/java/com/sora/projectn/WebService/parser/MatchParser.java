package com.sora.projectn.WebService.parser;

import android.content.Context;

import com.sora.projectn.po.MatchPo;

import java.util.List;

/**
 * Created by Sora on 2016/2/3.
 */
public interface MatchParser {

    /**
     * 解析得到某日的所有比赛列表对应的网络路径
     *
     * @param result
     * @return 某日的所有比赛列表对应的网络路径
     */
    public List<String> parseMatchList(StringBuffer result);

    /**
     * 解析某场比赛的比赛数据
     *
     * @param result 比赛的具体信息.html
     * @return 某场比赛的比赛数据
     */
    public MatchPo parMatchInfo(StringBuffer result);
}
