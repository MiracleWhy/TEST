package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.PermissionRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.Permission;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.PermissionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/10/30.
 */
@Controller
public class PermissionManagementController {

    @Autowired
    PermissionManagementService permissionManagementService;

    @Resource
    CacheService cacheService;

    @RequestMapping("/insertPower")
    @ResponseBody
    public BaseResult insertPower(HttpServletRequest request,@RequestBody PermissionRequest vo){
        try{
            Acount acount = cacheService.getAcountInfoFromCache(request);
            BaseResult baseResult = permissionManagementService.insertPower(acount.getId(),vo);
            return baseResult;
        }catch (Exception e){
            return BaseResult.fail("0001","系统异常");
        }
    }

    @RequestMapping("/selectPermission")
    @ResponseBody
    public BaseResult selectPermission(HttpServletRequest request,@RequestBody PermissionRequest vo){
        try{
            BaseResult baseResult = permissionManagementService.selectPermission(vo);
            return baseResult;
        }catch (Exception e){
            e.printStackTrace();
            return BaseResult.fail("0001","系统异常");
        }
    }

}
