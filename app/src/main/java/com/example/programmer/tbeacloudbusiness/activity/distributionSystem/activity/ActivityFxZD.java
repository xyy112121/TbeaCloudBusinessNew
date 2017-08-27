package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.DownFileTask;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA12010000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA12020000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.RuleCategoryItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.RuleListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxZdItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxZdTitleItem;

import java.util.ArrayList;

public class ActivityFxZD extends Activity implements AsyncListener, OnScrollListener, RefreshableView.PullToRefreshListener, OnClickListener, OnItemClickListener {
    private LinearLayout linTitle;
    private ListView lstContent;
    private RefreshableView refView;


    private TBEA12010000 req;
    private TBEA12020000 req2;
    private DownFileTask downTask;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_zd);
        init();
        setView();
        req = new TBEA12010000(this);
        req2 = new TBEA12020000(this);
        downTask = new DownFileTask(this);
        req.execute();

        adapter = new Adapter();
        lstContent.setAdapter(adapter);
        lstContent.setOnScrollListener(this);
        lstContent.setOnItemClickListener(this);
    }


    private void init() {
        linTitle = (LinearLayout) findViewById(R.id.linTitle);
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
        if (serviceCode.equals(TBEA12010000.SERVICE_CODE)) {
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                fullTitle();
            } else if (!req.isHasError()) {
                Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
        if (serviceCode.equals(TBEA12020000.SERVICE_CODE)) {
            if (!req2.isHasError() && req2.rspInfo.rspSuccess) {
                if (req2.RuleList.size() < req2.PageSize) isEnd = true;
                fullData();
            } else if (!req2.isHasError()) {
                Toast.makeText(this, req2.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
        if (serviceCode.equals(DownFileTask.SERVICE_CODE)) {
            if (!downTask.isHasError()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("file://" + downTask.localPath));
                startActivity(intent);
            } else {
                Toast.makeText(this, "下载文件失败", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void fullTitle() {
        linTitle.removeAllViews();
        for (RuleCategoryItem item : req.RuleCategoryList) {
            ViewFxZdTitleItem itemView = new ViewFxZdTitleItem(this, null);
            itemView.fullData(item);
            itemView.setOnClickListener(this);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            linTitle.addView(itemView, lp);
        }
        if (linTitle.getChildCount() > 0) {
            onClick(linTitle.getChildAt(0));
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ViewFxZdTitleItem) {
            toTitleChange(((ViewFxZdTitleItem) v).getData());
        }
    }

    private void toTitleChange(RuleCategoryItem item) {
        int subCount = linTitle.getChildCount();
        for (int i = 0; i < subCount; i++) {
            ViewFxZdTitleItem vItem = (ViewFxZdTitleItem) linTitle.getChildAt(i);
            vItem.setFlag(vItem.getData().ID.equals(item.ID));
        }
        toQuery(item.ID);
    }


    private void toQuery(String id) {
        data.clear();
        adapter.notifyDataSetInvalidated();
        isEnd = false;
        req2.RuleCategoryId = id;
        req2.Page = 1;
        req2.PageSize = 14;
        req2.execute();
    }

    private void fullData() {
        data.addAll(req2.RuleList);
        adapter.notifyDataSetChanged();
    }


    private boolean nexting;
    private boolean isEnd;
    private int lastViewItemIndex;
    private int page = 1;

    public void nextPage() {
        if (nexting) return;
        page++;
        req2.Page = page;
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

    private ArrayList<RuleListItem> data = new ArrayList<RuleListItem>();

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
                itemView = new ViewFxZdItem(ActivityFxZD.this, null);
            }
            RuleListItem item = data.get(index);
            ((ViewFxZdItem) itemView).fullData(item, MyApplication.instance.getImgPath());
            return itemView;
        }
    }

    @Override
    public void onRefresh() {
        data.clear();
        adapter.notifyDataSetInvalidated();
        isEnd = false;
        page = 1;
        req2.Page = page;
        req2.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RuleListItem item = data.get(position);
        int typeIndex = item.Attachment.lastIndexOf(".");
        if (typeIndex == -1) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(MyApplication.instance.getImgPath(item.Attachment)));
            startActivity(intent);
        } else {
            if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                String fileType = item.Attachment.substring(typeIndex + 1);
                downTask.fileType = fileType;
                downTask.fileUrl = MyApplication.instance.getImgPath(item.Attachment);
                downTask.execute();
            } else {
                Toast.makeText(this, "找不到存储卡,不能下载文件", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
