package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.RegionReportResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.service.SaasRegionReportService;
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
 * Created by SEELE on 2017/12/14.
 */
@Controller
public class SaasRegionReportController {


    private final static Logger logger = Logger.getLogger(SaasRegionReportController.class);

    @Resource
    CacheService cacheService;

    @Autowired
    SaasRegionReportService regionReportService;

    @Autowired
    SaasAuthorityService authorityService;

    @Autowired
    ChildAccountMapper childAccountMapper;


    @RequestMapping("getRegionReportPage")
    @ResponseBody
    public BaseResult getRegionPage(HttpServletRequest request, ReportDateRequest dateParams){
        try {
            if(dateParams.getType()==2&&dateParams.getDate().split(",").length!=2){
                return BaseResult.exception("(报表参数不正确)");
            }
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
                    RegionReportResponse regionResponse=regionReportService.getRegionPage(acount,childAccounts,dateParams);
                    return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo,regionResponse);
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
            RegionReportResponse regionResponse=regionReportService.getRegionPage(acount,childAccounts,dateParams);
            return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo,regionResponse);

        } catch (Exception e) {
            logger.error("获取区域报表页 getRegionPage  error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

}
