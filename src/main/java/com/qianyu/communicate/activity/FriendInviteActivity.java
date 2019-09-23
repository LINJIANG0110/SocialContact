package com.qianyu.communicate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.APPURL;

import org.haitao.common.utils.ToastUtil;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.utils.DeviceUtils;

import static com.qianyu.communicate.utils.Tools.isAppInstalled;

/**
 * Created by JavaDog on 2019-4-16.
 */

public class FriendInviteActivity extends BaseActivity {
    @InjectView(R.id.mSearchFriendLL)
    LinearLayout mSearchFriendLL;
    @InjectView(R.id.mPhoneFriendLL)
    LinearLayout mPhoneFriendLL;
    @InjectView(R.id.mWxFriendLL)
    LinearLayout mWxFriendLL;
    @InjectView(R.id.mQQFriendLL)
    LinearLayout mQQFriendLL;
    @InjectView(R.id.mAppFriendLL)
    LinearLayout mAppFriendLL;
    @InjectView(R.id.mLocalFriendLL)
    LinearLayout mLocalFriendLL;
    private boolean friend;
    private FamilyList.ContentBean familyInfo;
    // 话题参数
    private String topicId;
    private String topicTitle;
    private String mType;

    @Override
    public int getRootViewId() {
        return R.layout.activity_friend_invite;
    }

    @Override
    public void setViews() {
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            mType = getIntent().getStringExtra("mType");// 判断类型
            if (mType.equals("topic")) {
                // 话题邀请回答
                topicId = getIntent().getStringExtra("topicId");
                topicTitle = getIntent().getStringExtra("topicTitle");
            } else {
                friend = getIntent().getBooleanExtra("friend", false);
                familyInfo = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
                setTitleTv(friend ? "添加好友" : "邀请好友入群");
                mAppFriendLL.setVisibility(friend ? View.GONE : View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.mSearchFriendLL, R.id.mPhoneFriendLL, R.id.mWxFriendLL, R.id.mQQFriendLL, R.id.mAppFriendLL, R.id.mLocalFriendLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 搜索好友
            case R.id.mSearchFriendLL:
                Intent intent1 = new Intent(FriendInviteActivity.this, FriendSearchActivity.class);
                if (mType.equals("topic")) {
                    intent1.putExtra("mType",mType);
                    intent1.putExtra("topicId", topicId);
                    intent1.putExtra("topicTitle", topicTitle);
                    startActivity(intent1);
                } else {
                    intent1.putExtra("friend", friend);
                    intent1.putExtra("family", familyInfo);
                    startActivity(intent1);
                }
                break;
            // 手机好友
            case R.id.mPhoneFriendLL:
                Intent intent2 = new Intent(FriendInviteActivity.this, ContactListActivity.class);
                intent2.putExtra("friend", friend);
                intent2.putExtra("topicTitle",topicTitle);
                intent2.putExtra("mType", mType);
                startActivity(intent2);
                break;
            case R.id.mWxFriendLL:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.mQQFriendLL:
                share(SHARE_MEDIA.QQ);
                break;
            // 千语好友
            case R.id.mAppFriendLL:
                Intent intent = new Intent(FriendInviteActivity.this, FriendListActivity.class);
                if (mType.equals("topic")) {
                    intent.putExtra("mType",mType);
                    intent.putExtra("topicId", topicId);
                    intent.putExtra("topicTitle", topicTitle);
                    startActivity(intent);
                } else {
                    intent.putExtra("mType",mType);
                    intent.putExtra("friend", false);
                    intent.putExtra("family", familyInfo);
                    startActivity(intent);
                }
                break;
            // 附近的人
            case R.id.mLocalFriendLL:
                Intent intent3 = new Intent(FriendInviteActivity.this, LocalFriendsActivity.class);
                if (mType.equals("topic")) {
                    intent3.putExtra("mType",mType);
                    intent3.putExtra("topicId", topicId);
                    intent3.putExtra("topicTitle", topicTitle);
                    startActivity(intent3);
                } else {
                    intent3.putExtra("friend", friend);
                    intent3.putExtra("family", familyInfo);
                    startActivity(intent3);
                }
                break;
        }
    }

    //分享
    private void share(SHARE_MEDIA platform) {
        User user = MyApplication.getInstance().user;
        UMWeb web = new UMWeb(APPURL.BASE_SHARE_URL + "gameType=10003&headUrl=" + user.getHeadUrl() + "&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(FriendInviteActivity.this));
        if (mType.equals("")) {
            UMImage image = new UMImage(FriendInviteActivity.this, R.drawable.sharelogo);
            web.setTitle("千语分享");//标题
            web.setThumb(image);  //缩略图
            web.setDescription("据说有实力的人，可以让任何人闭嘴。");//描述
        } else {
            UMImage image = new UMImage(FriendInviteActivity.this, R.drawable.topic_fmt);
            web.setTitle("千语分享");//标题
            web.setThumb(image);  //缩略图
            web.setDescription("问答邀请：" + topicTitle);//描述
        }
        new ShareAction(FriendInviteActivity.this)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(shareListener)
                .share();
        NetUtils.getInstance().appShare(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {

            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, null);
    }

    //分享回调
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            if (platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE) {
                if (!isAppInstalled(FriendInviteActivity.this, "com.tencent.mobileqq") && !isAppInstalled(FriendInviteActivity.this, "com.tencent.tim")) {
                    ToastUtil.shortShowToast("请先安装QQ或者TIM客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
                if (!isAppInstalled(FriendInviteActivity.this, "com.tencent.mm")) {
                    ToastUtil.shortShowToast("请先安装微信客户端");
                    return;
                }
            } else if (platform == SHARE_MEDIA.SINA) {
                if (!isAppInstalled(FriendInviteActivity.this, "com.sina.weibo")) {
                    ToastUtil.shortShowToast("请先安装新浪微博客户端");
                    return;
                }
            }
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(FriendInviteActivity.this, "分享成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(FriendInviteActivity.this, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(FriendInviteActivity.this, "分享取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
