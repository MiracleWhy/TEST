package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.FollowResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SEELE on 2017/11/11.
 */
@Service
public interface FollowService {
    FollowResponse getFollowPage(Acount acount, List<CarsokChildAccount> childAccounts, ReportDateRequest vo);
}
