package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.WalletAdapter;
import com.qianyu.communicate.alipay.AliPayUtil;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.AliPay;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.RechargeList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.wxpay.WXPayHelper;
import com.qianyu.communicate.wxpay.WxPay;

import net.neiquan.applibrary.wight.RefreshLayout;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class WalletActivity extends BaseActivity implements RefreshLayout.RetryListener {
    @InjectView(R.id.back_img)
    ImageView backImg;
    @InjectView(R.id.back_tv)
    TextView backTv;
    @InjectView(R.id.ly_back)
    LinearLayout lyBack;
    @InjectView(R.id.title_tv)
    TextView titleTv;
    @InjectView(R.id.next_tv)
    TextView nextTv;
    @InjectView(R.id.next_img)
    ImageView nextImg;
    @InjectView(R.id.searh_next_img)
    ImageView searhNextImg;
    @InjectView(R.id.head_view)
    RelativeLayout headView;
    @InjectView(R.id.coinRemain)
    TextView coinRemain;
    @InjectView(R.id.coinFirst)
    ImageView coinFirst;
    @InjectView(R.id.refesh_ly)
    RefreshLayout mRefeshLy;
    @InjectView(R.id.mRecylerView)
    XRecyclerView mRecylerView;
    @InjectView(R.id.troubleHint)
    TextView troubleHint;
    @InjectView(R.id.aliPayChosen)
    ImageView aliPayChosen;
    @InjectView(R.id.aliPayFL)
    FrameLayout aliPayFL;
    @InjectView(R.id.wxPayChosen)
    ImageView wxPayChosen;
    @InjectView(R.id.wxPayFL)
    FrameLayout wxPayFL;
    private RechargeList chargeBottomBean;
    private WalletAdapter adapter;
    private TextView mCoinNum;
    private TextView mCoinPay;
    private LinearLayout coinHeadLL;
    private ImageView chosenLogo;
    private TextView mCoinEffect;
    private RechargeList rechargeBean;

    @Override
    public int getRootViewId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        setTitleTv("钱包");
        setNextTv("收支明细");
//        Spanned spanned = Html.fromHtml("充值如遇到问题请<font color='#882f17'><u>联系客服</u></font>");
//        troubleHint.setText(spanned);
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getInstance().isLogin()) {
                    startActivity(new Intent(WalletActivity.this, BillActivity_.class));
                } else {
                    ToastUtil.shortShowToast("未登录,请先登录...");
                    startActivity(new Intent(WalletActivity.this, RegistActivity.class));
                }
            }
        });
        initRecylerView();
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

    private void initRecylerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRefeshLy.setRetryListener(this);
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.setLoadingMoreEnabled(false);
        mRecylerView.setPullRefreshEnabled(false);
        mRecylerView.hideFootView();
        adapter = new WalletAdapter(this, null);
        mRecylerView.setAdapter(adapter);
        View footView = LayoutInflater.from(this).inflate(R.layout.layout_coin_head, null);
        coinHeadLL = footView.findViewById(R.id.coinHeadLL);
        chosenLogo = footView.findViewById(R.id.chosenLogo);
        mCoinNum = footView.findViewById(R.id.mCoinNum);
        mCoinPay = footView.findViewById(R.id.mCoinPay);
        mCoinEffect = footView.findViewById(R.id.mCoinEffect);
        coinHeadLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RechargeList> data = adapter.data;
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setSelected(false);
                    adapter.notifyDataSetChanged();
                }
                chargeBottomBean.setSelected(true);
                rechargeBean = chargeBottomBean;
                coinHeadLL.setBackground(getResources().getDrawable(chargeBottomBean.isSelected() ? R.drawable.shape_cornor_line : R.drawable.shape_cornor_white));
                chosenLogo.setVisibility(chargeBottomBean.isSelected() ? View.VISIBLE : View.GONE);
            }
        });
        mRecylerView.addFootView(footView);
    }

    @Override
    public void initData() {
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                for (int i = 0; i < data.size(); i++) {
                    ((RechargeList) data.get(i)).setSelected(false);
                }
                ((RechargeList) data.get(position)).setSelected(true);
                rechargeBean = ((RechargeList) data.get(position));
                adapter.notifyDataSetChanged();
                chargeBottomBean.setSelected(false);
                coinHeadLL.setBackground(getResources().getDrawable(chargeBottomBean.isSelected() ? R.drawable.shape_cornor_line : R.drawable.shape_cornor_white));
                chosenLogo.setVisibility(chargeBottomBean.isSelected() ? View.VISIBLE : View.GONE);
            }
        });
        reminMoney();
    }

    private void reminMoney() {
        if (MyApplication.getInstance().isLogin()) {
            User user = MyApplication.getInstance().user;
            NetUtils.getInstance().mineInfo(user.getUserId(), new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    PersonalInfo personalInfo = (PersonalInfo) model.getModel();
                    if (personalInfo != null) {
                        coinRemain.setText(NumberUtils.rounLong(personalInfo.getDiamond()));
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, PersonalInfo.class);
        }
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
            Tools.showDialog(this);
            NetUtils.getInstance().productList(1,new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    Tools.dismissWaitDialog();
                    List<RechargeList> list = (List<RechargeList>) model.getModelList();
                    mRefeshLy.hideAll();
                    if (list != null && list.size() > 0) {
                        coinHeadLL.setVisibility(View.VISIBLE);
                        chargeBottomBean = list.get(list.size() - 1);
                        mCoinNum.setText(TextUtils.isEmpty(chargeBottomBean.getProductName()) ? "" : chargeBottomBean.getProductName());
                        mCoinPay.setText("售价" + chargeBottomBean.getProductPrice() + "元");
                        mCoinEffect.setText(TextUtils.isEmpty(chargeBottomBean.getProductDescribe()) ? "" : chargeBottomBean.getProductDescribe());
                        list.remove(list.size() - 1);
                    } else {
                        coinHeadLL.setVisibility(View.GONE);
                        mRefeshLy.showEmptyView();
                    }
                    adapter.appendAll(list);
                }

                @Override
                public void onFail(String code, String msg, String result) {
                    ToastUtil.shortShowToast(msg);
                    Tools.dismissWaitDialog();
                    mRefeshLy.hideAll();
                    mRefeshLy.showFailView();
                    coinHeadLL.setVisibility(View.GONE);
                }
            }, RechargeList.class);
        }
    }

    @OnClick({R.id.troubleHint, R.id.aliPayFL, R.id.wxPayFL})
    public void onViewClicked(View view) {
        User user = MyApplication.getInstance().user;
        switch (view.getId()) {
            case R.id.troubleHint:

                break;
            case R.id.aliPayFL:
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(WalletActivity.this, RegistActivity.class));
                    return;
                }
                if (rechargeBean == null) {
                    ToastUtil.shortShowToast("请先选择充值金额...");
                    return;
                }
                changePayBg(true);
                NetUtils.getInstance().aliPay(rechargeBean.getProductId() , 1,Tools.getIPAddress(WalletActivity.this),user.getUserId(), DeviceUtils.getDeviceId(WalletActivity.this), new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        AliPay aliPay = (AliPay) model.getModel();
                        if (aliPay != null && !TextUtils.isEmpty(aliPay.getBody())) {
                            new AliPayUtil(WalletActivity.this, new AliPayUtil.AliPayCallBack() {
                                @Override
                                public void success() {
                                    EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
                                    loadDatas();
                                }

                                @Override
                                public void falure(String message) {

                                }
                            }).payV2(aliPay.getBody());
//                            new AliPayUtil(WalletActivity.this).payV2_();
                        }
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                }, AliPay.class);
                break;
            case R.id.wxPayFL:
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(WalletActivity.this, RegistActivity.class));
                    return;
                }
                if (rechargeBean == null) {
                    ToastUtil.shortShowToast("请先选择充值金额...");
                    return;
                }
                changePayBg(false);
                NetUtils.getInstance().wxPay(rechargeBean.getProductId(), 1, Tools.getIPAddress(WalletActivity.this), user.getUserId(), DeviceUtils.getDeviceId(WalletActivity.this), new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        WxPay wxPay = (WxPay) model.getModel();
                        if (wxPay != null) {
                            WXPayHelper.getInstance(WalletActivity.this, new WXPayHelper.WXPayCallBack() {
                                @Override
                                public void success() {
                                    //没走回调
                                    EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
                                    loadDatas();
                                }

                                @Override
                                public void falure(String message) {

                                }
                            }).directPay(wxPay);
                        }
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                }, WxPay.class);
                break;
        }
    }

    private void changePayBg(boolean flag) {
        aliPayFL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_white));
        aliPayChosen.setVisibility(View.GONE);
        wxPayFL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_white));
        wxPayChosen.setVisibility(View.GONE);
        if (flag) {
            aliPayFL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_line));
            aliPayChosen.setVisibility(View.VISIBLE);
        } else {
            wxPayFL.setBackground(getResources().getDrawable(R.drawable.shape_cornor_line));
            wxPayChosen.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    public void onRetryClick() {
        loadDatas();
    }
}
