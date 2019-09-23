package com.qianyu.communicate.giftlibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.RideModel;


/**
 * Created by KathLine on 2017/1/8.
 */
public class GiftFrameLayout extends FrameLayout {

    private LayoutInflater mInflater;
    LinearLayout mRideViewLL;
    SimpleDraweeView mRideImageView;
    TextView mRideName;
    TextView mRideTv;
    private RideModel mGift;
    private View rootView;
    private int mIndex = 1;

    public int getmIndex() {
        return mIndex;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public GiftFrameLayout(Context context) {
        this(context, null);
    }

    public GiftFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        rootView = mInflater.inflate(R.layout.item_gift, null);
        mRideViewLL = rootView.findViewById(R.id.mRideViewLL);
        mRideImageView = rootView.findViewById(R.id.mRideImageView);
        mRideName = rootView.findViewById(R.id.mRideName);
        mRideTv = rootView.findViewById(R.id.mRideTv);
        this.addView(rootView);
    }

    public void firstHideLayout() {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(GiftFrameLayout.this, alpha);
        animator.setStartDelay(0);
        animator.setDuration(0);
        animator.start();
    }

    public void setGiftViewEndVisibility(boolean hasGift) {
        if (hasGift) {
            GiftFrameLayout.this.setVisibility(View.GONE);
        } else {
            GiftFrameLayout.this.setVisibility(View.INVISIBLE);
        }
    }

    public boolean setGift(RideModel gift) {
        if (gift == null) {
            return false;
        }
        mGift = gift;
        mRideName.setText(TextUtils.isEmpty(gift.getNickName()) ? "" : gift.getNickName());
        mRideTv.setText("驾驭" + gift.getMountName() + "上线了！");
//        mRideImageView.setImageURI(TextUtils.isEmpty(gift.getMountPicPath()) ? "" : gift.getMountPicPath());
        mRideImageView.setImageURI("https://qianyubk.oss-cn-shanghai.aliyuncs.com/mount/MOUNTS1.png");
        return true;
    }

    public RideModel getGift() {
        return mGift;
    }

    public interface LeftGiftAnimationStatusListener {
        void dismiss(int index);
    }
    public void initLayoutState() {
        this.setVisibility(View.VISIBLE);
        this.setAlpha(1f);
        mRideName.setText(TextUtils.isEmpty(mGift.getNickName()) ? "" : mGift.getNickName());
        mRideTv.setText("驾驭" + mGift.getMountName() + "上线了！");
//        mRideImageView.setImageURI(TextUtils.isEmpty(gift.getMountPicPath()) ? "" : gift.getMountPicPath());
        mRideImageView.setImageURI("https://qianyubk.oss-cn-shanghai.aliyuncs.com/mount/MOUNTS1.png");
    }

    public AnimatorSet startAnimation1(ICustormAnim anim) {
        if (anim == null) {
//            hideView();
            //布局飞入
            ObjectAnimator flyFromLtoR = GiftAnimationUtil.createFlyFromLtoR(mRideViewLL, -getWidth(), 0, 400, new OvershootInterpolator());
            flyFromLtoR.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    initLayoutState();
                }
            });
            //礼物飞入
            ObjectAnimator flyFromLtoR2 = GiftAnimationUtil.createFlyFromLtoR(mRideImageView, -getWidth(), 0, 400, new DecelerateInterpolator());
            flyFromLtoR2.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mRideImageView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }
            });
            AnimatorSet animatorSet = GiftAnimationUtil.startAnimation(flyFromLtoR, flyFromLtoR2);
            return animatorSet;
        } else {
            return anim.startAnim(this, rootView);
        }
    }

    public AnimatorSet endAnmation(ICustormAnim anim) {
        if (anim == null) {
            //向上渐变消失
            ObjectAnimator fadeAnimator = GiftAnimationUtil.createFadeAnimator(GiftFrameLayout.this, 0, -100, 500, 0);
            fadeAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {

                }
            });
            // 复原
            ObjectAnimator fadeAnimator2 = GiftAnimationUtil.createFadeAnimator(GiftFrameLayout.this, 100, 0, 0, 0);
            AnimatorSet animatorSet = GiftAnimationUtil.startAnimation(fadeAnimator, fadeAnimator2);
            return animatorSet;
        } else {
            return anim.endAnim(this, rootView);
        }
    }
}
