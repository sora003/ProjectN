package com.sora.projectn.model.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.sora.projectn.model.Fragment.MatchListFragment;


public class MainActivity extends AppCompatActivity implements MatchListFragment.OnFragmentInteractionListener{

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ListView lv_left_menu;
    private Context mContext;





    //ActionBarDrawerToggle控件
    private ActionBarDrawerToggle mDrawerToggle;
    //ActionBarDrawerToggle控件内的内容

    private String[] data = {"我关注的球队","球队","球员检索","历史比赛数据","设置","排行榜(测试)","球探功能(测试)","教练球队数据对比功能测试"};

    private ArrayAdapter arrayAdapter;


    private Intent intent;
    private Bundle bundle;


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
//                        startActivity(new Intent(mContext,TeamListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext,TeamListActivity.class));
                        break;
                    case 2:
//                        startActivity(new Intent(mContext,MatchListActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext,MatchSearchActivity.class));
                        break;
                    case 4:
                        intent = new Intent(mContext,WelcomeActivity.class);
                        bundle = new Bundle();
                        bundle.putInt("MainActivity",1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 5:
                        startActivity(new Intent(mContext,RankActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(mContext,SearchPlayerActivity.class));
                        break;
                    case 7:
                        bundle = new Bundle();
                        bundle.putInt("teamId", 1);
                        intent = new Intent(mContext,TeamCombatListActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
