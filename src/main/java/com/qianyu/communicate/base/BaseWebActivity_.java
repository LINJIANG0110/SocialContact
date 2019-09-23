package com.qianyu.communicate.base;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.qianyu.communicate.activity.CoinGetActivity;
import com.qianyu.communicate.activity.FamilyRoomActivity;
import com.qianyu.communicate.activity.FubowGetActivity;
import com.qianyu.communicate.activity.HotMallActivity;
import com.qianyu.communicate.activity.MainActivity;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.activity.SkillDetailActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserGroups;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.R;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.applibrary.wight.ProgressWebView;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.APPURL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by wl_user on 2017/7/11.
 */

public class BaseWebActivity_ extends BaseActivity {
    protected ProgressWebView mWebView;
    private String url = "";
    private ProgressWebView luckPanWebView;

    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        return R.layout.activity_web_;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        mWebView = findViewById(R.id.baseweb_webview);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.GURAD_CDART) {//押镖劫镖
            EventRecord.ContentBean contentBean = (EventRecord.ContentBean) event.getMessage();
            if (contentBean != null) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("pdartId", contentBean.getPdartId());
                    jsonObject.put("eventType", contentBean.getEventType());
                    mWebView.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (event.getState() == EventBuss.GURAD_SAVE) {//押镖解救
            EventRecord.ContentBean contentBean = (EventRecord.ContentBean) event.getMessage();
            if (contentBean != null) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("pdartId", contentBean.getPdartId());
                    jsonObject.put("eventType", contentBean.getEventType());
                    mWebView.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initData() {
        setResult(101);
        url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
        loadH5();
    }

    private void loadH5() {
        Tools.showDialog(BaseWebActivity_.this);
        mWebView.setOnH5Listener(new ProgressWebView.OnH5Listener() {
            @Override
            public void onH5Listener(String message) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    String type = jsonObject.getString("type");
                    switch (type) {
                        case "loading"://h5加载
                            Tools.dismissWaitDialog();
                            break;
                        case "closeEscort"://关闭押镖
                            finish();
                            break;
                        case "GetMoreCoins"://获取金币
                            JSONObject value = jsonObject.getJSONObject("value");
                            long gold = value.getLong("gold");
                            Intent intent = new Intent(BaseWebActivity_.this, CoinGetActivity.class);
                            intent.putExtra("coin", gold);
                            startActivityForResult(intent, 100);
                            break;
                        case "GetMoreFubao"://获取福宝
                            JSONObject value1 = jsonObject.getJSONObject("value");
                            long fubao = value1.getLong("fubao");
                            Intent intent1 = new Intent(BaseWebActivity_.this, FubowGetActivity.class);
                            intent1.putExtra("fubao", fubao);
                            startActivityForResult(intent1, 100);
                            break;
                        case "TurnLuckDraw"://跳转大转盘
                            showLuckPanPopWindow();
                            break;
                        case "updateSkill"://技能升级
                            startActivity(new Intent(BaseWebActivity_.this, SkillDetailActivity.class));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 & resultCode == 101) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("eventType", "backupEvent");
                if (luckPanWebView != null) {
                    jsonObject.put("value", "GROTARY");
                    luckPanWebView.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                } else {
                    jsonObject.put("value", "PDART");
                    mWebView.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //大转盘弹窗
    private void showLuckPanPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(com.qianyu.communicate.R.layout.activity_web)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
                //设置动画
                .setAnimationStyle(com.qianyu.communicate.R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1.0f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RelativeLayout mHeadView = (RelativeLayout) view.findViewById(com.qianyu.communicate.R.id.head_view);
                        mHeadView.setVisibility(View.GONE);
                        luckPanWebView = (ProgressWebView) view.findViewById(com.qianyu.communicate.R.id.baseweb_webview);
                        luckPanWebView.setBackgroundColor(0);
                        luckPanWebView.getBackground().setAlpha(0);
                        User user = MyApplication.getInstance().user;
                        luckPanWebView.loadUrl(APPURL.BASE_H5_URL + "gameType=10000&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(BaseWebActivity_.this));
                        Tools.showDialog(BaseWebActivity_.this);
                        luckPanWebView.setOnH5Listener(new ProgressWebView.OnH5Listener() {
                            @Override
                            public void onH5Listener(String message) {
                                try {
                                    JSONObject jsonObject = new JSONObject(message);
                                    String type = jsonObject.getString("type");
                                    switch (type) {
                                        case "loading":
                                            Tools.dismissWaitDialog();
                                            break;
                                        case "closeLuckDraw":
                                            popupWindow.dismiss();
                                            break;
                                        case "ShoppingMall":
                                            Intent intent = new Intent(BaseWebActivity_.this, HotMallActivity.class);
                                            startActivityForResult(intent, 100);
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mWebView, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("eventType", "backupEvent");
                    jsonObject.put("value", "GROTARY");
                    mWebView.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (luckPanWebView != null) {
                    // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再 // destory()
                    ViewParent parent = luckPanWebView.getParent();
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(luckPanWebView);
                    }
                    luckPanWebView.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                    luckPanWebView.getSettings().setJavaScriptEnabled(false);
                    luckPanWebView.clearHistory();
                    luckPanWebView.clearView();
                    luckPanWebView.removeAllViews();
                    luckPanWebView.destroy();
                    luckPanWebView = null;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再 // destory()
        ViewParent parent = mWebView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mWebView);
        }
        mWebView.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearHistory();
        mWebView.clearView();
        mWebView.removeAllViews();
        mWebView.destroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }
}
