package com.qianyu.communicate.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.Tools;
import com.reyun.sdk.ReYunGame;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.RegexValidateUtil;
import org.haitao.common.utils.ToastUtil;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import tv.buka.sdk.utils.DeviceUtils;

import static com.qianyu.communicate.utils.Tools.isAppInstalled;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class RegistActivity extends BaseActivity {
    @InjectView(R.id.qqLogin)
    FrameLayout qqLogin;
    @InjectView(R.id.wxLogin)
    FrameLayout wxLogin;
    @InjectView(R.id.userProtecl)
    TextView userProtecl;
    @InjectView(R.id.userProtecl_)
    TextView userProtecl_;
    @InjectView(R.id.phoneET)
    EditText phoneET;
    @InjectView(R.id.deletePhoneImg)
    ImageView deletePhoneImg;
    @InjectView(R.id.codeET)
    EditText codeET;
    @InjectView(R.id.codeSend)
    TextView codeSend;
    @InjectView(R.id.loginCardView)
    CardView loginCardView;
    private String openid;
    private String name;
    private String iconurl;
    private int sex;
    private LocationHelper locationHelper;
    private double lat;
    private double lng;
    private String address = "";

    @Override
    public int getRootViewId() {
        return R.layout.activity_regist;
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
                if (phone.length() == 11) {
                    codeSend.setTextColor(getResources().getColor(R.color.app_color));
                } else {
                    codeSend.setTextColor(getResources().getColor(R.color.text_gray));
                }
            }
        });
        Spanned spanned = Html.fromHtml("点击登录即表示您同意<font color='#59e5df'><u>用户协议</u></font>");
        userProtecl.setText(spanned);
        Spanned spanned_ = Html.fromHtml("<font color='#59e5df'><u>隐私条款</u></font>");
        userProtecl_.setText(spanned_);
    }

    @Override
    public void initData() {
        locationHelper = LocationHelper.getInstance();
        if (locationHelper != null) {
            locationHelper.setCallBack(new LocationHelper.LocationCallBack() {
                @Override
                public void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province) {
                    RegistActivity.this.lat = lat;
                    RegistActivity.this.lng = lng;
                    address = city;
                }
            });
            locationHelper.start();
        }
//        startActivity(new Intent(this, ServiceDialogActivity.class));
    }

    @OnClick({R.id.qqLogin, R.id.wxLogin, R.id.userProtecl, R.id.userProtecl_, R.id.deletePhoneImg, R.id.codeSend, R.id.loginCardView})
    public void onViewClicked(View view) {
        String deviceId = DeviceUtils.getDeviceId(RegistActivity.this);
        final String phone = phoneET.getText().toString().trim();
        final String code = codeET.getText().toString().trim();
        switch (view.getId()) {
            case R.id.qqLogin:
                SpringUtils.springAnim(qqLogin);
//                loginAuth("qq123321" + deviceId, 1);
                if (!isAppInstalled(RegistActivity.this, "com.tencent.mobileqq") && !isAppInstalled(RegistActivity.this, "com.tencent.tim")) {
                    ToastUtil.shortShowToast("请先安装QQ或者TIM客户端");
                    return;
                }
                UMShareAPI.get(RegistActivity.this).getPlatformInfo(RegistActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.wxLogin:
                SpringUtils.springAnim(wxLogin);
//                loginAuth("wx6" + deviceId, 2);
                if (!isAppInstalled(RegistActivity.this, "com.tencent.mm")) {
                    ToastUtil.shortShowToast("请先安装微信客户端");
                    return;
                }
                UMShareAPI.get(RegistActivity.this).getPlatformInfo(RegistActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.deletePhoneImg:
                phoneET.setText("");
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
                    Tools.showDialog(RegistActivity.this);
                    NetUtils.getInstance().login(4, phone, JPushInterface.getRegistrationID(this), phone, code, "" + lat, "" + lng, address, new NetCallBack() {
                        @Override
                        public void onSuccess(String result, String msg, ResultModel model) {
                            Log.e("登录返回结果a",code+"**"+msg+result);
                            User user = model.getModel();
                            JPushInterface.setAlias(RegistActivity.this, (int) user.getUserId(), user.getUserId() + "");
                            MyApplication.getInstance().saveUser(user);
                            ReYunGame.setLoginWithAccountID(user.getPhone(), user.getLevel(), "qianyu", user.getNickName(), user.getSex() == 1 ? ReYunGame.Gender.M : ReYunGame.Gender.F, user.getAge() + "");
                            if (!EMClient.getInstance().isLoggedInBefore()) {
                                loginHX(user);
                            } else {
                                Tools.dismissWaitDialog();
                                ToastUtil.shortShowToast(msg);
                                Intent intent = new Intent(RegistActivity.this, GuideNewActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFail(String code, String msg, String result) {
                            Log.e("登录返回结果",code+"**"+msg);
                            Tools.dismissWaitDialog();
                            ToastUtil.shortShowToast(msg);
                            //code 205 去走绑定手机的逻辑
                            //code 1001 去补全用户基本信息
                            switch (code) {
                                case "205":

                                    break;
                                case "1001":
                                    Intent intent = new Intent(RegistActivity.this, InfoCompleteActivity.class);
                                    UserEntity userEntity = new UserEntity();
                                    userEntity.setPhone(phone);
                                    userEntity.setCode(code);
                                    userEntity.setOtherToken(phone);
                                    userEntity.setOtherType(4);
                                    intent.putExtra("userEntity", userEntity);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    }, User.class);
                }
                break;
            case R.id.userProtecl:
                startActivity(new Intent(RegistActivity.this, UserServiceActivity_.class));
                break;
            case R.id.userProtecl_:
                startActivity(new Intent(RegistActivity.this, UserServiceActivity2.class));
                break;
        }
    }

    private void loginAuth(final String openId, final int authType) {
        AppLog.e("============jpushId===========" + JPushInterface.getRegistrationID(this));
        Tools.showDialog(this);
        NetUtils.getInstance().login(authType, openId, JPushInterface.getRegistrationID(this), "", "", "" + lat, "" + lng, address, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                User user = model.getModel();
                JPushInterface.setAlias(RegistActivity.this, (int) user.getUserId(), user.getUserId() + "");
                MyApplication.getInstance().saveUser(user);
                ReYunGame.setLoginWithAccountID(user.getPhone(), user.getLevel(), "qianyu", user.getNickName(), user.getSex() == 1 ? ReYunGame.Gender.M : ReYunGame.Gender.F, user.getAge() + "");
                if (!EMClient.getInstance().isLoggedInBefore()) {
                    loginHX(user);
                } else {
                    Tools.dismissWaitDialog();
                    ToastUtil.shortShowToast(msg);
                    Intent intent = new Intent(RegistActivity.this, GuideNewActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
//                code 205 去走绑定手机的逻辑
//                code 1001 去补全用户基本信息
                switch (code) {
                    case "205":
                        Intent intent1 = new Intent(RegistActivity.this, LoginActivity.class);
                        UserEntity userEntity1 = new UserEntity();
                        userEntity1.setOtherType(authType);
                        userEntity1.setOtherToken(openId);
                        userEntity1.setNickName(name);
                        userEntity1.setHeadUrl(iconurl);
                        userEntity1.setSex(sex);
                        intent1.putExtra("userEntity", userEntity1);
                        startActivity(intent1);
                        break;
                    case "1001":
                        Intent intent2 = new Intent(RegistActivity.this, InfoCompleteActivity.class);
                        UserEntity userEntity2 = new UserEntity();
                        userEntity2.setOtherType(authType);
                        userEntity2.setOtherToken(openId);
                        userEntity2.setNickName(name);
                        userEntity2.setHeadUrl(iconurl);
                        userEntity2.setSex(sex);
                        intent2.putExtra("userEntity", userEntity2);
                        startActivity(intent2);
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
                Intent intent = new Intent(RegistActivity.this, GuideNewActivity.class);
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
                        if (code == 200) {
                            Intent intent = new Intent(RegistActivity.this, GuideNewActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
            Toast.makeText(RegistActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
            openid = map.get("openid");
            name = Tools.stringFilter(map.get("name"));
            iconurl = map.get("iconurl");
            String gender = map.get("gender");
            sex = TextUtils.equals("男", gender) ? 1 : 2;
            AppLog.e("=======map=========" + map.toString());
            AppLog.e("======openid=======" + openid + "========name=======" + name + "==========iconurl============" + iconurl);
            switch (platform) {
                case QQ:
                    loginAuth(openid, 1);
                    break;
                case WEIXIN:
                    loginAuth(openid, 2);
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(RegistActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
            AppLog.e("======action=======" + action + "========t=======" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(RegistActivity.this, "登录取消!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(RegistActivity.this).onActivityResult(requestCode, resultCode, data);
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
                public void onFail(String result, String msg, String info) {
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
                mVertiyTv.setText("倒计时：" + millisUntilFinished / 1000 + "秒");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationHelper != null) {
            locationHelper.stop();
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.onFinish();
        }
    }

}
