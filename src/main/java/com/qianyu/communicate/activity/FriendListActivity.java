package com.qianyu.communicate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hys.utils.ToastUtils;
import com.qianyu.chatuidemo.ui.ChatActivity;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FriendListAdapter;
import com.qianyu.communicate.adapter.FriendRequestListAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.FriendList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.adpter.SortAdapter;
import net.neiquan.applibrary.bean.SortModel;
import net.neiquan.applibrary.utils.CharacterParser;
import net.neiquan.applibrary.utils.PinyinComparator;
import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.MySideBar;
import net.neiquan.applibrary.wight.NoSclooListView;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JavaDog on 2019-4-16.
 */

public class FriendListActivity extends BaseActivity {

    @InjectView(R.id.mRequestListView)
    NoSclooListView noSclooListView;
    @InjectView(R.id.mListView)
    ListView mListView;
    @InjectView(R.id.mTvDialog)
    TextView mTvDialog;
    @InjectView(R.id.mSiderBar)
    MySideBar mSiderBar;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private List<SortModel> SourceDateList;
    public FriendListAdapter adapter;
    private FriendRequestListAdapter requestListAdapter;
    private boolean friend;
    private FamilyList.ContentBean familyInfo;
    private String mType;
    // 邀请回答
    private String topicId;
    private String topicTitle;

    @Override
    public int getRootViewId() {
        return R.layout.activity_friend_list;
    }

    @Override
    public void setViews() {
        setTitleTv("好友列表");
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        if (getIntent() != null) {
            mType = getIntent().getStringExtra("mType");
            if (mType.equals("topic")) {
                topicId = getIntent().getStringExtra("topicId");
                topicTitle = getIntent().getStringExtra("topicTitle");
                // 话题邀请回答
                setNextOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<SortModel> sortModels = adapter.getmList();
                        for (int i = 0; i < sortModels.size(); i++) {
                            if (sortModels.get(i).isSelected()) {
                                if (topicId != null) {
                                    ToastUtil.shortShowToast("邀请回答成功!");
                                    Log.e("topicID", topicId);
                                    Tools.setTopic(sortModels.get(i).getPhone(), "", topicTitle, topicId);
                                }
                            }
                        }
                    }
                });
            } else {
                friend = getIntent().getBooleanExtra("friend", false);
                familyInfo = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
                if (friend) {
                    setNextImage(R.mipmap.guanzhu);
                    setNextOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(FriendListActivity.this, FriendInviteActivity.class);
                            intent.putExtra("mType", "");
                            intent.putExtra("friend", true);
                            startActivity(intent);
                        }
                    });
                    EventBus.getDefault().post(new EventBuss(EventBuss.FRIEND_CLEAR));
                } else {
                    setNextOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            List<SortModel> sortModels = adapter.getmList();
                            for (int i = 0; i < sortModels.size(); i++) {
                                if (sortModels.get(i).isSelected()) {
                                    if (familyInfo != null) {
                                        ToastUtil.shortShowToast("邀请入群成功!");
                                        Tools.groupInvite(sortModels.get(i).getPhone(), familyInfo.getHeadUrl(), familyInfo.getGroupName(), familyInfo.getGroupId());
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        mSiderBar.setTextView(mTvDialog);
        // 设置右侧触摸监听
        mSiderBar.setOnTouchingLetterChangedListener(new MySideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                if (adapter != null) {
                    int position = adapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        mListView.setSelection(position);
                    }
                }
            }
        });
        //item点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                SortModel sortModel;
                if (friend) {
                    sortModel = (SortModel) adapter.getItem(position);
                    Intent intent3 = new Intent(FriendListActivity.this, ChatActivity.class);
                    intent3.putExtra(com.qianyu.chatuidemo.Constant.EXTRA_USER_ID, sortModel.getPhone());
                    startActivity(intent3);
                } else {
                    sortModel = (SortModel) adapter.getItem(position);
                    int count = 0;
                    List<SortModel> sortModels = adapter.getmList();
                    for (int i = 0; i < sortModels.size(); i++) {
                        if (sortModels.get(i).isSelected()) {
                            count++;
                        }
                    }
                    setNextTv("确认（" + count + "/" + adapter.getCount() + "）");
                    sortModel.setSelected(!sortModel.isSelected());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (friend) {
                    final SortModel sortModel = (SortModel) adapter.getItem(position);
                    new AlertDialog.Builder(FriendListActivity.this).setTitle("删除好友?")
                            .setMessage("您是否确定删除该好友？")
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    User user = MyApplication.getInstance().user;
                                    if (null != sortModel.getPhone() && sortModel.getPhone().equals("13500000000")) {
                                        ToastUtils.getInstance().show(FriendListActivity.this, "官方客服不可删除");
                                        return;
                                    }
                                    NetUtils.getInstance().deleteFriend(sortModel.getUserId(), user.getUserId(), new NetCallBack() {
                                        @Override
                                        public void onSuccess(String result, String msg, ResultModel model) {
                                            ToastUtil.shortShowToast(msg);
                                            adapter.getmList().remove(sortModel);
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFail(String code, String msg, String result) {
                                            ToastUtil.shortShowToast(msg);
                                        }
                                    }, null);
                                }
                            }).create().show();
                }
                return true;
            }
        });

    }

    @Override
    public void initData() {
        User user = MyApplication.getInstance().user;
        NetUtils.getInstance().friendList(user.getUserId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                FriendList friendList = (FriendList) model.getModel();
                if (friendList != null) {
                    if (friendList.getApplyList() != null && friendList.getApplyList().size() > 0) {
                        requestListAdapter = new FriendRequestListAdapter(FriendListActivity.this, friendList.getApplyList());
                        noSclooListView.setAdapter(requestListAdapter);
                        requestListAdapter.setOnFriendRequestListener(new FriendRequestListAdapter.OnFriendRequestListener() {
                            @Override
                            public void onFriendRequest() {
                                initData();
                            }
                        });
                    }
                    if (friendList.getFriendList() != null && friendList.getFriendList().size() > 0) {
                        //数据填充
                        SourceDateList = filledData(characterParser, friendList.getFriendList());
                        // 根据a-z进行排序源数据
                        Collections.sort(SourceDateList, pinyinComparator);
                        adapter = new FriendListAdapter(FriendListActivity.this, SourceDateList, friend);
                        mListView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, FriendList.class);
    }

    private List<SortModel> filledData(CharacterParser characterParser, List<FriendList.FriendListBean> datas) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < datas.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(datas.get(i).getNickName());
            sortModel.setHeadUrl(datas.get(i).getHeadUrl());
            sortModel.setSex(datas.get(i).getSex());
            sortModel.setLevel(datas.get(i).getLevel());
            sortModel.setPhone(datas.get(i).getPhone());
            sortModel.setUserId(datas.get(i).getUserId());
            sortModel.setExpand(datas.get(i).getExpand());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(datas.get(i).getNickName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
