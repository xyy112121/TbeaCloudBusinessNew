package com.example.programmer.tbeacloudbusiness.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryUpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.CompletionDataResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.RelaNameAuthenticationRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.RelaNameAuthenticationResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomAddressPicker;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.AssetsUtils;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;
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
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * 企业认证
 */

public class RealNameAuthenticationActivity extends BaseActivity {
    @BindView(R.id.real_name_companyName)
    EditText mCompanyNameView;
    @BindView(R.id.real_name_companyLisenceCode)
    EditText mCompanyLisenceCodeView;
    @BindView(R.id.real_name_addr)
    TextView mAddrView;
    @BindView(R.id.real_name_addr2)
    EditText mAddr2View;
    @BindView(R.id.real_name_businessScope)
    EditText mBusinessScopeView;
    @BindView(R.id.real_name_companyLisencePicture)
    ImageView mCompanyLisencePictureView;
    @BindView(R.id.real_name_masterPerson)
    EditText mMasterPersonView;
    @BindView(R.id.real_name_masterPersonID)
    EditText mMasterPersonIDView;
    @BindView(R.id.real_name_masterPersonIdCard1)
    ImageView mMasterPersonIdCard1View;
    @BindView(R.id.real_name_masterPersonIdCard2)
    ImageView mMasterPersonIdCard2View;
    @BindView(R.id.real_name_companyPhoto_layout)
    LinearLayout mCompanyPhotoParentView;
    @BindView(R.id.real_name_companyLisencePicture_pb)
    ProgressBar mCompanyLisencePicturePbView;
    @BindView(R.id.real_name_masterPersonIdCard1_pb)
    ProgressBar mPersonIdCard1PbView;
    @BindView(R.id.real_name_masterPersonIdCard2_pb)
    ProgressBar mPersonIdCard2PbView;
    @BindView(R.id.real_name_companyPhoto_finish)
    Button mFinishView;



    private String mFlag;//判断当前选择的是什么 LisencePicture（营业执照） IdCard1（身份证正面）IdCard2（身份证反面）CompanyPhoto（实景照片）
    List<LocalMedia> mSelectLisencePictureList = new ArrayList<>();
    List<LocalMedia> mSelectIdCard1List = new ArrayList<>();
    List<LocalMedia> mSelectIdCard2List = new ArrayList<>();
    List<LocalMedia> mSelectCompanyPhotoList = new ArrayList<>();
    List<LocalMedia> mSelectList = new ArrayList<>();
    RelaNameAuthenticationRequestModel mRequest = new RelaNameAuthenticationRequestModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_authentication);
        ButterKnife.bind(this);
        initTopbar("实名认证");
        initView();
    }

    private void initView(){
        String identify = ShareConfig.getConfigString(mContext, Constants.whetheridentifiedid, "");
        if (!"notidentify".equals(identify)) {//未认证
            getDate();
        }
    }

    public void getDate() {
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
                            RelaNameAuthenticationResponseModel model = (RelaNameAuthenticationResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                RelaNameAuthenticationResponseModel.DataBean.CompanyidentifyinfoBean obj = model.data.companyidentifyinfo;
                                ShareConfig.getConfigString(mContext,Constants.whetheridentifiedid,obj.whetheridentifiedid);
                                mCompanyNameView.setText(obj.companyname);
                                mCompanyLisenceCodeView.setText(obj.companylisencecode);
                                mAddrView.setText(obj.companyaddress);
                                mAddr2View.setText(obj.address);
                                mBusinessScopeView.setText(obj.businessscope);
                                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.companylisencepicture, mCompanyLisencePictureView);
                                mMasterPersonView.setText(obj.masterperson);
                                mMasterPersonIDView.setText(obj.masterpersonid);
                                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.masterpersonidcard1, mMasterPersonIdCard1View);
                                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.masterpersonidcard2, mMasterPersonIdCard2View);
                                if(obj.companypicture.length()>0){
                                    String[] pictures = obj.companypicture.split(",");
                                    mCompanyPhotoParentView.removeAllViews();
                                    for (String s:pictures) {
                                        View v = getLayoutInflater().inflate(R.layout.activity_real_name_authentication_companyphoto, null);
                                        ImageView iv = (ImageView) v.findViewById(R.id.real_name_companyPhoto);
                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + s, iv);
                                        mCompanyPhotoParentView.addView(v);
                                    }
                                }

                                setView();
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
                        UserAction action = new UserAction();
                        RelaNameAuthenticationResponseModel model = action.getRelaNameAuthentication();
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

    private void setView() {
        mCompanyNameView.setFocusable(false);
        mCompanyLisenceCodeView.setFocusable(false);
        mAddrView.setClickable(false);
        mAddr2View.setFocusable(false);
        mBusinessScopeView.setFocusable(false);
        mCompanyLisencePictureView.setClickable(false);
        mMasterPersonView.setFocusable(false);
        mMasterPersonIDView.setFocusable(false);
        mMasterPersonIdCard1View.setClickable(false);
        mMasterPersonIdCard2View.setClickable(false);
        mCompanyPhotoParentView.setClickable(false);
        mFinishView.setText("查看认证状态");
    }


    @OnClick({R.id.real_name_addr, R.id.real_name_companyLisencePicture, R.id.real_name_masterPersonIdCard1, R.id.real_name_masterPersonIdCard2, R.id.real_name_companyPhoto_layout, R.id.real_name_companyPhoto_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.real_name_addr:
                showAddrPicker();
                break;
            case R.id.real_name_companyLisencePicture:
                mFlag = "LisencePicture";
                openImage();
                break;
            case R.id.real_name_masterPersonIdCard1:
                mFlag = "IdCard1";
                openImage();
                break;
            case R.id.real_name_masterPersonIdCard2:
                mFlag = "IdCard2";
                openImage();
                break;
            case R.id.real_name_companyPhoto_layout:
                mFlag = "CompanyPhoto";
                openImage();
                break;
            case R.id.real_name_companyPhoto_finish:
                String identify = ShareConfig.getConfigString(mContext, Constants.whetheridentifiedid, "");
                if ("notidentify".equals(identify)) {//未认证
                    attestation();
                }else {
                    getAttestationstate();
                }
                break;
        }
    }

    /**
     * 查看认证状态
     */
    private void getAttestationstate(){
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo model = (ResponseInfo) msg.obj;
                        ToastUtil.showMessage(model.getMsg());

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
                    ResponseInfo model = action.getAttestationState();
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

    }

    /**
     * 认证
     */
    private void attestation() {
        mRequest.companyname = mCompanyNameView.getText() + "";
        mRequest.companylisencecode = mCompanyLisenceCodeView.getText() + "";
        mRequest.address = mAddr2View.getText() + "";
        mRequest.businessscope = mBusinessScopeView.getText() + "";
        mRequest.masterperson = mMasterPersonView.getText() + "";
        mRequest.masterpersonid = mMasterPersonIDView.getText() + "";
        if ("".equals(mRequest.companyname) || "".equals(mRequest.companylisencecode) || mRequest.province == null || "".equals(mRequest.address) ||
                "".equals(mRequest.businessscope) || mRequest.companylisencepicture == null || "".equals(mRequest.masterperson) || "".equals(mRequest.masterpersonid) || mRequest.masterpersonidcard1 == null || mRequest.masterpersonidcard2 == null || mRequest.companyphoto == null) {
            ToastUtil.showMessage("请补全资料");
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
                                ShareConfig.setConfig(mContext, Constants.whetheridentifiedid, "identifying");
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
                        UserAction action = new UserAction();
                        ResponseInfo model = action.attestation(mRequest);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    if ("LisencePicture".equals(mFlag)) {
                        mSelectLisencePictureList = mSelectList;
                        ImageLoader.getInstance().displayImage("file://" + mSelectList.get(0).getCompressPath(), mCompanyLisencePictureView);
                        uploadImage(mSelectLisencePictureList, mCompanyLisencePicturePbView);
                    } else if ("IdCard1".equals(mFlag)) {
                        mSelectIdCard1List = mSelectList;
                        ImageLoader.getInstance().displayImage("file://" + mSelectList.get(0).getCompressPath(), mMasterPersonIdCard1View);
                        uploadImage(mSelectIdCard1List, mPersonIdCard1PbView);
                    } else if ("IdCard2".equals(mFlag)) {
                        mSelectIdCard2List = mSelectList;
                        ImageLoader.getInstance().displayImage("file://" + mSelectList.get(0).getCompressPath(), mMasterPersonIdCard2View);
                        uploadImage(mSelectIdCard2List, mPersonIdCard2PbView);
                    } else if ("CompanyPhoto".equals(mFlag)) {
                        mSelectCompanyPhotoList = mSelectList;
                        mCompanyPhotoParentView.removeAllViews();
                        for (LocalMedia item : mSelectCompanyPhotoList) {
                            View v = getLayoutInflater().inflate(R.layout.activity_real_name_authentication_companyphoto, null);
                            ImageView iv = (ImageView) v.findViewById(R.id.real_name_companyPhoto);
                            ImageLoader.getInstance().displayImage("file://" + item.getCompressPath(), iv);
                            mCompanyPhotoParentView.addView(v);
                        }
                        uploadImage(mSelectCompanyPhotoList, null);
                    }
                    break;
            }
        }
    }

    private void uploadImage(final List<LocalMedia> list, final ProgressBar pb) {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        if (list.size() > 0) {
            if (pb == null) {
                dialog.show();
            } else {
                pb.setVisibility(View.VISIBLE);
            }
            try {
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {

                        if (pb != null) {
                            pb.setVisibility(View.GONE);
                        } else {
                            dialog.dismiss();
                        }
                        switch (msg.what) {
                            case ThreadState.SUCCESS:
                                MeetingGalleryUpdateResponseModel model = (MeetingGalleryUpdateResponseModel) msg.obj;
                                if (model.isSuccess() && model.data.pictureinfo != null) {
                                    String name = model.data.pictureinfo.picturesavenames;
                                    if ("LisencePicture".equals(mFlag)) {
                                        mRequest.companylisencepicture = name;
                                    } else if ("IdCard1".equals(mFlag)) {
                                        mRequest.masterpersonidcard1 = name;
                                    } else if ("IdCard2".equals(mFlag)) {
                                        mRequest.masterpersonidcard2 = name;
                                    } else if ("CompanyPhoto".equals(mFlag)) {
                                        mRequest.companyphoto = name;
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


    private void openImage() {
        int pictureConfig = PictureConfig.SINGLE;
        int number = 1;
        //（营业执照） IdCard1（身份证正面）IdCard2（身份证反面）CompanyPhoto（实景照片）
        if ("LisencePicture".equals(mFlag)) {
            mSelectList = mSelectLisencePictureList;
        } else if ("IdCard1".equals(mFlag)) {
            mSelectList = mSelectIdCard1List;
        } else if ("IdCard2".equals(mFlag)) {
            mSelectList = mSelectIdCard2List;
        } else if ("CompanyPhoto".equals(mFlag)) {
            mSelectList = mSelectCompanyPhotoList;
            pictureConfig = PictureConfig.MULTIPLE;
            number = 2;
        }
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .compress(true)
                .isCamera(false)
                .maxSelectNum(number)
                .selectionMode(pictureConfig)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .selectionMedia(mSelectList)// 是否传入已选图片 List<LocalMedia> list
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 显示地址控件
     */
    private void showAddrPicker() {
        try {
            ArrayList<AddressPicker.Province> data = new ArrayList<>();
            String json = AssetsUtils.readText(mContext, "city.json");
            data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
            final CustomAddressPicker picker = new CustomAddressPicker(mContext, data);
            picker.setTextSize(14);
            if (mRequest.province != null) {
                picker.setSelectedItem(mRequest.province, mRequest.city, mRequest.zone);
            }
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(String province, String city, String county) {
                    mRequest.province = province;
                    mRequest.city = city;
                    mRequest.zone = county;
                    ((TextView) findViewById(R.id.real_name_addr)).setText(province + city + county);
                }
            });
            picker.show();
            if (mRequest.province != null) {
                ((TextView) picker.getRootView().getChildAt(0).findViewById(R.id.picker_header_tv)).setText(mRequest.province + mRequest.city + mRequest.zone);
            }
            picker.setGravity(Gravity.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
