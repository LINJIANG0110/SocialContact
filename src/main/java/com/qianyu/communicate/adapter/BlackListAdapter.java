package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.BlackList;
import com.qianyu.communicate.entity.User;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class BlackListAdapter extends MyBaseAdapter<BlackList.ContentBean, BlackListAdapter.ViewHolder> {

    public BlackListAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_black_list, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final BlackList.ContentBean blackList = data.get(position);
            holder.mUserName.setText(TextUtils.isEmpty(blackList.getNickName()) ? "" : blackList.getNickName());
            holder.mRemoveBlackTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeBlack(position, blackList);
                }
            });
        }
    }

    private void removeBlack(final int p, BlackList.ContentBean blackList) {
        Tools.showDialog(context);
        NetUtils.getInstance().relieveBlack(blackList.getUserId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
                data.remove(p);
                notifyDataSetChanged();
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
            }
        },null);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mUserName)
        TextView mUserName;
        @InjectView(R.id.mRemoveBlackTv)
        TextView mRemoveBlackTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
