package com.qianyu.communicate.appliction;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.bkrtc_sdk.bkrtc_impl;
import com.qianyu.chatuidemo.DemoHelper;
import com.qianyu.chatuidemo.video.util.Utils;
import com.qianyu.communicate.activity.BossDialogActivity;
import com.qianyu.communicate.activity.FamilyDialogActivity;
import com.qianyu.communicate.activity.GiftDialogActivity;
import com.qianyu.communicate.activity.GuardRobDialogActivity;
import com.qianyu.communicate.activity.MainActivity;
import com.qianyu.communicate.activity.MyFamilyActivity;
import com.qianyu.communicate.activity.SKillDialogActivity;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.UserGroups;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.utils.Contants;
import com.qianyu.communicate.utils.GlideImageLoader;
import com.qianyu.communicate.utils.PhoneUtil;
import com.qianyu.communicate.utils.SpUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.Tools;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.reyun.sdk.ReYunGame;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.OkHttpUtils;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.FileUtils;
import org.haitao.common.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;
import tv.buka.sdk.BukaSDK;
import tv.buka.sdk.BukaSDKVersion;
import tv.buka.sdk.utils.DeviceUtils;


/**
 * 作者 ： dyj
 * 时间 ： 2016/1/29.
 */

public class MyApplication extends Application {

    public static MyApplication instance;
    public static Context context;
    public String currentUserNick = "";
//    public static Context applicationContext;
//    private static DemoApplication instance;
    /**
     * Activity集合
     */
    private ArrayList<Activity> activitys = new ArrayList<Activity>();
    //  private OkHttpFinalConfiguration.Builder builder;
    public User user;
    public boolean downApp;
    public String APP_FILE_NAME = "千语.apk";
    public boolean isActive = false;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        FileUtils.init(this);
        instance = this;
        Fresco.initialize(this);// 耗时300 毫秒
        context = this;
        initUM();
        //设置新浪微博的回调地址
//        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
        initPush();
        user = SpUtil.getBeanByFastJson(this, "user", User.class);
        ToastUtil.init(instance);
        Tools.setContext(instance);
        initImageLoader(this);
//        LeakCanary.install(this);
        initGalleryFinal();
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        //init demo helper
        DemoHelper.getInstance().init(this);
        initBuka();
//        initAve();
        OkHttpUtils.init(this, MainActivity.class);
        EventBus.getDefault().register(this);
        ReYunGame.initWithKeyAndChannelId(getApplicationContext(), "d9b6383d49e62c1f96c78b8e72f6fe60", null);

    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.PUSH_GIFT) {
            AppLog.e("===========PUSH_GIFT============");
            EventRecord.ContentBean contentBean = (EventRecord.ContentBean) event.getMessage();
            if (isBackground(this) == false && MyApplication.getInstance().isLogin() && contentBean != null && TextUtils.equals("world", contentBean.getPushType())) {
                Intent intent = new Intent(this, GiftDialogActivity.class);
                intent.putExtra("contentBean", contentBean);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if (isBackground(this) == false && event.getState() == EventBuss.PUSH_SKILL) {
            AppLog.e("===========PUSH_SKILL============");
            EventRecord.ContentBean contentBean = (EventRecord.ContentBean) event.getMessage();
            if (MyApplication.getInstance().isLogin() && contentBean != null) {
                Intent intent = new Intent(this, SKillDialogActivity.class);
                intent.putExtra("contentBean", contentBean);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if (isBackground(this) == false && event.getState() == EventBuss.FAMILY_BOSS) {
            AppLog.e("===========FAMILY_BOSS111============");
            EventRecord.ContentBean contentBean = (EventRecord.ContentBean) event.getMessage();
            if (MyApplication.getInstance().isLogin() && contentBean != null) {
                Intent intent = new Intent(this, BossDialogActivity.class);
                intent.putExtra("contentBean", contentBean);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if (isBackground(this) == false && event.getState() == EventBuss.FAMILY_RECRUIT) {
            AppLog.e("===========FAMILY_RECRUIT============");
            EventRecord.ContentBean contentBean = (EventRecord.ContentBean) event.getMessage();
            if (MyApplication.getInstance().isLogin() && contentBean != null) {
                Intent intent = new Intent(this, FamilyDialogActivity.class);
                intent.putExtra("contentBean", contentBean);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else if (isBackground(this) == false && event.getState() == EventBuss.GUARD_RESULT) {
            AppLog.e("===========GUARD_RESULT============");
            EventRecord.ContentBean contentBean = (EventRecord.ContentBean) event.getMessage();
            if (MyApplication.getInstance().isLogin() && contentBean != null) {
                Intent intent = new Intent(this, GuardRobDialogActivity.class);
                intent.putExtra("contentBean", contentBean);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    private void initBuka() {
//        BukaSDK.setSingalServerGroupUrl("http://vc.xdf.cn/xdf/signaling/");
//        BukaSDK.setMediaServerGroupUrl("http://vc.xdf.cn");
//        BukaSDK.setMediaServerNamespace("xdf");
//        c7c6b2a6a623f3ec022aa61f5aa4fa63  千语
        BukaSDK.setSingalServerGroupUrl("http://a.buka.tv/buka/signaling/server/select.do");
        BukaSDK.setMediaServerGroupUrl("http://a.buka.tv/v1/server/group");
        BukaSDK.setMediaServerNamespace("buka");
        BukaSDK.init("c7c6b2a6a623f3ec022aa61f5aa4fa63",
                this, BukaSDKVersion.BukaSDKVersion3);
        BukaSDK.start();
    }

    private void initAve() {
        bkrtc_impl.GetInstance().register(this);
        bkrtc_impl.GetInstance().AveCreate(false, true, 3);
        bkrtc_impl.GetInstance().AveSetUserId("2356");
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 保存用户
     *
     * @param user
     */
    public void saveUser(User user) {
        this.user = user;
        SpUtil.saveBeanByFastJson(this, "user", user);
    }

    public boolean isLogin() {
        return user != null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initPush() {
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

    private void initUM() {
        UMShareAPI.get(this);
        PlatformConfig.setWeixin(Contants.WX_APP_ID, Contants.WX_APP_SECRET);
//        http://sns.whalecloud.com/sina2/callback
//        http://sns.whalecloud.com
        PlatformConfig.setSinaWeibo(Contants.SINA_APP_ID, Contants.SINA_APP_SECRET, "http://sns.whalecloud.com/");
        PlatformConfig.setQQZone(Contants.QQ_APP_ID, Contants.QQ_APP_SECRET);

        IWXAPI api = WXAPIFactory.createWXAPI(this, Contants.WX_APP_ID, true);
        api.registerApp(Contants.WX_APP_ID);
    }

    /**
     * 添加Activity到ArrayList<Activity>管理集合
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        String className = activity.getClass().getName();
        for (Activity at : activitys) {
            if (className.equals(at.getClass().getName())) {
                activitys.remove(at);
                break;
            }
        }
        activitys.add(activity);
    }


    public void exitLogin() {
        for (Activity activity : activitys) {
            activity.finish();
        }
//        System.exit(0);
    }

    public static void initImageLoader(Context context) {
        // Create default options which will be used for every
        // displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        File file = FileUtils.getImageFile();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new UsingFreqLimitedMemoryCache((int) (Runtime.getRuntime().maxMemory() / 8)))
                .memoryCacheSizePercentage(13) // default
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app 日志log
                .diskCache(new UnlimitedDiskCache(file)).build();
        ImageLoader.getInstance().init(config);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private void initGalleryFinal() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(0x5D, 0x87, 0xF1))
                .setTitleBarTextColor(Color.WHITE)
                .setTitleBarIconColor(Color.WHITE)
                .setFabNornalColor(Color.rgb(0x5D, 0x87, 0xF1))
                .setFabPressedColor(Color.rgb(0x5D, 0x69, 0xF1))
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.BLACK)
                .build();
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnablePreview(false)
                .setEnableCrop(false)
                .setEnableEdit(false)
                .setForceCropEdit(false)
                .setForceCrop(false)
                .build();
        GlideImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
                .setNoAnimcation(true)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);

        //可设置项，如下
//        setMutiSelect(boolean)//配置是否多选
//        setMutiSelectMaxSize(int maxSize)//配置多选数量
//        setEnableEdit(boolean)//开启编辑功能
//        setEnableCrop(boolean)//开启裁剪功能
//        setEnableRotate(boolean)//开启选择功能
//        setEnableCamera(boolean)//开启相机功能
//        setCropWidth(int width)//裁剪宽度
//        setCropHeight(int height)//裁剪高度
//        setCropSquare(boolean)//裁剪正方形
//        setSelected(List)//添加已选列表,只是在列表中默认呗选中不会过滤图片
//        setFilter(List list)//添加图片过滤，也就是不在GalleryFinal中显示
//        takePhotoFolter(File file)//配置拍照保存目录，不做配置的话默认是/sdcard/DCIM/GalleryFinal/
//        setRotateReplaceSource(boolean)//配置选择图片时是否替换原始图片，默认不替换
//        setCropReplaceSource(boolean)//配置裁剪图片时是否替换原始图片，默认不替换
//        setForceCrop(boolean)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
//        setForceCropEdit(boolean)//在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
//        setEnablePreview(boolean)//是否开启预览功能
    }

    public static boolean isBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            // 5.0系统以上判断前后台方法
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            // 前台运行
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }
}
