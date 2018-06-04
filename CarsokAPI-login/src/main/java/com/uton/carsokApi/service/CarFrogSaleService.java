package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarForgSale;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;

import java.util.List;


/**
 * Created by Administrator on 2017/11/8/008.
 */
public interface CarFrogSaleService {
    CarForgSale SalesForg(ReportDateRequest vo, List<CarsokChildAccount> childAccounts, Acount acount);
}
