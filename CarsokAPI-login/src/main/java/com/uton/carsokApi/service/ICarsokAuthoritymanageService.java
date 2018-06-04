package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.CarsokAuthoritymanage;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
public interface ICarsokAuthoritymanageService extends IService<CarsokAuthoritymanage> {
	BaseResult insertAuthorityManage(CarsokAuthoritymanage carsokAuthoritymanage);
}
