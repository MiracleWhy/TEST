package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.CarsokRoleManageMapper;
import com.uton.carsokApi.model.CarsokRoleManage;
import com.uton.carsokApi.service.ICarsokRoleManageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author wangyj
 * @since 2017-11-09
 */
@Service
public class CarsokRoleManageServiceImpl extends ServiceImpl<CarsokRoleManageMapper, CarsokRoleManage> implements ICarsokRoleManageService {

    private final static Logger logger = Logger.getLogger(CarsokRoleManageServiceImpl.class);

    @Autowired
    CarsokRoleManageMapper carsokRoleManageMapper;

    @Override
    public BaseResult insertCarsokRoleManage(CarsokRoleManage carsokRoleManage) {
        try {
            BaseResult baseResult = BaseResult.success();
            baseResult.setData(carsokRoleManageMapper.insert(carsokRoleManage));
            return baseResult;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
            return BaseResult.fail("0001","系统异常");
        }
    }
}
