package com.qianyu.communicate.base;


import android.view.ViewGroup;
import android.view.ViewParent;
import net.neiquan.applibrary.R;
import net.neiquan.applibrary.wight.ProgressWebView;

/**
 * Created by dyj on 2016/8/29.
 */
public  class BaseWebFragment extends BaseFragment{

    protected ProgressWebView mWebView;

    @Override
    public int getRootViewId() {
        return R.layout.activity_web;
    }

    @Override
    public void setViews() {
        // mLoadingView = findViewById(R.id.baseweb_loading_indicator);
        mWebView = rootView.findViewById(R.id.baseweb_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        initData();
        setTitleTv("链接");
        String url = getArguments().getString("url");
//        mWebView.setActivity(getActivity());
        if(url!=null){
            mWebView.loadUrl(url);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再 // destory()
        ViewParent parent = mWebView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mWebView);
        }
        mWebView.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearHistory();
        mWebView.clearView();
        mWebView.removeAllViews();
        mWebView.destroy();
        super.onDestroy();
    }

}
