package com.sora.projectn.po;

/**
 * Created by Sora on 2016/2/5.
 *
 * 代码复用
 * 变量名       变量含义      原代码变量名
 * name         球员姓名      name
 * isFirstPlayer是否首发      isFirstPlayer
 * mp           比赛时长      time
 * fg           命中          successOfShot
 * fga          出手          numberOfShot
 * p3           3分命中       successOfThree
 * p3a          3分出手       numberOfThree
 * ft           罚球命中      successOfPenalty
 * fta          罚球出手      numberOfPenalty
 * orb          进攻          numberOfAssault
 * drb          防守          numberOfDefence
 * trb          篮板          backboard
 * ast          助攻          assist
 * stl          抢断          numberOfSteal
 * blk          盖帽          numberOfBlockShot
 * tov          失误          numberOfFault
 * pf           犯规          numberOfBreakRule
 * pts          得分          totalScore
 * hasDouble    上双          hasDouble
 *
 * TODO position 不确定是否会用到 源码存在对该变量的定义
 */
public class PlayerMatchInfoPo {

    private String name;
    private boolean isFirstPlayer;
    private short backboard;
    private short mp;
    private short fg;
    private short fga;
    private short p3;
    private short p3a;
    private short ft;
    private short fta;
    private short orb;
    private short drb;
    private short trb;
    private short ast;
    private short stl;
    private short blk;
    private short tov;
    private short pf;
    private short pts;
    private boolean hasDouble;

    public short getAst() {
        return ast;
    }

    public void setAst(short ast) {
        this.ast = ast;
    }

    public short getBackboard() {
        return backboard;
    }

    public void setBackboard(short backboard) {
        this.backboard = backboard;
    }

    public short getBlk() {
        return blk;
    }

    public void setBlk(short blk) {
        this.blk = blk;
    }

    public short getDrb() {
        return drb;
    }

    public void setDrb(short drb) {
        this.drb = drb;
    }

    public short getFg() {
        return fg;
    }

    public void setFg(short fg) {
        this.fg = fg;
    }

    public short getFga() {
        return fga;
    }

    public void setFga(short fga) {
        this.fga = fga;
    }

    public short getFt() {
        return ft;
    }

    public void setFt(short ft) {
        this.ft = ft;
    }

    public short getFta() {
        return fta;
    }

    public void setFta(short fta) {
        this.fta = fta;
    }

    public boolean isHasDouble() {
        return hasDouble;
    }

    public void setHasDouble(boolean hasDouble) {
        this.hasDouble = hasDouble;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setIsFirstPlayer(boolean isFirstPlayer) {
        this.isFirstPlayer = isFirstPlayer;
    }

    public short getMp() {
        return mp;
    }

    public void setMp(short mp) {
        this.mp = mp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getOrb() {
        return orb;
    }

    public void setOrb(short orb) {
        this.orb = orb;
    }

    public short getP3() {
        return p3;
    }

    public void setP3(short p3) {
        this.p3 = p3;
    }

    public short getP3a() {
        return p3a;
    }

    public void setP3a(short p3a) {
        this.p3a = p3a;
    }

    public short getPf() {
        return pf;
    }

    public void setPf(short pf) {
        this.pf = pf;
    }

    public short getPts() {
        return pts;
    }

    public void setPts(short pts) {
        this.pts = pts;
    }

    public short getStl() {
        return stl;
    }

    public void setStl(short stl) {
        this.stl = stl;
    }

    public short getTov() {
        return tov;
    }

    public void setTov(short tov) {
        this.tov = tov;
    }

    public short getTrb() {
        return trb;
    }

    public void setTrb(short trb) {
        this.trb = trb;
    }
}
