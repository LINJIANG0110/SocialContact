package com.qianyu.communicate.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.qianyu.communicate.entity.PhoneDto;

import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JavaDog on 2019-4-18.
 */

public class PhoneUtil {

    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    //上下文对象
    private Context context;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public PhoneUtil(Context context){
        this.context = context;
    }

    //获取所有联系人
    public List<PhoneDto> getPhone() {
        try {
            List<PhoneDto> phoneDtos = new ArrayList<>();
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
            while (cursor.moveToNext()) {
                PhoneDto phoneDto = new PhoneDto(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)));
                phoneDtos.add(phoneDto);
            }
            return phoneDtos;
        } catch (Exception e) {
            ToastUtil.longShowToast("请先在手机设置页面找到千语APP权限管理，并允许千语APP访问您的手机联系人...");
            return null;
        }
    }

}
