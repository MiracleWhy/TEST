package com.uton.carsokApi.service;

import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.OverdueForgModel;
import com.uton.carsokApi.model.SalesForg;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/12/20/020.
 */
public interface OverdueForgService {
    OverdueForgModel overdue(Acount acount);
}
