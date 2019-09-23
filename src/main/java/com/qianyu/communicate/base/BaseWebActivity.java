package com.qianyu.communicate.base;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qianyu.communicate.activity.BossDialogActivity;
import com.qianyu.communicate.activity.FamilyRoomActivity;
import com.qianyu.communicate.activity.HotMallActivity;
import com.qianyu.communicate.activity.MainActivity;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserGroups;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import net.neiquan.applibrary.R;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.applibrary.wight.ProgressWebView;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.APPURL;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import tv.buka.sdk.utils.DeviceUtils;

import static com.qianyu.communicate.utils.Tools.isAppInstalled;

/**
 * Created by wl_user on 2017/7/11.
 */

public class BaseWebActivity extends BaseActivity {
    protected ProgressWebView mWebView;
    private String url = "";
    private String title = "";
    private UserGroups userGroups;
    private boolean banner = false;
    private ProgressWebView luckPanWebView;

    @Override
    public int getRootViewId() {
        return R.layout.activity_web;
    }

    @Override
    public void setViews() {
        mWebView = findViewById(R.id.baseweb_webview);
    }

    @Override
    public void initData() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        banner = getIntent().getBooleanExtra("banner", false);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
        if (!TextUtils.isEmpty(title)) {
            setTitleTv(title);
        }
        loadFamily();
        loadH5();
    }

    private void loadH5() {
        if (!banner)
            Tools.showDialog(BaseWebActivity.this);
        mWebView.setOnH5Listener(new ProgressWebView.OnH5Listener() {
            @Override
            public void onH5Listener(String message) {
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    String type = jsonObject.getString("type");
                    switch (type) {
                        case "person"://排行榜跳个人页面
                            JSONObject value = jsonObject.getJSONObject("value");
                            long userId = value.getLong("userId");
                            Intent intent = new Intent(BaseWebActivity.this, PersonalPageActivity.class);
                            intent.putExtra("userId", userId);
                            startActivityForResult(intent, 100);
                            break;
                        case "loading"://h5加载
                            Tools.dismissWaitDialog();
                            break;
                        case "EverydayTask"://每日任务
                            User user = MyApplication.getInstance().user;
                            FamilyList.ContentBean groupInfo = userGroups.getGroupInfo();
                            JSONObject value1 = jsonObject.getJSONObject("value");
                            String activityType = value1.getString("activityType");
                            switch (activityType) {
                                case "GROTARY"://大转盘
                                    showLuckPanPopWindow();
                                    break;
                                case "GROUPBOSS"://打boss
                                    if (userGroups != null && groupInfo != null) {
                                        EventBus.getDefault().post(new EventBuss(EventBuss.FAMILY_EXIT));
                                        Tools.enterFamily(BaseWebActivity.this, groupInfo.getGroupId(), true);
                                        finish();
                                    } else {
                                        Intent intent1 = new Intent(BaseWebActivity.this, MainActivity.class);
                                        intent1.putExtra("family", true);
                                        startActivity(intent1);
                                        finish();
                                    }
                                    break;
                                case "PDART"://押镖
                                case "CDART"://劫镖
                                case "SAVE"://解救
                                    Intent intent2 = new Intent(BaseWebActivity.this, BaseWebActivity_.class);
                                    intent2.putExtra("url", APPURL.BASE_H5_URL + "gameType=10004&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(BaseWebActivity.this));
                                    startActivityForResult(intent2,100);
                                    break;
                                case "SPEAK"://积极发言
                                case "VOICE"://上麦
                                    if (userGroups != null && groupInfo != null) {
                                        EventBus.getDefault().post(new EventBuss(EventBuss.FAMILY_EXIT));
                                        Tools.enterFamily(BaseWebActivity.this, groupInfo.getGroupId(), false);
                                        finish();
                                    } else {
                                        Intent intent1 = new Intent(BaseWebActivity.this, MainActivity.class);
                                        intent1.putExtra("family", true);
                                        startActivity(intent1);
                                        finish();
                                    }
                                    break;
                                case "APPSHARED"://每日分享
                                    share();
                                    break;
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
                        luckPanWebView.loadUrl(APPURL.BASE_H5_URL + "gameType=10000&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(BaseWebActivity.this));
                        Tools.showDialog(BaseWebActivity.this);
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
                                            Intent intent = new Intent(BaseWebActivity.this, HotMallActivity.class);
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
                    jsonObject.put("value", "EVERYDAYTASK");
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

    //分享
    private void share() {
        User user = MyApplication.getInstance().user;
//        UMImage image = new UMImage(FriendInviteActivity.this, R.mipmap.logo);
        UMImage image = new UMImage(BaseWebActivity.this, com.qianyu.communicate.R.drawable.sharelogo);
        UMWeb web = new UMWeb(APPURL.BASE_SHARE_URL + "gameType=10003&headUrl=" + user.getHeadUrl() + "&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(BaseWebActivity.this));
        web.setTitle("千语分享");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("据说有实力的人，可以让任何人闭嘴。");//描述
        new ShareAction(BaseWebActivity.this)
//                .withText(friend ? "我在千语APP聊天交友，欢迎您的到来！" : "我在千语APP群聊，欢迎您的到来！")
                .withMedia(web)
//                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener)
                .open();
        NetUtils.getInstance().appShare(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("eventType", "backupEvent");
                    jsonObject.put("value", "EVERYDAYTASK");
                    if (mWebView != null) {
                        mWebView.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, null);
    }

    //分享回调
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            if (platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE) {
                if (!isAppInstalled(BaseWebActivity.this, "com.tencent.mobileqq") && !isAppInstalled(BaseWebActivity.this, "com.tencent.tim")) {
                    ToastUtil.shortShowToast("请先安装QQ或者TIM客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                if (!isAppInstalled(BaseWebActivity.this, "com.tencent.mm")) {
                    ToastUtil.shortShowToast("请先安装微信客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.SINA) {
                if (!isAppInstalled(BaseWebActivity.this, "com.sina.weibo")) {
                    ToastUtil.shortShowToast("请先安装新浪微博客户端");
                    return;
                }
            }
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(BaseWebActivity.this, "分享成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(BaseWebActivity.this, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(BaseWebActivity.this, "分享取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("eventType", "backupEvent");
                jsonObject.put("value", "EVERYDAYTASK");
                if (mWebView != null) {
                    mWebView.loadUrl("javascript:new CommunicateAppUtils().receiveAppMessage(" + jsonObject + ")");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFamily() {
        NetUtils.getInstance().userGroupList(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                userGroups = (UserGroups) model.getModel();
            }

            @Override
            public void onFail(String code, String msg, String result) {
            }
        }, UserGroups.class);
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
