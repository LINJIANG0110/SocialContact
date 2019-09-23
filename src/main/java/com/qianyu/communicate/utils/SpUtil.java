package com.qianyu.communicate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.haitao.common.utils.AppLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wl_user on 2017/8/28.
 */

public class SpUtil {
    private static String APP_NAME = "weiluezh";

    /**
     * 存放实体类以及任意类型
     *
     * @param context 上下文对象
     * @param key
     * @param obj
     */
    public static void saveBean(Context context, String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                SharedPreferences.Editor editor = getSharedPreferences(context).edit();
                editor.putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public static Object getBean(Context context, String key) {
        Object obj = null;
        try {
            String base64 = getSharedPreferences(context).getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void saveBeanByFastJson(Context context, String key,
                                          Object obj) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        try {
            String objString = JSON.toJSONString(obj);// fastjson的方法，需要导包的
            editor.putString(key, objString).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param key
     * @param clazz   这里传入一个类就是我们所需要的实体类(obj)
     * @return 返回我们封装好的该实体类(obj)
     */
    public static <T> T getBeanByFastJson(Context context, String key,
                                          Class<T> clazz) {
        String objString = getSharedPreferences(context).getString(key, "");
        try {
            AppLog.e(objString + "==========getBeanByFastJson111=========" + JSON.parseObject(objString, clazz));
            return JSON.parseObject(objString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AppLog.e(objString + "==========getBeanByFastJson222=========" + objString);
        return null;
    }


    public static void saveStringToSP(Context context, String key, String valueStr) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences(context);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, valueStr);
            //提交当前数据
            editor.commit();
        }
    }

    public static void saveLongToSP(Context context, String key, long valueStr) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences(context);
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong(key, valueStr);
            //提交当前数据
            editor.commit();
        }
    }

    public static void saveBooleanToSP(Context context, String key, boolean defaultBoolean) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences(context);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(key, defaultBoolean);
            //提交当前数据
            editor.commit();
        }
    }

    public static boolean getBooleanSP(Context context, String key, boolean defaultBoolean) {
        SharedPreferences sp = getSharedPreferences(context);
        if (!TextUtils.isEmpty(key)) {
            return sp.getBoolean(key, defaultBoolean);
        }
        return defaultBoolean;
    }

    public static String getStringSP(Context context, String key, String defaultValue) {
        SharedPreferences sp = getSharedPreferences(context);
        if (!TextUtils.isEmpty(key)) {
            return sp.getString(key, defaultValue);
        }
        return defaultValue;
    }

    public static long getLongSP(Context context, String key, long defaultValue) {
        SharedPreferences sp = getSharedPreferences(context);
        if (!TextUtils.isEmpty(key)) {
            return sp.getLong(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 存储List<String>
     *
     * @param context
     * @param key     List<String>对应的key
     * @param strList 对应需要存储的List<String>
     */
    public static void saveStrListValue(Context context, String key,
                                        List<String> strList) {
        if (null == strList) {
            return;
        }
        // 保存之前先清理已经存在的数据，保证数据的唯一性
        removeStrList(context, key);
        int size = strList.size();
        saveIntValue(context, key + "size", size);
        for (int i = 0; i < size; i++) {
            saveStringToSP(context, key + i, strList.get(i));
        }
    }


    /**
     * 取出List<String>
     *
     * @param context
     * @param key     List<String> 对应的key
     * @return List<String>
     */
    public static List<String> getStrListValue(Context context, String key) {
        List<String> strList = new ArrayList<String>();
        int size = getIntValue(context, key + "size", 0);
        //Log.d("sp", "" + size);
        for (int i = 0; i < size; i++) {
            strList.add(getStringSP(context, key + i, null));
        }
        return strList;
    }


    /**
     * 存储数据(Int)
     *
     * @param context
     * @param key
     * @param value
     */
    private static void saveIntValue(Context context, String key, int value) {
        SharedPreferences.Editor sp = getSharedPreferences(context)
                .edit();
        sp.putInt(key, value);
        sp.commit();
    }

    /**
     * 取出数据（int)
     *
     * @param context
     * @param key
     * @param defValue 默认值
     * @return
     */
    private static int getIntValue(Context context, String key, int defValue) {
        SharedPreferences sp = getSharedPreferences(context);
        int value = sp.getInt(key, defValue);
        return value;
    }


    /**
     * 清空List<String>所有数据
     *
     * @param context
     * @param key     List<String>对应的key
     */
    public static void removeStrList(Context context, String key) {
        int size = getIntValue(context, key + "size", 0);
        if (0 == size) {
            return;
        }
        delete(context, key + "size");
        for (int i = 0; i < size; i++) {
            delete(context, key + i);
        }
    }

    /**
     * 保存List  （List<String>，List<Map<String,Object>>，List<JavaBean>）
     *
     * @param key
     * @param datalist
     */
    public static <T> void saveDataList(Context context, String key, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0) {
            delete(context, key);
            return;
        }
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
//        editor.clear();
        editor.putString(key, strJson);
        editor.commit();
    }

    /**
     * 获取List
     *
     * @param key
     * @return
     */
    public static <T> List<T> getDataList(Context context, String key) {
        List<T> datalist = new ArrayList<T>();
        String strJson = getSharedPreferences(context).getString(key, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }

    public static <T> List<T> getDataList(Context context, String key, Class<T> cls) {
        List<T> datalist = new ArrayList<T>();
        String strJson = getSharedPreferences(context).getString(key, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
        for (final JsonElement elem : array) {
            datalist.add(gson.fromJson(elem, cls));
        }
        return datalist;
    }

    public static void delete(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

}
