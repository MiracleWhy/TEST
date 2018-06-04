package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.CarCollectRequest;
import com.uton.carsokApi.controller.response.CarCollectListResponse;
import com.uton.carsokApi.controller.response.ShareAccountInfo;
import com.uton.carsokApi.dao.CarsokCarCollectMapper;
import com.uton.carsokApi.model.CarsokCarCollect;
import com.uton.carsokApi.service.ICarsokCarCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Service
public class CarsokCarCollectServiceImpl extends ServiceImpl<CarsokCarCollectMapper, CarsokCarCollect> implements ICarsokCarCollectService {

    @Autowired
    CarsokCarCollectMapper carsokCarCollectMapper;

    @Value("${producturl.prefix}")
    private String productHtml;

    /**
     * 获取车源收藏列表
     * @param carCollectRequest
     * @return
     */
    @Override
    public Map getCarCollectList(CarCollectRequest carCollectRequest) {
        Map<String,Object> map = new HashMap<>();
        Integer count = carsokCarCollectMapper.getListCount(carCollectRequest.getAccountId(),carCollectRequest.getChildId(),carCollectRequest.getSearchName());
        PageHelper.startPage(carCollectRequest.getPageNum(),carCollectRequest.getPageSize());
        List<CarCollectListResponse> list = carsokCarCollectMapper.getCarCollectList(carCollectRequest);
        BigDecimal b = new BigDecimal(10000);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getPrice() != null){
                list.get(i).setPrice(list.get(i).getPrice().divide(b));
            }
            list.get(i).setCarUrl(productHtml + list.get(i).getProductNo() + ".html?carId=" + list.get(i).getCarId());
        }
        map.put("count",count);
        map.put("carCollectList",list);
        return map;
    }

    /**
     * 添加车源收藏
     * @param carsokCarCollect
     * @return
     */
    @Override
    public int addCarCollect(CarsokCarCollect carsokCarCollect) {
        carsokCarCollect.setCreateTime(new Date());
        carsokCarCollect.setUpdateTime(new Date());
        carsokCarCollect.insert();
        return carsokCarCollect.getId();
    }

    /**
     * 取消车源收藏
     * @param id
     * @return
     */
    @Override
    public Boolean delCarCollect(Integer id) {
        CarsokCarCollect carsokCarCollect = new CarsokCarCollect();
        carsokCarCollect.setId(id);
        return carsokCarCollect.deleteById();
    }

    /**
     * 查询车源是否收藏
     * @param accountId
     * @param childId
     * @param collectCarId
     * @return
     */
    @Override
    public Integer isCollectCar(Integer accountId, Integer childId, Integer collectCarId) {
        return carsokCarCollectMapper.isCollectCar(accountId, childId, collectCarId);
    }

    /**
     * 查询分享车行信息
     * @param accountId
     * @return
     */
    @Override
    public ShareAccountInfo getAcountInfo(Integer accountId) {
        return carsokCarCollectMapper.getAcountInfo(accountId);
    }
}
