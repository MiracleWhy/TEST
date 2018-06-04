package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.UpdateCustCarMsgRequest;
import com.uton.carsokApi.dao.CarsokCustomerTenureCarMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.ICarsokCustomerTenureCarService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangyj
 * @since 2017-11-09
 */
@Service
@Transactional
public class CarsokCustomerTenureCarServiceImpl extends ServiceImpl<CarsokCustomerTenureCarMapper, CarsokCustomerTenureCar> implements ICarsokCustomerTenureCarService {

    @Autowired
    ChildAccountMapper childAccountMapper;

   /* @Override
    public BaseResult updateCustCarMsg(Acount acount, UpdateCustCarMsgRequest u) {


        for (Integer productId : u.getProductId()) {
            CarsokProduct carsokp = new CarsokProduct();
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                carsokp.setSaledPeople(acount.getSubPhone());
            } else {
                carsokp.setSaledPeople(acount.getAccount());
            }
            carsokp.setId(productId);
            carsokp.updateById();

            CarsokProduct carsokProduct = new CarsokProduct().selectOne(new EntityWrapper()
                    .isNotNull("car_id")
                    .eq("id", productId));
            CarsokCustomer carsokCustomer = new CarsokCustomer().selectById(u.getCustId());
            if (carsokCustomer == null || carsokProduct == null) {
                return BaseResult.fail(ErrorCode.NullPointerExceptionRetCode, ErrorCode.NullPointerExceptionRetInfo);
            }
            CarsokCustomerTenureCar carsokCustomerTenureCar = new CarsokCustomerTenureCar();
            carsokCustomerTenureCar.setAccountId(acount.getId());
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childAccountMapper.selectByChildMobile(acount.getSubPhone());
                carsokCustomerTenureCar.setChildId(childAccount.getId());
            }
            carsokCustomerTenureCar.setCreateTime(new Date());
            carsokCustomerTenureCar.setSaleTime(new Date());
            carsokCustomerTenureCar.setTenureCarname(carsokProduct.getProductName());
            carsokCustomerTenureCar.setTenureVin(carsokProduct.getVin());
            //待定
          *//*  if (carsokProduct.getSaledType() != null) {
                carsokCustomerTenureCar.setTenureCartype(carsokProduct.getSaledType()==1 ?"全款":"贷款");
            }*//*

            carsokCustomerTenureCar.setTenureCarprice(carsokProduct.getSaledPrice());
            carsokCustomerTenureCar.setSalePeople(carsokProduct.getSaledPeople());
            carsokCustomerTenureCar.setProductId(productId);
            carsokCustomerTenureCar.setCustomerId(u.getCustId());
            carsokCustomerTenureCar.setIsDrivingTest(carsokCustomer.getIsDrivingTest());
            if (carsokProduct.getBusinessIf() != null) {
                carsokCustomerTenureCar.setIsBussiness(carsokProduct.getBusinessIf() == 1 ? "是" : "否");
            }
            carsokCustomerTenureCar.insert();
            //添加完后将客户级别改为已成交
            CarsokCustomer cc = new CarsokCustomer();
            cc.setId(u.getCustId());
            cc.setLevel("N 已成交");
            cc.updateById();
        }
        return BaseResult.success();
    }*/
}
