package com.sora.projectn.database.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sora.projectn.database.DBHelper;
import com.sora.projectn.po.PlayerPo;
import com.sora.projectn.po.TeamPo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/2/2.
 */
public class PlayerDBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    private static final String TABLE = "player";



    private static final String KEY_ABBR = "abbr";
    private static final String KEY_NO = "no";
    private static final String KEY_NAME = "name";
    private static final String KEY_POS = "pos";
    private static final String KEY_HT = "ht";
    private static final String KEY_WT = "wt";
    private static final String KEY_BIRTH = "birth";
    private static final String KEY_EXP = "collage" ;
    private static final String KEY_COLLAGE = "collage" ;
    private static final String KEY_IMG = "img" ;



    public PlayerDBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 增加 球员基本数据
     * @param list
     */
    public void add(List<PlayerPo> list) {
        for (PlayerPo po : list) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_ABBR, po.getAbbr());
            cv.put(KEY_NO, po.getNo());
            cv.put(KEY_NAME, po.getName());
            cv.put(KEY_POS, po.getPos());
            cv.put(KEY_HT, po.getHt());
            cv.put(KEY_WT, po.getWt());
            cv.put(KEY_BIRTH, po.getBirth());
            cv.put(KEY_EXP, po.getExp());
            cv.put(KEY_COLLAGE, po.getCollage());
            cv.put(KEY_IMG, po.getImg());

            db.insert(TABLE, null, cv);
        }
    }


    /**
     * 查找球员照片网络路径
     * @return List<String>
     */
    public Map<String,String> queryPlayerImg(){
        Map<String,String> map = new HashMap<>();
        Cursor c = queryTheCursor();

        while (c.moveToNext()){
            String img = c.getString(c.getColumnIndex(KEY_IMG));
            String name = c.getString(c.getColumnIndex(KEY_NAME));

            map.put(name,img);
        }

        c.close();
        return map;
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
