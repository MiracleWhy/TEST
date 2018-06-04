package com.uton.carsokApi.service.impl;


import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.BusinesMapper;
import com.uton.carsokApi.dao.CarsokPmsForfigureMapper;
import com.uton.carsokApi.model.CarsokPmsForfigure;
import com.uton.carsokApi.service.ICarsokPmsForfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2017-10-10
 */
@Service
public class CarsokPmsForfigureServiceImpl implements ICarsokPmsForfigureService {
    @Autowired
    CarsokPmsForfigureMapper carsokPmsForfigureMapper;
    @Autowired
    BusinesMapper businesMapper;
    @Override
    public List<CarsokPmsForfigure> findPage( int page,int size) {
        int firstpge=(page-1)*size;
      List<CarsokPmsForfigure> list=  businesMapper.findPageCondition(firstpge,size);
        return list;
    }

    @Override
    public String findPicture(int id) {
        String pictureUrl=businesMapper.findPicture(id);
        return pictureUrl;
    }
}
