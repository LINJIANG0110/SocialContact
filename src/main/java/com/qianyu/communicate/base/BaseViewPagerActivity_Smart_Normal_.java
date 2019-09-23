package com.qianyu.communicate.base;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import net.neiquan.applibrary.R;
import net.neiquan.applibrary.utils.SmartItem;

import java.util.List;


/**
 * Created by wl_user on 2017/7/11.
 */

public abstract class BaseViewPagerActivity_Smart_Normal_ extends BaseActivity {
    protected SmartTabLayout mSmartTabLayout;
    protected ViewPager mViewPager;
    private RelativeLayout mHeadView;
    protected EditText etSendmessage;
    protected  TextView countryTv;
    protected ImageView ivFaceNormal;
    protected ImageView ivFaceChecked;
    protected  RelativeLayout rlFace;
    protected   TextView btnSend;
    protected   FrameLayout emotionContainer;

    @Override
    public int getRootViewId() {
        return R.layout.base_viewpager_st;
    }

    @Override
    public void setViews() {
        mHeadView = findViewById(R.id.head_view);
        mSmartTabLayout = findViewById(R.id.smartTabLayout);
        mViewPager = findViewById(R.id.viewPager);
        etSendmessage = findViewById(R.id.et_sendmessage);
        countryTv = findViewById(R.id.countryTv);
        ivFaceNormal = findViewById(R.id.iv_face_normal);
        ivFaceChecked = findViewById(R.id.iv_face_checked);
        rlFace = findViewById(R.id.rl_face);
        btnSend = findViewById(R.id.btn_send);
        emotionContainer = findViewById(R.id.emotionContainer1);
        isHaveHead();
        if (isHaveHead()) {
            mHeadView.setVisibility(View.VISIBLE);
        } else {
            mHeadView.setVisibility(View.GONE);
        }
        List<SmartItem> smartItems = getSmartItems();
        FragmentPagerItems.Creator add = FragmentPagerItems.with(this);
        if (smartItems != null && smartItems.size() > 0) {
            for (SmartItem smartItem : smartItems) {
                if (smartItem.bundleExtra != null) {
                    add = add.add(smartItem.title, smartItem.fragmentClass, smartItem.bundleExtra);
                } else {
                    add = add.add(smartItem.title, smartItem.fragmentClass);
                }
            }
            FragmentStatePagerItemAdapter adapter = new FragmentStatePagerItemAdapter(
                    getSupportFragmentManager(), add
                    .create());
            mViewPager.setAdapter(adapter);
            mSmartTabLayout.setViewPager(mViewPager);
        }
    }

    public abstract boolean isHaveHead();

    public abstract List<SmartItem> getSmartItems();
}
