package com.qianyu.communicate.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class AboutUsActivity extends BaseActivity {
    @InjectView(R.id.mServiceTv)
    TextView mServiceTv;

    @Override
    public int getRootViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void setViews() {
        setTitleTv("关于");
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.mServiceTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mServiceTv:
                startActivity(new Intent(AboutUsActivity.this,UserServiceActivity.class));
                break;
        }
    }
}
