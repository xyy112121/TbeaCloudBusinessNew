package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.action.RebateAccountAction;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.WithdrawCashViewResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 积分提现(二维码生成界面)
 */

public class WithdrawCashViewActivity extends BaseActivity implements View.OnClickListener {
    private String mTakeMoneyCode = "";
    private Timer timer = new Timer();
    private boolean isStart = false;
    private String mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratate_account_withdraw_cash_view);
        initTopbar("提现凭证", "删除", this);
        String money = getIntent().getStringExtra("money");
        String distributorid = getIntent().getStringExtra("distributorid");
        if ("".equals(money) || money == null) {
            String id = getIntent().getStringExtra("takemoneycodeid");
            getDate2(id);
        } else {
            getDate(money, distributorid);
        }

        (findViewById(R.id.top_right_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_delete_dialog);
                dialog.setText("删除后提现二维码将会失效");
                dialog.setText2("如需提现请重新生成二维码");
                dialog.setConfirmBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                }, "取消");
                dialog.setCancelBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if ("".equals(mId)) {
//                            UtilAssistants.showToast("删除失败！");
                        } else {
                            delect(mId);
                        }
                    }
                }, "删除");
                dialog.show();
            }
        });

        findViewById(R.id.top_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 根据id获取数据
     */
    public void getDate2(final String takemoneycodeid) {
//        //TBEAENG005001006002
//        final CustomDialog dialog = new CustomDialog(WithdrawCashViewActivity.this,R.style.MyDialog,R.layout.tip_wait_dialog);
//        dialog.setText("加载中...");
//        dialog.show();
//        final Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                dialog.dismiss();
//                switch (msg.what){
//                    case ThreadState.SUCCESS:
//                        RspInfo1 re = (RspInfo1)msg.obj;
//                        if(re.isSuccess()){
//                            Map<String, Object> data = (Map<String, Object>) re.getData();
//                            Map<String, String> myMoneyInfo = (Map<String, String>) data.get("mymoneyinfo");
//                            Map<String, String> distriButorInfo = (Map<String, String>) data.get("distributorinfo");
//                            String validexpiredtime ="有效期:"+myMoneyInfo.get("validexpiredtime");
////                            String status = "状态:"+myMoneyInfo.get("status");
//                            mId = myMoneyInfo.get("id");
//                            mTakeMoneyCode = myMoneyInfo.get("takemoneycode");
//                            String money = "￥"+myMoneyInfo.get("money");
//                            String qrcodepicture = MyApplication.instance.getImgPath()+myMoneyInfo.get("qrcodepicture");
//                            String note = myMoneyInfo.get("note");
//                            String name = "提现单位:"+distriButorInfo.get("name");
//                            String addr = "地址:"+distriButorInfo.get("addr");
//                            String mobilenumber = "电话:"+distriButorInfo.get("mobilenumber");
//
//                            ((TextView)findViewById(R.id.wallet_withdraw_cash_view_validexpiredtime)).setText(validexpiredtime);
////                            ((TextView)findViewById(R.id.wallet_withdraw_cash_view_status)).setText(status);
//                            ((TextView)findViewById(R.id.wallet_withdraw_cash_view_takemoneycode)).setText(mTakeMoneyCode);
//                            ImageView imageView  = (ImageView)findViewById(R.id.wallet_withdraw_cash_view_qrcodepicture);
//                            ImageLoader.getInstance().displayImage(qrcodepicture,imageView);
//                            ((TextView)findViewById(R.id.wallet_withdraw_cash_view_money)).setText(money);
//                            ((TextView)findViewById(R.id.wallet_withdraw_cash_view_note)).setText(note);
//                            ((TextView)findViewById(R.id.wallet_withdraw_cash_view_distributorinfo_name)).setText(name);
//                            ((TextView)findViewById(R.id.wallet_withdraw_cash_view_distributorinfo_addr)).setText(addr);
//                            ((TextView)findViewById(R.id.wallet_withdraw_cash_view_distributorinfo_mobilenumber)).setText(mobilenumber);
//                            getCanexChangeMoneySuccess();
//                        }else {
//                            UtilAssistants.showToast(re.getMsg());
//                            finish();
//                        }
//
//                        break;
//                    case ThreadState.ERROR:
//                        UtilAssistants.showToast("操作失败！");
//                        finish();
//                        break;
//                }
//            }
//        };
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    UserAction userAction = new UserAction();
//                    RspInfo1 re = userAction.createCodeById(takemoneycodeid);
//                    handler.obtainMessage(ThreadState.SUCCESS,re).sendToTarget();
//                } catch (Exception e) {
//                    handler.sendEmptyMessage(ThreadState.ERROR);
//                }
//            }
//        }).start();
    }

    /**
     * 获取数据
     */
    public void getDate(final String money, final String distributorId) {
        final CustomDialog dialog = new CustomDialog(WithdrawCashViewActivity.this, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        WithdrawCashViewResponseModel model = (WithdrawCashViewResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            WithdrawCashViewResponseModel.TakemoneyInfo info = model.data.takemoneyinfo;
                            ((TextView) findViewById(R.id.wallet_withdraw_cash_view_withdraw)).setText("提现单位："+model.data.distributorinfo.name);
                            if (info != null) {
                                ImageView imageView = (ImageView) findViewById(R.id.wallet_withdraw_cash_view_qrcodepicture);
                                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + info.qrcodepicture, imageView);
                                ((TextView) findViewById(R.id.withdraw_cash_view_takemoneycode)).setText("验证码：" + info.takemoneycode);
                                ((TextView) findViewById(R.id.withdraw_cash_view_money)).setText(info.money);
                                ((TextView) findViewById(R.id.withdraw_cash_view_validexpiredtime)).setText("有效期："+info.validexpiredtime);
                                ((TextView) findViewById(R.id.wallet_withdraw_cash_view_note)).setText("提现单位："+info.note);
                            }
                            getCanexChangeMoneySuccess();
                        } else {
                            ToastUtil.showMessage(model.getMsg());
                            finish();
                        }

                        break;
                    case ThreadState.ERROR:
                        ToastUtil.showMessage("操作失败！");
                        finish();
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RebateAccountAction action = new RebateAccountAction();
                    WithdrawCashViewResponseModel re = action.getWithdrawCashView(money, distributorId);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    //处理调用定时器成功以后的操作
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ThreadState.ERROR:
                    if (isStart == false) {
                        isStart = true;
                        timer.schedule(timerTask, 1000, 10000);//10秒后执行
                    }
                    break;
                case ThreadState.SUCCESS:
//                   RspInfo1 re = (RspInfo1)msg.obj;
//                   if(re.isSuccess()){
//                       timer.cancel();
//                       Map<String, Object> data = (Map<String, Object>) re.getData();
//                       Map<String, String> takemoneyinfo = (Map<String, String>) data.get("takemoneyinfo");
////                       Map<String, String> distriButorInfo = (Map<String, String>) data.get("distributorinfo");
//                       String money = "￥"+takemoneyinfo.get("money");
//                       String name = takemoneyinfo.get("distributorname");
//                       String takemoneytime = takemoneyinfo.get("takemoneytime");
//                       Intent intent = new Intent(WithdrawCashViewActivity.this,WalletWithdrawCashSuccessActivity.class);
//                       intent.putExtra("money",money);
//                       intent.putExtra("distributorname",name);
//                       intent.putExtra("takemoneytime",takemoneytime);
//                       intent.putExtra("flag","view");
//                       startActivity(intent);
//                       finish();
//                   }else {
//                       if (isStart == false){
//                           isStart = true;
//                           timer.schedule(timerTask,1000,10000);//10秒后执行
//                       }
//                   }
                    break;
            }

        }
    };

    /**
     * 定时器
     */

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
//            try {
//                UserAction userAction = new UserAction();
//                RspInfo1 re = userAction.getCanexChangeMoneySuccess(mTakeMoneyCode);
//                handler.obtainMessage(ThreadState.SUCCESS,re).sendToTarget();
//            }catch (Exception e){
//                handler.sendEmptyMessage(ThreadState.ERROR);
//            }
        }
    };

    public void getCanexChangeMoneySuccess() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    UserAction userAction = new UserAction();
//                    RspInfo1 re = userAction.getCanexChangeMoneySuccess(mTakeMoneyCode);
//                    handler.obtainMessage(ThreadState.SUCCESS,re).sendToTarget();
//                }catch (Exception e){
//                    handler.sendEmptyMessage(ThreadState.ERROR);
//                }
//            }
//        }).start();
    }

    /**
     * 删除数据
     */
    public void delect(final String id) {
//        final CustomDialog dialog = new CustomDialog(mContext,R.style.MyDialog,R.layout.tip_wait_dialog);
//        dialog.setText("请等待...");
//        dialog.show();
//        final Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                dialog.dismiss();
//                switch (msg.what){
//                    case ThreadState.SUCCESS:
////                        RspInfo1 re = (RspInfo1)msg.obj;
////                        if(re.isSuccess()){
////                            UtilAssistants.showToast(re.getMsg());
////                            finish();
////                        }else {
////                            UtilAssistants.showToast(re.getMsg());
////                        }
//
//                        break;
//                    case ThreadState.ERROR:
//                        ToastUtil.showMessage("操作失败！");
//                        break;
//                }
//            }
//        };
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    UserAction userAction = new UserAction();
//                    RspInfo1 re = userAction.delectTakeMoneyCodeId(id);
//                    handler.obtainMessage(ThreadState.SUCCESS,re).sendToTarget();
//                } catch (Exception e) {
//                    handler.sendEmptyMessage(ThreadState.ERROR);
//                }
//            }
//        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onClick(View v) {

    }
}
