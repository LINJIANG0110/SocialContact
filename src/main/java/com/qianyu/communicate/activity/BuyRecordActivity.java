package com.qianyu.communicate.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.BuyRecordAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.jzvd.JZVideoPlayer;

import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class BuyRecordActivity extends BaseListActivity {

    private TextView contenTv;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("购买记录");
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_head_buy_record, null);
        contenTv = headView.findViewById(R.id.mContentTv);
        mRecyclerview.addHeaderView(headView);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new BuyRecordAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        loadDatas();
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
            //userId 某个人的id
//            Tools.showDialog(this);
        }
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

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
