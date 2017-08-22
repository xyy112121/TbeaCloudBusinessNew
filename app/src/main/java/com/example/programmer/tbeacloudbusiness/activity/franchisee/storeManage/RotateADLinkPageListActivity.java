package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 链接页面
 */

public class RotateADLinkPageListActivity extends BaseActivity {
    LinearLayout parentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_ad_linkpage_list);
        initTopbar("链接页面");
        initView();
    }

    private void initView() {
        parentLayout = (LinearLayout) findViewById(R.id.parent_layout);

        ListView listView = (ListView) findViewById(R.id.listview);
        MyAdapter mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);

        final List<String> typeList = new ArrayList<>();
        typeList.add("店铺商品");
        typeList.add("店铺动态");

        findViewById(R.id.rotate_ad_edit_link_page_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
                popWindow1.init(parentLayout, R.layout.pop_window_header1, R.layout.pop_window_tv, typeList);
                popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
                    @Override
                    public void onItemClick(String text) {
                        ((TextView) findViewById(R.id.completion_data_sex)).setText(text);
                    }
                });

            }
        });
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
//                convertView = getLayoutInflater().inflate(R.layout.activity_rotate_ad_edit_shop_dynamic_item, null);
                convertView = getLayoutInflater().inflate(R.layout.activity_rotate_ad_linkpage_list_commodity_item, null);
            }
            return convertView;
        }
    }
}
