package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.DRReceptionDetailResponse;
import com.uton.carsokApi.controller.request.DRReceptionResponse;
import com.uton.carsokApi.controller.request.DailyReportRequest;
import com.uton.carsokApi.controller.request.DailyReportZanRequest;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.ChildRoleMapper;
import com.uton.carsokApi.dao.DailyReportMapper;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.*;
import com.uton.carsokApi.util.DateStyle;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/19.
 */
@Service
public class DailyReportServiceImpl implements DailyReportService {

    @Autowired
    DailyReportMapper dailyReportMapper;

    @Autowired
    ChildAccountMapper childAccountMapper;

    @Autowired
    ChildRoleMapper childRoleMapper;

    @Resource
    CacheService cacheService;

    @Autowired
    SubUserService subUserService;

    @Autowired
    SharedRecordMapper sharedRecordMapper;

    @Autowired
    GleefulReportService gleefulReportService;

    @Autowired
    DailyCheckService dailyCheckService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    SaasAuthorityService authorityService;


    /**
     * 进入《今日战报》，获取卖车、收车、整备、接待列表。
     *
     * @param account
     * @param date    e.g 2017-12-05
     * @return
     */
    @SuppressWarnings("Duplicates")
    public DailyReportResponse intoDailySaleReport(Acount account, String date) {
//        BigDecimal revenue =new BigDecimal(0);//卖出价（保有完善）
//        BigDecimal collCost=new BigDecimal(0);//库存收车价
//        BigDecimal zbCost=new BigDecimal(0);//整备价
//        int accountId = account.getId();
//        List<ChildAccount> children = childAccountMapper.selectByAcountmobile(account.getAccount());
//        //卖车块--本月卖车 本日卖车 营收 毛利润
//        //本月卖车
//        List<CustomerTenureCar> carListOfMonth = dailyReportMapper.selectCustomerTenureCarList(accountId, children, date, "month");
//        Integer selledMonCount = carListOfMonth.size();
//        //本日卖车
//        List<CustomerTenureCar> carListOfDay = dailyReportMapper.selectCustomerTenureCarList(accountId, children, date, "day");
//        Integer selledDayCount = carListOfDay.size();
//        for (CustomerTenureCar item : carListOfDay) {
//            //本日营收(单位：万元)
//            if (item.getTenureCarprice() != null) {
//                revenue = revenue.add(new BigDecimal(item.getTenureCarprice())).setScale(2,BigDecimal.ROUND_HALF_UP);
//            }
//            if(item.getProductId()!=null){
//                Product product=productMapper.selectByProductId(item.getProductId().toString());
//                if(product.getCloseingPrice()!=null){
//                    collCost=collCost.add(product.getCloseingPrice());
//                }
//                Double zbMoney = dailyReportMapper.queryRestoreCostByProductId(item.getProductId());
//                if(zbMoney!=null){
//                    zbCost=zbCost.add(new BigDecimal(zbMoney).setScale(2,BigDecimal.ROUND_HALF_UP));
//                }
//            }
//        }
//        //本日毛利润
//        BigDecimal dayProfit =revenue.subtract(collCost).subtract(zbCost).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
//
//
//        //收车块--本月收车 本日收车 日支出(收车表数据)
//        //本月收车
//        Integer acqCarMonCount = dailyReportMapper.countCollectionByDate(accountId, children, date, "month");
//        //本日收车
//        Integer acqCarDayCount = dailyReportMapper.countCollectionByDate(accountId, children, date, "day");
//        //日支出(单位：万元)
//        Double acqCarDayAmount = dailyReportMapper.sumAcqCostByDate(accountId, children, date, "day");
//        if (acqCarDayAmount == null) {
//            acqCarDayAmount = 0.00;
//        }
//
//        //整备块--本月整备 本日整备 日支出（整备表数据）
//        //本月整备
//        Integer zbMonCount =dailyReportMapper.countRestoreByDate(account.getAccount(),date,"month");
//        //本日整备
//        Integer zbDayCount =dailyReportMapper.countRestoreByDate(account.getAccount(),date,"day");
//        //日整备支出(单位：元)
//        Double zbDayAmount = dailyReportMapper.sumRestoreCostByDate(account.getAccount(),date,"day");
//        if(zbDayAmount!=null){
//            //(单位：万元)
//            zbDayAmount=zbDayAmount/10000;
//        }else {
//            zbDayAmount=0.00;
//        }
//
//        //接待
//        Integer receptionCount = dailyReportMapper.getReceptionCount(accountId, children, date, "day");//本日接待人数
//
//
//        DailyReportResponse drr = new DailyReportResponse();
//        drr.setSelledMonCount(selledMonCount);
//        drr.setSelledDayCount(selledDayCount);
//        drr.setSelledDayAmount(revenue.doubleValue());
//        drr.setDayProfit(dayProfit.doubleValue());
//        drr.setAcqMonCount(acqCarMonCount);
//        drr.setAcqDayCount(acqCarDayCount);
//        drr.setAcqDayAmount(acqCarDayAmount);
//        drr.setZbMonCount(zbMonCount);
//        drr.setZbDayCount(zbDayCount);
//        drr.setZbDayAmount(zbDayAmount);
//        drr.setReceptionCount(receptionCount);
//        drr.setMerchantName(account.getMerchantName());


        List<ChildAccount> children = childAccountMapper.selectByAcountmobile(account.getAccount());
        //本月卖车
        List<CustomerTenureCar> carListOfMonth = dailyReportMapper.selectCustomerTenureCarList(account.getId(), children, date, "month");
        Integer selledMonCount = carListOfMonth.size();
        //本日卖车
        List<CustomerTenureCar> carListOfDay = dailyReportMapper.selectCustomerTenureCarList(account.getId(), children, date, "day");
        Integer selledDayCount = carListOfDay.size();

        //本月收车
        Integer acqCarMonCount = dailyReportMapper.countCollectionByDate(account.getId(), children, date, "month");
        //本日收车
        Integer acqCarDayCount = dailyReportMapper.countCollectionByDate(account.getId(), children, date, "day");
        //日支出(单位：万元)
        Double acqCarDayAmount = dailyReportMapper.sumAcqCostByDate(account.getId(), children, date, "day");
        if (acqCarDayAmount == null) {
            acqCarDayAmount = 0.00;
        }

        //本月整备
        Integer zbMonCount = dailyReportMapper.countRestoreByDate(account.getAccount(), date, "month");
        //本日整备
        Integer zbDayCount = dailyReportMapper.countRestoreByDate(account.getAccount(), date, "day");
        //日整备支出(单位：元)
        Double zbDayAmount = dailyReportMapper.sumRestoreCostByDate(account.getAccount(), date, "day");
        if (zbDayAmount != null) {
            //(单位：万元)
            zbDayAmount = zbDayAmount / 10000;
        } else {
            zbDayAmount = 0.00;
        }

        //接待
        Integer receptionCount = dailyReportMapper.getReceptionCount(account.getId(), children, date, "day");//本日接待人数


        BigDecimal revenue = new BigDecimal(0);//卖出价（保有完善）
        BigDecimal acqCost = new BigDecimal(0);//库存收车价
        BigDecimal zbCost = new BigDecimal(0);//整备价
        //已售车辆部分信息
        for (CustomerTenureCar item : carListOfDay) {
            //出售价格(取《车辆管理》模块中的卖车价格)/《库存管理》卖出价格
            if (item.getTenureCarprice() != null) {
                revenue = revenue.add(new BigDecimal(item.getTenureCarprice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //productId 当已售车辆为自定义车辆时，productId为空
            Integer productId = item.getProductId();
            if (productId != null) {
                Product product = productMapper.selectByProductId(String.valueOf(productId));
                //收车价格（发车管理 单位：万元）
                if (product.getCloseingPrice() != null) {
                    acqCost = acqCost.add(product.getCloseingPrice());
                }
                //收车价格（整备管理经理填写 单位：元）
                if (product.getCloseingPrice() == null) {
                    Double acqCostOfRestore = dailyReportMapper.queryAcqCostByProductId(productId);
                    if (acqCostOfRestore != null) {
                        acqCost = acqCost.add(new BigDecimal(acqCostOfRestore / 10000).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                }
                //整备价格(单位：元)
                Double zbMoney = dailyReportMapper.queryRestoreCostByProductId(productId);
                if (zbMoney != null) {
                    zbCost = zbCost.add(new BigDecimal(zbMoney / 10000).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        //毛利润(单位：万元)
        BigDecimal dayProfit = revenue.subtract(acqCost).subtract(zbCost).setScale(2, BigDecimal.ROUND_HALF_UP);

        DailyReportResponse drr = new DailyReportResponse();
        drr.setSelledMonCount(selledMonCount);
        drr.setSelledDayCount(selledDayCount);
        drr.setSelledDayAmount(revenue.doubleValue());
        drr.setDayProfit(dayProfit.doubleValue());
        drr.setAcqMonCount(acqCarMonCount);
        drr.setAcqDayCount(acqCarDayCount);
        drr.setAcqDayAmount(acqCarDayAmount);
        drr.setZbMonCount(zbMonCount);
        drr.setZbDayCount(zbDayCount);
        drr.setZbDayAmount(zbDayAmount);
        drr.setReceptionCount(receptionCount);
        drr.setMerchantName(account.getMerchantName());
        return drr;
    }


    /**
     * 获取售车列表
     *
     * @param account
     * @return
     */
    @Override
    public List<SelledCarMeg> selledCarMsgTotal(Acount account, String date) {
        List<ChildAccount> children = childAccountMapper.selectByAcountmobile(account.getAccount());
        //已售车辆部分信息
        List<CustomerTenureCar> carList = dailyReportMapper.selectCustomerTenureCarList(account.getId(), children, date, "day");
        List<SelledCarMeg> list = new ArrayList<>();
        for (CustomerTenureCar item : carList) {
            SelledCarMeg selledCarMeg = new SelledCarMeg();
            //车辆名称
            selledCarMeg.setProductName(item.getTenureCarname());
            //出售价格
            if (item.getTenureCarprice() != null) {
                selledCarMeg.setSalePrice(new BigDecimal(item.getTenureCarprice()));
            } else {
                selledCarMeg.setSalePrice(new BigDecimal(0));
            }
            //productId 当已售车辆为自定义车辆时，productId为空
            Integer productId = item.getProductId();
            if (productId != null) {
                Product product = productMapper.selectByProductId(String.valueOf(productId));
                selledCarMeg.setId(productId);
                //收车价格（发车管理 单位：万元）
                BigDecimal acqCost = product.getCloseingPrice();
                selledCarMeg.setCloseingPrice(acqCost);
                //整备价格(单位：元)
                Double zbMoney = dailyReportMapper.queryRestoreCostByProductId(productId);
                if (zbMoney != null) {
                    zbMoney = zbMoney / 10000;
                    selledCarMeg.setZbMoney(new BigDecimal(zbMoney));
                }
                //毛利润(单位：万元)
                if (acqCost == null) {
                    acqCost = new BigDecimal(0);
                }
                if (zbMoney == null) {
                    zbMoney = new Double(0);
                }
                BigDecimal oneProfit = selledCarMeg.getSalePrice().subtract(acqCost).subtract(new BigDecimal(zbMoney)).setScale(2, BigDecimal.ROUND_HALF_UP);
                selledCarMeg.setOneProfit(oneProfit);
            }
            if (productId == null) {
                selledCarMeg.setOneProfit(selledCarMeg.getSalePrice());
            }
            //销售人电话
            if (item.getChildId() == 0) {
                selledCarMeg.setSaledPeople(account.getAccount());
                selledCarMeg.setSalePeopleName("主账号");
            } else {
                ChildAccount record = new ChildAccount();
                record.setId(item.getChildId());
                record = childAccountMapper.selectByModel(record);
                selledCarMeg.setSaledPeople(record.getChildAccountMobile());
                selledCarMeg.setSalePeopleName(record.getChildAccountName());
            }
            //图片地址
            if (productId != null) {
                String picPath = dailyReportMapper.queryPicPathByProductId(productId);
                selledCarMeg.setPicPath(picPath);
            }
            list.add(selledCarMeg);
        }
        return list;
    }

    /**
     * 获取收车列表
     *
     * @param accountId
     * @param date
     * @return
     */
    public List<AcqCarMsg> acqCarMsgTotal(int accountId, String date) {
        List<AcqCarMsg> list = dailyReportMapper.selectAcqCarMsg(accountId, date);
        return list;
    }

    /**
     * 获取整备列表
     *
     * @param accountId
     * @param date
     * @return
     */
    public List<ZbMsg> zbMsgTotal(int accountId, String date) {
        List<ZbMsg> list = dailyReportMapper.selectZbMsg(accountId, date);
        for (ZbMsg item : list) {//整备任务只记录主账号归属
            item.setZber("主账号");
        }
        return list;
    }

    /**
     * 获取接待列表
     *
     * @param accountId
     * @param date
     * @return
     */
    public List<DRReceptionResponse> getReceptionList(String accountId, String date) {
        List<DRReceptionResponse> drReceptionResponseList = new ArrayList<>();
        try {
            drReceptionResponseList = dailyReportMapper.getReceptionList(date, accountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drReceptionResponseList;

    }


    /**
     * 获取接待详情
     *
     * @param accountId
     * @param date
     * @return
     */
    public List<DRReceptionDetailResponse> getReceptionDetail(String date, String accountId, String childID) {
        List<DRReceptionDetailResponse> list = new ArrayList<>();
        try {
            list = dailyReportMapper.getReceptionDetail(date, accountId, childID);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }


    @Override
    public BaseResult dailyReportInsert(DailyReportRequest vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportYes", vo.getReportYes());
        map.put("reportNo", vo.getReportNo());
        map.put("reportConcert", vo.getReportConcert());
        map.put("reportRemark", vo.getReportRemark());
        String param = JSONObject.toJSONString(map);
        DailyReport dailyReport = new DailyReport();
        dailyReport.setResult(param);
        dailyReport.setReporter(vo.getReporter());
        dailyReport.setTitle(vo.getTitle());
        dailyReport.setIsRead(0);
        dailyReport.setReportDate(new java.util.Date());
        dailyReport.setCreateTime(new java.util.Date());
        dailyReport.setUpdateTime(new java.util.Date());
        dailyReport.setEnable(1);
        int count = dailyReportMapper.insertDailyReport(dailyReport);
        if (count > 0) {
            return BaseResult.success();
        } else {
            return BaseResult.fail("0001", "添加日报失败");
        }
    }

    @Override
    public BaseResult selectDailyList(HttpServletRequest request, DailyReportRequest vo) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        String reporter = vo.getReporter();
        //初始化权限=管理者权限
        boolean isManagerOrNot = false;
        Pagebean<DailyReport> pb = new Pagebean<>();
        int pageSize = 10;
        int pageNumber = vo.getPage();
        BaseResult baseResult = BaseResult.success();
        if (acount.getSubPhone() != null) {//说明登录账号为子账号
            //获取登录子账号javabean
            ChildAccount child = new ChildAccount();
            child.setChildAccountMobile(reporter);
            child = childAccountMapper.selectByModel(child);
            //查询子账号权限
            isManagerOrNot = authorityService.judgeManagerOrNot(child.getId());
        } else {//说明登录账号为主账号
            isManagerOrNot = true;
        }
        if (isManagerOrNot) {
            //管理者权限:show all data
            return setDatato(request, acount.getAccount(), pageSize, pageNumber);
        } else {
            //员工权限:only self
            PageHelper.startPage(pageNumber, pageSize);
            List<DailyReport> beanList = dailyReportMapper.selectDailyReport(vo.getReporter());
            PageInfo pageInfo = new PageInfo(beanList);
            //zz 必须赋值  否则序列化response失败
            pb.setTr(1);
            pb.setPc(1);
            pb.setPr(1);
            pb.setBeanlist(pageInfo.getList());
            baseResult.setData(pb);
        }
        return baseResult;
    }

    public BaseResult setDatato(HttpServletRequest request, String mobile, int pr, int pc) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        int p1 = (pr * pc) - pr;
        int p2 = pr;
        ChildAccount c = new ChildAccount();
        c.setAccountPhone(mobile);
        List<ChildAccount> childList = childAccountMapper.selectListByModel(c);
        List<String> list = new ArrayList<>();
        for (ChildAccount lists : childList) {
            list.add(lists.getChildAccountMobile());
        }
        Number number = dailyReportMapper.selectCountByAccount(list);
        List<DailyReport> beanList = dailyReportMapper.selectDailyReportByAccount(list, p1, p2);
        List<DailyReportZan> zanList = selectCarIdByAccountPhone(acount.getAccount(), "User", acount.getAccount());
        if (!StringUtil.isEmpty(acount.getSubPhone())) {
            if (selectRoleNameByChildPhone(acount.getSubPhone()).contains("yyglz")) {
                beanList = dailyReportMapper.selectDailyReportByAccountZjl(acount.getSubPhone(), list, p1, p2);
                List<DailyReportZan> zjlZanList = selectCarIdByAccountPhone(acount.getAccount(), "User", acount.getSubPhone());
                for (DailyReport list1 : beanList) {
                    if (zjlZanList.size() == 0) {
                        list1.setZjlZanIf(false);
                    }
                    for (DailyReportZan d : zjlZanList) {
                        List<Integer> zanCarIdList = new ArrayList<Integer>();
                        zanCarIdList.add(d.getCarId());
                        if (zanCarIdList.contains(list1.getId())) {
                            list1.setZanId(d.getId());
                            list1.setZjlZanIf(true);
                            break;
                        } else {
                            list1.setZjlZanIf(false);
                        }
                    }
                }
            }
        } else {
            for (DailyReport list1 : beanList) {
                if (zanList.size() == 0) {
                    list1.setAccountZanIf(false);
                }
                for (DailyReportZan d : zanList) {
                    List<Integer> zanCarIdList = new ArrayList<Integer>();
                    zanCarIdList.add(d.getCarId());
                    if (zanCarIdList.contains(list1.getId())) {
                        list1.setZanId(d.getId());
                        list1.setAccountZanIf(true);
                        break;
                    } else {
                        list1.setAccountZanIf(false);
                    }
                }
            }
        }
        Pagebean<DailyReport> pb = new Pagebean<>();
        pb.setTr(number.intValue());
        pb.setBeanlist(beanList);
        pb.setPc(pc);
        pb.setPr(pr);
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(pb);
        return baseResult;
    }

    @Override
    public BaseResult dailyReportReadIf(DailyReportRequest vo) {
        int status = dailyReportMapper.updateIsRead(vo.getId());
        if (status > 0) {
            return BaseResult.success();
        } else {
            return BaseResult.fail("0001", "已读失败");
        }
    }

    /**
     * 总经理-日报已读
     *
     * @return
     * @张玉功
     * @paramvo
     */
    @Override
    public BaseResult dailyReportReadIfZjl(DailyReportRequest vo, String zjlPhone) {
        int status = dailyReportMapper.insertZjlIsRead(vo.getId(), zjlPhone);
        if (status > 0) {
            return BaseResult.success();
        } else {
            return BaseResult.fail("0001", "已读失败");
        }
    }

    @Override
    public BaseResult selectDailyReportMsg(HttpServletRequest request, DailyReportRequest vo) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        BaseResult baseResult = BaseResult.success();
        DailyReport dailyReport = dailyReportMapper.selectDailyReportMsg(vo.getId());
        baseResult.setData(dailyReport);
        return baseResult;
    }


//    /**
//     *获取
//     * @param accountId
//     * @param date
//     * @return
//     */
//    public int getReceptionCount(String accountId,String date)
//    {
//        int result = 0;
//        try
//        {
//            result = dailyReportMapper.getReceptionCount(date,accountId);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return result;
//    }


    /**
     * @param accountId
     * @param date
     * @param childId
     * @return
     */
    public List<DRReceptionDetailResponse> getReception(String accountId, String date, String childId) {
        List<DRReceptionDetailResponse> drReceptionResponseList = new ArrayList<>();
        try {
            drReceptionResponseList = dailyReportMapper.getReceptionDetail(date, accountId, childId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drReceptionResponseList;
    }

    public BaseResult dailyReportScreen(HttpServletRequest request, DailyReportRequest vo) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        List<ChildAccount> childLists = childAccountMapper.selectAllChild(acount.getAccount());
        List<String> childlist = new ArrayList<String>();
        childlist.add("0");
        for (ChildAccount child : childLists) {
            childlist.add(child.getChildAccountMobile());
        }
        java.util.Date date = new java.util.Date();
        int pr = 10;
        int pc = vo.getPage();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Pagebean<DailyReport> pb = new Pagebean<>();
        Number number = dailyReportMapper.selectCountByScreen(vo.getReportDate() == null ? null : vo.getReportDate(), StringUtil.isEmpty(vo.getReporter()) ? "" : vo.getReporter(), StringUtil.isEmpty(vo.getRoleName()) ? "" : vo.getRoleName(), childlist);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        int p1 = (pr * pc) - pr;
        int p2 = pr;
        List<DailyReport> beanList = dailyReportMapper.selectMsgByScreen(p1, p2, vo.getReportDate() == null ? null : vo.getReportDate(), StringUtil.isEmpty(vo.getReporter()) ? "" : vo.getReporter(), StringUtil.isEmpty(vo.getRoleName()) ? "" : vo.getRoleName(), childlist);
        String reportedCount = dailyReportMapper.selectReportedCount(null, childlist);
        List<DailyReportZan> zanList = selectCarIdByAccountPhone(acount.getAccount(), "User", acount.getAccount());
        if (!StringUtil.isEmpty(acount.getSubPhone())) {
            if (selectRoleNameByChildPhone(acount.getSubPhone()).contains("yyglz")) {
                beanList = dailyReportMapper.selectMsgByScreenZjl(acount.getSubPhone(), p1, p2, vo.getReportDate() == null ? null : vo.getReportDate(), StringUtil.isEmpty(vo.getReporter()) ? "" : vo.getReporter(), StringUtil.isEmpty(vo.getRoleName()) ? "" : vo.getRoleName(), childlist);
                List<DailyReportZan> zjlZanList = selectCarIdByAccountPhone(acount.getAccount(), "User", acount.getSubPhone());
                for (DailyReport list1 : beanList) {
                    if (zjlZanList.size() == 0) {
                        list1.setZjlZanIf(false);
                    }
                    for (DailyReportZan d : zjlZanList) {
                        List<Integer> zanCarIdList = new ArrayList<Integer>();
                        zanCarIdList.add(d.getCarId());
                        if (zanCarIdList.contains(list1.getId())) {
                            list1.setZanId(d.getId());
                            list1.setZjlZanIf(true);
                            break;
                        } else {
                            list1.setZjlZanIf(false);
                        }
                    }
                }
            }
        } else {
            for (DailyReport list1 : beanList) {
                if (zanList.size() == 0) {
                    list1.setAccountZanIf(false);
                }
                for (DailyReportZan d : zanList) {
                    List<Integer> zanCarIdList = new ArrayList<Integer>();
                    zanCarIdList.add(d.getCarId());
                    if (zanCarIdList.contains(list1.getId())) {
                        list1.setZanId(d.getId());
                        list1.setAccountZanIf(true);
                        break;
                    } else {
                        list1.setAccountZanIf(false);
                    }
                }
            }
        }
        pb.setBeanlist(beanList);
        pb.setContent("今日已" + reportedCount + "人发日报，共" + childlist.size() + "人");
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(pb);
        return baseResult;
    }

    @Override
    public BaseResult dailyReportShowInfo(HttpServletRequest request, DailyReportRequest vo) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        List<ChildAccount> childLists = childAccountMapper.selectAllChild(acount.getAccount());
        List<String> childlist = new ArrayList<String>();
        childlist.add("0");
        for (ChildAccount child : childLists) {
            childlist.add(child.getChildAccountMobile());
        }
        List<ChildAccount> reportedlist = dailyReportMapper.selectReportedList(null, childlist);

        Iterator iterator = childLists.iterator();
        while (iterator.hasNext()) {
            ChildAccount childAccount = (ChildAccount) iterator.next();
            for (int j = 0; j < reportedlist.size(); j++) {
                ChildAccount reportedChild = reportedlist.get(j);
                if (childAccount.getId().equals(reportedChild.getId())) {
                    iterator.remove();
                }
            }
        }

        Map map = new HashMap();
        map.put("titleUnreport", "今日未发" + childLists.size() + "人");
        map.put("listUnreport", childLists);
        map.put("titleReported", "今日已发" + reportedlist.size() + "人");
        map.put("listReported", reportedlist);
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(map);
        return baseResult;
    }

    @Override
    public BaseResult dailyReportCountRoleList(HttpServletRequest request) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        List<ChildAccount> childLists = childAccountMapper.selectAllChild(acount.getAccount());
        List<String> childlist = new ArrayList<String>();
        childlist.add("0");
        for (ChildAccount child : childLists) {
            childlist.add(child.getChildAccountMobile());
        }
        String reportedCount = dailyReportMapper.selectReportedCount(null, childlist);
        List<ZbRole> roleList = dailyReportMapper.selectRoleList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("childList", childLists);
        map.put("roleList", roleList);
        map.put("content", "今日已" + reportedCount + "人发日报，共" + childLists.size() + "人");
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(map);
        return baseResult;
    }

    @Override
    public BaseResult dailyReportZan(HttpServletRequest request, DailyReportZanRequest vo) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (vo.getId() != null) {
            if (dailyReportMapper.updateZanStatus(vo.getId()) > 0) {
                return BaseResult.success();
            } else {
                return BaseResult.fail("0001", "点赞状态修改失败");
            }
        } else {
            DailyReportZan dailyReportZan = new DailyReportZan();
            dailyReportZan.setAccountPhone(acount.getAccount());
            dailyReportZan.setGetfabulousPhone(vo.getGetfabulousPhone());
            dailyReportZan.setFabulousPhone(vo.getFabulousPhone());
            dailyReportZan.setReportType(vo.getReportType());
            dailyReportZan.setDepartmentType(vo.getDepartmentType());
            dailyReportZan.setCarId(vo.getCarId());
            dailyReportZan.setCreateTime(new java.util.Date());
            dailyReportZan.setUpdateTime(new java.util.Date());
            dailyReportZan.setEnable(vo.getEnable());
            int daily = dailyReportMapper.insetDailyReportZan(dailyReportZan);
            if (daily > 0) {
                return BaseResult.success(dailyReportZan.getId());
            } else {
                return BaseResult.fail("0001", "点赞失败");
            }
        }
    }

    @Override
    public List<String> selectRoleNameByChildPhone(String mobile) {
        return childRoleMapper.selectRoleNameByChildPhone(mobile);
    }

    @Override
    public List<DailyReportZan> selectCarIdByAccountPhone(String accountPhone, String departmentType, String
            fabulousPhone) {
        return dailyReportMapper.selectCarIdByAccountPhone(accountPhone, departmentType, fabulousPhone);
    }


    @Override
    public BaseResult employeeDailyReportInsert(DailyReportRequest vo, HttpServletRequest request) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        //点赞人电话
        String mobile = acount.getAccount();
        //子账号电话
        String childPhone = dailyReportMapper.selectChildAccountMobile(vo.getId());
        //根据子账号电话查出主账号电话
        String acountMobile = dailyReportMapper.selectAccountMobile(childPhone);

        DailyReportZan drz = new DailyReportZan();
        drz.setAccountPhone(acountMobile);
        drz.setGetfabulousPhone(childPhone);
        drz.setEnable(1);
        drz.setReportType("员工日报");
        drz.setDepartmentType("金融专员");
        drz.setFabulousPhone(mobile);
        int count = dailyReportMapper.insertDailyReportZan(drz);
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(count);
        return baseResult;
    }


    @Override
    public BaseResult selectDianZanList(HttpServletRequest request, DailyReportZan vo) {
        BaseResult baseResult = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        java.util.Date createTime = new java.util.Date();
        DailyReportZanResponse daily1 = new DailyReportZanResponse();
        daily1.setType("销售员");
        daily1.setTitle("卖车");
        daily1.setZanList(dailyReportMapper.selectZanListByDepartmentType(acount.getAccount(), "Product", vo.getCreateTime() == null ? createTime : vo.getCreateTime()));
        DailyReportZanResponse daily2 = new DailyReportZanResponse();
        daily2.setType("接待员");
        daily2.setTitle("接待");
        daily2.setZanList(dailyReportMapper.selectZanListByDepartmentType(acount.getAccount(), "Customer", vo.getCreateTime() == null ? createTime : vo.getCreateTime()));
        DailyReportZanResponse daily3 = new DailyReportZanResponse();
        daily3.setType("收车员");
        daily3.setTitle("收车");
        daily3.setZanList(dailyReportMapper.selectZanListByDepartmentType(acount.getAccount(), "Acquisition", vo.getCreateTime() == null ? createTime : vo.getCreateTime()));
        DailyReportZanResponse daily4 = new DailyReportZanResponse();
        daily4.setType("整备员");
        daily4.setTitle("整备");
        daily4.setZanList(dailyReportMapper.selectZanListByDepartmentType(acount.getAccount(), "TaskZB", vo.getCreateTime() == null ? createTime : vo.getCreateTime()));
        DailyReportZanResponse daily5 = new DailyReportZanResponse();
        daily5.setType("员工");
        daily5.setTitle("日报");
        daily5.setZanList(dailyReportMapper.selectZanListByDepartmentType(acount.getAccount(), "User", vo.getCreateTime() == null ? createTime : vo.getCreateTime()));
        List<Object> list = new ArrayList<>();
        list.add(daily1);
        list.add(daily2);
        list.add(daily3);
        list.add(daily4);
        list.add(daily5);
        baseResult.setData(list);
        return baseResult;
    }

    @Override
    public BaseResult selectOtherDailyReportCount(HttpServletRequest request, String searchDay) {
        BaseResult baseResult = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        Integer accountId = acount.getId();
        List<SubUserListResponse> subUserListResponseList = subUserService.getSubUserList(accountId);
        int totalSum = subUserListResponseList.size() + 1;// 子账号数量+总账号数(1)
        // 车辆分享统计
        String carSharedNum = dailyReportMapper.selectCarSharedNum(accountId, searchDay);
        String carUnshareNum = String.valueOf(totalSum - Integer.valueOf(carSharedNum));
        OtherDailyReport scCarShare = new OtherDailyReport();
        scCarShare.setImg("other_car");
        scCarShare.setTitle("车辆分享统计");
        scCarShare.setContent("未分享" + carUnshareNum + "人   已分享" + carSharedNum + "人");
        scCarShare.setType("other_car");
        // 软文分享统计
        String wordSharedNum = dailyReportMapper.selectWordSharedNum(accountId, searchDay);
        String wordUnshareNum = String.valueOf(totalSum - Integer.valueOf(wordSharedNum));
        OtherDailyReport scWordShare = new OtherDailyReport();
        scWordShare.setImg("other_artical");
        scWordShare.setTitle("软文分享统计");
        scWordShare.setContent("未分享" + wordUnshareNum + "人   已分享" + wordSharedNum + "人");
        scWordShare.setType("other_artical");
        // 涂鸦分享统计
        String paintSharedNum = dailyReportMapper.selectPaintSharedNum(accountId, searchDay);
        String paintUnshareNum = String.valueOf(totalSum - Integer.valueOf(paintSharedNum));
        OtherDailyReport scPaintShare = new OtherDailyReport();
        scPaintShare.setImg("other_scrawl");
        scPaintShare.setTitle("涂鸦分享统计");
        scPaintShare.setContent("未分享" + paintUnshareNum + "人   已分享" + paintSharedNum + "人");
        scPaintShare.setType("other_scrawl");
        // 喜报分享统计
        String newGleefulNum = dailyReportMapper.selectNewGleefulNum(accountId, searchDay);// 今日新增喜报数量
        OtherDailyReport scnewGleeful = new OtherDailyReport();
        scnewGleeful.setImg("other_news");
        scnewGleeful.setTitle("喜报分享统计");
        scnewGleeful.setContent("今日新增喜报" + newGleefulNum + "条");
        scnewGleeful.setType("other_news");
        // 日检管理
        String checkedNum = dailyReportMapper.selectCheckedCarNum(accountId, searchDay);
        String allNum = dailyReportMapper.selectAllCarNum(accountId, searchDay);
        String uncheckNum = String.valueOf(Integer.valueOf(allNum) - Integer.valueOf(checkedNum));
        OtherDailyReport scdailyCheck = new OtherDailyReport();
        scdailyCheck.setImg("other_check");
        scdailyCheck.setTitle("日检管理");
        scdailyCheck.setContent("今日未检" + uncheckNum + "辆   今日已检" + checkedNum + "辆");
        scdailyCheck.setType("other_check");

        List<Object> list = new ArrayList<>();
        list.add(scdailyCheck);
        list.add(scCarShare);
        list.add(scnewGleeful);
        list.add(scWordShare);
        list.add(scPaintShare);
        baseResult.setData(list);

        return baseResult;
    }

    @Override
    public BaseResult insertShare(HttpServletRequest request, SharedRecord vo) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        SharedRecord sharedRecord = new SharedRecord();
        sharedRecord.setSharer(vo.getSharer());
        sharedRecord.setAccountId(acount.getId().toString());
        sharedRecord.setShareTo("朋友圈");
        sharedRecord.setShareType(vo.getShareType());
        sharedRecord.setShareId(StringUtil.isEmpty(vo.getShareId()) ? "0" : vo.getShareId());
        sharedRecord.setCreateTime(new java.util.Date());
        sharedRecord.setUpdateTime(new java.util.Date());
        sharedRecord.setEnable(1);
        int status = sharedRecordMapper.insertSelective(sharedRecord);
        if (status > 0) {
            return BaseResult.success();
        } else {
            return BaseResult.fail("0001", "分享信息添加失败");
        }
    }

    /**
     * 喜报统计
     *
     * @param request
     * @param vo
     * @return
     */
    @Override
    public BaseResult selectOtherReport(HttpServletRequest request, OtherReport vo) {
        BaseResult baseResult = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        String checkDate = "";
        if (vo.getSearchDate() != null) {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            checkDate = sdf.format(vo.getSearchDate());
        }
        List<Object> resultList = new ArrayList<Object>();
        if ("other_news".equals(vo.getType())) {
            //获取喜报列表
            List<GleefulReportResponse> list2 = gleefulReportService.getGleefulReportList(acount.getId().toString(), acount.getSubPhone(), vo.getSearchDate(), vo.getSearchDate());
            //获得分享情况
            for (GleefulReportResponse lists : list2) {
                OtherReport otherReport = new OtherReport();
                otherReport.setPeopleYNum(gleefulReportService.getGleefulSharedRecordList(acount.getId().toString(), lists.getId().toString()).get("sharedCount"));
                otherReport.setPeopleNNum(gleefulReportService.getGleefulSharedRecordList(acount.getId().toString(), lists.getId().toString()).get("unSharedCount"));
                otherReport.setGleefulContent(lists.getTitle());
                otherReport.setShareId(lists.getId().toString());
                otherReport.setShareDate(lists.getCreateTime());
                otherReport.setPicList(lists.getPicList());
                resultList.add(otherReport);
            }
            baseResult.setData(resultList);
        } else if ("other_check".equals(vo.getType())) {
            Map<String, Object> resultMap = new HashMap<>();
            HashMap<String, Integer> count = new HashMap<>();
            resultMap.put("uncheck", dailyCheckService.getDispatcherList(acount.getId().toString(), acount.getSubPhone(), checkDate, 0, count));
            resultMap.put("checked", dailyCheckService.getDispatcherList(acount.getId().toString(), acount.getSubPhone(), checkDate, 1, count));
            resultMap.put("count", count);
            baseResult.setData(resultMap);
        } else if ("other_daily_check".equals(vo.getType())) {
            //2.4.8启用新日检
            Map<String, Object> resultMap = new HashMap<>();
            resultMap = dailyCheckService.getCheckedList(acount.getId().toString(), acount.getSubPhone(), checkDate);
            List<DailyCheckCheckerResult> dailyCheckCheckerResults = dailyCheckService.getUnCheckList(acount.getId().toString(), checkDate);
            resultMap.put("uncheckCount", dailyCheckCheckerResults.size());
            resultMap.put("uncheckList", dailyCheckCheckerResults);
            baseResult.setData(resultMap);
        } else {
            List<OtherShareContent> list = dailyReportMapper.selectPaihangList(acount.getId(), acount.getAccount(), acount.getMerchantName(), vo.getSearchDate(), vo.getType());
            resultList.add(list);
            baseResult.setData(resultList);
        }


        return baseResult;
    }

    @Override
    public List<String> selectQx(String childAccount) {

        return dailyReportMapper.selectQx(childAccount);
    }


}
