package com.qianyu.communicate.utils;

import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class SpringUtils {

    public static void springAnim(final View view) {
        com.facebook.rebound.SpringSystem mSpringSystem = com.facebook.rebound.SpringSystem.create();
        Spring spring = mSpringSystem
                .createSpring()
                .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(250, 8))
                .addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(Spring spring) {
                        float value = (float) spring.getCurrentValue();
                        view.setScaleX(value);
                        view.setScaleY(value);
                    }
                });
        spring.setEndValue(1);
    }
}
