package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeCreateResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.model.Condition;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 生成二维码
 */

public class ScanCodeCreateActivity extends BaseActivity implements View.OnClickListener {
    private final int REQEST_TYPE_NORMS = 1004;
    @BindView(R.id.create_code_money)
    EditText mMoneyView;
    @BindView(R.id.create_code_number)
    EditText mNumberView;
    @BindView(R.id.create_code_type_norms)
    TextView mTypeNormsView;
    private Condition mType;
    private Condition mNorms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code);
        ButterKnife.bind(this);
        mContext = this;
        initTopbar("生成返利二维码", "历史记录", this);
        initView();
    }

    private void initView() {

        mMoneyView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        mMoneyView.setText(s);
                        mMoneyView.setSelection(s.length());
                        ToastUtil.showMessage("输入值只能小数点后两位");
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mMoneyView.setText(s);
                    mMoneyView.setSelection(2);
                    ToastUtil.showMessage("输入值只能小数点后两位");
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mMoneyView.setText(s.subSequence(0, 1));
                        mMoneyView.setSelection(1);
                        ToastUtil.showMessage("输入值只能小数点后两位");
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.create_code_type_norms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ScanCodeCreateSelectActivity.class);
                startActivityForResult(intent, REQEST_TYPE_NORMS);
            }
        });

        findViewById(R.id.create_code_finish_bth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = mMoneyView.getText() + "";
                String number = mNumberView.getText() + "";
                if (TextUtils.isEmpty(money)) {
                    ToastUtil.showMessage("请输入金额");
                    return;
                }
                if (TextUtils.isEmpty(number)) {
                    ToastUtil.showMessage("请输入数量");
                    return;
                }
                if (mType == null || mNorms == null) {
                    ToastUtil.showMessage("请选择产品规格");
                    return;
                }
                showAlert(money,number);
            }
        });
    }

    private void showAlert(final String money,final String number ) {
        View parentLayout = findViewById(R.id.parentLayout);
        final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, R.layout.pop_window_header,
                R.layout.activity_scancode_pay_confirm_tip, "确认提示", "是否生成返利二维码，请确认！", "确认",0);
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
                createCode(money, number);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(mContext, ScanCodeHistoryListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQEST_TYPE_NORMS) {
            mType = (Condition) data.getSerializableExtra("type");
            mNorms = (Condition) data.getSerializableExtra("norms");
            String type = mType != null ? mType.getName() + "  " : "";
            String norm = mNorms != null ? mNorms.getName() : "";
            mTypeNormsView.setText(type + norm);
        }
    }

    /**
     * 从服务器获取数据
     */
    private void createCode(final String money, final String number) {
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
                            ScanCodeCreateResponseModel model = (ScanCodeCreateResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                Intent intent = new Intent(mContext, ScanCodeCreateSucceedActivity.class);
                                intent.putExtra("id", model.data.generateinfo.id);
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
                        ScanCodeAction action = new ScanCodeAction();
                        ScanCodeCreateResponseModel model = action.createScanCode(money, number, mType.getId(), mNorms.getId());
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
