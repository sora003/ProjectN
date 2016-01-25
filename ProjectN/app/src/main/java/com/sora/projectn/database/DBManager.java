package com.sora.projectn.database;

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

    private static final String TABLE_TEAM = "team";



    private static final String KEY_NAME = "name";
    private static final String KEY_ABBR = "abbr";
    private static final String KEY_FOUNDED = "founded";
    private static final String KEY_CITY = "city";
    private static final String KEY_LEAGUE = "league";
    private static final String KEY_CONFERENCE = "conference";
    private static final String KEY_SNAME = "sName" ;
//    private static final String KEY = ;


    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 增加 球队基本数据 仅包含name abbr founded sName
     * @param list
     */
    public void add(List<TeamPo> list) {
        for (TeamPo teamPo : list) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_NAME, teamPo.getName());
            cv.put(KEY_ABBR, teamPo.getAbbr());
            cv.put(KEY_FOUNDED, teamPo.getFounded());
            cv.put(KEY_SNAME, teamPo.getsName());
            db.insert(TABLE_TEAM,null,cv);
        }
    }

    /**
     * 更新 球队基本数据
     * @param list
     */
    public  void update(List<TeamPo> list){
        for (TeamPo teamPo : list) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_CITY, teamPo.getCity());
            cv.put(KEY_LEAGUE, teamPo.getLeague());
            cv.put(KEY_CONFERENCE, teamPo.getConference());
            db.update(TABLE_TEAM, cv, "abbr=?", new String[]{teamPo.getAbbr()});
        }
    }

    /**
     * 查找球队基本数据 仅查找abbr项
     * @return List<String>
     */
    public List<String> queryTeamAbbr(){
        List<String> list = new ArrayList<String>();
        Cursor c = queryTheCursor_team();

        while (c.moveToNext()){
            String abbr = null;
            abbr = c.getString(c.getColumnIndex(KEY_ABBR));
            list.add(abbr);
        }

        c.close();
        return list;
    }

    /**
     * 查找球队基本数据
     * @return List<TeamPo>
     */
    public List<TeamPo> queryTeamList(){
        List<TeamPo> list = new ArrayList<TeamPo>();
        Cursor c = queryTheCursor_team();

        while (c.moveToNext()){
            TeamPo teamPo = new TeamPo();
            teamPo.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            teamPo.setAbbr(c.getString(c.getColumnIndex(KEY_ABBR)));
            teamPo.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
            teamPo.setLeague(c.getString(c.getColumnIndex(KEY_LEAGUE)));
            teamPo.setConference(c.getString(c.getColumnIndex(KEY_CONFERENCE)));
            teamPo.setFounded(c.getInt(c.getColumnIndex(KEY_FOUNDED)));
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
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_TEAM,null);
        return c;
    }

    /**
     * 关闭数据库
     */
    public void closeDB(){
        db.close();
    }

}
