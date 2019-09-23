package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.BindState;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserEntity;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.qianyu.communicate.utils.Tools.isAppInstalled;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class CountBindingActivity extends BaseActivity {

    @InjectView(R.id.mPhoneTv)
    TextView mPhoneTv;
    @InjectView(R.id.mWxTv)
    TextView mWxTv;
    @InjectView(R.id.mQQTv)
    TextView mQQTv;
    @InjectView(R.id.mSinaTv)
    TextView mSinaTv;
    @InjectView(R.id.mPhoneRL)
    RelativeLayout mPhoneRL;
    @InjectView(R.id.mWXRL)
    RelativeLayout mWXRL;
    @InjectView(R.id.mQQRL)
    RelativeLayout mQQRL;
    @InjectView(R.id.mSinaRL)
    RelativeLayout mSinaRL;
    private int authType;
    private BindState bindState;

    @Override
    public int getRootViewId() {
        return R.layout.activity_count_binding;
    }

    @Override
    public void setViews() {
        setTitleTv("账号绑定");
    }

    @Override
    public void initData() {
        final User user = MyApplication.getInstance().user;
        if (user != null) {
            mPhoneTv.setText(TextUtils.isEmpty(user.getPhone()) ? "去绑定" : user.getPhone());
            NetUtils.getInstance().bindState(user.getUserId(), new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    bindState = (BindState) model.getModel();
                    if (bindState != null) {
                        mWxTv.setText(bindState.getBindingWX() == 0 ? "去绑定" : user.getNickName());
                        mQQTv.setText(bindState.getBindingQQ() == 0 ? "去绑定" : user.getNickName());
                        mSinaTv.setText(bindState.getBindingWB() == 0 ? "去绑定" : user.getNickName());
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, BindState.class);
        }
    }

    @OnClick({R.id.mPhoneRL, R.id.mWXRL, R.id.mQQRL, R.id.mSinaRL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mPhoneRL:
                break;
            case R.id.mWXRL:
                if (bindState != null&&bindState.getBindingWX()==0) {
                    if (!isAppInstalled(CountBindingActivity.this, "com.tencent.mm")) {
                        ToastUtil.shortShowToast("请先安装微信客户端");
                        return;
                    }
                    authType = 2;
                    UMShareAPI.get(CountBindingActivity.this).getPlatformInfo(CountBindingActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                }
                break;
            case R.id.mQQRL:
                if (bindState != null&&bindState.getBindingQQ()==0) {
                    if (!isAppInstalled(CountBindingActivity.this, "com.tencent.mobileqq") && !isAppInstalled(CountBindingActivity.this, "com.tencent.tim")) {
                        ToastUtil.shortShowToast("请先安装QQ或者TIM客户端");
                        return;
                    }
                    authType = 1;
                    UMShareAPI.get(CountBindingActivity.this).getPlatformInfo(CountBindingActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                }
                break;
            case R.id.mSinaRL:
                if (bindState != null&&bindState.getBindingWB()==0) {
                    if (!isAppInstalled(CountBindingActivity.this, "com.sina.weibo")) {
                        ToastUtil.shortShowToast("请先安装新浪微博客户端");
                        return;
                    }
                    authType = 3;
                    UMShareAPI.get(CountBindingActivity.this).getPlatformInfo(CountBindingActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                }
                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
            Toast.makeText(CountBindingActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
            String gender = map.get("gender");
            AppLog.e("=======map=========" + map.toString());
            Intent intent = new Intent(CountBindingActivity.this, BindActivity.class);
            UserEntity userEntity = new UserEntity();
            userEntity.setOtherType(authType);
            userEntity.setOtherToken(map.get("openid"));
            userEntity.setNickName(map.get("name"));
            userEntity.setHeadUrl(map.get("iconurl"));
            userEntity.setSex(TextUtils.equals("男", gender) ? 1 : 2);
            intent.putExtra("userEntity", userEntity);
            startActivity(intent);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(CountBindingActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
            AppLog.e("======action=======" + action + "========t=======" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(CountBindingActivity.this, "登录取消!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(CountBindingActivity.this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
