package com.qianyu.communicate.fragment;

import com.qianyu.communicate.adapter.EventRecordAdapter;
import com.qianyu.communicate.base.BaseListFragment_;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JavaDog on 2019-3-22.
 */

public class EventRecordFragment extends BaseListFragment_ {

    private int event;
    private long groupId;

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {
        EventBus.getDefault().register(this);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.EVENT_RECORD) {
            if(EventRecordFragment.this.event==2) {
                EventRecord.ContentBean content = ((EventRecord.ContentBean) event.getMessage());
                adapter.appendSingle(content);
                mRecyclerview.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        }
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new EventRecordAdapter(getActivity(), null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            event = getArguments().getInt("event");
            groupId = getArguments().getLong("groupId");
            loadDatas();
        }
    }

    private void loadDatas() {
        if (event == 1) {
            NetUtils.getInstance().groupEvent(groupId, -1, -1, new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    EventRecord circleList = (EventRecord) model.getModel();
                    if (circleList != null) {
                        List<EventRecord.ContentBean> list = circleList.getContent();
                        if (list != null && list.size() > 0) {
                            Collections.reverse(list);
                            adapter.appendAll(list);
                            mRecyclerview.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, EventRecord.class);
        } else {
            NetUtils.getInstance().worldEvent(-1, -1, new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    EventRecord circleList = (EventRecord) model.getModel();
                    if (circleList != null) {
                        List<EventRecord.ContentBean> list = circleList.getContent();
                        Collections.reverse(list);
                        adapter.appendAll(list);
                        if (list != null && list.size() > 0) {
                            mRecyclerview.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, EventRecord.class);
        }
    }

    @Override
    public void onRetryClick() {
        loadDatas();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
