package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class FuncSettingActivity extends BaseActivity {


    @InjectView(R.id.logCatLogo)
    ImageView logCatLogo;
    @InjectView(R.id.logCatRv)
    RelativeLayout logCatRv;
    @InjectView(R.id.distancePrivateLogo)
    ImageView distancePrivateLogo;
    @InjectView(R.id.distancePrivateRv)
    RelativeLayout distancePrivateRv;
    @InjectView(R.id.giftFullLogo)
    ImageView giftFullLogo;
    @InjectView(R.id.giftFullAnim)
    RelativeLayout giftFullAnim;
    @InjectView(R.id.avoidAddLogo)
    ImageView avoidAddLogo;
    @InjectView(R.id.avoidAddMsg)
    RelativeLayout avoidAddMsg;
    @InjectView(R.id.avoidChatLogo)
    ImageView avoidChatLogo;
    @InjectView(R.id.avoidChatMsg)
    RelativeLayout avoidChatMsg;
    private String refuseToTalk="1";
    private String refusedInvitation="1";

    @Override
    public int getRootViewId() {
        return R.layout.activity_func_setting;
    }

    @Override
    public void setViews() {
        setTitleTv("功能设置");
    }

    @Override
    public void initData() {
        loadDatas();
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
        }
    }


    @OnClick({R.id.logCatRv, R.id.distancePrivateRv, R.id.giftFullAnim, R.id.avoidAddMsg, R.id.avoidChatMsg})
    public void onViewClicked(View view) {
        User user = MyApplication.getInstance().user;
        switch (view.getId()) {
            case R.id.logCatRv:
                break;
            case R.id.distancePrivateRv:
                break;
            case R.id.giftFullAnim:
                break;
            case R.id.avoidAddMsg:
                refusedInvitation=TextUtils.equals("1",refusedInvitation)?"2":"1";
                if (!TextUtils.isEmpty(refusedInvitation)&&avoidAddLogo!=null) {
                    avoidAddLogo.setImageResource(TextUtils.equals("1", refusedInvitation) ? R.mipmap.shezhi_guanbi : R.mipmap.shezhi_dakai);
                }
                if (user == null) {
                    return;
                }
                break;
            case R.id.avoidChatMsg:
                refuseToTalk=TextUtils.equals("1",refuseToTalk)?"2":"1";
                if (!TextUtils.isEmpty(refuseToTalk)&&avoidChatLogo!=null) {
                    avoidChatLogo.setImageResource(TextUtils.equals("1", refuseToTalk) ? R.mipmap.shezhi_guanbi : R.mipmap.shezhi_dakai);
                }
                if (user == null) {
                    return;
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
