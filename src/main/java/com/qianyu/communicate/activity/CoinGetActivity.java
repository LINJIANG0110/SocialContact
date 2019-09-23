package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.BaseWebActivity;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserGroups;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.APPURL;

import org.haitao.common.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class CoinGetActivity extends BaseActivity {
    @InjectView(R.id.currentFuBow)
    TextView currentFuBow;
    @InjectView(R.id.toBeManager)
    LinearLayout toBeManager;
    @InjectView(R.id.shareTask)
    LinearLayout shareTask;
    @InjectView(R.id.getFuBag)
    LinearLayout getFuBag;
    private long coin;

    @Override
    public int getRootViewId() {
        return R.layout.activity_coin_get;
    }

    @Override
    public void setViews() {
        setTitleTv("获取金币");
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            coin = getIntent().getLongExtra("coin", 0);
            currentFuBow.setText(NumberUtils.rounLong(coin));
            setResult(101);
        }
    }

    @OnClick({R.id.toBeManager, R.id.shareTask, R.id.getFuBag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toBeManager:
                enterFamily();
                break;
            case R.id.shareTask:
                User user = MyApplication.getInstance().user;
                Intent intent = new Intent(CoinGetActivity.this, BaseWebActivity.class);
                intent.putExtra("title", "每日任务");
                intent.putExtra("url", APPURL.BASE_H5_URL+"gameType=10001&userId="+ user.getUserId()+"&time="+ System.currentTimeMillis()+ "&token=" + user.getToken()+ "&uid=" + user.getUserId()+ "&termType=1&deviceId=" + DeviceUtils.getDeviceId(CoinGetActivity.this));
                startActivity(intent);
                break;
            case R.id.getFuBag:
                startActivity(new Intent(CoinGetActivity.this,HotMallActivity.class));
                break;
        }
    }

    private void enterFamily() {
        NetUtils.getInstance().userGroupList(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                UserGroups userGroups = (UserGroups) model.getModel();
                if (userGroups == null) {
                    ToastUtil.shortShowToast(msg);
                    startActivity(new Intent(CoinGetActivity.this, FamilyCretaeActivity.class));
                } else {
                    final FamilyList.ContentBean contentBean = userGroups.getGroupInfo();
                    if (contentBean != null) {
                        startActivity(new Intent(CoinGetActivity.this, MyFamilyActivity.class));
                    } else {
                        startActivity(new Intent(CoinGetActivity.this, FamilyCretaeActivity.class));
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
            }
        }, UserGroups.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
