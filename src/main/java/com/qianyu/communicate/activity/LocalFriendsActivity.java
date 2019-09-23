package com.qianyu.communicate.activity;

import com.qianyu.communicate.adapter.HomeNearAdapter;
import com.qianyu.communicate.base.BaseListActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.FirendNear;
import com.qianyu.communicate.entity.FriendSelect;
import com.qianyu.communicate.fragment.FriendNearFragment;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.LocationHelper;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by JavaDog on 2019-4-25.
 */

public class LocalFriendsActivity extends BaseListActivity {
    private LocationHelper locationHelper;
    private double lat;
    private double lng;
    private FriendSelect friendSelect = new FriendSelect();
    private boolean friend;
    private HomeNearAdapter homeNearAdapter;
    private FamilyList.ContentBean familyInfo;
    private String mType;
    // 话题邀请
    private String topicId;
    private String topicTitle;

    @Override
    protected boolean isHaveHead() {
        return true;
    }

    @Override
    protected void setHeadViews() {
        setTitleTv("附近的人");
    }

    @Override
    protected MyBaseAdapter getAdapter() {
        homeNearAdapter = new HomeNearAdapter(this, null);
        return homeNearAdapter;
    }

    @Override
    protected int getLineNum() {
        return 0;
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            mType = getIntent().getStringExtra("mType");
            if (mType.equals("topic")) {
                topicId = getIntent().getStringExtra("topicId");
                topicTitle = getIntent().getStringExtra("topicTitle");
            } else {
                friend = getIntent().getBooleanExtra("friend", false);
                familyInfo = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
            }
        }
        locationHelper = LocationHelper.getInstance();
        if (locationHelper != null) {
            locationHelper.setCallBack(new LocationHelper.LocationCallBack() {
                @Override
                public void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province) {
                    LocalFriendsActivity.this.lat = lat;
                    LocalFriendsActivity.this.lng = lng;
                    homeNearAdapter.setLatLng(lat, lng);
                }
            });
            locationHelper.start();
        }
        homeNearAdapter.setFriendFamily(friend, familyInfo,topicId,topicTitle,mType);
        loadDatas();
    }

    private void loadDatas() {
        NetUtils.getInstance().nearBy("" + lng, "" + lat,
                friendSelect.getMinage(), friendSelect.getMaxage(), friendSelect.getConstellationId(),
                friendSelect.getActivetime(), friendSelect.getSex(), "" + pageNum, "" + PAEG_SIZE, DeviceUtils.getDeviceId(this), new NetCallBack() {
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
