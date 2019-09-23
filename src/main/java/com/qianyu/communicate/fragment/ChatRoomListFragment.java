package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.CityChooseActivity;
import com.qianyu.communicate.activity.FamilyCretaeActivity;
import com.qianyu.communicate.activity.MyEventRecordActivity;
import com.qianyu.communicate.activity.MyFamilyActivity;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.activity.RoomSearchActivity;
import com.qianyu.communicate.activity.SkillDetailActivity;
import com.qianyu.communicate.activity.WatchHistoryActivity;
import com.qianyu.communicate.adapter.ChatRoomOtherAdapter;
import com.qianyu.communicate.adapter.ChatRoomSuggestAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseFragment;
import com.qianyu.communicate.base.BaseWebActivity;
import com.qianyu.communicate.base.BaseWebActivity_;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.BannerList;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserGroups;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.StatusBarUtil;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.banner.Banner;
import com.qianyu.communicate.views.banner.listener.OnBannerClickListener;
import com.qianyu.communicate.views.banner.loader.FrescoImageLoader;

import net.neiquan.applibrary.wight.RefreshLayout;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.APPURL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class ChatRoomListFragment extends BaseFragment implements RefreshLayout.RetryListener, XRecyclerView.LoadingListener {
    protected int pageNum = 0;
    protected static int PAEG_SIZE = 10;
    protected View view;
    protected MyBaseAdapter adapter;
    @InjectView(R.id.status_view)
    View statusView;
    @InjectView(R.id.recyclerview)
    XRecyclerView mRecyclerview;
    @InjectView(R.id.refesh_ly)
    RefreshLayout mRefeshLy;
    @InjectView(R.id.mLocationTv)
    TextView mLocationTv;
    @InjectView(R.id.mLocationLL)
    LinearLayout mLocationLL;
    @InjectView(R.id.mFloatActionBtn)
    ImageView mFloatActionBtn;
    @InjectView(R.id.mClassifyLogo)
    ImageView mClassifyLogo;
    @InjectView(R.id.mHistoryLogo)
    ImageView mHistoryLogo;
    @InjectView(R.id.mSearchFL)
    FrameLayout mSearchFL;
    @InjectView(R.id.mUnReadFL)
    FrameLayout mUnReadFL;
    @InjectView(R.id.mSearchLL)
    LinearLayout mSearchLL;
    @InjectView(R.id.mUnReadDot)
    View mUnReadDot;
    private Banner bannerView;
    private RecyclerView mSuggestRecylerView;
    private ChatRoomSuggestAdapter chatRoomSuggestAdapter;
    private List<String> bannersImg = new ArrayList<String>();
    private View headView;
    private ImmersionBar mImmersionBar;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_chat_room_list;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        int statusHeight = StatusBarUtil.getStatusHeight(getActivity());
        statusView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight));
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true);  //状态栏字体是深色，不写默认为亮色
        mImmersionBar.init();
        setAdapter();
        setHeaderViews();
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.FAMILY_REFRESH) {
            onRefresh();
        } else if (event.getState() == EventBuss.FAMILY_EXIT_ENTER) {
            EventRecord.ContentBean eventRecord = (EventRecord.ContentBean) event.getMessage();
            Tools.enterFamily(getActivity(), eventRecord.getGroupId(), false);
        } else if (event.getState() == EventBuss.MY_EVENT) {
            mUnReadDot.setVisibility(View.VISIBLE);
        } else if (event.getState() == EventBuss.MY_EVENT_READ) {
            mUnReadDot.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                final FamilyList.ContentBean contentBean = (FamilyList.ContentBean) data.get(position);
                Tools.enterFamily(getActivity(), contentBean.getGroupId(), false);
            }
        });
        loadDatas();
        loadBanners();
    }

    private void loadBanners() {
        NetUtils.getInstance().banner(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                List<BannerList> bannerLists = (List<BannerList>) model.getModelList();
                initBanner(bannerLists);
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, BannerList.class);
    }

    private void loadDatas() {
        NetUtils.getInstance().activeGroup(pageNum, PAEG_SIZE, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                FamilyList familyList = model.getModel();
                if (familyList != null) {
                    List<FamilyList.ContentBean> alllist = familyList.getContent();
                    List<FamilyList.ContentBean> list = new ArrayList<>();
                    if (alllist != null && alllist.size() > 0) {
                        if (pageNum == 0) {
                            if (chatRoomSuggestAdapter != null) {
                                chatRoomSuggestAdapter.clear();
                            }
                            if (alllist.size() > 4) {
                                for (int i = 0; i < 4; i++) {
                                    if (chatRoomSuggestAdapter != null) {
                                        chatRoomSuggestAdapter.appendSingle(alllist.get(i));
                                    }
                                }
                                for (int i = 4; i < alllist.size(); i++) {
                                    list.add(alllist.get(i));
                                }
                            } else {
                                if (chatRoomSuggestAdapter != null) {
                                    chatRoomSuggestAdapter.append(alllist);
                                }
                            }
                        } else {
                            list.addAll(alllist);
                        }
                    }
                    if (mRecyclerview != null && mRefeshLy != null && adapter != null) {
                        mRecyclerview.loadMoreComplete();
                        mRecyclerview.refreshComplete();
                        mRefeshLy.hideAll();
                        if (pageNum == 0) {
                            adapter.clear();
                        }
                        adapter.append(list);
                        if (alllist == null || alllist.size() < PAEG_SIZE) {
                            if (pageNum == 0 && (alllist == null || alllist.size() == 0)) {
                                // mRefeshLy.showEmptyView();
                            }
                            mRecyclerview.setLoadingMoreEnabled(false);
                        } else {
                            mRecyclerview.setLoadingMoreEnabled(true);
                        }
                    }
                } else {
                    mRecyclerview.loadMoreComplete();
                    mRecyclerview.refreshComplete();
                    mRefeshLy.hideAll();
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
        }, FamilyList.class);
    }

    @Override
    public void onRetryClick() {
        pageNum = 0;
        loadDatas();
        loadBanners();
    }

    @Override
    public void onRefresh() {
        pageNum = 0;
        loadDatas();
        loadBanners();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        loadDatas();
    }

    @OnClick({R.id.mClassifyLogo, R.id.mLocationLL, R.id.mFloatActionBtn, R.id.mSearchFL, R.id.mUnReadFL, R.id.mHistoryLogo, R.id.mSearchLL})
    public void onViewClicked(View view) {
        User user = MyApplication.getInstance().user;
        switch (view.getId()) {
            case R.id.mClassifyLogo:
                NetUtils.getInstance().userGroupList(new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        UserGroups userGroups = (UserGroups) model.getModel();
                        if (userGroups == null) {
                            ToastUtil.shortShowToast(msg);
                            startActivity(new Intent(getActivity(), FamilyCretaeActivity.class));
                        } else {
                            final FamilyList.ContentBean contentBean = userGroups.getGroupInfo();
                            if (contentBean != null) {
                                startActivity(new Intent(getActivity(), MyFamilyActivity.class));
                            } else {
                                startActivity(new Intent(getActivity(), FamilyCretaeActivity.class));
                            }
                        }
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                }, UserGroups.class);
                break;
            case R.id.mLocationLL:
                startActivity(new Intent(getActivity(), CityChooseActivity.class));
                break;
            case R.id.mFloatActionBtn:
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(getActivity(), RegistActivity.class));
                    return;
                }
                break;
            case R.id.mSearchFL:
                startActivity(new Intent(getActivity(), RoomSearchActivity.class));
                break;
            case R.id.mUnReadFL:
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(getActivity(), RegistActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyEventRecordActivity.class));
                break;
            case R.id.mHistoryLogo:
                startActivity(new Intent(getActivity(), WatchHistoryActivity.class));
                break;
            case R.id.mSearchLL:
                startActivity(new Intent(getActivity(), RoomSearchActivity.class));
                break;
        }
    }

    private void initBanner(final List<BannerList> banners) {
        if (banners == null || banners.size() < 0) {
            bannerView.setVisibility(View.GONE);
            return;
        }
        bannerView.setVisibility(View.VISIBLE);
        bannersImg.clear();
        for (int i = 0; i < banners.size(); i++) {
            if (!TextUtils.isEmpty(banners.get(i).getPicUrl()))
                bannersImg.add(banners.get(i).getPicUrl());
        }
        bannerView.setImages(bannersImg).setDelayTime(3000)
//                .setBannerAnimation(FlipHorizontalTransformer.class)
                .setImageLoader(new FrescoImageLoader())
                .start();
        bannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BannerList bannerList = banners.get(0);
                if (bannerList != null) {
                    if (bannerList.getType() == 1) {
                        Intent intent = new Intent(getActivity(), BaseWebActivity.class);
                        intent.putExtra("title", bannerList.getBannerName());
                        intent.putExtra("url", bannerList.getBannerUrl());
                        intent.putExtra("banner", true);
                        startActivity(intent);
                    } else if (bannerList.getType() == 2) {
                        Tools.enterFamily(getActivity(), Long.parseLong(bannerList.getBannerUrl()), false);
                    } else {
                        startActivity(new Intent(getActivity(), SkillDetailActivity.class));
                    }

                }
            }
        });
        bannerView.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerList bannerList = banners.get(position - 1);
                if (bannerList != null) {
                    if (bannerList.getType() == 1) {
                        Intent intent = new Intent(getActivity(), BaseWebActivity.class);
                        intent.putExtra("title", bannerList.getBannerName());
                        intent.putExtra("url", bannerList.getBannerUrl());
                        intent.putExtra("banner", true);
                        startActivity(intent);
                    } else if (bannerList.getType() == 2) {
                        Tools.enterFamily(getActivity(), Long.parseLong(bannerList.getBannerUrl()), false);
                    } else {
                        startActivity(new Intent(getActivity(), SkillDetailActivity.class));
                    }
                }
            }
        });
    }

    private void setAdapter() {
        adapter = new ChatRoomOtherAdapter(getActivity(), null);
        mRecyclerview.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerview.setArrowImageView(net.neiquan.applibrary.R.mipmap.iconfont_downgrey);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(layoutManager);
        mRefeshLy.setRetryListener(this);

        mRecyclerview.setLoadingListener(this);
        mRecyclerview.setLoadingMoreEnabled(false);
        mRecyclerview.setPullRefreshEnabled(true);
    }

    private void setHeaderViews() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_chat_room_head_, null);
        bannerView = headView.findViewById(R.id.mChatRoomBanner);
        LinearLayout mPreViewLL = headView.findViewById(R.id.mPreViewLL);
        LinearLayout mMasterLL = headView.findViewById(R.id.mMasterLL);
        LinearLayout mRankListLL = headView.findViewById(R.id.mRankListLL);
        LinearLayout mComplicatedLL = headView.findViewById(R.id.mComplicatedLL);
        mSuggestRecylerView = headView.findViewById(R.id.mSuggestRecylerView);
        mRecyclerview.addHeaderView(headView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mSuggestRecylerView.setLayoutManager(layoutManager);
        chatRoomSuggestAdapter = new ChatRoomSuggestAdapter(getActivity(), null);
        mSuggestRecylerView.setAdapter(chatRoomSuggestAdapter);
        final User user = MyApplication.getInstance().user;
        chatRoomSuggestAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                final FamilyList.ContentBean contentBean = (FamilyList.ContentBean) data.get(position);
                Tools.enterFamily(getActivity(), contentBean.getGroupId(), false);
            }
        });
        final Intent intent = new Intent(getActivity(), BaseWebActivity.class);
        mRankListLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BaseWebActivity_.class);
                intent.putExtra("url", APPURL.BASE_H5_URL + "gameType=10004&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(getActivity()));
                startActivity(intent);
            }
        });
        mMasterLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("title", "每日任务");
                intent.putExtra("url", APPURL.BASE_H5_URL + "gameType=10001&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(getActivity()));
                startActivity(intent);
            }
        });
        mPreViewLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtils.getInstance().userGroupList(new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        UserGroups userGroups = (UserGroups) model.getModel();
                        if (userGroups == null) {
                            ToastUtil.shortShowToast(msg);
                            startActivity(new Intent(getActivity(), FamilyCretaeActivity.class));
                        } else {
                            final FamilyList.ContentBean contentBean = userGroups.getGroupInfo();
                            if (contentBean != null) {
                                Tools.enterFamily(getActivity(), contentBean.getGroupId(), false);
                            } else {
                                startActivity(new Intent(getActivity(), FamilyCretaeActivity.class));
                            }
                        }
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                        startActivity(new Intent(getActivity(), FamilyCretaeActivity.class));
                    }
                }, UserGroups.class);
            }
        });
        mComplicatedLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("title", "排行榜");
                User user = MyApplication.getInstance().user;
                intent.putExtra("url", APPURL.BASE_H5_URL + "gameType=10002&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(getActivity()));
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

}
