package com.qianyu.communicate.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class MyRecylerView extends RecyclerView {
    public MyRecylerView(Context context) {
        super(context);
    }

    public MyRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int newHeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, newHeightSpec);
    }
}
