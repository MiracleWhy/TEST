package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.AcquisitionCarMapper;
import com.uton.carsokApi.dao.EmployeesMapper;
import com.uton.carsokApi.model.AcquisitionCar;
import com.uton.carsokApi.model.AcquisitionConsult;
import com.uton.carsokApi.model.Employees;
import com.uton.carsokApi.model.Pagebean;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
@Service
public class EmployeesService {
    @Autowired
    EmployeesMapper employeesMapper;
    public List<Employees> selectAll()
    {
        return employeesMapper.selectAll();
    }
}
