package com.uton.carsokApi.controller;


import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CarCollectRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ICarsokAccountCollectService;
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
@RequestMapping("/carsokAccountCollect")
public class CarsokAccountCollectController {
    private final static Logger logger = Logger.getLogger(CarsokCarCollectController.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    ICarsokAccountCollectService carsokAccountCollectService;

    /**
     * 获取车行收藏列表
     * @param request
     * @param carCollectRequest
     * @return
     */
    @RequestMapping(value = "/accountCollectList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getAccountCollectList(HttpServletRequest request, @RequestBody CarCollectRequest carCollectRequest){
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        //subtoken不为空，获取子账号id
        if(!StringUtils.isEmpty(request.getHeader("subToken"))){
            ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
            if (childAccount == null) {
                return BaseResult.exception("请重新登陆");
            } else {
                carCollectRequest.setChildId(childAccount.getId());
            }
        }
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        } else {
            carCollectRequest.setAccountId(acount.getId());
        }
        try {
            result.setData(carsokAccountCollectService.getAccountCollectList(carCollectRequest));
        } catch (Exception e) {
            logger.error("getCarCollectList error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

	
}
