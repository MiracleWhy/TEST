package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CarsokSoundRecordingRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.SaasSoundRecordingService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/4/13 0013.
 */
@Controller
@RequestMapping("/SaasSoundRecording")
public class SaasSoundRecordingController {

    private final static Logger logger = Logger.getLogger(SaasSoundRecordingController.class);

    @Autowired
    SaasSoundRecordingService saasSoundRecordingService;

    @Autowired
    CacheService cacheService;
    /**
     *
     */
    @RequestMapping(value = "updateOrDeleteRecording", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateOrDeleteRecording(@RequestBody CarsokSoundRecordingRequest vo, HttpServletRequest request) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        int accountId = acount.getId();
        int childId = 0;
        if (!StringUtil.isEmpty(acount.getSubPhone())) {
            //如果账号的SubPhone不为空，则代表是子帐号操作。
            ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
            childId = childAccount.getId();
        }
        try {
            vo.setAccountId(accountId);
            vo.setChildId(childId);
            return saasSoundRecordingService.updateOrDeleteRecording(vo);
        } catch (Exception e) {
            logger.error("updateOrDeleteRecording error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }
    @RequestMapping(value = "insertRecording", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult insertRecording(@RequestBody CarsokSoundRecordingRequest vo, HttpServletRequest request) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        int accountId = acount.getId();
        int childId = 0;
        if (!StringUtil.isEmpty(acount.getSubPhone())) {
            //如果账号的SubPhone不为空，则代表是子帐号操作。
            ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
            childId = childAccount.getId();
        }
        try {
            vo.setAccountId(accountId);
            vo.setChildId(childId);
            return saasSoundRecordingService.insertRecording(vo);
        } catch (Exception e) {
            logger.error("insertRecording error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }
    @RequestMapping(value = "selectRecording", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult selectRecording(@RequestBody CarsokSoundRecordingRequest vo, HttpServletRequest request) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        int accountId = acount.getId();
        int childId = 0;
        if (!StringUtil.isEmpty(acount.getSubPhone())) {
            //如果账号的SubPhone不为空，则代表是子帐号操作。
            ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
            childId = childAccount.getId();
        }
        try {
            vo.setAccountId(accountId);
            vo.setChildId(childId);
            return saasSoundRecordingService.selectRecording(vo);
        } catch (Exception e) {
            logger.error("insertRecording error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }




}
