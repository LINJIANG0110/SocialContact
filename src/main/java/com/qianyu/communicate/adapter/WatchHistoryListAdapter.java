package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.WatchHistory;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class WatchHistoryListAdapter extends MyBaseAdapter<WatchHistory.HistoryBean, WatchHistoryListAdapter.ViewHolder> {

    private boolean delete = false;

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public WatchHistoryListAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_watch_history_list, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final WatchHistory.HistoryBean familyListBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(familyListBean.getHeadUrl()) ? "" : familyListBean.getHeadUrl());
            holder.mNameTv.setText(TextUtils.isEmpty(familyListBean.getGroupName()) ? "" : familyListBean.getGroupName());
            holder.mContentTv.setText(TextUtils.isEmpty(familyListBean.getDetails()) ? "" : familyListBean.getDetails());
            holder.mTimeTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(familyListBean.getUpdateTime()));
            holder.mChooseLogo.setVisibility(delete ? View.VISIBLE : View.GONE);
            holder.mChooseLogo.setImageResource(familyListBean.isSelected() ? R.mipmap.wxdl_xuanze : R.mipmap.wxdl_xuanzehui);
            holder.mRootLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (delete) {
                        familyListBean.setSelected(!familyListBean.isSelected());
                        holder.mChooseLogo.setImageResource(familyListBean.isSelected() ? R.mipmap.wxdl_xuanze : R.mipmap.wxdl_xuanzehui);
                    } else {
                        Tools.enterFamily(context,familyListBean.getGroupId(), false);
                    }
                }
            });
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mNameTv)
        TextView mNameTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mChooseLogo)
        ImageView mChooseLogo;
        @InjectView(R.id.mRootLL)
        LinearLayout mRootLL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
