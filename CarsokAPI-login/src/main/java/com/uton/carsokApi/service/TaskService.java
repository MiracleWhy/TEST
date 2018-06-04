package com.uton.carsokApi.service;

import java.util.Date;
import java.util.List;

import com.uton.carsokApi.controller.LoginController;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.util.RedisLock;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Component
public class TaskService {

	private final static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	AcountMapper accountMapper;

	@Autowired
	ProductMapper productMapper;

	@Autowired
	PushService pushService;

	@Autowired
	CustomerMapper customerMapper;

	@Autowired
	MessageCenterService messageCenterService;

	@Autowired
	AcquisitionCarMapper acquisitionCarMapper;

	@Autowired
	TenureCustomerMapper tenureCustomerMapper;

	@Autowired
	DailyCheckService dailyCheckService;

	@Autowired
	ActiveUserService activeUserService;

	@Resource
	private RedisTemplate redisTemplate;

	/**
	 * 每天18:00触发
	 */
	//@Scheduled(cron = "0 0 18 * * *")
	public void pushAccountData(){
		//分布式锁
		RedisLock redisLock = new RedisLock(redisTemplate, "pushAccountData", 0, 1000 * 60);
		try {
			if (redisLock.lock()) {
				System.out.println("************************定时任务执行了*******************************");
				List<Acount> accounts = accountMapper.selectAll();
				if (accounts != null && accounts.size() > 0) {
					for (Acount account : accounts) {
						int publishCount = productMapper.selectNewCount(account.getId());
						int salesCount = productMapper.selectSaleCount(account.getId());
						String content = account.getMerchantName() + "今日统计：" + "成交客户" + salesCount + "人；采购车辆" + publishCount + "辆";
						boolean df = pushService.SendCustomizedCast(account.getAccount(), content, "");
						logger.info("----------推送:推送标识: "+account.getAccount()+", 时间: "+new Date()+", 发送是否成功: "+df+" ----------");
					}
				}
				redisLock.unlock();
			}
			else
			{
				redisLock.unlock();
			}
		} catch (InterruptedException ie) {
			//do nothing
		}
	}

	/**
	 * 每天8点触发
	 */
	//@Scheduled(cron = "0 0 8 * * *")
	public void pushCustomerRemind(){
		//分布式锁
		RedisLock redisLock = new RedisLock(redisTemplate, "pushCustomerRemind", 0, 1000 * 60);
		try {
			if (redisLock.lock()) {
				System.out.println("************************门店接待管理定时任务执行了*******************************");
				List<StoreRemind> storeRemind = customerMapper.selectManyMsg();
				if (storeRemind != null && storeRemind.size() > 0) {
					for (StoreRemind sr : storeRemind) {
						String custPhone = sr.getCustomerPhone();
						String custName = sr.getCustomerName();
						String accountP = sr.getAccountPhone();
						String childP = sr.getChildPhone();
						int mendianId = sr.getMendianId();
						String content = "【门店接待管理】您该给名为:" + custName + ",电话为:" + custPhone + "的客户打电话了！";
//						MessageCenter mc = new MessageCenter();
//						mc.setTitle("门店接待提醒");
//						mc.setContent(content);
//						mc.setCreateTime(new Date());
//						mc.setEnable(1);
//						mc.setPushTo(accountP);
//						mc.setPushFrom("systems");
//						mc.setContentType("taskRemind");
//						mc.setPushStatus(1);
//						mc.setMendianId(mendianId);
//						int sf = messageCenterService.messageCenterAdd(mc);
//						boolean df = pushService.SendCustomizedCast(accountP, content, "Bussiness");
//						logger.info("----------门店接待提醒:接收人: "+accountP+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
						if (!StringUtil.isEmpty(childP)) {
							MessageCenter mc2 = new MessageCenter();
							mc2.setTitle("门店接待提醒");
							mc2.setContent(content);
							mc2.setCreateTime(new Date());
							mc2.setEnable(1);
							mc2.setPushTo(childP);
							mc2.setPushFrom("systems");
							mc2.setContentType("taskRemind");
							mc2.setPushStatus(1);
							mc2.setMendianId(mendianId);
							int sf2 = messageCenterService.messageCenterAdd(mc2);
							boolean df2 = pushService.SendCustomizedCast(childP, content, "Bussiness");
							logger.info("----------门店接待提醒:接收人: "+childP+", 时间: "+new Date()+", 数据插入是否成功: "+sf2+", 发送是否成功: "+df2+" ----------");
						}
					}
				}
				redisLock.unlock();
			}
		} catch (InterruptedException ie) {
			//do nothing;
		}
	}
	/**
	 * 每天9点触发
	 * 收车管理提醒
	 */
	//@Scheduled(cron = "0 0 9 * * *")
	public void pushAcquisitionCar(){
		//分布式锁
		RedisLock redisLock = new RedisLock(redisTemplate, "pushAcquisitionCar", 0, 1000 * 60);
		try {
			if (redisLock.lock()) {
				System.out.println("************************收车管理定时任务执行了*******************************");
				List<AcquisitionCar> acquisitionCars = acquisitionCarMapper.selectAliasCustName();
				for (AcquisitionCar acquisitionCar : acquisitionCars) {
					int id = acquisitionCar.getId();
					String custPhone = acquisitionCar.getContentNum();
					String alias = acquisitionCar.getAlias();
					String childPhone = acquisitionCar.getChildPhone();
					String account = acquisitionCar.getAccount();
					String content = "【收车管理】您该给客户:【" + custPhone + "】做电话邀约了！";
					MessageCenter mc = new MessageCenter();
					mc.setTitle("收车管理提醒");
					mc.setContent(content);
					mc.setCreateTime(new Date());
					mc.setEnable(1);
					mc.setPushFrom("systems");
					mc.setContentType("taskAcquisition");
					mc.setPushStatus(1);
					mc.setShoucheId(id);
					boolean df = false;
					int sf = 0 ;
					if (!StringUtil.isEmpty(acquisitionCar.getAlias())) {
						mc.setPushTo(childPhone);
						df = pushService.SendCustomizedCast(alias, content, "Bussiness");
						sf = messageCenterService.messageCenterAdd(mc);
					}
//					else {
//						mc.setPushTo(account);
//						df = pushService.SendCustomizedCast(account, content, "Bussiness");
//					}

					logger.info("----------收车管理提醒:接收人: "+mc.getPushTo()+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
				}
				redisLock.unlock();
			}
		} catch (InterruptedException ie) {
			//do nothing;
		}
	}

	/**
	 * 每天10点触发
	 * 保有3天回访提醒
	 */
	//@Scheduled(cron = "0 0 10 * * *")
	public void pushTenureThree(){
		//分布式锁
		RedisLock redisLock = new RedisLock(redisTemplate, "pushTenureThree", 0, 1000 * 60);
		try {
			if (redisLock.lock()) {
				System.out.println("************************保有客户3天回访提醒定时任务执行了*******************************");
				List<TenureTask> tenureList = tenureCustomerMapper.selectTenureThree();
				for (TenureTask tenure : tenureList) {
					int id = tenure.getId();
					String alias = tenure.getAlias();
					String childPhone = tenure.getChildPhone();
					String account = tenure.getAccount();
					String custPhone = tenure.getCustPhone();
					String content = "【保有客户管理】您该给电话为:【" + custPhone + "】的客户做3天回访了！";
					MessageCenter mc2 = new MessageCenter();
					mc2.setTitle("保有客户管理待办");
					mc2.setContent(content);
					mc2.setCreateTime(new Date());
					mc2.setEnable(1);
					mc2.setPushFrom("systems");
					mc2.setContentType("taskTenureThree");
					mc2.setPushStatus(1);
					mc2.setBaoyouId(id);
					boolean df = false;
					int sf = 0;
					if (!StringUtil.isEmpty(tenure.getAlias())) {
						mc2.setPushTo(childPhone);
						df = pushService.SendCustomizedCast(alias, content, "Bussiness");
						sf = messageCenterService.messageCenterAdd(mc2);
					}
//					else {
//						mc2.setPushTo(account);
//						df = pushService.SendCustomizedCast(account, content, "Bussiness");
//					}
					logger.info("----------保有客户管理代办:接收人: "+mc2.getPushTo()+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
				}
				redisLock.unlock();
			}
		} catch (InterruptedException ie) {
			//do nothing
		}
	}


	//@Scheduled(cron = "0 30 8 * * *")
	public void DoDailycheckDispatcher()
	{
		RedisLock redisLock = new RedisLock(redisTemplate, "DoDailycheckDispatcher", 0, 1000 * 60);
		try {
			if (redisLock.lock()) {
				dailyCheckService.DoDispatcherTask();
				redisLock.unlock();
			}
		} catch (InterruptedException ie) {
			//do nothing
		}
	}

	/**
	 * 计算月活（每月一号00:00）
	 */
	@Scheduled(cron = "0 0 0 1 * ?" )
	public void CalMonthAU()
	{
		RedisLock redisLock = new RedisLock(redisTemplate, "CalMonthAU", 0, 1000 * 60);
		try {
			if (redisLock.lock()) {
				activeUserService.persistentActiveUser("MONTH");
				redisLock.unlock();
			}
		} catch (InterruptedException ie) {
			//do nothing
		}
	}
	/**
	 * 计算周活（每周一00:00）
	 */
	@Scheduled(cron = "0 0 0 ? * MON" )
	public void CalWeekAU()
	{
		RedisLock redisLock = new RedisLock(redisTemplate, "CalWeekAU", 0, 1000 * 60);
		try {
			if (redisLock.lock()) {
				activeUserService.persistentActiveUser("WEEK");
				redisLock.unlock();
			}
		} catch (InterruptedException ie) {
			//do nothing
		}
	}

	/**
	 * 计算日活（每天00:00）
	 */
	@Scheduled(cron = "0 0 0 * * *" )
	public void CalDayAU()
	{
		RedisLock redisLock = new RedisLock(redisTemplate, "CalDayAU", 0, 1000 * 60);
		try {
			if (redisLock.lock()) {
				activeUserService.persistentActiveUser("DAY");
				redisLock.unlock();
			}
		} catch (InterruptedException ie) {
			//do nothing
		}
	}

	/**
	 * 计算日活（每天00:00）
	 */
	@Scheduled(cron = "0 0 8 * * *" )
	public void simLogin()
	{
		activeUserService.SimLogin();
	}


}
