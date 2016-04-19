package com.sora.projectn.gc.model.businesslogic;

import android.content.Context;
import android.graphics.Bitmap;

import com.sora.projectn.R;
import com.sora.projectn.gc.model.businesslogicservice.TeamBLS;
import com.sora.projectn.gc.dataservice.TeamDS;
import com.sora.projectn.gc.dataservice.impl.TeamDSImpl;
import com.sora.projectn.gc.po.TeamPo;
import com.sora.projectn.gc.po.TeamSeasonGamePo;
import com.sora.projectn.utils.beans.TeamConferenceVo;
import com.sora.projectn.utils.beans.TeamInfoVo;
import com.sora.projectn.utils.beans.TeamSeasonInfoVo;
import com.sora.projectn.utils.GetHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/26.
 */
public class TeamBL implements TeamBLS {


    @Override
    public List<TeamConferenceVo> getTeamConference(Context context) {


        //获取数据
        Map<String,String> map = getTeamList();


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

        //建立6个分区的对应logoList

        //Southwest
        List<Integer> swlogo =  new ArrayList<>();
        //Southeast
        List<Integer> selogo =  new ArrayList<>();
        //Pacific
        List<Integer> palogo =  new ArrayList<>();
        //Central
        List<Integer> celogo =  new ArrayList<>();
        //Northwest
        List<Integer> nwlogo =  new ArrayList<>();
        //Atlantic
        List<Integer> atlogo =  new ArrayList<>();

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
        Iterator iterator = map.keySet().iterator();


        //遍历ketSet
        while (iterator.hasNext()){
            Object key = iterator.next();
            String sName= key.toString();
            String conference = map.get(key);




            //判断球队所属分区
            switch (conference){
                case "西南区":
                    swlist.add(sName);
                    swlogo.add(getBitmap(sName,context));
                    break;
                case "西北区":
                    selist.add(sName);
                    selogo.add(getBitmap(sName,context));
                    break;
                case "太平洋区":
                    palist.add(sName);
                    palogo.add(getBitmap(sName,context));
                    break;
                case "中区":
                    celist.add(sName);
                    celogo.add(getBitmap(sName,context));
                    break;
                case "东南区":
                    nwlist.add(sName);
                    nwlogo.add(getBitmap(sName,context));
                    break;
                case "大西洋区":
                    atlist.add(sName);
                    atlogo.add(getBitmap(sName,context));
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

        //logo
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

    /**
     * 从server获取球队表
     *
     * @return
     */
    private Map<String,String> getTeamList() {
        Map<String,String> map = new HashMap<>();

        //从server获取数据
//        String jsonString = "[{\"id\":1,\"name\":\"圣安东尼奥马刺队\",\"abbr\":\"sas\",\"city\":\"圣安东尼奥\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"马刺\",\"founded\":1976},{\"id\":2,\"name\":\"孟菲斯灰熊队\",\"abbr\":\"mem\",\"city\":\"孟菲斯\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"灰熊\",\"founded\":1995},{\"id\":3,\"name\":\"达拉斯小牛队\",\"abbr\":\"dal\",\"city\":\"达拉斯\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"小牛\",\"founded\":1980},{\"id\":4,\"name\":\"休斯顿火箭队\",\"abbr\":\"hou\",\"city\":\"休斯顿\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"火箭\",\"founded\":1967},{\"id\":5,\"name\":\"新奥尔良鹈鹕队\",\"abbr\":\"noh\",\"city\":\"新奥尔良\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"鹈鹕\",\"founded\":1988},{\"id\":6,\"name\":\"明尼苏达森林狼队\",\"abbr\":\"min\",\"city\":\"明尼苏达\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"森林狼\",\"founded\":1989},{\"id\":7,\"name\":\"丹佛掘金队\",\"abbr\":\"den\",\"city\":\"丹佛\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"掘金\",\"founded\":1976},{\"id\":8,\"name\":\"犹他爵士队\",\"abbr\":\"UTAH\",\"city\":\"犹他\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"爵士\",\"founded\":1974},{\"id\":9,\"name\":\"波特兰开拓者队\",\"abbr\":\"por\",\"city\":\"波特兰\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"开拓者\",\"founded\":1970},{\"id\":10,\"name\":\"俄克拉荷马雷霆队\",\"abbr\":\"okc\",\"city\":\"俄克拉荷马城\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"雷霆\",\"founded\":1967},{\"id\":11,\"name\":\"萨克拉门托国王队\",\"abbr\":\"sac\",\"city\":\"萨克拉门托\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"国王\",\"founded\":1948},{\"id\":12,\"name\":\"菲尼克斯太阳队\",\"abbr\":\"pho\",\"city\":\"菲尼克斯\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"太阳\",\"founded\":1968},{\"id\":13,\"name\":\"洛杉矶湖人队\",\"abbr\":\"lal\",\"city\":\"洛杉矶\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"湖人\",\"founded\":1948},{\"id\":14,\"name\":\"洛杉矶快船队\",\"abbr\":\"lac\",\"city\":\"洛杉矶\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"快船\",\"founded\":1970},{\"id\":15,\"name\":\"金州勇士队\",\"abbr\":\"GS\",\"city\":\"金州\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"勇士\",\"founded\":1946},{\"id\":16,\"name\":\"迈阿密热队\",\"abbr\":\"mia\",\"city\":\"迈阿密\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"热火\",\"founded\":1988},{\"id\":17,\"name\":\"奥兰多魔术队\",\"abbr\":\"orl\",\"city\":\"奥兰多\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"魔术\",\"founded\":1989},{\"id\":18,\"name\":\"亚特兰大老鹰队\",\"abbr\":\"atl\",\"city\":\"亚特兰大\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"老鹰\",\"founded\":1949},{\"id\":19,\"name\":\"华盛顿奇才队\",\"abbr\":\"was\",\"city\":\"华盛顿\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"奇才\",\"founded\":1961},{\"id\":20,\"name\":\"夏洛特黄蜂队\",\"abbr\":\"cha\",\"city\":\"夏洛特\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"黄蜂\",\"founded\":2004},{\"id\":21,\"name\":\"底特律活塞队\",\"abbr\":\"det\",\"city\":\"底特律\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"活塞\",\"founded\":1948},{\"id\":22,\"name\":\"印第安纳步行者队\",\"abbr\":\"ind\",\"city\":\"印第安纳\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"步行者\",\"founded\":1976},{\"id\":23,\"name\":\"克利夫兰骑士队\",\"abbr\":\"cle\",\"city\":\"克利夫兰\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"骑士\",\"founded\":1970},{\"id\":24,\"name\":\"芝加哥公牛队\",\"abbr\":\"chi\",\"city\":\"芝加哥\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"公牛\",\"founded\":1966},{\"id\":25,\"name\":\"密尔沃基雄鹿队\",\"abbr\":\"mil\",\"city\":\"密尔沃基\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"雄鹿\",\"founded\":1968},{\"id\":26,\"name\":\"波士顿凯尔特人队\",\"abbr\":\"bos\",\"city\":\"波士顿\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"凯尔特人\",\"founded\":1946},{\"id\":27,\"name\":\"费城76人队\",\"abbr\":\"phi\",\"city\":\"费城\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"76人\",\"founded\":1947},{\"id\":28,\"name\":\"纽约尼克斯队\",\"abbr\":\"nyk\",\"city\":\"纽约\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"尼克斯\",\"founded\":1946},{\"id\":29,\"name\":\"布鲁克林篮网队\",\"abbr\":\"BKN\",\"city\":\"布鲁克林\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"篮网\",\"founded\":1967},{\"id\":30,\"name\":\"多伦多猛龙队\",\"abbr\":\"tor\",\"city\":\"多伦多\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"猛龙\",\"founded\":1995}]";

        String jsonString= GetHttpResponse.getHttpResponse("http://192.168.2.122:8080/NBADataSystem/getTeams.do");

        //解析jsonString 构造Map
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String sName = obj.getString("sName");
                JSONObject conferenceArray = obj.getJSONObject("conference");
                String conference = conferenceArray.getString("name");

                map.put(sName,conference);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return map;
    }


    private int getBitmap(String s,Context context) {
        
        switch (s){
            case "老鹰" :

                return R.drawable.atl;

            case "凯尔特人" :
                return R.drawable.bos;

            case "黄蜂" :
                return R.drawable.cha;

            case "公牛" :
                return R.drawable.chi;

            case "骑士" :
                return R.drawable.cle;

            case "小牛" :
                return R.drawable.dal;

            case "掘金" :
                return R.drawable.den;

            case "活塞" :
                return R.drawable.det;

            case "勇士" :
                return R.drawable.gsw;

            case "火箭" :
                return R.drawable.hou;

            case "步行者" :
                return R.drawable.ind;

            case "快船" :
                return R.drawable.lac;

            case "湖人" :
                return R.drawable.lal;

            case "灰熊" :
                return R.drawable.mem;

            case "热火" :
                return R.drawable.mia;

            case "雄鹿" :
                return R.drawable.mil;

            case "森林狼" :
                return R.drawable.min;

            case "篮网" :
                return R.drawable.njn;

            case "鹈鹕" :
                return R.drawable.noh;

            case "尼克斯" :
                return R.drawable.nyk;

            case "雷霆" :
                return R.drawable.okc;

            case "魔术" :
                return R.drawable.orl;

            case "76人" :
                return R.drawable.phi;

            case "太阳" :
                return R.drawable.pho;

            case "开拓者" :
                return R.drawable.por;

            case "国王" :
                return R.drawable.sac;

            case "马刺" :
                return R.drawable.sas;

            case "猛龙" :
                return R.drawable.tor;

            case "爵士" :
                return R.drawable.uta;

            case "奇才" :
                return R.drawable.was;

        }


        return 0;
    }

}
