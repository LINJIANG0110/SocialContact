package com.qianyu.communicate.test;

import android.view.View;


import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */

public class TabFragment extends BaseListFragment {
    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {

    }

    @Override
    protected MyBaseAdapter getAdapter() {
        MyAdapter myAdapter = new MyAdapter(getActivity(), null);
        myAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {

            }
        });
        return myAdapter;
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void initData() {
    }

    @Override
    public void onRetryClick() {

    }
}
