package com.example.programmer.tbeacloudbusiness.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.FranchiserSelectListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryUpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.CompletionDataRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.CompletionDataResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.RelaNameAuthenticationResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.UserTypeResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomAddressPicker;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomDatePicker;
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
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * 资料补全
 */

public class CompletionDataActivity extends BaseActivity {
    @BindView(R.id.completion_data_account)
    TextView mAccountView;
    @BindView(R.id.completion_data_userType)
    TextView mUserTypeView;
    @BindView(R.id.completion_data_area)
    TextView mAreaView;
    @BindView(R.id.completion_data_affiliation)
    TextView mAffiliationView;
    @BindView(R.id.completion_data_affiliation_layout)
    LinearLayout mAffiliationLayoutView;
    @BindView(R.id.completion_data_name)
    EditText mNameView;
    @BindView(R.id.completion_data_head)
    TextView mHeadView;
    @BindView(R.id.completion_data_head_iv)
    ImageView mHeadView1;
    @BindView(R.id.completion_data_sex)
    TextView mSexView;
    @BindView(R.id.completion_data_birthday)
    TextView mBirthdayView;

    private View parentLayout;
    CompletionDataRequestModel mRequest = new CompletionDataRequestModel();

    List<KeyValueBean> userTypeList = new ArrayList<>();
    List<LocalMedia> mSelectList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion_data);
        ButterKnife.bind(this);
        mRequest.province = MyApplication.instance.getProvince();
        mRequest.city = MyApplication.instance.getCity();
        mRequest.zone = MyApplication.instance.getDistrict();
        parentLayout = findViewById(R.id.completion_data_layout);
        initTopbar("资料补全");
        mAccountView.setText(ShareConfig.getConfigString(mContext,Constants.ACCOUNT,""));
        if("distributor".equals(ShareConfig.getConfigString(mContext,Constants.USERTYPE,""))){
            mUserTypeView.setText("经销商");
        }else {
            mUserTypeView.setText("其他商家");
        }
        initView();
    }

    private void initView() {
        String identify = ShareConfig.getConfigString(mContext, Constants.whetheridentifiedid, "");
        if ("notidentify".equals(identify)) {//未认证
            getUserTypeList();
        }else {
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
                            CompletionDataResponseModel model = (CompletionDataResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                CompletionDataResponseModel.DataBean.PersoninfoBean obj = model.data.personinfo;
                                mAreaView.setText(obj.province + obj.city + obj.zone);
                                mNameView.setText(obj.realname);
                                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.picture, mHeadView1);
                                if ("male".equals(obj.sexid)) {
                                    mSexView.setText("男");
                                } else {
                                    mSexView.setText("女");
                                }
                                mBirthdayView.setText(obj.birthyear + "-" + obj.birthmonth + "-" + obj.birthday);
                                CompletionDataResponseModel.DataBean.PersoninfoBean.FirstdistributorinfoBean item = obj.firstdistributorinfo;
                                if(item != null){
                                    mAffiliationView.setVisibility(View.GONE);
                                    mAffiliationLayoutView.setVisibility(View.VISIBLE);
                                }
                                View pernsonLayout = getLayoutInflater().inflate(R.layout.activity_person_layout2, null);
                                CircleImageView headView = (CircleImageView) pernsonLayout.findViewById(R.id.person_info_head);
                                ImageView typwView = (ImageView) pernsonLayout.findViewById(R.id.person_info_personjobtitle);
                                ImageView rightView = (ImageView) pernsonLayout.findViewById(R.id.person_info_right);
                                TextView nameView = (TextView) pernsonLayout.findViewById(R.id.person_info_name);
                                TextView companyNameView = (TextView) pernsonLayout.findViewById(R.id.person_info_companyname);
                                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + item.thumbpicture, headView);
                                typwView.setVisibility(View.GONE);
                                nameView.setText(item.personname);
                                companyNameView.setText(item.companyname);
                                rightView.setVisibility(View.GONE);
                                mAffiliationLayoutView.addView(pernsonLayout);
                                String identify = ShareConfig.getConfigString(mContext, Constants.whetheridentifiedid, "");
                                if (!"notidentify".equals(identify)) {//未认证
                                    setView();
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
                        UserAction action = new UserAction();
                        CompletionDataResponseModel model = action.getCompletionData();
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
        mAccountView.setFocusable(false);
//        mAccountView.setFocusableInTouchMode(false);
        mUserTypeView.setClickable(false);
        mAreaView.setClickable(false);
        mAffiliationView.setClickable(false);
        mNameView.setFocusable(false);
//        mNameView.setFocusableInTouchMode(false);
        mHeadView.setClickable(false);
        mSexView.setClickable(false);
        mBirthdayView.setClickable(false);

    }

    private void getUserTypeList() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        UserTypeResponseModel model = (UserTypeResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if (model.data.usertypelist != null) {
                                userTypeList = model.data.usertypelist;
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
                    SetAction action = new SetAction();
                    UserTypeResponseModel model = action.getUserType();
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

    }

    @OnClick({R.id.completion_data_area, R.id.completion_data_affiliation_layout, R.id.completion_data_affiliation, R.id.completion_data_head, R.id.completion_data_sex, R.id.completion_data_birthday, R.id.completion_data_next})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.completion_data_area:
                showAddrPicker();
                break;
            case R.id.completion_data_affiliation:
            case R.id.completion_data_affiliation_layout:
                Intent intent = new Intent(mContext, AffiliationSelectListActivity.class);
                intent.putExtra("province",mRequest.province);
                intent.putExtra("city",mRequest.city);
                startActivityForResult(intent, 1000);
                break;
            case R.id.completion_data_head:
                showImage();
                break;
            case R.id.completion_data_sex:
                showSexPicker();
                break;
            case R.id.completion_data_birthday:
                showDatePicker();
                break;
            case R.id.completion_data_next:
                String identify = ShareConfig.getConfigString(mContext, Constants.whetheridentifiedid, "");
                if ("notidentify".equals(identify)) {//未认证
                    uploadImage();
                } else {
                    intent = new Intent(mContext, RealNameAuthenticationActivity.class);
                    startActivityForResult(intent,1001);
                }

                break;
            case R.id.completion_data_userType:
                final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
                popWindow1.init(parentLayout, R.layout.pop_window_header, R.layout.pop_window_tv, userTypeList, "");
                popWindow1.setItemClick2(new CustomPopWindow1.ItemClick2() {
                    @Override
                    public void onItemClick2(KeyValueBean bean) {
                        mRequest.usertypeid = bean.getKey();
                        ((TextView) view).setText(bean.getValue());
                    }
                });
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    mAffiliationView.setVisibility(View.GONE);
                    mAffiliationLayoutView.setVisibility(View.VISIBLE);
                    FranchiserSelectListResponseModel.DataBean.DistributorlistBean item = (FranchiserSelectListResponseModel.DataBean.DistributorlistBean) data.getSerializableExtra("selectObj");
                    View pernsonLayout = getLayoutInflater().inflate(R.layout.activity_person_layout2, null);
                    CircleImageView headView = (CircleImageView) pernsonLayout.findViewById(R.id.person_info_head);
                    ImageView typwView = (ImageView) pernsonLayout.findViewById(R.id.person_info_personjobtitle);
                    ImageView rightView = (ImageView) pernsonLayout.findViewById(R.id.person_info_right);
                    TextView nameView = (TextView) pernsonLayout.findViewById(R.id.person_info_name);
                    TextView companyNameView = (TextView) pernsonLayout.findViewById(R.id.person_info_companyname);
                    ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + item.masterthumbpicture, headView);
                    ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + item.companytypeicon, typwView);
                    nameView.setText(item.name);
                    companyNameView.setText(item.mastername);
                    rightView.setVisibility(View.GONE);
                    mAffiliationLayoutView.addView(pernsonLayout);
                    mRequest.uplevelcompanyid = item.id;
                    break;
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    ImageLoader.getInstance().displayImage("file://" + mSelectList.get(0).getCompressPath(), mHeadView1);
                    break;
                case 1001:
                    finish();
                    break;
            }
        }
    }

    public void uploadImage() {
        mRequest.mobilenumber = mAccountView.getText() + "";
        mRequest.realname = mNameView.getText() + "";
        if ("".equals(mRequest.mobilenumber) || "".equals(mRequest.realname) || mRequest.province == null || mRequest.uplevelcompanyid == null || mRequest.sexid == null || mRequest.birthyear == null) {
            ToastUtil.showMessage("请补全资料");
            return;
        }

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
                                    Intent intent = new Intent(mContext, RealNameAuthenticationActivity.class);
                                    startActivityForResult(intent,1001);
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
                                UserAction userAction = new UserAction();
                                ResponseInfo model1 = userAction.completionData(mRequest);
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

    /**
     * 显示时间控件
     */
    private void showDatePicker() {
        try {
            Calendar calendar = Calendar.getInstance();
            CustomDatePicker picker = new CustomDatePicker(mContext);
            picker.setRange(1990, calendar.get(Calendar.YEAR));
            picker.setTextSize(14);
            picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            picker.setOnDatePickListener(new CustomDatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    mRequest.birthyear = year;
                    mRequest.birthmonth = month;
                    mRequest.birthday = day;
                    ((TextView) findViewById(R.id.completion_data_birthday))
                            .setText(year.replace("年", "") + "-" + month.replace("月", "") + "-" + day.replace("日", ""));
                }
            });
            picker.show();
            picker.setGravity(Gravity.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 显示性别
     */
    private void showSexPicker() {
        List<KeyValueBean> sexList = new ArrayList<>();
        sexList.add(new KeyValueBean("male", "男"));
        sexList.add(new KeyValueBean("femal", "女"));
        CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, R.layout.pop_window_header1, R.layout.pop_window_tv, sexList, "");
        popWindow1.setItemClick2(new CustomPopWindow1.ItemClick2() {
            @Override
            public void onItemClick2(KeyValueBean bean) {
                mRequest.sexid = bean.getKey();
                mSexView.setText(bean.getValue());
            }
        });
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
            //picker.setHideProvince(true);//加上此句举将只显示地级及县级
            //picker.setHideCounty(true);//加上此句举将只显示省级及地级
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(String province, String city, String county) {
                    mRequest.province = province;
                    mRequest.city = city;
                    mRequest.zone = county;
                    ((TextView) findViewById(R.id.completion_data_area)).setText(province + city + county);
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

    /**
     * 显示选择拍照，图库
     */
    private void showImage() {
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

}
