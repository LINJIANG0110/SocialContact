package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.base.MyMBaseAdapter;
import com.qianyu.communicate.entity.TopicBean;
import com.qianyu.communicate.entity.TopicDelBean;
import com.qianyu.communicate.utils.HtmlUtil;
import com.qianyu.communicate.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JavaDog on 2019-9-5.
 */

public class TopicDelAdapter extends MyBaseAdapter<TopicDelBean, TopicDelAdapter.ViewHolder> {

    public TopicDelAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected TopicDelAdapter.ViewHolder getViewHolder() {
        return new TopicDelAdapter.ViewHolder(mInflater.inflate(R.layout.item_topic_detaile, null));
    }

    @Override
    protected void onBindViewHolder_(final TopicDelAdapter.ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final TopicDelBean topicBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(topicBean.headUrl) ? "" : topicBean.headUrl);
            holder.tvTitle.setText(HtmlUtil.delHTMLTag(topicBean.content));
            holder.tvName.setText(topicBean.nickName);
            holder.tvDatetime.setText(TimeUtils.getTime(Long.valueOf(topicBean.createTime)));
            holder.tvCommentNum.setText(topicBean.commentNum + " 条评论");
            holder.tvZanNum.setText(topicBean.fabulousNum + " 赞同");
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
//        @InjectView(R.id.webContent)
//        WebView webContent;
        @InjectView(R.id.tvTitle)
        TextView tvTitle;
        @InjectView(R.id.tvDatetime)
        TextView tvDatetime;
        @InjectView(R.id.tvName)
        TextView tvName;
        @InjectView(R.id.tv_evalue)
        TextView tvCommentNum;
        @InjectView(R.id.tv_zan)
        TextView tvZanNum;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
