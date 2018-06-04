package com.uton.carsokApi.controller;


import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CarCollectRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokAccountCollect;
import com.uton.carsokApi.model.CarsokCarCollect;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ICarsokAccountCollectService;
import com.uton.carsokApi.service.ICarsokCarCollectService;
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
@RequestMapping("/carsokCarCollect")
public class CarsokCarCollectController {

    private final static Logger logger = Logger.getLogger(CarsokCarCollectController.class);

    @Autowired
    ICarsokCarCollectService carCollectService;

    @Autowired
    ICarsokAccountCollectService carsokAccountCollectService;

    @Autowired
    private CacheService cacheService;

    /**
     * 获取车源收藏列表，每个人只能看到自己收藏的车辆（包括主账号）
     * @param request
     * @param carCollectRequest
     * @return
     */
    @RequestMapping(value = "/carCollectList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getCarCollectList(HttpServletRequest request, @RequestBody CarCollectRequest carCollectRequest){
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
            result.setData(carCollectService.getCarCollectList(carCollectRequest));
        } catch (Exception e) {
            logger.error("getCarCollectList error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

    /**
     * 添加车源/车行收藏
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/addCollect",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult addCarCollect(HttpServletRequest request,Integer id,String type){
        BaseResult result = BaseResult.success();
        Integer count=null;
        CarsokCarCollect carsokCarCollect = new CarsokCarCollect();
        CarsokAccountCollect carsokAccountCollect = new CarsokAccountCollect();
        carsokCarCollect.setCollectProductId(id);
        carsokAccountCollect.setCollectAccountId(id);
        Acount acount = cacheService.getAcountInfoFromCache(request);
        //subtoken不为空，获取子账号id
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        }
        if(!StringUtils.isEmpty(request.getHeader("subToken"))){
            ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
            if (childAccount == null) {
                return BaseResult.exception("请重新登陆");
            } else {
                //子账号验证是否收藏
                if ("car".equals(type)) {
                    count = carCollectService.isCollectCar(acount.getId(), childAccount.getId(), id);
                    if (count != null) {
                        return BaseResult.fail("0010","车源已收藏，请刷新！");
                    }
                }
                if ("account".equals(type)) {
                    count = carsokAccountCollectService.isCollectAccount(acount.getId(), childAccount.getId(), id);
                    if (count != null) {
                        return BaseResult.fail("0010","车行已收藏，请刷新！");
                    }
                }
                carsokCarCollect.setChildId(childAccount.getId());
                carsokAccountCollect.setChildId(childAccount.getId());
            }
        }else {
            //主账号验证是否收藏
            if ("car".equals(type)) {
                count = carCollectService.isCollectCar(acount.getId(), null, id);
                if (count != null) {
                    return BaseResult.fail("0010","车源已收藏，请刷新！");
                }
            }
            if("account".equals(type)){
                count = carsokAccountCollectService.isCollectAccount(acount.getId(),null,id);
                if (count != null) {
                    return BaseResult.fail("0010","车行已收藏，请刷新！");
                }
            }
        }
            carsokCarCollect.setAccountId(acount.getId());
            carsokAccountCollect.setAccountId(acount.getId());
        try {
            if("car".equals(type)){
                result.setData(carCollectService.addCarCollect(carsokCarCollect));
            }
            if("account".equals(type)){
                result.setData(carsokAccountCollectService.addAccountCollect(carsokAccountCollect));
            }
        } catch (Exception e) {
            logger.error("addCarCollect error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

    /**
     * 取消车源/车行收藏
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/unCollect",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delCarCollect(HttpServletRequest request,Integer id,String type){
        BaseResult result = BaseResult.success();
        try {
            if("car".equals(type)){
                result.setData(carCollectService.delCarCollect(id));
            }
            if("account".equals(type)){
                result.setData(carsokAccountCollectService.delAccountCollect(id));
            }
        } catch (Exception e) {
            logger.error("delCarCollect error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

    /**
     * 查询车源/车行是否收藏（0 ：未收藏 1：已收藏）
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/isCollect",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult isCollectCar(HttpServletRequest request,Integer id,String type){
        BaseResult result = BaseResult.success();
        Integer count = 0;
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        }else{
            if("car".equals(type)){
                count = carCollectService.isCollectCar(acount.getId(),null,id);
            }
            if("account".equals(type)){
                count = carsokAccountCollectService.isCollectAccount(acount.getId(),null,id);
            }
            if(!StringUtils.isEmpty(request.getHeader("subToken"))){//subtoken不为空，获取子账号id
                ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
                if (childAccount == null) {
                    return BaseResult.exception("请重新登陆");
                } else {
                    if("car".equals(type)){
                        count = carCollectService.isCollectCar(acount.getId(),childAccount.getId(),id);
                    }
                    if("account".equals(type)){
                        count = carsokAccountCollectService.isCollectAccount(acount.getId(),childAccount.getId(),id);
                    }
                }
            }
        }
        try {
            result.setData(count);
        } catch (Exception e) {
            logger.error("isCollectCar error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }


    /**
     * 获取分享车行信息
     * @param request
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/getAcountInfo",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getAcountInfo(HttpServletRequest request,Integer accountId){
        BaseResult result = BaseResult.success();
        try {
            result.setData(carCollectService.getAcountInfo(accountId));
        } catch (Exception e) {
            logger.error("getAcountInfo error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

}
