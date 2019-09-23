package com.qianyu.communicate.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;


import net.neiquan.applibrary.R;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * 作者 ： 邓勇军
 * 时间 ： 2016/12/29.
 * version:1.0
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 根元素
     */
    protected View rootView;
    /**
     * 布局渲染工具
     */
    protected LayoutInflater mInflater;
    protected Context mContext;
    public Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mContext = inflater.getContext();
        mActivity = getActivity();
        rootView = mInflater.inflate(getRootViewId(), null);
        ButterKnife.inject(this, rootView);
        setViews();
        initData();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return rootView;
    }


    /**
     * 初始化View,加载布局
     *
     * @return 布局
     */
    public abstract int getRootViewId();

    /**
     * 解决fragment嵌套问题:No activity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化数据
     */
    public abstract void setViews();

    /**
     * 加载网络数据
     */
    public abstract void initData();


    protected void setTitleTv(int id) {
        TextView tv = rootView.findViewById(R.id.title_tv);
        tv.setText(id);
    }

    protected void setHeadVisibility(int visibility) {
        rootView.findViewById(net.neiquan.applibrary.R.id.head_view).setVisibility(visibility);
    }

    protected void setTitleTv(String str) {
        TextView tv = rootView.findViewById(R.id.title_tv);
        tv.setText(str);
    }


    protected void setNextTv(String str) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.VISIBLE);
        tv.setText(str);
        View iv = rootView.findViewById(R.id.next_img);
        iv.setVisibility(View.GONE);
    }

    protected TextView getNextTv() {

        return (TextView) rootView.findViewById(R.id.next_tv);
    }

    protected void setBackGone() {
        View tv = rootView.findViewById(R.id.ly_back);
        tv.setVisibility(View.GONE);
    }


    @SuppressLint("NewApi")
    protected void setNextTvBG(Drawable drawable) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setBackground(drawable);
    }

    protected void setNextImage(int id) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.GONE);
        ImageView iv = rootView.findViewById(R.id.next_img);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(id);
    }

    protected void setNextSearchImage(int id) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.GONE);
        ImageView iv = rootView.findViewById(R.id.searh_next_img);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(id);
    }

    protected void setTitleOnClick(View.OnClickListener onClick) {
        TextView tv = rootView.findViewById(R.id.title_tv);
        tv.setOnClickListener(onClick);
    }

    protected void setNextOnClick(View.OnClickListener onClick) {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setOnClickListener(onClick);
        View iv = rootView.findViewById(R.id.next_img);
        iv.setOnClickListener(onClick);
    }

    protected void setNextSearchOnClick(View.OnClickListener onClick) {
        View iv = rootView.findViewById(R.id.searh_next_img);
        iv.setOnClickListener(onClick);
    }

    protected void setNextGone() {
        TextView tv = rootView.findViewById(R.id.next_tv);
        tv.setVisibility(View.GONE);
        ImageView iv = rootView.findViewById(R.id.next_img);
        iv.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fixInputMethodManagerLeak(getActivity());
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
//                        if (QLog.isColorLevel()) {
//                            QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
//                        }
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }



}
