package com.uton.carsokApi.controller;


import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.AccountTofrontPrepayRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.pay.PayTool;
import com.uton.carsokApi.service.*;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Controller
@RequestMapping("/carsokAccountTofrontOrder")
public class CarsokAccountTofrontOrderController {

    private final static Logger logger = Logger.getLogger(CarsokAccountTofrontOrderController.class);

    @Autowired
    ICarsokAccountTofrontService carsokAccountTofrontService;

    @Autowired
    ICarsokCarTofrontService carsokCarTofrontService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ChildInfoService childInfoService;

    @Autowired
    private ICarsokAccountTofrontOrderService carsokAccountTofrontOrderService;

    @RequestMapping("/toExpirePage")
    public String toExpirePage(HttpServletRequest request,Integer id,String type){
        if("car".equals(type)){

        }
        if("account".equals(type)){

        }
        return "/expirePage";
    }

    /**
     * @author Z
     * @date 2018/1/25 16:41
     * @Description:微信预制支付
     */
    @RequestMapping( value = "prdpay" ,method = RequestMethod.POST)
    @ResponseBody
    public BaseResult prdpay (HttpServletRequest request , @RequestBody AccountTofrontPrepayRequest accountTofrontPrepayRequest){
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if (acount == null) {
                return BaseResult.fail("0002", "请重新登入");
            }
            accountTofrontPrepayRequest.setAccountId(acount.getId());
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                accountTofrontPrepayRequest.setChildId(childAccount.getId());
            } else {
                accountTofrontPrepayRequest.setChildId(0);
            }

            SortedMap<String,String> map = carsokAccountTofrontOrderService.prepay(accountTofrontPrepayRequest);
            if ("1".equals(map.get("code"))){
                return BaseResult.fail("0005","获取prepayid失败");
            } else if("2".equals(map.get("code"))) {
                return  BaseResult.fail("0006","订单生成失败");
            }
            return BaseResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    /**
     * @author Z
     * @date 2018/1/25 17:35
     * @Description:接收微信支付成功通知
     */
    @RequestMapping( value = "notify" ,method = RequestMethod.POST)
    @ResponseBody
    public void getnotify (HttpServletRequest request , HttpServletResponse response){
        try {
            Map<String, String> map = PayTool.wxnotifyPay(request);
            if (carsokAccountTofrontOrderService.notify(map)) {
                response.getWriter().print(map.get("return_msg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
}
