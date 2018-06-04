package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.MerchantListRequest;
import com.uton.carsokApi.controller.response.AccountTofrontResponse;
import com.uton.carsokApi.controller.response.CarTofrontResponse;
import com.uton.carsokApi.controller.response.MerchantListResponse;
import com.uton.carsokApi.dao.CarsokAccountTofrontMapper;
import com.uton.carsokApi.dao.CarsokAcountMapper;
import com.uton.carsokApi.dao.CarsokCarTofrontMapper;
import com.uton.carsokApi.model.CarsokAccountTofront;
import com.uton.carsokApi.service.ICarsokAccountTofrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
public class CarsokAccountTofrontServiceImpl extends ServiceImpl<CarsokAccountTofrontMapper, CarsokAccountTofront> implements ICarsokAccountTofrontService {

    @Autowired
    private CarsokAcountMapper carsokAcountMapper;

    @Autowired
    private CarsokAccountTofrontMapper carsokAccountTofrontMapper;

    @Autowired
    private CarsokCarTofrontMapper carsokCarTofrontMapper;

    @Value("${producturl.prefix}")
    private String productHtml;

    @Value("${store.url.prefix}")
    private String storeUrlPrefix;
    @Override
    public Page<MerchantListResponse> getMmerchantList(MerchantListRequest m) {

        PageHelper.startPage(m.getPageNum(),m.getPageSize());
        Page<MerchantListResponse> merchantListResponses = carsokAcountMapper.getMerchantList(m);
        //生成微店地址
        for (MerchantListResponse merchantListResponse : merchantListResponses) {
            merchantListResponse.setStoreUrl(storeUrlPrefix+merchantListResponse.getAccountCode()+".html?accountId="+merchantListResponse.getId());
        }
        return merchantListResponses;
    }

    /**
     * 查询置顶记录
     * @param accountId
     * @param childId
     * @return
     */
    @Override
    public Map<String, Object> getTofrontHistoryData(Integer accountId, Integer childId,Integer pageNum,Integer pageSize) {
        Map<String, Object> map = new HashMap();
        AccountTofrontResponse accountTofront = carsokAccountTofrontMapper.accountTofront(accountId,childId);
        if(accountTofront != null && accountTofront.getAccountCode() != null){
            accountTofront.setAccountUrl(storeUrlPrefix + accountTofront.getAccountCode() + ".html");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<CarTofrontResponse> carTofront = carsokCarTofrontMapper.carTofront(accountId, childId);
        if(carTofront.size() > 0){
            for(CarTofrontResponse oneCar:carTofront){
                if(oneCar.getProductNo() != null){
                    oneCar.setCarUrl(productHtml + oneCar.getProductNo() + ".html");
                }
            }
        }
        map.put("accountTofront",accountTofront);
        map.put("carTofront",carTofront);
        return map;
    }
}
