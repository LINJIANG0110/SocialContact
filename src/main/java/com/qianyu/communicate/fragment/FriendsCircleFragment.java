package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.CommentPraiseDetailActivity;
import com.qianyu.communicate.activity.FriendDetailActivity;
import com.qianyu.communicate.adapter.FriendCircleAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import com.qianyu.communicate.base.BaseMListFragment;
import com.qianyu.communicate.base.MyMBaseAdapter;
import com.qianyu.communicate.utils.SpUtil;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class FriendsCircleFragment extends BaseMListFragment {

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
                startActivity(new Intent(getActivity(), CommentPraiseDetailActivity.class));
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
        if (event.getState() == EventBuss.ADD_PRAISE) {
            int position = (int) event.getMessage();
            if (adapter.data != null && adapter.data.size() > 0) {
                CircleList.ListBean.ContentBean circleList = (CircleList.ListBean.ContentBean) adapter.data.get(position);
                circleList.setFabulous(circleList.getFabulous() + 1);
                circleList.setIsClick(1);
                adapter.notifyDataSetChanged();
            }
        } else if (event.getState() == EventBuss.CANCEL_PRAISE) {
            int position = (int) event.getMessage();
            if (adapter.data != null && adapter.data.size() > 0) {
                CircleList.ListBean.ContentBean circleList = (CircleList.ListBean.ContentBean) adapter.data.get(position);
                circleList.setFabulous(circleList.getFabulous() - 1);
                circleList.setIsClick(0);
                adapter.notifyDataSetChanged();
            }
        } else if (event.getState() == EventBuss.COMMENT) {
            int position = (int) event.getMessage();
            if (adapter.data != null && adapter.data.size() > 0) {
                CircleList.ListBean.ContentBean circleList = (CircleList.ListBean.ContentBean) adapter.data.get(position);
                circleList.setComment(circleList.getComment() + 1);
                adapter.notifyDataSetChanged();
            }
        }else if (event.getState() == EventBuss.COMMENT_DELETE) {
            int position = (int) event.getMessage();
            if (adapter.data != null && adapter.data.size() > 0) {
                CircleList.ListBean.ContentBean circleList = (CircleList.ListBean.ContentBean) adapter.data.get(position);
                circleList.setComment(circleList.getComment() - 1);
                adapter.notifyDataSetChanged();
            }
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
        return new FriendCircleAdapter(getActivity(), null);
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
                Intent intent = new Intent(getActivity(), FriendDetailActivity.class);
                CircleList.ListBean.ContentBean circleList = ((CircleList.ListBean.ContentBean) data.get(position));
                intent.putExtra("circleList", circleList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        loadDatas();
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
            NetUtils.getInstance().circleList(user.getUserId(), pageNum, PAEG_SIZE, new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    CircleList circleList = (CircleList) model.getModel();
                    if (circleList != null) {
                        CircleList.UnReadBean unRead = circleList.getUnRead();
                        if (unRead != null && unRead.getCount() > 0) {
                            headView.setVisibility(View.VISIBLE);
                            ImageUtil.loadHeadImgNet(unRead.getHeadUrl(), mHeadLogo);
                            mMsgNum.setText(unRead.getCount() + "条新消息");
                        } else {
                            headView.setVisibility(View.GONE);
                        }
                        if (circleList.getList() != null) {
                            List<CircleList.ListBean.ContentBean> list = circleList.getList().getContent();
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
            }, CircleList.class);
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
