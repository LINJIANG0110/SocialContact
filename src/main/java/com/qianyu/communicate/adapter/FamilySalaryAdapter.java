package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.WageInfo;
import com.qianyu.communicate.utils.NumberUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FamilySalaryAdapter extends MyBaseAdapter<WageInfo.UserScoreInfoBean, FamilySalaryAdapter.ViewHolder> {

    public FamilySalaryAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_family_salary, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
        if (data != null&&data.size()>0) {
            WageInfo.UserScoreInfoBean wageInfo = data.get(position);
            holder.chargeMoney.setText(TextUtils.isEmpty(wageInfo.getNickName())?"":wageInfo.getNickName());
            holder.levelRate.setText(NumberUtils.rounLong(wageInfo.getDay()));
            holder.userbleTime.setText(NumberUtils.rounLong(wageInfo.getWeek()));
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
