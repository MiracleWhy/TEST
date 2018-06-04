package com.uton.carsokApi.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.Product;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.filter.SubUserAuthority;
import com.uton.carsokApi.service.ProductService;

import java.math.BigDecimal;

/**
 * 
 * 车辆产品信息相关controller
 * 
 * @author bing.cheng
 *
 */
@Controller
public class ProductController {

	private final static Logger logger = Logger.getLogger(ProductController.class);
	
	@Resource
	ProductService productService;

	@Resource
	CacheService cacheService;
	
	//相关列表做成了多个接口 TODO
	
	
	/**
	 * 发车管理
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/publishCar" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult publishCar(HttpServletRequest request, @Valid @RequestBody PublishCarRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.publishCar(vo, request);
		} catch (Exception e) {
			logger.error("发布车辆 publishCar  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 *链接收车价格
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getAcqPriceByVin" }, method = { RequestMethod.GET })
	public @ResponseBody BaseResult  getAcqPriceByVin(HttpServletRequest request){
		String vin=request.getParameter("vin");
		BigDecimal price=productService.getAcqPrice(vin);
		return BaseResult.success(price);
	}
	
	/**
	 * 编辑车辆
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/editCar" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult editCar(HttpServletRequest request, @Valid @RequestBody UpdateCarRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.editCar(vo, request);
		} catch (Exception e) {
			logger.error("editCar  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 提交商品上架审核
	 * @param request
	 * @param vo
	 * @return
	 */
	
	@RequestMapping(value = { "/onShelf" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult onShelf(HttpServletRequest request, @Valid @RequestBody OnShelfRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.onShelf(vo, request);
		} catch (Exception e) {
			logger.error("商品上架onShelf  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = { "/refreshProduct" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult refreshProduct(HttpServletRequest request, @Valid @RequestBody OnShelfRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.refreshProduct(vo, request);
		} catch (Exception e) {
			logger.error("商品上架onShelf  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	/**
	 * 在售商品下架
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/offShelf" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult offShelf(HttpServletRequest request, @Valid @RequestBody OffShelfRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.offShelf(vo, request);
		} catch (Exception e) {
			logger.error("在售商品下架offShelf  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 商品为已经售出时增加销售人等信息
	 */
	@RequestMapping(value = { "/addsaledMessage" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult addsaledMessage(HttpServletRequest request, @RequestBody Product vo) {
		try {
			return productService.addsaledMessage(vo,request);
		} catch (Exception e) {
			logger.error("在售商品售出addsaledMessage  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 查询主帐号下所有子帐号
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/selectChild" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult selectChild(HttpServletRequest request, @RequestBody Acount vo) {
		try {
			return productService.selectChild(vo,request);
		} catch (Exception e) {
			logger.error("查询所有子帐号selectChild  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}


	/**
	 * 在售商品售出
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/saled" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult saled(HttpServletRequest request, @Valid @RequestBody SaledRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.saled(vo, request);
		} catch (Exception e) {
			logger.error("在售商品售出saled  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 商品删除（在售，售出，未上架同一个删除）
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/prodcutDel" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult prodcutDel(HttpServletRequest request, @Valid @RequestBody ProdcutDelRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.prodcutDel(vo, request);
		} catch (Exception e) {
			logger.error("商品删除prodcutDel  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	
	/**
	 * 在售列表
	 * @param request
//	 * @param vo
	 * @return
	 */
	@SubUserAuthority
	@RequestMapping(value = { "/onSaleList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult onSaleList(HttpServletRequest request, @Valid @RequestBody Page page, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.onSaleList(request, page);
		} catch (Exception e) {
			logger.error("在售列表 onSaleList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 搜索车辆
	 * @param request
//	 * @param vo
	 * @return
	 */
	@SubUserAuthority
	@RequestMapping(value = { "/searchCar" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult searchList(HttpServletRequest request, @Valid @RequestBody Search search, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.searchList(request, search);
		} catch (Exception e) {
			logger.error("搜索车辆 searchCar error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}	

	/**
	 * 已售列表
	 * @param request
//	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/saledList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult saledList(HttpServletRequest request, @Valid @RequestBody Page page, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			logger.info("传入参数->proCustomer:"+page.getProCustomer());
			return productService.saledList(request, page);
		} catch (Exception e) {
			logger.error("已售列表 saledList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 未上架列表
	 * @param request
//	 * @param vo
	 * @return
	 */
	
	@RequestMapping(value = { "/offShelfList" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult offShelfList(HttpServletRequest request, @Valid @RequestBody Page page, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.offShelfList(request, page);
		} catch (Exception e) {
			logger.error("未上架列表 offShelfList error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 获取车辆信息
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/carDetail" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult carDetail(HttpServletRequest request, @Valid @RequestBody CarDetailRequest vo, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return productService.carDetail(request, vo);
		} catch (Exception e) {
			logger.error("获取车辆信息 reqCarInfo error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/productStatusCount" }, method = { RequestMethod.GET })
	public @ResponseBody BaseResult productStatusCount(HttpServletRequest request ,ProductStatusCountRequest productStatusCountRequest) {
		try {
			return productService.productStatusCount(request, request.getParameter("productName"),productStatusCountRequest);
		} catch (Exception e) {
			logger.error("获取车辆状态数量失败  productStatusCount error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 推送上架,已售,库存合计消息ByDays
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/queryPublishCountByDay" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult queryPublishCountByDay(HttpServletRequest request) {
		try {
			return productService.queryPublishCountDay(request);
		} catch (Exception e) {
			logger.error("推送上架,收购,已售消息七天合计  queryPublishCountByDay error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 推送上架,已售,库存合计消息ByWeek
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/queryPublishCountByWeek" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult queryPublishCountByWeek(HttpServletRequest request) {
		try {
			return productService.queryPublishCountWeek(request);
		} catch (Exception e) {
			logger.error("推送上架,收购,已售消息周合计  queryPublishCountByWeek error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 推送上架,已售,库存合计消息ByMonth
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/queryPublishCountByMonth" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult queryPublishCountByMonth(HttpServletRequest request) {
		try {
			return productService.queryPublishCountMonth(request);
		} catch (Exception e) {
			logger.error("推送上架,收购,已售消息月合计  queryPublishCountByMonth error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}


	@RequestMapping(value = { "/productReserveIf" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult productReserveIf(HttpServletRequest request, @RequestBody ProductReserveRequest vo) {
		try {
			return productService.productReserveIf(request,vo);
		} catch (Exception e) {
			logger.error("车辆已定错误  productReserveIf：", e);
			return BaseResult.exception(e.getMessage());
		}
	}


	@RequestMapping(value = {"/getProductByVin"}, method = {RequestMethod.GET})
	public
	@ResponseBody
	BaseResult getProductByVin(HttpServletRequest request) {
		BaseResult result = BaseResult.success();
		try {
			String vin = request.getParameter("vin");
			Acount acount = cacheService.getAcountInfoFromCache(request);
			if (StringUtil.isEmpty(vin)) {
				result = BaseResult.fail("0001", "vin为空");
			}
			result.setData(productService.getProductByVin(acount.getId(), vin));
		} catch (Exception e) {
			logger.error("获取车辆错误：", e);
			return BaseResult.exception(e.getMessage());
		}
		return result;
	}
}
