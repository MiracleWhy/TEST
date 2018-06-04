package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CustomerTenureFollowRequest;
import com.uton.carsokApi.controller.request.FindMessagePageRequest;
import com.uton.carsokApi.controller.request.FollowMessageRequest;
import com.uton.carsokApi.dao.CarsokCustomerTenureFollowMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokCustomer;
import com.uton.carsokApi.model.CarsokCustomerTenureCar;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.SaasFollowMessageService;
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
@RequestMapping("/FollowMessage")
public class SaasFollowMessageController {

    private final static Logger logger = Logger.getLogger(SaasFollowMessageController.class);

    @Autowired
    SaasFollowMessageService saasFollowMessageService;

    @Autowired
    CacheService cacheService;

    @RequestMapping(value = "saveMessage" ,method = RequestMethod.POST)
    @ResponseBody
    public BaseResult saveTenureFollowMessage (@RequestBody FollowMessageRequest vo, HttpServletRequest request){
        //
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
            saasFollowMessageService.saveFollowMessage(vo);
        }catch (Exception e){
            return BaseResult.exception(e.getMessage());
        }
        return BaseResult.success();
    }

    @RequestMapping(value = "findMessagePage", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult selectFollowMessage(@RequestBody FindMessagePageRequest vo) {
        try {
            return BaseResult.success(saasFollowMessageService.selectFollowMessage(vo));
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
    }
}
