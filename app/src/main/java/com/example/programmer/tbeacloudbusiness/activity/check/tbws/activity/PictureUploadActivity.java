package com.example.programmer.tbeacloudbusiness.activity.check.tbws.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity.MeetingGalleryImageShowActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryUpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.SuccessActivity;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 检测图片数据上传
 */

public class PictureUploadActivity extends BaseActivity implements View.OnClickListener {
    List<LocalMedia> mSelectList = new ArrayList<>();
    GridAdapter mGridAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ck_sr_picture_upload);
        initTopbar(" 数据上传", "保存", this);
        initView();
        String code = getIntent().getStringExtra("code");
        ((PublishTextRowView) findViewById(R.id.sr_upload_code)).setValueText(code);
    }

    private void initView() {
        GridView gridView = (GridView) findViewById(R.id.sr_upload_image_gridView);
        mGridAdapter = new GridAdapter();
        gridView.setAdapter(mGridAdapter);
        mGridAdapter.addAll(mSelectList);

    }

    @Override
    public void onClick(View v) {
        uploadImage();
    }

    public void uploadImage() {
        final String assignid = getIntent().getStringExtra("assignid");
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
                                    Intent intent = getIntent();
                                    intent.setClass(mContext, SuccessActivity.class);
                                    intent.putExtra("flag", "pictureUpload");//派单
                                    startActivity(intent);
                                    finish();
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
                            MeetingGalleryUpdateResponseModel model = action.uploadImage(mSelectList);
                            if (model.isSuccess() && model.data.pictureinfo != null) {
                                SubscribeAction action1 = new SubscribeAction();
                                ResponseInfo model1 = action1.uploadPicture(assignid, model.data.pictureinfo.picturesavenames);
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


    private void openImage() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(8)// 最大图片选择数量 int
                .compress(true)
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
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
                    mGridAdapter.removeAll();
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    mGridAdapter.addAll(mSelectList);
                    break;
                case 1000:
                    mSelectList = (List<LocalMedia>) data.getSerializableExtra("images");
                    mGridAdapter.removeAll();
                    mGridAdapter.addAll(mSelectList);
                    break;
            }
        }
    }


    public class GridAdapter extends BaseAdapter {
        private List<LocalMedia> mList = new ArrayList<>();


        public void addAll(List<LocalMedia> list) {
            this.mList.addAll(list);
            if (mList.size() < 8) {
                this.mList.add(mList.size(), null);
            }
            notifyDataSetChanged();
        }

        public void removeAll() {
            this.mList.clear();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int postion, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_shop_dynamic_add_image_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            final LocalMedia obj = mList.get(postion);
            int displayWidth = UtilAssistants.getDisplayWidth(mContext);
            holder.mImageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (displayWidth / 4)));
            if (obj == null) {
                holder.mImageView.setImageResource(R.drawable.icon_take_photos);
                holder.mDeleteView.setVisibility(View.GONE);
            } else {
                ImageLoader.getInstance().displayImage("file://" + obj.getCompressPath(), holder.mImageView);
                holder.mDeleteView.setVisibility(View.VISIBLE);
            }
            holder.mImageView.setTag(postion);


            holder.mDeleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(postion);
                    mSelectList.remove(postion);
                    notifyDataSetChanged();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (postion == mList.size() - 1) {
                        openImage();
//                    }
//                    else {
//                        Intent intent = new Intent(mContext, MeetingGalleryImageShowActivity.class);
//                        intent.putExtra("images", (Serializable) mSelectList);
//                        startActivityForResult(intent, 1000);
//                    }
                }
            });
            return view;
        }

        class ViewHolder {
            @BindView(R.id.shop_dynamic_add_list_item_image)
            ImageView mImageView;
            @BindView(R.id.shop_dynamic_add_list_item_image_delete)
            ImageView mDeleteView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
