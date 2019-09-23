package com.qianyu.communicate.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyu.chatuidemo.DemoHelper;
import com.qianyu.chatuidemo.db.DemoDBManager;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.LocationHelper;
import com.qianyu.communicate.utils.Tools;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by JavaDog on 2018-4-24.
 */

public class CodeNumberActivity extends BaseActivity {
    @InjectView(R.id.mPhoneTv)
    TextView mPhoneTv;
    @InjectView(R.id.mTv1)
    TextView mTv1;
    @InjectView(R.id.mTv2)
    TextView mTv2;
    @InjectView(R.id.mTv3)
    TextView mTv3;
    @InjectView(R.id.mTv4)
    TextView mTv4;
    @InjectView(R.id.mTv5)
    TextView mTv5;
    @InjectView(R.id.mTv6)
    TextView mTv6;
    @InjectView(R.id.mPassWord)
    EditText mPassWord;
    @InjectView(R.id.reSendTv)
    TextView reSendTv;
    @InjectView(R.id.ensureCardView)
    CardView ensureCardView;
    private String pass;
    private TextView[] textViews;
    private String phone;
    private String codeNum;
    private String authType;
    private String authId;
    private String nickName;
    private String imgPath;
    private String sex;
    private LocationHelper locationHelper;
    private String address;

    @Override
    public int getRootViewId() {
        return R.layout.activity_code_num;
    }

    @Override
    public void setViews() {
        setTitleTv("短信验证码");
        if (getIntent() != null) {
            phone = getIntent().getStringExtra("phone");
            mPhoneTv.setText("验证码已发送至 +86 " + phone);
            authType = getIntent().getStringExtra("authType");
            authId = getIntent().getStringExtra("authId");
            nickName = getIntent().getStringExtra("nickName");
            imgPath = getIntent().getStringExtra("imgPath");
            sex = getIntent().getStringExtra("sex");
            if (!TextUtils.isEmpty(sex)) {
                sex = TextUtils.equals("0", sex) ? "男" : (TextUtils.equals("1", sex) ? "女" : sex);
            }
        }

        reSendTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        reSendTv.getPaint().setAntiAlias(true);//抗锯齿

        textViews = new TextView[]{mTv1, mTv2, mTv3, mTv4, mTv5, mTv6};
        mPassWord.addTextChangedListener(mTextWatcher);
        mPassWord.setOnKeyListener(keyListener);
    }

    @Override
    public void initData() {
        locationHelper = LocationHelper.getInstance();
        if (locationHelper != null) {
            locationHelper.setCallBack(new LocationHelper.LocationCallBack() {
                @Override
                public void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province) {
                    address = city;
                    AppLog.e("=========address=========" + address);
                }
            });
            locationHelper.start();
        }
        getCode(reSendTv, phone);
    }

    @OnClick({R.id.reSendTv, R.id.ensureCardView})
    public void onViewClicked(View view) {
        final String code = mPassWord.getText().toString().trim();
        switch (view.getId()) {
            case R.id.reSendTv:
                getCode(reSendTv, phone);
                break;
            case R.id.ensureCardView:
                KeyBoardUtils.hideSoftInput(mPassWord);
                if (!TextUtils.equals(codeNum, code)) {
                    ToastUtil.shortShowToast("请先输入正确的验证码...");
                } else {
                    if (TextUtils.equals("4", authType)) {
                        loginAuth(phone, code);
                    } else {
                        updateUser();
                    }
                }
                break;
        }
    }

    private void loginAuth(final String phone, String code) {
//        Tools.showDialog(this);
//        NetUtils.getInstance().login(phone, "4", "", code, new NetCallBack() {
//            @Override
//            public void onSuccess(String result, String msg, ResultModel model) {
//                User user = model.getModel();
//                MyApplication.getInstance().saveUser(user);
////                loginHX(user, phone);
//                Intent intent = new Intent(CodeNumberActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onFail(String code, String msg, String result) {
//                Tools.dismissWaitDialog();
//                ToastUtil.shortShowToast(msg);
//            }
//        }, User.class);
    }


    public void loginHX(final User user, final String phone) {
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
        DemoHelper.getInstance().setCurrentUserName(user.getPhone());
        EMClient.getInstance().login(user.getPhone(), "bukabuka123456", new EMCallBack() {

            @Override
            public void onSuccess() {
                Tools.dismissWaitDialog();
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        MyApplication.getInstance().currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                if (TextUtils.isEmpty(user.getNickName())||TextUtils.equals(user.getNickName(),"用户"+user.getUserNum())) {
//                    Intent intent = new Intent(LoginActivity.this, InfoCompleteActivity.class);
//                    intent.putExtra("authType", "4");
//                    intent.putExtra("authId", phone);
//                    startActivity(intent);
//                } else {
                Intent intent = new Intent(CodeNumberActivity.this, MainActivity.class);
                startActivity(intent);
//                }
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
                        if(code==200){
                            Intent intent = new Intent(CodeNumberActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void updateUser() {
        if (!MyApplication.getInstance().isLogin()) {
            return;
        }
        final User user = MyApplication.getInstance().user;
        final String token = user.getToken();
    }

    public void logout(final User user) {
        DemoHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
//                MyApplication.getInstance().saveUser(null);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                AppLog.e("=============hx=logout: onProgress==================");
            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(CodeNumberActivity.this, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() == 0) {
                pass = s.toString().trim();
                return;
            }
            pass = s.toString().trim();
            setTextValue(s.toString());
        }
    };

    View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL
                    && event.getAction() == KeyEvent.ACTION_UP) {
                delTextValue();
                return true;
            }
            return false;
        }
    };

    private void setTextValue(String str) {
        final int len = str.length();
        if (len <= 6) {
            textViews[len - 1].setVisibility(View.VISIBLE);
            textViews[len - 1].setText("" + str.charAt(len - 1));
        }
    }

    private void delTextValue() {
        int len = pass.length();
        textViews[len].setVisibility(View.INVISIBLE);
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
            NetUtils.getInstance().sendCode(phone, DeviceUtils.getDeviceId(this),new NetCallBack() {

                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    Tools.dismissWaitDialog();
                    ToastUtil.shortShowToast(msg);
                    if (mVertiyTv != null) {
//                        codeNum = ((String) model.getModel());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            codeNum = jsonObject.getString("result");
                            flag = true;
                            mVertiyTv.setClickable(true);
                            startTimmer(mVertiyTv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
//                mVertiyTv.setBackground(getResources().getDrawable(R.drawable.shape_solid_gray));
                mVertiyTv.setText(millisUntilFinished / 1000 + "秒后重发");
            }

            @Override
            public void onFinish() {
                flag = false;
//                mVertiyTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_app_corlor));
                mVertiyTv.setText("重新发送");
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


}
