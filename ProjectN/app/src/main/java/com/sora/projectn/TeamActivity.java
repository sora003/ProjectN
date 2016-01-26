package com.sora.projectn;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.sora.projectn.businesslogic.TeamBL;
import com.sora.projectn.businesslogicservice.TeamBLS;
import com.sora.projectn.vo.TeamConferenceVo;

import java.util.List;

/**
 * Created by Sora on 2016/1/24.
 */
public class TeamActivity extends AppCompatActivity {

    private static final int GET_DATA = 0x001;

    private List<TeamConferenceVo> list;
    private Context mContext;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        initView();
        getData.start();
    }

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


    }

    /**
     * 获取球队缩略名信息
     */
    Thread getData = new Thread(new Runnable() {
        @Override
        public void run() {
            //调用TeamBLS接口 获取球队缩略名信息
            TeamBLS teamBLS = new TeamBL();
            list = teamBLS.getTeamConferenceInfo(mContext);
            handler.sendEmptyMessage(GET_DATA);
        }
    });


    /**
     * 为View设置值
     */
    private void setView() {

        //TextView
        for (int i = 0; i < list.size(); i++) {
            //获取sNameList 球队缩略名表
            List<String> sNameList = list.get(i).getList();
            switch (list.get(i).getConference()){
                case "Southwest":
                    tv_eC1T1.setText(sNameList.get(0));
                    tv_wC1T1.setText(sNameList.get(1));
                    tv_wC1T2.setText(sNameList.get(2));
                    tv_wC1T3.setText(sNameList.get(3));
                    tv_wC1T4.setText(sNameList.get(4));
                    tv_wC1T5.setText(sNameList.get(5));
                    break;
                case "Pacific":
                    tv_wC2T1.setText(sNameList.get(0));
                    tv_wC2T2.setText(sNameList.get(1));
                    tv_wC2T3.setText(sNameList.get(2));
                    tv_wC2T4.setText(sNameList.get(3));
                    tv_wC2T5.setText(sNameList.get(4));
                    break;
                case "Northwest":
                    tv_wC3T1.setText(sNameList.get(0));
                    tv_wC3T2.setText(sNameList.get(1));
                    tv_wC3T3.setText(sNameList.get(2));
                    tv_wC3T4.setText(sNameList.get(3));
                    tv_wC3T5.setText(sNameList.get(4));
                    break;
                case "Southeast":
                    tv_eC1T1.setText(sNameList.get(0));
                    tv_eC1T2.setText(sNameList.get(1));
                    tv_eC1T3.setText(sNameList.get(2));
                    tv_eC1T4.setText(sNameList.get(3));
                    tv_eC1T5.setText(sNameList.get(4));
                    break;
                case "Central":
                    tv_eC2T1.setText(sNameList.get(0));
                    tv_eC2T2.setText(sNameList.get(1));
                    tv_eC2T3.setText(sNameList.get(2));
                    tv_eC2T4.setText(sNameList.get(3));
                    tv_eC2T5.setText(sNameList.get(4));
                    break;
                case "Atlantic":
                    tv_eC3T1.setText(sNameList.get(0));
                    tv_eC3T2.setText(sNameList.get(1));
                    tv_eC3T3.setText(sNameList.get(2));
                    tv_eC3T4.setText(sNameList.get(3));
                    tv_eC3T5.setText(sNameList.get(4));
                    break;
            }
        }
    }


}
