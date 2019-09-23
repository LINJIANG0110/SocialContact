package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyu.chatuidemo.DemoHelper;
import com.qianyu.chatuidemo.db.DemoDBManager;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserEntity;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.LocationHelper;
import com.qianyu.communicate.utils.Tools;

import com.qianyu.communicate.base.BaseActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;

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

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.backFL)
    FrameLayout backFL;
    @InjectView(R.id.phoneET)
    EditText phoneET;
    @InjectView(R.id.codeET)
    EditText codeET;
    @InjectView(R.id.codeSend)
    TextView codeSend;
    @InjectView(R.id.loginCardView)
    CardView loginCardView;
    @InjectView(R.id.deletePhoneImg)
    ImageView deletePhoneImg;
    @InjectView(R.id.mLoginTextView)
    TextView mLoginTextView;
    private UserEntity userEntity;

    @Override
    public int getRootViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void setViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        phoneET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = phoneET.getText().toString().trim();
                if(phone.length()==11){
                    codeSend.setTextColor(getResources().getColor(R.color.app_color));
                }else {
                    codeSend.setTextColor(getResources().getColor(R.color.text_gray));
                }
            }
        });
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            userEntity = ((UserEntity) getIntent().getSerializableExtra("userEntity"));
            mLoginTextView.setText("手机号绑定");
        }
    }


    @OnClick({R.id.backFL,R.id.codeSend, R.id.loginCardView, R.id.deletePhoneImg})
    public void onViewClicked(View view) {
        final String phone = phoneET.getText().toString().trim();
        final String code = codeET.getText().toString().trim();
        switch (view.getId()) {
            case R.id.backFL:
                finish();
                break;
            case R.id.codeSend:
                KeyBoardUtils.hideSoftInput(phoneET);
                KeyBoardUtils.hideSoftInput(codeET);
                if (!RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtil.shortShowToast("请先输入正确的手机号码...");
                    return;
                }
                getCode(codeSend, phone);
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
            case R.id.deletePhoneImg:
                phoneET.setText("");
                break;
        }
    }

    private void loginAuth(final String phone, final String code) {
        AppLog.e("============jpushId===========" + JPushInterface.getRegistrationID(this));
        Tools.showDialog(this);
        NetUtils.getInstance().userBind(userEntity.getOtherType(), userEntity.getOtherToken(),
                JPushInterface.getRegistrationID(this), phone, code, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        User user = model.getModel();
                        JPushInterface.setAlias(LoginActivity.this, (int) user.getUserId(), user.getUserId() + "");
                        MyApplication.getInstance().saveUser(user);
                        if (!EMClient.getInstance().isLoggedInBefore()) {
                            loginHX(user);
                        } else {
                            Tools.dismissWaitDialog();
                            ToastUtil.shortShowToast(msg);
                            Intent intent = new Intent(LoginActivity.this, GuideNewActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        Tools.dismissWaitDialog();
                        ToastUtil.shortShowToast(msg);
//                            code 1000 该微信号绑定过其他的账号了
//                            code 1001 去保存用户的基本信息
//                            code 1002 这个手机号已经被其它的同类型的账号绑定了 如微信绑定，提示该手机号被另一个微信号绑定了
                        switch (code) {
                            case "1000":

                                break;
                            case "1001":
                                Intent intent = new Intent(LoginActivity.this, InfoCompleteActivity.class);
                                userEntity.setPhone(phone);
                                userEntity.setCode(code);
                                intent.putExtra("userEntity", userEntity);
                                startActivity(intent);
                                break;
                            case "1002":

                                break;
                        }
                    }
                }, User.class);
    }


    public void loginHX(final User user) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Tools.dismissWaitDialog();
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user.getPhone())) {
            Tools.dismissWaitDialog();
            MyApplication.getInstance().saveUser(null);
            ToastUtil.shortShowToast("环信账号为空");
            return;
        }
        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();
        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(user.getNickName());
        EMClient.getInstance().login(user.getPhone(), "qianyu123456", new EMCallBack() {

            @Override
            public void onSuccess() {
                Tools.dismissWaitDialog();
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        user.getNickName());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                Intent intent = new Intent(LoginActivity.this, GuideNewActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                AppLog.e("=============hx=login: onProgress==================");
            }

            @Override
            public void onError(final int code, final String message) {
                AppLog.e("=============hx=login: onError==================" + code);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Tools.dismissWaitDialog();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
                mVertiyTv.setTextColor(getResources().getColor(R.color.text_gray));
                mVertiyTv.setText("倒计时："+millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                flag = false;
                mVertiyTv.setTextColor(getResources().getColor(R.color.app_color));
                mVertiyTv.setText("获取验证码");
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
