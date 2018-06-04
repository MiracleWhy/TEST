package com.uton.carsokApi.service;


import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.CarsokPmsForfigure;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2017-10-10
 */
public interface ICarsokPmsForfigureService {
    List<CarsokPmsForfigure> findPage(int page, int size);
    String findPicture(int id);
}
