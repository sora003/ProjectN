package com.sora.projectn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.sora.projectn.Service.CrawlerService;

/**
 * Created by Sora on 2016/1/11.
 * 爬取NBA官网数据，在爬取过程中显示固定画面
 */
public class WelcomeActivity extends AppCompatActivity{

    private Intent intent;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initService();
    }


    //启动Service
    private void initService() {
        intent = new Intent(mContext, CrawlerService.class);
        //启动WeatherService
        startService(intent);
        //绑定Service
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    ///ServiceConnection 与Service交互
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
