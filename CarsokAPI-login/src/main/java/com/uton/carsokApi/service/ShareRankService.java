package com.uton.carsokApi.service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.SharingRequest;
import com.uton.carsokApi.controller.response.ShareRankResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.ShareRecordMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 分享排行service
 */
@Service("RankService")
public class ShareRankService {
	
	private final static Logger logger = Logger.getLogger(ShareRankService.class);

	@Resource
	CacheService cacheService;

	@Autowired
	AcountMapper acountMapper;

	@Autowired
	ShareRecordMapper shareRecordMapper;

	@Autowired
	ChildAccountMapper childAccountMapper;

	@Resource
	RedisTemplate redisTemplate;

	/**
	 * 查询天分享次数
	 * @param req
	 * @return
	 */
	public BaseResult shareRankingsByDays(HttpServletRequest req){
		Acount account = cacheService.getAcountInfoFromCache(req);
		BaseResult baseResult = BaseResult.success();
		List<String> list = new ArrayList();
		Map<String,Object> item = new HashMap<String,Object>();
		List<ChildAccount> childAccountList = new ArrayList<>();
		List<ShareRankResponse> checkMobile = shareRecordMapper.shareRankList(account.getMobile());
		for(int index = 0; index < checkMobile.size(); index++){
			if(Integer.valueOf(0)==index){
				if(!"0".equals(checkMobile.get(index).getCount())){
					list.add(account.getMobile());
					item.put(account.getMobile(),account);
					childAccountList = childAccountMapper.queryAllChildByAccountPhone(account.getMobile());
					for(int i = 0; i < childAccountList.size(); i++){
						list.add(childAccountList.get(i).getChildAccountMobile());
						item.put(childAccountList.get(i).getChildAccountMobile(),childAccountList.get(i));
					}
				}else{
					childAccountList =childAccountMapper.queryAllChildByChildPhone(account.getMobile());
					for(int i = 0; i < childAccountList.size(); i++){
						if(i==0){
							list.add(childAccountList.get(i).getAccountPhone());
							list.add(childAccountList.get(i).getChildAccountMobile());
							item.put(childAccountList.get(i).getAccountPhone(),acountMapper.selectByMobile(childAccountList.get(i).getAccountPhone()));
							item.put(childAccountList.get(i).getChildAccountMobile(),childAccountList.get(i));
							continue;
						}
						list.add(childAccountList.get(i).getChildAccountMobile());
						item.put(childAccountList.get(i).getChildAccountMobile(),childAccountList.get(i));
					}
				}
			}
		}
		//返回数据
		List<JSONObject> resultList = new ArrayList<>();

		Date date = new Date();
		List<String> dateList = new ArrayList();
		String pattern = "yyyy-MM-dd";
		dateList.add(DateUtil.DateToString(date,pattern));
		List<JSONObject> info_fake=new ArrayList<>();
		List<JSONObject> shareRankReturnDateList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			JSONObject map = new JSONObject();
			map.put("count","0");
			Object object = item.get(list.get(i));
			if(object instanceof Acount){
				Acount acount=(Acount)object;
				map.put("name",getName(acount));
			}else{
				ChildAccount childAccount = (ChildAccount)object;
				map.put("name",getName(childAccount));
			}
			info_fake.add(map);
		}
		for(int j = 1; j <= 7; j++){
			dateList.add(DateUtil.DateToString(DateUtil.addDay(date,-j),pattern));
			JSONObject shareRankReturnData = new JSONObject();
			shareRankReturnData.put("days",dateList.get(j-1).toString());
			shareRankReturnData.put("dayCounts","0");
			List<JSONObject> jsonObjects=new ArrayList<>();
			for(JSONObject oooo:info_fake){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("count",oooo.get("count"));
				jsonObject.put("name",oooo.get("name"));
				jsonObjects.add(jsonObject);
			}
			shareRankReturnData.put("info",jsonObjects);
			shareRankReturnDateList.add(shareRankReturnData);
		}

		//查询排行
		List<Map> result = shareRecordMapper.queryRankingCountByDay(list);
		Map<String,JSONObject> dayMap=new HashMap();

		if(result.size() != 0) {
			for (Map map : result) {
				Integer shareCount = Integer.valueOf(0);
				List<JSONObject> resultBeans = new ArrayList<>();
				String days = (String) map.get("days");
				for (String phone : list) {
					Long count = (Long) map.get(phone);
					if (count > 0) {
						shareCount++;
					}
					Object object = item.get(phone);
					JSONObject info = new JSONObject();
					info.put("count", count.toString());
					if (object instanceof Acount) {
						Acount acount = (Acount) object;
						info.put("name", getName(acount));
					} else {
						ChildAccount childAccount = (ChildAccount) object;
						info.put("name", getName(childAccount));
					}
					resultBeans.add(info);
				}
				for (int i = 0; i < resultBeans.size() - 1; i++) {
					for (int j = 0; j < resultBeans.size() - i - 1; j++) {
						if (resultBeans.get(j).getInteger("count") < resultBeans.get(j + 1).getInteger("count")) {
							JSONObject temp = resultBeans.get(j);
							resultBeans.set(j, resultBeans.get(j + 1));
							resultBeans.set(j + 1, temp);
						}
					}
				}
				JSONObject resultBean = new JSONObject();
				resultBean.put("dayCounts", shareCount.toString());
				resultBean.put("info", resultBeans);
				resultBean.put("days", days);
				dayMap.put(days,resultBean);
			}
		}
		for(JSONObject shareRankReturnData:shareRankReturnDateList){
			JSONObject object = dayMap.get(shareRankReturnData.getString("days"));
			if(object==null){
				resultList.add(shareRankReturnData);
			}
			else {
				resultList.add(object);
			}
		}
		baseResult.setData(resultList);
		return baseResult;
	}


	/**
	 * 分享后增加车商分
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult addquotientScore(HttpServletRequest request,SharingRequest vo){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acounts = new Acount();
		acounts.setAccount(acount.getAccount());
		Acount select = acountMapper.selectByModel(acounts);
		//更新缓存的车商分
		acount.setQuotientScore(select.getQuotientScore()+0.5*(vo.getShareTimes()));
		//数据库修改车商分
		Acount updateAcount = new Acount();
		updateAcount.setId(acount.getId());
		updateAcount.setQuotientScore(select.getQuotientScore()+0.5*(vo.getShareTimes()));
		acountMapper.updateQuotientScore(updateAcount);
		//缓存重新赋值
		//redisTemplate.opsForValue().set(acount.getToken(), acount, 30, TimeUnit.DAYS);
		logger.info("帐号为："+acount.getAccount()+"的车商分为："+acount.getQuotientScore()+"(缓存),"+select.getQuotientScore()+"(DB)");
		BaseResult baseResult = BaseResult.success();
		return baseResult;
	}

	/**
	 * 查询周分享次数
	 * @param req
	 * @return
	 */
	public BaseResult shareRankingsByWeek(HttpServletRequest req){
		Acount account = cacheService.getAcountInfoFromCache(req);
		BaseResult baseResult = BaseResult.success();
		List<String> list = new ArrayList();
		Map<String,Object> item = new HashMap<String,Object>();
		List<ChildAccount> childAccountList = new ArrayList<>();
		List<ShareRankResponse> checkMobile = shareRecordMapper.shareRankList(account.getMobile());
		for(int index = 0; index < checkMobile.size(); index++){
			if(Integer.valueOf(0)==index){
				if(!"0".equals(checkMobile.get(index).getCount())){
					list.add(account.getMobile());
					item.put(account.getMobile(),account);
					childAccountList = childAccountMapper.queryAllChildByAccountPhone(account.getMobile());
					for(int i = 0; i < childAccountList.size(); i++){
						list.add(childAccountList.get(i).getChildAccountMobile());
						item.put(childAccountList.get(i).getChildAccountMobile(),childAccountList.get(i));
					}
				}else{
					childAccountList =childAccountMapper.queryAllChildByChildPhone(account.getMobile());
					for(int i = 0; i < childAccountList.size(); i++){
						if(i==0){
							list.add(childAccountList.get(i).getAccountPhone());
							list.add(childAccountList.get(i).getChildAccountMobile());
							item.put(childAccountList.get(i).getAccountPhone(),acountMapper.selectByMobile(childAccountList.get(i).getAccountPhone()));
							item.put(childAccountList.get(i).getChildAccountMobile(),childAccountList.get(i));
							continue;
						}
						list.add(childAccountList.get(i).getChildAccountMobile());
						item.put(childAccountList.get(i).getChildAccountMobile(),childAccountList.get(i));
					}
				}
			}
		}
		//返回数据
		List<Object> resultList = new ArrayList<>();
		/*Integer page = Integer.valueOf(0);
		Integer pageSize = Integer.valueOf(10);
		if (null != vo) {
			page = (vo.getPage()-1) * vo.getPageSize();
			pageSize = vo.getPageSize();
		}*/
		//查询排行
		List<Map> result = shareRecordMapper.queryRankingCountByWeek(list);

		for(Map map : result){
			Integer shareCount = Integer.valueOf(0);
			List<JSONObject> resultBeans = new ArrayList<>();
			String weeks = (String)map.get("weeks");
			String year = weeks.substring(0,4);
			String month = weeks.substring(year.length());
			for(String phone : list){
				Long count=(Long)map.get(phone);
				if(count>0){
					shareCount++;
				}
				Object object = item.get(phone);
				JSONObject info = new JSONObject();
				info.put("count",count.toString());
				if(object instanceof Acount){
					Acount acount=(Acount)object;
					info.put("name",getName(acount));
				}
				else{
					ChildAccount childAccount=(ChildAccount) object;
					info.put("name",getName(childAccount));
				}
				resultBeans.add(info);
			}
			for(int i=0;i<resultBeans.size()-1;i++){
				for(int j = 0 ;j < resultBeans.size() - i - 1; j++) {
					if (resultBeans.get(j).getInteger("count") < resultBeans.get(j + 1).getInteger("count")) {
						JSONObject temp = resultBeans.get(j);
						resultBeans.set(j, resultBeans.get(j + 1));
						resultBeans.set(j + 1, temp);
					}
				}
			}
			JSONObject resultBean=new JSONObject();
			resultBean.put("dayCounts",shareCount.toString());
			resultBean.put("info",resultBeans);
			resultBean.put("week",year + " " + month);
			resultList.add(resultBean);
		}
		baseResult.setData(resultList);
		return baseResult;
	}

	/**
	 * 查询月分享次数
	 * @param req
	 * @return
	 */
	public BaseResult shareRankingsByMonth(HttpServletRequest req){
		Acount account = cacheService.getAcountInfoFromCache(req);
		BaseResult baseResult = BaseResult.success();
		List<String> list = new ArrayList();
		Map<String,Object> item = new HashMap<String,Object>();
		List<ChildAccount> childAccountList = new ArrayList<>();
		List<ShareRankResponse> checkMobile = shareRecordMapper.shareRankList(account.getMobile());
		for(int index = 0; index < checkMobile.size(); index++){
			if(Integer.valueOf(0)==index){
				if(!"0".equals(checkMobile.get(index).getCount())){
					list.add(account.getMobile());
					item.put(account.getMobile(),account);
					childAccountList = childAccountMapper.queryAllChildByAccountPhone(account.getMobile());
					for(int i = 0; i < childAccountList.size(); i++){
						list.add(childAccountList.get(i).getChildAccountMobile());
						item.put(childAccountList.get(i).getChildAccountMobile(),childAccountList.get(i));
					}
				}else{
					childAccountList =childAccountMapper.queryAllChildByChildPhone(account.getMobile());
					for(int i = 0; i < childAccountList.size(); i++){
						if(i==0){
							list.add(childAccountList.get(i).getAccountPhone());
							list.add(childAccountList.get(i).getChildAccountMobile());
							item.put(childAccountList.get(i).getAccountPhone(),acountMapper.selectByMobile(childAccountList.get(i).getAccountPhone()));
							item.put(childAccountList.get(i).getChildAccountMobile(),childAccountList.get(i));
							continue;
						}
						list.add(childAccountList.get(i).getChildAccountMobile());
						item.put(childAccountList.get(i).getChildAccountMobile(),childAccountList.get(i));
					}
				}
			}
		}
		//返回数据
		List<Object> resultList = new ArrayList<>();
		/*Integer page = Integer.valueOf(0);
		Integer pageSize = Integer.valueOf(10);
		if (null != vo) {
			page = (vo.getPage()-1) * vo.getPageSize();
			pageSize = vo.getPageSize();
		}*/
		//查询排行
		List<Map> result = shareRecordMapper.queryRankingCountByMonth(list);

		for(Map map : result){
			Integer shareCount = Integer.valueOf(0);
			List<JSONObject> resultBeans = new ArrayList<>();
			String mon = (String)map.get("mon");
			for(String phone : list){
				Long count=(Long)map.get(phone);
				if(count>0){
					shareCount++;
				}
				Object object = item.get(phone);
				JSONObject info = new JSONObject();
				info.put("count",count.toString());
				if(object instanceof Acount){
					Acount acount=(Acount)object;
					info.put("name",getName(acount));
				}
				else{
					ChildAccount childAccount=(ChildAccount) object;
					info.put("name",getName(childAccount));
				}
				resultBeans.add(info);
			}
			for(int i=0;i<resultBeans.size()-1;i++){
				for(int j = 0 ;j < resultBeans.size() - i - 1; j++) {
					if (resultBeans.get(j).getInteger("count") < resultBeans.get(j + 1).getInteger("count")) {
						JSONObject temp = resultBeans.get(j);
						resultBeans.set(j, resultBeans.get(j + 1));
						resultBeans.set(j + 1, temp);
					}
				}
			}
			JSONObject resultBean=new JSONObject();
			resultBean.put("dayCounts",shareCount.toString());
			resultBean.put("info",resultBeans);
			resultBean.put("date",mon);
			resultList.add(resultBean);
		}
		baseResult.setData(resultList);
		return baseResult;
	}

	public String getName(Object obj){
		String name="";
		if(obj instanceof  Acount){
			Acount acount=(Acount) obj;
			name=acount.getRealName();
			if(StringUtil.isEmpty(name)){
				name=acount.getMobile();
			}
			return name;
		}else{
			ChildAccount acount=(ChildAccount) obj;
			name=acount.getChildAccountName();
			if(StringUtil.isEmpty(name)){
				name=acount.getChildAccountMobile();
			}
			return name;
		}

	}
}
