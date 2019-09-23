package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.utils.SpUtil;
import com.qianyu.communicate.utils.Tools;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-7-16.
 */

public class AdultOpenActivity extends BaseActivity {
    @InjectView(R.id.openAdultModel)
    TextView openAdultModel;

    @Override
    public int getRootViewId() {
        return R.layout.activity_adult_open;
    }

    @Override
    public void setViews() {
        setTitleTv("青少年模式");
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.openAdultModel)
    public void onViewClicked() {
        Intent intent = new Intent(AdultOpenActivity.this, AdultPwdActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
