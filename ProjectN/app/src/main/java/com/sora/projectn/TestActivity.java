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
