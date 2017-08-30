package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.RegionSeleteResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by programmer on 2017/8/3.
 */

public class RegionSelectActivity extends BaseActivity implements View.OnClickListener {
    private ListView mListView;
    private MyAdapter mAdapter;
    public List<String> mSeleteId = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select_list);
        initTopbar("区域选择", "确定", this);
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        getData();
    }


    private void getData() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            RegionSeleteResponseModel model = (RegionSeleteResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data.zonelist != null) {
                                    mAdapter.addAll(model.data.zonelist);
                                }

                            } else {
                                ToastUtil.showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            ToastUtil.showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        PlumberMeetingAction action = new PlumberMeetingAction();
                        RegionSeleteResponseModel model = action.getRegionSelete();
                        handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        String ids = "";
        if (mSeleteId.size() > 0) {
            for (String item : mSeleteId) {
                if (!"".equals(ids)) {
                    ids += ",";
                }
                ids += item;
            }
        }
        Intent in = new Intent();
        in.putExtra("ids", ids);
        setResult(RESULT_OK, in);
        finish();
    }

    public class MyAdapter extends BaseAdapter {

        public List<KeyValueBean> mList = new ArrayList<>();

        @Override
        public int getCount() {
            return mList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_region_select_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mSelectNameView.setText(mList.get(position).getValue());
            holder.mSelectNameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.mckView.isChecked() == false) {
                        holder.mckView.setChecked(true);
                        mSeleteId.add(mList.get(position).getKey());
                    } else {
                        holder.mckView.setChecked(false);
                        mSeleteId.remove(mList.get(position).getKey());
                    }
                }
            });

            holder.mckView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.mckView.isChecked() == true) {
                        mSeleteId.add(mList.get(position).getKey());
                    } else {
                        mSeleteId.remove(mList.get(position).getKey());
                    }
                }
            });
            return convertView;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<KeyValueBean> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.region_select_ck)
            CheckBox mckView;
            @BindView(R.id.region_select_name)
            TextView mSelectNameView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}