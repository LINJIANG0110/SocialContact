package com.qianyu.communicate.bukaSdk.popwindows;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyu.communicate.R;
import com.qianyu.communicate.bukaSdk.adapter.PhotoChoiceAdapter;

import org.haitao.common.utils.AppLog;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @说明: //直播间图片选择的pop
 * @作者: hwk
 * @创建日期: 2017/8/30 18:24
 */

public class PhotoChoicePop extends PopupWindow {
    private Context context;
    private PopupWindow mPopupWindow;
    private CallBackPop callBackPop;

    public PhotoChoicePop(Context context, CallBackPop callBackPop) {
        this.context = context;
        this.callBackPop = callBackPop;

        init();
    }

    public void showPop(View v) {
        if (null != mPopupWindow && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
    }

    public void disMissPop() {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private ArrayList<String> fileNames;
    private HashMap<Integer, Boolean> isChecked;

    private void init() {
        fileNames = new ArrayList();
        isChecked = new HashMap<>();
        int position = 0;
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //获取图片的生成日期
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            fileNames.add(new String(data, 0, data.length - 1));
            isChecked.put(position, false);
            position++;
        }

        View convertView = LayoutInflater.from(context).inflate(R.layout.pop_choice_photo, null);
        LinearLayout ly_back = convertView.findViewById(R.id.ly_back);
//        iv_bar_back.setColorFilter(Color.WHITE);
        TextView tv_bar_title = convertView.findViewById(R.id.tv_bar_title);
        TextView tv_bar_right = convertView.findViewById(R.id.tv_bar_right);
        RelativeLayout rl_nodata = convertView.findViewById(R.id.rl_nodata);
        GridView gridview = convertView.findViewById(R.id.gridview);

        if (fileNames == null || fileNames.size() == 0) {
            rl_nodata.setVisibility(View.VISIBLE);
            gridview.setVisibility(View.GONE);
        } else {
            rl_nodata.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
            PhotoChoiceAdapter adapter = new PhotoChoiceAdapter(context, callBack, fileNames, isChecked);
            gridview.setAdapter(adapter);
        }

        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppLog.e("===============disMissPop=================");
                disMissPop();
            }
        });

        tv_bar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCheck) {
                    callBackPop.close(path);
                    disMissPop();
                } else {
                    Toast.makeText(context, "您还没有选中照片！！！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPopupWindow = new PopupWindow(convertView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
    }


    public interface CallBackPop {
        void close(String path);
    }

    private boolean isCheck;
    private String path;

    PhotoChoiceAdapter.CallBack callBack = new PhotoChoiceAdapter.CallBack() {
        @Override
        public void check(boolean isChecked, String nativePath) {
            isCheck = isChecked;
            path = nativePath;
        }
    };

}
