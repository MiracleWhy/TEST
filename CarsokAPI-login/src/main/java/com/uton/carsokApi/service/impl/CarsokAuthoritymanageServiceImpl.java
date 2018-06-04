package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.CarsokAuthoritymanageMapper;
import com.uton.carsokApi.model.CarsokAuthoritymanage;
import com.uton.carsokApi.service.ICarsokAuthoritymanageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/11/12.
 */
@Service
public class CarsokAuthoritymanageServiceImpl extends ServiceImpl<CarsokAuthoritymanageMapper, CarsokAuthoritymanage> implements ICarsokAuthoritymanageService {

    private final static Logger logger = Logger.getLogger(CarsokRoleManageServiceImpl.class);

    @Autowired
    CarsokAuthoritymanageMapper carsokAuthoritymanageMapper;

    @Override
    public BaseResult insertAuthorityManage(CarsokAuthoritymanage carsokAuthoritymanage) {
        try {
            BaseResult baseResult = BaseResult.success();
            baseResult.setData(carsokAuthoritymanageMapper.insert(carsokAuthoritymanage));
            return baseResult;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
            return BaseResult.fail("0001","系统异常");
        }
    }
}
