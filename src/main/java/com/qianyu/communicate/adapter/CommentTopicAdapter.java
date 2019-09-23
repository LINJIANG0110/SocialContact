package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.CommentList;
import com.qianyu.communicate.entity.TopicEvalueBean;
import com.qianyu.communicate.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JavaDog on 2019-9-9.
 */

public class CommentTopicAdapter extends MyBaseAdapter<TopicEvalueBean, CommentTopicAdapter.ViewHolder> {


    public CommentTopicAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected CommentTopicAdapter.ViewHolder getViewHolder() {
        return new CommentTopicAdapter.ViewHolder(mInflater.inflate(R.layout.item_topic_comment, null));
    }

    @Override
    protected void onBindViewHolder_(final CommentTopicAdapter.ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final TopicEvalueBean commentList = data.get(position);
            long createTime = commentList.getCreateTime();
            if (createTime == 0) {
                holder.mTimeTv.setVisibility(View.GONE);
            } else {
                holder.mTimeTv.setVisibility(View.VISIBLE);
                holder.mTimeTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(createTime));
            }
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(commentList.getHeadUrl()) ? "" : commentList.getHeadUrl());
            holder.mUserName.setText(commentList.getNickName());
            String content = TextUtils.isEmpty(commentList.getContent()) ? "" : commentList.getContent();
            if (content.contains("回复")) {
                SpannableString emotionContent = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mContentTv, content);
                try {
                    ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.name_color));
                    int end = content.indexOf("：");
                    int end_ = content.indexOf(":");
                    emotionContent.setSpan(span, 2, end > 0 ? end : end_, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                } catch (Exception e) {

                }
                holder.mContentTv.setText(emotionContent);
            } else {
                holder.mContentTv.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mContentTv, content));
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
        @InjectView(R.id.mUserName)
        TextView mUserName;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mContentLL)
        LinearLayout mContentLL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}