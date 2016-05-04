package com.sora.projectn.model.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.sora.projectn.R;
import com.sora.projectn.utils.ACache;
import com.sora.projectn.utils.Consts;
import com.sora.projectn.utils.GetHttpResponse;
import com.sora.projectn.utils.MatchListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qhy on 2016/5/4.
 */
public class MatchSearchActivity  extends FragmentActivity {


    private Toolbar toolbar;
    private List<Map<String,String>> matchlist = new ArrayList<Map<String,String>>();
    private ListView matchlistview;
    private DatePicker datepicker;
    private int year,monthofyear,dayofmonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchsearch);
        initview();
        initlistener();

    }

    private void initview(){

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("历史球赛");
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        datepicker = (DatePicker)findViewById(R.id.datepicker);
        matchlistview = (ListView)findViewById(R.id.matchlist_for_date);
    }

    private void initlistener(){
        datepicker.init(2016, 4, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                MatchSearchActivity.this.year = year;
                MatchSearchActivity.this.monthofyear = monthOfYear;
                MatchSearchActivity.this.dayofmonth = dayOfMonth;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String month = String.format("%02d", monthofyear);
                        String day = String.format("%02d",dayofmonth);
                        getmatchlistfordate(String.valueOf(MatchSearchActivity.this.year)+"-"+month+"-"+day);
                        handler.sendEmptyMessage(Consts.SET_VIEW);
                    }
                }).start();
            }
        });
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.SET_VIEW:
                    initmatchlist();
                    break;
                case Consts.RES_ERROR:
                    Toast.makeText(MatchSearchActivity.this, Consts.ToastMessage01, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initmatchlist(){
        MatchListAdapter mladapter = new MatchListAdapter(matchlist,this);
        matchlistview.setAdapter(mladapter);
        matchlistview.setOnItemClickListener(new MListener());
    }

    private void getmatchlistfordate(String dates){

        String jsonString;

        /**
         * getTeamRankInfos
         */
        jsonString = ACache.get(this).getAsString("getMatchListForDay"+dates );

        if (jsonString == null){

            jsonString = GetHttpResponse.getHttpResponse(Consts.getMatchListForDay+"?"+"date=" + dates);

            if (jsonString == null){
//                    return null;
                handler.sendEmptyMessage(Consts.RES_ERROR);
            }

            ACache.get(this).put("getMatchListForDay"+dates ,jsonString,ACache.TEST_TIME);
            Log.i("Resource", Consts.resourceFromServer);
        }
        else
        {
            Log.i("Resource",Consts.resourceFromCache);
        }
        List<Map<String, String>> matchfordate = new ArrayList<Map<String,String>>();
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                Map<String,String> temp = new HashMap<String,String>();
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String vId = obj.getString("vId");
                String visitingTeam = obj.getString("visitingTeam");
                String hId = obj.getString("hId");
                String homeTeam = obj.getString("homeTeam");
                String visitingScore = obj.getString("visitingScore");
                String homeScore = obj.getString("homeScore");
                String type = obj.getString("type");
                String date = obj.getString("date");
                String year = obj.getString("year");
                String time = obj.getString("time");

                temp.put("id", id);
                temp.put("vId", vId);
                temp.put("visitingTeam", visitingTeam);
                temp.put("hId", hId);
                temp.put("homeTeam", homeTeam);
                temp.put("visitingScore", visitingScore);
                temp.put("homeScore", homeScore);
                temp.put("type", type);
                temp.put("date", date);
                temp.put("year", year);
                temp.put("time", time);

                matchfordate.add(temp);
            }



        } catch (JSONException e) {
            e.printStackTrace();
//            match_teaminfo = null;
        }
//        this.match_team_info = match_teaminfo;
        matchlist = matchfordate;
    }

    private class MListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(MatchSearchActivity.this, MatchActivity.class);
            intent.putExtra("no",Integer.parseInt(matchlist.get(position).get("id")));
            startActivity(intent);


        }
    }

}
