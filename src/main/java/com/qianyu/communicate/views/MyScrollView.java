package com.qianyu.communicate.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class MyScrollView extends ScrollView {
    private OnScrollChanged mOnScrollChanged;
    public MyScrollView(Context context) {
        this(context,null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChanged != null)
            mOnScrollChanged.onScroll(l, t, oldl, oldt);
    }

    public void setOnScrollChanged(OnScrollChanged onScrollChanged){
        this.mOnScrollChanged = onScrollChanged;
    }
    public interface OnScrollChanged{
        void onScroll(int l, int t, int oldl, int oldt);
    }
}