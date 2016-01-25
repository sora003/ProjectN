package com.sora.projectn.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
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
        db.execSQL("CREATE TABLE IF NOT EXISTS team (id integer primary key autoincrement ,abbr varchar(20), name varchar(60) , city varchar(60) , league varchar(60) , conference varchar(60) , arena varchar(60) , founded integer)");

    }

    //TODO 如果DATABASE_VERSION 的值被改为2  系统发现数据库版本更新 将调用OnUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS team");

        onCreate(db);
    }
}
