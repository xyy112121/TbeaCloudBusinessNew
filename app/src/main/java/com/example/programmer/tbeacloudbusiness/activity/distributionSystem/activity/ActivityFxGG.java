package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA11010000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.AnnouncementListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxGgItem;

import java.util.ArrayList;

public class ActivityFxGG extends Activity implements AsyncListener, OnScrollListener, RefreshableView.PullToRefreshListener, OnClickListener, OnItemClickListener, OnKeyListener {

    private EditText edtSearch;
    private ListView lstContent;
    private RefreshableView refView;


    private TBEA11010000 req;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_gg);
        init();
        setView();
        adapter = new Adapter();
        req = new TBEA11010000(this);
        req.Page = 1;
        req.PageSize = 14;
        lstContent.setAdapter(adapter);
        lstContent.setOnScrollListener(this);
        lstContent.setOnItemClickListener(this);
        req.execute();
    }


    private void init() {
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        lstContent = (ListView) findViewById(R.id.lstContent);
        refView = (RefreshableView) findViewById(R.id.refView);
    }

    private void setView() {
        edtSearch.setSingleLine(true);
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEND);
        edtSearch.setOnKeyListener(this);
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
        if (serviceCode.equals(TBEA11010000.SERVICE_CODE)) {
            nexting = false;
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                if (req.AnnouncementList.size() < req.PageSize) isEnd = true;
                fullData();
            } else if (!req.isHasError()) {
                Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        //if(butEvaluation==v)toEvaluation();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次  
        if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
            toQuery();
            //处理事件  
            return true;
        }
        return false;
    }

    private void toQuery() {
        data.clear();
        adapter.notifyDataSetInvalidated();
        isEnd = false;
        req.Name = edtSearch.getText().toString();
        req.Page = 1;
        req.PageSize = 14;
        req.execute();
    }

    private void fullData() {
        data.addAll(req.AnnouncementList);
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

    private ArrayList<AnnouncementListItem> data = new ArrayList<AnnouncementListItem>();

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
                itemView = new ViewFxGgItem(ActivityFxGG.this, null);
            }
            AnnouncementListItem item = data.get(index);
            ((ViewFxGgItem) itemView).fullData(item);
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
        AnnouncementListItem item = data.get(position);
        Intent intent = new Intent(ActivityFxGG.this, ActivityFxGgXQ.class);
        intent.putExtra("id", item.ID);
        startActivity(intent);
    }
}
