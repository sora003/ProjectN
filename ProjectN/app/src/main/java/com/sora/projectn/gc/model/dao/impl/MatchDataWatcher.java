package com.sora.projectn.gc.model.dao.impl;

import android.content.Context;

import com.sora.projectn.gc.database.DBManager.MatchDBManager;
import com.sora.projectn.gc.po.MatchInfoPo;
import com.sora.projectn.gc.po.MatchPrimaryInfoPo;
import com.sora.projectn.gc.po.PlayerMatchInfoPo;
import com.sora.projectn.gc.po.TeamMatchInfoPo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sora on 2016/2/5.
 */
public class MatchDataWatcher {

    private static MatchDataWatcher instance = null;

    private Context context;

    /**
     * 原始数据类
     */
    private List<MatchInfoPo> matchInfoPoList = new ArrayList<>();

    /**
     * 处理后的数据类
     */
    private List<MatchPrimaryInfoPo> matchPrimaryInfoPoList = new ArrayList<>();

    private HashMap<String, List<PlayerMatchInfoPo>> playerMatchInfoMap = new HashMap<String, List<PlayerMatchInfoPo>>();

    private HashMap<String, List<TeamMatchInfoPo>> teamMatchInfoMap = new HashMap<String, List<TeamMatchInfoPo>>();


    public static MatchDataWatcher getInstance(Context context) {
        return instance == null ? (instance = new MatchDataWatcher(context)) : instance;
    }



    public MatchDataWatcher(Context context) {
        this.context = context;

        init();

        getMatch();
    }

    /**
     * 调用数据库 获取原始数据类
     */
    private void init() {
        //调用数据库 获取所有比赛数据原始类
        MatchDBManager db = new MatchDBManager(context);
        matchInfoPoList = db.queryMatchInfo();
        //关闭数据库
        db.closeDB();
    }



    /**
     * 处理数据
     */
    private void getMatch() {
        //遍历matchInfoPoList
        for (MatchInfoPo matchInfoPo : matchInfoPoList){

            //定义球队比赛数据
            TeamMatchInfoPo teamDataA = new TeamMatchInfoPo();
            TeamMatchInfoPo teamDataB = new TeamMatchInfoPo();

            MatchPrimaryInfoPo po = new MatchPrimaryInfoPo();

            //20151224
            po.setTime(matchInfoPo.getDate());
            teamDataA.setTime(matchInfoPo.getDate());
            teamDataB.setTime(matchInfoPo.getDate());

            //lal
            po.setTeamAName(matchInfoPo.getAbbr1());
            teamDataA.setName(matchInfoPo.getAbbr1());
            teamDataB.setRivalName(matchInfoPo.getAbbr1());

            //AST
            po.setTeamBName(matchInfoPo.getAbbr2());
            teamDataA.setRivalName(matchInfoPo.getAbbr2());
            teamDataB.setName(matchInfoPo.getAbbr2());

            String[] scoring1 = matchInfoPo.getScoring1().split("-");
            //球队A小节分数
            List<Short> periodScoreA = new ArrayList<>();
            //球队A总分
            short scoreA = 0;
            for (int i = 0; i < scoring1.length; i++) {
                periodScoreA.add(Short.parseShort(scoring1[i]));
                scoreA += periodScoreA.get(i);
            }
            po.setPeriodScoreA(periodScoreA);
            po.setScoreA(scoreA);

            String[] scoring2 = matchInfoPo.getScoring2().split("-");
            //球队B小节分数
            List<Short> periodScoreB = new ArrayList<>();
            //球队B总分
            short scoreB = 0;
            for (int i = 0; i < scoring2.length; i++) {
                periodScoreB.add(Short.parseShort(scoring2[i]));
                scoreB += periodScoreB.get(i);
            }
            po.setPeriodScoreB(periodScoreB);
            po.setScoreB(scoreB);
            //TODO
//            teamDataA.setPeriodScore(periodScoreA);
//            teamDataB.setPeriodScore(periodScoreB);
            teamDataA.setTotalScore(scoreA);
            teamDataB.setTotalScore(scoreB);
            teamDataA.setStatus(scoreA - scoreB);
            teamDataB.setStatus(scoreB - scoreA);




            //球队A球员数据列表
            List<PlayerMatchInfoPo> teamAList = new ArrayList<>();
            //球队B球员数据列表
            List<PlayerMatchInfoPo> teamBList = new ArrayList<>();

            //记录上双
            short[] dd = new short[5];

            //提取球队A球员各项参数
            String[] player = matchInfoPo.getPlayer1().split("-");
            String[] mp = matchInfoPo.getMp1().split("-");
            String[] fg = matchInfoPo.getFg1().split("-");
            String[] fga = matchInfoPo.getFga1().split("-");
            String[] p3 = matchInfoPo.getP31().split("-");
            String[] p3a = matchInfoPo.getP3a1().split("-");
            String[] ft = matchInfoPo.getFt1().split("-");
            String[] fta = matchInfoPo.getFta1().split("-");
            String[] orb = matchInfoPo.getOrb1().split("-");
            String[] drb = matchInfoPo.getDrb1().split("-");
            String[] trb = matchInfoPo.getTrb1().split("-");
            String[] ast = matchInfoPo.getAst1().split("-");
            String[] stl = matchInfoPo.getStl1().split("-");
            String[] blk = matchInfoPo.getBlk1().split("-");
            String[] tov = matchInfoPo.getTov1().split("-");
            String[] pf = matchInfoPo.getPf1().split("-");
            String[] pts = matchInfoPo.getPts1().split("-");

            //遍历原始数据类 所有数据对应数组长度相同
            for (int i = 0; i < player.length; i++) {

                //球员比赛数据
                PlayerMatchInfoPo playerMatchInfoPo = new PlayerMatchInfoPo();

                //设置球员基本比赛数据
                playerMatchInfoPo.setName(player[i]);
                playerMatchInfoPo.setMp(Short.parseShort(mp[i]));
                playerMatchInfoPo.setFg(Short.parseShort(fg[i]));
                playerMatchInfoPo.setFga(Short.parseShort(fga[i]));
                playerMatchInfoPo.setP3(Short.parseShort(p3[i]));
                playerMatchInfoPo.setP3a(Short.parseShort(p3a[i]));
                playerMatchInfoPo.setFt(Short.parseShort(ft[i]));
                playerMatchInfoPo.setFta(Short.parseShort(fta[i]));
                playerMatchInfoPo.setOrb(Short.parseShort(orb[i]));
                playerMatchInfoPo.setDrb(Short.parseShort(drb[i]));
                playerMatchInfoPo.setTrb(dd[1] = Short.parseShort(trb[i]));
                playerMatchInfoPo.setAst(dd[2] = Short.parseShort(ast[i]));
                playerMatchInfoPo.setStl(dd[3] = Short.parseShort(stl[i]));
                playerMatchInfoPo.setBlk(dd[4] = Short.parseShort(blk[i]));
                playerMatchInfoPo.setTov(Short.parseShort(tov[i]));
                playerMatchInfoPo.setPf(Short.parseShort(pf[i]));
                playerMatchInfoPo.setPts(dd[5] = Short.parseShort(pts[i]));

                //是否首发 考虑到首发人数为5人以及爬取的顺序性 策略为i < 5时  为首发
                playerMatchInfoPo.setIsFirstPlayer(i < 5);

                //hasDouble 计算
                int ddCnt = 0;
                for (int j = 0; j < 5; j++) {
                    if (dd[j] >= 10){
                        ddCnt++;
                    }
                }
                playerMatchInfoPo.setHasDouble(ddCnt >= 2);


                //设置球员比赛列表  考虑Map的key包含某球员和不包含某球员的情况
                if (playerMatchInfoMap.containsKey(playerMatchInfoPo.getName())) {
                    List<PlayerMatchInfoPo> oldList = playerMatchInfoMap.get(playerMatchInfoPo.getName());
                    oldList.add(playerMatchInfoPo);
                    playerMatchInfoMap.put(playerMatchInfoPo.getName(), oldList);
                } else {
                    List<PlayerMatchInfoPo> oldList = new ArrayList<PlayerMatchInfoPo>();
                    oldList.add(playerMatchInfoPo);
                    playerMatchInfoMap.put(playerMatchInfoPo.getName(), oldList);
                }

                teamAList.add(playerMatchInfoPo);

            }


            //提取球队B球员各项参数
            player = matchInfoPo.getPlayer2().split("-");
            mp = matchInfoPo.getMp2().split("-");
            fg = matchInfoPo.getFg2().split("-");
            fga = matchInfoPo.getFga2().split("-");
            p3 = matchInfoPo.getP32().split("-");
            p3a = matchInfoPo.getP3a2().split("-");
            ft = matchInfoPo.getFt2().split("-");
            fta = matchInfoPo.getFta2().split("-");
            orb = matchInfoPo.getOrb2().split("-");
            drb = matchInfoPo.getDrb2().split("-");
            trb = matchInfoPo.getTrb2().split("-");
            ast = matchInfoPo.getAst2().split("-");
            stl = matchInfoPo.getStl2().split("-");
            blk = matchInfoPo.getBlk2().split("-");
            tov = matchInfoPo.getTov2().split("-");
            pf = matchInfoPo.getPf2().split("-");
            pts = matchInfoPo.getPts2().split("-");

            //遍历原始数据类 所有数据对应数组长度相同
            for (int i = 0; i < player.length; i++) {

                //球员比赛数据
                PlayerMatchInfoPo playerMatchInfoPo = new PlayerMatchInfoPo();

                //设置球员基本比赛数据
                playerMatchInfoPo.setName(player[i]);
                playerMatchInfoPo.setMp(Short.parseShort(mp[i]));
                playerMatchInfoPo.setFg(Short.parseShort(fg[i]));
                playerMatchInfoPo.setFga(Short.parseShort(fga[i]));
                playerMatchInfoPo.setP3(Short.parseShort(p3[i]));
                playerMatchInfoPo.setP3a(Short.parseShort(p3a[i]));
                playerMatchInfoPo.setFt(Short.parseShort(ft[i]));
                playerMatchInfoPo.setFta(Short.parseShort(fta[i]));
                playerMatchInfoPo.setOrb(Short.parseShort(orb[i]));
                playerMatchInfoPo.setDrb(Short.parseShort(drb[i]));
                playerMatchInfoPo.setTrb(dd[1] = Short.parseShort(trb[i]));
                playerMatchInfoPo.setAst(dd[2] = Short.parseShort(ast[i]));
                playerMatchInfoPo.setStl(dd[3] = Short.parseShort(stl[i]));
                playerMatchInfoPo.setBlk(dd[4] = Short.parseShort(blk[i]));
                playerMatchInfoPo.setTov(Short.parseShort(tov[i]));
                playerMatchInfoPo.setPf(Short.parseShort(pf[i]));
                playerMatchInfoPo.setPts(dd[5] = Short.parseShort(pts[i]));

                //是否首发 考虑到首发人数为5人以及爬取的顺序性 策略为i < 5时  为首发
                playerMatchInfoPo.setIsFirstPlayer(i < 5);


                //hasDouble 计算
                int ddCnt = 0;
                for (int j = 0; j < 5; j++) {
                    if (dd[j] >= 10){
                        ddCnt++;
                    }
                }
                playerMatchInfoPo.setHasDouble(ddCnt >= 2);


                //设置球员比赛列表  考虑Map的key包含某球员和不包含某球员的情况
                if (playerMatchInfoMap.containsKey(playerMatchInfoPo.getName())) {
                    List<PlayerMatchInfoPo> oldList = playerMatchInfoMap.get(playerMatchInfoPo.getName());
                    oldList.add(playerMatchInfoPo);
                    playerMatchInfoMap.put(playerMatchInfoPo.getName(), oldList);
                } else {
                    List<PlayerMatchInfoPo> oldList = new ArrayList<PlayerMatchInfoPo>();
                    oldList.add(playerMatchInfoPo);
                    playerMatchInfoMap.put(playerMatchInfoPo.getName(), oldList);
                }


                teamBList.add(playerMatchInfoPo);

            }

            //设置两支球队的球员数据表
            po.setTeamAList(teamAList);
            po.setTeamBList(teamBList);
            teamDataA.setPlayerList(teamAList);
            teamDataA.setRivalPlayerList(teamBList);
            teamDataB.setPlayerList(teamBList);
            teamDataB.setRivalPlayerList(teamAList);

            //设置球队比赛列表  考虑Map的key包含某球队和不包含某球队的情况
            if (teamMatchInfoMap.containsKey(po.getTeamAName())) {
                List<TeamMatchInfoPo> oldList = teamMatchInfoMap.get(teamDataA.getName());
                oldList.add(teamDataA);
                teamMatchInfoMap.put(teamDataA.getName(), oldList);
            } else {
                List<TeamMatchInfoPo> oldList = new ArrayList<TeamMatchInfoPo>();
                oldList.add(teamDataA);
                teamMatchInfoMap.put(teamDataA.getName(), oldList);
            }

            if (teamMatchInfoMap.containsKey(po.getTeamBName())) {
                List<TeamMatchInfoPo> oldList = teamMatchInfoMap.get(teamDataB.getName());
                oldList.add(teamDataB);
                teamMatchInfoMap.put(teamDataB.getName(), oldList);
            } else {
                List<TeamMatchInfoPo> oldList = new ArrayList<TeamMatchInfoPo>();
                oldList.add(teamDataB);
                teamMatchInfoMap.put(teamDataB.getName(), oldList);
            }




            //添加进比赛数据总表
            matchPrimaryInfoPoList.add(po);

        }
    }

    public List<MatchPrimaryInfoPo> getMatchPrimaryInfoPoList() {
        return matchPrimaryInfoPoList;
    }

    public HashMap<String, List<PlayerMatchInfoPo>> getPlayerMatchInfoMap() {
        return playerMatchInfoMap;
    }

    public HashMap<String, List<TeamMatchInfoPo>> getTeamMatchInfoMap() {
        return teamMatchInfoMap;
    }

}
