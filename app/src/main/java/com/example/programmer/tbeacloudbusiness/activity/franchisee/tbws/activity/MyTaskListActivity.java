package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.TaskStateResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.Condition;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 16/12/24.订单列表
 */

public class MyTaskListActivity extends BaseActivity {
    private List<FrameLayout> mListLayout = new ArrayList<>();
    private int mIndex = 0;
    private int mIndex2 = -1;//前一次点击的下标
    private int screenWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_my_task_list);
        initTopbar("我发布的任务");
        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        getStateDate();
    }

    /**
     * 获取订单状态
     */
    public void getStateDate() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        TaskStateResponseModel re = (TaskStateResponseModel) msg.obj;
                        if (re.isSuccess()) {
                            final List<Condition> list = re.data.electricalcheckstatuslist;
                            if (list != null) {
                                LinearLayout parentLayout = (LinearLayout) findViewById(R.id.top_layout);
                                for (int i = 0; i < list.size(); i++) {
                                    final int index = i;
                                    final Condition item = list.get(i);
                                    final FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_top_layout_item, null);
                                    TextView t = (TextView) layout.findViewById(R.id.top_tv);
                                    t.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_normal));
                                    t.setText(item.getName());
                                    if (i == 0) {
                                        t.setTextColor(ContextCompat.getColor(mContext, R.color.head_color));
                                        swithFragment("全部");
                                    }
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / list.size(), LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layout.setLayoutParams(lp);
                                    layout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            mIndex2 = mIndex;
                                            mIndex = index;
                                            if (mIndex2 != -1 && mIndex != mIndex2) {
                                                ((TextView) view.findViewById(R.id.top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.head_color));
//                                                (view.findViewById(R.id.order_list_top_line)).setVisibility(View.VISIBLE);
                                                setViewColor();
                                            }
                                            swithFragment(item.getName());
                                        }
                                    });
                                    mListLayout.add(layout);
                                    parentLayout.addView(layout);
                                }

                            }
                        } else {
                            ToastUtil.showMessage("操作失败！");
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
                    SubscribeAction action = new SubscribeAction();
                    TaskStateResponseModel re = action.getState();
                    if (re == null) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    } else {
                        handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    private void setViewColor() {
        FrameLayout layout = mListLayout.get(mIndex2);
        ((TextView) layout.findViewById(R.id.top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_normal));
    }

    private void swithFragment(String status) {
        if ("全部".equals(status)) {
            MyTaskAllListFragment f = new MyTaskAllListFragment();
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.content_frame, f);
            t.commit();
        } else if ("待处理".equals(status)) {
            MyTaskListWaitingFragment f = new MyTaskListWaitingFragment();
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.content_frame, f);
            t.commit();
        } else if ("已派单".equals(status)) {
            MyTaskListHaveOderFragment f = new MyTaskListHaveOderFragment();
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.content_frame, f);
            t.commit();
        } else if ("已完工".equals(status)) {
            MyTaskListHaveEvaluationFragment f = new MyTaskListHaveEvaluationFragment();
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.content_frame, f);
            t.commit();
        } else if ("已上传".equals(status)) {
            MyTaskListHaveFinishedFragment f = new MyTaskListHaveFinishedFragment();
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.content_frame, f);
            t.commit();
        }

    }

}
