package com.qianyu.communicate.fragment;

import com.qianyu.communicate.adapter.GiftFuBowAdapter;

import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class GiftFuBowFragment extends BaseListFragment {
    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {

    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new GiftFuBowAdapter(getActivity(),null);
    }

    @Override
    protected int getLineNum() {
        return 2;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
