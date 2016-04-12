package com.sora.projectn.model.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sora.projectn.R;

/**
 * Created by Sora on 2016/1/28.
 */
public class TeamDataFragment extends Fragment {

    private static final int GET_DATA = 0x01;
    String abbr;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teamdata,container,false);


        mContext = this.getActivity();

        parseIntent();



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
//            list = BLS.getTeamSeasonTotal(mContext,abbr);
//            handler.sendEmptyMessage(GET_DATA);
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

    private void setView(){

    }

}
