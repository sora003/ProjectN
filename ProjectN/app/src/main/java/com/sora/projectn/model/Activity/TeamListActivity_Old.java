package com.sora.projectn.model.Activity;

import android.content.Context;
import android.content.Intent;
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

import com.sora.projectn.R;
import com.sora.projectn.utils.ACache;
import com.sora.projectn.utils.BitmapHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/1/24.
 */
public class TeamListActivity_Old extends AppCompatActivity {



    private static final int SET_VIEW = 0x001;
    private static final int GET_name = 0x002;
    private static final int GET_ABBR = 0x003;
    private static final String TITLE = "   球队列表";

    /**
     * 记录分区和球队名称
     */
    Map<String, String> confMap = new HashMap<>();
    /**
     * 记录id和球队名称
     */
    Map<String, Integer> idMap = new HashMap<>();

    private Context mContext;
    private String name;
    private int id;



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
        setContentView(R.layout.old_activity_teamlist);

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
                case GET_name:
                    //根据球队缩略名获取球队缩写信息
                    //该子线程会重复调用  不可以直接定义  必须每次新建
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            id = getTeamID().get(name);
                            handler.sendEmptyMessage(GET_ABBR);
                        }
                    }).start();
                    break;
                case GET_ABBR:
                    //绑定id参数 启动TeamActivity
                    Intent intent = new Intent(mContext,TeamActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
//                    Log.i("id", String.valueOf(id));
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
            confMap = getTeamList();

            idMap = getTeamID();

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
        Iterator iterator = confMap.keySet().iterator();
        
        while (iterator.hasNext()){
            Object key = iterator.next();
            String name= key.toString();
            String conference = confMap.get(key);




            //判断球队所属分区
            switch (conference){
                case "西南区":
                    swList.add(name);
                    break;
                case "西北区":
                    nwList.add(name);
                    break;
                case "太平洋区":
                    paList.add(name);
                    break;
                case "中部区":
                    ceList.add(name);
                    break;
                case "东南区":
                    seList.add(name);
                    break;
                case "大西洋区":
                    atList.add(name);
                    break;
            }

        }



        tv_wC1T1.setText(swList.get(0));
        tv_wC1T2.setText(swList.get(1));
        tv_wC1T3.setText(swList.get(2));
        tv_wC1T4.setText(swList.get(3));
        tv_wC1T5.setText(swList.get(4));

        iv_wC1T1.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(swList.get(0))));
        iv_wC1T2.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(swList.get(1))));
        iv_wC1T3.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(swList.get(2))));
        iv_wC1T4.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(swList.get(3))));
        iv_wC1T5.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(swList.get(4))));



        tv_wC2T1.setText(paList.get(0));
        tv_wC2T2.setText(paList.get(1));
        tv_wC2T3.setText(paList.get(2));
        tv_wC2T4.setText(paList.get(3));
        tv_wC2T5.setText(paList.get(4));


        iv_wC2T1.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(paList.get(0))));
        iv_wC2T2.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(paList.get(1))));
        iv_wC2T3.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(paList.get(2))));
        iv_wC2T4.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(paList.get(3))));
        iv_wC2T5.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(paList.get(4))));



        tv_wC3T1.setText(nwList.get(0));
        tv_wC3T2.setText(nwList.get(1));
        tv_wC3T3.setText(nwList.get(2));
        tv_wC3T4.setText(nwList.get(3));
        tv_wC3T5.setText(nwList.get(4));

        iv_wC3T1.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(nwList.get(0))));
        iv_wC3T2.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(nwList.get(1))));
        iv_wC3T3.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(nwList.get(2))));
        iv_wC3T4.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(nwList.get(3))));
        iv_wC3T5.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(nwList.get(4))));




        tv_eC1T1.setText(seList.get(0));
        tv_eC1T2.setText(seList.get(1));
        tv_eC1T3.setText(seList.get(2));
        tv_eC1T4.setText(seList.get(3));
        tv_eC1T5.setText(seList.get(4));

        iv_eC1T1.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(seList.get(0))));
        iv_eC1T2.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(seList.get(1))));
        iv_eC1T3.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(seList.get(2))));
        iv_eC1T4.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(seList.get(3))));
        iv_eC1T5.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(seList.get(4))));



        tv_eC2T1.setText(ceList.get(0));
        tv_eC2T2.setText(ceList.get(1));
        tv_eC2T3.setText(ceList.get(2));
        tv_eC2T4.setText(ceList.get(3));
        tv_eC2T5.setText(ceList.get(4));

        iv_eC2T1.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(ceList.get(0))));
        iv_eC2T2.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(ceList.get(1))));
        iv_eC2T3.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(ceList.get(2))));
        iv_eC2T4.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(ceList.get(3))));
        iv_eC2T5.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(ceList.get(4))));



        tv_eC3T1.setText(atList.get(0));
        tv_eC3T2.setText(atList.get(1));
        tv_eC3T3.setText(atList.get(2));
        tv_eC3T4.setText(atList.get(3));
        tv_eC3T5.setText(atList.get(4));

        iv_eC3T1.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(atList.get(0))));
        iv_eC3T2.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(atList.get(1))));
        iv_eC3T3.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(atList.get(2))));
        iv_eC3T4.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(atList.get(3))));
        iv_eC3T5.setImageBitmap(BitmapHelper.loadBitMap(mContext, BitmapHelper.getBitmap(atList.get(4))));

    
    }
    



    


    /**
     * 监听模块
     */
    private void initListener() {
        wC1T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC1T1.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC1T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC1T2.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC1T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC1T3.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC1T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC1T4.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC1T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC1T5.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });


        wC2T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC2T1.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC2T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC2T2.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC2T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC2T3.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC2T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC2T4.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC2T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC2T5.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });


        wC3T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC3T1.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC3T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC3T2.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC3T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC3T3.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC3T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC3T4.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        wC3T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_wC3T5.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });







        eC1T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC1T1.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC1T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC1T2.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC1T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC1T3.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC1T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC1T4.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC1T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC1T5.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });


        eC2T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC2T1.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC2T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC2T2.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC2T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC2T3.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC2T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC2T4.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC2T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC2T5.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });


        eC3T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC3T1.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC3T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC3T2.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC3T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC3T3.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC3T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC3T4.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });

        eC3T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (String) tv_eC3T5.getText();
                handler.sendEmptyMessage(GET_name);
            }
        });
    }




    /**
     * 从server获取球队表
     * @return
     */
    private Map<String,String> getTeamList() {
        Map<String,String> map = new HashMap<>();

        String jsonString = ACache.get(mContext).getAsString("getTeams");

        /**
         * Test
         * Remember to delete
         */
        jsonString = null;


        if (jsonString == null){
            //从server获取数据
            jsonString = "[{\"id\":1,\"name\":\"老鹰\",\"city\":\"亚特兰大\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"菲利普斯球馆\",\"startYearInNBA\":1949,\"numOfChampions\":1},{\"id\":2,\"name\":\"凯尔特人\",\"city\":\"波士顿\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"TD花园球馆\",\"startYearInNBA\":1946,\"numOfChampions\":17},{\"id\":3,\"name\":\"鹈鹕\",\"city\":\"新奥尔良\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"新奥尔良球馆\",\"startYearInNBA\":1988,\"numOfChampions\":0},{\"id\":4,\"name\":\"公牛\",\"city\":\"芝加哥\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"联合中心球馆\",\"startYearInNBA\":1966,\"numOfChampions\":6},{\"id\":5,\"name\":\"骑士\",\"city\":\"克利夫兰\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"快贷球馆\",\"startYearInNBA\":1970,\"numOfChampions\":0},{\"id\":6,\"name\":\"小牛\",\"city\":\"达拉斯\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"美航中心球馆\",\"startYearInNBA\":1980,\"numOfChampions\":1},{\"id\":7,\"name\":\"掘金\",\"city\":\"丹佛\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"百事中心球馆\",\"startYearInNBA\":1976,\"numOfChampions\":0},{\"id\":8,\"name\":\"活塞\",\"city\":\"底特律\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"奥本山宫球馆\",\"startYearInNBA\":1948,\"numOfChampions\":3},{\"id\":9,\"name\":\"勇士\",\"city\":\"金州\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"甲骨文球馆\",\"startYearInNBA\":1946,\"numOfChampions\":4},{\"id\":10,\"name\":\"火箭\",\"city\":\"休斯顿\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"丰田中心球馆\",\"startYearInNBA\":1967,\"numOfChampions\":2},{\"id\":11,\"name\":\"步行者\",\"city\":\"印第安纳\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"银行家生活球馆\",\"startYearInNBA\":1976,\"numOfChampions\":0},{\"id\":12,\"name\":\"快船\",\"city\":\"洛杉矶\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"斯台普斯中心球馆\",\"startYearInNBA\":1970,\"numOfChampions\":0},{\"id\":13,\"name\":\"湖人\",\"city\":\"洛杉矶\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"斯台普斯中心球馆\",\"startYearInNBA\":1948,\"numOfChampions\":16},{\"id\":14,\"name\":\"热火\",\"city\":\"迈阿密\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"美航球馆\",\"startYearInNBA\":1988,\"numOfChampions\":3},{\"id\":15,\"name\":\"雄鹿\",\"city\":\"密尔沃基\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"布拉德利中心球馆\",\"startYearInNBA\":1968,\"numOfChampions\":1},{\"id\":16,\"name\":\"森林狼\",\"city\":\"明尼苏达\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"标靶中心球馆\",\"startYearInNBA\":1989,\"numOfChampions\":0},{\"id\":17,\"name\":\"篮网\",\"city\":\"布鲁克林\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"巴克莱中心球馆\",\"startYearInNBA\":1967,\"numOfChampions\":1},{\"id\":18,\"name\":\"尼克斯\",\"city\":\"纽约\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"麦迪逊广场花园球馆\",\"startYearInNBA\":1946,\"numOfChampions\":2},{\"id\":19,\"name\":\"魔术\",\"city\":\"奥兰多\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"安利中心球馆\",\"startYearInNBA\":1989,\"numOfChampions\":0},{\"id\":20,\"name\":\"76人\",\"city\":\"费城\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"富国银行中心球馆\",\"startYearInNBA\":1937,\"numOfChampions\":3},{\"id\":21,\"name\":\"太阳\",\"city\":\"菲尼克斯\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"美航中心球馆\",\"startYearInNBA\":1968,\"numOfChampions\":0},{\"id\":22,\"name\":\"开拓者\",\"city\":\"波特兰\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"玫瑰花园球馆\",\"startYearInNBA\":1970,\"numOfChampions\":1},{\"id\":23,\"name\":\"国王\",\"city\":\"萨克拉门托\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"能量平衡球馆\",\"startYearInNBA\":1948,\"numOfChampions\":1},{\"id\":24,\"name\":\"马刺\",\"city\":\"圣安东尼奥\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"AT&T中心球馆\",\"startYearInNBA\":1976,\"numOfChampions\":5},{\"id\":25,\"name\":\"雷霆\",\"city\":\"俄克拉荷马城\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"切萨皮克能源球馆\",\"startYearInNBA\":1967,\"numOfChampions\":1},{\"id\":26,\"name\":\"爵士\",\"city\":\"犹他\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"能源方案球馆\",\"startYearInNBA\":1974,\"numOfChampions\":0},{\"id\":27,\"name\":\"奇才\",\"city\":\"华盛顿\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"威瑞森中心球馆\",\"startYearInNBA\":1961,\"numOfChampions\":1},{\"id\":28,\"name\":\"猛龙\",\"city\":\"多伦多\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"加航中心球馆\",\"startYearInNBA\":1995,\"numOfChampions\":0},{\"id\":29,\"name\":\"灰熊\",\"city\":\"孟菲斯\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"联邦快递球馆\",\"startYearInNBA\":1995,\"numOfChampions\":0},{\"id\":30,\"name\":\"黄蜂\",\"city\":\"夏洛特\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"时代华纳中心球馆\",\"startYearInNBA\":2004,\"numOfChampions\":0}]";

//            jsonString= GetHttpResponse.getHttpResponse(GetHttpResponse.getTeams);

            ACache.get(mContext).put("getTeams",jsonString,ACache.TEST_TIME);
            Log.i("Resource","Internet");
        }
        else
        {
            Log.i("Resource","Cache");
        }

        Log.i("info",jsonString);

        //解析jsonString 构造Map
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String name = obj.getString("name");
                String conference = obj.getString("conference");

                map.put(name,conference);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return map;
    }

    private Map<String, Integer> getTeamID() {
        Map<String,Integer> map = new HashMap<>();

        String jsonString = ACache.get(mContext).getAsString("getTeams");

        /**
         * Test
         * Remember to delete
         */
        jsonString = null;

        if (jsonString == null){
            //从server获取数据
            jsonString = "[{\"id\":1,\"name\":\"老鹰\",\"city\":\"亚特兰大\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"菲利普斯球馆\",\"startYearInNBA\":1949,\"numOfChampions\":1},{\"id\":2,\"name\":\"凯尔特人\",\"city\":\"波士顿\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"TD花园球馆\",\"startYearInNBA\":1946,\"numOfChampions\":17},{\"id\":3,\"name\":\"鹈鹕\",\"city\":\"新奥尔良\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"新奥尔良球馆\",\"startYearInNBA\":1988,\"numOfChampions\":0},{\"id\":4,\"name\":\"公牛\",\"city\":\"芝加哥\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"联合中心球馆\",\"startYearInNBA\":1966,\"numOfChampions\":6},{\"id\":5,\"name\":\"骑士\",\"city\":\"克利夫兰\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"快贷球馆\",\"startYearInNBA\":1970,\"numOfChampions\":0},{\"id\":6,\"name\":\"小牛\",\"city\":\"达拉斯\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"美航中心球馆\",\"startYearInNBA\":1980,\"numOfChampions\":1},{\"id\":7,\"name\":\"掘金\",\"city\":\"丹佛\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"百事中心球馆\",\"startYearInNBA\":1976,\"numOfChampions\":0},{\"id\":8,\"name\":\"活塞\",\"city\":\"底特律\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"奥本山宫球馆\",\"startYearInNBA\":1948,\"numOfChampions\":3},{\"id\":9,\"name\":\"勇士\",\"city\":\"金州\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"甲骨文球馆\",\"startYearInNBA\":1946,\"numOfChampions\":4},{\"id\":10,\"name\":\"火箭\",\"city\":\"休斯顿\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"丰田中心球馆\",\"startYearInNBA\":1967,\"numOfChampions\":2},{\"id\":11,\"name\":\"步行者\",\"city\":\"印第安纳\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"银行家生活球馆\",\"startYearInNBA\":1976,\"numOfChampions\":0},{\"id\":12,\"name\":\"快船\",\"city\":\"洛杉矶\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"斯台普斯中心球馆\",\"startYearInNBA\":1970,\"numOfChampions\":0},{\"id\":13,\"name\":\"湖人\",\"city\":\"洛杉矶\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"斯台普斯中心球馆\",\"startYearInNBA\":1948,\"numOfChampions\":16},{\"id\":14,\"name\":\"热火\",\"city\":\"迈阿密\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"美航球馆\",\"startYearInNBA\":1988,\"numOfChampions\":3},{\"id\":15,\"name\":\"雄鹿\",\"city\":\"密尔沃基\",\"league\":\"东部\",\"conference\":\"中部区\",\"court\":\"布拉德利中心球馆\",\"startYearInNBA\":1968,\"numOfChampions\":1},{\"id\":16,\"name\":\"森林狼\",\"city\":\"明尼苏达\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"标靶中心球馆\",\"startYearInNBA\":1989,\"numOfChampions\":0},{\"id\":17,\"name\":\"篮网\",\"city\":\"布鲁克林\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"巴克莱中心球馆\",\"startYearInNBA\":1967,\"numOfChampions\":1},{\"id\":18,\"name\":\"尼克斯\",\"city\":\"纽约\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"麦迪逊广场花园球馆\",\"startYearInNBA\":1946,\"numOfChampions\":2},{\"id\":19,\"name\":\"魔术\",\"city\":\"奥兰多\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"安利中心球馆\",\"startYearInNBA\":1989,\"numOfChampions\":0},{\"id\":20,\"name\":\"76人\",\"city\":\"费城\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"富国银行中心球馆\",\"startYearInNBA\":1937,\"numOfChampions\":3},{\"id\":21,\"name\":\"太阳\",\"city\":\"菲尼克斯\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"美航中心球馆\",\"startYearInNBA\":1968,\"numOfChampions\":0},{\"id\":22,\"name\":\"开拓者\",\"city\":\"波特兰\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"玫瑰花园球馆\",\"startYearInNBA\":1970,\"numOfChampions\":1},{\"id\":23,\"name\":\"国王\",\"city\":\"萨克拉门托\",\"league\":\"西部\",\"conference\":\"太平洋区\",\"court\":\"能量平衡球馆\",\"startYearInNBA\":1948,\"numOfChampions\":1},{\"id\":24,\"name\":\"马刺\",\"city\":\"圣安东尼奥\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"AT&T中心球馆\",\"startYearInNBA\":1976,\"numOfChampions\":5},{\"id\":25,\"name\":\"雷霆\",\"city\":\"俄克拉荷马城\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"切萨皮克能源球馆\",\"startYearInNBA\":1967,\"numOfChampions\":1},{\"id\":26,\"name\":\"爵士\",\"city\":\"犹他\",\"league\":\"西部\",\"conference\":\"西北区\",\"court\":\"能源方案球馆\",\"startYearInNBA\":1974,\"numOfChampions\":0},{\"id\":27,\"name\":\"奇才\",\"city\":\"华盛顿\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"威瑞森中心球馆\",\"startYearInNBA\":1961,\"numOfChampions\":1},{\"id\":28,\"name\":\"猛龙\",\"city\":\"多伦多\",\"league\":\"东部\",\"conference\":\"大西洋区\",\"court\":\"加航中心球馆\",\"startYearInNBA\":1995,\"numOfChampions\":0},{\"id\":29,\"name\":\"灰熊\",\"city\":\"孟菲斯\",\"league\":\"西部\",\"conference\":\"西南区\",\"court\":\"联邦快递球馆\",\"startYearInNBA\":1995,\"numOfChampions\":0},{\"id\":30,\"name\":\"黄蜂\",\"city\":\"夏洛特\",\"league\":\"东部\",\"conference\":\"东南区\",\"court\":\"时代华纳中心球馆\",\"startYearInNBA\":2004,\"numOfChampions\":0}]";

//            jsonString= GetHttpResponse.getHttpResponse(GetHttpResponse.getTeams);

            ACache.get(mContext).put("getTeams",jsonString,ACache.TEST_TIME);
            Log.i("Resource","Internet");
        }
        else
        {
            Log.i("Resource","Cache");
        }

        //解析jsonString 构造Map
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String name = obj.getString("name");
                int id = obj.getInt("id");

                map.put(name, id);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return map;
    }
  
}
