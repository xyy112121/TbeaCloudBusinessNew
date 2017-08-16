package com.example.programmer.tbeacloudbusiness.activity.my.set.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity.AddrSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.AddressModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 新增收货地址
 */

public class AddressEditActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.addr_edit_contactperson)
    EditText mNameView;
    @BindView(R.id.addr_edit_contactmobile)
    EditText mMobileView;
    @BindView(R.id.addr_edit_provincial_city)
    TextView mCityView;
    @BindView(R.id.addr_edit_address)
    EditText mAddressView;
    @BindView(R.id.addr_edit_isdefault)
    CheckBox mIsdefault;
    private Context mContext;
    private final int RESULT_ADDR = 1002;
    private AddressModel mObj = new AddressModel();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        ButterKnife.bind(this);
        mContext = this;
        if ("edit".equals(getIntent().getStringExtra("flag"))) {
//            Gson gson = new Gson();
//            String objGson = getIntent().getStringExtra("obj");
//            mObj = gson.fromJson(objGson,Address.class);
            initTopbar("收货地址管理", "删除", this);
//            getDate(mObj.getId());
        } else {
            initTopbar("收货地址管理", "保存", this);
        }
    }

    /**
     * 验证手机格式 false不正确
     */
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

    @Override
    public void onClick(View v) {
        String name = mNameView.getText() + "";
        String contactmobile = mMobileView.getText() + "";
        String address = mAddressView.getText() + "";
        String citys = mCityView.getText() + "";

        CheckBox ck = (CheckBox) findViewById(R.id.addr_edit_isdefault);

        if ("".equals(name) || "".equals(contactmobile) || "".equals(address) || "".equals(citys)) {
            ToastUtil.showMessage("请填写完整的地址信息");
            return;
        }

        if (isMobileNO(contactmobile) == false) {
            ToastUtil.showMessage("请输入正确的手机号码");
            return;
        }

        if (ck.isChecked()) {
            mObj.isdefault = "1";
        } else {
            mObj.isdefault = "0";
        }
        mObj.contactperson = name;
        mObj.contactmobile = contactmobile;
        mObj.address = address;
//
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        BaseResponseModel re = (BaseResponseModel) msg.obj;
                        if (re.isSuccess()) {
                            setResult(RESULT_OK);
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
                    SetAction action = new SetAction();
                    BaseResponseModel model = action.editAddrss(mObj);
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @OnClick(R.id.addr_edit_provincial_city)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, AddrSelectActivity.class);
        intent.putExtra("flag", "addrEdit");
        startActivityForResult(intent, RESULT_ADDR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_ADDR://举办地点
                    mObj.province = data.getStringExtra("province");
                    mObj.city = data.getStringExtra("city");
                    mObj.zone = data.getStringExtra("county");
                    mCityView.setText(mObj.province + mObj.city + mObj.zone);
                    break;
            }
        }

    }
}
