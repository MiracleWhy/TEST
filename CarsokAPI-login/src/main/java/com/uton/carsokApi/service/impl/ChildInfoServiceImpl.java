package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.response.ChildInfoResponse;
import com.uton.carsokApi.controller.response.InfoResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.CarsokCustomerTenureCarMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.ChildInfoMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ChildInfoService;
import com.uton.carsokApi.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */
@Service
public class ChildInfoServiceImpl implements ChildInfoService {

    @Resource
    CacheService cacheService;

    @Autowired
    UploadService uploadService;

    @Autowired
    ChildAccountMapper childAccountMapper;
    @Autowired
    AcountMapper acountMapper;
    @Autowired
    ChildInfoMapper childInfoMapper;
    @Autowired
    CarsokCustomerTenureCarMapper carsokCustomerTenureCarMapper;

    @Value("${store.url.prefix}")
    private String storeUrlPrefix;

    @Override
    public BaseResult getChildInfoMsg(HttpServletRequest request) {
        BaseResult baseResult = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        ChildAccount childAccount = new ChildAccount();
        childAccount.setChildAccountMobile(acount.getSubPhone());
        ChildAccount child = childAccountMapper.selectByModel(childAccount);
        if(child == null){
            return BaseResult.fail("-8", "还未登录");
        }
        ChildInfoResponse childInfo = childInfoMapper.selectChildInfo(child.getId());
        baseResult.setData(childInfo);
        return baseResult;
    }

    @Override
    public BaseResult uploadChildHead(HttpServletRequest request, String childHeadPic) {
        Acount acount = cacheService.getAcountInfoFromCache(request);
        ChildAccount childs = new ChildAccount();
        childs.setChildAccountMobile(acount.getSubPhone());
        ChildAccount child = childAccountMapper.selectByModel(childs);
        ChildAccount childAccount = new ChildAccount();
        childAccount.setChildHeadPic(childHeadPic);
        childAccount.setId(child.getId());
        int status = childAccountMapper.updateByPrimaryKeySelective(childAccount);
        if (status > 0) {
            return BaseResult.success();
        } else {
            return BaseResult.fail("0005", "上传头像失败");
        }
    }

    // 汽车管家首页 我的管家
    @Override
    public BaseResult getChildInfoMsgById(String mobile) {
        BaseResult baseResult = BaseResult.success();
        List<InfoResponse> infoResponse = childInfoMapper.selectInfoByCustPhone(mobile);
        baseResult.setData(infoResponse);
        return baseResult;
    }

    @Override
    public BaseResult getChildInfoMsgByCarId(String CarId) {
        InfoResponse infoResponse = childInfoMapper.selectInfoByCarId(CarId);
        String infoUrl = storeUrlPrefix + infoResponse.getAccountPhone() + ".html";
        infoResponse.setInfoUrl(infoUrl);
        return BaseResult.success(infoResponse);
    }

    @Override
    public List<ChildAndSale> AllMassage(String id) {

        String accountPhone = acountMapper.telephone(id);
        List<ChildAndSale> list = childAccountMapper.AllMassage(accountPhone);
        ChildAndSale accountInfo = childAccountMapper.AccountButler(accountPhone);
        list.add(accountInfo);
        java.util.Collections.reverse(list);
        return list;
    }

    @Override
    public ChildAndSales oneMessage(String id) {
        ChildAndSales childAccount = childAccountMapper.oneMassage(id);

        return childAccount;
    }

    @Override
    public BaseResult updateMessage(ChildAccount childAccount) {

        if (childAccount == null) {
            return BaseResult.fail("0001", "更新参数为空");
        }
        childAccount.setUpdateTime(new Date());
        int up = childAccountMapper.updateMessage(childAccount);
        if (up < 0) {
            BaseResult.fail("0002", "更新失败");
        }
        return BaseResult.success();
    }

    @Override
    public List<SelectSaleCar> selectSaleCar(String id) {

        List<SelectSaleCar> list = childAccountMapper.selectSaleCar(id);
        return list;
    }

    @Override
    public ChildAccount selectByChildAccount(String mobile) {
        return childAccountMapper.selectByChildMobile(mobile);
    }

    @Override
    public BaseResult getSellerInfo(String productId) {
        CarsokCustomerTenureCar carsokCustomerTenureCar = new CarsokCustomerTenureCar();
        carsokCustomerTenureCar.setProductId(Integer.parseInt(productId));
        CarsokCustomerTenureCar tenureCarRes = carsokCustomerTenureCarMapper.selectOne(carsokCustomerTenureCar);
        //如果ChildId为0的话，代表是主账号对其进行的操作。
//        if (res.getChildId() == 0) {
//            CarsokAcount carsokAcount = new CarsokAcount();
//            CarsokAcount accountRes = carsokAcount.selectOne(new EntityWrapper<CarsokAcount>().eq("account_code",result.getAccountPhone()));
//        }
//        //如果子账号不为0，则子账号对其进行的操作
//        else {
//            CarsokChildAccount childAccountRes = new CarsokChildAccount();
//            CarsokChildAccount result = childAccountRes.selectById(res.getChildId());
//            CarsokAcount carsokAcount = new CarsokAcount();
//            CarsokAcount accountRes = carsokAcount.selectOne(new EntityWrapper<CarsokAcount>().eq("account_code",result.getAccountPhone()));
//        }
        CarsokAcount carsokAcount = new CarsokAcount();
        List<CarsokAcount> accountResList = carsokAcount.selectList(new EntityWrapper<CarsokAcount>().eq("id", tenureCarRes.getAccountId()));
        if (accountResList.size()==1) {
            CarsokAcount accountRes = accountResList.get(0);
            JSONObject res = new JSONObject();
            res.put("merchantName", accountRes.getMerchantName());
            res.put("address", accountRes.getMerchantAddress());
            res.put("merchantDescription", accountRes.getMerchantDescr());
            return BaseResult.success(res.toJSONString());
        } else {
            return BaseResult.success(null);
        }

    }
}
