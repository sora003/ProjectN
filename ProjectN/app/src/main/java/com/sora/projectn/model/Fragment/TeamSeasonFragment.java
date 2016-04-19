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
import android.widget.GridView;

import com.sora.projectn.R;
import com.sora.projectn.gc.model.businesslogic.TeamBL;
import com.sora.projectn.gc.model.businesslogicservice.TeamBLS;
import com.sora.projectn.utils.TeamSeasonAdapter;
import com.sora.projectn.utils.beans.TeamSeasonInfoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/1/28.
 */
public class TeamSeasonFragment extends Fragment {


    private static final int GET_DATA = 0x01;



    private TeamBLS BLS = new TeamBL();
    private List<TeamSeasonInfoVo> list = new ArrayList<>();
    private String abbr;
    private Context mContext;


    private GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teamseason,container,false);

        //获取上下文环境
        mContext = this.getActivity();

        gridView = (GridView) view.findViewById(R.id.gv_teamSeason);

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
        list = BLS.getTeamSeasonTotal(mContext,abbr);
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
        TeamSeasonAdapter adapter = new TeamSeasonAdapter(mContext,list);

        gridView.setAdapter(adapter);
    }
}
