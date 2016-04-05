package com.sora.projectn.gc.database.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sora.projectn.gc.database.DBHelper;
import com.sora.projectn.gc.po.TeamSeasonGamePo;

/**
 * Created by Sora on 2016/1/27.
 *
 * 对teamSeasonGame表进行操作
 */
public class TeamSeasonGameDBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    private static final String TABLE = "teamSeasonGame";



    private static final String KEY_ABBR = "abbr";
    private static final String KEY_YEAR = "year";
    private static final String KEY_WIN = "win";
    private static final String KEY_LOSE = "lose";
    private static final String KEY_RANK = "rank";
    //Team
    private static final String KEY_MP = "mp";
    private static final String KEY_FG = "fg";
    private static final String KEY_FGA = "fga";
    private static final String KEY_P3 = "p3";
    private static final String KEY_P3A = "p3a";
    private static final String KEY_P2 = "p2";
    private static final String KEY_P2A = "p2a";
    private static final String KEY_FT = "ft";
    private static final String KEY_FTA = "fta";
    private static final String KEY_ORB = "orb";
    private static final String KEY_DRB = "drb";
    private static final String KEY_TRB = "trb";
    private static final String KEY_AST = "ast";
    private static final String KEY_STL = "stl";
    private static final String KEY_BLK = "blk";
    private static final String KEY_TOV = "tov";
    private static final String KEY_PF = "pf";
    private static final String KEY_PTS = "pts";
    //Opponent
    private static final String KEY_OPMP = "opMp";
    private static final String KEY_OPFG = "opFg";
    private static final String KEY_OPFGA = "opFga";
    private static final String KEY_OPP3 = "opP3";
    private static final String KEY_OPP3A = "opP3a";
    private static final String KEY_OPP2 = "opP2";
    private static final String KEY_OPP2A = "opP2a";
    private static final String KEY_OPFT = "opFt";
    private static final String KEY_OPFTA = "opFta";
    private static final String KEY_OPORB = "opOrb";
    private static final String KEY_OPDRB = "opDrb";
    private static final String KEY_OPTRB = "opTrb";
    private static final String KEY_OPAST = "opAst";
    private static final String KEY_OPSTL = "opStl";
    private static final String KEY_OPBLK = "opBlk";
    private static final String KEY_OPTOV = "opTov";
    private static final String KEY_OPPF = "opPf";
    private static final String KEY_OPPTS = "opPts";





    public TeamSeasonGameDBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }


    /**
     * 添加球队赛季比赛数据
     *
     * @param po  用TeamSeasonGamePo封装的数据对象
     */
    public void add(TeamSeasonGamePo po) {

        ContentValues cv = new ContentValues();

        cv.put(KEY_YEAR, po.getYear());
        cv.put(KEY_WIN, po.getWin());
        cv.put(KEY_LOSE, po.getLose());
        cv.put(KEY_RANK,  po.getRank());

        cv.put(KEY_MP, po.getMp());
        cv.put(KEY_FG, po.getFg());
        cv.put(KEY_FGA, po.getFga());
        cv.put(KEY_P3, po.getP3());
        cv.put(KEY_P3A, po.getP3a());
        cv.put(KEY_P2, po.getP2());
        cv.put(KEY_P2A, po.getP2a());
        cv.put(KEY_FT, po.getFt());
        cv.put(KEY_FTA, po.getFta());
        cv.put(KEY_ORB, po.getOrb());
        cv.put(KEY_DRB, po.getDrb());
        cv.put(KEY_TRB, po.getTrb());
        cv.put(KEY_AST, po.getAst());
        cv.put(KEY_STL, po.getStl());
        cv.put(KEY_BLK, po.getBlk());
        cv.put(KEY_TOV, po.getTov());
        cv.put(KEY_PF, po.getPf());
        cv.put(KEY_PTS,  po.getPts());

        cv.put(KEY_OPMP, po.getOpMp());
        cv.put(KEY_OPFG, po.getOpFg());
        cv.put(KEY_OPFGA, po.getOpFga());
        cv.put(KEY_OPP3, po.getOpP3());
        cv.put(KEY_OPP3A, po.getOpP3a());
        cv.put(KEY_OPP2, po.getOpP2());
        cv.put(KEY_OPP2A, po.getOpP2a());
        cv.put(KEY_OPFT, po.getOpFt());
        cv.put(KEY_OPFTA, po.getOpFta());
        cv.put(KEY_OPORB, po.getOpOrb());
        cv.put(KEY_OPDRB, po.getOpDrb());
        cv.put(KEY_OPTRB, po.getOpTrb());
        cv.put(KEY_OPAST, po.getOpAst());
        cv.put(KEY_OPSTL, po.getOpStl());
        cv.put(KEY_OPBLK, po.getOpBlk());
        cv.put(KEY_OPTOV, po.getOpTov());
        cv.put(KEY_OPPF, po.getOpPf());
        cv.put(KEY_OPPTS,  po.getOpPts());

        db.insert(TABLE, null, cv);

    }



    /**
     * 根据提供的球队缩写 查找最新赛季年份信息
     *
     * @param abbr 球队缩写
     * @return  int 最新赛季年份信息
     */
    public int queryYear(String abbr){
        int year = 0;
        Cursor c = queryTheCursor();

        while (c.moveToNext()){
            String cAbbr = c.getString(c.getColumnIndex(KEY_ABBR));
            if (cAbbr.equals(abbr)){
                year = c.getInt(c.getColumnIndex(KEY_YEAR));
                break;
            }
        }

        c.close();
        return year;
    }




    /**
     * 根据提供的球队缩写 查找球队最新赛季数据
     *
     * @param abbr 球队缩写
     * @return  TeamSeasonGamePo
     */
    public TeamSeasonGamePo queryTeamSeasonGameInfo(String abbr){
        TeamSeasonGamePo po = new TeamSeasonGamePo();
        Cursor c = queryTheCursor();

        while (c.moveToNext()){
            String cAbbr = c.getString(c.getColumnIndex(KEY_ABBR));
            if (cAbbr.equals(abbr)){
                po.setYear(c.getInt(c.getColumnIndex(KEY_YEAR)));
                po.setWin(c.getInt(c.getColumnIndex(KEY_WIN)));
                po.setLose(c.getInt(c.getColumnIndex(KEY_LOSE)));
                po.setRank(c.getInt(c.getColumnIndex(KEY_RANK)));

                po.setMp(c.getInt(c.getColumnIndex(KEY_MP)));
                po.setFg(c.getInt(c.getColumnIndex(KEY_FG)));
                po.setFga(c.getInt(c.getColumnIndex(KEY_FGA)));
                po.setP3(c.getInt(c.getColumnIndex(KEY_P3)));
                po.setP3a(c.getInt(c.getColumnIndex(KEY_P3A)));
                po.setP2(c.getInt(c.getColumnIndex(KEY_P2)));
                po.setP2a(c.getInt(c.getColumnIndex(KEY_P2A)));
                po.setFt(c.getInt(c.getColumnIndex(KEY_FT)));
                po.setFta(c.getInt(c.getColumnIndex(KEY_FTA)));
                po.setOrb(c.getInt(c.getColumnIndex(KEY_ORB)));
                po.setDrb(c.getInt(c.getColumnIndex(KEY_DRB)));
                po.setTrb(c.getInt(c.getColumnIndex(KEY_TRB)));
                po.setAst(c.getInt(c.getColumnIndex(KEY_AST)));
                po.setStl(c.getInt(c.getColumnIndex(KEY_STL)));
                po.setBlk(c.getInt(c.getColumnIndex(KEY_BLK)));
                po.setTov(c.getInt(c.getColumnIndex(KEY_TOV)));
                po.setPf(c.getInt(c.getColumnIndex(KEY_PF)));
                po.setPts(c.getInt(c.getColumnIndex(KEY_PTS)));

                po.setOpMp(c.getInt(c.getColumnIndex(KEY_OPMP)));
                po.setOpFg(c.getInt(c.getColumnIndex(KEY_OPFG)));
                po.setOpFga(c.getInt(c.getColumnIndex(KEY_OPFGA)));
                po.setOpP3(c.getInt(c.getColumnIndex(KEY_OPP3)));
                po.setOpP3a(c.getInt(c.getColumnIndex(KEY_OPP3A)));
                po.setOpP2(c.getInt(c.getColumnIndex(KEY_OPP2)));
                po.setOpP2a(c.getInt(c.getColumnIndex(KEY_OPP2A)));
                po.setOpFt(c.getInt(c.getColumnIndex(KEY_OPFT)));
                po.setOpFta(c.getInt(c.getColumnIndex(KEY_OPFTA)));
                po.setOpOrb(c.getInt(c.getColumnIndex(KEY_OPORB)));
                po.setOpDrb(c.getInt(c.getColumnIndex(KEY_OPDRB)));
                po.setOpTrb(c.getInt(c.getColumnIndex(KEY_OPTRB)));
                po.setOpAst(c.getInt(c.getColumnIndex(KEY_OPAST)));
                po.setOpStl(c.getInt(c.getColumnIndex(KEY_OPSTL)));
                po.setOpBlk(c.getInt(c.getColumnIndex(KEY_OPBLK)));
                po.setOpTov(c.getInt(c.getColumnIndex(KEY_OPTOV)));
                po.setOpPf(c.getInt(c.getColumnIndex(KEY_OPPF)));
                po.setOpPts(c.getInt(c.getColumnIndex(KEY_OPPTS)));

                break;
            }
        }

        c.close();
        return po;
    }

    /**
     * 查找 球队最新赛季数据 返回带游标的数据集
     *
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
