package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.ViewPagerAdapter;
import com.qianyu.communicate.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-8-2.
 */

public class GuideActivity extends BaseActivity {

    @InjectView(R.id.mViewPager)
    ViewPager mViewPager;
    @InjectView(R.id.mEnsureTv)
    TextView mEnsureTv;
    @InjectView(R.id.ll_dot)
    LinearLayout llDot;
    private int index=0;

    @Override
    public int getRootViewId() {
        return R.layout.activity_guide;
    }

    @Override
    public void setViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
    }

    @Override
    public void initData() {
        final List<View> mPagerList = new ArrayList<View>();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(R.drawable.login_bg);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mPagerList.add(imageView);
        }
        mViewPager.setAdapter(new ViewPagerAdapter(mPagerList, GuideActivity.this));
        llDot.removeAllViews();
        for (int i = 0; i < 3; i++) {
            llDot.addView(LayoutInflater.from(this).inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        llDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                llDot.getChildAt(index)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                llDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                index=position;
                mEnsureTv.setVisibility(position==2?View.VISIBLE:View.GONE);
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @OnClick(R.id.mEnsureTv)
    public void onViewClicked() {
        startActivity(new Intent(GuideActivity.this,SplashActiviy_.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
