package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.constants.enums.TaskTypeEnums;
import com.uton.carsokApi.controller.response.FindListResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.HandlerCountMapper;
import com.uton.carsokApi.dao.SaasTenureCustomerMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.HandlerCountService;
import com.uton.carsokApi.service.ICarsokCustomerService;
import com.uton.carsokApi.service.ITaskFacade;
import com.uton.carsokApi.service.core.task.FilterSQLParam;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/14.
 */
@Service
public class HandlerCountServiceImpl implements HandlerCountService{

    @Autowired
    HandlerCountMapper handlerCountMapper;

    @Resource
    CacheService cacheService;

    @Autowired
    ChildAccountMapper childAccountMapper;

    @Autowired
    SaasTenureCustomerMapper saasTenureCustomerMapper;

    @Autowired
    ITaskFacade iTaskFacade;

    @Autowired
    ICarsokCustomerService carsokCustomerService;

    @Override
    public BaseResult selectCount(HttpServletRequest request, HandlerCount vo) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        int accountId = acount.getId();
        int childId = 0;
        if (!StringUtil.isEmpty(acount.getSubPhone())){
            ChildAccount child = childAccountMapper.selectByChildMobile(acount.getSubPhone());
            if (child != null){
                childId = child.getId();
            }
        }
        List<String> roleName = new ArrayList<>();
        if (childId != 0){
            roleName = saasTenureCustomerMapper.getRoleName(childId);
        }
        int roleType = 0;
        if(roleName.contains("byyxgw") && !roleName.contains("byjlgl") && !roleName.contains("bykfdp")){
            //销售,只能看到自己相关的信息
            roleType = 1;
        }
        //待完善一栏的数量
        PageHelper.startPage(0, 0);
        List<FindListResponse> notDoneList = new ArrayList<>();
        notDoneList = saasTenureCustomerMapper.findCarList(roleType, accountId, childId, 0, "", 1, 0, null);
        int notDoneNum = notDoneList.size();
        //例行一栏的数量
        FilterSQLParam sqlParam = new FilterSQLParam();
        String sqlStr = " module='retaincustomer' AND task_status='"+ TaskStatusEnums.ready +"' " +
                "AND type IN ('"+ TaskTypeEnums.threedays_followup +"', '"+ TaskTypeEnums.fifteendays_followup +"', '"+ TaskTypeEnums.onemonth_flollowup +"') " +
                "AND json_extract(extra_fields,'$.nextIsDone')!=1 " +
                "AND json_extract(extra_fields,'$.accountId')="+ accountId +" ";
        String roleStr = "";
        if (roleType == 1){
            roleStr = "AND json_extract(extra_fields,'$.childId')="+ childId +" ";
        }
        sqlStr = sqlStr + roleStr + "AND DATE_FORMAT(scheduled_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')";
        sqlParam.setSqlTemplate(sqlStr);
        sqlParam.setOrderByColumn("create_time");
        sqlParam.setIsAsc(false);
        PageInfo<CarsokTenureTask> pageInfo = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam);
        int onDutyNum = pageInfo.getList().size();
        //关怀一栏的数量
        FilterSQLParam sqlParam1 = new FilterSQLParam();
        String sqlStr1 = " module='retaincustomer' AND task_status='"+ TaskStatusEnums.ready +"' " +
                "AND type IN ('"+ TaskTypeEnums.compulsory_insurance_remind +"', '"+ TaskTypeEnums.commercial_insurance_remind +"', '"+ TaskTypeEnums.quality_assurance_remind +"', '"+ TaskTypeEnums.maintain_remind +"', '"+ TaskTypeEnums.annual_survey_remind +"', '"+ TaskTypeEnums.boughtday_solicitude +"', '"+ TaskTypeEnums.birthday_solicitude +"') " +
                "AND json_extract(extra_fields,'$.nextIsDone')!=1 " +
                "AND json_extract(extra_fields,'$.accountId')="+ accountId +" ";
        String roleStr1 = "";
        if (roleType == 1){
            roleStr1 = "AND json_extract(extra_fields,'$.childId')="+ childId +" ";
        }
        sqlStr1 = sqlStr1 + roleStr1 + "AND DATE_FORMAT(scheduled_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')";
        sqlParam1.setSqlTemplate(sqlStr1);
        sqlParam1.setOrderByColumn("create_time");
        sqlParam1.setIsAsc(false);
        PageInfo<CarsokTenureTask> pageInfo1 = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam1);
        int solicitudeNum = pageInfo1.getList().size();
        //逾期一栏的数量
        FilterSQLParam sqlParam2 = new FilterSQLParam();
        String sqlStr2 = " module='retaincustomer' AND task_status='"+ TaskStatusEnums.delay +"' " +
                "AND json_extract(extra_fields,'$.nextIsDone')!=1 " +
                "AND json_extract(extra_fields,'$.accountId')="+ accountId +" ";
        String roleStr2 = "";
        if (roleType == 1){
            roleStr2 = "AND json_extract(extra_fields,'$.childId')="+ childId +" ";
        }
        sqlStr2 = sqlStr2 + roleStr2;
        sqlParam2.setSqlTemplate(sqlStr2);
        sqlParam2.setOrderByColumn("create_time");
        sqlParam2.setIsAsc(false);
        PageInfo<CarsokTenureTask> pageInfo2 = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam2);
        int outDateNum = pageInfo2.getList().size();

        int baoyouNum = notDoneNum + onDutyNum + solicitudeNum + outDateNum;

        int id = 0;
        String ids = "";
        FilterSQLParam filterSQLParam = new FilterSQLParam();
        filterSQLParam.setOrderByColumn("create_time");
        filterSQLParam.setIsAsc(false);
        String power = "qkjlkf";//能看到全部的内容
        if(roleName.contains("qkyxgw") && !roleName.contains("qkjlgl") && !roleName.contains("qkkfdp")){
            //销售,只能看到自己相关的信息
            power = "qkyxgw";
        }
        if ("qkjlkf".equals(power)) {
            ids = "account_id";
            id = accountId;
        } else {
            ids = "child_id";
            id = childId;
        }
        DateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String startDate = sdfStart.format(new Date());
        DateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String endDate = sdfEnd.format(new Date());
        String dhfSQL = "json_extract(extra_fields,'$." + ids + "')='" + id + "' AND extra_fields->>'$.level' NOT IN ('F 战败','F0 战败待确认')";
        String gqSQL = "json_extract(extra_fields,'$." + ids + "')='" + id + "'  AND extra_fields->>'$.level' NOT IN ('F 战败','F0 战败待确认')";
        String zbf0SQL = "json_extract(extra_fields,'$." + ids + "')='" + id + "'" + " AND extra_fields->'$.level' ='F0 战败待确认' and module = 'potentialcustomer' and task_status = 'ready' ";
        filterSQLParam.setSqlTemplate(dhfSQL);
        List<Integer> dhfList = carsokCustomerService.skyCount(1, 0, 0, filterSQLParam);
        int dhfCount = dhfList.size();
        filterSQLParam.setSqlTemplate(gqSQL);
        List<Integer> gqList = carsokCustomerService.skyCount(2, 0, 0, filterSQLParam);
        int gqCount = gqList.size();
        filterSQLParam.setSqlTemplate(zbf0SQL);
        List<Integer> zbf0List = carsokCustomerService.skyCount(4, 0, 0, filterSQLParam);
        int zbf0Count = zbf0List.size();

        int qiankeNum = dhfCount + gqCount + zbf0Count;

        BaseResult baseResult = BaseResult.success();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("mendian",vo.getMobile().equals(acount.getAccount())?0:qiankeNum);
        map.put("baoyou",vo.getMobile().equals(acount.getAccount())?0:baoyouNum);
        map.put("rijian",vo.getMobile().equals(acount.getAccount())?0:handlerCountMapper.selectRiJianCount(vo.getMobile()));
        map.put("shouche",vo.getMobile().equals(acount.getAccount())?0:handlerCountMapper.selectShouCheCount(vo.getMobile()));
        map.put("xibao",vo.getMobile().equals(acount.getAccount())?0:handlerCountMapper.selectXiBaoCount(vo.getMobile()));
        baseResult.setData(map);
        return baseResult;
    }

    @Override
    public String deleteMendianMsg(String id) {
        int ids = 0;
        if(!StringUtil.isEmpty(id)){
            ids = Integer.parseInt(id);
        }
        int count = handlerCountMapper.deleteMendianMsg(ids);
        if(count>0){
            return "0000";
        }else {
            return "0001";
        }
    }

    @Override
    public String deleteShoucheMsg(String id) {
        int ids = 0;
        if(!StringUtil.isEmpty(id)){
            ids = Integer.parseInt(id);
        }
        int count = handlerCountMapper.deleteShoucheMsg(ids);
        if(count>0){
            return "0000";
        }else {
            return "0001";
        }
    }


}
