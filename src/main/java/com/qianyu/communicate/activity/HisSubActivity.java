package com.qianyu.communicate.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.HisSubAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.User;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class HisSubActivity extends BaseListActivity {

    private TextView mContentTv;
    private int totalPerson;
    private int userId;
    private HisSubAdapter adapter;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_head_buy_record, null);
        mContentTv = headView.findViewById(R.id.mContentTv);
        mContentTv.setText("共收藏了93个节目");
        mRecyclerview.addHeaderView(headView);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        adapter = new HisSubAdapter(this, null);
        return adapter;
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            userId = getIntent().getIntExtra("userId", 0);
            User user = MyApplication.getInstance().user;
            if(user!=null&&userId== user.getUserId()){
                setTitleTv("我的收藏");
                adapter.setDeleteble(true);
            }else{
                setTitleTv("Ta的收藏");
                adapter.setDeleteble(false);
            }
        }
        loadDatas();
    }

    private void loadDatas() {
    }

    @Override
    public void onRetryClick() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        loadDatas();
    }
}
