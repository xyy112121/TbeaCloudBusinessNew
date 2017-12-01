package com.example.programmer.tbeacloudbusiness.component;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.user.LoginActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;

import com.example.zhouwei.library.CustomPopWindow;

import java.util.List;


public class CustomPopWindow1 {
    private ItemClick mItemClick;//内容点击事件
    private ItemClick2 mItemClick2;//内容点击事件
    private ItemClickClose mItemClickClose;//内容点击事件
    private Context mContext;
    CustomPopWindow mPopWindow;

    public CustomPopWindow1(Context context) {
        mContext = context;
    }

    /**
     * 类似性别选择框
     *
     * @param parentLayout 父布局
     * @param headerRes    头部布局
     * @param contentRes   内容
     * @param items        显示的内容
     */
    public void init(View parentLayout, int headerRes, int contentRes, List<KeyValueBean> items, String flag) {
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
                for (KeyValueBean item : items) {
                    View contentView = ((Activity) mContext).getLayoutInflater().inflate(contentRes, null);
                    TextView textView = (TextView) contentView.findViewById(R.id.pop_window_tv);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopWindow.dissmiss();
                            if (mItemClick2 != null) {
                                mItemClick2.onItemClick2((KeyValueBean) v.getTag());
                            }
                        }
                    });
                    textView.setText(item.getValue());
                    textView.setTag(item);
                    parentView.addView(contentView);
                }
            } else {
                View contentView = ((Activity) mContext).getLayoutInflater().inflate(contentRes, null);
                parentView.addView(contentView);
            }

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
                    textView.setTag(item);
                    parentView.addView(contentView);
                }
            } else {
                View contentView = ((Activity) mContext).getLayoutInflater().inflate(contentRes, null);
                parentView.addView(contentView);
            }

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

    public void init(View parentLayout, int headerRes, int contentRes, String title) {
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
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dissmiss();
                    if (mItemClick != null) {
                        mItemClick.onItemClick("");
                    }
                }
            });
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
//            if ("功能受限".equals(title)) {
//                headerView.findViewById(R.id.picker_header_close).setVisibility(View.GONE);
//            }
            headerView.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickClose == null) {
                        mPopWindow.dissmiss();
                    } else {
                        mItemClickClose.close();
                    }

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

//            ColorDrawable dw = new ColorDrawable(00000);popupwindow.setBackgroundDrawable(dw);
            mPopWindow = new CustomPopWindow.PopupWindowBuilder((mContext)).setOnDissmissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (mItemClickClose == null) {
                        mPopWindow.dissmiss();
                    } else {
                        mItemClickClose.close();
                    }

                }
            })
                    .setView(parentView)
                    .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                    .setBgDarkAlpha(0.4f) // 控制亮度
                    .setAnimationStyle(R.style.PopWindowAnimationFade)
                    .setTouchable(true)
                    .enableOutsideTouchableDissmiss(false)
                    .create()
                    .showAtLocation(parentLayout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示一行提示文本
     */
    public void init(View parentLayout, int headerRes, int contentRes, String title, String content, String btnText,int flag) {
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
            TextView textView = (TextView) contentView.findViewById(R.id.scan_code_pay_confirm_tip_user);
            textView.setText(content);
            TextView moneyView = (TextView) contentView.findViewById(R.id.scan_code_pay_confirm_tip_money);
            moneyView.setVisibility(View.GONE);
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

    /**
     * 显示两行文本
     */
    public void init(View parentLayout, int headerRes, int contentRes, String title, String content, String content1, String btnText, int length1,int length) {
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
            TextView textView = (TextView) contentView.findViewById(R.id.scan_code_pay_confirm_tip_user);
            SpannableStringBuilder builder = new SpannableStringBuilder(content);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.head_color));
            builder.setSpan(redSpan, length1, length1 + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(builder);

            TextView moneyView = (TextView) contentView.findViewById(R.id.scan_code_pay_confirm_tip_money);
            moneyView.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
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
            moneyView.setText(content1);
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

    /**
     * 显示三行文本
     */
    public void init(View parentLayout, String title, String content, String content1,String content2, String btnText) {
        try {
            LinearLayout parentView = (LinearLayout) ((Activity) mContext).getLayoutInflater().inflate(R.layout.pop_window_layout, null);
            View headerView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.pop_window_header, null);
            ((TextView) headerView.findViewById(R.id.picker_header_tv)).setText(title);
            headerView.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dissmiss();
                }
            });

            parentView.addView(headerView);

            View contentView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.activity_ck_sr_view_pop, null);
            TextView textView = (TextView) contentView.findViewById(R.id.tip_text);
            TextView textView1 = (TextView) contentView.findViewById(R.id.tip_text1);
            TextView textView2 = (TextView) contentView.findViewById(R.id.tip_text2);
            textView.setText(content);
            textView1.setText(content1);
            textView2.setText(content2);

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

    public void init(View parentLayout, int headerRes, int contentRes, String title, String content, String content1, String btnText) {
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
            TextView textView = (TextView) contentView.findViewById(R.id.scan_code_pay_confirm_tip_user);
            TextView moneyView = (TextView) contentView.findViewById(R.id.scan_code_pay_confirm_tip_money);
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
            moneyView.setText(content1);
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

    public interface ItemClickClose {
        void close();
    }

    public interface ItemClick2 {
        void onItemClick2(KeyValueBean bean);
    }

    public void setItemClickClose(ItemClickClose click) {
        this.mItemClickClose = click;
    }

    public void setItemClick(ItemClick click) {
        this.mItemClick = click;
    }

    public void setItemClick2(ItemClick2 click) {
        this.mItemClick2 = click;
    }
}
