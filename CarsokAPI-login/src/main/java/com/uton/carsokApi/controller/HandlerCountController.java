package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.HandlerCount;
import com.uton.carsokApi.model.MessageCenter;
import com.uton.carsokApi.service.HandlerCountService;
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
 * Created by Administrator on 2017/6/14.
 */
@Controller
@RequestMapping("/handlerCount")
public class HandlerCountController {

    private final static Logger logger = Logger.getLogger(HandlerCountController.class);

    @Autowired
    HandlerCountService handlerCountService;

    @RequestMapping(value={"/selectCountFoEverything"} ,method = { RequestMethod.POST })
    public @ResponseBody BaseResult selectCountFoEverything(HttpServletRequest request,@RequestBody HandlerCount vo){
        try{
            BaseResult baseResult = handlerCountService.selectCount(request,vo);
            return baseResult;
        }catch (Exception e) {
            logger.error("selectCountFoEverything--查询门店、保有、日检出错:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value={"/deleteHandlerMsg"} ,method = { RequestMethod.POST })
    public @ResponseBody void deleteHandlerMsg(HttpServletRequest request){
        try{
            String mendianid = request.getParameter("mendianId");
            if(!StringUtil.isEmpty(mendianid)){
                handlerCountService.deleteMendianMsg(mendianid);
            }
            String shoucheId = request.getParameter("shoucheId");
            if(!StringUtil.isEmpty(shoucheId)){
                handlerCountService.deleteShoucheMsg(shoucheId);
            }
        }catch (Exception e) {
            logger.error("deleteHandlerMsg--删除门店消、收车消息出错:", e);
        }
    }
}
