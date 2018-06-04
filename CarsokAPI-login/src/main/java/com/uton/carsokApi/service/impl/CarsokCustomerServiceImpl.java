package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.enums.ModuleEnums;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.constants.enums.TaskTypeEnums;
import com.uton.carsokApi.controller.request.CarsOkCustomerRequest;
import com.uton.carsokApi.controller.request.FollowUpMsgRequest;
import com.uton.carsokApi.controller.request.LatentCustomerRequest;
import com.uton.carsokApi.controller.response.CarName;
import com.uton.carsokApi.controller.response.CarsOkCustmoerResponse;
import com.uton.carsokApi.controller.response.CarsokCustomerResponse;
import com.uton.carsokApi.dao.CarsokCustomerIntentionCarMapper;
import com.uton.carsokApi.dao.CarsokCustomerMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.FollowUpMsgService;
import com.uton.carsokApi.service.ICarsokCustomerService;
import com.uton.carsokApi.service.ITaskFacade;
import com.uton.carsokApi.service.core.ObjectDiffExcutor;
import com.uton.carsokApi.service.core.task.FilterSQLParam;
import com.uton.carsokApi.util.DozerMapperUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
@Service
@Transactional
public class CarsokCustomerServiceImpl extends ServiceImpl<CarsokCustomerMapper, CarsokCustomer> implements ICarsokCustomerService {
    Logger logger = Logger.getLogger(CarsokCustomerServiceImpl.class);
    @Autowired
    private CarsokCustomerIntentionCarMapper carsokCustomerIntentionCarMapper;

    @Autowired
    CarsokCustomerMapper carsokCustomerMapper;

    @Autowired
    ChildAccountMapper childAccountMapper;

    @Autowired
    private FollowUpMsgService followUpMsgService;

    @Autowired
    ITaskFacade iTaskFacade;

    private  final  String mainAccount ="主账号";

    @Override
    public boolean updateLatentMsg(CarsOkCustomerRequest carsOkCustomerRequest, String accountId, String childId) throws ParseException {
        CarsokCustomer carsokCustomer = new CarsokCustomer();
        DozerMapperUtil.getInstance().map(carsOkCustomerRequest, carsokCustomer);
        Integer cusId = carsokCustomer.getId();
        if (cusId == null) {
            carsokCustomer.setCreateTime(new Date());
            carsokCustomer.setUpdateTime(new Date());
        } else {
            //如果是修改操作 则判断 是否有修改内容 有的话记录到数据库中
            carsokCustomer.setUpdateTime(new Date());
            CarsokCustomer carsokCustomer1 = new CarsokCustomer().selectById(carsOkCustomerRequest.getId());
            carsokCustomer.setChildId(carsokCustomer1.getChildId());
            ObjectDiffExcutor<CarsokCustomer> objectDiffExcutor = new ObjectDiffExcutor<>();
            if("[]".equals(carsokCustomer1.getMarkerCar())){
                carsokCustomer1.setMarkerCar(null);
            }
            if("[]".equals(carsokCustomer.getMarkerCar())){
                carsokCustomer.setMarkerCar(null);
            }
            String cmp1 = objectDiffExcutor.compareObjectWitTemplate(carsokCustomer1, carsokCustomer, 1);

            ObjectDiffExcutor<CarsokCustomerIntentionCar> objectDiffExcutorlist = new ObjectDiffExcutor<>();
            List<CarsokCustomerIntentionCar> cOld = new CarsokCustomerIntentionCar()
                    .selectList(new EntityWrapper().eq("customer_id", carsOkCustomerRequest.getId()));
            List<CarsokCustomerIntentionCar> cNew = new ArrayList<>();
            for (CarName carName : carsOkCustomerRequest.getCarName()) {
                CarsokCustomerIntentionCar cc3 = new CarsokCustomerIntentionCar();
                cc3.setCustomerId(carsokCustomer.getId());
                cc3.setModel(carName.getModel());
                cc3.setSeries(carName.getSeries());
                cc3.setBrand(carName.getBrand());
                cc3.setProductStatus(carName.getProductStatus());
                cc3.setProductType(carName.getProductType());
                cc3.setProductId(carName.getProductId());
                cc3.setMerchantId(carName.getMerchantId());
                cNew.add(cc3);
            }
            String cmp2 = objectDiffExcutorlist.compareListObject(cOld, cNew, 1);


            //保存修改记录
            if (!StringUtil.isEmpty(cmp1) || !StringUtil.isEmpty(cmp2)) {
                StringBuffer str = new StringBuffer();
                if (!StringUtil.isEmpty(cmp1)) {
                    cmp1 = cmp1.replace("["," ");
                    cmp1 = cmp1.replace("]"," ");
                    cmp1 = cmp1.replace("\"","");
                    str.append(cmp1);
                }
                if (!StringUtil.isEmpty(cmp2)) {
                    str.append(" 意向车型 " + cmp2);
                }
                CarsokRecord carsokRecord = new CarsokRecord();
                carsokRecord.setCreateTime(new Date());
                carsokRecord.setAccountId(carsOkCustomerRequest.getAccountId());
                carsokRecord.setChildId(Integer.valueOf(childId));
                carsokRecord.setModel("1");
                carsokRecord.setOutId(cusId);
                carsokRecord.setType(carsOkCustomerRequest.getLevel());
                carsokRecord.setMessage(str.toString().trim());
                carsokRecord.insert();
            }
        }
        carsokCustomer.insertOrUpdate();
        carsokCustomer.getId();
        new CarsokCustomerIntentionCar().delete(new EntityWrapper().eq("customer_id", carsokCustomer.getId()));
        List<CarName> list = carsOkCustomerRequest.getCarName();
        for (CarName s : list) {
            CarsokCustomerIntentionCar c = new CarsokCustomerIntentionCar();
            c.setCustomerId(carsokCustomer.getId());
            c.setModel(s.getModel());
            c.setSeries(s.getSeries());
            c.setBrand(s.getBrand());
            c.setProductStatus(s.getProductStatus());
            c.setProductType(s.getProductType());
            c.setProductId(s.getProductId());
            c.setMerchantId(s.getMerchantId());
            c.insert();
        }
        //如果是插入操作
        if (cusId == null) {
            TaskInitParam taskInitParam = new TaskInitParam();
            Calendar c = Calendar.getInstance();
            c.setTime(carsOkCustomerRequest.getInTime());
            switch (carsokCustomer.getLevel()) {
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
            }
            taskInitParam.setBusiness_id(carsokCustomer.getId().toString());
            taskInitParam.setModule(ModuleEnums.potentialcustomer);
            Map<String, String> map = new HashMap<>();
            map.put("account_id", accountId);
            map.put("child_id", childId);
            map.put("level", carsokCustomer.getLevel());
            taskInitParam.setExtraFields(map);
            Date now = new Date();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String nowStr = sdf.format(now);
            String stStr = sdf.format(taskInitParam.getScheduled_time());
            now = sdf.parse(nowStr);
            Date st = sdf.parse(stStr);
            long diff = st.getTime() - now.getTime();
            if(diff >= 0){//任务需要执行的时间比现在晚 ready
                taskInitParam.setStatusEnums(TaskStatusEnums.ready);
            }else{//任务需要执行的时间比现在早 delay
                taskInitParam.setStatusEnums(TaskStatusEnums.delay);
            }
            iTaskFacade.createTask(taskInitParam);
        }
        return true;
    }

    @Override
    public CarsOkCustmoerResponse selectCustMsg(Integer id) {
        CarsokCustomer carsokCustomer = new CarsokCustomer().selectById(id);
        //详情页添加销售顾问
        JSONArray jsonArray = JSON.parseArray(carsokCustomer.getMarkerCar());
        CarsokChildAccount carsokChildAccount =new CarsokChildAccount();
        if (carsokCustomer != null) {
            if (carsokCustomer.getChildId() == 0) {
                //如果childid，则显示主账号
                carsokChildAccount.setChildAccountName(mainAccount);
            }else {
                carsokChildAccount =new CarsokChildAccount().selectById(carsokCustomer.getChildId());
            }
        }
        List<CarsokCustomerIntentionCar> carsokCustomerIntentionCars = new CarsokCustomerIntentionCar().selectList(new EntityWrapper().eq("customer_id", id));
        CarsOkCustmoerResponse carsOkCustmoerResponse = new CarsOkCustmoerResponse();
        carsOkCustmoerResponse.setCarName(new ArrayList<CarName>());
        carsOkCustmoerResponse.setPower(new ArrayList<String>());
        //详情页添加销售顾问
        carsOkCustmoerResponse.setChildAccountName(carsokChildAccount==null?"离职员工":carsokChildAccount.getChildAccountName());
        DozerMapperUtil.getInstance().map(carsokCustomer, carsOkCustmoerResponse);
        for (CarsokCustomerIntentionCar c : carsokCustomerIntentionCars) {
            CarName carName = new CarName();
            carName.setBrand(c.getBrand());
            carName.setSeries(c.getSeries());
            carName.setModel(c.getModel());
            carName.setProductStatus(c.getProductStatus());
            carName.setProductType(c.getProductType());
            carName.setProductId(c.getProductId());
            carName.setMerchantId(c.getMerchantId());
            carsOkCustmoerResponse.getCarName().add(carName);
        }
        //查询关联的taskid
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());
        FilterSQLParam sqlStatement = new FilterSQLParam();
        String sqlStr = "business_id = "+ id +" AND module = 'potentialcustomer' " +
                "AND ((task_status  = '"+ TaskStatusEnums.ready +"' AND (scheduled_time IS NULL OR DATE_FORMAT(scheduled_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d'))) " +
                "OR (task_status = '"+ TaskStatusEnums.delay +"' ))";
        sqlStatement.setSqlTemplate(sqlStr);
        PageInfo<CarsokTenureTask> page = iTaskFacade.queryTaskBySQLFilter(1,1, sqlStatement);
        CarsokTenureTask carsokTenureTask = null;
        if (page.getList().size() > 0){
            carsokTenureTask = page.getList().get(0);
        }
//        logger.error("carsokTenureTask:" + carsokTenureTask + "任务信息空指针异常 ");
        if (carsokTenureTask != null) {
            carsOkCustmoerResponse.setTaskId(carsokTenureTask.getId());
        }
        List<CarsokTenureTask> task = new CarsokTenureTask().selectList(new EntityWrapper().eq("business_id", id));
        String title = "客户跟进";
        if(task!=null&&task.size()>0){
            if("ready".equals(task.get(task.size()-1).getTaskStatus()) || "delay".equals(task.get(task.size()-1).getTaskStatus())){
                if("F0 战败待确认".equals(carsokCustomer.getLevel())){
                    title = "战败待确认";
                }else if("F 战败".equals(carsokCustomer.getLevel())){
                    title = "战败客户";
                }else {
                    title = "客户跟进";
                }
                logger.info("title1为："+title);
            }
            if(task.size()>1){
                if("finish".equals(task.get(task.size()-1).getTaskStatus())){
                    title = "今日已回访";
                }
                if("ready".equals(task.get(task.size()-1).getTaskStatus()) && "finish".equals(task.get(task.size()-2).getTaskStatus())){
                    DateFormat taskSdf = new SimpleDateFormat("yyyy-MM-dd");
                    if(taskSdf.format(task.get(task.size()-2).getActualFinishTime()==null?new Date(new Date().getTime() - 86400000L):task.get(task.size()-2).getActualFinishTime()).equals(taskSdf.format(new Date())) ){
                        if("F0 战败待确认".equals(carsokCustomer.getLevel())){
                            title = "战败待确认";
                        }else if("F 战败".equals(carsokCustomer.getLevel())){
                            title = "战败客户";
                        }else {
                            title = "今日已回访";
                        }
                        logger.info("title5为："+title);
                    }else {
                        if("F0 战败待确认".equals(carsokCustomer.getLevel())){
                            title = "战败待确认";
                        }else if("F 战败".equals(carsokCustomer.getLevel())){
                            title = "战败客户";
                        }else {
                            title = "客户跟进";
                        }
                        logger.info("title4为："+title);
                    }
                    logger.info("title2为："+title);
                }
            }
        }else {
            title = "客户跟进";
            logger.info("title3为："+title);
        }
        carsOkCustmoerResponse.setCustomerTitle(title);
        carsOkCustmoerResponse.setMarkerCar(jsonArray);
        logger.info("潜客详情页信息："+JSON.toJSONString(carsOkCustmoerResponse));
        return carsOkCustmoerResponse;
    }


    @Override
    public Map selectLatentList(int accountId, String accountMobile, LatentCustomerRequest latentCustomerRequest) {
        BaseResult baseResult = BaseResult.success();
        Map<String, Object> map = new HashMap();
        Map<String, Object> countMap = new HashMap();
        List<CarsokCustomer> list = new ArrayList<>();
        List<String> powerList = new ArrayList<>();
        PageInfo pageInfo = null;
        try {
            if (!StringUtil.isEmpty(accountMobile)) {//子帐号查看
                ChildAccount child = new ChildAccount();
                child.setChildAccountMobile(accountMobile);
                ChildAccount childAccount = childAccountMapper.selectByModel(child);
                if (childAccount != null) {
                    powerList = carsokCustomerMapper.selectPowerByChildMobile(childAccount.getId());
                    pageInfo = selectList(latentCustomerRequest, accountId, powerList, list, childAccount);
                    if (powerList.contains("qkjlgl") || powerList.contains("qkkfdp")
                            || (powerList.contains("qkjlgl") && powerList.contains("qkkfdp") && powerList.contains("qkyxgw"))
                            || (powerList.contains("qkjlgl") && powerList.contains("qkyxgw"))
                            || (powerList.contains("qkkfdp") && powerList.contains("qkyxgw"))) {//如果是经理管理或者客服点评
                        countMap = selectTabCount("qkjlkf", latentCustomerRequest.getPageCount(), latentCustomerRequest.getPageSize(), accountId, childAccount.getId());
                    } else if (powerList.contains("qkyxgw") && !powerList.contains("qkjlgl") && !powerList.contains("qkkfdp")) {//如果是销售顾问，只能看到自己的信息
                        countMap = selectTabCount("qkyxgw", latentCustomerRequest.getPageCount(), latentCustomerRequest.getPageSize(), accountId, childAccount.getId());
                    }
                } else {//主帐号查看
                    powerList.add("qkjlgl");
                    powerList.add("qkkfdp");
                    powerList.add("qkyxgw");
                    countMap = selectTabCount("qkjlkf", latentCustomerRequest.getPageCount(), latentCustomerRequest.getPageSize(), accountId, 0);
                    pageInfo = selectList(latentCustomerRequest, accountId, powerList, list, new ChildAccount());
                }
            }
            map.put("latentList", pageInfo);
            map.put("power", powerList);
            map.put("count", countMap);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public void test(){
//        ObjectDiffExcutor<CarsokCustomer> objectDiffExcutor = new ObjectDiffExcutor<>();
//        String cd = objectDiffExcutor.compareListObject(li);
//        objectDiffExcutor.compareObjectWitTemplate("","");
//    }

    public PageInfo selectList(LatentCustomerRequest latentCustomerRequest, int accountId, List<String> powerList, List<CarsokCustomer> list, ChildAccount childAccount) {
        String childExtraSQL = "json_extract(extra_fields,'$.child_id')='" + childAccount.getId() + "' AND extra_fields->>'$.level' NOT IN ('F 战败','F0 战败待确认','G 复购')";
        String accountExtraSQL = "json_extract(extra_fields,'$.account_id')='" + accountId + "'  AND extra_fields->>'$.level' NOT IN ('F 战败','F0 战败待确认','G 复购')";
        String accountDefeatSQL = "json_extract(extra_fields,'$.account_id')='" + accountId + "'" + " AND extra_fields->>'$.level' IN ('F 战败','F0 战败待确认') ";
        String childDefeatSQL = "json_extract(extra_fields,'$.child_id')='" + childAccount.getId() + "'" + " AND extra_fields->>'$.level' IN ('F 战败','F0 战败待确认') ";
        FilterSQLParam filterSQLParam = new FilterSQLParam();
        filterSQLParam.setOrderByColumn("create_time");
        filterSQLParam.setIsAsc(false);
        List<CarsokCustomerResponse> custList = new ArrayList<>();
        List<CarsokTenureTask> taskList = null;
        //如果是经理管理或者客服，查看所有信息
        if ((powerList.contains("qkjlgl") || powerList.contains("qkkfdp")) ||
                (powerList.contains("qkjlgl") && powerList.contains("qkkfdp") && powerList.contains("qkyxgw"))
                || (powerList.contains("qkkfdp") && powerList.contains("qkyxgw"))
                || (powerList.contains("qkjlgl") && powerList.contains("qkyxgw"))) {
            if (latentCustomerRequest.getType() >= 1 && latentCustomerRequest.getType() <= 4) {//tab页查询
                if (latentCustomerRequest.getType() == 4) {
                    filterSQLParam.setSqlTemplate(accountDefeatSQL);
                } else {
                    //如果是已回访 则根据更新时间排序
                    if (latentCustomerRequest.getType() == 3) {
                        filterSQLParam.setOrderByColumn("update_time");
                    }
                    filterSQLParam.setSqlTemplate(accountExtraSQL);
                }
                taskList = sky(latentCustomerRequest.getType(),latentCustomerRequest.getPageCount(),latentCustomerRequest.getPageSize(),filterSQLParam);
                for(CarsokTenureTask task:taskList){
                    CarsokCustomerResponse carsokCustomerResponse = carsokCustomerMapper.selectTaskById(Integer.parseInt(task.getBusinessId()));
                    CarsokCustomerResponse temp = new CarsokCustomerResponse();
                    carsokCustomerResponse.setTaskId(task.getId());
                    carsokCustomerResponse.setTaskType(taskType(task.getType()));
                    DozerMapperUtil.getInstance().map(carsokCustomerResponse, temp);
                    custList.add(temp);
                }
            }
            if (latentCustomerRequest.getSelect() != null && latentCustomerRequest.getType() == 0) {//搜索
                PageHelper.startPage(latentCustomerRequest.getPageCount(), latentCustomerRequest.getPageSize());
                custList = carsokCustomerMapper.selectLatentListBySearchKey(latentCustomerRequest.getSelect(), accountId, 0);
            }
            if (latentCustomerRequest.getType() == 5) {//筛选查询
                PageHelper.startPage(latentCustomerRequest.getPageCount(), latentCustomerRequest.getPageSize());
                custList = carsokCustomerMapper.selectLatentListByScreen(latentCustomerRequest.getTimes(), latentCustomerRequest.getLevel(), latentCustomerRequest.getSource(), latentCustomerRequest.getBudget(), latentCustomerRequest.getChildId(), accountId, "qkjlkf", childAccount.getId() == null ? 0 : childAccount.getId());
            }
        } else if (powerList.contains("qkyxgw") && !powerList.contains("qkjlgl") && !powerList.contains("qkkfdp")) {//如果是销售顾问，只能看到自己的信息
            if (latentCustomerRequest.getType() >= 1 && latentCustomerRequest.getType() <= 4) {//tab页查询
                if (latentCustomerRequest.getType() == 4) {
                    filterSQLParam.setSqlTemplate(childDefeatSQL);
                } else {
                    filterSQLParam.setSqlTemplate(childExtraSQL);
                }
                taskList = sky(latentCustomerRequest.getType(),latentCustomerRequest.getPageCount(),latentCustomerRequest.getPageSize(),filterSQLParam);
                for(CarsokTenureTask task:taskList){
                    CarsokCustomerResponse carsokCustomerResponse = carsokCustomerMapper.selectTaskById(Integer.parseInt(task.getBusinessId()));
                    CarsokCustomerResponse temp = new CarsokCustomerResponse();
                    carsokCustomerResponse.setTaskId(task.getId());
                    carsokCustomerResponse.setTaskType(taskType(task.getType()));
                    DozerMapperUtil.getInstance().map(carsokCustomerResponse, temp);
                    custList.add(temp);
                }
            }
            if (latentCustomerRequest.getSelect() != null && latentCustomerRequest.getType() == 0) {//搜索
                PageHelper.startPage(latentCustomerRequest.getPageCount(), latentCustomerRequest.getPageSize());
                custList = carsokCustomerMapper.selectLatentListBySearchKey(latentCustomerRequest.getSelect(), accountId, childAccount != null ? childAccount.getId() : 0);
            }
            if (latentCustomerRequest.getType() == 5) {//筛选查询
                PageHelper.startPage(latentCustomerRequest.getPageCount(), latentCustomerRequest.getPageSize());
                custList = carsokCustomerMapper.selectLatentListByScreen(latentCustomerRequest.getTimes(), latentCustomerRequest.getLevel(), latentCustomerRequest.getSource(), latentCustomerRequest.getBudget(), new ArrayList<Integer>(), accountId, "qkyxgw", childAccount.getId());
            }
        }
        if (custList.size()>0) {
            for (CarsokCustomerResponse carsokCustomerResponse : custList) {
                if (carsokCustomerResponse.getChildId()==0) {
                    carsokCustomerResponse.setChildAccountName("主账号");
                }
            }
        }
//        显示离职员工
        for(CarsokCustomerResponse cus:custList){
            if(StringUtil.isEmpty(cus.getChildAccountName())){
                cus.setChildAccountName("离职员工");
            }
        }
        return new PageInfo<>(custList);
    }

    public Map selectTabCount(String power, int pageCount, int pageSize, int accountId, int childId) {
        try {
            int id = 0;
            String ids = "";
            FilterSQLParam filterSQLParam = new FilterSQLParam();
            filterSQLParam.setOrderByColumn("create_time");
            filterSQLParam.setIsAsc(false);
            if ("qkjlkf".equals(power)) {
                ids = "account_id";
                id = accountId;
            } else {
                ids = "child_id";
                id = childId;
            }
            String dhfSQL = "json_extract(extra_fields,'$." + ids + "')='" + id + "' AND extra_fields->>'$.level' NOT IN ('F 战败','F0 战败待确认')";
            String gqSQL = "json_extract(extra_fields,'$." + ids + "')='" + id + "'  AND extra_fields->>'$.level' NOT IN ('F 战败','F0 战败待确认')";
            String yhfSQL = "json_extract(extra_fields,'$." + ids + "')='" + id + "'  AND extra_fields->>'$.level' NOT IN ('F 战败','F0 战败待确认','D 已成交','G 复购') and DATE_FORMAT(actual_finish_time, '%Y-%m-%d') = (DATE_FORMAT(NOW(), '%Y-%m-%d')) and task_status = 'finish' and module = 'potentialcustomer'";
            String zbf0SQL = "json_extract(extra_fields,'$." + ids + "')='" + id + "'" + " AND extra_fields->'$.level' ='F0 战败待确认' and module = 'potentialcustomer' and task_status = 'ready' ";
            String zbfSQL = "json_extract(extra_fields,'$." + ids + "')='" + id + "'" + " AND extra_fields->'$.level' ='F 战败' and module = 'potentialcustomer' and task_status = 'ready' ";
            Map<String, Object> map = new HashMap<>();
            filterSQLParam.setSqlTemplate(dhfSQL);
            List<Integer> dhfList = skyCount(1, 0, 0, filterSQLParam);
            int dhfCount = dhfList.size();
            filterSQLParam.setSqlTemplate(gqSQL);
            List<Integer> gqList = skyCount(2, 0, 0, filterSQLParam);
            int gqCount = gqList.size();
            filterSQLParam.setSqlTemplate(yhfSQL);
            List<Integer> yhfList = skyCount(3, 0, 0, filterSQLParam);
            int yhfCount = yhfList.size();
            filterSQLParam.setSqlTemplate(zbf0SQL);
            List<Integer> zbf0List = skyCount(4, 0, 0, filterSQLParam);
            int zbf0Count = zbf0List.size();
            filterSQLParam.setSqlTemplate(zbfSQL);
            List<Integer> zbfList = skyCount(5, 0, 0, filterSQLParam);
            int zbfCount = zbfList.size();
            map.put("dhf", dhfCount);
            map.put("gq", gqCount);
            map.put("yhf", yhfCount);
            map.put("zbf0", zbf0Count);
            map.put("zbf", zbfCount);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    @Override
    public CarsokCustomer selectByMobile(String mobile,Integer accountId) {
        return new CarsokCustomer().selectOne(new EntityWrapper().eq("account_id",accountId)
                                                                 .eq("mobile", mobile));
    }

    @Override
    public BaseResult selectAllSaledPeople(String mobile) {
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(childAccountMapper.selectAllChild(mobile));
        return baseResult;
    }

    public List<CarsokTenureTask> sky(int type, int pageCount, int pageSize, FilterSQLParam filterSQLParam){
        PageInfo<CarsokTenureTask> pageInfo = null;
        List<CarsokTenureTask> idList = new ArrayList<>();
        switch (type) {
            case 1:
                pageInfo = iTaskFacade.queryTaskByStatusWithSQL(pageCount, pageSize, TaskStatusEnums.ready, ModuleEnums.potentialcustomer, filterSQLParam);
                break;
            case 2:
                pageInfo = iTaskFacade.queryTaskByStatusWithSQL(pageCount, pageSize, TaskStatusEnums.delay, ModuleEnums.potentialcustomer, filterSQLParam);
                break;
            case 3:
                filterSQLParam.setSqlTemplate(filterSQLParam.getSqlTemplate() + " and DATE_FORMAT(actual_finish_time, '%Y-%m-%d') = (DATE_FORMAT(NOW(), '%Y-%m-%d'))");
                pageInfo = iTaskFacade.queryTaskByStatusWithSQL(pageCount, pageSize, TaskStatusEnums.finish, ModuleEnums.potentialcustomer, filterSQLParam);
                break;
            case 4:
                filterSQLParam.setSqlTemplate(filterSQLParam.getSqlTemplate() + "and task_status ='ready' and module = 'potentialcustomer'");
                pageInfo = iTaskFacade.queryTaskBySQLFilter(pageCount, pageSize, filterSQLParam);
                break;
        }

        for(CarsokTenureTask task:pageInfo.getList()){
            CarsokTenureTask tenureTask = new CarsokTenureTask();
            tenureTask.setId(task.getId());
            tenureTask.setBusinessId(task.getBusinessId());
            tenureTask.setType(task.getType());
            idList.add(tenureTask);
        }
        return idList;
    }

    /**
     * @author zhangdi
     * @date 2017/11/20 11:13
     * @Description: 潜客购车结束所有任务
     */
    @Override
    public List<CarsokTenureTask> selecAllTask(Integer cusId) {
        CarsokCustomer cus = new CarsokCustomer().selectById(cusId);
        List<CarsokTenureTask> carsokTenureTask = new ArrayList<>();
        if (cus != null) {
            if (("D 已成交").equals(cus.getLevel())) {
                carsokTenureTask = new CarsokTenureTask().selectList(new EntityWrapper().eq("business_id", cusId));
            }
        }
        return carsokTenureTask;
    }


    public List<Integer> skyCount(int type, int pageCount, int pageSize, FilterSQLParam filterSQLParam) {
        PageInfo<CarsokTenureTask> pageInfo = null;
        List<Integer> idList = new ArrayList<>();
        switch (type) {
            case 1:
                pageInfo = iTaskFacade.queryTaskByStatusWithSQL(pageCount, pageSize, TaskStatusEnums.ready, ModuleEnums.potentialcustomer, filterSQLParam);
                break;
            case 2:
                pageInfo = iTaskFacade.queryTaskByStatusWithSQL(pageCount, pageSize, TaskStatusEnums.delay, ModuleEnums.potentialcustomer, filterSQLParam);
                break;
            case 3:
                pageInfo = iTaskFacade.queryTaskByStatusWithSQL(pageCount, pageSize, TaskStatusEnums.finish, ModuleEnums.potentialcustomer, filterSQLParam);
                break;
            case 4:
                pageInfo = iTaskFacade.queryTaskBySQLFilter(pageCount, pageSize, filterSQLParam);
                break;
            case 5:
                pageInfo = iTaskFacade.queryTaskBySQLFilter(pageCount, pageSize, filterSQLParam);
                break;
        }
        for (CarsokTenureTask task : pageInfo.getList()) {
            idList.add(Integer.parseInt(task.getBusinessId()));
        }
        return idList;
    }

    public String taskType(String type){
        String taskType = "";
        switch (type){
            case "oneday_buy":
                taskType = "N 24小时内回访";
                break;
            case "threedays_buy":
                taskType = "H 3天内购买";
                break;
            case "sevendays_buy":
                taskType = "A 7天内购买";
                break;
            case "fifteendays_buy":
                taskType = "B 15天内购买";
                break;
            case "onemonth_buy":
                taskType = "C 30天内购买";
                break;
            case "defeat":
                taskType = "F 战败";
                break;
            case "defeat_confirm":
                taskType = "F0 战败待确认";
                break;
            case "re_purchase":
                taskType = "G 复购";
                break;

        }
        return taskType;
    }


    @Override
    public void customerRePurchase(int accountId, int childId, String level, String customerFlowMessage,String custId) {
        FollowUpMsgRequest purchase = new FollowUpMsgRequest();
        purchase.setCustomerFlowMessage(customerFlowMessage);
        purchase.setLevel("G 复购");
        purchase.setAccount_id(String.valueOf(accountId));
        purchase.setChild_id(String.valueOf(childId));
        purchase.setCustId(custId);
        //创建一条复购的任务
        int taskId = followUpMsgService.insertTaskTable(purchase);
        //完成复购的任务
        if(childId==0){
            iTaskFacade.finishTaskById(taskId,String.valueOf(accountId),null);
        }else {
            iTaskFacade.finishTaskById(taskId,null,String.valueOf(childId));
        }
        CarsokCustomer customer = carsokCustomerMapper.selectById(custId);
        //修改客户级别
        carsokCustomerMapper.updateCustLevelById(Integer.parseInt(custId),level);
        //增加record记录
        purchase.setLevel("[复购]"+level);
        followUpMsgService.insertCarsokRecord(purchase,"");
        purchase.setLevel(level);
        //增加flow记录
        followUpMsgService.insertNewMsg(purchase);
        //新增选择客户级别的任务
        followUpMsgService.insertTaskTable(purchase);
    }

}
