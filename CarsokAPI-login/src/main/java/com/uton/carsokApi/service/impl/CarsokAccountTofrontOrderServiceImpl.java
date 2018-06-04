package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uton.carsokApi.controller.request.AccountTofrontPrepayRequest;
import com.uton.carsokApi.dao.CarsokAccountTofrontOrderMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.pay.weixin.WeixinPay;
import com.uton.carsokApi.service.ICarsokAccountTofrontOrderService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Service
public class CarsokAccountTofrontOrderServiceImpl extends ServiceImpl<CarsokAccountTofrontOrderMapper, CarsokAccountTofrontOrder> implements ICarsokAccountTofrontOrderService {
    @Override
    public SortedMap<String,String>  prepay(AccountTofrontPrepayRequest a) {

        WeixinPay wpay = new WeixinPay();
        SortedMap<String, String> map = new TreeMap<>();
        map.put("total_fee", a.getTotal_fee().toString());
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
            CarsokAccountTofrontOrder order = new CarsokAccountTofrontOrder();
            order.setCreateTime(new Date());
            order.setEnable(0);
            order.setIsSuccess(1);
            order.setOrderNo(prepayid);
            order.setOrderPrice(a.getTotal_fee());
            order.setUpdateTime(new Date());
            order.setTofrontPlanId(a.getTofrontPlanId());
            order.setTofrontAccountId(a.getTofrontAccountId());
            order.setAccountId(a.getAccountId());
            order.setChildId(a.getChildId());
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

            CarsokAccountTofrontOrder order = new CarsokAccountTofrontOrder().selectById(map.get("payOrderNo").toString());
            if (order != null) {
                if (order.getPayTime() == null) {
                    order.setPayTime(new Date());
                    order.setEnable(0);
                    order.setIsSuccess(0);
                    order.updateById();
                    //查询
                    CarsokAccountTofront carsokAccountTofront = new CarsokAccountTofront().selectOne(new EntityWrapper()
                            .eq("tofront_account_id", order.getTofrontAccountId()));
                    //查询购买套餐详情
                    CarsokTofrontPlan plan = new CarsokTofrontPlan().selectById(order.getTofrontPlanId());
                    if (carsokAccountTofront == null) {
                        carsokAccountTofront.setCreateTime(new Date());

                    }
                    carsokAccountTofront.setUpdateTime(new Date());
                    carsokAccountTofront.setAccountOrderId(order.getId());
                    carsokAccountTofront.setEnable(0);
                    //随机生成0-10000随机数
                    carsokAccountTofront.setNewOrder(new Random().nextInt(10000));
                    carsokAccountTofront.setStartTime(new Date());
                    //计算是失效时间
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.DATE, plan.getPlanTime());
                    carsokAccountTofront.setEndTime(calendar.getTime());
                    carsokAccountTofront.setTofrontAccountId(order.getTofrontAccountId());
                    carsokAccountTofront.insertOrUpdate();
                    return true;
                }
            }
        }
        return false;
    }
}
