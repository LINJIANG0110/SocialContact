package com.qianyu.communicate.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.HomeFriendAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class HomeFriendFragment extends BaseListFragment {

    private TextView mContentTv;
    private int totalPerson;

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_head_buy_record, null);
        mContentTv = headView.findViewById(R.id.mContentTv);
        mContentTv.setText("共收藏了93个节目");
        mRecyclerview.addHeaderView(headView);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new HomeFriendAdapter(getActivity(), null);
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
