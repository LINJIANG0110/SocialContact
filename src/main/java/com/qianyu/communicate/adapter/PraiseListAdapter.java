package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.GiftList;
import com.qianyu.communicate.utils.SpringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wl_user on 2017/8/24.
 */

public class PraiseListAdapter extends MyBaseAdapter<GiftList.SouvenirNosBean, PraiseListAdapter.ViewHolder> {


    public PraiseListAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_praise_list, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
//        holder.mMoneyTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//        holder.mMoneyTv.getPaint().setAntiAlias(true);//抗锯齿
        switch (position) {
            case 0:
                holder.mMoneyTv.setText("2元");
                break;
            case 1:
                holder.mMoneyTv.setText("6元");
                break;
            case 2:
                holder.mMoneyTv.setText("9元");
                break;
            case 3:
                holder.mMoneyTv.setText("66元");
                break;
            case 4:
                holder.mMoneyTv.setText("88元");
                break;
            case 5:
                holder.mMoneyTv.setText("520元");
                break;
        }
        holder.mMoneyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpringUtils.springAnim(holder.mMoneyTv);
                mOnItemClickListener.onItemClick(holder.mMoneyTv,null,position);
            }
        });
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
