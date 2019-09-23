package com.qianyu.communicate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;

import butterknife.ButterKnife;

public class TopicEvalueDialog extends BaseActivity {
    @Override
    public int getRootViewId() {
        return R.layout.activity_topic_evalue_dialog;
    }

    @Override
    public void setViews() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }
}
