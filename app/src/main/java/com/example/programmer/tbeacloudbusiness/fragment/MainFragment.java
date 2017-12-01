package com.example.programmer.tbeacloudbusiness.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MainActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberManage.activity.DranchiseeSeleteActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity.PlumberMeetingListActivity;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity.FxMainActivity;
import com.example.programmer.tbeacloudbusiness.activity.distributor.scanCode.activity.DbScanCodeMainListActivity;
import com.example.programmer.tbeacloudbusiness.activity.distributorManage.activity.DistributorListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity.PlumberManageMainListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.PlumberMeetingMainListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.activity.ScanCodeAcctivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.ScanCodeMainListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.StoreManageMainActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.order.OrderListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.activity.MyTaskListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.MessageTypeListActivity;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.HistorySearchActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.CompletionDataActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.LoginActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.HomeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.MyGridView;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 首页
 */

public class MainFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ListView mListView;
    private MyAdapter mAdapter;
    private LayoutInflater mInflater;
    private BGARefreshLayout mRefreshLayout;
    private View mView;
    private View headView;
    private GridAdapter mGridAdapter;
    private MyGridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, null);
        mInflater = inflater;
        initView(mView);
        mRefreshLayout.beginRefreshing();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        String type = ShareConfig.getConfigString(getActivity(), Constants.USERTYPE, "");
        String identify = ShareConfig.getConfigString(getActivity(), Constants.whetheridentifiedid, "");
        if (("distributor".equals(type) || "seller".equals(type)) && ("notidentify".equals(identify) || "identifying".equals(identify))) {
            //需要认证
            showAlert();
        }
    }


    /**
     * 从服务器获取数据
     */
    private void getData() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ResponseInfo re = (ResponseInfo) msg.obj;
                            if (re.isSuccess()) {
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                String json = gson.toJson(re.data);
                                HomeMainResponseModel model = gson.fromJson(json, HomeMainResponseModel.class);
                                initFunctionModel(model.functionmodulelist);
                                initStaticsItem(model.staticsitemlist);
                                initMessage(model.systemmessagelist);
                            } else {
                                showMessage(re.getMsg(),getActivity());
                            }
                            break;
                        case ThreadState.ERROR:
                            showMessage("操作失败！",getActivity());
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        UserAction userAction = new UserAction();
                        ResponseInfo model = userAction.getMainData();
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

    //顶上的功能
    private void initFunctionModel(List<HomeMainResponseModel.FunctionModel> modelList) {
        if (modelList != null) {
            mGridAdapter.addAll(modelList);
        }
    }

    //中间的message
    private void initMessage(List<HomeMainResponseModel.SystemMessage> modelList) {
        if (modelList != null) {
            LinearLayout view = (LinearLayout) headView.findViewById(R.id.home_top_message_layout);
            view.removeAllViews();

            for (HomeMainResponseModel.SystemMessage item : modelList) {
                View itemView = getActivity().getLayoutInflater().inflate(R.layout.home_top_message_layout_item, null);
                ImageView imageView = (ImageView) itemView.findViewById(R.id.top_imgae);
                TextView nameView = (TextView) itemView.findViewById(R.id.top_text1);
                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + item.icon, imageView);
                nameView.setText(item.title);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), MessageTypeListActivity.class));
                    }
                });
                view.addView(itemView);
            }
        }

    }

    //下面的横条cell功能
    private void initStaticsItem(List<HomeMainResponseModel.StaticsItem> modelList) {
        if (modelList != null) {
            mAdapter.addAll(modelList);
        }
    }

    /**
     * 显示警示框
     */
    private void showAlert() {
        View parentLayout = mView.findViewById(R.id.parentLayout);
        final CustomPopWindow1 popWindow1 = new CustomPopWindow1(getActivity());
        popWindow1.init(parentLayout, R.layout.pop_window_header,
                R.layout.pop_window_btn_layout, "功能受限", getResources().getString(R.string.alert_text), "前往认证");
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
                Intent intent = new Intent(getActivity(), CompletionDataActivity.class);
                startActivity(intent);

            }
        });
        popWindow1.setItemClickClose(new CustomPopWindow1.ItemClickClose() {
            @Override
            public void close() {
                ShareConfig.setConfig(getActivity(), Constants.ONLINE, false);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }


    private void initView(View view) {
        mRefreshLayout = getViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), false));

        mListView = (ListView) view.findViewById(R.id.home_list);
        headView = mInflater.inflate(R.layout.fragment_home_top, null);


        gridView = (MyGridView) headView.findViewById(R.id.gridView);
        mGridAdapter = new GridAdapter();
        gridView.setAdapter(mGridAdapter);

        mAdapter = new MyAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.addHeaderView(headView);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
            }
        });

        mView.findViewById(R.id.open_my_sacncode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanCodeAcctivity.class);
                startActivity(intent);
            }
        });

        mView.findViewById(R.id.mian_home_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //首页搜索
                Intent intent = new Intent(getActivity(), HistorySearchActivity.class);
                intent.putExtra("type", "all");
                startActivity(intent);

            }
        });

        mView.findViewById(R.id.mian_home_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageTypeListActivity.class));
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mView.findViewById(id);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        return true;
    }

    private class GridAdapter extends BaseAdapter {
        private List<HomeMainResponseModel.FunctionModel> mList = new ArrayList<>();


        public void addAll(List<HomeMainResponseModel.FunctionModel> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View view1 = getActivity().getLayoutInflater().inflate(R.layout.fragment_home_top_item, null);
            ImageView imageView = (ImageView) view1.findViewById(R.id.home_top_item_image);
            TextView textView = (TextView) view1.findViewById(R.id.home_top_item_name);
            if (mList.get(i).newmessagenumber > 0) {
                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + mList.get(i).iconwithnewdata, imageView);
            } else {
                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + mList.get(i).moduleicon, imageView);
            }

            textView.setText(mList.get(i).modulename);
            LinearLayout layout = (LinearLayout) view1.findViewById(R.id.home_top_tb_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeMainResponseModel.FunctionModel obj = mList.get(i);
                    openActivity(obj.moduleid);

                }
            });
            return view1;
        }
    }

    private void openActivity(String moduleid) {
        if ("distributor_shaomafanli".equals(moduleid)) {//分销商
            //扫码返利
            startActivity(new Intent(getActivity(), DbScanCodeMainListActivity.class));
        } else if("electricalcheckor_tebianweishi".equals(moduleid)){
            startActivity(new Intent(getActivity(), com.example.programmer.tbeacloudbusiness.activity.check.tbws.activity.MyTaskListActivity.class));
        }else
            if ("tebianweishi".equals(moduleid)) {
            startActivity(new Intent(getActivity(), MyTaskListActivity.class));
        } else if ("shaomafanli".equals(moduleid)) {
            //扫码返利
            startActivity(new Intent(getActivity(), ScanCodeMainListActivity.class));
        } else if ("tbea".equals(moduleid)) {
            //特变电工
            ((MainActivity) getActivity()).showTbMaianFragment();
        } else if ("qitayingyong".equals(moduleid)) {
            //特变电工
            ((MainActivity) getActivity()).showOtherFragment();
        } else if ("shuidiangongguanli".equals(moduleid)) {//总经销商
            //水电工管理
            Intent intent = new Intent(getActivity(), PlumberManageMainListActivity.class);
            intent.putExtra("type", "");
            startActivity(intent);
        } else if ("tebianfenxiao".equals(moduleid)) {
            //分销系统
            Intent intent = new Intent(getActivity(), FxMainActivity.class);
            startActivity(intent);
        } else if ("shuidiangonghuiyi".equals(moduleid)) {
            //水电工会议
            startActivity(new Intent(getActivity(), PlumberMeetingMainListActivity.class));
        } else if ("shangchengxitong".equals(moduleid)) {
            //商城管理
            startActivity(new Intent(getActivity(), StoreManageMainActivity.class));
        } else if ("distributor_shuidiangongguanli".equals(moduleid)) {//分销商
            //水电工管理
            Intent intent = new Intent(getActivity(), PlumberManageMainListActivity.class);
            intent.putExtra("type", "distributor");
            startActivity(intent);
        } else if ("marketer_shuidiangonghuiyi".equals(moduleid)) {

            //水电工会议
            startActivity(new Intent(getActivity(), PlumberMeetingListActivity.class));
        } else if ("marketer_shuidiangongguanli".equals(moduleid)) {//外勤人员
            //水电工管理
            startActivity(new Intent(getActivity(), DranchiseeSeleteActivity.class));
        } else if ("fenxiaoshang".equals(moduleid)) {
            startActivity(new Intent(getActivity(), DistributorListActivity.class));
        } else if ("dingdanguanli".equals(moduleid)) {//订单管理
            Intent intent = new Intent(getActivity(), OrderListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 附近商家
     */
    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        private List<HomeMainResponseModel.StaticsItem> mList = new ArrayList<>();

        public void addAll(List<HomeMainResponseModel.StaticsItem> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

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
        public View getView(int position, View convertView, ViewGroup parent) {
            final HomeMainResponseModel.StaticsItem obj = mList.get(position);
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.activity_scan_code_main_list_top, null);
            ImageView iconView = (ImageView) view.findViewById(R.id.top_imgae);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.icon, iconView);
            TextView nameView = (TextView) view.findViewById(R.id.top_text1);
            TextView nameView2 = (TextView) view.findViewById(R.id.top_text2);
            TextView valueView2 = (TextView) view.findViewById(R.id.top_text3);
            TextView nameView3 = (TextView) view.findViewById(R.id.top_text4);
            TextView valueView3 = (TextView) view.findViewById(R.id.top_text5);
            TextView labelView1 = (TextView) view.findViewById(R.id.top_text4_label);
            TextView labelView2 = (TextView) view.findViewById(R.id.top_text5_label);
            nameView.setText(obj.name);
            nameView2.setText(obj.subitemlist.get(0).name);
            valueView2.setText(obj.subitemlist.get(1).name);
            nameView3.setText(obj.subitemlist.get(0).value);
            valueView3.setText(obj.subitemlist.get(1).value);

            if ("style01".equals(obj.style)) {
                if ("1".equals(obj.subitemlist.get(0).ismoney)) {
                    labelView1.setVisibility(View.VISIBLE);
                }
                if ("1".equals(obj.subitemlist.get(1).ismoney)) {
                    labelView2.setVisibility(View.VISIBLE);
                }
            } else if ("style02".equals(obj.style)) {
                labelView1.setVisibility(View.VISIBLE);
                String value = "";
                String[] values = obj.subitemlist.get(1).value.split(",");
                for (String item : values) {
                    value = value + item;
                }
                SpannableString spannableString = new SpannableString(value);
                int index1 = values[0].length();
                int index2 = values[1].length();
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#00AAEF")), 0, index1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FA8B27")), index1, index1 + index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                valueView3.setTextColor(Color.parseColor("#B1B1B1"));
                valueView3.setText(spannableString);

            } else if ("style03".equals(obj.style)) {
                if (obj.subitemlist != null && obj.subitemlist.size() > 0) {
                    String value1 = obj.subitemlist.get(0).value1 + "/" + obj.subitemlist.get(0).value2;
                    String value2 = obj.subitemlist.get(1).value1 + "/" + obj.subitemlist.get(1).value2;
                    SpannableString spannableString1 = new SpannableString(value1);
                    spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#00AAEF")), 0, obj.subitemlist.get(0).value1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    nameView3.setText(spannableString1);
                    SpannableString spannableString2 = new SpannableString(value2);
                    spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#00AAEF")), 0, obj.subitemlist.get(1).value1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    valueView3.setText(spannableString2);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openActivity(obj.moduleid);
//                    if ("shaomafanli".equals(obj.moduleid)) {
//                        //扫码返利
//                        startActivity(new Intent(getActivity(), ScanCodeMainListActivity.class));
//                    } else if ("tbea".equals(obj.moduleid)) {
//                        //特变电工
//                        ((MainActivity) getActivity()).showTbMaianFragment();
//                    } else if ("shuidiangongguanli".equals(obj.moduleid)) {
//                        //水电工管理
//                        Intent intent = new Intent(getActivity(), PlumberManageMainListActivity.class);
//                        intent.putExtra("type", "");
//                        startActivity(intent);
//                    } else if ("shuidiangonghuiyi".equals(obj.moduleid)) {
//                        //水电工会议
//                        startActivity(new Intent(getActivity(), PlumberMeetingMainListActivity.class));
//                    } else if ("shangchengxitong".equals(obj.moduleid)) {
//                        //商城管理
//                        startActivity(new Intent(getActivity(), StoreManageMainActivity.class));
//                    } else if ("distributor_shaomafanli".equals(obj.moduleid)) {//分销商
//                        //扫码返利
//                        startActivity(new Intent(getActivity(), DbScanCodeMainListActivity.class));
//                    } else if ("distributor_shuidiangongguanli".equals(obj.moduleid)) {//分销商
//                        //水电工管理
//                        Intent intent = new Intent(getActivity(), PlumberManageMainListActivity.class);
//                        intent.putExtra("type", "distributor");
//                        startActivity(intent);
//                    } else if ("marketer_shuidiangongguanli".equals(obj.moduleid)) {//公司人员
//                        //水电工管理
//                        startActivity(new Intent(getActivity(), DranchiseeSeleteActivity.class));
//                    } else if ("marketer_shuidiangonghuiyi".equals(obj.moduleid)) {//公司人员
//
//                        //水电工会议
//                        startActivity(new Intent(getActivity(), PlumberMeetingListActivity.class));
//                    }
                }
            });

            return view;
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }

}
