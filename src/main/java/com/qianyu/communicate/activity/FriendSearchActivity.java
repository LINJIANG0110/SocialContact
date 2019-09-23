package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FriendSearchAdapter;
import com.qianyu.communicate.adapter.HomeNearAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.SearchFriend;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.fragment.FriendNearFragment;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.LocationHelper;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JavaDog on 2019-4-18.
 */

public class FriendSearchActivity extends BaseActivity {
    @InjectView(R.id.mSearchEt)
    EditText mSearchEt;
    @InjectView(R.id.mRecylerView)
    RecyclerView mRecylerView;
    private FriendSearchAdapter adapter;
    private LocationHelper locationHelper;
    private double lat;
    private double lng;
    private boolean friend;
    private FamilyList.ContentBean familyInfo;
    List<SearchFriend.ContentBean> list;
    private String mType;
    private String topicId;
    private String topicTitle;

    @Override
    public int getRootViewId() {
        return R.layout.activity_friend_search;
    }

    @Override
    public void setViews() {
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
        setTitleTv("搜索用户");
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        adapter = new FriendSearchAdapter(this, null);
        mRecylerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, final int position) {
                SearchFriend.ContentBean searchFriend = (SearchFriend.ContentBean) data.get(position);
                if (mType.equals("topic")) {
                    if (list != null && list.size() > position) {
                        adapter.removeSingle(position);
                    }
                    ToastUtil.shortShowToast("问答邀请成功!");
                    Tools.setTopic(searchFriend.getPhone(), "", topicTitle, topicId);
                } else {
                    if (friend) {
                        User user = MyApplication.getInstance().user;
                        NetUtils.getInstance().applyUser(searchFriend.getUserId(), user.getUserId(), new NetCallBack() {
                            @Override
                            public void onSuccess(String result, String msg, ResultModel model) {
                                ToastUtil.shortShowToast(msg);
                            }

                            @Override
                            public void onFail(String code, String msg, String result) {
                                ToastUtil.shortShowToast(msg);
                            }
                        }, null);
                    } else {
                        if (list != null && list.size() > position) {
                            adapter.removeSingle(position);
                        }
                        ToastUtil.shortShowToast("邀请入群成功!");
                        Tools.groupInvite(searchFriend.getPhone(), familyInfo.getHeadUrl(), familyInfo.getGroupName(), familyInfo.getGroupId());
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = mSearchEt.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.shortShowToast("请先输入你要搜索的内容...");
                    } else {
                        searchFriend(content);
                        KeyBoardUtils.hideSoftInput(mSearchEt);
                    }
                    return true;
                }
                return false;
            }
        });
        locationHelper = LocationHelper.getInstance();
        if (locationHelper != null) {
            locationHelper.setCallBack(new LocationHelper.LocationCallBack() {
                @Override
                public void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province) {
                    FriendSearchActivity.this.lat = lat;
                    FriendSearchActivity.this.lng = lng;
                    adapter.setLatLng(lat, lng);
                }
            });
            locationHelper.start();
        }
    }

    private void searchFriend(String content) {
        NetUtils.getInstance().searchUser(content, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                SearchFriend searchFriend = (SearchFriend) model.getModel();
                if (searchFriend != null) {
                    list = searchFriend.getContent();
                    if (list != null && list.size() > 0) {
                        adapter.appendAll(list);
                    } else {
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, SearchFriend.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationHelper != null) {
            locationHelper.stop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
