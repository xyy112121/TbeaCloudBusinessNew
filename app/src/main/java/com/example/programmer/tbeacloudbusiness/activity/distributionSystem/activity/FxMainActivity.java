package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.DistributionSystemAction;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.DsMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.CircularImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FxMainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.txtGG)
    TextView txtGG;
    @BindView(R.id.txtMoney)
    TextView txtMoney;
    @BindView(R.id.linGG)
    LinearLayout linGG;
    @BindView(R.id.imgUserIcon)
    CircularImageView imgUserIcon;
    private Button butDDCX;
    private Button butKCCX;
    private LinearLayout linZXXD;
    private LinearLayout linZDJG;
    private LinearLayout linHKMX;
    private LinearLayout linYHZC;
    private LinearLayout linZXTS;
    private LinearLayout linGGCX;
    private LinearLayout linZDCX;
    private LinearLayout linLXWM;

    DsMainResponseModel model;


    //	private TBEA15000000 req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		AppApp.instance.addActivity(this);
        setContentView(R.layout.activity_fx_main);
        ButterKnife.bind(this);
        initTopbar("分销系统");
        init();
        setView();
        getData();
    }

    private void getData() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            model = (DsMainResponseModel) msg.obj;
                            if (model.HomepageInfo != null) {
                                setData(model);
                            } else {
                                showMessage("操作失败！");
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
                        DistributionSystemAction action = new DistributionSystemAction();
                        DsMainResponseModel model = action.getMainList();
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


    private void init() {
        imgUserIcon = (CircularImageView) findViewById(R.id.imgUserIcon);
        txtMoney = (TextView) findViewById(R.id.txtMoney);
        txtGG = (TextView) findViewById(R.id.txtGG);
        butDDCX = (Button) findViewById(R.id.butDDCX);
        butKCCX = (Button) findViewById(R.id.butKCCX);
        linZXXD = (LinearLayout) findViewById(R.id.linZXXD);
        linZDJG = (LinearLayout) findViewById(R.id.linZDJG);
        linHKMX = (LinearLayout) findViewById(R.id.linHKMX);
        linYHZC = (LinearLayout) findViewById(R.id.linYHZC);
        linZXTS = (LinearLayout) findViewById(R.id.linZXTS);
        linGGCX = (LinearLayout) findViewById(R.id.linGGCX);
        linZDCX = (LinearLayout) findViewById(R.id.linZDCX);
        linLXWM = (LinearLayout) findViewById(R.id.linLXWM);
        linGG = (LinearLayout) findViewById(R.id.linGG);
    }

    private void setView() {
        txtGG.setOnClickListener(this);
        butDDCX.setOnClickListener(this);
        butKCCX.setOnClickListener(this);
        linZXXD.setOnClickListener(this);
        linZDJG.setOnClickListener(this);
        linHKMX.setOnClickListener(this);
        linYHZC.setOnClickListener(this);
        linZXTS.setOnClickListener(this);
        linGGCX.setOnClickListener(this);
        linZDCX.setOnClickListener(this);
        linLXWM.setOnClickListener(this);
    }

//	@Override
//	public void start(String serviceCode) {
//		PDUtil.showPD(this);
//	}
//
//
//	@Override
//	public void finish(String serviceCode) {
//		PDUtil.hidePD(this);
//		if(serviceCode.equals(TBEA15000000.SERVICE_CODE)){
//			if(!req.isHasError() && req.rspInfo.rspSuccess){
//				fullData();
//			}else if(!req.isHasError()){
//				Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
//			}else{
//				Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
//			}
//		}
//	}

    @Override
    public void onClick(View v) {
        if (txtGG == v) toGG();
        if (butDDCX == v) toDDCX();
        if (butKCCX == v) toKCCX();
        if (linZXXD == v) toZXXD();
        if (linZDJG == v) toZDJG();
        if (linHKMX == v) toHKMX();
        if (linYHZC == v) toYHZC();
        if (linZXTS == v) toZXTS();
        if (linGGCX == v) toGGCX();
        if (linZDCX == v) toZDCX();
        if (linLXWM == v) toLXWM();
    }

    private void toGG() {
        if (model.HomepageInfo.Announcement.size() > 0) {
            Intent intent = new Intent(this, ActivityFxGgXQ.class);
            intent.putExtra("id", model.HomepageInfo.Announcement.get(0).Id);
            startActivity(intent);
        }
    }

    private void toDDCX() {
        Intent intent = new Intent(this, ActivityFxOrder.class);
        startActivity(intent);
    }

    private void toKCCX() {
        Intent intent = new Intent(this, ActivityFxKcQueryResult.class);
        startActivity(intent);
    }

    private void toZXXD() {
        Intent intent = new Intent(this, ActivityFxZxxd.class);
        startActivity(intent);
    }

    private void toZDJG() {
        Intent intent = new Intent(this, ActivityFxZdjgQuery.class);
        startActivity(intent);
    }

    private void toHKMX() {
        Intent intent = new Intent(this, ActivityFxHKLB.class);
        startActivity(intent);
    }

    private void toYHZC() {
        Intent intent = new Intent(this, ActivityFxFL.class);
        startActivity(intent);
    }

    private void toZXTS() {
        Intent intent = new Intent(this, ActivityFxTSLB.class);
        startActivity(intent);
    }

    private void toGGCX() {
        Intent intent = new Intent(this, ActivityFxGG.class);
        startActivity(intent);
    }

    private void toZDCX() {
        Intent intent = new Intent(this, ActivityFxZD.class);
        startActivity(intent);
    }

    private void toLXWM() {
        Intent intent = new Intent(this, ActivityFxLXWM.class);
        startActivity(intent);
    }


    private void setData(DsMainResponseModel model) {
        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath1() + model.HomepageInfo.PersonPicture, imgUserIcon);
        txtMoney.setText("￥" + model.HomepageInfo.ShouldReveivedMoney);
        if (model.HomepageInfo.Announcement.size() > 0) {
            linGG.setVisibility(View.VISIBLE);
            txtGG.setText(model.HomepageInfo.Announcement.get(0).Name);
        } else {
            linGG.setVisibility(View.GONE);
            txtGG.setText("");
        }
    }

}
