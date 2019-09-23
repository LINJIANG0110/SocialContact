package com.qianyu.communicate.test;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseViewPagerFragment_Icon;

import java.util.ArrayList;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */

public class FourFragment extends BaseViewPagerFragment_Icon {
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
    public String[] getTitle() {
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
        setTitleTv("点我跳转");
        setTitleOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),FourActivity.class));
            }
        });
    }
}
