package com.sora.projectn.model.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.sora.projectn.R;
import com.sora.projectn.gc.model.businesslogic.PlayerBL;
import com.sora.projectn.gc.model.businesslogicservice.PlayerBLS;
import com.sora.projectn.utils.PlayerListAdapter;
import com.sora.projectn.gc.model.vo.AllPlayerInfoVo;
import com.sora.projectn.gc.model.vo.SearchPlayerKeysVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/2/19.
 */
public class PlayerListActivity extends AppCompatActivity{

    private static final int SET_VIEW = 0x01;
    private static final int GET_DATA = 0x02;

    private Spinner spinner_playerLeague;
    private Spinner spinner_playerPosition;
    private Spinner spinner_playerAge;
    private Spinner spinner_playerKey;
    private Context mContext;

    private String league = "all";
    private String pos = "all";
    private int age = 0;
    private int sortKey = 0;

    private SearchPlayerKeysVo searchPlayerKeysVo = new SearchPlayerKeysVo(age,league,pos,sortKey);

    private List<AllPlayerInfoVo> list = new ArrayList<>();

    private PlayerBLS BLS = new PlayerBL();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playerlist);
        initView();

        initListener();
    }



    private void initView() {

        listView = (ListView) findViewById(R.id.lv_playerList);

        mContext = this;

        spinner_playerLeague = (Spinner) findViewById(R.id.spinner_playerLeague);
        spinner_playerPosition = (Spinner) findViewById(R.id.spinner_playerPosition);
        spinner_playerAge = (Spinner) findViewById(R.id.spinner_playerAge);
        spinner_playerKey = (Spinner) findViewById(R.id.spinner_playerKey);


        //spinner_playerLeague
        List<String> list = new ArrayList<>();
        list.add("所有");
        list.add("西部联盟");
        list.add("东部联盟");
        //新建ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,list);
        //adapter设置下拉列表
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner加载适配器
        spinner_playerLeague.setAdapter(adapter);

        //spinner_playerPosition
        list = new ArrayList<>();
        list.add("所有位置");
        list.add("前锋");
        list.add("中锋");
        list.add("后卫");
        //新建ArrayAdapter
        adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,list);
        //adapter设置下拉列表
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner加载适配器
        spinner_playerPosition.setAdapter(adapter);

        //spinner_playerAge
        list = new ArrayList<>();
        list.add("22以下");
        list.add("22-25");
        list.add("25-30");
        list.add("30以上");
        //新建ArrayAdapter
        adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,list);
        //adapter设置下拉列表
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner加载适配器
        spinner_playerAge.setAdapter(adapter);

        //spinner_playerKey
        list = new ArrayList<>();
        list.add("得分");
        list.add("篮板");
        list.add("助攻");
        list.add("得分/篮板/助攻");
        //新建ArrayAdapter
        adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,list);
        //adapter设置下拉列表
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner加载适配器
        spinner_playerKey.setAdapter(adapter);
    }


    private void initListener() {
        spinner_playerLeague.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = league;
                switch (position) {
                    case 0:
                        league = "all";
                        break;
                    case 1:
                        league = "west";
                        break;
                    case 2:
                        league = "east";
                        break;
                }
                if (!item.equals(league)){
                    searchPlayerKeysVo = new SearchPlayerKeysVo(age,league,pos,sortKey);
                    handler.sendEmptyMessage(GET_DATA);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_playerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = pos;
                switch (position){
                    case 0:
                        pos= "all";
                        break;
                    case 1:
                        pos= "f";
                        break;
                    case 2:
                        pos= "c";
                        break;
                    case 3:
                        pos= "g";
                        break;
                }
                if (!item.equals(pos)){
                    searchPlayerKeysVo = new SearchPlayerKeysVo(age,league,pos,sortKey);
                    handler.sendEmptyMessage(GET_DATA);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_playerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(age == position)){
                    age = position;
                    searchPlayerKeysVo = new SearchPlayerKeysVo(age,league,pos,sortKey);
                    handler.sendEmptyMessage(GET_DATA);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_playerKey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //初始化需要至少一次获取数据
                sortKey = position;
                searchPlayerKeysVo = new SearchPlayerKeysVo(age,league,pos,sortKey);
                handler.sendEmptyMessage(GET_DATA);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 获取数据
     */
    private void getData(){
        //TODO 死循环了
        Log.i("查询条件", league+"  "+pos+"  "+age+"  "+sortKey);
        list = BLS.getAllPlayerInfo(searchPlayerKeysVo,mContext);



        for (AllPlayerInfoVo vo : list){
            Log.i("id", String.valueOf(vo.getName()));

        }


        handler.sendEmptyMessage(SET_VIEW);
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_DATA:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getData();
                        }
                    }).start();
                    break;
                case SET_VIEW:
                    setView();
            }
        }
    };

    /**
     * 设置视图 载入适配器
     */
    private void setView() {
        //新建适配器
        PlayerListAdapter adapter = new PlayerListAdapter(mContext,list);

        //为listView 设置适配器
        listView.setAdapter(adapter);

    }
}
