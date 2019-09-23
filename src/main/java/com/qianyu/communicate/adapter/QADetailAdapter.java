package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.QAList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpringUtils;

import net.neiquan.applibrary.wight.AlertDialog;

import org.haitao.common.utils.TimeUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class QADetailAdapter extends MyBaseAdapter<QAList.ReplyListBean, QADetailAdapter.ViewHolder> {


    public QADetailAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_qa_detail, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        holder.mReplyRecylerView.setLayoutManager(layoutManager);
        holder.mReplyRecylerView.setNestedScrollingEnabled(false);
        final QAReplyAdapter adapter = new QAReplyAdapter(context, null);
        if (data != null && data.size() > 0) {
            final QAList.ReplyListBean replyListBean = data.get(position);
            if (replyListBean.getReplyList() != null && replyListBean.getReplyList().size() > 0) {
                holder.mReplyRecylerView.setVisibility(View.VISIBLE);
//                QAReplyAdapter adapter = new QAReplyAdapter(context, replyListBean.getReplyList());
                holder.mReplyRecylerView.setAdapter(adapter);
                adapter.appendAll(replyListBean.getReplyList());
            } else {
                holder.mReplyRecylerView.setVisibility(View.GONE);
            }
            final int doctorId = replyListBean.getDoctorId();
            if (doctorId == 0) {
                holder.mLevelTv.setVisibility(View.GONE);
                holder.mHeadImg.setImageURI(TextUtils.isEmpty(replyListBean.getUserHeadPath()) ? "" : replyListBean.getUserHeadPath());
                holder.mUserName.setText(TextUtils.isEmpty(replyListBean.getUserNickName()) ? "" : replyListBean.getUserNickName());
            } else {
                holder.mLevelTv.setVisibility(View.VISIBLE);
                holder.mHeadImg.setImageURI(TextUtils.isEmpty(replyListBean.getDoctorHeadPath()) ? "" : replyListBean.getDoctorHeadPath());
                holder.mUserName.setText(TextUtils.isEmpty(replyListBean.getDoctoeNickName()) ? "" : replyListBean.getDoctoeNickName());
            }
            SpannableString emotionContent = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.mContentTv, replyListBean.getContent());
            holder.mContentTv.setText(emotionContent);
            holder.mTimeTv.setText(TimeUtil.getZoneTime(replyListBean.getCreateTime()));
            holder.mShareTv.setText("" + NumberUtils.roundInt(replyListBean.getPraise()));
            final User user = MyApplication.getInstance().user;
            if (user != null) {
                holder.mDeleteTv.setVisibility(user.getUserId() == (doctorId==0?replyListBean.getUserId():replyListBean.getDoctorId()) ? View.VISIBLE : View.GONE);
            }
            holder.mReplyTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onReplyListener != null) {
                        onReplyListener.onReply(2,replyListBean,position);
                    }
                }
            });
            adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, List data, int position) {
                    if (onReplyListener != null) {
                        onReplyListener.onReply(3,replyListBean,position);
                    }
                }
            });
            holder.mCommentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpringUtils.springAnim(holder.praiseLogo);
                    replyListBean.setPraise(replyListBean.getPraise() + 1);
                    holder.mShareTv.setText("" + replyListBean.getPraise());
                }
            });
            holder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setTitle("删除该评论?")
                            .setMessage("您是否确定删除该评论？")
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
            });
        }
    }

    private OnReplyListener onReplyListener;

    public void setOnReplyListener(OnReplyListener onReplyListener) {
        this.onReplyListener = onReplyListener;
    }

    public interface OnReplyListener {
        void onReply(int type, QAList.ReplyListBean replyListBean, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mReplyRecylerView)
        RecyclerView mReplyRecylerView;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mUserName)
        TextView mUserName;
        @InjectView(R.id.mLevelTv)
        TextView mLevelTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.praiseLogo)
        ImageView praiseLogo;
        @InjectView(R.id.mShareTv)
        TextView mShareTv;
        @InjectView(R.id.mReplyTv)
        TextView mReplyTv;
        @InjectView(R.id.mDeleteTv)
        TextView mDeleteTv;
        @InjectView(R.id.mCommentLL)
        LinearLayout mCommentLL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
