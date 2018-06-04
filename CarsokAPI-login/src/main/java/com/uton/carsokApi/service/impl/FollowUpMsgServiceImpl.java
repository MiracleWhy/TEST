package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uton.carsokApi.constants.enums.ModuleEnums;
import com.uton.carsokApi.constants.enums.TaskTypeEnums;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.CarName;
import com.uton.carsokApi.dao.CarsokCustomerMapper;
import com.uton.carsokApi.dao.CarsokRecordMapper;
import com.uton.carsokApi.dao.CarsokTenureTaskMapper;
import com.uton.carsokApi.dao.FollowUpMsgMapper;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.ChildInfoService;
import com.uton.carsokApi.service.ITaskFacade;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uton.carsokApi.service.FollowUpMsgService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by SEELE on 2017/11/11.
 */
@Service
public class FollowUpMsgServiceImpl implements FollowUpMsgService {

    @Autowired
    FollowUpMsgMapper followUpMsgMapper;
    @Autowired
    ITaskFacade iTaskFacade;
    @Autowired
    CarsokRecordMapper carsokRecordMapper;
    @Autowired
    CarsokTenureTaskMapper carsokTenureTaskMapper;
    @Autowired
    CarsokCustomerMapper carsokCustomerMapper;



    @Override
    public void insertCustomerFlowMsg(FollowUpDistributionRequest u) {
        followUpMsgMapper.updateDistribution(u);
    }

    @Override
    public void insertNewMsg(FollowUpMsgRequest u) {

        FollowUpMsg f=new FollowUpMsg();
        f.setCustId(u.getCustId());
        f.setCustomerFlowMessage(u.getCustomerFlowMessage());
        f.setLevel(u.getLevel());
        f.setDefeat(u.getDefeat());
        f.setAccount_id(u.getAccount_id());
        f.setChild_id(u.getChild_id());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        f.setUpdate_time(formatter.format(new Date()));
        f.setCreate_time(formatter.format(new Date()));
        if(!StringUtil.isEmpty(f.getCustId())){
            followUpMsgMapper.insertMsg(f);
            followUpMsgMapper.updateMsg(f);
        }
    }

//    改task表
    @Override
    public void updateTaskTable(FollowUpMsgRequest u) {
        TaskInitParam taskInitParam = new TaskInitParam();
        taskInitParam.setBusiness_id(u.getCustId());
        taskInitParam.setModule(ModuleEnums.potentialcustomer);
        Map<String,String> map =new HashMap<>();
        map.put("account_id",u.getAccount_id());
        map.put("child_id",u.getChild_id()==null?"0":u.getChild_id());
        map.put("level",u.getLevel());
        taskInitParam.setExtraFields(map);

//          改carsok_tenure_task表
        iTaskFacade.updateExtraData(Integer.valueOf(u.getTaskId()),map,u.getAccount_id(),u.getChild_id());

        CarsokTenureTask vo = carsokTenureTaskMapper.selectById(u.getTaskId()) ;
        vo.setId(Integer.parseInt(u.getTaskId()));
        vo.setTaskStatus("finish");
        carsokTenureTaskMapper.updateById(vo);

    }

    @Override
    public int insertTaskTable(FollowUpMsgRequest u) {


        TaskInitParam taskInitParam = new TaskInitParam();
        Calendar c = Calendar.getInstance();


        if("F 战败".equals(u.getLevel())==false||"F0 战败待确认".equals(u.getLevel())==false){
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
                case "G 复购":
                    taskInitParam.setType(TaskTypeEnums.re_purchase);
                    break;
            }
        }
        taskInitParam.setBusiness_id(u.getCustId());
        taskInitParam.setModule(ModuleEnums.potentialcustomer);
        Map map =new HashMap();
        map.put("account_id",u.getAccount_id());
//            查登录人的子id
        CarsokCustomer carsokCustomer = new CarsokCustomer();
        if(!StringUtil.isEmpty(u.getCustId())){
            carsokCustomer.setId(Integer.valueOf(u.getCustId()));
            carsokCustomer = carsokCustomer.selectById(Integer.valueOf(u.getCustId()));
            map.put("child_id",String.valueOf(carsokCustomer.getChildId()));
        }
        map.put("level",u.getLevel());
        taskInitParam.setExtraFields(map);
        return iTaskFacade.createTask(taskInitParam);
    }

    @Override
    public List<ChildAccount> selectReallocateList(String account_phone) {
        return followUpMsgMapper.selectReallocate(account_phone);
    }

    @Override
    public void insertCarsokRecord(FollowUpMsgRequest u,String change) {
        CarsokRecord carsokRecord = new CarsokRecord();
        carsokRecord.setModel("1");
        carsokRecord.setType(u.getLevel());
        String CFM="";
        if(!change.equals("")){
            CFM+=change;
        }
        CFM+=u.getCustomerFlowMessage();
        if(!StringUtil.isEmpty(u.getDefeat())){
            CFM+="\n"+u.getDefeat();
        }
        carsokRecord.setMessage(CFM);
        carsokRecord.setAccountId(Integer.valueOf(u.getAccount_id()));
        carsokRecord.setChildId(Integer.valueOf(u.getChild_id()));
        carsokRecord.setCreateTime(new Date());
        if(!StringUtil.isEmpty(u.getCustId())){
            carsokRecord.setOutId(Integer.parseInt(u.getCustId()));
        }
        carsokRecordMapper.insert(carsokRecord);
    }

/*    @Override
    public void insertNewMsgDistribution(FollowUpDistributionRequest u) {

//        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
//        u.setCreate_time(formatter1.format(new Date()));
//        followUpMsgMapper.insertMsg(u);
//        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        u.setUpdate_time(formatter2.format(new Date()));
//        followUpMsgMapper.updateMsg(u);

    }*/



    @Override
    public int followUpDistribution(Acount acount, FollowUpDistributionRequest u) {
        int a=0;
        if(!StringUtil.isEmpty(u.getLevel())){
            if(u.getLevel().equals("F 战败")||u.getLevel().equals("F0 战败待确认")){
                u.setLevel("N 24小时内回访");
                a=1;
            }
        }
        List<String> taskidList =new ArrayList<String>();
        if(StringUtil.isEmpty(u.getTaskId())){
            taskidList=followUpMsgMapper.selectTaskIdByCusId(u.getCustId());
        }else{
            taskidList.add(u.getTaskId());
        }

        //已成交, 只finish现任务, 不重建新任务
        if("D 已成交".equals(u.getLevel())){
            Iterator<String> it = taskidList.iterator();
            while(it.hasNext()){
                String selectTaskid=it.next();
                if (Integer.valueOf(selectTaskid)!=null&&Integer.valueOf(selectTaskid)!=0) {
                    if (Integer.valueOf(u.getChild_id()) == 0) {
                        iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), u.getAccount_id(), null);
                    } else {
                        iTaskFacade.finishTaskById(Integer.valueOf(selectTaskid), null, u.getChild_id());
                    }
                }
            }
        }else {
            //先结束现任务
            Iterator<String> it = taskidList.iterator();
            while(it.hasNext()){
                String selectTaskid=it.next();
                if (Integer.valueOf(selectTaskid)!=null&&Integer.valueOf(selectTaskid)!=0) {
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
            if(!StringUtil.isEmpty(u.getLevel())){
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
            if(!StringUtil.isEmpty(u.getLevel())){
                map.put("level",u.getLevel());
            }

            map.put("account_id",u.getAccount_id());
//                    查询登录人的子账号
//            CarsokCustomer carsokCustomer = new CarsokCustomer();
//            carsokCustomer.setId(Integer.valueOf(u.getCustId()));
//            carsokCustomer = carsokCustomer.selectById(Integer.valueOf(u.getCustId()));
            map.put("child_id",u.getId());
            taskInitParam.setExtraFields(map);
            iTaskFacade.createTask(taskInitParam);
//            修改task表
            Iterator<String> its = taskidList.iterator();
            while(its.hasNext()){
                String selectTaskid=its.next();
                if (!StringUtil.isEmpty(selectTaskid)){
                    iTaskFacade.updateExtraData(Integer.valueOf(selectTaskid),map,u.getAccount_id(),u.getId());
                }
            }
        }
        return a;
    }

    @Override
    public List<String> selectTaskid(String custId){
        return followUpMsgMapper.selectTaskIdByCusId(custId);
    }

    @Override
    public void posCusC(PosCusRequest u){

//        存保有carsok_customer_tenure_car表
        CarsokCustomer customer = carsokCustomerMapper.selectById(u.getCustId());
        CarsokCustomerTenureCar carsokCustomerTenureCarenurecar = new CarsokCustomerTenureCar();
        carsokCustomerTenureCarenurecar.setCustomerId(u.getCustId());
        carsokCustomerTenureCarenurecar.setTenureCarname(u.getBrand()+" "+u.getSeries()+" "+u.getModel());
        carsokCustomerTenureCarenurecar.setAccountId(customer.getAccountId());
        carsokCustomerTenureCarenurecar.setChildId(customer.getChildId());
        carsokCustomerTenureCarenurecar.setCreateTime(new Date());
        carsokCustomerTenureCarenurecar.setSaleTime(new Date());
        carsokCustomerTenureCarenurecar.setUpdateTime(new Date());
        carsokCustomerTenureCarenurecar.insert();
    }

    @Override
    public void testDrive(TestDriveRequest u){

//      存carsok_customer_testdrive_car表
        new CarsokCustomerTestdriveCar().delete(new EntityWrapper().eq("customer_id", u.getCustId()));
        List<CarName> list = u.getList();
        for (CarName s : list) {
            CarsokCustomerTestdriveCar c = new CarsokCustomerTestdriveCar();
            c.setCustomerId(u.getCustId());
            c.setModel(s.getModel());
            c.setSeries(s.getSeries());
            c.setBrand(s.getBrand());
            c.insert();
        }

//        存record表
        CarsokRecord carsokRecord = new CarsokRecord();
        carsokRecord.setModel("1");
        String CarName="";
        for (CarName s : list) {
            if(s.getBrand()!=null&&!s.getBrand().equals("")){
                CarName+=s.getBrand()+" ";
            }
            CarName+=s.getSeries()+" ";
            if(s.getModel()!=null&&!s.getModel().equals("")){
                CarName+=s.getModel();
            }
            CarName+="\n";
        }
        carsokRecord.setMessage("驾驶时长:"+u.getTDtime()+"                           "
                +u.getSatisfaction()+"\n"+CarName+u.getCustomerFlowMessage()
        );
        carsokRecord.setAccountId(u.getAccount_id());
        carsokRecord.setChildId(u.getChild_id());
        carsokRecord.setCreateTime(new Date());
        carsokRecord.setOutId(u.getCustId());
        carsokRecord.insert();

//        添加CarsokCustomerFlowmsg表
        CarsokCustomerFlowmsg carsokCustomerFlowmsg = new CarsokCustomerFlowmsg();
        carsokCustomerFlowmsg.setCreateTime(new Date());
        carsokCustomerFlowmsg.setCustomerFlowMessage(u.getCustomerFlowMessage());
        carsokCustomerFlowmsg.setCustomerId(u.getCustId());
        carsokCustomerFlowmsg.setAccountId(u.getAccount_id());
        carsokCustomerFlowmsg.setChildId(u.getChild_id());
        carsokCustomerFlowmsg.insert();

    }

    @Override
    public List<CarsokChildAccount> selectPowerByChildId(List<CarsokChildAccount> carsokChildAccounts){
        List<CarsokChildAccount> list =new ArrayList<CarsokChildAccount>();
        for (Iterator<CarsokChildAccount> it = carsokChildAccounts.iterator(); it.hasNext();) {
            CarsokChildAccount ow = it.next();
            List<CarsokAccountPower> powerList=new CarsokAccountPower().selectList(new EntityWrapper().eq("child_id",ow.getId()));
            List<String> power=new ArrayList<String>();
            for (Iterator<CarsokAccountPower> its = powerList.iterator(); its.hasNext();) {
                CarsokAccountPower oo= its.next();
                power.add(oo.getPowerName());
                its.remove();
            }
            if(power.contains("qkjlgl")||power.contains("qkyxgw")){
                list.add(ow);
            }
            it.remove();
        }
        return list;
    }













}
