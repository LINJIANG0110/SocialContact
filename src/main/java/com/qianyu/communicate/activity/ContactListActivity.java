package com.qianyu.communicate.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.ContactListAdapter;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.PhoneDto;
import com.qianyu.communicate.utils.PhoneUtil;

import net.neiquan.applibrary.bean.SortModel;
import net.neiquan.applibrary.utils.CharacterParser;
import net.neiquan.applibrary.utils.PinyinComparator;
import net.neiquan.applibrary.wight.MySideBar;

import org.haitao.common.utils.AppLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JavaDog on 2019-4-16.
 */

public class ContactListActivity extends BaseActivity {

    @InjectView(R.id.mListView)
    ListView mListView;
    @InjectView(R.id.mTvDialog)
    TextView mTvDialog;
    @InjectView(R.id.mSiderBar)
    MySideBar mSiderBar;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private List<SortModel> SourceDateList;
    public ContactListAdapter adapter;
    private List<PhoneDto> phoneDtos;
    private boolean friend;
    private SortModel sortModel;
    private String mType;
    private String topicTitle;

    @Override
    public int getRootViewId() {
        return R.layout.activity_friend_list;
    }

    @Override
    public void setViews() {
        if (getIntent() != null) {
            topicTitle = getIntent().getStringExtra("topicTitle");
            mType = getIntent().getStringExtra("mType");
            friend = getIntent().getBooleanExtra("friend", false);
        }
        setTitleTv("联系人 ");
    }

    @Override
    public void initData() {
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
                sortModel = (SortModel) adapter.getItem(position);
                smsOpera(sortModel);
            }
        });
        requestPermisson();
    }

    private List<SortModel> filledData(CharacterParser characterParser) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < phoneDtos.size(); i++) {
            AppLog.e(phoneDtos.get(i).getTelPhone() + "===============phoneDtos============" + phoneDtos.get(i).getName());
            SortModel sortModel = new SortModel();
            sortModel.setName(phoneDtos.get(i).getName());
            sortModel.setPhone(phoneDtos.get(i).getTelPhone());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(phoneDtos.get(i).getName());
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

    private void getPhoneList() {
        PhoneUtil phoneUtil = new PhoneUtil(this);
        phoneDtos = phoneUtil.getPhone();
        if (phoneDtos == null) {
            finish();
            return;
        }
        //数据填充
        SourceDateList = filledData(characterParser);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new ContactListAdapter(this, SourceDateList);
        mListView.setAdapter(adapter);
    }

    private void smsOpera(SortModel sortModel) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SENDTO");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("smsto:" + sortModel.getPhone()));
        if (null != mType && mType.equals("topic")) {
            intent.putExtra("sms_body", "我在千语APP遇到个问题(" + topicTitle + ")，欢迎您前来解答！");
        } else {
            intent.putExtra("sms_body", friend ? "我在千语APP聊天交友，欢迎您的到来！" : "我在千语APP群聊，欢迎您的到来！");
        }
        startActivity(intent);
    }

    //申请权限
    private void requestPermisson() {
        //判断用户是否已经授权给我们了 如果没有，调用下面方法向用户申请授权，之后系统就会弹出一个权限申请的对话框
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
            AppLog.e("==============permission111================");
        } else {
            AppLog.e("==============permission222================");
            getPhoneList();
        }
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AppLog.e("==============permission333================");
                getPhoneList();
            } else {
                Toast.makeText(this, "获取联系人权限失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
