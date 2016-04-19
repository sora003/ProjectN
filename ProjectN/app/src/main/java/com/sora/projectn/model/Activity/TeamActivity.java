package com.sora.projectn.model.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sora.projectn.model.Fragment.TeamDataFragment;
import com.sora.projectn.model.Fragment.TeamPlayerFragment;
import com.sora.projectn.model.Fragment.TeamScheduleFragment;
import com.sora.projectn.model.Fragment.TeamSeasonFragment;
import com.sora.projectn.R;
import com.sora.projectn.utils.ACache;
import com.sora.projectn.utils.BitmapHelper;
import com.sora.projectn.utils.FragAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/26.
 */
public class TeamActivity extends FragmentActivity {

    private static final int SET_VIEW = 0x001;


    private String sName;
    private Context mContext;
    private int id;
    private Map<String,String> infoMap = new HashMap<>();

    //Fragment
    private TeamDataFragment teamDataFragment;
    private TeamSeasonFragment teamSeasonFragment;
    private TeamPlayerFragment teamPlayerFragment;
    private TeamScheduleFragment teamScheduleFragment;

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
    private Map<String, String> teamInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        initView();

        parseIntent();

        initViewPager();
        initListener();

        //获取球队基本信息
        getData.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SET_VIEW:
                    setTopView();
                    break;
            }
        }
    };

    //设置顶层视图
    private void setTopView() {
        iv_teamLogo.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(sName, mContext)));
        tv_teamName.setText(infoMap.get("name"));
//        tv_season.setText(teamInfoVo.getSeason());
//        tv_winlose.setText(teamInfoVo.getWin_lose());
//        tv_rank.setText(teamInfoVo.getRank());
    }

    //获取球队基本信息
    Thread getData = new Thread(new Runnable() {
        @Override
        public void run() {
            infoMap = getTeamInfo();
            handler.sendEmptyMessage(SET_VIEW);
        }
    });

    /**
     * 获取Intent传递来的abbr值
     */
    private void parseIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sName = bundle.getString("sName");
        id = bundle.getInt("id");

        //需要统一Fragment实例化对象 采用全局变量定义
        bundle = new Bundle();
        bundle.putInt("id", id);

        teamDataFragment.setArguments(bundle);

        teamSeasonFragment.setArguments(bundle);

        teamPlayerFragment.setArguments(bundle);

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


        //Fragment
        teamDataFragment = new TeamDataFragment();
        teamSeasonFragment = new TeamSeasonFragment();
        teamPlayerFragment = new TeamPlayerFragment();
        teamScheduleFragment = new TeamScheduleFragment();

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        fragments = new ArrayList<Fragment>();
        fragments.add(teamDataFragment);
        fragments.add(teamSeasonFragment);
        fragments.add(teamPlayerFragment);
        fragments.add(teamScheduleFragment);

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

    public Map<String, String> getTeamInfo() {
        Map<String ,String> map = new HashMap<>();

        String jsonString = ACache.get(mContext).getAsString("getTeams");


        /**
         * 
         */

        if (jsonString == null){
            //从server获取数据
            jsonString = "[{\"id\":1,\"name\":\"圣安东尼奥马刺队\",\"abbr\":\"sas\",\"city\":\"圣安东尼奥\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"马刺\",\"founded\":1976},{\"id\":2,\"name\":\"孟菲斯灰熊队\",\"abbr\":\"mem\",\"city\":\"孟菲斯\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"灰熊\",\"founded\":1995},{\"id\":3,\"name\":\"达拉斯小牛队\",\"abbr\":\"dal\",\"city\":\"达拉斯\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"小牛\",\"founded\":1980},{\"id\":4,\"name\":\"休斯顿火箭队\",\"abbr\":\"hou\",\"city\":\"休斯顿\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"火箭\",\"founded\":1967},{\"id\":5,\"name\":\"新奥尔良鹈鹕队\",\"abbr\":\"noh\",\"city\":\"新奥尔良\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"鹈鹕\",\"founded\":1988},{\"id\":6,\"name\":\"明尼苏达森林狼队\",\"abbr\":\"min\",\"city\":\"明尼苏达\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"森林狼\",\"founded\":1989},{\"id\":7,\"name\":\"丹佛掘金队\",\"abbr\":\"den\",\"city\":\"丹佛\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"掘金\",\"founded\":1976},{\"id\":8,\"name\":\"犹他爵士队\",\"abbr\":\"UTAH\",\"city\":\"犹他\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"爵士\",\"founded\":1974},{\"id\":9,\"name\":\"波特兰开拓者队\",\"abbr\":\"por\",\"city\":\"波特兰\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"开拓者\",\"founded\":1970},{\"id\":10,\"name\":\"俄克拉荷马雷霆队\",\"abbr\":\"okc\",\"city\":\"俄克拉荷马城\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"雷霆\",\"founded\":1967},{\"id\":11,\"name\":\"萨克拉门托国王队\",\"abbr\":\"sac\",\"city\":\"萨克拉门托\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"国王\",\"founded\":1948},{\"id\":12,\"name\":\"菲尼克斯太阳队\",\"abbr\":\"pho\",\"city\":\"菲尼克斯\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"太阳\",\"founded\":1968},{\"id\":13,\"name\":\"洛杉矶湖人队\",\"abbr\":\"lal\",\"city\":\"洛杉矶\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"湖人\",\"founded\":1948},{\"id\":14,\"name\":\"洛杉矶快船队\",\"abbr\":\"lac\",\"city\":\"洛杉矶\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"快船\",\"founded\":1970},{\"id\":15,\"name\":\"金州勇士队\",\"abbr\":\"GS\",\"city\":\"金州\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"勇士\",\"founded\":1946},{\"id\":16,\"name\":\"迈阿密热队\",\"abbr\":\"mia\",\"city\":\"迈阿密\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"热火\",\"founded\":1988},{\"id\":17,\"name\":\"奥兰多魔术队\",\"abbr\":\"orl\",\"city\":\"奥兰多\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"魔术\",\"founded\":1989},{\"id\":18,\"name\":\"亚特兰大老鹰队\",\"abbr\":\"atl\",\"city\":\"亚特兰大\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"老鹰\",\"founded\":1949},{\"id\":19,\"name\":\"华盛顿奇才队\",\"abbr\":\"was\",\"city\":\"华盛顿\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"奇才\",\"founded\":1961},{\"id\":20,\"name\":\"夏洛特黄蜂队\",\"abbr\":\"cha\",\"city\":\"夏洛特\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"黄蜂\",\"founded\":2004},{\"id\":21,\"name\":\"底特律活塞队\",\"abbr\":\"det\",\"city\":\"底特律\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"活塞\",\"founded\":1948},{\"id\":22,\"name\":\"印第安纳步行者队\",\"abbr\":\"ind\",\"city\":\"印第安纳\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"步行者\",\"founded\":1976},{\"id\":23,\"name\":\"克利夫兰骑士队\",\"abbr\":\"cle\",\"city\":\"克利夫兰\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"骑士\",\"founded\":1970},{\"id\":24,\"name\":\"芝加哥公牛队\",\"abbr\":\"chi\",\"city\":\"芝加哥\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"公牛\",\"founded\":1966},{\"id\":25,\"name\":\"密尔沃基雄鹿队\",\"abbr\":\"mil\",\"city\":\"密尔沃基\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"雄鹿\",\"founded\":1968},{\"id\":26,\"name\":\"波士顿凯尔特人队\",\"abbr\":\"bos\",\"city\":\"波士顿\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"凯尔特人\",\"founded\":1946},{\"id\":27,\"name\":\"费城76人队\",\"abbr\":\"phi\",\"city\":\"费城\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"76人\",\"founded\":1947},{\"id\":28,\"name\":\"纽约尼克斯队\",\"abbr\":\"nyk\",\"city\":\"纽约\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"尼克斯\",\"founded\":1946},{\"id\":29,\"name\":\"布鲁克林篮网队\",\"abbr\":\"BKN\",\"city\":\"布鲁克林\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"篮网\",\"founded\":1967},{\"id\":30,\"name\":\"多伦多猛龙队\",\"abbr\":\"tor\",\"city\":\"多伦多\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"猛龙\",\"founded\":1995}]";

//            jsonString= GetHttpResponse.getHttpResponse(GetHttpResponse.getTeams);

            ACache.get(mContext).put("getTeams",jsonString,ACache.TIME_DAY);
            Log.i("Resource", "Internet");
        }
        else
        {
            Log.i("Resource","Cache");
        }

        //解析jsonString 构造Map
        try {
            JSONArray array = new JSONArray(jsonString);

            JSONObject obj = array.getJSONObject(id);
            String name = obj.getString("name");

            map.put("name",name);




        } catch (JSONException e) {
            e.printStackTrace();
        }


        return map;
    }


    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
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
