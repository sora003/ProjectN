package com.sora.projectn.utils.beans;

/**
 * Created by Sora on 2016/2/12.
 *
 * 代码复用
 *
 * 该类主要用于包装界面层向逻辑层传递的关于Player的筛选条件，筛选条件主要有以下几个方面： 球员位置
 *               （all全部位置，f前锋，c中锋，g后卫） 效力的联盟 （all全部联盟，east东部，west西部） 排序依据
 *               （0得分，1篮板
 *               ，2助攻，3得分/篮板/助攻（加权比为1:1:1），4盖帽，5抢断，6犯规，7失误，8分钟，9效率，10投篮，11
 *               三分，12罚球， 13两双（特指得分、篮板、助攻、抢断、盖帽中任何两项 ）） 关于类的设计：
 *               球员位置使用String类型表示，对应上方位置，分别为：all,f,c,g;
 *               效力的联盟使用string类型表示，对应上方位置，分别为：all,east,west;
 *               排序依据使用int类型表示，对应上方位置，分别为：0,1,2,3,4,5,6,7,8,9,10,11,12,13;
 *               年龄筛选条件使用int类型表示，分别用0,1,2,3,4表示all, <=22 ，22< X <= 25 , 25 < X
 *               <= 30 , >30。
 */
public class SearchPlayerKeysVo {

    private String position;
    private String league;
    private int sortKey;
    private int age;

    public SearchPlayerKeysVo(int age, String league, String position, int sortKey) {
        this.age = age;
        this.league = league;
        this.position = position;
        this.sortKey = sortKey;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSortKey() {
        return sortKey;
    }

    public void setSortKey(int sortKey) {
        this.sortKey = sortKey;
    }
}
