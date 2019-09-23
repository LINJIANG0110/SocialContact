package com.qianyu.communicate.views;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.utils.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by JavaDog on 2019-6-28.
 */
public class WindowUtils {
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;
    public static Boolean isShown = false;

    public static void showPopupWindow(final Context context, EventRecord.ContentBean contentBean) {
        if (isShown) {
            return;
        }
        isShown = true;
        mContext = context.getApplicationContext();
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mView = setUpView(context, contentBean);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        if(isMIUI()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0+
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        }else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
//                params.type = WindowManager.LayoutParams.TYPE_TOAST;
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        params.flags = flags;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        mWindowManager.addView(mView, params);
    }

    public static void hidePopupWindow() {
        if (isShown && null != mView && mView.isAttachedToWindow()) {
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    private static View setUpView(final Context context, EventRecord.ContentBean contentBean) {
        View view = LayoutInflater.from(context).inflate(R.layout.world_gift_pop, null);
        SimpleDraweeView mGiftImg = (SimpleDraweeView) view.findViewById(R.id.mGiftImg);
        SimpleDraweeView mHeadImg = (SimpleDraweeView) view.findViewById(R.id.mHeadImg);
        TextView mNickName = (TextView) view.findViewById(R.id.mNickName);
        TextView mGiftDetail = (TextView) view.findViewById(R.id.mGiftDetail);
        Uri uri = Uri.parse(TextUtils.isEmpty(contentBean.getPicPath()) ? "" : contentBean.getPicPath());
//        Uri uri = Uri.parse("http://188.131.236.58:8080/pictures/mtl_450.gif");
        final DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        mGiftImg.setController(draweeController);
        mHeadImg.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl());
        mNickName.setText(TextUtils.isEmpty(contentBean.getSendNickName()) ? "" : contentBean.getSendNickName());
        int sendSex = contentBean.getSendSex();
        int acceptSex = contentBean.getAcceptSex();
        mNickName.setTextColor(context.getResources().getColor(sendSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv));
        if (!TextUtils.isEmpty(contentBean.getEventMsg())) {
            try {
                SpannableString content = Tools.matcherSearchTitle(context.getResources().getColor(sendSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
                        context.getResources().getColor(acceptSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
                        contentBean.getEventMsg(), contentBean.getSendNickName(), contentBean.getAcceptNickName());
                mGiftDetail.setText(SpanStringUtils.getEmotionContent_(EmotionUtils.EMOTION_CLASSIC_TYPE, context, mGiftDetail, content));
            } catch (Exception e) {
                mGiftDetail.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, mGiftDetail, contentBean.getEventMsg()));
            }
        } else {
            mGiftDetail.setText("");
        }
        // 点击窗口外部区域可消除
        // 这点的实现主要将悬浮窗设置为全屏大小，外层有个透明背景，中间一部分视为内容区域
        // 所以点击内容区域外部视为点击悬浮窗外部
        final View popupWindowView = view.findViewById(R.id.mWorldFL);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = new Rect();
                popupWindowView.getGlobalVisibleRect(rect);
                if (!rect.contains(x, y)) {
                    WindowUtils.hidePopupWindow();
                }
                return false;
            }
        });
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                switch (keyCode) {
//                    case KeyEvent.KEYCODE_BACK:
//                        WindowUtils.hidePopupWindow();
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        });
        return view;
    }

    // 检测MIUI
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    public static boolean isMIUI() {
//        if(SPUtils.getInstance().getCacheDataSP().contains("isMIUI"))
//        {
//            return SPUtils.getInstance().getCacheDataSP().getBoolean("isMIUI",false);
//        }
        Properties prop= new Properties();
        boolean isMIUI;
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        isMIUI= prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
//        SPUtils.getInstance().putCacheData("isMIUI",isMIUI);//保存是否MIUI
        return isMIUI;
    }
}
