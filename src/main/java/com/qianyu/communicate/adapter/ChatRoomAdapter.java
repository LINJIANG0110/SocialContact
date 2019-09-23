package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.views.MyRecylerView;

import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.wight.CommonPopupWindow;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class ChatRoomAdapter extends MyBaseAdapter<User, ChatRoomAdapter.ViewHolder> {


    public ChatRoomAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_chat_room, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        holder.mHeadImg.setImageURI("http://www.qqzhi.com/uploadpic/2015-01-16/013806507.jpg");
        holder.mHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPersonalInfoPopWindow(holder.mChatRoomLL);
            }
        });
    }

    private void showPersonalInfoPopWindow(LinearLayout mChatRoomLL) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(context)
                //设置PopupWindow布局
                .setView(R.layout.personal_info_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        SimpleDraweeView mHeadImg = view.findViewById(R.id.mHeadImg);
                        mHeadImg.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
                        MyRecylerView mCircleRecylerView = view.findViewById(R.id.circleRecylerView);
                        initCircleRecylerView(mCircleRecylerView);

                        final LinearLayout atTaLL = view.findViewById(R.id.atTaLL);
                        final LinearLayout giftLL = view.findViewById(R.id.giftLL);

                        atTaLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(atTaLL);
                            }
                        });
                        giftLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SpringUtils.springAnim(giftLL);
                            }
                        });

                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mChatRoomLL, Gravity.BOTTOM, 0, 0);
    }

    private void initCircleRecylerView(MyRecylerView mCircleRecylerView) {
//        LinearLayoutManager layoutManager =
//                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        mCircleRecylerView.setLayoutManager(layoutManager);
//        mCircleRecylerView.setAdapter(new InfoCircleAdapter(this, null));

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.HORIZONTAL);
        mCircleRecylerView.setHasFixedSize(true);
        mCircleRecylerView.setLayoutManager(layoutManager);
        mCircleRecylerView.setAdapter(new InfoCircleAdapter(context, null));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mChatRoomLL)
        LinearLayout mChatRoomLL;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
