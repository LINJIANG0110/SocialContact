package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.utils.Tools;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-7-4.
 */

public class FamilyDialogActivity extends BaseActivity {

    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mUserName)
    TextView mUserName;
    @InjectView(R.id.mCancelTv)
    TextView mCancelTv;
    @InjectView(R.id.mSureTv)
    TextView mSureTv;
    @InjectView(R.id.mCallET)
    EditText mCallET;
    private EventRecord.ContentBean contentBean;

    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        setFinishOnTouchOutside(false);//点击空白处，窗体不关闭
        return R.layout.family_boss_pop;
    }

    @Override
    public void setViews() {
        if (getIntent() != null) {
            contentBean = ((EventRecord.ContentBean) getIntent().getSerializableExtra("contentBean"));
            mHeadImg.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl());
            mUserName.setText(TextUtils.isEmpty(contentBean.getSendNickName()) ? "" : contentBean.getSendNickName());
        }
    }

    @Override
    public void initData() {
        mCallET.setFocusable(false);
    }

    @OnClick({R.id.mCancelTv, R.id.mSureTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mCancelTv:
                finish();
                break;
            case R.id.mSureTv:
                EventBus.getDefault().post(new EventBuss(EventBuss.FAMILY_EXIT));
                Tools.enterFamily(FamilyDialogActivity.this, contentBean.getGroupId(), true);
                finish();
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
