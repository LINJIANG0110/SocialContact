package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.QAList;
import com.qianyu.communicate.utils.NumberUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class QAListAdapter extends MyBaseAdapter<QAList, QAListAdapter.ViewHolder> {


    public QAListAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_qa_list, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            QAList qaList = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(qaList.getUserHeadPath()) ? "" : qaList.getUserHeadPath());
            holder.mUserName.setText(TextUtils.isEmpty(qaList.getUserNickName()) ? "" : qaList.getUserNickName());
            SpannableString emotionContent = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mTitleTv, qaList.getTitle());
            holder.mTitleTv.setText(emotionContent);
            holder.mTitleTv.setVisibility(TextUtils.isEmpty(qaList.getTitle()) ? View.GONE : View.VISIBLE);
            holder.mContentTv.setText(TextUtils.isEmpty(qaList.getContent()) ? "" : qaList.getContent());
            holder.mShareTv.setText(NumberUtils.roundInt(qaList.getViews()));
            holder.mCommentTv.setText(NumberUtils.roundInt(qaList.getReplys()));
            List<QAList.ReplyListBean> replyList = qaList.getReplyList();
            if (replyList != null && replyList.size() > 0) {
                holder.mAnsweredFL.setVisibility(View.VISIBLE);
                QAList.ReplyListBean replyListBean = replyList.get(0);
                String answer = replyListBean.getDoctoeNickName() + "回答：" + replyListBean.getContent();
                SpannableString emotionContent_ = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mAnsweredTv, answer);
                ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.name_color));
                emotionContent_.setSpan(span, 0, replyListBean.getDoctoeNickName().length() + 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.mAnsweredTv.setText(emotionContent_);
            } else {
                holder.mAnsweredFL.setVisibility(View.GONE);
            }
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mTitleTv)
        TextView mTitleTv;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mUserName)
        TextView mUserName;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mAnsweredFL)
        FrameLayout mAnsweredFL;
        @InjectView(R.id.mAnsweredTv)
        TextView mAnsweredTv;
        @InjectView(R.id.mCommentTv)
        TextView mCommentTv;
        @InjectView(R.id.mCommentLL)
        LinearLayout mCommentLL;
        @InjectView(R.id.mShareTv)
        TextView mShareTv;
        @InjectView(R.id.mShareLL)
        LinearLayout mShareLL;
        @InjectView(R.id.praiseLogo)
        ImageView praiseLogo;
        @InjectView(R.id.commentLogo)
        ImageView commentLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
