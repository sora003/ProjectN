package com.sora.projectn.database.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sora.projectn.database.DBHelper;
import com.sora.projectn.po.TeamPo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/19.
 *
 * 对team表进行操作
 */
public class TeamDBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    private static final String TABLE = "team";



    private static final String KEY_NAME = "name";
    private static final String KEY_ABBR = "abbr";
    private static final String KEY_FOUNDED = "founded";
    private static final String KEY_CITY = "city";
    private static final String KEY_LEAGUE = "league";
    private static final String KEY_CONFERENCE = "conference";
    private static final String KEY_SNAME = "sName" ;



    public TeamDBManager(Context context) {
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
            db.insert(TABLE,null,cv);
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
            String[] whereArgs = {teamPo.getAbbr()};

//            Log.i("db_Conference",teamPo.getConference());

            db.update(TABLE, cv, "abbr=?", whereArgs);
        }
    }

    /**
     * 查找球队基本数据 仅查找abbr项
     * @return List<String>
     */
    public List<String> queryTeamAbbr(){
        List<String> list = new ArrayList<String>();
        Cursor c = queryTheCursor();

        while (c.moveToNext()){
            String abbr = null;
            abbr = c.getString(c.getColumnIndex(KEY_ABBR));
            list.add(abbr);
        }

        c.close();
        return list;
    }

    /**
     * 查找球队分区和缩略名信息
     * @return Map<String,String>
     *     key - sName
     *     value - conference
     */
    public Map<String,String> queryTeamSNameAndConference(){

        //由于conference可能相同 将sName作为key存储 虽然在取出的时候会产生一些麻烦
        Map<String,String> map = new HashMap<>();

        Cursor c = queryTheCursor();

        while (c.moveToNext()){
            String value = c.getString(c.getColumnIndex(KEY_CONFERENCE));
            String key = c.getString(c.getColumnIndex(KEY_SNAME));
            map.put(key, value);
        }

        c.close();
        return map;
    }


    /**
     * 查找球队缩略名和缩写信息
     * @return Map<String,String>
     *     key - sName
     *     value - abbr
     */
    public Map<String,String> queryTeamSNameAndAbbr(){
        Map<String,String> map = new HashMap<String,String>();

        Cursor c = queryTheCursor();

        while (c.moveToNext()){
            String key = c.getString(c.getColumnIndex(KEY_SNAME));
            String value = c.getString(c.getColumnIndex(KEY_ABBR));
            map.put(key, value);
        }

        c.close();
        return map;
    }

    /**
     * 查找球队缩略名和缩写信息
     * @return String
     */
    public String queryTeamSName(String abbr){
        String sName = "";

        Cursor c = queryTheCursor();

        while (c.moveToNext()){
            if (c.getString(c.getColumnIndex(KEY_ABBR)).equals(abbr)){
                sName = c.getString(c.getColumnIndex(KEY_SNAME));
                break;
            }
        }

        c.close();
        return sName;
    }


    /**
     * 查找球队所属分区
     *
     * @param abbr
     * @return
     */
    public String queryTeamLeague(String abbr){
        String league = "";

        Cursor c = queryTheCursor();

        while (c.moveToNext()){
            if (c.getString(c.getColumnIndex(KEY_ABBR)).equals(abbr)){
                league = c.getString(c.getColumnIndex(KEY_LEAGUE));
                break;
            }
        }

        c.close();
        return league;
    }


    /**
     * 查找球队基本数据
     * @param abbr
     * @return TeamPo
     */
    public TeamPo queryTeamInfo(String abbr){
        TeamPo po = new TeamPo();
        Cursor c = queryTheCursor();

        while (c.moveToNext()){
            if (c.getString(c.getColumnIndex(KEY_ABBR)).equals(abbr)){
                po.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                po.setAbbr(c.getString(c.getColumnIndex(KEY_ABBR)));
                po.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
                po.setLeague(c.getString(c.getColumnIndex(KEY_LEAGUE)));
                po.setConference(c.getString(c.getColumnIndex(KEY_CONFERENCE)));
                po.setFounded(c.getInt(c.getColumnIndex(KEY_FOUNDED)));
                po.setsName(c.getString(c.getColumnIndex(KEY_SNAME)));
                break;
            }
        }
        c.close();
        return po;
    }

    /**
     * 查找 球队基本数据 返回带游标的数据集
     * @return Cursor
     */
    public Cursor queryTheCursor(){
        Cursor c = db.rawQuery("SELECT * FROM "+ TABLE,null);
        return c;
    }

    /**
     * 关闭数据库
     */
    public void closeDB(){
        db.close();
    }

}
