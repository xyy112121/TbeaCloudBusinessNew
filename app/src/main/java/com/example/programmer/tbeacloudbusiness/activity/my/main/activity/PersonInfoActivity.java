package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity.AddrSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryUpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.PersonInfoRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.PersonInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.UploadImageResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * 个人信息
 */

public class PersonInfoActivity extends BaseActivity {
    @BindView(R.id.person_info_account)
    PublishTextRowView mAccountView;
    @BindView(R.id.person_info_header)
    ImageView mHeaderView;
    @BindView(R.id.person_info_header_layout)
    RelativeLayout mHeaderLayout;
    @BindView(R.id.person_info_name)
    PublishTextRowView mNameView;
    @BindView(R.id.person_info_sex)
    PublishTextRowView mSexView;
    @BindView(R.id.person_info_age)
    PublishTextRowView mAgeView;
    @BindView(R.id.person_info_addr)
    PublishTextRowView mAddrView;
    @BindView(R.id.person_info_phone)
    PublishTextRowView mPhoneView;
    @BindView(R.id.person_info_zone)
    PublishTextRowView mZoneView;

    @BindView(R.id.person_info_state)
    PublishTextRowView mStateView;

    PersonInfoRequestModel mRequest = new PersonInfoRequestModel();
    PersonInfoResponseModel.DataBean.UserinfoBean mModel;
    private final int RESULT_ADDR = 1002;
    List<LocalMedia> mSelectList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
        initTopbar("");
        getDate();
    }


    public void save(final PersonInfoRequestModel request, final String flag) {
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
                            ResponseInfo re = (ResponseInfo) msg.obj;
                            ToastUtil.showMessage(re.getMsg());
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
                        UserAction action = new UserAction();
                        if (mSelectList.size() > 0 && "picture".equals(flag)) {
                            ResponseInfo re = action.uploadImage(mSelectList);
                            if (re.isSuccess()) {
                                Gson gson = new GsonBuilder().create();
                                String json = gson.toJson(re.data);
                                UploadImageResponseModel model = gson.fromJson(json, UploadImageResponseModel.class);
                                if (model.pictureinfo != null) {
                                    request.picture = model.pictureinfo.picturesavenames;
                                    ResponseInfo result = action.setPersonInfo(request);
                                    handler.obtainMessage(ThreadState.SUCCESS, result).sendToTarget();
                                } else {
                                    handler.sendEmptyMessage(ThreadState.ERROR);
                                }
                            } else {
                                handler.sendEmptyMessage(ThreadState.ERROR);
                            }
                        } else {
                            ResponseInfo re = action.setPersonInfo(request);
                            handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                        }
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据
     */
    public void getDate() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        PersonInfoResponseModel re = (PersonInfoResponseModel) msg.obj;
                        if (re.isSuccess() && re.data != null) {
                            mModel = re.data.userinfo;
                            if (mModel != null) {
                                mAccountView.setValueText(mModel.username);
                                mNameView.setValueText(mModel.usertype);
                                ImageLoader.getInstance().displayImage(mModel.thumbpicture, mHeaderView);
                                mSexView.setValueText(mModel.sex);
                                mAgeView.setValueText(mModel.age);
                                if (mModel.province != null) {
                                    mAddrView.setValueText(mModel.province + mModel.city + mModel.zone + mModel.addr);
                                }

                                mPhoneView.setValueText(mModel.mobielnumber);
                                mZoneView.setValueText(mModel.companyname);
                                mStateView.setValueText(mModel.identifystatus);
                            }


//                            mRequest.address = mModel.address;
//                            mRequest.province = mModel.province;
//                            mRequest.city = mModel.city;
//                            mRequest.zone = mModel.zone;
//                            mRequest.sex = mModel.sex;
//                            mRequest.thumbpicture = mModel.thumbpicture;

                        } else {
                            ToastUtil.showMessage(re.getMsg());
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
                    UserAction userAction = new UserAction();
                    PersonInfoResponseModel re = userAction.getPersonInfo();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_ADDR://地址
                    mRequest.province = data.getStringExtra("province");
                    mRequest.city = data.getStringExtra("city");
                    mRequest.zone = data.getStringExtra("county");
                    mRequest.address = data.getStringExtra("addrInfo");
                    String addr = data.getStringExtra("addr");
                    mAddrView.setValueText(addr);
                    save(mRequest, "");
                    break;
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    ImageLoader.getInstance().displayImage("file://" + mSelectList.get(0).getCompressPath(), mHeaderView);
                    save(mRequest, "picture");
                    break;
            }
        }

    }

    /**
     * 显示性别
     */
    private void showSexPicker() {
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        List<KeyValueBean> sexList = new ArrayList<>();
        sexList.add(new KeyValueBean("male", "先生"));
        sexList.add(new KeyValueBean("female", "女士"));
        CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, R.layout.pop_window_header1, R.layout.pop_window_tv, sexList, "");
        popWindow1.setItemClick2(new CustomPopWindow1.ItemClick2() {
            @Override
            public void onItemClick2(KeyValueBean bean) {
                mRequest.sex = bean.getKey();
                mSexView.setValueText(bean.getValue());
                save(mRequest, "");
            }
        });
    }


    /**
     * 显示选择拍照，图库
     */
    private void showImage() {
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        List<String> photoOperationList = new ArrayList<>();
        photoOperationList.add("拍照上传");
        photoOperationList.add("本地上传");
        CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, R.layout.pop_window_header1, R.layout.pop_window_tv, photoOperationList);
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
                if ("本地上传".equals(text)) {
                    openImage();
                } else {
                    PictureSelector.create(mContext)
                            .openCamera(PictureMimeType.ofImage())
                            .compress(true)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }

            }
        });
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

    @OnClick({R.id.top_left, R.id.person_info_header_layout, R.id.person_info_sex, R.id.person_info_addr, R.id.person_info_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.person_info_header_layout:
                showImage();
                break;
            case R.id.person_info_sex:
                showSexPicker();
                break;
            case R.id.person_info_addr:
                Intent intent = new Intent(mContext, AddrSelectActivity.class);
                intent.putExtra("province", mModel.province);
                intent.putExtra("city", mModel.city);
                intent.putExtra("county", mModel.zone);
                intent.putExtra("addr", mModel.addr);
                startActivityForResult(intent, RESULT_ADDR);
                break;
            case R.id.person_info_phone:
                break;
            case R.id.top_left:
                finish();
                break;
        }
    }
}
