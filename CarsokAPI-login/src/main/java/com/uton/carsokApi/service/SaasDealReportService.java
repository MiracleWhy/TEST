package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.DealReportResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;

import java.util.List;

/**
 * Created by SEELE on 2017/12/12.
 */
public interface SaasDealReportService {
    DealReportResponse getDealReportPage(Acount acount, List<CarsokChildAccount> childAccounts, ReportDateRequest dateParam);
}
