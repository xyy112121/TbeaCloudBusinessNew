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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.administrator.realNameAuthentication.MemberListActivity;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity.MyRebateAccountlistActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.BypassAccountManageListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.MyAttentionActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.MyFansActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.RealNameAuthenticationDistributorActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.SetActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.HomeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.MyMainResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by programmer on 2017/6/22.
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my, null);
        listener();
        getData();
        return mView;
    }

    /**
     * 从服务器获取数据
     */
    private void getData() {
        final CustomDialog mDialog = new CustomDialog(getActivity(), R.style.MyDialog, R.layout.tip_wait_dialog);
        mDialog.setText("加载中...");
        mDialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mDialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            MyMainResponseModel model = (MyMainResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                initItemView(model.data.itemlist);
                                initPersonInfo(model.data.userpersoninfo);
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
                        UserAction userAction = new UserAction();
                        MyMainResponseModel model = userAction.getMyMainData();
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

    private void initItemView(List<List<MyMainResponseModel.Item>> modelList) {
        LinearLayout parentLayout = (LinearLayout) mView.findViewById(R.id.fragment_my_item_layout);
        for (int i = 0;i<modelList.size();i++) {
            List<MyMainResponseModel.Item> list  = modelList.get(i);
            for (final MyMainResponseModel.Item item : list) {
                View layout = getActivity().getLayoutInflater().inflate(R.layout.fragment_my_item, null);
                TextView leftView = (TextView) layout.findViewById(R.id.my_item_left_text);
                TextView rightView = (TextView) layout.findViewById(R.id.my_item_right_text);
                ImageView leftImageView = (ImageView) layout.findViewById(R.id.my_item_imgae);
                leftView.setText(item.name);
                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + item.icon, leftImageView);
                rightView.setText(item.displayvalue);
                parentLayout.addView(layout);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if("mymoneyaccount".equals(item.id)){
                            startActivity(new Intent(getActivity(), MyRebateAccountlistActivity.class));

                        }
                    }
                });
            }
            if(i < modelList.size()-1){
                View layout = getActivity().getLayoutInflater().inflate(R.layout.fragment_my_view_item, null);
                parentLayout.addView(layout);
            }
        }

    }

    private void initPersonInfo(MyMainResponseModel.UserPersonInfo model) {
        ((TextView) mView.findViewById(R.id.user_name)).setText(model.username);
        ((TextView) mView.findViewById(R.id.user_usertype)).setText(model.usertype);
        ((TextView) mView.findViewById(R.id.user_usercompanyname)).setText(model.usercompanyname);
        ImageView headView = (ImageView) mView.findViewById(R.id.user_picture);
        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + model.thumbpicture, headView);

    }

    private void listener() {
        mView.findViewById(R.id.my_set).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_set:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
//            case R.id.my_relaname_authentication:
//                startActivity(new Intent(getActivity(), RealNameAuthenticationDistributorActivity.class));
//                break;
//            case R.id.my_attention:
//                startActivity(new Intent(getActivity(), MyAttentionActivity.class));
//                break;
//            case R.id.my_fans:
//                startActivity(new Intent(getActivity(), MyFansActivity.class));
//                break;
//            case R.id.my_bypass_account_manage:
//                startActivity(new Intent(getActivity(), BypassAccountManageListActivity.class));
//                break;
//            case R.id.fragment_mian_account_layout:
//                startActivity(new Intent(getActivity(), MemberListActivity.class));
//                break;


        }
    }
}
