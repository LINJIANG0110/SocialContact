package com.qianyu.communicate.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qianyu.communicate.base.BaseViewPagerFragment_Smart_Normal;
import net.neiquan.applibrary.utils.SmartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */

public class ThreeFragment extends BaseViewPagerFragment_Smart_Normal {
    @Override
    public boolean isHaveHead() {
        return true;
    }

    @Override
    public List<SmartItem> getSmartItems() {
        List<SmartItem> smartItems = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("BoardFragment", 1);
        smartItems.add(new SmartItem("热播榜", ThreeFragment_.class, bundle));
        Bundle bundle1 = new Bundle();
        bundle1.putInt("BoardFragment", 2);
        smartItems.add(new SmartItem("热销榜", TwoFragment.class, bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putInt("BoardFragment", 3);
        smartItems.add(new SmartItem("热评榜", TwoFragment.class, bundle2));
        return smartItems;
    }

    @Override
    public void initData() {
        setTitleTv("点我跳转");
        setTitleOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ThreeActivity.class));
            }
        });
    }

}
