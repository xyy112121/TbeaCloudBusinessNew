package com.example.programmer.tbeacloudbusiness.activity.publicUse.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 搜索历史记录
 */
public class HistorySearchActivity extends BaseActivity {
    private EditText searchTV;
    private ListView mListView;
    private MyAdapter mAdapter;
    private String mType;
    private String mHistoryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_search);
        searchTV = (EditText) findViewById(R.id.expert_search_text);
        mListView = (ListView) findViewById(R.id.search_history_list);
        mType = getIntent().getStringExtra("type");
        mHistoryKey = "yunshan" + mType;
        mAdapter = new MyAdapter();
        // 获取搜索记录文件内容
        SharedPreferences sp = getSharedPreferences("yunshan_search", 0);
        String history = sp.getString(mHistoryKey, "");
        if (!"".equals(history)) {
            // 用逗号分割内容返回数组
            String[] history_arr = history.split(",");
            List<String> list = new ArrayList<>();
            if (history_arr.length >= 1) {
                // 保留前50条数据
                if (history_arr.length > 50) {
                    String[] newArrays = new String[50];
                    // 实现数组之间的复制
                    System.arraycopy(history_arr, 0, newArrays, 0, 50);
                    Collections.addAll(list, newArrays);
                } else {
                    Collections.addAll(list, history_arr);
                }
            } else {
                findViewById(R.id.search_history_del).setVisibility(View.GONE);
                findViewById(R.id.search_history_view).setVisibility(View.GONE);
                findViewById(R.id.search_history_text).setVisibility(View.GONE);

            }
            // 设置适配器
            mListView.setAdapter(mAdapter);
            if (list != null && list.size() > 0) {
                mAdapter.addAll(list);
            }
        } else {
            findViewById(R.id.search_history_del).setVisibility(View.GONE);
            findViewById(R.id.search_history_view).setVisibility(View.GONE);
            findViewById(R.id.search_history_text).setVisibility(View.GONE);
        }
        listener();
    }

    public void listener() {

        findViewById(R.id.search_history_back).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        findViewById(R.id.search_history_cancel).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String text = searchTV.getText().toString().trim();
                        if (!"".equals(text) && text.length() >= 2) {
                            save(text);
                            search(text);
                        } else {
                            showMessage("搜索内容至少需要两个字符！");
                        }
                    }
                });

        searchTV.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String text = searchTV.getText().toString().trim();
                    if (!"".equals(text) && text.length() >= 2) {
                        save(text);
                        search(text);
                    } else {
                        showMessage("搜索内容至少需要两个字符！");
                    }
                }
                return false;
            }
        });

        findViewById(R.id.search_history_del).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                findViewById(R.id.search_history_view).setVisibility(View.GONE);
                SharedPreferences mysp = getSharedPreferences("yunshan_search", 0);
                SharedPreferences.Editor myeditor = mysp.edit();
                myeditor.putString(mHistoryKey, "").commit();
                mAdapter.removeAll();
            }
        });

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                save(mAdapter.list.get(position));
                search(mAdapter.list.get(position));
            }
        });
    }

    public void search(String keyword) {
        Intent intent = new Intent(HistorySearchActivity.this, HistorySearchListActivity.class);
        intent.putExtra("keyword", keyword);
        intent.putExtra("type", mType);
        startActivity(intent);
    }

    public class MyAdapter extends BaseAdapter {
        List<String> list = new ArrayList<>();

        @Override
        public int getCount() {
            return list.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.activity_history_item, null);
            ((TextView) view.findViewById(R.id.expert_search_item_text)).setText(list.get(position));
            view.findViewById(R.id.expert_search_item_del).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferences mysp = getSharedPreferences("yunshan_search", 0);
                    String old_text = mysp.getString(mHistoryKey, "");
                    // 用逗号分割内容返回数组
                    String[] history_arr = old_text.split(",");
                    List<String> hisList = new ArrayList<>();
                    Collections.addAll(hisList, history_arr);
                    for (int i = 0; i < hisList.size(); i++) {
                        if (list.get(position).equals(hisList.get(i))) {
                            hisList.remove(i);
                        }
                    }

                    StringBuilder builder = new StringBuilder();
                    for (String string : hisList) {
                        builder.append(string + ",");
                    }

                    // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
                    SharedPreferences.Editor myeditor = mysp.edit();
                    myeditor.putString(mHistoryKey, builder.toString()).commit();
                    list.remove(position);
                    notifyDataSetChanged();
//			        } 
                }
            });
            return view;
        }

        public void addAll(List<String> list) {
            this.list.addAll(list);
        }

        public void add(String text) {
            list.add(text);
            notifyDataSetChanged();
        }

        public void removeAll() {
            list.clear();
            notifyDataSetChanged();
        }
    }


    public void save(String text) {
        if (!"".equals(text)) {
            findViewById(R.id.search_history_del).setVisibility(View.VISIBLE);
            findViewById(R.id.search_history_text).setVisibility(View.VISIBLE);
            // 获取搜索框信息
            SharedPreferences mysp = getSharedPreferences("yunshan_search", 0);
            String old_text = mysp.getString(mHistoryKey, "");

            // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
            StringBuilder builder = new StringBuilder(old_text);
            builder.append(text + ",");

            // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
            if (!old_text.contains(text + ",")) {
                SharedPreferences.Editor myeditor = mysp.edit();
                myeditor.putString(mHistoryKey, builder.toString());
                myeditor.commit();
                mAdapter.add(text);
            }
        }
    }
}
