package com.sora.projectn.businesslogicservice;

import android.content.Context;

import com.sora.projectn.vo.TeamConferenceVo;

import java.util.List;

/**
 * Created by Sora on 2016/1/26.
 */
public interface TeamBLS {

    public List<TeamConferenceVo> getTeamConferenceInfo(Context context);
}
