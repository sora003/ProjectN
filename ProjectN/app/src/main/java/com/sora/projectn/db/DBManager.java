package com.sora.projectn.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sora.projectn.po.TeamPo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/1/19.
 */
public class DBManager  {

    private  DBHelper helper;
    private SQLiteDatabase db;


    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 增加 球队基本数据
     * @param list
     */
    public void add(List<TeamPo> list) {
        for (TeamPo teamPo : list) {
            ContentValues cv = new ContentValues();
            cv.put("name", teamPo.getName());
            cv.put("abbr", teamPo.getAbbr());
            cv.put("city", teamPo.getCity());
            cv.put("league", teamPo.getLeague());
            cv.put("conference", teamPo.getConference());
            cv.put("arena", teamPo.getArena());
            cv.put("founded", teamPo.getFounded());
            db.insert("team",null,cv);
        }
    }

    /**
     * TODO
     * 修改 球队基本数据
     * @param list
     */
    public  void update(List<TeamPo> list){
        for (TeamPo teamPo : list) {
            ContentValues cv = new ContentValues();
            cv.put("name", teamPo.getName());
            cv.put("abbr", teamPo.getAbbr());
            cv.put("city", teamPo.getCity());
            cv.put("league", teamPo.getLeague());
            cv.put("conference", teamPo.getConference());
            cv.put("arena", teamPo.getArena());
            cv.put("founded", teamPo.getFounded());
            db.update("team", cv, "abbr=?", new String[]{teamPo.getAbbr()});
        }
    }

    /**
     * 查找球队基本数据
     * @return List<TeamPo>
     */
    public List<TeamPo> query(){
        List<TeamPo> list = new ArrayList<TeamPo>();
        Cursor c = queryTheCursor_team();

        while (c.moveToNext()){
            TeamPo teamPo = new TeamPo();
            teamPo.setName(c.getString(c.getColumnIndex("name")));
            teamPo.setAbbr(c.getString(c.getColumnIndex("abbr")));
            teamPo.setCity(c.getString(c.getColumnIndex("city")));
            teamPo.setLeague(c.getString(c.getColumnIndex("league")));
            teamPo.setConference(c.getString(c.getColumnIndex("conference")));
            teamPo.setArena(c.getString(c.getColumnIndex("arena")));
            teamPo.setFounded(c.getInt(c.getColumnIndex("founded")));
            list.add(teamPo);
        }

        c.close();
        return list;
    }

    /**
     * 查找 球队基本数据 返回带游标的数据集
     * @return Cursor
     */
    public Cursor queryTheCursor_team(){
        Cursor c = db.rawQuery("SELECT * FROM team",null);
        return c;
    }

    /**
     * 关闭数据库
     */
    public void closeDB(){
        db.close();
    }

}
