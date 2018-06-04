package com.uton.carsokApi.controller;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.Common;
import com.uton.carsokApi.controller.request.RequestAuthority;
import com.uton.carsokApi.service.ActiveUserService;
import com.uton.carsokApi.service.SmsService;
import com.uton.carsokApi.util.DateStyle;
import com.uton.carsokApi.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
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
 * Created by SEELE on 2018/2/2.
 */
@Controller
@RequestMapping("/ActiveUser")
public class ActiveUserController {
    @Resource
    RedisTemplate redisTemplate;

    @Autowired
    ActiveUserService activeUserService;

    private final static Logger logger = Logger.getLogger(ActiveUserController.class);


    @RequestMapping(value = { "/getAllUser" }, method = { RequestMethod.GET })
    public @ResponseBody
    BaseResult getAllUser(HttpServletRequest request) {
        try {
            BaseResult result = BaseResult.success();
            List<String> list = new ArrayList<>();
            list.addAll(redisTemplate.keys(Common.USER+"*"));

            result.setData(list);
            return  result;

        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
    }


    @RequestMapping(value = { "/calActiveUser" }, method = { RequestMethod.GET })
    public @ResponseBody
    BaseResult calActiveUser(HttpServletRequest request) {
        try {
            BaseResult result = BaseResult.success();
            List<String> list = new ArrayList<>();
            list.addAll(redisTemplate.keys(Common.USER+"*"));

            int count = 0;

            for(String key:list)
            {
                RequestAuthority authority = (RequestAuthority) redisTemplate.opsForValue().get(key);
                if(authority.getLastTime().equals(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD)))
                {
                    count++;
                }

                logger.debug("count:"+count+" key:"+key+" value:"+ JSON.toJSONString(authority));
            }

            result.setData(count);
            return  result;
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value = { "/getAU" }, method = { RequestMethod.GET })
    public @ResponseBody
    BaseResult getAU(HttpServletRequest request) {
        try {
            String type = request.getParameter("type");

            BaseResult result = BaseResult.success();

            Date endDate = new Date();
            Date startDate = DateUtil.StringToDate(DateUtil.DateToString(endDate,DateStyle.YYYY_MM_DD),DateStyle.YYYY_MM_DD);

            switch (type)
            {
                case "MONTH":
                    startDate=DateUtil.addDay(startDate,-31);
                    break;
                case "WEEK":
                    startDate=DateUtil.addDay(startDate,-7);
                    break;
                case "DAY":
                default:
                    break;
            }

            result.setData(activeUserService.calActiveUser(startDate,endDate));
            return  result;
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
    }



}
