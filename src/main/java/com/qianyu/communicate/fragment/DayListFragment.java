package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.view.View;

import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.adapter.DayListAdapter;
import com.qianyu.communicate.entity.FansList;

import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class DayListFragment extends BaseListFragment {

    private String sign;
    private String userId;
    private String fansId;

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {

    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new DayListAdapter(getActivity(), null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            sign = getArguments().getString("sign");
            userId = getArguments().getString("userId");
            fansId = getArguments().getString("fansId");
            loadDatas();
        }
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                FansList.SouvenirModelsBean souvenirModelsBean = (FansList.SouvenirModelsBean) data.get(position);
//                startActivity(new Intent(getActivity(), PersonalPageActivity.class));
                Intent intent = new Intent(getActivity(), PersonalPageActivity.class);
                intent.putExtra("userId", souvenirModelsBean.getUserid());
                startActivity(intent);
            }
        });
    }

    private void loadDatas() {
//        Tools.showDialog(getActivity());
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
