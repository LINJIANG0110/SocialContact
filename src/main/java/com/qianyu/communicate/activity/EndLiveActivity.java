package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.FamilyOver;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class EndLiveActivity extends BaseActivity {
    @InjectView(R.id.mFinishLogo)
    FrameLayout mFinishLogo;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mSureTv)
    TextView mSureTv;
    @InjectView(R.id.mProductionEdit)
    RelativeLayout mProductionEdit;
    @InjectView(R.id.mFamilyName)
    TextView mFamilyName;
    @InjectView(R.id.mLiveTime)
    TextView mLiveTime;
    @InjectView(R.id.mTotalPerson)
    TextView mTotalPerson;
    @InjectView(R.id.mTotalIncome)
    TextView mTotalIncome;
    private int fid;
    private FamilyOver familyOver;

    @Override
    public int getRootViewId() {
        return R.layout.activity_end_live;
    }

    @Override
    public void setViews() {

    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            fid = getIntent().getIntExtra("fid", 0);
        }
    }

    @OnClick({R.id.mFinishLogo, R.id.mSureTv, R.id.mProductionEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mFinishLogo:
                finish();
                break;
            case R.id.mSureTv:
                finish();
                break;
            case R.id.mProductionEdit:
                Intent intent = new Intent(EndLiveActivity.this, ProductionEditActivity.class);
                intent.putExtra("familyOver",familyOver);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
