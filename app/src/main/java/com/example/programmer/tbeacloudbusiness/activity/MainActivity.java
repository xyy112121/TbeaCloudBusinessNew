package com.example.programmer.tbeacloudbusiness.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.component.MainNavigateTabBar;
import com.example.programmer.tbeacloudbusiness.fragment.MainFragment;
import com.example.programmer.tbeacloudbusiness.fragment.MyFragment;
import com.example.programmer.tbeacloudbusiness.fragment.OtherFragment;
import com.example.programmer.tbeacloudbusiness.fragment.TbMianFragment;
import com.jaeger.library.StatusBarUtil;


public class MainActivity extends BaseActivity {
    private MainNavigateTabBar mNavigateTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTransparentForImageViewInFragment(MainActivity.this, null);
        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);

        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(MainFragment.class, new MainNavigateTabBar.TabParam(R.drawable.icon_main, R.drawable.icon_main_select, "首页"));
        mNavigateTabBar.addTab(TbMianFragment.class, new MainNavigateTabBar.TabParam(R.drawable.icon_main_tbea, R.drawable.icon_main_tbea_select, "特变电工"));
        mNavigateTabBar.addTab(OtherFragment.class, new MainNavigateTabBar.TabParam(R.drawable.icon_other, R.drawable.icon_other_select, "其他应用"));
        mNavigateTabBar.addTab(MyFragment.class, new MainNavigateTabBar.TabParam(R.drawable.icon_my, R.drawable.icon_my_select, "我的"));
        MyApplication.instance.addActivity(MainActivity.this);
    }


    public void showTbMaianFragment() {
        View view = mNavigateTabBar.getChildAt(1);
        view.performClick();
    }

    public void showOtherFragment() {
        View view = mNavigateTabBar.getChildAt(2);
        view.performClick();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
