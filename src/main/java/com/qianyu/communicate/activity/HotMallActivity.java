package com.qianyu.communicate.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FamilyManageAdapter;
import com.qianyu.communicate.adapter.HotMallAdapter;
import com.qianyu.communicate.adapter.WalletAdapter;
import com.qianyu.communicate.alipay.AliPayUtil;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.AliPay;
import com.qianyu.communicate.entity.FamilyManage;
import com.qianyu.communicate.entity.RechargeList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.wxpay.WXPayHelper;
import com.qianyu.communicate.wxpay.WxPay;
import com.reyun.sdk.ReYunGame;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.applibrary.wight.AlertChooser;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by JavaDog on 2019-5-31.
 */

public class HotMallActivity extends BaseListActivity {
    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("热销商品");
        setNextTv("账单");
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isLogin()) {
                    startActivity(new Intent(HotMallActivity.this, BillActivity_.class));
                } else {
                    ToastUtil.shortShowToast("未登录,请先登录...");
                    startActivity(new Intent(HotMallActivity.this, RegistActivity.class));
                }
            }
        });
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new HotMallAdapter(this, null);
    }

    @Override
    protected int getLineNum() {
        return 3;
    }

    @Override
    public void initData() {
        setResult(101);
        mRecyclerview.setLoadingMoreEnabled(false);
        mRecyclerview.setPullRefreshEnabled(false);
        mRecyclerview.hideFootView();
        mRecyclerview.setBackgroundColor(getResources().getColor(R.color.mall_bg));
        mRecyclerview.addItemDecoration(new SpacesItemDecoration(20));
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                final RechargeList rechargeBean = (RechargeList) data.get(position);
                if (rechargeBean.getProductType() == 1) {
                    rechargeDiamond(rechargeBean);
                } else {
                    showMallPopWindow(view, rechargeBean);
                }
            }
        });
        loadDatas();
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
            Tools.showDialog(this);
            NetUtils.getInstance().productList(2,new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    Tools.dismissWaitDialog();
                    List<RechargeList> list = (List<RechargeList>) model.getModelList();
                    mRefeshLy.hideAll();
                    if (list == null && list.size() <= 0) {
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
                }
            }, RechargeList.class);
        }
    }

    @Override
    public void onRetryClick() {
        loadDatas();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    private void rechargeDiamond(final RechargeList rechargeBean) {
        final User user = MyApplication.getInstance().user;
        new AlertChooser.Builder(HotMallActivity.this).setTitle("选择充值方式").addItem("微信", new AlertChooser.OnItemClickListener() {
            @Override
            public void OnItemClick(AlertChooser chooser) {
                chooser.dismiss();
                NetUtils.getInstance().wxPay(rechargeBean.getProductId(), 1, Tools.getIPAddress(HotMallActivity.this), user.getUserId(), DeviceUtils.getDeviceId(HotMallActivity.this), new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        WxPay wxPay = (WxPay) model.getModel();
                        if (wxPay != null) {
                            WXPayHelper.getInstance(HotMallActivity.this, new WXPayHelper.WXPayCallBack() {
                                @Override
                                public void success() {
                                    //没走回调
                                    EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
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
            }
        }).addItem("支付宝", new AlertChooser.OnItemClickListener() {
            @Override
            public void OnItemClick(AlertChooser chooser) {
                chooser.dismiss();
                NetUtils.getInstance().aliPay(rechargeBean.getProductId(), 1, Tools.getIPAddress(HotMallActivity.this), user.getUserId(), DeviceUtils.getDeviceId(HotMallActivity.this), new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        AliPay aliPay = (AliPay) model.getModel();
                        if (aliPay != null && !TextUtils.isEmpty(aliPay.getBody())) {
                            new AliPayUtil(HotMallActivity.this, new AliPayUtil.AliPayCallBack() {
                                @Override
                                public void success() {
                                    EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
//                                    ReYunGame.setPayment(rechargeBean.getProductId()+"", ReYunGame.PaymentType.valueOf("zhifubao")+"", "CNY", (float) rechargeBean.getProductPrice(),
//                                            rechargeBean.getReward(), "diamond" ,user.getLevel(),rechargeBean.getReward());
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
            }
        }).setNegativeItem("取消", new AlertChooser.OnItemClickListener() {
            @Override
            public void OnItemClick(AlertChooser chooser) {
                chooser.dismiss();
            }
        }).show();
    }

    private void showMallPopWindow(View view, final RechargeList rechargesBean) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.hot_mall_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        ImageView mMallReduce = view.findViewById(R.id.mMallReduce);
                        ImageView mMallAdd = view.findViewById(R.id.mMallAdd);
                        ImageView mMallImg = view.findViewById(R.id.mMallImg);
                        ImageUtil.loadPicNet(rechargesBean.getPicPath(), mMallImg);
                        TextView mMallName = view.findViewById(R.id.mMallName);
                        mMallName.setText(TextUtils.isEmpty(rechargesBean.getProductName()) ? "" : rechargesBean.getProductName());
                        TextView mMallDetail = view.findViewById(R.id.mMallDetail);
                        mMallDetail.setText(TextUtils.isEmpty(rechargesBean.getProductDescribe()) ? "" : rechargesBean.getProductDescribe());
                        final EditText mMallCount = view.findViewById(R.id.mMallCount);
                        mMallCount.requestFocus();
                        final TextView mMallCost = view.findViewById(R.id.mMallCost);
                        mMallCost.setText("共计花费 " + rechargesBean.getProductPrice() + " 钻石");
                        TextView cancelTv = view.findViewById(R.id.cancelTv);
                        TextView sureTv = view.findViewById(R.id.sureTv);
                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        sureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String str = mMallCount.getText().toString().trim();
                                if (TextUtils.isEmpty(str)) {
                                    ToastUtil.shortShowToast("请先输入商品数量...");
                                } else {
                                    int count = Integer.parseInt(str);
                                    if (count <= 0) {
                                        ToastUtil.shortShowToast("请先输入商品数量...");
                                        return;
                                    }
                                    User user = MyApplication.getInstance().user;
                                    NetUtils.getInstance().buyShop(rechargesBean.getProductId(), count, user.getUserId(), DeviceUtils.getDeviceId(HotMallActivity.this),new NetCallBack() {
                                        @Override
                                        public void onSuccess(String result, String msg, ResultModel model) {
                                            popupWindow.dismiss();
                                            ToastUtil.shortShowToast(msg);
                                            EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
                                        }

                                        @Override
                                        public void onFail(String code, String msg, String result) {
                                            popupWindow.dismiss();
                                            ToastUtil.shortShowToast(msg);
                                        }
                                    }, null);
                                }
                            }
                        });
                        mMallReduce.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String str = mMallCount.getText().toString().trim();
                                if (TextUtils.isEmpty(str)) {
                                    ToastUtil.shortShowToast("请先输入商品数量...");
                                } else {
                                    int count = Integer.parseInt(str);
                                    mMallCount.setText(count <= 0 ? "0" : ((count - 1) + ""));
                                }
                            }
                        });
                        mMallAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String str = mMallCount.getText().toString().trim();
                                if (TextUtils.isEmpty(str)) {
                                    mMallCount.setText("1");
                                } else {
                                    mMallCount.setText((Integer.parseInt(str) + 1) + "");
                                }
                            }
                        });
                        mMallCount.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String str = mMallCount.getText().toString().trim();
                                if (TextUtils.isEmpty(str)) {
                                    mMallCost.setText("共计花费0钻石");
                                } else {
                                    mMallCost.setText("共计花费 " + NumberUtils.rounDouble(Integer.parseInt(str) * rechargesBean.getProductPrice()) + " 钻石");
                                }
                                mMallCount.setSelection(str.length());
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
//            if (parent.getChildPosition(view) == 0)
//                outRect.top = space;
        }
    }
}
