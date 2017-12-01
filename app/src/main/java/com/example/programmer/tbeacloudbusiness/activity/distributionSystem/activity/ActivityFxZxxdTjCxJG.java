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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07030700;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ProductListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxZxxdTjCxJgItem;

import java.util.ArrayList;

public class ActivityFxZxxdTjCxJG extends Activity implements AsyncListener, OnScrollListener, RefreshableView.PullToRefreshListener, OnClickListener, OnItemClickListener {


    private TextView txtSLSM;
    private ListView lstContent;
    private RefreshableView refView;


    private String bm;
    private String mc;
    private String ms;

    private TBEA07030700 req;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.instance.addActivity(this);
        bm = getIntent().getStringExtra("bm");
        mc = getIntent().getStringExtra("mc");
        ms = getIntent().getStringExtra("ms");
        setContentView(R.layout.activity_fx_zxxd_tj_cx_jg);
        init();
        setView();
        adapter = new Adapter();
        req = new TBEA07030700(this);
        req.ProductCode = bm;
        req.ProductDescription = ms;
        req.ProductName = mc;
        req.Page = 1;
        req.PageSize = 14;
        lstContent.setAdapter(adapter);
        lstContent.setOnScrollListener(this);
        lstContent.setOnItemClickListener(this);
        req.execute();
    }


    private void init() {
        txtSLSM = (TextView) findViewById(R.id.txtSLSM);
        lstContent = (ListView) findViewById(R.id.lstContent);
        refView = (RefreshableView) findViewById(R.id.refView);
    }

    private void setView() {
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
        if (serviceCode.equals(TBEA07030700.SERVICE_CODE)) {
            nexting = false;
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                if (req.ProductList.size() < req.PageSize) isEnd = true;
                fullData();
            } else if (!req.isHasError()) {
                com.mic.etoast2.Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                com.mic.etoast2.Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null && tag instanceof ProductListItem) {
            toSelectOK((ProductListItem) tag);
        }
    }


    private void toSelectOK(ProductListItem data) {
        Intent intent = new Intent();
        intent.putExtra("wlbm", data.ProductCode);
        intent.putExtra("wlmc", data.ProductName);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void toFB() {
        Intent intent = new Intent(this, ActivityFxTSFB.class);
        startActivity(intent);
    }

    private void fullData() {
        data.addAll(req.ProductList);
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

    private ArrayList<ProductListItem> data = new ArrayList<ProductListItem>();

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
                itemView = new ViewFxZxxdTjCxJgItem(ActivityFxZxxdTjCxJG.this, null);
            }
            ProductListItem item = data.get(index);
            ((ViewFxZxxdTjCxJgItem) itemView).fullData(item, ActivityFxZxxdTjCxJG.this);
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
        ProductListItem item = data.get(position);
        Intent intent = new Intent();
        intent.putExtra("wlbm", item.ProductCode);
        intent.putExtra("wlmc", item.ProductName);
        setResult(RESULT_OK, intent);
        finish();
    }
}
