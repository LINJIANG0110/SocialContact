package com.qianyu.communicate.activity;

import android.content.Intent;
import android.view.View;

import com.qianyu.communicate.adapter.MyQAndAAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.QAList;
import com.qianyu.communicate.entity.User;

import java.util.List;

/**
 * Created by JavaDog on 2018-5-29.
 */

public class MyQAndAActivity extends BaseListActivity {

    private boolean live;
    private MyQAndAAdapter myQAndAAdapter;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        User user = MyApplication.getInstance().user;
//        live = TextUtils.equals("2", user.getStatus());
        setTitleTv(live ? "我的咨询" : "学员提问");
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        myQAndAAdapter = new MyQAndAAdapter(this, null);
        return myQAndAAdapter;
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        myQAndAAdapter.setLive(live);
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                QAList qaList = (QAList) data.get(position);
                Intent intent = new Intent(MyQAndAActivity.this, QADetailActivity.class);
                intent.putExtra("iId",qaList.getiId());
                startActivity(intent);
            }
        });
        loadDatas();
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
//        NetUtils.getInstance().qaList(0, 244, live?2:1, 0, 0, pageNum, PAEG_SIZE, new NetCallBack() {
    }

    @Override
    public void onRetryClick() {
        pageNum=0;
        loadDatas();
    }

    @Override
    public void onRefresh() {
        pageNum=0;
        loadDatas();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        loadDatas();
    }
}
