package com.sora.projectn.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Sora on 2016/1/11.
 * 爬取NBA官网数据
 */
public class CrawlerService extends Service {

    //关联Service和Activity
    private CrawlerServiceBinder binder = new CrawlerServiceBinder();
    private OnParserCallBack callBack;
    private final int CRAWLER_DATA = 0x01;
    private final int CRAWLER_SUCCESS = 0x02;
    private final int CRAWLER_ERROR = 0x03;


    //返回binder 使得Service的引用可以通过返回的IBinder对象得到
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        handler.sendEmptyMessage(CRAWLER_DATA);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CRAWLER_DATA:
                    crawlerData();
                    break;
                case CRAWLER_SUCCESS:
                    //爬取数据完成
                    if (callBack != null){
                        callBack.OnParserComplete(true);
                    }
                    break;
                case CRAWLER_ERROR:
                    Toast.makeText(CrawlerService.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //爬取数据
    private void crawlerData() {

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
