package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.PotCusResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by SEELE on 2017/11/9.
 */
@Service
public interface SaasPotCusReportService {
    PotCusResponse getPotCusPage(Acount acount, List<CarsokChildAccount> accounts, ReportDateRequest dateParam);
}
