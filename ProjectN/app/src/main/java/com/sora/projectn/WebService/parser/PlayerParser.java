package com.sora.projectn.WebService.parser;

import android.graphics.Bitmap;

import com.sora.projectn.po.PlayerPo;

import java.util.List;

/**
 * Created by Sora on 2016/1/27.
 */
public interface PlayerParser {

    public List<PlayerPo> parsePlayerInfo(StringBuffer result,String abbr);

    public Bitmap parsePlayerImg(StringBuffer result);
}
