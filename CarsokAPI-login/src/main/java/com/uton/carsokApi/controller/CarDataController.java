package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.controller.request.CarDataSearchRequest;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarDataInfo;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.CarDataInfoService;

/**
 * 车辆数据控制器
 * @author SEELE
 *
 */
@Controller
public class CarDataController {
	@Autowired
	private CarDataInfoService carDataInfoService;
	@Autowired
	private CacheService cacheService;

	@RequestMapping("carDataList")
	@ResponseBody
	public OperateResult carList(HttpServletRequest request,CarDataSearchRequest carDataRequest){
		Acount acount=cacheService.getAcountInfoFromCache(request);
		if(acount==null){
			return new OperateResult(false,"请重新登录");
		}
		//分页
		//这里的筛选条件比较多
		PageHelper.startPage(carDataRequest.getCurPage(),carDataRequest.getPageSize());
		carDataRequest.setAccountId(acount.getId()+"");
		Page<CarDataInfo> page =carDataInfoService.queryByCondition(carDataRequest);
		//车源 品牌 车龄 排序 地区 车系/品牌模糊搜索
		return new OperateResult(true, "",page.toPageInfo());
	}
	@RequestMapping("/getCarList")
	public String getCarList(HttpServletRequest request){
		 return "/getCarList";
	}
	
}
