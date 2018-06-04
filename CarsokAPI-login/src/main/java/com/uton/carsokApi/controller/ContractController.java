package com.uton.carsokApi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.uton.carsokApi.controller.response.CarContractResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarContract;
import com.uton.carsokApi.service.CacheService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.ContractRequest;
import com.uton.carsokApi.controller.request.Search;
import com.uton.carsokApi.controller.response.ContractResponse;
import com.uton.carsokApi.filter.SubUserAuthority;
import com.uton.carsokApi.service.ContractService;
import com.uton.carsokApi.service.ProductService;

@Controller
public class ContractController {
	private final static Logger logger = Logger.getLogger(ContractController.class);

	@Resource
	CacheService cacheService;
	
	@Autowired
	private ContractService contractService;
	
	@Resource
	ProductService productService;
	
	@RequestMapping(value = { "/contractList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult contractList(HttpServletRequest request, @Valid @RequestBody ContractRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return contractService.querylist(vo);
		} catch (Exception e) {
			logger.error("contractList  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	@SubUserAuthority
	@RequestMapping(value = { "/searchContract" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult searchContract(HttpServletRequest request, @Valid @RequestBody Search search, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.searchContract(request, search.getSearchKey(),search.getOnShelfStatus());
		} catch (Exception e) {
			logger.error("在售列表 onSaleList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	@RequestMapping("/queryContractByCarId")
	public @ResponseBody BaseResult queryContractByCarId(String carId){
		return contractService.queryByCarId(carId);
	}

	@RequestMapping("/queryContractList")
	public @ResponseBody BaseResult queryContractList(HttpServletRequest request,@RequestBody List<String> carIdList){
		BaseResult result = BaseResult.success();
		List<ContractResponse> contractList = new ArrayList<ContractResponse>();
		String tempStr=null;
		String tempName=null;
		Map<String,String> map=new HashMap<String,String>();
		map.put("1", "试驾协议");
		map.put("2", "交车确认单");
		map.put("3", "车辆交易合同");
		map.put("4", "二手车寄售合同书");
		map.put("5", "寄售车取回确认单");
		map.put("6", "定金条");
		map.put("7", "定金收据");
		map.put("8", "收车合同");
		map.put("9", "保密协议");
		map.put("10", "新车合同");
		if(null==carIdList||carIdList.size()==0){
			result.setData(contractList);
			return result;
		}
		try {
			String curPage = request.getParameter("curPage");
			String pageSize = request.getParameter("pageSize");
			PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize));
			String accountId = request.getParameter("account");

			//电子合同
			Page<ContractResponse> page  = contractService.queryByCarIdListContract(carIdList);
			//纸质合同
			List<ContractResponse> contractResponses = contractService.selectCarContractByCarId(carIdList,accountId);
			//纸质合同
			List<ContractResponse> tempList= page.getResult();
			//车辆列表
			List<CarContractResponse> carContractResponseList = contractService.selectCarList(carIdList);
			//所有合同
			contractResponses.addAll(tempList);
			//将相同车辆的合同放在同一辆车下面
			for (int i = 0; i < contractResponses.size(); i++) {
				for (int j = 0; j < contractResponses.size(); j++) {
					if (carContractResponseList.get(i).getCarId() == contractResponses.get(j).getCarId())
						carContractResponseList.get(i).getContractResponseList().add(contractResponses.get(j));
				}
			}



			for (ContractResponse contractResponse : tempList) {
				for(String key:map.keySet()){
					tempStr=map.get(key);
					if(key.equals(String.valueOf(contractResponse.getTypeInt()))){
						contractResponse.setType(tempStr);
						if(null!=contractResponse.getName()){
							tempName=contractResponse.getName()+"-"+tempStr;
						}
						contractResponse.setName(tempName);

					}
				}
				contractList.add(contractResponse);
			}

			result.setData(tempList);
		} catch (Exception e) {
			return BaseResult.exception(e.getMessage());
		}
		return result;
	}

	@RequestMapping("/saveCarContract")
	public @ResponseBody BaseResult saveCarContract(HttpServletRequest request, @RequestBody CarContract vo){
		BaseResult result = BaseResult.success();
		try {
			Acount acount = cacheService.getAcountInfoFromCache(request);
			vo.setAcountId(acount.getId());
			contractService.saveCarContract(vo);
		} catch (Exception e) {
			logger.error("saveCarContract  error:", e);
			return BaseResult.exception(e.getMessage());
		}
		return result;
	}


	@RequestMapping("/carContractList")
	public @ResponseBody BaseResult carContractList(HttpServletRequest request, @RequestBody CarContract vo){
		try {
			return contractService.getCarContractList(vo);
		} catch (Exception e) {
			logger.error("saveCarContract  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 根据id删除合同
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delContractById")
	@ResponseBody
	public BaseResult delContractById(HttpServletRequest request){
		try {
			if(request.getParameter("type") != null && request.getParameter("id") != null ){
				contractService.delContractById(request.getParameter("type"),request.getParameter("id"));
				return BaseResult.success();
			}else{
				logger.error("参数为空");
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
			}
		} catch (Exception e) {
			logger.error("delContractById  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 获取纸质合同图片路径
	 * @param carId
	 * @return
	 */
	@RequestMapping("/carContractByCarId")
	public @ResponseBody BaseResult carContractByCarId(String carId){
		try {
			return contractService.carContractByCarId(carId);
		} catch (Exception e) {
			e.printStackTrace();
			return BaseResult.exception(e.getMessage());
		}
	}

	@RequestMapping(value = "/queryContractByCarIdAndType",method = RequestMethod.POST)
	public @ResponseBody BaseResult queryContractByCarIdAndType(HttpServletRequest request,String carId,String type){
		carId = request.getParameter("carId");
		type = request.getParameter("type");
		BaseResult baseResult = contractService.queryContractByCarIdAndType(carId, type);
		return baseResult;
	}
}
