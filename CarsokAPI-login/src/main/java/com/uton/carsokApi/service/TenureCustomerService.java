package com.uton.carsokApi.service;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.StoreInfoController;
import com.uton.carsokApi.controller.request.OffShelfRequest;
import com.uton.carsokApi.controller.request.TenureCarMsgRequest;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.util.HttpClientUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TenureCustomerService {

    @Autowired
    CustomerService customerService;

    @Autowired
    TenureCustomerMapper tenureCustomerMapper;

    @Autowired
    AcountMapper acountMapper;

    @Value("${buyerSide}")
    private String buyerSide;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    PruductOldcarMapper pruductOldcarMapper;

    @Autowired
    private ChildAccountMapper childAccountMapper;

    @Autowired
    private HandlerCountMapper handlerCountMapper;

    @Value("${store.url.prefix}")
    private String storeUrl;

    private final static Logger logger = Logger.getLogger(TenureCustomerService.class);

    public Pagebean<CustomerTenure> queryTenureList(int pr,int pc,int times,String mobile,int perfect){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<CustomerTenure> pb = new Pagebean();
        List<Integer> listId = customerService.selectId(mobile);
        System.out.println("------------------------------------------------------"+listId.get(0)+"@@@@@@@"+listId.get(1)+"-----------------------------------------------------");
        int accountId = listId.get(0);
        int childId = listId.get(1);
        Number number;
        if(times==1){
            number = tenureCustomerMapper.selectDayCount(accountId,childId,perfect);
            pb.setTr(number.intValue());
        }else if(times==2){
            number = tenureCustomerMapper.selectWeekCount(accountId,childId,perfect);
            pb.setTr(number.intValue());
        }else if(times==3){
            number = tenureCustomerMapper.selectMonthCount(accountId,childId,perfect);
            pb.setTr(number.intValue());
        }else if(times==4){
            number = tenureCustomerMapper.selectYearCount(accountId,childId,perfect);
            pb.setTr(number.intValue());
        }
        pb.setPc(pc);
        pb.setPr(pr);
        List<CustomerTenure> beanList = tenureCustomerMapper.selectTenureMsg(p1,p2,times,accountId,childId,perfect);
        pb.setBeanlist(beanList);
        return pb;
    }

    public Pagebean<CustomerTenure> queryByMonth(int pr,int pc,String month,String mobile,int perfect){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<CustomerTenure> pb = new Pagebean();
        List<Integer> listId = customerService.selectId(mobile);
        System.out.println("------------------------------------------------------"+listId.get(0)+"@@@@@@@"+listId.get(1)+"-----------------------------------------------------");
        int accountId = listId.get(0);
        int childId = listId.get(1);
        Number number = tenureCustomerMapper.selectByMonthCount(accountId,childId,perfect,month);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        List<CustomerTenure> beanList = tenureCustomerMapper.selectTenureMsgByMonth(p1,p2,month,accountId,childId,perfect);
        pb.setBeanlist(beanList);
        return pb;
    }

    public Map querytenureCarPeopleMsg(int tid, int tcid, String mobile){
        List<Integer> list = customerService.selectId(mobile);
        int accountId = list.get(0);
        int childId = list.get(1);
        Map map = new HashMap();
        map.put("tenureMap",tenureCustomerMapper.selectTenureAll(tid)==null?"":tenureCustomerMapper.selectTenureAll(tid));
        CustomerCar customerCar = tenureCustomerMapper.selectCarAll(tcid);
        map.put("carMap",customerCar);
        Date date = customerCar.getSaleTime();
        DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String saleTime = sdf.format(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 3);
        String threeTime = (new SimpleDateFormat("yyyy.MM.dd")).format(cal.getTime());
        cal.add(Calendar.DATE, 4);
        String sevenTime = (new SimpleDateFormat("yyyy.MM.dd")).format(cal.getTime());
        map.put("followMap",tenureCustomerMapper.selectFollowMessage(tcid));
        map.put("saleTime",saleTime);
        map.put("threeTime",threeTime);
        map.put("sevenTime",sevenTime);
        return map;
    }

    public Pagebean<CustomerTenure> selectBySearch(int pr, int pc, String selects, String mobile, String month){
        // month处理
        if(!StringUtil.isEmpty(month)){
            month = month + "-00";
        }
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<CustomerTenure> pb = pb = new Pagebean<CustomerTenure>();
        List<Integer> list = customerService.selectId(mobile);
        System.out.println("------------------------------------------------------"+list.get(0)+"@@@@@@@"+list.get(1)+"-----------------------------------------------------");
        int accountId = list.get(0);
        int childId = list.get(1);
        Number number = tenureCustomerMapper.selectBySearch(selects,month,accountId,childId);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        List<CustomerTenure> beanList = tenureCustomerMapper.selectSearchList(p1,p2,selects,month,accountId,childId);
        pb.setBeanlist(beanList);
        return pb;
    }

    public Pagebean<CustomerTenure> selectByNull(int pr,int pc,String perfectStatus,String mobile,int time,String month){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<CustomerTenure> pb = pb = new Pagebean<CustomerTenure>();
        List<Integer> list = customerService.selectId(mobile);
        System.out.println("------------------------------------------------------"+list.get(0)+"@@@@@@@"+list.get(1)+"-----------------------------------------------------");
        int accountId = list.get(0);
        int childId = list.get(1);
        String months = "";
        if(!StringUtil.isEmpty(month)){
            months = month + "-00";
        }
        if(("1").equals(perfectStatus)){
            Number number = tenureCustomerMapper.selectByNull(accountId,childId,time,months);
            pb.setTr(number.intValue());
            pb.setPc(pc);
            pb.setPr(pr);
            List<CustomerTenure> beanList = tenureCustomerMapper.selectNullList(p1,p2,accountId,childId,time,months);
            pb.setBeanlist(beanList);
        }else if(("2").equals(perfectStatus)){
            Number number = tenureCustomerMapper.selectByNotNull(accountId,childId,time,months);
            pb.setTr(number.intValue());
            pb.setPc(pc);
            pb.setPr(pr);
            List<CustomerTenure> beanList = tenureCustomerMapper.selectNotNullList(p1,p2,accountId,childId,time,months);
            pb.setBeanlist(beanList);
        }
        return pb;
    }

    public int updateCustCarMsg(TenureCarMsgRequest vo){
        CustomerTenure customerTenure = new CustomerTenure();
        if(vo.getTid()!=null){
            customerTenure = tenureCustomerMapper.selectTenureById(vo.getTid());
        }
        CustomerCar customerCar = tenureCustomerMapper.selectTenureCarById(vo.getTcid());

        CustomerTenure ct = new CustomerTenure();
        CustomerCar cc = new CustomerCar();
        int cts = 0;
        int ccs = 0;
        if(customerTenure != null){
            ct.setCustName(vo.getCustName()==null?"":vo.getCustName());
            ct.setCustPhone(vo.getCustPhone()==null?"":vo.getCustPhone());
            ct.setCustSex(vo.getCustSex()==null?"":vo.getCustSex());
            ct.setCustAge(vo.getCustAge()==null?"":vo.getCustAge());
            ct.setCustBirthday(vo.getCustBirthday());
            ct.setCustVisittime(vo.getCustVisittime());
            ct.setCustThreevisit(vo.getCustThreevisit()==null?"":vo.getCustThreevisit());
            ct.setCustSevenvisit(vo.getCustSevenvisit()==null?"":vo.getCustSevenvisit());
            ct.setCustVocation(vo.getCustVocation()==null?"":vo.getCustVocation());
            ct.setCustBeforecar(vo.getCustBeforecar()==null?"":vo.getCustBeforecar());
            ct.setCustRemark(vo.getCustRemark()==null?"":vo.getCustRemark());
            ct.setCustsaledpeople(customerTenure.getCustsaledpeople()==null?"":customerTenure.getCustsaledpeople());
            ct.setCreateTime(customerTenure.getCreateTime()==null?new Date():customerTenure.getCreateTime());
            ct.setUpdateTime(new Date());
            ct.setEnable(customerTenure.getEnable());
            ct.setTenurecarId(vo.getTcid());
            ct.setPurchaseStatus(vo.getPurchaseStatus());
            if(vo.getTid()!=null){
                ct.setId(vo.getTid());
                cts = tenureCustomerMapper.updateTenureMsg(ct);
            }else{
                if(vo.getTenurecarId()==null){
                    int num = 0;
                    if(vo.getTcid() != null){
                        num = tenureCustomerMapper.selectByTenurecarId(vo.getTcid());
                    }
                    if(num == 0){
                        cts = tenureCustomerMapper.insertTenure(ct);
                    }
                }
            }
            int count = handlerCountMapper.deleteBaoyouMsg(vo.getTcid());
        }
        if(customerCar != null){
            tenureCustomerMapper.deleteByTcid(vo.getTcid());
            cc.setId(vo.getTcid());
            cc.setTenureCarname(customerCar.getTenureCarname());
            cc.setTenureVin(vo.getTenureVin());
            cc.setTenureCarnum(vo.getTenureCarnum());
            cc.setTenureCartype(vo.getTenureCartype());
            cc.setTenureCarprice(vo.getTenureCarprice());
            cc.setCarMales(vo.getCarMales());
            cc.setTenureCompulsory(vo.getTenureCompulsory());
            cc.setTenureBusiness(vo.getTenureBusiness());
            cc.setTenureMaintain(vo.getTenureMaintain());
            cc.setTenureWarranty(vo.getTenureWarranty());
            cc.setProductId(customerCar.getProductId());
            cc.setCreateTime(customerCar.getCreateTime());
            cc.setUpdateTime(new Date());
            cc.setEnable(customerCar.getEnable());
            cc.setSalePeople(customerCar.getSalePeople());
            cc.setSaleTime(customerCar.getSaleTime());
            cc.setAccountId(customerCar.getAccountId());
            cc.setChildId(customerCar.getChildId());
            cc.setBrand(customerCar.getTenureCarname().split(" ")[0]);
            ccs = tenureCustomerMapper.insertTenureCar(cc);
        }

        PruductOldcar p = new PruductOldcar();
        p.setPruductId(customerCar.getProductId());
        PruductOldcar pruductOldcar = pruductOldcarMapper.selectByModel(p);
        CustomerCar oldCar = new CustomerCar();
        if(vo.getTenurecarId()!=null && vo.getPurchaseStatus()==7){
            CustomerCar newCar = tenureCustomerMapper.selectTenureCarById(vo.getTcid());
            oldCar.setTenureCarname(newCar.getTenureCarname());
            oldCar.setTenureCarnum(newCar.getTenureCarnum());
            oldCar.setTenureCarprice(newCar.getTenureCarprice());
            oldCar.setTenureCartype(newCar.getTenureCartype());
            oldCar.setTenureCompulsory(newCar.getTenureCompulsory());
            oldCar.setTenureVin(newCar.getTenureVin());
            oldCar.setTenureBusiness(newCar.getTenureBusiness());
            oldCar.setTenureCompulsory(newCar.getTenureCompulsory());
            oldCar.setTenureMaintain(newCar.getTenureMaintain());
            oldCar.setTenureWarranty(newCar.getTenureWarranty());
            oldCar.setCarMales(newCar.getCarMales());
            oldCar.setSaleTime(newCar.getSaleTime());
            oldCar.setProductId(newCar.getProductId());
            oldCar.setBrand(newCar.getTenureCarname().split(" ")[0]);
            oldCar.setUpdateTime(new Date());
            oldCar.setId(vo.getTenurecarId());
            int newUpOld = tenureCustomerMapper.updateOldByNew(oldCar);
            int deleteNewCar = tenureCustomerMapper.updateNewCarIsDelete(vo.getTcid());
            CustomerTenure oldCustMsg = tenureCustomerMapper.selectCustMsgByCarId(vo.getTenurecarId());
            CustomerTenure newCustMsg = new CustomerTenure();
            newCustMsg.setId(oldCustMsg.getId());
            newCustMsg.setUpdateTime(new Date());
            newCustMsg.setSaleTime(newCar.getSaleTime());
            newCustMsg.setCustAge(vo.getCustAge());
            newCustMsg.setCustBeforecar(vo.getCustBeforecar());
            newCustMsg.setCustName(vo.getCustName());
            newCustMsg.setCustPhone(vo.getCustPhone());
            newCustMsg.setCustThreevisit(vo.getCustThreevisit());
            newCustMsg.setCustSevenvisit(vo.getCustSevenvisit());
            newCustMsg.setCustSex(vo.getCustSex());
            newCustMsg.setCustVocation(vo.getCustVocation());
            newCustMsg.setCustBirthday(vo.getCustBirthday());
            newCustMsg.setCustRemark(vo.getCustRemark());
            newCustMsg.setCustVisittime(vo.getCustVisittime());
            newCustMsg.setPurchaseStatus(vo.getPurchaseStatus());
            int updateOldCustomer = tenureCustomerMapper.updateTenureMsg(newCustMsg);
        }
        //if(vo.getTid()==null) {
        	try{
                Acount acount = acountMapper.selectByPrimaryKey(customerCar.getAccountId());
                Customer custList = customerMapper.selectByCustPhone(vo.getCustPhone(),acount.getId());
                if(custList!=null){
                    customerMapper.updateStatus(vo.getPurchaseStatus().toString(),custList.getId(),custList.getCustomerCome()==null?0:custList.getCustomerCome());
                }
                //信息推送给买家端
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String url = buyerSide;
                Map<String, Object> map = new HashedMap();
                BuyerInfo buyerInfo = new BuyerInfo();
                buyerInfo.setLicensePlate(vo.getTenureCarnum()==null?vo.getTenureCarnum():vo.getTenureCarnum());
                buyerInfo.setVinCode(customerCar.getTenureVin());
                buyerInfo.setSellerId(customerCar.getAccountId().toString());
                JSONObject carModel=new JSONObject();
                String carName=customerCar.getTenureCarname();
                String carNames []=carName.split(" ");
                int length=carNames.length;
                if(length>3){
                    carModel.put("carBrand",carNames[0] );
                    carModel.put("carSeries",carNames[1] );
                    String simpleModel="";
                    for(int i=2;i<length;i++){
                    		simpleModel=simpleModel+carNames[i];
                    }
                    carModel.put("carModel",simpleModel );
                }
                else{
                	carModel.put("carBrand",carNames[0] );
                    carModel.put("carModel",carNames[1] );
                }
                Picture pictureQuery = new Picture();
    			pictureQuery.setProductId(customerCar.getProductId());
    			short pictype = 1;
    			pictureQuery.setType(pictype); //只查询主图
    			List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
                if(pruductOldcar!=null){
                    carModel.put("province",pruductOldcar.getProvince());
                    carModel.put("city",pruductOldcar.getCity());
                    carModel.put("firstUpTime",pruductOldcar.getFirstUpTime());
                }
                carModel.put("carId",customerCar.getProductId());
                carModel.put("merchantDescription",acount.getMerchantDescr());
                carModel.put("carImage", pictureList.get(0).getPicPath());
                ChildAccount childAccount=new ChildAccount();
                childAccount.setId(customerCar.getChildId());
                childAccount= childAccountMapper.selectByModel(childAccount);
                carModel.put("sellerName",childAccount==null? customerCar.getSalePeople():childAccount.getChildAccountName());
                buyerInfo.setCarModel(carModel);
                buyerInfo.setSalesPrice(customerCar.getTenureCarprice().toString());
                buyerInfo.setSalesMileage(customerCar.getCarMales());
                buyerInfo.setPaymentType(customerCar.getTenureCartype()==null?"0":customerCar.getTenureCartype().toString());
                buyerInfo.setCompulsoryInsuranceDate(vo.getTenureCompulsory()==null?null:formatter.format(vo.getTenureCompulsory()).toString());
                buyerInfo.setCommercialInsuranceDate(vo.getTenureBusiness()==null?null:formatter.format(vo.getTenureBusiness()).toString());
                buyerInfo.setLastMiantenanceDate(vo.getTenureMaintain()==null?null:formatter.format(vo.getTenureMaintain()).toString());
                buyerInfo.setWarrantyDueDate(vo.getTenureWarranty()==null?null:formatter.format(vo.getTenureWarranty()).toString());
                buyerInfo.setSalesConsultant(customerCar.getSalePeople());
                buyerInfo.setFromApp("CHESHANG_APP");
                CustomerInfo customerInfo = new CustomerInfo();
                customerInfo.setPhone(vo.getCustPhone());
                customerInfo.setName(vo.getCustName());
                buyerInfo.setCustomerInfo(customerInfo);
                SellerInfo sellerInfo = new SellerInfo();
                sellerInfo.setThirdId(customerCar.getAccountId()+"");
                sellerInfo.setFrom("CHESHANG_APP");
                sellerInfo.setInfoUrl(storeUrl + acount.getAccountCode()+".html");
                sellerInfo.setMerchantName(acount.getMerchantName());
                sellerInfo.setName(StringUtils.isEmpty(acount.getRealName())?acount.getMerchantName():acount.getRealName());
                sellerInfo.setAddress(acount.getMerchantAddress());
                sellerInfo.setPhone(acount.getAccount());
                buyerInfo.setSellerInfo(sellerInfo);
                buyerInfo.setBuyerPhone(vo.getCustPhone());
                String param = JSONObject.toJSONString(buyerInfo);
                BaseEvent event = new BaseEvent();
                event.setData(param);
                event.setEventName(EventConstants.PUSH_AUTO_INFO_TO_BUYER_APP_EVENT);
                eventBus.publish(event);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            }
        //}
        if(cts>0&&ccs>0){
            return 1;
        }else{
            return 0;
        }
    }

    public Map selectCount(int accountId,int childId,int times,String month){
        Map<String,Object> map = new HashedMap();
        map.put("dayCount",tenureCustomerMapper.selectDayCount(accountId,childId,0));
        map.put("weekCount",tenureCustomerMapper.selectWeekCount(accountId,childId,0));
        map.put("monthCount",tenureCustomerMapper.selectMonthCount(accountId,childId,0));
        map.put("yearCount",tenureCustomerMapper.selectYearCount(accountId,childId,0));
        if(times == 0){
            map.put("perfect", tenureCustomerMapper.selectYesCountByMonth(accountId, childId, month));
            map.put("noperfect", tenureCustomerMapper.selectNoCountByMonth(accountId, childId, month));
        }else {
            map.put("perfect", tenureCustomerMapper.selectYesCount(accountId, childId, times));
            map.put("noperfect", tenureCustomerMapper.selectNoCount(accountId, childId, times));
        }
        return map;
    }


    public Pagebean<CustomerTenure> selectBySaled(int pr,int pc,String mobile,int otherId){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<CustomerTenure> pb = pb = new Pagebean<CustomerTenure>();
        List<Integer> list = customerService.selectId(mobile);
        System.out.println("------------------------------------------------------"+list.get(0)+"@@@@@@@"+list.get(1)+"-----------------------------------------------------");
        int accountId = list.get(0);
        int childId = list.get(1);
        Number number = tenureCustomerMapper.selectBySaledCount(accountId,childId,otherId);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        List<CustomerTenure> beanList = tenureCustomerMapper.selectBySaledMsg(p1,p2,accountId,childId,otherId);
        pb.setBeanlist(beanList);
        return pb;
    }

    public int updateCarWelfare(Welfare vo){
        int index = 0;
        Welfare welfare = new Welfare();
        if(vo.getId()==0){
            welfare.setAccountMobile(vo.getAccountMobile());
            welfare.setReferral(vo.getReferral());
            welfare.setReplacementVehicle(vo.getReplacementVehicle());
            welfare.setBuybackVehicle(vo.getBuybackVehicle());
            welfare.setRemark(vo.getRemark());
            index = tenureCustomerMapper.insertWelfare(vo);
        }else if(vo.getId()!=0){
            welfare.setId(vo.getId());
            welfare.setAccountMobile(vo.getAccountMobile());
            welfare.setReferral(vo.getReferral());
            welfare.setReplacementVehicle(vo.getReplacementVehicle());
            welfare.setBuybackVehicle(vo.getBuybackVehicle());
            welfare.setRemark(vo.getRemark());
            index = tenureCustomerMapper.updateWelfare(vo);
        }
        String uuid = UUID.randomUUID().toString();
        BuyerWelfare buyerWelfare = new BuyerWelfare();
        buyerWelfare.setMessageName("MESSAGE_UPDATE_CAR_WELFARE");
        Map<String,Object> map = new HashMap();
        map.put("accountMobile",vo.getAccountMobile());
        buyerWelfare.setPayload(map);
        buyerWelfare.setFromApp("CHESHANG_APP");
        buyerWelfare.setBizId(uuid);

        String param = JSONObject.toJSONString(buyerWelfare);
        BaseEvent event = new BaseEvent();
        event.setData(param);
        event.setEventName(EventConstants.MESSAGE_UPDATE_CAR_WELFARE);
        eventBus.publish(event);
        return index;
    }

    public BaseResult selectCarWelfare(String mobile){
        Welfare welfare = tenureCustomerMapper.selectCarWelfareByMobile(mobile);
        BaseResult baseResult = BaseResult.success();
        if(welfare!=null){
            baseResult.setData(welfare);
            return baseResult;
        }else {
            baseResult.setRetCode("0003");
            baseResult.setRetMsg("还未添加过福利数据");
            return baseResult;
        }
    }

    public Map selectCustIfExist(String mobile,String custPhone){
        List<Integer> list = customerService.selectId(mobile);
        int accountId = list.get(0);
        int childId = list.get(1);
        CustomerTenure customerTenure = tenureCustomerMapper.selectNewMsgBycustPhone(custPhone,accountId,childId);
        Map<String,Object> map = new HashMap<String, Object>();
        if(customerTenure != null){
            CustomerCar customerCar = tenureCustomerMapper.selectCarAll(customerTenure.getTenurecarId());
            List<CustomerTenure> beanList = tenureCustomerMapper.selectCarListByCustPhone(custPhone,accountId,childId);
            map.put("tenureMap",customerTenure);
            map.put("carMap",customerCar);
            map.put("beanlist",beanList);
        }else {
            map.put("tenureMap","");
            map.put("carMap","");
        }
        return map;
    }

    public Object selectCustCounts(int accountId, int childId) {
        Map<String,Object> map = new HashMap();
        map.put("allNum",tenureCustomerMapper.selectNum(accountId, childId, 1));
        map.put("threeNum",tenureCustomerMapper.selectNum(accountId, childId, 2));// 3日
        map.put("seveNum",tenureCustomerMapper.selectNum(accountId, childId, 3));// 7日
        map.put("outDateNum",tenureCustomerMapper.selectNum(accountId, childId, 4));// 过期
        return map;
    }

    public Page<CustomerTenure> selectListNew(String pageNum, String mobile, String type, String selects, String compeleteStatus, String dateStatus, String buyStatus) {
        List<Integer> list = customerService.selectId(mobile);
        int accountId = list.get(0);
        int childId = list.get(1);
        PageHelper.startPage(Integer.parseInt(pageNum),10);
        Page<CustomerTenure> page = tenureCustomerMapper.selectListNew(accountId, childId, type, selects, compeleteStatus, dateStatus, buyStatus);
        long count = page.getTotal();
        return page;
    }

    public BaseResult saveFollowMessage(TenureCarFollow tenureCarFollow) {
        BaseResult baseResult = BaseResult.success();
        List<Integer> list = customerService.selectId(tenureCarFollow.getMobile());
        int accountId = list.get(0);
        int childId = list.get(1);
        tenureCarFollow.setAccountId(accountId);
        tenureCarFollow.setChildId(childId);
        tenureCarFollow.setCreateTime(new Date());
        tenureCustomerMapper.saveFollowMessage(tenureCarFollow);
        if (tenureCarFollow.getFollowType() == 2 || tenureCarFollow.getFollowType() == 3){
            tenureCustomerMapper.updateStatusByType(tenureCarFollow.getTenurecarId(),tenureCarFollow.getFollowType());
        }
        return baseResult;
    }
}
