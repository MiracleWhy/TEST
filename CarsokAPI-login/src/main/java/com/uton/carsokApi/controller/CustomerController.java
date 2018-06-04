package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.Page;
import com.uton.carsokApi.model.Bussines;
import com.uton.carsokApi.model.Customer;
import com.uton.carsokApi.model.CustomerFlow;
import com.uton.carsokApi.model.Pagebean;
import com.uton.carsokApi.service.CustomerService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.iterators.ObjectArrayIterator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/13 0013.
 * 客户管理
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final static Logger logger = Logger.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;
    /**
     * 新增客户
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping( "/customerInsert" )
    public  @ResponseBody BaseResult customerInsert(HttpServletRequest request, @RequestBody Customer vo){
                try{
                    BaseResult result = customerService.customerInsert(vo);
                    return result;
                }catch (Exception e) {
                    logger.error("customerInsert:", e);
                    return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping( "/selectByRemind" )
    public  @ResponseBody Map selectByRemind(HttpServletRequest request){
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }
        int otherId = 0;
        String otherIds = request.getParameter("otherId");
        String xxly = request.getParameter("xxly");
        String khjb = request.getParameter("khjb");
        String dfzt = request.getParameter("dfzt");
        String gmys = request.getParameter("gmys");
        if(!StringUtil.isEmpty(otherIds)){
            otherId = Integer.parseInt(otherIds);
        }
        String mobile = request.getParameter("mobile");
        Map<String, Object> map = new HashMap();
        List<Integer> listId = customerService.selectId(mobile);
        Map maptr = customerService.getCount(listId.get(0), listId.get(1), xxly, khjb, dfzt, gmys);
        Pagebean<Customer> pb = customerService.selectByRemind(pr,pc,otherId,mobile);
        map.put("record",pb);
        map.put("maptr",maptr);
        return map;
    }

    @RequestMapping( "/querycustomerList" )
    public  @ResponseBody Map querycustomerList(HttpServletRequest request){
        String time = request.getParameter("times");
        String month = request.getParameter("month");
        String mobile = request.getParameter("mobile");
        String xxly = request.getParameter("xxly");
        String khjb = request.getParameter("khjb");
        String dfzt = request.getParameter("dfzt");
        String gmys = request.getParameter("gmys");
        Map map = customerResult(request,time,mobile,xxly,khjb,dfzt,gmys,month);
        String select=request.getParameter("customerPhone");
        if(!StringUtil.isEmpty(select)){
            int pc = getpc(request);
            Pagebean<Customer> pb = customerService.queryListForCustPhone(select,mobile,pc);
            map.put("record",pb);
        }
        return map;
    }

    @RequestMapping( "/querycustomerBysearchkey" )
    public  @ResponseBody Map querycustomerBysearchkey(HttpServletRequest request){
        String selects = request.getParameter("select");
        String month = request.getParameter("month");
        String mobile = request.getParameter("mobile");
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }
        Pagebean<Customer> pb = customerService.querycustomerListBysearchkey(pr,pc,selects, month,mobile);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("record",pb);
        return map;
    }
    @RequestMapping( "/customerTrack" )
    public  @ResponseBody Map customerTrack(HttpServletRequest request){
        String custTrack = request.getParameter("track");
        String custPhone = request.getParameter("custP");
        String custName = request.getParameter("custN");
        String custI = request.getParameter("custI");
        int custId = Integer.parseInt(custI);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("maps",customerService.updateTrack(custTrack,custPhone,custName,custId));
        return map;
    }

    /**
     * 更新跟踪信息
     * @param request
     * @return
     */
    @RequestMapping("customerFlow")
    public @ResponseBody Map customerFlow(HttpServletRequest request){
        String custFlow = request.getParameter("custFlow");
        String custI = request.getParameter("custI");
        String customerStatus = request.getParameter("customerStatus");
        String custRemind = request.getParameter("remind");
        String custCom = request.getParameter("comeIf");
        String mobile = request.getParameter("mobile");
        int custCome = -1;
        if("yes".equals(custCom)){
            custCome = 1;
        }else if("no".equals(custCom)){
            custCome = 2;
        }
        int custId = Integer.parseInt(custI);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("maps",customerService.insertFlow(custId,custFlow,customerStatus,custRemind,custCome,mobile));
        return map;
    }

    @RequestMapping("selectAllFlow")
    public @ResponseBody Map selectAllFlow(HttpServletRequest request){
        String custI = request.getParameter("custI");
        int custId = Integer.parseInt(custI);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("maps",customerService.selectAllFlows(custId));
        return map;
    }

    @RequestMapping( "/selectTrack" )
    public  @ResponseBody Map selectTrack(HttpServletRequest request){
        String custPhone = request.getParameter("custP");
        String custName = request.getParameter("custN");
        String custI = request.getParameter("custI");
        int custId = Integer.parseInt(custI);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("maps",customerService.selectTrack(custPhone,custName,custId));
        return map;
    }

    /**
     * WebView修改客户到访状态时  查询跟踪信息
     * @param request
     * @return
     */
    @RequestMapping( "/selectFlow" )
    public  @ResponseBody Map selectFlow(HttpServletRequest request){
        String custI = request.getParameter("custI");
        int custId = Integer.parseInt(custI);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("maps",customerService.selectFlow(custId));
        map.put("maps2",customerService.selectCustomerStatus(custId));
        return map;
    }

    @RequestMapping("customerScreenMessage")
    public @ResponseBody Map customerScreenMessage(HttpServletRequest request){
        String type = request.getParameter("type");
        String xxly = request.getParameter("xxly");
        String khjb = request.getParameter("khjb");
        String dfzt = request.getParameter("dfzt");
        String gmys = request.getParameter("gmys");
        String times = request.getParameter("times");
        String mobile = request.getParameter("mobile");
        int pc = getpc(request);
        Map<String,Object> map = new HashMap<String,Object>();
        if("1".equals(times) || "2".equals(times) || "3".equals(times)) {
            int time = Integer.parseInt(times);
            map.put("record", customerService.customerScreenMessage(pc, xxly, khjb, dfzt, gmys, mobile, time));
        }else {
            int pr = 10;
            map.put("record", customerService.queryCustByMonth(pr, pc, times, mobile, xxly, khjb, dfzt, gmys));
        }
        return map;
    }

    private int getpc(HttpServletRequest request){
        String value = request.getParameter("pc");
        if(value == null || value.trim().isEmpty()){
            return 1;
        }
        return Integer.parseInt(value);
    }

    public Map customerResult(HttpServletRequest request,String time,String mobile,String xxly,String khjb,String dfzt,String gmys,String month){
            if (time == null || "".equals(time)) {
                time = "0";
                int times = Integer.parseInt(time);
                int pr = 10;
                int pc = getpc(request);
                if (pc == 0) {
                    pc = 1;
                }
                Pagebean<Customer> pb = customerService.querycustomerList(pr, pc, times, mobile, xxly, khjb, dfzt, gmys);
                List<Integer> listId = customerService.selectId(mobile);
                Map maptr = customerService.getCount(listId.get(0), listId.get(1), xxly, khjb, dfzt, gmys);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("record", pb);
                map.put("maptr", maptr);
                return map;
            } else if (!"4".equals(time)) {
                int times = Integer.parseInt(time);
                int pr = 10;
                int pc = getpc(request);

                Pagebean<Customer> pb = customerService.querycustomerList(pr, pc, times, mobile, xxly, khjb, dfzt, gmys);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("record", pb);
                return map;
            }else {
                int pr = 10;
                int pc = getpc(request);
                if (pc == 0) {
                    pc = 1;
                }
                Pagebean<Customer> pb = customerService.queryCustByMonth(pr, pc, month, mobile, xxly, khjb, dfzt, gmys);
                List<Integer> listId = customerService.selectId(mobile);
                Map maptr = customerService.getCount(listId.get(0), listId.get(1), xxly, khjb, dfzt, gmys);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("record", pb);
                map.put("maptr", maptr);
                return map;
            }
//        if(StringUtil.isEmpty(month)) {
//            if (time == null || time == "") {
//                time = "0";
//                int times = Integer.parseInt(time);
//                int pr = 10;
//                int pc = getpc(request);
//                if (pc == 0) {
//                    pc = 1;
//                }
//                Pagebean<Customer> pb = customerService.querycustomerList(pr, pc, times, mobile, xxly, khjb, dfzt, gmys);
//                List<Integer> listId = customerService.selectId(mobile);
//                Map maptr = customerService.getCount(listId.get(0), listId.get(1), xxly, khjb, dfzt, gmys);
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("record", pb);
//                map.put("maptr", maptr);
//                return map;
//            } else {
//                int times = Integer.parseInt(time);
//                int pr = 10;
//                int pc = getpc(request);
//
//                Pagebean<Customer> pb = customerService.querycustomerList(pr, pc, times, mobile, xxly, khjb, dfzt, gmys);
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("record", pb);
//                return map;
//            }
//        }else {
//            int pr = 10;
//            int pc = getpc(request);
//            if (pc == 0) {
//                pc = 1;
//            }
//            Pagebean<Customer> pb = customerService.queryCustByMonth(pr, pc, month, mobile, xxly, khjb, dfzt, gmys);
//            List<Integer> listId = customerService.selectId(mobile);
//            Map maptr = customerService.getCount(listId.get(0), listId.get(1), xxly, khjb, dfzt, gmys);
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("record", pb);
//            map.put("maptr", maptr);
//            return map;
//        }
    }

    @RequestMapping( "/selectCustIfExist" )
    @ResponseBody
    public BaseResult selectCustIfExist(@RequestBody Customer vo){
        List<Customer> cust = customerService.selectCustIfExist(vo.getMobiles(),vo.getCustomerPhone());
        BaseResult baseResult = BaseResult.success();
        if (cust != null && cust.size() >= 1) {
            baseResult.setData(cust.get(0));
            baseResult.setRetCode("0002");
            baseResult.setRetMsg("用户已经存在");
            int status=cust.get(0).getCustomerStatus();
            if(status==3 || status==4 ||status==7){
                baseResult.setData(cust.get(0));
                baseResult.setRetCode("0003");
                baseResult.setRetMsg("用户已经存在且为已购买或置换状态");
            }
        }else {
            baseResult.setRetCode("0004");
            baseResult.setRetMsg("用户不存在，可以添加");
        }
        return baseResult;
    }

    @RequestMapping(value = { "/selectByUpdate" }, method = { RequestMethod.GET })
    @ResponseBody
    public BaseResult selectByUpdate(HttpServletRequest request){
        try{
            String id = request.getParameter("id");
            BaseResult result = customerService.selectByUpdate(id);
            return result;
        }catch (Exception e) {
            logger.error("selectByUpdate:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping("/updateMsg")
    @ResponseBody
    public BaseResult updateMsg(HttpServletRequest request, @RequestBody Customer vo){
        try{
            BaseResult result = customerService.customerUpdateMsg(vo);
            return result;
        }catch (Exception e) {
            logger.error("customerUpdateMsg:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    //查询跟进客户
    @RequestMapping( "/selectByFollow")
    @ResponseBody
    public BaseResult selectByFollow(@RequestBody Customer vo){
        try{
           Customer customer= customerService.selectByFollow(vo.getId());
            BaseResult baseResult = BaseResult.success();
            baseResult.setData(customer);
            return baseResult;
        }catch (Exception e) {
            logger.error("selectByFollow:", e);
            return BaseResult.exception(e.getMessage());
        }
    }


    //修改
    @RequestMapping("/customerUpdate")
    public @ResponseBody BaseResult bussinesUpdate(HttpServletRequest request,@RequestBody Customer vo){
        try{
            System.out.println("------------------------------------------------------aaaaaaaaaaaaaaa-----------------------------------------------------");
            BaseResult result =  customerService.updateCustomer(vo);
            return result;
        }catch (Exception e) {
            logger.error("seeInsert:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

}
