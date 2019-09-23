package com.qianyu.communicate.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.utils.SpUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2018-4-26.
 */

public class IdentifyResultActivity extends BaseActivity {

    @InjectView(R.id.mStatusImg)
    ImageView mStatusImg;
    @InjectView(R.id.mStatusTv)
    TextView mStatusTv;
    @InjectView(R.id.mFailTv)
    TextView mFailTv;
    @InjectView(R.id.mEnsureTv)
    CardView mEnsureTv;
    private String status;
    private boolean live;

    @Override
    public int getRootViewId() {
        return R.layout.activity_identify_result;
    }

    @Override
    public void setViews() {
        setTitleTv("提交审核");
        mFailTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mFailTv.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            status = getIntent().getStringExtra("status");
            live = getIntent().getBooleanExtra("live", false);
            switch (status) {
                case "2":
                    mStatusTv.setText("正在审核中，1-2个工作日内审核完毕");
                    mStatusImg.setImageResource(R.drawable.tijiaoshenhe_export_export);
                    mFailTv.setVisibility(View.GONE);
                    mEnsureTv.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    mStatusTv.setText("认证信息已通过审核，可以体验更多功能了哟！");
                    mStatusImg.setImageResource(R.drawable.shenheyitongguo_export);
                    mFailTv.setVisibility(View.GONE);
                    mEnsureTv.setVisibility(View.VISIBLE);
                    break;
                case "4":
                    mStatusTv.setText("很抱歉！审核未通过，建议重新编辑认证信息");
                    mStatusImg.setImageResource(R.drawable.shenheweitongguo_export);
                    mFailTv.setVisibility(View.VISIBLE);
                    mEnsureTv.setVisibility(View.GONE);
                    break;
            }
            SpUtil.saveBooleanToSP(IdentifyResultActivity.this, status, true);
            SpUtil.saveStringToSP(IdentifyResultActivity.this, "status", status);
        }
    }

    @OnClick({R.id.mFailTv, R.id.mEnsureTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mFailTv:
                startActivity(new Intent(IdentifyResultActivity.this, PersonalIdentifyActivity.class));
                finish();
                break;
            case R.id.mEnsureTv:
                startActivity(new Intent(IdentifyResultActivity.this, live ? ChatRoomCretaeActivity.class : LiveWorkPlaceActivity.class));
                finish();
                break;
        }
    }
}
