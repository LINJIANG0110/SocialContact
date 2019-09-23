package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class ImpressTagAdapter extends MyBaseAdapter<String, ImpressTagAdapter.ViewHolder> {

    public ImpressTagAdapter(Activity context, List data) {
        super(context, data);
    }


    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_impress_tag, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
        if (data != null&&data.size()>0) {
            String str = data.get(position);
            holder.mTagTv.setText(str);
            if(position==data.size()-1){
                holder.mTagTv.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_dashed));
            }
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mTagTv)
        TextView mTagTv;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
