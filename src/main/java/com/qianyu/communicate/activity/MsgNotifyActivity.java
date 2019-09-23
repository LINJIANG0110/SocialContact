package com.qianyu.communicate.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qianyu.chatuidemo.DemoHelper;
import com.qianyu.chatuidemo.DemoModel;
import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMPushConfigs;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class MsgNotifyActivity extends BaseActivity {
    @InjectView(R.id.msgVoiceLogo)
    ImageView msgVoiceLogo;
    @InjectView(R.id.msgVoiceNotify)
    RelativeLayout msgVoiceNotify;
    @InjectView(R.id.msgShakeLogo)
    ImageView msgShakeLogo;
    @InjectView(R.id.msgShakeNotify)
    RelativeLayout msgShakeNotify;
    @InjectView(R.id.msgNotifyLogo)
    ImageView msgNotifyLogo;
    @InjectView(R.id.msgNotify)
    RelativeLayout msgNotify;
    @InjectView(R.id.avoidDisturbLogo)
    ImageView avoidDisturbLogo;
    @InjectView(R.id.avoidDisturbNotify)
    RelativeLayout avoidDisturbNotify;
    private DemoModel settingsModel;
    private EMPushConfigs mPushConfigs;
    private boolean disutrbFlag = true;

    @Override
    public int getRootViewId() {
        return R.layout.activity_msg_notify;
    }

    @Override
    public void setViews() {
        setTitleTv("消息通知");
    }

    @Override
    public void initData() {
        settingsModel = DemoHelper.getInstance().getModel();
        msgVoiceLogo.setImageResource(settingsModel.getSettingMsgSound() ? R.mipmap.shezhi_dakai : R.mipmap.shezhi_guanbi);
        msgShakeLogo.setImageResource(settingsModel.getSettingMsgVibrate() ? R.mipmap.shezhi_dakai : R.mipmap.shezhi_guanbi);
        msgNotifyLogo.setImageResource(settingsModel.getSettingMsgNotification() ? R.mipmap.shezhi_dakai : R.mipmap.shezhi_guanbi);
        mPushConfigs = EMClient.getInstance().pushManager().getPushConfigs();
        if (mPushConfigs == null) {
            final ProgressDialog loadingPd = new ProgressDialog(this);
            loadingPd.setMessage("loading");
            loadingPd.setCanceledOnTouchOutside(false);
            loadingPd.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mPushConfigs = EMClient.getInstance().pushManager().getPushConfigsFromServer();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingPd.dismiss();
                                processPushConfigs();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingPd.dismiss();
                                Toast.makeText(MsgNotifyActivity.this, "loading failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        } else {
            processPushConfigs();
        }
    }

    @OnClick({R.id.msgVoiceNotify, R.id.msgShakeNotify, R.id.msgNotify, R.id.avoidDisturbNotify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.msgVoiceNotify:
                settingsModel.setSettingMsgSound(settingsModel.getSettingMsgSound() ? false : true);
                msgVoiceLogo.setImageResource(settingsModel.getSettingMsgSound() ? R.mipmap.shezhi_dakai : R.mipmap.shezhi_guanbi);
                break;
            case R.id.msgShakeNotify:
                settingsModel.setSettingMsgVibrate(settingsModel.getSettingMsgVibrate() ? false : true);
                msgShakeLogo.setImageResource(settingsModel.getSettingMsgVibrate() ? R.mipmap.shezhi_dakai : R.mipmap.shezhi_guanbi);
                break;
            case R.id.msgNotify:
                settingsModel.setSettingMsgNotification(settingsModel.getSettingMsgNotification() ? false : true);
                msgNotifyLogo.setImageResource(settingsModel.getSettingMsgNotification() ? R.mipmap.shezhi_dakai : R.mipmap.shezhi_guanbi);
                break;
            case R.id.avoidDisturbNotify:
                disutrbFlag = !disutrbFlag;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                try {
                    if (disutrbFlag) {
                        avoidDisturbLogo.setImageResource(R.mipmap.shezhi_dakai);
                        EMClient.getInstance().pushManager().disableOfflinePush(0, 24);
                    } else {
                        avoidDisturbLogo.setImageResource(R.mipmap.shezhi_guanbi);
                        EMClient.getInstance().pushManager().enableOfflinePush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(MsgNotifyActivity.this, R.string.push_save_failed, Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
                }
//                }).start();
                break;
        }
    }

    private void processPushConfigs() {
        if (mPushConfigs == null)
            return;
        if (mPushConfigs.isNoDisturbOn()) {
            disutrbFlag = true;
            avoidDisturbLogo.setImageResource(R.mipmap.shezhi_dakai);
        } else {
            disutrbFlag = false;
            avoidDisturbLogo.setImageResource(R.mipmap.shezhi_guanbi);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
