package com.sora.projectn.gc.dataservice;

import android.content.Context;

import com.sora.projectn.gc.po.PlayerPo;
import com.sora.projectn.gc.po.PlayerPrimaryInfoPo;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/2/2.
 */
public interface PlayerDS {


    /**
     * 获取对应某球队的所有球员信息
     *
     * @param context
     * @param abbr
     * @return
     */
    public List<PlayerPo> getPlayerList(Context context,String abbr);

    /**
     * 获取所有球员信息
     *
     * @param context
     * @return
     */
    public List<PlayerPrimaryInfoPo> getPlayerList(Context context);

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
