package com.qianyu.communicate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.InfoEditActivity;

import java.util.List;

/**
 * Created by JavaDog on 2019-8-28.
 */

public class PostArticleImgAdapter extends RecyclerView.Adapter<PostArticleImgAdapter.MyViewHolder> {

    private List<String> mDatas;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ItemClickListener itemClickListener;

    public PostArticleImgAdapter(Context context, List<String> datas) {
        this.mDatas = datas;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.item_post_activity, parent, false));
    }

    public void setmDatas(List<String> mDatas) {
        Log.e("setMdatas", "yes");
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        Log.e("*****url", mDatas.size() + "***" + position);
        if (position >= InfoEditActivity.IMAGE_SIZE) {//图片已选完时，隐藏添加按钮
            holder.imageView.setVisibility(View.GONE);
//            Log.e("*****url", "隐藏");
        } else {
            holder.imageView.setVisibility(View.VISIBLE);
//            Log.e("*****url", "隐藏2");
        }
//        Glide.with(mContext).load(mDatas.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
//        if (position == mDatas.size()-1){
//            Glide.with(mContext).load(mDatas.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
//        }else {
        Glide.with(mContext).load(mDatas.get(position)).placeholder(R.mipmap.def_image).into(holder.imageView);
//        }
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (position != mDatas.size() - 1)
//                itemClickListener.rvItemOnclick(position, holder);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sdv);
        }
    }

    public interface ItemClickListener {
        public void rvItemOnclick(int position, RecyclerView.ViewHolder vh);
    }

}