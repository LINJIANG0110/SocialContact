package com.qianyu.communicate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.baidu.mapapi.map.Circle;
import com.qianyu.communicate.adapter.MyCircleAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.CircleDetail;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;

import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class MyCircleActivity extends BaseListActivity {

    private long userId;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        EventBus.getDefault().register(this);
        setTitleTv("个人动态");
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
        }
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new MyCircleAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            userId = getIntent().getLongExtra("userId", 0);
        }
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, final int position) {
                final CircleList.ListBean.ContentBean circleList = ((CircleList.ListBean.ContentBean) data.get(position));
                Intent intent = new Intent(MyCircleActivity.this, FriendDetailActivity.class);
                intent.putExtra("circleList", circleList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new MyBaseAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, List data, int position) {
                final CircleList.ListBean.ContentBean contentBean = (CircleList.ListBean.ContentBean) data.get(position);
                new AlertDialog.Builder(MyCircleActivity.this).setTitle("删除该动态?")
                        .setMessage("您是否确定删除该动态？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                User user = MyApplication.getInstance().user;
                                if (user != null) {
                                    NetUtils.getInstance().deleteCircle(contentBean.getCircleId(), user.getUserId(), new NetCallBack() {
                                        @Override
                                        public void onSuccess(String result, String msg, ResultModel model) {
                                            ToastUtil.shortShowToast(msg);
                                            adapter.removeSingle(contentBean);
                                            EventBus.getDefault().post(new EventBuss(EventBuss.FRIEND_CIRCLE));
                                        }

                                        @Override
                                        public void onFail(String code, String msg, String result) {
                                            ToastUtil.shortShowToast(msg);
                                        }
                                    }, null);
                                }
                            }
                        }).create().show();
            }
        });
    }

    private void loadDatas() {
        NetUtils.getInstance().myCircle(userId, pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                CircleList.ListBean circleList = (CircleList.ListBean) model.getModel();
                if (circleList != null) {
                    List<CircleList.ListBean.ContentBean> list = circleList.getContent();
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
        }, CircleList.ListBean.class);
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
