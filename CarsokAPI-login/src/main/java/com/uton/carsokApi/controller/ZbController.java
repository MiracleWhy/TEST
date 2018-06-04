package com.uton.carsokApi.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokZbTaskSxyWb;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.LoanUserService;
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
import com.uton.carsokApi.model.ChildRole;
import com.uton.carsokApi.service.ZbService;

@Controller
public class ZbController {
	private final static Logger logger = Logger.getLogger(ZbController.class);
	
	@Autowired
	private ZbService zbService;

	@Autowired
	private LoanUserService loanUserService;

	@Resource
	CacheService cacheService;
	
	
	/**
	 * 角色管理 获取主账户角色账户列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getChildRole" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult getChildRole(HttpServletRequest request) {
		try {
			return zbService.getChildRole(request);
		} catch (Exception e) {
			logger.error("getChildRole error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 角色管理 获取主账户角色账户列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/hasRole" }, method = { RequestMethod.GET })
	public @ResponseBody BaseResult hasRole(HttpServletRequest request) {
		try {
			Acount acount =cacheService.getAcountInfoFromCache(request);
			String role=request.getParameter("role");
			if(StringUtil.isEmpty(role)) {
				role="2";
			}
			return BaseResult.success(loanUserService.isLoanUser(acount.getId().toString(),role));
		} catch (Exception e) {
			logger.error("getChildRole error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}


	
	/**
	 * 子账户分配权限
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping(value = { "/saveChildRole" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult saveChildRole(HttpServletRequest request,@RequestBody ChildRole role) {
		try {
			return zbService.saveChildRole(request, role);
		} catch (Exception e) {
			logger.error("getChildRole error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 子账户分配权限--按顺序
	 * @param request
	 * @param role
	 * @return
	 */
	@RequestMapping(value = { "/saveChildRoleOnOrder" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult saveChildRoleOnOrder(HttpServletRequest request,@RequestBody ChildRole role) {
		try {
			return zbService.saveChildRoleOnOrder(request, role);
		} catch (Exception e) {
			logger.error("getChildRole error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/removeRoleById" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult removeRoleById(HttpServletRequest request,@RequestBody ChildRole role) {
		try {
			return zbService.removeById(request, role);
		} catch (Exception e) {
			logger.error("removeById error:", e);
			return BaseResult.exception(e.getMessage());
		}
		}

		/**
		 * 手续员发布任务
		 * @param request
		 * @param vo
		 * @param result
		 * @return
		 */
		@RequestMapping(value = { "/publishTask" }, method = { RequestMethod.POST })
		public @ResponseBody BaseResult publishCar(HttpServletRequest request, @Valid @RequestBody PublishTaskRequest vo, BindingResult result) {
			try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return zbService.publishTask(vo, request);
		} catch (Exception e) {
			logger.error("发布任务 publishTask  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 评估师提交任务
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/pgsEditTask" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult pgsEditTask(HttpServletRequest request, @Valid @RequestBody PgsTaskRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return zbService.editTaskBypgs(vo, request);
		} catch (Exception e) {
			logger.error("评估师提交任务  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 整备员提交任务
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/zbyEditTask" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult zbyEditTask(HttpServletRequest request, @Valid @RequestBody ZbyTaskRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return zbService.editTaskByzby(vo, request);
		} catch (Exception e) {
			logger.error("评估师提交任务  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 经理提交任务
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/managerEditTask" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult managerEditTask(HttpServletRequest request, @Valid @RequestBody ManagerTaskRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return zbService.editTaskBymanager(vo, request);
		} catch (Exception e) {
			logger.error("评估师提交任务  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 待办任务
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/toDoTask" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult toDoTask(HttpServletRequest request, @Valid @RequestBody ToDoTaskRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return zbService.toDoTask(vo, request);
		} catch (Exception e) {
			logger.error("评估师提交任务  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 已办任务
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/hasDoTask" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult hasDoTask(HttpServletRequest request, @Valid @RequestBody ToDoTaskRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return zbService.hasDoTask(vo, request);
		} catch (Exception e) {
			logger.error("评估师提交任务  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

	/**
	 * 删除任务
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/deleteTask" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult deleteTask(HttpServletRequest request,@RequestBody DelTaskRequest vo) {
		try {
			return zbService.deleteTask(vo.getId(),request);
		} catch (Exception e) {
			logger.error("删除任务  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 已办任务详情
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/hasDoTaskInfo" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult taskInfo(HttpServletRequest request, @Valid @RequestBody TaskInfoRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return zbService.taskInfo(vo, request,1);
		} catch (Exception e) {
			logger.error("评估师提交任务  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 待办任务详情
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/toDoTaskInfo" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult toDoTaskInfo(HttpServletRequest request, @Valid @RequestBody TaskInfoRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return zbService.taskInfo(vo, request,2);
		} catch (Exception e) {
			logger.error("评估师提交任务  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}


	/**
	 * 运营专员详情修改
	 * @param request
	 * @param vo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = { "/updateTaskByyyzy" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult updateTaskByyyzy(HttpServletRequest request, @Valid @RequestBody TaskForYyzyRequest vo, BindingResult result) {
		try {
			if(result.hasErrors()){
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return zbService.updateTaskByyyzy(request,vo);
		} catch (Exception e) {
			logger.error("评估师提交任务  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	@RequestMapping(value = { "/zbCount" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult zbCount(HttpServletRequest request,@RequestBody ZbCountRequest vo){
		try {
			return zbService.selectZbCount(request,vo);
		} catch (Exception e) {
			logger.error("查询整备总数错误  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	/**
	 * 手续员待办
	 */
	@RequestMapping(value = { "/sxydb" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult sxydb(HttpServletRequest request){
		try {
			return  zbService.sxydb(request);
		} catch (Exception e) {
			logger.error("手续员待办  error:", e);
			return BaseResult.exception(e.getMessage());
		}


	}

	/**
	 * 再次整备
	 */
	@RequestMapping(value = { "/zbAgain" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult zbAgain(HttpServletRequest request,@RequestBody ZbyTaskAgainRequest vo){
		try {
			return  zbService.zbAgain(request,vo);
		} catch (Exception e) {
			logger.error("再次整备  error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}

}
