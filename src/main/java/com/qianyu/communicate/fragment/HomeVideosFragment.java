package com.qianyu.communicate.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.HomeVideoAdapter;
import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.jzvd.JZMediaManager;
import com.qianyu.communicate.jzvd.JZUtils;
import com.qianyu.communicate.jzvd.JZVideoPlayer;
import com.qianyu.communicate.jzvd.JZVideoPlayerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class HomeVideosFragment extends BaseListFragment {

    private TextView contenTv;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        EventBus.getDefault().register(this);
        setBackGone();
        setTitleTv("视频");
//        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_head_buy_record, null);
//        contenTv = headView.findViewById(R.id.mContentTv);
//        mRecyclerview.addHeaderView(headView);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.HOME_MSG_REFRESH) {
            mRecyclerview.scrollToPosition(0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new HomeVideoAdapter(getActivity(), null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        loadDatas();
        mRecyclerview.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayer jzvd = view.findViewById(R.id.jz_video);
                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())
                        &&JZVideoPlayerManager.getCurrentJzvd()!=null) {
                    if(JZVideoPlayerManager.getCurrentJzvd().currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN){
                        JZVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });
    }

    private void loadDatas() {
        //userId 为0表示所有人的视频
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
