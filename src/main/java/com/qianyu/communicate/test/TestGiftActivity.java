package com.qianyu.communicate.test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.GiftModel;
import com.qianyu.communicate.giftlibrary.CustormAnim;
import com.qianyu.communicate.giftlibrary.GiftControl;

import com.qianyu.communicate.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class TestGiftActivity extends BaseActivity {
    @InjectView(R.id.sendGift)
    Button sendGift;
    @InjectView(R.id.ll_gift_parent)
    LinearLayout giftParent;
    private GiftModel giftModel;
    private String giftUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511271301814&di=2b542764d69ffd6425ac445fa7bf90ac&imgtype=0&src=http%3A%2F%2Fimg10.360buyimg.com%2Fn0%2Fjfs%2Ft277%2F296%2F1179312606%2F378234%2Fc47b9387%2F543242bdNd0823674.jpg";
    private boolean currentStart;
    private GiftControl giftControl;
    private String userImg = "http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg";

    @Override
    public int getRootViewId() {
        return R.layout.activity_test_gift;
    }

    @Override
    public void setViews() {
        giftControl = new GiftControl(this);
        giftControl.setGiftLayout(false, giftParent, 3);
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.sendGift)
    public void onViewClicked() {
        giftModel = new GiftModel();
        giftModel.setGiftId("礼物Id").setGiftName("礼物名字").setGiftCount(1).setGiftPic(giftUrl)
                .setSendUserId("1234").setSendUserName("吕靓茜").setSendUserPic(userImg).setSendGiftTime(System.currentTimeMillis())
                .setCurrentStart(currentStart);
        if (currentStart) {
            giftModel.setHitCombo(1);
        }
//        giftControl.loadGift(giftModel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
