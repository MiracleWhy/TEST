package com.uton.carsokApi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.CategorieRequest;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.service.CategorieService;

/**
 * 车系相关
 * @author Administrator
 *
 */
@Controller
public class CategorieController {
	private final static Logger logger = Logger.getLogger(CategorieController.class);
	
	@Autowired
	CategorieService categorieService;
	
	/**
	 * 父级（brand车系品牌）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/carBrand" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult carBrand(HttpServletRequest request) {
		try {
			return categorieService.carBrand();
		} catch (Exception e) {
			logger.error("carBrand  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 子级（model车系）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/carType" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult carType(HttpServletRequest request,@Valid @RequestBody CategorieRequest vo, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return categorieService.carType(vo);
		} catch (Exception e) {
			logger.error("carModel error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 具体车型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/carModel" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult carModel(HttpServletRequest request,@Valid @RequestBody CategorieRequest vo, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return categorieService.carModel(vo);
		} catch (Exception e) {
			logger.error("carModel error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 车型模糊查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/searchCarModels" }, method = { RequestMethod.GET })
	public @ResponseBody BaseResult searchCarModels(HttpServletRequest request) {
		try {
			String keyword = request.getParameter("keyword");
			String pageNum = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			if (StringUtil.isEmpty(pageNum)){
				pageNum = "0";
			}
			if (StringUtil.isEmpty(pageSize)){
				pageSize = "0";
			}
			return  BaseResult.success(categorieService.searchCarModels(keyword, Integer.valueOf(pageNum), Integer.valueOf(pageSize)));
		} catch (Exception e) {
			logger.error("searchCarModels error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	/**
	 * 具体车型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/carLogo" }, method = { RequestMethod.POST })
	public @ResponseBody OperateResult carModel(HttpServletRequest request,@RequestBody Map<String,String> map) {
		try {
			return new OperateResult(true,"操作成功", categorieService.carLogo(map.get("carBrand")));
		} catch (Exception e) {
			logger.error("carModel error:", e);
			return new OperateResult(false,e.getMessage());
		}
	}
}
