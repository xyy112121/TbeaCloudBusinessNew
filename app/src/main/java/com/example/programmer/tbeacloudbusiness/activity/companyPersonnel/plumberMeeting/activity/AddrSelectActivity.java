package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.utils.AssetsUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * 地址选择
 */

public class AddrSelectActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.cp_addr_province)
    PublishTextRowView mProvinceView;
    @BindView(R.id.cp_addr_info)
    PublishTextRowView mAddrInfoView;

    private String mProvince = MyApplication.instance.getProvince();
    private String mCity = MyApplication.instance.getCity();
    private String mCounty = MyApplication.instance.getDistrict();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_addr_select);
        ButterKnife.bind(this);
        initTopbar("地址选择", "保存", this);
        String title = getIntent().getStringExtra("title");
        if(title != null || !"".equals(title)){
            mAddrInfoView.setValueHint("请输入"+title);
            mAddrInfoView.setLeftext(title);
        }
        if ("addrEdit".equals(getIntent().getStringExtra("flag"))) {
            mAddrInfoView.setVisibility(View.GONE);
        }
        if (getIntent().getStringExtra("province") != null) {
            mProvince = getIntent().getStringExtra("province");
            mCity = getIntent().getStringExtra("city");
            mCounty = getIntent().getStringExtra("county");
        }
        mProvinceView.setValueText(mProvince+mCity+mCounty);
        mAddrInfoView.setValueText(getIntent().getStringExtra("addr"));
    }

    @OnClick(R.id.cp_addr_province)
    public void onViewClicked() {
        ArrayList<AddressPicker.Province> data = new ArrayList<>();
        String json = AssetsUtils.readText(mContext, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(mContext, data);
        picker.setSelectedItem(mProvince, mCity, mCounty);
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {
                mProvince = province;
                mCity = city;
                mCounty = county;
                mProvinceView.setValueText(province + city + county);
            }
        });
        picker.show();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("province", mProvince);
        intent.putExtra("city", mCity);
        intent.putExtra("county", mCounty);
        intent.putExtra("addrInfo", mAddrInfoView.getValueText());
        intent.putExtra("addr", mProvince + mCity + mCounty + mAddrInfoView.getValueText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
