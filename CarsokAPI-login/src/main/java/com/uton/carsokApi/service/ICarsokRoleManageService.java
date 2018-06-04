package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.CarsokRoleManage;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangyj
 * @since 2017-11-09
 */
public interface ICarsokRoleManageService extends IService<CarsokRoleManage> {
	BaseResult insertCarsokRoleManage(CarsokRoleManage carsokRoleManage);
}
