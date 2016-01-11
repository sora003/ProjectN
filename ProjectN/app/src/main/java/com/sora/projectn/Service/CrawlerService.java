package com.sora.projectn.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Sora on 2016/1/11.
 * 爬取NBA官网数据
 */
public class CrawlerService extends Service {

    //关联Service和Activity
    private CrawlerServiceBinder binder = new CrawlerServiceBinder();
    private OnParserCallBack callBack;

    private boolean crawlered;


    //返回binder 使得Service的引用可以通过返回的IBinder对象得到
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //爬取未完成
        crawlered = false;
    }

    //定义CrawlerServiceBinder
    public class CrawlerServiceBinder extends Binder{
        public CrawlerService getService(){
            return CrawlerService.this;
        }
    }

    //定义接口 为binder访问service服务
    public interface OnParserCallBack{
        public void OnParserComplete(Boolean crawlered);
    }

    public void setCallBack(OnParserCallBack callBack){
        this.callBack = callBack;
    }

    public void removeCallBack(){
        callBack = null;
    }
}
