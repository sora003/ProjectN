package com.sora.projectn.model.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toolbar;

import com.sora.projectn.R;
import com.sora.projectn.utils.ACache;
import com.sora.projectn.utils.Consts;
import com.sora.projectn.utils.GetHttpResponse;
import com.sora.projectn.utils.MatchPlayerAdapter;
import com.sora.projectn.utils.MatchTeamAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/2/6.
 */
public class MatchActivity extends AppCompatActivity{

    private int no;
    private int matchId = 2015102701;
    private int hometeamId = 0 , customteamId = 0;
    private static final int GET_DATA = 0x01;
    private static final String serverIP = "192.168.31.225";
    private static final String serverPORT = "8080";
    private static final String databaseNAME = "NBADataSystem";
    private static final String getmatchInfo = "getTeamMatchStatistics.do";
    private static final String getplayerInfo = "getPlayerMatchStatistics.do";
    private List<Map<String,String>> match_team_info = new ArrayList<Map<String,String>>();
    private List<Map<String,String>> match_hometeam_playerinfos = new ArrayList<Map<String,String>>();
    private List<Map<String,String>> match_customteam_playerinfos = new ArrayList<Map<String,String>>();

    private Toolbar toolbar;

    private ListView match_teaminfo_lv,match_hometeam_playerinfo_lv,match_customteam_playerinfo_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        initview();
        parseIntent();

        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }).start();


    }

    /**
     * 获取数据
     */
    private void getData() {
        getmatchteaminfo();
        getmatchplayerinfo();
        handler.sendEmptyMessage(GET_DATA);
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_DATA:
                    //TODO 让界面显示比赛信息
                    MatchTeamAdapter mtadapter = new MatchTeamAdapter(match_team_info,MatchActivity.this);
                    match_teaminfo_lv.setAdapter(mtadapter);
                    MatchPlayerAdapter mpadapter1 = new MatchPlayerAdapter(match_hometeam_playerinfos,MatchActivity.this);
                    match_hometeam_playerinfo_lv.setAdapter(mpadapter1);
                    MatchPlayerAdapter mpadapter2 = new MatchPlayerAdapter(match_customteam_playerinfos,MatchActivity.this);
                    match_customteam_playerinfo_lv.setAdapter(mpadapter2);
            }
        }
    };


    private void getmatchteaminfo(){
        List<Map<String, String>> match_teaminfo = new ArrayList<Map<String,String>>();
//        String url = Consts.getTeamMatchStatistics+"?"+"matchId="+matchId;
        String url = "";
        String jsonstring = GetHttpResponse.getHttpResponse(url);
        try {
            JSONArray array = new JSONArray(jsonstring);
            for (int i = 0; i < array.length(); i++) {
                Map<String,String> temp = new HashMap<String,String>();
                JSONObject obj = array.getJSONObject(i);
                String tempurl = Consts.getTeamInfos+"?"+obj.get("teamId");
                String name = null;
                String jsonString;

                /**
                 * getTeamInfos
                 */
                jsonString = ACache.get(this).getAsString("getTeamInfos - " + obj.get("teamId"));

                if (jsonString == null){

                    jsonString = GetHttpResponse.getHttpResponse(Consts.getTeamInfos + "?teamId=" + obj.get("teamId"));



                    ACache.get(this).put("getTeamInfos - " + obj.get("teamId") ,jsonString,ACache.TEST_TIME);
                    Log.i("Resource", Consts.resourceFromServer);
                }
                else
                {
                    Log.i("Resource",Consts.resourceFromCache);
                }

                //解析jsonString 构造Map
                try {

                    JSONObject obj2 = new JSONObject(jsonString);

                    name = obj2.getString("name");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String matchId = obj.getString("matchId");
                String teamId = obj.getString("teamId");
                String ifHome = obj.getString("ifHome");
                String time = obj.getString("time");
                String twoHit = obj.getString("twoHit");
                String twoShot = obj.getString("twoShot");
                String threeHit = obj.getString("threeHit");
                String threeShot = obj.getString("threeShot");
                String freeThrowHit = obj.getString("freeThrowHit");
                String freeThrowShot = obj.getString("freeThrowShot");
                String offReb = obj.getString("offReb");
                String defReb = obj.getString("defReb");
                String ass = obj.getString("ass");
                String steal = obj.getString("steal");
                String blockShot = obj.getString("blockShot");
                String turnOver = obj.getString("turnOver");
                String foul = obj.getString("foul");
                String score = obj.getString("score");

                temp.put("matchId",matchId);
                temp.put("teamId",teamId);
                temp.put("name",name);
                temp.put("ifHome",ifHome);
                if(customteamId == 0 && Integer.parseInt(ifHome) == 0){
                    customteamId = Integer.parseInt(teamId);
                }
                else if(hometeamId == 0 && Integer.parseInt(ifHome) == 1){
                    hometeamId = Integer.parseInt(teamId);
                }
                temp.put("time",time);
                temp.put("twoHit",twoHit);
                temp.put("twoShot",twoShot);
                temp.put("threeHit",threeHit);
                temp.put("threeShot",threeShot);
                temp.put("freeThrowHit",freeThrowHit);
                temp.put("freeThrowShot",freeThrowShot);
                temp.put("offReb",offReb);
                temp.put("defReb",defReb);
                temp.put("ass",ass);
                temp.put("steal",steal);
                temp.put("blockShot",blockShot);
                temp.put("turnOver",turnOver);
                temp.put("foul",foul);
                temp.put("score",score);



                match_teaminfo.add(temp);
            }



        } catch (JSONException e) {
            e.printStackTrace();
            match_teaminfo = null;
        }
        this.match_team_info = match_teaminfo;
    }

    private void getmatchplayerinfo(){

        List<Map<String, String>> match_playerinfo = new ArrayList<Map<String,String>>();
        String url = "http://"+serverIP+":"+serverPORT+"/"+databaseNAME+"/"+getplayerInfo+"?"+"matchId="+matchId;
        String jsonstring = GetHttpResponse.getHttpResponse(url);
        try {
            JSONArray array = new JSONArray(jsonstring);
            for (int i = 0; i < array.length(); i++) {
                Map<String,String> temp = new HashMap<String,String>();
                JSONObject obj = array.getJSONObject(i);
                String matchId = obj.getString("matchId");
                String playerId = obj.getString("playerId");
                String teamId = obj.getString("teamId");
                String playerName = obj.getString("playerName");
                String isFirst = obj.getString("isFirst");
                String time = obj.getString("time");
                String twoHit = obj.getString("twoHit");
                String twoShot = obj.getString("twoShot");
                String threeHit = obj.getString("threeHit");
                String threeShot = obj.getString("threeShot");
                String freeThrowHit = obj.getString("freeThrowHit");
                String freeThrowShot = obj.getString("freeThrowShot");
                String offReb = obj.getString("offReb");
                String defReb = obj.getString("defReb");
                String ass = obj.getString("ass");
                String steal = obj.getString("steal");
                String blockShot = obj.getString("blockShot");
                String turnOver = obj.getString("turnOver");
                String foul = obj.getString("foul");
                String score = obj.getString("score");

                temp.put("playerName",playerName);
                temp.put("playerId",playerId);
                temp.put("matchId",matchId);
                temp.put("teamId",teamId);
                temp.put("isFirst",isFirst);
                temp.put("time",time);
                temp.put("twoHit",twoHit);
                temp.put("twoShot",twoShot);
                temp.put("threeHit",threeHit);
                temp.put("threeShot",threeShot);
                temp.put("freeThrowHit",freeThrowHit);
                temp.put("freeThrowShot",freeThrowShot);
                temp.put("offReb",offReb);
                temp.put("defReb",defReb);
                temp.put("ass",ass);
                temp.put("steal",steal);
                temp.put("blockShot",blockShot);
                temp.put("turnOver",turnOver);
                temp.put("foul",foul);
                temp.put("score",score);



                 match_playerinfo.add(temp);
            }



        } catch (JSONException e) {
            e.printStackTrace();
            match_playerinfo = null;
        }
        for(Map<String,String> t:match_playerinfo){
            if(Integer.parseInt(t.get("teamId")) == hometeamId){
                match_hometeam_playerinfos.add(t);
            }
            else if(Integer.parseInt(t.get("teamId")) == customteamId){
                match_customteam_playerinfos.add(t);
            }
        }

    }

    private void initview(){
        match_teaminfo_lv = (ListView)findViewById(R.id.match_pts);
        match_hometeam_playerinfo_lv = (ListView)findViewById(R.id.match_2);
        match_customteam_playerinfo_lv = (ListView)findViewById(R.id.match_3);

    }


    /**
     * 读取Intent传递的no 获取比赛对应编号
     */
    private void parseIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        no = bundle.getInt("no");
        matchId = no;
    }
}
