package com.example.programmer.tbeacloudbusiness.activity.my.set.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.AddrListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.AddressModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收货地址列表
 */

public class AddressEditListActivity extends BaseActivity implements View.OnClickListener {
    private ListView mListView;
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initTopbar("收货地址管理", "新增", this);
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        getListDate();
        listener();
    }

    private void listener() {

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

    private void delect(final String id) {
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
        if (resultCode == RESULT_OK && requestCode == 100) {
            mAdapter.removeAll();
            getListDate();
        }
    }

    /**
     * 获取数据
     */
    public void getListDate() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        AddrListResponseModel model = (AddrListResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if (model.data.recvaddrlist != null) {
                                mAdapter.addAll(model.data.recvaddrlist);
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
                    SetAction userAction = new SetAction();
                    AddrListResponseModel re = userAction.getAddrList();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(mContext,AddressEditActivity.class),100);
    }

    class MyAdapter extends BaseAdapter {
        private List<AddressModel> mList = new ArrayList<>();


        @Override
        public int getCount() {
            return mList.size();
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
            ViewHolder holder;
            if (v == null) {
                v = getLayoutInflater().inflate(R.layout.addr_edit_list_item, null);
                holder = new ViewHolder(v);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
            AddressModel obj = mList.get(i);
            holder.mNameView.setText(obj.contactperson);
            holder.mAddressView.setText(obj.address);
            holder.mMobileView.setText(obj.contactmobile);
            if ("1".equals(obj.isdefault)) {
                holder.mIsdefaultView.setVisibility(View.VISIBLE);

            } else {
                holder.mIsdefaultView.setVisibility(View.GONE);
            }
            return v;
        }

        public void addAll(List<AddressModel> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.addr_item_contactperson)
            TextView mNameView;
            @BindView(R.id.addr_item_contactmobile)
            TextView mMobileView;
            @BindView(R.id.addr_item_isdefault)
            TextView mIsdefaultView;
            @BindView(R.id.addr_item_address)
            TextView mAddressView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}