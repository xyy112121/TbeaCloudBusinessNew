package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域选择
 */

public class ScanCodeRegionSelectActivity extends BaseActivity {
    private ListView mListView;
    private MyAdapter mAdapter;
    private  int mPage = 1;
    private int mPagesiz =10 ;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select);
        initTopbar("区域选择");
        mContext = this;
        mListView = (ListView)findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<Object> mList = new ArrayList<>();

        /**
         * 构造函数
         *
         * @param context
         *            android上下文环境
         */
        public MyAdapter(Context context) {
            this.context = context;
        }

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
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = null;
            try {
                view =  layoutInflater.inflate(
                        R.layout.activity_region_select_item, null);
                CheckBox ck = (CheckBox)view.findViewById(R.id.region_select_name);
                if(position == 0){
                    ck.setChecked(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

//        public void addAll(List<Collect> list){
//            mList.addAll(list);
//            notifyDataSetChanged();
//        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
