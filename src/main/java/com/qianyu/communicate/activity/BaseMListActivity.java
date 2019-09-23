package com.qianyu.communicate.activity;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyMBaseAdapter;

import net.neiquan.applibrary.wight.RefreshLayout;

import java.lang.reflect.Field;

public abstract class BaseMListActivity extends BaseActivity implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {
    protected int pageNum = 0;
    protected static int PAEG_SIZE = 10;
    protected static int PAEG_SIZE_16 = 16;
    protected View view;
    protected MyMBaseAdapter adapter;
    protected XRecyclerView mRecyclerview;
    protected RelativeLayout mHeadView;
    protected RefreshLayout mRefeshLy;
    protected ImageView mFloatActionBtn;
    protected CardView deleteCardView;

    @Override
    public int getRootViewId() {
        return net.neiquan.applibrary.R.layout.base_common_xlistview;
    }

    @Override
    public void setViews() {
        mRecyclerview = findViewById(net.neiquan.applibrary.R.id.recyclerview);
        mHeadView = findViewById(net.neiquan.applibrary.R.id.head_view);
        mRefeshLy = findViewById(net.neiquan.applibrary.R.id.refesh_ly);
        mFloatActionBtn = findViewById(net.neiquan.applibrary.R.id.mFloatActionBtn);
        deleteCardView = findViewById(net.neiquan.applibrary.R.id.deleteCardView);
        setAdapter();
    }


    private void setAdapter() {
        setHeadView(isHaveHead());
        setHeadViews();
        adapter = getAdapter();
        mRecyclerview.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getLineNum() == 0 ? 1 : getLineNum(),
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(layoutManager);
        mRefeshLy.setRetryListener(this);

        mRecyclerview.setLoadingListener(this);
        mRecyclerview.setLoadingMoreEnabled(false);
        mRecyclerview.setPullRefreshEnabled(true);
    }

    protected abstract boolean isHaveHead();

    /**
     * 当返回为true时候设置
     */
    protected abstract void setHeadViews();

    private void setHeadView(boolean isHaveHead) {
        if (!isHaveHead) {
            mHeadView.setVisibility(View.GONE);
        } else {
            mHeadView.setVisibility(View.VISIBLE);
        }
    }

    protected abstract MyMBaseAdapter getAdapter();

    protected abstract int getLineNum();

    public void setmFloatActionBtn(int id) {
        mFloatActionBtn.setVisibility(View.VISIBLE);
        mFloatActionBtn.setImageResource(id);
    }

    public void setFloatActionBtnOnClick(View.OnClickListener onClick) {
        mFloatActionBtn.setOnClickListener(onClick);
    }

    public void setDeleteBtnVisible(int visible) {
        deleteCardView.setVisibility(visible);
    }

    public void setDeleteBtnOnClick(View.OnClickListener onClick) {
        deleteCardView.setOnClickListener(onClick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fixInputMethodManagerLeak(this);
        mRecyclerview = null;
        adapter = null;
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
