package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA03010103;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;

public class ActivityFxZxxdTjCX extends Activity implements AsyncListener, OnClickListener {

    private EditText edtBM;
    private EditText edtMC;
    private EditText edtMS;
    private Button butQuery;


    private TBEA03010103 req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_zxxd_tj_cx);
        init();
        setView();
        //req=new TBEA03010103(this);
        //req.execute();
    }


    private void init() {
        edtBM = (EditText) findViewById(R.id.edtBM);
        edtMC = (EditText) findViewById(R.id.edtMC);
        edtMS = (EditText) findViewById(R.id.edtMS);
        butQuery = (Button) findViewById(R.id.butQuery);
    }

    private void setView() {
        butQuery.setOnClickListener(this);
    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        if (serviceCode.equals(TBEA03010103.SERVICE_CODE)) {
            /*
			nexting=false;
			if(!req.isHasError() && req.rspInfo.rspSuccess){
				if(req.AppraiseList.size()<req.PageSize)isEnd=true;
				fullData();
			}else if(!req.isHasError()){
				Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();	
			}else{
				Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
			}*/
        }
    }

    @Override
    public void onClick(View v) {
        if (butQuery == v) toQuery();
    }

    private void toQuery() {
        String bm = edtBM.getText().toString();
        String mc = edtMC.getText().toString();
        String ms = edtMS.getText().toString();
        Intent intent = new Intent(this, ActivityFxZxxdTjCxJG.class);
        intent.putExtra("bm", bm);
        intent.putExtra("mc", mc);
        intent.putExtra("ms", ms);
        startActivityForResult(intent, 0x123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x123 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
