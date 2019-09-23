package com.qianyu.communicate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.TopicNewsAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.CircleDetail;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.entity.TopicNewsBean;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 话题新消息提醒
 */
public class TopicNewsActivity extends BaseListActivity {
    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setNextTv("清空");
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new TopicNewsAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
            NetUtils.getInstance().unReadCircleTopic(user.getUserId(), pageNum, PAEG_SIZE, new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    List<TopicNewsBean> list = (List<TopicNewsBean>) model.getModelList();
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
                    EventBus.getDefault().post(new EventBuss(EventBuss.CIRCLE_READ));
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
            }, TopicNewsBean.class);
        }
    }


    @Override
    public void onRetryClick() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void initData() {
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, final int position) {
                final List<TopicNewsBean> commentList = adapter.data;
                startActivity(new Intent(TopicNewsActivity.this, TopicDelActivity.class)
                        .putExtra("topicTitle", commentList.get(position).topicTitle)
                        .putExtra("topicId", commentList.get(position).topicId)
                        .putExtra("commentId", commentList.get(position).commentId));
            }
        });
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
//    activity_topic_news
}
