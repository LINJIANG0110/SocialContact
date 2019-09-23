package com.qianyu.communicate.base;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import net.neiquan.applibrary.R;
import net.neiquan.applibrary.utils.SmartItem;

import java.util.List;


/**
 * 作者 ： dyj
 * 时间 ： 2016/8/16.
 */

public abstract class BaseViewPagerFragment_Smart_Normal extends BaseFragment {

    protected SmartTabLayout mSmartTabLayout;
    protected ViewPager mViewPager;

    @Override
    public int getRootViewId() {
        return R.layout.base_viewpager_fragment_smart_normal;
    }

    @Override
    public void setViews() {
        mSmartTabLayout = rootView.findViewById(R.id.smartTabLayout);
        mViewPager = rootView.findViewById(R.id.viewPager);
        isHaveHead();
        if (isHaveHead()) {
            setHeadVisibility(View.VISIBLE);
        } else {
            setHeadVisibility(View.GONE);
        }

        List<SmartItem> smartItems = getSmartItems();
        FragmentPagerItems.Creator add = FragmentPagerItems.with(getActivity());
        if (smartItems != null && smartItems.size() > 0) {
            for (SmartItem smartItem : smartItems) {
                if (smartItem.bundleExtra != null) {
                    add = add.add(smartItem.title, smartItem.fragmentClass, smartItem.bundleExtra);
                } else {
                    add = add.add(smartItem.title, smartItem.fragmentClass);
                }
            }
            FragmentStatePagerItemAdapter adapter = new FragmentStatePagerItemAdapter(
                    getChildFragmentManager(), add
                    .create());
            mViewPager.setAdapter(adapter);
            mSmartTabLayout.setViewPager(mViewPager);
        }
    }

    public abstract boolean isHaveHead();

    public abstract List<SmartItem> getSmartItems();

}
