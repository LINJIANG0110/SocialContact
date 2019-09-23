package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.TopicAllActivity;
import com.qianyu.communicate.activity.TopicNewsActivity;
import com.qianyu.communicate.adapter.TopicAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseMListFragment;
import com.qianyu.communicate.base.MyMBaseAdapter;
import com.qianyu.communicate.entity.TopicBean;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by JavaDog on 2019-9-5.
 */

/**
 * 新增-话题模块入口页
 */
public class FriendsTopicFragment extends BaseMListFragment {

    private LinearLayout headView;
    private ImageView mHeadLogo;
    private TextView mMsgNum;

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_circle_head, null);
        headView = (LinearLayout) view.findViewById(R.id.mHeadLL);
        mHeadLogo = (ImageView) view.findViewById(R.id.mHeadLogo);
        mMsgNum = (TextView) view.findViewById(R.id.mMsgNum);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TopicNewsActivity.class));
            }
        });
        mRecyclerview.addHeaderView(view);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.TOPIC_ANSWER || event.getState() == EventBuss.TOPIC_ZAN || event.getState() == EventBuss.TOPIC_PL) {
            // 刷新-添加回答
            onRefresh();
        } else if (event.getState() == EventBuss.FRIEND_CIRCLE) {
            onRefresh();
        } else if (event.getState() == EventBuss.CIRCLE_MSG) {
            onRefresh();
        } else if (event.getState() == EventBuss.CIRCLE_READ) {
            onRefresh();
        }
    }

    @Override
    protected MyMBaseAdapter getAdapter() {
        return new TopicAdapter(getActivity(), null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        setmFloatActionBtn(R.mipmap.dongtai_dingduan);
        setFloatActionBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerview.scrollToPosition(0);
            }
        });
        adapter.setOnItemClickListener(new MyMBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                Intent intent = new Intent(getActivity(), TopicAllActivity.class);
                TopicBean.TopicDataBean topicBean = ((TopicBean.TopicDataBean) data.get(position));
                // 传入话题及话题id
                intent.putExtra("topicId", topicBean.getTopicId());
                intent.putExtra("topicTitle", topicBean.getTitle());
                intent.putExtra("commentId", "");// 传入空默认获取最热话题讨论详情数据
                startActivity(intent);
            }
        });
        loadDatas();
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
            NetUtils.getInstance().topicList(user.getUserId(), pageNum, PAEG_SIZE, new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    TopicBean topicBean = (TopicBean) model.getModel();
                    if (topicBean != null) {
                        TopicBean.TopicUnreadBean unRead = topicBean.unreadMap;
                        if (unRead != null && unRead.count > 0) {
                            headView.setVisibility(View.VISIBLE);
                            ImageUtil.loadHeadImgNet(unRead.headUrl, mHeadLogo);
                            mMsgNum.setText(unRead.count + "条新消息");
                        } else {
                            headView.setVisibility(View.GONE);
                        }
                        if (topicBean.topicList != null) {
                            List<TopicBean.TopicDataBean> list = topicBean.topicList;
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
            }, TopicBean.class);
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
