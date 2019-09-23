package com.qianyu.communicate.test;

import android.view.View;


import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by wl_user on 2017/7/11.
 */

public class TwoActivity extends BaseListActivity {
    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("我是AcitivtyTwo");
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        MyAdapter myAdapter = new MyAdapter(this, null);
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
