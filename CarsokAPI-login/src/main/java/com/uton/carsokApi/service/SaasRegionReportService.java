package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.RegionReportResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;

import java.util.List;

/**
 * Created by SEELE on 2017/12/14.
 */
public interface SaasRegionReportService {

    RegionReportResponse getRegionPage(Acount acount, List<CarsokChildAccount> childAccounts, ReportDateRequest vo);
}
