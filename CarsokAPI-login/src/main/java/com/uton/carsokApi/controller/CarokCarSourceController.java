package com.uton.carsokApi.controller;

import com.github.pagehelper.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.enums.LimitEnum;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.CarSourceResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.*;
import com.uton.carsokApi.service.core.IndexSendService;
import com.uton.carsokApi.util.DozerMapperUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2018/1/18.
 * desc:
 */
@RestController
@RequestMapping("carsokCarSource")
public class CarokCarSourceController {



    @Autowired
    private CacheService cacheService;

    @Autowired
    private ICarsokProductService productService;

    @Autowired
    private ICarsokAccountPowerService carsokAccountPowerService;

    @Autowired
    private ICarsokAccountTofrontService carsokAccountTofrontService;

    @Autowired
    private ChildInfoService childInfoService;


    /**
    * @author zhangdi
    * @date 2018/1/22 11:25
    * @Description: 车源列表
    */
    @RequestMapping(value = "/carSourList",method = RequestMethod.POST)
    public BaseResult getInventoryList(HttpServletRequest request, @RequestBody CarSourceRequest carSourceRequest){

        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if (acount == null) {
                return BaseResult.fail("0002", "请重新登入");
            }
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                carSourceRequest.setChildId(childAccount.getId());
            }
            carSourceRequest.setAccountId(acount.getId());
            CarSourceSearchRequest carSourceSearchRequest = new CarSourceSearchRequest();
            BeanUtils.copyProperties(carSourceRequest,carSourceSearchRequest);
            int pageNum = (carSourceRequest.getPageNum() -1)*carSourceRequest.getPageSize();
            int pageSize = carSourceRequest.getPageSize();
            List<CarSourceResponse> carSourceResponses=productService.getCarsouceList(carSourceSearchRequest,acount,pageNum,pageSize);
            int limit = carsokAccountPowerService.getQueryVehicleCarSourcePermissions(acount, LimitEnum.CARSOURCE.getCode());
            Map map =new HashedMap();
            map.put("limit",limit);
            map.put("list",carSourceResponses);
            return  BaseResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return  BaseResult.fail("0001","系统异常");
        }
    }

    /**
     * @author zhangdi
     * @date 2018/1/22 11:25
     * @Description: 车行列表
     */
    @RequestMapping(value = "/merchantList",method = RequestMethod.POST)
    public BaseResult merchantList(HttpServletRequest request, @RequestBody MerchantListRequest m){

        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if (acount == null) {
                return BaseResult.fail("0002", "请重新登入");
            }
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                m.setChildId(childAccount.getId());
            }
                m.setAccountId(acount.getId());
            return BaseResult.success(carsokAccountTofrontService.getMmerchantList(m));
        } catch (Exception e) {
            e.printStackTrace();
            return  BaseResult.fail("0001","系统异常");
        }
    }

    /**
     * @author zhangdi
     * @date 2018/1/22 11:25
     * @Description: 置顶车源列表
     */
    @RequestMapping(value = "/topCarSourceList",method = RequestMethod.POST)
    public BaseResult topCarSourceList(HttpServletRequest request, @RequestBody TopCarSourceListRequest topCarSourceListRequest){

        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if (acount == null) {
                return BaseResult.fail("0002","请重新登入");
            }
            topCarSourceListRequest.setAccountId(acount.getId());
            return BaseResult.success(productService.getTopCarSourceList(topCarSourceListRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return  BaseResult.fail("0001","系统异常");
        }
    }

    /**
     * @author z
     * @date 2017/12/26 17:24
     * @Description: title数据
     */
    @RequestMapping( "titleData")
    @ResponseBody
    public BaseResult titleData (HttpServletRequest request){
        try {
            return BaseResult.success(productService.titleData());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }
    /**
     * @author z
     * @date 2017/12/26 17:24
     * @Description: title数据
     */
    @RequestMapping( value = "addBrowse" ,method = RequestMethod.GET)
    @ResponseBody
    public BaseResult addBrowse (HttpServletRequest request ,int id){
        try {
            boolean flag = productService.addBrowse(id);
            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    }