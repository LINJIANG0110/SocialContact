package com.qianyu.communicate.giftlibrary;

import android.animation.AnimatorSet;
import android.view.View;

/**
 * Created by KathLine on 2017/7/7.
 */

public interface ICustormAnim {
    AnimatorSet startAnim(GiftFrameLayout giftFrameLayout, View rootView);
    AnimatorSet endAnim(GiftFrameLayout giftFrameLayout, View rootView);
}
