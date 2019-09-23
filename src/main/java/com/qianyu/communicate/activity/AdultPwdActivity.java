package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.utils.SpUtil;
import com.qianyu.communicate.utils.Tools;

import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-7-16.
 */

public class AdultPwdActivity extends BaseActivity {
    @InjectView(R.id.mFirstPwd)
    EditText mFirstPwd;
    @InjectView(R.id.mSecondPwd)
    EditText mSecondPwd;
    @InjectView(R.id.mLastStepTv)
    TextView mLastStepTv;
    @InjectView(R.id.mEnsureTv)
    TextView mEnsureTv;

    @Override
    public void back(View view) {
        super.back(view);
        KeyBoardUtils.hideSoftInput(mFirstPwd);
        KeyBoardUtils.hideSoftInput(mSecondPwd);
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_adult_pwd;
    }

    @Override
    public void setViews() {
        setTitleTv("青少年模式");
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.mLastStepTv, R.id.mEnsureTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mLastStepTv:
                finish();
                break;
            case R.id.mEnsureTv:
                String childPwd = SpUtil.getStringSP(AdultPwdActivity.this, "childPwd","");
                KeyBoardUtils.hideSoftInput(mFirstPwd);
                KeyBoardUtils.hideSoftInput(mSecondPwd);
                String fPwd = mFirstPwd.getText().toString().trim();
                String sPwd = mSecondPwd.getText().toString().trim();
                if(TextUtils.isEmpty(fPwd)){
                    ToastUtil.shortShowToast("请先输入密码...");
                }else if(TextUtils.isEmpty(sPwd)){
                    ToastUtil.shortShowToast("请再次确认密码...");
                }else if(!TextUtils.equals(fPwd,sPwd)){
                    ToastUtil.shortShowToast("两次密码输入不一致...");
                }else {
                    if(TextUtils.isEmpty(childPwd)) {
                        SpUtil.saveStringToSP(AdultPwdActivity.this, "childPwd", fPwd);
                    }else {
                        if(!TextUtils.equals(fPwd,childPwd)){
                            ToastUtil.shortShowToast("您输入的密码不正确...");
                            return;
                        }
                    }
                    finish();
                }
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
