package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class FamilyEditActivity extends BaseActivity {

    @InjectView(R.id.familyPhoto)
    ImageView familyPhoto;
    @InjectView(R.id.familyName)
    EditText familyName;
    @InjectView(R.id.familyWelcome)
    EditText familyWelcome;
    @InjectView(R.id.familyWelcomeLogo)
    ImageView familyWelcomeLogo;
    @InjectView(R.id.familyDetail)
    EditText familyDetail;
    @InjectView(R.id.familyDetailLogo)
    ImageView familyDetailLogo;
    @InjectView(R.id.submitEdit)
    CardView submitEdit;

    @Override
    public int getRootViewId() {
        return R.layout.activity_family_edit;
    }

    @Override
    public void setViews() {
        setTitleTv("编辑家族资料");
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.familyPhoto, R.id.submitEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.familyPhoto:
                break;
            case R.id.submitEdit:
                startActivity(new Intent(FamilyEditActivity.this, FamilyMemberDetailActivity.class));
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
