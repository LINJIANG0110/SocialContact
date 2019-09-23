package com.qianyu.communicate.fragment;

import android.view.View;

import com.qianyu.communicate.adapter.BroadcastAdapter;
import com.qianyu.communicate.adapter.HomeNearAdapter;
import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.BroadcastBean;
import com.qianyu.communicate.entity.FirendNear;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import java.util.ArrayList;
import java.util.List;

import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by JavaDog on 2019-8-20.
 */

public class BroadcastFragment extends BaseListFragment {

    BroadcastAdapter broadcastAdapter;

    @Override
    protected MyBaseAdapter getAdapter() {
        broadcastAdapter = new BroadcastAdapter(getActivity(), null);
        return broadcastAdapter;
    }

    @Override
    public void initData() {
        loadDatas();
    }

    private void loadDatas() {
        List<BroadcastBean> broadcastData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BroadcastBean mBean = new BroadcastBean();
            mBean.setName("0001");
            broadcastData.add(mBean);
        }
        adapter.append(broadcastData);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void setHeadViews() {

    }

    @Override
    public void onRefresh() {

    }
}
