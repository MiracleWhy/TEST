package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.DailyCheckResultRequest;
import com.uton.carsokApi.controller.request.DailyCheckerRequest;
import com.uton.carsokApi.controller.response.DailyCheckCheckerResult;
import com.uton.carsokApi.controller.response.DailyCheckerResponse;
import com.uton.carsokApi.controller.response.SubUserListResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.DailycheckChecker;
import com.uton.carsokApi.service.AreaService;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.DailyCheckService;
import com.uton.carsokApi.service.SubUserService;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DailyCheckController {
    private final static Logger logger = Logger.getLogger(DailyCheckController.class);

    @Autowired
    DailyCheckService dailyCheckService;
    @Autowired
    SubUserService subUserService;

    @Resource
    CacheService cacheService;

    /**
     * 更新日检人员表
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/updateCheckerList"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult updateCheckerList(HttpServletRequest request, @RequestBody DailyCheckerRequest dailyCheckerRequest) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            dailyCheckService.updateDailyChecker(acount.getId().toString(), dailyCheckerRequest.getCheckerList());
        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取日检人员表
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/getCheckerList"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getCheckerList(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            result.setData(dailyCheckService.getDailyChecker(acount.getId().toString()));
        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    /**
     * 日检人员分配
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/dailyCheckDispatcher"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult dailyCheckDispatcher(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            dailyCheckService.dailyCheckerDispatcher(acount.getId().toString(),acount.getAccount());

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    /**
     * 更新车辆检查结果
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/updateCheckerResult"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult updateCheckerResult(HttpServletRequest request, @RequestBody DailyCheckResultRequest dailyCheckResultRequest) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            dailyCheckService.updateCheckResult(dailyCheckResultRequest.getProductId(), dailyCheckResultRequest.getChecker(), dailyCheckResultRequest.getDate(), dailyCheckResultRequest.getResult());

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取车辆检查结果
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/getCheckerResult"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult getCheckerResult(HttpServletRequest request,@RequestBody DailyCheckResultRequest dailyCheckResultRequest) {
        BaseResult result = BaseResult.success();
        try {
            HashMap<String,Object> resultMap = new HashMap<>();
            Acount acount = cacheService.getAcountInfoFromCache(request);
            resultMap.put("check",dailyCheckService.getCheckResultByProductId(dailyCheckResultRequest.getProductId(),dailyCheckResultRequest.getDate()));
            resultMap.put("carInfo",dailyCheckService.getProductInfoByProductId(dailyCheckResultRequest.getProductId()));
            resultMap.put("picList",dailyCheckService.getPicListByProductId(dailyCheckResultRequest.getProductId()));
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    /**
     * 获取车辆列表
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/getDisaptcherList"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getDisaptcherList(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            HashMap<String,Integer> count = new HashMap<>();
            result.setData(dailyCheckService.getDispatcherList(acount.getId().toString(),acount.getSubPhone(),"",2,count));
        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取车辆列表
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/getDisaptcherListGroupBy"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getDisaptcherListGroupBy(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            Map<String,Object> resultMap = new HashMap<>();
            HashMap<String,Integer> count = new HashMap<>();
            resultMap.put("uncheck",dailyCheckService.getDispatcherList(acount.getId().toString(),acount.getSubPhone(),"",0,count));
            resultMap.put("checked",dailyCheckService.getDispatcherList(acount.getId().toString(),acount.getSubPhone(),"",1,count));
            resultMap.put("count",count);
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

/**
     * 获取车辆列表
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/getHistoryResult"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult getHistoryResult(HttpServletRequest request, @RequestBody String productId) {
        BaseResult result = BaseResult.success();
        try {
            HashMap<String,Object> resultMap = new HashMap<>();
            Acount acount = cacheService.getAcountInfoFromCache(request);
            resultMap.put("check",dailyCheckService.getHistoryResult(productId));
            resultMap.put("carInfo",dailyCheckService.getProductInfoByProductId(productId));
            resultMap.put("picList",dailyCheckService.getPicListByProductId(productId));
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    /**
     * 获取子账号今日已检
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/getCheckerResult"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getCheckerResult(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            HashMap<String,Object> resultMap = new HashMap<>();
            String checkDate = request.getParameter("checkDate");
            if(StringUtil.isEmpty(checkDate))
            {
                checkDate= DateUtil.getDate(new Date());
            }
            Acount acount = cacheService.getAcountInfoFromCache(request);
            result.setData(dailyCheckService.getCheckerResult(acount.getId().toString(),acount.getSubPhone(),checkDate));
        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    /**
     * 获取子账号今日已检
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/getResultList"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getResultList(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Map<String,Object> resultMap = new HashMap<>();
            String checkDate = request.getParameter("checkDate");
            if(StringUtil.isEmpty(checkDate))
            {
                checkDate= DateUtil.getDate(new Date());
            }
            Acount acount = cacheService.getAcountInfoFromCache(request);
            resultMap = dailyCheckService.getCheckedList(acount.getId().toString(),acount.getSubPhone(),checkDate);
            List<DailyCheckCheckerResult> dailyCheckCheckerResults = dailyCheckService.getUnCheckList(acount.getId().toString(),checkDate);
            resultMap.put("uncheckCount",dailyCheckCheckerResults.size());
            resultMap.put("uncheckList",dailyCheckCheckerResults);
            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }




}
