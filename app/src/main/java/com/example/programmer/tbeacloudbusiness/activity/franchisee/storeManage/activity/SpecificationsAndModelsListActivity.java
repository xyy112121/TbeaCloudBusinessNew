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
 * 规格型号
 */

public class SpecificationsAndModelsListActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifications_and_models_list);
        initTopbar("规格型号","添加",this);
        initView();
    }

    private void  initView(){
        ListView listView = (ListView) findViewById(R.id.listview);
        MyAdapter mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext,SpecificationsAndModelsEditActivity.class);
        startActivity(intent);
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
                convertView = getLayoutInflater().inflate(R.layout.activity_specifications_and_models_list_item, null);
            }
            return convertView;
        }
    }
}
