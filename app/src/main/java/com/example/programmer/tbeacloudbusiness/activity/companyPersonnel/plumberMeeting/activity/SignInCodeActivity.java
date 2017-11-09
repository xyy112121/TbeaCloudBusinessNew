package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.SignInCodeResonseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingViewResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.programmer.tbeacloudbusiness.utils.permissonutil.MainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by programmer on 2017/11/9.
 */

public class SignInCodeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.signin_code_meeting_code)
    PublishTextRowView mCodeView;
    @BindView(R.id.signin_code_time)
    PublishTextRowView mTimeView;
    @BindView(R.id.signin_code_addr)
    PublishTextRowView mAddrView;
    @BindView(R.id.signin_code_picture)
    ImageView mPictureView;

    String pictureUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_code);
        ButterKnife.bind(this);
        initTopbar("签到码", "下载", this);
        getData();

    }


    private void getData() {
        final String id = getIntent().getStringExtra("id");
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            SignInCodeResonseModel model = (SignInCodeResonseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data != null && model.data.meetinginfo != null) {
                                    SignInCodeResonseModel.Data.Meetinginfo info = model.data.meetinginfo;
                                    mCodeView.setValueText(info.meetingcode);
                                    mTimeView.setValueText(info.meetingtime);
                                    mAddrView.setValueText(info.meetingplace);
                                    ImageLoader.getInstance().displayImage(info.meetingqrcodepicture, mPictureView);
                                    pictureUrl = info.meetingqrcodepicture;
                                }
                            } else {
                                ToastUtil.showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            ToastUtil.showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        PlumberMeetingAction action = new PlumberMeetingAction();
                        SignInCodeResonseModel model = action.getSignInCode(id);
                        handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        //多个权限获取
        checkPermission(new CheckPermListener() {
            @Override
            public void Granted() {
                if (!TextUtils.isEmpty(pictureUrl)) {
                    saveBitmap(pictureUrl);
                } else {
                    ToastUtil.showMessage("下载失败！");
                }
            }

            @Override
            public void Denied() {
                Toast.makeText(mContext, "请开启权限，才可以正常操作！", Toast.LENGTH_SHORT).show();
                //如果选择不在提示
            }
        }, "请求获取读取文件权限", Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public void saveBitmap(final String imageurl) {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.ERROR:
                        ToastUtil.showMessage("下载失败！");
                        break;
                    case ThreadState.SUCCESS:
                        Bitmap bm = (Bitmap) msg.obj;
                        File appDir = new File(Environment.getExternalStorageDirectory(), "");
                        if (!appDir.exists()) {
                            appDir.mkdir();
                        }
                        String fileName = "tbea" + System.currentTimeMillis() + ".jpg";
                        File f = new File(appDir, fileName);
                        if (f.exists()) {
                            f.delete();
                        }
                        try {
                            FileOutputStream out = new FileOutputStream(f);
                            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
                            out.flush();
                            out.close();

                            //把文件插入图库
                            MediaStore.Images.Media.insertImage(getContentResolver(), f.getAbsolutePath(), fileName, null);
                            //保存图片后发送广播通知更新数据库  f
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + f.getPath())));
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ToastUtil.showMessage("下载成功！");
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection connection = null;
                try {
                    url = new URL(imageurl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(6000); //超时设置
                    connection.setDoInput(true);
                    connection.setUseCaches(false); //设置不使用缓存
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    handler.obtainMessage(ThreadState.SUCCESS, bitmap).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }


}
