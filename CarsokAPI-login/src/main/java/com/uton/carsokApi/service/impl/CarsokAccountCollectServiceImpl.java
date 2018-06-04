package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.CarCollectRequest;
import com.uton.carsokApi.controller.response.AccountCollectListResponse;
import com.uton.carsokApi.dao.CarsokAccountCollectMapper;
import com.uton.carsokApi.model.CarsokAccountCollect;
import com.uton.carsokApi.service.ICarsokAccountCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class CarsokAccountCollectServiceImpl extends ServiceImpl<CarsokAccountCollectMapper, CarsokAccountCollect> implements ICarsokAccountCollectService {

    @Autowired
    CarsokAccountCollectMapper carsokAccountCollectMapper;

    @Value("${store.url.prefix}")
    private String weiShopUrl;

    /**
     * 获取车行收藏列表
     * @param carCollectRequest
     * @return
     */
    @Override
    public Map getAccountCollectList(CarCollectRequest carCollectRequest) {
        Map<String,Object> map = new HashMap<>();
        Integer count = carsokAccountCollectMapper.getListCount(carCollectRequest.getAccountId(),carCollectRequest.getChildId(),carCollectRequest.getSearchName());
        PageHelper.startPage(carCollectRequest.getPageNum(),carCollectRequest.getPageSize());
        List<AccountCollectListResponse> list = carsokAccountCollectMapper.getAccountCollectList(carCollectRequest);
        for(int i = 0; i < list.size(); i++){
            list.get(i).setAccountUrl(weiShopUrl + list.get(i).getAccountCode() + ".html?accountId=" + list.get(i).getCollectAccountId());
        }
        map.put("list",list);
        map.put("count",count);
        return map;
    }

    /**
     * 添加收藏车行
     * @param carsokAccountCollect
     * @return
     */
    @Override
    public int addAccountCollect(CarsokAccountCollect carsokAccountCollect) {
        carsokAccountCollect.setUpdateTime(new Date());
        carsokAccountCollect.setCreateTime(new Date());
        carsokAccountCollect.insert();
        return carsokAccountCollect.getId();
    }

    /**
     * 取消收藏车行
     * @param id
     * @return
     */
    @Override
    public Boolean delAccountCollect(Integer id) {
        CarsokAccountCollect carsokAccountCollect = new CarsokAccountCollect();
        carsokAccountCollect.setId(id);
        return carsokAccountCollect.deleteById();
    }

    /**
     * 查询车行是否被收藏
     * @param accountId
     * @param childId
     * @param collectAccountId
     * @return
     */
    @Override
    public Integer isCollectAccount(Integer accountId, Integer childId, Integer collectAccountId) {
        return carsokAccountCollectMapper.isCollectAccount(accountId,childId,collectAccountId);
    }
}
