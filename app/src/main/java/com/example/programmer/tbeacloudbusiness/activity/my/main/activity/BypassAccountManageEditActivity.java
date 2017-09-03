package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingGalleryUpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.action.MyAction;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.BypassAccountEditRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.BypassAccountEditUserTypeRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.AddrListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomDatePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 子账号管理增加
 */

public class BypassAccountManageEditActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.my_bypass_account_edit_phone)
    EditText mPhoneView;
    @BindView(R.id.my_bypass_account_edit_name)
    EditText mNameView;
    @BindView(R.id.my_bypass_account_edit_pwd)
    EditText mPwdView;
    @BindView(R.id.my_bypass_account_edit_JobTitle)
    EditText mJobTitleView;
    @BindView(R.id.my_bypass_account_edit_head)
    RelativeLayout mHeadView;
    @BindView(R.id.authorization_functions)
    TextView mFunctionsView;
    @BindView(R.id.my_bypass_account_edit_save)
    Button mSaveView;
    @BindView(R.id.my_bypass_account_edit_type)
    TextView mTypeView;
    @BindView(R.id.bypass_account_manage_edit_layout)
    View parentLayout;

    List<LocalMedia> mSelectList = new ArrayList<>();
    BypassAccountEditRequestModel mRequest = new BypassAccountEditRequestModel();
    CustomDialog mDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bypass_account_manage_edit);
        ButterKnife.bind(this);
        if ("edit".equals(getIntent().getStringExtra("flag"))) {
            initTopbar("子账号管理", "删除", this);
        } else {
            initTopbar("子账号管理");
            getDate();
        }
    }


    @Override
    public void onClick(View v) {
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
                        BypassAccountEditUserTypeRequestModel model = (BypassAccountEditUserTypeRequestModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if (model.data.subaccountusertypeinfo != null) {
                                mRequest.usertypeId = model.data.subaccountusertypeinfo.id;
                                mTypeView.setText(model.data.subaccountusertypeinfo.name);
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
                    MyAction userAction = new MyAction();
                    BypassAccountEditUserTypeRequestModel re = userAction.getUserType();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @OnClick({R.id.my_bypass_account_edit_sex, R.id.my_bypass_account_edit_birthday, R.id.my_bypass_account_edit_head, R.id.my_bypass_account_edit_save, R.id.authorization_functions})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_bypass_account_edit_sex:
                showSexPicker(view);
                break;
            case R.id.my_bypass_account_edit_birthday:
                showDatePicker(view);
                break;
            case R.id.my_bypass_account_edit_head:
                openImage();
                break;
            case R.id.my_bypass_account_edit_save:
                uploadImage();
                break;
            case R.id.authorization_functions:
                startActivityForResult(new Intent(mContext, ByPassAccountAuthorizationFunctionsActivity.class), 1000);
                break;
        }
    }

    public void uploadImage() {
        mRequest.realname = mNameView.getText() + "";
        mRequest.mobilenumber = mPhoneView.getText() + "";
        mRequest.password = mPwdView.getText() + "";
        mRequest.jobtitle = mJobTitleView.getText() + "";
        if (isMobileNO(mRequest.mobilenumber) == false) {
            ToastUtil.showMessage("请输入正确的手机号码");
            return;
        }

        if ("".equals(mRequest.realname)) {
            ToastUtil.showMessage("请输入真实姓名");
            return;
        }

        if (mRequest.sex == null) {
            ToastUtil.showMessage("请选择性别");
            return;
        }

        if ("".equals(mRequest.password)) {
            ToastUtil.showMessage("请输入登录密码");
            return;
        }

        if ("".equals(mRequest.jobtitle)) {
            ToastUtil.showMessage("请输入职务");
            return;
        }

        mDialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        mDialog.setText("请等待...");
        mDialog.show();
        if (mSelectList.size() > 0) {
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                            MeetingGalleryUpdateResponseModel model = action.uploadImage(mSelectList);
                            if (model.isSuccess() && model.data.pictureinfo != null) {
                                mRequest.thumbpicture = model.data.pictureinfo.picturesavenames;
                                save();
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
            save();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mDialog.dismiss();
            switch (msg.what) {
                case ThreadState.SUCCESS:
                    ResponseInfo model = (ResponseInfo) msg.obj;
                    if (model.isSuccess()) {
                        ToastUtil.showMessage("操作成功！");
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

    /**
     * 保存
     */
    private void save() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyAction action = new MyAction();
                    ResponseInfo model = action.saveBypassAccount(mRequest);
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

    }

    public boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (mobiles.equals("")) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 打开相册
     */
    private void openImage() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(8)// 最大图片选择数量 int
                .compress(true)
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
                    ImageView headView = ((ImageView) findViewById(R.id.my_bypass_account_edit_head_iv));
                    ImageLoader.getInstance().displayImage("file://" + mSelectList.get(0).getCompressPath(), headView);
                    break;
                case 1000:
                    mRequest.authorizationlist = data.getStringExtra("authorizationList");
                    break;

            }
        }
    }

    /**
     * 显示性别
     */
    private void showSexPicker(final View view) {
        List<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, R.layout.pop_window_header1, R.layout.pop_window_tv, sexList);
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
                ((TextView) view).setText(text);
                mRequest.sex = text;
            }
        });
    }

    /**
     * 显示时间控件
     */
    private void showDatePicker(final View view) {
        try {
            Calendar calendar = Calendar.getInstance();
            CustomDatePicker picker = new CustomDatePicker((Activity) mContext);
            Calendar c = Calendar.getInstance();//首先要获取日历对象
            picker.setRange(1990, c.get(Calendar.YEAR));
            picker.setTextSize(14);
            picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            picker.setOnDatePickListener(new CustomDatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    mRequest.birthyear = year;
                    mRequest.birthmonth = month;
                    mRequest.birthday = day;
                    ((TextView) view)
                            .setText(year.replace("年", "") + "-" + month.replace("月", "") + "-" + day.replace("日", ""));
                }
            });

            picker.show();
            picker.setGravity(Gravity.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
