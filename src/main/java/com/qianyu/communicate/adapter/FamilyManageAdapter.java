package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyManage;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FamilyManageAdapter extends MyBaseAdapter<FamilyManage, FamilyManageAdapter.ViewHolder> {


    public FamilyManageAdapter(Activity context, List data) {
        super(context, data);
    }


    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_family_manage, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null&&data.size()>0) {
            FamilyManage familyManage = data.get(position);
            holder.mManageTv.setText(familyManage.getManageName());
            holder.mManageLogo.setImageResource(familyManage.isSelected()?R.mipmap.wxdl_xuanze:R.mipmap.wxdl_xuanzehui);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mManageLogo)
        ImageView mManageLogo;
        @InjectView(R.id.mManageTv)
        TextView mManageTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
