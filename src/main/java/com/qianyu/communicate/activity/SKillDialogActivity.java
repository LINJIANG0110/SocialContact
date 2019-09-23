package com.qianyu.communicate.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-7-4.
 */

public class SKillDialogActivity extends BaseActivity {
    @InjectView(R.id.mGiftImg)
    SimpleDraweeView mGiftImg;
    @InjectView(R.id.mGiftDetail)
    TextView mGiftDetail;
    @InjectView(R.id.mJustForget)
    TextView mJustForget;
    @InjectView(R.id.mRevenge)
    TextView mRevenge;
    @InjectView(R.id.mOperateLL)
    LinearLayout mOperateLL;
    @InjectView(R.id.mCloseLogo)
    ImageView mCloseLogo;
    private EventRecord.ContentBean contentBean;

    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        setFinishOnTouchOutside(false);//点击空白处，窗体不关闭
        return R.layout.activity_skill_dialog;
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
        User user = MyApplication.getInstance().user;
        Uri uri = Uri.parse(TextUtils.isEmpty(contentBean.getPicPath()) ? "" : contentBean.getPicPath());
//        Uri uri = Uri.parse("http://188.131.236.58:8080/pictures/mtl_450.gif");
        final DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        mGiftImg.setController(draweeController);
        mGiftDetail.setText(TextUtils.isEmpty(contentBean.getEventMsg()) ? "" : Html.fromHtml(contentBean.getEventMsg()));
        mOperateLL.setVisibility(contentBean.getSendUserId() == user.getUserId() ? View.GONE : View.VISIBLE);
        mOperateLL.setVisibility(contentBean.getAcceptUserId() == user.getUserId() ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.mJustForget, R.id.mRevenge,R.id.mCloseLogo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mCloseLogo:
            case R.id.mJustForget:
                finish();
                break;
            case R.id.mRevenge:
                Intent intent = new Intent(SKillDialogActivity.this, SkillActivity.class);
                intent.putExtra("userId", contentBean.getSendUserId());
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
