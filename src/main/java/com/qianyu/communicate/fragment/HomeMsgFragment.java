package com.qianyu.communicate.fragment;

import com.qianyu.communicate.adapter.HomeMsgAdapter;

import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class HomeMsgFragment extends BaseListFragment {
    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {

    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new HomeMsgAdapter(getActivity(),null);
    }

    @Override
    protected int getLineNum() {
        return 0;
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
