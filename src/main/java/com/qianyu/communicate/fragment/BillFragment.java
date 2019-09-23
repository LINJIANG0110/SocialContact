package com.qianyu.communicate.fragment;

import android.os.Bundle;

import com.qianyu.communicate.base.BaseViewPagerFragment_Smart_Normal_;
import net.neiquan.applibrary.utils.SmartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class BillFragment extends BaseViewPagerFragment_Smart_Normal_ {

    private int type;

    @Override
    public boolean isHaveHead() {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        return false;
    }

    @Override
    public List<SmartItem> getSmartItems() {
        List<SmartItem> smartItems = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("type1", 1);
        smartItems.add(new SmartItem("福宝", FuBowFragment.class, bundle));
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", type);
        bundle1.putInt("type1", 2);
        smartItems.add(new SmartItem("钻石", FuBowFragment.class, bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", type);
        bundle2.putInt("type1", 3);
        smartItems.add(new SmartItem("金币", FuBowFragment.class, bundle2));
        return smartItems;
    }

    @Override
    public void initData() {

    }
}
