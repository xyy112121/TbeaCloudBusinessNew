package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryUpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityManageListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.DynamicListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.RotateADEditRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.RotateADListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.RotateAdEditResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.StoreIntroduceResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.RealNameAuthenticationActivity;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
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

import static android.R.attr.data;

/**
 * 轮换广告修改
 */

public class RotateADEditActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rotate_ad_edit_title)
    PublishTextRowView mTitleView;
    @BindView(R.id.rotate_ad_edit_link_page)
    PublishTextRowView mLinkPageView;
    @BindView(R.id.rotate_ad_edit_url)
    LinearLayout mUrlView;
    @BindView(R.id.rotate_ad_edit_pic)
    ImageView mPicView;
    @BindView(R.id.rotate_ad_edit_btn)
    Button mDeleteBtn;
    private String mFlag;
    private final int REQUEST_LINK = 1001;

    List<LocalMedia> mSelectList = new ArrayList<>();

    RotateADEditRequestModel mRequest = new RotateADEditRequestModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_ad_edit);
        ButterKnife.bind(this);
        mFlag = getIntent().getStringExtra("flag");
        if ("edit".equals(mFlag)) {
            mDeleteBtn.setVisibility(View.VISIBLE);
            mRequest.advertiseid = getIntent().getStringExtra("advertiseId");
            getData();
        }
        initTopbar("轮换广告", "保存", this);
        findViewById(R.id.rotate_ad_edit_url_view).setVisibility(View.GONE);
    }

    /**
     * 从服务器获取数据
     */
    private void getData() {
        try {
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
            dialog.setText("请等待...");
            dialog.show();
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            RotateAdEditResponseModel model = (RotateAdEditResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.shopadvertiseinfo != null) {
                                    RotateAdEditResponseModel.DataBean.ShopadvertiseinfoBean info = model.data.shopadvertiseinfo;
                                    mRequest.urltype = info.urltype;
                                    mRequest.picture = info.picture;
                                    mTitleView.setValueText(info.title);
                                    if ("news".equals(info.urltype)) {
                                        RotateAdEditResponseModel.DataBean.NewBean obj = model.data.newsinfo;
                                        mRequest.url = obj.newsid;
                                        mUrlView.removeAllViews();
                                        mRequest.url = obj.newsid;
                                        View view = getLayoutInflater().inflate(R.layout.activity_rotate_ad_edit_shop_dynamic_item, null);
                                        ImageView pictureView = (ImageView) view.findViewById(R.id.news_item_picture);
                                        ((TextView) view.findViewById(R.id.news_item_time)).setText(obj.time);
                                        ((TextView) view.findViewById(R.id.news_item_title)).setText(obj.title);
                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, pictureView);
                                        mUrlView.addView(view);
                                        mLinkPageView.setValueText("店铺动态");

                                    } else {
                                        RotateAdEditResponseModel.DataBean.CommodityinfoBean obj = model.data.commodityinfo;
                                        mRequest.url = obj.commodityid;
                                        mUrlView.removeAllViews();
                                        View view = getLayoutInflater().inflate(R.layout.activity_rotate_ad_edit_commodity_item, null);
                                        ImageView pictureView = (ImageView) view.findViewById(R.id.commdity_item_picture);
                                        ((TextView) view.findViewById(R.id.commdity_item_title)).setText(obj.name);
                                        ((TextView) view.findViewById(R.id.commdity_item_money)).setText(obj.price);
                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, pictureView);
                                        mUrlView.addView(view);
                                        mLinkPageView.setValueText("店铺商品");
                                    }

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
                        StoreManageAction action = new StoreManageAction();
                        RotateAdEditResponseModel model = action.getRotateAd(mRequest.advertiseid);
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
        sumbit();
    }

    @OnClick({R.id.rotate_ad_edit_link_page, R.id.rotate_ad_edit_btn, R.id.rotate_ad_edit_pic_tv, R.id.rotate_ad_edit_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rotate_ad_edit_link_page:
                Intent intent = new Intent(mContext, RotateADLinkPageListActivity.class);
                startActivityForResult(intent, REQUEST_LINK);
                break;
            case R.id.rotate_ad_edit_btn:
                delete();

                break;
            case R.id.rotate_ad_edit_pic_tv:
            case R.id.rotate_ad_edit_pic:
                openImage();
                break;
        }
    }

    private void sumbit() {
        if (mSelectList.size() > 0) {
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
            dialog.setText("请等待...");
            dialog.show();

            mRequest.title = mTitleView.getValueText();
            try {
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        dialog.dismiss();
                        switch (msg.what) {
                            case ThreadState.SUCCESS:
                                ResponseInfo model = (ResponseInfo) msg.obj;
                                if (model.isSuccess()) {
                                    Intent intent = new Intent(mContext, RotateADEditSucceedActivity.class);
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
                                mRequest.picture = model.data.pictureinfo.picturesavenames;
                                StoreManageAction userAction = new StoreManageAction();
                                ResponseInfo model1 = userAction.editRotateAd(mRequest);
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
            ToastUtil.showMessage("请选择图片！");
        }
    }

    private void openImage() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .compress(true)
                .isCamera(true)
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .selectionMedia(mSelectList)// 是否传入已选图片 List<LocalMedia> list
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
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
                                    finish();
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
                            ResponseInfo re = action.deleteRotateAd(mRequest.advertiseid);
                            handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                        } catch (Exception e) {
                            handler.sendEmptyMessage(ThreadState.ERROR);
                        }
                    }
                }).start();

            }
        }, "是");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    ImageLoader.getInstance().displayImage("file://" + mSelectList.get(0).getCompressPath(), mPicView);
                    break;
                case REQUEST_LINK:
                    String type = data.getStringExtra("type");
                    mRequest.urltype = type;
                    findViewById(R.id.rotate_ad_edit_url_view).setVisibility(View.VISIBLE);

                    if ("news".equals(type)) {
                        DynamicListResponseModel.DataBean.NewslistBean obj = (DynamicListResponseModel.DataBean.NewslistBean) data.getSerializableExtra("obj");
                        mUrlView.removeAllViews();
                        mRequest.url = obj.newsid;
                        View view = getLayoutInflater().inflate(R.layout.activity_rotate_ad_edit_shop_dynamic_item, null);
                        ImageView pictureView = (ImageView) view.findViewById(R.id.news_item_picture);
                        ((TextView) view.findViewById(R.id.news_item_time)).setText(obj.time);
                        ((TextView) view.findViewById(R.id.news_item_title)).setText(obj.title);
                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, pictureView);
                        mUrlView.addView(view);
                        mLinkPageView.setValueText("店铺动态");

                    } else {
                        CommodityManageListResponseModel.DataBean.CommoditylistBean obj = (CommodityManageListResponseModel.DataBean.CommoditylistBean) data.getSerializableExtra("obj");
                        mUrlView.removeAllViews();
                        View view = getLayoutInflater().inflate(R.layout.activity_rotate_ad_edit_commodity_item, null);
                        ImageView pictureView = (ImageView) view.findViewById(R.id.commdity_item_picture);
                        ((TextView) view.findViewById(R.id.commdity_item_title)).setText(obj.commodityname);
                        ((TextView) view.findViewById(R.id.commdity_item_money)).setText(obj.price);
                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, pictureView);
                        mUrlView.addView(view);
                        mLinkPageView.setValueText("店铺商品");
                    }
                    break;
            }
        }
    }
}
