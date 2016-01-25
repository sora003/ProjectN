package com.sora.projectn;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sora.projectn.Web.sqlDS.TeamSDS;
import com.sora.projectn.Web.sqlDS.impl.Teamimpl;
import com.sora.projectn.Web.webDS.TeamWDS;
import com.sora.projectn.Web.webDS.impl.TeamData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Sora on 2016/1/22.
 */
public class TestActivity extends AppCompatActivity {

    CountDownLatch countDownLatch = new CountDownLatch(1);
    TextView textView;
    List<String> list = new ArrayList<String>();
    Bitmap bmp = null;

    ImageView imageView;

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        textView = (TextView) findViewById(R.id.testText);

        imageView = (ImageView) findViewById(R.id.testImage);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                    TeamSDS teamSDS = new Teamimpl();
//                    list = teamSDS.getTeamAbbrFromSql(getApplicationContext());
//                    handler.sendEmptyMessage(1);
//
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                TeamSDS teamIO = new Teamimpl();
                teamIO.setTeamListToSql(getApplicationContext());


                //调用TeamSDS接口 获取球队缩写列表
                TeamSDS teamSDS = new Teamimpl();
//                List list = teamSDS.getTeamAbbrFromSql(getApplicationContext());

                //调用TeamWDS接口 获取(k,v)=(球队缩写,球队logo)的Map
                TeamWDS teamWDS = new TeamData();
                Map<String,Bitmap> map = teamWDS.getTeamLogoFromWeb(list);


                //获取map的key 的首个对象
                Iterator iterator = map.keySet().iterator();

                //遍历set(key)
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    String abbr = key.toString();
                    Bitmap bmp = map.get(key);

                    //设置文件路径  SDCard/NBADATA/TeamLogo
                    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NBADATA/TeamLogo" ;



                    String fileName = abbr + ".png";

                    File pFile = new File(filePath);

                    //判断文件是否存在 若不存在 则创建文件
                    if (! pFile.exists()){
                        pFile.mkdirs();
                    }

                    file = new File(filePath + "/" + fileName);

                    handler.sendEmptyMessage(3);



                    FileOutputStream fileOutputStream;
                    try {
                        fileOutputStream = new FileOutputStream(file);

                        //存储图片
                        bmp.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);

                        //关闭文件
                        fileOutputStream.flush();
                        fileOutputStream.close();



                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }


//                handler.sendEmptyMessage(2);
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
                    break;
                case 2:
                    imageView.setImageBitmap(bmp);
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(),file.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    };

}
