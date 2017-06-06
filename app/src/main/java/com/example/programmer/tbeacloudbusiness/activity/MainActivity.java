package com.example.programmer.tbeacloudbusiness.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.component.MainNavigateTabBar;
import com.example.programmer.tbeacloudbusiness.fragment.MianFragment;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;


public class MainActivity extends TopActivity {
    private MainNavigateTabBar mNavigateTabBar;
    /**
     * 权限
     */
    private String[] mPermissions = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= 23){
            mPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CALL_PHONE};
            PermissionGen.needPermission(MainActivity.this,100,mPermissions);
        }
        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);

        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(MianFragment.class, new MainNavigateTabBar.TabParam(R.drawable.icon_home, R.drawable.icon_home, "首页"));
        mNavigateTabBar.addTab(MianFragment.class, new MainNavigateTabBar.TabParam(R.drawable.icon_tbea, R.drawable.icon_tbea, "特变电工"));
        mNavigateTabBar.addTab(MianFragment.class, new MainNavigateTabBar.TabParam(R.drawable.icon_other, R.drawable.icon_other, "其他应用"));
        mNavigateTabBar.addTab(MianFragment.class, new MainNavigateTabBar.TabParam(R.drawable.icon_me, R.drawable.icon_me, "我的"));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        listener();
        MyApplication.instance.addActivity(MainActivity.this);
    }

    private void listener(){

    }


    @PermissionFail(requestCode = 100)
    private void doFailSomething() {
        for (int i = 0; i < mPermissions.length; i++) {
            boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(this, mPermissions[i]);
            if(isTip){
                Toast.makeText(MainActivity.this,"你需要允许访问权限，才可正常使用该功能！",Toast.LENGTH_SHORT).show();
                finish();
                break;
            }else {
                showMissingPermissionDialog();
                break;
            }
        }
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
