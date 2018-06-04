package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.OverdueForgModel;
import com.uton.carsokApi.model.SalesForg;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.OverdueForgService;
import com.uton.carsokApi.service.SaasAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20/020.
 */
@Controller
public class OverdueForgController {
    @Resource
    CacheService cacheService;
    @Autowired
    OverdueForgService overdueForgService;
    @Autowired
    SaasAuthorityService authorityService;
    @RequestMapping("/getOverdue")
    @ResponseBody
    public BaseResult selectSource(HttpServletRequest request){
        try{ Acount acount = cacheService.getAcountInfoFromCache(request);
            if(acount==null){
                return new BaseResult(ErrorCode.ErrorRetCode,"登录用户token异常",null);
            }
            List<ChildAccount> childAccounts=authorityService.queryTargetChildAccounts(acount);
            if(childAccounts==null){//无查看权限
                return new BaseResult(ErrorCode.HaveNoPermission,ErrorCode.HaveNoPermissionInfo,null);
            }
            BaseResult baseResult = BaseResult.success();
            OverdueForgModel overdueForgModel= overdueForgService.overdue(acount);
            baseResult.setData(overdueForgModel);
            return baseResult;
        }catch (Exception e){
            return BaseResult.exception(e.getMessage());
        }
    }

}
