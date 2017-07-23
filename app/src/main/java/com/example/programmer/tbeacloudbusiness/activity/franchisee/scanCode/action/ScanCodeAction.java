package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.PayStatusResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.PayeeTypeResponeModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeActivityStatusResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeCreateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeHistoryResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeNormsSelectReponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeRebateListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeStateListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeTypeSelectReponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.WithdrawDepositDateHistoryListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.WithdrawDepositDateInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.WithdrawDepositDateResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫码返利
 */

public class ScanCodeAction extends BaseAction {
    /**
     * 获取主页显示数据
     */
    public ScanCodeMainResponseModel getMainData() throws Exception {
        ScanCodeMainResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN004002001000", pairs);
        model = gson.fromJson(result, ScanCodeMainResponseModel.class);
        return model;
    }

    //获取选择型号
    public ScanCodeTypeSelectReponseModel getScanCodeTypeSelect() throws Exception {
        ScanCodeTypeSelectReponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("flag", "1"));
        String result = sendRequest("TBEAYUN001002001000", pairs);
        model = gson.fromJson(result, ScanCodeTypeSelectReponseModel.class);
        return model;
    }

    //获取选择规格
    public ScanCodeNormsSelectReponseModel getScanCodeNormsSelect() throws Exception {
        ScanCodeNormsSelectReponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("flag", "1"));
        String result = sendRequest("TBEAYUN001002002000", pairs);
        model = gson.fromJson(result, ScanCodeNormsSelectReponseModel.class);
        return model;
    }

    //生成二维码
    public ScanCodeCreateResponseModel createScanCode(String money, String number, String typeId, String normsId) throws Exception {
        ScanCodeCreateResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("money", money));
        pairs.add(new BasicNameValuePair("number", number));
        pairs.add(new BasicNameValuePair("commoditymodelid", typeId));
        pairs.add(new BasicNameValuePair("commodityspecificationid", normsId));//规格id
        String result = sendRequest("TBEAYUN004002006000", pairs);
        model = gson.fromJson(result, ScanCodeCreateResponseModel.class);
        return model;
    }

    //获取激活选择项
    public ScanCodeActivityStatusResponseModel getActivityStatus() throws Exception {
        ScanCodeActivityStatusResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN001003003000", pairs);
        model = gson.fromJson(result, ScanCodeActivityStatusResponseModel.class);
        return model;
    }

    /**
     * 历史记录
     * starttime   选择时间自定义时传此参数其它时候为空
     * endtime    选择时间自定义时传此参数其它时候为空
     * activitystatusid    选择激活项时传些参数
     * orderitem   此项注意(money,count,time)有三种参数，分别对应选择金额，选择数量，选择金额时间时，此项赋值成对应的选择项，当选择了时间的自定义项时，此项也有效果，即选择了时间的自定义，那么此项选择数量 或者金额时条件也可行，当选择的为时间项，但不是自定义项时，那么此项就为，时间，金额，数量 中的一个,此项与starttime,endtime，activitystatusid并行
     * order    对应选择时间，数量，金额时的升序(asc)，降序(desc)  时间的选择项包括(默认，正序(desc)，倒序(asc))  数量的选项包括(默认，数量从大到小，数量从小到大)  金额的选项包括(默认，金额从大到小，金额从小到大)
     * page
     * pagesize
     */
    public ScanCodeHistoryResponseModel getScanCodeHistoryList
    (String starttime, String endtime, String activitystatusid, String orderitem, String order, int page, int pagesize) throws Exception {
        ScanCodeHistoryResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("starttime", starttime));
        pairs.add(new BasicNameValuePair("endtime", endtime));
        pairs.add(new BasicNameValuePair("activitystatusid", activitystatusid));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));//规格id
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004002007000", pairs);
        model = gson.fromJson(result, ScanCodeHistoryResponseModel.class);
        return model;
    }

    /**
     * 获取以激活列表
     * qrcodegenerateid    前一个页面的id
     * orderitem    表示time
     * order
     * pagesize
     * page
     */
    public ScanCodeStateListResponseModel getScanCodeStateList
    (String qrcodegenerateid, String orderitem, String order, int page, int pagesize) throws Exception {
        ScanCodeStateListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("qrcodegenerateid", qrcodegenerateid));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));//规格id
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004002008000", pairs);
        model = gson.fromJson(result, ScanCodeStateListResponseModel.class);
        return model;
    }

    /**
     * 扫描详情
     */
    public ScanCodeInfoResponseModel getScanCodeInfo(String id) throws Exception {
        ScanCodeInfoResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("qrcodeactivityid", id));
        String result = sendRequest("TBEAYUN004002009000", pairs);
        model = gson.fromJson(result, ScanCodeInfoResponseModel.class);
        return model;
    }

    /**
     * 获取提现数据列表中的用户类型
     */
    public PayeeTypeResponeModel getPayeeType() throws Exception {
        PayeeTypeResponeModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN001003001000", pairs);
        model = gson.fromJson(result, PayeeTypeResponeModel.class);
        return model;
    }

    /**
     * 获取提现数据列表中的状态类型
     */
    public PayStatusResponseModel getPayStatus() throws Exception {
        PayStatusResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN001003002000", pairs);
        model = gson.fromJson(result, PayStatusResponseModel.class);
        return model;
    }

    /**
     * 获取提现数据列表
     * paystatusid  提现类型 通过TBEAYUN001003002000这个接口获取
     * payeetypeid  提现用户类型通过TBEAYUN001003001000获取
     * starttime     开始时间
     * endtime      结束时间
     * orderitem    排序项  time  money
     * order         desc  asc
     * pagesize
     * page
     */
    public WithdrawDepositDateResponseModel getWithdrawDepositDateList(String paystatusid, String payeetypeid,
                                                                       String starttime, String endtime, String orderitem, String order, int page, int pagesize) throws Exception {
        WithdrawDepositDateResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("paystatusid", paystatusid));
        pairs.add(new BasicNameValuePair("payeetypeid", payeetypeid));
        pairs.add(new BasicNameValuePair("starttime", starttime));
        pairs.add(new BasicNameValuePair("endtime", endtime));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004002004000", pairs);
        model = gson.fromJson(result, WithdrawDepositDateResponseModel.class);
        return model;
    }

    /**
     * 获取提现排名
     */
    public ScanCodeRebateListResponseModel getWithdrawDepositDateList(String zoneids, String orderitem, String order, int page, int pagesize) throws Exception {
        ScanCodeRebateListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("zoneids", zoneids));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004002002000", pairs);
        model = gson.fromJson(result, ScanCodeRebateListResponseModel.class);
        return model;
    }

    /**
     * 提现详情
     */
    public WithdrawDepositDateInfoResponseModel getWithdrawDepositDateInfo(String id) throws Exception {
        WithdrawDepositDateInfoResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("takemoneyid", id));
        String result = sendRequest("TBEAYUN004002005000", pairs);
        model = gson.fromJson(result, WithdrawDepositDateInfoResponseModel.class);
        return model;
    }

    /**
     * 获取提现历史
     */
    public WithdrawDepositDateHistoryListResponseModel getWithdrawDepositDateHistoryList(String personorcompany, String payeeid,String starttime, String endtime, String orderitem, String order, int page, int pagesize) throws Exception {
        WithdrawDepositDateHistoryListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("personorcompany", personorcompany));
        pairs.add(new BasicNameValuePair("payeeid", payeeid));
        pairs.add(new BasicNameValuePair("starttime", starttime));
        pairs.add(new BasicNameValuePair("endtime", endtime));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN004002003000", pairs);
        model = gson.fromJson(result, WithdrawDepositDateHistoryListResponseModel.class);
        return model;
    }

}
