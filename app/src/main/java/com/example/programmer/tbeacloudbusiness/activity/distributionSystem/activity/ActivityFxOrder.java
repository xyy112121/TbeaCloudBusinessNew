package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07010100;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07020000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.OrderListItem2;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.OrderStatusListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxOrderItem;


public class ActivityFxOrder extends Activity implements AsyncListener, OnScrollListener, RefreshableView.PullToRefreshListener, OnClickListener, OnItemClickListener {

    private LinearLayout linKSRQ;
    private LinearLayout linJSRQ;
    private TextView txtKSRQ;
    private TextView txtJSRQ;
    private TextView txtZT;
    private Button butQuery;
    private ImageButton butSearch;
    private ListView lstContent;
    private RefreshableView refView;


    private TBEA07020000 req;
    private TBEA07010100 req2;
    private Adapter adapter;

    private String ksrq;
    private String jsrq;
    private String ddzt;
    private String ddh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ksrq = getIntent().getStringExtra("ksrq");
        jsrq = getIntent().getStringExtra("jsrq");
        ddzt = getIntent().getStringExtra("ddzt");
        ddh = getIntent().getStringExtra("ddh");
//        AppApp.instance.addActivity(this);
        setContentView(R.layout.activity_fx_order);
        init();
        setView();
        adapter = new Adapter();
        req = new TBEA07020000(this);
        req2 = new TBEA07010100(this);
        req.Page = 1;
        req.PageSize = 14;
        if (ksrq != null) req.StartDate = ksrq;
        if (jsrq != null) req.EndDate = jsrq;
        if (ddzt != null) req.OrderStatusId = ddzt;
        if (ddh != null) req.OrderCode = ddh;
        lstContent.setAdapter(adapter);
        lstContent.setOnScrollListener(this);
        lstContent.setOnItemClickListener(this);
        req.execute();
    }


    private void init() {
        txtKSRQ = (TextView) findViewById(R.id.txtKSRQ);
        txtJSRQ = (TextView) findViewById(R.id.txtJSRQ);
        linKSRQ = (LinearLayout) findViewById(R.id.linKSRQ);
        linJSRQ = (LinearLayout) findViewById(R.id.linJSRQ);
        txtZT = (TextView) findViewById(R.id.txtZT);
        butQuery = (Button) findViewById(R.id.butQuery);
//        butSearch = (ImageButton) findViewById(R.id.butSearch);
        lstContent = (ListView) findViewById(R.id.lstContent);
        refView = (RefreshableView) findViewById(R.id.refView);
    }

    private void setView() {
        linKSRQ.setOnClickListener(this);
        linJSRQ.setOnClickListener(this);
        txtZT.setOnClickListener(this);
        butSearch.setOnClickListener(this);
        butQuery.setOnClickListener(this);
        refView.setOnRefreshListener(this, 0x123);
        Calendar ca = Calendar.getInstance(Locale.getDefault());
        String js = String.format(Locale.getDefault(), "%04d-%02d-%02d", ca.get(Calendar.YEAR), ca.get(Calendar.MONTH) + 1, ca.get(Calendar.DAY_OF_MONTH));
        txtJSRQ.setText(js);
        ca.add(Calendar.YEAR, -2);
        String ks = String.format(Locale.getDefault(), "%04d-%02d-%02d", ca.get(Calendar.YEAR), ca.get(Calendar.MONTH) + 1, ca.get(Calendar.DAY_OF_MONTH));
        txtKSRQ.setText(ks);

    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        refView.finishRefreshing();
        if (serviceCode.equals(TBEA07020000.SERVICE_CODE)) {
            nexting = false;
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                if (req.OrderList.size() < req.PageSize) isEnd = true;
                fullData();
            } else if (!req.isHasError()) {
                Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
        if (serviceCode.equals(TBEA07010100.SERVICE_CODE)) {
            if (!req2.isHasError() && req2.rspInfo.rspSuccess) {
                _toZT();
            } else if (!req2.isHasError()) {
                Toast.makeText(this, req2.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (linKSRQ == v) {
            toKSRQ();
        } else if (linJSRQ == v) {
            toJSRQ();
        } else if (txtZT == v) {
            toZT();
        } else if (butSearch == v) {
            toSearch();
        } else if (butQuery == v) {
            toQuery();
        } else if (v instanceof TextView) {
            pTextView((TextView) v);
        }
    }

    private void pTextView(TextView v) {
        Object[] tag = (Object[]) v.getTag();
        String type = (String) tag[0];
        OrderListItem2 datat = (OrderListItem2) tag[1];
        if (type.equals("1")) {
            //发运明细
//            Intent intent = new Intent(ActivityFxOrder.this, ActivityFxOrderFYMX.class);
//            intent.putExtra("id", datat.Id);
//            startActivity(intent);
            return;
        }
        if (type.equals("2")) {
            //订货明细
//            Intent intent = new Intent(ActivityFxOrder.this, ActivityFxOrderDHMX.class);
//            intent.putExtra("id", datat.Id);
//            startActivity(intent);
            return;
        }
    }

    private void toKSRQ() {
        Intent intent = new Intent(this, ActivityUserInfoChang6.class);
        intent.putExtra("title", "选择日期:");
        intent.putExtra("value", -1);
        startActivityForResult(intent, 0x11);
    }

    private void toJSRQ() {
        Intent intent = new Intent(this, ActivityUserInfoChang6.class);
        intent.putExtra("title", "选择日期:");
        intent.putExtra("value", -1);
        startActivityForResult(intent, 0x12);
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

    private void toZT() {
        req2.execute();
    }

    private Dialog ztDialog;

    private void _toZT() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("订单状态选择");
        ArrayList<String> itemArray = new ArrayList<String>();
        for (int i = 0; i < req2.OrderStatusList.size(); i++) {
            OrderStatusListItem cItem = req2.OrderStatusList.get(i);
            itemArray.add(cItem.Name);
        }
        builder.setSingleChoiceItems(itemArray.toArray(new String[0]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OrderStatusListItem item = req2.OrderStatusList.get(which);
                ddzt = item.Id;
                txtZT.setText(item.Name);
                ztDialog.dismiss();
            }
        });
        ztDialog = builder.show();
    }

    private void toSearch() {
//        startActivity(new Intent(this, ActivityFxOrderQuery.class));
    }

    private void toQuery() {
        data.clear();
        adapter.notifyDataSetInvalidated();
        isEnd = false;
        String ksrq = txtKSRQ.getText().toString().trim();
        String jsrq = txtJSRQ.getText().toString().trim();
        req.StartDate = ksrq;
        req.EndDate = jsrq;
        if (ddzt != null) {
            req.OrderStatusId = ddzt;
        } else {
            req.OrderStatusId = "";
        }
        req.OrderCode = "";
        req.Page = 1;
        req.PageSize = 14;
        req.execute();
    }

    private void fullData() {
        data.addAll(req.OrderList);
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

    private ArrayList<OrderListItem2> data = new ArrayList<OrderListItem2>();

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
                itemView = new ViewFxOrderItem(ActivityFxOrder.this, null);
            }
            OrderListItem2 item = data.get(index);
            ((ViewFxOrderItem) itemView).fullData(item, ActivityFxOrder.this);
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
        req.EndDate = "";
        req.StartDate = "";
        req.OrderCode = "";
        req.OrderStatusId = "";
        req.execute();
        ddzt = null;
        txtKSRQ.setText("");
        txtJSRQ.setText("");
        txtZT.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        OrderListItem2 item = data.get(position);
//        Intent intent = new Intent(ActivityFxOrder.this, ActivityFxOrderDHMX.class);
//        intent.putExtra("id", item.Id);
//        startActivity(intent);
    }
}
