package com.sora.projectn.dataservice.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.sora.projectn.WebService.parser.PlayerParser;
import com.sora.projectn.WebService.parser.impl.PlayerParserImpl;
import com.sora.projectn.WebService.webDS.PlayerWDS;
import com.sora.projectn.WebService.webDS.impl.PlayerWDSImpl;
import com.sora.projectn.dao.impl.PlayerDAOImpl;
import com.sora.projectn.database.DBManager.PlayerDBManager;
import com.sora.projectn.dataservice.PlayerDS;
import com.sora.projectn.dataservice.TeamDS;
import com.sora.projectn.po.PlayerPo;
import com.sora.projectn.po.PlayerPrimaryInfoPo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
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
