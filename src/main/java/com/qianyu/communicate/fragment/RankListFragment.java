package com.qianyu.communicate.fragment;

import android.os.Bundle;

import com.qianyu.communicate.base.BaseViewPagerFragment_Smart_Normal;
import net.neiquan.applibrary.utils.SmartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class RankListFragment extends BaseViewPagerFragment_Smart_Normal {
    @Override
    public boolean isHaveHead() {
        return false;
    }

    @Override
    public List<SmartItem> getSmartItems() {
        List<SmartItem> smartItems = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        smartItems.add(new SmartItem("富豪榜", RichListFragment.class, bundle));
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 2);
        smartItems.add(new SmartItem("魅力榜", RichListFragment.class, bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 3);
        smartItems.add(new SmartItem("战力榜", RichListFragment.class, bundle2));
        return smartItems;
    }

    @Override
    public void initData() {

    }
}
