package com.sora.projectn.po;

/**
 * Created by Sora on 2016/2/12.
 *
 * 代码复用 修改了部分变量名
 */
public class PlayerTempInfoPo {

    private String name;

    private String position;
    private String team;
    private String league;
    private short age;

    private short match;
    private short firstPlay;
    private int mp;
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
    private double efficiency;
    private short hasDouble;

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public short getAst() {
        return ast;
    }

    public void setAst(short ast) {
        this.ast = ast;
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

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
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

    public short getFirstPlay() {
        return firstPlay;
    }

    public void setFirstPlay(short firstPlay) {
        this.firstPlay = firstPlay;
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

    public short isHasDouble() {
        return hasDouble;
    }

    public void setHasDouble(short hasDouble) {
        this.hasDouble = hasDouble;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public short getMatch() {
        return match;
    }

    public void setMatch(short match) {
        this.match = match;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
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
