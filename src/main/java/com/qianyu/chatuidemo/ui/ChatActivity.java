package com.qianyu.chatuidemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.qianyu.chatuidemo.runtimepermissions.PermissionsManager;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.GiftListActivity;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.StatusBarUtil;
import com.qianyu.communicate.utils.Tools;
import com.hyphenate.easeui.ui.EaseChatFragment;

import net.neiquan.applibrary.niorgai.StatusBarCompats;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.NetUtils;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

public class ChatActivity extends FragmentActivity implements EaseChatFragment.OnLocationClickListener {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    public String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        StatusBarUtil.setStatusBarColor(this);// 状态栏字体颜色设置黑色
        StatusBarCompats.setStatusBarColor(this, getResources().getColor(net.neiquan.applibrary.R.color.app_color_little_1), 0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        chatFragment.setOnLocationClickListener(this);
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
//        if (EasyUtils.isSingleActivity(this)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }


    @Override
    public void onLocationClick() {
        AppLog.e("============onLocationClick================");
        Tools.showDialog(ChatActivity.this);
        NetUtils.getInstance().getUserByHx(toChatUsername, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Tools.dismissWaitDialog();
                User user = (User) model.getModel();
                if (user != null) {
                    Intent intent = new Intent(ChatActivity.this, GiftListActivity.class);
                    intent.putExtra("userId", user.getUserId());
                    intent.putExtra("phone", toChatUsername);
                    startActivity(intent);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
            }
        }, User.class);
    }
}
