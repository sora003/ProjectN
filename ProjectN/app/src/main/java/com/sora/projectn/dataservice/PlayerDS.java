package com.sora.projectn.dataservice;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/2/2.
 */
public interface PlayerDS {


    public Map<String,String> getPlayerImg(Context context);

    /**
     * 写入球员基本数据
     *
     * @param context
     */
    public void setPlayInfo(Context context);


    public void setPlayerImg(Context context);
}
