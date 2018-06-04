package com.uton.carsokApi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import com.uton.carsokApi.controller.request.ModifyLoginAliasRequest;
import com.uton.carsokApi.dao.ChildAccountMapper;
//import com.uton.carsokApi.model.ChildAccount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.base.JisuResult;
import com.uton.carsokApi.base.PmsResult;
import com.uton.carsokApi.controller.request.StoreInfoRequest;
import com.uton.carsokApi.controller.response.StoreInfoResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.NoticeMapper;
import com.uton.carsokApi.dao.TaskMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.Notice;
import com.uton.carsokApi.model.Task;
import com.uton.carsokApi.util.HttpClientUtil;
import com.uton.carsokApi.util.StringUtil;

/**
 * 店铺信息service
 * @author bing.cheng
 *
 */
@Service
public class StoreInfoService {
	private final static Logger logger = Logger.getLogger(StoreInfoService.class);

	@Autowired 
	AcountMapper acountMapper;

	@Autowired
	ChildAccountMapper childAccountMapper;
	
	@Resource
	CacheService cacheService;
	
	@Resource
	ProductService productService;
	
	@Autowired
	NoticeMapper noticeMapper;
	
	@Autowired
	TaskMapper taskMapper;
	
	@Value("${store.url.prefix}")
	private String storeUrlPrefix;
	
	@Value("${make.storehtlm.api}")
	private String makeStorehtml;
	/**
	 * 获取店铺信息
	 * @param request
	 * @return
	 */
	public BaseResult getStoreInfo(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount resAcount = acountMapper.selectByModel(acountQuery);
		
		StoreInfoResponse res = new StoreInfoResponse();
		res.setMerchantAddress(resAcount.getMerchantAddress());
		res.setMerchantDescr(resAcount.getMerchantDescr());
		res.setMerchantImagePath(resAcount.getMerchantImagePath());
		
		res.setMerchantName(resAcount.getMerchantName());
		res.setMobile(resAcount.getAccount());
		res.setNick(resAcount.getRealName());
		
		return BaseResult.success(res);
	}

	/**
	 *  修改店铺信息
	 * @param request
	 * @param vo 
	 * @return
	 */
	public BaseResult upStoreInfo(HttpServletRequest request, StoreInfoRequest vo) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount updatAcount = new Acount(); 
		updatAcount.setId(acount.getId());
		updatAcount.setMerchantAddress(vo.getMerchantAddress());
		updatAcount.setMerchantDescr(vo.getMerchantDescr());
		updatAcount.setMerchantImagePath(vo.getMerchantImagePath());
		updatAcount.setMerchantName(vo.getMerchantName());
		updatAcount.setMobile(vo.getMobile());
		updatAcount.setNick(vo.getNick());
		updatAcount.setHeadPortraitPath(vo.getHeadportraitpath());
		updatAcount.setProvince(vo.getProvince());
		updatAcount.setCity(vo.getCity());
		updatAcount.setAccountKey(vo.getAccountKey());
		updatAcount.setBusinessLicencePath(vo.getBusinessLicencePath());
		acountMapper.updateBySelective(updatAcount);
		//添加搜索消息通知
		Notice notice = new Notice("carsok_acount",acount.getId(),0);
		noticeMapper.insert(notice);
		//生成商家静态页
		makeStoreHtml(acount.getId());
		return BaseResult.success();
	}

	/**
	 *  修改店铺信息
	 * @param request
	 * @return
	 */
	public BaseResult refreshStore(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount updatAcount = new Acount(); 
		updatAcount.setId(acount.getId());
		updatAcount.setUpdateTime(new Date());
		acountMapper.updateBySelective(updatAcount);
		return BaseResult.success();
	}
	
	public BaseResult getStoreUrl(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount resAcount = acountMapper.selectByModel(acountQuery);
		String url = storeUrlPrefix+resAcount.getAccountCode()+".html";
		String title = null;
		String context = null;
		String info = null;
		int count = productService.onSaleCount(request);
		int saledCount = productService.saledCount(acount.getId());
		if(count > 0){
			info = "向您推荐"+count+"台车"+"（累计售出"+saledCount+"台）";
		}
		else
		{
			info = "向您推荐0台车";
		}
		if(!StringUtil.isEmpty(resAcount.getMerchantName())){
			title = resAcount.getMerchantName()+info;
		}else{
			title = resAcount.getAccountCode()+"的微店"+info;
		}
		if(!StringUtil.isEmpty(resAcount.getMerchantDescr())){
			context = resAcount.getMerchantDescr();
		}else{
			context = "高端二手车行";
		}
		String iconurl = resAcount.getMerchantImagePath();
		Map<String,String> map = new HashMap<String,String>();
		map.put("url", url);
		map.put("title", title);
		map.put("context", context);
		map.put("iconurl", iconurl);
		return BaseResult.success(map);
	}
	
	/**
	 * 生成店铺详情页
	 * @param id
	 * @return
	 */
	public void makeStoreHtml(Integer id){
		String url = makeStorehtml+id;
		String result = HttpClientUtil.sendGetRequest(url, "UTF-8");
		//判断是否发送成功
		if(result!=null && !StringUtil.isEmpty(result.trim())){
			PmsResult res = (PmsResult) JSONObject.parseObject(result,PmsResult.class);
			if(!res.isSuccess()){
				//记录到task表里进行定时任务生成
				Task task = new Task();
				task.setRecordId(id);
				task.setType(1);
				task.setCreateTime(new Date());
				task.setUpdateTime(new Date());
				taskMapper.insert(task);
				logger.info("生成店铺详情页失败"+url);
			}
		}else{
			//记录到task表里进行定时任务生成
			Task task = new Task();
			task.setRecordId(id);
			task.setType(1);
			task.setCreateTime(new Date());
			task.setUpdateTime(new Date());
			taskMapper.insert(task);
			logger.info("生成店铺详情页失败"+url);
		}
	}

//	/**
//	 *  修改登录名
//	 * @param request
//	 * @param vo
//	 * @return
//	 */
//	public BaseResult modifyLoginAlias(HttpServletRequest request, ModifyLoginAliasRequest vo) {
//		BaseResult baseResult = BaseResult.success();
//		Acount acount = cacheService.getAcountInfoFromCache(request);
//		Acount exist = new Acount();
//		ChildAccount childexist = new ChildAccount();
//		if(acount.getAccount().equals(vo.getAccount()) && acount.getAccountPassword().equals(vo.getAccountPassword())){
//			//密码校验通过
//			//判断登录名是否是某一主账号
//			Acount acount1 = new Acount();
//			acount1.setAccount(vo.getLoginAlias());
//			exist = acountMapper.selectByModel(acount1);
//			if (exist != null){//是主账号
//				baseResult.setData("登录名已经存在");
//			}else{//不是主账号
//				//判断登录名是否已经被占用
//				Acount acount2 = new Acount();
//				acount2.setLoginAlias(vo.getLoginAlias());
//				exist = acountMapper.selectByModel(acount2);
//				if (exist != null){//被占用
//					baseResult.setData("登录名已经存在");
//				}else{
//					//判断登录名是否是某一子账号
//					ChildAccount childAccount = new ChildAccount();
//					childAccount.setChildAccountMobile(vo.getLoginAlias());
//					childexist = childAccountMapper.selectByModel(childAccount);
//					if (childexist != null){//被占用
//						baseResult.setData("登录名已经存在");
//					}else{//修改登录名
//						Acount updatAcount = new Acount();
//						updatAcount.setId(acount.getId());
//						updatAcount.setLoginAlias(vo.getLoginAlias());
//						updatAcount.setLoginAliasPassword(vo.getLoginAliasPassword());
//						acountMapper.updateBySelective(updatAcount);
//						baseResult.setData("登录名修改成功");
//					}
//				}
//			}
//		}else{
//			baseResult.setData("密码错误");
//		}
//		return baseResult;
//	}

	public BaseResult updateMerchantInfo(Acount acount) {
		int result = acountMapper.updateMerchantInfo(acount);
		if (result == 1){
			//生成商家静态页
			makeStoreHtml(acount.getId());
			return BaseResult.success();
		}else {
			return BaseResult.fail("0007","更新失败");
		}
	}
}
