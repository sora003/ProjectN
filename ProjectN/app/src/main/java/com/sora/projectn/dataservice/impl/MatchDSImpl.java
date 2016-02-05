package com.sora.projectn.dataservice.impl;

import android.content.Context;

import com.sora.projectn.dao.impl.MatchDAOImpl;
import com.sora.projectn.dataservice.MatchDS;

/**
 * Created by Sora on 2016/2/3.
 */
public class MatchDSImpl implements MatchDS {

    @Override
    public void setMatchInfo(Context context, String startDay, String endDay) {
        new MatchDAOImpl().setMatchInfo(context,startDay,endDay);
    }


}
