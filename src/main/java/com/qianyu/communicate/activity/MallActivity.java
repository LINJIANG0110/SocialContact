package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.fragment.HotToolFragment;
import com.qianyu.communicate.views.banner.Banner;
import com.qianyu.communicate.views.banner.listener.OnBannerClickListener;
import com.qianyu.communicate.views.banner.loader.FrescoImageLoader;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import com.qianyu.communicate.base.BaseActivity;
import net.neiquan.applibrary.utils.SmartItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class MallActivity extends BaseActivity {
    @InjectView(R.id.mMallBanner)
    Banner mMallBanner;
    @InjectView(R.id.smartTabLayout)
    SmartTabLayout smartTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> bannersImg = new ArrayList<String>();

    @Override
    public int getRootViewId() {
        return R.layout.activity_mall;
    }

    @Override
    public void setViews() {
        setTitleTv("商城");
        initBanner();
        initSmartItems();
    }

    @Override
    public void initData() {

    }

    private void initSmartItems() {
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
            viewPager.setAdapter(adapter);
            smartTabLayout.setViewPager(viewPager);
        }
    }

    private void initBanner() {
        bannersImg.clear();
        bannersImg.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3052930844,3064262324&fm=27&gp=0.jpg");
        bannersImg.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2756706171,599458151&fm=27&gp=0.jpg");
        bannersImg.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1819487004,4229590880&fm=27&gp=0.jpg");
        mMallBanner.setImages(bannersImg).setDelayTime(3000)
//                .setBannerAnimation(FlipHorizontalTransformer.class)
                .setImageLoader(new FrescoImageLoader())
                .start();
        mMallBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mMallBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }

    public List<SmartItem> getSmartItems() {
        List<SmartItem> smartItems=new ArrayList<>();
        Bundle bundle=new Bundle();
        bundle.putInt("type",1);
        smartItems.add(new SmartItem("热卖道具",HotToolFragment.class,bundle));
        Bundle bundle1=new Bundle();
        bundle1.putInt("type",2);
        smartItems.add(new SmartItem("礼包",HotToolFragment.class,bundle1));
        Bundle bundle2=new Bundle();
        bundle2.putInt("type",3);
        smartItems.add(new SmartItem("月亮",HotToolFragment.class,bundle2));
        return smartItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
