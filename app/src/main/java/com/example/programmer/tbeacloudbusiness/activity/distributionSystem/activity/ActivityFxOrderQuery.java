package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07010100;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.OrderStatusListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityFxOrderQuery extends Activity implements AsyncListener, OnClickListener {

    private TextView txtKSRQ;
    private TextView txtJSRQ;
    private EditText edtDDH;
    private TextView txtDDZT;
    private LinearLayout linKSRQ;
    private LinearLayout linJSRQ;
    private LinearLayout linDDZT;
    private Button butQuery;


    private TBEA07010100 req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_order_query);
        init();
        setView();
        req = new TBEA07010100(this);
        //req.execute();
    }


    private void init() {
        txtKSRQ = (TextView) findViewById(R.id.txtKSRQ);
        txtJSRQ = (TextView) findViewById(R.id.txtJSRQ);
        edtDDH = (EditText) findViewById(R.id.edtDDH);
        txtDDZT = (TextView) findViewById(R.id.txtDDZT);
        linKSRQ = (LinearLayout) findViewById(R.id.linKSRQ);
        linJSRQ = (LinearLayout) findViewById(R.id.linJSRQ);
        linDDZT = (LinearLayout) findViewById(R.id.linDDZT);
        butQuery = (Button) findViewById(R.id.butQuery);
    }

    private void setView() {
        butQuery.setOnClickListener(this);
        linKSRQ.setOnClickListener(this);
        linJSRQ.setOnClickListener(this);
        linDDZT.setOnClickListener(this);
    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        if (serviceCode.equals(TBEA07010100.SERVICE_CODE)) {
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                _toDDZT();
            } else if (!req.isHasError()) {
                com.mic.etoast2.Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                com.mic.etoast2.Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (linKSRQ == v) toKSRQ();
        if (linJSRQ == v) toJSRQ();
        if (linDDZT == v) toDDZT();
        if (butQuery == v) toQuery();
    }

    private void toQuery() {
        Intent intent = new Intent(this, ActivityFxOrder.class);
        intent.putExtra("ksrq", txtKSRQ.getText().toString());
        intent.putExtra("jsrq", txtJSRQ.getText().toString());
        intent.putExtra("ddzt", ddzt);
        intent.putExtra("ddh", edtDDH.getText().toString());
        startActivity(intent);
        finish();
    }

    private void toKSRQ() {
        Intent intent = new Intent(this, ActivityUserInfoChang6.class);
        intent.putExtra("title", "选择开始日期:");
        intent.putExtra("value", -1);
        startActivityForResult(intent, 0x11);
    }

    private void toJSRQ() {
        Intent intent = new Intent(this, ActivityUserInfoChang6.class);
        intent.putExtra("title", "选择结束日期:");
        intent.putExtra("value", -1);
        startActivityForResult(intent, 0x12);
    }


    private void toDDZT() {
        req.execute();
    }

    private Dialog ztDialog;
    private String ddzt;

    private void _toDDZT() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("订单状态选择");
        ArrayList<String> itemArray = new ArrayList<String>();
        for (int i = 0; i < req.OrderStatusList.size(); i++) {
            OrderStatusListItem cItem = req.OrderStatusList.get(i);
            itemArray.add(cItem.Name);
        }
        builder.setSingleChoiceItems(itemArray.toArray(new String[0]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OrderStatusListItem item = req.OrderStatusList.get(which);
                ddzt = item.Id;
                txtDDZT.setText(item.Name);
                ztDialog.dismiss();
            }
        });
        ztDialog = builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x11 && resultCode == Activity.RESULT_OK) {
            int year = data.getIntExtra("year", 0);
            int month = data.getIntExtra("month", 0);
            int day = data.getIntExtra("day", 0);
            String birthday = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
            txtKSRQ.setText(birthday);
        }
        if (requestCode == 0x12 && resultCode == Activity.RESULT_OK) {
            int year = data.getIntExtra("year", 0);
            int month = data.getIntExtra("month", 0);
            int day = data.getIntExtra("day", 0);
            String birthday = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
            txtJSRQ.setText(birthday);
        }
    }

}
