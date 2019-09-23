package com.qianyu.communicate.test;

import android.support.v4.app.Fragment;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseViewPagerActivity_Icon;

import java.util.ArrayList;

/**
 * Created by wl_user on 2017/7/11.
 */

public class FourActivity extends BaseViewPagerActivity_Icon {
    private  String[] titles = new String[]{"One", "Two", "Three"};


    @Override
    public ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new TabFragment());
        fragments.add(new TabFragment());
        fragments.add(new TabFragment());
        return fragments;
    }

    @Override
    public String[] getTitles() {
        return titles;
    }

    @Override
    public int[] getTabIcons() {
        return tabIcons;
    }

    @Override
    public int[] getTabIconsPressed() {
        return tabIconsPressed;
    }

    private int[] tabIcons = {
            R.mipmap.iconfont_downgrey,
            R.mipmap.iconfont_downgrey,
            R.mipmap.iconfont_downgrey,
    };

    private int[] tabIconsPressed = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
    };

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setData(int currentItem) {
    }

    @Override
    public void initData() {
          setTitleTv("我是ActivityFour");
    }
}
