package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.AddNewCarRequest;
import com.uton.carsokApi.controller.request.CarStockListRequest;
import com.uton.carsokApi.controller.request.CarStockPicRequest;
import com.uton.carsokApi.controller.request.CarStockRequest;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.ICarsokCarStockService;
import com.uton.carsokApi.util.DozerMapperUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author csw
 * @since 2017-11-08
 */
@Service
public class CarsokCarStockServiceImpl extends ServiceImpl<CarsokCarStockMapper, CarsokCarStock> implements ICarsokCarStockService {

    private final static Logger logger = Logger.getLogger(CarsokCarStockServiceImpl.class);

    @Autowired
    TCarBrandMapper tCarBrandMapper;
    @Autowired
    CarsokCarStockPictureMapper carsokCarStockPictureMapper;
    @Autowired
    CarsokProductMapper carsokProductMapper;
    @Autowired
    CarsokPruductOldcarMapper carsokPruductOldcarMapper;
    @Autowired
    CarsokPictureMapper carsokPictureMapper;
    @Value("${product.num.prefix}")
    private String product_num_prefix;


    @Override
    public Integer addNewCar(AddNewCarRequest acr) {

        CarsokProduct cp = new CarsokProduct();
        BeanUtils.copyProperties(acr, cp);
        cp.setProductName(acr.getCarBrand() + " " + acr.getCarSeries() + " " + acr.getConfiguration());
        cp.setCreateTime(new Date());
        cp.setUpdateTime(new Date());
        cp.setProductNo(product_num_prefix + StringUtil.getRandCode());
        cp.setOnShelfStatus(1);
        //carsok_product 新增数据
        carsokProductMapper.insert(cp);

        List<CarsokCarStockPicture> pics = carsokCarStockPictureMapper.selectList(Condition.create().eq("car_id", cp.getCarId()));
        for (int i = 0; i < pics.size(); i++) {
            CarsokCarStockPicture newCarsokCarStockPicture = pics.get(i);
            //插入车型图片
            CarsokPicture newcp = new CarsokPicture();
            BeanUtils.copyProperties(newCarsokCarStockPicture, newcp);
            newcp.setId(null);
            newcp.setProductId(cp.getId());
            newcp.setCreateTime(new Date());
            newcp.setUpdateTime(new Date());
            carsokPictureMapper.insert(newcp);
        }

        Integer id = cp.getId();
        if (id != null) {
            CarsokPruductOldcar carsokPruductOldcar = new CarsokPruductOldcar();
            carsokPruductOldcar.setPruductId(id);
            carsokPruductOldcar.setCreateTime(new Date());
            carsokPruductOldcar.setUpdateTime(new Date());
            carsokPruductOldcar.setcBrand(acr.getCarBrand());
            carsokPruductOldcar.setcModel(acr.getCarSeries());
            carsokPruductOldcar.setAriableBox(acr.getAriableBox());
            carsokPruductOldcar.setDisplacement(acr.getDisplacement());
            carsokPruductOldcarMapper.insert(carsokPruductOldcar);
        }

        return id;
    }


    @Override
    public CarStockListResponse getCarStockList(CarStockListRequest clr) {

        CarsokCarStock param = new CarsokCarStock();
        param.setAccountId(clr.getAccountId());//账户id
        param.setCarBrandId(clr.getCarBrandId());//品牌id
        param.setId(clr.getId());//车型id
        param.setLike(clr.getSearchBy());//搜索字段
        Page<CarsokCarStock> page = PageHelper.startPage(clr.getPageNum(), clr.getPageSize());

        List<CarsokCarStock> list = baseMapper.selectCarsokCarStockList(param);
        long count = page.getTotal();

        List<CarStockListBean> result = new ArrayList<>();

        if (page.getResult() != null && page.getResult().size() > 0) {
            CarStockListBean clb = null;
            for (int i = 0; i < page.getResult().size(); i++) {
                clb = new CarStockListBean();
                BeanUtils.copyProperties(page.getResult().get(i), clb);
                result.add(clb);
            }
        }


        return new CarStockListResponse(count, result);
    }

    @Override
    public BrandListResponse getBrandList(String accountId) {

        List<CarsokCarStock> list = baseMapper.selectList(Condition.create().eq("enable", 1).eq("account_id", accountId).groupBy("car_brand_id").orderBy("car_brand_id"));

        List<BrandListBean> brandList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            BrandListBean blb = null;
            for (int i = 0; i < list.size(); i++) {
                blb = new BrandListBean();
                blb.setCarBrandId(list.get(i).getCarBrandId());
                blb.setCarBrand(list.get(i).getCarBrand());
                brandList.add(blb);
            }
        }


        return new BrandListResponse(brandList);
    }

    @Override
    public SeriesListResponse getSeriesList(String accountId, Integer carBrandId) {

        List<CarsokCarStock> list = baseMapper.selectList(Condition.create().eq("enable", 1).eq("account_id", accountId).eq("car_brand_id", carBrandId).orderBy("id"));
        List<SeriesListBean> seriesList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            SeriesListBean slb = null;
            for (int i = 0; i < list.size(); i++) {
                slb = new SeriesListBean();
                slb.setId(list.get(i).getId());
                slb.setCarSeries(list.get(i).getCarSeries());
                seriesList.add(slb);
            }
        }
        return new SeriesListResponse(seriesList);
    }

    @Override
    public CarStockInfoResponse getCarStockInfo(String accountId, Integer id) {
        CarsokCarStock carsokCarStock = baseMapper.selectById(id);
        if (carsokCarStock != null && accountId.equals(carsokCarStock.getAccountId())) {

            List<CarsokCarStockPicture> list = carsokCarStockPictureMapper.selectList(Condition.create().eq("enable", 1).eq("car_id", carsokCarStock.getId()).orderBy("id desc"));

            List<PicListBean> picList = new ArrayList<>();

            if (list != null && list.size() > 0) {
                PicListBean plb = null;
                for (int i = 0; i < list.size(); i++) {
                    plb = new PicListBean();
                    BeanUtils.copyProperties(list.get(i), plb);
                    picList.add(plb);
                }
            }

            return new CarStockInfoResponse(carsokCarStock.getId(), carsokCarStock.getCarBrand(), carsokCarStock.getCarBrandId(),carsokCarStock.getCarSeries(), picList);
        }

        return null;
    }

    @Override
    public void delCarStock(String accountId, Integer id) {
        CarsokCarStock carsokCarStock = baseMapper.selectById(id);
        if (carsokCarStock != null && accountId.equals(carsokCarStock.getAccountId())) {
            carsokCarStock.setUpdateTime(new Date());
            carsokCarStock.setEnable(0);
            baseMapper.updateById(carsokCarStock);

            CarsokCarStockPicture ccsp = new CarsokCarStockPicture();
            ccsp.setUpdateTime(new Date());
            ccsp.setEnable(0);
            carsokCarStockPictureMapper.update(ccsp, Condition.create().eq("car_id", carsokCarStock.getId()));
        }
    }

    /**
     * 获取原有品牌列表
     *
     * @return
     */
    @Override
    public List<OldBrandResponse> getOldBrandList() {
        List<TCarBrand> list = tCarBrandMapper.searchAllBrand();
        List<OldBrandResponse> oldBrandList = new ArrayList();
        for (TCarBrand oneList : list) {
            OldBrandResponse oldBrandResponse = new OldBrandResponse();
            oldBrandResponse.setCarBrand(oneList.getBrandName());
            oldBrandResponse.setCarBrandId(Integer.valueOf(oneList.getBrandId()));
            oldBrandList.add(oldBrandResponse);
        }
        return oldBrandList;
    }

    /**
     * 车型库新增/编辑
     *
     * @param carStockRequest
     * @return
     */
    @Override
    public Boolean editNewCarStock(CarStockRequest carStockRequest) {
        Boolean result = false;
        CarsokCarStock carsokCarStock = new CarsokCarStock();
        //车型库表数据
        DozerMapperUtil.getInstance().map(carStockRequest, carsokCarStock);
        carsokCarStock.setEnable(1);
        //传入图片列表
        List<CarStockPicRequest> list = carStockRequest.getPicList();

        if (carStockRequest.getId() == null) {//车型id为空，则为新增
            carsokCarStock.setCreateTime(new Date());
            carsokCarStock.setUpdateTime(new Date());
            //插入车型库表
            Boolean flag1 = carsokCarStock.insert();
            //插入车型图片
            Boolean flag2 = addNewPic(list, carsokCarStock.getId());
            if (flag1 && flag2) {
                result = true;
            }

        } else {//车型id不为空，则为修改
            carsokCarStock.setUpdateTime(new Date());
            //更新车型库表
            Boolean flag1 = carsokCarStock.updateById();
            //先删除已有图片
            CarsokCarStockPicture carsokCarStockPicture = new CarsokCarStockPicture();
            carsokCarStockPicture.setEnable(0);
            carsokCarStockPicture.delete(new EntityWrapper().eq("car_id", carStockRequest.getId()));
//            carsokPictureMapper.delete(new EntityWrapper().eq("product_id", carStockRequest.getId()));
            //插入新图片
            Boolean flag2 = addNewPic(list, carStockRequest.getId());

            if (flag1 && flag2) {
                result = true;
            }
        }
        return result;
    }


    /**
     * 插入新图片
     *
     * @param list
     * @param carId
     * @return
     */
    private Boolean addNewPic(List<CarStockPicRequest> list, Integer carId) {
        for (CarStockPicRequest oneList : list) {
            CarsokCarStockPicture newCarsokCarStockPicture = new CarsokCarStockPicture();
            DozerMapperUtil.getInstance().map(oneList, newCarsokCarStockPicture);
            newCarsokCarStockPicture.setEnable(1);
            newCarsokCarStockPicture.setCreateTime(new Date());
            newCarsokCarStockPicture.setUpdateTime(new Date());
            newCarsokCarStockPicture.setCarId(carId);
            newCarsokCarStockPicture.insert();
            //插入车型图片
//            CarsokPicture cp = new CarsokPicture();
//            BeanUtils.copyProperties(newCarsokCarStockPicture,cp);
//            newCarsokCarStockPicture.insert();
//            cp.setProductId(carId);
//            carsokPictureMapper.insert(cp);

        }
        return true;
    }
}
