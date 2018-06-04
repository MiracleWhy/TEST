package com.uton.carsokApi.controller;


import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ICarsokQuoteService;
import com.uton.carsokApi.service.ICarsokTofrontPlanService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Controller
@RequestMapping("/carsokTofrontPlan")
public class CarsokTofrontPlanController {
    private final static Logger logger = Logger.getLogger(CarsokTofrontPlanController.class);

    @Autowired
    ICarsokTofrontPlanService carsokTofrontPlanService;

    @Resource
    CacheService cacheService;

    @Autowired
    ICarsokQuoteService carsokQuoteService;

    /**
     * 车源置顶套餐查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCarTofrontPlan",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getCarTofrontPlan(HttpServletRequest request){
        BaseResult result = BaseResult.success();
        try {
            result.setData(carsokTofrontPlanService.getCarTofrontPlan());
        } catch (Exception e) {
            logger.error("getCarTofrontPlan error:", e);
            return BaseResult.exception("系统错误");
        }
        return result;
    }

    /**
     * 车行置顶套餐查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAccountTofrontPlan",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getAccountTofrontPlan(HttpServletRequest request){
        BaseResult result = BaseResult.success();
        try {
            result.setData(carsokTofrontPlanService.getAccountTofrontPlan());
        } catch (Exception e) {
            logger.error("getAccountTofrontPlan error:", e);
            return BaseResult.exception("系统错误");
        }
        return result;
    }

    /**
     * 报价/置顶消息权限显示查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/isShow")
    @ResponseBody
    public BaseResult isShow(HttpServletRequest request){
        BaseResult result = BaseResult.success();
        Map map = new HashMap();
        Boolean tofront = false;
        Boolean quote = false;
        if(!StringUtils.isEmpty(request.getHeader("token"))){
            tofront = true;
            quote = true;
        }
        if(!StringUtils.isEmpty(request.getHeader("subToken"))){
            ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
            if(childAccount != null){
                int tofrontCount = carsokTofrontPlanService.getIsShow(childAccount.getId(),"zdtg");
                if(tofrontCount == 1){
                    tofront = true;
                }
                int quoteCount = carsokTofrontPlanService.getIsShow(childAccount.getId(),"xc");
                if(quoteCount == 1){
                    quote = true;
                }
            }
        }
        map.put("tofront",tofront);
        map.put("quote",quote);
        result.setData(map);
        return result;
    }
	
}
