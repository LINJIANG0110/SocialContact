package com.qianyu.communicate.base;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import net.neiquan.applibrary.R;

import java.util.ArrayList;

/**
 * 作者:  dyj
 * 时间: 2016/3/3.
 */
public abstract class BaseViewPagerFragment_Icon extends BaseFragment {


    public RelativeLayout mHeadView;
    public TabLayout mTabLayout;
    public ViewPager mViewPager;
    public LinearLayout mMainContent;
    private String[] title;
    private int[] tabIcons;
    private int[] tabIconsPressed;

    @Override
    public int getRootViewId() {
        return R.layout.base_viewpager_fragment_icon;
    }

    private void setupViewPager() {
        title = getTitle();
        tabIcons = getTabIcons();
        tabIconsPressed = getTabIconsPressed();
        ArrayList<Fragment> fragments = getFragments();
        if (title != null && title.length > 0 && fragments != null && fragments.size() > 0 && title.length == fragments.size()) {
//            for (int i = 0; i < title.length; i++) {
//                mTabLayout.addTab(mTabLayout.newTab().setText(title[i]));
//            }
            FragmentAdapter adapter =
                    new FragmentAdapter(getChildFragmentManager(), fragments, title);
            mViewPager.setAdapter(adapter);
            mTabLayout.setupWithViewPager(mViewPager);
            mTabLayout.setTabsFromPagerAdapter(adapter);
            for (int i = 0; i < title.length; i++) {
                mTabLayout.getTabAt(i).setCustomView(getTabView(i, title));
            }
        }
    }

    public View getTabView(int position, String[] titles) {
        //这是头部导航的布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab, null);
        TextView txt_title = view.findViewById(R.id.txt_title);
        txt_title.setText(titles[position]);
        ImageView img_title = view.findViewById(R.id.img_title);
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


    public abstract ArrayList<Fragment> getFragments();

    public abstract String[] getTitle();

    public abstract int[] getTabIcons();

    public abstract int[] getTabIconsPressed();

    protected abstract boolean isHaveHead();


    @Override
    public void setViews() {
        mHeadView = rootView.findViewById(R.id.head_view);
        mTabLayout = rootView.findViewById(R.id.tabLayout);
        mViewPager = rootView.findViewById(R.id.viewPager);
        mMainContent = rootView.findViewById(R.id.main_content);
        setHeadView(isHaveHead());
        setupViewPager();
        setData(mViewPager.getCurrentItem());
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

    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = view.findViewById(R.id.img_title);
        TextView txt_title = view.findViewById(R.id.txt_title);
        //这个地方设置字体选中颜色
        txt_title.setTextColor(Color.RED);
        if (title != null && title.length > 0 && tabIconsPressed != null && tabIconsPressed.length > 0 && title.length == tabIconsPressed.length) {
            img_title.setImageResource(tabIconsPressed[tab.getPosition()]);
        }
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = view.findViewById(R.id.img_title);
        TextView txt_title = view.findViewById(R.id.txt_title);
        //这个地方设置字体未选中颜色
        txt_title.setTextColor(Color.BLACK);
        if (title != null && title.length > 0 && tabIcons != null && tabIcons.length > 0 && title.length == tabIcons.length) {
            img_title.setImageResource(tabIcons[tab.getPosition()]);
        }
    }

    /**
     * 返回第一个选中
     *
     * @param currentItem
     */
    protected void setSelectFragment(int currentItem) {
        mViewPager.setCurrentItem(currentItem);
    }

    /**
     * 当返回为true时候设置
     */
    protected abstract void setData(int currentItem);

    private void setHeadView(boolean isHaveHead) {
        if (!isHaveHead) {
            mHeadView.setVisibility(View.GONE);
        }
    }

    protected void setTitleTv(int id) {
        TextView tv = rootView.findViewById(R.id.title_tv);
        tv.setText(id);
    }

    protected void setTitleTv(String str) {
        TextView tv = rootView.findViewById(R.id.title_tv);
        tv.setText(str);
    }

    protected void setBackTv(String str) {
        TextView tv = rootView.findViewById(R.id.back_tv);
        tv.setText(str);
    }

    protected void setNextTv(String str) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.VISIBLE);
        tv.setText(str);
        View iv = rootView.findViewById(R.id.next_img);
        iv.setVisibility(View.GONE);
    }

    protected TextView getNextTv() {

        return (TextView) rootView.findViewById(R.id.next_tv);
    }

    protected void setBackGone() {
        View tv = rootView.findViewById(R.id.ly_back);
        tv.setVisibility(View.GONE);
    }

    protected void setBackOnClick(View.OnClickListener onClick) {
        LinearLayout ly_back = rootView.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(onClick);
        View iv = rootView.findViewById(R.id.back_img);
        iv.setOnClickListener(onClick);
    }


    @SuppressLint("NewApi")
    protected void setNextTvBG(Drawable drawable) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setBackground(drawable);
    }

    protected void setBackImage(int id) {
        ImageView iv = rootView.findViewById(R.id.back_img);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(id);
    }

    protected void setNextImage(int id) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.GONE);
        ImageView iv = rootView.findViewById(R.id.next_img);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(id);
    }

    protected void setNextOnClick(View.OnClickListener onClick) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setOnClickListener(onClick);
        View iv = rootView.findViewById(R.id.next_img);
        iv.setOnClickListener(onClick);
    }

    protected void setNextGone() {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.GONE);
        ImageView iv = rootView.findViewById(R.id.next_img);
        iv.setVisibility(View.GONE);
    }

}
