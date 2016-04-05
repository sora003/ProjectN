package com.sora.projectn.logic.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;

import com.sora.projectn.R;
import com.sora.projectn.model.businesslogic.MatchBL;
import com.sora.projectn.model.businesslogicservice.MatchBLS;
import com.sora.projectn.gc.dataservice.MatchDS;
import com.sora.projectn.gc.dataservice.impl.MatchDSImpl;
import com.sora.projectn.gc.po.MatchInfoPo;
import com.sora.projectn.utils.MatchListAdapter;
import com.sora.projectn.model.vo.MatchInfoVo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Sora on 2016/2/7.
 */
public class MatchListActivity extends AppCompatActivity {

    private static final int GET_DATA = 0x01;
    private static final int SET_VIEW = 0x02;
    private static final String TITLE = "比赛列表";

    private Context mContext;

    private DatePicker datePicker;
    private ListView listView;
    private Toolbar toolbar;


    //记录用户选择的最新日期
    private int year;
    private int month;
    private int day;

    //记录比赛编号
    private int no;

    /**
     * 数据层接口
     */
    private MatchDS DS = new MatchDSImpl();
    /**
     * 业务逻辑层接口
     */
    private MatchBLS BLS = new MatchBL();

    /**
     * 日比赛表
     */
    private List<MatchInfoVo> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchlist);

        initView();

        initListener();



    }

    /**
     * 初始化View
     */
    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //设置Toolbar标题
        toolbar.setTitle(TITLE);
        //设置标题颜色
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        setSupportActionBar(toolbar);


        mContext = this;

        datePicker = (DatePicker) findViewById(R.id.dp);
        listView = (ListView) findViewById(R.id.lv_matchlist);
    }

    /**
     * 监听器
     */
    private void initListener() {
        Calendar c = Calendar.getInstance();
        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MatchListActivity.this.year = year;
                MatchListActivity.this.month = monthOfYear;
                MatchListActivity.this.day = dayOfMonth;

                handler.sendEmptyMessage(GET_DATA);


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取比赛序号
                no = list.get(position).getId();
                //跳转显示比赛详细信息
                Bundle bundle = new Bundle();
                bundle.putInt("no" , no);

                Intent intent = new Intent(mContext,MatchActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
    }


    /**
     * 获取数据
     */
    private void getData(){
        Calendar c = new GregorianCalendar(year , month , day);
        Date day = c.getTime();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String date = df.format(day);

        List<MatchInfoPo> poList = DS.getDateMatchList(mContext,date);

        list = BLS.getMatchInfoVo(mContext,poList);

        for (MatchInfoVo vo : list){
            Log.i("id", String.valueOf(vo.getId()));
            Log.i("team", String.valueOf(vo.getTeamAName())+"   "+String.valueOf(vo.getTeamBName()));
            Log.i("score", String.valueOf(vo.getScoreA())+"   "+String.valueOf(vo.getScoreB()));

        }


        handler.sendEmptyMessage(SET_VIEW);
    }


    /**
     * Handler
     */
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
        MatchListAdapter adapter = new MatchListAdapter(mContext,list);

        //为listView 设置适配器
        listView.setAdapter(adapter);

    }


}
