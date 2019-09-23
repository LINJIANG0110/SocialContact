package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
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

public class MyQAndAAdapter extends MyBaseAdapter<QAList, MyQAndAAdapter.ViewHolder> {

    private boolean live;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public MyQAndAAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_my_qa, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            QAList qaList = data.get(position);
            holder.mHeadImg.setVisibility(live ? View.VISIBLE : View.GONE);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(qaList.getUserHeadPath())?"":qaList.getUserHeadPath());
            if(TextUtils.isEmpty(qaList.getTitle())){
                holder.mContentTv.setVisibility(View.GONE);
            }else {
                holder.mContentTv.setVisibility(View.VISIBLE);
                SpannableString emotionContent = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mContentTv, qaList.getTitle());
                holder.mContentTv.setText(emotionContent);
            }
            if((qaList.getReplyList()!=null&&qaList.getReplyList().size()>0)) {
                holder.mReplyTv.setText("[已回复]");
                holder.mReplyTv.setTextColor(context.getResources().getColor(R.color.colorRed));
            }else {
                holder.mReplyTv.setText("未回复  ");
                holder.mReplyTv.setTextColor(context.getResources().getColor(R.color.text_gray));
            }
            String qa = live?(qaList.getUserNickName()+" 向我提问"):"我 向 "+qaList.getDoctoeNickName()+" 医师 提问";
            SpannableString emotionContent_ = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mQATv,qa);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.name_color));
            emotionContent_.setSpan(colorSpan, live?0:4, live?(TextUtils.isEmpty(qaList.getUserNickName())?0:qaList.getUserNickName().length()):(4+(TextUtils.isEmpty(qaList.getDoctoeNickName())?0:qaList.getDoctoeNickName().length())), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.mQATv.setText(emotionContent_);
//            SpannableString spannableString = new SpannableString(qa);
//            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.name_color));
//            spannableString.setSpan(colorSpan, live?0:4, live?qaList.getUserNickName().length():(4+qaList.getDoctoeNickName().length()), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            holder.mQATv.setText(spannableString);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mQATv)
        TextView mQATv;
        @InjectView(R.id.mReplyTv)
        TextView mReplyTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
