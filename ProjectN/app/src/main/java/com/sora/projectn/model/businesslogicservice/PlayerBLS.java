package com.sora.projectn.model.businesslogicservice;

import android.content.Context;

import com.sora.projectn.model.vo.AllPlayerInfoVo;
import com.sora.projectn.model.vo.SearchPlayerKeysVo;
import com.sora.projectn.model.vo.TeamPlayerVo;

import java.util.List;

/**
 * Created by Sora on 2016/2/5.
 */
public interface PlayerBLS {

    /**
     * 根据球队缩写 获取效力该球队的所有球员
     *
     * @param context
     * @param abbr
     * @return
     */
    public List<TeamPlayerVo> getPlayer(Context context,String abbr);

    public List<AllPlayerInfoVo> getAllPlayerInfo(SearchPlayerKeysVo searchKeys,Context context);



}
