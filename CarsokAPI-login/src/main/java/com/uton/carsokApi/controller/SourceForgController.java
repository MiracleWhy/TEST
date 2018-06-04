package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.SalesForg;
import com.uton.carsokApi.model.SourceForgSum;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.service.SourceForgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
/**
 * 来源报表
 */
public class SourceForgController {

    @Autowired
    SaasAuthorityService authorityService;

    @Autowired
    ChildAccountMapper ChildAccountMapper;

    @Autowired
    SourceForgService sourceForgService;

    @Resource
    CacheService cacheService;


    @RequestMapping("/geSourcePage")
    @ResponseBody
        public BaseResult selectSource(HttpServletRequest request,SalesForg salesForg){
            try {
                Acount acount = cacheService.getAcountInfoFromCache(request);
                if(acount==null){
                    return new BaseResult(ErrorCode.ErrorRetCode,"登录用户token异常",null);
                }
                List<ChildAccount> childAccounts=authorityService.queryTargetChildAccounts(acount);
                if(childAccounts==null){//无查看权限
                    return new BaseResult(ErrorCode.HaveNoPermission,ErrorCode.HaveNoPermissionInfo,null);
                }
                SourceForgSum sourceForgSum = sourceForgService.salesForg(request,salesForg);
                return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo,sourceForgSum);
            }catch (Exception e) {
                return BaseResult.exception(e.getMessage());
            }
        }
}
