package com.uton.carsokApi.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.core.IndexSendService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.LoginErrorCode;
import com.uton.carsokApi.constants.enums.CarDel;
import com.uton.carsokApi.constants.enums.OnSelfStatus;
import com.uton.carsokApi.constants.enums.OrderTypeEnum;
import com.uton.carsokApi.constants.enums.SaleStatus;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.StringUtil;

/**
 * 车辆信息管理service
 * @author bing.cheng
 *
 */
@Service("productService")
public class ProductService {
	
	private final static Logger logger = Logger.getLogger(ProductService.class);

	@Autowired
	private EventBus eventBus;

	@Autowired
	PictureMapper pictureMapper;
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	AcountMapper acountMapper;
	
	@Autowired
	PruductOldcarMapper pruductOldcarMapper;
	
	@Resource
    RedisTemplate redisTemplate;
	
	@Resource
	CacheService cacheService;
	
	@Autowired
	UploadService uploadService;
	
	@Autowired
	NoticeMapper noticeMapper;
	
	@Autowired
	private ContractMapper contractMapper;

	@Autowired
	private ZbTaskMapper taskMapper;
	
	@Value("${product.num.prefix}")
	private String product_num_prefix;
	
	@Value("${producturl.prefix}")
	private String producturl_prefix;

	@Autowired
	ShareRecordMapper shareRecordMapper;

	@Autowired
	ChildAccountMapper childAccountMapper;

	@Autowired
	TenureCustomerMapper tenureCustomerMapper;

	@Autowired
	MessageCenterService messageCenterService;

	@Autowired
	PushService pushService;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	AcquisitionCarMapper acquisitionCarMapper;

	@Autowired
	IndexSendService indexSendService;

	@Autowired
	CarsokCustomerMapper carsokCustomerMapper;

	@Autowired
	CarsokCustomerTenureCarMapper carsokCustomerTenureCarMapper;

	@Autowired
	SaasTenureCustomerService saasTenureCustomerService;

	/**
	 * 发布车辆
	 * @param vo
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public BaseResult publishCar(PublishCarRequest vo, HttpServletRequest req) throws Exception {
		Acount acount = cacheService.getAcountInfoFromCache(req);
		saveCarInfo(vo, acount,req);
		return BaseResult.success();
	}

	/**
	 * 获取收车价格 By Vin
	 */
	public BigDecimal getAcqPrice(String vin){
		return productMapper.getAcqPriceByVin(vin);
	}
	/**
	 * 编辑车辆
	 * @param vo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class, readOnly = false, propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
	public BaseResult editCar(UpdateCarRequest vo, HttpServletRequest req) throws Exception {
		editCarInfo(vo,req);
		return BaseResult.success();
	}
	
	private void editCarInfo(UpdateCarRequest vo,HttpServletRequest req) throws Exception{
		Date now = new Date();
		//修改产品
		Product productModel = new Product();
		productModel.setId(vo.getId());
		productModel.setProductName(vo.getCbrand()+" "+vo.getCmodel()+" "+vo.getCtype());
		productModel.setProductDescr(vo.getProductDescr());
		productModel.setProductShortName(vo.getCbrand()+" "+vo.getCmodel()+" "+vo.getCtype());
		//销售价格
		if(!StringUtil.isEmpty(vo.getPrice())){
			productModel.setPrice(new BigDecimal(vo.getPrice()).multiply(new BigDecimal(10000)));
		}else{
			productModel.setPrice(new BigDecimal(0));
		}
		//保底价格
		if(!StringUtil.isEmpty(vo.getMiniprice())){
			productModel.setMiniprice(new BigDecimal(vo.getMiniprice()).multiply(new BigDecimal(10000)));
		}else{
			productModel.setMiniprice(new BigDecimal(0));
		}
		productModel.setUpdateTime(now);
		//修改后状态置为审核中
		short onshelfstatus = 2;
		productModel.setOnShelfStatus(onshelfstatus);
		productModel.setMaintainInfo(vo.getMaintainInfo());
		productModel.setVin(vo.getVin());
		productModel.setCloseingPrice(vo.getCloseingPrice());
		productModel.setConsignment(vo.getConsignment());
		productMapper.updateByModel(productModel);
		
		//修改产品辅助表
		PruductOldcar pruductOldcarModel = new PruductOldcar();
		pruductOldcarModel.setId(vo.getSid());
		pruductOldcarModel.setcBrand(vo.getCbrand());
		pruductOldcarModel.setcModel(vo.getCmodel());
		pruductOldcarModel.setcType(vo.getCtype());
		pruductOldcarModel.setCity(vo.getCity());
		pruductOldcarModel.setPruductId(productModel.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    if(!StringUtil.isEmpty(vo.getFirstUpTime())){
	    	 Date date = sdf.parse(vo.getFirstUpTime());
	    	 pruductOldcarModel.setFirstUpTime(date);
	    }else{
	    	String dateNowStr = sdf.format(new Date());
	    	Date date = sdf.parse(dateNowStr);
	    	pruductOldcarModel.setFirstUpTime(date);
	    }
		
	    if(vo.getMiles() != null){
	    	Integer miles = (int) (vo.getMiles()*10000);
	    	pruductOldcarModel.setMiles(miles);
	    }else{
	    	pruductOldcarModel.setMiles(0);
	    }
		pruductOldcarModel.setProvince(vo.getProvince());
		pruductOldcarModel.setUpdateTime(now);
		pruductOldcarMapper.updateByPrimaryKeySelective(pruductOldcarModel);
		
		//先删除图片
		pictureMapper.deleteByPid(vo.getId());
		//插入图片表
		if(vo.getPicListURL()!=null&&vo.getPicListURL().size()!=0){
			//2.4.6开始使用此方法
			for (String url : vo.getPicListURL()) {
				Picture picture = new Picture();
				picture.setPicPath(url);
				picture.setProductId(productModel.getId());
				picture.setUpdateTime(now);
				picture.setCreateTime(now);
				picture.setType((short) 0);
				pictureMapper.insertSelective(picture);
			}
		}
		if(vo.getPicList()!=null&&vo.getPicList().size()!=0){
			//2.4.6以前使用此方法
			for (String imgStr : vo.getPicList()) {
				String imgurl = uploadService.upload(req, imgStr);
				Picture picture = new Picture();
				picture.setPicPath(imgurl);
				picture.setProductId(productModel.getId());
				picture.setUpdateTime(now);
				picture.setCreateTime(now);
				picture.setType((short) 0);
				pictureMapper.insertSelective(picture);
			}
		}

		//插入主图
		if(vo.getHeaderPicURL()!=null&&!vo.getHeaderPicURL().equals("")){
			//2.4.6开始使用此方法
			Picture headerhPic = new Picture();
			headerhPic.setPicPath(vo.getHeaderPicURL());
			headerhPic.setProductId(productModel.getId());
			headerhPic.setUpdateTime(now);
			headerhPic.setCreateTime(now);
			headerhPic.setType((short) 1);
			pictureMapper.insertSelective(headerhPic);
		}
		if(vo.getHeaderPic()!=null&&!vo.getHeaderPic().equals("")){
			//2.4.6以前使用此方法
			Picture headerhPic = new Picture();
			String imgurl = uploadService.upload(req, vo.getHeaderPic());
			headerhPic.setPicPath(imgurl);
			headerhPic.setProductId(productModel.getId());
			headerhPic.setUpdateTime(now);
			headerhPic.setCreateTime(now);
			headerhPic.setType((short) 1);
			pictureMapper.insertSelective(headerhPic);
		}

		//如果先发车后整备 tid不为空 否则不处理
		int tid = 0 ;
		if(vo.getTid()!=null){
			tid = vo.getTid();
		}
		if(tid>0){
			//修改主任务信息
			ZbTask task = new ZbTask();
			task.setTaskTime(now);
			task.setTaskStatus(5); //任务标记已完成
			task.setId(tid);
			task.setProductId(productModel.getId());
			task.setCarName(productModel.getProductName());
			taskMapper.updateByModel(task);
		}

		//添加搜索消息通知
		Notice notice = new Notice("carsok_product",vo.getId(),2);
		noticeMapper.insert(notice);

		//推送至SearchCenter
		indexSendService.SingleInsertOrUpdate(productModel.getId(),false);
	}
	

	private void saveCarInfo(PublishCarRequest vo, Acount acount,HttpServletRequest req) throws Exception{
		Date now = new Date();
		//插入产品
		Product productModel = new Product();
		productModel.setProductName(vo.getCbrand()+" "+vo.getCmodel()+" "+vo.getCtype());
		productModel.setProductNo(product_num_prefix+StringUtil.getRandCode());
		productModel.setProductDescr(vo.getProductDescr());
		productModel.setProductShortName(vo.getCbrand()+" "+vo.getCmodel()+" "+vo.getCtype());
		//销售价格
		if(!StringUtil.isEmpty(vo.getPrice())){
			productModel.setPrice(new BigDecimal(vo.getPrice()).multiply(new BigDecimal(10000)));
		}else{
			productModel.setPrice(new BigDecimal(0));
		}
		//保底价格
		if(!StringUtil.isEmpty(vo.getMiniprice())){
			productModel.setMiniprice(new BigDecimal(vo.getMiniprice()).multiply(new BigDecimal(10000)));
		}else{
			productModel.setMiniprice(new BigDecimal(0));
		}
		productModel.setAccountId(acount.getId());
		productModel.setCreateTime(now);
		productModel.setUpdateTime(now);
		productModel.setMaintainInfo(vo.getMaintainInfo());
		productModel.setVin(vo.getVin());
		productModel.setCloseingPrice(vo.getCloseingPrice());
		productModel.setConsignment(vo.getConsignment());
		int id = productMapper.insertSelective(productModel);

		//插入合同
		ContractMsg contractmsg = new ContractMsg();

		contractmsg.setContractUrl( acquisitionCarMapper.selectContract(vo.getTid()));
		if (!StringUtil.isEmpty(contractmsg.getContractUrl())) {
			StringBuffer sbf=new StringBuffer();
			sbf.append(contractmsg.getContractUrl());
			//获取合同编号号
			contractmsg.setContractNum(sbf.substring(sbf.length()-12,sbf.length()-4));
			contractmsg.setCarId(productModel.getId());
			contractmsg.setContractType(3);
			contractmsg.setEnable(1);
			contractMapper.insertPdfContract(contractmsg);
		}

		//插入产品辅助表
		PruductOldcar pruductOldcarModel = new PruductOldcar();
		pruductOldcarModel.setcBrand(vo.getCbrand());
		pruductOldcarModel.setcModel(vo.getCmodel());
		pruductOldcarModel.setcType(vo.getCtype());
		pruductOldcarModel.setCity(vo.getCity());
		pruductOldcarModel.setPruductId(productModel.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	   
	    if(!StringUtil.isEmpty(vo.getFirstUpTime())){
	    	 Date date = sdf.parse(vo.getFirstUpTime());
	    	 pruductOldcarModel.setFirstUpTime(date);
	    }else{
	    	String dateNowStr = sdf.format(new Date());
	    	Date date = sdf.parse(dateNowStr);
	    	pruductOldcarModel.setFirstUpTime(date);
	    }
		
	    if(vo.getMiles() != null){
	    	Integer miles = (int) (vo.getMiles()*10000);
	    	pruductOldcarModel.setMiles(miles);
	    }else{
	    	pruductOldcarModel.setMiles(0);
	    }
		pruductOldcarModel.setProvince(vo.getProvince());
		pruductOldcarModel.setCreateTime(now);
		pruductOldcarModel.setUpdateTime(now);
		pruductOldcarMapper.insertSelective(pruductOldcarModel);


		//插入非主图
		if(vo.getPicURLList()!=null&&vo.getPicURLList().size()!=0) {
			//2.4.6版本开始由前台直接返回七牛云URL
			for (String url : vo.getPicURLList()) {
				Picture picture = new Picture();
				picture.setPicPath(url);
				picture.setProductId(productModel.getId());
				picture.setUpdateTime(now);
				picture.setCreateTime(now);
				picture.setType((short) 0);
				pictureMapper.insertSelective(picture);
			}
		}
		if(vo.getPicList()!=null&&vo.getPicList().size()!=0){
			//2.4.6版本以前使用此方法插入图片表
			for (String imgStr : vo.getPicList()) {
				String imgurl = uploadService.upload(req, imgStr);
				Picture picture = new Picture();
				picture.setPicPath(imgurl);
				picture.setProductId(productModel.getId());
				picture.setUpdateTime(now);
				picture.setCreateTime(now);
				picture.setType((short) 0);
				pictureMapper.insertSelective(picture);
			}
		}
		//插入主图
		if(vo.getHeaderPicURL()!=null&&!vo.getHeaderPicURL().equals("")){
			//2.4.6开始使用此方法
			Picture headerhPic = new Picture();
			headerhPic.setPicPath(vo.getHeaderPicURL());
			headerhPic.setProductId(productModel.getId());
			headerhPic.setUpdateTime(now);
			headerhPic.setCreateTime(now);
			headerhPic.setType((short) 1);
			pictureMapper.insertSelective(headerhPic);
		}
		if(vo.getHeaderPic()!=null&&!vo.getHeaderPic().equals("")){
			//2.4.6以前使用此方法
			Picture headerhPic = new Picture();
			String imgurl = uploadService.upload(req, vo.getHeaderPic());
			headerhPic.setPicPath(imgurl);
			headerhPic.setProductId(productModel.getId());
			headerhPic.setUpdateTime(now);
			headerhPic.setCreateTime(now);
			headerhPic.setType((short) 1);
			pictureMapper.insertSelective(headerhPic);
		}

		//获取任务id,为空或小于零则不做任何处理
		int tid = vo.getTid();
		if(tid>0){
			//修改主任务信息
			ZbTask task = new ZbTask();
			task.setTaskTime(now);
			task.setTaskStatus(5); //任务标记已完成
			task.setId(tid);
			task.setProductId(productModel.getId());
			task.setCarName(productModel.getProductName());
			taskMapper.updateByModel(task);
		}
		int delete = messageCenterService.deleteCenter(tid,"yyzy");
		System.out.print("成功删除------------------------------------------"+delete+"条信息");
		//成功发布车商分+0.5
		Acount qacount = new Acount();
		qacount.setId(acount.getId());
		Acount selectq = acountMapper.selectQuotientScore(qacount);
		//缓存同步
		acount.setQuotientScore(selectq.getQuotientScore()+0.5);
		//修改数据库车商分
		Acount updateAcount = new Acount();
		updateAcount.setId(acount.getId());
		updateAcount.setQuotientScore(selectq.getQuotientScore()+0.5);
		acountMapper.updateQuotientScore(updateAcount);
		//缓存重新赋值
		//redisTemplate.opsForValue().set(acount.getToken(), acount, 30, TimeUnit.DAYS);

	}
	
	public int onSaleCount(HttpServletRequest req){
		Acount acount = cacheService.getAcountInfoFromCache(req);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount resAcount = acountMapper.selectByModel(acountQuery);
		
		Map prodcutQueryMap = new HashMap();
		prodcutQueryMap.put("acountId", acount.getId());
		prodcutQueryMap.put("onShelfStatus", OnSelfStatus.ON_SELF.getCode());
		prodcutQueryMap.put("saleStatus", SaleStatus.ON_SALE.getCode());
		prodcutQueryMap.put("isDel", CarDel.DEL_NO.getCode());
		List<Product> prodcutList = productMapper.selectOnsaleCount(prodcutQueryMap);
		if (null == prodcutList || prodcutList.size() <= 0) {
			return 0;
		}

		return prodcutList.size();
	}
	
	/**
	 * 按名称搜索
	 * @param req
	 * @param search
	 * @return
	 */
	public BaseResult searchList(HttpServletRequest req,Search search){
		Acount acount = cacheService.getAcountInfoFromCache(req);
		if (null == acount) {
			return BaseResult.success(LoginErrorCode.NOT_LOGIN_CODE, LoginErrorCode.NOT_LOGIN_MESSAGE);
		}
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount resAcount = acountMapper.selectByModel(acountQuery);
		 
		Map prodcutQueryMap = new HashMap();
		prodcutQueryMap.put("acountId", acount.getId());
		prodcutQueryMap.put("productName", search.getSearchKey());
		switch(search.getOnShelfStatus())
		{
			case "0"://在售
				prodcutQueryMap.put("onShelfStatus", OnSelfStatus.ON_SELF.getCode());
				prodcutQueryMap.put("saleStatus", SaleStatus.ON_SALE.getCode());
				prodcutQueryMap.put("isDel", CarDel.DEL_NO.getCode());
				break;
			case "1"://已售
				prodcutQueryMap.put("saleStatus", SaleStatus.SALED.getCode());
				prodcutQueryMap.put("isDel", CarDel.DEL_NO.getCode());
				break;
			case "2"://未上架
				prodcutQueryMap.put("onShelfStatus", OnSelfStatus.ON_NEW.getCode());
				prodcutQueryMap.put("saleStatus", SaleStatus.ON_SALE.getCode());
				prodcutQueryMap.put("isDel", CarDel.DEL_NO.getCode());
				break;
			default:
				break;
		}
//		prodcutQueryMap.put("category",search.getCategory());
		prodcutQueryMap.put("sort",search.getSort());
		prodcutQueryMap.put("maxPrice",search.getMaxPrice());
		prodcutQueryMap.put("minPrice",search.getMinPrice());
		//潜客购车标识符
		prodcutQueryMap.put("proCustomer",search.getProCustomer());
		List<Product> prodcutList = productMapper.selectByName(prodcutQueryMap);

		if (null == prodcutList || prodcutList.size() <= 0) {
			return BaseResult.success();
		}
		List<String> ids=new ArrayList<String>();
		for(Product product:prodcutList){
			ids.add(product.getId().toString());
		}
		List<Map<String,String>> orderNos=orderService.querByProductIdsAndType(ids,OrderTypeEnum.CAR);
		Map<String,Map<String,String>> orderMap=new HashMap();
		for(Map<String,String> map:orderNos){
			orderMap.put(map.get("productId"), map);
		}
		List<OnSaleListResponse> resList = new ArrayList<>();
		for (Product product : prodcutList) {
			OnSaleListResponse res = new OnSaleListResponse();
			String producturl = producturl_prefix+product.getProductNo()+".html";
			PruductOldcar pruductOldcarQuery = new PruductOldcar();
			pruductOldcarQuery.setPruductId(product.getId());
			PruductOldcar pruductOldcar = pruductOldcarMapper.selectByModel(pruductOldcarQuery);
			if(orderMap.containsKey(product.getId().toString())){
				res.setOrderNo(orderMap.get(product.getId().toString()).get("orderNo"));
				res.setOrderType(OrderTypeEnum.CAR.name());
				res.setPayStatus(orderMap.get(product.getId().toString()).get("payStatus"));
			}
			res.setBrowseNumTimes(pruductOldcar.getBrowseNumTimes());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			String firstUpTime = sdf.format(pruductOldcar.getFirstUpTime());
			res.setFirstUpTime(firstUpTime);
			
			res.setSaledDays(DateUtil.getDaySub(product.getOnShelfTime(), new Date()));
			
			res.setId(product.getId());
			res.setConsignment(product.getConsignment());
			res.setOnShelfTime(product.getOnShelfTime());
			//变成万为单位的价格
			if(product.getPrice().compareTo(BigDecimal.ZERO) == 1){
				BigDecimal price = product.getPrice().divide(new BigDecimal(10000));
				res.setPrice(price.toString());
			}else{
				res.setPrice(product.getPrice().toString());
			}

			if(product.getMiniprice().compareTo(BigDecimal.ZERO) == 1){
				BigDecimal price = product.getMiniprice().divide(new BigDecimal(10000));
				res.setMiniprice(price.toString());
			}else{
				res.setMiniprice(product.getMiniprice().toString());
			}
			
			res.setProductName(product.getProductName());
			res.setTelNumTimes(pruductOldcar.getTelNumTimes());
			if(pruductOldcar.getMiles()>0){
				Double miles = Double.valueOf(pruductOldcar.getMiles().toString())/10000;
				res.setMiles(miles.toString());
			}else{
				res.setMiles(pruductOldcar.getMiles().toString());
			}
			
			Picture pictureQuery = new Picture();
			pictureQuery.setProductId(product.getId());
			short pictype = 1;
			pictureQuery.setType(pictype); //只查询主图
			List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
			List<String> pictureListStr = new ArrayList<>();
			for (Picture picture : pictureList) {
				pictureListStr.add(picture.getPicPath());
			}
			res.setPrimaryPicUrl(pictureListStr); 
			List<String> sharepics = new ArrayList<>();
			pictureQuery.setType(null);
			List<Picture> pictureLists = pictureMapper.selectByModel(pictureQuery);
			for (Picture picture : pictureLists) {
				sharepics.add(picture.getPicPath());
			}
			res.setSharePics(sharepics);
			if(acount.getSubPhone()!=null && !"".equals(acount.getSubPhone())){
				res.setMobile(acount.getSubPhone());
			}else{
				res.setMobile(resAcount.getAccount());
			}
			res.setMerchantName(resAcount.getMerchantName());
			res.setProducturl(producturl);
			res.setDesc(product.getProductDescr());
			res.setVin(product.getVin());
			res.setStatus(product.getOnShelfStatus());
			Calendar calendarStart = Calendar.getInstance();
			Calendar calendarEnd = Calendar.getInstance();
			calendarStart.setTime(new Date());
			calendarEnd.setTime(new Date());
			Date firstUpTimeCompare = new Date();
			try {
				firstUpTimeCompare = sdf.parse(firstUpTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if("1".equals(search.getVehicleAge())){
				calendarEnd.add(Calendar.YEAR,-1);
				if(calendarEnd.getTimeInMillis() < firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("2".equals(search.getVehicleAge())){
				calendarStart.add(Calendar.YEAR,-3);
				calendarEnd.add(Calendar.YEAR,-1);
				if(calendarStart.getTimeInMillis() <= firstUpTimeCompare.getTime()
						&& calendarEnd.getTimeInMillis() >= firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("3".equals(search.getVehicleAge())){
				calendarStart.add(Calendar.YEAR,-5);
				calendarEnd.add(Calendar.YEAR,-3);
				if(calendarStart.getTimeInMillis() <= firstUpTimeCompare.getTime()
						&& calendarEnd.getTimeInMillis() >= firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("4".equals(search.getVehicleAge())){
				calendarEnd.add(Calendar.YEAR,-5);
				if(calendarEnd.getTimeInMillis() > firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else{
				resList.add(res);
			}
		}
		Map map=new HashMap();
		map.put("resList", resList);
        //如果是潜客购车页逻辑
        if ("yes".equals(search.getProCustomer())) {
            map.put("countInfo",productMapper.selectCountByStatusForQianke(prodcutQueryMap));

        }else {
            map.put("countInfo",productMapper.selectCountByStatus(prodcutQueryMap));
        }
		return BaseResult.success(map);
	}
	
	/**
	 * 按名称搜索合同
	 * @param req
	 * @param productName
	 * @return
	 */
	public BaseResult searchContract(HttpServletRequest req,String productName,String onShelfStatus){
		Acount acount = cacheService.getAcountInfoFromCache(req);
		if (null == acount) {
			return BaseResult.success(LoginErrorCode.NOT_LOGIN_CODE, LoginErrorCode.NOT_LOGIN_MESSAGE);
		}
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount resAcount = acountMapper.selectByModel(acountQuery);
		
		Map prodcutQueryMap = new HashMap();
		prodcutQueryMap.put("acountId", acount.getId());
		prodcutQueryMap.put("productName", productName);
		prodcutQueryMap.put("onShelfStatus",onShelfStatus);
		List<Product> prodcutList = productMapper.selectByName(prodcutQueryMap);
		if (null == prodcutList || prodcutList.size() <= 0) {
			return BaseResult.success();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		int[] ids = new int [prodcutList.size()];
		for(int i = 0;i<prodcutList.size();i++){
			ids[i] = prodcutList.get(i).getId();
		}
		params.put("ids", ids);
		List<Contract> list = contractMapper.queryByCar(params);
		return BaseResult.success(list);
	}
	/**
	 * 在售列表
	 * @param req
	 * @param page
	 * @return
	 */
	public BaseResult onSaleList(HttpServletRequest req, Page page) {
		Acount acount = cacheService.getAcountInfoFromCache(req);
		if (null == acount) {
			return BaseResult.success(LoginErrorCode.NOT_LOGIN_CODE, LoginErrorCode.NOT_LOGIN_MESSAGE);
		}
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount resAcount = acountMapper.selectByModel(acountQuery);

		Map prodcutQueryMap = new HashMap();



		prodcutQueryMap.put("acountId", acount.getId());
		prodcutQueryMap.put("onShelfStatus", OnSelfStatus.ON_SELF.getCode());
		prodcutQueryMap.put("saleStatus", SaleStatus.ON_SALE.getCode());
		prodcutQueryMap.put("isDel", CarDel.DEL_NO.getCode());
		if (null != page) {
			prodcutQueryMap.put("curPage", page.getCurPage() * page.getPageSize());
			prodcutQueryMap.put("pageSize", page.getPageSize());
		}
		prodcutQueryMap.put("category",page.getCategory());
		prodcutQueryMap.put("sort",page.getSort());
		prodcutQueryMap.put("maxPrice",page.getMaxPrice());
		prodcutQueryMap.put("minPrice",page.getMinPrice());

		List<Product> prodcutList = productMapper.selectByPage(prodcutQueryMap);
		if (null == prodcutList || prodcutList.size() <= 0) {
			return BaseResult.success();
		}
		
		List<OnSaleListResponse> resList = new ArrayList<>();
		for (Product product : prodcutList) {
			OnSaleListResponse res = new OnSaleListResponse();
			String mobile=StringUtil.isEmpty(acount.getSubPhone())?acount.getAccount():acount.getSubPhone();
			String producturl = producturl_prefix+product.getProductNo()+".html?phoneNumber="+mobile;
			PruductOldcar pruductOldcarQuery = new PruductOldcar();
			pruductOldcarQuery.setPruductId(product.getId());
			PruductOldcar pruductOldcar = pruductOldcarMapper.selectByModel(pruductOldcarQuery);

//			res.setBrowseNumTimes(pruductOldcar.getBrowseNumTimes()+pruductOldcar.getBrowseNumTimesFake());
			res.setBrowseNumTimes(pruductOldcar.getBrowseNumTimes());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String firstUpTime = sdf.format(pruductOldcar.getFirstUpTime());
			res.setFirstUpTime(firstUpTime);
			
			res.setSaledDays(DateUtil.getDaySub(product.getOnShelfTime(), new Date()));
			res.setOnShelfTime(product.getOnShelfTime());
			res.setId(product.getId());
			res.setConsignment(product.getConsignment());
			//变成万为单位的价格
			if(product.getPrice().compareTo(BigDecimal.ZERO) == 1){
				BigDecimal price = product.getPrice().divide(new BigDecimal(10000));
				res.setPrice(price.toString());
			}else{
				res.setPrice(product.getPrice().toString());
			}

			if(product.getMiniprice().compareTo(BigDecimal.ZERO) == 1){
				BigDecimal price = product.getMiniprice().divide(new BigDecimal(10000));
				res.setMiniprice(price.toString());
			}else{
				res.setMiniprice(product.getMiniprice().toString());
			}

			res.setProductName(product.getProductName());
			res.setTelNumTimes(pruductOldcar.getTelNumTimes());
			if(pruductOldcar.getMiles()>0){
				Double miles = Double.valueOf(pruductOldcar.getMiles().toString())/10000;
				res.setMiles(miles.toString());
			}else{
				res.setMiles(pruductOldcar.getMiles().toString());
			}
			Picture pictureQuery = new Picture();
			pictureQuery.setProductId(product.getId());
			short pictype = 1;
			pictureQuery.setType(pictype); //只查询主图
			List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
			List<String> pictureListStr = new ArrayList<>();
			for (Picture picture : pictureList) {
				pictureListStr.add(picture.getPicPath());
			}
			res.setPrimaryPicUrl(pictureListStr); 
			List<String> sharepics = new ArrayList<>();
			pictureQuery.setType(null);
			List<Picture> pictureLists = pictureMapper.selectByModel(pictureQuery);
			for (Picture picture : pictureLists) {
				sharepics.add(picture.getPicPath());
			}
			res.setSharePics(sharepics);
			if(acount.getSubPhone()!=null && !"".equals(acount.getSubPhone())){
				res.setMobile(acount.getSubPhone());
			}else{
				res.setMobile(resAcount.getAccount());
			}
			res.setMerchantName(resAcount.getMerchantName());
			res.setProducturl(producturl);
			res.setDesc(product.getProductDescr());
			res.setVin(product.getVin());
			Calendar calendarStart = new GregorianCalendar();
			Calendar calendarEnd = new GregorianCalendar();
			calendarStart.setTime(new Date());
			calendarEnd.setTime(new Date());
			Date firstUpTimeCompare = new Date();
			try {
				firstUpTimeCompare = sdf.parse(firstUpTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if("1".equals(page.getVehicleAge())){
				calendarEnd.add(Calendar.YEAR,-1);
				if(calendarEnd.getTimeInMillis() < firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("2".equals(page.getVehicleAge())){
				calendarStart.add(Calendar.YEAR,-3);
				calendarEnd.add(Calendar.YEAR,-1);
				if(calendarStart.getTimeInMillis() <= firstUpTimeCompare.getTime()
						&& calendarEnd.getTimeInMillis() >= firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("3".equals(page.getVehicleAge())){
				calendarStart.add(Calendar.YEAR,-5);
				calendarEnd.add(Calendar.YEAR,-3);
				if(calendarStart.getTimeInMillis() <= firstUpTimeCompare.getTime()
						&& calendarEnd.getTimeInMillis() >= firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("4".equals(page.getVehicleAge())){
				calendarEnd.add(Calendar.YEAR,-5);
				if(calendarEnd.getTimeInMillis() > firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else{
				resList.add(res);
			}
		}
		return BaseResult.success(resList);
	}

	/**
	 * 已售列表
	 * @param req
	 * @param page
	 * @return
	 */
	public BaseResult saledList(HttpServletRequest req, Page page) {
		Acount acount = cacheService.getAcountInfoFromCache(req);
		Map prodcutQueryMap = new HashMap();
		prodcutQueryMap.put("acountId", acount.getId());
		prodcutQueryMap.put("saleStatus", SaleStatus.SALED.getCode());
		prodcutQueryMap.put("isDel", CarDel.DEL_NO.getCode());
		if (null != page) {
			prodcutQueryMap.put("curPage", page.getCurPage() * page.getPageSize());
			prodcutQueryMap.put("pageSize", page.getPageSize());
		}
		prodcutQueryMap.put("category",page.getCategory());
		prodcutQueryMap.put("sort",page.getSort());
		prodcutQueryMap.put("maxPrice",page.getMaxPrice());
		prodcutQueryMap.put("minPrice",page.getMinPrice());

		//设置标识符，判断是否从潜客调用的在售列表
		prodcutQueryMap.put("proCustomer",page.getProCustomer());
		
		List<Product> prodcutList = productMapper.selectByPage(prodcutQueryMap);
		if (null == prodcutList || prodcutList.size() <= 0) {
			return BaseResult.success();
		}
		List<String> ids=new ArrayList<String>();
		/**
		 * 2017-06-21 pos已售列表新增
		 */
		for(Product product:prodcutList){
			ids.add(product.getId().toString());
		}
		List<Map<String,String>> orderNos=orderService.querByProductIdsAndType(ids,OrderTypeEnum.CAR);
		Map<String,Map<String,String>> orderMap=new HashMap();
		for(Map<String,String> map:orderNos){
			orderMap.put(map.get("productId"), map);
		}
		List<SaledListResponse> resList = new ArrayList<>();
		for (Product product : prodcutList) {
			SaledListResponse res = new SaledListResponse();
			if(orderMap.containsKey(product.getId().toString())){
				res.setOrderNo(orderMap.get(product.getId().toString()).get("orderNo"));
				res.setOrderType(OrderTypeEnum.CAR.name());
				res.setPayStatus(orderMap.get(product.getId().toString()).get("payStatus"));
			}
			/**
			 * 新增END------
			 */
			PruductOldcar pruductOldcarQuery = new PruductOldcar();
			pruductOldcarQuery.setPruductId(product.getId());
			PruductOldcar pruductOldcar = pruductOldcarMapper.selectByModel(pruductOldcarQuery);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			res.setFirstUpTime(sdf.format(pruductOldcar.getFirstUpTime()));
			res.setId(product.getId());
			res.setOnShelfTime(product.getOnShelfTime());
			res.setVin(product.getVin());
			res.setConsignment(product.getConsignment());
			//变成万为单位的价格
			if(product.getPrice().compareTo(BigDecimal.ZERO) == 1){
				BigDecimal price = product.getPrice().divide(new BigDecimal(10000));
				res.setPrice(price.toString());
			}else{
				res.setPrice(product.getPrice().toString());
			}
			if(product.getMiniprice().compareTo(BigDecimal.ZERO) == 1){
				BigDecimal price = product.getMiniprice().divide(new BigDecimal(10000));
				res.setMiniprice(price.toString());
			}else{
				res.setMiniprice(product.getMiniprice().toString());
			}
			
			res.setProductName(product.getProductName());
			if(pruductOldcar.getMiles()>0){
				Double miles = Double.valueOf(pruductOldcar.getMiles().toString())/10000;
				res.setMiles(miles.toString());
			}else{
				res.setMiles(pruductOldcar.getMiles().toString());
			}
			Picture pictureQuery = new Picture();
			pictureQuery.setProductId(product.getId());
			short pictype = 1;
			pictureQuery.setType(pictype); //只查询主图
			List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
			List<String> pictureListStr = new ArrayList<>();
			for (Picture picture : pictureList) {
				pictureListStr.add(picture.getPicPath());
			}
			res.setPrimaryPicUrl(pictureListStr);
			Calendar calendarStart = new GregorianCalendar();
			Calendar calendarEnd = new GregorianCalendar();
			calendarStart.setTime(new Date());
			calendarEnd.setTime(new Date());
			Date firstUpTimeCompare = new Date();
			String firstUpTime = sdf.format(pruductOldcar.getFirstUpTime());
			try {
				firstUpTimeCompare = sdf.parse(firstUpTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if("1".equals(page.getVehicleAge())){
				calendarEnd.add(Calendar.YEAR,-1);
				if(calendarEnd.getTimeInMillis() < firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("2".equals(page.getVehicleAge())){
				calendarStart.add(Calendar.YEAR,-3);
				calendarEnd.add(Calendar.YEAR,-1);
				if(calendarStart.getTimeInMillis() <= firstUpTimeCompare.getTime()
						&& calendarEnd.getTimeInMillis() >= firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("3".equals(page.getVehicleAge())){
				calendarStart.add(Calendar.YEAR,-5);
				calendarEnd.add(Calendar.YEAR,-3);
				if(calendarStart.getTimeInMillis() <= firstUpTimeCompare.getTime()
						&& calendarEnd.getTimeInMillis() >= firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("4".equals(page.getVehicleAge())){
				calendarEnd.add(Calendar.YEAR,-5);
				if(calendarEnd.getTimeInMillis() > firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else{
				resList.add(res);
			}
		}
		return BaseResult.success(resList);
	
	}

	/**
	 * 未上架列表
	 * 
	 * @param req
	 * @param page
	 * @return
	 */
	public BaseResult offShelfList(HttpServletRequest req, Page page) {
		Acount acount = cacheService.getAcountInfoFromCache(req);
		Map prodcutQueryMap = new HashMap();
		prodcutQueryMap.put("acountId", acount.getId());
		prodcutQueryMap.put("onShelfStatus", OnSelfStatus.ON_NEW.getCode());
		prodcutQueryMap.put("saleStatus", SaleStatus.ON_SALE.getCode());
		prodcutQueryMap.put("isDel", CarDel.DEL_NO.getCode());
		if (null != page) {
			prodcutQueryMap.put("curPage", page.getCurPage() * page.getPageSize());
			prodcutQueryMap.put("pageSize", page.getPageSize());
		}
		prodcutQueryMap.put("category",page.getCategory());
		prodcutQueryMap.put("sort",page.getSort());
		prodcutQueryMap.put("maxPrice",page.getMaxPrice());
		prodcutQueryMap.put("minPrice",page.getMinPrice());
		
		List<Product> prodcutList = productMapper.selectByPage(prodcutQueryMap);
		if (null == prodcutList || prodcutList.size() <= 0) {
			return BaseResult.success();
		}
		
		List<OffShelfListResponse> resList = new ArrayList<>();
		for (Product product : prodcutList) {
			OffShelfListResponse res = new OffShelfListResponse();
			
			PruductOldcar pruductOldcarQuery = new PruductOldcar();
			pruductOldcarQuery.setPruductId(product.getId());
			PruductOldcar pruductOldcar = pruductOldcarMapper.selectByModel(pruductOldcarQuery);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			res.setFirstUpTime(sdf.format(pruductOldcar.getFirstUpTime()));
			res.setId(product.getId());
			res.setOnShelfTime(product.getOnShelfTime());
			//变成万为单位的价格
			if(product.getPrice().compareTo(BigDecimal.ZERO) == 1){
				BigDecimal price = product.getPrice().divide(new BigDecimal(10000));
				res.setPrice(price.toString());
			}else{
				res.setPrice(product.getPrice().toString());
			}
			if(product.getMiniprice().compareTo(BigDecimal.ZERO) == 1){
				BigDecimal price = product.getMiniprice().divide(new BigDecimal(10000));
				res.setMiniprice(price.toString());
			}else{
				res.setMiniprice(product.getMiniprice().toString());
			}

			res.setProductName(product.getProductName());
			res.setStatus(product.getOnShelfStatus());
			if(pruductOldcar.getMiles()>0){
				Double miles = Double.valueOf(pruductOldcar.getMiles().toString())/10000;
				res.setMiles(miles.toString());
			}else{
				res.setMiles(pruductOldcar.getMiles().toString());
			}
			Picture pictureQuery = new Picture();
			pictureQuery.setProductId(product.getId());
			short pictype = 1;
			pictureQuery.setType(pictype); //只查询主图
			List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
			List<String> pictureListStr = new ArrayList<>();
			for (Picture picture : pictureList) {
				pictureListStr.add(picture.getPicPath());
			}
			res.setPrimaryPicUrl(pictureListStr); 
			res.setVin(product.getVin());
			res.setConsignment(product.getConsignment());
			String firstUpTime = sdf.format(pruductOldcar.getFirstUpTime());
			Calendar calendarStart = new GregorianCalendar();
			Calendar calendarEnd = new GregorianCalendar();
			calendarStart.setTime(new Date());
			calendarEnd.setTime(new Date());
			Date firstUpTimeCompare = new Date();
			try {
				firstUpTimeCompare = sdf.parse(firstUpTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if("1".equals(page.getVehicleAge())){
				calendarEnd.add(Calendar.YEAR,-1);
				if(calendarEnd.getTimeInMillis() < firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("2".equals(page.getVehicleAge())){
				calendarStart.add(Calendar.YEAR,-3);
				calendarEnd.add(Calendar.YEAR,-1);
				if(calendarStart.getTimeInMillis() <= firstUpTimeCompare.getTime()
						&& calendarEnd.getTimeInMillis() >= firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("3".equals(page.getVehicleAge())){
				calendarStart.add(Calendar.YEAR,-5);
				calendarEnd.add(Calendar.YEAR,-3);
				if(calendarStart.getTimeInMillis() <= firstUpTimeCompare.getTime()
						&& calendarEnd.getTimeInMillis() >= firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else if("4".equals(page.getVehicleAge())){
				calendarEnd.add(Calendar.YEAR,-5);
				if(calendarEnd.getTimeInMillis() > firstUpTimeCompare.getTime()){
					resList.add(res);
				}
			}else{
				resList.add(res);
			}
		}
		
		return BaseResult.success(resList);
	
	}
	
	/**
	 * 在售商品下架
	 * @param vo
	 * @param request
	 * @return
	 */
	public BaseResult offShelf(OffShelfRequest vo, HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Product record = new Product();
		record.setAccountId(acount.getId());
		record.setId(vo.getId());
		record.setOnShelfStatus(OnSelfStatus.OFF_SELF.getCode().shortValue());
		record.setOffShelfTime(new Date());
		productMapper.updateByModel(record);
		//添加搜索消息通知
		Notice notice = new Notice("carsok_product",vo.getId(),2);
		noticeMapper.insert(notice);
		//推送至SearchCenter
		indexSendService.SingleInsertOrUpdate(record.getId(),false);

		return BaseResult.success();
	}

	public BaseResult selectChild(Acount vo,HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
//		Acount acount1 = new Acount();
//		acount1.setAccount(vo.getAccount());
//		Acount acounts = acountMapper.selectByModel(acount1);
		Acount acount2 = new Acount();
		acount2.setAccount(acount.getAccount());
		acount2.setMerchantName(acount.getMerchantName());
		List<ChildAccount> list = childAccountMapper.selectAllChild(acount.getAccount());
		BaseResult baseResult = BaseResult.success();
		Map<String,Object> map = new HashedMap();
		map.put("childList",list);
		map.put("accountObject",acount2);
		baseResult.setData(map);
		return baseResult;
	}

	/**
	 * 商品为已经售出时增加销售人等信息
	 */
	public BaseResult addsaledMessage(Product vo,HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Product record = new Product();
		record.setId(vo.getId());
		record.setSaledPeople(vo.getSaledPeople());
		record.setSaledPrice(vo.getSaledPrice());
		record.setSaledType(vo.getSaledType());
		record.setBusinessIf(vo.getBusinessIf());

		if(productMapper.updateByModel(record)>0){
			ChildAccount ca = new ChildAccount();
			ca.setChildAccountMobile(vo.getSaledPeople());
			ca.setAccountPhone(acount.getAccount());
			ChildAccount ccc = childAccountMapper.selectByModel(ca);
			Product pp = productMapper.selectByModel(record);
			int num = carsokCustomerTenureCarMapper.selectCount(new EntityWrapper<CarsokCustomerTenureCar>().eq("product_id", pp.getId()));
			if (num == 0){
				PruductOldcar pruductOldcar = new PruductOldcar();
				pruductOldcar.setPruductId(vo.getId());
				PruductOldcar po = pruductOldcarMapper.selectByModel(pruductOldcar);
				int acountId = acount.getId();
				int childId = 0;
				if(ccc != null){
					childId = ccc.getId();
				}
				CarsokCustomer customer = new CarsokCustomer();
				customer.setInTime(new Date());
				customer.setLevel("D 已成交");
				customer.setAccountId(acountId);
				customer.setChildId(childId);
				customer.setCreateTime(new Date());
				customer.setUpdateTime(new Date());
				carsokCustomerMapper.insert(customer);
				int customerId = customer.getId();
				CarsokCustomerTenureCar car = new CarsokCustomerTenureCar();
				car.setTenureCarname(pp.getProductName());
				car.setSaleTime(new Date());
				car.setBrand(pp.getProductName().split(" ")[0]);
				car.setCarMiles(po.getMiles().toString());
				if (vo.getBusinessIf() == 1){
					car.setIsBussiness("是");
				}else{
					car.setIsBussiness("否");
				}
				car.setSalePeople(vo.getSaledPeople());
				car.setTenureCarprice(vo.getSaledPrice());
				if(vo.getSaledType() == 1){
					car.setTenureCartype("全款");
				}else if(vo.getSaledType() == 2){
					car.setTenureCartype("按揭");
				}
				car.setTenureVin(pp.getVin());
				car.setProductId(pp.getId());
				car.setCustomerId(customerId);
				car.setRemark(pp.getProductDescr());
				car.setIsNewRecord(0);
				car.setIsDone(0);
				car.setEnable(1);
				car.setAccountId(acountId);
				car.setChildId(childId);
				car.setCreateTime(new Date());
				car.setUpdateTime(new Date());
				carsokCustomerTenureCarMapper.insert(car);
				saasTenureCustomerService.runTenureCarTask(car, null);
				int baoyouId = car.getId();
				pushSaledMsg(vo.getSaledPeople(),baoyouId);
			}

//			CustomerCar customerCar = new CustomerCar();
//			customerCar.setTenureCarname(pp.getProductName());
//			customerCar.setTenureVin(pp.getVin());
//			customerCar.setTenureCartype(vo.getSaledType());
//			customerCar.setTenureCarprice(vo.getSaledPrice());
//			customerCar.setProductId(pp.getId());
//			customerCar.setCreateTime(new Date());
//			customerCar.setUpdateTime(new Date());
//			customerCar.setEnable(1);
//			customerCar.setSalePeople(vo.getSaledPeople());
//			customerCar.setCarMales(po.getMiles().toString());
//			customerCar.setSaleTime(new Date());
//			customerCar.setAccountId(acount.getId());
//			customerCar.setChildId(childId);
//			customerCar.setBrand(pp.getProductName().split(" ")[0]);
//			int num = tenureCustomerMapper.selectByProductId(pp.getId());
//			if (num == 0){
//				tenureCustomerMapper.insertTenureCar(customerCar);
//				int baoyouId = customerCar.getId();
//				pushSaledMsg(vo.getSaledPeople(),baoyouId);
//			}
			return BaseResult.success();
		}else {
			return BaseResult.fail("009","修改已售信息失败");
		}

	}

	public void pushSaledMsg(String mobile,int baoyouId){
		Acount acount = acountMapper.selectByMobile(mobile);
		String content = "您有一辆车已经售出，自动添加到保有客户管理中，点击查看详情";
		if(acount == null){
			ChildAccount childAccount = new ChildAccount();
			childAccount.setChildAccountMobile(mobile);
			ChildAccount child = childAccountMapper.selectByModel(childAccount);
			if (child != null){
				messageCenterService.addMsg(content,content,mobile,"taskTenure",0,baoyouId,0);
				messageCenterService.addMsg(content,content,child.getAccountPhone(),"taskTenure",0,baoyouId,0);
				BaseEvent event = new BaseEvent();
				event.setData(mobile);
				event.setEventName(EventConstants.PUSH_TENURE_MESSAGE_EVENT);
				eventBus.publish(event);
			}
		}else {
			messageCenterService.addMsg(content,content,mobile,"taskTenure",0,baoyouId,0);
		}
	}

	/**
	 * 在售商品售出
	 * @param vo
	 * @param request
	 * @return
	 */
	public BaseResult saled(SaledRequest vo, HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Product record = new Product();
		record.setAccountId(acount.getId());
		record.setId(vo.getId());
		record.setSaleStatus(SaleStatus.SALED.getCode().shortValue());
		record.setUpdateTime(new Date());
		productMapper.updateByModel(record);
		//添加搜索消息通知
		Notice notice = new Notice("carsok_product",vo.getId(),2);
		noticeMapper.insert(notice);
		//推送至SearchCenter
		indexSendService.SingleInsertOrUpdate(record.getId(),false);

		return BaseResult.success();
	}

	/**
	 * 商品删除
	 * @param vo
	 * @param request
	 * @return
	 */
	public BaseResult prodcutDel(ProdcutDelRequest vo, HttpServletRequest request) {
		if(productMapper.selectApplyCSD(vo.getId())>0){
			return BaseResult.fail("0001","车辆已申请消费贷不能删除");
		}
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Product record = new Product();
		record.setAccountId(acount.getId());
		record.setId(vo.getId());
		record.setIsDel(CarDel.DEL_YES.getCode().shortValue());
		productMapper.updateByModel(record);
		//添加搜索消息通知
		Notice notice = new Notice("carsok_product",vo.getId(),2);
		noticeMapper.insert(notice);
		//推送至SearchCenter
		indexSendService.SingleInsertOrUpdate(record.getId(),false);

		return BaseResult.success();
	}

	/**
	 * 商品上架
	 * @param vo
	 * @param request
	 * @return
	 */
	public BaseResult onShelf(OnShelfRequest vo, HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Product record = new Product();
		record.setAccountId(acount.getId());
		record.setId(vo.getId());
		record.setOnShelfStatus(OnSelfStatus.ON_CHECK.getCode().shortValue());
		record.setOnShelfTime(new Date());
		productMapper.updateByModel(record);
		//添加搜索消息通知
		Notice notice = new Notice("carsok_product",vo.getId(),0);
		noticeMapper.insert(notice);
		//推送至SearchCenter
		indexSendService.SingleInsertOrUpdate(record.getId(),false);

		return BaseResult.success();
	}

	public BaseResult refreshProduct(OnShelfRequest vo, HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Product record = new Product();
		record.setAccountId(acount.getId());
		record.setId(vo.getId());
		record.setOnShelfTime(new Date());
		productMapper.updateByModel(record);
		return BaseResult.success();
	}	
	/**
	 * 获取车辆信息
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult carDetail(HttpServletRequest request, CarDetailRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Product productQuery = new Product();
		productQuery.setId(vo.getId());
		productQuery.setAccountId(acount.getId());
		Product product = productMapper.selectByModel(productQuery);
		if (null == product) {
			BaseResult.success("-1", "信息不存在，acountid = " + acount.getAccount() + "  车辆id = " + vo.getId());
		}
		
		CarInfoResponse res = new CarInfoResponse();
		
		PruductOldcar pruductOldcarQuery = new PruductOldcar();
		pruductOldcarQuery.setPruductId(product.getId());
		PruductOldcar pruductOldcar = pruductOldcarMapper.selectByModel(pruductOldcarQuery);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		res.setFirstUpTime(sdf.format(pruductOldcar.getFirstUpTime()));
		res.setId(product.getId());
		res.setConsignment(product.getConsignment());
		if(product.getPrice().compareTo(BigDecimal.ZERO) == 1){
			BigDecimal price = product.getPrice().divide(new BigDecimal(10000));
			res.setPrice(price.toString());
		}else{
			res.setPrice(product.getPrice().toString());
		}
		if(product.getMiniprice().compareTo(BigDecimal.ZERO) == 1){
			BigDecimal price = product.getMiniprice().divide(new BigDecimal(10000));
			res.setMiniprice(price.toString());
		}else{
			res.setMiniprice(product.getMiniprice().toString());
		}
		res.setProductName(product.getProductName());
		if(pruductOldcar.getMiles()>0){
			Double miles = Double.valueOf(pruductOldcar.getMiles().toString())/10000;
			res.setMiles(miles.toString());
		}else{
			res.setMiles(pruductOldcar.getMiles().toString());
		}
		res.setProvince(pruductOldcar.getProvince());
		res.setCity(pruductOldcar.getCity());
		res.setOldCarId(pruductOldcar.getId());
		res.setCmodel(pruductOldcar.getcModel());
		res.setCtype(pruductOldcar.getcType());
		res.setCbrand(pruductOldcar.getcBrand());
		res.setProductDescr(product.getProductDescr());
		res.setBrowseNumTimes(pruductOldcar.getBrowseNumTimes()); //浏览次数
		res.setTelNumTimes(pruductOldcar.getTelNumTimes());     //电话咨询次数
		if(product.getOnShelfTime() != null){
			res.setSaledDays(DateUtil.getDaySub(product.getOnShelfTime(), new Date()));
		}else{
			res.setSaledDays(0);  //在售天数
		}
		res.setVin(product.getVin());
		res.setCloseingPrice(product.getCloseingPrice());
		Picture pictureQuery = new Picture();
		pictureQuery.setProductId(product.getId());
		List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);
		List<String> pictureListStr = new ArrayList<>();
		for (Picture picture : pictureList) {
			pictureListStr.add(picture.getPicPath());
		}
		res.setPrimaryPicUrl(pictureListStr); 
		return BaseResult.success(res);
	}
	
	public BaseResult productStatusCount(HttpServletRequest request,String productName,ProductStatusCountRequest productStatusCountRequest){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		if (null == acount) {
			return BaseResult.success(LoginErrorCode.NOT_LOGIN_CODE, LoginErrorCode.NOT_LOGIN_MESSAGE);
		}
		Map param = new HashMap();
		param.put("productName",productName);
		param.put("isDel",CarDel.DEL_NO.getCode());
		param.put("acountId",acount.getId());
		Map map =new HashedMap();

		if ("yes".equals(productStatusCountRequest .getProCustomer())) {
			map=productMapper.selectCountByStatusForQianke(param);
		}else {
			 map=productMapper.selectCountByStatus(param);
		}
		return BaseResult.success(map);

	}

	public BaseResult queryPublishCountDay(HttpServletRequest request){
		Acount account = cacheService.getAcountInfoFromCache(request);
		BaseResult baseResult = BaseResult.success();
		List<String> list = new ArrayList();
		List<ShareRankResponse> checkMobile = shareRecordMapper.shareRankList(account.getMobile());
		list = queryParentAndChildId(checkMobile,account);
		// 上架
		List<Map> putAway = productMapper.queryPutAwayCountByDay(list);
		// 已售
		List<Map> sold = productMapper.querySoldCountByDay(list);
		// 库存
		Integer repertory = productMapper.queryRepertoryCount(list);
		//时间的list 日期
		List<String> days = getDays();
		List<JSONObject> results=new ArrayList<>();
		for(String day : days){
			JSONObject object = new JSONObject();
			object.put("date",day);
			if(Integer.valueOf(0)!=putAway.size()){
				for(Map map:putAway){
					Object obj=map.get("days");
					if(!StringUtils.equals(day,(String)obj)){
						object.put("putAway",0);
					}else{
						object.put("putAway",map.get("count"));
						break;
					}
				}
			}else{
				object.put("putAway",0);
			}
			if(Integer.valueOf(0)!=sold.size()) {
				for (Map map : sold) {
					Object obj = map.get("days");
					if (!StringUtils.equals(day, (String) obj)) {
						object.put("sold", 0);
					} else {
						object.put("sold", map.get("count"));
						break;
					}
				}
			}else{
				object.put("sold", 0);
			}
			object.put("repertory",repertory);
			results.add(object);
		}
		baseResult.setData(results);
		return baseResult;
	}

	public BaseResult queryPublishCountWeek(HttpServletRequest request){
		Acount account = cacheService.getAcountInfoFromCache(request);
		BaseResult baseResult = BaseResult.success();
		List<ShareRankResponse> checkMobile = shareRecordMapper.shareRankList(account.getMobile());
		List<String> list = queryParentAndChildId(checkMobile,account);
		// 上架
		List<Map> putAway = productMapper.queryPutAwayCountByWeek(list);
		// 已售
		List<Map> sold = productMapper.querySoldCountByWeek(list);
		// 库存
		Integer repertory = productMapper.queryRepertoryCount(list);
		//时间的list 日期
		List<String> weeks = getWeek();
		List<JSONObject> results=new ArrayList<>();
		for(String week : weeks){
			JSONObject object = new JSONObject();
			object.put("date",week);
			if(Integer.valueOf(0)!=putAway.size()) {
				for (Map map : putAway) {
					Object obj = map.get("weeks");
					if (!StringUtils.equals(week, (String) obj)) {
						object.put("putAway", 0);
					} else {
						object.put("putAway", map.get("count"));
						break;
					}
				}
			}else{
				object.put("putAway", 0);
			}
			if(Integer.valueOf(0)!=sold.size()) {
				for (Map map : sold) {
					Object obj = map.get("weeks");
					if (!StringUtils.equals(week, (String) obj)) {
						object.put("sold", 0);
					} else {
						object.put("sold", map.get("count"));
						break;
					}
				}
			}else{
				object.put("sold", 0);
			}
			object.put("repertory",repertory);
			results.add(object);
		}
		baseResult.setData(results);
		return baseResult;
	}

	public BaseResult queryPublishCountMonth(HttpServletRequest request){
		Acount account = cacheService.getAcountInfoFromCache(request);
		BaseResult baseResult = BaseResult.success();
		List<ShareRankResponse> checkMobile = shareRecordMapper.shareRankList(account.getMobile());
		List<String> list = queryParentAndChildId(checkMobile,account);
		// 上架
		List<Map> putAway = productMapper.queryPutAwayCountByMonth(list);
		// 已售
		List<Map> sold = productMapper.querySoldCountByMonth(list);
		// 库存
		Integer repertory = productMapper.queryRepertoryCount(list);
		//时间的list 日期
		List<String> months = getMonth();
		List<JSONObject> results=new ArrayList<>();
		for(String month : months){
			JSONObject object = new JSONObject();
			object.put("date",month);
			if(Integer.valueOf(0)!=putAway.size()) {
				for (Map map : putAway) {
					Object obj = map.get("mon");
					if (!StringUtils.equals(month, (String) obj)) {
						object.put("putAway", 0);
					} else {
						object.put("putAway", map.get("count"));
						break;
					}
				}
			}else{
				object.put("putAway", 0);
			}
			if(Integer.valueOf(0)!=sold.size()) {
				for (Map map : sold) {
					Object obj = map.get("mon");
					if (!StringUtils.equals(month, (String) obj)) {
						object.put("sold", 0);
					} else {
						object.put("sold", map.get("count"));
						break;
					}
				}
			}else{
				object.put("sold", 0);
			}
			object.put("repertory",repertory);
			results.add(object);
		}
		baseResult.setData(results);
		return baseResult;
	}

	/**
	 * Get Child And Parent ID,AccountCode
	 * @param checkMobile
	 * @param account
	 * @return
	 */
	public List<String> queryParentAndChildId(List<ShareRankResponse> checkMobile,Acount account){
		List<String> list = new ArrayList();
		List<ChildAccount> childAccountList = new ArrayList<>();
		for(int index = 0; index < checkMobile.size(); index++){
			if(Integer.valueOf(0)==index){
				if(!"0".equals(checkMobile.get(index).getCount())){
					list.add(account.getId().toString());
					childAccountList = childAccountMapper.queryAllChildByAccountPhone(account.getMobile());
					for(int i = 0; i < childAccountList.size(); i++){
						list.add(childAccountList.get(i).getCcaId().toString());
					}
				}else{
					childAccountList =childAccountMapper.queryAllChildByChildPhone(account.getMobile());
					for(int i = 0; i < childAccountList.size(); i++){
						if(i==0){
							list.add(childAccountList.get(i).getCaId());
							list.add(childAccountList.get(i).getCcaId());
							continue;
						}
						list.add(childAccountList.get(i).getCcaId());
					}
				}
			}
		}
		return list;
	}


	/**
	 * DateTool
	 * @return Bef 7 Days List
	 */
	public List<String> getDays(){
		List<String> daysList = new ArrayList<>();
		Date date = new Date();
		String pattern = "yyyy-MM-dd";
		daysList.add(DateUtil.DateToString(date,pattern));
		for(int i=1;i<=6;i++){
			daysList.add(DateUtil.DateToString(DateUtil.addDay(date,-i),pattern));
		}
		return daysList;
	}

	/**
	 * DateTool
	 * @return Bef 4 Weeks List
	 */
	public List<String> getWeek(){
		Calendar calendar = Calendar.getInstance();
		List<String> weekList = new ArrayList<>();
		Date date = new Date();
		calendar.setTime(date);
		Integer week = calendar.get(Calendar.WEEK_OF_YEAR);
		Integer year = calendar.get(Calendar.YEAR);
		for(int i = 0; i < 4; i++){
			if(week<Integer.valueOf(10)){
				if(week == Integer.valueOf(0)){
					week = Integer.valueOf(12);
					year--;
					weekList.add(year.toString() + "-" +week.toString());
				}else{
					weekList.add(year.toString() + "-0" +week.toString());
				}
			}else{
				weekList.add(year.toString() + "-" +week.toString());
			}
			Integer.valueOf(week--);
		}
		return weekList;
	}

	/**
	 * DateTool
	 * @return Bef 12 Month List
	 */
	public List<String> getMonth(){
		List<String> monthList = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		Integer month = calendar.get(Calendar.MONTH)+1;
		Integer year = calendar.get(Calendar.YEAR);
		for(int i = 0; i < 12; i++){
			if(month<Integer.valueOf(10)){
				if(Integer.valueOf(0) == month){
					year--;
					month = Integer.valueOf(12);
					monthList.add(year.toString() + "-" +month.toString());
				}else {
					monthList.add(year.toString() + "-0" +month.toString());
				}
			}else{
				monthList.add(year.toString() + "-" +month.toString());
			}
			Integer.valueOf(month--);
		}
		return monthList;
	}
	
	public OperateResult unFreezeProduct(String productId,Integer accountId) {
		OperateResult result = new OperateResult();
		Product product = productMapper.selectByIdForUpdate(productId,accountId);
		if (product == null) {
			result.setMessage("订单不存在");
			return result;
}
		product.setSaleStatus((short)0);
		product.setUpdateTime(new Date());
		productMapper.updateByModel(product);
		result.setSuccess(true);
		return result;
	}

	public BaseResult productReserveIf(HttpServletRequest request, ProductReserveRequest vo){
		if(productMapper.updateReserve(vo.getId(),vo.getReserveIf())>0){
			return BaseResult.success();
		}else{
			return BaseResult.fail("0001","修改已定状态失败");
		}
	}

	public Product getProductByVin(Integer accountId, String vin) {
		Product product = new Product();
		try {
			product = productMapper.selectPidByVin(vin, accountId, false);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return product;
	}

	public int saledCount(Integer accountId){
		//查询已售车辆
		int count = productMapper.selectSaledCount(accountId);
		return count;
	}


	public Map<String,Object> productMsg(int accountId,String account){
		Map<String,Object> map = new HashMap<>();
		//已售台数
		int saledCount = productMapper.selectSaledCount(accountId);
		//在售台数
		int onSaleCount = productMapper.selectOnSaleCounts(accountId);
		//累计销售额
		BigDecimal saledPrice = productMapper.selectSaledPriceCount(accountId);
		map.put("saledCount",saledCount);
		map.put("onSaleCount",onSaleCount);
		map.put("saledPrice",saledPrice);
		map.put("wechatUrl",producturl_prefix+account+".html");
		return map;
	}

}
