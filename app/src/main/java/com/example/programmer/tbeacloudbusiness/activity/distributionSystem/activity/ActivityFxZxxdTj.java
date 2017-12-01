package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07030000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07030301;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07030400;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07030501;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.PreOrderProductListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.PriceTypeItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxZxxdTjItem;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityFxZxxdTj extends Activity implements AsyncListener, OnScrollListener, RefreshableView.PullToRefreshListener, OnClickListener, OnItemClickListener {
    private String OrderId = "";

    private EditText edtJHRQ;
    private EditText edtKF;
    private EditText edtJGLX;
    private EditText edtBZ;
    private TextView txtJGZJ;
    private TextView txtSC;
    private TextView txtTJ;
    private ListView lstContent;
    private RefreshableView refView;
    private ImageButton butSearch;

    private Adapter adapter;
    private TBEA07030400 req;
    private TBEA07030501 req2;
    private TBEA07030000 req3;
    private TBEA07030301 req4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrderId = getIntent().getStringExtra("OrderId");
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_zxxd_tj);
        init();
        setView();
        adapter = new Adapter();
        req4 = new TBEA07030301(this);
        req3 = new TBEA07030000(this);
        req2 = new TBEA07030501(this);
        req2.OrderId = OrderId;
        req = new TBEA07030400(this);
        req.OrderId = OrderId;
        req.Page = 1;
        req.PageSize = 14;
        lstContent.setAdapter(adapter);
        lstContent.setOnScrollListener(this);
        lstContent.setOnItemClickListener(this);
        req.execute();
    }


    private void init() {
        edtJHRQ = (EditText) findViewById(R.id.edtJHRQ);
        edtKF = (EditText) findViewById(R.id.edtKF);
        edtBZ = (EditText) findViewById(R.id.edtBZ);
        edtJGLX = (EditText) findViewById(R.id.edtJGLX);
        txtJGZJ = (TextView) findViewById(R.id.txtJGZJ);
        txtSC = (TextView) findViewById(R.id.txtSC);
        txtTJ = (TextView) findViewById(R.id.txtTJ);
        lstContent = (ListView) findViewById(R.id.lstContent);
        refView = (RefreshableView) findViewById(R.id.refView);
        butSearch = (ImageButton) findViewById(R.id.butSearch);
    }

    private void setView() {
        txtSC.setOnClickListener(this);
        txtTJ.setOnClickListener(this);
        refView.setOnRefreshListener(this, 0x113);
        butSearch.setOnClickListener(this);
        edtJHRQ.setOnClickListener(this);
        edtKF.setOnClickListener(this);
        edtJGLX.setOnClickListener(this);
    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        if (serviceCode.equals(TBEA07030400.SERVICE_CODE)) {
            refView.finishRefreshing();
            nexting = false;
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                if (req.PreOrderProductList.size() < req.PageSize) isEnd = true;
                fullData();
            } else if (!req.isHasError()) {
                com.mic.etoast2.Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                com.mic.etoast2.Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }

        if (serviceCode.equals(TBEA07030501.SERVICE_CODE)) {
            if (!req2.isHasError() && req2.rspInfo.rspSuccess) {
                com.mic.etoast2.Toast.makeText(this, req2.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
                onRefresh();
            } else if (!req2.isHasError()) {
                com.mic.etoast2.Toast.makeText(this, req2.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                com.mic.etoast2.Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }

        if (serviceCode.equals(TBEA07030000.SERVICE_CODE)) {
            if (!req3.isHasError() && req3.rspInfo.rspSuccess) {
                com.mic.etoast2.Toast.makeText(this, req3.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else if (!req3.isHasError()) {
                com.mic.etoast2.Toast.makeText(this, req3.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                com.mic.etoast2.Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }

        if (serviceCode.equals(TBEA07030301.SERVICE_CODE)) {
            if (!req4.isHasError() && req4.rspInfo.rspSuccess) {
                _toJGLX();
            } else if (!req4.isHasError()) {
                com.mic.etoast2.Toast.makeText(this, req4.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                com.mic.etoast2.Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null && tag instanceof PreOrderProductListItem) {
            PreOrderProductListItem item = (PreOrderProductListItem) tag;
            toMove(item);
        } else {
            if (txtTJ == v) toTJ();
            if (txtSC == v) toSC();
            if (butSearch == v) toOK();
            if (edtJHRQ == v) toJHRQ();
            if (edtKF == v) toKF();
            if (edtJGLX == v) toJGLX();
        }
    }

    @SuppressLint("DefaultLocale")
    private void toJGLX() {
        boolean canEdit = req.preOrderInfo.CanModify.toUpperCase().equals("YES");
        if (!canEdit) return;
        if (req4.PriceTypeList.size() == 0) {
            req4.execute();
        } else {
            _toJGLX();
        }
    }


    private Dialog jglxDialog;

    private void _toJGLX() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("价格类型选择");
        ArrayList<String> itemArray = new ArrayList<String>();
        int gsIndex = 0;
        for (int i = 0; i < req4.PriceTypeList.size(); i++) {
            PriceTypeItem cItem = req4.PriceTypeList.get(i);
            if (cItem.Name.equals(edtJGLX.getText().toString())) gsIndex = i;
            itemArray.add(cItem.Name);
        }
        String[] items = itemArray.toArray(new String[0]);
        builder.setSingleChoiceItems(items, gsIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                req3.PriceType = req4.PriceTypeList.get(which).Id;
                edtJGLX.setText(req4.PriceTypeList.get(which).Name);
                jglxDialog.dismiss();
            }
        });
        jglxDialog = builder.show();
    }


    private void toMove(PreOrderProductListItem item) {
        //等接口
    }


    @SuppressLint("DefaultLocale")
    private void toJHRQ() {
        boolean canEdit = req.preOrderInfo.CanModify.toUpperCase().equals("YES");
        if (canEdit) {
            Intent intent = new Intent(this, ActivityUserInfoChang6.class);
            intent.putExtra("title", "选择日期:");
            intent.putExtra("value", -1);
            startActivityForResult(intent, 0x11);
        }
    }

    @SuppressLint("DefaultLocale")
    private void toKF() {
        boolean canEdit = req.preOrderInfo.CanModify.toUpperCase().equals("YES");
        if (canEdit) {
            Intent intent = new Intent(this, ActivityFxZxxdKFTjCX.class);
            startActivityForResult(intent, 0x12);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x11 && resultCode == Activity.RESULT_OK) {
            int year = data.getIntExtra("year", 0);
            int month = data.getIntExtra("month", 0);
            int day = data.getIntExtra("day", 0);
            String birthday = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
            edtJHRQ.setText(birthday);
        }
        if (requestCode == 0x12 && resultCode == Activity.RESULT_OK) {
            kfbm = data.getStringExtra("kfbm");
            String kfmc = data.getStringExtra("kfmc");
            edtKF.setText(kfmc);
        }
        if (requestCode == 0x21 && resultCode == Activity.RESULT_OK) {
            onRefresh();
        }
    }

    private String kfbm;

    private void toOK() {
        String jhrq = edtJHRQ.getText().toString();
        String bz = edtBZ.getText().toString();
        if (jhrq.isEmpty()) {
            com.mic.etoast2.Toast.makeText(this, "交货日期必须填写", Toast.LENGTH_SHORT).show();
            return;
        }
        if (req3.PriceType == null || req3.PriceType.isEmpty()) {
            com.mic.etoast2.Toast.makeText(this, "请选择价格类型", Toast.LENGTH_SHORT).show();
            return;
        }
        req3.CustomerId = kfbm;
        req3.DeliveryDate = jhrq;
        req3.OrderNote = bz;
        req3.OrderId = OrderId;
        req3.execute();
    }

    private void toSC() {
        req2.execute();
    }

    @SuppressLint("DefaultLocale")
    private void toTJ() {
        boolean canEdit = req.preOrderInfo.CanModify.toUpperCase().equals("YES");
        if (canEdit) {
            Intent intent = new Intent(this, ActivityFxZxxdTjTj.class);
            intent.putExtra("OrderId", OrderId);
            //startActivity(intent);
            startActivityForResult(intent, 0x21);
        }
    }


    @SuppressLint("DefaultLocale")
    private void fullData() {
        edtJHRQ.setText(req.preOrderInfo.DeliverDate);
        edtKF.setText(req.preOrderInfo.CustomerName);
        edtBZ.setText(req.preOrderInfo.OrderNote);
        txtJGZJ.setText("￥" + req.preOrderInfo.OrderTotleMoney);
        edtJGLX.setText(req.preOrderInfo.PriceTypeName);
        req3.PriceType = req.preOrderInfo.PriceType;
        if (!req.preOrderInfo.CanModify.toUpperCase().equals("YES")) {
            txtTJ.setVisibility(View.INVISIBLE);
            txtSC.setVisibility(View.INVISIBLE);
            butSearch.setVisibility(View.INVISIBLE);
            edtBZ.setEnabled(false);
        } else {
            txtTJ.setVisibility(View.VISIBLE);
            txtSC.setVisibility(View.VISIBLE);
            butSearch.setVisibility(View.VISIBLE);
            edtBZ.setEnabled(true);
        }
        data.addAll(req.PreOrderProductList);
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

    private ArrayList<PreOrderProductListItem> data = new ArrayList<PreOrderProductListItem>();

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

        @SuppressLint("DefaultLocale")
        @Override
        public View getView(int index, View itemView, ViewGroup parent) {
            if (itemView == null) {
                itemView = new ViewFxZxxdTjItem(ActivityFxZxxdTj.this, null);
            }
            PreOrderProductListItem item = data.get(index);
            boolean canEdit = req.preOrderInfo.CanModify.toUpperCase().equals("YES");
            ((ViewFxZxxdTjItem) itemView).fullData(canEdit, item, ActivityFxZxxdTj.this);
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
        /*
        ProductListItem item=data.get(position);
		Intent intent = new Intent(this,ActivityFxTSXQ.class);
		intent.putExtra("id", item.ID);
		startActivity(intent);*/
    }


}
