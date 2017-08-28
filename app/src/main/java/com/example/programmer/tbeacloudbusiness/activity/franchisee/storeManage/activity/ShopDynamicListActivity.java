package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity;

import android.content.Intent;
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
 * 店铺动态
 */

public class ShopDynamicListActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_dynamic_list);
        initTopbar("店铺动态");
        initView();
    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.listview);
        MyAdapter mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);

        findViewById(R.id.shop_dynamic_list_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop_dynamic_list_add:
                Intent intent = new Intent(mContext,ShopDynamicAddActivity.class);
                startActivity(intent);
                break;
        }

    }

    private class MyAdapter extends BaseAdapter {
        private List<Object> mList = new ArrayList<>();

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_shop_dynamic_list_item, null);
            }
            return convertView;
        }
    }
}
