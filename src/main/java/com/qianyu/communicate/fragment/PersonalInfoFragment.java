package com.qianyu.communicate.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.HisSubActivity;
import com.qianyu.communicate.activity.MyGiftActivity;
import com.qianyu.communicate.activity.MyRoomActivity;
import com.qianyu.communicate.adapter.CircleBgAdapter;
import com.qianyu.communicate.adapter.GiftBgAdapter;
import com.qianyu.communicate.adapter.HisRoomAdapter;
import com.qianyu.communicate.adapter.TagAdapter_;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.ImpressLabel;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.views.MyRecylerView;

import com.qianyu.communicate.base.BaseFragment;
import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.flowtag.FlowTagLayout;
import net.neiquan.applibrary.flowtag.OnTagSelectListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class PersonalInfoFragment extends BaseFragment {
    @InjectView(R.id.mScrollView)
    ScrollView mScrollView;
    @InjectView(R.id.hisRoomLL)
    LinearLayout hisRoomLL;
    @InjectView(R.id.hisRoom)
    TextView hisRoom;
    @InjectView(R.id.mRoomRecylerView)
    MyRecylerView mRoomRecylerView;
    @InjectView(R.id.friendCircleLL)
    LinearLayout friendCircleLL;
    @InjectView(R.id.friendCircle)
    TextView friendCircle;

    @InjectView(R.id.mCircleRecylerView)
    MyRecylerView mCircleRecylerView;
    @InjectView(R.id.mGiftRecylerView)
    MyRecylerView mGiftRecylerView;
    @InjectView(R.id.hisGiftLL)
    LinearLayout hisGiftLL;
    @InjectView(R.id.mCompanyDepart)
    TextView mCompanyDepart;
    @InjectView(R.id.mIntroductionTv)
    TextView mIntroductionTv;
    @InjectView(R.id.mSpecialTv)
    TextView mSpecialTv;
    @InjectView(R.id.mAgeSexTv)
    TextView mAgeSexTv;
    @InjectView(R.id.mLocationTv)
    TextView mLocationTv;
    @InjectView(R.id.mSignTv)
    TextView mSignTv;
    @InjectView(R.id.mTagFlowLayout)
    FlowTagLayout mTagFlowLayout;
    private TagAdapter_<String> tagAdapter;
    private GiftBgAdapter giftBgAdapter;
    private CircleBgAdapter circleBgAdapter;
    private int userId;
    private HisRoomAdapter hisRoomAdapter;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_personal_info;
    }

    @Override
    public void setViews() {
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", 0);
        }
        initRecylerView();
        initTagRecylerView();
        circleBgAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                Intent intent = new Intent(getActivity(), HisSubActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        giftBgAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                Intent intent = new Intent(getActivity(), MyGiftActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        hisRoomAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                Intent intent = new Intent(getActivity(), MyRoomActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        // Scroll view does not have scroll listener
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        loadPersonalInfo();
    }

    private void loadPersonalInfo() {
        User user = MyApplication.getInstance().user;
        //userId  某个人的userId
    }

    @OnClick({R.id.friendCircleLL, R.id.hisGiftLL, R.id.hisRoomLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.friendCircleLL:
                Intent intent = new Intent(getActivity(), HisSubActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                break;
            case R.id.hisGiftLL:
                Intent intent1 = new Intent(getActivity(), MyGiftActivity.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
                break;
            case R.id.hisRoomLL:
                Intent intent2 = new Intent(getActivity(), MyRoomActivity.class);
                intent2.putExtra("userId", userId);
                startActivity(intent2);
                break;
        }
    }

    private void initRecylerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCircleRecylerView.setLayoutManager(layoutManager);
        circleBgAdapter = new CircleBgAdapter(getActivity(), null);
        mCircleRecylerView.setAdapter(circleBgAdapter);
        mCircleRecylerView.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager2 =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mGiftRecylerView.setLayoutManager(layoutManager2);
        giftBgAdapter = new GiftBgAdapter(getActivity(), null);
        mGiftRecylerView.setAdapter(giftBgAdapter);
        mGiftRecylerView.setNestedScrollingEnabled(false);

        StaggeredGridLayoutManager layoutManager3 = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager3.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRoomRecylerView.setLayoutManager(layoutManager3);
        mRoomRecylerView.setNestedScrollingEnabled(false);
        hisRoomAdapter = new HisRoomAdapter(getActivity(), null);
        mRoomRecylerView.setAdapter(hisRoomAdapter);
    }

    private void initTagRecylerView() {
        mTagFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tagAdapter = new TagAdapter_<String>(getActivity());
        mTagFlowLayout.setAdapter(tagAdapter);
        mTagFlowLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//                startActivity(new Intent(getActivity(), ImpressTagActivity.class));

//                for (int i = 0; i < selectedList.size(); i++) {
//                    AppLog.e("==========setOnTagSelectListener===========" + selectedList.get(i));
//                    Search data = ((Search) parent.getAdapter().getItem(selectedList.get(i)));
//                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
//                    intent.putExtra("search", data.getTitle());
//                    startActivity(intent);
//                }
            }
        });
    }

}
