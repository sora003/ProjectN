package com.sora.projectn.model.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.sora.projectn.R;
import com.sora.projectn.gc.Service.ScrapeService;

/**
 * Created by Sora on 2016/1/11.
 * 爬取NBA官网数据，在爬取过程中显示固定画面
 */
public class WelcomeActivity_Old extends AppCompatActivity{

    private static final String HAS_TEAMINFO = "hasTeamLogo";

    private Intent intent;
    private Context mContext = this;
    private ScrapeService scrapeService;
    private Boolean hasTeamInfo;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_welcome);
        //使用SharedPreferences 读取球队基本数据是否已经存在
        SharedPreferences sharedPreferences = getSharedPreferences("hasData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        hasTeamInfo = sharedPreferences.getBoolean(HAS_TEAMINFO,false);
        if (hasTeamInfo){
            startActivity(new Intent(mContext,MainActivity.class));
            finish();
        }
        else {
            initService();
            editor.remove(HAS_TEAMINFO);
            editor.putBoolean(HAS_TEAMINFO, true);
        }



    }


    //TODO 考虑到爬取数据尤其是图片是非常耗时的操作 因此考虑仅在第一次启动程序时爬取数据 可在Setting中添加更新数据库操作功能  不排除每次启动时仅更新部分数据的可行性
    //启动Service
    private void initService() {
        intent = new Intent(mContext, ScrapeService.class);
        //启动WeatherService
        startService(intent);
        //绑定Service
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    ///ServiceConnection 与Service交互
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //通过Binder 获取Service的引用
           scrapeService = ((ScrapeService.CrawlerServiceBinder) service).getService();

            //从接口读取数据
            scrapeService.setCallBack(new ScrapeService.OnParserCallBack() {
                @Override
                public void OnParserComplete(Boolean isScrape) {
                    //如果爬取完成 跳转到MainActivity
                    if (isScrape){
                        //修改SharedPreferences中的数据 说明已爬取过基本数据
                        //取代commit使用
                        editor.apply();
                        startActivity(new Intent(mContext,MainActivity.class));
                        //TODO  WeatherActivity的关闭问题
                        finish();
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            scrapeService.removeCallBack();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //添加了是否使用Service爬取数据的判断 防止数据已爬取过不调用Service而报错的情况
        if (!hasTeamInfo){
            unbindService(conn);
            stopService(intent);
        }
    }
}
