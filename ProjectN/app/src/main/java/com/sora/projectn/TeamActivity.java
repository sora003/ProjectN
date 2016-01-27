package com.sora.projectn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Sora on 2016/1/26.
 */
public class TeamActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        test();
    }

    private void test() {
        textView = (TextView) findViewById(R.id.infotext);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        textView.setText(bundle.getString("abbr"));
    }
}
