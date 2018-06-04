package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.DealReportResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.service.SaasDealReportService;
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
 * Created by SEELE on 2017/12/12.
 */
@Controller
public class SaasDealReportController {

    private final static Logger logger = Logger.getLogger(SaasDealReportController.class);

    @Resource
    CacheService cacheService;

    @Autowired
    SaasDealReportService dealReportService;

    @Autowired
    SaasAuthorityService authorityService;

    @Autowired
    ChildAccountMapper childAccountMapper;

    /**
     *
     * @param request
     * @param dateParam type=1:日报  type=2:周报  type=3:月报  type=4:年报  时间参数
     *                  （周报示例：2017-12,1 代表2017年12月第一周）
     *                  （日报示例：2017-12-12）
     *                  （月报示例：2017-12）
     *                  （年报示例：2017）
     * @return
     */
    @RequestMapping("getDealReportPage")
    @ResponseBody
    public BaseResult getDealReportPage(HttpServletRequest request, ReportDateRequest dateParam){
        try{
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
                    DealReportResponse dealReportResponse=dealReportService.getDealReportPage(acount,childAccounts,dateParam);
                    return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo,dealReportResponse);
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
            DealReportResponse dealReportResponse=dealReportService.getDealReportPage(acount,childAccounts,dateParam);
            return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo,dealReportResponse);
        }catch (Exception e){
            logger.error("获取成交报表接口 getDealReportPage  error:", e);
            e.printStackTrace();
            return BaseResult.exception(e.getMessage());
        }
    }


}
