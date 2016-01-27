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
                "pts integer ," +                                               //得分
                "hasData integer ," +                                           //是否存储数据
                "setTime integer)");                                         //存储数据时间

    }

    //TODO 如果DATABASE_VERSION 的值被改为2  系统发现数据库版本更新 将调用OnUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS team");

        onCreate(db);
    }
}
