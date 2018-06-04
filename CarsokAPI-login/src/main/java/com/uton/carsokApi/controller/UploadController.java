package com.uton.carsokApi.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.StoreInfoRequest;
import com.uton.carsokApi.controller.request.UploadRequest;
import com.uton.carsokApi.service.StoreInfoService;
import com.uton.carsokApi.service.UploadService;
import com.uton.carsokApi.util.StringUtil;
import com.uton.carsokApi.util.UploadUtil;

/**
 * 文件上传
 * @author runxin.fu
 *
 */
@Controller
public class UploadController {
	private final static Logger logger = Logger.getLogger(UploadController.class);
	
	@Autowired
	StoreInfoService storeInfoService;
	
	@Autowired
	UploadService uploadService;
	
	
	/**
	 * 上传图片
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = { "/upload" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult upload(HttpServletRequest request, @RequestBody UploadRequest vo) {
		try {
			if (null == vo) {
					logger.error("参数为空");
					return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
			}
			if(StringUtil.isEmpty(vo.getImgstr())&&StringUtil.isEmpty(vo.getImgstrURL())){
				logger.error("参数为空");
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
			}
			String url="";
			if(vo.getImgstr()!=null&&!"".equals(vo.getImgstr())){
				url=uploadService.upload(request, vo.getImgstr());
			}
			if(vo.getImgstrURL()!=null&&!"".equals(vo.getImgstrURL())){
				url=vo.getImgstrURL();
			}
			if(!StringUtil.isEmpty(url)){
				return BaseResult.success(url);
			}else{
				return BaseResult.fail(ErrorCode.UploadFail, ErrorCode.UploadFailInfo);
			}
		} catch (Exception e) {
			logger.error("login error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 头像上传
//	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/uploadHead" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult uploadHead(@RequestBody UploadRequest vo,HttpServletRequest request) {
		try{
			if (null == vo ) {
				logger.error("参数为空");
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
			}
			if(StringUtil.isEmpty(vo.getImgstr())&&StringUtil.isEmpty(vo.getImgstrURL())){
				logger.error("参数为空");
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
			}
			String url="";
			if(vo.getImgstr()!=null&&!"".equals(vo.getImgstr())){
				url=uploadService.upload(request, vo.getImgstr());
			}
			if(vo.getImgstrURL()!=null&&!"".equals(vo.getImgstrURL())){
				url=vo.getImgstrURL();
			}
			if(!StringUtil.isEmpty(url)){
				StoreInfoRequest req = new StoreInfoRequest();
				req.setHeadportraitpath(url);
	            storeInfoService.upStoreInfo(request, req);
	            return BaseResult.success();
			}else{
				return BaseResult.fail(ErrorCode.UploadFail, ErrorCode.UploadFailInfo);
			}
		}catch(Exception e){
			logger.error("upload image error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
	
	/**
	 * 头像上传
//	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/uploadShopPic" }, method = { RequestMethod.POST })
	public @ResponseBody BaseResult uploadShopPic(@RequestBody UploadRequest vo,HttpServletRequest request) {
		try{
			if (null == vo ) {
				logger.error("参数为空");
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
			}
			if (StringUtil.isEmpty(vo.getImgstr())&&StringUtil.isEmpty(vo.getImgstrURL())) {
				logger.error("参数为空");
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
			}
			String url="";
			if(vo.getImgstr()!=null&&!vo.getImgstr().equals("")){
				url=uploadService.upload(request, vo.getImgstr());
			}
			if(vo.getImgstrURL()!=null&&!vo.getImgstrURL().equals("")){
				url=vo.getImgstrURL();
			}
			if(!StringUtil.isEmpty(url)){
				StoreInfoRequest req = new StoreInfoRequest();
				req.setMerchantImagePath(url);
	            storeInfoService.upStoreInfo(request, req);
	            return BaseResult.success();
			}else{
				return BaseResult.fail(ErrorCode.UploadFail, ErrorCode.UploadFailInfo);
			}
		}catch(Exception e){
			logger.error("upload image error:", e);
			return BaseResult.exception(e.getMessage());
		}
	}
}
