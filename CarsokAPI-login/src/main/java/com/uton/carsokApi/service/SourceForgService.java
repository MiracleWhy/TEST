package com.uton.carsokApi.service;

import com.uton.carsokApi.model.SalesForg;
import com.uton.carsokApi.model.SourceForgSum;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/11/13/013.
 */

public interface SourceForgService {
    SourceForgSum salesForg(HttpServletRequest request,SalesForg salesForg);
}
