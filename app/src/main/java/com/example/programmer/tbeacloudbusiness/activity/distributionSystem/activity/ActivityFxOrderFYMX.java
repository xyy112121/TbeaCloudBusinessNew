package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07020200;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07020300;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.OrderInfo;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.TransitionListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ActivityFxOrderFYMXView1;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ActivityFxOrderFYMXView3;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;

import java.util.ArrayList;

public class ActivityFxOrderFYMX extends Activity implements AsyncListener, OnScrollListener, RefreshableView.PullToRefreshListener, OnClickListener {


    private ListView lstContent;
    private RefreshableView refView;

    private TBEA07020200 req;
    private TBEA07020300 req2;
    private Adapter adapter;
    private String id;

    private ActivityFxOrderFYMXView1 head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_order_fymx);
        init();
        adapter = new Adapter();
        lstContent.setAdapter(adapter);
        lstContent.setOnScrollListener(this);
        head = new ActivityFxOrderFYMXView1(this, null);
        lstContent.addHeaderView(head);
        req = new TBEA07020200(this);
        req2 = new TBEA07020300(this);
        req.Page = page;
        req.PageSize = 14;
        req.OrderId = id;
        req.execute();
    }


    private void init() {
        lstContent = (ListView) findViewById(R.id.lstContent);
        refView = (RefreshableView) findViewById(R.id.refView);
        refView.setOnRefreshListener(this, 0x113);
    }


    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        refView.finishRefreshing();
        if (serviceCode.equals(TBEA07020200.SERVICE_CODE)) {
            nexting = false;
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                if (req.TransitionList.size() < req.PageSize) isEnd = true;
                fullData();
            } else if (!req.isHasError()) {
                Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
        if (serviceCode.equals(TBEA07020300.SERVICE_CODE)) {
            if (!req2.isHasError() && req2.rspInfo.rspSuccess) {
                //onRefresh();
                Toast.makeText(this, req2.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else if (!req2.isHasError()) {
                Toast.makeText(this, req2.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fullData() {
        head.fullData(req.orderInfo, this);
        data.addAll(req.TransitionList);
        adapter.notifyDataSetChanged();
    }

    private boolean nexting;
    private boolean isEnd;
    private int lastViewItemIndex;
    private int page = 1;

    public void nextPage() {
        if (nexting) return;
        page++;
        req.Page = page;
        nexting = true;
        req.execute();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        lastViewItemIndex = firstVisibleItem + visibleItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            int lastItemsIndex = adapter.getCount();
            if (!isEnd && lastItemsIndex == lastViewItemIndex) {
                nextPage();
            }
        }
    }


    private ArrayList<TransitionListItem> data = new ArrayList<TransitionListItem>();


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
                itemView = new ActivityFxOrderFYMXView3(ActivityFxOrderFYMX.this, null);
            }
            TransitionListItem item = data.get(index);
            ((ActivityFxOrderFYMXView3) itemView).fullData(item, ActivityFxOrderFYMX.this);
            return itemView;
        }
    }


    @Override
    public void onRefresh() {
        data.clear();
        adapter.notifyDataSetInvalidated();
        isEnd = false;
        page = 1;
        req.Page = page;
        req.execute();

    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof Object[]) {
            Object[] _tag = (Object[]) tag;
            String type = (String) _tag[0];
            if (type.equals("qrsh")) {
                toQRSH((TransitionListItem) _tag[1]);
            } else if (type.equals("dhcpmx")) {
                toDHCPMX((OrderInfo) _tag[1]);
            }
        }
    }

    private void toQRSH(TransitionListItem data) {
        req2.TransitionCode = data.TransitionCode;
        req2.TransitionCarCode = data.TransitionCarCode;
        req2.execute();
    }

    private void toDHCPMX(OrderInfo data) {
        Intent intent = new Intent(this, ActivityFxOrderDHMX.class);
        intent.putExtra("id", data.Id);
        startActivity(intent);
    }
}
