package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
 * Created by JavaDog on 2019-7-4.
 */

public class AdultDialogActivity extends BaseActivity {

    @InjectView(R.id.goAdultModel)
    LinearLayout goAdultModel;
    @InjectView(R.id.mEnsureTv)
    TextView mEnsureTv;
    @InjectView(R.id.noShowHint)
    TextView noShowHint;

    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setFinishOnTouchOutside(false);//点击空白处，窗体不关闭
        return R.layout.activity_adult_dialog;
    }

    @Override
    public void setViews() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.goAdultModel, R.id.mEnsureTv, R.id.noShowHint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goAdultModel:
                Intent intent = new Intent(AdultDialogActivity.this, AdultOpenActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mEnsureTv:
                SpUtil.saveBooleanToSP(AdultDialogActivity.this, "child", false);
                finish();
                break;
            case R.id.noShowHint:
                Intent intent2 = new Intent(AdultDialogActivity.this, AdultCloseActivity.class);
                startActivity(intent2);
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
