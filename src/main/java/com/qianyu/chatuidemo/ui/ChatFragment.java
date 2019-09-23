package com.qianyu.chatuidemo.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.hyphenate.easeui.widget.presenter.ChatRowGroupl;
import com.hyphenate.easeui.widget.presenter.ChatRowGuardFaill;
import com.hyphenate.easeui.widget.presenter.ChatRowSkillFaill;
import com.hyphenate.easeui.widget.presenter.ChatRowSkillSuccessl;
import com.hyphenate.easeui.widget.presenter.ChatRowTopic;
import com.hyphenate.easeui.widget.presenter.EaseChatGiftPresenter;
import com.hyphenate.easeui.widget.presenter.EaseChatGroupPresenter;
import com.hyphenate.easeui.widget.presenter.EaseChatGuardFailPresenter;
import com.hyphenate.easeui.widget.presenter.EaseChatGuardSuccessPresenter;
import com.hyphenate.easeui.widget.presenter.EaseChatSkillFailPresenter;
import com.hyphenate.easeui.widget.presenter.EaseChatSkillSuccessPresenter;
import com.hyphenate.easeui.widget.presenter.EaseChatTopicPersenter;
import com.qianyu.chatuidemo.Constant;
import com.qianyu.chatuidemo.DemoHelper;
import com.qianyu.chatuidemo.domain.EmojiconExampleGroupData;
import com.qianyu.chatuidemo.domain.RobotUser;
import com.qianyu.chatuidemo.widget.EaseChatRecallPresenter;
import com.qianyu.chatuidemo.widget.EaseChatVoiceCallPresenter;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.MainActivity;
import com.qianyu.communicate.activity.PersonalPageActivity;
import com.qianyu.communicate.activity.SkillActivity;
import com.qianyu.communicate.activity.SkillDetailActivity;
import com.qianyu.communicate.activity.TopicAllActivity;
import com.qianyu.communicate.entity.User;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.easeui.widget.presenter.EaseChatRowPresenter;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.NetUtils;

import org.haitao.common.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper {

    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;
    private static final int MESSAGE_TYPE_RECALL = 9;


    /**
     * if it is chatBot
     */
    private boolean isRobot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState,
                DemoHelper.getInstance().getModel().isMsgRoaming() && (chatType != EaseConstant.CHATTYPE_CHATROOM));
    }

    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            Map<String, RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                isRobot = true;
            }
        }
        super.setUpView();
        // set click listener
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });
        ((EaseEmojiconMenu) inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 1 && "@".equals(String.valueOf(s.charAt(start)))) {
                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        titleBar.setTitleClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtils.getInstance().getUserByHx(toChatUsername, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        User user = (User) model.getModel();
                        if (user != null) {
                            Intent intent = new Intent(getActivity(), PersonalPageActivity.class);
                            intent.putExtra("userId", user.getUserId());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                }, User.class);
            }
        });
    }

    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
//        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // copy
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // delete
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;

                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
                    Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
                    intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
                    startActivity(intent);
                    break;
                case ContextMenuActivity.RESULT_CODE_RECALL://recall
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMMessage msgNotification = EMMessage.createTxtSendMessage(" ", contextMenuMessage.getTo());
                                EMTextMessageBody txtBody = new EMTextMessageBody(getResources().getString(R.string.msg_recall_by_self));
                                msgNotification.addBody(txtBody);
                                msgNotification.setMsgTime(contextMenuMessage.getMsgTime());
                                msgNotification.setLocalTime(contextMenuMessage.getMsgTime());
                                msgNotification.setAttribute(Constant.MESSAGE_TYPE_RECALL, true);
                                msgNotification.setStatus(EMMessage.Status.SUCCESS);
                                EMClient.getInstance().chatManager().recallMessage(contextMenuMessage);
                                EMClient.getInstance().chatManager().saveMessage(msgNotification);
                                messageList.refresh();
                            } catch (final HyphenateException e) {
                                e.printStackTrace();
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                    break;

                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if (data != null) {
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }


    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            startActivityForResult(
                    (new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)),
                    REQUEST_CODE_GROUP_DETAIL);
        } else if (chatType == Constant.CHATTYPE_CHATROOM) {
            startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
        }
    }

    @Override
    public void onAvatarClick(String username) {
        //handling when user click avatar
        NetUtils.getInstance().getUserByHx(username, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                User user = (User) model.getModel();
                if (user != null) {
                    Intent intent = new Intent(getActivity(), PersonalPageActivity.class);
                    intent.putExtra("userId", user.getUserId());
                    startActivity(intent);
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
            }
        }, User.class);
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        try {
            if (message.getBooleanAttribute(EaseConstant.MESSAGE_FAMILY_AT_TYPE)) {
                final long gId = message.getIntAttribute(EaseConstant.MESSAGE_FAMILY_AT_GROUPID);
                Tools.enterFamily(getActivity(), gId, false);
                return true;
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //file
                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL:
                startVoiceCall();
                break;
            case ITEM_VIDEO_CALL:
                startVideoCall();
                break;
            default:
                break;
        }
        //keep exist extend menu
        return false;
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 11 + 2 + 2 + 2 + 2 + 2 + 2;//加上自定义入群邀请和送礼物消息和技能成功和失败和押镖成功和失败的接受和发送
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //messagee recall
                else if (message.getBooleanAttribute(Constant.MESSAGE_TYPE_RECALL, false)) {
                    return MESSAGE_TYPE_RECALL;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_GROUP_TYPE, false)) {  //自定义消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? EaseConstant.MESSAGE_TYPE_RECV_GROUP : EaseConstant.MESSAGE_TYPE_SENT_GROUP;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_GIFT_TYPE, false)) {  //自定义消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? EaseConstant.MESSAGE_TYPE_RECV_GIFT : EaseConstant.MESSAGE_TYPE_SENT_GIFT;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_SKILL_SUCCESS_TYPE, false)) {  //自定义消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? EaseConstant.MESSAGE_TYPE_RECV_SKILL_SUCCESS : EaseConstant.MESSAGE_TYPE_SENT_SKILL_SUCCESS;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_SKILL_FAIL_TYPE, false)) {  //自定义消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? EaseConstant.MESSAGE_TYPE_RECV_SKILL_FAIl : EaseConstant.MESSAGE_TYPE_SENT_SKILL_FAIL;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_GUARD_SUCCESS_TYPE, false)) {  //自定义消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? EaseConstant.MESSAGE_TYPE_RECV_GUARD_SUCCESS : EaseConstant.MESSAGE_TYPE_SENT_GUARD_SUCCESS;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_GUARD_FAIL_TYPE, false)) {  //自定义消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? EaseConstant.MESSAGE_TYPE_RECV_GUARD_FAIL : EaseConstant.MESSAGE_TYPE_SENT_GUARD_FAIL;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_TOPIC_TYPE, false)) {  //自定义消息话题
                    return message.direct() == EMMessage.Direct.RECEIVE ? EaseConstant.MESSAGE_TYPE_RECV_TOPIC : EaseConstant.MESSAGE_TYPE_SENT_TOPIC;
                }
            }
            return 0;
        }

        @Override
        public EaseChatRowPresenter getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // voice call or video call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    EaseChatRowPresenter presenter = new EaseChatVoiceCallPresenter();
                    return presenter;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_GROUP_TYPE, false)) {//自定义消息
                    EaseChatGroupPresenter presenter = new EaseChatGroupPresenter();
                    presenter.setOnEnterGroupListener(new ChatRowGroupl.OnEnterGroupListener() {
                        @Override
                        public void onEnterGroupListener(int gId, String groupName, String groupUrl) {
                            Tools.enterFamily(getActivity(), gId, false);
                        }
                    });
                    return presenter;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_GIFT_TYPE, false)) {//自定义消息
                    EaseChatGiftPresenter presenter = new EaseChatGiftPresenter();
                    return presenter;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_SKILL_SUCCESS_TYPE, false)) {//自定义消息
                    EaseChatSkillSuccessPresenter presenter = new EaseChatSkillSuccessPresenter();
                    presenter.setOnSkillFailListener(new ChatRowSkillSuccessl.OnSkillSuccessListener() {
                        @Override
                        public void onSkillSuccess(int type, long userId) {
                            if (type == 1) {
                                startActivity(new Intent(getActivity(), SkillDetailActivity.class));
                            } else if (type == 2) {
                                Intent intent = new Intent(getActivity(), SkillActivity.class);
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                            } else {
                                getActivity().finish();
                            }
                        }
                    });
                    return presenter;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_SKILL_FAIL_TYPE, false)) {//自定义消息
                    EaseChatSkillFailPresenter presenter = new EaseChatSkillFailPresenter();
                    presenter.setOnSkillFailListener(new ChatRowSkillFaill.OnSkillFailListener() {
                        @Override
                        public void onSkillFail(int type, long userId) {
                            if (type == 1) {
                                startActivity(new Intent(getActivity(), SkillDetailActivity.class));
                            } else {
                                Intent intent = new Intent(getActivity(), SkillActivity.class);
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                            }
                        }
                    });
                    return presenter;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_GUARD_SUCCESS_TYPE, false)) {//自定义消息
                    EaseChatGuardSuccessPresenter presenter = new EaseChatGuardSuccessPresenter();
                    return presenter;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_GUARD_FAIL_TYPE, false)) {//自定义消息
                    EaseChatGuardFailPresenter presenter = new EaseChatGuardFailPresenter();
                    presenter.setOnGuardFailListener(new ChatRowGuardFaill.OnGuardFailListener() {
                        @Override
                        public void onGuardFail() {
                            startActivity(new Intent(getActivity(), SkillDetailActivity.class));
                        }
                    });
                    return presenter;
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_GUARD_FAIL_TYPE, false)) {//自定义话题邀请消息
                    EaseChatGuardFailPresenter presenter = new EaseChatGuardFailPresenter();
                    presenter.setOnGuardFailListener(new ChatRowGuardFaill.OnGuardFailListener() {
                        @Override
                        public void onGuardFail() {
                            startActivity(new Intent(getActivity(), SkillDetailActivity.class));
                        }
                    });
                    return presenter;
                }
                //recall message
                else if (message.getBooleanAttribute(Constant.MESSAGE_TYPE_RECALL, false)) {
                    EaseChatRowPresenter presenter = new EaseChatRecallPresenter();
                    return presenter;
                }
                //recall message邀请回答
                else if (message.getBooleanAttribute(Constant.MESSAGE_TOPIC_TYPE, false)) {
                    EaseChatTopicPersenter presenter = new EaseChatTopicPersenter();
                    presenter.setOnEnterTopicListener(new ChatRowTopic.OnEnterTopicListener() {
                        @Override
                        public void onEnterTopicListener(String topicId, String topicTitle, String mUrl) {
                            startActivity(new Intent(getActivity(), TopicAllActivity.class).putExtra("topicId", topicId).putExtra("topicTitle", topicTitle));
                            // Log.e("话题邀请消息点击", "ChatFragment.java-543");
                        }
                    });
                    return presenter;
                }
            }
            return null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //调用该方法可防止红包SDK引起的内存泄漏
    }
}
