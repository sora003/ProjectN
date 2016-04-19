package com.sora.projectn.model.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sora.projectn.R;
import com.sora.projectn.utils.ACache;
import com.sora.projectn.utils.GetHttpResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sora on 2016/1/28.
 */
public class TeamDataFragment extends Fragment {

    private static final int SET_VIEW = 0x01;


    /**
     * 记录球队具体信息
     */
    private Map<String ,String> infoMap = new HashMap<>();


    private int id;
    private Context mContext;
    private Map<String, String> teamInfo;
    private View fView;

    private TextView tv_league;
    private TextView tv_conference;
    private TextView tv_city;
    private TextView tv_founded;
    private TextView tv_court;
    private TextView tv_champions;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teamdata,container,false);

        fView = view;

        initView();

        parseIntent();

        getData.start();

        return view;

    }

    private void initView() {
        mContext = this.getActivity();

        tv_league = (TextView) fView.findViewById(R.id.tv_league);
        tv_conference = (TextView) fView.findViewById(R.id.tv_conference);
        tv_city = (TextView) fView.findViewById(R.id.tv_city);
        tv_founded = (TextView) fView.findViewById(R.id.tv_founded);
        tv_court = (TextView) fView.findViewById(R.id.tv_court);
        tv_champions = (TextView) fView.findViewById(R.id.tv_champions);
    }

    /**
     * 接收Activity传递的参数
     */
    private void parseIntent(){
        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");

    }

    /**
     * 获取数据
     */
    Thread getData = new Thread(new Runnable() {
        @Override
        public void run() {
//            infoMap = getTeamInfo();

            //测试数据
            test();

            handler.sendEmptyMessage(SET_VIEW);
        }
    });

    private void test() {
        Map<String,String> map = new HashMap<>();

        map.put("city","新奥尔良");
        map.put("league","西部");
        map.put("conference","西南区");
        map.put("court","新奥尔良球馆");
        map.put("startYearInNBA", "1988");
        map.put("numOfChampions", "0");

        infoMap = map;
    }

    /**
     * Handler
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SET_VIEW:
                    setView();
            }
        }
    };

    private void setView(){
        tv_league.setText("联盟 ： " + infoMap.get("league"));
        tv_conference.setText("分区 ： " + infoMap.get("conference"));
        tv_city.setText("城市 ： " +infoMap.get("city"));
        tv_founded.setText("成立时间 ： " + infoMap.get("startYearInNBA"));
        tv_court.setText("球馆 ： " + infoMap.get("court"));
        tv_champions.setText("夺冠次数 ： " + infoMap.get("numOfChampions"));

    }


    /**
     * 获取球队具体信息
     *
     * @return
     */
    private Map<String,String> getTeamInfo() {
        Map<String,String> map = new HashMap<>();

        String jsonString = ACache.get(mContext).getAsString("getTeamInfos" + id);



        if (jsonString == null){

            //从server获取数据

            jsonString = "test";
//            jsonString= GetHttpResponse.getHttpResponse(GetHttpResponse.getTeamInfos + "?teamId=" + id);

            ACache.get(mContext).put("getTeamInfos" + id ,jsonString,ACache.TIME_HOUR);

            Log.i("Resource", "Internet");
        }
        else
        {
            Log.i("Resource","Cache");
        }

        //解析jsonString 构造Map

        JSONObject obj = null;
        try {
            obj = new JSONObject(jsonString);

            map.put("city",obj.getString("city"));
            map.put("league",obj.getString("league"));
            map.put("conference",obj.getString("conference"));
            map.put("court",obj.getString("court"));
            map.put("startYearInNBA", String.valueOf(obj.getInt("startYearInNBA")));
            map.put("numOfChampions", String.valueOf(obj.getInt("numOfChampions")));



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return map;
    }

}
