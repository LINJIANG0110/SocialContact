package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.FansList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.fragment.DayListFragment;
import com.qianyu.communicate.views.MarqueeView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import com.qianyu.communicate.base.BaseActivity;

import net.neiquan.applibrary.utils.SmartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class FansRankListActivity extends BaseActivity {
    @InjectView(R.id.smartTabLayout)
    SmartTabLayout mSmartTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mv_bar)
    MarqueeView mvBar;
    @InjectView(R.id.contionbutionRL)
    RelativeLayout contionbutionRL;
    @InjectView(R.id.upOrDowTv)
    TextView upOrDowTv;
    @InjectView(R.id.contributionTv)
    TextView contributionTv;
    @InjectView(R.id.mSendGiftTv)
    TextView mSendGiftTv;
    private String userId;
    private String fansId;
    private FansList fansList;

    @Override
    public int getRootViewId() {
        return R.layout.activity_fans_rank;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        setTitleTv("贡献榜");
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            fansId = getIntent().getStringExtra("fansId");
            if (TextUtils.isEmpty(fansId)) {
                contionbutionRL.setVisibility(View.GONE);
            } else {
                contionbutionRL.setVisibility(View.VISIBLE);
            }
            initSmartItem();
            mSendGiftTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FansRankListActivity.this, GiftListActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {

    }

    @Override
    public void initData() {
        //设置集合
        List<String> items = new ArrayList<>();
        items.add("@梦逍遥   送给   美人鱼的嫁衣   三个丘比特之箭");
        items.add("@孙思邈   送给   华佗   三个大宝剑");
        items.add("@老中医   送给   女神经 四头皮皮虾、三条皮皮膳、二个皮皮蟹和一根超级巨无霸的大黄瓜，哈哈哈哈哈哈哈哈哈！");
//        mvBar.startWithText("@老中医   送给   女神经 四头皮皮虾、三条皮皮膳、二个皮皮蟹和一根超级巨无霸的大黄瓜，哈哈哈哈哈哈哈哈哈！");
        mvBar.startWithList(items);
    }

    private void initSmartItem() {
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


    public List<SmartItem> getSmartItems() {
        List<SmartItem> smartItems = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("sign", "1");
        bundle.putString("userId", userId);
        bundle.putString("fansId", fansId);
        smartItems.add(new SmartItem("日榜", DayListFragment.class, bundle));
        Bundle bundle1 = new Bundle();
        bundle1.putString("sign", "2");
        bundle1.putString("userId", userId);
        bundle1.putString("fansId", fansId);
        smartItems.add(new SmartItem("周榜", DayListFragment.class, bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putString("sign", "3");
        bundle2.putString("userId", userId);
        bundle2.putString("fansId", fansId);
        smartItems.add(new SmartItem("总榜", DayListFragment.class, bundle2));
        return smartItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
