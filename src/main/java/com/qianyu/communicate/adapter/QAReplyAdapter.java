package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.QAList;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class QAReplyAdapter extends MyBaseAdapter<QAList.ReplyListBean, QAReplyAdapter.ViewHolder> {


    public QAReplyAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_qa_reply, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            QAList.ReplyListBean replyListBean = data.get(position);
            int type = replyListBean.getType();
            int doctorId = replyListBean.getDoctorId();
            if (type == 3) {
                String answer = (replyListBean.getDoctoeNickName()+"回复"+replyListBean.getUserNickName()) + "：" + replyListBean.getContent();
                SpannableString emotionContent_ = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mAnsweredTv, answer);
                ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.name_color));
                ForegroundColorSpan span_ = new ForegroundColorSpan(context.getResources().getColor(R.color.name_color));
                emotionContent_.setSpan(span, 0, replyListBean.getDoctoeNickName().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                emotionContent_.setSpan(span_, replyListBean.getDoctoeNickName().length()+2, replyListBean.getDoctoeNickName().length()+2+replyListBean.getUserNickName().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.mAnsweredTv.setText(emotionContent_);
            } else {
                String answer = (doctorId == 0 ? replyListBean.getUserNickName() : replyListBean.getDoctoeNickName()) + "：" + replyListBean.getContent();
                SpannableString emotionContent_ = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mAnsweredTv, answer);
                ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.name_color));
                emotionContent_.setSpan(span, 0, doctorId == 0 ? replyListBean.getUserNickName().length() : replyListBean.getDoctoeNickName().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                holder.mAnsweredTv.setText(emotionContent_);
            }
            if (position == data.size() - 1) {
                holder.mViewLine.setVisibility(View.GONE);
            }
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mAnsweredTv)
        TextView mAnsweredTv;
        @InjectView(R.id.mViewLine)
        View mViewLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
