package com.sora.projectn.model.vo;

/**
 * Created by Sora on 2016/2/5.
 */
public class TeamPlayerVo {
    private String name;
    private String pos;
    private int no;

    public TeamPlayerVo(String name, int no, String pos) {
        this.name = name;
        this.no = no;
        this.pos = pos;
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
}
