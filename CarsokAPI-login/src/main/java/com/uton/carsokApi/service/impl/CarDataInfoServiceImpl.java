package com.uton.carsokApi.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.BZYCarDataRequest;
import com.uton.carsokApi.controller.request.CarDataSearchRequest;
import com.uton.carsokApi.controller.request.ThirdDataSellCountRequest;
import com.uton.carsokApi.controller.response.ThirdDataSellCountResponse;
import com.uton.carsokApi.dao.CarDataImageMapper;
import com.uton.carsokApi.dao.CarDataInfoMapper;
import com.uton.carsokApi.dao.PartnerMapper;
import com.uton.carsokApi.model.CarDataImage;
import com.uton.carsokApi.model.CarDataInfo;
import com.uton.carsokApi.model.Partner;
import com.uton.carsokApi.service.CarDataInfoService;
import com.uton.carsokApi.util.DateUtil;

@Service
public class CarDataInfoServiceImpl implements CarDataInfoService {
	@Autowired
	private CarDataInfoMapper carDataInfoMapper;
	@Autowired
	private CarDataImageMapper carDataImageMapper;
	@Autowired
	private PartnerMapper partnerMapper;
	@Value("${data_product.min_price}")
	private String dataProductMinPrice;
	@Value("${date_product.filter.min_sell_price}")
	private BigDecimal minSellPrice;
	@Override
	public BaseResult createCarDataInfo(CarDataInfo carDataInfo) {
		carDataInfo.setGmtCreate(new Date());
		carDataInfo.setGmtModify(new Date());
		carDataInfo.setEnable(true);
		carDataInfoMapper.insertSelective(carDataInfo);
		return BaseResult.success();
	}

	@Override
	public BaseResult createCarDataInfoAndImage(BZYCarDataRequest request) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		CarDataInfo carDataInfo = request.getCarInfo();
		List<CarDataImage> carImages = request.getCarImages();
		for(CarDataImage carImage:carImages){
			if(carImage==null||StringUtils.isEmpty(carImage.getUrl())){
				return BaseResult.fail("0001", "图片为url为空");
			}
		}
		if(carImages==null||carImages.size()==0){
			return BaseResult.fail("0001", "图片为空");
		}
		if(carDataInfo==null){
			return BaseResult.fail("0001", "数据为空");
		}
		if(StringUtils.isEmpty(carDataInfo.getCarBrand())||StringUtils.isEmpty(carDataInfo.getCardDate())||StringUtils.isEmpty(carDataInfo.getCarModel())||StringUtils.isEmpty(carDataInfo.getDealerName())||StringUtils.isEmpty(carDataInfo.getThirdId())||carDataInfo.getSellPrice()==null){
			return BaseResult.fail("0001", "数据为错误");
		}
		if(minSellPrice.compareTo(carDataInfo.getSellPrice())>0){
			return BaseResult.fail("0001", "价格太低");
		}
		carDataInfo.setUuid(uuid);
		BaseResult result = createCarDataInfo(carDataInfo);
		result = createCarDataImages(carImages, uuid);
		return result;
	}

	@Override
	public BaseResult createCarDataImages(List<CarDataImage> carDataImages, String uuid) {
		// 集合为空，没有图片
		if (carDataImages == null) {
			return BaseResult.success();
		}
		for (CarDataImage carDataImage : carDataImages) {
			carDataImage.setCarUuid(uuid);
			carDataImage.setEnable("1");
			carDataImage.setGmtCreate(new Date());
			carDataImage.setGmtModify(new Date());
			carDataImageMapper.insertSelective(carDataImage);
		}
		return BaseResult.success();
	}

	@Override
	public BaseResult validatePartner(String partner, String token) {
		Partner p = partnerMapper.selectPartner(partner, token);
		if (p != null) {
			return BaseResult.success(p.getProviderName());
		}
		return BaseResult.fail(ErrorCode.IdVerifyFail, ErrorCode.IdVerifyFailInfo);
	}

	@Override
	public CarDataInfo queryById(String id) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(id)) {
			return null;
		}
		return carDataInfoMapper.selectByPrimaryKey(Integer.valueOf(id));
	}

	@Override
	public ThirdDataSellCountResponse querySellCount(ThirdDataSellCountRequest countRequest) {
		ThirdDataSellCountResponse response = new ThirdDataSellCountResponse();
		List<Map> map = carDataInfoMapper.selectSellCount(countRequest);
		Long totalCount = new Long(0);
		for (Map entry : map) {
			Object obj = entry.get("counts");
			if (obj != null) {
				totalCount = totalCount + (Long) obj;
			}
		}
		response.setCounts(map);
		response.setTotalCount(totalCount);
		return response;
	}

	@Override
	public Page<CarDataInfo> queryByCondition(CarDataSearchRequest carDataRequest) {
		if (!StringUtils.isEmpty(carDataRequest.getCarAge())) {
			String carAge = carDataRequest.getCarAge();
			if (carDataRequest.getCarAge().indexOf(",") > -1) {
				String strs[] = carAge.split(",");
				Date carDateEnd = DateUtil.addYear(new Date(), -Integer.parseInt(strs[0]));
				Date carDateStart = DateUtil.addYear(new Date(), -Integer.parseInt(strs[1]));
				carDataRequest.setCarDateStart(carDateStart);
				carDataRequest.setCarDateEnd(carDateEnd);
			} else {
				Date carDateEnd = DateUtil.addYear(new Date(), -Integer.parseInt(carAge));
				carDataRequest.setCarDateEnd(carDateEnd);
			}
		}
		carDataRequest.setOrderBy(getOrderBy(carDataRequest.getOrder()));
		Page<CarDataInfo> page = carDataInfoMapper.selectByCondition(carDataRequest);
		for (CarDataInfo carDataInfo : page) {
			carDataInfo.setPrice(getPrice(carDataInfo));
		}
		return page;
	}

	public String getOrderBy(String order) {
		String orderInfo = "";
		switch (order) {
		case "0":
			orderInfo = "order by gmt_create desc";
			break;
		case "1":
			orderInfo = "order by sell_price asc";
			break;
		case "2":
			orderInfo = "order by sell_price desc";
			break;
		case "3":
			orderInfo = "order by card_date desc";
			break;
		case "4":
			orderInfo = "order by mileage asc";
			break;
		default:
			break;
		}
		return orderInfo;
	}
	@Override
	public  BigDecimal getPrice(CarDataInfo carDataInfo) {
		BigDecimal  minPrice=new BigDecimal(24);
		if(!StringUtils.isEmpty(dataProductMinPrice)){
			try{
				minPrice=new BigDecimal(dataProductMinPrice);
			}
			catch (Exception e) {
			}
		}
		BigDecimal sellPrice = carDataInfo.getSellPrice();
		if (sellPrice == null) {
			return minPrice;
		}
		if (sellPrice.multiply(new BigDecimal(10000)).intValue() <= 120000) {
			return minPrice;
		} else {
			int value = sellPrice.subtract(new BigDecimal(12)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() * 2;
			return new BigDecimal(value).add(minPrice);
		}
	}

	@Override
	public CarDataImage queryCarImage(String uuid) {
		return carDataImageMapper.selectOneByUuid(uuid);
	}

}
