package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.ModularTransferRequest;
import com.uton.carsokApi.controller.response.TransferCountResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.TransferAscriptionMapper;
import com.uton.carsokApi.model.CarsokCustomerTenureCar;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.TransferAscription;
import com.uton.carsokApi.service.ICarsokCustomerTenureCarService;
import com.uton.carsokApi.service.ITaskFacade;
import com.uton.carsokApi.service.TransferAscriptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/22.
 */
@Service
public class TransferAscriptionServiceImpl implements TransferAscriptionService {

    private final static Logger logger = Logger.getLogger(TransferAscriptionServiceImpl.class);

    @Autowired
    TransferAscriptionMapper transferAscriptionMapper;

    @Autowired
    ChildAccountMapper childAccountMapper;

    @Autowired
    private ICarsokCustomerTenureCarService iCarsokCustomerTenureCarService;

    @Autowired
    private ITaskFacade iTaskFacade;

    @Override
    public void insertTransferMsg(TransferAscription transferAscription) {
        transferAscriptionMapper.insertTransferAscription(transferAscription);
    }

    @Override
    public List<TransferAscription> selectTransferAscriptionMsg(String id,String type) {
        return transferAscriptionMapper.selectTransferMsg(id,type);
    }

    @Override
    public int selectTransferCount(int accountId, int childId) {
        int count = 0;
        try{
            TransferCountResponse response = transferAscriptionMapper.selectNewTransferCount(accountId,childId);
            //新版本, baoyouCount是所有客户的数量(包含潜客和保有), shoucheCount是收车的数量
            count = response.getBaoyouCount() + response.getShoucheCount();
            logger.info("--------保有："+response.getBaoyouCount()+"--------");
//            TransferCountResponse response = transferAscriptionMapper.selectTransferCount(accountId,childId);
//            count = response.getMendianCount()+response.getBaoyouCount()+response.getShoucheCount();
//            logger.info("门店："+response.getMendianCount()+"--------保有："+response.getBaoyouCount()+"--------收车："+response.getShoucheCount());
        }catch (Exception e){
            logger.info(e);
        }
        return count;
    }

    @Override
    public BaseResult transferByModular(ModularTransferRequest vo,int accountId) {
        BaseResult baseResult = BaseResult.success();
        try {
            //转移客户
            List<Integer> customerList = transferAscriptionMapper.selectModularIds("carsok_customer",accountId,vo.getBeforeChildId());
            transfer("carsok_customer",vo.getAfterChildId(),vo.getBeforeChildId(),customerList);
            //循环客户, 处理客户的保有车辆
            for(Integer customerId : customerList){
                List<CarsokCustomerTenureCar> carList = iCarsokCustomerTenureCarService.selectList(new EntityWrapper<CarsokCustomerTenureCar>().eq("customer_id", customerId));
                //循环, 遍历出business_id是carID的所有保有任务
                for (CarsokCustomerTenureCar car : carList){
                    //更新车的acountId和childID
                    car.setAccountId(accountId);
                    car.setChildId(vo.getAfterChildId());
                    iCarsokCustomerTenureCarService.update(car, new EntityWrapper().eq("id", car.getId()));

                    EntityWrapper<CarsokTenureTask> ew = new EntityWrapper<CarsokTenureTask>();
                    ew.eq("business_id", car.getId()).like("module", "retaincustomer").in("task_status", new String[]{"ready", "delay"});
                    PageInfo<CarsokTenureTask> retainTaskList = iTaskFacade.queryTaskByEntityWrapper(0, 0, ew);
                    //循环任务list, 更新任务的extra_fields
                    for (CarsokTenureTask task : retainTaskList.getList()){
                        Map oldmap = JSON.parseObject(task.getExtraFields());
                        oldmap.put("accountId",accountId);
                        oldmap.put("childId",vo.getAfterChildId());
                        iTaskFacade.updateExtraData(task.getId(), oldmap, null, null);
                    }
                }
                //处理这个客户的任务
                //1.查出business_id是customerID的生日关怀保有任务
                EntityWrapper<CarsokTenureTask> entityWrapper = new EntityWrapper<CarsokTenureTask>();
                entityWrapper.eq("business_id", customerId).like("type", "birthday_solicitude").in("task_status", new String[]{"ready", "delay"});
                PageInfo<CarsokTenureTask> taskBirthday = iTaskFacade.queryTaskByEntityWrapper(0, 0, entityWrapper);
                //循环, 更新任务的extra_fields
                for (CarsokTenureTask task : taskBirthday.getList()){
                    Map oldmap = JSON.parseObject(task.getExtraFields());
                    oldmap.put("accountId",accountId);
                    oldmap.put("childId",vo.getAfterChildId());
                    iTaskFacade.updateExtraData(task.getId(), oldmap, null, null);
                }
                //2.查出business_id是customerID的所有潜客任务
                EntityWrapper<CarsokTenureTask> entity = new EntityWrapper<CarsokTenureTask>();
                entity.eq("business_id", customerId).like("module", "potentialcustomer").in("task_status", new String[]{"ready", "delay"});
                PageInfo<CarsokTenureTask> taskQianke = iTaskFacade.queryTaskByEntityWrapper(0, 0, entity);
                //循环, 更新任务的extra_fields
                for (CarsokTenureTask task : taskQianke.getList()){
                    Map oldmap = JSON.parseObject(task.getExtraFields());
                    oldmap.put("account_id",String.valueOf(accountId));
                    oldmap.put("child_id",String.valueOf(vo.getAfterChildId()));
                    iTaskFacade.updateExtraData(task.getId(), oldmap, null, null);
                }
            }

//            List<Integer> baoyouIdList = transferAscriptionMapper.selectModularIds("carsok_customer_trnure_car",accountId,vo.getBeforeChildId());
            List<Integer> shoucheIdList = transferAscriptionMapper.selectModularIds("carsok_acquisition_car",accountId,vo.getBeforeChildId());
//            List<Integer> mendianIdList = transferAscriptionMapper.selectModularIds("carsok_customer_manage",accountId,vo.getBeforeChildId());
//            transfer("carsok_customer_trnure_car",vo.getAfterChildId(),vo.getBeforeChildId(),baoyouIdList);
            transfer("carsok_acquisition_car",vo.getAfterChildId(),vo.getBeforeChildId(),shoucheIdList);
//            transfer("carsok_customer_manage",vo.getAfterChildId(),vo.getBeforeChildId(),mendianIdList);
        }catch (Exception e){
            logger.error(e);
            baseResult.fail("0001","系统异常");
            return baseResult;
        }
        return baseResult;
    }


    public void transfer(String tableName,int childIdAfter,int childIdBefore,List<Integer> idLists){
        try {
            transferAscriptionMapper.transferByModular(tableName,childIdAfter,idLists);
            TransferAscription transferAscription = new TransferAscription();
            for(Integer ids:idLists){
                transferAscription.setTransferBefore(String.valueOf(childIdBefore));
                transferAscription.setTransferAfter(String.valueOf(childIdAfter));
                transferAscription.setTransferType(tableName);
                transferAscription.setTransferId(ids);
                transferAscription.setCreateTime(new Date());
                transferAscriptionMapper.insertTransferAscription(transferAscription);
            }
            childAccountMapper.deleteByPrimaryKey(childIdBefore);
        }catch (Exception e){
            logger.error(e);
        }
    }

    @Override
    public BaseResult transferBychildList(String account,int childId) {
        BaseResult baseResult = BaseResult.success();
        try{
            List<ChildAccount> childList = transferAscriptionMapper.selectChildListByTransfer(account,childId);
            baseResult.setData(childList);
        }catch (Exception e){
            logger.error(e);
        }
        return baseResult;
    }
}
