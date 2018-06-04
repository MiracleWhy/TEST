package com.uton.carsokApi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.Poster;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.EmployeesService;
import com.uton.carsokApi.service.PosterService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class EmployeesController {
    private final static Logger logger = Logger.getLogger(EmployeesController.class);

    @Autowired
    EmployeesService employeesService;

    @Resource
    CacheService cacheService;


    /**
     * 获取所有雇员
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/getAllEmployees"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getAllPoster(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            result.setData(employeesService.selectAll());
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }
}
