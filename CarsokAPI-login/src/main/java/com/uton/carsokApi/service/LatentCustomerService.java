package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.LatentCustomerRequest;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface LatentCustomerService {
    BaseResult selectLatentList(String accountMobile, LatentCustomerRequest latentCustomerRequest);
}
