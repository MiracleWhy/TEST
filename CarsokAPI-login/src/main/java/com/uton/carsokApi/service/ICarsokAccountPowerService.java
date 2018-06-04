package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokAccountPower;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangdi
 * @since 2017-11-09
 */
public interface ICarsokAccountPowerService extends IService<CarsokAccountPower> {
    /**
     * @author zhangdi
     * @date 2017/11/9 17:38
     * @Description: 通过子账号id查询权限
     */

    List<CarsokAccountPower> selectByChildID(Integer id);

    /**
     * @author zhangdi
     * @date 2018/1/19 10:22
     * @Description: 权限查询
     */
    int getQueryVehicleCarSourcePermissions(Acount acount, String limitEumn);
}
