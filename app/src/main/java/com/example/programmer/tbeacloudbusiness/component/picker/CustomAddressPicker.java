package com.example.programmer.tbeacloudbusiness.component.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.programmer.tbeacloudbusiness.R;

import java.util.ArrayList;

import cn.qqtheme.framework.picker.AddressPicker;

/**
 * Created by programmer on 2017/6/10.
 */

public class CustomAddressPicker extends AddressPicker {
    private boolean hideProvince = false;
    private boolean hideCounty = false;
    public View mHeadView;



    @Nullable
    @Override
    protected View makeHeaderView() {
        mHeadView = super.activity.getLayoutInflater().inflate(R.layout.picker_header,null);
//        ((TextView)mHeadView.findViewById(R.id.picker_header_tv)).setText("dddkkdkd");
        mHeadView.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return mHeadView;//顶部视图
    }

    @Override
    @NonNull
    protected View makeCenterView() {
        if (hideCounty) {
            hideProvince = false;
        }
        if (firstList.size() == 0) {
            throw new IllegalArgumentException("please initial data at first, can't be empty");
        }
        LinearLayout layout = new LinearLayout(activity);
        layout.setPadding(100,0,100,0);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        final CustomWheelView provinceView = new CustomWheelView(activity);
        final int width = (screenWidthPixels-200) / 3;
        provinceView.setLayoutParams(new LinearLayout.LayoutParams(width, WRAP_CONTENT));
        provinceView.setTextSize(textSize);
        provinceView.setTextColor(textColorNormal, textColorFocus);
        provinceView.setLineVisible(lineVisible);
        provinceView.setLineColor(lineColor);
        provinceView.setOffset(offset);
        layout.addView(provinceView);
        if (hideProvince) {
            provinceView.setVisibility(View.GONE);
        }
        final CustomWheelView cityView = new CustomWheelView(activity);
        cityView.setLayoutParams(new LinearLayout.LayoutParams(width, WRAP_CONTENT));
        cityView.setTextSize(textSize);
        cityView.setTextColor(textColorNormal, textColorFocus);
        cityView.setLineVisible(lineVisible);
        cityView.setLineColor(lineColor);
        cityView.setOffset(offset);
        layout.addView(cityView);
        final CustomWheelView countyView = new CustomWheelView(activity);
        countyView.setLayoutParams(new LinearLayout.LayoutParams(width, WRAP_CONTENT));
        countyView.setTextSize(textSize);
        countyView.setTextColor(textColorNormal, textColorFocus);
        countyView.setLineVisible(lineVisible);
        countyView.setLineColor(lineColor);
        countyView.setOffset(offset);
        layout.addView(countyView);
        if (hideCounty) {
            countyView.setVisibility(View.GONE);
        }
        provinceView.setItems(firstList, selectedFirstIndex);
        provinceView.setOnWheelViewListener(new CustomWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedFirstText = item;
                selectedFirstIndex = selectedIndex;
                selectedThirdIndex = 0;
                //根据省份获取地市
                cityView.setItems(secondList.get(selectedFirstIndex), isUserScroll ? 0 : selectedSecondIndex);
                //根据地市获取区县
                countyView.setItems(thirdList.get(selectedFirstIndex).get(0), isUserScroll ? 0 : selectedThirdIndex);
            }
        });
        cityView.setItems(secondList.get(selectedFirstIndex), selectedSecondIndex);
        cityView.setOnWheelViewListener(new CustomWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedSecondText = item;
                selectedSecondIndex = selectedIndex;
                //根据地市获取区县
                countyView.setItems(thirdList.get(selectedFirstIndex).get(selectedSecondIndex), isUserScroll ? 0 : selectedThirdIndex);
            }
        });
        countyView.setItems(thirdList.get(selectedFirstIndex).get(selectedSecondIndex), selectedThirdIndex);
        countyView.setOnWheelViewListener(new CustomWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedThirdText = item;
                selectedThirdIndex = selectedIndex;
            }
        });
        return layout;
    }

    public void setGravity(int gravity){
        getRootView().getChildAt(0).setBackground(ContextCompat.getDrawable(super.activity,R.drawable.picket_information_background));
        getWindow().setGravity(gravity);
    }

    /**
     * 隐藏省级行政区，只显示地市级和区县级。
     * 设置为true的话，地址数据中只需要某个省份的即可
     * 参见示例中的“city2.json”
     *
     * @param hideProvince the hide province
     */
    public void setHideProvince(boolean hideProvince) {
        this.hideProvince = hideProvince;
    }

    /**
     * 隐藏县级行政区，只显示省级和市级。
     * 设置为true的话，hideProvince将强制为false
     * 数据源依然使用“city.json” 仅在逻辑上隐藏县级选择框。
     *
     * @param hideCounty the hide county
     */
    public void setHideCounty(boolean hideCounty) {
        this.hideCounty = hideCounty;
    }

    @Nullable
    @Override
    protected View makeFooterView() {
        View view = super.activity.getLayoutInflater().inflate(R.layout.picker_footer,null);
        view.findViewById(R.id.picker_footer_comfrim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSubmit();
            }
        });
        return view;//底部视图
    }

    public CustomAddressPicker(Activity activity, ArrayList<Province> data) {
        super(activity, data);
    }

    @Override
    public void onSubmit() {
        super.onSubmit();//点击确定
    }

    @Override
    protected void onCancel() {
        super.onCancel();//点击取消
    }

}
