package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.SharingRequest;
import com.uton.carsokApi.service.ShareRankService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 分享排行controller
 */
@Controller
public class RankController {

	private final static Logger logger = Logger.getLogger(ProductController.class);

	@Resource
	ShareRankService shareRankService;

	/**
	 * 分享排行ByDay
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/rankLevelByDay" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult rankLevelByDay(HttpServletRequest request) {
		try {
			return shareRankService.shareRankingsByDays(request);
		} catch (Exception e) {
			logger.error("分享排行ByDay rankLevelByDay  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 车辆分享朋友圈车商分+1
	 */
	@RequestMapping(value = { "/rankQuotientScore" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult rankQuotientScore(HttpServletRequest request,@RequestBody SharingRequest vo) {
		try {
			return shareRankService.addquotientScore(request,vo);
		} catch (Exception e) {
			logger.error("分享增加车商分  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 分享排行ByWeek
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/rankLevelByWeek" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult rankLevelByWeek(HttpServletRequest request) {
		try {
			return shareRankService.shareRankingsByWeek(request);
		} catch (Exception e) {
			logger.error("分享排行ByWeek rankLevelByWeek  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 分享排行ByMonth
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/rankLevelByMonth" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult rankLevelByMonth(HttpServletRequest request) {
		try {
			return shareRankService.shareRankingsByMonth(request);
		} catch (Exception e) {
			logger.error("分享排行ByMonth rankLevelByMonth  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
}
