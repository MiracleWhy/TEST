package com.uton.carsokApi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.StoreRequest;
import com.uton.carsokApi.dao.CustomerMessageMapper;
import com.uton.carsokApi.model.Customer;
import com.uton.carsokApi.model.CustomerFlow;
import com.uton.carsokApi.service.CustomerMessageService;
import com.uton.carsokApi.service.CustomerService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/30.
 */
@Service
public class CustomerMessageServiceImpl implements CustomerMessageService {

    private final static Logger logger = Logger.getLogger(CustomerMessageServiceImpl.class);

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerMessageMapper customerMessageMapper;

    @Override
    public Page<Customer> selectCustList(Map<String,String> map) {
        try {
            Map screenMap = customerService.getScreen(map.get("xxly"), map.get("khjb"), map.get("dfzt"), map.get("gmys"));
            List<Integer> listId = customerService.selectId(map.get("mobile"));
            String xxly1 = screenMap.get("xxly").toString();//信息来源
            String khjb1 = screenMap.get("khjb").toString();//客户级别
            String dfzt1 = screenMap.get("dfzt").toString();//到访状态
            String gmys1 = screenMap.get("gmys").toString();//购买预算
            int dfzts = 0;
            if (!StringUtil.isEmpty(dfzt1)) {
                dfzts = Integer.parseInt(dfzt1);
            }
            PageHelper.startPage(Integer.parseInt(map.get("pc")), 10);
            Page<Customer> page = customerMessageMapper.selectCustList(Integer.parseInt(StringUtil.isEmpty(map.get("time")) ? "0" : map.get("time")), listId.get(0), listId.get(1), xxly1, khjb1, dfzts, gmys1,map.get("type"));
            long count = page.getTotal();
            System.out.print("!~!~!~!~!~" + count + "!~!~!~!~!~!~~");
            return page;
        }catch (Exception e){
            logger.error("selectCustList errors:   "+e);
        }
        return null;
    }

    @Override
    public Page<Customer> selectCustListBySearchOrMonth(String select,String mobile,String pc) {
        try {
            List<Integer> listId = customerService.selectId(mobile);
            PageHelper.startPage(Integer.parseInt(pc), 10);
            Page<Customer> page = customerMessageMapper.selectCustListBySearchOrMonth(select, listId.get(0), listId.get(1));
            long count = page.getTotal();
            System.out.print("!~!~!~!~!~" + count + "!~!~!~!~!~!~~");
            return page;
        }catch (Exception e){
            logger.error("selectCustListBySearchOrMonth errors:   "+e);
        }
        return null;
    }

    @Override
    public Map selectCustMsgById(String id) {
        try {
            Map<String, Object> map = new HashMap();
            Customer custMsg = customerMessageMapper.selectCustAllMsgById(id);
            DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(custMsg.getInTime());
            String time = sdf.format(custMsg.getInTime());
            map.put("intimes",time);
            cal.add(Calendar.DATE, 3);
            map.put("threeDays",new SimpleDateFormat("yyyy.MM.dd").format(cal.getTime()));
            cal.add(Calendar.DATE, 4);
            map.put("sevenDays",new SimpleDateFormat("yyyy.MM.dd").format(cal.getTime()));
            List<CustomerFlow> flowList = customerMessageMapper.seletCustFlowList(id);
            map.put("custMsg", custMsg);
            map.put("flowList", flowList);
            DateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String timeShow = sd.format(custMsg.getInTime());
            map.put("intimesShow",timeShow);
            return map;
        }catch (Exception e){
            logger.error("selectCustMsgById errors:   "+e);
        }
        return null;
    }

    @Override
    public Map selectThreeOrSevenCount(String mobile) {
        try{
            List<Integer> listId = customerService.selectId(mobile);
            Map<String,Integer> map = new HashMap<>();
            map.put("all",customerMessageMapper.selectThreeOrSevenCount(listId.get(0),listId.get(1),1));
            map.put("three",customerMessageMapper.selectThreeOrSevenCount(listId.get(0),listId.get(1),2));
            map.put("seven",customerMessageMapper.selectThreeOrSevenCount(listId.get(0),listId.get(1),3));
            map.put("out",customerMessageMapper.selectThreeOrSevenCount(listId.get(0),listId.get(1),4));
            return map;
        }catch (Exception e){
            logger.error(e);
        }
        return null;
    }
}
