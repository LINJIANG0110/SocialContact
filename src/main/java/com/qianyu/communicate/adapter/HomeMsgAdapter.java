package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.activity.PersonalPageActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class HomeMsgAdapter extends MyBaseAdapter<User, HomeMsgAdapter.ViewHolder> {

    public HomeMsgAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected HomeMsgAdapter.ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_home_msg_, null));
    }

    @Override
    protected void onBindViewHolder_(HomeMsgAdapter.ViewHolder holder, int position) {
        holder.mHeadImg.setImageURI("http://a.hiphotos.baidu.com/baike/pic/item/32fa828ba61ea8d3fd15e9f89d0a304e251f5812.jpg");
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.width = context.getResources().getDisplayMetrics().widthPixels;
//        holder.contentLL.setLayoutParams(params);
        holder.contentLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PersonalPageActivity.class));
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mDotRed)
        TextView mDotRed;
        @InjectView(R.id.mNameTv)
        TextView mNameTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mRemindLogo)
        ImageView mRemindLogo;
        @InjectView(R.id.contentLL)
        LinearLayout contentLL;
        @InjectView(R.id.mCancelTv)
        TextView mCancelTv;
        @InjectView(R.id.mDeleteTv)
        TextView mDeleteTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
