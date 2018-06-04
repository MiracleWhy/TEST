package com.uton.carsokApi.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.AllyMapper;
import com.uton.carsokApi.dao.CarsokProductMapper;
import com.uton.carsokApi.dao.CarsokPruductOldcarMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.ICarsokProductService;
import com.uton.carsokApi.service.core.IndexSendService;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.DozerMapperUtil;
import com.uton.carsokApi.util.HttpClientUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author csw
 * @since 2017-11-08
 */
@Service("carsokproductservice")
@Transactional
public class CarsokProductServiceImpl extends ServiceImpl<CarsokProductMapper, CarsokProduct> implements ICarsokProductService {

    @Autowired
    CarsokProductMapper carsokProductMapper;

    @Value("${make.carStocktlm.api}")
    private String carStocktlm;

    @Value("${share.url}")
    private String carDetailUrls;

    @Autowired
    IndexSendService indexSendService;

    @Value("${producturl.prefix}")
    private String producturl_prefix;

    @Autowired
    private CarsokPruductOldcarMapper carsokPruductOldcarMapper;

    @Autowired
    private AllyMapper allyMapper;

    @Autowired
    AcountMapper accountMapper;

    private final static Logger logger = Logger.getLogger(CarsokProductServiceImpl.class);

    /**
     * 库存列表数据查询
     *
     * @param inventoryRequest
     * @return
     */
    @Override
    public CarsokProductResponse getInventoryList(InventoryRequest inventoryRequest) {
        String carUrl = "";
        //在售数量
        Integer onSaleCount = carsokProductMapper.getOnSaleCount(inventoryRequest);
        //已售数量
        Integer saledCount = carsokProductMapper.getSaledCount(inventoryRequest);
        //分页查询数据列表
        Page<InventoryResponse> page = PageHelper.startPage(inventoryRequest.getPageNum(), inventoryRequest.getPageSize());
        List<InventoryResponse> carStockList = carsokProductMapper.getInventoryList(inventoryRequest);
        for (InventoryResponse inventoryResponse:carStockList) {
            List<CarStockPic> picList = carsokProductMapper.getPicList(inventoryResponse.getCarId());
            List<CarStockPic> newpicList = new ArrayList<>();
            for (CarStockPic carStockPic:picList) {
                if(carStockPic.getType() == 1){
                    inventoryResponse.setPicPath(carStockPic.getPicPath());
                }
                CarStockPic ss = new CarStockPic();
                BeanUtils.copyProperties(carStockPic,ss);
                newpicList.add(ss);
            }
            inventoryResponse.setPicList(newpicList);
            carUrl = carDetailUrls+ inventoryResponse.getProductNo()+".html";
            inventoryResponse.setCarUrl(carUrl);
        }
        //数据总条数
        long listCount = page.getTotal();
        CarsokProductResponse carsokProductResponse = new CarsokProductResponse();
        carsokProductResponse.setCarStockList(carStockList);
        carsokProductResponse.setListCount(listCount);
        carsokProductResponse.setOnSaleCount(onSaleCount);
        carsokProductResponse.setSaledCount(saledCount);
        return carsokProductResponse;
    }

    /**
     * 获取车辆详情
     *
     * @param id
     * @return
     */
    @Override
    public CarDetailsResponse getCarDetails(String id) {
        CarDetailsResponse carDetailsResponse= carsokProductMapper.getCarDetails(id);
        String carDetailUrl = carDetailUrls+ carDetailsResponse.getProductNo()+".html";
        carDetailsResponse.setCarDetailUrl(carDetailUrl);
        return carDetailsResponse;
    }

    /**
     * 修改车辆详情
     * @param newCarDetailsRequest
     * @return
     */
    @Override
    public Boolean editCarDetails(NewCarDetailsRequest newCarDetailsRequest) {
        CarsokProduct carsokProduct = new CarsokProduct();
        DozerMapperUtil.getInstance().map(newCarDetailsRequest, carsokProduct);
        if(carsokProduct.getMiniprice() != null){
            carsokProduct.setMiniprice(carsokProduct.getMiniprice().multiply(new BigDecimal(10000)));
        }else{
            carsokProduct.setMiniprice(new BigDecimal(0));
        }
        if(carsokProduct.getPrice() != null){
            carsokProduct.setPrice(carsokProduct.getPrice().multiply(new BigDecimal(10000)));
        }else{
            carsokProduct.setPrice(new BigDecimal(0));
        }

        String url = carStocktlm+newCarDetailsRequest.getId();
        HttpClientUtil.sendGetRequest(url, "UTF-8");
        Boolean flag = carsokProduct.updateById();
        //推送至SearchCenter
        if(flag){
            indexSendService.SingleInsertOrUpdate(newCarDetailsRequest.getId(),false);
        }
        return flag;
    }

    /**
     * 删除车辆接口
     *
     * @param id
     * @return
     */
    @Override
    public Boolean delCarDetails(String id) {
        CarsokProduct carsokProduct = new CarsokProduct();
        carsokProduct.setId(Integer.valueOf(id));
        carsokProduct.setIsDel(1);
        Boolean flag = carsokProduct.updateById();
        //推送至SearchCenter
        if(flag){
            indexSendService.SingleInsertOrUpdate(Integer.valueOf(id),false);
        }
        return flag;
    }

    /**
     * 更新车辆在售状态
     *
     * @param id
     * @return
     */
    @Override
    public Boolean updateCarSaleStatus(String id) {
        CarsokProduct carsokProduct = new CarsokProduct();
        carsokProduct.setId(Integer.valueOf(id));
        carsokProduct.setSaleStatus(1);
        Boolean flag = carsokProduct.updateById();
        //推送至SearchCenter
        if(flag){
            indexSendService.SingleInsertOrUpdate(Integer.valueOf(id),false);
        }
        return flag;
    }

    @Override
    public CarsokProduct selectByProductNo(Integer id) {
        return new CarsokProduct().selectOne(new EntityWrapper()
//                .isNotNull("car_id")
                .eq("id", id));
    }

    @Override
    public boolean updateSaledPeople(Integer id, String phone) {
        CarsokProduct carsokProduct = new CarsokProduct();
        carsokProduct .setId(id);
        carsokProduct.setSaledPeople(phone);
        carsokProduct.setUpdateTime(new Date());
        return  carsokProduct.updateById() ;
    }

    @Override
    public Page<IntentionCarsListResponse> getIntentionCarsList(String accountId, int pageSize, int pageNum) {
        Map<String,String> map = new HashMap<>();
        map.put("accountId",accountId);
        map.put("onShelfStatus","1");
        map.put("saleStatus","0");
        PageHelper.startPage(pageNum,pageSize);
        Page<IntentionCarsListResponse> intentionCarsListResponses =carsokProductMapper.getintentionCarsList(map);
        if (intentionCarsListResponses.size()>=0) {
            for (IntentionCarsListResponse intentionCarsListResponse:intentionCarsListResponses) {
                if (intentionCarsListResponse.getMiles()==null){
                    intentionCarsListResponse.setMiles(0);
                }
                CarsokPicture pic =new CarsokPicture().selectOne(new EntityWrapper()
                        .eq("type",1)
                        .eq("product_id",intentionCarsListResponse.getId()));
                 if (pic!=null){
                intentionCarsListResponse.setPicPath(pic.getPicPath());
                 }
              /*  List<CarStockPic> picList = carsokProductMapper.getPicList(intentionCarsListResponse.getCarId());
                List<CarStockPic> newpicList = new ArrayList<>();
                for (CarStockPic carStockPic:picList) {
                    if(carStockPic.getType() == 1){
                        intentionCarsListResponse.setPicPath(carStockPic.getPicPath());
                    }
                    CarStockPic ss = new CarStockPic();
                    BeanUtils.copyProperties(carStockPic,ss);
                    newpicList.add(ss);
                }
               */
            }
        }

        return intentionCarsListResponses;
    }

    @Override
    public Page<IntentionCarsListResponse> getIntentionCarsList_271(Acount acount, IntentionCarsListRequest icarListRequest) {
        Page<IntentionCarsListResponse> carList = intentionCarList(acount, icarListRequest);
        return carList;
    }

    @Override
    public Page<IntentionCarsListResponse> intentionCarList(Acount acount, IntentionCarsListRequest icarListRequest){
        logger.info("请求参数为："+JSON.toJSONString(icarListRequest));
        Page<IntentionCarsListResponse> iCarsListResponses = new Page<IntentionCarsListResponse>();
        Calendar productionEndDate = Calendar.getInstance();
        Calendar productionStartDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        switch (icarListRequest.getCarAge()) {
            case 1:
                productionStartDate.add(Calendar.YEAR, -1);
                icarListRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                break;
            case 2:
                productionStartDate.add(Calendar.YEAR, -3);
                productionEndDate.add(Calendar.YEAR, -1);
                icarListRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                icarListRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            case 4:
                productionStartDate.add(Calendar.YEAR, -5);
                productionEndDate.add(Calendar.YEAR, -3);
                icarListRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                icarListRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            case 5:
                productionStartDate.add(Calendar.YEAR, -5);
                productionEndDate.add(Calendar.YEAR, -8);
                icarListRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                icarListRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            case 6:
                productionStartDate.add(Calendar.YEAR, -8);
                productionEndDate.add(Calendar.YEAR, -10);
                icarListRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                icarListRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            case 7:
                productionEndDate.add(Calendar.YEAR, -10);
                icarListRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            default:
                break;
        }

        if(icarListRequest.getCarType()==0){
            PageHelper.startPage(icarListRequest.getPageNum(),icarListRequest.getPageSize());
            iCarsListResponses = carsokProductMapper.getintentionCarsList_271(acount.getId(),icarListRequest);
        }else {
            List<Integer> acountIdList = new ArrayList<>();
            List<AllyResponse> allyResponse=allyMapper.getFriendList(acount.getAccount());
            for(AllyResponse ally:allyResponse){
                if(!ally.getFriendAccount().equals(acount.getAccount())){
                    Acount account = accountMapper.selectByAccountCode(ally.getFriendAccount());
                    if(account != null){
                        acountIdList.add(account.getId());
                    }
                }else {
                    Acount account = accountMapper.selectByAccountCode(ally.getCarAccountCode());
                    if(account != null){
                        acountIdList.add(account.getId());
                    }
                }
            }
            logger.info(acount.getAccount()+"的好友id为"+acountIdList);
            PageHelper.startPage(icarListRequest.getPageNum(),icarListRequest.getPageSize());
            iCarsListResponses =carsokProductMapper.getintentionCarsListForAlly(acountIdList,icarListRequest);
        }
        if (iCarsListResponses.size()>=0) {
            for (IntentionCarsListResponse intentionCarsListResponse:iCarsListResponses) {
                if (intentionCarsListResponse.getMiles()==null){
                    intentionCarsListResponse.setMiles(0);
                }
                CarsokPicture pic =new CarsokPicture().selectOne(new EntityWrapper()
                        .eq("type",1)
                        .eq("product_id",intentionCarsListResponse.getId()));
                if (pic!=null){
                    intentionCarsListResponse.setPicPath(pic.getPicPath());
                }
                try {
                    intentionCarsListResponse.setOnShelfDays(DateUtil.getDaySub(sdf.parse(intentionCarsListResponse.getOnShelfTime()), new Date()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                CarsokProduct product = new CarsokProduct().selectOne(new EntityWrapper().eq("id",intentionCarsListResponse.getId()));
                intentionCarsListResponse.setProductUrl(producturl_prefix+product.getProductNo()+".html");
                logger.info("微店车辆url："+producturl_prefix+product.getProductNo()+".html");
            }
        }
        return iCarsListResponses;
    }

    @Override
    public List<CarSourceResponse> getCarsouceList(CarSourceSearchRequest carSourceSearchRequest, Acount acount,Integer pageNum,Integer pageSize) {
        Calendar productionEndDate = Calendar.getInstance();
        Calendar productionStartDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        switch (carSourceSearchRequest.getCarAge())
        {
            case 1:
                productionStartDate.add(Calendar.YEAR, -1);
                carSourceSearchRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                break;
            case 2:
                productionStartDate.add(Calendar.YEAR, -3);
                productionEndDate.add(Calendar.YEAR, -1);
                carSourceSearchRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                carSourceSearchRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            case 4:
                productionStartDate.add(Calendar.YEAR, -5);
                productionEndDate.add(Calendar.YEAR, -3);
                carSourceSearchRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                carSourceSearchRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            case 5:
                productionStartDate.add(Calendar.YEAR, -5);
                productionEndDate.add(Calendar.YEAR, -8);
                carSourceSearchRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                carSourceSearchRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            case 6:

                productionStartDate.add(Calendar.YEAR, -8);
                productionEndDate.add(Calendar.YEAR, -10);
                carSourceSearchRequest.setProductionStartDate(sdf.format(productionStartDate.getTime()));
                carSourceSearchRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            case 7:

                productionEndDate.add(Calendar.YEAR, -10);
                carSourceSearchRequest.setProductionEndDate(sdf.format(productionEndDate.getTime()));
                break;
            default:
                break;
        }
        List<CarSourceResponse> carSourceResponses = carsokProductMapper.getCarsouceList(carSourceSearchRequest,pageNum,pageSize);
        if (carSourceResponses.size()>0) {
            for (CarSourceResponse carSourceResponse : carSourceResponses) {
                carSourceResponse.setCarUrl( producturl_prefix+carSourceResponse.getProductNo()+".html");
            }
        }
        return carSourceResponses;
    }

    @Override
    public Page<TopCarSourceListResponse> getTopCarSourceList(TopCarSourceListRequest t) {
         Page<TopCarSourceListResponse> responses = carsokProductMapper.getTopCarSourceLsit(t);
        return  responses;
    }

    @Override
    public TitleDataResponse titleData() {
        TitleDataResponse titleDataResponse=new TitleDataResponse();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

        Integer ratio = 3;
        int browse = 537;
        titleDataResponse.setGrandTotalNew(carsokProductMapper.getGrandTotalNew());
        titleDataResponse.setTodayNew(new CarsokProduct().selectCount(new EntityWrapper()
                .between("create_time",formatter1.format(new Date()),formatter2.format(new Date()))));
        titleDataResponse.setTodayBrowse(ratio*carsokPruductOldcarMapper.getTodayViewCount() + browse);
        titleDataResponse.setGrandTotalBrowse(carsokPruductOldcarMapper.getTotleViewCount() + titleDataResponse.getTodayBrowse());
        return  titleDataResponse;

    }

    @Override
    public boolean clearDayViewCount() {
        CarsokPruductOldcar carsokPruductOldcar = new CarsokPruductOldcar();
        carsokPruductOldcar.setTodayBrowseNumTimes(0);
        return carsokPruductOldcar.update(new EntityWrapper());
    }

    @Override
    public boolean addBrowse(int productId) {
        CarsokPruductOldcar carsokPruductOldcar =new CarsokPruductOldcar().selectOne(new EntityWrapper().eq("pruduct_id",productId));
        boolean flag= false;
        if (carsokPruductOldcar != null) {
            carsokPruductOldcar.setTodayBrowseNumTimes(carsokPruductOldcar.getTodayBrowseNumTimes()+1);
            carsokPruductOldcar.setBrowseNumTimes(carsokPruductOldcar.getBrowseNumTimes()+1);
        flag = carsokPruductOldcar.update(new EntityWrapper().eq("pruduct_id",productId));
        }
      return  flag;
    }

    @Override
    public int getTotalCount() {
        return carsokProductMapper.getGrandTotalNew();
    }

    @Override
    public Page<AllyCarListResponse> getAllyCarList(List<Integer> accountIds, AllyCarListRequest request) {
        Map<String,String> map = new HashMap<>();

        map.put("onShelfStatus","1");
        map.put("saleStatus","0");
        map.put("searchBy",request.getSearchBy());
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        Page<AllyCarListResponse> allyCarList =carsokProductMapper.getAllyCarList(accountIds,map);
        if (allyCarList.size()>=0) {
            for (AllyCarListResponse allyCarListResponse:allyCarList) {
                if (allyCarListResponse.getMiles()==null){
                    allyCarListResponse.setMiles(0);
                }
                CarsokPicture pic =new CarsokPicture().selectOne(new EntityWrapper()
                        .eq("type",1)
                        .eq("product_id",allyCarListResponse.getId()));
                if (pic!=null){
                    allyCarListResponse.setPicPath(pic.getPicPath());
                }

            }
        }

        return allyCarList;
    }


}
