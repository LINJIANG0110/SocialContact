package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.QAList;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class ArticleListAdapter extends MyBaseAdapter<QAList, ArticleListAdapter.ViewHolder> {

    public ArticleListAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_article_list, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        holder.mHeadImg.setImageURI("http://word.file.buka.tv/8672540d8e036615a650a077f2388a16.png");
//        if (data != null && data.size() > 0) {
//            QAList qaList = data.get(position);
//            holder.mHeadImg.setImageURI(TextUtils.isEmpty(qaList.getUserHeadPath()) ? "" : qaList.getUserHeadPath());
//            SpannableString emotionContent = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mTitleTv, qaList.getTitle());
//            holder.mTitleTv.setText(emotionContent);
//            holder.mTitleTv.setVisibility(TextUtils.isEmpty(qaList.getTitle()) ? View.GONE : View.VISIBLE);
//            holder.mContentTv.setText(TextUtils.isEmpty(qaList.getText()) ? "" : qaList.getText());
//            holder.mCommentTv.setText(NumberUtils.roundInt(qaList.getReplys()));
//        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mTitleTv)
        TextView mTitleTv;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mCommentPraiseTv)
        TextView mCommentPraiseTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
