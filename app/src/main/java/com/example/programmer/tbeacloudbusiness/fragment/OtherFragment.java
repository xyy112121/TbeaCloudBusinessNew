package com.example.programmer.tbeacloudbusiness.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.ScanCodeMainListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.StoreManageMainActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.HomeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.OtherResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.MyGridView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 全部应用
 */

public class OtherFragment extends BaseFragment {

    @BindView(R.id.top_left)
    ImageButton topLeft;
    @BindView(R.id.top_center)
    TextView topCenter;
    @BindView(R.id.gridView)
    MyGridView gridView;
    Unbinder unbinder;
    private GridAdapter mGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        getData();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getData();
    }

    private void initView() {
        mGridAdapter = new GridAdapter();
        gridView.setAdapter(mGridAdapter);

        topLeft.setVisibility(View.GONE);
        topCenter.setText("其他应用");
    }

    /**
     * 从服务器获取数据
     */
    private void getData() {
        final CustomDialog dialog = new CustomDialog(getActivity(), R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            OtherResponseModel model = (OtherResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                initFunctionModel(model.data.functionmodulelist);

                            } else {
                                showMessage(model.getMsg(),getActivity());
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
                        OtherResponseModel model = userAction.getOtherData();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + mList.get(i).moduleicon, imageView);
            textView.setText(mList.get(i).modulename);
            LinearLayout layout = (LinearLayout) view1.findViewById(R.id.home_top_tb_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeMainResponseModel.FunctionModel obj = mList.get(i);
                    if ("distributor_shaomafanli".equals(obj.moduleid)) {//分销商
                        //扫码返利
                        startActivity(new Intent(getActivity(), DbScanCodeMainListActivity.class));
                    }
                    if ("shaomafanli".equals(obj.moduleid)) {
                        //扫码返利
                        startActivity(new Intent(getActivity(), ScanCodeMainListActivity.class));
                    } else if ("tbea".equals(obj.moduleid)) {
                        //特变电工
                        ((MainActivity) getActivity()).showTbMaianFragment();
                    } else if ("qitayingyong".equals(obj.moduleid)) {
                        //特变电工
                        ((MainActivity) getActivity()).showOtherFragment();
                    } else if ("shuidiangongguanli".equals(obj.moduleid)) {//总经销商
                        //水电工管理
                        Intent intent = new Intent(getActivity(), PlumberManageMainListActivity.class);
                        intent.putExtra("type", "");
                        startActivity(intent);
                    } else if ("tebianfenxiao".equals(obj.moduleid)) {
                        //分销系统
                        Intent intent = new Intent(getActivity(), FxMainActivity.class);
                        startActivity(intent);
                    } else if ("shuidiangonghuiyi".equals(obj.moduleid)) {
                        //水电工会议
                        startActivity(new Intent(getActivity(), PlumberMeetingMainListActivity.class));
                    } else if ("shangchengxitong".equals(obj.moduleid)) {
                        //商城管理
                        startActivity(new Intent(getActivity(), StoreManageMainActivity.class));
                    } else if ("distributor_shuidiangongguanli".equals(obj.moduleid)) {//分销商
                        //水电工管理
                        Intent intent = new Intent(getActivity(), PlumberManageMainListActivity.class);
                        intent.putExtra("type", "distributor");
                        startActivity(intent);
                    } else if ("marketer_shuidiangonghuiyi".equals(obj.moduleid)) {

                        //水电工会议
                        startActivity(new Intent(getActivity(), PlumberMeetingListActivity.class));
                    } else if ("marketer_shuidiangongguanli".equals(obj.moduleid)) {
                        //水电工管理
                        startActivity(new Intent(getActivity(), DranchiseeSeleteActivity.class));
                    } else if ("fenxiaoshang".equals(obj.moduleid)) {
                        startActivity(new Intent(getActivity(), DistributorListActivity.class));
                    }
                }
            });
            return view1;
        }
    }
}
