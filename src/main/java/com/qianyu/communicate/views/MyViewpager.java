package com.qianyu.communicate.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/1/8 0008.
 */

public class MyViewpager extends ViewPager {

    public MyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent p_event) {
//        return true;
//    }
//
//    private PointF sp = new PointF();
//
//    @Override
//    public boolean onTouchEvent(MotionEvent arg0) {
//        // TODO Auto-generated method stub
//        int action = arg0.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                sp = new PointF(arg0.getX(), arg0.getY());
//                //====dyj=======
//                if (getParent() != null) {
//                    Log.i("DEBUG", "intercept move event");
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }
//                //====dyj=======
//                break;
//            case MotionEvent.ACTION_MOVE:
////                PointF ep = new PointF(arg0.getX(), arg0.getY());
////                float move_x = sp.x - ep.x;
////                if (!(move_x < 0 && getCurrentItem() == 0) && getParent() != null
////                        && !(move_x > 0 && getCurrentItem() == getAdapter().getCount() - 1)
////                        && sp.x > 50) {
////                    Log.i("DEBUG", "intercept move event");
////                    getParent().requestDisallowInterceptTouchEvent(true);
////                }
//                break;
//        }
//        return super.onTouchEvent(arg0);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}

