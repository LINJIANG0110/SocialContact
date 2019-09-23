package com.qianyu.communicate.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.MyProductionAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.jzvd.JZVideoPlayer;

import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class MyProductionsActivity extends BaseListActivity{

    private TextView totalVideos;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        EventBus.getDefault().register(this);
        setTitleTv("我的作品");
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_head_my_production, null);
        totalVideos = headView.findViewById(R.id.totalVideos);
        mRecyclerview.addHeaderView(headView);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new MyProductionAdapter(this,null);
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        loadDatas();

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
//                    if(JZVideoPlayerManager.getCurrentJzvd().currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN){
//                        JZVideoPlayer.releaseAllVideos();
//                    }
//                }
//            }
//        });
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            return;
        }
    }


    @Override
    public void onRetryClick() {
        pageNum=0;
        loadDatas();
    }

    @Override
    public void onRefresh() {
        pageNum=0;
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

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
