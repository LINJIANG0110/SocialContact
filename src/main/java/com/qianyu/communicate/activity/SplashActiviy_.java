package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.views.LuckyPanView;

import com.qianyu.communicate.base.BaseActivity;

/**
 * Created by cxh on 2016/9/9.
 */
public class SplashActiviy_ extends BaseActivity {
    private LuckyPanView mLuckyPanView;
    private ImageView mStartBtn;

    @Override
    public int getRootViewId() {
        return R.layout.activity_splash_;
    }

    @Override
    public void setViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (MyApplication.getInstance().isLogin()) {
                    startActivity(new Intent(SplashActiviy_.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActiviy_.this, RegistActivity.class));
                }
                finish();
            }
        }, 1200);


        mLuckyPanView = findViewById(R.id.id_luckypan);
        mStartBtn = findViewById(R.id.id_start_btn);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLuckyPanView.isStart()) {
                    mStartBtn.setImageResource(R.drawable.stop);
                    mLuckyPanView.luckyStart(1);
                } else {
                    if (!mLuckyPanView.isShouldEnd()) {
                        mStartBtn.setImageResource(R.drawable.start);
                        mLuckyPanView.luckyEnd();
                    }
                }
            }
        });

    }

    @Override
    public void initData() {

    }
}
