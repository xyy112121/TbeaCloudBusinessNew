package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.CommdityListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListAllResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListHaveEvaluationResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListHaveFinishedResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListHaveOrderResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.MyTaskListWaitingResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.TaskStateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info.MyPictureListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info.SendOrdersPersonSelectResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info.TestDetailsResponseModel;
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

    /**
     * 未处理
     */
    public ResponseInfo getPendingInfo(String id) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricalcheckid", id));
        String result = sendRequest("TBEAYUN004006007000", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 已派单
     */
    public ResponseInfo getHaveAssignr(String id) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricalcheckid", id));
        String result = sendRequest("TBEAYUN004006011000", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 已完工
     */
    public ResponseInfo getHaveFinish(String id) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricalcheckid", id));
        String result = sendRequest("TBEAYUN004006012000", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 已上传
     */
    public ResponseInfo getHaveUpload(String id) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricalcheckid", id));
        String result = sendRequest("TBEAYUN004006014000", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 获得服务凭证对应的购买产品列表。
     */
    public CommdityListResponseModel getCommdityList(String code, int page, int pagesize) throws Exception {
        CommdityListResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("vouchercode", code));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004006008000", pairs);
        rspInfo = gson.fromJson(result, CommdityListResponseModel.class);
        return rspInfo;
    }

    /**
     * 获得服务凭证对应的购买产品列表。
     */
    public SendOrdersPersonSelectResponseModel getSendOrdersPerson() throws Exception {
        SendOrdersPersonSelectResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN004006009000", pairs);
        rspInfo = gson.fromJson(result, SendOrdersPersonSelectResponseModel.class);
        return rspInfo;
    }

    /**
     * 派单
     * @return
     * @throws Exception
     */
    public ResponseInfo sendOrder(String electricalCheckId,String electricianId) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("electricalcheckid", electricalCheckId));
        pairs.add(new BasicNameValuePair("electricianid", electricianId));
        String result = sendRequest("TBEAYUN004006010000", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 获得我的免费检测-­‐预约详情-­‐待评价-­‐检测详情
     */
    public TestDetailsResponseModel testDetails(String checkresultid) throws Exception {
        TestDetailsResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("checkresultid", checkresultid));
        String result = sendRequest("TBEAYUN004006013000", pairs);
        rspInfo = gson.fromJson(result, TestDetailsResponseModel.class);
        return rspInfo;
    }

    /**
     * 获得我的免费检测-­‐预约详情-­‐完工-­‐检测详情-­‐照片列表。
     */
    public MyPictureListResponseModel getPicList(String checkresultid) throws Exception {
        MyPictureListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("checkresultid", checkresultid));
        String result = sendRequest("TBEAYUN004006015000", pairs);
        model = gson.fromJson(result, MyPictureListResponseModel.class);
        return model;
    }



}
