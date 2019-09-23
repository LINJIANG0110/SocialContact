package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.SpringUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.qianyu.communicate.image.ImagePreviewActivity;

import com.qianyu.communicate.base.MyBaseAdapter;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.applibrary.wight.MyNineGridLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class PersonalCircleAdapter extends MyBaseAdapter<User, PersonalCircleAdapter.ViewHolder> {


    public PersonalCircleAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_personal_circle, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        List<String> mUrls = new ArrayList<>();
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510910397110&di=3aa93fddfc7d8fdf37df366f1317dabb&imgtype=0&src=http%3A%2F%2Feasyread.ph.126.net%2FYybMgLmKMgxIPz6AlWhEnw%3D%3D%2F7917092849429000484.jpg");
        if (position == 0)
            holder.layoutNineGrid.setUrlList(mUrls);
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510910519450&di=5b9941c9def656d8f40b400e54fa9a40&imgtype=0&src=http%3A%2F%2Fwww.shuoshuokong.org%2Fuploads%2Fallimg%2F160819%2F2-160Q9111547-50.jpg");
        if (position == 1)
            holder.layoutNineGrid.setUrlList(mUrls);
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510910519448&di=b20b7f1faf16c3c772e00edaa11513f5&imgtype=0&src=http%3A%2F%2Fi.dimg.cc%2F1a%2Fb0%2F16%2Fa8%2F98%2F69%2Fb8%2F76%2F05%2Fee%2F08%2Fc1%2Fb4%2Fca%2F5f%2F29.jpg");
        if (position == 2)
            holder.layoutNineGrid.setUrlList(mUrls);
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510910569218&di=1a57e10cf3ce8e001afd1fd0a85dd550&imgtype=0&src=http%3A%2F%2Fimg.mp.sohu.com%2Fupload%2F20170727%2F91e465a94ed441728fa35abf6d48d313_th.png");
        if (position == 3)
            holder.layoutNineGrid.setUrlList(mUrls);
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510910569218&di=fd69d6604004bd07449fed64b8fa789f&imgtype=0&src=http%3A%2F%2Fcss.topstudy8.com%2Ftophumpcaimg2014%2F20150408%2FHZM_20150408163742691.jpg");
        if (position == 4)
            holder.layoutNineGrid.setUrlList(mUrls);
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510910569217&di=a23d815af1ca89cc2b48735e848e12da&imgtype=0&src=http%3A%2F%2Fwww.shuoshuokong.org%2Fuploads%2Fallimg%2F160819%2F2-160QZZF8.jpg");
        if (position == 5)
            holder.layoutNineGrid.setUrlList(mUrls);
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510910634105&di=f0e93bf3844ccb2c6e75a19c845ed2c2&imgtype=0&src=http%3A%2F%2Fi.dimg.cc%2F7b%2F5d%2Fbf%2F82%2F94%2F44%2Fc1%2Fc0%2F96%2F55%2F72%2F37%2Fe7%2F85%2F6c%2Fd5.jpg");
        if (position == 6)
            holder.layoutNineGrid.setUrlList(mUrls);
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510910634100&di=17116f01989f4101986e4ce3bbcc522e&imgtype=0&src=http%3A%2F%2Fimg.mp.sohu.com%2Fq_mini%2Cc_zoom%2Cw_640%2Fupload%2F20170803%2F308581a7a25e45d19a56df6a8611b9b2_th.jpg");
        if (position == 7)
            holder.layoutNineGrid.setUrlList(mUrls);
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510910665038&di=d63519b5535a0f57ac5d4549366ce290&imgtype=0&src=http%3A%2F%2Fimg.mp.sohu.com%2Fq_mini%2Cc_zoom%2Cw_640%2Fupload%2F20170803%2Fe88d661111624c049de88b9b0fa99efd_th.jpg");
        if (position == 8)
            holder.layoutNineGrid.setUrlList(mUrls);
        holder.layoutNineGrid.setOnImgClickListener(new MyNineGridLayout.OnImgClickListener() {
            @Override
            public void onClick(int p, String url, List<String> urlList) {
                final ArrayList<ImageItem> mImageList = new ArrayList<>();
                mImageList.clear();
                if (urlList != null && urlList.size() > 0) {
                    for (int i = 0; i < urlList.size(); i++) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.setPath(urlList.get(i));
                        mImageList.add(imageItem);
                    }
                    Intent intent = new Intent();
                    intent.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, p);
                    intent.setClass(context, ImagePreviewActivity.class);
                    intent.putParcelableArrayListExtra(ImagePreviewActivity.IMAGEURL, mImageList);
                    context.startActivity(intent);
                }
            }
        });
        holder.mHeadImg.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
        Spanned spanned = Html.fromHtml("来自家族 <font color='#58e4df'><u>美人鱼的眼泪</u></font>");
        holder.mFamilyTv.setText(spanned);

        holder.mShareLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showSharePopWindow(holder.mRootView);
                SpringUtils.springAnim(holder.commentLogo);
            }
        });

        holder.mCommentLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpringUtils.springAnim(holder.praiseLogo);
            }
        });

        holder.mHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PersonalPageActivity.class));
            }
        });
    }

    private void showSharePopWindow(LinearLayout mRootView) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(context)
                //设置PopupWindow布局
                .setView(R.layout.layout_share_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        FrameLayout qqShare = view.findViewById(R.id.qqShare);
                        FrameLayout wxShare = view.findViewById(R.id.wxShare);
                        FrameLayout sinaShare = view.findViewById(R.id.sinaShare);
                        FrameLayout circleShare = view.findViewById(R.id.circleShare);
                        TextView cancelShare = view.findViewById(R.id.cancelShare);
                        cancelShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mRootView)
        LinearLayout mRootView;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mUserName)
        TextView mUserName;
        @InjectView(R.id.mUserAge)
        TextView mUserAge;
        @InjectView(R.id.mUserLevel)
        TextView mUserLevel;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.layout_nine_grid)
        MyNineGridLayout layoutNineGrid;
        @InjectView(R.id.mFamilyTv)
        TextView mFamilyTv;
        @InjectView(R.id.mGiftTv)
        TextView mGiftTv;
        @InjectView(R.id.mGiftLL)
        LinearLayout mGiftLL;
        @InjectView(R.id.mPraiseTv)
        TextView mPraiseTv;
        @InjectView(R.id.mPraiseLL)
        LinearLayout mPraiseLL;
        @InjectView(R.id.mCommentTv)
        TextView mCommentTv;
        @InjectView(R.id.mCommentLL)
        LinearLayout mCommentLL;
        @InjectView(R.id.mShareTv)
        TextView mShareTv;
        @InjectView(R.id.mShareLL)
        LinearLayout mShareLL;
        @InjectView(R.id.praiseLogo)
        ImageView praiseLogo;
        @InjectView(R.id.commentLogo)
        ImageView commentLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
