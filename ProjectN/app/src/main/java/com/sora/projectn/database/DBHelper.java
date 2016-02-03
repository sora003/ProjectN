package com.sora.projectn.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sora on 2016/1/19.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NBADATA.db";
    public static final  int DATABASE_VERSION = 1;



    public DBHelper(Context context) {
        //SQLiteDatabase.CursorFactory 设置为null 使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次创建时调用OnCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建table team
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "team " +                                                       //表名
                "(id integer primary key autoincrement ," +                     //主键
                "abbr varchar(20) , " +                                         //球队缩写
                "name varchar(60) , " +                                         //球队全名
                "sName varchar(60) , " +                                        //球队缩略名
                "city varchar(60) , " +                                         //球队所在城市名
                "league varchar(60) , " +                                        //球队所在联盟名
                "conference varchar(60) , " +                                    //球队所在分区名
                "founded integer) ");                                             //球队建立时间



        //缓存表  每次调用都需要先更新数据
        //创建table teamSeasonGame
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "teamSeasonGame " +                                              //表名
                "(id integer primary key autoincrement ," +                     //主键
                "abbr varchar(20) , " +                                         //球队缩写
                "year integer ," +                                             //最新赛季
                "win integer , " +                                              //球队胜场
                "lose integer ," +                                             //球队负场
                "rank integer ," +                                                //排名
                "mp integer , " +                                               //比赛时长
                "fg integer , " +                                               //命中
                "fga integer , " +                                              //出手
                "p3 integer , " +                                               //3分命中
                "p3a integer , " +                                              //3分出手
                "p2 integer , " +                                               //2分命中
                "p2a integer , " +                                              //2分出手
                "ft integer , " +                                               //罚球命中
                "fta integer , " +                                              //罚球出手
                "orb integer , " +                                              //进攻
                "drb integer , " +                                              //防守
                "trb integer , " +                                              //篮板
                "ast integer , " +                                              //助攻
                "stl integer , " +                                              //抢断
                "blk integer , " +                                              //盖帽
                "tov integer , " +                                              //失误
                "pf integer , " +                                               //犯规
                "pts integer ," +                                             //得分
                "opMp integer , " +                                               //对手比赛时长
                "opFg integer , " +                                               //对手命中
                "opFga integer , " +                                              //对手出手
                "opP3 integer , " +                                               //对手3分命中
                "opP3a integer , " +                                              //对手3分出手
                "opP2 integer , " +                                               //对手2分命中
                "opP2a integer , " +                                              //对手2分出手
                "opFt integer , " +                                               //对手罚球命中
                "opFta integer , " +                                              //对手罚球出手
                "opOrb integer , " +                                              //对手进攻
                "opDrb integer , " +                                              //对手防守
                "opTrb integer , " +                                              //对手篮板
                "opAst integer , " +                                              //对手助攻
                "opStl integer , " +                                              //对手抢断
                "opBlk integer , " +                                              //对手盖帽
                "opTov integer , " +                                              //对手失误
                "opPf integer , " +                                               //对手犯规
                "opPts integer)");                                                //对手得分


        //创建table player
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "player " +                                                     //表名
                "(id integer primary key autoincrement ," +                     //主键
                "abbr varchar(60) , " +                                         //球员效力球队
                "no integer , " +                                               //球衣号码
                "name varchar(60) ," +                                          //球员姓名
                "pos varchar(60) , " +                                          //球场担任位置
                "ht varchar(60) ," +                                            //球员身高
                "wt integer , " +                                               //球员体重
                "birth integer , " +                                               //球员生日
                "exp integer , " +                                              //球员的NBA职业生涯时间
                "collage varchar(60) , " +                                       //球员毕业大学
                "img varchar(60))");                                                //球员照片网络路径


        //创建table match
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "match " +                                                     //表名
                "(id integer primary key autoincrement ," +                     //主键
                "date varchar(20) , " +                                         //比赛日期
                "abbr1 varchar(20) , " +                                        //第一支球队球队缩写
                "scoring1 varchar(60) ," +                                      //第一支球队球队小节得分
                "player1 varchar(255) , " +                                     //第一支球队球员
                "mp1 varchar(80) ," +                                           //第一支球队在场时间
                "fg1 varchar(80) , " +                                          //第一支球队命中
                "fga1 varchar(80) , " +                                         //第一支球队出手
                "p31 varchar(80) , " +                                          //第一支球队3分命中
                "p3a1 varchar(80) , " +                                         //第一支球队3分出手
                "ft1 varchar(80) , " +                                          //第一支球队罚球命中
                "fta1 varchar(80) , " +                                         //第一支球队罚球出手
                "orb1 varchar(80) , " +                                         //第一支球队进攻
                "drb1 varchar(80) , " +                                         //第一支球队防守
                "trb1 varchar(80) , " +                                         //第一支球队篮板
                "ast1 varchar(80) , " +                                         //第一支球队助攻
                "stl1 varchar(80) , " +                                         //第一支球队抢断
                "blk1 varchar(80) , " +                                         //第一支球队盖帽
                "tov1 varchar(80) , " +                                         //第一支球队失误
                "pf1 varchar(80) , " +                                          //第一支球队犯规
                "pts1 varchar(80) , " +                                         //第一支球队得分
                "abbr2 varchar(20) , " +                                        //第二支球队球队缩写
                "scoring2 varchar(60) ," +                                      //第二支球队球队小节得分
                "player2 varchar(255) , " +                                     //第二支球队球员
                "mp2 varchar(80) ," +                                           //第二支球队在场时间
                "fg2 varchar(80) , " +                                          //第二支球队命中
                "fga2 varchar(80) , " +                                         //第二支球队出手
                "p32 varchar(80) , " +                                          //第二支球队3分命中
                "p3a2 varchar(80) , " +                                         //第二支球队3分出手
                "ft2 varchar(80) , " +                                          //第二支球队罚球命中
                "fta2 varchar(80) , " +                                         //第二支球队罚球出手
                "orb2 varchar(80) , " +                                         //第二支球队进攻
                "drb2 varchar(80) , " +                                         //第二支球队防守
                "trb2 varchar(80) , " +                                         //第二支球队篮板
                "ast2 varchar(80) , " +                                         //第二支球队助攻
                "stl2 varchar(80) , " +                                         //第二支球队抢断
                "blk2 varchar(80) , " +                                         //第二支球队盖帽
                "tov2 varchar(80) , " +                                         //第二支球队失误
                "pf2 varchar(80) , " +                                          //第二支球队犯规
                "pts2 varchar(80))");                                            //第二支球队得分
    }



    private String date;

    private String abbr1;
    private String scoring1;
    private String player1;
    private String mp1;
    private String fg1;
    private String fga1;
    private String p31;
    private String p3a1;
    private String ft1;
    private String fta1;
    private String orb1;
    private String drb1;
    private String trb1;
    private String ast1;
    private String stl1;
    private String blk1;
    private String tov1;
    private String pf1;
    private String pts1;

    private String abbr2;
    private String scoring2;
    private String player2;
    private String mp2;
    private String fg2;
    private String fga2;
    private String p32;
    private String p3a2;
    private String ft2;
    private String fta2;
    private String orb2;
    private String drb2;
    private String trb2;
    private String ast2;
    private String stl2;
    private String blk2;
    private String tov2;
    private String pf2;
    private String pts2;

    //TODO 如果DATABASE_VERSION 的值被改为2  系统发现数据库版本更新 将调用OnUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS team");

        onCreate(db);
    }
}
