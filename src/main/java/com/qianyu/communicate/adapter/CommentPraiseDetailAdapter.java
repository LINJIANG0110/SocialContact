package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class CommentPraiseDetailAdapter extends MyBaseAdapter<CircleList.ListBean.ContentBean, CommentPraiseDetailAdapter.ViewHolder> {

    public CommentPraiseDetailAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_detail_comment_praise, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final CircleList.ListBean.ContentBean commentList = data.get(position);
            long createTime = commentList.getCreateTime();
            if (createTime == 0) {
                holder.mTimeTv.setVisibility(View.GONE);
            } else {
                holder.mTimeTv.setVisibility(View.VISIBLE);
                holder.mTimeTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(createTime));
            }
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(commentList.getHeadUrl()) ? "" : commentList.getHeadUrl());
            String picPath = commentList.getFileItemUrl();
            if (!TextUtils.isEmpty(picPath)) {
                holder.mCircleTv.setVisibility(View.GONE);
                holder.mCircleImg.setVisibility(View.VISIBLE);
                String[] split = picPath.split(",");
                holder.mCircleImg.setImageURI(TextUtils.isEmpty(split[0]) ? "" : split[0]);
            }else {
                holder.mCircleTv.setVisibility(View.VISIBLE);
                holder.mCircleImg.setVisibility(View.GONE);
                holder.mCircleTv.setText(TextUtils.isEmpty(commentList.getTitle())?"":commentList.getTitle());
            }
            holder.mUserName.setText(commentList.getNickName());
            switch (commentList.getType()) {
                case "1":
                    holder.mContentTv.setVisibility(View.GONE);
                    holder.mPraiseLogo.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    holder.mContentTv.setVisibility(View.VISIBLE);
                    holder.mPraiseLogo.setVisibility(View.GONE);
                    String content = TextUtils.isEmpty(commentList.getText()) ? "" : commentList.getText();
                    if (content.contains("回复")) {
                        SpannableString emotionContent = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mContentTv, content);
                        ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.name_color));
                        int end = content.indexOf("：");
                        int end_ = content.indexOf(":");
                        emotionContent.setSpan(span, 2, end > 0 ? end : end_, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        holder.mContentTv.setText(emotionContent);
                    } else {
                        holder.mContentTv.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mContentTv, content));
                    }
                    break;
            }
            holder.mHeadImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonalPageActivity.class);
                    intent.putExtra("userId", commentList.getUserId());
                    context.startActivity(intent);
                }
            });
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mCircleImg)
        SimpleDraweeView mCircleImg;
        @InjectView(R.id.mUserName)
        TextView mUserName;
        @InjectView(R.id.mCircleTv)
        TextView mCircleTv;
        @InjectView(R.id.mPraiseLogo)
        ImageView mPraiseLogo;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
