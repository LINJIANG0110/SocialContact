package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.LocationHelper;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.RegexValidateUtil;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by JavaDog on 2018-4-17.
 */

public class PhoneBindingActivity extends BaseActivity {
    @InjectView(R.id.backFL)
    FrameLayout backFL;
    @InjectView(R.id.phoneET)
    EditText phoneET;
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
    private LocationHelper locationHelper;
    private String address;
    private String authType;
    private String authId;
    private String nickName;
    private String imgPath;
    private String sex;
    private String codeNum;

    @Override
    public int getRootViewId() {
        return R.layout.activity_phone_binding;
    }

    @Override
    public void setViews() {
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
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            authType = getIntent().getStringExtra("authType");
            authId = getIntent().getStringExtra("authId");
            nickName = getIntent().getStringExtra("nickName");
            imgPath = getIntent().getStringExtra("imgPath");
            sex = getIntent().getStringExtra("sex");
            if (!TextUtils.isEmpty(sex)) {
                sex = TextUtils.equals("0", sex) ? "男" : (TextUtils.equals("1", sex) ? "女" : sex);
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
              if (!TextUtils.equals(authType, "4") && !RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtil.shortShowToast("请先输入正确的手机号码...");
                } else if (!TextUtils.equals(authType, "4") && !TextUtils.equals(codeNum, code)) {
                    ToastUtil.shortShowToast("请先输入正确的验证码...");
                }  else {
                    updateUser(nickName, phone);
                }
                break;
            case R.id.deletePhoneImg:
                phoneET.setText("");
                break;
        }
    }

    private void updateUser(String nickName, String phone) {
        if (!MyApplication.getInstance().isLogin()) {
            return;
        }
        User user = MyApplication.getInstance().user;
        final String token = user.getToken();
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
                mVertiyTv.setBackground(getResources().getDrawable(R.drawable.shape_solid_gray));
                mVertiyTv.setText(millisUntilFinished / 1000 + "秒后重发");
            }

            @Override
            public void onFinish() {
                flag = false;
                mVertiyTv.setBackground(getResources().getDrawable(R.drawable.shape_cornor_info_corlor));
                mVertiyTv.setText("发送验证码");
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
