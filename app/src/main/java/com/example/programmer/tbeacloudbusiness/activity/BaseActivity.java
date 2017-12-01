package com.example.programmer.tbeacloudbusiness.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.utils.permissonutil.PermissionActivity;
import com.jaeger.library.StatusBarUtil;
import com.mic.etoast2.Toast;

import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;

public class BaseActivity extends PermissionActivity {
	protected ImageButton mBackBtn;
	protected ImageButton mRightBtn;
	public final int SET_REQEST = 1000;
	protected Activity mContext;

	@Override
	public void setContentView(@LayoutRes int layoutResID) {
		super.setContentView(layoutResID);
		StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.head_color),0);
		mContext = this;
		MyApplication.instance.addActivity(mContext);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PermissionGen.onRequestPermissionsResult(this,requestCode, permissions, grantResults);
	}

	/**
	 * 查找View
	 *
	 * @param id   控件的id
	 * @param <VT> View类型
	 * @return
	 */
	protected <VT extends View> VT getViewById(@IdRes int id) {
		return (VT) findViewById(id);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void initTopbar(String text) {
		initTopbar(text, null);
	}


	protected void initTopbar(String text, OnClickListener listener) {
		TextView tv = (TextView) findViewById(R.id.top_center);
		mBackBtn = (ImageButton) findViewById(R.id.top_left);
		mRightBtn = (ImageButton) findViewById(R.id.top_right);
		if (listener != null) {
			mRightBtn.setVisibility(View.VISIBLE);
			mRightBtn.setOnClickListener(listener);
		}
		tv.setText(text);
		mBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}


	protected void initTopbar(String text, OnClickListener listener,
							  int resource) {
		TextView tv = (TextView) findViewById(R.id.top_center);
		mBackBtn = (ImageButton) findViewById(R.id.top_left);
		mRightBtn = (ImageButton) findViewById(R.id.top_right);
		mRightBtn.setImageResource(resource);
		if (listener != null) {
			mRightBtn.setVisibility(View.VISIBLE);
			mRightBtn.setOnClickListener(listener);
		}
		tv.setText(text);
		mBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	protected void initTopbar(String text,String rightText, OnClickListener listener,
							  int resource,OnClickListener listener2) {
		TextView tv = (TextView) findViewById(R.id.top_center);
		mBackBtn = (ImageButton) findViewById(R.id.top_left);
		mRightBtn = (ImageButton) findViewById(R.id.top_right_text_image);
		mRightBtn.setImageResource(resource);
		if (listener != null) {
			mRightBtn.setVisibility(View.VISIBLE);
			mRightBtn.setOnClickListener(listener2);
		}
		tv.setText(text);
		TextView rightTv = (TextView)findViewById(R.id.top_right_text);
		rightTv.setText(rightText);
		rightTv.setVisibility(View.VISIBLE);
		rightTv.setOnClickListener(listener);

		mBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}



	/**
	 * 右边的文字
	 */
	protected void initTopbar(String text, String rightText,
							  OnClickListener listener) {
		TextView tv = (TextView) findViewById(R.id.top_center);
		mBackBtn = (ImageButton) findViewById(R.id.top_left);
		if (!"".equals(rightText)) {
			TextView rightTv = (TextView) findViewById(R.id.top_right_text);
			rightTv.setVisibility(View.VISIBLE);
			rightTv.setText(rightText);
			rightTv.setOnClickListener(listener);
		}
		tv.setText(text);
		mBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 判断某个界面是否在前台
	 * 
	 * @param context
	 * @param className
	 *            某个界面名称
	 */
	public static boolean isForeground(Context context, String className) {
		if (context == null || TextUtils.isEmpty(className)) {
			return false;
		}
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
		if (list != null && list.size() > 0) {
			ComponentName cpn = list.get(0).topActivity;
			if (className.equals(cpn.getClassName())) {
				return true;
			}
		}
		return false;
	}

	private  Handler handler = new Handler(Looper.getMainLooper());

	private  Toast toast = null;

	private  Object synObj = new Object();

	public  void showMessage(final String msg) {
		showMessage( msg, android.widget.Toast.LENGTH_SHORT);
	}

	/**
	 * 根据设置的文本显示
	 *
	 * @param msg
	 */
	public  void showMessage(final int msg) {
		showMessage( msg, android.widget.Toast.LENGTH_SHORT);
	}

	/**
	 * 显示一个文本并且设置时长
	 *
	 * @param msg
	 * @param len
	 */
	public  void showMessage( final CharSequence msg, final int len) {
		if (msg == null || msg.equals("")) {
			return;
		}
		handler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
					if (toast != null) {
						//toast.cancel();
						toast.setText(msg);
//                        toast.setDuration(len);
					} else {
						toast = Toast.makeText(mContext, msg + "", len);
					}
					toast.show();
				}
			}
		});
	}

	/**
	 * 资源文件方式显示文本
	 *
	 * @param msg
	 * @param len
	 */
	public  void showMessage( final int msg, final int len) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (synObj) {
//                    if (toast != null) {
//                        //toast.cancel();
//                        toast.setText(msg);
//                        toast.setDuration(len);
//                    } else {
					toast = Toast.makeText(mContext, msg, len);
//                    }
					toast.show();
				}
			}
		});
	}

	public void setTextViewValue(int id,String value){
		((TextView)findViewById(id)).setText(value);
	}
}
