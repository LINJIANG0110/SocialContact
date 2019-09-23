package com.qianyu.communicate.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.HomeFriendAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.User;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class MyCollectActivity extends BaseListActivity {

    private TextView mContentTv;
    private int totalPerson;

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
        return new HomeFriendAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        setTitleTv("我的收藏");
        loadDatas();
    }

    private void loadDatas() {
        //sign   1.我关注的人  2.关注我的人
        User user = MyApplication.getInstance().user;
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
