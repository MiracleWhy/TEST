package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.CarFindListRequest;
import com.uton.carsokApi.controller.request.FindCarMsgRequest;
import com.uton.carsokApi.controller.request.PublishFindCarRequest;
import com.uton.carsokApi.controller.response.CarFindUp;
import com.uton.carsokApi.controller.response.CarsokQuoteResponse;
import com.uton.carsokApi.controller.response.FindCarMsgResponse;
import com.uton.carsokApi.dao.CarsokFindCarMapper;
import com.uton.carsokApi.model.CarsokFindCar;
import com.uton.carsokApi.service.ICarsokFindCarService;
import com.uton.carsokApi.util.DozerMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Service
public class CarsokFindCarServiceImpl extends ServiceImpl<CarsokFindCarMapper, CarsokFindCar> implements ICarsokFindCarService {

    @Autowired
    private CarsokFindCarMapper carsokFindCarMapper;

    @Override
    public Page<CarFindUp> getCarFindList(CarFindListRequest carFindListRequest) {

        PageHelper.startPage(carFindListRequest.getPageNum(), carFindListRequest.getPageSize());
        Page<CarFindUp> carFindUps = carsokFindCarMapper.getFindCarList(carFindListRequest);
        return carFindUps;
    }

    @Override
    public boolean publishFindCar(PublishFindCarRequest p) {
        CarsokFindCar carsokFindCar = new CarsokFindCar();
        DozerMapperUtil.getInstance().map(p, carsokFindCar);
        carsokFindCar.setCreateTime(new Date());
        carsokFindCar.setUpdateTime(new Date());
        carsokFindCar.setEnable(0);
        carsokFindCar.setIntentionalNo("");
        return carsokFindCar.insertOrUpdate();
    }

    @Override
    public FindCarMsgResponse selectMsg(FindCarMsgRequest f) {
        FindCarMsgResponse response = carsokFindCarMapper.selectMsg(f);
        if (response != null && response.getQuoteList().size()>0) {
            for (CarsokQuoteResponse carsokQuoteResponse : response.getQuoteList()) {
                String name = carsokQuoteResponse.getName().substring(0,1);
                name +="**";
                carsokQuoteResponse.setName(name);
            }
        }
        return response;
    }
}
