package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.GiftFuBowAdapter;
import com.qianyu.communicate.adapter.GiftNumAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.GiftList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.utils.SpringUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.applibrary.wight.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class GiftActivity extends BaseActivity implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {
    @InjectView(R.id.remainLogo)
    ImageView remainLogo;
    @InjectView(R.id.remainMoney)
    TextView remainMoney;
    @InjectView(R.id.addNum)
    ImageView addNum;
    @InjectView(R.id.sendNum)
    TextView sendNum;
    @InjectView(R.id.sendGift)
    TextView sendGift;
    @InjectView(R.id.mGiftLL)
    LinearLayout mGiftLL;
    @InjectView(R.id.recyclerview)
    XRecyclerView mRecyclerview;
    @InjectView(R.id.refesh_ly)
    RefreshLayout mRefeshLy;
    @InjectView(R.id.head_view)
    RelativeLayout headView;
    @InjectView(R.id.mRechargeLL)
    LinearLayout mRechargeLL;
    private GiftFuBowAdapter adapter;
    protected int pageNum = 0;
    protected static int PAEG_SIZE = 100;
    private List<GiftList.SouvenirNosBean> souvenirNos;
    private GiftNumAdapter oderPopAdapter;
    private long noId = 1;
    private long userId;

    @Override
    public int getRootViewId() {
        setSystemBarTint_();
        return R.layout.activity_gift;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        setTitleTv("礼物");
//        setNextTv("礼物说明");
        headView.setBackground(getResources().getDrawable(R.drawable.toolbar_bg_bmp));
        setAdapter();
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.MINE_TAB) {
           loadDatas();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            userId = getIntent().getIntExtra("userId", 0);
        }
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                for (int i = 0; i < data.size(); i++) {
                    ((GiftList.ContentBean) data.get(i)).setSelected(false);
                }
                ((GiftList.ContentBean) data.get(position)).setSelected(true);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.addNum, R.id.sendNum, R.id.sendGift, R.id.mGiftLL, R.id.mRechargeLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addNum:
                break;
            case R.id.mGiftLL:
                showGiftNumPopWindow();
                break;
            case R.id.sendGift:
                sendGift();
                break;
            case R.id.mRechargeLL:
                SpringUtils.springAnim(addNum);
                startActivity(new Intent(GiftActivity.this, WalletActivity.class));
                break;
        }
    }

    @Override
    public void onRetryClick() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        loadDatas();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        loadDatas();
    }

    private void loadDatas() {
//        Tools.showDialog(this);
        User user = MyApplication.getInstance().user;
    }

    private void sendGift() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(GiftActivity.this, RegistActivity.class));
            return;
        }
        List<GiftList.ContentBean> data = adapter.data;
        long gid = 0;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                GiftList.ContentBean goodSBean = data.get(i);
                if (goodSBean.isSelected()) {
                    gid = goodSBean.getGiftId();
                }
            }
            if (gid <= 0) {
                ToastUtil.shortShowToast("请先选择礼物...");
                return;
            }
            //passivityUserId  被送礼物人的id
//            Tools.showDialog(GiftActivity.this);
        } else {
            ToastUtil.shortShowToast("暂无礼物...");
        }
    }

    private void showGiftNumPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.gift_num_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1.0f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RecyclerView mRecylerView = view.findViewById(R.id.mRecylerView);
                        initRecylerView(mRecylerView);
                        oderPopAdapter = new GiftNumAdapter(GiftActivity.this, null);
                        mRecylerView.setAdapter(oderPopAdapter);
                        oderPopAdapter.appendAll(souvenirNos);
                        oderPopAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, List data, int position) {
                                GiftList.SouvenirNosBean souvenirNosBean = (GiftList.SouvenirNosBean) data.get(position);
                                noId=souvenirNosBean.getId();
                                sendNum.setText("" + souvenirNosBean.getQuantity());
                                popupWindow.dismiss();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
//        popupWindow.showAsDropDown(mGiftLL,0, 10);
        int[] location = new int[2];
        mGiftLL.getLocationOnScreen(location);
        popupWindow.showAtLocation(mGiftLL, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight() - 40);
    }

    private void initRecylerView(RecyclerView mRecylerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(layoutManager);
    }

    private void setAdapter() {
        adapter = new GiftFuBowAdapter(this, null);
        mRecyclerview.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
