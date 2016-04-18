package com.sora.projectn.model.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sora.projectn.R;
import com.sora.projectn.gc.model.businesslogic.TeamBL;
import com.sora.projectn.gc.model.businesslogicservice.TeamBLS;
import com.sora.projectn.gc.dataservice.TeamDS;
import com.sora.projectn.gc.dataservice.impl.TeamDSImpl;
import com.sora.projectn.gc.model.vo.TeamConferenceVo;
import com.sora.projectn.utils.ACache;
import com.sora.projectn.utils.BitmapHelper;
import com.sora.projectn.utils.GetHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/24.
 */
public class TeamListActivity extends AppCompatActivity {



    private static final int SET_VIEW = 0x001;
    private static final int GET_SNAME = 0x002;
    private static final int GET_ABBR = 0x003;
    private static final String TITLE = "   球队列表";

    Map<String, String> map = new HashMap<>();
    private Context mContext;
    private TeamDS DS = new TeamDSImpl();
    private String sName;
    private String abbr;



    //TextView
    private TextView tv_wC1T1;
    private TextView tv_wC1T2;
    private TextView tv_wC1T3;
    private TextView tv_wC1T4;
    private TextView tv_wC1T5;

    private TextView tv_wC2T1;
    private TextView tv_wC2T2;
    private TextView tv_wC2T3;
    private TextView tv_wC2T4;
    private TextView tv_wC2T5;

    private TextView tv_wC3T1;
    private TextView tv_wC3T2;
    private TextView tv_wC3T3;
    private TextView tv_wC3T4;
    private TextView tv_wC3T5;

    private TextView tv_eC1T1;
    private TextView tv_eC1T2;
    private TextView tv_eC1T3;
    private TextView tv_eC1T4;
    private TextView tv_eC1T5;

    private TextView tv_eC2T1;
    private TextView tv_eC2T2;
    private TextView tv_eC2T3;
    private TextView tv_eC2T4;
    private TextView tv_eC2T5;

    private TextView tv_eC3T1;
    private TextView tv_eC3T2;
    private TextView tv_eC3T3;
    private TextView tv_eC3T4;
    private TextView tv_eC3T5;

    //ImageView
    private ImageView iv_wC1T1;
    private ImageView iv_wC1T2;
    private ImageView iv_wC1T3;
    private ImageView iv_wC1T4;
    private ImageView iv_wC1T5;

    private ImageView iv_wC2T1;
    private ImageView iv_wC2T2;
    private ImageView iv_wC2T3;
    private ImageView iv_wC2T4;
    private ImageView iv_wC2T5;

    private ImageView iv_wC3T1;
    private ImageView iv_wC3T2;
    private ImageView iv_wC3T3;
    private ImageView iv_wC3T4;
    private ImageView iv_wC3T5;

    private ImageView iv_eC1T1;
    private ImageView iv_eC1T2;
    private ImageView iv_eC1T3;
    private ImageView iv_eC1T4;
    private ImageView iv_eC1T5;

    private ImageView iv_eC2T1;
    private ImageView iv_eC2T2;
    private ImageView iv_eC2T3;
    private ImageView iv_eC2T4;
    private ImageView iv_eC2T5;

    private ImageView iv_eC3T1;
    private ImageView iv_eC3T2;
    private ImageView iv_eC3T3;
    private ImageView iv_eC3T4;
    private ImageView iv_eC3T5;

    //Layout
    private RelativeLayout wC1T1;
    private RelativeLayout wC1T2;
    private RelativeLayout wC1T3;
    private RelativeLayout wC1T4;
    private RelativeLayout wC1T5;

    private RelativeLayout wC2T1;
    private RelativeLayout wC2T2;
    private RelativeLayout wC2T3;
    private RelativeLayout wC2T4;
    private RelativeLayout wC2T5;

    private RelativeLayout wC3T1;
    private RelativeLayout wC3T2;
    private RelativeLayout wC3T3;
    private RelativeLayout wC3T4;
    private RelativeLayout wC3T5;

    private RelativeLayout eC1T1;
    private RelativeLayout eC1T2;
    private RelativeLayout eC1T3;
    private RelativeLayout eC1T4;
    private RelativeLayout eC1T5;

    private RelativeLayout eC2T1;
    private RelativeLayout eC2T2;
    private RelativeLayout eC2T3;
    private RelativeLayout eC2T4;
    private RelativeLayout eC2T5;

    private RelativeLayout eC3T1;
    private RelativeLayout eC3T2;
    private RelativeLayout eC3T3;
    private RelativeLayout eC3T4;
    private RelativeLayout eC3T5;

    //Toolbar
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamlist);

        initView();
        getData.start();

        initListener();
    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SET_VIEW:
                    //设置View
                    setView();
                    break;
                case GET_SNAME:
                    //根据球队缩略名获取球队缩写信息
                    //该子线程会重复调用  不可以直接定义  必须每次新建
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            abbr = DS.getTeamAbbr(mContext,sName);
                            handler.sendEmptyMessage(GET_ABBR);
                        }
                    }).start();
                    break;
                case GET_ABBR:
                    //绑定abbr参数 启动TeamInfoActivity
                    Intent intent = new Intent(mContext,TeamActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("abbr", abbr);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
        }
    };




    /**
     * 获取球队缩略名和图标信息
     */
    Thread getData = new Thread(new Runnable() {
        @Override
        public void run() {
            //调用TeamBLS接口 获取球队缩略名信息
            map = getTeamList();

            handler.sendEmptyMessage(SET_VIEW);
        }
    });


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

        //TextView
        tv_wC1T1 = (TextView) findViewById(R.id.tv_wC1T1);
        tv_wC1T2 = (TextView) findViewById(R.id.tv_wC1T2);
        tv_wC1T3 = (TextView) findViewById(R.id.tv_wC1T3);
        tv_wC1T4 = (TextView) findViewById(R.id.tv_wC1T4);
        tv_wC1T5 = (TextView) findViewById(R.id.tv_wC1T5);

        tv_wC2T1 = (TextView) findViewById(R.id.tv_wC2T1);
        tv_wC2T2 = (TextView) findViewById(R.id.tv_wC2T2);
        tv_wC2T3 = (TextView) findViewById(R.id.tv_wC2T3);
        tv_wC2T4 = (TextView) findViewById(R.id.tv_wC2T4);
        tv_wC2T5 = (TextView) findViewById(R.id.tv_wC2T5);

        tv_wC3T1 = (TextView) findViewById(R.id.tv_wC3T1);
        tv_wC3T2 = (TextView) findViewById(R.id.tv_wC3T2);
        tv_wC3T3 = (TextView) findViewById(R.id.tv_wC3T3);
        tv_wC3T4 = (TextView) findViewById(R.id.tv_wC3T4);
        tv_wC3T5 = (TextView) findViewById(R.id.tv_wC3T5);

        tv_eC1T1 = (TextView) findViewById(R.id.tv_eC1T1);
        tv_eC1T2 = (TextView) findViewById(R.id.tv_eC1T2);
        tv_eC1T3 = (TextView) findViewById(R.id.tv_eC1T3);
        tv_eC1T4 = (TextView) findViewById(R.id.tv_eC1T4);
        tv_eC1T5 = (TextView) findViewById(R.id.tv_eC1T5);

        tv_eC2T1 = (TextView) findViewById(R.id.tv_eC2T1);
        tv_eC2T2 = (TextView) findViewById(R.id.tv_eC2T2);
        tv_eC2T3 = (TextView) findViewById(R.id.tv_eC2T3);
        tv_eC2T4 = (TextView) findViewById(R.id.tv_eC2T4);
        tv_eC2T5 = (TextView) findViewById(R.id.tv_eC2T5);

        tv_eC3T1 = (TextView) findViewById(R.id.tv_eC3T1);
        tv_eC3T2 = (TextView) findViewById(R.id.tv_eC3T2);
        tv_eC3T3 = (TextView) findViewById(R.id.tv_eC3T3);
        tv_eC3T4 = (TextView) findViewById(R.id.tv_eC3T4);
        tv_eC3T5 = (TextView) findViewById(R.id.tv_eC3T5);

        //ImageView
        iv_wC1T1 = (ImageView) findViewById(R.id.iv_wC1T1);
        iv_wC1T2 = (ImageView) findViewById(R.id.iv_wC1T2);
        iv_wC1T3 = (ImageView) findViewById(R.id.iv_wC1T3);
        iv_wC1T4 = (ImageView) findViewById(R.id.iv_wC1T4);
        iv_wC1T5 = (ImageView) findViewById(R.id.iv_wC1T5);

        iv_wC2T1 = (ImageView) findViewById(R.id.iv_wC2T1);
        iv_wC2T2 = (ImageView) findViewById(R.id.iv_wC2T2);
        iv_wC2T3 = (ImageView) findViewById(R.id.iv_wC2T3);
        iv_wC2T4 = (ImageView) findViewById(R.id.iv_wC2T4);
        iv_wC2T5 = (ImageView) findViewById(R.id.iv_wC2T5);

        iv_wC3T1 = (ImageView) findViewById(R.id.iv_wC3T1);
        iv_wC3T2 = (ImageView) findViewById(R.id.iv_wC3T2);
        iv_wC3T3 = (ImageView) findViewById(R.id.iv_wC3T3);
        iv_wC3T4 = (ImageView) findViewById(R.id.iv_wC3T4);
        iv_wC3T5 = (ImageView) findViewById(R.id.iv_wC3T5);

        iv_eC1T1 = (ImageView) findViewById(R.id.iv_eC1T1);
        iv_eC1T2 = (ImageView) findViewById(R.id.iv_eC1T2);
        iv_eC1T3 = (ImageView) findViewById(R.id.iv_eC1T3);
        iv_eC1T4 = (ImageView) findViewById(R.id.iv_eC1T4);
        iv_eC1T5 = (ImageView) findViewById(R.id.iv_eC1T5);

        iv_eC2T1 = (ImageView) findViewById(R.id.iv_eC2T1);
        iv_eC2T2 = (ImageView) findViewById(R.id.iv_eC2T2);
        iv_eC2T3 = (ImageView) findViewById(R.id.iv_eC2T3);
        iv_eC2T4 = (ImageView) findViewById(R.id.iv_eC2T4);
        iv_eC2T5 = (ImageView) findViewById(R.id.iv_eC2T5);

        iv_eC3T1 = (ImageView) findViewById(R.id.iv_eC3T1);
        iv_eC3T2 = (ImageView) findViewById(R.id.iv_eC3T2);
        iv_eC3T3 = (ImageView) findViewById(R.id.iv_eC3T3);
        iv_eC3T4 = (ImageView) findViewById(R.id.iv_eC3T4);
        iv_eC3T5 = (ImageView) findViewById(R.id.iv_eC3T5);

        //Layout
        wC1T1 = (RelativeLayout) findViewById(R.id.wC1T1);
        wC1T2 = (RelativeLayout) findViewById(R.id.wC1T2);
        wC1T3 = (RelativeLayout) findViewById(R.id.wC1T3);
        wC1T4 = (RelativeLayout) findViewById(R.id.wC1T4);
        wC1T5 = (RelativeLayout) findViewById(R.id.wC1T5);

        wC2T1 = (RelativeLayout) findViewById(R.id.wC2T1);
        wC2T2 = (RelativeLayout) findViewById(R.id.wC2T2);
        wC2T3 = (RelativeLayout) findViewById(R.id.wC2T3);
        wC2T4 = (RelativeLayout) findViewById(R.id.wC2T4);
        wC2T5 = (RelativeLayout) findViewById(R.id.wC2T5);

        wC3T1 = (RelativeLayout) findViewById(R.id.wC3T1);
        wC3T2 = (RelativeLayout) findViewById(R.id.wC3T2);
        wC3T3 = (RelativeLayout) findViewById(R.id.wC3T3);
        wC3T4 = (RelativeLayout) findViewById(R.id.wC3T4);
        wC3T5 = (RelativeLayout) findViewById(R.id.wC3T5);

        eC1T1 = (RelativeLayout) findViewById(R.id.eC1T1);
        eC1T2 = (RelativeLayout) findViewById(R.id.eC1T2);
        eC1T3 = (RelativeLayout) findViewById(R.id.eC1T3);
        eC1T4 = (RelativeLayout) findViewById(R.id.eC1T4);
        eC1T5 = (RelativeLayout) findViewById(R.id.eC1T5);

        eC2T1 = (RelativeLayout) findViewById(R.id.eC2T1);
        eC2T2 = (RelativeLayout) findViewById(R.id.eC2T2);
        eC2T3 = (RelativeLayout) findViewById(R.id.eC2T3);
        eC2T4 = (RelativeLayout) findViewById(R.id.eC2T4);
        eC2T5 = (RelativeLayout) findViewById(R.id.eC2T5);

        eC3T1 = (RelativeLayout) findViewById(R.id.eC3T1);
        eC3T2 = (RelativeLayout) findViewById(R.id.eC3T2);
        eC3T3 = (RelativeLayout) findViewById(R.id.eC3T3);
        eC3T4 = (RelativeLayout) findViewById(R.id.eC3T4);
        eC3T5 = (RelativeLayout) findViewById(R.id.eC3T5);
    }


    /**
     * 设置界面
     */
    private void setView() {

        //Southwest
        List<String> swList =  new ArrayList<String>();
        //Southeast
        List<String> seList =  new ArrayList<String>();
        //Pacific
        List<String> paList =  new ArrayList<String>();
        //Central
        List<String> ceList =  new ArrayList<String>();
        //Northwest
        List<String> nwList =  new ArrayList<String>();
        //Atlantic
        List<String> atList =  new ArrayList<String>();


        //获取map的key 的首个对象
        Iterator iterator = map.keySet().iterator();
        
        while (iterator.hasNext()){
            Object key = iterator.next();
            String sName= key.toString();
            String conference = map.get(key);




            //判断球队所属分区
            switch (conference){
                case "西南区":
                    swList.add(sName);
                    break;
                case "西北区":
                    nwList.add(sName);
                    break;
                case "太平洋区":
                    paList.add(sName);
                    break;
                case "中区":
                    ceList.add(sName);
                    break;
                case "东南区":
                    seList.add(sName);
                    break;
                case "大西洋区":
                    atList.add(sName);
                    break;
            }

        }



        tv_wC1T1.setText(swList.get(0));
        tv_wC1T2.setText(swList.get(1));
        tv_wC1T3.setText(swList.get(2));
        tv_wC1T4.setText(swList.get(3));
        tv_wC1T5.setText(swList.get(4));

        iv_wC1T1.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(swList.get(0), mContext)));
        iv_wC1T2.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(swList.get(1), mContext)));
        iv_wC1T3.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(swList.get(2), mContext)));
        iv_wC1T4.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(swList.get(3), mContext)));
        iv_wC1T5.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(swList.get(4), mContext)));



        tv_wC2T1.setText(paList.get(0));
        tv_wC2T2.setText(paList.get(1));
        tv_wC2T3.setText(paList.get(2));
        tv_wC2T4.setText(paList.get(3));
        tv_wC2T5.setText(paList.get(4));


        iv_wC2T1.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(paList.get(0), mContext)));
        iv_wC2T2.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(paList.get(1), mContext)));
        iv_wC2T3.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(paList.get(2), mContext)));
        iv_wC2T4.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(paList.get(3), mContext)));
        iv_wC2T5.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(paList.get(4), mContext)));



        tv_wC3T1.setText(nwList.get(0));
        tv_wC3T2.setText(nwList.get(1));
        tv_wC3T3.setText(nwList.get(2));
        tv_wC3T4.setText(nwList.get(3));
        tv_wC3T5.setText(nwList.get(4));

        iv_wC3T1.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(nwList.get(0), mContext)));
        iv_wC3T2.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(nwList.get(1), mContext)));
        iv_wC3T3.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(nwList.get(2), mContext)));
        iv_wC3T4.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(nwList.get(3), mContext)));
        iv_wC3T5.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(nwList.get(4), mContext)));




        tv_eC1T1.setText(seList.get(0));
        tv_eC1T2.setText(seList.get(1));
        tv_eC1T3.setText(seList.get(2));
        tv_eC1T4.setText(seList.get(3));
        tv_eC1T5.setText(seList.get(4));

        iv_eC1T1.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(seList.get(0), mContext)));
        iv_eC1T2.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(seList.get(1), mContext)));
        iv_eC1T3.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(seList.get(2), mContext)));
        iv_eC1T4.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(seList.get(3), mContext)));
        iv_eC1T5.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(seList.get(4), mContext)));



        tv_eC2T1.setText(ceList.get(0));
        tv_eC2T2.setText(ceList.get(1));
        tv_eC2T3.setText(ceList.get(2));
        tv_eC2T4.setText(ceList.get(3));
        tv_eC2T5.setText(ceList.get(4));

        iv_eC2T1.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(ceList.get(0), mContext)));
        iv_eC2T2.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(ceList.get(1), mContext)));
        iv_eC2T3.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(ceList.get(2), mContext)));
        iv_eC2T4.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(ceList.get(3), mContext)));
        iv_eC2T5.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(ceList.get(4), mContext)));



        tv_eC3T1.setText(atList.get(0));
        tv_eC3T2.setText(atList.get(1));
        tv_eC3T3.setText(atList.get(2));
        tv_eC3T4.setText(atList.get(3));
        tv_eC3T5.setText(atList.get(4));

        iv_eC3T1.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(atList.get(0), mContext)));
        iv_eC3T2.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(atList.get(1), mContext)));
        iv_eC3T3.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(atList.get(2), mContext)));
        iv_eC3T4.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(atList.get(3), mContext)));
        iv_eC3T5.setImageBitmap(readBitMap(mContext, BitmapHelper.getBitmap(atList.get(4), mContext)));

    
    }
    



    


    /**
     * 监听模块
     */
    private void initListener() {
        wC1T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC1T1.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC1T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC1T2.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC1T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC1T3.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC1T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC1T4.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC1T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC1T5.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });


        wC2T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC2T1.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC2T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC2T2.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC2T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC2T3.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC2T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC2T4.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC2T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC2T5.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });


        wC3T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC3T1.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC3T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC3T2.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC3T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC3T3.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC3T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC3T4.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC3T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC3T5.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });







        eC1T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC1T1.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC1T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC1T2.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC1T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC1T3.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC1T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC1T4.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC1T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC1T5.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });


        eC2T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC2T1.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC2T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC2T2.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC2T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC2T3.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC2T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC2T4.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC2T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC2T5.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });


        eC3T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC3T1.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC3T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC3T2.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC3T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC3T3.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC3T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC3T4.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC3T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC3T5.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param resId
     * @return
     */
    private Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }


    /**
     * 从server获取球队表
     * @return
     */
    private Map<String,String> getTeamList() {
        Map<String,String> map = new HashMap<>();

        String jsonString = ACache.get(mContext).getAsString("getTeams");



        if (jsonString == null){
            //从server获取数据
            jsonString = "[{\"id\":1,\"name\":\"圣安东尼奥马刺队\",\"abbr\":\"sas\",\"city\":\"圣安东尼奥\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"马刺\",\"founded\":1976},{\"id\":2,\"name\":\"孟菲斯灰熊队\",\"abbr\":\"mem\",\"city\":\"孟菲斯\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"灰熊\",\"founded\":1995},{\"id\":3,\"name\":\"达拉斯小牛队\",\"abbr\":\"dal\",\"city\":\"达拉斯\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"小牛\",\"founded\":1980},{\"id\":4,\"name\":\"休斯顿火箭队\",\"abbr\":\"hou\",\"city\":\"休斯顿\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"火箭\",\"founded\":1967},{\"id\":5,\"name\":\"新奥尔良鹈鹕队\",\"abbr\":\"noh\",\"city\":\"新奥尔良\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":1,\"name\":\"西南区\"},\"sName\":\"鹈鹕\",\"founded\":1988},{\"id\":6,\"name\":\"明尼苏达森林狼队\",\"abbr\":\"min\",\"city\":\"明尼苏达\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"森林狼\",\"founded\":1989},{\"id\":7,\"name\":\"丹佛掘金队\",\"abbr\":\"den\",\"city\":\"丹佛\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"掘金\",\"founded\":1976},{\"id\":8,\"name\":\"犹他爵士队\",\"abbr\":\"UTAH\",\"city\":\"犹他\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"爵士\",\"founded\":1974},{\"id\":9,\"name\":\"波特兰开拓者队\",\"abbr\":\"por\",\"city\":\"波特兰\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"开拓者\",\"founded\":1970},{\"id\":10,\"name\":\"俄克拉荷马雷霆队\",\"abbr\":\"okc\",\"city\":\"俄克拉荷马城\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":2,\"name\":\"西北区\"},\"sName\":\"雷霆\",\"founded\":1967},{\"id\":11,\"name\":\"萨克拉门托国王队\",\"abbr\":\"sac\",\"city\":\"萨克拉门托\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"国王\",\"founded\":1948},{\"id\":12,\"name\":\"菲尼克斯太阳队\",\"abbr\":\"pho\",\"city\":\"菲尼克斯\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"太阳\",\"founded\":1968},{\"id\":13,\"name\":\"洛杉矶湖人队\",\"abbr\":\"lal\",\"city\":\"洛杉矶\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"湖人\",\"founded\":1948},{\"id\":14,\"name\":\"洛杉矶快船队\",\"abbr\":\"lac\",\"city\":\"洛杉矶\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"快船\",\"founded\":1970},{\"id\":15,\"name\":\"金州勇士队\",\"abbr\":\"GS\",\"city\":\"金州\",\"league\":{\"id\":1,\"name\":\"西部\"},\"conference\":{\"id\":3,\"name\":\"太平洋区\"},\"sName\":\"勇士\",\"founded\":1946},{\"id\":16,\"name\":\"迈阿密热队\",\"abbr\":\"mia\",\"city\":\"迈阿密\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"热火\",\"founded\":1988},{\"id\":17,\"name\":\"奥兰多魔术队\",\"abbr\":\"orl\",\"city\":\"奥兰多\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"魔术\",\"founded\":1989},{\"id\":18,\"name\":\"亚特兰大老鹰队\",\"abbr\":\"atl\",\"city\":\"亚特兰大\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"老鹰\",\"founded\":1949},{\"id\":19,\"name\":\"华盛顿奇才队\",\"abbr\":\"was\",\"city\":\"华盛顿\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"奇才\",\"founded\":1961},{\"id\":20,\"name\":\"夏洛特黄蜂队\",\"abbr\":\"cha\",\"city\":\"夏洛特\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":4,\"name\":\"东南区\"},\"sName\":\"黄蜂\",\"founded\":2004},{\"id\":21,\"name\":\"底特律活塞队\",\"abbr\":\"det\",\"city\":\"底特律\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"活塞\",\"founded\":1948},{\"id\":22,\"name\":\"印第安纳步行者队\",\"abbr\":\"ind\",\"city\":\"印第安纳\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"步行者\",\"founded\":1976},{\"id\":23,\"name\":\"克利夫兰骑士队\",\"abbr\":\"cle\",\"city\":\"克利夫兰\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"骑士\",\"founded\":1970},{\"id\":24,\"name\":\"芝加哥公牛队\",\"abbr\":\"chi\",\"city\":\"芝加哥\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"公牛\",\"founded\":1966},{\"id\":25,\"name\":\"密尔沃基雄鹿队\",\"abbr\":\"mil\",\"city\":\"密尔沃基\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":5,\"name\":\"中区\"},\"sName\":\"雄鹿\",\"founded\":1968},{\"id\":26,\"name\":\"波士顿凯尔特人队\",\"abbr\":\"bos\",\"city\":\"波士顿\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"凯尔特人\",\"founded\":1946},{\"id\":27,\"name\":\"费城76人队\",\"abbr\":\"phi\",\"city\":\"费城\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"76人\",\"founded\":1947},{\"id\":28,\"name\":\"纽约尼克斯队\",\"abbr\":\"nyk\",\"city\":\"纽约\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"尼克斯\",\"founded\":1946},{\"id\":29,\"name\":\"布鲁克林篮网队\",\"abbr\":\"BKN\",\"city\":\"布鲁克林\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"篮网\",\"founded\":1967},{\"id\":30,\"name\":\"多伦多猛龙队\",\"abbr\":\"tor\",\"city\":\"多伦多\",\"league\":{\"id\":2,\"name\":\"东部\"},\"conference\":{\"id\":6,\"name\":\"大西洋区\"},\"sName\":\"猛龙\",\"founded\":1995}]";

//        String jsonString= GetHttpResponse.getHttpResponse("http://192.168.2.122:8080/NBADataSystem/getTeams.do");

            ACache.get(mContext).put("getTeams",jsonString);
            Log.i("数据源","网络");
        }
        else Log.i("数据源","缓存");

        //解析jsonString 构造Map
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String sName = obj.getString("sName");
                JSONObject conferenceArray = obj.getJSONObject("conference");
                String conference = conferenceArray.getString("name");

                map.put(sName,conference);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return map;
    }

  
}
