package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.commodityManage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryUpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.ScanCodeCreateSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityAddRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityAddResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityCategoryResponseModel;
import com.example.programmer.tbeacloudbusiness.component.ClearEditText;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomOptionObjPicker;
import com.example.programmer.tbeacloudbusiness.model.Condition;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品添加
 */

public class StoreCommodityManageAddActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.commdity_add_name)
    PublishTextRowView mNameView;
    @BindView(R.id.commdity_add_categoryid)
    PublishTextRowView mCategoryView;
    @BindView(R.id.commdity_add_modespec)
    PublishTextRowView mModespecView;
    @BindView(R.id.commdity_add_price)
    PublishTextRowView mPriceView;
    @BindView(R.id.commdity_add_discountmoney)
    PublishTextRowView mDiscountmoneyView;
    @BindView(R.id.commdity_add_unit)
    PublishTextRowView mUnitView;
    @BindView(R.id.commdity_add_stocknumber)
    PublishTextRowView mStocknumberView;
    @BindView(R.id.commdity_adda_description)
    EditText mDescriptionView;
    @BindView(R.id.commdity_add_recommended)
    CheckBox mRecommendedView;
    @BindView(R.id.commdity_add_btn)
    Button mDeleteBtn;
    private LinearLayout mThumbListParentView;
    private LinearLayout mThumbListView;

    private LinearLayout mPictureListParentView;
    private LinearLayout mPictureListView;

    private String mFlag;//判断当前选择的图片是什么 ThumbList （商品缩略图） PictureList（详情图片）
    List<LocalMedia> mSelectPictureList = new ArrayList<>();
    List<LocalMedia> mSelectThumbList = new ArrayList<>();
    List<String> mPictureListPath = new ArrayList<>();
    List<String> mThumbListPath = new ArrayList<>();
    List<LocalMedia> mSelectList = new ArrayList<>();

    CommodityAddRequestModel mRequest = new CommodityAddRequestModel();

    List<Condition> categoryList = new ArrayList<>();

    private View parentLayout;
    private final int REQEST_TYPE_NORMS = 1004;
    private Condition mType;
    private Condition mNorms;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_commodity_manage_add);
        ButterKnife.bind(this);
        initTopbar("商品添加", "保存", this);
        initView();
        String flag = getIntent().getStringExtra("flag");
        if ("edit".equals(flag)) {
            initTopbar("商品编辑", "保存", this);
            mRequest.commodityid = getIntent().getStringExtra("commodityId");
            getData();
            mDeleteBtn.setVisibility(View.VISIBLE);
        }
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
                            CommodityAddResponseModel model = (CommodityAddResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.commodityinfo != null) {
                                    CommodityAddResponseModel.DataBean.CommodityinfoBean obj = model.data.commodityinfo;
                                    mRequest.categoryid = obj.categoryid;
                                    mRequest.moditymodelid = obj.moditymodelid;
                                    mRequest.modityspecid = obj.modityspecid;
                                    if ("1".equals(obj.recommended)) {
                                        mRecommendedView.setChecked(true);
                                    } else {
                                        mRecommendedView.setChecked(false);
                                    }
                                    mRequest.recommended = obj.recommended;
                                    mCategoryView.setValueText(obj.categoryname);
                                    mNameView.setValueText(obj.name);
                                    mPriceView.setValueText(obj.price);
                                    mDiscountmoneyView.setValueText(obj.discountmoney);
                                    mUnitView.setValueText(obj.unit);
                                    mStocknumberView.setValueText(obj.stocknumber);
                                    mDescriptionView.setText(obj.description);
                                    mModespecView.setValueText(obj.moditymodelname + " " + obj.modityspecname);
                                    if (obj.thumblist != null) {
                                        for (String t : obj.thumblist.split(",")) {
                                            mThumbListPath.add(t);
                                        }
                                        addImage(mThumbListPath, mThumbListView);
                                    }

                                    if (obj.thumblist != null) {
                                        for (String t : obj.picturelist.split(",")) {
                                            mPictureListPath.add(t);
                                        }
                                        addImage(mPictureListPath, mPictureListView);
                                    }
                                }

                            } else {
                                showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        StoreManageAction action = new StoreManageAction();
                        CommodityAddResponseModel model = action.getCommodity(mRequest.commodityid);
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

    private void initView() {
        parentLayout = findViewById(R.id.commdity_add_layout);
        getUserTypeList();

        mThumbListParentView = getViewById(R.id.commdity_add_thumb_layout);
        mThumbListView = (LinearLayout) mThumbListParentView.findViewById(R.id.select_image_layout);
        ((TextView) mThumbListParentView.findViewById(R.id.select_image_title)).setText("商品缩略图");
        mThumbListParentView.findViewById(R.id.select_image_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFlag = "ThumbList";//商品缩略图
                openImage();
            }
        });

        mPictureListParentView = getViewById(R.id.commdity_add_picture_layout);
        mPictureListView = (LinearLayout) mPictureListParentView.findViewById(R.id.select_image_layout);
        ((TextView) mPictureListParentView.findViewById(R.id.select_image_title)).setText("详情图片");
        mPictureListParentView.findViewById(R.id.select_image_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFlag = "PictureList";//详情图片
                openImage();
            }
        });
    }

    private void getUserTypeList() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        CommodityCategoryResponseModel model = (CommodityCategoryResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if (model.data.categorylist != null) {
                                for (CommodityCategoryResponseModel.DataBean.CategorylistBean item : model.data.categorylist) {
                                    Condition bean = new Condition(item.categoryid, item.categoryname);
                                    categoryList.add(bean);
                                }
                            }

                        } else {
                            showMessage(model.getMsg());
                        }
                        break;
                    case ThreadState.ERROR:
                        showMessage("操作失败！");
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StoreManageAction action = new StoreManageAction();
                    CommodityCategoryResponseModel model = action.getCommodityType();
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

    }

    @Override
    public void onClick(View view) {
        sumbit();
    }

    @OnClick({R.id.commdity_add_categoryid, R.id.commdity_add_modespec, R.id.commdity_add_btn})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.commdity_add_categoryid:
                try {
                    if (categoryList != null) {
                        CustomOptionObjPicker optionPicker = new CustomOptionObjPicker(mContext, "分类选择", categoryList);
                        optionPicker.setTextSize(14);
                        if (categoryList.size() > 2) {
                            optionPicker.setSelectedIndex(1);
                        }


                        optionPicker.setOnOptionPickListener(new CustomOptionObjPicker.OnOptionPickListener() {
                            @Override
                            public void onOptionPicked(Condition option) {
                                mRequest.categoryid = option.getId();
                                ((PublishTextRowView) view).setValueText(option.getName());
                            }
                        });
                        optionPicker.show();
                        optionPicker.setGravity(Gravity.CENTER);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.commdity_add_modespec:
                Intent intent = new Intent(mContext, ScanCodeCreateSelectActivity.class);
                intent.putExtra("type", mType);
                intent.putExtra("withall", "1");
                intent.putExtra("norms", mNorms);
                startActivityForResult(intent, REQEST_TYPE_NORMS);
                break;
            case R.id.commdity_add_btn:
                delete();
                break;
        }
    }

    private void sumbit() {
        if (mRecommendedView.isChecked()) {
            mRequest.recommended = "1";
        } else {
            mRequest.recommended = "0";
        }
        mRequest.name = mNameView.getValueText();
        mRequest.price = mPriceView.getValueText();
        mRequest.discountmoney = mDiscountmoneyView.getValueText();
        mRequest.unit = mUnitView.getValueText();
        mRequest.stocknumber = mStocknumberView.getValueText();
        mRequest.description = mDescriptionView.getText() + "";
        String picture = "";
        if (mPictureListPath != null) {
            for (String item : mPictureListPath) {
                picture += ",";
                picture += item;
            }
        }
        mRequest.picturelist += picture;

        String thumb = "";
        if (mThumbListPath != null) {
            for (String item : mThumbListPath) {
                thumb += ",";
                thumb += item;
            }
        }
        mRequest.thumblist += thumb;

        if ("".equals(mRequest.name) || "".equals(mRequest.price) || "".equals(mRequest.discountmoney) ||
                "".equals(mRequest.unit) || "".equals(mRequest.description)
                || "".equals(mRequest.thumblist) || mRequest.thumblist == null || mRequest.picturelist == null || "".equals(mRequest.picturelist) ||
                mRequest.categoryid == null || mRequest.moditymodelid == null || mRequest.modityspecid == null) {
            showMessage("请补全资料");
            return;
        }

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
                                finish();
                            }
                            showMessage(model.getMsg());
                            break;
                        case ThreadState.ERROR:
                            showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        StoreManageAction action = new StoreManageAction();
                        ResponseInfo model1 = action.addCommodity(mRequest);
                        handler.obtainMessage(ThreadState.SUCCESS, model1).sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void openImage() {
        int pictureConfig = PictureConfig.MULTIPLE;
        int number = 6;
        if ("ThumbList".equals(mFlag)) {
            mSelectList = mSelectThumbList;
        } else if ("PictureList".equals(mFlag)) {
            mSelectList = mSelectPictureList;
            number = 20;
        }
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .compress(true)
                .isCamera(true)
                .maxSelectNum(number)
                .selectionMode(pictureConfig)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .selectionMedia(mSelectList)// 是否传入已选图片 List<LocalMedia> list
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    if ("ThumbList".equals(mFlag)) {
                        mSelectThumbList = mSelectList;
                        uploadImage(mSelectThumbList, mThumbListView);
                    } else if ("PictureList".equals(mFlag)) {
                        mSelectPictureList = mSelectList;
                        uploadImage(mSelectPictureList, mPictureListView);
                    }
                    break;
                case REQEST_TYPE_NORMS:
                    mType = (Condition) data.getSerializableExtra("type");
                    mNorms = (Condition) data.getSerializableExtra("norms");
                    String type = "";
                    String norm = "";
                    if (mType != null) {
                        mRequest.moditymodelid = mType.getId();
                        type = mType != null ? mType.getName() + "  " : "";
                    }

                    if (mNorms != null) {
                        mRequest.modityspecid = mNorms.getId();
                        norm = mNorms != null ? mNorms.getName() : "";
                    }
                    mModespecView.setValueText(type + norm);
                    break;
            }
        }
    }

    private void addImage(final List<String> list, final LinearLayout parentLayout) {
        parentLayout.removeAllViews();
        for (final String item : list) {
            final FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_store_commodity_add_image, null);
            ImageView iv = (ImageView) layout.findViewById(R.id.shop_dynamic_add_list_item_image);
            ImageView deleteView = (ImageView) layout.findViewById(R.id.shop_dynamic_add_list_item_image_delete);
            ImageLoader.getInstance().displayImage(item, iv);
            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(item);
                    parentLayout.removeView(layout);
                }
            });
            parentLayout.addView(layout);
        }
    }

    private void uploadImage(final List<LocalMedia> list, final LinearLayout parentLayout) {
        if (list.size() > 0) {
            try {
                parentLayout.removeAllViews();
                for (final LocalMedia item : list) {
                    final FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_shop_dynamic_add_image_item, null);
                    ImageView iv = (ImageView) layout.findViewById(R.id.shop_dynamic_add_list_item_image);
                    ImageView deleteView = (ImageView) layout.findViewById(R.id.shop_dynamic_add_list_item_image_delete);
                    ImageLoader.getInstance().displayImage("file://" + item.getCompressPath(), iv);
                    deleteView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            list.remove(item);
                            parentLayout.removeView(layout);
                        }
                    });
                    layout.setLayoutParams(new FrameLayout.LayoutParams(DensityUtil.dip2px(mContext, 100), DensityUtil.dip2px(mContext, 100)));
                    parentLayout.addView(layout);
                }
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case ThreadState.SUCCESS:
                                MeetingGalleryUpdateResponseModel model = (MeetingGalleryUpdateResponseModel) msg.obj;
                                if (model.isSuccess() && model.data.pictureinfo != null) {
                                    String name = model.data.pictureinfo.picturesavenames;
                                    if ("ThumbList".equals(mFlag)) {
                                        mRequest.thumblist = name;
                                    } else if ("PictureList".equals(mFlag)) {
                                        mRequest.picturelist = name;
                                    }
                                } else {
                                    showMessage(model.getMsg());
                                }
                                break;
                            case ThreadState.ERROR:
                                showMessage("操作失败！");
                                break;
                        }
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                            MeetingGalleryUpdateResponseModel model = action.uploadImage(list);
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
                                    Intent intent = new Intent(mContext, StoreCommodityManageListActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    showMessage(re.getMsg());
                                }

                                break;
                            case ThreadState.ERROR:
                                showMessage("操作失败!");
                                break;
                        }
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            StoreManageAction action = new StoreManageAction();
                            ResponseInfo re = action.deleteCommodity(mRequest.commodityid);
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
