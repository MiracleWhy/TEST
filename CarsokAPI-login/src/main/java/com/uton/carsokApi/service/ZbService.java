package com.uton.carsokApi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.util.StringUtil;



@Service
public class ZbService {

	private final static Logger logger = Logger.getLogger(ZbService.class);

	@Autowired
	private ZbRoleMapper zbroleMapper;

	@Autowired
	private ChildRoleMapper childRoleMapper;

	@Autowired
	AcountMapper acountMapper;

	@Autowired
	private ZbTaskMapper taskMapper;

	@Resource
	CacheService cacheService;

	@Autowired
	UploadService uploadService;

	@Resource
	PushService pushService;

	@Autowired
	MessageCenterService messageCenterService;

	@Autowired
	LoanUserService loanUserService;

	@Autowired
	ChildAccountMapper childAccountMapper;

	@Autowired
	CustomerService customerService;

	@Autowired
	MessageCenterMapper messageCenterMapper;



	public static final String UTF_8 = "UTF-8";

	public BaseResult getChildRole(HttpServletRequest request) {
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Acount acountQuery = new Acount();
		acountQuery.setId(acount.getId());
		Acount resAcount = acountMapper.selectByModel(acountQuery);
		//获取有角色的子账户列表
		List<ChildRoleVo> data = childRoleMapper.selectRoleInfo(resAcount.getAccount());
		//获取所有角色
		List<ZbRole> roledata = zbroleMapper.selectAllByType(acount.getId().toString());
		List<ChildRoleResponse> res = new ArrayList<ChildRoleResponse>();
		for(ZbRole role:roledata){
			ChildRoleResponse resp = new ChildRoleResponse();
			List<ChildRoleVo> volist = new ArrayList<ChildRoleVo>();
			resp.setRoleName(role.getName());
			resp.setRoleKey(role.getValue());
			for(ChildRoleVo vo:data){
				if(vo.getRoleName().equals(role.getValue())){
					volist.add(vo);
					resp.setData(volist);
				}
			}
			res.add(resp);
		}
		return BaseResult.success(res);
	}

	public String getAlias(String account,String roleName){
		List<ChildRoleVo> data = childRoleMapper.selectRoleInfo(account);
		String alisa = null;
		for(ChildRoleVo vo:data){
			if(vo.getRoleName().equals(roleName)){
				alisa =  vo.getAlias();
			}
		}
		return alisa;
	}
	public BaseResult saveChildRole(HttpServletRequest request,ChildRole childRole) {
		childRoleMapper.insert(childRole);
		return BaseResult.success();
	}

	public BaseResult saveChildRoleOnOrder(HttpServletRequest request,ChildRole childRole) {
		String[] childIdArr = null;
		if(!StringUtil.isEmpty(childRole.getChildList())){
			childIdArr = childRole.getChildList().trim().split(",");
		}
		if(childIdArr.length > 0){
			for (int i=0 ; i<childIdArr.length ; i++){
				ChildRole cr = new ChildRole();
				cr.setChildId(Integer.valueOf(childIdArr[i]));
				cr.setRoleName(childRole.getRoleName());
				childRoleMapper.insert(cr);
			}
		}
		return BaseResult.success();
	}

	public BaseResult removeById(HttpServletRequest request,ChildRole childRole){
		childRoleMapper.removeById(childRole.getId());
		return BaseResult.success();
	}
	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, readOnly = false, propagation = Propagation.REQUIRED)
	public BaseResult publishTask(PublishTaskRequest vo,HttpServletRequest request) throws Exception {
		createTaskInfo(vo,request);
		return BaseResult.success();
	}

	public List<String> getRoleList(String account,String roleName){
		List<ChildRoleVo> data = childRoleMapper.selectRoleInfo(account);
		List<String> list = new ArrayList<String>();
		for(ChildRoleVo vo:data){
			if(vo.getRoleName().equals(roleName)){
				list.add(vo.getChild_account_mobile());
			}
		}
		return list;
	}

	public void createTaskInfo(PublishTaskRequest vo,HttpServletRequest req) throws Exception{
		logger.info("createTaskInfo Start Time = "+new Date());
		Acount acount = cacheService.getAcountInfoFromCache(req);
		int sss = 0;
		List<String> list = getRoleList(acount.getAccount(),"pgs");
		AcquisitionCar acquisitionCar = new AcquisitionCar();
		Date now = new Date();
		//插入主任务
		ZbTask task = new ZbTask();
		task.setCarName(vo.getCarName());
		task.setCarNum(vo.getCarNum());
		task.setTaskStatus(1);
		String taskNum = taskMapper.selectSxyDh(vo.getAcquisitioncarId());
		if(null==taskNum){
			taskNum= "TK"+StringUtil.getRandCode();
		}
		task.setTaskNum(taskNum);
		task.setTaskAccount(acount.getAccount());
		task.setTaskTime(now);
		task.setVin(vo.getVin());
		taskMapper.createTask(task);

		//将task表中的Id插入到收车表中
		if(vo.getAcquisitioncarId()!=0){
			taskMapper.updateId(task.getId(),vo.getAcquisitioncarId());
			taskMapper.updateWBtaskId(task.getId(),vo.getAcquisitioncarId());
		}
		//将enable变为1
		if(vo.getAcquisitioncarId()!=0){
			taskMapper.updateEnable(vo.getAcquisitioncarId());
		}


		//插入手续员任务
		ZbTasksxy tasksxy = new ZbTasksxy();
		tasksxy.setTid(task.getId());
		String arcPath ="";
		if(vo.getArcURLPath()!=null&&!vo.getArcURLPath().equals("")){
			arcPath=vo.getArcURLPath();
		}
		if(vo.getArcPath()!=null&&!vo.getArcPath().equals("")){
			arcPath = uploadService.upload(req, vo.getArcPath());
		}
		tasksxy.setArcPath(arcPath);
		String dlPath="";
		if(vo.getDlURLPath()!=null&&!vo.getDlURLPath().equals("")){
			dlPath=vo.getDlURLPath();
		}
		if(vo.getDlPath()!=null&&!vo.getDlPath().equals("")){
			dlPath = uploadService.upload(req, vo.getDlPath());
		}
		tasksxy.setDlPath(dlPath);
		String idcardPath="";
		if(vo.getIdcardURLPath()!=null&&!vo.getIdcardURLPath().equals("")){
			idcardPath=vo.getIdcardURLPath();
		}
		if(vo.getIdcardPath()!=null&&!vo.getIdcardPath().equals("")){
			idcardPath = uploadService.upload(req, vo.getIdcardPath());
		}
		tasksxy.setIdcardPath(idcardPath);
		tasksxy.setInfoMobile(vo.getInfoMobile());
		tasksxy.setInfoName(vo.getInfoName());
		tasksxy.setInfoSource(vo.getInfoSource());
		String policyPath="";
		if(vo.getPolicyURLPath()!=null&&!vo.getPolicyURLPath().equals("")){
			policyPath=vo.getPolicyURLPath();
		}
		if(vo.getPolicyPath()!=null&&!vo.getPolicyPath().equals("")){
			policyPath = uploadService.upload(req, vo.getPolicyPath());
		}
		tasksxy.setPolicyPath(policyPath);
		tasksxy.setRemark(vo.getRemark());
		tasksxy.setKeysNum(vo.getKeysNum());
		tasksxy.setSelfName(vo.getSelfName());
		tasksxy.setSelfMobile(vo.getSelfMobile());
		tasksxy.setVin(vo.getVin());
		taskMapper.createSxyTask(tasksxy);



		for (String pgsMobile:list) {
			MessageCenter mc = new MessageCenter();
			mc.setTitle("待办任务通知");
			mc.setContent("您有一项新的待处理任务，点击查看详情");
			mc.setCreateTime(new Date());
			mc.setEnable(1);
			mc.setPushTo(pgsMobile);
			mc.setPushFrom("systems");
			mc.setContentType("taskPGS");
			mc.setPushStatus(1);
			mc.setTaskId(task.getId());
			mc.setRoleName("pgs");
			int sf = messageCenterService.messageCenterAdd(mc);
			boolean df = pushService.SendCustomizedCast(acount.getAccount()+pgsMobile, "您有一项新的待处理任务，点击查看详情","Bussiness");
			System.out.println("插入数据成功!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			logger.info("----------待办任务通知:接收人: "+pgsMobile+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
		}
//		MessageCenter mc2 = new MessageCenter();
//		mc2.setTitle("待办任务通知");
//		mc2.setContent("您有一项新的待处理任务，点击查看详情");
//		mc2.setCreateTime(new Date());
//		mc2.setEnable(1);
//		mc2.setPushTo(acount.getAccount());
//		mc2.setPushFrom("systems");
//		mc2.setContentType("taskPGS");
//		mc2.setPushStatus(1);
//		mc2.setTaskId(task.getId());
//		mc2.setRoleName("pgs");
//		int sf = messageCenterService.messageCenterAdd(mc2);
		Integer id = taskMapper.selectID(vo.getAcquisitioncarId());
		//手续员新增不用删除推送
		if(id!=null){
			int delete = messageCenterMapper.deleteCenterSXY(id,"sxy");
			System.out.print("成功删除------------------------------------------"+delete+"条信息");
		}
		//消息通知评估师
		//String alias = getAlias(acount.getAccount(),"pgs");
		//boolean df = pushService.SendCustomizedCast(acount.getAccount(),"您有一项新的待处理任务，点击查看详情","Bussiness");
		logger.info("createTaskInfo end Time = "+new Date());
		//logger.info("----------待办任务通知:接收人: "+acount.getAccount()+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
	}

	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, readOnly = false, propagation = Propagation.REQUIRED)
	public BaseResult editTaskBypgs(PgsTaskRequest vo,HttpServletRequest request) throws Exception{
		logger.info("editTaskBypgs Start Time = "+new Date());
		if(queryByTid("pgs",vo.getTid())>0){
			return BaseResult.fail("0009","数据已经保存");
		}
		editTaskBypgsInfo(vo,request);
		logger.info("editTaskBypgs end Time = "+new Date());
		return BaseResult.success();
	}

	public void editTaskBypgsInfo(PgsTaskRequest vo,HttpServletRequest request) throws Exception{
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Date now = new Date();
		//插入评估师任务
		ZbTaskpgs pgstask = new ZbTaskpgs();
		pgstask.setTid(vo.getTid());
		pgstask.setInfos(vo.getInfos());
		pgstask.setVin(vo.getVin());
		pgstask.setRemark(vo.getRemark());
		pgstask.setPicOutlook(vo.getPicOutlook());
		pgstask.setPicInside(vo.getPicInside());
		pgstask.setPicPaint(vo.getPicPaint());
		taskMapper.createPgsTask(pgstask);
		//修改主任务信息
		ZbTask task = new ZbTask();
		task.setTaskTime(now);
		task.setTaskStatus(2);
		task.setId(vo.getTid());
		taskMapper.updateByModel(task);
		int sss = 0;
		List<String> list = getRoleList(acount.getAccount(),"zby");
		for (String zbyMobile:list) {
			MessageCenter mc = new MessageCenter();
			mc.setTitle("待办任务通知");
			mc.setContent("您有一项新的待处理任务，点击查看详情");
			mc.setCreateTime(new Date());
			mc.setEnable(1);
			mc.setPushTo(zbyMobile);
			mc.setPushFrom("systems");
			mc.setContentType("taskZBY");
			mc.setPushStatus(1);
			mc.setTaskId(vo.getTid());
			mc.setRoleName("zby");
			sss = messageCenterService.messageCenterAdd(mc);
			boolean df = pushService.SendCustomizedCast(acount.getAccount()+zbyMobile,"您有一项新的待处理任务，点击查看详情","Bussiness");
			logger.info("----------待办任务通知:接收人: "+zbyMobile+", 时间: "+new Date()+", 数据插入是否成功: "+sss+", 发送是否成功: "+df+" ----------");
		}
//		MessageCenter mc2 = new MessageCenter();
//		mc2.setTitle("待办任务通知");
//		mc2.setContent("您有一项新的待处理任务，点击查看详情");
//		mc2.setCreateTime(new Date());
//		mc2.setEnable(1);
//		mc2.setPushTo(acount.getAccount());
//		mc2.setPushFrom("systems");
//		mc2.setContentType("taskZBY");
//		mc2.setPushStatus(1);
//		mc2.setTaskId(task.getId());
//		mc2.setRoleName("zby");
//		int sf = messageCenterService.messageCenterAdd(mc2);
//		System.out.println("插入数据成功!"+sss);
		int delete = messageCenterService.deleteCenter(vo.getTid(),"pgs");
		System.out.print("成功删除------------------------------------------"+delete+"条信息");
		//消息通知整备员
		//String alias = getAlias(acount.getAccount(),"zby");
		//pushService.sendIOSCustomizedcast(alias);
//		boolean df = pushService.SendCustomizedCast(acount.getAccount(),"您有一项新的待处理任务，点击查看详情","Bussiness");
//		logger.info("----------待办任务通知:接收人: "+acount.getAccount()+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
	}

	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, readOnly = false, propagation = Propagation.REQUIRED)
	public BaseResult editTaskByzby(ZbyTaskRequest vo,HttpServletRequest request) throws Exception{
		logger.info("editTaskByzby Start Time = "+new Date());
		if(queryByTid("zby",vo.getTid())>0){
			return BaseResult.fail("0009","数据已经保存");
		}
		editTaskByzbyInfo(vo,request);
		logger.info("editTaskByzby end Time = "+new Date());
		return BaseResult.success();
	}

	public void editTaskByzbyInfo(ZbyTaskRequest vo,HttpServletRequest request) throws Exception{
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Date now = new Date();
		//插入整备员任务
		ZbTaskzby zbytask = new ZbTaskzby();
		zbytask.setTid(vo.getTid());
		if(!StringUtil.isEmpty(vo.getZbMoney())){
			zbytask.setZbMoney(new BigDecimal(vo.getZbMoney()));
		}
		zbytask.setVin(vo.getVin());
		zbytask.setRemark(vo.getRemark());
		taskMapper.createZbyTask(zbytask);
		//插入整备费用明细
		List<ZbMoneyInfo> list = vo.getMinfos();
		if(null != list && list.size()>0){
			for(ZbMoneyInfo info:list){
				info.setZbyId(zbytask.getId());
				taskMapper.insertMoneyInfo(info);
			}
		}
		if(vo.getBillList()!=null && vo.getBillList().size()>0){
			TaskZbBill taskZbBill = new TaskZbBill();
			for(String bill:vo.getBillList()){
				taskZbBill.setTid(vo.getTid());
				taskZbBill.setBillPic(bill);
				taskMapper.insertBill(taskZbBill);
			}
		}
		//修改主任务信息
		ZbTask task = new ZbTask();
		task.setLastCarNum(vo.getLastCarNum());
		task.setTaskTime(now);
		task.setTaskStatus(3);
		task.setId(vo.getTid());
		taskMapper.updateByModel(task);

		List<String> lists = getRoleList(acount.getAccount(),"manager");
		for (String managerMobile:lists) {
			MessageCenter mc = new MessageCenter();
			mc.setTitle("待办任务通知");
			mc.setContent("您有一项新的待处理任务，点击查看详情");
			mc.setCreateTime(new Date());
			mc.setEnable(1);
			mc.setPushTo(managerMobile);
			mc.setPushFrom("systems");
			mc.setContentType("taskManager");
			mc.setPushStatus(1);
			mc.setTaskId(vo.getTid());
			mc.setRoleName("manager");
			int sf = messageCenterService.messageCenterAdd(mc);
			boolean df = pushService.SendCustomizedCast(acount.getAccount()+managerMobile,"您有一项新的待处理任务，点击查看详情","Bussiness");
			logger.info("----------待办任务通知:接收人: "+managerMobile+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
		}
//		MessageCenter mc2 = new MessageCenter();
//		mc2.setTitle("待办任务通知");
//		mc2.setContent("您有一项新的待处理任务，点击查看详情");
//		mc2.setCreateTime(new Date());
//		mc2.setEnable(1);
//		mc2.setPushTo(acount.getAccount());
//		mc2.setPushFrom("systems");
//		mc2.setContentType("taskManager");
//		mc2.setPushStatus(1);
//		mc2.setTaskId(task.getId());
//		mc2.setRoleName("manager");
//		int sf = messageCenterService.messageCenterAdd(mc2);
		int delete = messageCenterService.deleteCenter(vo.getTid(),"zby");
		System.out.print("成功删除------------------------------------------"+delete+"条信息");
		//消息通知经理
		//String alias = getAlias(acount.getAccount(),"manager");
		//pushService.sendIOSCustomizedcast(alias);
//		boolean df = pushService.SendCustomizedCast(acount.getAccount(),"您有一项新的待处理任务，点击查看详情","Bussiness");
//		logger.info("----------待办任务通知:接收人: "+acount.getAccount()+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
	}

	@Transactional(rollbackFor={RuntimeException.class, Exception.class}, readOnly = false, propagation = Propagation.REQUIRED)
	public BaseResult editTaskBymanager(ManagerTaskRequest vo,HttpServletRequest request) throws Exception{
		logger.info("editTaskBymanager Start Time = "+new Date());
		if(queryByTid("manager",vo.getTid())>0){
			return BaseResult.fail("0009","数据已经保存");
		}
		editTaskBymanagerInfo(vo,request);
		logger.info("editTaskBymanager end Time = "+new Date());
		return BaseResult.success();
	}

	public void editTaskBymanagerInfo(ManagerTaskRequest vo,HttpServletRequest request) throws Exception{
		logger.info("editTaskBymanagerInfo Start Time = "+new Date());
		Acount acount = cacheService.getAcountInfoFromCache(request);
		Date now = new Date();
		//插入经理任务
		ZbTaskManager managertask = new ZbTaskManager();
		managertask.setTid(vo.getTid());
		managertask.setBuyprice(new BigDecimal(Double.parseDouble(vo.getBuyprice())).multiply(new BigDecimal(10000)));
		managertask.setSelfprice(new BigDecimal(Double.parseDouble(vo.getSelfprice())).multiply(new BigDecimal(10000)));
		managertask.setOverprice(new BigDecimal(Double.parseDouble(vo.getOverprice())).multiply(new BigDecimal(10000)));
		managertask.setRemark(vo.getRemark());
		taskMapper.createManagerTask(managertask);
		// 插入经理任务信息的同时,修改carsok_acquisition_car的closeingPrice(成交价格)
		// 通过taskId查carsok_acquisition_car的id,能查到说明是通过收车进的整备,则执行修改
		String acarId = taskMapper.selectAcarIdByTaskId(vo.getTid());
		if(!StringUtil.isEmpty(acarId)){
			taskMapper.modifyAcquisitionClosingPrice(acarId, vo.getBuyprice());
		}
		//修改主任务信息
		ZbTask task = new ZbTask();
		task.setTaskTime(now);
		task.setTaskStatus(4);
		task.setId(vo.getTid());
		taskMapper.updateByModel(task);

		List<String> lists = getRoleList(acount.getAccount(),"yyzy");
		for (String yyzyMobile:lists) {
			MessageCenter mc = new MessageCenter();
			mc.setTitle("待办任务通知");
			mc.setContent("您有一项新的待处理任务，点击查看详情");
			mc.setCreateTime(new Date());
			mc.setEnable(1);
			mc.setPushTo(yyzyMobile);
			mc.setPushFrom("systems");
			mc.setContentType("taskYYZY");
			mc.setPushStatus(1);
			mc.setTaskId(vo.getTid());
			mc.setRoleName("yyzy");
			int sf = messageCenterService.messageCenterAdd(mc);
			boolean df = pushService.SendCustomizedCast(acount.getAccount()+yyzyMobile,"您有一项新的待处理任务，点击查看详情","Bussiness");
			logger.info("----------待办任务通知:接收人: "+yyzyMobile+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
		}
//		MessageCenter mc2 = new MessageCenter();
//		mc2.setTitle("待办任务通知");
//		mc2.setContent("您有一项新的待处理任务，点击查看详情");
//		mc2.setCreateTime(new Date());
//		mc2.setEnable(1);
//		mc2.setPushTo(acount.getAccount());
//		mc2.setPushFrom("systems");
//		mc2.setContentType("taskYYZY");
//		mc2.setPushStatus(1);
//		mc2.setTaskId(task.getId());
//		mc2.setRoleName("yyzy");
//		int sf = messageCenterService.messageCenterAdd(mc2);
		int delete = messageCenterService.deleteCenter(vo.getTid(),"manager");
		System.out.print("成功删除------------------------------------------"+delete+"条信息");
		//消息通知运营专员
		//String alias = getAlias(acount.getAccount(),"yyzy");
		//pushService.sendIOSCustomizedcast(alias);
//		boolean df = pushService.SendCustomizedCast(acount.getAccount(),"您有一项新的待处理任务，点击查看详情","Bussiness");
		logger.info("editTaskBymanagerInfo end Time = "+new Date());
//		logger.info("----------待办任务通知:接收人: "+acount.getAccount()+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
	}

	public BaseResult toDoTask(ToDoTaskRequest vo,HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		String roleName = vo.getRoleName();
		int status = 0;
		if(roleName.equals("pgs")){
			status = 1;
		}

		if(roleName.equals("zby")){
			status = 2;
		}

		if(roleName.equals("manager")){
			status = 3;
		}

		if(roleName.equals("yyzy")){
			status = 4;
		}
		ZbTask task = new ZbTask();
		task.setTaskAccount(acount.getAccount());
		task.setTaskStatus(status);
		List<ZbTask> data = taskMapper.selectByModel(task);
		List<TaskVo> list = new ArrayList<TaskVo>();
		for(ZbTask zbta:data){
			TaskVo tv = new TaskVo();
			tv.setId(zbta.getId());
			tv.setCarName(zbta.getCarName());
			tv.setTaskAccount(zbta.getTaskAccount());
			tv.setTaskNum(zbta.getTaskNum());
			tv.setTaskStatus(zbta.getTaskStatus());
			tv.setTaskTime(zbta.getTaskTime());
			if(status <3 ){
				tv.setCarNum(zbta.getCarNum());
			}else{
				tv.setCarNum(zbta.getLastCarNum());
			}
			list.add(tv);
		}
		return BaseResult.success(list);
	}

	public BaseResult hasDoTask(ToDoTaskRequest vo,HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		String roleName = vo.getRoleName();
		int status = 0;

		if(roleName.equals("sxy")){
			status = 0;
		}
		if(roleName.equals("pgs")){
			status = 1;
		}

		if(roleName.equals("zby")){
			status = 2;
		}

		if(roleName.equals("manager")){
			status = 3;
		}

		if(roleName.equals("yyzy")){
			status = 4;
		}
		ZbTask task = new ZbTask();
		task.setTaskAccount(acount.getAccount());
		task.setTaskStatus(status);
		List<ZbTask> data = taskMapper.selecthasDoTask(task);
		List<TaskVo> list = new ArrayList<TaskVo>();
		for(ZbTask zbta:data){
			TaskVo tv = new TaskVo();
			tv.setId(zbta.getId());
			tv.setCarName(zbta.getCarName());
			tv.setTaskAccount(zbta.getTaskAccount());
			tv.setTaskNum(zbta.getTaskNum());
			tv.setTaskStatus(zbta.getTaskStatus());
			tv.setTaskTime(zbta.getTaskTime());
			tv.setVin(zbta.getVin());
			if(status <3 ){
				tv.setCarNum(zbta.getCarNum());
			}else{
				tv.setCarNum(zbta.getLastCarNum());
			}
			list.add(tv);
		}
		return BaseResult.success(list);
	}

	public BaseResult deleteTask(int id,HttpServletRequest request)
	{
		try {
			taskMapper.updateEnableFlag(id);
			messageCenterService.deleteCenter(id,"");
		}
		catch(Exception e) {
			return BaseResult.exception(e.getMessage());
		}
		return BaseResult.success();
	}

	public BaseResult taskInfo(TaskInfoRequest vo,HttpServletRequest request,int isDoTask){
		TaskInfoResponse resp = new TaskInfoResponse();
		if(vo.getRoleName().equals("sxy")){
			resp = sxyTaskInfo(vo.getTaskid());
		}
		if(vo.getRoleName().equals("pgs")){
			resp = pgsTaskInfo(vo.getTaskid(),isDoTask);
		}
		if(vo.getRoleName().equals("zby")){
			resp = zbyTaskInfo(vo.getTaskid(),isDoTask);
		}
		if(vo.getRoleName().equals("manager")){
			resp = managerTaskInfo(vo.getTaskid(),isDoTask);
		}
		if(vo.getRoleName().equals("yyzy")){
			resp = yyzyTaskInfo(vo.getTaskid(),isDoTask);
		}
		return BaseResult.success(resp);
	}
	public TaskInfoResponse getTask(int taskid){
		ZbTask queryInfo = new ZbTask();
		queryInfo.setId(taskid);
		List<ZbTask> list =  taskMapper.selectByModel(queryInfo);
		TaskInfoResponse resp = new TaskInfoResponse();
		if(list !=null && list.size()>0){
			ZbTask task = list.get(0);
			resp.setId(task.getId());
			resp.setCarName(task.getCarName());
			resp.setCarNum(task.getCarNum());
			resp.setTaskNum(task.getTaskNum());
			resp.setTaskTime(task.getTaskTime());
			resp.setLastCarNum(task.getLastCarNum());
			resp.setVin(task.getVin());
		}
		return resp;
	}
	//手续员任务详情
	public TaskInfoResponse sxyTaskInfo(int taskid){
		TaskInfoResponse resp = getTask(taskid);
		ZbTasksxy sxy = new ZbTasksxy();
		sxy.setTid(taskid);
		sxy = taskMapper.selectsxyTask(sxy);
		resp.setRemark(sxy.getRemark());
		resp.setTaskInfo(sxy);
		resp.setVin(sxy.getVin());
		return resp;
	}
	//评估师任务详情
	public TaskInfoResponse pgsTaskInfo(int taskid,int isDoTask){
		TaskInfoResponse resp = getTask(taskid);
		if(isDoTask == 1){
			ZbTaskpgs pgs = new ZbTaskpgs();
			pgs.setTid(taskid);
			pgs = taskMapper.selectpgsTask(pgs);
			resp.setRemark(pgs.getRemark());
			resp.setVin(pgs.getVin());
			Map<String,String[]> data = new HashMap<String, String[]>();
			if(!StringUtil.isEmpty(pgs.getInfos())){
				String[] info = pgs.getInfos().split(";");
				for(int i=0;i<info.length;i++){
					int index = info[i].indexOf(":");
					String key = info[i].substring(0,index);
					String value = info[i].substring(index+1,info[i].length());
					String[] values = value.split(",");
					data.put(key, values);
				}
			}
			resp.setTaskInfo(data);
		}
		return resp;
	}
	//整备员任务详情
	public TaskInfoResponse zbyTaskInfo(int taskid,int isDoTask){
		TaskInfoResponse resp = getTask(taskid);
		if(isDoTask == 1){
			ZbyTaskInfoResponse data = new ZbyTaskInfoResponse();
			ZbTaskzby zby = new ZbTaskzby();
			zby.setTid(taskid);
			zby = taskMapper.selectzbyTask(zby);
			ZbMoneyInfo info = new ZbMoneyInfo();
			info.setZbyId(zby.getId());
			List<ZbMoneyInfo> minfo = taskMapper.selectzbMoneyInfo(info);// 经理待办--这个 "收车价格" 是 收车管理 中的 closeingPrice
			List<TaskZbBill> billList = taskMapper.selectBillListByTid(taskid);
			data.setZbMoney(zby.getZbMoney().toString());
			data.setMlist(minfo);
			data.setVin(zby.getVin());
			data.setBillList(billList);
			resp.setTaskInfo(data);
			resp.setRemark(zby.getRemark());
			resp.setVin(zby.getVin());
			resp.setAgainTimes(zby.getZbyAgainTimes());
			resp.setAgainTime(zby.getZbyAgainTime());
		}else{
			ZbTaskpgs pgs = new ZbTaskpgs();
			pgs.setTid(taskid);
			pgs = taskMapper.selectpgsTask(pgs);
			resp.setRemark(pgs.getRemark());
			resp.setVin(pgs.getVin());
			Map<String,String[]> data = new HashMap<String, String[]>();
			if(!StringUtil.isEmpty(pgs.getInfos())){
				String[] info = pgs.getInfos().split(";");
				for(int i=0;i<info.length;i++){
					int index = info[i].indexOf(":");
					String key = info[i].substring(0,index);
					String value = info[i].substring(index+1,info[i].length());
					String[] values = value.split(",");
					data.put(key, values);
				}
			}
			resp.setTaskInfo(data);
		}

		return resp;
	}
	//经理任务详情
	public TaskInfoResponse managerTaskInfo(int taskid,int isDoTask){
		TaskInfoResponse resp = getTask(taskid);
		if(isDoTask == 1){
			ManagerTaskInfoResponse data = new ManagerTaskInfoResponse();
			ZbTaskzby zby = new ZbTaskzby();
			zby.setTid(taskid);
			zby = taskMapper.selectzbyTask(zby);
			ZbMoneyInfo info = new ZbMoneyInfo();
			info.setZbyId(zby.getId());
			List<ZbMoneyInfo> minfo = taskMapper.selectzbMoneyInfo(info);
			ZbTaskManager manager = new ZbTaskManager();
			manager.setTid(taskid);
			manager = taskMapper.selectmanagerTask(manager);
			manager.setBuyprice(manager.getBuyprice().divide(new BigDecimal(10000)));
			manager.setOverprice(manager.getOverprice().divide(new BigDecimal(10000)));
			manager.setSelfprice(manager.getSelfprice().divide(new BigDecimal(10000)));
			data.setManagerInfo(manager);
			data.setMlist(minfo);
			resp.setTaskInfo(data);
			resp.setRemark(manager.getRemark());
		}else{
			ZbyTaskInfoResponse data = new ZbyTaskInfoResponse();
			ZbTaskzby zby = new ZbTaskzby();
			zby.setTid(taskid);
			zby = taskMapper.selectzbyTask(zby);
			ZbMoneyInfo info = new ZbMoneyInfo();
			info.setZbyId(zby.getId());
			List<ZbMoneyInfo> minfo = taskMapper.selectzbMoneyInfo(info);
			BigDecimal closingPrice = taskMapper.selectClosingPriceByTaskId(taskid);
			data.setClosingPrice(closingPrice);
			data.setZbMoney(zby.getZbMoney().toString());
			data.setMlist(minfo);
			data.setVin(zby.getVin());
			resp.setTaskInfo(data);
			resp.setVin(zby.getVin());
		}
		Map<String,String> picMap=taskMapper.selectPictureLook(taskid);
		resp.setPicMap(picMap);
		return resp;
	}
	//运营专员任务详情
	public TaskInfoResponse yyzyTaskInfo(int taskid,int isDoTask){
		TaskInfoResponse resp = getTask(taskid);
		Map<String,Object> yyzyInfo = new HashMap<String, Object>();
		ZbTaskManager manager = new ZbTaskManager();
		manager.setTid(taskid);
		manager = taskMapper.selectmanagerTask(manager);
		ZbTasksxy sxy = new ZbTasksxy();
		sxy.setTid(taskid);
		ZbTasksxy managersxy = taskMapper.selectsxyTask(sxy);
		ZbTaskzby zby = new ZbTaskzby();
		zby.setTid(taskid);
		ZbTaskzby managerzby = taskMapper.selectzbyTask(zby);
		ZbMoneyInfo info = new ZbMoneyInfo();
		info.setZbyId(managerzby.getId());
		List<ZbMoneyInfo> minfo = taskMapper.selectzbMoneyInfo(info);
		BigDecimal selfprice = manager.getSelfprice().divide(new BigDecimal(10000));
		BigDecimal overprice = manager.getOverprice().divide(new BigDecimal(10000));
		BigDecimal buyPrice = manager.getBuyprice().divide(new BigDecimal(10000));
		List<TaskZbBill> billList = taskMapper.selectBillListByTid(taskid);
		yyzyInfo.put("billList",billList);
		yyzyInfo.put("selfprice", selfprice.toString());
		yyzyInfo.put("overprice", overprice.toString());
		yyzyInfo.put("buyPrice", buyPrice.toString());
		yyzyInfo.put("pricingRemark", manager.getRemark());
		yyzyInfo.put("mList", minfo);
		yyzyInfo.put("managersxy",managersxy);
		yyzyInfo.put("managerzby",managerzby);
		resp.setTaskInfo(yyzyInfo);
		return resp;
	}

	/**
	 * 修改yyzy任务详情
	 * @param request
	 * @param vo
	 * @return
	 */
	public BaseResult updateTaskByyyzy(HttpServletRequest request,TaskForYyzyRequest vo){
		logger.info("updateTaskByyyzy Start Time = "+new Date());
		TaskForYyzyResponse r = new TaskForYyzyResponse();
		BaseResult baseResult = BaseResult.success();
		Map<String,Object> map = new HashMap<String,Object>();

		ZbTask zbTask = new ZbTask();
		zbTask.setId(vo.getId());
		zbTask.setCarName(vo.getCarName());
		zbTask.setCarNum(vo.getCarNum());
		zbTask.setLastCarNum(vo.getLastCarNuml());
		zbTask.setVin(vo.getVin());
		map.put("zbTask",taskMapper.updateTaskModel(zbTask));

		if(!StringUtil.isEmpty(vo.getOverprice())||!StringUtil.isEmpty(vo.getSelfprice())||vo.getBuyPrice()!=new BigDecimal(0)){
			ZbTaskManager manager = new ZbTaskManager();
			manager.setTid(vo.getId());
			manager.setOverprice(new BigDecimal(Double.parseDouble(vo.getOverprice())).multiply(new BigDecimal(10000)));
			manager.setSelfprice(new BigDecimal(Double.parseDouble(vo.getSelfprice())).multiply(new BigDecimal(10000)));
			map.put("manager",taskMapper.updateManagerTask(manager));
		}
		ZbMoneyInfo info = new ZbMoneyInfo();
		info.setZbyId(vo.getZbyid());
		List<ZbMoneyInfo> minfo = taskMapper.selectzbMoneyInfo(info);
		ZbMoneyInfo zbMoneyInfo = new ZbMoneyInfo();
//		if(vo.getManagerInfoList().size()>0){
//			taskMapper.deleteMoneyInfo(vo.getZbyid());
//			for(ZbMoneyInfo zmi:vo.getManagerInfoList()){
//				zbMoneyInfo.setZbyId(zmi.getZbyId());
//				zbMoneyInfo.setAmount(zmi.getAmount());
//				zbMoneyInfo.setClassification(zmi.getClassification());
//				zbMoneyInfo.setDetail(zmi.getDetail());
//
//				taskMapper.insertMoneyInfo(zbMoneyInfo);
//			}
//		}else if(vo.getManagerInfoList().size()==0){
//			taskMapper.deleteMoneyInfo(vo.getZbyid());
//		}

//		TaskZbBill taskZbBill = new TaskZbBill();
//		if(vo.getBillList() != null && vo.getBillList().size()>0){
//			taskMapper.deleteZbyBill(vo.getId());
//			for(TaskZbBill bill:vo.getBillList()){
//				taskZbBill.setTid(vo.getId());
//				taskZbBill.setBillPic(bill.getBillPic());
//				taskMapper.insertBill(taskZbBill);
//			}
//		}



		ZbTasksxy sxy = new ZbTasksxy();
		sxy.setTid(vo.getId());
		sxy.setKeysNum(vo.getKeysNum());
		sxy.setSelfMobile(StringUtil.isEmpty(vo.getSelfMobile())?"":vo.getSelfMobile());
		sxy.setSelfName(StringUtil.isEmpty(vo.getSelfName())?"":vo.getSelfName());
		sxy.setVin(vo.getVin());
		map.put("sxy",taskMapper.updateSxyTask(sxy));

//		ZbTaskzby zby = new ZbTaskzby();
//		zby.setTid(vo.getId());
//		zby.setZbMoney(vo.getZbMoney());
//		zby.setVin(vo.getVin());
//		map.put("zby",taskMapper.updateZbyTask(zby));
		//int delete = messageCenterService.deleteCenter(vo.getId(),"yyzy");
		//System.out.print("成功删除------------------------------------------"+delete+"条信息");
		baseResult.setData(map);
		logger.info("updateTaskByyyzy end Time = "+new Date());
		return baseResult;
	}

	public BaseResult selectZbCount(HttpServletRequest request,ZbCountRequest vo){
		BaseResult baseResult = BaseResult.success();
		String mobile = vo.getAccountMobile();
		//判断主子账号
		Acount acount2 = cacheService.getAcountInfoFromCache(request);
		String childIdPhone = acount2.getSubPhone();
		int accountId=0;
		int childId=0;
		if(childIdPhone==null){
			List<Integer> list1 = customerService.selectId(mobile);
			accountId = list1.get(0);
			childId = list1.get(1);
		}else {
			List<Integer> list1 = customerService.selectId(childIdPhone);
			accountId = list1.get(0);
			childId = list1.get(1);
		}

		Acount_child_Id acount_child_id = new Acount_child_Id();
		acount_child_id.setAcountId(accountId);
		acount_child_id.setChildId(childId);

		List<String> roleList = vo.getRoleList();
		int zbCount = 0;
		int sxydbCount=0;
		Acount acount = new Acount();
		acount.setAccount(mobile);

		Acount acount1 = acountMapper.selectByModel(acount);
		//手续员待办数量
		sxydbCount = taskMapper.sxydbCount(acount_child_id);
		if(acount1==null){
			List qx=acountMapper.selectQx(acount_child_id.getChildId());
			//整備管理
			if(qx.contains("zbgl")){

				roleList = new ArrayList<String>();
				//定價
				if(qx.contains("dj")){
					roleList.add("taskManager");
				}
				//評估
				if(qx.contains("pg")){
					roleList.add("taskPGS");
				}
				//整備
				if(qx.contains("zb")){
					roleList.add("taskZBY");
				}
				//入庫
				if(qx.contains("rk")){
					roleList.add("taskYYZY");
				}
				//手續
				if(qx.contains("sx")) {
					roleList.add("taskSXY");
				}
				List<String> mobiles = new ArrayList<String>();
				List<ChildAccount> childList = childAccountMapper.selectAllChild(mobile);
				for(ChildAccount child:childList){
					mobiles.add(child.getChildAccountMobile());
				}
				ChildAccount child = new ChildAccount();
				child.setChildAccountMobile(mobile);
				ChildAccount childAccount = childAccountMapper.selectByModel(child);
				mobiles.add(childAccount.getChildAccountMobile());
				zbCount = taskMapper.selectZbCountByZjl(mobiles,roleList);
				zbCount = sxydbCount + zbCount;
			}else {
				if(vo.getRoleList()==null || vo.getRoleList().size()==0){
					baseResult.setData(0);
					return baseResult.success(0);
				}
				zbCount = taskMapper.selectZbCount(mobile, roleList);
				//zbCount = sxydbCount + zbCount;
			}
		}else {
			roleList = new ArrayList<String>();
			roleList.add("taskPGS");
			roleList.add("taskZBY");
			roleList.add("taskManager");
			roleList.add("taskYYZY");
			roleList.add("taskSXY");
			zbCount = 0;
			//zbCount = sxydbCount + zbCount;
		}
		baseResult.setData(zbCount);
		return baseResult;
	}

	public BaseResult sxydb(HttpServletRequest request){
		Acount acount = cacheService.getAcountInfoFromCache(request);
		String mobile=acount.getAccount();
		String childIdPhone = acount.getSubPhone();
		int accountId=0;
		int childId=0;
		if(childIdPhone==null){
			List<Integer> list1 = customerService.selectId(mobile);
			accountId = list1.get(0);
			childId = list1.get(1);
		}else {
			List<Integer> list1 = customerService.selectId(childIdPhone);
			accountId = list1.get(0);
			childId = list1.get(1);
		}


		Acount_child_Id acount_child_id = new Acount_child_Id();
		acount_child_id.setAcountId(accountId);
		acount_child_id.setChildId(childId);

		List<CarsokZbTaskSxyWb> list = taskMapper.sxydb(acount_child_id);
		return BaseResult.success(list);

	}
	//手续员推送
	public void SXYTaskInfoTs(HttpServletRequest req,Integer id) throws Exception{
		Acount acount = cacheService.getAcountInfoFromCache(req);
		List<String> list = getRoleList(acount.getAccount(),"sxy");
		//插入主任务
		for (String pgsMobile:list) {
			MessageCenter mc = new MessageCenter();
			mc.setTitle("待办任务通知");
			mc.setContent("您有一项新的待处理任务，点击查看详情");
			mc.setCreateTime(new Date());
			mc.setEnable(1);
			mc.setPushTo(pgsMobile);
			mc.setPushFrom("systems");
			mc.setContentType("taskSXY");
			mc.setPushStatus(1);
			mc.setSxyId(id);
			mc.setRoleName("sxy");
			int sf = messageCenterService.messageCenterAdd(mc);
			boolean df = pushService.SendCustomizedCast(acount.getAccount()+pgsMobile, "您有一项新的待处理任务，点击查看详情","Bussiness");
			//System.out.println("插入数据成功!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			logger.info("----------待办任务通知:接收人: "+pgsMobile+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
		}

//		MessageCenter mc2 = new MessageCenter();
//		mc2.setTitle("待办任务通知");
//		mc2.setContent("您有一项新的待处理任务，点击查看详情");
//		mc2.setCreateTime(new Date());
//		mc2.setEnable(1);
//		mc2.setPushTo(acount.getAccount());
//		mc2.setPushFrom("systems");
//		mc2.setContentType("taskSXY");
//		mc2.setPushStatus(1);
//		mc2.setSxyId(id);
//		mc2.setRoleName("sxy");
//		int sf = messageCenterService.messageCenterAdd(mc2);
		//消息通知手续员
		//String alias = getAlias(acount.getAccount(),"pgs");
//		boolean df = pushService.SendCustomizedCast(acount.getAccount(),"您有一项新的待处理任务，点击查看详情","Bussiness");
//		logger.info("----------待办任务通知:接收人: "+acount.getAccount()+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
	}

	public int queryByTid(String roleName,int tid){
		int num = 0;
		if("pgs".equals(roleName)){
			num = taskMapper.selectPgsByTid(tid);
		}else if("zby".equals(roleName)){
			num = taskMapper.selectZbyByTid(tid);
		}else if("manager".equals(roleName)){
			num = taskMapper.selectManagerByTid(tid);
		}
		return num;
	}

	public BaseResult zbAgain(HttpServletRequest request,ZbyTaskAgainRequest vo){
		ZbMoneyInfo zbmi = new ZbMoneyInfo();
		ZbTaskzby zbTaskzby = new ZbTaskzby();
		zbTaskzby.setTid(vo.getTid());
		zbTaskzby = taskMapper.selectzbyTask(zbTaskzby);
        Date date = new Date();
		if(vo.getMinfos().size()>0){
			for(ZbMoneyInfo zmi:vo.getMinfos()){
				zbmi.setZbyId(zbTaskzby.getId());
				zbmi.setAmount(zmi.getAmount());
				zbmi.setClassification(zmi.getClassification());
				zbmi.setDetail(zmi.getDetail());
                zbmi.setAgainTimes(zbTaskzby.getZbyAgainTimes()+1);
                zbmi.setAgainTime(date);
				taskMapper.insertMoneyInfo(zbmi);
			}
		}

		TaskZbBill taskZbBill = new TaskZbBill();
		if(vo.getBillList().size()>0 && vo.getBillList() != null){
			for(String bill:vo.getBillList()){
				taskZbBill.setTid(vo.getTid());
				taskZbBill.setBillPic(bill);
                taskZbBill.setAgainTimes(zbTaskzby.getZbyAgainTimes()+1);
                taskZbBill.setAgainTime(date);
				taskMapper.insertBill(taskZbBill);
			}
		}
		ZbTaskzby zbTaskzbys = new ZbTaskzby();
		zbTaskzbys.setTid(vo.getTid());
		zbTaskzbys.setZbyAgainTimes(zbTaskzby.getZbyAgainTimes()+1);
		zbTaskzbys.setZbyAgainTime(date);
		zbTaskzbys.setZbMoney(new BigDecimal(vo.getZbMoney()).add(zbTaskzby.getZbMoney()));
		taskMapper.updateZbyTask(zbTaskzbys);
		return BaseResult.success();
	}

}
