package com.qianyu.communicate.test;

import android.os.Bundle;


import com.qianyu.communicate.base.BaseViewPagerActivity_Smart_Normal;
import net.neiquan.applibrary.utils.SmartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wl_user on 2017/7/11.
 */

public class ThreeActivity extends BaseViewPagerActivity_Smart_Normal {
    @Override
    public boolean isHaveHead() {
        return true;
    }

    @Override
    public List<SmartItem> getSmartItems() {
        List<SmartItem> smartItems=new ArrayList<>();
        Bundle bundle=new Bundle();
        bundle.putInt("BoardFragment",1);
        smartItems.add(new SmartItem("热播榜",ThreeFragment_.class,bundle));
        Bundle bundle1=new Bundle();
        bundle1.putInt("BoardFragment",2);
        smartItems.add(new SmartItem("热销榜",TwoFragment.class,bundle1));
        Bundle bundle2=new Bundle();
        bundle2.putInt("BoardFragment",3);
        smartItems.add(new SmartItem("热评榜",TwoFragment.class,bundle2));
        return smartItems;
    }

    @Override
    public void initData() {
        setTitleTv("我是ActivityThree");
    }
}
