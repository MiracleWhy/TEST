package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.ModularTransferRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.TransferAscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/22.
 */
@Controller
@RequestMapping("/transfer")
public class TransferAscriptionController {

    @Autowired
    TransferAscriptionService transferAscriptionService;

    @Resource
    CacheService cacheService;

    /**
     * 市场拓展订单转移
     * @param id
     * @param type
     * @return
     */
    @RequestMapping("/selectTransferMsg")
    @ResponseBody
    public Map selectTransferMsg(String id,String type){
        Map<String,Object> map = new HashMap();
        map.put("maps",transferAscriptionService.selectTransferAscriptionMsg(id,type));
        return map;
    }

    /**
     * 删除子帐号前查询订单数
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping("/selectOrders")
    @ResponseBody
    public BaseResult selectOrders(HttpServletRequest request, @RequestBody ModularTransferRequest vo){
        Acount acount = cacheService.getAcountInfoFromCache(request);
        int count = transferAscriptionService.selectTransferCount(acount.getId(),vo.getBeforeChildId());
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(count);
        return baseResult;
    }

    /**
     * 根据模块转移订单
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping("/modularTransfer")
    @ResponseBody
    public BaseResult modularTransfer(HttpServletRequest request, @RequestBody ModularTransferRequest vo){
        Acount acount = cacheService.getAcountInfoFromCache(request);
        return transferAscriptionService.transferByModular(vo,acount.getId());
    }

    /**
     * 根据模块转移订单
     * @param request
     * @return
     */
    @RequestMapping("/childListTransfer")
    @ResponseBody
    public BaseResult childListTransfer(HttpServletRequest request, @RequestBody ModularTransferRequest vo){
        Acount acount = cacheService.getAcountInfoFromCache(request);
        return transferAscriptionService.transferBychildList(acount.getAccount(),vo.getBeforeChildId());
    }

}
