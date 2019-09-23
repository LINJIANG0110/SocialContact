package com.qianyu.communicate.views.webView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by tangxiaowei on 2017/8/23.
 */

public class MyWebView extends WebView {
    private OnTouchScreenListener onTouchScreenListener;
    private boolean isClick = true;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isClick) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (onTouchScreenListener != null) {
                    return onTouchScreenListener.onTouchScreen();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public interface OnTouchScreenListener {
        boolean onTouchScreen();
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public void setOnTouchScreenListener(OnTouchScreenListener onTouchScreenListener) {
        this.onTouchScreenListener = onTouchScreenListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
