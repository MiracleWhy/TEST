package com.uton.carsokApi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.StoreRequest;
import com.uton.carsokApi.model.Customer;
import com.uton.carsokApi.model.Pagebean;
import com.uton.carsokApi.service.AcquisitionCarService;
import com.uton.carsokApi.service.CustomerMessageService;
import com.uton.carsokApi.service.CustomerService;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 * 门店接待重构
 */
@Controller
@RequestMapping("/storeOrAcquisitionCar")
public class CustomerMessageController {

    @Autowired
    CustomerService customerService;

    @Resource
    AcquisitionCarService acquisitionCarService;

    @Autowired
    CustomerMessageService customerMessageService;

    @RequestMapping("/storeMethod")
    @ResponseBody
    public Map storeMethod(HttpServletRequest request){
        Map<String,String> requestMap = new HashMap<String,String>();
        requestMap.put("time",request.getParameter("times"));
        requestMap.put("type",request.getParameter("type"));
        requestMap.put("mobile",request.getParameter("mobile"));
        requestMap.put("xxly",request.getParameter("xxly")==null?"":request.getParameter("xxly"));
        requestMap.put("khjb",request.getParameter("khjb"));
        requestMap.put("dfzt",request.getParameter("dfzt"));
        requestMap.put("gmys",request.getParameter("gmys"));
        requestMap.put("pc",request.getParameter("pageNum"));
        Page<Customer> pb = customerMessageService.selectCustList(requestMap);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("record",pb.toPageInfo());
        map.put("count",customerMessageService.selectThreeOrSevenCount(request.getParameter("mobile")));
        return map;
    }

    @RequestMapping("/storeSearchOrMonth")
    @ResponseBody
    public Map storeSearchOrMonth(HttpServletRequest request){
        String select = request.getParameter("select");
        String mobile = request.getParameter("mobile");
        String pc = request.getParameter("pageNum");
        Page<Customer> pb = customerMessageService.selectCustListBySearchOrMonth(select,mobile,pc);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("record",pb.toPageInfo());
        return map;
    }

    @RequestMapping(value = { "/customerCenterPage" }, method = { RequestMethod.GET })
    public String tenurePage(HttpServletResponse response, HttpServletRequest request, String mobile,String custId) {
        request.setAttribute("mobile",mobile);
        request.setAttribute("custId",custId);
        return "/customerDetail";
    }

    @RequestMapping("/searchStoreCustMsg")
    @ResponseBody
    public Map searchStoreCustMsg(HttpServletRequest request){
        String id = request.getParameter("id");
        Map pb= customerMessageService.selectCustMsgById(id);
        return pb;
    }

}
