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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA09010000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.KucunListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxKcQueryResultItem;

import java.util.ArrayList;


public class ActivityFxKcQueryResult extends Activity implements AsyncListener, OnScrollListener, RefreshableView.PullToRefreshListener, OnClickListener {

    private TextView txtGJZ;
    private TextView txtSLSM;
    private ImageView imgHDDB;
    private ImageButton butSearch;
    private ListView lstContent;
    private RefreshableView refView;


    private TBEA09010000 req;
    private Adapter adapter;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("name");
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_kc_query_result);
        init();
        setView();
        adapter = new Adapter();
        req = new TBEA09010000(this);
        if (name != null) req.CommodityName = name;
        req.Page = 1;
        req.PageSize = 14;
        lstContent.setAdapter(adapter);
        lstContent.setOnScrollListener(this);
        req.execute();
    }


    private void init() {
        txtGJZ = (TextView) findViewById(R.id.txtGJZ);
        txtSLSM = (TextView) findViewById(R.id.txtSLSM);
        imgHDDB = (ImageView) findViewById(R.id.imgHDDB);
        butSearch = (ImageButton) findViewById(R.id.butSearch);
        lstContent = (ListView) findViewById(R.id.lstContent);
        refView = (RefreshableView) findViewById(R.id.refView);
    }

    private void setView() {
        butSearch.setOnClickListener(this);
        refView.setOnRefreshListener(this, 0x113);
        if (name == null) {
            txtGJZ.setText("");
        } else {
            txtGJZ.setText(name);
        }
        imgHDDB.setVisibility(View.GONE);
    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        refView.finishRefreshing();
        if (serviceCode.equals(TBEA09010000.SERVICE_CODE)) {
            nexting = false;
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                if (req.KucunList.size() < req.PageSize) isEnd = true;
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
        if (butSearch == v) toSearch();
    }

    private void toSearch() {
        Intent intent = new Intent(this, ActivityFxKcQuery.class);
        startActivity(intent);
    }

    private void fullData() {
        data.addAll(req.KucunList);
        adapter.notifyDataSetChanged();
        txtSLSM.setText("查询到" + req.Totlecount + "条记录");
        txtGJZ.setText(req.CommodityName);
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

    private ArrayList<KucunListItem> data = new ArrayList<KucunListItem>();

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
                itemView = new ViewFxKcQueryResultItem(ActivityFxKcQueryResult.this, null);
            }
            KucunListItem item = data.get(index);
            ((ViewFxKcQueryResultItem) itemView).fullData(item);
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
}
