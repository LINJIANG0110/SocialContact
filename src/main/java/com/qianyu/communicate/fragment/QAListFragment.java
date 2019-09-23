package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.QADetailActivity;
import com.qianyu.communicate.adapter.QAListAdapter;
import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.QAList;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class QAListFragment extends BaseListFragment {

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {

    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new QAListAdapter(getActivity(), null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        setmFloatActionBtn(R.mipmap.dongtai_dingduan);
        setFloatActionBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerview.scrollToPosition(0);
            }
        });
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                QAList qaList = (QAList) data.get(position);
                Intent intent = new Intent(getActivity(), QADetailActivity.class);
                intent.putExtra("iId",qaList.getiId());
                startActivity(intent);
            }
        });
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
