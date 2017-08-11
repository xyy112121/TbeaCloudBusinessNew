package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity;

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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.action.RebateAccountAction;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.RebateAccountListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的返利账户
 */

public class MyRebateAccountlistActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private Context mContext;
    private ListView mListView;
    private MyAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private int mCurrentMoney;
    private boolean isFirst = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_account_list);
        initTopbar("返利账户", "收支明细", this);
        mContext = this;
        initUI();
    }

    /**
     * 获取数据
     */
    public void getDate() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        RebateAccountListResponseModel model = (RebateAccountListResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            mCurrentMoney = model.data.mymoneyinfo.currentmoney;
                            if(isFirst == true){
                                LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_rebate_account_list_head, null);
                                mListView.addHeaderView(layout);
                                FrameLayout layout1 = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_rebate_account_list_item_head, null);
                                mListView.addHeaderView(layout1);
                                findViewById(R.id.my_rebate_account_withdraw_cash).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (mCurrentMoney == 0) {
                                            ToastUtil.showMessage("你当前可提现金额为0");
                                        } else {
                                            startActivity(new Intent(mContext,RebateAccountWithdrawCashActivity.class));
                                        }

                                    }
                                });
                            }
                            ((TextView) findViewById(R.id.my_rebate_account_list_currentmoney)).setText(mCurrentMoney + "");

                            mAdapter.addAll(model.data.nottakemoneylist);
                            isFirst = false;
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
                    RebateAccountAction action = new RebateAccountAction();
                    RebateAccountListResponseModel re = action.getRebateAccountList();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    /**
     * 实例化组件
     */
    private void initUI() {
        mListView = (ListView) findViewById(R.id.my_wallet_listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.removeAll();
        getDate();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getDate();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshLayout.beginRefreshing();
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        private List<RebateAccountListResponseModel.NottakeMoney> mList = new ArrayList<>();

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
            View view = layoutInflater.inflate(
                    R.layout.activity_rebate_account_list_item, null);
            ((TextView) view.findViewById(R.id.wallet_item_takemoneycode)).setText(mList.get(position).takemoneycode);
            ((TextView) view.findViewById(R.id.wallet_item_validexpiredtime)).setText(mList.get(position).validexpiredtime);
            ((TextView) view.findViewById(R.id.wallet_item_money)).setText(mList.get(position).money);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_delete_dialog);
                    dialog.setText("删除后提现二维码将会失效");
                    dialog.setText2("如需提现请重新生成二维码");
                    dialog.setConfirmBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    }, "取消");
                    dialog.setCancelBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
//                            delect(mList.get(position).getId());
                        }
                    }, "删除");
                    dialog.show();
                    return false;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(mContext,WalletWithdrawCashViewActivity.class);
//                    intent.putExtra("money","");
//                    intent.putExtra("takemoneycodeid",mList.get(position).getId());
//                    startActivity(intent);
                }
            });
            return view;
        }

        public void addAll(List<RebateAccountListResponseModel.NottakeMoney> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 删除数据
     */
    public void delect(final String id) {
//        final CustomDialog dialog = new CustomDialog(mContext,R.style.MyDialog,R.layout.tip_wait_dialog);
//        dialog.setText("请等待...");
//        dialog.show();
//        final Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                dialog.dismiss();
//                switch (msg.what){
//                    case ThreadState.SUCCESS:
//                        RspInfo1 re = (RspInfo1)msg.obj;
//                        if(re.isSuccess()){
//                            UtilAssistants.showToast(re.getMsg());
//                            mRefreshLayout.beginRefreshing();
//                        }else {
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
//                    RspInfo1 re = userAction.delectTakeMoneyCodeId(id);
//                    handler.obtainMessage(ThreadState.SUCCESS,re).sendToTarget();
//                } catch (Exception e) {
//                    handler.sendEmptyMessage(ThreadState.ERROR);
//                }
//            }
//        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
//        startActivity(new Intent(mContext,WalletIncomeAndExpensesActivity.class));

    }
}
