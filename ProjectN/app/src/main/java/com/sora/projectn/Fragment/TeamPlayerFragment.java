package com.sora.projectn.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.sora.projectn.R;
import com.sora.projectn.businesslogic.PlayerBL;
import com.sora.projectn.businesslogicservice.PlayerBLS;
import com.sora.projectn.utils.TeamPlayerAdapter;
import com.sora.projectn.utils.TeamSeasonAdapter;
import com.sora.projectn.vo.TeamPlayerVo;

import java.util.List;

/**
 * Created by Sora on 2016/1/28.
 */
public class TeamPlayerFragment extends Fragment {

    private static final int GET_DATA = 0x01;
    private Context mContext;
    private ListView listView;
    private String abbr;
    private PlayerBLS BLS = new PlayerBL();
    private List<TeamPlayerVo> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teamplayer,container,false);




        //获取上下文环境
        mContext = this.getActivity();

        listView = (ListView) view.findViewById(R.id.lv_teamPlayer);

        parseIntent();

        getData.start();


        return view;

    }


    /**
     * 接收Activity传递的参数
     */
    private void parseIntent(){
        Bundle bundle = this.getArguments();
        abbr = bundle.getString("abbr");
    }

    /**
     * 获取数据
     */
    Thread getData = new Thread(new Runnable() {
        @Override
        public void run() {
            list = BLS.getPlayer(mContext, abbr);
            handler.sendEmptyMessage(GET_DATA);
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
                case GET_DATA:
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
}