package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.ModularApplyController;
import com.uton.carsokApi.controller.request.ModularApplyRequest;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ModularApplyMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ModularApply;
import com.uton.carsokApi.service.ModularApplyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/10/23.
 */
@Service
public class ModularApplyServiceImpl implements ModularApplyService {

    private final static Logger logger = Logger.getLogger(ModularApplyController.class);

    @Autowired
    ModularApplyMapper modularApplyMapper;

    @Autowired
    AcountMapper acountMapper;

    @Override
    public BaseResult applyModular(ModularApplyRequest modularApplyRequest) {
        try {
            BaseResult baseResult = BaseResult.success();
            Acount acount = new Acount();
            acount.setId(modularApplyRequest.getAccountId());
            acount.setMerchantSize(modularApplyRequest.getMerchantSize());
            acount.setEmployeeNum(modularApplyRequest.getEmployeeNum());
            acount.setContactName(modularApplyRequest.getContactName());
            acount.setContactMobile(modularApplyRequest.getContactMobile());
            acount.setMerchantAddress(modularApplyRequest.getMerchantAddress());
            acountMapper.updateAccountForApplyPro(acount);
            ModularApply modularApplys = modularApplyMapper.selectByTypeAndAccountId(modularApplyRequest.getAccountId(),modularApplyRequest.getType());
            if(modularApplys == null){
                ModularApply modularApply = new ModularApply();
                modularApply.setAccountId(modularApplyRequest.getAccountId());
                modularApply.setType(modularApplyRequest.getType());
                modularApply.setApplyIf(2);
                modularApply.setLastUseTime(new Date());
                modularApply.setCreateTime(new Date());
                modularApply.setLastUseTime(new Date());
                modularApply.setApplyTime(new Date());
                int insert = modularApplyMapper.insertSelective(modularApply);
                if(insert>0){
                    baseResult.setData(insert);
                }else {
                    baseResult.fail("0001","申请审核失败");
                }
                logger.info("申请人id："+modularApply.getAccountId()+"  申请模块："+modularApply.getType()+"  申请时间："+modularApply.getApplyTime());
            }else if(modularApplys !=null && (modularApplys.getApplyIf() == 4 || modularApplys.getApplyIf() == 3)){
                ModularApply modularApply = new ModularApply();
                modularApply.setId(modularApplys.getId());
                modularApply.setApplyIf(2);
                modularApplyMapper.updateByPrimaryKeySelective(modularApply);
            }else {
                baseResult.fail("0002","请勿重复提交信息");
            }
            return baseResult;
        }catch (Exception e) {
            logger.error("applyModular error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @Override
    public BaseResult selectApplyModular(ModularApply modularApply) {
        try {
            BaseResult baseResult = BaseResult.success();
            Map<String,Object> map = new HashMap<>();
            Acount acount = acountMapper.selectByPrimaryKey(modularApply.getAccountId());
            ModularApply modular = modularApplyMapper.selectByTypeAndAccountId(modularApply.getAccountId(),modularApply.getType());
            if(modular!=null){
                //如果是已经可以使用的状态
                if(modular.getApplyIf()==1){
                    //判断是否超过15天
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date beginDate= format.parse(format.format(modular.getLastUseTime()));
                    Date endDate= format.parse(format.format(new Date()));
                    long day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
                    if(day>15){
                        //超过15天置为过期状态
                        ModularApply modu = new ModularApply();
                        modu.setId(modular.getId());
                        modu.setApplyIf(4);
                        modularApplyMapper.updateByPrimaryKeySelective(modu);
                        map.put("applyStatus","4");
                        map.put("applyMsg",acount);
                        baseResult.setData(map);
                    }else {
                        //否则返回当前状态值
                        map.put("applyStatus",modular.getApplyIf());
                        map.put("applyMsg",acount);
                        baseResult.setData(map);
                    }
                }else{
                    //否则返回当前状态值
                    map.put("applyStatus",modular.getApplyIf());
                    map.put("applyMsg",acount);
                    baseResult.setData(map);
                }
                ModularApply modularApplys = new ModularApply();
                modularApplys.setId(modular.getId());
                modularApplys.setLastUseTime(new Date());
                modularApplyMapper.updateByPrimaryKeySelective(modularApplys);
            }else{
                map.put("applyStatus",0);
                map.put("applyMsg",acount);
                baseResult.setData(map);
            }
            return baseResult;
        }catch (Exception e) {
            logger.error("selectApplyModular error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @Override
    public BaseResult selectAllApplyStatus(int accountId) {
        try{
            BaseResult baseResult = BaseResult.success();
            Map<String,Integer> map = new HashMap<>();
            List<String> modularApplyList = modularApplyMapper.selectAllApplyList(accountId);
            baseResult.setData(modularApplyList);
            return baseResult;
        }catch (Exception e){
            logger.error("selectAllApplyStatus error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }


}
