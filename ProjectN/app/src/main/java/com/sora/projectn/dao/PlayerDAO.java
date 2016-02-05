package com.sora.projectn.dao;

import android.content.Context;

import com.sora.projectn.po.PlayerPo;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/2/2.
 */
public interface PlayerDAO {


    /**
     * 获取对应某球队的所有球员信息
     *
     * @param context
     * @param abbr
     * @return
     */
    public List<PlayerPo> getPlayerList(Context context, String abbr);


    /**
     * 获取球员照片的网络路径
     *
     * @param context
     * @return
     */
    public Map<String,String> getPlayerImg(Context context);

    /**
     * 写入球员基本数据
     *
     * @param context
     */
    public void setPlayInfo(Context context);


    public void setPlayerImg(Context context);
}
