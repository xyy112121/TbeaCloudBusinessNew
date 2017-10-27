package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set.RotateADListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set.SetVisualGraphActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set.SpecificationsAndModelsListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set.SpecificationsAndModelsSelectListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set.StoreCodeActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set.StoreIntroduceActivity;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.NetWebViewActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 店铺设置
 */

public class StoreSetActtivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_set);
        ButterKnife.bind(this);
        initTopbar("店铺设置");
    }

    @OnClick({R.id.store_set_visual_graph, R.id.store_set_rotate_ad, R.id.store_set_store_code, R.id.store_set_store_introduce, R.id.store_set_store_specifications_and_models})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.store_set_visual_graph:
                Intent intent = new Intent(mContext, SetVisualGraphActivity.class);
                startActivity(intent);
                break;
            case R.id.store_set_rotate_ad:
                intent = new Intent(mContext, RotateADListActivity.class);
                startActivity(intent);
                break;
            case R.id.store_set_store_code:
//                intent = new Intent(mContext, StoreCodeActivity.class);
//                startActivity(intent);
                intent = new Intent(mContext, NetWebViewActivity.class);
                intent.putExtra("title", "店铺二维码");
                String par = "companyqrcode?companyid=" + MyApplication.instance.getUserId();
                intent.putExtra("parameter", par);//URL后缀
                startActivity(intent);
                break;
            case R.id.store_set_store_introduce:
                intent = new Intent(mContext, StoreIntroduceActivity.class);
                startActivity(intent);
                break;
            case R.id.store_set_store_specifications_and_models:
                intent = new Intent(mContext, SpecificationsAndModelsSelectListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
