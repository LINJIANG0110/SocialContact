package com.qianyu.communicate.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.GiftList;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class GiftDetailActivity extends BaseActivity {

    @InjectView(R.id.giftName)
    TextView giftName;
    @InjectView(R.id.giftExprience)
    TextView giftExprience;
    @InjectView(R.id.giftBeautfy)
    TextView giftBeautfy;
    @InjectView(R.id.giftBeautfyAdd)
    TextView giftBeautfyAdd;
    @InjectView(R.id.giftImg)
    SimpleDraweeView giftImg;
    @InjectView(R.id.costNum)
    TextView costNum;
    @InjectView(R.id.remainMoney)
    TextView remainMoney;
    @InjectView(R.id.addNum)
    ImageView addNum;
    @InjectView(R.id.sendGift)
    TextView sendGift;
    @InjectView(R.id.yijianzhongqing)
    ImageView yijianzhongqing;
    @InjectView(R.id.tianchangdijiu)
    ImageView tianchangdijiu;
    @InjectView(R.id.woaini)
    ImageView woaini;
    @InjectView(R.id.yishengyishi)
    ImageView yishengyishi;
    @InjectView(R.id.shiquanshimei)
    ImageView shiquanshimei;
    @InjectView(R.id.qinqin)
    ImageView qinqin;
    @InjectView(R.id.xiangni)
    ImageView xiangni;
    @InjectView(R.id.yaobaobao)
    ImageView yaobaobao;
    private int giftNum = 0;
    private GiftList.ContentBean contentBean;
    private long userId;
    private String phone;

    @Override
    public int getRootViewId() {
        return R.layout.activity_gift_detail;
    }

    @Override
    public void setViews() {
        setTitleTv("钻石送礼");
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            contentBean = ((GiftList.ContentBean) getIntent().getSerializableExtra("gift"));
            userId = getIntent().getLongExtra("userId", 0);
            phone = getIntent().getStringExtra("phone");
            if (contentBean != null) {
                giftName.setText(TextUtils.isEmpty(contentBean.getGiftName()) ? "" : contentBean.getGiftName());
                giftExprience.setText("+" + contentBean.getAcceptExperience());
                giftBeautfy.setText("+" + contentBean.getAcceptCharm());
                giftBeautfyAdd.setText("+" + contentBean.getAcceptCharmSpecial() * 100 + "%");
//                ImageUtil.loadPicNet(contentBean.getGiftUrl(), giftImg);
                Uri uri = Uri.parse(TextUtils.isEmpty(contentBean.getGiftUrl()) ? "" : contentBean.getGiftUrl());
                final DraweeController draweeController =
                        Fresco.newDraweeControllerBuilder()
                                .setUri(uri)
                                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                                .build();
                giftImg.setController(draweeController);
                costNum.setText("" + contentBean.getGiftPrice());
            }
        }
        if (MyApplication.getInstance().isLogin()) {
            User user = MyApplication.getInstance().user;
            NetUtils.getInstance().mineInfo(user.getUserId(), new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                   PersonalInfo personalInfo = (PersonalInfo) model.getModel();
                    remainMoney.setText(NumberUtils.rounLong(personalInfo.getDiamond()));
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, PersonalInfo.class);
        }
    }

    @OnClick({R.id.addNum, R.id.sendGift, R.id.yijianzhongqing, R.id.tianchangdijiu, R.id.woaini, R.id.yishengyishi, R.id.shiquanshimei, R.id.qinqin, R.id.xiangni, R.id.yaobaobao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addNum:
                startActivity(new Intent(GiftDetailActivity.this, WalletActivity.class));
                break;
            case R.id.sendGift:
                sendGift();
                break;
            case R.id.yijianzhongqing:
                giftNum=1;
                sendGift();
                break;
            case R.id.tianchangdijiu:
                giftNum=999;
                sendGift();
                break;
            case R.id.woaini:
                giftNum=520;
                sendGift();
                break;
            case R.id.yishengyishi:
                giftNum=1314;
                sendGift();
                break;
            case R.id.shiquanshimei:
                giftNum=10;
                sendGift();
                break;
            case R.id.qinqin:
                giftNum=77;
                sendGift();
                break;
            case R.id.xiangni:
                giftNum=365;
                sendGift();
                break;
            case R.id.yaobaobao:
                giftNum=188;
                sendGift();
                break;
        }
    }

    private void sendGift() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(this, RegistActivity.class));
            return;
        }
        if (giftNum == 0) {
            ToastUtil.shortShowToast("请先选择送礼个数...");
            return;
        }
        Tools.showDialog(this);
        NetUtils.getInstance().sendGift(userId, contentBean.getGiftId(), giftNum, user.getUserId(), DeviceUtils.getDeviceId(this), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                ToastUtil.shortShowToast(msg);
                Tools.dismissWaitDialog();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    long sendExp = data.getLong("sendExp");
                    long sendPlut = data.getLong("sendPlut");
                    long accCharm = data.getLong("accCharm");
                    Tools.sendGift(phone,contentBean.getGiftType(),contentBean.getGiftPrice()+"",contentBean.getGiftName(),contentBean.getGiftUrl(),giftNum+"",""+sendExp,""+sendPlut,""+accCharm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
                Tools.dismissWaitDialog();
            }
        }, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
