package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.qianyu.chatuidemo.DemoHelper;
import com.qianyu.chatuidemo.db.DemoDBManager;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserEntity;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.LocationHelper;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.RegexValidateUtil;
import org.haitao.common.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class BindActivity extends BaseActivity {

    @InjectView(R.id.backFL)
    FrameLayout backFL;
    @InjectView(R.id.phoneET)
    TextView phoneET;
    @InjectView(R.id.phoneLL)
    LinearLayout phoneLL;
    @InjectView(R.id.codeET)
    EditText codeET;
    @InjectView(R.id.codeSend)
    TextView codeSend;
    @InjectView(R.id.codeLL)
    LinearLayout codeLL;
    @InjectView(R.id.loginCardView)
    CardView loginCardView;
    @InjectView(R.id.notLoginImg)
    ImageView notLoginImg;
    @InjectView(R.id.deletePhoneImg)
    ImageView deletePhoneImg;
    @InjectView(R.id.mLoginTextView)
    TextView mLoginTextView;
    private UserEntity userEntity;

    @Override
    public int getRootViewId() {
        return R.layout.activity_bind;
    }

    @Override
    public void setViews() {

    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            userEntity = ((UserEntity) getIntent().getSerializableExtra("userEntity"));
            mLoginTextView.setText(userEntity.getOtherType() == 4 ? "登录" : "手机号绑定");
            User user = MyApplication.getInstance().user;
            if (user != null) {
                phoneET.setText(TextUtils.isEmpty(user.getPhone())?"":user.getPhone());
            }
        }
    }


    @OnClick({R.id.backFL, R.id.phoneLL, R.id.codeSend, R.id.codeLL, R.id.loginCardView, R.id.notLoginImg, R.id.deletePhoneImg})
    public void onViewClicked(View view) {
        final String phone = phoneET.getText().toString().trim();
        final String code = codeET.getText().toString().trim();
        switch (view.getId()) {
            case R.id.backFL:
                finish();
                break;
            case R.id.phoneLL:
                break;
            case R.id.codeSend:
                KeyBoardUtils.hideSoftInput(codeET);
                if (!RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtil.shortShowToast("请先输入正确的手机号码...");
                    return;
                }
                getCode(codeSend, phone);
                break;
            case R.id.codeLL:
                break;
            case R.id.loginCardView:
                if (!RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtil.shortShowToast("请先输入正确的手机号码...");
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.shortShowToast("请先输入验证码...");
                } else {
                    loginAuth(phone, code);
                }
                break;
            case R.id.notLoginImg:
                break;
            case R.id.deletePhoneImg:
                phoneET.setText("");
                break;
        }
    }

    private void loginAuth(final String phone, final String code) {
        NetUtils.getInstance().userBindThird(userEntity.getOtherType(), userEntity.getOtherToken(),
                JPushInterface.getRegistrationID(this), phone, code, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        ToastUtil.shortShowToast(msg);
                        finish();
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        Tools.dismissWaitDialog();
                        ToastUtil.shortShowToast(msg);
//                            code 1000 该微信号绑定过其他的账号了
//                            code 1001 去保存用户的基本信息
//                            code 1002 这个手机号已经被其它的同类型的账号绑定了 如微信绑定，提示该手机号被另一个微信号绑定了
                    }
                }, User.class);
    }


    private boolean flag;
    private CountDownTimer mTimer;// 倒计时；

    /**
     * 获取验证码
     *
     * @param phone
     */
    private void getCode(final TextView mVertiyTv, String phone) {
        if (flag) {
            ToastUtil.shortShowToast("验证码已经发送过了");
        } else {
            mVertiyTv.setClickable(false);
//            Tools.showDialog(this);
            NetUtils.getInstance().sendCode(phone, DeviceUtils.getDeviceId(this), new NetCallBack() {

                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    Tools.dismissWaitDialog();
                    ToastUtil.shortShowToast(msg);
                    if (mVertiyTv != null) {
                        flag = true;
                        mVertiyTv.setClickable(true);
                        startTimmer(mVertiyTv);
                    }
                }

                @Override
                public void onFail(String result, String info, String msg) {
                    Tools.dismissWaitDialog();
                    if (mVertiyTv != null) {
                        mVertiyTv.setClickable(true);
                    }
                    ToastUtil.shortShowToast(msg);
                }
            }, null);
        }
    }

    private void startTimmer(final TextView mVertiyTv) {
        mTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mVertiyTv.setBackground(getResources().getDrawable(R.drawable.shape_solid_gray));
                mVertiyTv.setText(millisUntilFinished / 1000 + "秒后重发");
            }

            @Override
            public void onFinish() {
                flag = false;
                mVertiyTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_app_corlor));
                mVertiyTv.setText("发送验证码");
            }
        };
        mTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.onFinish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

}
