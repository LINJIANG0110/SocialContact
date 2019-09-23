package com.qianyu.communicate.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.CityChooseadapter;
import com.qianyu.communicate.adapter.CityHeaddapter;

import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class CityChooseActivity extends BaseListActivity {
    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("选择城市");
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_city_head, null);
        RecyclerView mHeadRecylerView = headView.findViewById(R.id.mHeadRecylerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mHeadRecylerView.setLayoutManager(layoutManager);
        mHeadRecylerView.setAdapter(new CityHeaddapter(this, null));
        mRecyclerview.addHeaderView(headView);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new CityChooseadapter(this, null);
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
