package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.PublishCircleActivity;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseViewPagerFragment_Smart_Normal2;
import com.qianyu.communicate.utils.SpringUtils;

import net.neiquan.applibrary.utils.SmartItem;

import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JavaDog on 2018-5-29.
 */

public class FindFragment extends BaseViewPagerFragment_Smart_Normal2 {
    @Override
    public boolean isHaveHead() {
        return false;
    }

    @Override
    public List<SmartItem> getSmartItems() {
        List<SmartItem> smartItems = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        smartItems.add(new SmartItem("文章", ArticleListFragment.class, bundle));
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 1);
        smartItems.add(new SmartItem("问答", QAListFragment.class, bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 2);
        smartItems.add(new SmartItem("动态", FriendsCircleFragment.class, bundle2));
        return smartItems;
    }

    @Override
    public void initData() {
        setmPublishCicle(R.mipmap.dongtai_xiedongtai);
        setPublishOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpringUtils.springAnim(getmPublishCicle());
                if (MyApplication.getInstance().isLogin()) {
                    startActivity(new Intent(getActivity(), PublishCircleActivity.class));
                } else {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(getActivity(), RegistActivity.class));
                }
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getmPublishCicle().setVisibility(position == 1 ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
