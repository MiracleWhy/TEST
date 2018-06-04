package com.uton.carsokApi.controller;


import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.InventoryRequest;
import com.uton.carsokApi.controller.request.NewCarDetailsRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ICarsokProductService;
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
 * 库存管理
 * </p>
 *
 * @author csw
 * @since 2017-11-08
 */
@Controller
@RequestMapping("/inventoryManage")
public class CarsokProductController {

    private final static Logger logger = Logger.getLogger(CarsokProductController.class);

    @Autowired
    ICarsokProductService carsokProductService;

    @Autowired
    private CacheService cacheService;

    /**
     * 获取车辆管理首页列表
     * @param request
     * @param inventoryRequest
     * @return
     */
    @RequestMapping(value = "/getInventoryList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getInventoryList(HttpServletRequest request, @RequestBody InventoryRequest inventoryRequest){
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        }else{
            inventoryRequest.setAccountId(acount.getId().toString());
        }
        try {
            result.setData(carsokProductService.getInventoryList(inventoryRequest));
        } catch (Exception e) {
            logger.error("getInventoryList error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取车辆详情
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCarDetails",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getCarDetails(HttpServletRequest request,String id){
        BaseResult result = BaseResult.success();
        try {
            result.setData(carsokProductService.getCarDetails(id));
        } catch (Exception e) {
            logger.error("getCarDetails error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 编辑车辆详情
     * @param request
     * @param newCarDetailsRequest
     * @return
     */
    @RequestMapping(value = "/editCarDetails",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult editCarDetails(HttpServletRequest request,@RequestBody NewCarDetailsRequest newCarDetailsRequest){
        BaseResult result = BaseResult.success();
        try {
            carsokProductService.editCarDetails(newCarDetailsRequest);
        } catch (Exception e) {
            logger.error("editCarDetails error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 删除车辆信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/delCarDetails",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delCarDetails(HttpServletRequest request,String id){
        BaseResult result = BaseResult.success();
        try {
            carsokProductService.delCarDetails(id);
        } catch (Exception e) {
            logger.error("delCarDetails error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 更新车辆在售状态
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateCarSaleStatus",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult updateCarSaleStatus(HttpServletRequest request,String id){
        BaseResult result = BaseResult.success();
        try {
            carsokProductService.updateCarSaleStatus(id);
        } catch (Exception e) {
            logger.error("updateCarSaleStatus error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取已售，且没有购买人的车辆信息
     * @param request
     * @param inventoryRequest
     * @return
     */
    @RequestMapping(value = "/getSaledCarList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getSaledCarList(HttpServletRequest request, @RequestBody InventoryRequest inventoryRequest){
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        }else{
            inventoryRequest.setAccountId(acount.getId().toString());
        }
        try {
            inventoryRequest.setProCustomer("yes");
            result.setData(carsokProductService.getInventoryList(inventoryRequest));
        } catch (Exception e) {
            logger.error("getSaledCarList error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
    * @author zhangD
    * @date 2018/2/1 9:54
    * @Description: 二手车数量统计
    */
    @RequestMapping(value = "/totalCount",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getTotalCount(HttpServletRequest request){

        try {

            return  BaseResult.success(carsokProductService.getTotalCount());

        } catch (Exception e) {
            logger.error("getTotalCount error:", e);
            return BaseResult.fail("0000","系统异常");
        }
    }

}
