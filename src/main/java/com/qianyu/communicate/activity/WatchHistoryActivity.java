package com.qianyu.communicate.activity;


import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.qianyu.communicate.adapter.WatchHistoryAdapter;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.BukaHelper_;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.WatchHistory;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JavaDog on 2018-8-1.
 */

public class WatchHistoryActivity extends BaseListActivity {

    private boolean delete = false;
    private WatchHistoryAdapter chatPreViewAdapter;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("浏览历史");
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        chatPreViewAdapter = new WatchHistoryAdapter(this, null);
        return chatPreViewAdapter;
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void back(View view) {
        if (delete) {
            WatchHistory watchHistory = chatPreViewAdapter.getWatchHistory();
            List<WatchHistory.HistoryBean> todaylist = watchHistory.getTodaylist();
            List<WatchHistory.HistoryBean> historylist = watchHistory.getHistorylist();
            for (int i = 0; i < todaylist.size(); i++) {
                todaylist.get(i).setSelected(true);
            }
            for (int i = 0; i < historylist.size(); i++) {
                historylist.get(i).setSelected(true);
            }
            chatPreViewAdapter.notifyDataSetChanged();
        } else {
            finish();
        }
    }

    @Override
    public void initData() {
        setNextTv("删除");
        setDeleteBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupIds = "";
                List<WatchHistory.HistoryBean> historyBeans = new ArrayList<>();
                WatchHistory watchHistory = chatPreViewAdapter.getWatchHistory();
                List<WatchHistory.HistoryBean> todaylist = watchHistory.getTodaylist();
                List<WatchHistory.HistoryBean> historylist = watchHistory.getHistorylist();
                for (int i = 0; i < todaylist.size(); i++) {
                    final WatchHistory.HistoryBean historyBean = todaylist.get(i);
                    if (historyBean.isSelected()) {
                        historyBeans.add(historyBean);
                    }
                }
                for (int i = 0; i < historylist.size(); i++) {
                    final WatchHistory.HistoryBean historyBean = historylist.get(i);
                    if (historyBean.isSelected()) {
                        historyBeans.add(historyBean);
                    }
                }
                if (historyBeans != null && historyBeans.size() > 0) {
                    for (int i = 0; i < historyBeans.size(); i++) {
                        if (i == historyBeans.size() - 1) {
                            groupIds += historyBeans.get(i).getGroupId();
                        } else {
                            groupIds += historyBeans.get(i).getGroupId() + ",";
                        }
                    }
                }
                AppLog.e("======groupIds========"+groupIds);
                if (TextUtils.isEmpty(groupIds)) {
                    ToastUtil.shortShowToast("请先选择要删除的记录...");
                    return;
                }
                NetUtils.getInstance().clearHistory(groupIds, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        ToastUtil.shortShowToast(msg);
                        onRefresh();
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                }, null);
            }
        });
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete = !delete;
                setNextTv(delete ? "取消" : "删除");
                if (delete) {
                    setBackTv("全选");
                } else {
                    setBackImg();
                }
                setDeleteBtnVisible(delete ? View.VISIBLE : View.GONE);
                chatPreViewAdapter.setDelete(delete);
            }
        });
        loadDatas();
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

    private void loadDatas() {
        Tools.showDialog(WatchHistoryActivity.this);
        NetUtils.getInstance().groupHistory(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Tools.dismissWaitDialog();
                WatchHistory watchHistory = (WatchHistory) model.getModel();
                if (watchHistory != null) {
                    List<WatchHistory.HistoryBean> toDay = watchHistory.getTodaylist();
                    List<WatchHistory.HistoryBean> toMorrow = watchHistory.getHistorylist();
                    if (mRecyclerview != null && mRefeshLy != null && chatPreViewAdapter != null) {
                        mRecyclerview.loadMoreComplete();
                        mRecyclerview.refreshComplete();
                        mRefeshLy.hideAll();
                        if ((toDay == null || toDay.size() <= 0) && (toMorrow == null || toMorrow.size() <= 0)) {
                            mRefeshLy.showEmptyView();
                        } else {
                            chatPreViewAdapter.setWatchHistory(watchHistory);
                        }
                        mRecyclerview.setLoadingMoreEnabled(false);
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
                if (mRecyclerview != null && mRefeshLy != null) {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    mRefeshLy.showFailView();
                }
            }
        }, WatchHistory.class);
    }

}
