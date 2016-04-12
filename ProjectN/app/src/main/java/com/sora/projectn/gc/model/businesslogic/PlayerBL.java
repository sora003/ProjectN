package com.sora.projectn.gc.model.businesslogic;

import android.content.Context;
import android.util.Log;

import com.sora.projectn.gc.model.businesslogicservice.PlayerBLS;
import com.sora.projectn.gc.dataservice.MatchDS;
import com.sora.projectn.gc.dataservice.PlayerDS;
import com.sora.projectn.gc.dataservice.impl.MatchDSImpl;
import com.sora.projectn.gc.dataservice.impl.PlayerDSImpl;
import com.sora.projectn.gc.po.PlayerMatchInfoPo;
import com.sora.projectn.gc.po.PlayerPo;
import com.sora.projectn.gc.po.PlayerPrimaryInfoPo;
import com.sora.projectn.gc.po.PlayerTempInfoPo;
import com.sora.projectn.gc.model.vo.AllPlayerInfoVo;
import com.sora.projectn.gc.model.vo.SearchPlayerKeysVo;
import com.sora.projectn.gc.model.vo.TeamPlayerVo;

import java.text.DecimalFormat;
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

    @Override
    public List<AllPlayerInfoVo> getAllPlayerInfo(SearchPlayerKeysVo searchKeys,Context context) {
        // 获取中间临时文件信息
        List<PlayerTempInfoPo> tempList = new ArrayList<PlayerTempInfoPo>();
        tempList = myCleanHelper(context);
        Log.i("progress","ok");
        for (PlayerTempInfoPo playerTempInfoPo : tempList){
            Log.i("name",playerTempInfoPo.getName());
        }
        // 对中间临时文件信息按照筛选条件进行筛选
        tempList = filtrateTempInfo(tempList, searchKeys);
        // 将转换后的数据，根据排序条件，按照降序排列，取前50返回，如果不足50，则全部返回。
        //TODO  暂不处理
//        tempList = sortAllTempInfo(tempList, searchKeys);
        // 将筛选排序后的结果按照VO层的相关要求，进行数据转换
        List<AllPlayerInfoVo> allPlayerInforList = new ArrayList<AllPlayerInfoVo>();
        for (int i = 0; i < tempList.size(); i++) {
            PlayerTempInfoPo tempInfor = new PlayerTempInfoPo();
            tempInfor = tempList.get(i);
            // 球员名称，所属球队，参赛场数，先发场数，篮板数，助攻数，在场时间，
            // 投篮命中率，三分命中率，罚球命中率，进攻数，防守数，抢断数，盖帽数，失误数，犯规数，总得分
            // 将涉及到的所有double类型都转换为保留一位小数点。
            DecimalFormat df = new DecimalFormat("0.0");
            double fgp = 0.0;
            double p3p = 0.0;
            double ftp = 0.0;
            if (tempInfor.getFga() > 0) {
                fgp = (double) tempInfor.getFg()
                        / (double) tempInfor.getFga();
            }
            if (tempInfor.getP3a() > 0) {
                p3p = (double) tempInfor.getP3()
                        / (double) tempInfor.getP3a();
            }
            if (tempInfor.getFta() > 0) {
                ftp = (double) tempInfor.getFt()
                        / (double) tempInfor.getFta();
            }
            fgp = Double.parseDouble(df.format(fgp));
            p3p = Double.parseDouble(df.format(p3p));
            ftp = Double.parseDouble(df.format(ftp));

            AllPlayerInfoVo tempPlayer = new AllPlayerInfoVo();

            tempPlayer.setName(tempInfor.getName());
            tempPlayer.setTeam(tempInfor.getTeam());
            tempPlayer.setMatch(tempInfor.getMatch());
            tempPlayer.setFirstPlay(tempInfor.getFirstPlay());
            tempPlayer.setMp(tempInfor.getMp());
            tempPlayer.setFgp(fgp);
            tempPlayer.setP3p(p3p);
            tempPlayer.setFtp(ftp);
            tempPlayer.setOrb(tempInfor.getOrb());
            tempPlayer.setDrb(tempInfor.getDrb());
            tempPlayer.setTrb(tempInfor.getTrb());
            tempPlayer.setAst(tempInfor.getAst());
            tempPlayer.setStl(tempInfor.getStl());
            tempPlayer.setBlk(tempInfor.getBlk());
            tempPlayer.setTov(tempInfor.getTov());
            tempPlayer.setPf(tempInfor.getPf());
            tempPlayer.setPts(tempInfor.getPts());

            // 增加对高阶数据处理的支持。
            // addHighLevelInfor(tempPlayer, tempInfor);

            allPlayerInforList.add(tempPlayer);
        }

        return allPlayerInforList;
    }


    /**
     * TODO
     * 利用PlayerMatchList，进行相关处理，并生成对应原格式的中间临时数据的List。
     *
     * @return
     */
    private List<PlayerTempInfoPo> myCleanHelper(Context context) {
        MatchDS matchDS = new MatchDSImpl();
        List<PlayerTempInfoPo> resultList = new ArrayList<PlayerTempInfoPo>();
        List<PlayerPrimaryInfoPo> playerList = new ArrayList<PlayerPrimaryInfoPo>();
        List<PlayerMatchInfoPo> playerMatchList = new ArrayList<PlayerMatchInfoPo>();
        playerList = getPrimaryPlayerInfo(context);
        Log.i("progress","ok");
        for (int i = 0; i < playerList.size(); i++) {
            Log.i("name",playerList.get(i).getName());
            /**
             * 循环遍历整个PlayerList，对于每一个Player，从PlayerMatchList中获取相应的比赛信息，
             * 然后转化为PlayerTempInfoPo格式的List。
             */
            playerMatchList = matchDS.getPlayerMatchList(context,playerList.get(i).getName());
            if (!playerMatchList.isEmpty()) {
                // 对需要计算获得的数据进行初始化。
                short match = 0;
                int mp = 0;
                short fg = 0;
                short fga = 0;
                short p3 = 0;
                short p3a = 0;
                short ft = 0;
                short fta = 0;
                short orb = 0;
                short drb = 0;
                short trb = 0;
                short ast = 0;
                short stl = 0;
                short blk = 0;
                short tov = 0;
                short pf = 0;
                short pts = 0;
                double efficiency = 0.0;
                short hasDouble = 0;
                short firstPlay = 0;

                // 循环遍历对应球员的PlayerMatchList，并对相应的数据进行相应处理。
                for (int j = 0; j < playerMatchList.size(); j++) {
                    PlayerMatchInfoPo tempPlayerMatchInfo = new PlayerMatchInfoPo();
                    tempPlayerMatchInfo = playerMatchList.get(j);
                    ast = (short) (ast + tempPlayerMatchInfo.getAst());
                    trb = (short) (trb + tempPlayerMatchInfo.getTrb());
                    orb = (short) (orb + tempPlayerMatchInfo.getOrb());
                    blk = (short) (blk + tempPlayerMatchInfo.getBlk());
                    pf = (short) (pf + tempPlayerMatchInfo.getPf());
                    drb = (short) (drb + tempPlayerMatchInfo.getDrb());
                    if (tempPlayerMatchInfo.isHasDouble()) {
                        hasDouble = (short) (hasDouble + 1);
                    }
                    tov = (short) (tov + tempPlayerMatchInfo.getTov());
                    if (tempPlayerMatchInfo.isFirstPlayer()) {
                        firstPlay = (short) (firstPlay + 1);
                    }
                    fta = (short) (fta + tempPlayerMatchInfo.getFta());
                    fga = (short) (fga + tempPlayerMatchInfo.getFga());
                    stl = (short) (stl + tempPlayerMatchInfo.getStl());
                    p3a = (short) (p3a + tempPlayerMatchInfo.getP3a());
                    mp = mp + tempPlayerMatchInfo.getMp();
                    pts = (short) (pts + tempPlayerMatchInfo.getPts());
                    ft = (short) (ft + tempPlayerMatchInfo.getFt());
                    fg = (short) (fg + tempPlayerMatchInfo.getFg());
                    p3 = (short) (p3 + tempPlayerMatchInfo.getP3());
                }
                match = (short) playerMatchList.size();
                // 效率 = [ (得分+篮板+助攻+抢断+盖帽)-（出手次数-命中次数）-（罚球次数-罚球命中次数）-失误次数
                // ]/球员上场比赛的场次
                efficiency = (double) ((pts + trb + ast
                        + stl + blk)
                        - (fga - fg)
                        - (fta - ft) - tov)
                        / (double) match;
                // 将涉及到的所有double类型都转换为保留一位小数点。
                DecimalFormat df = new DecimalFormat("0.0");
                efficiency = Double.parseDouble(df.format(efficiency));
                // 对目标数据对象进行相应的赋值操作
                PlayerTempInfoPo tempPlayer = new PlayerTempInfoPo();
                tempPlayer.setAge(playerList.get(i).getAge());
                tempPlayer.setAst(ast);
                tempPlayer.setTrb(trb);
                tempPlayer.setEfficiency(efficiency);
                tempPlayer.setLeague(playerList.get(i).getLeague());
                tempPlayer.setName(playerList.get(i).getName());
                tempPlayer.setOrb(orb);
                tempPlayer.setBlk(blk);
                tempPlayer.setPf(pf);
                tempPlayer.setDrb(drb);
                tempPlayer.setHasDouble(hasDouble);
                tempPlayer.setTov(tov);
                tempPlayer.setFirstPlay(firstPlay);
                tempPlayer.setMatch(match);
                tempPlayer.setFta(fta);
                tempPlayer.setFga(fga);
                tempPlayer.setStl(stl);
                tempPlayer.setP3a(p3a);
                tempPlayer.setPosition(playerList.get(i).getPosition());
                tempPlayer.setFta(ft);
                tempPlayer.setFga(fg);
                tempPlayer.setP3a(p3);
                tempPlayer.setTeam(playerList.get(i).getTeam());
                tempPlayer.setMp(mp);
                tempPlayer.setPts(pts);
                // 将中间数据对象加入到最后的结果列表中。
                resultList.add(tempPlayer);
            }
        }
        return resultList;
    }

    /**
     * 接收待筛选信息以及筛选条件，对信息进行筛选处理
     * 
     * @param tempList
     * @param keys
     * @return
     */
    private List<PlayerTempInfoPo> filtrateTempInfo(List<PlayerTempInfoPo> tempList, SearchPlayerKeysVo keys) {
        // 提取筛选条件
        // 球员位置使用String类型表示，分别为：all,f,c,g;
        // 效力的联盟使用string类型表示，分别为：all,east,west;
        // 年龄筛选条件使用int类型表示，分别用0,1,2,3,4表示all,<=22 ，22< X <= 25 , 25 < X <= 30
        // ,>30。
        String position = keys.getPosition();
        String league = keys.getLeague();
        int age = keys.getAge();
        // 依据筛选条件，顺次筛选
        // 依据球员位置进行筛选。
        List<PlayerTempInfoPo> playerListA = new ArrayList<>();
        if (!position.equals("all")) {
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).getPosition()
                        //TODO toUpperCase
                        .contains(position.toUpperCase())) {
                    playerListA.add(tempList.get(i));
                }
            }
        } else {
            playerListA = tempList;
        }
        // 依据球员效力的联盟进行筛选
        List<PlayerTempInfoPo> playerListB = new ArrayList<>();
        if (!league.equals("all")) {
            for (int i = 0; i < playerListA.size(); i++) {
                if (playerListA.get(i).getLeague().equals(league)) {
                    playerListB.add(playerListA.get(i));
                }
            }
        } else {
            playerListB = playerListA;
        }
        // 依据球员年龄进行筛选。
        // 年龄筛选条件使用int类型表示，分别用0,1,2,3,4表示all,<=22 ，22< X <= 25 , 25 < X <= 30
        // ,>30。
        List<PlayerTempInfoPo> playerListC = new ArrayList<>();
        switch (age) {
            case 0:
                playerListC = playerListB;
                break;
            case 1:
                for (int i = 0; i < playerListB.size(); i++) {
                    if (playerListB.get(i).getAge() <= 22) {
                        playerListC.add(playerListB.get(i));
                    }
                }
                break;
            case 2:
                for (int i = 0; i < playerListB.size(); i++) {
                    if (playerListB.get(i).getAge() > 22
                            && playerListB.get(i).getAge() <= 25) {
                        playerListC.add(playerListB.get(i));
                    }
                }
                break;
            case 3:
                for (int i = 0; i < playerListB.size(); i++) {
                    if (playerListB.get(i).getAge() > 25
                            && playerListB.get(i).getAge() <= 30) {
                        playerListC.add(playerListB.get(i));
                    }
                }
                break;
            case 4:
                for (int i = 0; i < playerListB.size(); i++) {
                    if (playerListB.get(i).getAge() > 30) {
                        playerListC.add(playerListB.get(i));
                    }
                }
                break;
        }
        return playerListC;
    }


    private List<PlayerPrimaryInfoPo> getPrimaryPlayerInfo(Context context) {
        List<PlayerPrimaryInfoPo> tempList = new ArrayList<PlayerPrimaryInfoPo>();
        PlayerDS tempPlayerDS = new PlayerDSImpl();
        tempList = tempPlayerDS.getPlayerList(context);
        return tempList;

    }


}
