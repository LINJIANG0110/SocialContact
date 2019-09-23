package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by cxh on 2016/9/9.
 */
public class GuideNewActivity extends BaseActivity {

    @InjectView(R.id.mGuideImageView)
    ImageView mGuideImageView;
    private List<Integer> guideImgs = new ArrayList<>();
    private int index = 0;
    private Handler mHandler = new Handler();
    private MyRunnable mRunnable;
    private boolean click = false;

    @Override
    public int getRootViewId() {
        return R.layout.activity_new_guide;
    }

    @Override
    public void setViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        guideImgs.add(R.drawable.guide_1);
        guideImgs.add(R.drawable.guide_2);
        guideImgs.add(R.drawable.guide_3);
        guideImgs.add(R.drawable.guide_4);
        guideImgs.add(R.drawable.guide_5);
        guideImgs.add(R.drawable.guide_6);
        guideImgs.add(R.drawable.guide_7);
        guideImgs.add(R.drawable.guide_8);
        guideImgs.add(R.drawable.guide_9);
        guideImgs.add(R.drawable.guide_10);
        guideImgs.add(R.drawable.guide_11);
        guideImgs.add(R.drawable.guide_12);
        guideImgs.add(R.drawable.guide_13);
        guideImgs.add(R.drawable.guide_14);
        guideImgs.add(R.drawable.guide_15);
        guideImgs.add(R.drawable.guide_16);
        mGuideImageView.setImageResource(guideImgs.get(index));
    }

    @OnClick(R.id.mGuideImageView)
    public void onViewClicked() {
        click = true;
        if (index >= guideImgs.size() - 1) {
            goToMain();
        } else {
            mGuideImageView.setImageResource(guideImgs.get(++index));
        }
    }

    @Override
    public void initData() {
        boolean guideNew = SpUtil.getBooleanSP(this, "guideNew", false);
        if (guideNew) {
            goToMain();
        } else {
            SpUtil.saveBooleanToSP(this, "guideNew", true);
            if (mRunnable == null) {
                mRunnable = new MyRunnable();
                mHandler.postDelayed(mRunnable, 2000);
            }
        }
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            if (index >= guideImgs.size() - 1) {
                goToMain();
            } else {
                mGuideImageView.setImageResource(guideImgs.get(click ? index : ++index));
                click = false;
                mHandler.postDelayed(this, 2000);
            }
        }
    }

    private void goToMain() {
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
            mRunnable = null;
        }
        Log.e("**********gotomain","**10");
        startActivity(new Intent(GuideNewActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
            mRunnable = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
