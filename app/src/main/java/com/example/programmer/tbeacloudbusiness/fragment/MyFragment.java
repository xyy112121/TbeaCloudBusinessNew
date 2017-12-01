package com.example.programmer.tbeacloudbusiness.fragment;

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
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity.PlumberMeetingListActivity;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity.FxMainActivity;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity.MyRebateAccountlistActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.order.OrderListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.BypassAccountManageListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.MessageTypeListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.MyAttentionActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.MyFansActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.PersonInfoActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.RealNameAuthenticationDistributorActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.ServiceCenterActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.SetActivity;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.NetWebViewActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.MyMainResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by programmer on 2017/6/22.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    Unbinder unbinder;
    private View mView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my, null);
        listener();
        unbinder = ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), false));
    }

    @Override
    public void onResume() {
        super.onResume();
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    /**
     * 从服务器获取数据
     */
    private void getData() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endLoadingMore();
                    mRefreshLayout.endRefreshing();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ResponseInfo re = (ResponseInfo) msg.obj;
                            if (re.isSuccess()) {
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                String json = gson.toJson(re.data);
                                MyMainResponseModel model = gson.fromJson(json, MyMainResponseModel.class);
                                initItemView(model.itemlist);
                                initPersonInfo(model.userpersoninfo);

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
                        ResponseInfo model = userAction.getMyMainData();
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
        parentLayout.removeAllViews();
        for (int i = 0; i < modelList.size(); i++) {
            List<MyMainResponseModel.Item> list = modelList.get(i);
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

                        if ("platformhelp".equals(item.id)) {
//                            Intent intent = new Intent(getActivity(), NetWebViewActivity.class);
//                            intent.putExtra("title", "帮助中心");
//                            String par = "userhelpdetail?questionid=" + MyApplication.instance.getUserId();
//                            intent.putExtra("parameter", par);//URL后缀
//                            startActivity(intent);
                            startActivity(new Intent(getActivity(), ServiceCenterActivity.class));

                        }
                        if ("aboutplatform".equals(item.id)) {
                            Intent intent = new Intent(getActivity(), NetWebViewActivity.class);
                            intent.putExtra("title", "关于我们");
                            intent.putExtra("parameter", "aboutplatform");//URL后缀
                            startActivity(intent);
                        }

                        if ("mymessage".equals(item.id)) {
                            startActivity(new Intent(getActivity(), MessageTypeListActivity.class));
                        }
                        if ("mymoneyaccount".equals(item.id)) {//我的返利账户
                            startActivity(new Intent(getActivity(), MyRebateAccountlistActivity.class));
                        }
                        if ("mysubaccount".equals(item.id)) {//我的子账号
                            startActivity(new Intent(getActivity(), BypassAccountManageListActivity.class));
                        }

                        if ("myfocus".equals(item.id)) {//我的关注
                            startActivity(new Intent(getActivity(), MyAttentionActivity.class));
                        }

                        if ("myfans".equals(item.id)) {//我的粉丝
                            startActivity(new Intent(getActivity(), MyFansActivity.class));
                        }

                        if ("myorder".equals(item.id)) {
                            startActivity(new Intent(getActivity(), OrderListActivity.class));
                        }

                        if ("companyidentify".equals(item.id)) {//实名认证
                            startActivity(new Intent(getActivity(), RealNameAuthenticationDistributorActivity.class));
//                            String identify = ShareConfig.getConfigString(getActivity(), Constants.whetheridentifiedid, "");
//                            if ("identified".equals(identify)) {
////                                startActivity(new Intent(getActivity(), RealNameAuthenticationPlumberActivity.class));
//                                startActivity(new Intent(getActivity(), RealNameAuthenticationDistributorActivity.class));
//                            } else {
//                                startActivity(new Intent(getActivity(), CompletionDataActivity.class));
//                            }
                        }
                        if ("marketer_shuidiangonghuiyi".equals(item.id) || "shuidiangonghuiyi".equals(item.id)) {

                            //水电工会议
                            startActivity(new Intent(getActivity(), PlumberMeetingListActivity.class));
                        }

                        if ("tebianfenxiao".equals(item.id) || "fxsubsystem".equals(item.id)) {
                            //分销系统
                            Intent intent = new Intent(getActivity(), FxMainActivity.class);
                            startActivity(intent);
                        }

                    }
                });
            }
            if (i < modelList.size() - 1) {
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
        mView.findViewById(R.id.my_person_info).setOnClickListener(this);
        mView.findViewById(R.id.fragment_mian_account_layout).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_set:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.my_person_info:
            case R.id.fragment_mian_account_layout:
                startActivity(new Intent(getActivity(), PersonInfoActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
