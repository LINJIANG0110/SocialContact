package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.qianyu.communicate.R;
import com.qianyu.communicate.jzvd.JZVideoPlayer;
import com.qianyu.communicate.jzvd.JZVideoPlayerStandard;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class JzFullActivity extends AppCompatActivity {

    private String url;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_jz_full);
        ((LinearLayout) findViewById(R.id.ly_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (getIntent() != null) {
            url = getIntent().getStringExtra("url");
            name = getIntent().getStringExtra("name");
            JZVideoPlayerStandard.startFullscreen(this, JZVideoPlayerStandard.class, url, name);
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        finish();
    }
}
