package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.WatchHistory;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class WatchHistoryAdapter extends MyBaseAdapter<User, WatchHistoryAdapter.ViewHolder> {

    private WatchHistory watchHistory;

    public WatchHistory getWatchHistory() {
        return watchHistory;
    }

    public void setWatchHistory(WatchHistory watchHistory) {
        this.watchHistory = watchHistory;
        notifyDataSetChanged();
    }

    public WatchHistoryAdapter(Activity context, List data) {
        super(context, data);
    }


    private boolean delete = false;

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_watch_history, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, final int position) {
        if (watchHistory != null) {
            List<WatchHistory.HistoryBean> listBeans = null;
            switch (position) {
                case 0:
                    listBeans = watchHistory.getTodaylist();
                    holder.mDayTv.setText("今天");
                    break;
                case 1:
                    listBeans = watchHistory.getHistorylist();
                    holder.mDayTv.setText("更早");
                    break;
            }
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                    StaggeredGridLayoutManager.VERTICAL);
            layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
            holder.mRecyclerView.setHasFixedSize(true);
            holder.mRecyclerView.setLayoutManager(layoutManager);
            WatchHistoryListAdapter adapter = new WatchHistoryListAdapter(context, listBeans);
            adapter.setDelete(delete);
            holder.mRecyclerView.setAdapter(adapter);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mDayTv)
        TextView mDayTv;
        @InjectView(R.id.mRecyclerView)
        RecyclerView mRecyclerView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
