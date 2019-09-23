package com.qianyu.communicate.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qianyu.communicate.R;

import spa.lyh.cn.statusbarlightmode.ImmersionConfiguration;
import spa.lyh.cn.statusbarlightmode.ImmersionMode;
import spa.lyh.cn.statusbarlightmode.barutils.barUtils;

/**
 * 白底黑字状态栏
 */
public class StatusBarUtil {

    private static final String TAG = StatusBarUtil.class.getSimpleName();
    private static final int PHONE_TYPE_LIGHT_MODE_NOT_SUPPORTED = 4;
    private static boolean sInitialized = false;
    private static int sStatusBarBackgroundColor = R.color.status_bar_background_color;

    private static void init(Context context) {
        ImmersionMode.getInstance().init(getConfiguration(context));
    }

    private static int getStatusBarBackgroundColor() {
        return sStatusBarBackgroundColor;
    }

    private static void setStatusBarBackgroundColor(int statusBarBackgroundColor) {
        sStatusBarBackgroundColor = statusBarBackgroundColor;
    }

    // 文字黑加背景白
    public static void setStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Log.e(TAG, "status bar color can not be set for pre kitkat");
            return;
        }
        if (!sInitialized) {
            init(activity);
            sInitialized = true;
        }
        ImmersionMode.getInstance().execImmersionMode(activity);
        postCheckLightModeStatusBar(activity);
    }

    private static void postCheckLightModeStatusBar(Activity activity) {
        // 如果状态栏不支持light mode，且标题栏为浅色的话，将状态栏的颜色置为深色
        if (ImmersionMode.getInstance().getPhoneType() == PHONE_TYPE_LIGHT_MODE_NOT_SUPPORTED && isLightColor(ContextCompat.getColor(activity, getStatusBarBackgroundColor()))) {
            setStatusBarBackgroundColor(R.color.status_bar_background_color_dark);
            ImmersionMode.getInstance().destory();
            init(activity);
            ImmersionMode.getInstance().execImmersionMode(activity);
        }
    }

    private static ImmersionConfiguration getConfiguration(Context context) {
        return getConfiguration(context, getStatusBarBackgroundColor());
    }

    private static ImmersionConfiguration getConfiguration(Context context, int colorResourceId) {
        return new ImmersionConfiguration.Builder(context)
                .enableImmersionMode(ImmersionConfiguration.ENABLE)
                .setColor(colorResourceId)
                .build();
    }

    private static boolean isLightColor(int color) {
        int redValue = Color.red(color);
        int greenValue = Color.green(color);
        int blueValue = Color.blue(color);
        int[] colorArray = new int[]{redValue, greenValue, blueValue};
        return barUtils.isLightRGB(colorArray);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
