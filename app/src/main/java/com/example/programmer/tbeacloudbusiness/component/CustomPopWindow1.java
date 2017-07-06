package com.example.programmer.tbeacloudbusiness.component;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.List;


public class CustomPopWindow1 {
    private ItemClick mItemClick;//内容点击事件
    private Context mContext;
    CustomPopWindow mPopWindow;

    public CustomPopWindow1(Context context){
        mContext  = context;
    }

    /**
     * 类似性别选择框
     *
     * @param parentLayout 父布局
     * @param headerRes    头部布局
     * @param contentRes   内容
     * @param items        显示的内容
     */
    public void init(View parentLayout, int headerRes, int contentRes, List<String> items) {
        try {
            LinearLayout parentView = (LinearLayout) ((Activity) mContext).getLayoutInflater().inflate(R.layout.pop_window_layout, null);
            View headerView = ((Activity) mContext).getLayoutInflater().inflate(headerRes, null);
            headerView.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dissmiss();
                }
            });

            parentView.addView(headerView);
            if (items != null) {
                for (String item : items) {
                    View contentView = ((Activity) mContext).getLayoutInflater().inflate(contentRes, null);
                    TextView textView = (TextView) contentView.findViewById(R.id.pop_window_tv);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopWindow.dissmiss();
                            if (mItemClick != null) {
                                mItemClick.onItemClick(((TextView) v).getText() + "");
                            }
                        }
                    });
                    textView.setText(item);
                    parentView.addView(contentView);
                }
            } else {
                View contentView = ((Activity) mContext).getLayoutInflater().inflate(contentRes, null);
                parentView.addView(contentView);
            }

            mPopWindow =   new CustomPopWindow.PopupWindowBuilder((mContext))
                    .setView(parentView)
                    .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                    .setBgDarkAlpha(0.5f) // 控制亮度
                    .setAnimationStyle(R.style.PopWindowAnimationFade)
                    .create()
                    .showAtLocation(parentLayout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 警告框
     *
     * @param parentLayout 父布局
     * @param headerRes    头部布局
     * @param contentRes   内容
     * @param title        标题
     * @param content      内容
     * @param btnText      btton显示的文本
     */
    public void init(View parentLayout, int headerRes, int contentRes, String title, String content, String btnText) {
        try {
            LinearLayout parentView = (LinearLayout) ((Activity) mContext).getLayoutInflater().inflate(R.layout.pop_window_layout, null);
            View headerView = ((Activity) mContext).getLayoutInflater().inflate(headerRes, null);
            ((TextView) headerView.findViewById(R.id.picker_header_tv)).setText(title);
            headerView.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dissmiss();
                }
            });

            parentView.addView(headerView);

            View contentView = ((Activity) mContext).getLayoutInflater().inflate(contentRes, null);
            TextView textView = (TextView) contentView.findViewById(R.id.pop_window_tv);
            Button button = (Button) contentView.findViewById(R.id.pop_window_btn);
            button.setText(btnText);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dissmiss();
                    if (mItemClick != null) {
                        mItemClick.onItemClick("");
                    }
                }
            });
            textView.setText(content);
            parentView.addView(contentView);


            mPopWindow = new CustomPopWindow.PopupWindowBuilder((mContext))
                    .setView(parentView)
                    .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                    .setBgDarkAlpha(0.5f) // 控制亮度
                    .setAnimationStyle(R.style.PopWindowAnimationFade)
                    .create()
                    .showAtLocation(parentLayout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ItemClick {
        void onItemClick(String text);
    }


    public void setItemClick(ItemClick click) {
        this.mItemClick = click;
    }
}
