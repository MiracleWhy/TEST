package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokAccountPower;
import com.uton.carsokApi.dao.CarsokAccountPowerMapper;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.ChildInfoService;
import com.uton.carsokApi.service.ICarsokAccountPowerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangdi
 * @since 2017-11-09
 */
@Service
public class CarsokAccountPowerServiceImpl extends ServiceImpl<CarsokAccountPowerMapper, CarsokAccountPower> implements ICarsokAccountPowerService {

    @Autowired
    private ChildInfoService childInfoService;


    @Override
    public List<CarsokAccountPower> selectByChildID(Integer id) {
        return new CarsokAccountPower().selectList(new EntityWrapper().eq("child_id",id));
    }

    @Override
    public int getQueryVehicleCarSourcePermissions(Acount acount,String limitEnmu) {
        if (StringUtil.isEmpty(acount.getSubPhone())) {
            return 1;
        }
        ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
        List<CarsokAccountPower> list=new CarsokAccountPower().selectList(new EntityWrapper().eq("child_id",childAccount.getId()));
        if(list!=null){
            Iterator<CarsokAccountPower> it = list.iterator();
            while(it.hasNext()) {
                CarsokAccountPower PaNo = it.next();
                if(PaNo.getPowerName().equals(limitEnmu)){
                    return 1;
                }
            }
        }
        return 0;
    }
}
