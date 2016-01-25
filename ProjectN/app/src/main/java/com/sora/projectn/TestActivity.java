package com.sora.projectn;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sora.projectn.Web.sqlDS.TeamSDS;
import com.sora.projectn.Web.sqlDS.impl.Teamimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Sora on 2016/1/22.
 */
public class TestActivity extends AppCompatActivity {

    CountDownLatch countDownLatch = new CountDownLatch(1);
    TextView textView;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        textView = (TextView) findViewById(R.id.testText);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO 加入如果第一次运行 爬取数据 否则 调用Sql中的数据的处理
                TeamSDS teamSDS = new Teamimpl();
                teamSDS.setTeamListToSql(getApplicationContext());
                countDownLatch.countDown();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    TeamSDS teamSDS = new Teamimpl();
                    list = teamSDS.getTeamAbbrFromSql(getApplicationContext());
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    textView.setText(list.get(10));
            }
        }
    };

}
