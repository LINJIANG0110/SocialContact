package com.qianyu.communicate.views.banner.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.generic.RoundingParams;
import com.qianyu.communicate.R;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;


public class FrescoImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //用fresco加载图片
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }
    //提供createImageView 方法，方便fresco自定义ImageView
    @Override
    public ImageView createImageView(Context context) {
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(1000)
                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                .setPlaceholderImage(R.mipmap.huoqutupianmoshi)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setFailureImage(R.mipmap.huoqutupianmoshi)
                .setFailureImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setProgressBarImage(R.mipmap.huoqutupianmoshi)
                .setProgressBarImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .setProgressBarImage(R.drawable.progressbar)
                .setProgressBarImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                .setRoundingParams(new RoundingParams().setCornersRadii(10, 10, 10, 10))
                .build();
        simpleDraweeView.setHierarchy(hierarchy);


//        app:progressBarAutoRotateInterval="3000"
//        app:progressBarImage="@drawable/progressbar"
//        app:progressBarImageScaleType="centerInside"
        return simpleDraweeView;
    }


}
