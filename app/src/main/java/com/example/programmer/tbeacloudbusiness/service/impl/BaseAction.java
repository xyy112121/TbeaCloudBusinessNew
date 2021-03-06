package com.example.programmer.tbeacloudbusiness.service.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.http.ReqBase;
import com.example.programmer.tbeacloudbusiness.http.ReqBase1;
import com.example.programmer.tbeacloudbusiness.http.ReqHead;
import com.example.programmer.tbeacloudbusiness.http.ReqHead1;
import com.example.programmer.tbeacloudbusiness.http.ReqUploadFile;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by abc on 17/1/14.
 */

public class BaseAction {
    public Gson gson;

    public  BaseAction(){
        gson = new Gson();
    }

    public String sendRequest(final String serviceCode,final List<NameValuePair> pairs) throws InterruptedException, IOException,
            ExecutionException {
        FutureTask task = new FutureTask(
                new Callable()
                {
                    public String call() throws Exception {
                        ReqHead rh = new ReqHead();
                        rh.serviceCode=serviceCode;
                        String url = MyApplication.instance.getServicePath();
                        ReqBase req = new ReqBase(rh, pairs);
                        req.req(url);
                        String rspContext = req.getRspContext();
                        Log.d("返回的json",rspContext);
                        return rspContext;
                    }
                });
        new Thread(task).start();
        return (String)task.get();
    }

    public String sendRequest1(final String serviceCode,final List<NameValuePair> pairs) throws InterruptedException, IOException,
            ExecutionException {
        FutureTask task = new FutureTask(
                new Callable()
                {
                    public String call() throws Exception {
                        ReqHead1 rh = new ReqHead1();
                        rh.setServiceCode(serviceCode);
                        ReqBase1 req = new ReqBase1(rh, pairs);
                        String url = MyApplication.instance.getServicePath1();
                        req.req();
                        String rspContext = req.getRspContext();
                        return rspContext;
                    }
                });
        new Thread(task).start();
        return (String)task.get();
    }

    /**
     * 注册
     * @return
     */
    public String uploadImage(final String serviceCode,final Map<String ,String> paramsIn,final Map<String,String> fileIn ) throws  Exception{
        FutureTask task = new FutureTask(
                new Callable()
                {
                    public String call() throws Exception {
                        ReqHead rh = new ReqHead();
                        rh.serviceCode=serviceCode;
                        ReqUploadFile req = new ReqUploadFile(rh, paramsIn,fileIn);
                        req.req();
                        String rspContext = req.getRspContext();
                        return rspContext;
                    }
                });
        new Thread(task).start();
        return (String)task.get();
    }


}
