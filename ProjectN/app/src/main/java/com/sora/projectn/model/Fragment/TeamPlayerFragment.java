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
import android.widget.ListView;

import com.sora.projectn.R;
import com.sora.projectn.utils.ACache;
import com.sora.projectn.utils.GetHttpResponse;
import com.sora.projectn.utils.TeamPlayerAdapter;
import com.sora.projectn.utils.beans.TeamPlayerVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/1/28.
 */
public class TeamPlayerFragment extends Fragment {

    private static final int SET_VIEW = 0x01;

    private Context mContext;


    private int id;
    private List<TeamPlayerVo> list = new ArrayList<>();

    private ListView listView;
    private View  fView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teamplayer, container, false);

        fView = view;

        initView();

        parseIntent();

        getData.start();


        return view;

    }

    /**
     * 初始化
     */
    private void initView() {
        //获取上下文环境
        mContext = this.getActivity();

        listView = (ListView) fView.findViewById(R.id.lv_teamPlayer);
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
            list = getTeamPlayer();

            handler.sendEmptyMessage(SET_VIEW);
        }
    });

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

    /**
     * 更新界面
     */
    private void setView() {
        TeamPlayerAdapter adapter = new TeamPlayerAdapter(mContext,list);

        listView.setAdapter(adapter);

    }

    public List<TeamPlayerVo> getTeamPlayer() {
        List<TeamPlayerVo> list = new ArrayList<>();

        String jsonString = ACache.get(mContext).getAsString("c" + id);

        if (jsonString == null){

            //从server获取数据

            jsonString = "test";
//            jsonString= GetHttpResponse.getHttpResponse(GetHttpResponse.getTeamPlayerList + "?teamId=" + id);

            ACache.get(mContext).put("getTeamPlayerList" + id ,jsonString);

            Log.i("Resource", "Internet");
        }
        else
        {
            Log.i("Resource","Cache");
        }

        //解析jsonString 构造Map

        try {
            JSONArray array = new JSONArray(jsonString);
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject obj = array.getJSONObject(i);
//                String sName = obj.getString("sName");
//                int id = obj.getInt("id");
//
//                map.put(sName, id);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }
}