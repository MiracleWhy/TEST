package com.uton.carsokApi.controller;


import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.TofrontHistoryRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ICarsokAccountTofrontService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Controller
@RequestMapping("/carsokAccountTofront")
public class CarsokAccountTofrontController {

    private final static Logger logger = Logger.getLogger(CarsokCarCollectController.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    ICarsokAccountTofrontService carsokAccountTofrontService;

    @RequestMapping(value = "/tofrontHistory",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getTofrontHistoryData(HttpServletRequest request, @RequestBody TofrontHistoryRequest tofrontHistoryRequest){
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        Integer accountId = null;
        Integer childId = null;
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        }else{
            accountId = acount.getId();
        }
        ChildAccount childAccount = null;
        if(!StringUtils.isEmpty(request.getHeader("subToken"))){
            childAccount = cacheService.getSubAcountInfoFromCache(request);
            if (childAccount == null) {
                return BaseResult.exception("请重新登陆");
            }else{
                childId = childAccount.getId();
            }
        }
        try {
            result.setData(carsokAccountTofrontService.getTofrontHistoryData(accountId,childId,tofrontHistoryRequest.getPageNum(),tofrontHistoryRequest.getPageSize()));
        } catch (Exception e) {
            logger.error("getTofrontHistoryData error:", e);
            return BaseResult.exception("系统异常！");
        }

        return result;
    }
}
