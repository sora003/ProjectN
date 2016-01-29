package com.sora.projectn.businesslogicservice;

import android.content.Context;

import com.sora.projectn.vo.TeamConferenceVo;
import com.sora.projectn.vo.TeamInfoVo;

import java.util.List;

/**
 * Created by Sora on 2016/1/26.
 */
public interface TeamBLS {

    /**
     * 获取TeamListActivity显示内容
     * @param context
     * @return
     */
    public List<TeamConferenceVo> getTeamConferenceInfo(Context context);

    public TeamInfoVo getTeamInfo(Context context,String abbr);
}
