package com.qianyu.communicate.giftlibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.widget.LinearLayout;

import com.qianyu.communicate.entity.GiftModel;
import com.qianyu.communicate.entity.RideModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by KathLine on 2017/1/8.
 */

public class GiftControl implements GiftFrameLayout.LeftGiftAnimationStatusListener {

    private static final String TAG = "GiftControl";
    protected Context mContext;
    private ArrayList<RideModel> mGiftQueue;
    private SparseArray<GiftFrameLayout> mGiftLayoutList;

    public GiftControl(Context context) {
        mContext = context;
        mGiftQueue = new ArrayList<>();
    }

    /**
     * @param isHideMode       是否开启隐藏动画
     * @param giftLayoutParent 存放礼物控件的父容器
     * @param giftLayoutNums   礼物控件的数量
     * @return
     */
    public GiftControl setGiftLayout(boolean isHideMode, LinearLayout giftLayoutParent, @NonNull int giftLayoutNums) {
        if (giftLayoutNums <= 0) {
            throw new IllegalArgumentException("GiftFrameLayout数量必须大于0");
        }
        if (giftLayoutParent.getChildCount() > 0) {//如果父容器没有子孩子，就进行添加
            return this;
        }
        final SparseArray<GiftFrameLayout> giftLayoutList = new SparseArray<>();
        for (int i = 0; i < giftLayoutNums; i++) {
            giftLayoutList.append(i, new GiftFrameLayout(mContext));
        }
        mGiftLayoutList = giftLayoutList;
        GiftFrameLayout giftFrameLayout;
        for (int i = 0; i < mGiftLayoutList.size(); i++) {
            giftFrameLayout = mGiftLayoutList.get(i);
            giftLayoutParent.addView(giftFrameLayout);
            giftFrameLayout.firstHideLayout();
            giftFrameLayout.setmIndex(i);
        }
        return this;
    }

    public void loadGift(RideModel gift) {
        addGiftQueue(gift);
    }

    private void addGiftQueue(final RideModel gift) {
        if (mGiftQueue != null) {
            if (mGiftQueue.size() == 0) {
                Log.d(TAG, "addGiftQueue---集合个数：" + mGiftQueue.size() + ",礼物：" + gift.getUserId());
                mGiftQueue.add(gift);
                showGift();
                return;
            }
        }
        Log.d(TAG, "addGiftQueue---集合个数：" + mGiftQueue.size() + ",礼物：" + gift.getUserId());
        mGiftQueue.add(gift);
    }

    /**
     * 显示礼物
     */
    public synchronized void showGift() {
        if (isEmpty()) {
            return;
        }
        GiftFrameLayout giftFrameLayout;
        for (int i = 0; i < mGiftLayoutList.size(); i++) {
            giftFrameLayout = mGiftLayoutList.get(i);
            Log.d(TAG, "showGift: begin->集合个数：" + mGiftQueue.size());
            boolean hasGift = giftFrameLayout.setGift(getGift());
            if (hasGift) {
                giftFrameLayout.startAnimation1(null);
            }
            Log.d(TAG, "showGift: end->集合个数：" + mGiftQueue.size());
        }
    }

    /**
     * 取出礼物
     *
     * @return
     */
    private synchronized RideModel getGift() {
        RideModel gift = null;
        if (mGiftQueue.size() != 0) {
            gift = mGiftQueue.get(0);
            mGiftQueue.remove(0);
            Log.i(TAG, "getGift---集合个数：" + mGiftQueue.size() + ",送出礼物---" + gift.getUserId() + ",礼物数X" + gift.getMountName());
        }
        return gift;
    }

    @Override
    public void dismiss(int index) {
        GiftFrameLayout giftFrameLayout;
        for (int i = 0; i < mGiftLayoutList.size(); i++) {
            giftFrameLayout = mGiftLayoutList.get(i);
            if (giftFrameLayout.getmIndex() == index) {
                reStartAnimation(giftFrameLayout, giftFrameLayout.getmIndex());
            }
        }
    }

    private void reStartAnimation(final GiftFrameLayout giftFrameLayout, final int index) {
        //动画结束，这时不能触发连击动画
        Log.d(TAG, "reStartAnimation: 动画结束");
        AnimatorSet animatorSet = giftFrameLayout.endAnmation(null);
        if (animatorSet != null) {
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.i(TAG, "礼物动画dismiss: index = " + index);
                    //动画完全结束
                    giftFrameLayout.setGiftViewEndVisibility(isEmpty());
                    showGift();
                }
            });
        }
    }

    /**
     * 礼物是否为空
     *
     * @return
     */
    public synchronized boolean isEmpty() {
        return mGiftQueue == null || mGiftQueue.size() == 0;
    }
}
