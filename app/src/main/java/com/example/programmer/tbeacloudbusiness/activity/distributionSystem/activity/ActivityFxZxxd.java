package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07030100;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07030200;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07030300;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.PreOrderListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxZxxdItem;

import java.util.ArrayList;

public class ActivityFxZxxd extends Activity implements AsyncListener, OnScrollListener, RefreshableView.PullToRefreshListener, OnClickListener, OnItemClickListener {


    private ListView lstContent;
    private RefreshableView refView;
    private Button butQuery;


    private TBEA07030100 req;

    private TBEA07030200 req2;
    private TBEA07030300 req3;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_zxxd);
        init();
        setView();
        adapter = new Adapter();
        req3 = new TBEA07030300(this);
        req2 = new TBEA07030200(this);
        req = new TBEA07030100(this);
        req.Page = 1;
        req.PageSize = 14;
        lstContent.setAdapter(adapter);
        lstContent.setOnScrollListener(this);
        lstContent.setOnItemClickListener(this);
        req.execute();
    }


    private void init() {
        butQuery = (Button) findViewById(R.id.butQuery);
        lstContent = (ListView) findViewById(R.id.lstContent);
        refView = (RefreshableView) findViewById(R.id.refView);
    }

    private void setView() {
        butQuery.setOnClickListener(this);
        refView.setOnRefreshListener(this, 0x113);
    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        if (serviceCode.equals(TBEA07030100.SERVICE_CODE)) {
            refView.finishRefreshing();
            nexting = false;
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                if (req.PreOrderList.size() < req.PageSize) isEnd = true;
                fullData();
            } else if (!req.isHasError()) {
                Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
        if (serviceCode.equals(TBEA07030200.SERVICE_CODE)) {
            if (!req2.isHasError() && req2.rspInfo.rspSuccess) {
                Toast.makeText(this, req2.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
                onRefresh();
            } else if (!req2.isHasError()) {
                Toast.makeText(this, req2.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
        if (serviceCode.equals(TBEA07030300.SERVICE_CODE)) {
            if (!req3.isHasError() && req3.rspInfo.rspSuccess) {
                Toast.makeText(this, req3.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
                onRefresh();
            } else if (!req3.isHasError()) {
                Toast.makeText(this, req3.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (butQuery == v) {
            toAdd();
        } else if (butQuery == v) {
            toAdd();
        } else if (tag != null && tag instanceof Object[]) {
            Object[] t = (Object[]) tag;
            String type = (String) t[0];
            if (type.equals("ckxq")) {
                toCKXQ((PreOrderListItem) t[1]);
            } else if (type.equals("sc")) {
                toSC((PreOrderListItem) t[1]);
            } else if (type.equals("tj")) {
                toTJ((PreOrderListItem) t[1]);
            }
        }
    }

    private void toCKXQ(PreOrderListItem data) {
        Intent intent = new Intent(this, ActivityFxZxxdTj.class);
        intent.putExtra("OrderId", data.Id);
        startActivity(intent);
    }

    private void toSC(PreOrderListItem data) {
        req2.OrderId = data.Id;
        req2.execute();
    }

    private void toTJ(PreOrderListItem data) {
        req3.OrderId = data.Id;
        req3.execute();
    }

    private void toAdd() {
        Intent intent = new Intent(this, ActivityFxZxxdTj.class);
        startActivity(intent);
    }

    private void fullData() {
        data.addAll(req.PreOrderList);
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

    private ArrayList<PreOrderListItem> data = new ArrayList<PreOrderListItem>();

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
                itemView = new ViewFxZxxdItem(ActivityFxZxxd.this, null);
            }
            PreOrderListItem item = data.get(index);
            ((ViewFxZxxdItem) itemView).fullData(item, ActivityFxZxxd.this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PreOrderListItem item = data.get(position);
        Intent intent = new Intent(this, ActivityFxZxxdTj.class);
        intent.putExtra("OrderId", item.Id);
        startActivity(intent);
    }
}
