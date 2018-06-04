package com.uton.carsokApi.controller;


import com.github.pagehelper.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.enums.LimitEnum;
import com.uton.carsokApi.controller.request.CarFindListRequest;
import com.uton.carsokApi.controller.request.FindCarMsgRequest;
import com.uton.carsokApi.controller.request.PublishFindCarRequest;
import com.uton.carsokApi.controller.response.CarFindUp;
import com.uton.carsokApi.controller.response.FindCarMsgResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ChildInfoService;
import com.uton.carsokApi.service.ICarsokAccountPowerService;
import com.uton.carsokApi.service.ICarsokFindCarService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@RestController
@RequestMapping("/carsokFindCar")
public class CarsokFindCarController {

    @Resource
    CacheService cacheService;

    @Autowired
    private ICarsokFindCarService carsokFindCarService;

    @Autowired
    private ICarsokAccountPowerService carsokAccountPowerService;

    @Autowired
    private ChildInfoService childInfoService;

    /**
     * @author zhangdi
     * @date 2018/1/19 11:19
     * @Description: 寻车列表
     */
    @RequestMapping("carFindList")
    public BaseResult carFindList(HttpServletRequest request, @RequestBody CarFindListRequest u) {
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            Page<CarFindUp> carFindUps = carsokFindCarService.getCarFindList(u);
            int limit = carsokAccountPowerService.getQueryVehicleCarSourcePermissions(acount, LimitEnum.FINDCAR.getCode());
            if (limit == 1 && acount.getIsMerchantAudit() == 2) {
                limit = 1;
            } else {
                limit = 0;
            }
            Map map = new HashedMap();
            map.put("limit", limit);
            map.put("list", carFindUps);
            return BaseResult.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    /**
     * @author zhangdi
     * @date 2018/1/22 17:51
     * @Description: 发布寻车
     */
    @RequestMapping("publishFindCar")
    public BaseResult publishFindCar(HttpServletRequest request, @RequestBody PublishFindCarRequest p) {
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if (acount == null) {
                return BaseResult.fail("0002", "请重新登入");
            }
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                ChildAccount childAccount = childInfoService.selectByChildAccount(acount.getSubPhone());
                p.setChildId(childAccount.getId());
            } else {
                p.setChildId(0);
            }
            p.setAccountId(acount.getId());
            boolean flag = carsokFindCarService.publishFindCar(p);
            if (flag == false) {
                return BaseResult.fail("0003", "发布寻车失败");
            }
            return BaseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }

    /**
    * @author zhangdi
    * @date 2018/1/23 10:22
    * @Description: 查看寻车信息
    */
    @RequestMapping("selectMsg")
    public BaseResult selectMsg(HttpServletRequest request, @RequestBody FindCarMsgRequest f) {
        try {
            FindCarMsgResponse response = carsokFindCarService.selectMsg(f);
            return BaseResult.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("0001", "系统异常");
        }
    }
}
