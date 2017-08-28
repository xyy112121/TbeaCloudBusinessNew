package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryUpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.VisualGraphResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.BackgroundInfoModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置店铺形象图
 */

public class SetVisualGraphActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.set_background_image)
    ImageView mImageView;
    List<LocalMedia> mSelectList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_background);
        ButterKnife.bind(this);
        initTopbar("形象图", "删除", this);
        getData();
    }

    private void getData() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        VisualGraphResonpseModel re = (VisualGraphResonpseModel) msg.obj;
                        if (re.isSuccess() && re.data != null) {
                            if (!"".equals(re.data.shoppictureinfo)) {
                                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + re.data.shoppictureinfo, mImageView);
                            }
                        } else {
                            ToastUtil.showMessage(re.getMsg());
                        }

                        break;
                    case ThreadState.ERROR:
                        ToastUtil.showMessage("操作失败!");

                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StoreManageAction action = new StoreManageAction();
                    VisualGraphResonpseModel model = action.getBackground();
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        delete();
    }

    @OnClick({R.id.open_album, R.id.open_camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_album:
                openImage();
                break;
            case R.id.open_camera:
                PictureSelector.create(mContext)
                        .openCamera(PictureMimeType.ofImage())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
        }
    }

    private void openImage() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .compress(true)
                .isCamera(false)
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .selectionMedia(mSelectList)// 是否传入已选图片 List<LocalMedia> list
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    ImageLoader.getInstance().displayImage("file://" + mSelectList.get(0).getCompressPath(), mImageView);
                    uploadImage();
                    break;
            }
        }
    }

    public void uploadImage() {
        if (mSelectList.size() > 0) {
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
            dialog.setText("请等待...");
            dialog.show();
            try {
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        dialog.dismiss();
                        switch (msg.what) {
                            case ThreadState.SUCCESS:
                                ResponseInfo model = (ResponseInfo) msg.obj;
                                if (model.isSuccess()) {
                                    ToastUtil.showMessage("操作成功！");
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
                            CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                            MeetingGalleryUpdateResponseModel model = action.uploadGallery(mSelectList);
                            if (model.isSuccess() && model.data.pictureinfo != null) {
                                StoreManageAction action1 = new StoreManageAction();
                                ResponseInfo model1 = action1.setBackground(model.data.pictureinfo.picturesavenames);
                                handler.obtainMessage(ThreadState.SUCCESS, model1).sendToTarget();
                            } else {
                                handler.sendEmptyMessage(ThreadState.ERROR);
                            }
                        } catch (Exception e) {
                            handler.sendEmptyMessage(ThreadState.ERROR);
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.showMessage("请选择需要上传的图片");
        }
    }

    private void delete() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_delete_dialog);
        dialog.show();
        dialog.setText("删除后不可恢复，确定删除么？");
        dialog.setConfirmBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        }, "否");
        dialog.setCancelBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
                dialog.setText("请等待...");
                dialog.show();
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        dialog.dismiss();
                        switch (msg.what) {
                            case ThreadState.SUCCESS:
                                ResponseInfo re = (ResponseInfo) msg.obj;
                                if (re.isSuccess()) {
                                    mImageView.setImageResource(R.drawable.icon_defult);
                                } else {
                                    ToastUtil.showMessage(re.getMsg());
                                }

                                break;
                            case ThreadState.ERROR:
                                ToastUtil.showMessage("操作失败!");
                                break;
                        }
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            StoreManageAction action = new StoreManageAction();
                            ResponseInfo re = action.deleteBackground();
                            handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                        } catch (Exception e) {
                            handler.sendEmptyMessage(ThreadState.ERROR);
                        }
                    }
                }).start();

            }
        }, "是");
    }
}
