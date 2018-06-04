package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.QianKeBaoYouDBRequest;
import com.uton.carsokApi.controller.request.ZbCountRequest;
import com.uton.carsokApi.controller.response.QianKeBaoYouDBResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokZbTaskSxyWb;
import com.uton.carsokApi.model.MessageCenter;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.MessageCenterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/11 0011.
 */
@Controller
public class MessageCententController {

    private final static Logger logger = Logger.getLogger(MessageCententController.class);

    @Autowired
    MessageCenterService messageCenterService;
    @Resource
    CacheService cacheService;

    @RequestMapping(value={"/selectMsgByContentType"} ,method = { RequestMethod.POST })
    public @ResponseBody BaseResult selectMsgByContentType(HttpServletRequest request,@RequestBody MessageCenter vo){
        try{
            BaseResult baseResult = messageCenterService.selectByContentType(request,vo);
            return baseResult;
        }catch (Exception e) {
            logger.error("selectMsgByContentType:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value={"/selectExtentionByMobile"} ,method = { RequestMethod.POST })
    public @ResponseBody BaseResult selectExtentionByMobile(HttpServletRequest request,@RequestBody MessageCenter vo){
        try{
            BaseResult baseResult = messageCenterService.selectExtentionByMobile(vo);
            return baseResult;
        }catch (Exception e) {
            logger.error("selectExtentionByMobile:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value={"/selectUnReadMessageCount"},method = { RequestMethod.GET })
    public @ResponseBody BaseResult selectUnReadMessageCount(HttpServletRequest request) {


        int unReadCount = 0;
        BaseResult result = BaseResult.success();
        try {
            String timestamp = request.getParameter("timestamp");
            String type = request.getParameter("type");
            Date unReadTime = new Date(new Long(timestamp));
            Acount acount = cacheService.getAcountInfoFromCache(request);
            String mobile;
            if (acount.getSubPhone() != null && !"".equals(acount.getSubPhone())) {
                mobile = acount.getSubPhone();
            } else {
                mobile = acount.getAccount();
            }
            List<String> typeList = new ArrayList<>();
            switch(type)
            {
                case "task":
                    typeList.add("taskPGS");
                    typeList.add("taskZBY");
                    typeList.add("taskManager");
                    typeList.add("taskYYZY");
                    typeList.add("taskRemind");
                    typeList.add("taskTenure");
                    typeList.add("taskDC");
                    break;
                default:
                    typeList.add(type);
                    break;
            }

            unReadCount = messageCenterService.selectCountByTime(mobile, unReadTime,typeList);
            result.setData(unReadCount);
        } catch (Exception e) {
            result=BaseResult.exception(e.getMessage());
        }

        return result;
    }

    @RequestMapping(value={"/handleCount"},method = { RequestMethod.POST })
    public @ResponseBody BaseResult handleCount(HttpServletRequest request,@RequestBody ZbCountRequest vo){
        try {
            return messageCenterService.selectHandleCount(request,vo);
        } catch (Exception e) {
            logger.error("查询待办消息总数错误  error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }
    //查询手续员推送内容
    @RequestMapping(value={"/sxyts"},method = { RequestMethod.POST })
    public @ResponseBody BaseResult sxyts(@RequestBody CarsokZbTaskSxyWb vo){
        try {
            return messageCenterService.selectsxyts(vo);
        } catch (Exception e) {
            logger.error("查询手续员待办消息总数错误  error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 查询潜客和保有的代办消息
     * @param request
     * @return
     */
    @RequestMapping(value={"/qianKeBaoYouDB"},method = { RequestMethod.POST })
    public @ResponseBody BaseResult qianKeBaoYouDB(HttpServletRequest request,@RequestBody QianKeBaoYouDBRequest vo){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            Map map = messageCenterService.selectQianKeBaoYouList(acount,vo);
            return BaseResult.success(map);
        } catch (Exception e) {
            logger.error("查询潜客保有代办消息总数错误  error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }


}
