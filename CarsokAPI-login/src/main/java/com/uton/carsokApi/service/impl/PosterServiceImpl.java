package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.uton.carsokApi.constants.enums.CarDel;
import com.uton.carsokApi.constants.enums.OnSelfStatus;
import com.uton.carsokApi.constants.enums.SaleStatus;
import com.uton.carsokApi.controller.response.DailyCheckerResponse;
import com.uton.carsokApi.controller.response.SubUserListResponse;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.PosterService;
import com.uton.carsokApi.service.SubUserService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PosterServiceImpl implements PosterService {

    @Autowired
    PosterMapper posterMapper;
    @Autowired
    AcountMapper acountMapper;
    @Autowired
    CarsokCarNewsMapper carsokCarNewsMapper;
    @Autowired
    GleefulReportMapper gleefulReportMapper;
    @Value("${store.url.prefix}")
    private String storeUrlPrefix;

    public Page<Poster> selectAllByPage(String sort) {
        return posterMapper.selectAllByPage();
    }

    public Map<String, Object> getUserInfoByAccount(int accountId) {
        Acount acountQuery = new Acount();
        acountQuery.setId(accountId);
        Acount acountRes = acountMapper.selectByModel(acountQuery);
        Map<String,Object> resultMap = new HashedMap();

        resultMap.put("merchantDescr",acountRes.getMerchantDescr());
        resultMap.put("merchantName",acountRes.getMerchantName());
        resultMap.put("merchantUrl",storeUrlPrefix+acountRes.getAccountCode()+".html");

        return resultMap;
    }

    @Override
    public List getPosterByDate() {
        return posterMapper.selectPostByDate();
    }

    @Override
    public CarsokCarNews getNewCarNewsLimit(Map param) {
        return carsokCarNewsMapper.selectNewCarNewsLimit(param);
    }

    @Override
    public GleefulReport getNewGleefulReport(Integer accountId) {
        Map param = new HashMap();
        param.put("accountId",accountId);
        param.put("start",Integer.valueOf(0));

        param.put("end",Integer.valueOf(1));
        return gleefulReportMapper.selectFirstReportByAccountId(param);
    }

}
