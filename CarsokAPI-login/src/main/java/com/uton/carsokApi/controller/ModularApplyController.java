package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.ModularApplyRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ModularApply;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ModularApplyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/23.
 */
@Controller
public class ModularApplyController {

    private final static Logger logger = Logger.getLogger(ModularApplyController.class);

    @Autowired
    ModularApplyService modularApplyService;

    @Resource
    CacheService cacheService;

    @RequestMapping("/applyModular")
    @ResponseBody
    public BaseResult applyModular(HttpServletRequest request,@RequestBody ModularApplyRequest vo){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            vo.setAccountId(acount.getId());
            BaseResult baseResult = modularApplyService.applyModular(vo);
            return baseResult;
        }catch (Exception e) {
            logger.error("applyModular error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping("/selectApplyModular")
    @ResponseBody
    public BaseResult selectApplyModular(HttpServletRequest request,@RequestBody ModularApply vo){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            vo.setAccountId(acount.getId());
            BaseResult baseResult = modularApplyService.selectApplyModular(vo);
            return baseResult;
        }catch (Exception e) {
            logger.error("selectApplyModular error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping("/selectAllApplyStatus")
    @ResponseBody
    public BaseResult selectAllApplyStatus(HttpServletRequest request){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            BaseResult baseResult = modularApplyService.selectAllApplyStatus(acount.getId());
            return baseResult;
        }catch (Exception e) {
            logger.error("selectAllApplyStatus error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

}
