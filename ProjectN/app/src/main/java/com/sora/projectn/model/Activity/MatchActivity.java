package com.sora.projectn.model.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sora.projectn.R;

/**
 * Created by Sora on 2016/2/6.
 */
public class MatchActivity extends AppCompatActivity{

    private int no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        parseIntent();

        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }).start();


    }

    /**
     * 获取数据
     */
    private void getData() {

    }


    /**
     * 读取Intent传递的no 获取比赛对应编号
     */
    private void parseIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        no = bundle.getInt("no");
    }
}
