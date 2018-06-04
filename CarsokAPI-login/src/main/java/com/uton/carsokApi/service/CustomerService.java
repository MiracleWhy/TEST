package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.CustomerMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/13 0013.
 * 客户管理
 */
@Service
public class CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    AcountMapper acountMapper;

    @Autowired
    ChildAccountMapper childAccountMapper;



    /**
     * 门店新增客户
     * @param vo
     * @return
     */
    public BaseResult customerInsert(Customer vo){
        Customer customer = new Customer();
        List<Integer> list = selectId(vo.getMobiles());
        System.out.println("------------------------------------------------------"+list.get(0)+"@@@@@@@"+list.get(1)+"-----------------------------------------------------");
        customer.setAccountId(list.get(0));
        customer.setChildId(list.get(1));
        customer.setInTime(vo.getInTime());
        customer.setOutTime(vo.getOutTime());
        customer.setPeopleNum(vo.getPeopleNum());
//        List<Customer> cust = customerMapper.selectByCustPhone(vo.getCustomerPhone(),list.get(0));
//        if(cust!=null&&cust.size()!=0){
//            if(cust.get(cust.size()-1).getCustomerStatus()<=2||cust.get(cust.size()-1).getCustomerStatus()==6){
//                return BaseResult.fail("0005","该客户已经存在且为初次到店或电话邀约状态,请联系经理");
//            }else if(cust.get(cust.size()-1).getCustomerStatus()==3&&(vo.getCustomerStatus()<=2||vo.getCustomerStatus()==6)){
//                return BaseResult.fail("0006","该客户已经存在且为已购状态,只能选择置换或者复购");
//            }
//        }
        Acount record = new Acount();
        record.setAccount(vo.getMobiles());
        Acount acount = acountMapper.selectByModel(record);//查找主账号
        if(acount==null){//为查到主账号，则为子账号
            List<ChildAccount> listC = childAccountMapper.selectBymobile(vo.getMobiles());
            customer.setSalesAdviser(listC.get(0).getChildAccountName());
        }else{
            customer.setSalesAdviser(acount.getMerchantName());
        }
        customer.setCustomerName(vo.getCustomerName());
        customer.setCustomerPhone(vo.getCustomerPhone());
        customer.setCustomerStatus(vo.getCustomerStatus());
        customer.setInformationSources(vo.getInformationSources());
        customer.setIntentionalVehicle(vo.getIntentionalVehicle());
        customer.setCustomerLevel(vo.getCustomerLevel());
        customer.setCustomerBudget(vo.getCustomerBudget());
        customer.setCustomerRegion(vo.getCustomerRegion());
        customer.setCustomerCome(vo.getCustomerCome());
        customer.setBrand(vo.getIntentionalVehicle().split(" ")[0]);
        customer.setCustomerTrack(vo.getCustomerTrack());
        customerMapper.insertCustomer(customer);
        return BaseResult.success();
    }

    public Pagebean<Customer> selectByRemind(int pr,int pc,int otherId,String mobile){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<Customer> pb = new Pagebean<Customer>();
        List<Integer> list = selectId(mobile);
        System.out.println("------------------------------------------------------"+list.get(0)+"@@@@@@@"+list.get(1)+"-----------------------------------------------------");
        int accountId = list.get(0);
        int childId = list.get(1);
        Number number = customerMapper.selectByRemindCount(otherId,accountId,childId);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        List<Customer> beanlist = customerMapper.selectByRemindMsg(p1,p2,otherId,accountId,childId);
        pb.setBeanlist(beanlist);
        return pb;
    }


    public Pagebean<Customer> querycustomerList(int pr,int pc,int times,String mobile,String xxly,String khjb,String dfzt,String gmys){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<Customer> pb = new Pagebean<Customer>();
        Number number;
        List<Integer> listId = selectId(mobile);
        System.out.println("------------------------------------------------------"+listId.get(0)+"@@@@@@@"+listId.get(1)+"-----------------------------------------------------");
        int accountId = listId.get(0);
        int childId = listId.get(1);
        Map screenMap = getScreen(xxly,khjb,dfzt,gmys);
        String xxly1 = screenMap.get("xxly").toString();
        String khjb1 = screenMap.get("khjb").toString();
        String dfzt1 = screenMap.get("dfzt").toString();
        String gmys1 = screenMap.get("gmys").toString();
        int dfzts = 0;
        if(!StringUtil.isEmpty(dfzt1)){
            dfzts = Integer.parseInt(dfzt1);
        }
        if(times==0){
            number = customerMapper.selectCount(accountId,childId,xxly1,khjb1,dfzts,gmys1);
            pb.setTr(number.intValue());
        }else if(times==1){
            number = customerMapper.selectDayCount(accountId,childId,xxly1,khjb1,dfzts,gmys1);
            pb.setTr(number.intValue());
        }else if(times==2){
            number = customerMapper.selectWeekCount(accountId,childId,xxly1,khjb1,dfzts,gmys1);
            pb.setTr(number.intValue());
        }else if(times==3){
            number = customerMapper.selectMonthCount(accountId,childId,xxly1,khjb1,dfzts,gmys1);
            pb.setTr(number.intValue());
        }

        pb.setPc(pc);
        pb.setPr(pr);

        List<Customer> beanlist = customerMapper.selectCustomerMsg(p1,p2,times,accountId,childId,xxly1,khjb1,dfzts,gmys1);

        pb.setBeanlist(beanlist);
        return pb;
    }

    public Pagebean<Customer> queryCustByMonth(int pr,int pc,String month,String mobile,String xxly,String khjb,String dfzt,String gmys){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<Customer> pb = new Pagebean<Customer>();
        Number number;
        List<Integer> listId = selectId(mobile);
        System.out.println("------------------------------------------------------"+listId.get(0)+"@@@@@@@"+listId.get(1)+"-----------------------------------------------------");
        int accountId = listId.get(0);
        int childId = listId.get(1);
        Map screenMap = getScreen(xxly,khjb,dfzt,gmys);
        String xxly1 = screenMap.get("xxly").toString();
        String khjb1 = screenMap.get("khjb").toString();
        String dfzt1 = screenMap.get("dfzt").toString();
        String gmys1 = screenMap.get("gmys").toString();
        int dfzts = 0;
        if(!StringUtil.isEmpty(dfzt1)){
            dfzts = Integer.parseInt(dfzt1);
        }
        if(!StringUtil.isEmpty(month)){
            month = month + "-00";
        }
        number = customerMapper.selectCountByMonth(accountId,childId,xxly1,khjb1,dfzts,gmys1,month);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        List<Customer> beanlist = customerMapper.selectCustMsgByMonth(p1,p2,month,accountId,childId,xxly1,khjb1,dfzts,gmys1);
        pb.setBeanlist(beanlist);
        return pb;
    }

    public List<Integer> selectId(String mobile){
        int accountId = 0;
        int childId = 0;
        Acount record = new Acount();
        record.setAccount(mobile);
        Acount acount = acountMapper.selectByModel(record);//通过主账户电话 查询主账户
        if(acount==null){
            //未查到主账户，则为子账户电话
            List<ChildAccount> list = childAccountMapper.selectBymobile(mobile);//子账户集合
            if(list !=null && list.size()>0){
                Acount recordc = new Acount();
                recordc.setAccount(list.get(0).getAccountPhone());//获取主账户电话
                Acount acountc = acountMapper.selectByModel(recordc);//查询主账户信息
                accountId = acountc.getId();//主账号id
                childId = list.get(0).getId();//子账户id
                System.out.print("-----------------------accountId="+acountc.getId()+"***childId="+list.get(0).getId()+"----------------------");
            }else {
                accountId = 0;
                childId = 0;
            }
        }else{
            accountId = acount.getId();//主账户id
            System.out.print("-----------------------accountId="+acount.getId()+"----------------------");
        }
        List<Integer> list = new ArrayList<Integer>();
        list.add(accountId);
        list.add(childId);
        return list;
    }

    public Pagebean<Customer> querycustomerListBysearchkey(int pr,int pc,String selects, String month,String mobile){
        if(!StringUtil.isEmpty(month)){
            month = month + "-00";
        }
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<Customer> pb = new Pagebean<Customer>();
        List<Integer> list = selectId(mobile);
        System.out.println("------------------------------------------------------"+list.get(0)+"@@@@@@@"+list.get(1)+"-----------------------------------------------------");
        int accountId = list.get(0);
        int childId = list.get(1);
        Number number = customerMapper.selectBySearchKey(selects,month,accountId,childId);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);

        List<Customer> beanlist = customerMapper.querycustomerListBysearchkey(p1,p2,selects,month,accountId,childId);
        pb.setBeanlist(beanlist);
        return pb;
    }
    public Map<String,Object> getCount(int accountId,int childId,String xxly,String khjb,String dfzt,String gmys){
        Map screenMap = getScreen(xxly,khjb,dfzt,gmys);
        String xxly1 = screenMap.get("xxly").toString();//信息来源
        String khjb1 = screenMap.get("khjb").toString();//客户级别
        String dfzt1 = screenMap.get("dfzt").toString();//到访状态
        String gmys1 = screenMap.get("gmys").toString();//购买预算
        int dfzts = 0;
        if(!StringUtil.isEmpty(dfzt1)){
            dfzts = Integer.parseInt(dfzt1);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("daytr",customerMapper.selectDayCount(accountId,childId,xxly1,khjb1,dfzts,gmys1));
        map.put("weektr",customerMapper.selectWeekCount(accountId,childId,xxly1,khjb1,dfzts,gmys1));
        map.put("monthtr",customerMapper.selectMonthCount(accountId,childId,xxly1,khjb1,dfzts,gmys1));
        return map;
    }
    public List<Customer> updateTrack(String custTrack,String custPhone,String custName,int custId){
        if(customerMapper.updateTrack(custTrack,custPhone,custName,custId)>0){
            return customerMapper.selectMsgByNameOrPhone(custName,custPhone,custId);
        }else{
            return null;
        }
    }

    public int insertFlow(int custId,String custFlow,String customerStatus,String remind,int custCome,String mobile){
        int status = Integer.parseInt(customerStatus);
        customerMapper.updateStatus(customerStatus,custId,custCome);
        List<Integer> listMobile = selectId(mobile);
        int accountId = listMobile.get(0);
        int childId = listMobile.get(1);
        if("2".equals(customerStatus)){
            customerMapper.updateThreeSevenById(custId,3);
        }else if("6".equals(customerStatus)){
            customerMapper.updateThreeSevenById(custId,7);
        }
        return customerMapper.insertFlow(custId,custFlow,status,remind,custCome,accountId,childId);
    }

    public List<CustomerFlow> selectAllFlows(int custId){
        return customerMapper.selectAllFlows(custId);
    }

    public List<Customer> selectTrack(String custPhone,String custName,int custId){

        return customerMapper.selectMsgByNameOrPhone(custName,custPhone,custId);
    }

    public List<CustomerFlow> selectFlow(int custId){
        List<CustomerFlow> list = customerMapper.selectFlowMsgByCustomerId(custId);
        if(list.size()>0){
            return list;
        }
        return new ArrayList<CustomerFlow>();
    }

    public int selectCustomerStatus(int custId){
        return customerMapper.selectCustomerStatus(custId);
    }

    public Pagebean<Customer> customerScreenMessage(int pc,String xxly,String khjb,String dfzt,String gmys,String mobile,int time){
        int pr = 10;
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        List<Integer> listMobile = selectId(mobile);
        int accountId = listMobile.get(0);
        int childId = listMobile.get(1);
        Pagebean<Customer> pb = new Pagebean<Customer>();
        Number number;
        List<Customer> list = new ArrayList<Customer>();
        Map screenMap = getScreen(xxly,khjb,dfzt,gmys);
        String xxly1 = screenMap.get("xxly").toString();
        String khjb1 = screenMap.get("khjb").toString();
        String dfzt1 = screenMap.get("dfzt").toString();
        String gmys1 = screenMap.get("gmys").toString();
        int dfzts = 0;
        if(!StringUtil.isEmpty(dfzt1)){
            dfzts = Integer.parseInt(dfzt1);
        }
        number = customerMapper.selectScreenCount(accountId,childId,xxly1,khjb1,dfzts,gmys1,time);
        list = customerMapper.ScreenMessageAll(p1,p2,accountId,childId,xxly1,khjb1,dfzts,gmys1,time);
        pb.setTr(number.intValue());
        pb.setBeanlist(list);

        pb.setPc(pc);
        pb.setPr(pr);
        return pb;
    }
    public Map<String,Object> getScreen(Object xxly,Object khjb,Object dfzt,Object gmys){
        Map<String,Object> map = new HashedMap();
        int dfzts = 0;
        if(xxly != null && khjb != null && dfzt != null){
            if("所有来源".equals(xxly)){
                xxly = "";
            }
            if("所有预算".equals(gmys)){
                gmys = "";
            }
            if("所有级别".equals(khjb)){
                khjb = "";
            }else if(khjb.equals("A : 一周以内(购买欲望强)")){
                khjb = "A";
            }else if(khjb.equals("B : 一月以内(准买车用户)")){
                khjb = "B";
            }else if(khjb.equals("C : 三个月以内(有购买意向)")){
                khjb = "C";
            }else if(khjb.equals("D : 闲逛(近期无意向)")){
                khjb = "D";
            }
            if("全部状态".equals(dfzt)){
                dfzt = "";
            }else if(dfzt.equals("初次")){
                dfzts = 1;
            }else if(dfzt.equals("3日电话邀约")){
                dfzts = 2;
            }else if(dfzt.equals("7日电话邀约")){
                dfzts = 6;
            }else if(dfzt.equals("首次购买")){
                dfzts = 3;
            }else if(dfzt.equals("首次置换购买")){
                dfzts = 4;
            }else if(dfzt.equals("置换")){
                dfzts = 7;
            }else if(dfzt.equals("复购")){
                dfzts = 5;
            }
            map.put("xxly",xxly);
            map.put("khjb",khjb);
            map.put("dfzt",dfzts);
            map.put("gmys",gmys);
        }else{
            map.put("xxly","");
            map.put("khjb","");
            map.put("dfzt","");
            map.put("gmys","");
        }

        return map;
    }

    public List<Customer> selectCustIfExist(String mobiles,String customerPhone){
        List<Integer> list = selectId(mobiles);
        int accountId = list.get(0);
        List<Customer> customer = customerMapper.selectCustIf(customerPhone,accountId,0);//查询主账户及子账户中是否添加过此客户
        return customer;
    }


    public BaseResult selectByUpdate(String ids){
        BaseResult baseResult = BaseResult.success();
        if(StringUtil.isEmpty(ids)){
            ids = "0";
        }
        int id = Integer.parseInt(ids);
        Customer customer = customerMapper.selectByUpdate(id);
        baseResult.setData(customer);
        return baseResult;
    }
    public BaseResult customerUpdateMsg(Customer vo){
        BaseResult baseResult = BaseResult.success();
        Customer customer = new Customer();
        customer.setCustomerName(vo.getCustomerName());
        customer.setCustomerPhone(vo.getCustomerPhone());
        customer.setInformationSources(vo.getInformationSources());
        customer.setIntentionalVehicle(vo.getIntentionalVehicle());
        customer.setCustomerLevel(vo.getCustomerLevel());
        customer.setCustomerBudget(vo.getCustomerBudget());
        customer.setCustomerRegion(vo.getCustomerRegion());
        customer.setCustomerCome(vo.getCustomerCome());
        customer.setBrand(vo.getIntentionalVehicle().split(" ")[0]);
        customer.setCustomerStatus(vo.getCustomerStatus());
        customer.setCustomerTrack(vo.getCustomerTrack());
        int updates = customerMapper.updateCustomerMsg(customer);
        if(updates>0){
            baseResult.setData(updates);
            return baseResult;
        }else {
            return baseResult.fail("0001","修改失败");
        }

    }


    public Customer selectByFollow(int id){
        Customer buModel=customerMapper.selectByFollow(id);
        return buModel!=null? buModel:null;
    }

    public BaseResult updateCustomer(Customer customer){
        Customer cus = customerMapper.selectByFollow(customer.getId());
        int Status = cus.getCustomerStatus();
        customerMapper.updateCustomer(customer);
        Customer cus01 = customerMapper.selectByFollow(customer.getId());
        int StatusAft = cus01.getCustomerStatus();
        if (Status != StatusAft) {
            updateCustomerFlower(customer.getId());
        }
        return BaseResult.success();
    }



    public BaseResult updateCustomerFlower(int customerId){
        customerMapper.updateCustomerFlow(customerId);
        return BaseResult.success();
    }




    public Pagebean<Customer> queryListForCustPhone(String select,String mobile,int pc){
        int pr = 10;
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        List<Integer> listMobile = selectId(mobile);
        int accountId = listMobile.get(0);
        int childId = listMobile.get(1);
        Pagebean<Customer> pb = new Pagebean<Customer>();
        Number number = customerMapper.selectListByCustPhoneCount(select,accountId,childId);
        List<Customer> beanlist = customerMapper.selectListByCustPhone(p1,p2,select,accountId,childId);
        pb.setTr(number.intValue());
        pb.setBeanlist(beanlist);
        pb.setPc(pc);
        pb.setPr(pr);
        return pb;
    }
}
