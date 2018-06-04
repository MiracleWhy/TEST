package com.uton.carsokApi.controller;

import com.github.pagehelper.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.TenureCarMsgRequest;
import com.uton.carsokApi.model.CustomerTenure;
import com.uton.carsokApi.model.Pagebean;
import com.uton.carsokApi.model.TenureCarFollow;
import com.uton.carsokApi.model.Welfare;
import com.uton.carsokApi.service.CustomerService;
import com.uton.carsokApi.service.TenureCustomerService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/13 0013.
 */
@Controller
@RequestMapping("/TenureCustomer")
public class TenureCustomerController {

    private final static Logger logger = Logger.getLogger(TenureCustomerController.class);

    @Autowired
    TenureCustomerService tenureCustomerService;

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = { "/tenurePage" }, method = { RequestMethod.GET })
    public String tenurePage(HttpServletResponse response, HttpServletRequest request, String mobile,String otherId) {
        if(StringUtil.isEmpty(otherId)){
            otherId = "0";
        }
        request.setAttribute("otherId",otherId);
        request.setAttribute("mobile",mobile);
        return "/retentionCustom";
    }

    @RequestMapping(value = { "/tenureDetail" }, method = { RequestMethod.GET })
    public String tenureDetail(HttpServletResponse response, HttpServletRequest request, String mobile) {
        request.setAttribute("mobile",mobile);
        return "/retentionCustomDetail";
    }

    @RequestMapping(value = { "/tenureMsgPage" }, method = { RequestMethod.GET })
    public String tenureMsgPage(HttpServletRequest request,String id,String mobile){
        String tid = id.split(":")[0];
        String tcid = id.split(":")[1];
        System.out.println(tid+":::::::::::::::"+tcid);
        request.setAttribute("tid",tid);
        request.setAttribute("tcid",tcid);
        request.setAttribute("mobile",mobile);
        return "/welfare";
    }

    @RequestMapping("/tenureList")
    @ResponseBody
    public Map tenureList(HttpServletRequest request){
        String time = request.getParameter("times");
        String mobile = request.getParameter("mobile");
        String perfects = request.getParameter("perfect");
        String month = request.getParameter("month");
        int perfect = Integer.parseInt(perfects);
        Map map = pageResult(request,time,mobile,perfect,month);
        return map;
    }

    @RequestMapping("/tenureBySaled")
    @ResponseBody
    public Map tenureBySaled(HttpServletRequest request){
        String time = request.getParameter("times");
        String mobile = request.getParameter("mobile");
        String perfects = request.getParameter("perfect");
        String otherIds = request.getParameter("otherId");
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }
        int otherId = 0;
        if(!StringUtil.isEmpty(otherIds)){
            otherId = Integer.parseInt(otherIds);
        }
        List<Integer> listId = customerService.selectId(mobile);
        int accountId = listId.get(0);
        int childId = listId.get(1);
        int times = -1;
        if(!StringUtil.isEmpty(time)){
            times = Integer.parseInt(time);
        }
        Pagebean<CustomerTenure> pb = tenureCustomerService.selectBySaled(pr,pc,mobile,otherId);
        Map<String,Object> map = new HashMap();
        map.put("count", tenureCustomerService.selectCount(accountId, childId, times,""));
        map.put("record",pb);
        return map;
    }

    @RequestMapping("/tenurePerfectIfList")
    @ResponseBody
    public Map tenurePerfectIfList(HttpServletRequest request){
        String perfectStatus = request.getParameter("perfectStatus");
        String mobile = request.getParameter("mobile");
        String times = request.getParameter("times");
        String month = request.getParameter("month");
        if("".equals(times)){
            times = "1";
        }
        int time = Integer.parseInt(times);
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }
        Pagebean<CustomerTenure> pb = tenureCustomerService.selectByNull(pr,pc,perfectStatus,mobile,time,month);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("record",pb);
        return map;
    }

    @RequestMapping("/tenureCarPeopleMsg")
    @ResponseBody
    public Map tenureCarPeopleMsg (HttpServletRequest request){
        String tids = request.getParameter("tid");
        String tcids = request.getParameter("tcid");
        String mobile = request.getParameter("mobile");
        if("".equals(tids)){
            tids = "0";
        }
        int tid = Integer.parseInt(tids);
        int tcid = Integer.parseInt(tcids);
        Map map = tenureCustomerService.querytenureCarPeopleMsg(tid,tcid,mobile);
        return map;
    }
    @RequestMapping("/selectBySearch")
    @ResponseBody
    public Map selectBySearch(HttpServletRequest request){
        String select = request.getParameter("select");
        String mobile = request.getParameter("mobile");
        String month  = request.getParameter("month");
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }
        Pagebean<CustomerTenure> pb = tenureCustomerService.selectBySearch(pr,pc,select,mobile,month);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("record",pb);
        return map;
    }

    public Map pageResult(HttpServletRequest request,String time,String mobile,int perfect,String month){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Integer> listId = customerService.selectId(mobile);
        int accountId = listId.get(0);
        int childId = listId.get(1);
        int pr = 10;
        int pc = getpc(request);
        if (pc == 0) {
            pc = 1;
        }
        Pagebean<CustomerTenure> pb = new Pagebean<CustomerTenure>();
        if(StringUtil.isEmpty(month)) {
            int times = 1;
            if(!StringUtil.isEmpty(time)){
                times = Integer.parseInt(time);
            }
            pb = tenureCustomerService.queryTenureList(pr, pc, times, mobile, perfect);
            map.put("count", tenureCustomerService.selectCount(accountId, childId, times,""));
        }else{
            String months = month + "-00";
            pb = tenureCustomerService.queryByMonth(pr, pc, months, mobile, perfect);
            map.put("count", tenureCustomerService.selectCount(accountId, childId, 0,months));
        }
        map.put("record", pb);
        return map;
    }

    private int getpc(HttpServletRequest request){
        String value = request.getParameter("pc");
        if(value == null || value.trim().isEmpty()){
            return 1;
        }
        return Integer.parseInt(value);
    }

    @RequestMapping(value = { "/updateCustCarMsg" }, method = { RequestMethod.POST })
    @ResponseBody
    public String updateCustCarMsg(HttpServletRequest request, TenureCarMsgRequest vo){
        //return "000";
        int flag = tenureCustomerService.updateCustCarMsg(vo);
        if(flag>0){
            return "000";
        }
        return "001";
    }

    @RequestMapping(value = { "/updateCarWelfare" }, method = { RequestMethod.POST })
    @ResponseBody
    public BaseResult updateCarWelfare(HttpServletRequest request,@RequestBody Welfare vo){
        try {
            int flag = tenureCustomerService.updateCarWelfare(vo);
            if (flag > 0) {
                return BaseResult.success();
            } else {
                return BaseResult.fail("0001", "添加或修改失败");
            }
        }catch (Exception e) {
            logger.error("updateCarWelfare error:", e);
            return BaseResult.exception(e.getMessage());
        }

    }

    @RequestMapping(value = { "/selectCarWelfare" }, method = { RequestMethod.POST })
    @ResponseBody
    public BaseResult selectCarWelfare(HttpServletRequest request, @RequestBody Welfare vo){
        try {
            return tenureCustomerService.selectCarWelfare(vo.getAccountMobile());
        }catch (Exception e) {
            logger.error("updateCarWelfare error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value = { "/selectCustIfExist" }, method = { RequestMethod.POST })
    @ResponseBody
    public Map selectCustIfExist (HttpServletRequest request,String mobile,String custPhone){
        Map map = tenureCustomerService.selectCustIfExist(mobile,custPhone);
        return map;
    }

    @RequestMapping( value = { "/selectCustCounts" }, method = { RequestMethod.POST } )
    @ResponseBody
    public Map selectCustCounts(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        List<Integer> listId = customerService.selectId(mobile);
        int accountId = listId.get(0);
        int childId = listId.get(1);
        Map<String,Object> map = new HashMap();
        map.put("counts", tenureCustomerService.selectCustCounts(accountId,childId));
        return map;
    }

    @RequestMapping( value = { "/selectListNew" }, method = { RequestMethod.POST } )
    @ResponseBody
    public Map selectListNew(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            String pageNum = request.getParameter("page");
            String mobile = request.getParameter("mobile");
            String type = request.getParameter("type");
            String selects = "";
            String compeleteStatus = "";//1-待完善 2-已完善
            String dateStatus = "";//1-本日 2-本周 3-本月
            String buyStatus = "";//3：首次购买 4：首次置换 5：复购 7：置换
            switch (type){
                case "0" : // 搜索
                    selects = request.getParameter("selects");
                    break;
                case "1" : // 全部
                    //break;
                case "2" : // 3日
                    //break;
                case "3" : // 7日
                    //break;
                case "4" : // 过期
                    compeleteStatus = request.getParameter("compeleteStatus");
                    dateStatus = request.getParameter("dateStatus");
                    buyStatus = request.getParameter("buyStatus");
                    break;
            }
            Page<CustomerTenure> page = tenureCustomerService.selectListNew(pageNum, mobile, type, selects, compeleteStatus, dateStatus, buyStatus);
            map.put("record",page);
            List<Integer> listId = customerService.selectId(mobile);
            int accountId = listId.get(0);
            int childId = listId.get(1);
            map.put("counts", tenureCustomerService.selectCustCounts(accountId,childId));
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return map;
    }

    @RequestMapping(value = { "/saveFollowMessage" }, method = { RequestMethod.POST })
    @ResponseBody
    public BaseResult saveFollowMessage(HttpServletRequest request, TenureCarFollow tenureCarFollow){
        try {
            return tenureCustomerService.saveFollowMessage(tenureCarFollow);
        }catch (Exception e) {
            logger.error("saveFollowMessage error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }
}
