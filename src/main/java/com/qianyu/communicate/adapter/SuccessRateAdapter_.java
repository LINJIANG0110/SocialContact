package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.User;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class SuccessRateAdapter_ extends MyBaseAdapter<User, SuccessRateAdapter_.ViewHolder> {

    public SuccessRateAdapter_(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_success_rate_, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.rankName.setText("1");
                holder.rickRank.setText("10%");
                holder.charmRank.setText("10%");
                holder.charmRankMore.setText("10%");
                break;
            case 1:
                holder.rankName.setText("2");
                holder.rickRank.setText("8%");
                holder.charmRank.setText("8%");
                holder.charmRankMore.setText("8%");
                break;
            case 2:
                holder.rankName.setText("3");
                holder.rickRank.setText("6%");
                holder.charmRank.setText("6%");
                holder.charmRankMore.setText("6%");
                break;
            case 3:
                holder.rankName.setText("4-10");
                holder.rickRank.setText("4%");
                holder.charmRank.setText("4%");
                holder.charmRankMore.setText("4%");
                break;
            case 4:
                holder.rankName.setText("11-50");
                holder.rickRank.setText("3%");
                holder.charmRank.setText("3%");
                holder.charmRankMore.setText("3%");
                break;
            case 5:
                holder.rankName.setText("51-100");
                holder.rickRank.setText("2%");
                holder.charmRank.setText("2%");
                holder.charmRankMore.setText("2%");
                break;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.rankName)
        TextView rankName;
        @InjectView(R.id.rickRank)
        TextView rickRank;
        @InjectView(R.id.charmRank)
        TextView charmRank;
        @InjectView(R.id.charmRankMore)
        TextView charmRankMore;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
