package com.sora.projectn.businesslogic;

import android.content.Context;

import com.sora.projectn.businesslogicservice.TeamBLS;
import com.sora.projectn.dataservice.TeamDS;
import com.sora.projectn.dataservice.impl.TeamDSImpl;
import com.sora.projectn.vo.TeamConferenceVo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/26.
 */
public class TeamBL implements TeamBLS {

    @Override
    public List<TeamConferenceVo> getTeamConferenceInfo(Context context) {

        //调用TeamDS接口 获取球队基本数据
        TeamDS teamDS = new TeamDSImpl();
        Map<String,String> map_STOC = teamDS.getTeamSNameAndConference(context);


        //建立6个分区的对应list

        //Southwest
        List<String> swlist =  new ArrayList<String>();
        //Southeast
        List<String> selist =  new ArrayList<String>();
        //Pacific
        List<String> palist =  new ArrayList<String>();
        //Central
        List<String> celist =  new ArrayList<String>();
        //Northwest
        List<String> nwlist =  new ArrayList<String>();
        //Atlantic
        List<String> atlist =  new ArrayList<String>();



        //建立6个分区的对应vo

        //Southwest
        TeamConferenceVo swVo = new TeamConferenceVo();
        //Southeast
        TeamConferenceVo seVo = new TeamConferenceVo();
        //Pacific
        TeamConferenceVo paVo = new TeamConferenceVo();
        //Central
        TeamConferenceVo ceVo = new TeamConferenceVo();
        //Northwest
        TeamConferenceVo nwVo = new TeamConferenceVo();
        //Atlantic
        TeamConferenceVo atVo = new TeamConferenceVo();



        //获取map的key 的首个对象
        Iterator iterator = map_STOC.keySet().iterator();


        //遍历ketSet
        while (iterator.hasNext()){
            Object key = iterator.next();
            String sName= key.toString();
            String conference = map_STOC.get(key);


            //判断球队所属分区
            switch (conference){
                case "Southwest":
                    swlist.add(sName);
                    break;
                case "Southeast":
                    selist.add(sName);
                    break;
                case "Pacific":
                    palist.add(sName);
                    break;
                case "Central":
                    celist.add(sName);
                    break;
                case "Northwest":
                    nwlist.add(sName);
                    break;
                case "Atlantic":
                    atlist.add(sName);
                    break;
            }

        }

        //为vo赋值

        //conference
        swVo.setConference("Southwest");
        seVo.setConference("Southeast");
        paVo.setConference("Pacific");
        ceVo.setConference("Central");
        nwVo.setConference("Northwest");
        atVo.setConference("Atlantic");

        //List<sName>
        swVo.setList(swlist);
        seVo.setList(selist);
        paVo.setList(palist);
        ceVo.setList(celist);
        nwVo.setList(nwlist);
        atVo.setList(atlist);


        //新建List<TeamConferenceVo>对象
        List<TeamConferenceVo> list = new ArrayList<TeamConferenceVo>();

        //向list添加值
        //添加顺序为纵向向添加!!
        list.add(swVo);
        list.add(paVo);
        list.add(nwVo);
        list.add(seVo);
        list.add(ceVo);
        list.add(atVo);

        return list;
    }

}
