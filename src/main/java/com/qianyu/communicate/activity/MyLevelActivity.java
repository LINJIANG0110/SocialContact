package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.MyLevel;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;

import java.math.BigDecimal;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JavaDog on 2019-6-14.
 */

public class MyLevelActivity extends BaseActivity {

    @InjectView(R.id.ly_back)
    LinearLayout lyBack;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mNickName)
    TextView mNickName;
    @InjectView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.mLevelTv)
    TextView mLevelTv;
    @InjectView(R.id.mProgressTv)
    TextView mProgressTv;
    @InjectView(R.id.mUpdateLogo)
    ImageView mUpdateLogo;
    @InjectView(R.id.mUpdateHint)
    TextView mUpdateHint;
    @InjectView(R.id.mNeedCoinTv)
    TextView mNeedCoinTv;
    @InjectView(R.id.mUpdateNow)
    CardView mUpdateNow;
    @InjectView(R.id.mMyCoinTv)
    TextView mMyCoinTv;
    @InjectView(R.id.mCloseTv)
    TextView mCloseTv;
    private PersonalInfo personalInfo;

    @Override
    public int getRootViewId() {
        return R.layout.activity_my_level;
    }

    @Override
    public void setViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
    }

    @Override
    public void initData() {
        loadDatas();
    }

    @OnClick({R.id.ly_back, R.id.mUpdateNow, R.id.mCloseTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_back:
                finish();
                break;
            case R.id.mUpdateNow:
                if (personalInfo != null) {
                    if (personalInfo.getExperience() < personalInfo.getNeedExperience()) {
                        ToastUtil.shortShowToast("经验不足...");
                        return;
                    }
                }
                showCoinPopWindow();
                break;
            case R.id.mCloseTv:
                finish();
                break;
        }
    }

    private void showCoinPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.coin_charge_pop)
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
                        TextView mCoinHintTv = view.findViewById(R.id.mCoinHintTv);
                        TextView mHaveCoinTv = view.findViewById(R.id.mHaveCoinTv);
                        TextView mNeedCoinTv = view.findViewById(R.id.mNeedCoinTv);
                        final TextView cancelTv = view.findViewById(R.id.cancelTv);
                        TextView sureTv = view.findViewById(R.id.sureTv);
                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                                startActivity(new Intent(MyLevelActivity.this, CoinGetActivity.class));
                            }
                        });
                        sureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                                if (personalInfo != null) {
                                    if (personalInfo.getGold() < personalInfo.getNeedGold()) {
                                        ToastUtil.shortShowToast("您的金币不足");
                                    } else {
                                        NetUtils.getInstance().userUpdate(new NetCallBack() {
                                            @Override
                                            public void onSuccess(String result, String msg, ResultModel model) {
                                                ToastUtil.shortShowToast(msg);
                                                EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
                                                loadDatas();
                                            }

                                            @Override
                                            public void onFail(String code, String msg, String result) {
                                                ToastUtil.shortShowToast(msg);
                                            }
                                        }, MyLevel.class);
                                    }
                                }
                            }
                        });
                        if (personalInfo != null) {
                            mHaveCoinTv.setText("" + personalInfo.getGold());
                            mNeedCoinTv.setText("/" + personalInfo.getNeedGold());
                            if (personalInfo.getGold() >= personalInfo.getNeedGold()) {
                                mCoinHintTv.setText("您的金币充足");
                                cancelTv.setVisibility(View.GONE);
                            } else {
                                mCoinHintTv.setText("您的金币不足");
                                cancelTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mUpdateNow, Gravity.CENTER, 0, 0);
    }

    private void loadDatas() {
        if (MyApplication.getInstance().isLogin()) {
            User user = MyApplication.getInstance().user;
            NetUtils.getInstance().mineInfo(user.getUserId(), new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    personalInfo = (PersonalInfo) model.getModel();
                    if (personalInfo != null) {
                        mHeadImg.setImageURI(TextUtils.isEmpty(personalInfo.getHeadUrl()) ? "" : personalInfo.getHeadUrl());
                        mNickName.setText(TextUtils.isEmpty(personalInfo.getNickName()) ? "" : personalInfo.getNickName());
                        mLevelTv.setText(personalInfo.getLevel() + "");
                        mProgressTv.setText(personalInfo.getExperience() + "/" + personalInfo.getNeedExperience() + "");
                        if (personalInfo.getNeedExperience() != 0) {
                            double value = new BigDecimal((float) personalInfo.getExperience() / personalInfo.getNeedExperience()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            mProgressBar.setProgress((int) (value * 100));
                            if (personalInfo.getExperience() >= personalInfo.getNeedExperience()) {
                                mUpdateLogo.setVisibility(View.VISIBLE);
                                mUpdateHint.setVisibility(View.VISIBLE);
                            } else {
                                mUpdateLogo.setVisibility(View.GONE);
                                mUpdateHint.setVisibility(View.GONE);
                            }
                        } else {
                            mProgressBar.setProgress(100);
                            mUpdateLogo.setVisibility(View.VISIBLE);
                            mUpdateHint.setVisibility(View.VISIBLE);
                        }
                        mNeedCoinTv.setText(personalInfo.getNeedGold() + "");
                        mMyCoinTv.setText(personalInfo.getGold() + "）");
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, PersonalInfo.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
