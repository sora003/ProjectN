package com.sora.projectn.po;

/**
 * Created by Sora on 2016/2/12.
 */
public class PlayerPrimaryInfoPo {

    private String name;
    /**
     * 注意：球员位置信息的存储类型为String，具体方式表示如下： f,c,g分别表示前锋，中锋，后卫。 如果球员是前锋，则 position =
     * f; 如果球员是前锋和中锋，则position = fc; 如果球员是前锋，中锋，后卫，则position = fcg; 依次类推。
     */
    private String position;
    private String team;
    /**
     * 注意：球员所属联盟信息的存储类型为String，具体方式表示如下： east，west分别表示东部联盟，西部联盟。
     * 如果球员是东部联盟，则league = east; 如果球员是西部联盟，则league = west;
     */
    private String league;//表示球员分区的小分区。
    private short age;

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
