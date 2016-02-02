package com.sora.projectn.businesslogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.sora.projectn.businesslogicservice.TeamBLS;
import com.sora.projectn.dataservice.TeamDS;
import com.sora.projectn.dataservice.impl.TeamDSImpl;
import com.sora.projectn.po.TeamPo;
import com.sora.projectn.po.TeamSeasonGamePo;
import com.sora.projectn.vo.TeamConferenceVo;
import com.sora.projectn.vo.TeamInfoVo;
import com.sora.projectn.vo.TeamSeasonInfoVo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/26.
 */
public class TeamBL implements TeamBLS {

    @Override
    public List<TeamConferenceVo> getTeamConference(Context context) {

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

    @Override
    public List<TeamSeasonInfoVo> getTeamSeasonTotal(Context context, String abbr) {

        List<TeamSeasonInfoVo> list = new ArrayList<>();

        //TeamSeasonDataVo对应的三项
        String[] entryString;

        List<String> tmDataList = new ArrayList<>();

        List<String> opDataList = new ArrayList<>();

        //调用TeamDS接口 获取TeamSeasonGamePo对象
        TeamDS teamDS = new TeamDSImpl();
        TeamSeasonGamePo po = teamDS.getTeamSeasonGameInfo(context,abbr);

        //entryList处理
        entryString = new String[]{"","场数","时长","命中","出手","命中率","3分命中","3分出手","3分命中率","2分命中","2分出手","2分命中率","罚球命中","罚球出手","罚球命中率",
                                            "进攻","防守","篮板","助攻","抢断","盖帽","失误","犯规","得分",};


        //tmDataList处理
        DecimalFormat df = new DecimalFormat("#.###");

        int game = po.getWin()+po.getLose();
        int mp = po.getMp();
        int fg = po.getFg();
        int fga = po.getFga();
        double fgp = (double)fg/fga;
        int p3 = po.getP3();
        int p3a = po.getP3a();
        double p3p = (double)p3/p3a;
        int p2 = po.getP2();
        int p2a = po.getP2a();
        double p2p = (double)p2/p2a;
        int ft = po.getFt();
        int fta = po.getFta();
        double ftp = (double)ft/fta;
        int orb = po.getOrb();
        int drb = po.getDrb();
        int trb = po.getTrb();
        int ast = po.getAst();
        int stl = po.getStl();
        int blk = po.getBlk();
        int tov = po.getTov();
        int pf = po.getPf();
        int pts = po.getPts();


        tmDataList.add("球队");
        tmDataList.add(String.valueOf(game));
        tmDataList.add(String.valueOf(mp));
        tmDataList.add(String.valueOf(fg));
        tmDataList.add(String.valueOf(fga));
        tmDataList.add(df.format(fgp));
        tmDataList.add(String.valueOf(p3));
        tmDataList.add(String.valueOf(p3a));
        tmDataList.add(df.format(p3p));
        tmDataList.add(String.valueOf(p2));
        tmDataList.add(String.valueOf(p2a));
        tmDataList.add(df.format(p2p));
        tmDataList.add(String.valueOf(ft));
        tmDataList.add(String.valueOf(fta));
        tmDataList.add(df.format(ftp));
        tmDataList.add(String.valueOf(orb));
        tmDataList.add(String.valueOf(drb));
        tmDataList.add(String.valueOf(trb));
        tmDataList.add(String.valueOf(ast));
        tmDataList.add(String.valueOf(stl));
        tmDataList.add(String.valueOf(blk));
        tmDataList.add(String.valueOf(tov));
        tmDataList.add(String.valueOf(pf));
        tmDataList.add(String.valueOf(pts));

        //tmDataList处理

        mp = po.getOpMp();
        fg = po.getOpFg();
        fga = po.getOpFga();
        fgp = (double)fg/fga;
        p3 = po.getOpP3();
        p3a = po.getOpP3a();
        p3p = (double)p3/p3a;
        p2 = po.getOpP2();
        p2a = po.getOpP2a();
        p2p = (double)p2/p2a;
        ft = po.getOpFt();
        fta = po.getOpFta();
        ftp = (double)ft/fta;
        orb = po.getOpOrb();
        drb = po.getOpDrb();
        trb = po.getOpTrb();
        ast = po.getOpAst();
        stl = po.getOpStl();
        blk = po.getOpBlk();
        tov = po.getOpTov();
        pf = po.getOpPf();
        pts = po.getOpPts();


        opDataList.add("对手");
        opDataList.add(String.valueOf(game));
        opDataList.add(String.valueOf(mp));
        opDataList.add(String.valueOf(fg));
        opDataList.add(String.valueOf(fga));
        opDataList.add(df.format(fgp));
        opDataList.add(String.valueOf(p3));
        opDataList.add(String.valueOf(p3a));
        opDataList.add(df.format(p3p));
        opDataList.add(String.valueOf(p2));
        opDataList.add(String.valueOf(p2a));
        opDataList.add(df.format(p2p));
        opDataList.add(String.valueOf(ft));
        opDataList.add(String.valueOf(fta));
        opDataList.add(df.format(ftp));
        opDataList.add(String.valueOf(orb));
        opDataList.add(String.valueOf(drb));
        opDataList.add(String.valueOf(trb));
        opDataList.add(String.valueOf(ast));
        opDataList.add(String.valueOf(stl));
        opDataList.add(String.valueOf(blk));
        opDataList.add(String.valueOf(tov));
        opDataList.add(String.valueOf(pf));
        opDataList.add(String.valueOf(pts));


        for (int i = 0; i < entryString.length; i++) {
            TeamSeasonInfoVo vo = new TeamSeasonInfoVo();
            vo.setEntry(entryString[i]);
            vo.setTmData(tmDataList.get(i));
            vo.setOpData(opDataList.get(i));
            list.add(vo);
        }

        return list;
    }


}
