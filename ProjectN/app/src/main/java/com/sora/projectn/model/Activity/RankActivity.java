package com.sora.projectn.model.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sora.projectn.R;

import java.util.ArrayList;

/**
 * Created by qhy on 2016/4/18.
 * RankActivity
 */
public class RankActivity extends FragmentActivity {

    private ViewPager pager;
    private PagerTabStrip tabstrip;
    ArrayList<View> viewContainter = new ArrayList<View>();
    ArrayList<String> titleContainer = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
    }

    private void initview(){
        setContentView(R.layout.activity_rank);
        pager = (ViewPager)findViewById(R.id.rankpager);
        tabstrip = (PagerTabStrip)findViewById(R.id.ranktabstrip);
    }

    private void initviewpager(){

    }

    private void initlistener(){

    }
}
