package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.dao.CarForgSaleMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.model.CarForgSale;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.CarFrogSaleService;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.util.CalculationUtil;
import com.uton.carsokApi.util.DateParamUtil;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Administrator on 2017/11/8/008.
 */
@Service
public class CarFrogSaleServiceImp implements CarFrogSaleService {

    @Autowired
    CarForgSaleMapper carForgSaleMapper;

    @Autowired
    ChildAccountMapper childAccountMapper;


    @Override
    public CarForgSale SalesForg(ReportDateRequest vo, List<CarsokChildAccount> childAccounts, Acount acount) {
        int accountId = acount.getId();
        int rowNum = 0;//销售数量总计
        int rowSum = 0;//销售总计总计
        //分析时间参数
        Map<String, String> map = DateParamUtil.dateParamHandler(vo.getDate(), vo.getType());
        String startDate = map.get("startDate");
        String endDate = map.get("endDate");
        Map<String, String> mapBefore = DateParamUtil.lastDateParamHandler(startDate, endDate, vo.getType());
        //行数据
        List<CarForgSaleOne> rows = new ArrayList<>();
        for (CarsokChildAccount item : childAccounts) {
            CarForgSaleOne po = new CarForgSaleOne();
            //销售顾问
            String consultant = item.getChildAccountName();
            //单人销售数量
            int saledCarNumber = carForgSaleMapper.countSaledCar(accountId, item.getId(), startDate, endDate);
            //单人销售总计
            int saledCarTotal = carForgSaleMapper.countSaledCar(accountId, item.getId(), null, null);
            //环比
            int before = carForgSaleMapper.countSaledCar(accountId, item.getId(), mapBefore.get("startDate"), mapBefore.get("endDate"));
            double chain = CalculationUtil.calculateChainMethod(saledCarNumber, before);

            //箭头
            String arrow = CalculationUtil.judgeArrowMethod(saledCarNumber, before);

            po.setConsultant(consultant);
            po.setSaleOneCount(saledCarNumber);
            po.setSaleOneTotal(saledCarTotal);
            po.setChain(chain);
            po.setUpOrDown(arrow);

            rows.add(po);
            rowNum = rowNum + po.getSaleOneCount();
            rowSum = rowSum + po.getSaleOneTotal();
        }
        //总计
        //环比
        Integer saleBeforeSum = carForgSaleMapper.countSaledCar(accountId, null, mapBefore.get("startDate"), mapBefore.get("endDate"));
        double chainTotal = CalculationUtil.calculateChainMethod(rowNum, saleBeforeSum);
        //箭头
        String arrow = CalculationUtil.judgeArrowMethod(rowNum, saleBeforeSum);

        CarForgSale dto = new CarForgSale();
        dto.setSaleSum(rowNum);
        dto.setSaleTotalSum(rowSum);
        dto.setChainSum(chainTotal);
        dto.setUpOrDownSum(arrow);

        Collections.sort(rows, new CarForgSaleOne());
        dto.setRows(rows);
        toSetRanking(rows);
        return dto;
    }

    private List<CarForgSaleOne> toSetRanking(List<CarForgSaleOne> rows) {
        int max = rows.size();
        rows.get(0).setRanking(1);
        for (int i = 0; i < max - 1; i++) {
            if (rows.get(i).getSaleOneCount().compareTo(rows.get(i + 1).getSaleOneCount()) == 0) {
                rows.get(i + 1).setRanking(rows.get(i).getRanking());
            } else {
                rows.get(i + 1).setRanking(i + 2);
            }
        }
        return rows;
    }
}
