package com.qianyu.communicate.activity;

import com.qianyu.communicate.adapter.DayTaskAdapter;

import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class DayTaskActivity extends BaseListActivity {
    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("每日任务");
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new DayTaskAdapter(this,null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
