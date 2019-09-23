package com.qianyu.communicate.activity;

import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.User;

import org.haitao.common.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2018-3-19.
 */

public class AccountBandActivity extends BaseActivity {
    @InjectView(R.id.mNameEt)
    EditText mNameEt;
    @InjectView(R.id.mIdNumEt)
    EditText mIdNumEt;
    @InjectView(R.id.mAccountEt)
    EditText mAccountEt;
    @InjectView(R.id.ensureCardView)
    CardView ensureCardView;
    private boolean aliPay;
    private double remains;

    @Override
    public int getRootViewId() {
        return R.layout.activity_account_band;
    }

    @Override
    public void setViews() {

    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            aliPay = getIntent().getBooleanExtra("aliPay", false);
            remains = getIntent().getDoubleExtra("remains", 0);
            if (aliPay) {
                setTitleTv("支付宝账户");
                mNameEt.setHint("与提现支付宝的姓名一致");
                mIdNumEt.setHint("与提现支付宝的身份证一致");
                mAccountEt.setHint("已绑定的支付宝账号");
            } else {
                setTitleTv("微信账户");
                mNameEt.setHint("与提现微信的姓名一致");
                mIdNumEt.setHint("与提现微信的身份证一致");
                mAccountEt.setHint("已绑定的微信账号");
            }
        }
    }


    @OnClick({R.id.ensureCardView})
    public void onViewClicked(View view) {
        String name = mNameEt.getText().toString();
        String idNum = mIdNumEt.getText().toString();
        String account = mAccountEt.getText().toString();
        switch (view.getId()) {
            case R.id.ensureCardView:
                User user = MyApplication.getInstance().user;
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.shortShowToast("请先输入您的姓名...");
                }
//                else if (!personIdValidation(idNum)) {
//                    ToastUtil.shortShowToast("请先输入正确的身份证号...");
//                }
                else if (TextUtils.isEmpty(account)) {
                    ToastUtil.shortShowToast("请先输入您的账号...");
                } else {

                }
                break;
        }
    }

    public boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String regx1 = "[0-9]{17}X";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(regx1) || text.matches(reg1) || text.matches(regex);
    }

}
