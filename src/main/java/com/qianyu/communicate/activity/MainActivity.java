package com.qianyu.communicate.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyu.chatuidemo.Constant;
import com.qianyu.chatuidemo.DemoHelper;
import com.qianyu.chatuidemo.db.InviteMessgeDao;
import com.qianyu.chatuidemo.db.UserDao;
import com.qianyu.chatuidemo.runtimepermissions.PermissionsManager;
import com.qianyu.chatuidemo.runtimepermissions.PermissionsResultAction;
import com.qianyu.chatuidemo.ui.*;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseMainActivity;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.UserGroups;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.fragment.ChatRoomListFragment;
import com.qianyu.communicate.fragment.HomeNearFragment;
import com.qianyu.communicate.fragment.MineFragment;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.jzvd.JZVideoPlayer;
import com.qianyu.communicate.utils.DownloadAppService;
import com.qianyu.communicate.utils.NotificationsUtils;
import com.qianyu.communicate.utils.SpUtil;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMClientListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.qianyu.communicate.utils.Tools;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ServiceUtils;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class MainActivity extends BaseMainActivity {

    @InjectView(R.id.tab_home)
    LinearLayout home_lay;
    @InjectView(R.id.tab_talent)
    LinearLayout sort_lay;
    @InjectView(R.id.tab_how)
    LinearLayout shop_lay;
    @InjectView(R.id.tab_me)
    LinearLayout mine_lay;
    @InjectView(R.id.mShopCount)
    TextView unreadAddressLable;
    @InjectView(R.id.mDotRed)
    TextView unreadLabel;
    @InjectView(R.id.mFriendCount)
    TextView mFriendCount;
    private int preId[] = {R.mipmap.tubiao_sy2, R.mipmap.tubiao_lb2, R.mipmap.tubiao_xx2, R.mipmap.tubiao_wo2};
    private int norId[] = {R.mipmap.tubiao_sy1, R.mipmap.tubiao_lb1, R.mipmap.tubiao_xx1, R.mipmap.tubiao_wo1};
    private ViewGroup Ly[];
    private FragmentTransaction fragmentTransaction;
    private Fragment fragments[] = {new HomeNearFragment(), new ChatRoomListFragment(), new ConversationListFragment(), new MineFragment()};
    public boolean isConflict = false;
    private boolean isCurrentAccountRemoved = false;
    private int versionCode;
    private CommonPopupWindow popupWindow;
    private boolean loginOut = false;

    /**
     * check if current user account was remove
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        Ly = new ViewGroup[]{sort_lay, shop_lay, home_lay, mine_lay};
        setDefaultFragment(true, 1);
        if (getIntent() != null &&
                (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false))) {
            DemoHelper.getInstance().logout(false, null);
            MyApplication.getInstance().saveUser(null);
            startActivity(new Intent(this, RegistActivity.class));
            finish();
            return;
        } else if (getIntent() != null && getIntent().getBooleanExtra("isConflict", false)) {
            MyApplication.getInstance().saveUser(null);
            startActivity(new Intent(this, RegistActivity.class));
            finish();
            return;
        }
        requestPermissions();
        //umeng api
        MobclickAgent.updateOnlineConfig(this);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        showExceptionDialogFromIntent(getIntent());
        inviteMessgeDao = new InviteMessgeDao(this);
        registerBroadcastReceiver();
        EMClient.getInstance().contactManager().setContactListener(new MainActivity.MyContactListener());
        EMClient.getInstance().addClientListener(clientListener);
        EMClient.getInstance().addMultiDeviceListener(new MainActivity.MyMultiDeviceListener());
        registerInternalDebugReceiver();

        if (getIntent() != null) {
            boolean family = getIntent().getBooleanExtra("family", false);
            if (family) {
                setTabSelect(1);
                setDefaultFragment(false, 1);
            }
        }
        net.neiquan.okhttp.http.NetUtils.deviceId = DeviceUtils.getDeviceId(this);
        if (MyApplication.getInstance().user != null) {
            net.neiquan.okhttp.http.NetUtils.userId = MyApplication.getInstance().user.getUserId();
            net.neiquan.okhttp.http.NetUtils.token = MyApplication.getInstance().user.getToken();
        }
    }


    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.EXIT_LOGIN) {
            finish();
            System.exit(0);
        } else if (event.getState() == EventBuss.FRIEND_REQUEST) {
            mFriendCount.setVisibility(View.VISIBLE);
        } else if (event.getState() == EventBuss.FRIEND_CLEAR) {
            mFriendCount.setVisibility(View.GONE);
        } else if (event.getState() == EventBuss.LOGIN_OUT) {
            loginOut = true;
        } else if (event.getState() == EventBuss.HOME_MSG_REFRESH) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }
    }

    @Override
    public void initData() {
        checkVersion();
        if (!NotificationsUtils.isNotificationEnabled(this)) {
            AlertDialog.Builder buile = new AlertDialog.Builder(MainActivity.this).setTitle("开启通知?").setMessage("开启通知后才能在通知栏收到通知消息。需要手动打开允许该应用的通知。").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    NotificationsUtils.goToSetting(MainActivity.this);
                }
            }).setNegativeButton("取消", null);
            Dialog dialog = buile.create();
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
        getConfuseFromAssets("confuse.txt");
        boolean child = SpUtil.getBooleanSP(this, "child", true);
        if (child) {
            startActivity(new Intent(this, AdultDialogActivity.class));
        }
        handler.sendEmptyMessageDelayed(0, 2000);//发送消息
        boolean isAddKf = SpUtil.getBooleanSP(this, "kefuTag", false);
        if (!isAddKf) {
            // 请求官方客服接口
            addKefu();
        }
    }

    private void addKefu() {
        NetUtils.getInstance().addKefu(MyApplication.getInstance().user.getUserId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("添加客服返回结果",result);
                try {
                    JSONObject job = new JSONObject(result);
                    int code = job.optInt("code");
                    if (code == 200){
                        SpUtil.saveBooleanToSP(MainActivity.this, "kefuTag", true);
                    }
                }catch (Exception e){

                }
            }
            @Override
            public void onFail(String code, String msg, String result) {
                Log.e("添加客服返回结果fail",result);
            }
        }, null);
    }

    private Handler handler = new Handler() {
        //接收handler发送的message
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //这里可执行重复循环的动作
            loadFamilyText();
            handler.sendEmptyMessageDelayed(0, 2000);//发送消息
        }
    };

    private void loadFamilyText() {
        if (!loginOut) {
            com.qianyu.communicate.http.NetUtils.getInstance().lookGroup(new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    UserGroups userGroups = (UserGroups) model.getModel();
                    if (userGroups != null) {
                        final FamilyList.ContentBean contentBean = userGroups.getGroupInfo();
                        if (contentBean != null && !TextUtils.isEmpty(contentBean.getContent()) && SpUtil.getLongSP(MainActivity.this, "time0", 0) != contentBean.getCreateTime()) {
                            Tools.familyRoom("13111111111", contentBean.getHeadUrl(), contentBean.getGroupName(), contentBean.getContent(), contentBean.getGroupId());
                            SpUtil.saveLongToSP(MainActivity.this, "time0", contentBean.getCreateTime());
                            EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                        }
                        List<FamilyList.ContentBean> gzlist = userGroups.getGzlist();
                        if (gzlist != null && gzlist.size() > 0) {
                            switch (gzlist.size()) {
                                case 1:
                                    if (gzlist.get(0) != null && !TextUtils.isEmpty(gzlist.get(0).getContent()) && SpUtil.getLongSP(MainActivity.this, "time1", 0) != gzlist.get(0).getCreateTime()) {
                                        Tools.familyRoom("13222222222", gzlist.get(0).getHeadUrl(), gzlist.get(0).getGroupName(), gzlist.get(0).getContent(), gzlist.get(0).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time1", gzlist.get(0).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    break;
                                case 2:
                                    if (gzlist.get(0) != null && !TextUtils.isEmpty(gzlist.get(0).getContent()) && SpUtil.getLongSP(MainActivity.this, "time1", 0) != gzlist.get(0).getCreateTime()) {
                                        Tools.familyRoom("13222222222", gzlist.get(0).getHeadUrl(), gzlist.get(0).getGroupName(), gzlist.get(0).getContent(), gzlist.get(0).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time1", gzlist.get(0).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(1) != null && !TextUtils.isEmpty(gzlist.get(1).getContent()) && SpUtil.getLongSP(MainActivity.this, "time2", 0) != gzlist.get(1).getCreateTime()) {
                                        Tools.familyRoom("13333333333", gzlist.get(1).getHeadUrl(), gzlist.get(1).getGroupName(), gzlist.get(1).getContent(), gzlist.get(1).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time2", gzlist.get(1).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    break;
                                case 3:
                                    if (gzlist.get(0) != null && !TextUtils.isEmpty(gzlist.get(0).getContent()) && SpUtil.getLongSP(MainActivity.this, "time1", 0) != gzlist.get(0).getCreateTime()) {
                                        Tools.familyRoom("13222222222", gzlist.get(0).getHeadUrl(), gzlist.get(0).getGroupName(), gzlist.get(0).getContent(), gzlist.get(0).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time1", gzlist.get(0).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(1) != null && !TextUtils.isEmpty(gzlist.get(1).getContent()) && SpUtil.getLongSP(MainActivity.this, "time2", 0) != gzlist.get(1).getCreateTime()) {
                                        Tools.familyRoom("13333333333", gzlist.get(1).getHeadUrl(), gzlist.get(1).getGroupName(), gzlist.get(1).getContent(), gzlist.get(1).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time2", gzlist.get(1).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(2) != null && !TextUtils.isEmpty(gzlist.get(2).getContent()) && SpUtil.getLongSP(MainActivity.this, "time3", 0) != gzlist.get(2).getCreateTime()) {
                                        Tools.familyRoom("13444444444", gzlist.get(2).getHeadUrl(), gzlist.get(2).getGroupName(), gzlist.get(2).getContent(), gzlist.get(2).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time3", gzlist.get(2).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    break;
                                case 4:
                                    if (gzlist.get(0) != null && !TextUtils.isEmpty(gzlist.get(0).getContent()) && SpUtil.getLongSP(MainActivity.this, "time1", 0) != gzlist.get(0).getCreateTime()) {
                                        Tools.familyRoom("13222222222", gzlist.get(0).getHeadUrl(), gzlist.get(0).getGroupName(), gzlist.get(0).getContent(), gzlist.get(0).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time1", gzlist.get(0).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(1) != null && !TextUtils.isEmpty(gzlist.get(1).getContent()) && SpUtil.getLongSP(MainActivity.this, "time2", 0) != gzlist.get(1).getCreateTime()) {
                                        Tools.familyRoom("13333333333", gzlist.get(1).getHeadUrl(), gzlist.get(1).getGroupName(), gzlist.get(1).getContent(), gzlist.get(1).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time2", gzlist.get(1).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(2) != null && !TextUtils.isEmpty(gzlist.get(2).getContent()) && SpUtil.getLongSP(MainActivity.this, "time3", 0) != gzlist.get(2).getCreateTime()) {
                                        Tools.familyRoom("13444444444", gzlist.get(2).getHeadUrl(), gzlist.get(2).getGroupName(), gzlist.get(2).getContent(), gzlist.get(2).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time3", gzlist.get(2).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(3) != null && !TextUtils.isEmpty(gzlist.get(3).getContent()) && SpUtil.getLongSP(MainActivity.this, "time4", 0) != gzlist.get(3).getCreateTime()) {
                                        Tools.familyRoom("13555555555", gzlist.get(3).getHeadUrl(), gzlist.get(3).getGroupName(), gzlist.get(3).getContent(), gzlist.get(3).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time4", gzlist.get(3).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    break;
                                case 5:
                                    if (gzlist.get(0) != null && !TextUtils.isEmpty(gzlist.get(0).getContent()) && SpUtil.getLongSP(MainActivity.this, "time1", 0) != gzlist.get(0).getCreateTime()) {
                                        Tools.familyRoom("13222222222", gzlist.get(0).getHeadUrl(), gzlist.get(0).getGroupName(), gzlist.get(0).getContent(), gzlist.get(0).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time1", gzlist.get(0).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(1) != null && !TextUtils.isEmpty(gzlist.get(1).getContent()) && SpUtil.getLongSP(MainActivity.this, "time2", 0) != gzlist.get(1).getCreateTime()) {
                                        Tools.familyRoom("13333333333", gzlist.get(1).getHeadUrl(), gzlist.get(1).getGroupName(), gzlist.get(1).getContent(), gzlist.get(1).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time2", gzlist.get(1).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(2) != null && !TextUtils.isEmpty(gzlist.get(2).getContent()) && SpUtil.getLongSP(MainActivity.this, "time3", 0) != gzlist.get(2).getCreateTime()) {
                                        Tools.familyRoom("13444444444", gzlist.get(2).getHeadUrl(), gzlist.get(2).getGroupName(), gzlist.get(2).getContent(), gzlist.get(2).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time3", gzlist.get(2).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(3) != null && !TextUtils.isEmpty(gzlist.get(3).getContent()) && SpUtil.getLongSP(MainActivity.this, "time4", 0) != gzlist.get(3).getCreateTime()) {
                                        Tools.familyRoom("13555555555", gzlist.get(3).getHeadUrl(), gzlist.get(3).getGroupName(), gzlist.get(3).getContent(), gzlist.get(3).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time4", gzlist.get(3).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    if (gzlist.get(4) != null && !TextUtils.isEmpty(gzlist.get(4).getContent()) && SpUtil.getLongSP(MainActivity.this, "time5", 0) != gzlist.get(4).getCreateTime()) {
                                        Tools.familyRoom("13666666666", gzlist.get(4).getHeadUrl(), gzlist.get(4).getGroupName(), gzlist.get(4).getContent(), gzlist.get(4).getGroupId());
                                        SpUtil.saveLongToSP(MainActivity.this, "time5", gzlist.get(4).getCreateTime());
                                        EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                                    }
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {
                }
            }, UserGroups.class);
        }
    }

    public void getConfuseFromAssets(String fileName) {
        List<String> confuseList = SpUtil.getStrListValue(this, "confuse");
        if (confuseList == null || confuseList.size() <= 0) {
            confuseList = new ArrayList<>();
            try {
                InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName), "utf-8");
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line;
                while ((line = bufReader.readLine()) != null) {
                    confuseList.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            SpUtil.saveStrListValue(this, "confuse", confuseList);
        } else {
            AppLog.e("======confuseList========" + confuseList.size());
        }
    }

    private void checkVersion() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 获取当前时间戳
        final long tempStamp = System.currentTimeMillis();// 毫秒13位
        String mKey = SpUtil.getStringSP(this, "tempStamp", "0");
        Long resultStamp = Long.valueOf(tempStamp) - Long.valueOf(mKey);
        if (resultStamp / 1000 / 60 / 60 / 24 < 3 && !mKey.equals("0")) {
            return;
        }
        // 检查版本
        NetUtils.getInstance().versionUpdate(new NetCallBack() {
            @Override
            public void onSuccess(String info, String msg, ResultModel model) {
                Log.e("更新检查", info);
                try {
                    JSONObject result = new JSONObject(info);
                    JSONObject jsonObject = result.getJSONObject("data");
                    final String url = jsonObject.getString("downLoadPath");
                    final String content = jsonObject.getString("content");
                    final int version = jsonObject.getInt("intVersion");
                    if (!TextUtils.isEmpty(url)) {
                        if (versionCode < version) {
                            // 记录更新提示时间-三天内不再提示
                            SpUtil.saveStringToSP(MainActivity.this, "tempStamp", tempStamp + "");
                            if (!ServiceUtils.isServiceRunning(MainActivity.this, DownloadAppService.serviceName)) {
                                if (home_lay != null) {
                                    home_lay.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            showUpdatePopWindow(url, content);
                                        }
                                    });
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(String result, String info, String msg) {
                Log.e("更新a", result);
            }
        }, null);
    }

    private void showUpdatePopWindow(final String url, final String content) {
        //设置PopupWindow布局
        //设置宽高
        //设置动画
        //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
        //设置PopupWindow里的子View及点击事件
        //设置外部是否可点击 默认是true
        //开始构建
        popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.update_pop_window_)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                .setOutsideTouchable(false)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        TextView mDetailTv = (TextView) view.findViewById(R.id.mDetailTv);
                        View mEnsureTv = (View) view.findViewById(R.id.mEnsureTv);
                        View mCancelTv = (View) view.findViewById(R.id.mCancelTv);
                        mDetailTv.setText(content);
                        mCancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        mEnsureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                Intent it = new Intent(MainActivity.this, DownloadAppService.class);
                                it.putExtra(DownloadAppService.SERVICRINTENTURL, url);
                                it.putExtra(DownloadAppService.SERVACESHARENAME, 0);
                                startService(it);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
//                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(home_lay, Gravity.CENTER, 0, 0);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setFocusable(false);
    }

    private int position = 0;

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_home:
                position = 2;
                EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                break;
            case R.id.tab_talent:
                position = 0;
                break;
            case R.id.tab_how:
                position = 1;
                break;
            case R.id.tab_me:
                position = 3;
                EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
                break;
            default:
                break;
        }
        setTabSelect(position);
        setDefaultFragment(false, position);
        SpringUtils.springAnim(Ly[position]);
    }

    EMClientListener clientListener = new EMClientListener() {
        @Override
        public void onMigrate2x(boolean success) {
            Toast.makeText(MainActivity.this, "onUpgradeFrom 2.x to 3.x " + (success ? "success" : "fail"), Toast.LENGTH_LONG).show();
            if (success) {
                refreshUIWithMessage();
            }
        }
    };

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
				// Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }


    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
                if (position == 2) {
                    // refresh conversation list
                    EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                }
            }
        });
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();
                if (position == 2) {
                    // refresh conversation list
                    EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                } else if (position == 1) {
                    EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                }
                String action = intent.getAction();
                if (action.equals(Constant.ACTION_GROUP_CHANAGED)) {
                    if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
            updateUnreadAddressLable();
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onFriendRequestAccepted(String username) {
        }

        @Override
        public void onFriendRequestDeclined(String username) {
        }
    }

    public class MyMultiDeviceListener implements EMMultiDeviceListener {

        @Override
        public void onContactEvent(int event, String target, String ext) {

        }

        @Override
        public void onGroupEvent(int event, String target, final List<String> username) {
            switch (event) {
                case EMMultiDeviceListener.GROUP_LEAVE:
                    ChatActivity.activityInstance.finish();
                    break;
                default:
                    break;
            }
        }
    }

    private void unregisterBroadcastReceiver() {
        if (broadcastManager != null) {
            broadcastManager.unregisterReceiver(broadcastReceiver);
        }
    }


    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * update the total unread count
     */
    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
                    unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
                    unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }

    private InviteMessgeDao inviteMessgeDao;

    @Override
    protected void onResume() {
        super.onResume();

        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }

        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        EMClient.getInstance().removeClientListener(clientListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
    }


    private android.app.AlertDialog.Builder exceptionBuilder;
    private boolean isExceptionDialogShow = false;
    private BroadcastReceiver internalDebugReceiver;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    private int getExceptionMessageId(String exceptionType) {
        if (exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return R.string.em_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return R.string.user_forbidden;
        }
        return R.string.Network_error;
    }

    /**
     * show the dialog when user met some exception: such as login on another device, user removed or user forbidden
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        MyApplication.getInstance().saveUser(null);
                        Intent intent = new Intent(MainActivity.this, RegistActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
            }
        }
    }

    private void showExceptionDialogFromIntent(Intent intent) {
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false)) {
            MyApplication.getInstance().saveUser(null);
            startActivity(new Intent(this, RegistActivity.class));
            this.finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }

    /**
     * debug purpose only, you can ignore this
     */
    private void registerInternalDebugReceiver() {
        internalDebugReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                DemoHelper.getInstance().logout(false, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                MyApplication.getInstance().saveUser(null);
                                startActivity(new Intent(MainActivity.this, RegistActivity.class));
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }

                    @Override
                    public void onError(int code, String message) {
                    }
                });
            }
        };
        IntentFilter filter = new IntentFilter(getPackageName() + ".em_internal_debug");
        registerReceiver(internalDebugReceiver, filter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }


    private void setTabSelect(int position) {
        for (int i = 0; i < Ly.length; i++) {
            if (position == i) {
                ((ImageView) ((FrameLayout) Ly[i].getChildAt(0)).getChildAt(0)).setImageResource(preId[i]);
                ((TextView) Ly[i].getChildAt(1)).setTextColor(getResources().getColor(R.color.app_color));
            } else {
                ((ImageView) ((FrameLayout) Ly[i].getChildAt(0)).getChildAt(0)).setImageResource(norId[i]);
                ((TextView) Ly[i].getChildAt(1)).setTextColor(getResources().getColor(R.color.text_gray));
            }
        }
    }

    private void setDefaultFragment(boolean first, int index) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (first) {
            fragmentTransaction.add(R.id.framLayoutId, fragments[0]);
            fragmentTransaction.add(R.id.framLayoutId, fragments[1]);
            fragmentTransaction.add(R.id.framLayoutId, fragments[2]);
            fragmentTransaction.add(R.id.framLayoutId, fragments[3]);
        }
        fragmentTransaction.hide(fragments[0]);
        fragmentTransaction.hide(fragments[1]);
        fragmentTransaction.hide(fragments[2]);
        fragmentTransaction.hide(fragments[3]);
        fragmentTransaction.show(fragments[index]);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (exceptionBuilder != null) {
            exceptionBuilder.create().dismiss();
            exceptionBuilder = null;
            isExceptionDialogShow = false;
        }
        unregisterBroadcastReceiver();

        try {
            unregisterReceiver(internalDebugReceiver);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
            System.exit(0);
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
