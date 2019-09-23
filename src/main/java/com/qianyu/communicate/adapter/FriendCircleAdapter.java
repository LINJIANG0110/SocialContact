package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.MyMBaseAdapter;
import com.qianyu.communicate.entity.CircleList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.image.ImagePreviewActivity;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FriendCircleAdapter extends MyMBaseAdapter<CircleList.ListBean.ContentBean, FriendCircleAdapter.ViewHolder> {

    public FriendCircleAdapter(Activity context, List data) {
        super(context, data);
    }

    private enum ItemType {
        Typezero, Typeone, Typetwo, Typethree, Typefour, Typefive, Typesix, Typeseven, Typeeight, Typenine;
    }

    @Override
    public int getItemViewType(int position) {
        final CircleList.ListBean.ContentBean circleList = data.get(position);
        List<String> mUrls = new ArrayList<>();
        String picPath = circleList.getFileItemUrl();
        if (!TextUtils.isEmpty(picPath)) {
            String[] split = picPath.split(",");
            for (int i = 0; i < split.length; i++) {
                mUrls.add(split[i]);
            }
        }
        if (mUrls.size() == 0) {
            return ItemType.Typezero.ordinal();
        } else if (mUrls.size() == 1) {
            return ItemType.Typeone.ordinal();
        } else if (mUrls.size() == 2) {
            return ItemType.Typetwo.ordinal();
        } else if (mUrls.size() == 3) {
            return ItemType.Typethree.ordinal();
        } else if (mUrls.size() == 4) {
            return ItemType.Typefour.ordinal();
        } else if (mUrls.size() == 5) {
            return ItemType.Typefive.ordinal();
        } else if (mUrls.size() == 6) {
            return ItemType.Typesix.ordinal();
        } else if (mUrls.size() == 7) {
            return ItemType.Typeseven.ordinal();
        } else if (mUrls.size() == 8) {
            return ItemType.Typeeight.ordinal();
        } else if (mUrls.size() == 9) {
            return ItemType.Typenine.ordinal();
        }
        return -1;
    }

    @Override
    protected ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        //根据不同的viewType，创建并返回影响的ViewHolder
        if (viewType == ItemType.Typezero.ordinal()) {
            return new ViewHolder(mInflater.inflate(R.layout.item_friend_circle, null));// 图片数量0的itemtype
        } else if (viewType == ItemType.Typeone.ordinal()) {
            return new ViewHolder(mInflater.inflate(R.layout.item_friend_circle_1, null));// 1张图
        } else if (viewType == ItemType.Typefour.ordinal()) {
            return new ViewHolder(mInflater.inflate(R.layout.item_friend_circle_4, null));// 4张图
        } else if (viewType == ItemType.Typetwo.ordinal() || viewType == ItemType.Typethree.ordinal()) {
            return new ViewHolder(mInflater.inflate(R.layout.item_friend_circle_23, null));// 2 3 张图通用
        } else if (viewType == ItemType.Typefive.ordinal() || viewType == ItemType.Typesix.ordinal()) {
            return new ViewHolder(mInflater.inflate(R.layout.item_friend_circle_56, null));// 5 6 张图通用
        } else {
            return new ViewHolder(mInflater.inflate(R.layout.item_friend_circle_789, null));// 2 3 5 6 7 8 9张图通用
        }
//        return null;
//        return new ViewHolder(mInflater.inflate(R.layout.item_friend_circle, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final CircleList.ListBean.ContentBean circleList = data.get(position);
            final List<String> mUrls = new ArrayList<>();
            String picPath = circleList.getFileItemUrl();
            if (!TextUtils.isEmpty(picPath)) {
                String[] split = picPath.split(",");
                for (int i = 0; i < split.length; i++) {
                    mUrls.add(split[i]);
                }
            }
//            holder.layoutNineGrid.setUrlList(mUrls);
//            holder.layoutNineGrid.setOnImgClickListener(new MyNineGridLayout.OnImgClickListener() {
//                @Override
//                public void onClick(int p, String url, List<String> urlList) {
//                    final ArrayList<ImageItem> mImageList = new ArrayList<>();
//                    mImageList.clear();
//                    if (urlList != null && urlList.size() > 0) {
//                        for (int i = 0; i < urlList.size(); i++) {
//                            ImageItem imageItem = new ImageItem();
//                            imageItem.setPath(urlList.get(i));
//                            mImageList.add(imageItem);
//                        }
//                        Intent intent = new Intent();
//                        intent.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, p);
//                        intent.setClass(context, ImagePreviewActivity.class);
//                        intent.putParcelableArrayListExtra(ImagePreviewActivity.IMAGEURL, mImageList);
//                        context.startActivity(intent);
//                    }
//                }
//            });
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(circleList.getHeadUrl()) ? "" : circleList.getHeadUrl());
            holder.mUserName.setText(TextUtils.isEmpty(circleList.getNickName()) ? "" : circleList.getNickName());
            holder.mContentTv.setText(TextUtils.isEmpty(circleList.getTitle()) ? "" : circleList.getTitle());
            holder.mContentTv.setVisibility(TextUtils.isEmpty(circleList.getTitle()) ? View.GONE : View.VISIBLE);
            Spanned spanned = Html.fromHtml("来自家族 <font color='#58e4df'><u>" + circleList.getGroupName() + "</u></font>");
            holder.mFamilyTv.setText(spanned);
            holder.mFamilyTv.setVisibility(TextUtils.isEmpty(circleList.getGroupName()) ? View.GONE : View.VISIBLE);
            holder.mUserAge.setText(circleList.getAge() + "");
            holder.mUserLevel.setText("Lv  " + circleList.getLevel());
            holder.mShareTv.setText(NumberUtils.roundInt(circleList.getFabulous()));
            holder.mCommentTv.setText(NumberUtils.roundInt(circleList.getComment()));
            holder.mTimeTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(circleList.getCreateTime()));
            holder.praiseLogo.setImageResource(1 == circleList.getIsClick() ? R.mipmap.dongtai_dianzan_se : R.mipmap.dongtai_dianzan);
            int sex = circleList.getSex();
            switch (sex) {
                case 1:
                    holder.sexLL.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_blue_));
                    holder.sexLogo.setImageResource(R.mipmap.xiangqing_nan1);
//                        holder.mUserName.setTextColor(context.getResources().getColor(R.color.btn_blue_));
                    break;
                case 2:
                    holder.sexLL.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_pink));
                    holder.sexLogo.setImageResource(R.mipmap.xiangqing_nv1);
//                        holder.mUserName.setTextColor(context.getResources().getColor(R.color.colorRed_));
                    break;
            }
            holder.mHeadImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PersonalPageActivity.class);
                    intent.putExtra("userId", circleList.getUserId());
                    context.startActivity(intent);
                }
            });
            holder.mCommentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpringUtils.springAnim(holder.praiseLogo);
                    praise(circleList, holder.mShareTv, holder.praiseLogo);
                }
            });
            holder.mFamilyTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tools.enterFamily(context, circleList.getGroupId(), false);
                }
            });
            // 这里设置九宫格图片
            if (mUrls.size() > 0) {
                int width = 105;
                int height = 105;
                if (mUrls.size() == 1) {
                    width = 140;
                    height = 200;
                } else if (mUrls.size() == 4) {
                    width = 140;
                    height = 140;
                }
                for (int i = 0; i < mUrls.size(); i++) {
                    try {
                        // 图片压缩，否则遇到大图片滑动卡顿
                        Uri uri = Uri.parse(mUrls.get(i));
                        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                                .setResizeOptions(new ResizeOptions(width, height))
                                .build();
                        if (i == 0) {
                            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                                    .setOldController(holder.mImage1.getController())
                                    .setImageRequest(request)
                                    .build();
                            holder.mImage1.setController(controller);
                        } else if (i == 1) {
                            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                                    .setOldController(holder.mImage2.getController())
                                    .setImageRequest(request)
                                    .build();
                            holder.mImage2.setController(controller);
                            if (mUrls.size() == 2) {
                                holder.mImage3.setVisibility(View.GONE);
                            }
                        } else if (i == 2) {
                            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                                    .setOldController(holder.mImage3.getController())
                                    .setImageRequest(request)
                                    .build();
                            holder.mImage3.setController(controller);
//                            holder.mImage3.setImageURI(mUrls.get(2));
                        } else if (i == 3) {
                            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                                    .setOldController(holder.mImage4.getController())
                                    .setImageRequest(request)
                                    .build();
                            holder.mImage4.setController(controller);
                        } else if (i == 4) {
                            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                                    .setOldController(holder.mImage5.getController())
                                    .setImageRequest(request)
                                    .build();
                            holder.mImage5.setController(controller);
                            if (mUrls.size() == 5) {
                                holder.mImage6.setVisibility(View.GONE);
                            }
                        } else if (i == 5) {
                            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                                    .setOldController(holder.mImage6.getController())
                                    .setImageRequest(request)
                                    .build();
                            holder.mImage6.setController(controller);
                        } else if (i == 6) {
                            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                                    .setOldController(holder.mImage7.getController())
                                    .setImageRequest(request)
                                    .build();
                            holder.mImage7.setController(controller);
                            if (mUrls.size() == 7) {
                                holder.mImage8.setVisibility(View.GONE);
                                holder.mImage9.setVisibility(View.GONE);
                            }
                        } else if (i == 7) {
                            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                                    .setOldController(holder.mImage8.getController())
                                    .setImageRequest(request)
                                    .build();
                            holder.mImage8.setController(controller);
                            if (mUrls.size() == 8) {
                                holder.mImage9.setVisibility(View.GONE);
                            }
                        } else if (i == 8) {
                            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                                    .setOldController(holder.mImage9.getController())
                                    .setImageRequest(request)
                                    .build();
                            holder.mImage9.setController(controller);
//                            holder.mImage9.setImageURI(mUrls.get(8));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                holder.mImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreview(mUrls,0);
                    }
                });
                holder.mImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreview(mUrls,1);
                    }
                });
                holder.mImage3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreview(mUrls,2);
                    }
                });
                holder.mImage4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreview(mUrls,3);
                    }
                });
                holder.mImage5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreview(mUrls,4);
                    }
                });
                holder.mImage6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreview(mUrls,5);
                    }
                });
                holder.mImage7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreview(mUrls,6);
                    }
                });
                holder.mImage8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreview(mUrls,7);
                    }
                });
                holder.mImage9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreview(mUrls,8);
                    }
                });
            }
        }
    }

    private void setPreview(List<String> urlList,int p) {
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

    private void praise(final CircleList.ListBean.ContentBean circleList, final TextView mShareTv, final ImageView praiseLogo) {
        final User user = MyApplication.getInstance().user;
        if (user != null) {
            NetUtils.getInstance().commentPraise(circleList.getCircleId(), user.getUserId(), circleList.getUserId(), 1, "", new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    ToastUtil.shortShowToast(msg);
                    circleList.setIsClick(circleList.getIsClick() == 0 ? 1 : 0);
                    if (circleList.getIsClick() == 0) {
                        circleList.setFabulous(circleList.getFabulous() - 1);
                    } else {
                        circleList.setFabulous(circleList.getFabulous() + 1);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onFail(String code, String msg, String result) {
                    ToastUtil.shortShowToast(msg);
                }
            }, null);
        } else {
            ToastUtil.shortShowToast("请先登录...");
            context.startActivity(new Intent(context, RegistActivity.class));
        }
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
        //        @InjectView(R.id.layout_nine_grid)
//        MyNineGridLayout layoutNineGrid;
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
        @InjectView(R.id.sexLL)
        LinearLayout sexLL;
        @InjectView(R.id.sexLogo)
        ImageView sexLogo;
        // 九宫格
        @InjectView(R.id.mImage1)
        SimpleDraweeView mImage1;
        @InjectView(R.id.mImage2)
        SimpleDraweeView mImage2;
        @InjectView(R.id.mImage3)
        SimpleDraweeView mImage3;
        @InjectView(R.id.mImage4)
        SimpleDraweeView mImage4;
        @InjectView(R.id.mImage5)
        SimpleDraweeView mImage5;
        @InjectView(R.id.mImage6)
        SimpleDraweeView mImage6;
        @InjectView(R.id.mImage7)
        SimpleDraweeView mImage7;
        @InjectView(R.id.mImage8)
        SimpleDraweeView mImage8;
        @InjectView(R.id.mImage9)
        SimpleDraweeView mImage9;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
