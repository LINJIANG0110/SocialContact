package com.qianyu.communicate.bukaSdk;

/**
 * Created by hwk on 2017/12/15.
 */
public class BukaUtils {

    /**
     * 校验文件名是否是图片
     *
     * @param docName
     * @return
     */
    public static boolean checkOutDocType(String docName) {
        String type = ".jpg .png .gif";
        String[] split = type.split(" ");
        for (String string : split) {
            if (docName.contains(string)) {
                return true;
            }
        }
        return false;
    }

}
