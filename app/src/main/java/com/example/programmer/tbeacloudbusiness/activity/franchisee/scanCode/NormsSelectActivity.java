package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * 规格选择
 */

public class NormsSelectActivity extends TopActivity {
    private ListView mListView;
    String[] strings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerate_code_select_norms_list);
        initTopbar("型号规格");
        strings = new String[] {"1*1","1*1.5","1*2","1*2.5","1*4"};
        mListView = (ListView)findViewById(R.id.listview);
        MyAdapter mAdapter = new MyAdapter(this,strings);
        mListView.setAdapter(mAdapter);
    }

    public class MyAdapter extends ArrayAdapter<String> {

        private Activity activity;

        public MyAdapter(Activity activity, String[] objects) {
            super(activity, R.layout.activty_create_code_select_type_list_item, R.id.label, objects);//为自定义视图指定XML布局文件
            this.activity = activity;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            final String label = getItem(position);
            if (view == null) {
                view = activity.getLayoutInflater().inflate(R.layout.activty_create_code_select_type_list_item,
                        parent, false);
            }
            final TextView header = (TextView) view.findViewById(R.id.header);
            final TextView labelView = (TextView) view.findViewById(R.id.label);

            View headView = view.findViewById(R.id.list_item_header_view);
            header.setVisibility(View.GONE);//隐藏分段标头
            headView.setVisibility(View.GONE);
            final CheckBox ck = (CheckBox)view.findViewById(R.id.item_ck);
            view.findViewById(R.id.list_item_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    boolean isSelect =  ck.isChecked();
//                    if(isSelect == true){
//                        selectList.remove(position+"");
//                        ck.setChecked(false);
//                        ck.setVisibility(View.GONE);
//                        labelView.setTextColor(ContextCompat.getColor(activity,R.color.text_color));
//                    }else {
//                        selectList.add(position+"");
//                        ck.setChecked(true);
                        ck.setVisibility(View.VISIBLE);
                        labelView.setTextColor(ContextCompat.getColor(activity,R.color.text_color2));
                        Intent intent = new Intent();
                        intent.putExtra("value",labelView.getText()+"");
                        setResult(RESULT_OK,intent);
                        finish();
//                    }
                }
            });

            return super.getView(position, view, parent);
        }
    }
}
