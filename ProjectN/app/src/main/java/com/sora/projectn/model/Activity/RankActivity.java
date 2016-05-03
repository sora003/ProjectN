package com.sora.projectn.model.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import com.sora.projectn.R;
import com.sora.projectn.model.Fragment.DayRankFragment;
import com.sora.projectn.model.Fragment.EastTeamRankFragment;
import com.sora.projectn.model.Fragment.PlayerRankFragment;
import com.sora.projectn.model.Fragment.WestTeamRankFragment;
import com.sora.projectn.utils.Consts;
import com.sora.projectn.utils.GetHttpResponse;
import com.sora.projectn.utils.RankAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qhy on 2016/4/18.
 * RankActivity
 */
public class RankActivity extends FragmentActivity {

    private ViewPager pager;
    private PagerTabStrip tabstrip;
    ArrayList<Fragment> viewContainter = new ArrayList<Fragment>();
    ArrayList<String> titleContainer = new ArrayList<String>();
    List<Map<String, String>> eastranks = new ArrayList<Map<String,String>>(),westranks = new ArrayList<Map<String,String>>();
    List<Map<String, String>> playerranks = new ArrayList<Map<String,String>>();
    List<Map<String, String>> dayranks = new ArrayList<Map<String,String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO 这个要改掉
//            list = BLS.getTeamConference(mContext);
                List<Map<String, String>> ranks = new ArrayList<Map<String,String>>();
                String url = Consts.getTeamSeasonRanks;
                String jsonstring = GetHttpResponse.getHttpResponse(url);


                try {
                    JSONArray array = new JSONArray(jsonstring);
                    for (int i = 0; i < array.length(); i++) {
                        Map<String,String> temp = new HashMap<String,String>();
                        JSONObject obj = array.getJSONObject(i);
                        String teamID = obj.getString("teamId");
                        String rank = obj.getString("rank");
                        String name = obj.getString("name");
                        String league = obj.getString("league");
                        String wins = obj.getString("wins");
                        String loses = obj.getString("loses");
                        String winRate = obj.getString("winRate");
                        String gamesBehind = obj.getString("gamesBehind");
                        String pspg = obj.getString("pspg");
                        String papg = obj.getString("papg");

                        temp.put("teamID", teamID);
                        temp.put("rank", rank);
                        temp.put("name", name);
                        temp.put("league", league);
                        temp.put("wins", wins);
                        temp.put("loses", loses);
                        temp.put("winRate", winRate);
                        temp.put("gamesBehind", gamesBehind);
                        temp.put("pspg", pspg);
                        temp.put("papg", papg);

                        ranks.add(temp);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    ranks = null;
                }

                getplayerranks();
                getdayranks();
                initdata(ranks);
                handler.sendEmptyMessage(Consts.SET_VIEW);
            }
        }).start();

//        initviewpager();
    }

    private void initview(){
        setContentView(R.layout.activity_rank);
        pager = (ViewPager)findViewById(R.id.rankpager);
        tabstrip = (PagerTabStrip) this.findViewById(R.id.ranktabstrip);
        //取消tab下面的长横线
        tabstrip.setDrawFullUnderline(false);
        //设置tab的背景色
        tabstrip.setBackgroundColor(this.getResources().getColor(R.color.bg));
        //设置当前tab页签的下划线颜色
        tabstrip.setTabIndicatorColor(this.getResources().getColor(R.color.red));
        tabstrip.setTextSpacing(200);

    }

    private void initdata(List<Map<String,String>> ranks){
        for(Map<String, String> team:ranks){
            if(team.get("league").equals("0")){
                eastranks.add(team);
            }
            else if(team.get("league").equals("1")){
                westranks.add(team);
            }
        }
        Collections.sort(eastranks, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> lhs, Map<String, String> rhs) {
                if (Integer.parseInt(lhs.get("rank")) < Integer.parseInt(rhs.get("rank"))) {
                    return -1;
                } else return 1;
            }
        });
        Collections.sort(westranks, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> lhs, Map<String, String> rhs) {
                if (Integer.parseInt(lhs.get("rank")) < Integer.parseInt(rhs.get("rank"))) {
                    return -1;
                } else return 1;
            }
        });
    }

    private void initviewpager(){
        viewContainter.add(new EastTeamRankFragment(eastranks));
        viewContainter.add(new WestTeamRankFragment(westranks));
        viewContainter.add(new PlayerRankFragment(playerranks));
        viewContainter.add(new DayRankFragment(dayranks));
        titleContainer.add("东部排行");
        titleContainer.add("西部排行");
        titleContainer.add("球员排行");
        titleContainer.add("今日排行");
        pager.setAdapter(new RankAdapter(getSupportFragmentManager(), viewContainter, titleContainer));

    }





    public void getplayerranks(){
        List<Map<String,String>> playerranks = new ArrayList<Map<String,String>>();
        String url2 =Consts.getPlayerRanks;
        String jsonstring2 = GetHttpResponse.getHttpResponse(url2);
        try{
            JSONArray array2 = new JSONArray(jsonstring2);
            for (int i = 0; i < array2.length(); i++) {
                Map<String,String> temp = new HashMap<String,String>();
                JSONObject obj = array2.getJSONObject(i);
                String name = obj.getString("name");
                String id = obj.getString("id");
                String teamName = obj.getString("teamName");
                String data = obj.getString("data");
                String seasonData = obj.getString("seasonData");
                String type = obj.getString("type");

                temp.put("name",name);
                temp.put("id", id);
                temp.put("teamName", teamName);
                temp.put("data",data);
                temp.put("seasonData",seasonData);
                temp.put("type",type);
                playerranks.add(temp);
            }

        }catch (JSONException e2) {
            e2.printStackTrace();
        }
        RankActivity.this.playerranks = playerranks;
    }

    public void getdayranks(){
        List<Map<String,String>> dayranks = new ArrayList<Map<String,String>>();
        String url2 = Consts.getPlayerRanks + "?date=2016-03-21";
        String jsonstring2 = GetHttpResponse.getHttpResponse(url2);
        try{
            JSONArray array2 = new JSONArray(jsonstring2);
            for (int i = 0; i < array2.length(); i++) {
                Map<String,String> temp = new HashMap<String,String>();
                JSONObject obj = array2.getJSONObject(i);
                String name = obj.getString("name");
                String id = obj.getString("id");
                String teamName = obj.getString("teamName");
                String data = obj.getString("data");
                String seasonData = obj.getString("seasonData");
                String type = obj.getString("type");

                temp.put("name",name);
                temp.put("id", id);
                temp.put("teamName", teamName);
                temp.put("data",data);
                temp.put("seasonData",seasonData);
                temp.put("type",type);
                dayranks.add(temp);
            }

        }catch (JSONException e2) {
            e2.printStackTrace();
        }
        RankActivity.this.dayranks = dayranks;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.SET_VIEW:
                    initviewpager();
            }
        }
    };

    private void initlistener(){
        //TODO 监听什么的以后再说.
    }



}
