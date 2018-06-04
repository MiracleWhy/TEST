package com.uton.carsokApi.controller;


import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.AddNewCarRequest;
import com.uton.carsokApi.controller.request.CarStockListRequest;
import com.uton.carsokApi.controller.request.CarStockPicRequest;
import com.uton.carsokApi.controller.request.CarStockRequest;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ICarsokCarStockService;
import com.uton.carsokApi.service.core.IndexSendService;
import com.uton.carsokApi.util.HttpClientUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 入库管理
 * </p>
 *
 * @author csw
 * @since 2017-11-08
 */
@RestController
@RequestMapping("/carStockManage")
public class CarsokCarStockController {
    private final static Logger logger = Logger.getLogger(CarsokCarStockController.class);

    @Autowired
    ICarsokCarStockService carsokCarStockService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    IndexSendService indexSendService;

    @Value("${make.carStocktlm.api}")
    private String carStocktlm;


    /**
     * 获取原有品牌列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOldBrandList", method = RequestMethod.POST)
    public BaseResult getOldBrandList(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            result.setData(carsokCarStockService.getOldBrandList());

        } catch (Exception e) {
            logger.error("getOldBrandList error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 4.3.2 车辆入库
     * 车辆入库
     * eg
     *
     * @param acr
     * @return
     */
    @PostMapping("/addNewCar")
    public BaseResult addNewCar(HttpServletRequest request, @Valid @RequestBody AddNewCarRequest acr, BindingResult valid) {
        if (valid.hasErrors()) {

            List<FieldError> list = valid.getFieldErrors();
            FieldError error = null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                error = list.get(i);
                sb.append(error.getField()).append(error.getDefaultMessage());
            }
            return BaseResult.exception(sb.toString());
        }
        if (acr.getMiniprice() != null) {
            acr.setMiniprice(acr.getMiniprice().multiply(new BigDecimal(10000)));
        }
        acr.setVin(acr.getVin().toUpperCase());
        acr.setPrice(acr.getPrice().multiply(new BigDecimal(10000)));
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        } else {
            acr.setAccountId(acount.getId());
        }
        Integer id = carsokCarStockService.addNewCar(acr);
        if (id == null) {
            return BaseResult.exception("入库失败");
        }
        indexSendService.SingleInsertOrUpdate(id, true);
        String url = carStocktlm + id;
        HttpClientUtil.sendGetRequest(url, "UTF-8");
        result.setData(null);
        return result;
    }


    /**
     * 4.3.3 入库管理首页列表查询
     * 车型列表查询
     * eg {"pageNum":"1","pageSize":"1","searchBy":"1","carBrandId":"3","id":"1"}
     *
     * @param clr
     * @return
     */
    @PostMapping("/getCarStockList")
    public BaseResult getCarStockList(HttpServletRequest request, @RequestBody CarStockListRequest clr) {
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        } else {
            clr.setAccountId(acount.getId().toString());
        }
        result.setData(carsokCarStockService.getCarStockList(clr));
        return result;
    }

    /**
     * 4.3.4 品牌列表查询
     * 品牌列表查询
     * eg
     *
     * @return
     */
    @PostMapping("/getBrandList")
    public BaseResult getBrandList(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        } else {

            result.setData(carsokCarStockService.getBrandList(acount.getId().toString()));
        }
        return result;
    }

    /**
     * 4.3.5 车型列表查询
     * 车型列表查询
     * eg
     *
     * @return
     */
    @PostMapping("/getSeriesList")
    public BaseResult getSeriesList(HttpServletRequest request, @RequestBody CarStockListRequest clr) {
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        } else {
            result.setData(carsokCarStockService.getSeriesList(acount.getId().toString(), clr.getCarBrandId()));
        }
        return result;
    }

    /**
     * 4.3.6 车型库编辑查询
     * 车型库编辑查询
     * eg
     *
     * @return
     */
    @PostMapping("/getCarStockInfo")
    public BaseResult getCarStockInfo(HttpServletRequest request, @RequestBody CarStockListRequest clr) {
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        } else {
            result.setData(carsokCarStockService.getCarStockInfo(acount.getId().toString(), clr.getId()));
        }
        return result;
    }

    /**
     * 4.3.7 车型库删除（逻辑删除）
     * 车型库删除（逻辑删除）
     * eg
     *
     * @return
     */
    @GetMapping("/delCarStock")
    public BaseResult delCarStock(HttpServletRequest request, Integer id) {
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        } else {
            carsokCarStockService.delCarStock(acount.getId().toString(), id);
        }
        return result;
    }

    @RequestMapping(value = "/editNewCarStock", method = RequestMethod.POST)
    public BaseResult editNewCarStock(HttpServletRequest request, @RequestBody CarStockRequest carStockRequest) {
        BaseResult result = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if (acount == null) {
            return BaseResult.exception("请重新登陆");
        } else {
            carStockRequest.setAccountId(acount.getId().toString());
        }

        if (StringUtil.isEmpty(carStockRequest.getMainPic())) {
            return BaseResult.exception("请添加主图");
        }

        List<CarStockPicRequest> picList = new ArrayList<>();
        if (carStockRequest.getPicLists() != null && carStockRequest.getPicLists().length > 0) {
            for (int i = 0; i < carStockRequest.getPicLists().length; i++) {
                CarStockPicRequest csp = new CarStockPicRequest();
                csp.setType(0);
                csp.setPicPath(carStockRequest.getPicLists()[i]);
                picList.add(csp);
            }
        }
        Collections.reverse(picList);
        if (carStockRequest.getPicList() != null && carStockRequest.getPicList().size() > 0) {

            for (int i = 0; i < carStockRequest.getPicList().size(); i++) {
                carStockRequest.getPicList().get(i).setType(0);
            }

            CarStockPicRequest csp = new CarStockPicRequest();
            csp.setType(1);
            csp.setPicPath(carStockRequest.getMainPic());
            picList.add(csp);
        } else {
            CarStockPicRequest csp = new CarStockPicRequest();
            csp.setType(1);
            csp.setPicPath(carStockRequest.getMainPic());
            picList.add(csp);
        }
        carStockRequest.setPicList(picList);


        try {
            Boolean flag = carsokCarStockService.editNewCarStock(carStockRequest);
            if (flag) {
                return result;
            } else {
                return BaseResult.exception("插入数据失败");
            }
        } catch (Exception e) {
            logger.error("editNewCarStock error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }
}
