package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.FriendDetailActivity;
import com.qianyu.communicate.adapter.FriendCircleAdapter;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import com.qianyu.communicate.base.BaseFragment;
import com.qianyu.communicate.base.MyMBaseAdapter;
import net.neiquan.applibrary.wight.RefreshLayout;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class PersonalCircleFragment extends BaseFragment implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {

    @InjectView(R.id.mRecylerView)
    XRecyclerView mRecyclerview;
    @InjectView(R.id.refesh_ly)
    RefreshLayout mRefeshLy;
    private FriendCircleAdapter adapter;
    protected int pageNum = 0;
    protected  int PAEG_SIZE = 10;
    private int userId;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_personal_circle;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        initRecylerView();
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
                CircleList.ListBean.ContentBean circleList = adapter.data.get(position);
                circleList.setFabulous(circleList.getFabulous() + 1);
                circleList.setIsClick(1);
                adapter.notifyDataSetChanged();
            }
        } else if (event.getState() == EventBuss.CANCEL_PRAISE) {
            int position = (int) event.getMessage();
            if (adapter.data != null && adapter.data.size() > 0) {
                CircleList.ListBean.ContentBean circleList = adapter.data.get(position);
                circleList.setFabulous(circleList.getFabulous() - 1);
                circleList.setIsClick(0);
                adapter.notifyDataSetChanged();
            }
        } else if (event.getState() == EventBuss.COMMENT) {
            int position = (int) event.getMessage();
            if (adapter.data != null && adapter.data.size() > 0) {
                CircleList.ListBean.ContentBean circleList = adapter.data.get(position);
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
    public void initData() {
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", 0);
        }
        loadDatas();
    }

    private void loadDatas() {
//        NetUtils.getInstance().circleList(userId, pageNum, PAEG_SIZE, new NetCallBack() {
//            @Override
//            public void onSuccess(String result, String msg, ResultModel model) {
//                CircleList circleList = (CircleList) model.getModel();
//                if (circleList != null) {
//                    List<CircleList.ContentBean> list = circleList.getContent();
//                    if (mRecyclerview != null && mRefeshLy != null && adapter != null) {
//                        mRecyclerview.loadMoreComplete();
//                        mRecyclerview.refreshComplete();
//                        mRefeshLy.hideAll();
//                        if (pageNum == 0) {
//                            adapter.clear();
//                        }
//                        adapter.append(list);
//                        if (list == null || list.size() < PAEG_SIZE) {
//                            if (pageNum == 0 && (list == null || list.size() == 0)) {
//                                mRefeshLy.showEmptyView();
//                            }
//                            mRecyclerview.setLoadingMoreEnabled(false);
//                        } else {
//                            mRecyclerview.setLoadingMoreEnabled(true);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFail(String code, String msg, String result) {
//                if (mRecyclerview != null && mRefeshLy != null) {
//                    mRecyclerview.loadMoreComplete();
//                    mRecyclerview.refreshComplete();
//                    mRefeshLy.hideAll();
//                    mRefeshLy.showFailView();
//                }
//            }
//        }, CircleList.class);
    }


    private void initRecylerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(layoutManager);

        mRecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(layoutManager);
        mRefeshLy.setRetryListener(this);

        mRecyclerview.setLoadingListener(this);
        mRecyclerview.setLoadingMoreEnabled(false);
        mRecyclerview.setPullRefreshEnabled(false);

        adapter = new FriendCircleAdapter(getActivity(), null);
        mRecyclerview.setAdapter(adapter);
//        mRecylerView.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(new MyMBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                Intent intent = new Intent(getActivity(), FriendDetailActivity.class);
                CircleList circleList = ((CircleList) data.get(position));
                intent.putExtra("circleList", circleList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        // attach top listener
        mRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }
}
