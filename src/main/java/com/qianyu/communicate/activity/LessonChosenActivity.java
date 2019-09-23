package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.fragment.LessonChosenFragment;
import com.qianyu.communicate.views.NoScrollViewPager;
import com.qianyu.communicate.views.weibowindow.MoreWindow;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import com.qianyu.communicate.base.BaseActivity;

import net.neiquan.applibrary.utils.SmartItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class LessonChosenActivity extends BaseActivity {
    @InjectView(R.id.smartTabLayout)
    SmartTabLayout mSmartTabLayout;
    @InjectView(R.id.viewPager)
    NoScrollViewPager mViewPager;
    @InjectView(R.id.mChosenTv)
    TextView mChosenTv;
    @InjectView(R.id.mPlayTv)
    TextView mPlayTv;
    private MoreWindow mMoreWindow;

    @Override
    public int getRootViewId() {
        return R.layout.activity_lesson_chosen;
    }

    @Override
    public void setViews() {
        setTitleTv("临时云端");
        setNextImage(R.mipmap.z);
        setNextSearchImage(R.mipmap.liulanlishi_sousuo);
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showMoreWindow(view);
            }
        });
    }

    private void showMoreWindow(View view) {
        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow(this);
            mMoreWindow.init();
        }

        mMoreWindow.showMoreWindow(view,100);
    }

    @Override
    public void initData() {
        initSmartItem();
    }

    @OnClick({R.id.mPlayTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mPlayTv:
                break;
        }
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
        bundle.putString("fansId", "1");
        smartItems.add(new SmartItem("全部", LessonChosenFragment.class, bundle));
        Bundle bundle1 = new Bundle();
        bundle1.putString("fansId", "2");
        smartItems.add(new SmartItem("视频", LessonChosenFragment.class, bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putString("fansId", "3");
        smartItems.add(new SmartItem("图片", LessonChosenFragment.class, bundle2));
        Bundle bundle3 = new Bundle();
        bundle3.putString("fansId", "4");
        smartItems.add(new SmartItem("音频", LessonChosenFragment.class, bundle2));
        Bundle bundle4 = new Bundle();
        bundle4.putString("fansId", "4");
        smartItems.add(new SmartItem("PPT", LessonChosenFragment.class, bundle2));
        return smartItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
