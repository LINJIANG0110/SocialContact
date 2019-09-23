package com.qianyu.communicate.bukaSdk.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.utils.TimeUtils;

import org.haitao.common.utils.AppLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tv.buka.sdk.BukaSDKManager;
import tv.buka.sdk.entity.User;

public class UserAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater = null;
    private List<User> mList;
    private Activity activity;

    public UserAdapter(Activity activity) {
        super();
        this.activity = activity;
        this.mLayoutInflater = LayoutInflater.from(activity);
        this.mList = new ArrayList<User>();
    }

    @Override
    public int getCount() {
//        return mList.size() <= 0 ? 11 : mList.size();
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_on_line, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.mNameTv);
            holder.ageSexLL = convertView.findViewById(R.id.ageSexLL);
            holder.sexLogo = convertView.findViewById(R.id.sexLogo);
            holder.userRole = convertView.findViewById(R.id.userRole);
            holder.speakLogo = convertView.findViewById(R.id.speakLogo);
            holder.userOfficial = convertView.findViewById(R.id.userOfficial);
            holder.mAgeTv = convertView.findViewById(R.id.mAgeTv);
            holder.mHeadImg = convertView.findViewById(R.id.mHeadImg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mList != null && mList.size() > 0) {
            UserBean userBean = new UserBean(mList.get(arg0).getUser_extend());
            AppLog.e(mList.get(arg0).getSession_id() + "=============userBean============" + userBean.toString());
            holder.name.setText(TextUtils.isEmpty(userBean.getNickName()) ? "" : userBean.getNickName());
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(userBean.getHeadUrl()) ? "" : userBean.getHeadUrl());
            holder.speakLogo.setVisibility((userBean.getMike() == 1 || userBean.getMike() == 2) ? View.VISIBLE : View.GONE);
            holder.userOfficial.setVisibility(userBean.getExpand()>0 ? View.VISIBLE : View.GONE);
            int sex = userBean.getSex();
            switch (sex) {
                case 1:
                    holder.name.setTextColor(activity.getResources().getColor(R.color.chat_color_nan));
                    break;
                case 2:
                    holder.name.setTextColor(activity.getResources().getColor(R.color.chat_color_nv));
                    break;
            }
//            holder.mAgeTv.setText("" + TimeUtils.getAgeFromBirthTime(userBean.getAge()));
//            int sex = userBean.getSex();
//            switch (sex) {
//                case 1:
//                    holder.ageSexLL.setBackground(activity.getResources().getDrawable(R.drawable.shape_cornor_blue_));
//                    holder.sexLogo.setImageResource(R.mipmap.xiangqing_nan1);
//                    break;
//                case 2:
//                    holder.ageSexLL.setBackground(activity.getResources().getDrawable(R.drawable.shape_cornor_pink));
//                    holder.sexLogo.setImageResource(R.mipmap.xiangqing_nv1);
//                    break;
//            }
            int role = userBean.getUserType();
            holder.userRole.setVisibility(View.VISIBLE);
            switch (role) {
                case 1:
                    holder.userRole.setImageResource(R.mipmap.patriarch);
                    break;
                case 2:
                    holder.userRole.setImageResource(R.mipmap.manager);
                    break;
                default:
                    holder.userRole.setVisibility(View.GONE);
                    break;
            }
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView name;
        LinearLayout ageSexLL;
        ImageView sexLogo;
        ImageView userRole;
        ImageView speakLogo;
        ImageView userOfficial;
        TextView mAgeTv;
        SimpleDraweeView mHeadImg;
    }

    public List<User> getmList() {
        return mList;
    }

    public void setmList() {
        List<User> userArr = BukaSDKManager.getUserManager()
                .getUserArr();
        this.mList.clear();
        for (int i = 0; i < userArr.size(); i++) {
            this.mList.add(userArr.get(i));
        }
        Collections.sort(this.mList, new Comparator<User>() {
            /*
             * int compare(User o1, User o2) 返回一个基本类型的整型，
             * 返回正数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回负数表示：o1大于o2。
             */
            @Override
            public int compare(User o1, User o2) {
                UserBean userBean1 = new UserBean(o1.getUser_extend());
                UserBean userBean2 = new UserBean(o2.getUser_extend());
                int m1 = userBean1.getUserType();
                int m2 = userBean2.getUserType();
                //按照用户角色排列
                if (m1 > m2) {
                    return 1;
                }
                if (m1 == m2) {
                    return 0;
                }
                return -1;
            }
        });
        notifyDataSetChanged();
    }

}
