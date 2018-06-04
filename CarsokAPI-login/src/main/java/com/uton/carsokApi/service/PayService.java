package com.uton.carsokApi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.PosCode;
import com.uton.carsokApi.constants.UserErrorCode;
import com.uton.carsokApi.controller.request.ForgetPayPwdRequest;
import com.uton.carsokApi.controller.request.PayDetailRequest;
import com.uton.carsokApi.controller.request.PosPayRequest;
import com.uton.carsokApi.controller.request.SetPayPwdRequest;
import com.uton.carsokApi.controller.request.UpPayPwdRequest;
import com.uton.carsokApi.controller.response.IsSetPayPwdResponse;
import com.uton.carsokApi.controller.response.NoPayListResponse;
import com.uton.carsokApi.controller.response.PayListResponse;
import com.uton.carsokApi.controller.response.PosPayResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.AcountPosMapper;
import com.uton.carsokApi.dao.PictureMapper;
import com.uton.carsokApi.dao.PosPayDetailMapper;
import com.uton.carsokApi.dao.PosPayMapper;
import com.uton.carsokApi.dao.ProductMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.AcountPos;
import com.uton.carsokApi.model.Picture;
import com.uton.carsokApi.model.PosPay;
import com.uton.carsokApi.model.PosPayDetail;
import com.uton.carsokApi.model.Product;
import com.uton.carsokApi.util.BarCodeUtil;
import com.uton.carsokApi.util.PassWordUtil;
import com.uton.carsokApi.util.StringUtil;

/**
 *  支付相关service
 * @author bing.cheng
 *
 */
@Service
public class PayService {

	@Resource
	RedisTemplate redisTemplate;

	@Resource
	CacheService cacheService;

	@Autowired
	AcountMapper acountMapper;

	@Autowired
	PosPayDetailMapper posPayDetailMapper;

	@Autowired
	PosPayMapper posPayMapper;

	@Autowired
	AcountPosMapper acountPosMapper;

	@Autowired
	PictureMapper pictureMapper;

	@Autowired
	ProductMapper productMapper;

	//图片服务器地址
	@Value("${pic.host}")
	private String pichost;
	/**
	 * 支付详情
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult payDetail(HttpServletRequest request, PayDetailRequest vo) {
		PosPay order = new PosPay();
		order.setId(vo.getId());
		order = posPayMapper.selectByModel(order);
		List<PosPayDetail> list = posPayMapper.selectPayedDetail(vo.getId());
		BigDecimal amount = new BigDecimal(0);
		for(PosPayDetail detail:list){
			amount  = amount.add(detail.getAmount());
		}
		PosPayResponse res = new PosPayResponse();
		res.setAmount(amount); //已收金额
		res.setSubamount(order.getOrderPrice().subtract(amount)); //待收金额
		res.setStatus(order.getStatus());
		res.setPayDetails(list);
		return BaseResult.success(res);
	}

	/**
	 * 获取当前用户待支付列表
	 * @param request
	 * @return
	 */
	public BaseResult getToPayList(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		List<PosPay> list = posPayMapper.selectToPayList(acount.getId());
		List<PayListResponse> resp = new ArrayList<PayListResponse>();
		for(PosPay pospay:list){
			PayListResponse res = new PayListResponse();
			res.setId(pospay.getId());
			res.setOrder_num(pospay.getPayBarCode());
			res.setPayBarCodeUrl(pospay.getPayBarCodePicPath());
			res.setOrder_price(pospay.getOrderPrice().toString());
			res.setPay_price(pospay.getAmount().toString());
			res.setNopay_price(pospay.getOrderPrice().subtract(pospay.getAmount()).toString());
			res.setPos_id(pospay.getPosId().toString());
			res.setPay_time(pospay.getUpdateTime());
			res.setProduct_id(pospay.getProductId());
			Product productQuery = new Product();
			productQuery.setId(pospay.getProductId());
			productQuery.setAccountId(acount.getId());
			Product product = productMapper.selectByModel(productQuery);
			Picture pictureQuery = new Picture();
			pictureQuery.setProductId(product.getId());
			short pictype = 1;
			pictureQuery.setType(pictype); //只查询主图
			List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
			String imgurl = pictureList.get(0).getPicPath();
			res.setProduct_imgurl(imgurl);
			res.setProduct_name(product.getProductName());
			List<PosPayDetail> detaillist = posPayMapper.selectPayedDetail(pospay.getId());
			res.setPayDetails(detaillist);
			resp.add(res);
		}
		return BaseResult.success(resp);
	}

	/**
	 * 获取当前用户已支付列表
	 * @param request
	 * @return
	 */
	public BaseResult getPayedList(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		List<PosPay> list = posPayMapper.selectToPayList(acount.getId());
		List<NoPayListResponse> resp = new ArrayList<NoPayListResponse>();
		for(PosPay pospay:list){
			NoPayListResponse res = new NoPayListResponse();
			res.setOrder_num(pospay.getPayBarCode());
			res.setOrder_price(pospay.getOrderPrice().toString());
			res.setPay_time(pospay.getUpdateTime());
			Product productQuery = new Product();
			productQuery.setId(pospay.getProductId());
			productQuery.setAccountId(acount.getId());
			Product product = productMapper.selectByModel(productQuery);
			Picture pictureQuery = new Picture();
			pictureQuery.setProductId(product.getId());
			short pictype = 1;
			pictureQuery.setType(pictype); //只查询主图
			List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
			String imgurl = pictureList.get(0).getPicPath();
			res.setProduct_imgurl(imgurl);
			res.setProduct_name(product.getProductName());
			resp.add(res);
		}
		return BaseResult.success(resp);
	}

	/**
	 * 设置支付密码
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult setPayPwd(HttpServletRequest request, SetPayPwdRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);

		Acount upAcount = new Acount();
		upAcount.setId(acountRes.getId());
		String payPwd = vo.getPayPassword();
		upAcount.setPayPassword(payPwd);
		upAcount.setIsPaypwd((short) 2);
		upAcount.setUpdateTime(new Date());
		acountMapper.updateBySelective(upAcount);

		return BaseResult.success();
	}

	/**
	 * 校验支付密码
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult checkPayPwd(HttpServletRequest request, SetPayPwdRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);

		String payPwd = vo.getPayPassword();
		if(!payPwd.equals(acountRes.getPayPassword())){
			return BaseResult.fail(UserErrorCode.PayPwdErrorCode, UserErrorCode.PayPwdErrorfo);
		}
		return BaseResult.success();
	}
	/**
	 * 修改支付密码
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult upPayPwd(HttpServletRequest request, UpPayPwdRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);

		String oldPas = vo.getPayPassword();

		if (!oldPas.equals(acountRes.getPayPassword())) {
			return BaseResult.fail(UserErrorCode.OldPwdErrorRetCode, UserErrorCode.OldPwdErrorRetInfo);
		}

		Acount upAcount = new Acount();
		upAcount.setId(acountRes.getId());
		String newPas = vo.getNwPayPassword();
		upAcount.setPayPassword(newPas);
		upAcount.setUpdateTime(new Date());
		acountMapper.updateBySelective(upAcount);

		return BaseResult.success();
	}

	/**
	 * 忘记支付密码-重设支付密码
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult forgetPayPwd(HttpServletRequest request, ForgetPayPwdRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		boolean flag = cacheService.checkCode(acount.getAccount(), vo.getCode());
		if (!flag) {
	    	 return BaseResult.fail(UserErrorCode.CaptchaErrorRetCode, UserErrorCode.CaptchaErrorRetInfo);
		}
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		
		
		Acount upAcount = new Acount();
		upAcount.setId(acountRes.getId());
		String newPas = vo.getPayPassword();
		upAcount.setPayPassword(newPas);
		upAcount.setUpdateTime(new Date());
		acountMapper.updateBySelective(upAcount);
		
		return BaseResult.success();
	}

	/**
	 * 是否设置了支付密码
	 * @param request
	 * @return
	 */
	public BaseResult isSetPayPwd(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount acountRes = acountMapper.selectByModel(acountQuery);
		IsSetPayPwdResponse res = new IsSetPayPwdResponse();
		res.setIsSetPayPwd(acountRes.getIsPaypwd());
		return BaseResult.success(res);
	}
	
	/**
	 * 判断是否开通pos收款功能
	 * @param request
	 * @return
	 */
	public BaseResult checkPosPay(HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		AcountPos posQuery = new AcountPos();
		posQuery.setAccountId(acount.getId());
		AcountPos pos = acountPosMapper.selectByModel(posQuery);
		if(pos == null){
			return BaseResult.fail(PosCode.GetPosFail, PosCode.GetPosFailInfo);
		}else{
			if( pos.getApplyStatus() == 2 ){
				short openStatus = pos.getOpenStatus();
				if(openStatus == 1){
					return BaseResult.fail(PosCode.PosOpenFail, PosCode.PosOpenFailInfo);
				}
			}else if( pos.getApplyStatus() == 1 ){
				return BaseResult.fail(PosCode.PosApplyChecking, PosCode.PosApplyCheckingInfo);
			}else{
				return BaseResult.fail(PosCode.PosApplyCheckFail, PosCode.PosApplyCheckFailInfo);
			}
		}
		return BaseResult.success(pos);
	}
	
	/**
	 * Pos收款
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult posGoPay(HttpServletRequest request,PosPayRequest vo){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		AcountPos posQuery = new AcountPos();
		posQuery.setAccountId(acount.getId());
		AcountPos pos = acountPosMapper.selectByModel(posQuery);
		//生成收款代码
		String pay_bar_code = StringUtil.getRandCode();
		//生成收款条形码
		String pay_bar_code_pic_path = pichost+BarCodeUtil.getBarcode(pay_bar_code, "png", request);
		PosPay pospay = new PosPay();
		pospay.setPosId(pos.getId());
		pospay.setProductId(vo.getProductId());
		pospay.setPayBarCode(pay_bar_code);
		//已收金额为零元
		pospay.setAmount(new BigDecimal(0));
		pospay.setOrderPrice(vo.getOrderPrice().multiply(new BigDecimal(10000)));
		//收款单类型为“车”
		pospay.setReceiptType(1);
		pospay.setPayBarCodePicPath(pay_bar_code_pic_path);
		//状态为未支付
		pospay.setStatus(new Short("2"));
		pospay.setSummitTime(new Date());
		pospay.setPayTime(new Date());
		pospay.setCreateTime(new Date());
		pospay.setUpdateTime(new Date());
		posPayMapper.insertSelective(pospay);

		//车商分+50
		acount.setQuotientScore(acount.getQuotientScore()+50);
		acountMapper.updateQuotientScore(acount);
//		redisTemplate.opsForValue().set(acount.getToken(), acount, 30, TimeUnit.DAYS);
		return BaseResult.success(pospay);
	}
}
