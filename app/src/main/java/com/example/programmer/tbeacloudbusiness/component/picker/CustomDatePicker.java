package com.example.programmer.tbeacloudbusiness.component.picker;


import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.programmer.tbeacloudbusiness.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cn.qqtheme.framework.picker.WheelPicker;
import cn.qqtheme.framework.util.DateUtils;

/**
 * 日期选择器
 */
public class CustomDatePicker extends WheelPicker {
    /**
     * 年月日
     */
    public static final int YEAR_MONTH_DAY = 0;
    /**
     * 年月
     */
    public static final int YEAR_MONTH = 1;
    /**
     * 月日
     */
    public static final int MONTH_DAY = 2;
    private ArrayList<String> years = new ArrayList<String>();
    private ArrayList<String> months = new ArrayList<String>();
    private ArrayList<String> days = new ArrayList<String>();
    private OnDatePickListener onDatePickListener;
    private String yearLabel = "年", monthLabel = "月", dayLabel = "日";
    private int selectedYearIndex = 0, selectedMonthIndex = 0, selectedDayIndex = 0;
    private int mode = YEAR_MONTH_DAY;

    /**
     * 安卓开发应避免使用枚举类（enum），因为相比于静态常量enum会花费两倍以上的内存。
     *
     * @link http ://developer.android.com/training/articles/memory.html#Overhead
     */
    @IntDef(flag = false, value = {YEAR_MONTH_DAY, YEAR_MONTH, MONTH_DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    /**
     * Instantiates a new Date picker.
     *
     * @param activity the activity
     */
    public CustomDatePicker(Activity activity) {
        this(activity, YEAR_MONTH_DAY);
    }

    /**
     * Instantiates a new Date picker.
     *
     * @param activity the activity
     * @param mode     the mode
     * @see #YEAR_MONTH_DAY #YEAR_MONTH_DAY#YEAR_MONTH_DAY
     * @see #YEAR_MONTH #YEAR_MONTH#YEAR_MONTH
     * @see #MONTH_DAY #MONTH_DAY#MONTH_DAY
     */
    public CustomDatePicker(Activity activity, int mode) {
        super(activity);
        this.mode = mode;
        for (int i = 2000; i <= 2050; i++) {
            years.add(i + yearLabel);
        }
        for (int i = 1; i <= 12; i++) {
            months.add((DateUtils.fillZero(i)) + monthLabel);
        }
        for (int i = 1; i <= 31; i++) {
            days.add((DateUtils.fillZero(i)) + dayLabel);
        }
    }

    /**
     * Sets label.
     *
     * @param yearLabel  the year label
     * @param monthLabel the month label
     * @param dayLabel   the day label
     */
    public void setLabel(String yearLabel, String monthLabel, String dayLabel) {
        this.yearLabel = yearLabel;
        this.monthLabel = monthLabel;
        this.dayLabel = dayLabel;
    }

    public void setGravity(int gravity){
        getRootView().getChildAt(0).setBackground(ContextCompat.getDrawable(super.activity, R.drawable.picket_information_background));
        getWindow().setGravity(gravity);
    }
    /**
     * Sets range.
     *
     * @param startYear the start year
     * @param endYear   the end year
     */
    public void setRange(int startYear, int endYear) {
        years.clear();
        for (int i = startYear; i <= endYear; i++) {
            years.add(String.valueOf(i) + yearLabel);
        }
    }

    private int findItemIndex(ArrayList<String> items, int item) {
        //折半查找有序元素的索引
        int index = Collections.binarySearch(items, item, new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {
                String lhsStr = lhs.toString();
                String rhsStr = rhs.toString();
                int i = lhsStr.indexOf(yearLabel);
                int x = lhsStr.indexOf(monthLabel);
                int y = lhsStr.indexOf(dayLabel);

                if (i > 0) {
                    lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1, i) : lhsStr.substring(0, i);
                } else if (y > 0) {
                    lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1, y) : lhsStr.substring(0, y);
                } else if (x > 0) {
                    lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1, x) : lhsStr.substring(0, x);
                } else {
                    lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1) : lhsStr;
                }
                rhsStr = rhsStr.startsWith("0") ? rhsStr.substring(1) : rhsStr;
                return Integer.parseInt(lhsStr) - Integer.parseInt(rhsStr);
            }
        });
        if (index < 0) {
            index = 0;
        }
        return index;
    }

    /**
     * Sets selected item.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     */
    public void setSelectedItem(int year, int month, int day) {
        selectedYearIndex = findItemIndex(years, year);
        selectedMonthIndex = findItemIndex(months, month);
        selectedDayIndex = findItemIndex(days, day);
    }

    /**
     * Sets selected item.
     *
     * @param yearOrMonth the year or month
     * @param monthOrDay  the month or day
     */
    public void setSelectedItem(int yearOrMonth, int monthOrDay) {
        if (mode == MONTH_DAY) {
            selectedMonthIndex = findItemIndex(months, yearOrMonth);
            selectedDayIndex = findItemIndex(days, monthOrDay);
        } else {
            selectedYearIndex = findItemIndex(years, yearOrMonth);
            selectedMonthIndex = findItemIndex(months, monthOrDay);
        }
    }

    /**
     * Sets on date pick listener.
     *
     * @param listener the listener
     */
    public void setOnDatePickListener(OnDatePickListener listener) {
        this.onDatePickListener = listener;
    }

    @Nullable
    @Override
    protected View makeHeaderView() {
       View view = super.activity.getLayoutInflater().inflate(R.layout.picker_header,null);
        LinearLayout.LayoutParams  layoutParams =new LinearLayout.LayoutParams(WRAP_CONTENT,50);
        view.setLayoutParams(layoutParams);
        (view.findViewById(R.id.picker_header_tv)).setVisibility(View.GONE);
        view.findViewById(R.id.picker_header_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;//顶部视图
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

    @Override
    @NonNull
    protected View makeCenterView() {
        LinearLayout layout = new LinearLayout(activity);
        layout.setPadding(100,0,100,0);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        CustomWheelView yearView = new CustomWheelView(activity.getBaseContext());
        final int width = (screenWidthPixels-200) / 3;
        yearView.setLayoutParams(new LinearLayout.LayoutParams(width, WRAP_CONTENT));
//        yearView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        yearView.setTextSize(textSize);
        yearView.setTextColor(textColorNormal, textColorFocus);
        yearView.setLineVisible(lineVisible);
        yearView.setLineColor(lineColor);
        yearView.setOffset(offset);
        layout.addView(yearView);

        CustomWheelView monthView = new CustomWheelView(activity.getBaseContext());
        monthView.setLayoutParams(new LinearLayout.LayoutParams(width, WRAP_CONTENT));
        monthView.setTextSize(textSize);
        monthView.setTextColor(textColorNormal, textColorFocus);
        monthView.setLineVisible(lineVisible);
        monthView.setLineColor(lineColor);
        monthView.setOffset(offset);
        layout.addView(monthView);

        final CustomWheelView dayView = new CustomWheelView(activity.getBaseContext());
        dayView.setLayoutParams(new LinearLayout.LayoutParams(width, WRAP_CONTENT));
        dayView.setTextSize(textSize);
        dayView.setTextColor(textColorNormal, textColorFocus);
        dayView.setLineVisible(lineVisible);
        dayView.setLineColor(lineColor);
        dayView.setOffset(offset);
        layout.addView(dayView);
        if (mode == YEAR_MONTH) {
            dayView.setVisibility(View.GONE);
        } else if (mode == MONTH_DAY) {
            yearView.setVisibility(View.GONE);
        }
        if (mode != MONTH_DAY) {

            if (selectedYearIndex == 0) {
                yearView.setItems(years);
            } else {
                yearView.setItems(years, selectedYearIndex);
            }
            yearView.setOnWheelViewListener(new CustomWheelView.OnWheelViewListener() {
                @Override
                public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                    try {
                        selectedYearIndex = selectedIndex;
                        //需要根据年份及月份动态计算天数
                        days.clear();
                        int maxDays = DateUtils.calculateDaysInMonth(stringToYearMonthDay(item), stringToYearMonthDay(months.get(selectedMonthIndex)));
                        for (int i = 1; i <= maxDays; i++) {
                            days.add((DateUtils.fillZero(i)) + dayLabel);
                        }
                        if (selectedDayIndex >= maxDays) {
                            //年或月变动时，保持之前选择的日不动：如果之前选择的日是之前年月的最大日，则日自动为该年月的最大日
                            selectedDayIndex = days.size() - 1;
                        }
                        dayView.setItems(days, selectedDayIndex);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (selectedMonthIndex == 0) {
            monthView.setItems(months);
        } else {
            monthView.setItems(months, selectedMonthIndex);
        }
        monthView.setOnWheelViewListener(new CustomWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedMonthIndex = selectedIndex;
                if (mode != YEAR_MONTH) {
                    //年月日或年月模式下，需要根据年份及月份动态计算天数
                    days.clear();
                    int maxDays = DateUtils.calculateDaysInMonth(stringToYearMonthDay(years.get(selectedYearIndex)), stringToYearMonthDay(item));
                    for (int i = 1; i <= maxDays; i++) {
                        days.add((DateUtils.fillZero(i)) + dayLabel);
                    }
                    if (selectedDayIndex >= maxDays) {
                        //年或月变动时，保持之前选择的日不动：如果之前选择的日是之前年月的最大日，则日自动为该年月的最大日
                        selectedDayIndex = days.size() - 1;
                    }
                    dayView.setItems(days, selectedDayIndex);
                }
            }
        });
        if (mode != YEAR_MONTH) {
            if (selectedDayIndex == 0) {
                dayView.setItems(days);
            } else {
                dayView.setItems(days, selectedDayIndex);
            }
            dayView.setOnWheelViewListener(new CustomWheelView.OnWheelViewListener() {
                @Override
                public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                    selectedDayIndex = selectedIndex;
                }
            });
        }
        return layout;
    }

    private int stringToYearMonthDay(String lhsStr) {
        int i = lhsStr.indexOf(yearLabel);
        int x = lhsStr.indexOf(monthLabel);
        int y = lhsStr.indexOf(dayLabel);

        if (i > 0) {
            lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1, i) : lhsStr.substring(0, i);
        } else if (y > 0) {
            lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1, y) : lhsStr.substring(0, y);
        } else if (x > 0) {
            lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1, x) : lhsStr.substring(0, x);
        } else {
            lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1) : lhsStr;
        }
        return Integer.parseInt(lhsStr);
    }

    @Override
    protected void onSubmit() {
        if (onDatePickListener != null) {
            String year = getSelectedYear();
            String month = getSelectedMonth();
            String day = getSelectedDay();
            switch (mode) {
                case YEAR_MONTH:
                    ((OnYearMonthPickListener) onDatePickListener).onDatePicked(year, month);
                    break;
                case MONTH_DAY:
                    ((OnMonthDayPickListener) onDatePickListener).onDatePicked(month, day);
                    break;
                default:
                    ((OnYearMonthDayPickListener) onDatePickListener).onDatePicked(year, month, day);
                    break;
            }
        }
    }

    /**
     * Gets selected year.
     *
     * @return the selected year
     */
    public String getSelectedYear() {
        return years.get(selectedYearIndex);
    }

    /**
     * Gets selected month.
     *
     * @return the selected month
     */
    public String getSelectedMonth() {
        return months.get(selectedMonthIndex);
    }

    /**
     * Gets selected day.
     *
     * @return the selected day
     */
    public String getSelectedDay() {
        return days.get(selectedDayIndex);
    }

    /**
     * The interface On date pick listener.
     */
    protected interface OnDatePickListener {

    }

    /**
     * The interface On year month day pick listener.
     */
    public interface OnYearMonthDayPickListener extends OnDatePickListener {

        /**
         * On date picked.
         *
         * @param year  the year
         * @param month the month
         * @param day   the day
         */
        void onDatePicked(String year, String month, String day);

    }

    /**
     * The interface On year month pick listener.
     */
    public interface OnYearMonthPickListener extends OnDatePickListener {

        /**
         * On date picked.
         *
         * @param year  the year
         * @param month the month
         */
        void onDatePicked(String year, String month);

    }

    /**
     * The interface On month day pick listener.
     */
    public interface OnMonthDayPickListener extends OnDatePickListener {

        /**
         * On date picked.
         *
         * @param month the month
         * @param day   the day
         */
        void onDatePicked(String month, String day);

    }

}

