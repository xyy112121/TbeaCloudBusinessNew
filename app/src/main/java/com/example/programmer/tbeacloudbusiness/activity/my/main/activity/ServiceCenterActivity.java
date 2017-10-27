package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.MessageCategory;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.NetWebViewActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cy on 2017/2/8.
 */

public class ServiceCenterActivity extends BaseActivity {
    private ListView mListView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_center);
        initTopbar("客服中心");
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(ServiceCenterActivity.this);
        mListView.setAdapter(mAdapter);
        findViewById(R.id.service_center_call_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "0838-28022916"));
                //开启系统拨号器
                startActivity(intent);
            }
        });
        getListDate();
    }

    /**
     * 获取数据
     */
    public void getListDate() {
        final CustomDialog dialog = new CustomDialog(ServiceCenterActivity.this, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess()) {
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            String json = gson.toJson(re.data);
                            MessageCategory model = gson.fromJson(json, MessageCategory.class);
//                            List<MessageCategory> list = (List<MessageCategory>) re.getDateObj("questionlist");
//                            if (list != null) {
//                                mAdapter.addAll(list);
//                            }

                        } else {
                            ToastUtil.showMessage(re.getMsg());
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
                    UserAction userAction = new UserAction();
                    ResponseInfo re = userAction.getServiceCenterInfo();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    private class MyAdapter extends BaseAdapter {
        private Context mContext;
        private List<MessageCategory> mList = new ArrayList<>();

        public MyAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View v, ViewGroup viewGroup) {
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.activity_service_center_list_item, null);
            final MessageCategory obj = mList.get(i);
            ((TextView) view.findViewById(R.id.text)).setText(obj.getQuestion());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NetWebViewActivity.class);
                    intent.putExtra("title", "客服中心");
                    String par = "userhelpdetail?questionid=" + obj.getId();
                    intent.putExtra("parameter", par);//URL后缀
                    startActivity(intent);

                }
            });
            return view;
        }

        public void addAll(List<MessageCategory> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }
}
