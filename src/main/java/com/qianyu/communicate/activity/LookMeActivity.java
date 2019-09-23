package com.qianyu.communicate.activity;

import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.HomeNearAdapter;
import com.qianyu.communicate.adapter.LookMeAdapter;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FirendNear;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import java.util.List;

import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by JavaDog on 2019-7-25.
 */

public class LookMeActivity extends BaseListActivity {
    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("谁看过我");
        setNextTv("清空");
        getNextTv().setTextColor(getResources().getColor(R.color.look_me));
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtils.getInstance().deleteLook("", 1, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        adapter.clear();
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {

                    }
                },null);
            }
        });
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new LookMeAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void onRetryClick() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void initData() {
        loadDatas();
    }

    private void loadDatas() {
        NetUtils.getInstance().lookMe(pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                FirendNear firendNear = (FirendNear) model.getModel();
                if (firendNear != null) {
                    List<FirendNear.ContentBean> list = firendNear.getContent();
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
        }, FirendNear.class);
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
