package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.FamilyRoomActivity;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.CircleList;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.entity.CommentList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.TimeUtils;

import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class CommentAdapter extends MyBaseAdapter<CommentList.ContentBean, CommentAdapter.ViewHolder> {

    private int circleId;

    public CommentAdapter(Activity context, List data, int circleId) {
        super(context, data);
        this.circleId = circleId;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_detail_comment, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.width = (PixelUtil.getScreenWidth(context) - PixelUtil.dp2px(context, 80)) / 8;
//        params.height = params.width;
//        holder.mHeadImg.setLayoutParams(params);
        if (data != null && data.size() > 0) {
            final CommentList.ContentBean commentList = data.get(position);
            long createTime = commentList.getCreateTime();
            if (createTime == 0) {
                holder.mTimeTv.setVisibility(View.GONE);
            } else {
                holder.mTimeTv.setVisibility(View.VISIBLE);
                holder.mTimeTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(createTime));
            }
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(commentList.getHeadUrl()) ? "" : commentList.getHeadUrl());
            holder.mUserName.setText(commentList.getNickName());
            String content = TextUtils.isEmpty(commentList.getText()) ? "" : commentList.getText();
            if (content.contains("回复")) {
                SpannableString emotionContent = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mContentTv, content);
                try {
                    ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.name_color));
                    int end = content.indexOf("：");
                    int end_ = content.indexOf(":");
                    emotionContent.setSpan(span, 2, end > 0 ? end : end_, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }catch (Exception e){

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
