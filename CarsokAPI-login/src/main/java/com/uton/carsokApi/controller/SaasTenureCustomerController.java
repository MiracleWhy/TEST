package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CreateCarServeInfoRequest;
import com.uton.carsokApi.controller.request.FindDetailInfoRequest;
import com.uton.carsokApi.controller.request.FindListRequest;
import com.uton.carsokApi.controller.request.SaveCarAndCustomeInfoRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokCustomerTenureServe;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.SaasTenureCustomerService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/4/13 0013.
 */
@Controller
@RequestMapping("/SaasTenureCustomer")
public class SaasTenureCustomerController {

    private final static Logger logger = Logger.getLogger(SaasTenureCustomerController.class);

    @Autowired
    SaasTenureCustomerService saasTenureCustomerService;

    @Autowired
    CacheService cacheService;

    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult findList(HttpServletRequest request, @RequestBody FindListRequest vo) {
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            int childId = 0;
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
                childId = childAccount.getId();
            }
            return saasTenureCustomerService.findList(vo, acount, childId);
        } catch (Exception e) {
            logger.error("findList error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveDetailInfo", method = RequestMethod.POST)
    public BaseResult saveDetailInfo(HttpServletRequest request, @RequestBody SaveCarAndCustomeInfoRequest vo) {
        try {
            if (vo.getCustomerId() != 0 && vo.getId() != 0) {
                //如果得到的CutomerId和getTenureCarId都不等于0，则是修改操作。
                int type = 1;
                Acount acount = cacheService.getAcountInfoFromCache(request);
                int accountId = acount.getId();
                int childId = 0;
                if (!StringUtil.isEmpty(acount.getSubPhone())) {
                    //如果账号的SubPhone不为空，则代表是子帐号操作。
                    ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
                    childId = childAccount.getId();
                }
                vo.setAccountId(accountId);
                vo.setChildId(childId);
               return saasTenureCustomerService.insertOrUpdateInfo(vo, type);

            } else {
                //否则则为添加操作。
                //获取账号信息。
                int type = 0;
                Acount acount = cacheService.getAcountInfoFromCache(request);
                int accountId = acount.getId();
                int childId = 0;
                if (!StringUtil.isEmpty(acount.getSubPhone())) {
                    //如果账号的SubPhone不为空，则代表是子帐号操作。
                    ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
                    childId = childAccount.getId();
                }
                vo.setAccountId(accountId);
                vo.setChildId(childId);
               return saasTenureCustomerService.insertOrUpdateInfo(vo, type);

            }
        } catch (Exception e) {
            logger.error("saveDetailInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value = "findDetailInfo",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult findDetailInfo (HttpServletRequest request, @RequestBody FindDetailInfoRequest vo){
       try {
           Acount acount = cacheService.getAcountInfoFromCache(request);
           int accountId = acount.getId();
           int childId = 0;
           if (!StringUtil.isEmpty(acount.getSubPhone())) {
               //如果账号的SubPhone不为空，则代表是子帐号操作。
               ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
               childId = childAccount.getId();
           }
           vo.setAccountId(accountId);
           vo.setChildId(childId);
           return saasTenureCustomerService.findDetailInfo(vo);
       }catch (Exception e){
           return BaseResult.exception(e.getMessage());
       }
    }

    /**
     * 逾期任务一键提醒
     * @param request
     * @Arthur zhangyugong 2017-12-21
     * @return
     */
    @RequestMapping(value = "OneKeyRemind")
    @ResponseBody
    public BaseResult OneKeyRemind (HttpServletRequest request){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            ChildAccount childAccount = null;
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                //如果账号的SubPhone不为空，则代表是子帐号操作。
                childAccount = cacheService.getSubAcountInfoFromCache(request);
            }
            return saasTenureCustomerService.pushOutDateTaskRemind(acount, childAccount);
        }catch (Exception e){
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 逾期任务查看
     * @param request
     * @Arthur zhangyugong 2017-12-21
     * @return
     */
    @RequestMapping(value = "OneKeyRemindShow")
    @ResponseBody
    public BaseResult OneKeyRemindShow (HttpServletRequest request, String timeParam){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            ChildAccount childAccount = null;
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                //如果账号的SubPhone不为空，则代表是子帐号操作。
                childAccount = cacheService.getSubAcountInfoFromCache(request);
            }
            return saasTenureCustomerService.OneKeyRemindShow(acount, childAccount, timeParam);
        }catch (Exception e){
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 待办通知和公司通知是否可见-接口
     * @param request
     * @Arthur zhangyugong 2017-12-21
     * @return
     */
    @RequestMapping(value = "canMessageShow")
    @ResponseBody
    public BaseResult canMessageShow (HttpServletRequest request){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            ChildAccount childAccount = null;
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                //如果账号的SubPhone不为空，则代表是子帐号操作。
                childAccount = cacheService.getSubAcountInfoFromCache(request);
            }
            return saasTenureCustomerService.canMessageShow(acount, childAccount);
        }catch (Exception e){
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 保有客户服务上侧信息+服务列表查询接口
     * @param vo
     * @return
     */
    @RequestMapping(value = "getCarServeInfo",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getCarServeInfo(@RequestBody CreateCarServeInfoRequest vo){
        try {
            return saasTenureCustomerService.getCarServeInfo(vo);
         } catch (Exception e){
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 保有客户服务信息保存接口
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping(value = "createCarServe",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult createCarServe(@RequestBody CarsokCustomerTenureServe vo, HttpServletRequest request){
           try{
               Acount acount = cacheService.getAcountInfoFromCache(request);
               int accountId = acount.getId();
               int childId = 0;
               if (!StringUtil.isEmpty(acount.getSubPhone())) {
                   //如果账号的SubPhone不为空，则代表是子帐号操作。
                   ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
                   childId = childAccount.getId();
               }
               vo.setAccountId(accountId);
               vo.setChildId(childId);
              return saasTenureCustomerService.createCarServe(vo);
           }catch (Exception e) {
                return BaseResult.exception(e.getMessage());
           }
    }

//    @RequestMapping(value = "testrun",method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResult testrun (@RequestBody CarsokCustomer carsokCustomer){
//        try {
//            saasTenureCustomerService.runCustomerTask(carsokCustomer, null);
//        }catch (Exception e){
//            return BaseResult.exception(e.getMessage());
//        }
//        return BaseResult.success();
//    }


//    @RequestMapping(value = "moveOldVersionBaoyouData",method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResult moveOldVersionBaoyouData (){
//        try {
//            return saasTenureCustomerService.moveOldVersionData();
//        }catch (Exception e){
//            return BaseResult.exception(e.getMessage());
//        }
//    }
//    @RequestMapping(value = "moveOldVersionQiankeData",method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResult moveOldVersionQiankeData (){
//        try {
//            return saasTenureCustomerService.moveOldVersionQiankeData();
//        }catch (Exception e){
//            return BaseResult.exception(e.getMessage());
//        }
//    }
//    @RequestMapping(value = "dealDecBaoyouCustomerTenureData",method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResult dealDecBaoyouCustomerTenureData (){
//        try {
//            return saasTenureCustomerService.dealDecBaoyouCustomerTenureData();
//        }catch (Exception e){
//            return BaseResult.exception(e.getMessage());
//        }
//    }
//    @RequestMapping(value = "dealNovBaoyouCustomerTenureData",method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResult dealNovBaoyouCustomerTenureData (){
//        try {
//            return saasTenureCustomerService.dealNovBaoyouCustomerTenureData();
//        }catch (Exception e){
//            return BaseResult.exception(e.getMessage());
//        }
//    }

//    @RequestMapping(value = "clearOldTask",method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResult clearOldTask (HttpServletRequest request){
//        try {
//            saasTenureCustomerService.clearOldTask();
//        }catch (Exception e){
//            return BaseResult.exception(e.getMessage());
//        }
//        return BaseResult.success();
//    }
}
