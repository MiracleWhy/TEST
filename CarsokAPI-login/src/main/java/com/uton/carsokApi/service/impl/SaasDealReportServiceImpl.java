package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.DealReportListResponse;
import com.uton.carsokApi.controller.response.DealReportResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.CustomerMapper;
import com.uton.carsokApi.dao.ProductMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.service.SaasDealReportService;
import com.uton.carsokApi.util.CalculationUtil;
import com.uton.carsokApi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SEELE on 2017/12/12.
 */
@Service
public class SaasDealReportServiceImpl implements SaasDealReportService {

    @Autowired
    ChildAccountMapper childAccountMapper;
    

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public DealReportResponse getDealReportPage(Acount acount, List<CarsokChildAccount> childAccounts, ReportDateRequest dateParam) {
        int accountId=acount.getId();
        List<DealReportListResponse> rows=new ArrayList<>();
        int potTotal=0;//潜客数量合计
        int dealTotal=0;//成交数合计
        int dealTotalBefore=0;//上月成交数合计
        for(CarsokChildAccount child:childAccounts){
            List<CarsokChildAccount> oneChild=new ArrayList<>();
            oneChild.add(child);
            int childId=child.getId();
            String date=dateParam.getDate();
            DealReportListResponse row=new DealReportListResponse();

            //销售
            row.setName(child.getChildAccountName());

            //潜客数量（包括"战败"+"已成交"）
            int potCusCount =customerMapper.countPotCusTotalOfOne(accountId,childId,DateUtil.StringToDate(date));
            row.setPotCusCount(potCusCount);

            //成交数
            int dealCount=productMapper.querySaleCountByDay(accountId,oneChild, DateUtil.StringToDate(date),"month");
            row.setDealCount(dealCount);

            //环比=(当月-前月)÷前月
            int dealCountBefore=productMapper.querySaleCountByDay(accountId,oneChild, DateUtil.StringToDate(DateUtil.addMonth(date,-1)),"month");
            double chain=CalculationUtil.calculateChainMethod(dealCount,dealCountBefore);
            row.setChain(chain);

            //箭头
            String upOrDown=CalculationUtil.judgeArrowMethod(dealCount,dealCountBefore);
            row.setUpOrDown(upOrDown);

            //成交率=当月成交数量÷潜客数量
            Double dealRate=CalculationUtil.getDealRate(dealCount,potCusCount);
            row.setDealRate(dealRate);
            rows.add(row);

            potTotal=potTotal+potCusCount;
            dealTotal=dealTotal+dealCount;
            dealTotalBefore=dealTotalBefore+dealCountBefore;
        }
        //排名
        Collections.sort(rows,new DealReportListResponse());
        toSetRanking(rows);

        //合计
        double chainSum=CalculationUtil.calculateChainMethod(dealTotal,dealTotalBefore);
        String arrow=CalculationUtil.judgeArrowMethod(dealTotal,dealTotalBefore);
        Double dealRateSum=CalculationUtil.getDealRate(dealTotal,potTotal);

        DealReportResponse dealReportResponse=new DealReportResponse();
        dealReportResponse.setRows(rows);
        dealReportResponse.setUpOrDownSum(arrow);
        dealReportResponse.setChainSum(chainSum);
        dealReportResponse.setDealRateSum(dealRateSum);
        dealReportResponse.setDealCountSum(dealTotal);
        dealReportResponse.setPotCusCountSum(potTotal);
        return  dealReportResponse;
    }


    private List<DealReportListResponse> toSetRanking(List<DealReportListResponse> vos) {
        int max=vos.size();
        vos.get(0).setRanking(1);
        for(int i=0;i<max-1;i++){
            if(vos.get(i).getDealCount().compareTo(vos.get(i+1).getDealCount())==0){
                vos.get(i+1).setRanking(vos.get(i).getRanking());
            }else {
                vos.get(i+1).setRanking(i+2);
            }
        }
        return vos;
    }

}
