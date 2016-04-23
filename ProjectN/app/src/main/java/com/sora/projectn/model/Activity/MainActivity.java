package com.sora.projectn.model.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sora.projectn.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ListView lv_left_menu;
    private Context mContext;




    //ActionBarDrawerToggle控件
    private ActionBarDrawerToggle mDrawerToggle;
    //ActionBarDrawerToggle控件内的内容
    private String[] data = {"球队数据","球员数据","比赛数据","排行榜"};
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_ActionBarDrawerToggle();

        lv_left_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(mContext,TeamListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext,PlayerListActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext,MatchListActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext,RankActivity.class));
                        break;
                }
            }
        });
    }



    private void init_ActionBarDrawerToggle() {
        mContext = this;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lv_left_menu = (ListView) findViewById(R.id.lv_left_menu);


        //设置Toolbar标题
        toolbar.setTitle("NBA");
        //设置标题颜色
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        setSupportActionBar(toolbar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        //决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置抽屉内的内容
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        lv_left_menu.setAdapter(arrayAdapter);

    }
}
