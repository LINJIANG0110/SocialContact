package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

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

public class FamilyRequestAdapter extends MyBaseAdapter<FamilyDetail.ApplyListBean, FamilyRequestAdapter.ViewHolder> {
    private long gId;

    public long getgId() {
        return gId;
    }

    public void setgId(long gId) {
        this.gId = gId;
    }

    public FamilyRequestAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_family_request, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        holder.mRootLL.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (data != null && data.size() > 0) {
            final FamilyDetail.ApplyListBean applyListBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(applyListBean.getHeadUrl()) ? "" : applyListBean.getHeadUrl());
            holder.userName.setText(TextUtils.isEmpty(applyListBean.getNickName()) ? "" : applyListBean.getNickName());
            holder.mRejectTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agreeGroup(applyListBean, 0);
                }
            });
            holder.mPassTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agreeGroup(applyListBean, 1);
                }
            });
        }
    }

    private void agreeGroup(final FamilyDetail.ApplyListBean applyListBean, int isSuccess) {
        Tools.showDialog(context);
        NetUtils.getInstance().agreeGroup(gId, applyListBean.getUserId(), isSuccess, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
                removeSingle(applyListBean);
                notifyDataSetChanged();
                EventBus.getDefault().post(new EventBuss( EventBuss.FAMILY_APPLY ));
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
            }
        }, null);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.userName)
        TextView userName;
        @InjectView(R.id.mRootLL)
        LinearLayout mRootLL;
        @InjectView(R.id.mRejectTv)
        TextView mRejectTv;
        @InjectView(R.id.mPassTv)
        TextView mPassTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
