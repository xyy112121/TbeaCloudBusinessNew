package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeNormsSelectReponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.Condition;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 规格选择
 */

public class ScanCodeNormsSelectActivity extends BaseActivity {
    private ListView mListView;
    private MyAdapter mAdapter;
    Condition mNorms;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_create_code_select_type_list);
        initTopbar("规格选择");
        mNorms = (Condition) getIntent().getSerializableExtra("norms");
        mListView = (ListView)findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        getData();
    }

    /**
     * 从服务器获取数据
     */
    private void getData() {
        try {
            final CustomDialog dialog = new CustomDialog(mContext,R.style.MyDialog,R.layout.tip_wait_dialog);
            dialog.setText("加载中...");
            dialog.show();
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ScanCodeNormsSelectReponseModel model = (ScanCodeNormsSelectReponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data.commodityspecificationlist != null && model.data.commodityspecificationlist.size() > 0) {
                                    mAdapter.addAll(model.data.commodityspecificationlist);
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
                        ScanCodeAction action = new ScanCodeAction();
                        String commodityId = getIntent().getStringExtra("commodityId");
                        ScanCodeNormsSelectReponseModel model = action.getScanCodeNormsSelect(commodityId);
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

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<Condition> mList = new ArrayList<>();

        /**
         * 构造函数
         *
         * @param context android上下文环境
         */
        public MyAdapter(Context context) {
            this.context = context;
        }

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
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            FrameLayout view = (FrameLayout) layoutInflater.inflate(
                    R.layout.activty_create_code_select_type_list_item, null);
            ((TextView)view.findViewById(R.id.label)).setText(mList.get(position).getName());
            CheckBox checkBox = (CheckBox)view.findViewById(R.id.item_ck);
            checkBox.setVisibility(View.VISIBLE);
            if(mNorms != null && mList.get(position).getId().equals(mNorms.getId())){
                checkBox.setChecked(true);
            }else {
                checkBox.setVisibility(View.GONE);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("obj",mList.get(position));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<Condition> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
