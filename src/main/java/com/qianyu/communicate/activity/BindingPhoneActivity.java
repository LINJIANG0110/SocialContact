package com.qianyu.communicate.activity;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;

import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.RegexValidateUtil;
import org.haitao.common.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2018-4-24.
 */

public class BindingPhoneActivity extends BaseActivity {
    @InjectView(R.id.mPhoneEt)
    EditText mPhoneEt;
    @InjectView(R.id.mDeleteLogo)
    ImageView mDeleteLogo;
    @InjectView(R.id.sendCodeCardView)
    CardView sendCodeCardView;
    private String authType;
    private String authId;
    private String nickName;
    private String imgPath;
    private String sex;

    @Override
    public int getRootViewId() {
        return R.layout.activity_binding_phone;
    }

    @Override
    public void setViews() {
        setTitleTv("绑定手机号");
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            authType = getIntent().getStringExtra("authType");
            authId = getIntent().getStringExtra("authId");
            nickName = getIntent().getStringExtra("nickName");
            imgPath = getIntent().getStringExtra("imgPath");
            sex = getIntent().getStringExtra("sex");
        }
    }

    @OnClick({R.id.mDeleteLogo, R.id.sendCodeCardView})
    public void onViewClicked(View view) {
        final String phone = mPhoneEt.getText().toString().trim();
        switch (view.getId()) {
            case R.id.mDeleteLogo:
                mPhoneEt.setText("");
                break;
            case R.id.sendCodeCardView:
                if (!RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtil.shortShowToast("请先输入正确的手机号码...");
                }else {
                    KeyBoardUtils.hideSoftInput(mPhoneEt);
                    Intent intent = new Intent(BindingPhoneActivity.this, CodeNumberActivity.class);
                    intent.putExtra("phone",phone);
                    intent.putExtra("authType", authType);
                    intent.putExtra("authId", authId);
                    intent.putExtra("nickName", nickName);
                    intent.putExtra("imgPath", imgPath);
                    intent.putExtra("sex", sex);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
