package com.example.programmer.tbeacloudbusiness.activity.my.set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *收货地址列表
 */

public class AddressEditListActivity extends TopActivity implements View.OnClickListener{
    private ListView mListView;
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initTopbar("收货地址管理","新增",this);
        mListView = (ListView)findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
//        getListDate();
        listener();
    }

    private void listener(){

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
//                final CustomDialog dialog = new CustomDialog(mContext,R.style.MyDialog,R.layout.tip_delete_dialog);
//                dialog.show();
//                dialog.setConfirmBtnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//
//                    }
//                },"否");
//                dialog.setCancelBtnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                        String id = ((Address)mAdapter.getItem(i)).getId();
//                        delect(id);
//
//                    }
//                },"是");
                return false;
            }
        });
    }

    private void delect(final String id){
//        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
//        dialog.setText("请等待...");
//        dialog.show();
//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                dialog.dismiss();
//                switch (msg.what) {
//                    case ThreadState.SUCCESS:
//                        RspInfo1 re = (RspInfo1) msg.obj;
//                        if (re.isSuccess()) {
//                           mAdapter.removeAll();
//                            getListDate();
//                        } else {
//                            UtilAssistants.showToast(re.getMsg());
//                        }
//
//                        break;
//                    case ThreadState.ERROR:
//                        UtilAssistants.showToast("操作失败！");
//                        break;
//                }
//            }
//        };
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    UserAction userAction = new UserAction();
//                    RspInfo1 re = userAction.delectAddr(id);
//                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
//                } catch (Exception e) {
//                    handler.sendEmptyMessage(ThreadState.ERROR);
//                }
//            }
//        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 100){
            mAdapter.removeAll();
            getListDate();
        }
    }

    /**
     * 获取数据
     */
    public void getListDate() {
//        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
//        dialog.setText("加载中...");
//        dialog.show();
//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                dialog.dismiss();
//                switch (msg.what) {
//                    case ThreadState.SUCCESS:
//                        RspInfo re = (RspInfo) msg.obj;
//                        if (re.isSuccess()) {
//                            List<Address> list = (List<Address>) re.getDateObj("addresslist");
//                            if (list != null) {
//                                mAdapter.addAll(list);
//                            }
//
//                        } else {
//                            UtilAssistants.showToast(re.getMsg());
//                        }
//
//                        break;
//                    case ThreadState.ERROR:
//                        UtilAssistants.showToast("操作失败！");
//                        break;
//                }
//            }
//        };
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    UserAction userAction = new UserAction();
//                    RspInfo re = userAction.getAddrList();
//                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
//                } catch (Exception e) {
//                    handler.sendEmptyMessage(ThreadState.ERROR);
//                }
//            }
//        }).start();
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends BaseAdapter {
        private Context mContext;
        private List<Object> mList = new ArrayList<>();

        public MyAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View v, ViewGroup viewGroup) {
            View view =  getLayoutInflater().inflate(R.layout.addr_edit_list_item, null);

            return view;
        }

        public void addAll(List<Object> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
