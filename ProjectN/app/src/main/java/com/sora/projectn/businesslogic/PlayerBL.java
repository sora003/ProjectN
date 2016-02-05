package com.sora.projectn.businesslogic;

import android.content.Context;

import com.sora.projectn.businesslogicservice.PlayerBLS;
import com.sora.projectn.dataservice.PlayerDS;
import com.sora.projectn.dataservice.impl.PlayerDSImpl;
import com.sora.projectn.po.PlayerPo;
import com.sora.projectn.vo.TeamPlayerVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/2/5.
 */
public class PlayerBL implements PlayerBLS {
    @Override
    public List<TeamPlayerVo> getPlayer(Context context,String abbr) {

        //调用PlayerDS接口
        PlayerDS playerDS = new PlayerDSImpl();
        List<PlayerPo> poList = playerDS.getPlayerList(context,abbr);

        List<TeamPlayerVo> voList = new ArrayList<>();

        for (PlayerPo po : poList){
            TeamPlayerVo vo = new TeamPlayerVo(po.getName(),po.getNo(),po.getPos());

            voList.add(vo);
        }

        return voList;
    }
}
