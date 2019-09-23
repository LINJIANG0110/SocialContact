package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.TopicDelAdapter;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.TopicDelBean;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import tv.buka.other.Gson;

/**
 * 话题查看全部回答列表
 */
public class TopicAllActivity extends BaseListActivity {
    TopicDelAdapter delAdapter;
    private List<TopicDelBean> delData = new ArrayList<>();
    private String topicId;//话题id
    private String topicTitle;// 标题

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                delData = adapter.data;
                String commentId = delData.get(position).commentId;
                startActivity(new Intent(TopicAllActivity.this, TopicDelActivity.class)
                        .putExtra("topicTitle", topicTitle)
                        .putExtra("topicId", topicId)
                        .putExtra("commentId", commentId));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.TOPIC_ANSWER) {
            // 刷新页面
            pageNum = 0;
            getTopicData();
        }
    }

    private void getTopicData() {
        NetUtils.getInstance().topicDetaile(topicId, pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("话题列表", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data.length() > 0) {
                        List<TopicDelBean> tempData = new Gson().fromJson(data + "", new TypeToken<List<TopicDelBean>>() {
                        }.getType());
                        if (mRecyclerview != null && mRefeshLy != null && adapter != null) {
                            mRecyclerview.loadMoreComplete();
                            mRecyclerview.refreshComplete();
                            mRefeshLy.hideAll();
                            if (pageNum == 0) {
                                adapter.clear();
                                delData.clear();
                            }
                            adapter.append(tempData);
                            delData = adapter.data;
                            if (tempData == null || tempData.size() < PAEG_SIZE) {
                                if (pageNum == 0 && (tempData == null || tempData.size() == 0)) {
                                    mRefeshLy.showEmptyView();
                                }
                                mRecyclerview.setLoadingMoreEnabled(false);
                            } else {
                                mRecyclerview.setLoadingMoreEnabled(true);
                            }
                        }
                    } else {
                        mRecyclerview.loadMoreComplete();
                        mRecyclerview.refreshComplete();
                        mRecyclerview.setLoadingMoreEnabled(false);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Log.e("话题列表fail", result);
                if (mRecyclerview != null && mRefeshLy != null) {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    mRefeshLy.showFailView();
                }
            }
        }, TopicDelBean.class);
    }

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // android:launchMode="singleTask" 回调
        topicId = getIntent().getStringExtra("topicId");
        topicTitle = getIntent().getStringExtra("topicTitle");
        onRefresh();
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("全部回答");
        topicId = getIntent().getStringExtra("topicId");
        topicTitle = getIntent().getStringExtra("topicTitle");
        onRefresh();
        View view = LayoutInflater.from(this).inflate(R.layout.activity_topic_all, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvInvitation = (TextView) view.findViewById(R.id.tv_invitation);
        LinearLayout llaySetcontent = (LinearLayout) view.findViewById(R.id.llaySetcontent);
        tvTitle.setText(topicTitle);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerview.setLayoutManager(layoutManager);
        delAdapter = new TopicDelAdapter(this, null);
        mRecyclerview.setAdapter(delAdapter);
        mRecyclerview.addHeaderView(view);
        llaySetcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TopicAllActivity.this, TopicAnswerActivity.class).putExtra("topicId", topicId).putExtra("topicTitle", topicTitle));// TopicAnswerActivity
            }
        });
        tvInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ToastUtils.getInstance().show(TopicAllActivity.this, "正在开发中，敬请期待！");
                Intent intent2 = new Intent(TopicAllActivity.this, FriendInviteActivity.class);
                intent2.putExtra("friend", false);
                intent2.putExtra("mType","topic");
                intent2.putExtra("topicId",topicId);
                intent2.putExtra("topicTitle",topicTitle);
                startActivity(intent2);
            }
        });
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new TopicDelAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        getTopicData();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        getTopicData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }
}
