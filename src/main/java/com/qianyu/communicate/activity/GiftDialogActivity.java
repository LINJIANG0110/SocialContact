package com.qianyu.communicate.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.utils.Tools;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JavaDog on 2019-7-4.
 */

public class GiftDialogActivity extends BaseActivity {
    @InjectView(R.id.mGiftImg)
    SimpleDraweeView mGiftImg;
    @InjectView(R.id.mGiftImg_)
    SimpleDraweeView mGiftImg_;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mNickName)
    TextView mNickName;
    @InjectView(R.id.mGiftDetail)
    TextView mGiftDetail;
    @InjectView(R.id.mWorldFL)
    LinearLayout mWorldFL;
    private EventRecord.ContentBean contentBean;

    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        setFinishOnTouchOutside(false);//点击空白处，窗体不关闭
        return R.layout.activity_dialog;
    }

    @Override
    public void setViews() {
        if (getIntent() != null) {
            contentBean = ((EventRecord.ContentBean) getIntent().getSerializableExtra("contentBean"));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 5000);
    }

    @Override
    public void initData() {
        Uri uri = Uri.parse(TextUtils.isEmpty(contentBean.getPicPath()) ? "" : contentBean.getPicPath());
//        Uri uri = Uri.parse("http://188.131.236.58:8080/pictures/mtl_450.gif");
        final DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        if (!TextUtils.isEmpty(contentBean.getPicPath())) {
            if (contentBean.getPicPath().endsWith(".gif")) {
                mGiftImg.setVisibility(View.VISIBLE);
                mGiftImg_.setVisibility(View.GONE);
                mGiftImg.setController(draweeController);
            } else {
                mGiftImg.setVisibility(View.GONE);
                mGiftImg_.setVisibility(View.VISIBLE);
                mGiftImg_.setController(draweeController);
            }
        }
        mHeadImg.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl());
        mNickName.setText(TextUtils.isEmpty(contentBean.getSendNickName()) ? "" : contentBean.getSendNickName());
        int sendSex = contentBean.getSendSex();
//        int acceptSex = contentBean.getAcceptSex();
        mNickName.setTextColor(getResources().getColor(sendSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv));
//        if (!TextUtils.isEmpty(contentBean.getEventMsg())) {
//            try {
//                SpannableString content = Tools.matcherSearchTitle(getResources().getColor(sendSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
//                        getResources().getColor(acceptSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
//                        contentBean.getEventMsg(), contentBean.getSendNickName(), contentBean.getAcceptNickName());
//                mGiftDetail.setText(SpanStringUtils.getEmotionContent_(EmotionUtils.EMOTION_CLASSIC_TYPE, this, mGiftDetail, content));
//            } catch (Exception e) {
//                mGiftDetail.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, this, mGiftDetail, contentBean.getEventMsg()));
//            }
//        } else {
//            mGiftDetail.setText("");
//        }
        mGiftDetail.setText(TextUtils.isEmpty(contentBean.getEventMsg())?"": Html.fromHtml(contentBean.getEventMsg()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
