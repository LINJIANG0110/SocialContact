package com.qianyu.communicate.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import org.haitao.common.utils.AppLog;

/**
 * Created by JavaDog on 2018-3-8.
 */
public class DragViewGroup extends FrameLayout {
    private int lastX, lastY, screenWidth, screenHeight;
    private int dx;
    private int dy;
    private boolean isIntercept=false;

    public DragViewGroup(Context context) {
        this(context, null);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
//        screenHeight = dm.heightPixels-50;//减去下边的高度
        screenHeight = dm.heightPixels;//减去下边的高度
    }

    //定位
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        AppLog.e("position222:" + l + ", " + t + ", " + r + ", " + b);
        super.onLayout(changed, l, t, r, b);
        //可以在这里确定这个viewGroup的：宽 = r-l.高 = b - t
        AppLog.e("============onLayout==============");
    }

    //拦截touch事件
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
////        LogTool.e("onInterceptTouchEvent");
//        int action = ev.getAction();
//        AppLog.e("==========action=================" + super.onInterceptTouchEvent(ev));
//        if (action == MotionEvent.ACTION_DOWN) {
//            AppLog.e("==========ACTION_DOWN=================");
//            lastX = (int) ev.getRawX();//设定移动的初始位置相对位置
//            lastY = (int) ev.getRawY();
//        } else if (action == MotionEvent.ACTION_MOVE) {
//            AppLog.e("==========ACTION_MOVE=================");
//            //event.getRawX()事件点距离屏幕左上角的距离
//            dx = (int) ev.getRawX() - lastX;
//            dy = (int) ev.getRawY() - lastY;
//
//            int left = this.getLeft() + dx;
//            int top = this.getTop() + dy;
//            int right = this.getRight() + dx;
//            int bottom = this.getBottom() + dy;
//            if (left < 0) { //最左边
//                left = 0;
//                right = left + this.getWidth();
//            }
//            if (right > screenWidth) { //最右边
//                right = screenWidth;
//                left = right - this.getWidth();
//            }
//            if (top < 0) {  //最上边
//                top = 0;
//                bottom = top + this.getHeight();
//            }
//            if (bottom > screenHeight) {//最下边
//                bottom = screenHeight;
//                top = bottom - this.getHeight();
//            }
//            this.layout(left, top, right, bottom);//设置控件的新位置
//            AppLog.e("position111:" + left + ", " + top + ", " + right + ", " + bottom);
//            lastX = (int) ev.getRawX();//再次将滑动其实位置定位
//            lastY = (int) ev.getRawY();
//        } else if (action == MotionEvent.ACTION_UP) {
//            AppLog.e("==========ACTION_UP=================");
//        }
//        if (dx > 0 || dy > 0) {
//            onDragViewListener.onDrag(true);
//        } else {
//            onDragViewListener.onDrag(false);
//        }
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        LogTool.e("onInterceptTouchEvent");
        int action = ev.getAction();
        AppLog.e("==========action=================" + super.onInterceptTouchEvent(ev));
        if (action == MotionEvent.ACTION_DOWN) {
            AppLog.e("==========ACTION_DOWN=================");
            lastX = (int) ev.getRawX();//设定移动的初始位置相对位置
            lastY = (int) ev.getRawY();
        } else if (action == MotionEvent.ACTION_MOVE) {
            AppLog.e("==========ACTION_MOVE=================");
            //event.getRawX()事件点距离屏幕左上角的距离
            dx = (int) ev.getRawX() - lastX;
            dy = (int) ev.getRawY() - lastY;

            int left = this.getLeft() + dx;
            int top = this.getTop() + dy;
            int right = this.getRight() + dx;
            int bottom = this.getBottom() + dy;
            if (left < 0) { //最左边
                left = 0;
                right = left + this.getWidth();
            }
            if (right > screenWidth) { //最右边
                right = screenWidth;
                left = right - this.getWidth();
            }
            if (top < 0) {  //最上边
                top = 0;
                bottom = top + this.getHeight();
            }
            if (bottom > screenHeight) {//最下边
                bottom = screenHeight;
                top = bottom - this.getHeight();
            }
            this.layout(left, top, right, bottom);//设置控件的新位置
            AppLog.e("position111:" + left + ", " + top + ", " + right + ", " + bottom);
            lastX = (int) ev.getRawX();//再次将滑动其实位置定位
            lastY = (int) ev.getRawY();

            int lastMoveDx = Math.abs((int) ev.getRawX() - lastX);
            int lastMoveDy = Math.abs((int) ev.getRawY() - lastY);
            if (0 != lastMoveDx || 0 != lastMoveDy) {
                isIntercept = true;
            } else {
                isIntercept = false;
            }
            // 每次移动都要设置其layout，不然由于父布局可能嵌套listview，当父布局发生改变冲毁（如下拉刷新时）则移动的view会回到原来的位置
            FrameLayout.LayoutParams lpFeedback = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lpFeedback.leftMargin = getLeft();
            lpFeedback.topMargin = getTop();
            lpFeedback.setMargins(getLeft(), getTop(), 0, 0);
            setLayoutParams(lpFeedback);
        } else if (action == MotionEvent.ACTION_UP) {
            AppLog.e("==========ACTION_UP=================");
        }
        if (dx > 0 || dy > 0) {
            onDragViewListener.onDrag(true);
        } else {
            onDragViewListener.onDrag(false);
        }
        return isIntercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }


    public interface onDragViewListener {
        void onDrag(boolean drag);
    }

    onDragViewListener onDragViewListener;

    public void setOnDragViewListener(onDragViewListener onDragListener) {
        this.onDragViewListener = onDragListener;
    }
}