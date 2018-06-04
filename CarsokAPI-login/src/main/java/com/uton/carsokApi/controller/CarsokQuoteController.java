package com.uton.carsokApi.controller;


import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.InsertQuoteInfoRequest;
import com.uton.carsokApi.controller.response.CarsokQuoteDetailsResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ChildInfoService;
import com.uton.carsokApi.service.ICarsokQuoteService;
import com.uton.carsokApi.util.StringUtil;
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
 * 前端控制器
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Controller
@RequestMapping("/carsokQuote")
public class CarsokQuoteController {

    private final static Logger logger = Logger.getLogger(CarsokQuoteController.class);

    @Autowired
    ICarsokQuoteService carsokQuoteService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ChildInfoService childInfoService;



    @RequestMapping("/carQuotePage")
    public String carQuotePage(HttpServletRequest request, Integer id) {
        CarsokQuoteDetailsResponse cqr = carsokQuoteService.getQuoteMessageById(id);
        request.setAttribute("cqr", cqr);
        return "/carQuoteMessage";
    }

    @RequestMapping("/insertQuoteInfo")
    @ResponseBody
    public BaseResult insertQuoteInfo(HttpServletRequest request, @RequestBody InsertQuoteInfoRequest i) {
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if (acount == null) {
                return BaseResult.fail("0002", "请重新登入");
            }
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                i.setChildId(childAccount.getId());
            } else {
                i.setChildId(0);
            }
            i.setAccountId(acount.getId());
            boolean flag = carsokQuoteService.insertQuoteInfo(i);
            if (flag == false) {
                return  BaseResult.fail("0003","报价失败");
            }
            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001","系统异常");
        }

    }

    /**
     * 获取报价消息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/quoteList",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getQuoteMessageList(HttpServletRequest request,String createTime){
        String pushTo = "";
        if(StringUtils.isNotEmpty(request.getHeader("token"))){
            Acount acount = cacheService.getAcountInfoFromCache(request);
            pushTo = acount.getAccount();
        }else if(StringUtils.isNotEmpty(request.getHeader("subToken"))){
            ChildAccount childAccount = cacheService.getSubAcountInfoFromCache(request);
            pushTo = childAccount.getChildAccountMobile();
        }else {
            return BaseResult.fail("0001","请重新登录");
        }
        try {
                return  BaseResult.success(carsokQuoteService.getQuoteMessageList(pushTo,createTime));
        } catch (Exception e) {
            logger.error("getQuoteMessageList error:", e);
            return BaseResult.fail("0001","系统异常");
        }
    }
}
