package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarForgSale;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.CarFrogSaleService;
import com.uton.carsokApi.service.SaasAuthorityService;
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
 * 销售报表
 */
@Controller
public class SaasSaleReportController {

    private final static Logger logger =Logger.getLogger(SaasSaleReportController.class);

    @Autowired
    CarFrogSaleService carFrogSaleService;

    @Resource
    CacheService cacheService;

    @Autowired
    SaasAuthorityService authorityService;

    @Autowired
    ChildAccountMapper childAccountMapper;


    @RequestMapping("saleStatement")
    @ResponseBody
    public BaseResult SalesForg(HttpServletRequest request,ReportDateRequest vo) {
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
                    CarForgSale carForgSale = carFrogSaleService.SalesForg(vo,childAccounts,acount);
                    return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo,carForgSale);
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
            CarForgSale carForgSale = carFrogSaleService.SalesForg(vo,childAccounts,acount);
            return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo,carForgSale);
        }catch (Exception e) {
            logger.error("销售报表出错，错误信息为： "+e.getMessage());
            return BaseResult.exception(e.getMessage());
        }
    }
}
