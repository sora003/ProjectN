package com.sora.projectn.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sora.projectn.Fragment.TeamDataFragment;
import com.sora.projectn.Fragment.TeamPlayerFragment;
import com.sora.projectn.Fragment.TeamScheduleFragment;
import com.sora.projectn.Fragment.TeamSeasonFragment;
import com.sora.projectn.R;
import com.sora.projectn.businesslogic.TeamBL;
import com.sora.projectn.businesslogicservice.TeamBLS;
import com.sora.projectn.utils.FragAdapter;
import com.sora.projectn.vo.TeamInfoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/1/26.
 */
public class TeamActivity extends FragmentActivity {

    private static final int GET_DATA = 0x001;
    private String abbr;
    private Context mContext;
    private TeamBLS BLS = new TeamBL();
    private TeamInfoVo teamInfoVo = new TeamInfoVo();


    //ViewPager
    private ViewPager viewPager;


    //TextView
    private TextView tv_teamGuide1;
    private TextView tv_teamGuide2;
    private TextView tv_teamGuide3;
    private TextView tv_teamGuide4;
    private TextView tv_teamName;
    private TextView tv_season;
    private TextView tv_winlose;
    private TextView tv_rank;

    //ImageView
    private ImageView cursor;
    private ImageView iv_teamLogo;

    private List<Fragment> fragments;

    private FragAdapter adapter;

    //游标宽度
    private int bmpw = 0;
    //动画图片偏移量 滑块占据一个标签栏 offset设置为0
    private int offset = 0;
    //当前页卡编号  初始编号为0
    private int currIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        parseIntent();
        initView();
        initViewPager();
        initListener();

        //获取球队基本信息
        getData.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA:
                    setTopView();
                    break;
            }
        }
    };

    //设置顶层视图
    private void setTopView() {
        iv_teamLogo.setImageBitmap(teamInfoVo.getBmp());
        tv_teamName.setText(teamInfoVo.getName());
        tv_season.setText(teamInfoVo.getSeason());
        tv_winlose.setText(teamInfoVo.getWin_lose());
        tv_rank.setText(teamInfoVo.getRank());
    }

    //获取球队基本信息
    Thread getData = new Thread(new Runnable() {
        @Override
        public void run() {
            teamInfoVo = BLS.getTeamInfo(mContext,abbr);
            handler.sendEmptyMessage(GET_DATA);
        }
    });

    /**
     * 获取Intent传递来的abbr值
     */
    private void parseIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        abbr = bundle.getString("abbr");
    }

    /**
     * 初始化View
     */
    private void initView() {

        mContext = this;

        //TextView
        tv_teamGuide1 = (TextView) findViewById(R.id.tv_teamGuide1);
        tv_teamGuide2 = (TextView) findViewById(R.id.tv_teamGuide2);
        tv_teamGuide3 = (TextView) findViewById(R.id.tv_teamGuide3);
        tv_teamGuide4 = (TextView) findViewById(R.id.tv_teamGuide4);
        tv_teamName = (TextView) findViewById(R.id.tv_teamName);
        tv_season = (TextView) findViewById(R.id.tv_season);
        tv_winlose = (TextView) findViewById(R.id.tv_winlose);
        tv_rank = (TextView) findViewById(R.id.tv_rank);


        //ImageView
        cursor = (ImageView) findViewById(R.id.iv_teamCursor);
        iv_teamLogo = (ImageView) findViewById(R.id.iv_teamLogo);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        fragments = new ArrayList<Fragment>();
        fragments.add(new TeamDataFragment());
        fragments.add(new TeamSeasonFragment());
        fragments.add(new TeamPlayerFragment());
        fragments.add(new TeamScheduleFragment());

        //新建适配器对象
        adapter = new FragAdapter(getSupportFragmentManager(),fragments);


        //设定适配器
        viewPager.setAdapter(adapter);

        //初始化指示器位置
        initCursorPos();

        //初始化ViewPager在第一个模块
        viewPager.setCurrentItem(0);
        //对应的标签栏高亮显示
        tv_teamGuide1.setAlpha(1);

    }


    private void initCursorPos() {
        //初始化游标宽度为四分之一屏幕宽度
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        bmpw = width/4;

        ViewGroup.LayoutParams params = cursor.getLayoutParams();

        //动态设置cursor的宽度
        params.width = bmpw;

    }


    /**
     * 设置监听
     */
    private void initListener() {
        //标签栏的监听
        tv_teamGuide1.setOnClickListener(new MyOnClickListener(){
            @Override
            public void onClick(View v) {
                super.onClick(v);
            }
        });
        tv_teamGuide2.setOnClickListener(new MyOnClickListener(){
            @Override
            public void onClick(View v) {
                super.onClick(v);
            }
        });
        tv_teamGuide3.setOnClickListener(new MyOnClickListener(){
            @Override
            public void onClick(View v) {
                super.onClick(v);
            }
        });
        tv_teamGuide4.setOnClickListener(new MyOnClickListener(){
            @Override
            public void onClick(View v) {
                super.onClick(v);
            }
        });

        //ViewPager的监听  解决用户通过手势滑动获取新的模块 此时标签栏没有同步改变的问题
        //同时完成滑块滑动的动画
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());


    }



    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int current = viewPager.getCurrentItem();
            //跳转到新的fragment
            switch (v.getId()){
                case R.id.tv_teamGuide1:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.tv_teamGuide2:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.tv_teamGuide3:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.tv_teamGuide4:
                    viewPager.setCurrentItem(3);
                    break;
            }

        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //相邻页面的偏移量
        private int one = bmpw;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //即将显示的页卡的index
        @Override
        public void onPageSelected(int position) {
            //初始化移动的动画 (从当前位置 平移到即将摇到的位置)
            Animation animation = new TranslateAnimation(currIndex * one , position * one , 0 , 0);
            //动画终止时停留在最后一帧  不然会回到没有执行前的状态
            animation.setFillAfter(true);
            //动画持续时间0.2秒
            animation.setDuration(200);
            //用游标来显示动画
            cursor.startAnimation(animation);

            //取消之前选中的标签的高亮显示
            switch (currIndex){
                case 0:
                    tv_teamGuide1.setAlpha((float) 0.5);
                    break;
                case 1:
                    tv_teamGuide2.setAlpha((float) 0.5);
                    break;
                case 2:
                    tv_teamGuide3.setAlpha((float) 0.5);
                    break;
                case 3:
                    tv_teamGuide4.setAlpha((float) 0.5);
                    break;
            }
            //将即将切换到的标签的高亮显示
            switch (position){
                case 0:
                    tv_teamGuide1.setAlpha(1);
                    break;
                case 1:
                    tv_teamGuide2.setAlpha(1);
                    break;
                case 2:
                    tv_teamGuide3.setAlpha(1);
                    break;
                case 3:
                    tv_teamGuide4.setAlpha(1);
                    break;
            }

            //当前页卡发生改变 重定义currIndex
            currIndex = position;
        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
