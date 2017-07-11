package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeActivityStatusResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 扫码返利详情
 */

public class ScanCodeViewActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_rebate_view);
        initTopbar("扫码详情");
        getData();
    }

    private void getData() {
        final CustomDialog dialog = new CustomDialog(mContext,R.style.MyDialog,R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            dialog.dismiss();
                            ScanCodeInfoResponseModel model = (ScanCodeInfoResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if(model.data != null && model.data.rebateqrcodeinfo != null){
                                    setViewText(R.id.scan_code_info_code,model.data.rebateqrcodeinfo.rebatecode);
                                    setViewText(R.id.scan_code_info_generatetime,model.data.rebateqrcodeinfo.generatetime);
                                    setViewText(R.id.scan_code_info_type,model.data.rebateqrcodeinfo.commodityspecificationandmodel);
                                    setViewText(R.id.scan_code_info_confirmstatus,model.data.rebateqrcodeinfo.confirmstatus);
                                    setViewText(R.id.scan_code_info_money,model.data.rebateqrcodeinfo.money);
                                }

                                if (model.data != null && model.data.rebateqrcodeactivityinfo != null){
                                    CircleImageView headView = (CircleImageView)findViewById(R.id.person_info_head);
                                    ImageView jobView = (ImageView)findViewById(R.id.person_info_personjobtitle);
                                    String imagePath = MyApplication.instance.getImgPath();
                                    ImageLoader.getInstance().displayImage(imagePath+model.data.rebateqrcodeactivityinfo.actuvityuserpicture,headView);
                                    ImageLoader.getInstance().displayImage(imagePath+model.data.rebateqrcodeactivityinfo.personjobtitle,jobView);
                                    setViewText(R.id.scan_code_info_activitytime,model.data.rebateqrcodeactivityinfo.activitytime);
                                    setViewText(R.id.scan_code_info_activityplace,model.data.rebateqrcodeactivityinfo.activityplace);
                                }
                            } else {
                                ToastUtil.showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            ToastUtil.showMessage("操作失败！");
                            dialog.dismiss();
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ScanCodeAction action = new ScanCodeAction();
                        String id = getIntent().getStringExtra("id");
                        ScanCodeInfoResponseModel model = action.getScanCodeInfo(id);
                        handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            ToastUtil.showMessage("操作失败！");
            dialog.dismiss();
        }
    }

    private void setViewText(int id,String text){
        ((TextView)findViewById(id)).setText(text);
    }
}
