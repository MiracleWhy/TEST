package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CarsokAdviceRequest;

public interface AdviceService {

    BaseResult insertNewAdvice(CarsokAdviceRequest carsokAdviceRequest);
}
