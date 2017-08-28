package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *销售详情-已出售
 */

public class MarketInfoBeSoldActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info_be_sold);
        initTopbar("已出售");
        initView();
    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.listview);
        MyAdapter mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {

    }

    private class MyAdapter extends BaseAdapter {
        private List<Object> mList = new ArrayList<>();

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_market_info_be_sold_list_item, null);
            }
            return view;
        }
    }
}
