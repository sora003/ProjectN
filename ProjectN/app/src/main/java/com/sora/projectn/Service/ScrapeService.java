package com.sora.projectn.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.sora.projectn.dataservice.MatchDS;
import com.sora.projectn.dataservice.PlayerDS;
import com.sora.projectn.dataservice.TeamDS;
import com.sora.projectn.dataservice.impl.MatchDSImpl;
import com.sora.projectn.dataservice.impl.PlayerDSImpl;
import com.sora.projectn.dataservice.impl.TeamDSImpl;

import java.util.List;
import java.util.concurrent.CountDownLatch;



/**
 * Created by Sora on 2016/1/11.
 * 爬取NBA官网数据
 */
public class ScrapeService extends Service {

    //关联Service和Activity
    private CrawlerServiceBinder binder = new CrawlerServiceBinder();
    private OnParserCallBack callBack;
    private final int SCRAPE_DATA = 0x01;
    private final int SCRAPE_SUCCESS = 0x02;
    private final int SCRAPE_ERROR = 0x03;
    private final int IO_ERROR = 0x04;

    //最新赛季年
    //TODO 该设置后续考虑 切换成用户输入或自动获取
    private final int CURR_YEAR = 2016;
    private final String startDay = "20151028";
    private final String endDay = "20160201";

    //球队基本数据 子线程
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    //球队logo爬取和球队联盟等信息爬取子线程
    private CountDownLatch handlerCountDownLatch = new CountDownLatch(4);
    //球队赛季数据 子线程
    private CountDownLatch seasonInfoCountDownLatch = new CountDownLatch(1);
    //球员基本数据 子线程
    private CountDownLatch playerCountDownLatch = new CountDownLatch(1);

    //获取TeamDS接口
    TeamDS teamDS = new TeamDSImpl();
    //获取Player接口
    PlayerDS playerDS = new PlayerDSImpl();
    //获取MatchDS接口
    MatchDS matchDS = new MatchDSImpl();


    //返回binder 使得Service的引用可以通过返回的IBinder对象得到
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        handler.sendEmptyMessage(SCRAPE_DATA);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SCRAPE_DATA:
                    crawlerData();
                    break;
                case SCRAPE_SUCCESS:
                    //爬取数据完成
                    if (callBack != null){
                        callBack.OnParserComplete(true);
                    }
                    break;
                case SCRAPE_ERROR:
                    Toast.makeText(ScrapeService.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    break;
                case IO_ERROR:
                    Toast.makeText(ScrapeService.this,"无法读取SDCard",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //爬取数据
    //TODO MATCH的日期选择问题 尽可能在SETTING中设置 实现重新获取的功能 目前默认为获取  [20141001-20151231] 的比赛记录
    private void crawlerData() {


        //TODO 加入如果第一次运行 爬取数据 否则 调用Sql中的数据的处理

        getTeamList.start();

        getTeamLogo.start();

        getTeamListInfo.start();

        getTeamSeasonGameInfo.start();

        getPlayerInfo.start();

        getPlayerImg.start();

        getMatchInfo.start();


    }

    /**
     * 获取球队的基本信息 包含了球队的缩写 名字  建立时间  并将这些数据添加到数据库中
     */
    Thread getTeamList = new Thread(new Runnable() {
        @Override
        public void run() {
            teamDS.setTeamList(getApplicationContext());
            countDownLatch.countDown();
        }
    });

    /**
     * 获取球队的logo 并将这些数据存储到SDCard
     * 需要判断SDCard是否存在
     */
    Thread getTeamLogo = new Thread(new Runnable() {
        @Override
        public void run() {
            //判断SD卡是否存在 若不存在 发送错误报告
//            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//                handler.sendEmptyMessage(IO_ERROR);
//                return;
//            }
            //等待球队Abbr等信息爬取完成
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            teamDS.setTeamLogo(getApplicationContext());

            handlerCountDownLatch.countDown();

            //等待getTeamLogo getTeamListInfo两个子线程执行完毕
            try {
                handlerCountDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(SCRAPE_SUCCESS);
        }
    });

    /**
     * 获取球队的基本信息 包含了球队的城市 分区 联盟 等信息
     */
    Thread getTeamListInfo = new Thread(new Runnable() {
        @Override
        public void run() {
            //等待球队Abbr等信息爬取完成
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            teamDS.setTeamListInfo(getApplicationContext());
            handlerCountDownLatch.countDown();
        }
    });

    /**
     * 获取球队最新赛季的数据
     */
    Thread getTeamSeasonGameInfo = new Thread(new Runnable() {
        @Override
        public void run() {
            //等待球队Abbr等信息爬取完成
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //新建teamSeasonGame表数据
//            teamDS.setTeamSeasonGame(getApplicationContext(),CURR_YEAR);

            seasonInfoCountDownLatch.countDown();

        }
    });

    /**
     * 获取球员基本数据
     */
    Thread getPlayerInfo = new Thread(new Runnable() {
        @Override
        public void run() {
            //等待球队赛季数据信息爬取完成
            try {
                seasonInfoCountDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playerDS.setPlayInfo(getApplicationContext());

            playerCountDownLatch.countDown();

        }
    });

    /**
     * 获取球员照片
     */
    Thread getPlayerImg = new Thread(new Runnable() {
        @Override
        public void run() {
            //判断SD卡是否存在 若不存在 发送错误报告
//            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//                handler.sendEmptyMessage(IO_ERROR);
//                return;
//            }
            //等待球员基本数据爬取完成
            try {
                playerCountDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            playerDS.setPlayerImg(getApplicationContext());


            handlerCountDownLatch.countDown();
        }
    });

    /**
     * 获取比赛数据
     */
    Thread getMatchInfo = new Thread(new Runnable() {
        @Override
        public void run() {

            matchDS.setMatchInfo(getApplicationContext(),startDay,endDay);

            handlerCountDownLatch.countDown();
        }
    });

    //定义CrawlerServiceBinder
    public class CrawlerServiceBinder extends Binder{
        public ScrapeService getService(){
            return ScrapeService.this;
        }
    }

    //定义接口 为binder访问service服务
    public interface OnParserCallBack{
        public void OnParserComplete(Boolean isScrape);
    }

    public void setCallBack(OnParserCallBack callBack){
        this.callBack = callBack;
    }

    public void removeCallBack(){
        callBack = null;
    }
}
