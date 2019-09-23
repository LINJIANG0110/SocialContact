package com.qianyu.communicate.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.MyConcernAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.entity.ConcernList;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class MyConcernActivity extends BaseListActivity {

    private TextView mContentTv;
    private int totalPerson;
    private String sign;
    private long userId;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
//        View headView = LayoutInflater.from(this).inflate(R.layout.layout_head_buy_record, null);
//        mContentTv = headView.findViewById(R.id.mContentTv);
//        mRecyclerview.addHeaderView(headView);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new MyConcernAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            sign = getIntent().getStringExtra("sign");
            userId = getIntent().getLongExtra("userId", 0);
            User user = MyApplication.getInstance().user;
            switch (sign){
                case "1":
                    setTitleTv((user!=null&&user.getUserId()==userId)?"我的关注":"Ta的关注");
                    break;
                case "2":
                    setTitleTv((user!=null&&user.getUserId()==userId)?"我的粉丝":"Ta的粉丝");
                    break;
            }
            loadDatas();
        }
    }

    private void loadDatas() {
        NetUtils.getInstance().userFans(userId, pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                ConcernList circleList = (ConcernList) model.getModel();
                if (circleList != null) {
                    List<ConcernList.ContentBean> list = circleList.getContent();
                    if (mRecyclerview != null && mRefeshLy != null && adapter != null) {
                        mRecyclerview.loadMoreComplete();
                        mRecyclerview.refreshComplete();
                        mRefeshLy.hideAll();
                        if (pageNum == 0) {
                            adapter.clear();
                        }
                        adapter.append(list);
                        if (list == null || list.size() < PAEG_SIZE) {
                            if (pageNum == 0 && (list == null || list.size() == 0)) {
                                mRefeshLy.showEmptyView();
                            }
                            mRecyclerview.setLoadingMoreEnabled(false);
                        } else {
                            mRecyclerview.setLoadingMoreEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                if (mRecyclerview != null && mRefeshLy != null) {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    mRefeshLy.showFailView();
                }
            }
        }, ConcernList.class);
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
