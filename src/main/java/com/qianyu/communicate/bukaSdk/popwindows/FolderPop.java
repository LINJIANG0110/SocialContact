package com.qianyu.communicate.bukaSdk.popwindows;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.bukaSdk.adapter.FolderAdapter;
import com.qianyu.communicate.bukaSdk.entity.DocImageEntity;

import java.util.List;


/**
 * @说明: //直播间文件夹的pop
 * @作者: hwk
 * @创建日期: 2017/8/29 18:24
 */

public class FolderPop extends PopupWindow {
    private List<DocImageEntity> mDocumentInfos;
    private Context context;
    private PopupWindow mPopupWindow;
    private FolderItemClickCallback folderItemClickCallback;
    private FolderAdapter mAdapter;

    public FolderPop(Context context) {
        super(context);
    }

    public FolderPop(Context context, List<DocImageEntity> documentInfos, FolderItemClickCallback folderItemClickCallback) {
        this.context = context;
        this.mDocumentInfos = documentInfos;
        this.folderItemClickCallback = folderItemClickCallback;
        init();
    }

    public void showPop(View v, List<DocImageEntity> documentInfos) {
        if (null != mPopupWindow && !mPopupWindow.isShowing()) {
            mAdapter.setDocumentInfos(documentInfos);
            mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
    }

    public boolean disMissPop() {
        boolean isshowing = false;
        if (null != mPopupWindow) {
            isshowing = mPopupWindow.isShowing();
            mPopupWindow.dismiss();
        }
        return isshowing;
    }

    private void init() {
        View convertView = LayoutInflater.from(context).inflate(R.layout.pop_folder, null);
        TextView tv_close = convertView.findViewById(R.id.tv_close);
        ListView listView = convertView.findViewById(R.id.listView);
        RelativeLayout rl_nodata = convertView.findViewById(R.id.rl_nodata);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disMissPop();
            }
        });

        if (mDocumentInfos != null && mDocumentInfos.size() != 0) {
            rl_nodata.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            mAdapter = new FolderAdapter(context, mDocumentInfos);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    folderItemClickCallback.click(i);
                }
            });
        } else {
            rl_nodata.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }


        mPopupWindow = new PopupWindow(convertView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
    }


    public interface FolderItemClickCallback {
        void click(int position);//item点击
    }
}
