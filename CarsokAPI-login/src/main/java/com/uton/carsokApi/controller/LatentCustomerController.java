package com.uton.carsokApi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.*;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.constants.enums.ModuleEnums;
import com.uton.carsokApi.constants.enums.TaskTypeEnums;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.request.PageInfo;
import com.uton.carsokApi.controller.response.AllyResponse;
import com.uton.carsokApi.controller.response.CarsOkCustmoerResponse;
import com.uton.carsokApi.controller.response.IntentionCarsListResponse;
import com.uton.carsokApi.dao.AllyMapper;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.*;
import com.uton.carsokApi.service.core.IndexSendService;
import com.uton.carsokApi.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by Administrator on 2017/11/8.
 */
@Controller
@RequestMapping("/latent")
public class LatentCustomerController {

    @Autowired
    LatentCustomerService latentCustomerService;

    @Resource
    CacheService cacheService;

    @Autowired
    private ITaskFacade iTaskFacade;

    @Autowired
    private ICarsokCustomerService iCarsokCustomerService;

    @Autowired
    private ChildInfoService childInfoService;

    @Autowired
    private ICarsokAccountPowerService iCarsokAccountPowerService;

    @Autowired
    private ICarsokProductService iCarsokProductService;

    @Autowired
    private ICarsokCustomerTenureCarService iCarsokCustomerTenureCarService;

    @Autowired
    private FollowUpMsgService followUpMsgService;

    @Autowired
    private SaasTenureCustomerService saasTenureCustomerService;

    @Autowired
    IndexSendService indexSendService;
    private String name;

    @Autowired
    private ICarsokTenureTaskService iCarsokTenureTaskService;

    @Autowired
    private AllyMapper allyMapper;

    @RequestMapping("selectLatentList")
    @ResponseBody
    public BaseResult selectLatentList(HttpServletRequest request, @RequestBody LatentCustomerRequest vo) {
        try {
            BaseResult baseResult = BaseResult.success();
            Acount acount = cacheService.getAcountInfoFromCache(request);
            Map map = iCarsokCustomerService.selectLatentList(acount.getId(), acount.getSubPhone() == null ? acount.getAccount() : acount.getSubPhone(), vo);
            baseResult.setData(map);
            return baseResult;
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    /**
     * @author zhangdi
     * @date 2017/11/10 13:39
     * @Description: 修改保存/添加公用接口
     */
    @PostMapping(value = "updateLatentMsg")
    @ResponseBody
    public BaseResult updateLatentMsg(HttpServletRequest request, @Valid @RequestBody CarsOkCustomerRequest carsOkCustomerRequest, BindingResult bindingResult) {
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if (bindingResult.hasErrors()) {
                List<ObjectError> list = bindingResult.getAllErrors();
                for (ObjectError error : list) {
                    return BaseResult.fail("0002", error.getDefaultMessage());
                }
            }
            if (carsOkCustomerRequest.getId() == null) {
                CarsokCustomer ck = iCarsokCustomerService.selectByMobile(carsOkCustomerRequest.getMobile(), acount.getId());
                if (ck != null) {
                    return BaseResult.fail(ErrorCode.CUSMobileChkParentFail, ErrorCode.CUSMobileChkParentFailInfo);
                }
            }else {
                CarsokCustomer ck = iCarsokCustomerService.selectByMobile(carsOkCustomerRequest.getMobile(), acount.getId());
                if(ck != null){
                    int id1 = ck.getId();
                    int id2 = carsOkCustomerRequest.getId();
                    if(id1 != id2){
                        return BaseResult.fail(ErrorCode.CUSMobileChkParentFail, ErrorCode.CUSMobileChkParentFailInfo);
                    }
                }
            }

            if (carsOkCustomerRequest.getOutTime() != null) {
                if (carsOkCustomerRequest.getInTime().after(carsOkCustomerRequest.getOutTime())) {
                    return BaseResult.fail("0040", "离店时间应晚于进店时间！");
                }
            }
            carsOkCustomerRequest.setAccountId(acount.getId());
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                if (carsOkCustomerRequest.getId()==null ) {
                    carsOkCustomerRequest.setChildId(childAccount.getId());
                }
                boolean flag = iCarsokCustomerService.updateLatentMsg(carsOkCustomerRequest, acount.getId().toString(), childAccount.getId().toString());
            } else {
                boolean flag = iCarsokCustomerService.updateLatentMsg(carsOkCustomerRequest, acount.getId().toString(), "0");
            }

            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    /**
     * @author zhangdi
     * @date 2017/11/10 13:39
     * @Description: 详情页
     */
    @PostMapping("selectCustMsg")
    @ResponseBody
    public BaseResult selectCustMsg(HttpServletRequest request, @RequestBody SelectCustMsgRequest s) {

        try {

            if (s.getId() == null) {
                return BaseResult.fail(ErrorCode.NullPointerExceptionRetCode, ErrorCode.NullPointerExceptionRetInfo);
            }

            CarsOkCustmoerResponse carsOkCustmoerResponse = iCarsokCustomerService.selectCustMsg(s.getId());
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                List<CarsokAccountPower> cp = iCarsokAccountPowerService.selectByChildID(childAccount.getId());
                for (CarsokAccountPower carsokAccountPower : cp) {

                    carsOkCustmoerResponse.getPower().add(carsokAccountPower.getPowerName());
                }
            } else {
                carsOkCustmoerResponse.getPower().add("qkjlgl");
                carsOkCustmoerResponse.getPower().add("qkkfdp");
                carsOkCustmoerResponse.getPower().add("qkyxgw");
            }
            return BaseResult.success(carsOkCustmoerResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }


    }

  /*  *//**
     * @author zhangdi
     * @date 2017/11/10 13:38
     * @Description: 选择车辆保存
     *//*

    @PostMapping("updateCustCarMsg")
    @ResponseBody
    public BaseResult updateCustCarMsg(HttpServletRequest request, @RequestBody UpdateCustCarMsgRequest u) {


        try {

            Acount acount = cacheService.getAcountInfoFromCache(request);
            return iCarsokCustomerTenureCarService.updateCustCarMsg(acount, u);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.exception(e.getMessage());
        }

    }*/

    /**
     * @author zhangdi
     * @date 2017/11/15 10:07
     * @Description: 选择车辆保存
     */
    @PostMapping("updateCustCarMsg")
    @ResponseBody
    public BaseResult updateCustCarMsg(HttpServletRequest request, @RequestBody UpdateCustCarMsgRequest u) {


        try {
            //将客户所有任务结束
            Acount acount = cacheService.getAcountInfoFromCache(request);
            List<CarsokTenureTask> carsokTenureTasks = iCarsokCustomerService.selecAllTask(u.getCustId());
            for (CarsokTenureTask carsokTenureTask : carsokTenureTasks) {
                if (!StringUtil.isEmpty(acount.getSubPhone())) {
                    ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                    iTaskFacade.finishTaskById(carsokTenureTask.getId(), null, childAccount.getId().toString());

                } else {
                    iTaskFacade.finishTaskById(carsokTenureTask.getId(), acount.getId().toString(), null);
                }
            }
            for (Integer productId : u.getProductId()) {

                if (!StringUtil.isEmpty(acount.getSubPhone())) {
                    ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                    iTaskFacade.finishTaskById(0, null, childAccount.getId().toString());
                    iCarsokProductService.updateSaledPeople(productId, acount.getSubPhone());
                } else {
                    iTaskFacade.finishTaskById(0, acount.getId().toString(), null);
                    iCarsokProductService.updateSaledPeople(productId, acount.getAccount());
                }

                CarsokProduct carsokProduct = iCarsokProductService.selectByProductNo(productId);
                CarsokCustomer carsokCustomer = iCarsokCustomerService.selectById(u.getCustId());
                if (carsokCustomer == null || carsokProduct == null) {
                    return BaseResult.fail(ErrorCode.NullPointerExceptionRetCode, ErrorCode.NullPointerExceptionRetInfo);
                }
                CarsokCustomerTenureCar carsokCustomerTenureCar = new CarsokCustomerTenureCar();
                carsokCustomerTenureCar.setAccountId(acount.getId());
//                if (!StringUtil.isEmpty(acount.getSubPhone())) {
//                    ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
//                    carsokCustomerTenureCar.setChildId(childAccount.getId());
//                } else {
//                    carsokCustomerTenureCar.setChildId(0);
//                }
                carsokCustomerTenureCar.setChildId(carsokCustomer.getChildId());
                carsokCustomerTenureCar.setCreateTime(new Date());
                carsokCustomerTenureCar.setSaleTime(new Date());
                carsokCustomerTenureCar.setUpdateTime(new Date());
                carsokCustomerTenureCar.setTenureCarname(carsokProduct.getProductName());
                carsokCustomerTenureCar.setTenureVin(carsokProduct.getVin());
                //待定
          /*  if (carsokProduct.getSaledType() != null) {
                carsokCustomerTenureCar.setTenureCartype(carsokProduct.getSaledType()==1 ?"全款":"贷款");
            }*/

                carsokCustomerTenureCar.setTenureCarprice(carsokProduct.getSaledPrice());
                carsokCustomerTenureCar.setSalePeople(carsokProduct.getSaledPeople());
                carsokCustomerTenureCar.setProductId(productId);
                carsokCustomerTenureCar.setCustomerId(u.getCustId());
                carsokCustomerTenureCar.setIsDrivingTest(carsokCustomer.getIsDrivingTest());
                if (carsokProduct.getBusinessIf() != null) {
                    carsokCustomerTenureCar.setIsBussiness(carsokProduct.getBusinessIf() == 1 ? "是" : "否");
                }
                iCarsokCustomerTenureCarService.insert(carsokCustomerTenureCar);
                //添加完后将客户级别改为已成交
                CarsokCustomer cc = new CarsokCustomer();
                cc.setId(u.getCustId());
                cc.setLevel("D 已成交");
                cc.setAccountId(carsokCustomer.getAccountId());
                cc.setChildId(carsokCustomer.getChildId());
                cc.updateById();
                //将车辆改为已成交
                CarsokProduct cp = new CarsokProduct();
                cp.setId(productId);
                cp.setSaleStatus(1);
                cp.updateById();
                saasTenureCustomerService.runSingleTask(carsokCustomerTenureCar, null, carsokCustomer, null);
                //推送至SearchCenter
                indexSendService.SingleInsertOrUpdate(productId, false);

            }
            return BaseResult.success();

        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }

    }

    //    存跟进信息
    @PostMapping("followUpMsg")
    @ResponseBody
    public BaseResult updateFlowMsg(HttpServletRequest request, @RequestBody FollowUpMsgRequest u) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        u.setAccount_id(String.valueOf(acount==null?"0":acount.getId()));

        try {
//          查询taskid
            List<String> taskidList =new ArrayList<String>();
            if(StringUtil.isEmpty(u.getTaskId())){
                taskidList=followUpMsgService.selectTaskid(u.getCustId());
            }else{
                taskidList.add(u.getTaskId());
            }
//          经理跟进
            if (u.getFlag().equals("1")) {
                CarsokCustomer carsokcustomer=new CarsokCustomer();
                String change="";
                if(carsokcustomer.selectById(u.getCustId())!=null||u.getLevel()!=null){
                    String level=carsokcustomer.selectById(u.getCustId()).getLevel();
                    if(!level.equals(u.getLevel())){
                        change=level+" → "+u.getLevel()+"\n";
                    }
                }else if(u.getLevel()==null){
                    u.setLevel("N 24小时内回访");
                }
//          取缓存里子id
                u.setChild_id(String.valueOf("0"));
                if (!StringUtil.isEmpty(acount.getSubPhone())) {
                    ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                    u.setChild_id(String.valueOf(childAccount==null?"0":childAccount.getId()));
                }
                CarsokCustomer carsokCustomer = new CarsokCustomer();
                carsokCustomer = carsokCustomer.selectById(u.getCustId());
                //判断经理跟进时是否更改了客户级别
                if (u.getLevel().equals(carsokCustomer.getLevel())) {
                    //没有更改, 只插入跟进信息, 不处理任务
                } else {
                    //客户级别有修改, 判断是否变为"D 已成交"\
                    if ("D 已成交".equals(u.getLevel())) {
                        //是已成交, 只finish当前任务
                        Iterator<String> it = taskidList.iterator();
                        while(it.hasNext()) {
                            String selectTaskid=it.next();
                            if (Integer.valueOf(selectTaskid) != null && Integer.valueOf(selectTaskid) != 0) {
                                if (Integer.valueOf(u.getChild_id()) == 0) {
                                    iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), u.getAccount_id(), null);
                                } else {
                                    iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), null, u.getChild_id());
                                }
                            }
                        }
                    } else {
                        //不是已成交
                        //先finish现任务
                        Iterator<String> it = taskidList.iterator();
                        while(it.hasNext()){
                            String selectTaskid=it.next();
                            if (Integer.valueOf(selectTaskid) != null && Integer.valueOf(selectTaskid) != 0) {
                                if (Integer.valueOf(u.getChild_id()) == 0) {
                                    iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), u.getAccount_id(), null);
                                } else {
                                    iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), null, u.getChild_id());
                                }
                            }
                        }

                        //再新建下一任务
                        TaskInitParam taskInitParam = new TaskInitParam();
                        taskInitParam.setBusiness_id(u.getCustId());
                        taskInitParam.setModule(ModuleEnums.potentialcustomer);
                        Calendar c = Calendar.getInstance();
                        c.setTime(new Date());//设置初始日期;
                        if ("F 战败".equals(u.getLevel()) == false || "F0 战败待确认".equals(u.getLevel()) == false) {
                            switch (u.getLevel()) {
                                case "N 24小时内回访":
                                    taskInitParam.setType(TaskTypeEnums.oneday_buy);
                                    c.add(Calendar.DAY_OF_MONTH, 1);
                                    taskInitParam.setScheduled_time(c.getTime());
                                    break;
                                case "H 3天内购买":
                                    taskInitParam.setType(TaskTypeEnums.threedays_buy);
                                    c.add(Calendar.DAY_OF_MONTH, 3);
                                    taskInitParam.setScheduled_time(c.getTime());

                                    break;
                                case "A 7天内购买":
                                    taskInitParam.setType(TaskTypeEnums.sevendays_buy);
                                    c.add(Calendar.DAY_OF_MONTH, 7);
                                    taskInitParam.setScheduled_time(c.getTime());
                                    break;
                                case "B 15天内购买":
                                    taskInitParam.setType(TaskTypeEnums.fifteendays_buy);
                                    c.add(Calendar.DAY_OF_MONTH, 15);
                                    taskInitParam.setScheduled_time(c.getTime());
                                    break;
                                case "C 30天内购买":
                                    taskInitParam.setType(TaskTypeEnums.onemonth_buy);
                                    c.add(Calendar.DAY_OF_MONTH, 30);
                                    taskInitParam.setScheduled_time(c.getTime());
                                    break;
                                case "F 战败":
                                    taskInitParam.setType(TaskTypeEnums.defeat);
                                    break;
                                case "F0 战败待确认":
                                    taskInitParam.setType(TaskTypeEnums.defeat_confirm);
                                    break;
                            }
                        }
                        Map map = new HashMap();
                        map.put("level", u.getLevel());
                        map.put("account_id", u.getAccount_id());
//                        查询登录人的子账号
                        map.put("child_id", String.valueOf(carsokCustomer.getChildId()));
                        taskInitParam.setExtraFields(map);
                        iTaskFacade.createTask(taskInitParam);
                    }
                }
                if(acount.getSubPhone()!=null){
                    ChildAccount ca = cacheService.getSubAcountInfoFromCache(request);
                    if(ca == null){
                        return BaseResult.fail("0008","登录过期，请重新登录");
                    }
                    u.setChild_id(ca.getId().toString());
                }
//          修改carsok_customer_flowmsg表和增添carsok_customer表
                followUpMsgService.insertNewMsg(u);
//          添加carsok_record表
                followUpMsgService.insertCarsokRecord(u,change);

                return BaseResult.success();
            } else {
//          顾问跟进
                CarsokCustomer carsokcustomer=new CarsokCustomer();
                String change="";
                if(carsokcustomer.selectById(u.getCustId())!=null||u.getLevel()!=null){
                    String level=carsokcustomer.selectById(u.getCustId()).getLevel();
                    if(!level.equals(u.getLevel())){
                        change=level+" → "+u.getLevel()+"\n";
                    }
                }else if(u.getLevel()==null){
                    u.setLevel("N 24小时内回访");
                }
//          取缓存里子id
                u.setChild_id(String.valueOf("0"));
                if (!StringUtil.isEmpty(acount.getSubPhone())) {
                    ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                    u.setChild_id(String.valueOf(childAccount==null?"0":childAccount.getId()));
                }
                //顾问跟进不需要判断是否修改了客户级别, 只要有跟进, 就结束现任务, 重建新任务
                //判断客户级别是否修改为"D 已成交"
                if ("D 已成交".equals(u.getLevel())) {
                    //已成交, 只finish现任务, 不重建新任务
                    Iterator<String> it = taskidList.iterator();
                    while(it.hasNext()){
                        String selectTaskid=it.next();
                        if (Integer.valueOf(selectTaskid) != null && Integer.valueOf(selectTaskid) != 0) {
                            if (Integer.valueOf(u.getChild_id()) == 0) {
                                iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), u.getAccount_id(), null);
                            } else {
                                iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), null, u.getChild_id());
                            }
                        }
                    }
                } else {
                    //先结束现任务
                    Iterator<String> it = taskidList.iterator();
                    while(it.hasNext()){
                        String selectTaskid=it.next();
                        if (Integer.valueOf(selectTaskid) != null && Integer.valueOf(selectTaskid) != 0) {
                            if (Integer.valueOf(u.getChild_id()) == 0) {
                                iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), u.getAccount_id(), null);
                            } else {
                                iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), null, u.getChild_id());
                            }
                        }
                    }
                    //再重建新任务
                    TaskInitParam taskInitParam = new TaskInitParam();
                    taskInitParam.setBusiness_id(u.getCustId());
                    taskInitParam.setModule(ModuleEnums.potentialcustomer);
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());//设置初始日期;
                    if ("F 战败".equals(u.getLevel()) == false || "F0 战败待确认".equals(u.getLevel()) == false) {
                        switch (u.getLevel()) {
                            case "N 24小时内回访":
                                taskInitParam.setType(TaskTypeEnums.oneday_buy);
                                c.add(Calendar.DAY_OF_MONTH, 1);
                                taskInitParam.setScheduled_time(c.getTime());
                                break;
                            case "H 3天内购买":
                                taskInitParam.setType(TaskTypeEnums.threedays_buy);
                                c.add(Calendar.DAY_OF_MONTH, 3);
                                taskInitParam.setScheduled_time(c.getTime());

                                break;
                            case "A 7天内购买":
                                taskInitParam.setType(TaskTypeEnums.sevendays_buy);
                                c.add(Calendar.DAY_OF_MONTH, 7);
                                taskInitParam.setScheduled_time(c.getTime());
                                break;
                            case "B 15天内购买":
                                taskInitParam.setType(TaskTypeEnums.fifteendays_buy);
                                c.add(Calendar.DAY_OF_MONTH, 15);
                                taskInitParam.setScheduled_time(c.getTime());
                                break;
                            case "C 30天内购买":
                                taskInitParam.setType(TaskTypeEnums.onemonth_buy);
                                c.add(Calendar.DAY_OF_MONTH, 30);
                                taskInitParam.setScheduled_time(c.getTime());
                                break;
                            case "F 战败":
                                taskInitParam.setType(TaskTypeEnums.defeat);
                                break;
                            case "F0 战败待确认":
                                taskInitParam.setType(TaskTypeEnums.defeat_confirm);
                                break;
                        }
                    }
                    Map map = new HashMap();
                    map.put("level", u.getLevel());
                    map.put("account_id", u.getAccount_id());
//                    查询登录人的子账号
                    CarsokCustomer carsokCustomer = new CarsokCustomer();
                    carsokCustomer = carsokCustomer.selectById(u.getCustId());
                    map.put("child_id", String.valueOf(carsokCustomer.getChildId()));
                    taskInitParam.setExtraFields(map);
                    iTaskFacade.createTask(taskInitParam);
                }

////          添加carsok_tenure_task表
//                if(u.getLevel().equals("D 已成交")){
////                改task表task_status字段为finish
//                    if(u.getTaskId()==null || "0".equals(u.getTaskId())){
//                        followUpMsgService.insertTaskTable(u);
//                    }else {
//                        CarsokTenureTask carsokTenureTask = new CarsokTenureTask();
//                        carsokTenureTask.setId(Integer.valueOf(u.getTaskId()));
//                        carsokTenureTask.setTaskStatus("finish");
//                        carsokTenureTask.updateById();
//                    }
//                }else{
//                    followUpMsgService.insertTaskTable(u);
//                }

                if(acount.getSubPhone()!=null){
                    ChildAccount ca = cacheService.getSubAcountInfoFromCache(request);
                    if(ca == null){
                        return BaseResult.fail("0008","登录过期，请重新登录");
                    }
                    u.setChild_id(ca.getId().toString());
                }
//          添加carsok_customer_flowmsg表和改carsok_customer表
                followUpMsgService.insertNewMsg(u);
//          添加carsok_record表
                followUpMsgService.insertCarsokRecord(u,change);

                return BaseResult.success();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    //  跟进页面激活按键
    @PostMapping("activationLoan")
    @ResponseBody
    public BaseResult activationLoan(HttpServletRequest request, @RequestBody FollowUpMsgRequest u) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        try {
            CarsokCustomer carsokcustomer=new CarsokCustomer();
            String change="";
            if(carsokcustomer.selectById(u.getCustId())!=null){
                String level=carsokcustomer.selectById(u.getCustId()).getLevel();
                if(!level.equals("N 24小时内回访")){
                    change=level+" → "+"N 24小时内回访"+"\n";
                }
            }
            u.setLevel("N 24小时内回访");
            u.setChild_id(String.valueOf("0"));
//          查询taskid
            List<String> taskidList =new ArrayList<String>();
            if(StringUtil.isEmpty(u.getTaskId())){
                taskidList=followUpMsgService.selectTaskid(u.getCustId());
            }else{
                taskidList.add(u.getTaskId());
            }
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                u.setChild_id(String.valueOf(childAccount==null?"0":childAccount.getId()));
                Iterator<String> it = taskidList.iterator();
                while(it.hasNext()){
                    String selectTaskid=it.next();
                    iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), null, String.valueOf(childAccount.getId().toString()));
                }
            } else {
                Iterator<String> it = taskidList.iterator();
                while(it.hasNext()){
                    String selectTaskid=it.next();
                    iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), String.valueOf(acount.getId().toString()), null);
                }
            }
            u.setAccount_id(String.valueOf(acount.getId()));

//          添加carsok_customer_flowmsg表和carsok_customer表
            followUpMsgService.insertNewMsg(u);
//          添加carsok_tenure_task表
//            CarsokTenureTask carsokTenureTask=new CarsokTenureTask();
//            carsokTenureTask.setId(Integer.valueOf(u.getTaskId()));
//            carsokTenureTask.setTaskStatus("finish");
//            carsokTenureTask.updateById();
            followUpMsgService.insertTaskTable(u);
//          添加carsok_record表
            followUpMsgService.insertCarsokRecord(u,change);
            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }


    //  跟进页面确认战败按键
    @PostMapping("followUpConfirmFailure")
    @ResponseBody
    public BaseResult followUpConfirmFailure(HttpServletRequest request, @RequestBody FollowUpMsgRequest u) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        try {
            CarsokCustomer carsokcustomer=new CarsokCustomer();
            String change="";
            if(carsokcustomer.selectById(u.getCustId())!=null){
                String level=carsokcustomer.selectById(u.getCustId()).getLevel();
                if(!level.equals("F 战败")){
                    change=level+" → "+"F 战败"+"\n";
                }
            }
            u.setLevel("F 战败");
            u.setChild_id(String.valueOf("0"));
//          查询taskid
            List<String> taskidList =new ArrayList<String>();
            if(StringUtil.isEmpty(u.getTaskId())){
                taskidList=followUpMsgService.selectTaskid(u.getCustId());
            }else{
                taskidList.add(u.getTaskId());
            }
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                u.setChild_id(String.valueOf(childAccount==null?"0":childAccount.getId()));
                Iterator<String> it = taskidList.iterator();
                while(it.hasNext()){
                    String selectTaskid=it.next();
                    iTaskFacade.finishTaskById(Integer.parseInt(selectTaskid), null, String.valueOf(childAccount.getId().toString()));
                }
            } else {
                Iterator<String> it = taskidList.iterator();
                while(it.hasNext()){
                    String selectTaskid=it.next();
                    iTaskFacade.finishTaskById(Integer.parseInt(selectTaskid), String.valueOf(acount.getId().toString()), null);
                }
            }
            u.setAccount_id(String.valueOf(acount==null?"0":acount.getId()));

//          添加carsok_customer_flowmsg表和carsok_customer表
            followUpMsgService.insertNewMsg(u);
//          添加carsok_tenure_task表
//            CarsokTenureTask carsokTenureTask=new CarsokTenureTask();
//            carsokTenureTask.setId(Integer.valueOf(u.getTaskId()));
//            carsokTenureTask.setTaskStatus("finish");
//            carsokTenureTask.updateById();
            followUpMsgService.insertTaskTable(u);
//          添加carsok_record表
            followUpMsgService.insertCarsokRecord(u,change);
            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    //    跟进页面重新分配按键
    @PostMapping("assignLoan")
    @ResponseBody
    public BaseResult assignLoan(HttpServletRequest request) {
        Acount acount = cacheService.getAcountInfoFromCache(request);

        try {
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                List<CarsokChildAccount> carsokChildAccounts = new CarsokChildAccount().selectList(new EntityWrapper().ne("child_account_mobile", acount.getSubPhone())
                        .eq("account_phone", acount.getAccount()));
                List<CarsokChildAccount> list=followUpMsgService.selectPowerByChildId(carsokChildAccounts);
                return BaseResult.success(list);
            } else {
                List<CarsokChildAccount> carsokChildAccounts = new CarsokChildAccount().selectList(new EntityWrapper().eq("account_phone", acount.getAccount()));
                List<CarsokChildAccount> list=followUpMsgService.selectPowerByChildId(carsokChildAccounts);
                return BaseResult.success(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    //      选择待分配销售顾问页面确定按键
    @PostMapping("FollowUpDistribution")
    @ResponseBody
    public BaseResult FollowUpDistribution(HttpServletRequest request, @RequestBody FollowUpDistributionRequest u) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        ChildAccount childAccount = new ChildAccount();
        try {
            u.setChild_id(String.valueOf("0"));
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                u.setChild_id(String.valueOf(childAccount==null?"0":childAccount.getId()));
                u.setAccount_id(String.valueOf(acount==null?"0":acount.getId()));
            } else {
                u.setAccount_id(String.valueOf(acount==null?"0":acount.getId()));
//                u.setChild_id("0");
            }

            String Lo="";
            if(!StringUtil.isEmpty(u.getLevel())){
                Lo=u.getLevel();
            }

            //添加task表
            int flag=followUpMsgService.followUpDistribution(acount, u);

            String Lev="";
            if(!StringUtil.isEmpty(u.getLevel())){
                Lev=u.getLevel();
            }

            //向CarsokRecord表中添加一条数据
            CarsokRecord carsokRecord = new CarsokRecord();
            carsokRecord.setModel("1");

//            通过childId查询名字
            CarsokChildAccount CarsokChildAccount = new CarsokChildAccount();
            name = CarsokChildAccount.selectById(u.getId())==null?"原账号":CarsokChildAccount.selectById(u.getId()).getChildAccountName();
//            通过custId查询有没有childId
            CarsokCustomer carsokCustomer2 = new CarsokCustomer();
//            查客户level
            String Le="";
            if(carsokCustomer2.selectById(u.getCustId())!=null){
                Le=carsokCustomer2.selectById(u.getCustId()).getLevel();
            }

            if(!StringUtil.isEmpty(Le)){
                u.setLevel(Le);
            }
            if(flag==1){
                u.setLevel("N 24小时内回访");
            }
            String Mes="";
            if(flag==1){
                if(!StringUtil.isEmpty(Le)){
                    Mes=Le+" → N 24小时内回访"+"\n";
                }
                if(!StringUtil.isEmpty(Lo)){
                    Mes=Lo+" → N 24小时内回访"+"\n";
                }
            }

            if(carsokCustomer2.selectById(u.getCustId())!=null){
                if ((carsokCustomer2.selectById(u.getCustId()).getChildId()) == 0) {
                    if(!StringUtil.isEmpty(u.getCustomerFlowMessage())){
                        Mes+="客户归属由主账号改成" + name + "\n" + u.getCustomerFlowMessage();
                    }else{
                        Mes+="客户归属由主账号改成" + name;
                    }
                } else {
                    CarsokCustomer carsokCustomer3 = new CarsokCustomer();
                    carsokCustomer3.setId(Integer.valueOf(u.getCustId()));
                    int aaa = carsokCustomer3.selectById().getChildId();
                    String name2 = CarsokChildAccount.selectById(aaa)==null?"原账号":CarsokChildAccount.selectById(aaa).getChildAccountName();
                    if(!StringUtil.isEmpty(u.getCustomerFlowMessage())){
                        Mes+="客户归属由主账号改成" + name + "\n" + u.getCustomerFlowMessage();
                    }else{
                        Mes+="客户归属由主账号改成" + name;
                    }
                }
            }
            carsokRecord.setMessage(Mes);
            if(flag==1){
                carsokRecord.setType("N 24小时内回访");
            }
            if(!StringUtil.isEmpty(Le)){
                carsokRecord.setType(Le);
            }
            if(!StringUtil.isEmpty(Lev)){
                carsokRecord.setType(Lev);
            }
            carsokRecord.setAccountId(acount.getId());
            carsokRecord.setChildId(childAccount.getId() == null ? 0 : childAccount.getId());
            carsokRecord.setCreateTime(new Date());
            carsokRecord.setOutId(Integer.parseInt(u.getCustId()));
            carsokRecord.insert();

//            修改carsok_customer表level
            if(Lo.equals("F 战败")||Lo.equals("F0 战败待确认")){
                u.setLevel("N 24小时内回访");
            }else{
                u.setLevel(Lo);
            }
            if(!StringUtil.isEmpty(u.getLevel())){
                followUpMsgService.insertCustomerFlowMsg(u);
            }else{
                if(Le.equals("F0 战败待确认")||Le.equals("F 战败")){
                    u.setLevel("N 24小时内回访");
                    followUpMsgService.insertCustomerFlowMsg(u);
                }else{
                    u.setLevel(Le);
                    followUpMsgService.insertCustomerFlowMsg(u);
                }
            }


            //向CarsokCustomerFlowmsg 表中添加一条数据
            CarsokCustomerFlowmsg carsokCustomerFlowmsg = new CarsokCustomerFlowmsg();
            carsokCustomerFlowmsg.setCreateTime(new Date());
            carsokCustomerFlowmsg.setCustomerFlowMessage(u.getCustomerFlowMessage());
            carsokCustomerFlowmsg.setCustomerId(Integer.parseInt(u.getCustId()));
            carsokCustomerFlowmsg.setAccountId(acount.getId());
            carsokCustomerFlowmsg.setChildId(childAccount.getId() == null ? 0 : childAccount.getId());
            carsokCustomerFlowmsg.insert();

            //修改customer childid；
            CarsokCustomer carsokCustomer = new CarsokCustomer();
            carsokCustomer.setId(Integer.parseInt(u.getCustId()));
            carsokCustomer.setChildId(Integer.parseInt(u.getId()));
            carsokCustomer.setAccountId(acount.getId());
            carsokCustomer.updateById();

            //查出business_id是customerID的生日关怀保有任务
            EntityWrapper<CarsokTenureTask> entityWrapper = new EntityWrapper<CarsokTenureTask>();
            entityWrapper.eq("business_id", u.getCustId()).like("type", "birthday_solicitude").in("task_status", new String[]{"ready", "delay"});
            com.github.pagehelper.PageInfo<CarsokTenureTask> taskBirthday = iTaskFacade.queryTaskByEntityWrapper(0, 0, entityWrapper);
            //循环, 更新任务的extra_fields
            for (CarsokTenureTask task : taskBirthday.getList()){
                Map oldmap = JSON.parseObject(task.getExtraFields());
                oldmap.put("accountId",acount.getId());
                oldmap.put("childId",u.getId());
                iTaskFacade.updateExtraData(task.getId(), oldmap, null, null);
            }
            //查出这个客户的所有车
            List<CarsokCustomerTenureCar> carList = iCarsokCustomerTenureCarService.selectList(new EntityWrapper<CarsokCustomerTenureCar>().eq("customer_id", u.getCustId()));
            //循环, 遍历出business_id是carID的所有保有任务, 同时更新车的acountId和childID
            for (CarsokCustomerTenureCar car : carList){
                //更新车的acountId和childID
                car.setAccountId(acount.getId());
                car.setChildId(Integer.valueOf(u.getId()));
                iCarsokCustomerTenureCarService.update(car, new EntityWrapper().eq("id", car.getId()));
                EntityWrapper<CarsokTenureTask> ew = new EntityWrapper<CarsokTenureTask>();
                ew.eq("business_id", car.getId()).like("module", "retaincustomer").in("task_status", new String[]{"ready", "delay"});
                com.github.pagehelper.PageInfo<CarsokTenureTask> retainTaskList = iTaskFacade.queryTaskByEntityWrapper(0, 0, ew);
                //循环任务list, 更新任务的extra_fields
                for (CarsokTenureTask task : retainTaskList.getList()){
                    Map oldmap = JSON.parseObject(task.getExtraFields());
                    oldmap.put("accountId",acount.getId());
                    oldmap.put("childId",Integer.valueOf(u.getId()));
                    iTaskFacade.updateExtraData(task.getId(), oldmap, null, null);
                }
            }

            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    //      客服跟进确定按键
    @PostMapping("CusFollowInfo")
    @ResponseBody
    public BaseResult CusFollowInfo(HttpServletRequest request, @RequestBody AssignLoanRequest u) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        try {
            u.setChild_id(String.valueOf("0"));
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                u.setChild_id(String.valueOf(childAccount==null?"0":childAccount.getId()));
            }

            u.setAccount_id(String.valueOf(acount.getId()));

//           添加CarsokCustomerFlowmsg表
            CarsokCustomerFlowmsg carsokCustomerFlowmsg = new CarsokCustomerFlowmsg();
            carsokCustomerFlowmsg.setCreateTime(new Date());
            carsokCustomerFlowmsg.setCustomerFlowMessage(u.getCustomerFlowMessage());
            carsokCustomerFlowmsg.setCustomerId(Integer.parseInt(u.getCustId()));
            carsokCustomerFlowmsg.setAccountId(Integer.parseInt(u.getAccount_id()));
            carsokCustomerFlowmsg.setChildId(Integer.parseInt(u.getChild_id()));
            Map<String, String> map = new HashMap<String, String>();
            map.put("是否热情", u.getEnthusiasm());
            map.put("是否专心", u.getConcentrate());
            map.put("是否24小时回访", u.getVisit());
            JSONObject jsonObject = JSONObject.fromObject(map);
            carsokCustomerFlowmsg.setCusServiceFollow(jsonObject.toString());
            carsokCustomerFlowmsg.insert();

//            添加record表
            CarsokRecord carsokRecord = new CarsokRecord();
            carsokRecord.setModel("1");
            carsokRecord.setMessage(u.getCustomerFlowMessage()+"\n"+"是否热情："+u.getEnthusiasm()+",是否专心："+u.getConcentrate()+",是否24小时回访："+u.getVisit());
            carsokRecord.setAccountId(Integer.valueOf(u.getAccount_id()));
            carsokRecord.setChildId(Integer.valueOf(u.getChild_id()));
            carsokRecord.setCreateTime(new Date());
            carsokRecord.setOutId(Integer.parseInt(u.getCustId()));
            carsokRecord.insert();

            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    @RequestMapping("saledAscription")
    @ResponseBody
    public BaseResult sdwdsdwd(HttpServletRequest request) {
        try {
            BaseResult baseResult = BaseResult.success();
            Acount acount = cacheService.getAcountInfoFromCache(request);
            return iCarsokCustomerService.selectAllSaledPeople(acount.getMobile());
        } catch (Exception e) {
            return BaseResult.fail("0001", "系统异常");
        }
    }

    @PostMapping("rePurchase")
    @ResponseBody
    public BaseResult rePurchase(HttpServletRequest request, @RequestBody RePurchaseFollowUpMsgRequest vo) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        try {
            int childId = 0;
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                childId = childInfoService.selectByChildAccount(acount.getSubPhone()).getId();
            }
            iCarsokCustomerService.customerRePurchase(acount.getId(), childId, vo.getLevel(), vo.getCustomerFlowMessage(), vo.getCustId());
            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    /**
     * @author zhangdi
     * @date 2017/12/4 18:03
     * @Description:意向车型库存列表
     */

    @PostMapping("intentionCarsList")
    @ResponseBody
    public BaseResult intentionCarsList(HttpServletRequest request ,@RequestBody  IntentionCarsListRequest intentionCarsListRequest) {

        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            com.github.pagehelper.Page<IntentionCarsListResponse> intentionCarsListResponses =iCarsokProductService.getIntentionCarsList(acount.getId().toString(),intentionCarsListRequest.getPageSize(),intentionCarsListRequest.getPageNum());
            return BaseResult.success(intentionCarsListResponses);
        } catch (Exception e) {
            e.printStackTrace();
            return  BaseResult.fail("0001", "系统异常");
        }
    }


    @PostMapping("intentionCarsList_271")
    @ResponseBody
    public BaseResult intentionCarsList_271(HttpServletRequest request ,@RequestBody  IntentionCarsListRequest intentionCarsListRequest) {
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            Map<String,Object> map = new HashMap<>();
            if(acount == null){
                return  BaseResult.fail("0003", "帐号异常");
            }
            com.github.pagehelper.Page<IntentionCarsListResponse> intentionCarsListResponses =iCarsokProductService.getIntentionCarsList_271(acount,intentionCarsListRequest);
            map.put("carList",intentionCarsListResponses);
            intentionCarsListRequest.setCarType(0);
            map.put("kcCount",iCarsokProductService.getIntentionCarsList_271(acount,intentionCarsListRequest).getTotal());
            intentionCarsListRequest.setCarType(1);
            map.put("lmCount",iCarsokProductService.getIntentionCarsList_271(acount,intentionCarsListRequest).getTotal());
            return BaseResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return  BaseResult.fail("0001", "系统异常");
        }
    }

    //    潜在客户购车自定义
    @PostMapping("PosCusPurchase")
    @ResponseBody
    public BaseResult PosCusPurchase(HttpServletRequest request ,@RequestBody PosCusRequest u){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            u.setChild_id(0);
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                u.setChild_id(childAccount.getId());
            }
            u.setAccount_id(acount.getId());
            followUpMsgService.posCusC(u);
            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    //    试驾
    @PostMapping("testDrive")
    @ResponseBody
    public BaseResult testDrive(HttpServletRequest request ,@RequestBody TestDriveRequest u){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            u.setChild_id(0);
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                u.setChild_id(childAccount.getId());
            }
            followUpMsgService.testDrive(u);
            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    /**
    * @author zhangD
    * @date 2018/2/27 14:47
    * @Description: 意向车型车商联盟
    */
    @PostMapping("AllyIntentionalCars")
    @ResponseBody
    public BaseResult AllyIntentionalCars (HttpServletRequest request ,@RequestBody AllyCarListRequest allyCarListRequest){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            List<AllyResponse> allyResponse=allyMapper.getFriendList(acount.getAccount());
            if (allyResponse.size()<=0) {
                return BaseResult.success("0");
            }
            List<Integer> accountIds=new ArrayList<>();
            for (AllyResponse response : allyResponse) {
                accountIds.add(response.getId());
            }
            return BaseResult.success(iCarsokProductService.getAllyCarList(accountIds,allyCarListRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }


}
