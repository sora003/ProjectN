package com.sora.projectn.model.businesslogicservice;

import android.content.Context;

import com.sora.projectn.model.vo.TeamConferenceVo;
import com.sora.projectn.model.vo.TeamInfoVo;
import com.sora.projectn.model.vo.TeamSeasonInfoVo;

import java.util.List;

/**
 * Created by Sora on 2016/1/26.
 */
public interface TeamBLS {

    /**
     * 获取TeamListActivity显示内容
     *
     * @param context
     * @return
     */
    public List<TeamConferenceVo> getTeamConference(Context context);

    /**
     * 获取TeamActivity的顶层显示内容
     *
     * @param context
     * @param abbr
     * @return
     */
    public TeamInfoVo getTeamInfo(Context context,String abbr);

    public List<TeamSeasonInfoVo> getTeamSeasonTotal(Context context,String abbr);
}
