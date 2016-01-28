package com.sora.projectn.po;

/**
 * Created by Sora on 2016/1/27.
 *
 * 代码复用
 * 修改 ：改变了数据命名方式  删去了一部分可计算得到的数据
 *
 * abbr         球队缩写
 * year         最新赛季
 * win          球队胜场
 * lose         球队负场
 * mp           比赛时长
 * fg           命中
 * fga          出手
 * p3           3分命中
 * p3a          3分出手
 * p2           2分命中
 * p2a          2分出手
 * ft           罚球命中
 * fta          罚球出手
 * orb          进攻
 * drb          防守
 * trb          篮板
 * ast          助攻
 * stl          抢断
 * blk          盖帽
 * tov          失误
 * pf           犯规
 * pts          得分
 */
public class TeamSeasonGamePo {

    private String abbr;
    private int year;
    private int win;
    private int lose;
    private int mp;
    private int fg;
    private int fga;
    private int p3;
    private int p3a;
    private int p2;
    private int p2a;
    private int ft;
    private int fta;
    private int orb;
    private int drb;
    private int trb;
    private int ast;
    private int stl;
    private int blk;
    private int tov;
    private int pf;
    private int pts;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public int getAst() {
        return ast;
    }

    public void setAst(int ast) {
        this.ast = ast;
    }

    public int getBlk() {
        return blk;
    }

    public void setBlk(int blk) {
        this.blk = blk;
    }

    public int getDrb() {
        return drb;
    }

    public void setDrb(int drb) {
        this.drb = drb;
    }

    public int getFg() {
        return fg;
    }

    public void setFg(int fg) {
        this.fg = fg;
    }

    public int getFga() {
        return fga;
    }

    public void setFga(int fga) {
        this.fga = fga;
    }

    public int getFt() {
        return ft;
    }

    public void setFt(int ft) {
        this.ft = ft;
    }

    public int getFta() {
        return fta;
    }

    public void setFta(int fta) {
        this.fta = fta;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getOrb() {
        return orb;
    }

    public void setOrb(int orb) {
        this.orb = orb;
    }

    public int getP2() {
        return p2;
    }

    public void setP2(int p2) {
        this.p2 = p2;
    }

    public int getP2a() {
        return p2a;
    }

    public void setP2a(int p2a) {
        this.p2a = p2a;
    }

    public int getP3() {
        return p3;
    }

    public void setP3(int p3) {
        this.p3 = p3;
    }

    public int getP3a() {
        return p3a;
    }

    public void setP3a(int p3a) {
        this.p3a = p3a;
    }

    public int getPf() {
        return pf;
    }

    public void setPf(int pf) {
        this.pf = pf;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getStl() {
        return stl;
    }

    public void setStl(int stl) {
        this.stl = stl;
    }

    public int getTov() {
        return tov;
    }

    public void setTov(int tov) {
        this.tov = tov;
    }

    public int getTrb() {
        return trb;
    }

    public void setTrb(int trb) {
        this.trb = trb;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
