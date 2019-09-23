package com.qianyu.chatuidemo.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.qianyu.chatuidemo.Constant;
import com.qianyu.chatuidemo.db.InviteMessgeDao;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.LookMeActivity;
import com.qianyu.communicate.activity.MainActivity;
import com.qianyu.communicate.event.EventBuss;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;
import com.qianyu.communicate.utils.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ConversationListFragment extends EaseConversationListFragment {

    private TextView errorText;
    private ImmersionBar mImmersionBar;

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        View errorView = View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = errorView.findViewById(R.id.tv_connect_errormsg);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true);  //状态栏字体是深色，不写默认为亮色
        mImmersionBar.init();
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.HOME_MSG_REFRESH) {
            refresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                conversation.markAllMessagesAsRead();
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser())) {
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.equals("13111111111", username)) {
                    EMMessage lastMessage = conversation.getLastMessage();
                    long gId = lastMessage.getLongAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_ID, 0);
                    Tools.enterFamily(getActivity(), gId, false);
                } else if (TextUtils.equals("13222222222", username)) {
                    EMMessage lastMessage = conversation.getLastMessage();
                    long gId = lastMessage.getLongAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_ID, 0);
                    Tools.enterFamily(getActivity(), gId, false);
                } else if (TextUtils.equals("13333333333", username)) {
                    EMMessage lastMessage = conversation.getLastMessage();
                    long gId = lastMessage.getLongAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_ID, 0);
                    Tools.enterFamily(getActivity(), gId, false);
                } else if (TextUtils.equals("13444444444", username)) {
                    EMMessage lastMessage = conversation.getLastMessage();
                    long gId = lastMessage.getLongAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_ID, 0);
                    Tools.enterFamily(getActivity(), gId, false);
                } else if (TextUtils.equals("13555555555", username)) {
                    EMMessage lastMessage = conversation.getLastMessage();
                    long gId = lastMessage.getLongAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_ID, 0);
                    Tools.enterFamily(getActivity(), gId, false);
                } else if (TextUtils.equals("13666666666", username)) {
                    EMMessage lastMessage = conversation.getLastMessage();
                    long gId = lastMessage.getLongAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_ID, 0);
                    Tools.enterFamily(getActivity(), gId, false);
                } else if (TextUtils.equals("13777777777", username)) {
                    startActivity(new Intent(getActivity(), LookMeActivity.class));
                } else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if (conversation.isGroup()) {
                        if (conversation.getType() == EMConversationType.ChatRoom) {
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        } else {
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }

                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        EMConversation conversation = (EMConversation) conversationListView.getItemAtPosition(((AdapterContextMenuInfo) menuInfo).position);
        final String username = conversation.conversationId();
        EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
        String tempName = group != null ? group.getGroupName() : username;
        if (tempName.equals("13500000000")) {
            Log.e("官方客服不可删除","mmmm");
            return;
        }
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            Log.e("删除会话和消息", item.getItemId() + "*");
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            Log.e("删除会话", item.getItemId() + "*");
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }
        if (tobeDeleteCons.getType() == EMConversationType.GroupChat) {
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }

}
