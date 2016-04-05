package com.sora.projectn.gc.po;

/**
 * Created by Sora on 2016/1/27.
 *
 * 代码复用
 * 修改 ： 合并了PlayerPo和PlayerPrimaryInforPo 并删去了部分数据 重新命名了数据
 *
 *
 * abbr         球员效力球队
 * no           球员编号
 * name         球员姓名
 * pos          球员担任位置
 * ht           球员身高
 * wt           球员体重
 * birth        球员生日
 * exp          球员球龄
 * collage      球员毕业大学
 * img          球员照片网络路径
 */
public class PlayerPo {

    private String abbr;
    private int no;
    private String name;
    private String pos;
    private String ht;
    private int wt;
    private int birth;
    private int exp;
    private String collage;
    private String img;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getHt() {
        return ht;
    }

    public void setHt(String ht) {
        this.ht = ht;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public int getWt() {
        return wt;
    }

    public void setWt(int wt) {
        this.wt = wt;
    }
}
