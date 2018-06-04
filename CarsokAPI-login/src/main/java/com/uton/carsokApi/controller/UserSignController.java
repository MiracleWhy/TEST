package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.UserSign;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.UserSignService;
import com.uton.carsokApi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by SEELE on 2017/10/20.
 */
@Controller
public class UserSignController {
    @Autowired
    UserSignService userSignService;

    @Resource
    CacheService cacheService;

    @RequestMapping(value = {"/doUserSign"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult doUserSign(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            String accountId = acount.getId().toString();

            //获取当前签到次数
            UserSign userSign = userSignService.getUserSign(accountId);

            //更新签到次数
            int times =  userSignService.updateUserSign(accountId);

            //签到次数满足，并且上次签到日期是昨天
            if((times==3||times==7)&&DateUtil.getIntervalDays(userSign.getLastSignDate(), new Date()) > 0)
            {
                int present = times == 7 ? 3 : 1;
                //赠送维保次数
                userSignService.presentToBolang(acount.getAccount(),present);
                //更新赠送总记录数
                userSignService.updatePresentTimes(accountId,present);
            }

            //result.setData(times);

        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = {"/getUserSignTimes"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getUserSignTimes(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            //获取签到次数
            result.setData(userSignService.getSignTimes(acount.getId().toString()));
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

}
