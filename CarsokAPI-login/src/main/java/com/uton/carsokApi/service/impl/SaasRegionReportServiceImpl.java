package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.RegionReportResponse;
import com.uton.carsokApi.controller.response.RegionReportRowResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.CustomerMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.KeyValuePair;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.service.SaasRegionReportService;
import com.uton.carsokApi.util.DateParamUtil;
import com.uton.carsokApi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by SEELE on 2017/12/14.
 */
@Service
public class SaasRegionReportServiceImpl implements SaasRegionReportService {

    @Resource
    ChildAccountMapper childAccountMapper;

    @Autowired
    CustomerMapper customerMapper;


    @Override
    public RegionReportResponse getRegionPage(Acount acount, List<CarsokChildAccount> childAccounts, ReportDateRequest vo) {
        RegionReportResponse regionReportResponse=new RegionReportResponse();
        //时间
        Map<String, String> map = DateParamUtil.dateParamHandler(vo.getDate(),vo.getType());
        String startDate=map.get("startDate");
        String endDate=map.get("endDate");
        Map<String, String> mapBefore=DateParamUtil.lastDateParamHandler(startDate,endDate,vo.getType());
        String startDateBefore=mapBefore.get("startDate");
        String endDateBefore=mapBefore.get("endDate");
        //总填写人数
        Integer customerSum=customerMapper.queryDailyRegionCountByDate(acount.getId(),childAccounts,startDate,endDate);
        Integer customerSumBefore=customerMapper.queryDailyRegionCountByDate(acount.getId(),childAccounts,startDateBefore,endDateBefore);
        //区域 人数
        if(customerSum!=0){
            List<RegionReportRowResponse> rows=new ArrayList<>();
            List<KeyValuePair> KeyValuePairs=customerMapper.queryRegionOfSomeCustomers(acount.getId(),childAccounts,startDate,endDate);
            for(KeyValuePair item:KeyValuePairs){
                RegionReportRowResponse row=new RegionReportRowResponse();
                String region=item.getKeyStr();
                Integer customerNum=Integer.valueOf(item.getVal());
                row.setRegion(region);
                row.setCustomerNum(customerNum);
                //环比
                //上一维度 此区县人数
                Integer before=customerMapper.countCustomerNumOfRegion(acount.getId(),childAccounts,startDate,endDate,item.getKeyStr(),vo.getType());
                Double chain=getChain(row.getCustomerNum(),before);
                row.setChain(chain);
                //箭头
                String arrow;
                if(customerNum>before){
                    arrow="up";
                }else {
                    arrow="down";
                }
                row.setUpOrDown(arrow);
                //比例
                Double rate=new BigDecimal(customerNum).divide(new BigDecimal(customerSum),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
                row.setRate(rate);
                rows.add(row);
            }
            regionReportResponse.setRows(rows);
            regionReportResponse.setCustomerNumSum(customerSum);
            regionReportResponse.setRateSum(new Double(100));
            regionReportResponse.setChainSum(getChain(customerSum,customerSumBefore));
            if(customerSum>customerSumBefore){
                regionReportResponse.setUpOrDownSum("up");
            }else {
                regionReportResponse.setUpOrDownSum("down");
            }
        }
        return regionReportResponse;
    }

    private double getChain(double choose,double before){
        Double chain;
        if(before==0){
            if(choose!=0){
                chain=new Double(100);
            }else {
                chain=new Double(0);
            }
        }else {//环比=(当月-前月)÷前月
            chain=new BigDecimal(choose-before)
                    .divide(new BigDecimal(before),4,BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100))
                    .abs().doubleValue();
        }
        return  chain;
    }
}
