package com.example.programmer.tbeacloudbusiness.component.dropdownMenu;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.example.programmer.tbeacloudbusiness.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandPopTabView extends LinearLayout implements OnDismissListener {
    private ArrayList<RelativeLayout> mViewLists = new ArrayList<RelativeLayout>();
    private DropdownButton mSelectedToggleBtn;
    private CustomPopw mPopupWindow;
    private Context mContext;
    private int mDisplayWidth;
    private int mDisplayHeight;
    private int mSelectPosition;
    private int mTabPostion = -1; //记录TAB页号
    private int mToggleBtnBackground;
    private int mToggleBtnBackgroundColor;
    private int mToggleTextColor;
    private int mPopViewBackgroundColor;
    private float mToggleTextSize;
    List<LinearLayout> mLayout = new ArrayList<>();
    List<String> mTopList = new ArrayList<>();
    OnClose mOnClose;

    public void addTopList(List<String> list) {
        this.mTopList = list;
    }

    public ExpandPopTabView(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandPopTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.ExpandPopTabView);
            mToggleBtnBackground = a.getResourceId(R.styleable.ExpandPopTabView_tab_toggle_btn_bg, -1);
            mToggleBtnBackgroundColor = a.getColor(R.styleable.ExpandPopTabView_tab_toggle_btn_color, -1);
            mToggleTextColor = a.getColor(R.styleable.ExpandPopTabView_tab_toggle_btn_font_color, -1);
            mPopViewBackgroundColor = a.getColor(R.styleable.ExpandPopTabView_tab_pop_bg_color, -1);
            mToggleTextSize = a.getDimension(R.styleable.ExpandPopTabView_tab_toggle_btn_font_size, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        mContext = context;
        mDisplayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        mDisplayHeight = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void addItemToExpandTab(String tabTitle, final ViewGroup tabItemView) {
        addItemView(tabTitle, tabItemView, -1, -1);
    }

    /**
     * 添加View
     *
     * @param tabTitle    显示的文本
     * @param tabItemView View
     * @param width       宽
     * @param gravity     控件位置
     */
    public void addItemToExpandTab(String tabTitle, final ViewGroup tabItemView, int width, int gravity) {
        addItemView(tabTitle, tabItemView, width, gravity);
    }

    /**
     * 添加View
     *
     * @param tabTitle    显示的文本
     * @param tabItemView View
     * @param gravity     控件位置
     */
    public void addItemToExpandTab(String tabTitle, final ViewGroup tabItemView, int gravity) {
        addItemView(tabTitle, tabItemView, -1, gravity);
    }

    public void addItemView(String tabTitle, ViewGroup tabItemView, int width, int gravity) {
        LinearLayout parentLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.expand_tab_button, this, false);
        DropdownButton tButton = (DropdownButton) parentLayout.findViewById(R.id.toggleButton);
        if (mToggleBtnBackground != -1) {
            tButton.setBackgroundResource(mToggleBtnBackground);
        }
        if (mToggleBtnBackgroundColor != -1) {
            tButton.setBackgroundColor(mToggleBtnBackgroundColor);
        }
//        if(mTabPostion == -1){
//            tButton.setViewColor(ContextCompat.getColor(mContext,R.color.blue));
//        }

//        if(mToggleTextColor != -1){
//            tButton.setTextColor(mToggleTextColor);
//        }
//        if(mToggleTextSize != -1){
//            tButton.setTextSize(mToggleTextSize);
//        }

        tButton.setText(tabTitle);
        tButton.setTag(++mTabPostion);

//        tButton.setTag(postion);
        tButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DropdownButton tButton = (DropdownButton) view;
                    if (mSelectedToggleBtn != null) {
                        int po = (Integer) mSelectedToggleBtn.getTag();
                        if (po != -1 && mSelectPosition == po && mPopupWindow.isShowing()) {
//                            mSelectedToggleBtn.setChecked(false);
                            mSelectPosition = -1;
                            onExpandPopView();
                            if (mOnClose != null) {
                                mOnClose.onCloseListener();
                            }
//                            setViewColor(ContextCompat.getColor(mContext, R.color.blue), "");
//                            setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                            Log.e("ExpandPopTabView", "-----------执行了操作1-------------");
                        } else {

                            mSelectedToggleBtn = tButton;
                            mSelectPosition = (Integer) mSelectedToggleBtn.getTag();
                            tButton.setChecked(true);
                            expandPopView();
                            Log.e("ExpandPopTabView", "-----------执行了操作2-------------");
                        }
                    } else {
                        tButton.setChecked(true);
                        mSelectedToggleBtn = tButton;
                        mSelectPosition = (Integer) tButton.getTag();
                        expandPopView();
                        Log.e("ExpandPopTabView", "-----------执行了操作3-------------");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (width != -1) {
            LayoutParams layoutParams = new LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);
            parentLayout.setLayoutParams(layoutParams);
        }

        if (gravity != -1) {
            tButton.setGravity(gravity);
        }

        addView(parentLayout);
        mLayout.add(parentLayout);

        RelativeLayout popContainerView = new RelativeLayout(mContext);

        if (mPopViewBackgroundColor != -1) {
            popContainerView.setBackgroundColor(mPopViewBackgroundColor);
        } else {
            popContainerView.setBackgroundColor(Color.parseColor("#b0000000"));
        }
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popContainerView.addView(tabItemView, rl);
        popContainerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onExpandPopView();
//                setViewColor(ContextCompat.getColor(mContext,R.color.blue));
            }
        });

        mViewLists.add(popContainerView);
    }

    //同一个界面上有多个下拉框时调用
    public void setViewColor(int color, String value) {
        for (int i = 0; i < mLayout.size(); i++) {
            LinearLayout parentLayout = mLayout.get(i);
            if (parentLayout != null) {
                DropdownButton tButton = (DropdownButton) parentLayout.findViewById(R.id.toggleButton);
                tButton.setText(mTopList.get(i));
                tButton.setViewColor(ContextCompat.getColor(mContext, R.color.text_color), R.drawable.icon_arrow_gray);
            }
        }
        if (mSelectedToggleBtn != null) {
            mSelectedToggleBtn.setViewColor(color);
            if (!"".equals(value)) {
                mSelectedToggleBtn.setText(value);
            }
        }
    }


    //同一个界面上有一个或者多个下拉框时,并且还有其他选项,并且是选中其他选项时调用
    public void setViewColor() {
        for (int i = 0; i < mLayout.size(); i++) {
            LinearLayout parentLayout = mLayout.get(i);
            if (parentLayout != null) {
                DropdownButton tButton = (DropdownButton) parentLayout.findViewById(R.id.toggleButton);
                tButton.setText(mTopList.get(i));
                tButton.setViewColor(ContextCompat.getColor(mContext, R.color.text_color), R.drawable.icon_arrow_gray);
            }
        }
    }


    public void setViewColor(int color) {
//        for (int i = 0; i < mLayout.size(); i++) {
//            LinearLayout parentLayout = mLayout.get(i);
//            if (parentLayout != null) {
//                DropdownButton tButton = (DropdownButton) parentLayout.findViewById(R.id.toggleButton);
//                tButton.setViewColor(ContextCompat.getColor(mContext, R.color.text_color), R.drawable.icon_arrow_gray);
//            }
//        }
        if (mSelectedToggleBtn != null) {
            mSelectedToggleBtn.setViewColor(color);
        }
    }

    //同一个界面上有一个下拉框时调用
    public void setViewColor(int color, int id) {
        for (int i = 0; i < mLayout.size(); i++) {
            LinearLayout parentLayout = mLayout.get(i);
            if (parentLayout != null) {
                DropdownButton tButton = (DropdownButton) parentLayout.findViewById(R.id.toggleButton);
                tButton.setViewColor(ContextCompat.getColor(mContext, R.color.text_color), R.drawable.icon_arrow_gray);
            }
        }
        if (mSelectedToggleBtn != null) {
            mSelectedToggleBtn.setViewColor(color, id);
        }
    }

    public void setToggleButtonText(String tabTitle) {
        DropdownButton toggleButton = (DropdownButton) getChildAt(mSelectPosition).findViewById(R.id.toggleButton);
        toggleButton.setText(tabTitle);
    }

    private void expandPopView() {
        if (mPopupWindow == null) {
            mPopupWindow = new CustomPopw(mViewLists.get(mSelectPosition), mDisplayWidth, mDisplayHeight);
            mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            mPopupWindow.setFocusable(false);
            mPopupWindow.setOutsideTouchable(false);
        }

        if (mSelectedToggleBtn.isCheck) {
            if (!mPopupWindow.isShowing()) {
                showPopView();
            } else {
                mPopupWindow.setOnDismissListener(this);
                mPopupWindow.dismiss();
            }
        } else {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
        }
    }

    /**
     * 如果菜单成展开状态，则让菜单收回去
     * 注：Activty 销毁时 如果对话框没有关闭刚关闭
     */
    public boolean onExpandPopView() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            if (mSelectedToggleBtn != null) {
                mSelectedToggleBtn.setChecked(false);
            }
            return true;
        } else {
            return false;
        }
    }

    public void showPopView() {
        if (mPopupWindow.getContentView() != mViewLists.get(mSelectPosition)) {
            mPopupWindow.setContentView(mViewLists.get(mSelectPosition));
        }
//        mPopupWindow.showAsDropDown(this);
        mPopupWindow.showAsDropDown(this, 0, 0);
//        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
//            int[] location = new int[2];
//            this.getLocationOnScreen(location);
//            int y = location[1];
//            mPopupWindow.showAtLocation(this, Gravity.NO_GRAVITY, 0, y);
//        } else {
//            mPopupWindow.showAsDropDown(this);
//        }
    }


    @Override
    public void onDismiss() {
        showPopView();
        mPopupWindow.setOnDismissListener(null);
    }

    public interface OnClose {
        void onCloseListener();
    }

    public void setOnCloseListener(OnClose onClose) {
        mOnClose = onClose;
    }


}
