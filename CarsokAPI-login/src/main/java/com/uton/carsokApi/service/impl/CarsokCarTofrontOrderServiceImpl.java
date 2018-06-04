package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uton.carsokApi.controller.request.CarTofrontPrepayRequest;
import com.uton.carsokApi.dao.CarsokCarTofrontOrderMapper;
import com.uton.carsokApi.model.CarsokCarTofront;
import com.uton.carsokApi.model.CarsokCarTofrontOrder;
import com.uton.carsokApi.model.CarsokTofrontPlan;
import com.uton.carsokApi.pay.weixin.WeixinPay;
import com.uton.carsokApi.service.ICarsokCarTofrontOrderService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Service
public class CarsokCarTofrontOrderServiceImpl extends ServiceImpl<CarsokCarTofrontOrderMapper, CarsokCarTofrontOrder> implements ICarsokCarTofrontOrderService {

    @Override
    public SortedMap<String, String> prepay(CarTofrontPrepayRequest c) {
        WeixinPay wpay = new WeixinPay();
        SortedMap<String, String> map = new TreeMap<>();
        map.put("total_fee", c.getTotal_fee().toString());
        SortedMap<String, String> prepayMap = null;
        try {
            prepayMap = wpay.wxAppPrePay(map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        String prepayid = prepayMap.get("prepayid");
        if (!StringUtil.isEmpty(prepayid)) {
            CarsokCarTofrontOrder order = new CarsokCarTofrontOrder();
            order.setCreateTime(new Date());
            order.setEnable(0);
            order.setIsSuccess(1);
            order.setOrderNo(prepayid);
            order.setOrderPrice(c.getTotal_fee());
            order.setUpdateTime(new Date());
            order.setTofrontPlanId(c.getTofrontPlanId());
            order.setTofrontProductId(c.getTofrontProductId());
            order.setAccountId(c.getAccountId());
            order.setChildId(c.getChildId());
            if (!order.insert()) {
                prepayMap.put("code", "2");
                prepayMap.put("info", "订单生成失败");
                return prepayMap;
            }
            prepayMap.put("code", "0");
            prepayMap.put("info", "success");
            prepayMap.put("out_trade_no", order.getId().toString());
        } else {
            prepayMap.clear();
            prepayMap.put("code", "1");
            prepayMap.put("info", "获取prepayid失败");
        }
        return prepayMap;
    }

    @Override
    public boolean notify(Map map) {

        if (map != null && StringUtils.equals("SUCCESS", map.get("return_code").toString())) {

            CarsokCarTofrontOrder carsokCarTofrontOrder = new CarsokCarTofrontOrder().selectById(map.get("payOrderNo").toString());
            if (carsokCarTofrontOrder != null) {
                if (carsokCarTofrontOrder.getPayTime() == null) {
                    carsokCarTofrontOrder.setPayTime(new Date());
                    carsokCarTofrontOrder.setEnable(0);
                    carsokCarTofrontOrder.setIsSuccess(0);
                    carsokCarTofrontOrder.updateById();
                    //查询
                    CarsokCarTofront carsokCarTofront = new CarsokCarTofront().selectOne(new EntityWrapper()
                            .eq("tofront_product_id", carsokCarTofrontOrder.getTofrontProductId()));
                    //查询购买套餐详情
                    CarsokTofrontPlan plan = new CarsokTofrontPlan().selectById(carsokCarTofrontOrder.getTofrontPlanId());
                    if (carsokCarTofront == null) {
                        carsokCarTofront.setCreateTime(new Date());

                    }
                    carsokCarTofront.setUpdateTime(new Date());
                    carsokCarTofront.setCarOrderId(carsokCarTofrontOrder.getId());
                    carsokCarTofront.setEnable(0);
                    //随机生成0-10000随机数
                    carsokCarTofront.setNewOrder(new Random().nextInt(10000));
                    carsokCarTofront.setStartTime(new Date());
                    //计算是失效时间
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.DATE, plan.getPlanTime());
                    carsokCarTofront.setEndTime(calendar.getTime());
                    carsokCarTofront.setTofrontProductId(carsokCarTofrontOrder.getTofrontProductId());
                    carsokCarTofront.insertOrUpdate();
                    return true;
                }
            }
        }
        return false;

    }
}
