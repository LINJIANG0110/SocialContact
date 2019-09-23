package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.entity.CircleList;
import com.facebook.drawee.view.SimpleDraweeView;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.PraiseList;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class PraiseHeadAdapter extends MyBaseAdapter<PraiseList.ContentBean, PraiseHeadAdapter.ViewHolder> {


    public PraiseHeadAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_praise_detail, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.width = (PixelUtil.getScreenWidth(context) - PixelUtil.dp2px(context, 70))/8;
//        params.height = params.width;
//        params.topMargin=params.leftMargin= PixelUtil.dp2px(context, 5);
//        holder.mHeadImg.setLayoutParams(params);
        if (data != null&&data.size()>0) {
            final PraiseList.ContentBean praiseList = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(praiseList.getHeadUrl())?"":praiseList.getHeadUrl());
            holder.mHeadImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonalPageActivity.class);
                    intent.putExtra("userId", praiseList.getUserId());
                    context.startActivity(intent);
                }
            });
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
