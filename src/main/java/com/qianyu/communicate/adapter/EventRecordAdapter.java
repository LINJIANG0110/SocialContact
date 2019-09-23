package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.FamilyRoomActivity;
import com.qianyu.communicate.activity.SkillActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseWebActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.EnterGroup;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class EventRecordAdapter extends MyBaseAdapter<EventRecord.ContentBean, EventRecordAdapter.ViewHolder> {

    public EventRecordAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_event_record, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final EventRecord.ContentBean eventRecord = data.get(position);
            int sendSex = eventRecord.getSendSex();
//            int acceptSex = eventRecord.getAcceptSex();
//            if (!TextUtils.isEmpty(eventRecord.getEventMsg())) {
//                try {
//                    SpannableString content = Tools.matcherSearchTitle(context.getResources().getColor(sendSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
//                            context.getResources().getColor(acceptSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv),
//                            eventRecord.getEventMsg(), eventRecord.getSendNickName(), eventRecord.getAcceptNickName());
//                    holder.text.setText(SpanStringUtils.getEmotionContent_(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.text, content));
//                } catch (Exception e) {
//                    holder.text.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, holder.text, eventRecord.getEventMsg()));
//                }
//            } else {
//                holder.text.setText("");
//            }
            holder.text.setText(TextUtils.isEmpty(eventRecord.getEventMsg())?"": Html.fromHtml(eventRecord.getEventMsg()));
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(eventRecord.getHeadUrl()) ? "" : eventRecord.getHeadUrl());
            holder.nickNameTv.setText(TextUtils.isEmpty(eventRecord.getSendNickName()) ? "" : eventRecord.getSendNickName());
            holder.nickNameTv.setTextColor(context.getResources().getColor(sendSex == 1 ? R.color.chat_color_nan : R.color.chat_color_nv));
            holder.mTextLogo.setVisibility(TextUtils.isEmpty(eventRecord.getPicPath()) ? View.INVISIBLE : View.VISIBLE);
            ImageUtil.loadPicNet(TextUtils.isEmpty(eventRecord.getPicPath()) ? "" : eventRecord.getPicPath(), holder.mTextLogo);
            if(!TextUtils.isEmpty(eventRecord.getEventType())) {
                switch (eventRecord.getEventType()) {
                    case "FAMILYBOSS"://家族Boss
                    case "FAMILYCALL"://家族召唤
                    case "FAMILYRECRUIT"://世界招募
                        holder.mTextLogo.setVisibility(View.VISIBLE);
                        holder.mTextLogo.setImageResource(R.mipmap.enter);
                        break;
                }
            }
            holder.mTextLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(eventRecord.getEventType())) {
                        switch (eventRecord.getEventType()) {
                            case "SKILL":// 技能相关
                                Intent intent = new Intent(context, SkillActivity.class);
                                intent.putExtra("userId", eventRecord.getSendUserId());
                                context.startActivity(intent);
                                break;
                            case "FAMILYBOSS"://家族Boss
                            case "FAMILYCALL"://家族召唤
                            case "FAMILYRECRUIT"://世界招募
                                EventBuss event = new EventBuss(EventBuss.FAMILY_EXIT_ENTER);
                                event.setMessage(eventRecord);
                                EventBus.getDefault().post(event);
                                context.finish();
                                break;
                        }
                    }
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.text)
        TextView text;
        @InjectView(R.id.nickNameTv)
        TextView nickNameTv;
        @InjectView(R.id.mTextLogo)
        ImageView mTextLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
