package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 型号选择
 */

public class TypeSelectActivity extends TopActivity {
    private TextView topHeader;//用于访问分段标头
    private LinearLayout mTopHeaderLayout;
    private int topVisiblePosition = -1;
    private ListView mListView;
    String[] strings;
    private List<String> selectList = new ArrayList<>();
    private  boolean mFlag = false;//判断当前取消收藏的按钮是否出现


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_create_code_select_type_list);
        initTopbar("型号规格");
        topHeader = (TextView) findViewById(R.id.header);
        strings = new String[] {"Ace","Bcw","Bcw","Bcw","Bcw","Bca","Bcc","Ca","Ca","Ca","Ca","Ca","Da","Da","Da","Da","Da","Da","Da","Ea","Ea","Ea",
                "Ea","Ea","Ea","Fa","Fa","Fa","Fa","Ga","Ga","Ga","Ga","Ga"};
        mListView = (ListView)findViewById(R.id.listview);
        MyAdapter mAdapter = new MyAdapter(this,strings);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != topVisiblePosition) {
                    topVisiblePosition = firstVisibleItem;
                    setTopHeader(firstVisibleItem);
                }
            }
        });
    }

    private void setTopHeader(int pos) {
        final String text = strings[pos].substring(0, 1);
        topHeader.setText(text);//更新文本内容
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
            if(position == 0){
                header.setVisibility(View.GONE);//隐藏分段标头
                headView.setVisibility(View.GONE);
            }else {
                ////检查列表项起始字母是否发生了改变，如果发生改变，该列表项就是分段中的第一项，修改分段标头的内容并显示该分段标头,否则隐藏
                if ( getItem(position - 1).charAt(0) != label.charAt(0)) {
                    header.setVisibility(View.VISIBLE);
                    headView.setVisibility(View.VISIBLE);
                    header.setText(label.substring(0, 1));
                } else {
                    header.setVisibility(View.GONE);//隐藏分段标头
                    headView.setVisibility(View.GONE);
                }
            }
            final CheckBox ck = (CheckBox)view.findViewById(R.id.item_ck);
                if(selectList.size() > 0 && selectList.get(position).equals(position)){
                    ck.setVisibility(View.VISIBLE);
                    labelView.setTextColor(ContextCompat.getColor(activity,R.color.text_color2));
                }else {
                    ck.setVisibility(View.GONE);
                    labelView.setTextColor(ContextCompat.getColor(activity,R.color.text_color));

            }

            view.findViewById(R.id.list_item_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   boolean isSelect =  ck.isChecked();
                    if(isSelect == true){
                        selectList.remove(position+"");
                        ck.setChecked(false);
                        ck.setVisibility(View.GONE);
                        labelView.setTextColor(ContextCompat.getColor(activity,R.color.text_color));
                    }else {
                        selectList.add(position+"");
                        ck.setChecked(true);
                        ck.setVisibility(View.VISIBLE);
                        labelView.setTextColor(ContextCompat.getColor(activity,R.color.text_color2));
                        Intent intent = new Intent();
                        intent.putExtra("value",labelView.getText()+"");
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            });

            return super.getView(position, view, parent);
        }
    }
}
