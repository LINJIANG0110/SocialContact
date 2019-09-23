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

public class SuccessRateAdapter extends MyBaseAdapter<User, SuccessRateAdapter.ViewHolder> {

    public SuccessRateAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_success_rate, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.chargeMoney.setText("50");
                holder.levelRate.setText("2%");
                break;
            case 1:
                holder.chargeMoney.setText("100");
                holder.levelRate.setText("5%");
                break;
            case 2:
                holder.chargeMoney.setText("300");
                holder.levelRate.setText("10%");
                break;
            case 3:
                holder.chargeMoney.setText("1000");
                holder.levelRate.setText("10%");
                break;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.chargeMoney)
        TextView chargeMoney;
        @InjectView(R.id.levelRate)
        TextView levelRate;
        @InjectView(R.id.userbleTime)
        TextView userbleTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
