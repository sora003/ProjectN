package com.sora.projectn.vo;

/**
 * Created by Sora on 2016/2/12.
 *
 * 代码复用
 */
public class AllPlayerInfoVo {

    private String name;

    private String team;

    private int match;
    private int firstPlay;
    private int mp;
    private double fgp;
    private double p3p;
    private double ftp;
    private int orb;
    private int drb;
    private int trb;
    private int ast;
    private int stl;
    private int blk;
    private int tov;
    private int pf;
    private int pts;


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

    public double getFgp() {
        return fgp;
    }

    public void setFgp(double fgp) {
        this.fgp = fgp;
    }

    public int getFirstPlay() {
        return firstPlay;
    }

    public void setFirstPlay(int firstPlay) {
        this.firstPlay = firstPlay;
    }

    public double getFtp() {
        return ftp;
    }

    public void setFtp(double ftp) {
        this.ftp = ftp;
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
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

    public int getOrb() {
        return orb;
    }

    public void setOrb(int orb) {
        this.orb = orb;
    }

    public double getP3p() {
        return p3p;
    }

    public void setP3p(double p3p) {
        this.p3p = p3p;
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
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
}
