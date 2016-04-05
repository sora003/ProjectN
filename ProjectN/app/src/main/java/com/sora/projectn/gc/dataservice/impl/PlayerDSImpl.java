package com.sora.projectn.gc.dataservice.impl;

import android.content.Context;

import com.sora.projectn.model.dao.impl.PlayerDAOImpl;
import com.sora.projectn.gc.dataservice.PlayerDS;
import com.sora.projectn.gc.po.PlayerPo;
import com.sora.projectn.gc.po.PlayerPrimaryInfoPo;

import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/2/2.
 */
public class PlayerDSImpl implements PlayerDS{

    //爬取网页最外层路径
    private static String MAIN_URL = "http://www.basketball-reference.com";


    @Override
    public List<PlayerPo> getPlayerList(Context context, String abbr) {
        return new PlayerDAOImpl().getPlayerList(context,abbr);
    }

    @Override
    public List<PlayerPrimaryInfoPo> getPlayerList(Context context) {
        return new PlayerDAOImpl().getPlayerList(context);
    }

    @Override
    public Map<String,String> getPlayerImg(Context context) {
        return new PlayerDSImpl().getPlayerImg(context);
    }

    @Override
    public void setPlayInfo(Context context) {
        new PlayerDAOImpl().setPlayInfo(context);
    }

    @Override
    public void setPlayerImg(Context context) {
        new PlayerDAOImpl().setPlayerImg(context);
    }
}
