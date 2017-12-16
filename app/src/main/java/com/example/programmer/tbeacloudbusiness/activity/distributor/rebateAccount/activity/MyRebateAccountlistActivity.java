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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.WithdrawDepositDateActivity;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的返利账户
 */

public class MyRebateAccountlistActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private ListView mListView;
    private MyAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private String mCurrentMoney;
    private boolean isFirst = true;
    private int mPage = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_account_list);
        initTopbar("返利账户", "收支明细", this);
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
                            if (isFirst == true) {
                                LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_rebate_account_list_head, null);
                                mListView.addHeaderView(layout);
                                String userType = ShareConfig.getConfigString(mContext, Constants.USERTYPE,"");
                                if("firstleveldistributor".equals(userType)){//总经销商不能提现
                                    layout.findViewById(R.id.my_rebate_account_withdraw_cash).setVisibility(View.GONE);
                                    ((TextView) findViewById(R.id.my_rebate_account_list_currentmoney_title)).setText("总支出");
                                    mListView.setDivider(null);
                                    mListView.setDividerHeight(0);
                                }else {
                                    FrameLayout layout1 = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_rebate_account_list_item_head, null);
                                    mListView.addHeaderView(layout1);
                                    findViewById(R.id.my_rebate_account_withdraw_cash).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (mCurrentMoney.equals("0.00") || mCurrentMoney.equals("0.0") ||mCurrentMoney.equals("0")) {
                                                showMessage("你当前可提现金额为0");
                                            } else {
                                                startActivity(new Intent(mContext, RebateAccountWithdrawCashActivity.class));
                                            }

                                        }
                                    });
                                    mAdapter.addAll(model.data.nottakemoneylist);
                                }

                            }
                            ((TextView) findViewById(R.id.my_rebate_account_list_currentmoney)).setText(mCurrentMoney + "");

                            isFirst = false;
                        } else {
                            showMessage(model.getMsg());
                        }
                        break;
                    case ThreadState.ERROR:
                        showMessage("操作失败！");
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RebateAccountAction action = new RebateAccountAction();
                    RebateAccountListResponseModel re = action.getRebateAccountList(mPage++, 10);
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
        mPage = 1;
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
                            delect(mList.get(position).id);
                        }
                    }, "删除");
                    dialog.show();
                    return false;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WithdrawCashViewActivity.class);
                    intent.putExtra("takemoneycodeid", mList.get(position).id);
                    intent.putExtra("money", "");
                    startActivity(intent);
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
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess()) {
                            showMessage(re.getMsg());
                            mRefreshLayout.beginRefreshing();
                        } else {
                            showMessage(re.getMsg());
                        }

                        break;
                    case ThreadState.ERROR:
                        showMessage("操作失败！");
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RebateAccountAction userAction = new RebateAccountAction();
                    ResponseInfo re = userAction.delectTakeMoneyCodeId(id);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(mContext, WithdrawDepositDateActivity.class);
        startActivity(intent);

    }
}
