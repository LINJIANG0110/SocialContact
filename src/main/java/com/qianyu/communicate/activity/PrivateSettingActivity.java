package com.qianyu.communicate.activity;

import android.content.Intent;
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

public class PrivateSettingActivity extends BaseActivity {

    @InjectView(R.id.avoidFriendLogo)
    ImageView avoidFriendLogo;
    @InjectView(R.id.avoidFriendRequest)
    RelativeLayout avoidFriendRequest;
    @InjectView(R.id.avoidStrangersLogo)
    ImageView avoidStrangersLogo;
    @InjectView(R.id.avoidStrangersChat)
    RelativeLayout avoidStrangersChat;
    @InjectView(R.id.blackNameRv)
    RelativeLayout blackNameRv;
    private String contactAddReject="1";
    private String refuseStranger="1";

    @Override
    public int getRootViewId() {
        return R.layout.activity_private_setting;
    }

    @Override
    public void setViews() {
        setTitleTv("隐私设置");
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


    @OnClick({R.id.avoidFriendRequest, R.id.avoidStrangersChat, R.id.blackNameRv})
    public void onViewClicked(View view) {
        User user = MyApplication.getInstance().user;
        switch (view.getId()) {
            case R.id.avoidFriendRequest:
                contactAddReject=TextUtils.equals("1",contactAddReject)?"2":"1";
                if (!TextUtils.isEmpty(contactAddReject)&&avoidFriendLogo!=null) {
                    avoidFriendLogo.setImageResource(TextUtils.equals("1", contactAddReject) ? R.mipmap.shezhi_guanbi : R.mipmap.shezhi_dakai);
                }
                if (user == null) {
                    return;
                }
                break;
            case R.id.avoidStrangersChat:
                refuseStranger=TextUtils.equals("1",refuseStranger)?"2":"1";
                if (!TextUtils.isEmpty(refuseStranger)&&avoidStrangersLogo!=null) {
                    avoidStrangersLogo.setImageResource(TextUtils.equals("1", refuseStranger) ? R.mipmap.shezhi_guanbi : R.mipmap.shezhi_dakai);
                }
                if (user == null) {
                    return;
                }
                break;
            case R.id.blackNameRv:
                startActivity(new Intent(PrivateSettingActivity.this,BlackListActivity.class));
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
