package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.PotCusResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.service.SaasPotCusReportService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 潜客报表
 */
@Controller
public class SaasPotCusReportController {

    private final static Logger logger = Logger.getLogger(SaasPotCusReportController.class);

    @Resource
    CacheService cacheService;

    @Resource
    SaasPotCusReportService  potCusReportService;

    @Autowired
    SaasAuthorityService authorityService;

    @Autowired
    ChildAccountMapper childAccountMapper;


    @RequestMapping("getPotCusPage")
    @ResponseBody
    public BaseResult getPotCusPage(HttpServletRequest request,ReportDateRequest dateParam){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if(acount==null){
                return new BaseResult(ErrorCode.ErrorRetCode,"登录用户token异常",null);
            }
            if(acount.getSubPhone()!=null){
                CarsokChildAccount child=childAccountMapper.selectOneByChildMobile(acount.getSubPhone());
                String authority=authorityService.queryAuthorityOfUser(child.getId());
                if(authority==null){//无查看权限
                    return new BaseResult(ErrorCode.HaveNoPermission,ErrorCode.HaveNoPermissionInfo,null);
                }
                if(authority.equals("Staff")){//员工权限
                    List<CarsokChildAccount> childAccounts=new ArrayList<>();
                    childAccounts.add(child);
                    PotCusResponse potCusResponse=potCusReportService.getPotCusPage(acount,childAccounts,dateParam);
                    return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo,potCusResponse);
                }
            }
            //管理者权限子账号+主账号
            List<CarsokChildAccount> childAccounts= childAccountMapper.selectListByAccountPhone(acount.getAccount());
            //将主账号插入到子账号列表中
            CarsokChildAccount account = new CarsokChildAccount();
            account.setId(0);
            account.setChildAccountName("主账号");
            account.setAccountPhone(acount.getAccount());
            account.setChildAccountMobile(acount.getAccount());
            if(childAccounts==null){
                childAccounts=new ArrayList<>();
            }
            childAccounts.add(account);
            //查询所有数据
            PotCusResponse potCusResponse=potCusReportService.getPotCusPage(acount,childAccounts,dateParam);
            return new BaseResult(ErrorCode.SuccessRetCode,ErrorCode.SuccessRetInfo,potCusResponse);
        } catch (Exception e) {
            logger.error("获取潜客报表页 getPotCusPage  error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

}
