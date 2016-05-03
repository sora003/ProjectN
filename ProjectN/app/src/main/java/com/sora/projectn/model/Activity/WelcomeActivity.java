package com.sora.projectn.model.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sora.projectn.R;
import com.sora.projectn.gc.Service.ScrapeService;
import com.sora.projectn.utils.Consts;

/**
 * Created by Sora on 2016-04-26.
 */
public class WelcomeActivity extends AppCompatActivity{

    private static final String HAS_TEAMINFO = "hasTeamLogo";

    private Intent intent;
    private Context mContext;


    private SharedPreferences.Editor editor;
    private int character;
    TextView textView_01;
    TextView textView_02;
    TextView textView_03;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();

        initListener();


        //使用SharedPreferences 读取球队基本数据是否已经存在
        SharedPreferences sharedPreferences = getSharedPreferences("character", MODE_PRIVATE);
        //获取编辑器
        editor = sharedPreferences.edit();
        character = sharedPreferences.getInt(Consts.SharedPreferences_KEY_01, 0);
        if (character != 0){
            startActivity(new Intent(mContext,MainActivity.class));
            /**
             * TODO 有待考量
             */
            finish();
        }
        else {

            editor.putInt(Consts.SharedPreferences_KEY_01, character);
            //提交修改
            editor.commit();
        }



    }

    private void initView() {
        mContext = this;

        textView_01 = (TextView) findViewById(R.id.tv_welcome_01);
        textView_02 = (TextView) findViewById(R.id.tv_welcome_02);
        textView_03 = (TextView) findViewById(R.id.tv_welcome_03);
    }

    private void initListener() {
        textView_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(Consts.SharedPreferences_KEY_01, Consts.SharedPreferences_Vaule_01_1);
                commit();

            }
        });
        textView_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(Consts.SharedPreferences_KEY_01, Consts.SharedPreferences_Vaule_01_2);
                commit();
            }
        });
        textView_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(Consts.SharedPreferences_KEY_01, Consts.SharedPreferences_Vaule_01_3);
                commit();
            }
        });
    }

    private void commit() {
        //提交
        editor.commit();

        //跳转
        Intent intent = new Intent(mContext,MainActivity.class);
        startActivity(intent);
    }


}
