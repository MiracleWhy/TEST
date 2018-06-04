package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.service.ProApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by SEELE on 2017/9/19.
 */
@Service
public class ProApplyServiceImpl implements ProApplyService {

    @Autowired
    private AcountMapper acountMapper;

    @Override
    public Integer updateAccountForApplyPro(Acount acount) {
        acount.setApplyStatus(1);//设置申请状态：申请中
        acount.setUpdateTime(new Date());
        return acountMapper.updateAccountForApplyPro(acount);
    }

    @Override
    public Integer queryApplyStatus(Acount acount) {
        Integer id=acount.getId();
        return acountMapper.queryApplyStatus(id);
    }
}
