package com.example.programmer.tbeacloudbusiness.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.HomeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.UpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.component.MainNavigateTabBar;
import com.example.programmer.tbeacloudbusiness.fragment.MainFragment;
import com.example.programmer.tbeacloudbusiness.fragment.MyFragment;
import com.example.programmer.tbeacloudbusiness.fragment.OtherFragment;
import com.example.programmer.tbeacloudbusiness.fragment.TbMianFragment;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.AppUpdateUtils;
import com.example.programmer.tbeacloudbusiness.utils.AppVersion;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        getUpdateInfo();
//        update();
    }

    /**
     * 从服务器获取数据
     */
    private void getUpdateInfo() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ResponseInfo re = (ResponseInfo) msg.obj;
                            if (re.isSuccess()) {
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                String json = gson.toJson(re.data);
                                UpdateResponseModel model = gson.fromJson(json, UpdateResponseModel.class);
                                if (model.versioninfo != null) {
                                    UpdateResponseModel.VersioninfoBean info = model.versioninfo;
                                    if ("on".equals(info.tipswitch) && info.jumpurl != null && !"".equals(info.jumpurl)) {
                                        AppVersion av = new AppVersion();
                                        av.setApkName("tbeacloudbusiness.apk");
//                                av.setSha1("FCDA0D0E1E7D620A75DA02A131E2FFEDC1742AC8");
//                                        av.setUrl("http://down.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
                                        av.setUrl(info.jumpurl);
                                        av.setContent(info.upgradedescription);
                                        av.setVerCode(info.versioncode);
                                        av.setVersionName(info.versionname);
                                        if("yes".equals(info.mustupgrade)){
                                            AppUpdateUtils.init(MainActivity.this, av, true, false,false);//强制升级
                                        }else {
                                            AppUpdateUtils.init(MainActivity.this, av, true, false,true);
                                        }
                                        AppUpdateUtils.upDate();
                                    }
                                } else {
                                    ToastUtil.showMessage(re.getMsg());
                                }

                            } else {
                                ToastUtil.showMessage(re.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            ToastUtil.showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        UserAction userAction = new UserAction();
                        ResponseInfo model = userAction.getUpdate();
                        handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void update() {
//        AppVersion av = new AppVersion();
//        av.setApkName("AppUpdate.apk");
//        av.setSha1("FCDA0D0E1E7D620A75DA02A131E2FFEDC1742AC8");
//        av.setAppName("博客园");
//        av.setUrl("http://down.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
//        av.setContent("1、测试升级;2、测试升级2！！;3、一大波功能！");
//        av.setVerCode(2);
//        av.setVersionName("1.1");
//        AppUpdateUtils.init(MainActivity.this, av, true,false,false);
//        AppUpdateUtils.upDate();
//    }


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
