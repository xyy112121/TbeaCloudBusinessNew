package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA13010000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ContactPersonItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxLxwmItem;

import java.util.ArrayList;

public class ActivityFxLXWM extends Activity implements AsyncListener, RefreshableView.PullToRefreshListener, OnClickListener {

    private ListView lstContent;
    private RefreshableView refView;

    private Adapter adapter;
    private TBEA13010000 req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_lxwm);
        init();
        setView();
        adapter = new Adapter();
        req = new TBEA13010000(this);
        lstContent.setAdapter(adapter);
        req.execute();
    }


    private void init() {
        lstContent = (ListView) findViewById(R.id.lstContent);
        refView = (RefreshableView) findViewById(R.id.refView);
    }

    private void setView() {
        refView.setOnRefreshListener(this, 0x123);
    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        refView.finishRefreshing();
        if (serviceCode.equals(TBEA13010000.SERVICE_CODE)) {
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                fullData();
            } else if (!req.isHasError()) {
                Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void fullData() {
        data.addAll(req.ContactPersonList);
        adapter.notifyDataSetChanged();
    }


    private ArrayList<ContactPersonItem> data = new ArrayList<ContactPersonItem>();

    class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int index) {
            return index;
        }


        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View itemView, ViewGroup parent) {
            if (itemView == null) {
                itemView = new ViewFxLxwmItem(ActivityFxLXWM.this, null);
            }
            ContactPersonItem item = data.get(index);
            ((ViewFxLxwmItem) itemView).fullData(item, ActivityFxLXWM.this);
            return itemView;
        }
    }

    @Override
    public void onRefresh() {
        data.clear();
        adapter.notifyDataSetInvalidated();
        req.execute();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ImageView) {
            Object obj = v.getTag();
            if (obj != null && obj instanceof ContactPersonItem) {
                ContactPersonItem tag = (ContactPersonItem) obj;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tag.MobileNumber));
                startActivity(intent);
            }
        }
    }
}
