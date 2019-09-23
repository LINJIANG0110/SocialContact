package com.qianyu.communicate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.FriendList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.bean.SortModel;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

/**
 * Created by JavaDog on 2019-4-16.
 */

public class FriendRequestListAdapter extends BaseAdapter {
    private List<FriendList.ApplyListBean> mList = null;
    private Context mContext;

    public FriendRequestListAdapter(Context mContext, List<FriendList.ApplyListBean> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList == null ? position : mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup arg2) {
        FriendRequestListAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new FriendRequestListAdapter.ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_friend_request_list, null);
            viewHolder.mUserName = (TextView) view.findViewById(R.id.mUserName);
            viewHolder.mRejectTv = (TextView) view.findViewById(R.id.mRejectTv);
            viewHolder.mPassTv = (TextView) view.findViewById(R.id.mPassTv);
            viewHolder.mSexLogo = (ImageView) view.findViewById(R.id.mSexLogo);
            viewHolder.userOfficial = (ImageView) view.findViewById(R.id.userOfficial);
            viewHolder.mHeadImg = (SimpleDraweeView) view.findViewById(R.id.mHeadImg);
            viewHolder.mLayout = (LinearLayout) view.findViewById(R.id.layout);
            view.setTag(viewHolder);
        } else {
            viewHolder = (FriendRequestListAdapter.ViewHolder) view.getTag();
        }
        if (mList != null && mList.size() > 0) {
            final FriendList.ApplyListBean applyListBean = mList.get(position);
            viewHolder.mHeadImg.setImageURI(TextUtils.isEmpty(applyListBean.getHeadUrl()) ? "" : applyListBean.getHeadUrl());
            viewHolder.mUserName.setText(TextUtils.isEmpty(applyListBean.getNickName()) ? "" : applyListBean.getNickName());
            viewHolder.userOfficial.setVisibility(applyListBean.getExpand()>0?View.VISIBLE:View.GONE);
            int sex = applyListBean.getSex();
            switch (sex) {
                case 1:
                    viewHolder.mSexLogo.setImageResource(R.mipmap.xiangqing_nan1);
//                    mUserName.setTextColor(getResources().getColor(R.color.btn_blue_));
                    break;
                case 2:
                    viewHolder.mSexLogo.setImageResource(R.mipmap.xiangqing_nv1);
//                    mUserName.setTextColor(getResources().getColor(R.color.colorRed_));
                    break;
            }
            viewHolder.mRejectTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = MyApplication.getInstance().user;
                    if (user != null) {
                        NetUtils.getInstance().agreeApply(applyListBean.getUserId(), user.getUserId(), 2, new NetCallBack() {
                            @Override
                            public void onSuccess(String result, String msg, ResultModel model) {
                                ToastUtil.shortShowToast(msg);
                                mList.remove(applyListBean);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(String code, String msg, String result) {
                                ToastUtil.shortShowToast(msg);
                            }
                        }, null);
                    }
                }
            });
            viewHolder.mPassTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = MyApplication.getInstance().user;
                    if (user != null) {
                        NetUtils.getInstance().agreeApply(applyListBean.getUserId(), user.getUserId(), 1, new NetCallBack() {
                            @Override
                            public void onSuccess(String result, String msg, ResultModel model) {
                                ToastUtil.shortShowToast(msg);
                                mList.remove(applyListBean);
                                notifyDataSetChanged();
                                if (onFriendRequestListener != null) {
                                    onFriendRequestListener.onFriendRequest();
                                }
                                Tools.agreeFriend(applyListBean.getPhone());
                            }

                            @Override
                            public void onFail(String code, String msg, String result) {
                                ToastUtil.shortShowToast(msg);
                            }
                        }, null);
                    }
                }
            });
        }
        return view;
    }

    private OnFriendRequestListener onFriendRequestListener;

    public void setOnFriendRequestListener(OnFriendRequestListener onFriendRequestListener) {
        this.onFriendRequestListener = onFriendRequestListener;
    }

    public interface OnFriendRequestListener {
        void onFriendRequest();
    }

    final static class ViewHolder {
        TextView mUserName;
        ImageView mSexLogo;
        ImageView userOfficial;
        TextView mRejectTv;
        TextView mPassTv;
        SimpleDraweeView mHeadImg;
        LinearLayout mLayout;
    }

}
