package com.qianyu.communicate.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.FragmentAdapter;
import com.qianyu.communicate.fragment.GiftFragment;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * 红包记录
 */
public class RedPackageJlActivity extends BaseActivity {
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    private String[] titles = new String[]{"", "", ""};
    public ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        GiftFragment giftFragment1 = new GiftFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("gift", 1);
        giftFragment1.setArguments(bundle1);
        fragments.add(giftFragment1);

        GiftFragment giftFragment2 = new GiftFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("gift", 2);
        giftFragment2.setArguments(bundle2);
        fragments.add(giftFragment2);

        GiftFragment giftFragment3 = new GiftFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("gift", 3);
        giftFragment3.setArguments(bundle3);
        fragments.add(giftFragment3);
        return fragments;
    }
    @Override
    public int getRootViewId() {
        return R.layout.activity_red_package_jl;
    }

    @Override
    public void setViews() {
//        setTitleTv("红包记录");
//        setNextTv("2019年");
        setupViewPager();
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private int[] tabIcons = {
            R.mipmap.wallet,
            R.mipmap.coin,
            R.mipmap.diamond,
    };

    private int[] tabIconsPressed = {
            R.mipmap.wallet1,
            R.mipmap.coin1,
            R.mipmap.diamond1,
    };

    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = view.findViewById(net.neiquan.applibrary.R.id.img_title);
        TextView txt_title = view.findViewById(net.neiquan.applibrary.R.id.txt_title);
        //这个地方设置字体选中颜色
        txt_title.setTextColor(Color.RED);
        if (titles != null && titles.length > 0 && tabIconsPressed != null && tabIconsPressed.length > 0 && titles.length == tabIconsPressed.length) {
            img_title.setImageResource(tabIconsPressed[tab.getPosition()]);
        }
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = view.findViewById(net.neiquan.applibrary.R.id.img_title);
        TextView txt_title = view.findViewById(net.neiquan.applibrary.R.id.txt_title);
        //这个地方设置字体未选中颜色
        txt_title.setTextColor(Color.BLACK);
        if (titles != null && titles.length > 0 && tabIcons != null && tabIcons.length > 0 && titles.length == tabIcons.length) {
            img_title.setImageResource(tabIcons[tab.getPosition()]);
        }
    }

    private void setupViewPager() {
        ArrayList<Fragment> fragments = getFragments();
        if (titles != null && titles.length > 0 && fragments != null && fragments.size() > 0 && titles.length == fragments.size()) {
//            for (int i = 0; i < title.length; i++) {
//                mTabLayout.addTab(mTabLayout.newTab().setText(title[i]));
//            }
            FragmentAdapter adapter =
                    new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
            mViewPager.setAdapter(adapter);
            mTabLayout.setupWithViewPager(mViewPager);
            mTabLayout.setTabsFromPagerAdapter(adapter);
            for (int i = 0; i < titles.length; i++) {
                mTabLayout.getTabAt(i).setCustomView(getTabView(i, titles));
            }
        }
    }

    public View getTabView(int position, String[] titles) {
        //这是头部导航的布局
        View view = LayoutInflater.from(this).inflate(net.neiquan.applibrary.R.layout.item_tab, null);
        TextView txt_title = view.findViewById(net.neiquan.applibrary.R.id.txt_title);
        txt_title.setText(titles[position]);
        ImageView img_title = view.findViewById(net.neiquan.applibrary.R.id.img_title);
        img_title.setImageResource(tabIcons[position]);
        if (position == 0) {
            //这个地方设置初始字体选中颜色
            txt_title.setTextColor(Color.RED);
            img_title.setImageResource(tabIconsPressed[position]);
        } else {
            //这个地方设置初始字体未选中颜色
            txt_title.setTextColor(Color.BLACK);
            img_title.setImageResource(tabIcons[position]);
        }
        return view;
    }
    @Override
    public void initData() {

    }
}
