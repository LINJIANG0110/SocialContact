
package com.qianyu.communicate.base;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.AppManager;
import com.hyphenate.easeui.EaseUI;
import com.qianyu.communicate.utils.LocationHelper;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.StatusBarUtil;
import com.qianyu.communicate.views.MyRecylerView;
import com.reyun.sdk.ReYunGame;
import com.umeng.analytics.MobclickAgent;
import com.zxc.layout.autolayout.AutoLayoutActivity;


import net.neiquan.applibrary.R;
import net.neiquan.applibrary.niorgai.StatusBarCompats;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * 作者 : dyj
 * 时间 : 2016/4/25.
 */
public abstract class BaseActivity extends AutoLayoutActivity {
    boolean isActive = true;
    private LocationHelper locationHelper;

    public void setTitleTv(int id) {
        TextView tv = findViewById(R.id.title_tv);
        tv.setText(id);
    }

    public void setTitleTv(String str) {
        TextView tv = findViewById(R.id.title_tv);
        tv.setText(str);
    }

    public void setNextTv(String str) {
        TextView tv = findViewById(R.id.next_tv);
        tv.setVisibility(View.VISIBLE);
        tv.setText(str);
        View iv = findViewById(R.id.next_img);
        iv.setVisibility(View.GONE);
    }

    public TextView getNextTv() {
        return (TextView) findViewById(R.id.next_tv);
    }

    public void setBackGone() {
        View tv = findViewById(R.id.ly_back);
        tv.setVisibility(View.GONE);
    }

    public void setBackImgGone() {
        View tv = findViewById(R.id.back_img);
        tv.setVisibility(View.GONE);
    }

    public void setBackTv(String text) {
        TextView tv = findViewById(R.id.back_tv);
        tv.setText(text);
        tv.setVisibility(View.VISIBLE);
        findViewById(R.id.back_img).setVisibility(View.GONE);
    }

    public void setBackImg() {
        findViewById(R.id.back_tv).setVisibility(View.GONE);
        findViewById(R.id.back_img).setVisibility(View.VISIBLE);
    }

    public void back(View view) {
        finish();
    }

    @SuppressLint("NewApi")
    public void setNextTvBG(Drawable drawable) {
        TextView tv = findViewById(R.id.next_tv);
        tv.setBackground(drawable);
    }

    public void setNextImage(int id) {
        TextView tv = findViewById(R.id.next_tv);
        tv.setVisibility(View.GONE);
        ImageView iv = findViewById(R.id.next_img);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(id);
    }

    public void setNextSearchImage(int id) {
        ImageView iv = findViewById(R.id.searh_next_img);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(id);
    }

    protected void setTitleOnClick(View.OnClickListener onClick) {
        TextView tv = findViewById(R.id.title_tv);
        tv.setOnClickListener(onClick);
    }

    public void setNextOnClick(View.OnClickListener onClick) {
        TextView tv = findViewById(R.id.next_tv);
        tv.setOnClickListener(onClick);
        View iv = findViewById(R.id.next_img);
        iv.setOnClickListener(onClick);
    }

    public void setNextGone() {
        TextView tv = findViewById(R.id.next_tv);
        tv.setVisibility(View.GONE);
        ImageView iv = findViewById(R.id.next_img);
        iv.setVisibility(View.GONE);
    }

    protected InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanced) {
        super.onCreate(savedInstanced);
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setSystemBarTint();
        StatusBarUtil.setStatusBarColor(this);// 状态栏字体颜色设置黑色
//        MyApplication.getInstance().addActivity(this);
        AppManager.getAppManager().addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(getRootViewId());
        ButterKnife.inject(this);
        setViews();
        initData();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        locationHelper = LocationHelper.getInstance();
        if (locationHelper != null) {
            locationHelper.setCallBack(new LocationHelper.LocationCallBack() {
                @Override
                public void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province) {
                    User user = MyApplication.getInstance().user;
                    if (user != null) {
                        NetUtils.getInstance().quickLogin(user.getUserId(), JPushInterface.getRegistrationID(BaseActivity.this), city, lat + "", lng + "", new NetCallBack() {
                            @Override
                            public void onSuccess(String result, String msg, ResultModel model) {

                            }

                            @Override
                            public void onFail(String code, String msg, String result) {

                            }
                        }, null);
                    }
                }
            });
            locationHelper.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台
            //全局变量isActive = false 记录当前已经进入后台
            MyApplication.getInstance().isActive = false;
        }
        if (ReYunGame.isAppOnForeground() == false) {
            //app 进入后台
            isActive = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        // umeng
        MobclickAgent.onResume(this);
        // cancel the notification
        EaseUI.getInstance().getNotifier().reset();
        if (!MyApplication.getInstance().isActive) {
            MyApplication.getInstance().isActive = true;
        }
        if (!isActive) {    // app 从后台唤醒到前台
            //ReYun.postSessionData();
            ReYunGame.startHeartBeat(this);
            isActive = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        // umeng
        MobclickAgent.onPause(this);
    }

    /**
     * 设置沉浸式
     */
    public void setSystemBarTint() {
        //只对api19以上版本有效
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
        //为状态栏着色
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.title_bg);
        StatusBarCompats.setStatusBarColor(this, getResources().getColor(R.color.app_color_little), 0);
    }

//    @TargetApi(19)
//    private void setTranslucentStatus(boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }

    /**
     * 设置沉浸式
     */
    public void setSystemBarTint_() {
        StatusBarCompats.setStatusBarColor(this, getResources().getColor(R.color.xq_title), 0);
        //只对api19以上版本有效
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(false);
//        }
        //为状态栏着色
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.xq_title);
    }


    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 初始化View,加载布局
     *
     * @return 布局
     */
    public abstract int getRootViewId();

    /**
     * 初始化数据
     */
    public abstract void setViews();

    /**
     * 加载网络数据
     */
    public abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fixInputMethodManagerLeak(this);
        AppManager.getAppManager().finishActivity(this);
        if (locationHelper != null) {
            locationHelper.stop();
        }
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
//                        if (QLog.isColorLevel()) {
//                            QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
//                        }
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }


}