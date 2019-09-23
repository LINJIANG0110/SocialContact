package com.qianyu.communicate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.qianyu.chatuidemo.DemoHelper;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.AllCacheUtil;
import com.qianyu.communicate.utils.AppManager;
import com.qianyu.communicate.utils.DownloadAppService;
import com.qianyu.communicate.utils.SpUtil;
import com.qianyu.communicate.utils.Tools;
import com.hyphenate.EMCallBack;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ServiceUtils;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class SettingActivity extends BaseActivity {
    @InjectView(R.id.countBanding)
    RelativeLayout countBanding;
    @InjectView(R.id.msgNotify)
    TextView msgNotify;
    @InjectView(R.id.privateSetting)
    TextView privateSetting;
    @InjectView(R.id.funcSetting)
    TextView funcSetting;
    @InjectView(R.id.suggestBack)
    RelativeLayout suggestBack;
    @InjectView(R.id.goodComment)
    TextView goodComment;
    @InjectView(R.id.cacheSize)
    TextView cacheSize;
    @InjectView(R.id.clearCache)
    RelativeLayout clearCache;
    @InjectView(R.id.currentVersion)
    TextView currentVersion;
    @InjectView(R.id.versionUpdate)
    RelativeLayout versionUpdate;
    @InjectView(R.id.aboutUs)
    TextView aboutUs;
    @InjectView(R.id.cancelLogin)
    TextView cancelLogin;
    @InjectView(R.id.existLogin)
    TextView existLogin;
    private AlertDialog dialog;
    private int versionCode;

    @Override
    public int getRootViewId() {
        return R.layout.activity_setting;
    }

    @Override
    public void setViews() {
        setTitleTv("设置");
    }

    @Override
    public void initData() {
        try {
            Log.e("当前版本", getVersionName());
            currentVersion.setText("当前版本  v" + getVersionName());
        } catch (Exception e) {

        }

        try {
            String totalCacheSize = AllCacheUtil.getTotalCacheSize(getApplicationContext());
            if (!TextUtils.isEmpty(totalCacheSize)) {
                cacheSize.setText(totalCacheSize);
            } else {
                cacheSize.setText("0.0M");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    @OnClick({R.id.countBanding, R.id.msgNotify, R.id.privateSetting, R.id.funcSetting, R.id.suggestBack, R.id.goodComment, R.id.clearCache, R.id.versionUpdate, R.id.aboutUs, R.id.cancelLogin, R.id.existLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.countBanding:
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(SettingActivity.this, RegistActivity.class));
                    return;
                }
                startActivity(new Intent(SettingActivity.this, CountBindingActivity.class));
                break;
            case R.id.msgNotify:
                startActivity(new Intent(SettingActivity.this, MsgNotifyActivity.class));
                break;
            case R.id.privateSetting:
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(SettingActivity.this, RegistActivity.class));
                    return;
                }
                startActivity(new Intent(SettingActivity.this, PrivateSettingActivity.class));
                break;
            case R.id.funcSetting:
                startActivity(new Intent(SettingActivity.this, FuncSettingActivity.class));
                break;
            case R.id.suggestBack:
                startActivity(new Intent(SettingActivity.this, SuggestBackActivity.class));
                break;
            case R.id.goodComment:
                break;
            case R.id.clearCache:
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("确定清除缓存？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                AllCacheUtil.clearAllCache(getApplicationContext());
                                try {
                                    String totalCacheSize = AllCacheUtil.getTotalCacheSize(getApplicationContext());
                                    if (!TextUtils.isEmpty(totalCacheSize)) {
                                        cacheSize.setText(totalCacheSize);
                                    } else {
                                        cacheSize.setText("0.0M");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.versionUpdate:
                checkVersion();
                break;
            case R.id.aboutUs:
                startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
                break;
            case R.id.cancelLogin:
//                String stringSP = SpUtil.getStringSP(SettingActivity.this, "status", "0");
//                SpUtil.saveBooleanToSP(SettingActivity.this, stringSP, false);
                User user = MyApplication.getInstance().user;
                if (user != null) {
                    JPushInterface.deleteAlias(SettingActivity.this, (int) user.getUserId());
                }
                logout();
                break;
            case R.id.existLogin:
                AppManager.getAppManager().AppExit(this);
                break;
        }
    }

    public void logout() {
        if (!DemoHelper.getInstance().isLoggedIn()) {
            goToLogin();
        } else {
            DemoHelper.getInstance().logout(true, new EMCallBack() {

                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            goToLogin();
                        }
                    });
                }

                @Override
                public void onProgress(int progress, String status) {
                    AppLog.e("=============hx=logout: onProgress==================");
                }

                @Override
                public void onError(int code, String message) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            goToLogin();
                        }
                    });
                }
            });
        }
    }

    private void goToLogin() {
        EventBus.getDefault().post(new EventBuss(EventBuss.LOGIN_OUT));
        MyApplication.getInstance().saveUser(null);
        AppManager.getAppManager().finishAllActivity();
        startActivity(new Intent(SettingActivity.this, RegistActivity.class));
        finish();
    }


    private void checkVersion() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            versionCode = packageInfo.versionCode;
            AppLog.e("versionCode", versionCode + "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 检查版本
        Tools.showDialog(this);
        NetUtils.getInstance().versionUpdate(new NetCallBack() {
            @Override
            public void onSuccess(String info, String msg, ResultModel model) {
                Tools.dismissWaitDialog();
                try {
                    JSONObject result = new JSONObject(info);
                    JSONObject jsonObject = result.getJSONObject("data");
                    final String url = jsonObject.getString("downLoadPath");
                    final String content = jsonObject.getString("content");
                    final int version = jsonObject.getInt("intVersion");
                    if (!TextUtils.isEmpty(url)) {
                        if (versionCode < version) {
                            if (ServiceUtils.isServiceRunning(SettingActivity.this, DownloadAppService.serviceName)) {
                                ToastUtil.shortShowToast("升级服务已经启动,无需再次启动");
                            } else {
//                                String tishi = getResources().getString(R.string.prompt);
//                                AlertDialog.Builder buile = new AlertDialog.Builder(SettingActivity.this).setTitle(tishi).setMessage("检查到版本更新").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        Intent it = new Intent(SettingActivity.this, DownloadAppService.class);
//                                        it.putExtra(DownloadAppService.SERVICRINTENTURL, url);
//                                        it.putExtra(DownloadAppService.SERVACESHARENAME, 0);
//                                        startService(it);
//                                        AppLog.e("启动服务。。。。。。。。。。。。。。");
//                                    }
//                                }).setNegativeButton("取消", null);
//                                Dialog dialog = buile.create();
//                                if (!dialog.isShowing()) {
//                                    dialog.show();
//                                }
                                showUpdatePopWindow(url, content);
                            }
                        } else {
                            ToastUtil.shortShowToast("当前为最新版本...");
//                            showUpdatePopWindow(url,content);
                        }
                    } else {
                        ToastUtil.shortShowToast("当前为最新版本...");
//                        showUpdatePopWindow(url,content);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result, String info, String msg) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
            }
        }, null);
    }

    private void showUpdatePopWindow(final String url, final String content) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.update_pop_window_)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
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
                                Intent it = new Intent(SettingActivity.this, DownloadAppService.class);
                                it.putExtra(DownloadAppService.SERVICRINTENTURL, url);
                                it.putExtra(DownloadAppService.SERVACESHARENAME, 0);
                                startService(it);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(versionUpdate, Gravity.CENTER, 0, 0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
