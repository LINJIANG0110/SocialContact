package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.EventRecord;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-7-4.
 */

public class GuardDialogActivity extends BaseActivity {

    @InjectView(R.id.mGuardLogo)
    ImageView mGuardLogo;
    @InjectView(R.id.mGuardTitle)
    TextView mGuardTitle;
    @InjectView(R.id.mGuardContent)
    TextView mGuardContent;
    @InjectView(R.id.mEnsureTv)
    TextView mEnsureTv;
    @InjectView(R.id.mOperateTv)
    TextView mOperateTv;
    private EventRecord.ContentBean contentBean;

    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        setFinishOnTouchOutside(false);//点击空白处，窗体不关闭
        return R.layout.guard_result_pop;
    }

    @Override
    public void setViews() {
        if (getIntent() != null) {
            contentBean = ((EventRecord.ContentBean) getIntent().getSerializableExtra("contentBean"));
        }
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.mEnsureTv, R.id.mOperateTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mEnsureTv:
                break;
            case R.id.mOperateTv:
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
