package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.view.View;

import com.qianyu.communicate.activity.GiftDetailActivity;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.adapter.GiftFuBowAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.GiftList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by JavaDog on 2019-3-12.
 */

public class GiftFragment extends BaseListFragment {

    private int gift;
    private long userId;
    private String phone;

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {

    }

    @Override
    protected MyBaseAdapter getAdapter() {
        return new GiftFuBowAdapter(getActivity(), null);
    }

    @Override
    protected int getLineNum() {
        return 2;
    }

    @Override
    public void initData() {
        PAEG_SIZE = 100;
        if (getArguments() != null) {
            gift = getArguments().getInt("gift");
            userId = getArguments().getLong("userId", 0);
            phone = getArguments().getString("phone");
        }
        loadDatas();
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, final List data, final int position) {
                final GiftList.ContentBean contentBean = (GiftList.ContentBean) data.get(position);
                if (contentBean.getGiftType() == 3) {
                    Intent intent = new Intent(getActivity(), GiftDetailActivity.class);
                    intent.putExtra("gift", ((GiftList.ContentBean) data.get(position)));
                    intent.putExtra("userId", userId);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        ((GiftList.ContentBean) data.get(i)).setSelected(false);
                    }
                    ((GiftList.ContentBean) data.get(position)).setSelected(true);
                    adapter.notifyDataSetChanged();
                    User user = MyApplication.getInstance().user;
                    if (user == null) {
                        ToastUtil.shortShowToast("请先登录...");
                        startActivity(new Intent(getActivity(), RegistActivity.class));
                        return;
                    }
                    NetUtils.getInstance().sendGift(userId, ((GiftList.ContentBean) data.get(position)).getGiftId(), 1, user.getUserId(), DeviceUtils.getDeviceId(getActivity()), new NetCallBack() {
                        @Override
                        public void onSuccess(String result, String msg, ResultModel model) {
                            ToastUtil.shortShowToast(msg);
                            Tools.dismissWaitDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONObject data = jsonObject.getJSONObject("data");
                                long sendExp = data.getLong("sendExp");
                                long sendPlut = data.getLong("sendPlut");
                                long accCharm = data.getLong("accCharm");
                                Tools.sendGift(phone, contentBean.getGiftType(), contentBean.getGiftPrice() + "", contentBean.getGiftName(), contentBean.getGiftUrl(), "1", sendExp + "", sendPlut + "", accCharm + "");
                                // 通知activity刷新金币
                                EventBuss event = new EventBuss(EventBuss.GIFT_DATA);
                                EventBus.getDefault().post(event);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(String code, String msg, String result) {
                            ToastUtil.shortShowToast(msg);
                            Tools.dismissWaitDialog();
                        }
                    }, null);
                }
            }
        });
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
        Tools.showDialog(getActivity());
        NetUtils.getInstance().giftList(gift, pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Tools.dismissWaitDialog();
                GiftList giftList = (GiftList) model.getModel();
                if (giftList != null) {
//                    giftNums(giftList);
                    List<GiftList.ContentBean> list = giftList.getContent();
                    if (mRecyclerview != null && mRefeshLy != null && adapter != null) {
                        mRecyclerview.loadMoreComplete();
                        mRecyclerview.refreshComplete();
                        mRefeshLy.hideAll();
                        if (pageNum == 0) {
                            adapter.clear();
                        }
                        adapter.append(list);
                        if (list == null || list.size() < PAEG_SIZE) {
                            if (pageNum == 0 && (list == null || list.size() == 0)) {
                                mRefeshLy.showEmptyView();
                            }
                            mRecyclerview.setLoadingMoreEnabled(false);
                        } else {
                            mRecyclerview.setLoadingMoreEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
                if (mRecyclerview != null && mRefeshLy != null) {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
                    mRefeshLy.showFailView();
                }
            }
        }, GiftList.class);
    }

    private void giftNums(GiftList giftList) {
        ArrayList<GiftList.SouvenirNosBean> souvenirNosBeans = new ArrayList<>();
        GiftList.SouvenirNosBean souvenirNosBean0 = new GiftList.SouvenirNosBean();
        souvenirNosBean0.setMeaning("一生一世");
        souvenirNosBean0.setQuantity(1314);
        souvenirNosBeans.add(souvenirNosBean0);
        GiftList.SouvenirNosBean souvenirNosBean1 = new GiftList.SouvenirNosBean();
        souvenirNosBean1.setMeaning("天长地久");
        souvenirNosBean1.setQuantity(999);
        souvenirNosBeans.add(souvenirNosBean1);
        GiftList.SouvenirNosBean souvenirNosBean2 = new GiftList.SouvenirNosBean();
        souvenirNosBean2.setMeaning("我爱你");
        souvenirNosBean2.setQuantity(520);
        souvenirNosBeans.add(souvenirNosBean2);
        GiftList.SouvenirNosBean souvenirNosBean3 = new GiftList.SouvenirNosBean();
        souvenirNosBean3.setMeaning("要抱抱");
        souvenirNosBean3.setQuantity(188);
        souvenirNosBeans.add(souvenirNosBean3);
        GiftList.SouvenirNosBean souvenirNosBean4 = new GiftList.SouvenirNosBean();
        souvenirNosBean4.setMeaning("亲亲");
        souvenirNosBean4.setQuantity(77);
        souvenirNosBeans.add(souvenirNosBean4);
        GiftList.SouvenirNosBean souvenirNosBean5 = new GiftList.SouvenirNosBean();
        souvenirNosBean5.setMeaning("十全十美");
        souvenirNosBean5.setQuantity(10);
        souvenirNosBeans.add(souvenirNosBean5);
        GiftList.SouvenirNosBean souvenirNosBean6 = new GiftList.SouvenirNosBean();
        souvenirNosBean6.setMeaning("一见钟情");
        souvenirNosBean6.setQuantity(1);
        souvenirNosBeans.add(souvenirNosBean6);
        giftList.setSouvenirNos(souvenirNosBeans);
        EventBuss event = new EventBuss(EventBuss.GIFT_DATA);
        event.setMessage(giftList);
        EventBus.getDefault().post(event);
    }
}
