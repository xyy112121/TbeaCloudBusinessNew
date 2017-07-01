package com.example.programmer.tbeacloudbusiness.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.administrator.realNameAuthentication.MemberListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.BypassAccountManageListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.MyAttentionActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.MyFansActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.RealNameAuthenticationDistributorActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.SetActivity;

/**
 * Created by programmer on 2017/6/22.
 */

public class MyFragment extends Fragment implements View.OnClickListener{
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my,null);
        listener();
        return mView;
    }

    private void listener(){
        mView.findViewById(R.id.my_set).setOnClickListener(this);
        mView.findViewById(R.id.my_relaname_authentication).setOnClickListener(this);
        mView.findViewById(R.id.my_attention).setOnClickListener(this);
        mView.findViewById(R.id.my_fans).setOnClickListener(this);
        mView.findViewById(R.id.my_bypass_account_manage).setOnClickListener(this);
        mView.findViewById(R.id.fragment_mian_account_layout).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_set:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.my_relaname_authentication:
                startActivity(new Intent(getActivity(), RealNameAuthenticationDistributorActivity.class));
                break;
            case R.id.my_attention:
                startActivity(new Intent(getActivity(), MyAttentionActivity.class));
                break;
            case R.id.my_fans:
                startActivity(new Intent(getActivity(), MyFansActivity.class));
                break;
            case R.id.my_bypass_account_manage:
                startActivity(new Intent(getActivity(), BypassAccountManageListActivity.class));
                break;
            case R.id.fragment_mian_account_layout:
                startActivity(new Intent(getActivity(), MemberListActivity.class));
                break;



        }
    }
}
