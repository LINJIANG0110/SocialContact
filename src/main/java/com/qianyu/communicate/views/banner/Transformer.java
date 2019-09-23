package com.qianyu.communicate.views.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.qianyu.communicate.views.banner.transformer.AccordionTransformer;
import com.qianyu.communicate.views.banner.transformer.BackgroundToForegroundTransformer;
import com.qianyu.communicate.views.banner.transformer.CubeInTransformer;
import com.qianyu.communicate.views.banner.transformer.CubeOutTransformer;
import com.qianyu.communicate.views.banner.transformer.DefaultTransformer;
import com.qianyu.communicate.views.banner.transformer.DepthPageTransformer;
import com.qianyu.communicate.views.banner.transformer.FlipHorizontalTransformer;
import com.qianyu.communicate.views.banner.transformer.FlipVerticalTransformer;
import com.qianyu.communicate.views.banner.transformer.ForegroundToBackgroundTransformer;
import com.qianyu.communicate.views.banner.transformer.RotateDownTransformer;
import com.qianyu.communicate.views.banner.transformer.RotateUpTransformer;
import com.qianyu.communicate.views.banner.transformer.ScaleInOutTransformer;
import com.qianyu.communicate.views.banner.transformer.StackTransformer;
import com.qianyu.communicate.views.banner.transformer.TabletTransformer;
import com.qianyu.communicate.views.banner.transformer.ZoomInTransformer;
import com.qianyu.communicate.views.banner.transformer.ZoomOutSlideTransformer;
import com.qianyu.communicate.views.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
