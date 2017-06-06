package com.example.programmer.tbeacloudbusiness.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

	private static final String SERVICE_PATH="http://www.u-shang.net/enginterface/index.php";
	private static final String IMG_SERVICE_PATH="http://www.u-shang.net/";
	
	
	public static MyApplication instance;
	private List<SoftReference<Activity>> activitys=new ArrayList<SoftReference<Activity>>();
	private boolean mOnline = false;

	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
//		initUniversalImageLoader();
	}

	
	public int getActivityCount(){
		return activitys.size();
	}
	
	public String getImgPath(String imgName){
		return IMG_SERVICE_PATH+imgName;
	}


	public String getImgPath(){
		return IMG_SERVICE_PATH;
	}


	public String getServicePath(){
		return SERVICE_PATH;
	}
	
	public void exit(){
		for(SoftReference<Activity> sa:activitys)if(sa.get()!=null)sa.get().finish();
//		System.exit(0);
	}
	
	public void addActivity(Activity activity){
		activitys.add(new SoftReference<Activity>(activity));
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishSingleActivity(Activity activity) {
		if (activity != null) {
			if (activitys.contains(activity)) {
				activitys.remove(activity);
			}
			activity.finish();
		}
	}



	public String getUserId(){
		return ShareConfig.getConfigString(instance, Constants.USERID,"");
	}




	// 显示缺失权限提示
//	public void showMissingPermissionDialog(Context  context) {
//		final CustomDialog dialog = new CustomDialog(context,R.style.MyDialog, R.layout.tip_delete_dialog);
//		dialog.setTitle(getResources().getString(R.string.help));
//		dialog.setText(getResources().getString(R.string.string_gps_help_text));
//		dialog.setConfirmBtnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		}, getResources().getString(R.string.quit));
//		dialog.setCancelBtnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//				MyApplication.instance.startActivity(intent);
//			}
//		},getResources().getString(R.string.settings));
//		dialog.show();
//	}

//	private void initUniversalImageLoader() {
//		DisplayImageOptions options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.icon_defult)
//				.showImageForEmptyUri(R.drawable.icon_defult)//设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.icon_defult)  //设置图片加载/解码过程中错误时候显示的图片
//				.resetViewBeforeLoading(true)
//				.cacheInMemory(true)
//				.cacheOnDisk(true)
//				.considerExifParams(true)
//				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//				.bitmapConfig(Bitmap.Config.RGB_565)
//				.build();
//
//		int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
//				.getMemoryClass();
//		int memCacheSize = 1024 * 1024 * memClass / 8;
//
//		File cacheDir = new File(Environment.getExternalStorageDirectory().getPath() + "/jiecao/cache");
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//				.threadPoolSize(3)
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				.denyCacheImageMultipleSizesInMemory()
//				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
//				.memoryCache(new UsingFreqLimitedMemoryCache(memCacheSize))
//				.memoryCacheSize(memCacheSize)
//				.diskCacheSize(50 * 1024 * 1024)
//				.tasksProcessingOrder(QueueProcessingType.LIFO)
//				.diskCache(new UnlimitedDiskCache(cacheDir))
//				.imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
//				.defaultDisplayImageOptions(options)
//				.build();
//		ImageLoader.getInstance().init(config);
//	}
}
