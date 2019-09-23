package com.qianyu.communicate.fragment;

import com.qianyu.communicate.adapter.HomeNearAdapter;
import com.qianyu.communicate.base.BaseListFragment;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FirendNear;
import com.qianyu.communicate.entity.FriendSelect;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.LocationHelper;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by JavaDog on 2019-3-7.
 */

public class FriendNearFragment extends BaseListFragment {
    private LocationHelper locationHelper;
    private double lat;
    private double lng;
    private FriendSelect friendSelect=new FriendSelect();
    private HomeNearAdapter homeNearAdapter;

    @Override
    protected boolean isHaveHead() {
        return false;
    }

    @Override
    protected void setHeadViews() {
        EventBus.getDefault().register(this);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.FRIEND_SELECT) {
            friendSelect = (FriendSelect) event.getMessage();
            onRefresh();
        }
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        homeNearAdapter = new HomeNearAdapter(getActivity(), null);
        return homeNearAdapter;
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        locationHelper = LocationHelper.getInstance();
        if (locationHelper != null) {
            locationHelper.setCallBack(new LocationHelper.LocationCallBack() {
                @Override
                public void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province) {
                    FriendNearFragment.this.lat = lat;
                    FriendNearFragment.this.lng = lng;
                    homeNearAdapter.setLatLng(lat,lng);
                    loadDatas();
                }
            });
            locationHelper.start();
        }
        homeNearAdapter.setFriendFamily(true,null,"","","");
        loadDatas();
    }

    private void loadDatas() {
        NetUtils.getInstance().nearBy("" + lng, "" + lat,
                friendSelect.getMinage(), friendSelect.getMaxage(), friendSelect.getConstellationId(),
                friendSelect.getActivetime(), friendSelect.getSex(), "" + pageNum, "" + PAEG_SIZE, DeviceUtils.getDeviceId(getActivity()),new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        FirendNear firendNear = (FirendNear) model.getModel();
                        if (firendNear != null) {
                            List<FirendNear.ContentBean> list = firendNear.getContent();
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
                        if (mRecyclerview != null && mRefeshLy != null) {
                            mRecyclerview.loadMoreComplete();
                            mRecyclerview.refreshComplete();
                            mRefeshLy.hideAll();
                            mRefeshLy.showFailView();
                        }
                    }
                }, FirendNear.class);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationHelper != null) {
            locationHelper.stop();
        }
        EventBus.getDefault().unregister(this);
    }
}
