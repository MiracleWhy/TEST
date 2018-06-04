package com.uton.carsokApi.service;

import com.uton.carsokApi.model.PurposeReturnSum;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/11/10/010.
 */
@Service
public interface PurposeForgService {
    PurposeReturnSum selectCustomerCount(HttpServletRequest request);
}
