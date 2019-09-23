package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.FriendSelectActivity;
import com.qianyu.communicate.activity.PublishCircleActivity;
import com.qianyu.communicate.activity.RedpackageResActivity;
import com.qianyu.communicate.activity.TopicCreateActivity;
import com.qianyu.communicate.base.BaseViewPagerFragment_Smart_Normal1;
import com.qianyu.communicate.utils.SpringUtils;

import net.neiquan.applibrary.utils.SmartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JavaDog on 2019-3-7.
 */

public class HomeNearFragment extends BaseViewPagerFragment_Smart_Normal1 {
    private int p = 0;

    @Override
    public boolean isHaveHead() {
        return false;
    }

    @Override
    public List<SmartItem> getSmartItems() {
        List<SmartItem> smartItems = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        smartItems.add(new SmartItem("附近的人", FriendNearFragment.class, bundle));
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 2);
        smartItems.add(new SmartItem("附近动态", FriendsCircleFragment.class, bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 3);
        smartItems.add(new SmartItem("话题", FriendsTopicFragment.class, bundle2));
        return smartItems;
    }

    @Override
    public void initData() {
        mPublishCicle.setVisibility(View.VISIBLE);
        setmPublishCicle(R.mipmap.sy_shaixuan);
        setPublishOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpringUtils.springAnim(getmPublishCicle());
                if (p == 0) {
                    startActivity(new Intent(getActivity(), FriendSelectActivity.class));
                } else if (p == 1) {
                    startActivity(new Intent(getActivity(), PublishCircleActivity.class));
                } else if (p == 2) {
                    startActivity(new Intent(getActivity(), TopicCreateActivity.class));
                }
            }
        });
        setEntranceOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RedpackageResActivity.class));//BroadcastCreateActivity RedpackageActivity
            }
        });
        ((TextView) mSmartTabLayout.getTabAt(0)).setTextSize(18);
        ((TextView) mSmartTabLayout.getTabAt(1)).setTextSize(13);
        ((TextView) mSmartTabLayout.getTabAt(2)).setTextSize(13);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                p = position;
                mPublishCicle.setVisibility(View.VISIBLE);
                setmPublishCicle(position == 0 ? R.mipmap.sy_shaixuan : position == 1 ? R.mipmap.dongtai_xiedongtai : -1);// R.mipmap.add_topic
                ((TextView) mSmartTabLayout.getTabAt(position)).setTextSize(18);
                if (position == 0) {
                    ((TextView) mSmartTabLayout.getTabAt(1)).setTextSize(13);
                    ((TextView) mSmartTabLayout.getTabAt(2)).setTextSize(13);
                } else if (position == 1) {
                    ((TextView) mSmartTabLayout.getTabAt(0)).setTextSize(13);
                    ((TextView) mSmartTabLayout.getTabAt(2)).setTextSize(13);
                } else {
                    ((TextView) mSmartTabLayout.getTabAt(1)).setTextSize(13);
                    ((TextView) mSmartTabLayout.getTabAt(0)).setTextSize(13);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
