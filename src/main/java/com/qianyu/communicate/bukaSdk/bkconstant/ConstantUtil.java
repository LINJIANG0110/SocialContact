package com.qianyu.communicate.bukaSdk.bkconstant;


/**
 * Created by tangxiaowei on 2017/8/8.
 */

public class ConstantUtil {
    public static final String WHITE_BOARD;
    static {
//        if (ApplicationLike.IS_DEBUG) {
//            WHITE_BOARD = "http://test.live.buka.tv/amals_cdn/hb/Whiteboard.v4.html?single_page=20&bg=3B3D4F&ptv=4";
//        } else {
            WHITE_BOARD = "http://live.buka.tv/amals_cdn/hb/Whiteboard.v4.html?single_page=20&bg=3B3D4F&ptv=4";
//        }
    }
    //文件类型
    public static final String FILE_PPT = "ppt";
    public static final String FILE_PPTX = "pptx";
    public static final String FILE_DOC = "doc";
    public static final String FILE_DOCX = "docx";
    public static final String FILE_XLS = "xls";
    public static final String FILE_XLSX = "xlsx";
    public static final String FILE_PDF = "pdf";
    public static final String FILE_PDFX = "pdfx";
    public static final String FILE_PNG = "png";
    public static final String FILE_JPG = "jpg";
    public static final String FILE_JPEG = "jpeg";
    public static final String FILE_MP4 = "mp4";
    public static final String FILE_FLV = "flv";
    public static final String FILE_WMV = "wmv";
    public static final String FILE_M3U8 = "m3u8";
    //文档信令
    public static final int RPC_HTML_PPT_URL = 4003;                //H5接收
    public static final int RPC_HTML_PPT_ACTION = 4010;             //ppt action
}

