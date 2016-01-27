package com.sora.projectn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sora.projectn.businesslogic.TeamBL;
import com.sora.projectn.businesslogicservice.TeamBLS;
import com.sora.projectn.dataservice.TeamDS;
import com.sora.projectn.dataservice.impl.TeamDSImpl;
import com.sora.projectn.vo.TeamConferenceVo;

import java.util.List;

/**
 * Created by Sora on 2016/1/24.
 */
public class TeamListActivity extends AppCompatActivity {



    private static final int GET_DATA = 0x001;
    private static final int GET_SNAME = 0x002;
    private static final int GET_ABBR = 0x003;

    private List<TeamConferenceVo> list;
    private Context mContext;
    private TeamDS DS = new TeamDSImpl();
    private TeamBLS BLS = new TeamBL();
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
                case GET_DATA:
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
                    bundle.putString("abbr",abbr);
                    intent.putExtras(bundle);
                    startActivity(intent);
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
            list = BLS.getTeamConferenceInfo(mContext);

            handler.sendEmptyMessage(GET_DATA);
        }
    });


    /**
     * 初始化View
     */
    private void initView() {

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
     * 为View设置值
     */
    private void setView() {

        //TextView
        for (int i = 0; i < list.size(); i++) {
            
            //获取sNameList 球队缩略名表
            List<String> sNameList = list.get(i).getsNameList();
            //获取logoList 球队logo表
            List<Bitmap> logoList = list.get(i).getLogoList();
            
            switch (list.get(i).getConference()){
                case "Southwest":
                    
                    tv_wC1T1.setText(sNameList.get(0));
                    tv_wC1T2.setText(sNameList.get(1));
                    tv_wC1T3.setText(sNameList.get(2));
                    tv_wC1T4.setText(sNameList.get(3));
                    tv_wC1T5.setText(sNameList.get(4));

                    iv_wC1T1.setImageBitmap(logoList.get(0));
                    iv_wC1T2.setImageBitmap(logoList.get(1));
                    iv_wC1T3.setImageBitmap(logoList.get(2));
                    iv_wC1T4.setImageBitmap(logoList.get(3));
                    iv_wC1T5.setImageBitmap(logoList.get(4));

                    break;

                case "Pacific":

                    tv_wC2T1.setText(sNameList.get(0));
                    tv_wC2T2.setText(sNameList.get(1));
                    tv_wC2T3.setText(sNameList.get(2));
                    tv_wC2T4.setText(sNameList.get(3));
                    tv_wC2T5.setText(sNameList.get(4));

                    iv_wC2T1.setImageBitmap(logoList.get(0));
                    iv_wC2T2.setImageBitmap(logoList.get(1));
                    iv_wC2T3.setImageBitmap(logoList.get(2));
                    iv_wC2T4.setImageBitmap(logoList.get(3));
                    iv_wC2T5.setImageBitmap(logoList.get(4));

                    break;

                case "Northwest":

                    tv_wC3T1.setText(sNameList.get(0));
                    tv_wC3T2.setText(sNameList.get(1));
                    tv_wC3T3.setText(sNameList.get(2));
                    tv_wC3T4.setText(sNameList.get(3));
                    tv_wC3T5.setText(sNameList.get(4));

                    iv_wC3T1.setImageBitmap(logoList.get(0));
                    iv_wC3T2.setImageBitmap(logoList.get(1));
                    iv_wC3T3.setImageBitmap(logoList.get(2));
                    iv_wC3T4.setImageBitmap(logoList.get(3));
                    iv_wC3T5.setImageBitmap(logoList.get(4));

                    break;

                case "Southeast":

                    tv_eC1T1.setText(sNameList.get(0));
                    tv_eC1T2.setText(sNameList.get(1));
                    tv_eC1T3.setText(sNameList.get(2));
                    tv_eC1T4.setText(sNameList.get(3));
                    tv_eC1T5.setText(sNameList.get(4));

                    iv_eC1T1.setImageBitmap(logoList.get(0));
                    iv_eC1T2.setImageBitmap(logoList.get(1));
                    iv_eC1T3.setImageBitmap(logoList.get(2));
                    iv_eC1T4.setImageBitmap(logoList.get(3));
                    iv_eC1T5.setImageBitmap(logoList.get(4));

                    break;

                case "Central":

                    tv_eC2T1.setText(sNameList.get(0));
                    tv_eC2T2.setText(sNameList.get(1));
                    tv_eC2T3.setText(sNameList.get(2));
                    tv_eC2T4.setText(sNameList.get(3));
                    tv_eC2T5.setText(sNameList.get(4));

                    iv_eC2T1.setImageBitmap(logoList.get(0));
                    iv_eC2T2.setImageBitmap(logoList.get(1));
                    iv_eC2T3.setImageBitmap(logoList.get(2));
                    iv_eC2T4.setImageBitmap(logoList.get(3));
                    iv_eC2T5.setImageBitmap(logoList.get(4));

                    break;

                case "Atlantic":

                    tv_eC3T1.setText(sNameList.get(0));
                    tv_eC3T2.setText(sNameList.get(1));
                    tv_eC3T3.setText(sNameList.get(2));
                    tv_eC3T4.setText(sNameList.get(3));
                    tv_eC3T5.setText(sNameList.get(4));

                    iv_eC3T1.setImageBitmap(logoList.get(0));
                    iv_eC3T2.setImageBitmap(logoList.get(1));
                    iv_eC3T3.setImageBitmap(logoList.get(2));
                    iv_eC3T4.setImageBitmap(logoList.get(3));
                    iv_eC3T5.setImageBitmap(logoList.get(4));

                    break;
            }
        }



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
                sName = (String) tv_wC1T1.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC3T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC1T2.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC3T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC1T3.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC3T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC1T4.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        wC3T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_wC1T5.getText();
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
                sName = (String) tv_eC1T1.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC3T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC1T2.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC3T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC1T3.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC3T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC1T4.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });

        eC3T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = (String) tv_eC1T5.getText();
                handler.sendEmptyMessage(GET_SNAME);
            }
        });
    }


}
