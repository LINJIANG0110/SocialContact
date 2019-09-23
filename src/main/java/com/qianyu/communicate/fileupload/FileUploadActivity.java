package com.qianyu.communicate.fileupload;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.wight.AlertDialog;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;

public class FileUploadActivity extends BaseActivity {
    @InjectView(R.id.listview)
    ListView mListview;
    @InjectView(R.id.mEmptyLL)
    LinearLayout mEmptyLL;
    private List<AddFileInfo> list = new ArrayList<AddFileInfo>();
    //    private String filePath = Environment.getExternalStorageDirectory().toString() + File.separator;
    private String filePath = "/sdcard/tencent/MicroMsg" + File.separator;
    private static Adapter adapter;
    private ACache aCache;
    private String fileDate = "";
    private UserBean userBean;

    @Override
    public int getRootViewId() {
        return R.layout.activity_file_upload;
    }

    @Override
    public void setViews() {
        if (getIntent() != null) {
            userBean = ((UserBean) getIntent().getSerializableExtra("userBean"));
        }
        setTitleTv("微信本地文档");
        mListview.setEmptyView(mEmptyLL);
        //    /sdcard/tencent/MicroMsg
        //   /==========filePath============/storage/emulated/0/
        AppLog.e(File.separator + "==========filePath============" + filePath);
    }

    @Override
    public void initData() {
        aCache = ACache.get(this);
        onLoad();
    }

    public void onLoad() {
        adapter = new Adapter(FileUploadActivity.this);
//        String string = aCache.getAsString("file");
        String string = null;
        if (string == null) {
            Tools.showWaitDialog(this, "搜索本地文件中...");
            new MyThread().start();
        } else {
            String[] str = string.split(",");
            for (int i = 0; i < str.length; i++) {
                Log.i("file", str[i]);
                File f = new File(str[i]);
                if (f.exists()) {
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(f);
                        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date(f.lastModified()));
                        AddFileInfo info = new AddFileInfo(f.getName(), Long.valueOf(fis.available()), time, false, f.getAbsolutePath());
                        fileDate += f.getAbsolutePath() + ",";
                        list.add(info);
                    } catch (Exception e) {
                        return;
                    }
                }
            }
        }
        mListview.setOnItemClickListener(onItemClickListener);
        mListview.setAdapter(adapter);
    }

    /****
     * 递归算法获取本地文件
     * @param path
     */
    private void doSearch(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fileArray = file.listFiles();
                for (File f : fileArray) {
                    if (f.isDirectory()) {
                        doSearch(f.getPath());
                    } else {
                        if (f.getName().endsWith(".ppt") || f.getName().endsWith(".pptx") || f.getName().endsWith(".docx")
                                || f.getName().endsWith(".doc") || f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx")
                                || f.getName().endsWith(".pdf") || f.getName().endsWith(".pdfx") || f.getName().endsWith(".mp4")
                                || f.getName().endsWith(".flv") || f.getName().endsWith(".wmv") || f.getName().endsWith(".m3u8")) {
                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(f);
                                String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date(f.lastModified()));
                                AddFileInfo info = new AddFileInfo(f.getName(), Long.valueOf(fis.available()), time, false, f.getAbsolutePath());
                                list.add(info);
                                fileDate += f.getAbsolutePath() + ",";
                                Log.i("url", f.getAbsolutePath() + "--" + f.getName() + "---" + fis.available() + "--");
                                System.out.println("文件名称：" + f.getName());
                                System.out.println("文件是否存在：" + f.exists());
                                System.out.println("文件的相对路径：" + f.getPath());
                                System.out.println("文件的绝对路径：" + f.getAbsolutePath());
                                System.out.println("文件可以读取：" + f.canRead());
                                System.out.println("文件可以写入：" + f.canWrite());
                                System.out.println("文件上级路径：" + f.getParent());
                                System.out.println("文件大小：" + f.length() + "B");
                                System.out.println("文件最后修改时间：" + new Date(f.lastModified()));
                                System.out.println("是否是文件类型：" + f.isFile());
                                System.out.println("是否是文件夹类型：" + f.isDirectory());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                doSearch(filePath);
                Thread.sleep(2000);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = 1;
                handler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Tools.dismissWaitDialog();
                adapter.notifyDataSetChanged();
                if (!TextUtils.isEmpty(fileDate) && fileDate.length() > 0) {
                    aCache.put("file", fileDate.substring(0, (fileDate.length() - 1)), 600);
                }
            }
        }
    };

    private AlertDialog dialog;
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String path = list.get(position).getPath();
            AlertDialog.Builder builder = new AlertDialog.Builder(FileUploadActivity.this).setTitle("提示").setMessage("确定清上传该文件？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            dialog.dismiss();
                            Log.e("info", "=======path=====" + path);
                            User user = MyApplication.getInstance().user;
                            if (user == null) {
                                ToastUtil.shortShowToast("请先登录...");
                                startActivity(new Intent(FileUploadActivity.this, RegistActivity.class));
                                return;
                            }
                            Tools.showDialog(FileUploadActivity.this);
//                            NetUtils.getInstance().fileUpload(userBean.getFid(), new File(path), new UploadListener() {
//                                @Override
//                                public void onUISuccess(final String res) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            AppLog.e("==========onUISuccess=======" + res);
//                                            ToastUtil.shortShowToast("上传成功!");
//                                            Tools.dismissWaitDialog();
//                                            setResult(1002);
//                                            finish();
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onUIFailure(Exception e) {
//                                    ToastUtil.shortShowToast("上传失败!");
//                                    Tools.dismissWaitDialog();
//                                    AppLog.e("==========onUIFailure=======" + e.getMessage());
//                                }
//
//                                @Override
//                                public void onUIProgress(Progress progress) {
//
//                                }
//                            });
                        }

                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });
            dialog = builder.create();
            dialog.show();
        }
    };

    /****
     *计算文件大小
     * @param length
     * @return
     */
    public static String ShowLongFileSzie(Long length) {
        if (length >= 1048576) {
            return (length / 1048576) + "MB";
        } else if (length >= 1024) {
            return (length / 1024) + "KB";
        } else if (length < 1024) {
            return length + "B";
        } else {
            return "0KB";
        }
    }

    class Adapter extends BaseAdapter {
        private int[] img_word = new int[]{R.mipmap.wenjian_word, R.mipmap.wenjian_exel, R.mipmap.wenjian_ppt, R.mipmap.wenjian_pdf, R.mipmap.yunduan_wendang};
        private LayoutInflater inflater;

        public Adapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.item_mytask_file_listview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            AddFileInfo info = (AddFileInfo) getItem(position);
            if (info.getName().endsWith(".doc") || info.getName().endsWith(".docx")) {
                holder.iv_img.setImageResource(img_word[0]);
            } else if (info.getName().endsWith(".xls") || info.getName().endsWith(".xlsx")) {
                holder.iv_img.setImageResource(img_word[1]);
            } else if (info.getName().endsWith(".ppt") || info.getName().endsWith(".pptx")) {
                holder.iv_img.setImageResource(img_word[2]);
            } else if (info.getName().endsWith(".pdf") || info.getName().endsWith(".pdfx")) {
                holder.iv_img.setImageResource(img_word[3]);
            } else {
                holder.iv_img.setImageResource(img_word[4]);
            }
            holder.tv_name.setText(info.getName());
            holder.size.setText(ShowLongFileSzie(info.getSize()));
            holder.time.setText(info.getTime());
            return convertView;
        }
    }

    class ViewHolder {
        private ImageView iv_img;
        private TextView tv_name;
        private TextView size;
        private TextView time;

        public ViewHolder(View view) {
            iv_img = (ImageView) view.findViewById(R.id.item_file_img);
            tv_name = (TextView) view.findViewById(R.id.item_file_name);
            size = (TextView) view.findViewById(R.id.item_file_size);
            time = (TextView) view.findViewById(R.id.item_file_time);
        }
    }

}
