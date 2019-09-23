package com.qianyu.communicate.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.PersonalVideoAdapter;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.jzvd.JZVideoPlayer;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import com.qianyu.communicate.base.BaseFragment;

import net.neiquan.applibrary.wight.RefreshLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class PersonalVideoFragment extends BaseFragment implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {

    @InjectView(R.id.mRecylerView)
    XRecyclerView mRecyclerview;
    @InjectView(R.id.refesh_ly)
    RefreshLayout mRefeshLy;
    private PersonalVideoAdapter adapter;
    protected int pageNum = 0;
    protected int PAEG_SIZE = 10;
    private int userId;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_personal_video;
    }

    @Override
    public void setViews() {
        initRecylerView();
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", 0);
        }
        loadDatas();
    }

    private void loadDatas() {
        //userId 某个人的id
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

        adapter = new PersonalVideoAdapter(getActivity(), null);
        mRecyclerview.setAdapter(adapter);
//        mRecylerView.setNestedScrollingEnabled(false);

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

//        mRecyclerview.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//            @Override
//            public void onChildViewAttachedToWindow(View view) {
//
//            }
//
//            @Override
//            public void onChildViewDetachedFromWindow(View view) {
//                JZVideoPlayer jzvd = view.findViewById(R.id.jz_video);
//                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
//                    if (JZVideoPlayerManager.getCurrentJzvd().currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
//                        JZVideoPlayer.releaseAllVideos();
//                    }
//                }
//            }
//        });
    }

    @Override
    public void onRetryClick() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void onRefresh() {

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
    }

//    @Override
//    public void onBackPressed() {
//        if (JZVideoPlayer.backPress()) {
//            return;
//        }
//        super.onBackPressed();
//    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
