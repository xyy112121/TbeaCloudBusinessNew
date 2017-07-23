package com.example.programmer.tbeacloudbusiness.component.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.model.Condition;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.WheelPicker;
import cn.qqtheme.framework.popup.BottomPopup;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by DELL on 2017/7/22.
 */

public class CustomOptionObjPicker extends WheelPicker{
    private String headTitle;
    /**
     * The Options.
     */
    protected List<Condition> options = new ArrayList<>();
    private OnOptionPickListener onOptionPickListener;
    private Condition selectedOption = null;
    private String label = "";

    @Nullable
    @Override
    protected View makeHeaderView() {
        View mHeadView = super.activity.getLayoutInflater().inflate(R.layout.picker_header,null);
        if(!"".equals(headTitle)){
            ((TextView)mHeadView.findViewById(R.id.picker_header_tv)).setText(headTitle);
        }
        mHeadView.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return mHeadView;//顶部视图
    }

    public void setGravity(int gravity){
        getRootView().getChildAt(0).setBackground(ContextCompat.getDrawable(super.activity,R.drawable.picket_information_background));
        getWindow().setGravity(gravity);
    }

    @Nullable
    @Override
    protected View makeFooterView() {
        View view = super.activity.getLayoutInflater().inflate(R.layout.picker_footer,null);
        view.findViewById(R.id.picker_footer_comfrim).setBackgroundResource(R.drawable.btn_bg_blue);
        view.findViewById(R.id.picker_footer_comfrim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSubmit();
            }
        });
        return view;//底部视图
    }

    public CustomOptionObjPicker(Activity activity, String headTitle, List<Condition> data) {
        super(activity);
        this.headTitle = headTitle;
        options = data;
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        setSize((int) (d.getWidth() * 0.9), BottomPopup.WRAP_CONTENT);
        setTextColor(ContextCompat.getColor(activity,R.color.text_color2),ContextCompat.getColor(activity,R.color.text_color));
        setLineColor(ContextCompat.getColor(activity,R.color.text_color));
        setTopLineVisible(false);
        setAnimationStyle(R.style.PopWindowAnimationFade);
       
    }


    @Override
    protected void onCancel() {
        super.onCancel();//点击取消
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Sets selected index.
     *
     * @param index the index
     */
    public void setSelectedIndex(int index) {
        for (int i = 0; i < options.size(); i++) {
            if (index == i) {
                selectedOption = options.get(index);
                break;
            }
        }
    }

    /**
     * Sets selected item.
     *
     * @param option the option
     */
    public void setSelectedItem(Condition option) {
        selectedOption = option;
    }

    /**
     * Sets on option pick listener.
     *
     * @param listener the listener
     */
    public void setOnOptionPickListener(OnOptionPickListener listener) {
        this.onOptionPickListener = listener;
    }

    @Override
    @NonNull
    protected View makeCenterView() {
        if (options.size() == 0) {
            throw new IllegalArgumentException("please initial options at first, can't be empty");
        }
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        WheelView optionView = new WheelView(activity);
        optionView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        optionView.setTextSize(textSize);
        optionView.setTextColor(textColorNormal, textColorFocus);
        optionView.setLineVisible(lineVisible);
        optionView.setLineColor(lineColor);
        optionView.setOffset(offset);
        layout.addView(optionView);
        TextView labelView = new TextView(activity);
        labelView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        labelView.setTextColor(textColorFocus);
        labelView.setTextSize(textSize);
        layout.addView(labelView);
        if (!TextUtils.isEmpty(label)) {
            labelView.setText(label);
        }
        List<String> data = new ArrayList<>();
        for (Condition item: options) {
            data.add(item.getName());
        }
        if (selectedOption== null || TextUtils.isEmpty(selectedOption.getName())) {
            optionView.setItems(data);
        } else {
            optionView.setItems(data, selectedOption.getName());
        }
        optionView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedOption = options.get(selectedIndex);
            }
        });
        return layout;
    }

    @Override
    public void onSubmit() {
        if (onOptionPickListener != null) {
            onOptionPickListener.onOptionPicked(selectedOption);
        }
    }

    /**
     * Gets selected option.
     *
     * @return the selected option
     */
    public Condition getSelectedOption() {
        return selectedOption;
    }

    /**
     * The interface On option pick listener.
     */
    public interface OnOptionPickListener {

        /**
         * On option picked.
         *
         * @param option the option
         */
        void onOptionPicked(Condition option);

    }

}
