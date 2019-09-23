package com.qianyu.communicate.base;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
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

public abstract class BaseViewPagerFragment_Smart_Normal1 extends BaseFragment {

    protected SmartTabLayout mSmartTabLayout;
    protected ViewPager mViewPager;
    protected ImageView mPublishCicle;
    protected  ImageView ivEntrance;
    private ImmersionBar mImmersionBar;
    @Override
    public int getRootViewId() {
        return R.layout.base_viewpager_fragment_smart_normal1;
    }

    @Override
    public void setViews() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true);  //状态栏字体是深色，不写默认为亮色
        mImmersionBar.init();
        ivEntrance = rootView.findViewById(R.id.iv_entrance);
        mSmartTabLayout = rootView.findViewById(R.id.smartTabLayout);
        mViewPager = rootView.findViewById(R.id.viewPager);
        mPublishCicle = rootView.findViewById(R.id.mPublishCicle);
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

    public void setEntranceOnClick(View.OnClickListener onClick) {
        ivEntrance.setOnClickListener(onClick);
    }

    public void setmPublishCicle(int id) {
        if (id == -1){
            mPublishCicle.setVisibility(View.GONE);
        }else {
            mPublishCicle.setVisibility(View.VISIBLE);
            mPublishCicle.setImageResource(id);
        }
    }

    public ImageView getmPublishCicle() {
        return mPublishCicle;
    }

    public void setPublishOnClick(View.OnClickListener onClick) {
        mPublishCicle.setOnClickListener(onClick);
    }

    public abstract boolean isHaveHead();

    public abstract List<SmartItem> getSmartItems();

}
