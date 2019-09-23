package com.qianyu.communicate.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.MyRoomAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.User;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class MyRoomActivity extends BaseListActivity {

    private TextView mContentTv;
    private int totalPerson;
    private int userId;
    private MyRoomAdapter adapter;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("我的聊天室");
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_head_buy_record, null);
        mContentTv = headView.findViewById(R.id.mContentTv);
        mContentTv.setText("共创建了2个聊天室");
        mRecyclerview.addHeaderView(headView);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        adapter = new MyRoomAdapter(this, null);
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
                setTitleTv("我的聊天室");
                adapter.setDeleteble(true);
            }else{
                setTitleTv("Ta的聊天室");
                adapter.setDeleteble(false);
            }
        }
        loadDatas();
    }

    private void loadDatas() {
        //sign   1.我关注的人  2.关注我的人
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
