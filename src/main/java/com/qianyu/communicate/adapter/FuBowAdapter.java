package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.InComeList;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FuBowAdapter extends MyBaseAdapter<InComeList.ContentBean, FuBowAdapter.ViewHolder> {

    private int type;
    public FuBowAdapter(Activity context, List data,int type) {
        super(context, data);
        this.type=type;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_fubow, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
        if (data != null&&data.size()>0) {
            InComeList.ContentBean accountBean = data.get(position);
            holder.titleName.setText(TextUtils.isEmpty(accountBean.getContent())?"":accountBean.getContent());
            holder.createTime.setText(TimeUtils.getTime(accountBean.getCreateTime()));
            holder.costNum.setText((1==type?"+":"-")+accountBean.getConsume());
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.titleName)
        TextView titleName;
        @InjectView(R.id.createTime)
        TextView createTime;
        @InjectView(R.id.costNum)
        TextView costNum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
