package com.sora.projectn.businesslogic;

import android.content.Context;
import android.graphics.Bitmap;

import com.sora.projectn.businesslogicservice.TeamBLS;
import com.sora.projectn.dataservice.TeamDS;
import com.sora.projectn.dataservice.impl.TeamDSImpl;
import com.sora.projectn.po.TeamPo;
import com.sora.projectn.po.TeamSeasonGamePo;
import com.sora.projectn.vo.TeamConferenceVo;
import com.sora.projectn.vo.TeamInfoVo;

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


        //建立6个分区的对应sNameList

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

        //建立6个分区的对应sNameList

        //Southwest
        List<Bitmap> swlogo = new ArrayList<Bitmap>();
        //Southeast
        List<Bitmap> selogo =  new ArrayList<Bitmap>();
        //Pacific
        List<Bitmap> palogo =  new ArrayList<Bitmap>();
        //Central
        List<Bitmap> celogo =  new ArrayList<Bitmap>();
        //Northwest
        List<Bitmap> nwlogo =  new ArrayList<Bitmap>();
        //Atlantic
        List<Bitmap> atlogo =  new ArrayList<Bitmap>();

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
                    swlogo.add(teamDS.getTeamLogo(context,teamDS.getTeamAbbr(context,sName)));
                    break;
                case "Southeast":
                    selist.add(sName);
                    selogo.add(teamDS.getTeamLogo(context,teamDS.getTeamAbbr(context,sName)));
                    break;
                case "Pacific":
                    palist.add(sName);
                    palogo.add(teamDS.getTeamLogo(context,teamDS.getTeamAbbr(context,sName)));
                    break;
                case "Central":
                    celist.add(sName);
                    celogo.add(teamDS.getTeamLogo(context,teamDS.getTeamAbbr(context,sName)));
                    break;
                case "Northwest":
                    nwlist.add(sName);
                    nwlogo.add(teamDS.getTeamLogo(context,teamDS.getTeamAbbr(context,sName)));
                    break;
                case "Atlantic":
                    atlist.add(sName);
                    atlogo.add(teamDS.getTeamLogo(context,teamDS.getTeamAbbr(context,sName)));
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
        swVo.setsNameList(swlist);
        seVo.setsNameList(selist);
        paVo.setsNameList(palist);
        ceVo.setsNameList(celist);
        nwVo.setsNameList(nwlist);
        atVo.setsNameList(atlist);

        //List<logo>
        swVo.setLogoList(swlogo);
        swVo.setLogoList(swlogo);
        seVo.setLogoList(selogo);
        paVo.setLogoList(palogo);
        ceVo.setLogoList(celogo);
        nwVo.setLogoList(nwlogo);
        atVo.setLogoList(atlogo);



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

    @Override
    public TeamInfoVo getTeamInfo(Context context,String abbr) {

        TeamInfoVo vo = new TeamInfoVo();

        //调用TeamDS接口 获取相关数据
        TeamDS teamDS = new TeamDSImpl();
        TeamPo teamPo = teamDS.getTeamInfo(context,abbr);
        TeamSeasonGamePo teamSeasonGamePo = teamDS.getTeamSeasonGameInfo(context,abbr);

        //取值
        Bitmap bmp = teamDS.getTeamLogo(context, abbr);
        String name = teamPo.getName();
        int year = teamDS.getTeamSeasonGameYear(context,abbr);
        int win = teamSeasonGamePo.getWin();
        int lose = teamSeasonGamePo.getLose();
        int rank = teamSeasonGamePo.getRank();


        //设置vo
        vo.setAbbr(abbr);
        vo.setBmp(bmp);
        vo.setName(name);
        vo.setSeason(String.valueOf(year - 1) + "-" + String.valueOf(year) + "赛季");
        vo.setWin_lose(String.valueOf(win)+"胜"+String.valueOf(lose)+"负");
        vo.setRank("联盟第"+rank+"名");

        return vo;
    }


}
