package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListAllResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListHaveEvaluationResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListHaveFinishedResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListHaveOrderResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListWaitingResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.TaskStateResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/9/24.
 */

public class SubscribeAction extends BaseAction {
    /**
     * 获取预约状态
     */
    public TaskStateResponseModel getState() throws Exception {
        TaskStateResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN004006001000", pairs);
        rspInfo = gson.fromJson(result, TaskStateResponseModel.class);
        return rspInfo;
    }

    /**
     * 获得我发布的预约列表-­‐全部
     * OrderItem   排序项  预约编号: TaskCode 预约状态：TaskStatus 发布日期：Time (默认)
     * Order   排序 正序：ASC 倒序：Desc (默认)
     * Page   第几页
     * PageSize   页大小
     */
    public MyTaskListAllResponseModel getListAll(String orderItem, String order, int page, int pagesize) throws Exception {
        MyTaskListAllResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004006002000", pairs);
        rspInfo = gson.fromJson(result, MyTaskListAllResponseModel.class);
        return rspInfo;
    }

    /**
     * 获得我发布的预约列表-­‐待处理
     * OrderItem   排序项  预约编号: TaskCode 竞价状态：BidStatus 发布日期：Time (默认)
     * Order   排序 正序：ASC 倒序：Desc (默认)
     * Page   第几页
     * PageSize   页大小
     */
    public MyTaskListWaitingResponseModel getListWaiting(String orderItem, String order, int page, int pagesize) throws Exception {
        MyTaskListWaitingResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004006003000", pairs);
        rspInfo = gson.fromJson(result, MyTaskListWaitingResponseModel.class);
        return rspInfo;
    }

    /**
     * 获得我发布的预约列表-­‐已派单
     * OrderItem   排序项  预约编号: TaskCode 竞价状态：BidStatus 发布日期：Time (默认)
     * Order   排序 正序：ASC 倒序：Desc (默认)
     * Page   第几页
     * PageSize   页大小
     */
    public MyTaskListHaveOrderResponseModel getListHaveOrder(String orderItem, String order, int page, int pagesize) throws Exception {
        MyTaskListHaveOrderResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004006004000", pairs);
        rspInfo = gson.fromJson(result, MyTaskListHaveOrderResponseModel.class);
        return rspInfo;
    }

    /**
     * 获得我发布的预约列表-­‐已完工
     * OrderItem   排序项  预约编号: TaskCode 接单价格：fee 完工日期：Time (默认)
     * Order   排序 正序：ASC 倒序：Desc (默认)
     * Page   第几页
     * PageSize   页大小
     */
    public MyTaskListHaveEvaluationResponseModel getListHaveEvaluation(String orderItem, String order, int page, int pagesize) throws Exception {
        MyTaskListHaveEvaluationResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004006005000", pairs);
        rspInfo = gson.fromJson(result, MyTaskListHaveEvaluationResponseModel.class);
        return rspInfo;
    }


    /**
     * 获得我发布的预约列表-­‐已上传
     * OrderItem   排序项  预约编号: TaskCode 接单价格：fee 完工日期：Time (默认)
     * Order   排序 正序：ASC 倒序：Desc (默认)
     * Page   第几页
     * PageSize   页大小
     */
    public MyTaskListHaveFinishedResponseModel getListHaveFinished(String orderItem, String order, int page, int pagesize) throws Exception {
        MyTaskListHaveFinishedResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004006006000", pairs);
        rspInfo = gson.fromJson(result, MyTaskListHaveFinishedResponseModel.class);
        return rspInfo;
    }

    public ResponseInfo getPendingInfo(String id) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricalcheckid", id));
        String result = sendRequest("TBEAYUN004006007000", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }


}
