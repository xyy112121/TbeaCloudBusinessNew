package com.example.programmer.tbeacloudbusiness.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;

import com.mic.etoast2.Toast;

/**
 * Created by programmer on 2017/12/1.
 */

public class BaseFragmentV extends Fragment {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Toast toast = null;

    private static Object synObj = new Object();

    public static void showMessage(final String msg, Context context) {
        showMessage(context, msg, android.widget.Toast.LENGTH_SHORT);
    }

    /**
     * 根据设置的文本显示
     *
     * @param msg
     */
    public static void showMessage(final int msg, Context context) {
        showMessage(context, msg, android.widget.Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个文本并且设置时长
     *
     * @param msg
     * @param len
     */
    public static void showMessage(final Context context, final CharSequence msg, final int len) {
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
                        toast = Toast.makeText(context, msg + "", len);
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
    public static void showMessage(final Context context, final int msg, final int len) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) {
//                    if (toast != null) {
//                        //toast.cancel();
//                        toast.setText(msg);
//                        toast.setDuration(len);
//                    } else {
                    toast = Toast.makeText(context, msg, len);
//                    }
                    toast.show();
                }
            }
        });
    }
}
