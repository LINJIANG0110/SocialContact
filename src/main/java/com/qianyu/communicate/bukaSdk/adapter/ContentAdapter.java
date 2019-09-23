package com.qianyu.communicate.bukaSdk.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.RedpackageResActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.emotions.utils.EmotionUtils;
import com.qianyu.communicate.emotions.utils.SpanStringUtils;
import com.qianyu.communicate.entity.SendCustomMsgBean;
import com.qianyu.communicate.entity.User;

import net.neiquan.applibrary.utils.ImageUtil;

import java.util.LinkedList;

public class ContentAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater = null;
    private LinkedList<UserBean> mList;
    private Activity activity;
    JsonParser parser;
    Gson mGson;

    public ContentAdapter(Activity activity) {
        super();
        this.activity = activity;
        this.mLayoutInflater = LayoutInflater.from(activity);
        this.mList = new LinkedList<UserBean>();
        parser = new JsonParser();
        mGson = new Gson();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_chat_room, null);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.text);
            holder.mChatRoomLL = convertView.findViewById(R.id.mChatRoomLL);
            holder.nickNameTv = convertView.findViewById(R.id.nickNameTv);
            holder.addressTv = convertView.findViewById(R.id.addressTv);
            holder.mUserLevel = convertView.findViewById(R.id.mUserLevel);
            holder.userRole = convertView.findViewById(R.id.userRole);
            holder.mHeadImg = convertView.findViewById(R.id.mHeadImg);
            holder.mStatusTv = convertView.findViewById(R.id.mStatusTv);
            holder.ivRedpackage = convertView.findViewById(R.id.iv_redpackage);
            holder.mLogoImg = convertView.findViewById(R.id.mLogoImg);
            holder.userOfficial = convertView.findViewById(R.id.userOfficial);
            holder.llayType7Root = convertView.findViewById(R.id.llay_type7root);
            holder.llayType0Root = convertView.findViewById(R.id.llay_type0root);
            holder.tvWord = convertView.findViewById(R.id.tv_word);
            holder.tvIdiom = convertView.findViewById(R.id.tv_idiom);

            holder.text_ = convertView.findViewById(R.id.text_);
            holder.mChatRoomLL_ = convertView.findViewById(R.id.mChatRoomLL_);
            holder.nickNameTv_ = convertView.findViewById(R.id.nickNameTv_);
            holder.addressTv_ = convertView.findViewById(R.id.addressTv_);
            holder.mUserLevel_ = convertView.findViewById(R.id.mUserLevel_);
            holder.userRole_ = convertView.findViewById(R.id.userRole_);
            holder.mHeadImg_ = convertView.findViewById(R.id.mHeadImg_);
            holder.mLogoImg_ = convertView.findViewById(R.id.mLogoImg_);
            holder.userOfficial_ = convertView.findViewById(R.id.userOfficial_);
            holder.llayType7Root_ = convertView.findViewById(R.id.llay_type7root_);
            holder.llayType0Root_ = convertView.findViewById(R.id.llay_type0root_);
            holder.tvWord_ = convertView.findViewById(R.id.tv_word_);
            holder.tvIdiom_ = convertView.findViewById(R.id.tv_idiom_);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mList != null && mList.size() > 0) {
            User user = MyApplication.getInstance().user;
            final UserBean userBean = mList.get(mList.size() - arg0 - 1);
            if (userBean.getUserId() == user.getUserId()) {
                String messageExt = userBean.getMessageExt();
                if (messageExt != null && !messageExt.equals("")) {
                    // 是json
                    SendCustomMsgBean customBean = mGson.fromJson(messageExt, SendCustomMsgBean.class);
                    int messageType = customBean.getMessageType();
                    if (messageType == 7) {
                        // 自定义红包
                        holder.llayType7Root_.setVisibility(View.VISIBLE);
                        holder.llayType0Root_.setVisibility(View.GONE);
                        holder.tvIdiom_.setText(customBean.redPacketWord);
                        holder.tvWord_.setText("接龙红包：接龙拼音" + customBean.nextWordPinyin);
                    } else if (messageType == 0) {
                        holder.llayType7Root_.setVisibility(View.GONE);
                        holder.llayType0Root_.setVisibility(View.VISIBLE);
                        // 普通消息
                        String mText = customBean.text;
                        // 非json直接显示
                        holder.text_.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, activity, holder.text_, (TextUtils.isEmpty(mText) ? "" : mText)));
                    }
                } else {
                    // 非json直接显示
                    holder.text_.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, activity, holder.text_, (TextUtils.isEmpty(userBean.getText()) ? "" : userBean.getText())));
                }
                holder.nickNameTv_.setText(TextUtils.isEmpty(userBean.getNickName()) ? "" : userBean.getNickName());
                holder.mHeadImg_.setImageURI(TextUtils.isEmpty(userBean.getHeadUrl()) ? "" : userBean.getHeadUrl());
                holder.addressTv_.setText(TextUtils.isEmpty(userBean.getAddress()) ? "" : userBean.getAddress().replace("市", ""));
                holder.mUserLevel_.setText("" + userBean.getLevel());
                holder.mLogoImg_.setVisibility(TextUtils.isEmpty(userBean.getDesignationUrl()) ? View.GONE : View.VISIBLE);
                holder.userOfficial_.setVisibility(userBean.getExpand() > 0 ? View.VISIBLE : View.GONE);
                ImageUtil.loadPicNet(userBean.getDesignationUrl(), holder.mLogoImg_);
                int sex = userBean.getSex();
                switch (sex) {
                    case 1:
                        holder.nickNameTv_.setTextColor(activity.getResources().getColor(R.color.chat_color_nan));
                        break;
                    case 2:
                        holder.nickNameTv_.setTextColor(activity.getResources().getColor(R.color.chat_color_nv));
                        break;
                }
                int role = userBean.getUserType();
                switch (role) {
                    case 1:
                        holder.userRole_.setVisibility(View.VISIBLE);
                        holder.userRole_.setImageResource(R.mipmap.patriarch);
                        break;
                    case 2:
                        holder.userRole_.setVisibility(View.VISIBLE);
                        holder.userRole_.setImageResource(R.mipmap.manager);
                        break;
                    default:
                        holder.userRole_.setVisibility(View.INVISIBLE);
                        break;
                }
                holder.llayType7Root_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendCustomMsgBean customBean = mGson.fromJson(userBean.getMessageExt(), SendCustomMsgBean.class);
                        activity.startActivity(new Intent(activity, RedpackageResActivity.class).putExtra("groupId", userBean.getGroupId()).putExtra("redPackageId", customBean.getRedPacketId() + ""));
                    }
                });
            } else {
                String messageExt = userBean.getMessageExt();
                if (messageExt != null && !messageExt.equals("")) {
                    SendCustomMsgBean mBean = mGson.fromJson(messageExt, SendCustomMsgBean.class);
                    int messageType = mBean.getMessageType();
                    if (messageType == 7) {
                        // 自定义红包
                        holder.llayType7Root.setVisibility(View.VISIBLE);
                        holder.llayType0Root.setVisibility(View.GONE);
                        holder.tvIdiom.setText(mBean.redPacketWord);
                        holder.tvWord.setText("接龙红包：接龙拼音" + mBean.nextWordPinyin);
                    } else if (messageType == 0) {
                        holder.llayType7Root.setVisibility(View.GONE);
                        holder.llayType0Root.setVisibility(View.VISIBLE);
                        // 普通消息
                        String mText = mBean.text;
                        // 非json直接显示
                        holder.text.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, activity, holder.text, (TextUtils.isEmpty(mText) ? "" : mText)));
                    }
                } else {
                    // item非本人
                    // 非json直接显示
                    holder.text.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, activity, holder.text, (TextUtils.isEmpty(userBean.getText()) ? "" : userBean.getText())));
                }
                holder.nickNameTv.setText(TextUtils.isEmpty(userBean.getNickName()) ? "" : userBean.getNickName());
                holder.mHeadImg.setImageURI(TextUtils.isEmpty(userBean.getHeadUrl()) ? "" : userBean.getHeadUrl());
                holder.addressTv.setText(TextUtils.isEmpty(userBean.getAddress()) ? "" : userBean.getAddress().replace("市", ""));
                holder.mUserLevel.setText("" + userBean.getLevel());
                holder.mLogoImg.setVisibility(TextUtils.isEmpty(userBean.getDesignationUrl()) ? View.GONE : View.VISIBLE);
                holder.userOfficial.setVisibility(userBean.getExpand() > 0 ? View.VISIBLE : View.GONE);
                ImageUtil.loadPicNet(userBean.getDesignationUrl(), holder.mLogoImg);
                int sex = userBean.getSex();
                switch (sex) {
                    case 1:
                        holder.nickNameTv.setTextColor(activity.getResources().getColor(R.color.chat_color_nan));
                        break;
                    case 2:
                        holder.nickNameTv.setTextColor(activity.getResources().getColor(R.color.chat_color_nv));
                        break;
                }
                int role = userBean.getUserType();
                switch (role) {
                    case 1:
                        holder.userRole.setVisibility(View.VISIBLE);
                        holder.userRole.setImageResource(R.mipmap.patriarch);
                        break;
                    case 2:
                        holder.userRole.setVisibility(View.VISIBLE);
                        holder.userRole.setImageResource(R.mipmap.manager);
                        break;
                    default:
                        holder.userRole.setVisibility(View.INVISIBLE);
                        break;
                }
            }
            int type = userBean.getMstype();
            switch (type) {
                case 0:
                    if (userBean.getUserId() == user.getUserId()) {
                        holder.mChatRoomLL_.setVisibility(View.VISIBLE);
                        holder.mChatRoomLL.setVisibility(View.GONE);
                    } else {
                        holder.mChatRoomLL.setVisibility(View.VISIBLE);
                        holder.mChatRoomLL_.setVisibility(View.GONE);
                    }
                    holder.mStatusTv.setVisibility(View.GONE);
                    holder.mStatusTv.setText(userBean.getText());
                    break;
                case 1:
                    holder.mChatRoomLL.setVisibility(View.GONE);
                    holder.mStatusTv.setVisibility(View.VISIBLE);
                    holder.mStatusTv.setText(userBean.getNickName() + "已被禁言72小时");
                    break;
                case 2:
                    holder.mChatRoomLL.setVisibility(View.GONE);
                    holder.mStatusTv.setVisibility(View.VISIBLE);
                    holder.mStatusTv.setText(userBean.getNickName() + "已被解除禁言");
                    break;
                case 3:
                    holder.mChatRoomLL.setVisibility(View.GONE);
                    holder.mStatusTv.setVisibility(View.VISIBLE);
                    holder.mStatusTv.setText(userBean.getNickName() + "已被禁入两小时");
                    break;
                case 4:
                    holder.mChatRoomLL.setVisibility(View.GONE);
                    holder.mStatusTv.setVisibility(View.VISIBLE);
                    holder.mStatusTv.setText(userBean.getNickName() + "已被解除禁入");
                    break;
                case 5:
                    holder.mChatRoomLL.setVisibility(View.GONE);
                    holder.mStatusTv.setVisibility(View.VISIBLE);
                    holder.mStatusTv.setText(userBean.getNickName() + "已被设为管理员");
                    break;
                case 6:
                    holder.mChatRoomLL.setVisibility(View.GONE);
                    holder.mStatusTv.setVisibility(View.VISIBLE);
                    holder.mStatusTv.setText(userBean.getNickName() + "已被踢出该群");
                    break;
            }
            // 最后一道判断
            String messageExt = userBean.getMessageExt();
            if (messageExt != null && !messageExt.equals("")) {
                // 是json
                SendCustomMsgBean customBean = mGson.fromJson(messageExt, SendCustomMsgBean.class);
                int messageType = customBean.getMessageType();
                if (messageType == 8) {
                    // 领取成功
                    holder.mChatRoomLL_.setVisibility(View.GONE);
                    holder.mChatRoomLL.setVisibility(View.GONE);
                    holder.ivRedpackage.setVisibility(View.VISIBLE);
                    holder.mStatusTv.setVisibility(View.VISIBLE);
                    holder.mStatusTv.setText(customBean.getText());
                }
                if (messageType == 9) {
                    // 领取成功
                    holder.mChatRoomLL_.setVisibility(View.GONE);
                    holder.mChatRoomLL.setVisibility(View.VISIBLE);
                    holder.ivRedpackage.setVisibility(View.GONE);
                    holder.mStatusTv.setVisibility(View.GONE);
                    holder.mLogoImg.setVisibility(View.GONE);
                    holder.llayType0Root.setVisibility(View.GONE);
                    holder.addressTv.setVisibility(View.GONE);
                    holder.userRole.setVisibility(View.GONE);
                    holder.mUserLevel.setVisibility(View.GONE);
                    holder.llayType7Root.setVisibility(View.VISIBLE);
                    holder.mHeadImg.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.mipmap.logo)).build());
                    holder.nickNameTv.setText("千千");
                    holder.nickNameTv.setTextColor(Color.rgb(255,108,204));
                    holder.tvIdiom.setText(customBean.getRedPacketWord());
                    holder.tvWord.setText("接龙红包：接龙拼音" + customBean.nextWordPinyin);
                } else {
                    holder.ivRedpackage.setVisibility(View.GONE);

                    // holder.mLogoImg.setVisibility(View.VISIBLE);
                    holder.addressTv.setVisibility(View.VISIBLE);
                    holder.userRole.setVisibility(View.VISIBLE);
                    holder.mUserLevel.setVisibility(View.VISIBLE);
                }
            } else {
                holder.ivRedpackage.setVisibility(View.GONE);
            }
            holder.mHeadImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 左侧可能有系统红包，该头像点击屏蔽
                    String messageExt = userBean.getMessageExt();
                    if (messageExt != null && !messageExt.equals("")) {
                        // 是json
                        SendCustomMsgBean customBean = mGson.fromJson(messageExt, SendCustomMsgBean.class);
                        int messageType = customBean.getMessageType();
                        if (messageType == 9) {
                            return;
                        }
                    }
                    if (onPersonalInfoListener != null) {
                        onPersonalInfoListener.onPersonalInfo(userBean);
                    }
                }
            });
            holder.mHeadImg_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPersonalInfoListener != null) {
                        onPersonalInfoListener.onPersonalInfo(userBean);
                    }
                }
            });
            holder.llayType7Root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendCustomMsgBean customBean = mGson.fromJson(userBean.getMessageExt(), SendCustomMsgBean.class);
                    activity.startActivity(new Intent(activity, RedpackageResActivity.class).putExtra("groupId", userBean.getGroupId()).putExtra("redPackageId", customBean.getRedPacketId() + ""));
                }
            });
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView text;
        TextView nickNameTv;
        TextView addressTv;
        ImageView userRole;
        SimpleDraweeView mHeadImg;
        TextView mStatusTv;
        ImageView ivRedpackage;
        TextView mUserLevel;
        LinearLayout mChatRoomLL;
        LinearLayout mChatRoomLL_;
        TextView text_;
        TextView nickNameTv_;
        TextView addressTv_;
        ImageView userRole_;
        SimpleDraweeView mHeadImg_;
        TextView mUserLevel_;
        ImageView mLogoImg;
        ImageView mLogoImg_;
        ImageView userOfficial;
        ImageView userOfficial_;
        LinearLayout llayType7Root;
        LinearLayout llayType7Root_;
        LinearLayout llayType0Root;
        LinearLayout llayType0Root_;
        TextView tvWord;
        TextView tvWord_;
        TextView tvIdiom;
        TextView tvIdiom_;
    }

    public LinkedList<UserBean> getmList() {
        return mList;
    }

    public void setmList(LinkedList<UserBean> mList) {
        this.mList = mList;
    }

    public void addMList(LinkedList<UserBean> mList) {
        if (mList != null) {
            this.mList.addAll(mList);
        }
    }

    private OnPersonalInfoListener onPersonalInfoListener;

    public void setOnPersonalInfoListener(OnPersonalInfoListener onPersonalInfoListener) {
        this.onPersonalInfoListener = onPersonalInfoListener;
    }

    public interface OnPersonalInfoListener {
        void onPersonalInfo(UserBean userBean);
    }
}
