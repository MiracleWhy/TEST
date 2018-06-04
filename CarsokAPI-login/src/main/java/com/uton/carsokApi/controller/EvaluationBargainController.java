package com.uton.carsokApi.controller;

import com.uton.carsokApi.controller.request.BargainRequest;
import com.uton.carsokApi.controller.request.EvaluationRequest;
import com.uton.carsokApi.controller.response.BargainInfoResponse;
import com.uton.carsokApi.model.Evaluations;
import com.uton.carsokApi.service.EvaluationBargainService;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 微店估价砍价控制器
 * Created by Administrator on 2017/10/11.
 */
@Controller
@RequestMapping("/utonwGK")
public class EvaluationBargainController {

    private final static Logger logger = Logger.getLogger(EvaluationBargainController.class);

    @Autowired
    EvaluationBargainService evaluationBargainService;

    @RequestMapping(value = { "/evaluationPage" }, method = { RequestMethod.GET })
    public String evaluationPage(HttpServletResponse response, HttpServletRequest request, String accountId,String isCar) {
        request.setAttribute("accountId",accountId);
        request.setAttribute("isCar",isCar);
        return "/carAssess";
    }

    @RequestMapping(value = { "/evaluationInfoPage" }, method = { RequestMethod.GET })
    public String evaluationInfoPage(HttpServletResponse response, HttpServletRequest request, String productId) {
        Evaluations evaluations = evaluationBargainService.selectEvaluationMsgById(productId);
        request.setAttribute("evaluationId",evaluations.getId());
        request.setAttribute("accountId",evaluations.getAccountId());
        request.setAttribute("mobile",evaluations.getMobile());
        request.setAttribute("name",evaluations.getName());
        request.setAttribute("vehicleModel",evaluations.getVehicleModel());
        return "/newsLook";
    }

    @RequestMapping(value = { "/bargainPage" }, method = { RequestMethod.GET })
    public String bargainPage(HttpServletResponse response, HttpServletRequest request, String productId) {
        request.setAttribute("productId",productId);
        return "/bargain";
    }

    @RequestMapping(value = { "/bargainInfoPage" }, method = { RequestMethod.GET })
    public String bargainInfoPage(HttpServletResponse response, HttpServletRequest request, String productId) {
        BargainInfoResponse bargainInfoResponse = evaluationBargainService.selectProductMsgByProductId(productId);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(bargainInfoResponse!=null){
            String dates = sdf.format(bargainInfoResponse.getFirstUpTime());
            request.setAttribute("productId",bargainInfoResponse.getProductId());
            request.setAttribute("firstUpTime",dates);
            request.setAttribute("miles",bargainInfoResponse.getMiles());
            request.setAttribute("miniPrice",bargainInfoResponse.getMiniPrice());
            request.setAttribute("picture",bargainInfoResponse.getPicture());
            request.setAttribute("price",bargainInfoResponse.getPrice());
            request.setAttribute("productName",bargainInfoResponse.getProductName());
            request.setAttribute("id",bargainInfoResponse.getId());
            request.setAttribute("name",bargainInfoResponse.getName());
            request.setAttribute("mobile",bargainInfoResponse.getMobile());
            request.setAttribute("intentionalPrice",bargainInfoResponse.getIntentionalPrice());
//          在售天数
            if(bargainInfoResponse.getOnShelfTime()!=null){
                int days = (int) ((new Date().getTime() - bargainInfoResponse.getOnShelfTime().getTime()) / (1000*3600*24));
                request.setAttribute("sellTime",days+1);
            }else{
                request.setAttribute("sellTime","--");
            }
        }else {
            request.setAttribute("productId",0);
            request.setAttribute("firstUpTime","1970-01-01");
            request.setAttribute("miles",0);
            request.setAttribute("miniPrice",new BigDecimal(0));
            request.setAttribute("picture","--");
            request.setAttribute("price",new BigDecimal(0));
            request.setAttribute("productName","网络异常");
            request.setAttribute("id",0);
            request.setAttribute("name","--");
            request.setAttribute("mobile","--");
            request.setAttribute("intentionalPrice","--");
            request.setAttribute("sellTime","--");
        }
        return "/bargainInfo";
    }

    @RequestMapping(value = { "/evaluationSubmit" }, method = { RequestMethod.POST })
    @ResponseBody
    public String evaluationSubmit(HttpServletRequest request,EvaluationRequest vo) {
        try {
            if(evaluationBargainService.evaluationSubmit(vo)>0){
                return "0000";
            }
        }catch (Exception e){
            logger.error("evaluationSubmit errors  :" + e);
        }
        return "0001";
    }

    @RequestMapping(value = { "/bargainSubmit" }, method = { RequestMethod.POST })
    @ResponseBody
    public String bargainSubmit(HttpServletRequest request, BargainRequest vo) {
        try{
            if(evaluationBargainService.bargainSubmit(vo)>0){
                return "0000";
            }
        }catch (Exception e){
            logger.error("bargainSubmit errors  :" + e);
            e.printStackTrace();
        }
        return "0001";
    }
}
