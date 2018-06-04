package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.PurposeReturnSum;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.PurposeForgService;
import com.uton.carsokApi.service.SaasAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 意向报表
 */
@Controller
public class PurposeForgController {

    @Autowired
    PurposeForgService purposeForgService;
    @Resource
    CacheService cacheService;

    @Autowired
    SaasAuthorityService authorityService;


    @RequestMapping("/getIntentionPage")
    @ResponseBody
    public BaseResult SalesForg(HttpServletRequest request) {
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if(acount==null){
                return new BaseResult(ErrorCode.ErrorRetCode,"登录用户token异常",null);
            }
            List<ChildAccount> childAccounts=authorityService.queryTargetChildAccounts(acount);
            if(childAccounts==null){//无查看权限
                return new BaseResult(ErrorCode.HaveNoPermission,ErrorCode.HaveNoPermissionInfo,null);
            }
            PurposeReturnSum purposeReturnSum = purposeForgService.selectCustomerCount(request);
            return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo,purposeReturnSum);
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
    }
}
