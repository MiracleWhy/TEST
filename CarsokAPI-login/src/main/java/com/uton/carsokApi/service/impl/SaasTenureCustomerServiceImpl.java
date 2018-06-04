package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.constants.enums.ModuleEnums;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.constants.enums.TaskTypeEnums;
import com.uton.carsokApi.controller.request.CreateCarServeInfoRequest;
import com.uton.carsokApi.controller.request.FindDetailInfoRequest;
import com.uton.carsokApi.controller.request.FindListRequest;
import com.uton.carsokApi.controller.request.SaveCarAndCustomeInfoRequest;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.*;
import com.uton.carsokApi.service.core.ObjectDiffExcutor;
import com.uton.carsokApi.service.core.task.FilterSQLParam;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.DozerMapperUtil;
import com.uton.carsokApi.util.HttpClientUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("runtaskservice")
public class SaasTenureCustomerServiceImpl implements SaasTenureCustomerService {

	@Autowired
	private SaasTenureCustomerMapper saasTenureCustomerMapper;
	@Autowired
	private CarsokCustomerMapper carsokCustomerMapper;
	@Autowired
	private CarsokCustomerTenureCarMapper carsokCustomerTenureCarMapper;
	@Autowired
	private CarsokRecordMapper carsokRecordMapper;
	@Autowired
	ITaskFacade iTaskFacade;
	@Autowired
	CarsokAccountPowerMapper carsokAccountPowerMapper;
	@Autowired
	CarsokAccountPowerManageMapper carsokAccountPowerManageMapper;
	@Autowired
	CarsokAcountMapper carsokAcountMapper;
	@Autowired
	CarsokChildAccountMapper carsokChildAccountMapper;
	@Autowired
	PushService pushService;
	@Autowired
	OverdueForgService overdueForgService;
	@Autowired
	ChildAccountMapper childAccountMapper;
	@Autowired
	MessageCenterService messageCenterService;
	@Autowired
	AcountMapper acountMapper;
	@Autowired
	private PictureMapper pictureMapper;
	@Autowired
	PruductOldcarMapper pruductOldcarMapper;
	@Autowired
	private EventBus eventBus;
	@Value("${buyerSide}")
	private String buyerSide;
	@Value("${store.url.prefix}")
	private String storeUrl;
	@Autowired
    CarsokCustomerTenureServeMapper carsokCustomerTenureServeMapper;
	@Autowired
	SaasFollowMessageService saasFollowMessageService;

	private final static Logger logger = Logger.getLogger(SaasTenureCustomerServiceImpl.class);

	@Override
    public List<String> getRoleName(int childId) {
        return saasTenureCustomerMapper.getRoleName(childId);
    }


	@Override
	public BaseResult findList(FindListRequest vo, Acount acount, int childId) {
		BaseResult result = BaseResult.success();
		Map map = new HashMap();
		DateFormat nyr = new SimpleDateFormat("yyyy-MM-dd");
		int accountId = acount.getId();
		List<String> roleName = new ArrayList<>();
		if (childId != 0){
			roleName = getRoleName(childId);
		}
		int roleType = 0;
		if(roleName.contains("byyxgw") && !roleName.contains("byjlgl") && !roleName.contains("bykfdp")){
			//销售,只能看到自己相关的信息
			roleType = 1;
		}
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<FindListResponse> page = new ArrayList<>();
		switch (vo.getType()){
			case 0 : //搜索
				page = saasTenureCustomerMapper.findCarList(roleType, accountId, childId, 0, vo.getSelects(), 0, 0, null);
				break;
			case 1 : //待完善
				page = saasTenureCustomerMapper.findCarList(roleType, accountId, childId, 0, "", 1, 0, null);
				break;
			case 2 : //例行
				FilterSQLParam sqlParam = new FilterSQLParam();
				String sqlStr = " module='retaincustomer' AND task_status='"+ TaskStatusEnums.ready +"' " +
						"AND type IN ('"+ TaskTypeEnums.threedays_followup +"', '"+ TaskTypeEnums.fifteendays_followup +"', '"+ TaskTypeEnums.onemonth_flollowup +"') " +
						"AND json_extract(extra_fields,'$.nextIsDone')!=1 " +
						"AND json_extract(extra_fields,'$.accountId')="+ accountId +" ";
				String roleStr = "";
				if (roleType == 1){
					roleStr = "AND json_extract(extra_fields,'$.childId')="+ childId +" ";
				}
				sqlStr = sqlStr + roleStr + "AND DATE_FORMAT(scheduled_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')";
				sqlParam.setSqlTemplate(sqlStr);
				sqlParam.setOrderByColumn("create_time");
				sqlParam.setIsAsc(false);
				PageInfo<CarsokTenureTask> pageInfo = iTaskFacade.queryTaskBySQLFilter(vo.getPageNum(), vo.getPageSize(), sqlParam);
				for (CarsokTenureTask task : pageInfo.getList()){
					FindListResponse response = saasTenureCustomerMapper.selectOtherInfoByCarId(task.getBusinessId());

					response.setShowType(getShowType(task.getType()));
					FindListResponse temp = new FindListResponse();
					DozerMapperUtil.getInstance().map(response, temp);
					page.add(temp);
				}
				break;
			case 3 : //关怀
				FilterSQLParam sqlParam1 = new FilterSQLParam();
				String sqlStr1 = " module='retaincustomer' AND task_status='"+ TaskStatusEnums.ready +"' " +
						"AND type IN ('"+ TaskTypeEnums.compulsory_insurance_remind +"', '"+ TaskTypeEnums.commercial_insurance_remind +"', '"+ TaskTypeEnums.quality_assurance_remind +"', '"+ TaskTypeEnums.maintain_remind +"', '"+ TaskTypeEnums.annual_survey_remind +"', '"+ TaskTypeEnums.boughtday_solicitude +"', '"+ TaskTypeEnums.birthday_solicitude +"') " +
						"AND json_extract(extra_fields,'$.nextIsDone')!=1 " +
						"AND json_extract(extra_fields,'$.accountId')="+ accountId +" ";
				String roleStr1 = "";
				if (roleType == 1){
					roleStr1 = "AND json_extract(extra_fields,'$.childId')="+ childId +" ";
				}
				sqlStr1 = sqlStr1 + roleStr1 + "AND DATE_FORMAT(scheduled_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')";
				sqlParam1.setSqlTemplate(sqlStr1);
				sqlParam1.setOrderByColumn("create_time");
				sqlParam1.setIsAsc(false);
				PageInfo<CarsokTenureTask> pageInfo1 = iTaskFacade.queryTaskBySQLFilter(vo.getPageNum(), vo.getPageSize(), sqlParam1);
				for (CarsokTenureTask task : pageInfo1.getList()){
					FindListResponse response;
					if(TaskTypeEnums.birthday_solicitude.name().equals(task.getType())){//说明businessId是客户id
						response = saasTenureCustomerMapper.selectOtherInfoByCustomerId(task.getBusinessId());
					}else {//说明businessId是保有车id
						response = saasTenureCustomerMapper.selectOtherInfoByCarId(task.getBusinessId());
					}
					response.setShowType(getShowType(task.getType()));
					FindListResponse temp = new FindListResponse();
					DozerMapperUtil.getInstance().map(response, temp);
					page.add(temp);
				}
				break;
			case 4 : //逾期
				FilterSQLParam sqlParam2 = new FilterSQLParam();
				String sqlStr2 = " module='retaincustomer' AND task_status='"+ TaskStatusEnums.delay +"' " +
						"AND json_extract(extra_fields,'$.nextIsDone')!=1 " +
						"AND json_extract(extra_fields,'$.accountId')="+ accountId +" ";
				String roleStr2 = "";
				if (roleType == 1){
					roleStr2 = "AND json_extract(extra_fields,'$.childId')="+ childId +" ";
				}
				sqlStr2 = sqlStr2 + roleStr2;
				sqlParam2.setSqlTemplate(sqlStr2);
				sqlParam2.setOrderByColumn("create_time");
				sqlParam2.setIsAsc(false);
				PageInfo<CarsokTenureTask> pageInfo2 = iTaskFacade.queryTaskBySQLFilter(vo.getPageNum(), vo.getPageSize(), sqlParam2);
				for (CarsokTenureTask task : pageInfo2.getList()){
					FindListResponse response;
					if(TaskTypeEnums.birthday_solicitude.name().equals(task.getType())){//说明businessId是客户id
						response = saasTenureCustomerMapper.selectOtherInfoByCustomerId(task.getBusinessId());
					}else {//说明businessId是保有车id
						response = saasTenureCustomerMapper.selectOtherInfoByCarId(task.getBusinessId());
					}
					response.setShowType(getShowType(task.getType()));
					Calendar cal = Calendar.getInstance();
					cal.setTime(task.getScheduledTime());
					long time1 = cal.getTimeInMillis();//任务应该执行的时间
					cal.setTime(new Date());
					long time2 = cal.getTimeInMillis();//当前时间
					long between_days = (time2-time1)/(1000*3600*24);
					String delayDays = String.valueOf(between_days);
					response.setDelayDays("逾期："+delayDays+"天");
					FindListResponse temp = new FindListResponse();
					DozerMapperUtil.getInstance().map(response, temp);
					page.add(temp);
				}
				break;
			case 5 : //筛选
				String[] buyArr = vo.getBuyStatus().trim().split(",");
				List<String> list = new ArrayList<>();
				for (int i=0; i<buyArr.length; i++){
					switch (buyArr[i]){
						case "1" :
							list.add("首次购买");
							break;
						case "2" :
							list.add("首次置换");
							break;
						case "3" :
							list.add("复购");
							break;
						case "4" :
							list.add("置换");
							break;
						case "0" :
//							list.add("首次购买");
//							list.add("首次置换");
//							list.add("复购");
//							list.add("置换");
							list = null;
							break;
					}
				}
				page = saasTenureCustomerMapper.findCarList(roleType, accountId, childId, 0, "", vo.getCompleteStatus(), vo.getTimes(), list);
				break;
		}
		map.put("infoPage",page);
		//待完善一栏的数量
		PageHelper.startPage(0, 0);
		List<FindListResponse> allList = new ArrayList<>();
		allList = saasTenureCustomerMapper.findCarList(roleType, accountId, childId, 0, "", 1, 0, null);
		int notDoneNum = allList.size();
		//例行一栏的数量
		FilterSQLParam sqlParam = new FilterSQLParam();
		String sqlStr = " module='retaincustomer' AND task_status='"+ TaskStatusEnums.ready +"' " +
				"AND type IN ('"+ TaskTypeEnums.threedays_followup +"', '"+ TaskTypeEnums.fifteendays_followup +"', '"+ TaskTypeEnums.onemonth_flollowup +"') " +
				"AND json_extract(extra_fields,'$.nextIsDone')!=1 " +
				"AND json_extract(extra_fields,'$.accountId')="+ accountId +" ";
		String roleStr = "";
		if (roleType == 1){
			roleStr = "AND json_extract(extra_fields,'$.childId')="+ childId +" ";
		}
		sqlStr = sqlStr + roleStr + "AND DATE_FORMAT(scheduled_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')";
		sqlParam.setSqlTemplate(sqlStr);
		sqlParam.setOrderByColumn("create_time");
		sqlParam.setIsAsc(false);
		PageInfo<CarsokTenureTask> pageInfo = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam);
		int onDutyNum = pageInfo.getList().size();
		//关怀一栏的数量
		FilterSQLParam sqlParam1 = new FilterSQLParam();
		String sqlStr1 = " module='retaincustomer' AND task_status='"+ TaskStatusEnums.ready +"' " +
				"AND type IN ('"+ TaskTypeEnums.compulsory_insurance_remind +"', '"+ TaskTypeEnums.commercial_insurance_remind +"', '"+ TaskTypeEnums.quality_assurance_remind +"', '"+ TaskTypeEnums.maintain_remind +"', '"+ TaskTypeEnums.annual_survey_remind +"', '"+ TaskTypeEnums.boughtday_solicitude +"', '"+ TaskTypeEnums.birthday_solicitude +"') " +
				"AND json_extract(extra_fields,'$.nextIsDone')!=1 " +
				"AND json_extract(extra_fields,'$.accountId')="+ accountId +" ";
		String roleStr1 = "";
		if (roleType == 1){
			roleStr1 = "AND json_extract(extra_fields,'$.childId')="+ childId +" ";
		}
		sqlStr1 = sqlStr1 + roleStr1 + "AND DATE_FORMAT(scheduled_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')";
		sqlParam1.setSqlTemplate(sqlStr1);
		sqlParam1.setOrderByColumn("create_time");
		sqlParam1.setIsAsc(false);
		PageInfo<CarsokTenureTask> pageInfo1 = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam1);
		int solicitudeNum = pageInfo1.getList().size();
		//逾期一栏的数量
		FilterSQLParam sqlParam2 = new FilterSQLParam();
		String sqlStr2 = " module='retaincustomer' AND task_status='"+ TaskStatusEnums.delay +"' " +
				"AND json_extract(extra_fields,'$.nextIsDone')!=1 " +
				"AND json_extract(extra_fields,'$.accountId')="+ accountId +" ";
		String roleStr2 = "";
		if (roleType == 1){
			roleStr2 = "AND json_extract(extra_fields,'$.childId')="+ childId +" ";
		}
		sqlStr2 = sqlStr2 + roleStr2;
		sqlParam2.setSqlTemplate(sqlStr2);
		sqlParam2.setOrderByColumn("create_time");
		sqlParam2.setIsAsc(false);
		PageInfo<CarsokTenureTask> pageInfo2 = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam2);
		int outDateNum = pageInfo2.getList().size();

		map.put("notDoneNum", notDoneNum);
		map.put("onDutyNum", onDutyNum);
		map.put("solicitudeNum", solicitudeNum);
		map.put("outDateNum", outDateNum);
		result.setData(map);
		return result;
	}

	private String getTypeString(String type) {
    	String result = "";
    	if (TaskTypeEnums.birthday_solicitude.name().equals(type)){
			result = TaskTypeEnums.birthday_solicitude.getTaskType();
		}else if (TaskTypeEnums.threedays_followup.name().equals(type)){
			result = TaskTypeEnums.threedays_followup.getTaskType();
		}else if (TaskTypeEnums.fifteendays_followup.name().equals(type)){
			result = TaskTypeEnums.fifteendays_followup.getTaskType();
		}else if (TaskTypeEnums.onemonth_flollowup.name().equals(type)){
			result = TaskTypeEnums.onemonth_flollowup.getTaskType();
		}else if (TaskTypeEnums.compulsory_insurance_remind.name().equals(type)){
			result = TaskTypeEnums.compulsory_insurance_remind.getTaskType();
		}else if (TaskTypeEnums.commercial_insurance_remind.name().equals(type)){
			result = TaskTypeEnums.commercial_insurance_remind.getTaskType();
		}else if (TaskTypeEnums.quality_assurance_remind.name().equals(type)){
			result = TaskTypeEnums.quality_assurance_remind.getTaskType();
		}else if (TaskTypeEnums.maintain_remind.name().equals(type)){
			result = TaskTypeEnums.maintain_remind.getTaskType();
		}else if (TaskTypeEnums.annual_survey_remind.name().equals(type)){
			result = TaskTypeEnums.annual_survey_remind.getTaskType();
		}else if (TaskTypeEnums.boughtday_solicitude.name().equals(type)){
			result = TaskTypeEnums.boughtday_solicitude.getTaskType();
		}
		return result;
	}

	private String getShowType(String type) {
		String result = "";
		if (TaskTypeEnums.birthday_solicitude.name().equals(type)){
			result = "生日";
		}else if (TaskTypeEnums.threedays_followup.name().equals(type)){
			result = "三日回访";
		}else if (TaskTypeEnums.fifteendays_followup.name().equals(type)){
			result = "十五日回访";
		}else if (TaskTypeEnums.onemonth_flollowup.name().equals(type)){
			result = "一个月回访";
		}else if (TaskTypeEnums.compulsory_insurance_remind.name().equals(type)){
			result = "强险到期";
		}else if (TaskTypeEnums.commercial_insurance_remind.name().equals(type)){
			result = "商业险到期";
		}else if (TaskTypeEnums.quality_assurance_remind.name().equals(type)){
			result = "质保到期";
		}else if (TaskTypeEnums.maintain_remind.name().equals(type)){
			result = "保养到期";
		}else if (TaskTypeEnums.annual_survey_remind.name().equals(type)){
			result = "年检";
		}else if (TaskTypeEnums.boughtday_solicitude.name().equals(type)){
			result = "购车纪念日";
		}
		return result;
	}

	@Override
    public BaseResult insertOrUpdateInfo(SaveCarAndCustomeInfoRequest vo, int type) {
        if (type == 0) {
            //如果Type=0 代表是添加操作
			EntityWrapper<CarsokCustomer> checkExist = new EntityWrapper<CarsokCustomer>();
            checkExist.eq("mobile", vo.getMobile()).eq("account_id", vo.getAccountId());
            List<CarsokCustomer> checkResult = carsokCustomerMapper.selectList(checkExist);
			if (checkResult.size()==0){
				CarsokCustomer carsokCustomer = new CarsokCustomer();
				DozerMapperUtil.getInstance().map(vo, carsokCustomer);
				carsokCustomer.setRemark(null);
				carsokCustomer.setCreateTime(new Date());
				carsokCustomerMapper.insert(carsokCustomer);
				CarsokCustomerTenureCar carsokCustomerTenureCar = new CarsokCustomerTenureCar();
				DozerMapperUtil.getInstance().map(vo, carsokCustomerTenureCar);
				carsokCustomerTenureCar.setCustomerId(carsokCustomer.getId());
				carsokCustomerTenureCar.setIsNewRecord(1);
				carsokCustomerTenureCar.setIsDone(1);
				carsokCustomerTenureCar.setCreateTime(new Date());
				carsokCustomerTenureCarMapper.insert(carsokCustomerTenureCar);
				this.runSingleTask(carsokCustomerTenureCar,null,carsokCustomer,null);
			}
			else {
                return BaseResult.fail("0017","电话号码已经存在");
            }
        } else {
            //如果Type=1 代表是更新操作
            CarsokCustomer carsokCustomer = new CarsokCustomer();
            DozerMapperUtil.getInstance().map(vo, carsokCustomer);
            carsokCustomer.setUpdateTime(new Date());
            carsokCustomer.setRemark(null);
            carsokCustomer.setId(vo.getCustomerId());
            ObjectDiffExcutor<CarsokCustomer> objectCustomerDiffExcutor = new ObjectDiffExcutor();
            CarsokCustomer oldCustomerInfo = carsokCustomerMapper.selectById(vo.getCustomerId());
            logger.info(String.format("SET之前的子账号ID : "+ carsokCustomer.getChildId()));
			carsokCustomer.setAccountId(oldCustomerInfo.getAccountId());
			carsokCustomer.setChildId(oldCustomerInfo.getChildId());
			logger.info(String.format("SET之后的子账号ID : "+ carsokCustomer.getChildId()));
            String customerMessage = objectCustomerDiffExcutor.compareObjectWitTemplate(oldCustomerInfo, carsokCustomer, 2);
            carsokCustomerMapper.updateById(carsokCustomer);
            CarsokCustomerTenureCar carsokCustomerTenureCar = new CarsokCustomerTenureCar();
            DozerMapperUtil.getInstance().map(vo, carsokCustomerTenureCar);
            carsokCustomerTenureCar.setUpdateTime(new Date());
            carsokCustomerTenureCar.setIsDone(1);
            carsokCustomerTenureCar.setCustomerId(vo.getCustomerId());
            CarsokCustomerTenureCar getOldCustomerTenureCar = new CarsokCustomerTenureCar();
            getOldCustomerTenureCar.setCustomerId(carsokCustomerTenureCar.getCustomerId());
            getOldCustomerTenureCar.setId(carsokCustomerTenureCar.getId());
            CarsokCustomerTenureCar oldCustomerTenureCarInfo = carsokCustomerTenureCarMapper.selectOne(getOldCustomerTenureCar);
			carsokCustomerTenureCar.setAccountId(oldCustomerTenureCarInfo.getAccountId());
			carsokCustomerTenureCar.setChildId(oldCustomerTenureCarInfo.getChildId());
            ObjectDiffExcutor<CarsokCustomerTenureCar> objectCarDiffExcutor = new ObjectDiffExcutor();
            String carMessage = objectCarDiffExcutor.compareObjectWitTemplate(oldCustomerTenureCarInfo, carsokCustomerTenureCar, 2);
            carsokCustomerTenureCarMapper.update(carsokCustomerTenureCar, new EntityWrapper<CarsokCustomerTenureCar>().eq("customer_id", carsokCustomerTenureCar.getCustomerId()).eq("id", carsokCustomerTenureCar.getId()));
            this.runSingleTask(carsokCustomerTenureCar,oldCustomerTenureCarInfo,carsokCustomer,oldCustomerInfo);
            String message;
            if(!StringUtil.isEmpty(customerMessage)){
				if(!StringUtil.isEmpty(carMessage)){
					message = customerMessage+ "," + carMessage;
				}else {
					message = customerMessage;
				}
			}else {
				message = carMessage;
			}
			if(!StringUtil.isEmpty(message)){
				CarsokRecord carsokRecord = new CarsokRecord();
				carsokRecord.setOutId(carsokCustomerTenureCar.getId());
				carsokRecord.setMessage(message);
				carsokRecord.setAccountId(vo.getAccountId());
				carsokRecord.setChildId(vo.getChildId());
				carsokRecord.setModel("2");
				carsokRecord.setType("编辑信息");
				carsokRecord.setCreateTime(new Date());
				carsokRecordMapper.insert(carsokRecord);
			}
			boolean needPush = false;
			if (oldCustomerTenureCarInfo.getIsDone()==0 || !StringUtil.isEmpty(message)){
				//需要推送的条件之一
				needPush = true;
			}
			//推送买家端
			if(oldCustomerTenureCarInfo.getProductId() != null && needPush){//productId不为空, 且有内容修改
				try{
					Acount acount = acountMapper.selectByPrimaryKey(carsokCustomer.getAccountId());
					//信息推送给买家端
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String url = buyerSide;
					Map<String, Object> map = new HashedMap();
					BuyerInfo buyerInfo = new BuyerInfo();
					// 车和客户id------------------------------------------------------
					buyerInfo.setCustomerId(String.valueOf(carsokCustomer.getId()));
					buyerInfo.setTenureCarId(String.valueOf(carsokCustomerTenureCar.getId()));
					//----------------------------------------------------------------
					buyerInfo.setLicensePlate(carsokCustomerTenureCar.getTenureCarnum()!=null?carsokCustomerTenureCar.getTenureCarnum():"");
					buyerInfo.setVinCode(carsokCustomerTenureCar.getTenureVin()!=null?carsokCustomerTenureCar.getTenureVin():"");
					buyerInfo.setSellerId(String.valueOf(carsokCustomer.getAccountId()));
					JSONObject carModel=new JSONObject();
					String carName = carsokCustomerTenureCar.getTenureCarname();
					String carNames [] = carName.split(" ");
					int length=carNames.length;
					if(length>3){
						carModel.put("carBrand",carNames[0] );
						carModel.put("carSeries",carNames[1] );
						String simpleModel="";
						for(int i=2;i<length;i++){
							simpleModel=simpleModel+carNames[i];
						}
						carModel.put("carModel",simpleModel );
					}
					else{
						carModel.put("carBrand",carNames[0] );
						carModel.put("carModel",carNames[1] );
					}

					Picture pictureQuery = new Picture();
					pictureQuery.setProductId(oldCustomerTenureCarInfo.getProductId());
					short pictype = 1;
					pictureQuery.setType(pictype); //只查询主图
					List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);

					PruductOldcar p = new PruductOldcar();
					p.setPruductId(oldCustomerTenureCarInfo.getProductId());
					PruductOldcar pruductOldcar = pruductOldcarMapper.selectByModel(p);
					if(pruductOldcar!=null){
						carModel.put("province",pruductOldcar.getProvince());
						carModel.put("city",pruductOldcar.getCity());
						carModel.put("firstUpTime",pruductOldcar.getFirstUpTime());
					}
					carModel.put("carId",oldCustomerTenureCarInfo.getProductId());
					carModel.put("merchantDescription",acount.getMerchantDescr());
					carModel.put("carImage", pictureList.get(0).getPicPath());
					ChildAccount childAccount=new ChildAccount();
					childAccount.setId(carsokCustomer.getChildId());
					childAccount= childAccountMapper.selectByModel(childAccount);
					carModel.put("sellerName",childAccount==null? (acount.getNick()==null?acount.getMerchantName():acount.getNick()):childAccount.getChildAccountName());
					buyerInfo.setCarModel(carModel);
					buyerInfo.setSalesPrice(carsokCustomerTenureCar.getTenureCarprice()!=null?carsokCustomerTenureCar.getTenureCarprice().toString():"");
					buyerInfo.setSalesMileage(carsokCustomerTenureCar.getCarMiles()!=null?carsokCustomerTenureCar.getCarMiles():"");
					buyerInfo.setPaymentType(carsokCustomerTenureCar.getTenureCartype()!=null?carsokCustomerTenureCar.getTenureCartype():"");
					buyerInfo.setCompulsoryInsuranceDate(carsokCustomerTenureCar.getTenureCompulsory()!=null?formatter.format(carsokCustomerTenureCar.getTenureCompulsory()):"");
					buyerInfo.setCommercialInsuranceDate(carsokCustomerTenureCar.getTenureBusiness()!=null?formatter.format(carsokCustomerTenureCar.getTenureBusiness()):"");
					buyerInfo.setMaintainDate(carsokCustomerTenureCar.getTenureMaintain()!=null?formatter.format(carsokCustomerTenureCar.getTenureMaintain()):"");
					buyerInfo.setWarrantyDueDate(carsokCustomerTenureCar.getTenureWarranty()!=null?formatter.format(carsokCustomerTenureCar.getTenureWarranty()):"");
					buyerInfo.setSalesConsultant(childAccount==null? acount.getAccount():childAccount.getChildAccountMobile());
					buyerInfo.setFromApp("CHESHANG_APP");
					CustomerInfo customerInfo = new CustomerInfo();
					customerInfo.setPhone(carsokCustomer.getMobile());
					customerInfo.setName(carsokCustomer.getName());
					buyerInfo.setCustomerInfo(customerInfo);
					SellerInfo sellerInfo = new SellerInfo();
					sellerInfo.setThirdId(carsokCustomer.getAccountId()+"");
					sellerInfo.setFrom("CHESHANG_APP");
					sellerInfo.setInfoUrl(storeUrl + acount.getAccountCode()+".html");
					sellerInfo.setMerchantName(acount.getMerchantName());
					sellerInfo.setName(StringUtils.isEmpty(acount.getRealName())?acount.getMerchantName():acount.getRealName());
					sellerInfo.setAddress(acount.getMerchantAddress());
					sellerInfo.setPhone(acount.getAccount());
					buyerInfo.setSellerInfo(sellerInfo);
					buyerInfo.setBuyerPhone(carsokCustomer.getMobile());
					//查询该车相关所有的服务
					List<CarsokCustomerTenureServe> serviceList = carsokCustomerTenureServeMapper.selectList(new EntityWrapper<CarsokCustomerTenureServe>().eq("tenure_carId", carsokCustomerTenureCar.getId()).orderBy("create_time", false));
					List<CarService> list = new ArrayList<>();
					int rescueNum = 0;
					for(CarsokCustomerTenureServe cs : serviceList){
						CarService carService = new CarService();
						carService.setAutoServiceType(cs.getType());
						if("RESCUE".equals(cs.getType())){
							rescueNum++;
						}
						if("INSPECTION".equals(cs.getType()) && StringUtil.isEmpty(buyerInfo.getLastMiantenanceDate())){
							//上次保养日期
							buyerInfo.setLastMiantenanceDate(formatter.format(cs.getCreateTime()));
						}
						if(cs.getServerTime() != null){
							carService.setAutoServiceDate(formatter.format(cs.getServerTime()));
						}
						if(cs.getMoney() != null){
							carService.setAutoServiceMoney(String.valueOf(cs.getMoney()));
						}
						if(!StringUtil.isEmpty(cs.getInfo())){
							carService.setAutoServiceContent(cs.getInfo());
						}
						if(!StringUtil.isEmpty(cs.getPicturesUrls())){
							carService.setAutoServiceUrl(cs.getPicturesUrls());
						}
						list.add(carService);
					}
					buyerInfo.setCarService(list);
					//救援次数
					buyerInfo.setRescueTime(rescueNum);
					String param = JSONObject.toJSONString(buyerInfo);
//					BaseEvent event = new BaseEvent();
//					event.setData(param);
//					event.setEventName(EventConstants.PUSH_AUTO_INFO_TO_BUYER_APP_EVENT);
//					eventBus.publish(event);
					String flag = HttpClientUtil.sendPostRequestByJava(buyerSide,param);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
        }
        return BaseResult.success();
    }

    @Override
    public BaseResult findDetailInfo(FindDetailInfoRequest vo) {
        CarsokCustomerTenureCar carsokCustomerTenureCar
                = carsokCustomerTenureCarMapper.selectById(vo.getTenureCarId());
        //销售经理
		String belongTo = "主账号";
		if (carsokCustomerTenureCar.getChildId() != 0){//说明属于子账号
			//查询子账号名
			CarsokChildAccount belongToChild = carsokChildAccountMapper.selectById(carsokCustomerTenureCar.getChildId());
			belongTo = belongToChild.getChildAccountName();
		}
        CarsokCustomer customerInfo
                = carsokCustomerMapper.selectById(carsokCustomerTenureCar.getCustomerId());
        com.baomidou.mybatisplus.plugins.Page<CarsokRecord> page = new com.baomidou.mybatisplus.plugins.Page<CarsokRecord>(1, 5);
        EntityWrapper<CarsokRecord> ew = new EntityWrapper<CarsokRecord>();
        ew.eq("out_id", vo.getTenureCarId());
        ew.eq("model", 2);
        ew.orderBy("create_time", false);
        List<CarsokRecord> CarsokRecordList = carsokRecordMapper.selectPage(page, ew);
        List<CarsokFlowmsgResponse> carsokFlowmsgResponseList
                = new ArrayList<CarsokFlowmsgResponse>();
        for (CarsokRecord cr : CarsokRecordList) {
            CarsokFlowmsgResponse re = new CarsokFlowmsgResponse();
            DozerMapperUtil.getInstance().map(cr, re);
			if (cr.getChildId()==0){
				re.setCreatorRole("主账号");
				CarsokAcount C = carsokAcountMapper.selectById(cr.getAccountId());
				if(!StringUtil.isEmpty(C.getNick())) {
                    re.setCreator(C.getNick());
                }else {
                    re.setCreator("主账号");
                }
			}else {// 子账号, 需要判断跟进记录中的子账号是否被删除
				CarsokChildAccount child = carsokChildAccountMapper.selectById(cr.getChildId());
				if (child != null){
					re.setCreator(child.getChildAccountName());
				}else {
					re.setCreator("离职员工");
				}
				EntityWrapper<CarsokAccountPower> filter = new EntityWrapper<CarsokAccountPower>();
				filter.eq("child_id",cr.getChildId());
				List<CarsokAccountPower> CarsokAccountPowerList=carsokAccountPowerMapper.selectList(filter);
				String role="";
				int checkNum=0;
				String[] chooseRole = new String[CarsokAccountPowerList.size()];
				for (CarsokAccountPower v : CarsokAccountPowerList) {
					chooseRole[checkNum] = v.getPowerName();
					checkNum++;
				}
				if (Arrays.asList(chooseRole).contains("byjlgl")) {
					role = "经理";
				} else if (Arrays.asList(chooseRole).contains("bykfdp")) {
					role = "客服";
				} else if (Arrays.asList(chooseRole).contains("byyxgw")) {
					role = "销售";
				}
				re.setCreatorRole(role);
			}
            carsokFlowmsgResponseList.add(re);
        }
        List<CarsokTenureTaskRes> carsokTenureTaskResList
                = new ArrayList<CarsokTenureTaskRes>();
        //将人关联的ID加入任务列表
        FilterSQLParam sqlParam = new FilterSQLParam();
        sqlParam.setSqlTemplate(" business_id='" + carsokCustomerTenureCar.getId() + "' AND module='" +ModuleEnums.retaincustomer +"' AND ((task_status='"+TaskStatusEnums.ready+"' AND DATE_FORMAT(scheduled_time,'%Y-%m-%d')=DATE_FORMAT(NOW(),'%Y-%m-%d')) OR (task_status='"+TaskStatusEnums.delay+"'))");
        PageInfo<CarsokTenureTask> pageInfo = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam);
        for (CarsokTenureTask res : pageInfo.getList()) {
            CarsokTenureTaskRes r = new CarsokTenureTaskRes();
            DozerMapperUtil.getInstance().map(res, r);
			r.setType(this.getTypeString(res.getType()));
            carsokTenureTaskResList.add(r);
        }
        //将车关联的ID加入到任务列表
        FilterSQLParam sqlParamTwo = new FilterSQLParam();
        sqlParamTwo.setSqlTemplate(" business_id='" + customerInfo.getId() + "' AND module='" + ModuleEnums.retaincustomer + "' AND ((task_status='"+TaskStatusEnums.ready+"' AND DATE_FORMAT(scheduled_time,'%Y-%m-%d')=DATE_FORMAT(NOW(),'%Y-%m-%d')) OR (task_status='"+TaskStatusEnums.delay+"'))");
        PageInfo<CarsokTenureTask> pageInfoTwo = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParamTwo);
        for (CarsokTenureTask res : pageInfoTwo.getList()) {
            CarsokTenureTaskRes r = new CarsokTenureTaskRes();
            DozerMapperUtil.getInstance().map(res, r);
            r.setType(this.getTypeString(r.getType()));
            carsokTenureTaskResList.add(r);
        }
        FindDetailInfoResponse res = new FindDetailInfoResponse();
        DozerMapperUtil.getInstance().map(carsokCustomerTenureCar,res);
        DozerMapperUtil.getInstance().map(customerInfo,res);
        res.setCustomerId(customerInfo.getId());
        res.setTenureCarId(carsokCustomerTenureCar.getId());
        res.setRemark(carsokCustomerTenureCar.getRemark());
        res.setCustomerMobile(customerInfo.getMobile());
        res.setCustomerName(customerInfo.getName());
        res.setTenureCarPrice(carsokCustomerTenureCar.getTenureCarprice());
        res.setTenureCarNum(carsokCustomerTenureCar.getTenureCarnum());
        res.setTenureCarType(carsokCustomerTenureCar.getTenureCartype());
        res.setTenureCarname(carsokCustomerTenureCar.getTenureCarname());
        Map<String, Object> map = new HashMap<String, Object>();
		String loginName = "";
		String loginRole = "";
		if (vo.getChildId()==0){
			loginRole = "主账号";
			CarsokAcount C = carsokAcountMapper.selectById(vo.getAccountId());
			loginName = C.getNick();
		}else {
			CarsokChildAccount child = carsokChildAccountMapper.selectById(vo.getChildId());
			loginName = child.getChildAccountName();
			EntityWrapper<CarsokAccountPower> filter = new EntityWrapper<CarsokAccountPower>();
			filter.eq("child_id",vo.getChildId());
			List<CarsokAccountPower> CarsokAccountPowerList=carsokAccountPowerMapper.selectList(filter);
			int checkNum=0;
			String[] chooseRole = new String[CarsokAccountPowerList.size()];
			for (CarsokAccountPower v : CarsokAccountPowerList) {
				chooseRole[checkNum] = v.getPowerName();
				checkNum++;
			}
			if (Arrays.asList(chooseRole).contains("byjlgl")) {
				loginRole = "经理";
			} else if (Arrays.asList(chooseRole).contains("bykfdp")) {
				loginRole = "客服";
			} else if (Arrays.asList(chooseRole).contains("byyxgw")) {
				loginRole = "销售";
			}
		}
		map.put("loginName",loginName);
		map.put("loginRole",loginRole);
		map.put("belongTo",belongTo);
        map.put("tenureInfo" , res);
        map.put("followInfo" , carsokFlowmsgResponseList);
        //tenureTaskInfo 存放前五位
		if (!carsokTenureTaskResList.isEmpty()){
		if(carsokTenureTaskResList.size()>5) {
			map.put("tenureTaskInfo", carsokTenureTaskResList.subList(0, 5));
		}
		else {
			map.put("tenureTaskInfo", carsokTenureTaskResList.subList(0, carsokTenureTaskResList.size()));
		}}
		else {
			map.put("tenureTaskInfo", carsokTenureTaskResList);
		}
        return BaseResult.success(map);
    }



	public void runTenureCarTask(CarsokCustomerTenureCar tenureCar, CarsokCustomerTenureCar oldCar){
		if (oldCar == null){
			oldCar = new CarsokCustomerTenureCar();
		}
		DateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat md = new SimpleDateFormat("MM-dd");
		DateFormat y = new SimpleDateFormat("yyyy");
		String nowYMD = ymd.format(new Date());
		String nowMD = md.format(new Date());
		String nowY = y.format(new Date());
		//先查出所有相关的就绪/完成/逾期任务
		FilterSQLParam sqlParam = new FilterSQLParam();
		sqlParam.setSqlTemplate(" business_id="+ tenureCar.getId() + " AND module='" + ModuleEnums.retaincustomer + "' AND task_status IN ('"+ TaskStatusEnums.ready +"','"+ TaskStatusEnums.finish +"','"+ TaskStatusEnums.delay +"') AND json_extract(extra_fields,'$.nextIsDone')!=1");
		PageInfo<CarsokTenureTask> pageInfo = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam);
		if (tenureCar.getSaleTime() != null){
			//购车纪念日
			dealTaskBySingle(tenureCar.getId(), tenureCar.getAccountId(), tenureCar.getChildId(), pageInfo.getList(), TaskTypeEnums.boughtday_solicitude, tenureCar.getSaleTime(), oldCar.getSaleTime());
			//回访任务
			Calendar c = Calendar.getInstance();
			c.setTime(tenureCar.getSaleTime());	//设置初始日期
			c.add(Calendar.DATE, 3); 	//日期加3天
			Date threeDayRevisitTime = c.getTime();
			//清除时分秒的影响, 方便后面时间以天为单位比对
			String clearHms = ymd.format(threeDayRevisitTime);
			threeDayRevisitTime = DateUtil.StringToDate(clearHms);
			c.setTime(threeDayRevisitTime);
			c.add(Calendar.DATE, 12); 	//日期再加12天
			Date fifteenDayRevisitTime = c.getTime();
			c.add(Calendar.DATE, 15); 	//日期再加15天
			Date oneMonthRevisitTime = c.getTime();
			if(oldCar.getSaleTime() != null){//说明是走的更新方法
				//判断是否有修改
				if (tenureCar.getSaleTime().getTime() != oldCar.getSaleTime().getTime()){
					//不一样, 竟然修改了...Dawn!Fucking Retarded child!Unbelievable that you can`t remember the day of sold car!
					//找到原来的任务, 终止掉, finish状态的增加nextIsDone=1
					CarsokTenureTask threeDayTask = isTaskExist(pageInfo.getList(), TaskTypeEnums.threedays_followup);
					if(threeDayTask.getType() == null){
						CarsokTenureTask fifteenDayTask = isTaskExist(pageInfo.getList(), TaskTypeEnums.fifteendays_followup);
						if(fifteenDayTask.getType() == null){
							CarsokTenureTask oneMonthTask = isTaskExist(pageInfo.getList(), TaskTypeEnums.onemonth_flollowup);
							if(oneMonthTask.getType() == null){
								//都没有, 说明之前的回访都完成了.不用终止了.
							}else{
								if(iTaskFacade.deleteTaskById(oneMonthTask.getId(), null, null)){
									Map oldmap = JSON.parseObject(oneMonthTask.getExtraFields());
									if(oldmap.containsKey("nextIsDone")){
										oldmap.put("nextIsDone",1);
									}
									iTaskFacade.updateExtraData(oneMonthTask.getId(), oldmap, null, null);
								}
							}
						}else{
							if(iTaskFacade.deleteTaskById(fifteenDayTask.getId(), null, null)){
								Map oldmap = JSON.parseObject(fifteenDayTask.getExtraFields());
								if(oldmap.containsKey("nextIsDone")){
									oldmap.put("nextIsDone",1);
								}
								iTaskFacade.updateExtraData(fifteenDayTask.getId(), oldmap, null, null);
							}
						}
					}else{
						if(iTaskFacade.deleteTaskById(threeDayTask.getId(), null, null)){
							Map oldmap = JSON.parseObject(threeDayTask.getExtraFields());
							if(oldmap.containsKey("nextIsDone")){
								oldmap.put("nextIsDone",1);
							}
							iTaskFacade.updateExtraData(threeDayTask.getId(), oldmap, null, null);
						}
					}
					//再根据修改后的时间判断当前时间距离哪一个ready节点较近, 插入一条相关回访记录
					Date nowWithoutHms = DateUtil.StringToDate(nowYMD);
					if(nowWithoutHms.getTime() <= threeDayRevisitTime.getTime()){//now在3日回访日之前, 插入一条3日回访任务
						TaskInitParam newTask = new TaskInitParam();
						newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
						newTask.setModule(ModuleEnums.retaincustomer);
						newTask.setScheduled_time(threeDayRevisitTime);
						newTask.setType(TaskTypeEnums.threedays_followup);
						Map map = new HashMap();
						map.put("nextIsDone",0);
						map.put("accountId",tenureCar.getAccountId());
						map.put("childId",tenureCar.getChildId());
						newTask.setExtraFields(map);
						iTaskFacade.createTask(newTask);
					}else {
						if(nowWithoutHms.getTime() <= fifteenDayRevisitTime.getTime()){//now在3日回访日之后,15日回访日之前, 插入一条3日回访任务
							TaskInitParam newTask = new TaskInitParam();
							newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
							newTask.setModule(ModuleEnums.retaincustomer);
							newTask.setScheduled_time(fifteenDayRevisitTime);
							newTask.setType(TaskTypeEnums.fifteendays_followup);
							Map map = new HashMap();
							map.put("nextIsDone",0);
							map.put("accountId",tenureCar.getAccountId());
							map.put("childId",tenureCar.getChildId());
							newTask.setExtraFields(map);
							iTaskFacade.createTask(newTask);
						}else {
							if(nowWithoutHms.getTime() <= oneMonthRevisitTime.getTime()){//now在15日回访日之后,一个月回访日之前, 插入一条3日回访任务
								TaskInitParam newTask = new TaskInitParam();
								newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
								newTask.setModule(ModuleEnums.retaincustomer);
								newTask.setScheduled_time(oneMonthRevisitTime);
								newTask.setType(TaskTypeEnums.onemonth_flollowup);
								Map map = new HashMap();
								map.put("nextIsDone",0);
								map.put("accountId",tenureCar.getAccountId());
								map.put("childId",tenureCar.getChildId());
								newTask.setExtraFields(map);
								iTaskFacade.createTask(newTask);
							}else{
								//now在一个月回访日之后, 不处理
							}
						}
					}
				}else{//没改, 跟新增一样处理
					CarsokTenureTask threeDayTask = isTaskExist(pageInfo.getList(), TaskTypeEnums.threedays_followup);
					//判断3日回访任务处于什么状态
					if(TaskStatusEnums.delay.toString().equals(threeDayTask.getTaskStatus())){
						//3日回访逾期, 判断今天是不是15日回访当天
						String oldThree = ymd.format(threeDayTask.getScheduledTime());
						if(nowYMD.equals(oldThree)){
							//是, 终止3日回访, 新增一条15日回访任务
							if(iTaskFacade.deleteTaskById(threeDayTask.getId(), null, null)){
								TaskInitParam newTask = new TaskInitParam();
								newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
								newTask.setModule(ModuleEnums.retaincustomer);
								newTask.setScheduled_time(new Date());
								newTask.setType(TaskTypeEnums.fifteendays_followup);
								Map map = new HashMap();
								map.put("nextIsDone",0);
								map.put("accountId",tenureCar.getAccountId());
								map.put("childId",tenureCar.getChildId());
								newTask.setExtraFields(map);
								iTaskFacade.createTask(newTask);
							}
						}else {
							//不是, 不处理
						}
					}else if(TaskStatusEnums.finish.toString().equals(threeDayTask.getTaskStatus())){
						//3日回访完成, 原任务增加nextIsDone=1, 新增15日回访任务
						Map oldmap = JSON.parseObject(threeDayTask.getExtraFields());
						if(oldmap.containsKey("nextIsDone")){
							oldmap.put("nextIsDone",1);
						}
						if (iTaskFacade.updateExtraData(threeDayTask.getId(), oldmap, null, null)){
							TaskInitParam newTask = new TaskInitParam();
							newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
							newTask.setModule(ModuleEnums.retaincustomer);
							newTask.setScheduled_time(fifteenDayRevisitTime);
							newTask.setType(TaskTypeEnums.fifteendays_followup);
							Map map = new HashMap();
							map.put("nextIsDone",0);
							map.put("accountId",tenureCar.getAccountId());
							map.put("childId",tenureCar.getChildId());
							newTask.setExtraFields(map);
							iTaskFacade.createTask(newTask);
						}
					}else if(StringUtil.isEmpty(threeDayTask.getTaskStatus())){
						//没有3日回访任务, 判断15日回访任务处于什么状态
						CarsokTenureTask fifteenDayTask = isTaskExist(pageInfo.getList(), TaskTypeEnums.fifteendays_followup);
						if(TaskStatusEnums.delay.toString().equals(fifteenDayTask.getTaskStatus())){
							//15日回访逾期, 判断今天是不是一个月回访当天
							String oldFifteen = ymd.format(fifteenDayTask.getScheduledTime());
							if(nowYMD.equals(oldFifteen)){
								//是, 终止15日回访, 新增一条一个月回访任务
								if(iTaskFacade.deleteTaskById(fifteenDayTask.getId(), null, null)){
									TaskInitParam newTask = new TaskInitParam();
									newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
									newTask.setModule(ModuleEnums.retaincustomer);
									newTask.setScheduled_time(new Date());
									newTask.setType(TaskTypeEnums.onemonth_flollowup);
									Map map = new HashMap();
									map.put("nextIsDone",0);
									map.put("accountId",tenureCar.getAccountId());
									map.put("childId",tenureCar.getChildId());
									newTask.setExtraFields(map);
									iTaskFacade.createTask(newTask);
								}
							}else {
								//不是, 不处理
							}
						}else if(TaskStatusEnums.finish.toString().equals(fifteenDayTask.getTaskStatus())){
							//15日回访完成, 原任务增加nextIsDone=1, 新增一个月回访任务
							Map oldmap = JSON.parseObject(fifteenDayTask.getExtraFields());
							if(oldmap.containsKey("nextIsDone")){
								oldmap.put("nextIsDone",1);
							}
							if (iTaskFacade.updateExtraData(fifteenDayTask.getId(), oldmap, null, null)){
								TaskInitParam newTask = new TaskInitParam();
								newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
								newTask.setModule(ModuleEnums.retaincustomer);
								newTask.setScheduled_time(oneMonthRevisitTime);
								newTask.setType(TaskTypeEnums.onemonth_flollowup);
								Map map = new HashMap();
								map.put("nextIsDone",0);
								map.put("accountId",tenureCar.getAccountId());
								map.put("childId",tenureCar.getChildId());
								newTask.setExtraFields(map);
								iTaskFacade.createTask(newTask);
							}
						}else if(StringUtil.isEmpty(fifteenDayTask.getTaskStatus())){
							//没有15日回访任务, 判断一个月回访任务处于什么状态
							CarsokTenureTask oneMonthTask = isTaskExist(pageInfo.getList(), TaskTypeEnums.onemonth_flollowup);
							if(TaskStatusEnums.delay.toString().equals(oneMonthTask.getTaskStatus())){
								//一个月回访逾期, 不处理
							}else if(TaskStatusEnums.finish.toString().equals(oneMonthTask.getTaskStatus())){
								//一个月回访完成, 原任务增加nextIsDone=1
								Map oldmap = JSON.parseObject(oneMonthTask.getExtraFields());
								if(oldmap.containsKey("nextIsDone")){
									oldmap.put("nextIsDone",1);
								}
								iTaskFacade.updateExtraData(oneMonthTask.getId(), oldmap, null, null);
							}else if(StringUtil.isEmpty(oneMonthTask.getTaskStatus())){
								//没有15日回访任务, 说明是之前没有相关任务, 判断当前时间距离哪一个ready节点较近, 插入一条相关回访记录
								Date nowWithoutHms = DateUtil.StringToDate(nowYMD);
								if(nowWithoutHms.getTime() <= threeDayRevisitTime.getTime()){//now在3日回访日之前, 插入一条3日回访任务
									TaskInitParam newTask = new TaskInitParam();
									newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
									newTask.setModule(ModuleEnums.retaincustomer);
									newTask.setScheduled_time(threeDayRevisitTime);
									newTask.setType(TaskTypeEnums.threedays_followup);
									Map map = new HashMap();
									map.put("nextIsDone",0);
									map.put("accountId",tenureCar.getAccountId());
									map.put("childId",tenureCar.getChildId());
									newTask.setExtraFields(map);
									iTaskFacade.createTask(newTask);
								}else {
									if(nowWithoutHms.getTime() <= fifteenDayRevisitTime.getTime()){//now在3日回访日之后,15日回访日之前, 插入一条3日回访任务
										TaskInitParam newTask = new TaskInitParam();
										newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
										newTask.setModule(ModuleEnums.retaincustomer);
										newTask.setScheduled_time(fifteenDayRevisitTime);
										newTask.setType(TaskTypeEnums.fifteendays_followup);
										Map map = new HashMap();
										map.put("nextIsDone",0);
										map.put("accountId",tenureCar.getAccountId());
										map.put("childId",tenureCar.getChildId());
										newTask.setExtraFields(map);
										iTaskFacade.createTask(newTask);
									}else {
										if(nowWithoutHms.getTime() <= oneMonthRevisitTime.getTime()){//now在15日回访日之后,一个月回访日之前, 插入一条3日回访任务
											TaskInitParam newTask = new TaskInitParam();
											newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
											newTask.setModule(ModuleEnums.retaincustomer);
											newTask.setScheduled_time(oneMonthRevisitTime);
											newTask.setType(TaskTypeEnums.onemonth_flollowup);
											Map map = new HashMap();
											map.put("nextIsDone",0);
											map.put("accountId",tenureCar.getAccountId());
											map.put("childId",tenureCar.getChildId());
											newTask.setExtraFields(map);
											iTaskFacade.createTask(newTask);
										}else{
											//now在一个月回访日之后, 不处理
										}
									}
								}
							}else {
								//一个月回访就绪, 不处理
							}
						}else {
							//15日回访就绪, 不处理
						}
					}else {
						//3日回访就绪, 不处理
					}
				}
			}else{//说明是走的是新增或者定时任务的方法
				CarsokTenureTask threeDayTask = isTaskExist(pageInfo.getList(), TaskTypeEnums.threedays_followup);
				//判断3日回访任务处于什么状态
				if(TaskStatusEnums.delay.toString().equals(threeDayTask.getTaskStatus())){
					//3日回访逾期, 判断今天是不是15日回访当天
					String oldThree = ymd.format(threeDayTask.getScheduledTime());
					if(nowYMD.equals(oldThree)){
						//是, 终止3日回访, 新增一条15日回访任务
						if(iTaskFacade.deleteTaskById(threeDayTask.getId(), null, null)){
							TaskInitParam newTask = new TaskInitParam();
							newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
							newTask.setModule(ModuleEnums.retaincustomer);
							newTask.setScheduled_time(new Date());
							newTask.setType(TaskTypeEnums.fifteendays_followup);
							Map map = new HashMap();
							map.put("nextIsDone",0);
							map.put("accountId",tenureCar.getAccountId());
							map.put("childId",tenureCar.getChildId());
							newTask.setExtraFields(map);
							iTaskFacade.createTask(newTask);
						}
					}else {
						//不是, 不处理
					}
				}else if(TaskStatusEnums.finish.toString().equals(threeDayTask.getTaskStatus())){
					//3日回访完成, 原任务增加nextIsDone=1, 新增15日回访任务
					Map oldmap = JSON.parseObject(threeDayTask.getExtraFields());
					if(oldmap.containsKey("nextIsDone")){
						oldmap.put("nextIsDone",1);
					}
					if (iTaskFacade.updateExtraData(threeDayTask.getId(), oldmap, null, null)){
						TaskInitParam newTask = new TaskInitParam();
						newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
						newTask.setModule(ModuleEnums.retaincustomer);
						newTask.setScheduled_time(fifteenDayRevisitTime);
						newTask.setType(TaskTypeEnums.fifteendays_followup);
						Map map = new HashMap();
						map.put("nextIsDone",0);
						map.put("accountId",tenureCar.getAccountId());
						map.put("childId",tenureCar.getChildId());
						newTask.setExtraFields(map);
						iTaskFacade.createTask(newTask);
					}
				}else if(StringUtil.isEmpty(threeDayTask.getTaskStatus())){
					//没有3日回访任务, 判断15日回访任务处于什么状态
					CarsokTenureTask fifteenDayTask = isTaskExist(pageInfo.getList(), TaskTypeEnums.fifteendays_followup);
					if(TaskStatusEnums.delay.toString().equals(fifteenDayTask.getTaskStatus())){
						//15日回访逾期, 判断今天是不是一个月回访当天
						String oldFifteen = ymd.format(fifteenDayTask.getScheduledTime());
						if(nowYMD.equals(oldFifteen)){
							//是, 终止15日回访, 新增一条一个月回访任务
							if(iTaskFacade.deleteTaskById(fifteenDayTask.getId(), null, null)){
								TaskInitParam newTask = new TaskInitParam();
								newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
								newTask.setModule(ModuleEnums.retaincustomer);
								newTask.setScheduled_time(new Date());
								newTask.setType(TaskTypeEnums.onemonth_flollowup);
								Map map = new HashMap();
								map.put("nextIsDone",0);
								map.put("accountId",tenureCar.getAccountId());
								map.put("childId",tenureCar.getChildId());
								newTask.setExtraFields(map);
								iTaskFacade.createTask(newTask);
							}
						}else {
							//不是, 不处理
						}
					}else if(TaskStatusEnums.finish.toString().equals(fifteenDayTask.getTaskStatus())){
						//15日回访完成, 原任务增加nextIsDone=1, 新增一个月回访任务
						Map oldmap = JSON.parseObject(fifteenDayTask.getExtraFields());
						if(oldmap.containsKey("nextIsDone")){
							oldmap.put("nextIsDone",1);
						}
						if (iTaskFacade.updateExtraData(fifteenDayTask.getId(), oldmap, null, null)){
							TaskInitParam newTask = new TaskInitParam();
							newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
							newTask.setModule(ModuleEnums.retaincustomer);
							newTask.setScheduled_time(oneMonthRevisitTime);
							newTask.setType(TaskTypeEnums.onemonth_flollowup);
							Map map = new HashMap();
							map.put("nextIsDone",0);
							map.put("accountId",tenureCar.getAccountId());
							map.put("childId",tenureCar.getChildId());
							newTask.setExtraFields(map);
							iTaskFacade.createTask(newTask);
						}
					}else if(StringUtil.isEmpty(fifteenDayTask.getTaskStatus())){
						//没有15日回访任务, 判断一个月回访任务处于什么状态
						CarsokTenureTask oneMonthTask = isTaskExist(pageInfo.getList(), TaskTypeEnums.onemonth_flollowup);
						if(TaskStatusEnums.delay.toString().equals(oneMonthTask.getTaskStatus())){
							//一个月回访逾期, 不处理
						}else if(TaskStatusEnums.finish.toString().equals(oneMonthTask.getTaskStatus())){
							//一个月回访完成, 原任务增加nextIsDone=1
							Map oldmap = JSON.parseObject(oneMonthTask.getExtraFields());
							if(oldmap.containsKey("nextIsDone")){
								oldmap.put("nextIsDone",1);
							}
							iTaskFacade.updateExtraData(oneMonthTask.getId(), oldmap, null, null);
						}else if(StringUtil.isEmpty(oneMonthTask.getTaskStatus())){
							//没有15日回访任务, 说明是之前没有相关任务, 判断当前时间距离哪一个ready节点较近, 插入一条相关回访记录
							Date nowWithoutHms = DateUtil.StringToDate(nowYMD);
							if(nowWithoutHms.getTime() <= threeDayRevisitTime.getTime()){//now在3日回访日之前, 插入一条3日回访任务
								TaskInitParam newTask = new TaskInitParam();
								newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
								newTask.setModule(ModuleEnums.retaincustomer);
								newTask.setScheduled_time(threeDayRevisitTime);
								newTask.setType(TaskTypeEnums.threedays_followup);
								Map map = new HashMap();
								map.put("nextIsDone",0);
								map.put("accountId",tenureCar.getAccountId());
								map.put("childId",tenureCar.getChildId());
								newTask.setExtraFields(map);
								iTaskFacade.createTask(newTask);
							}else {
								if(nowWithoutHms.getTime() <= fifteenDayRevisitTime.getTime()){//now在3日回访日之后,15日回访日之前, 插入一条3日回访任务
									TaskInitParam newTask = new TaskInitParam();
									newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
									newTask.setModule(ModuleEnums.retaincustomer);
									newTask.setScheduled_time(fifteenDayRevisitTime);
									newTask.setType(TaskTypeEnums.fifteendays_followup);
									Map map = new HashMap();
									map.put("nextIsDone",0);
									map.put("accountId",tenureCar.getAccountId());
									map.put("childId",tenureCar.getChildId());
									newTask.setExtraFields(map);
									iTaskFacade.createTask(newTask);
								}else {
									if(nowWithoutHms.getTime() <= oneMonthRevisitTime.getTime()){//now在15日回访日之后,一个月回访日之前, 插入一条3日回访任务
										TaskInitParam newTask = new TaskInitParam();
										newTask.setBusiness_id(String.valueOf(tenureCar.getId()));
										newTask.setModule(ModuleEnums.retaincustomer);
										newTask.setScheduled_time(oneMonthRevisitTime);
										newTask.setType(TaskTypeEnums.onemonth_flollowup);
										Map map = new HashMap();
										map.put("nextIsDone",0);
										map.put("accountId",tenureCar.getAccountId());
										map.put("childId",tenureCar.getChildId());
										newTask.setExtraFields(map);
										iTaskFacade.createTask(newTask);
									}else{
										//now在一个月回访日之后, 不处理
									}
								}
							}
						}else {
							//一个月回访就绪, 不处理
						}
					}else {
						//15日回访就绪, 不处理
					}
				}else {
					//3日回访就绪, 不处理
				}
			}
		}
//		if (tenureCar.getTenureInspection() != null){
//			//年检
//			dealTaskBySingle(tenureCar.getId(), tenureCar.getAccountId(), tenureCar.getChildId(), pageInfo.getList(), TaskTypeEnums.annual_survey_remind, tenureCar.getTenureInspection(), oldCar.getTenureInspection());
//		}
		if (tenureCar.getTenureMaintain() != null){
			//保养
			dealTaskBySingle(tenureCar.getId(), tenureCar.getAccountId(), tenureCar.getChildId(), pageInfo.getList(), TaskTypeEnums.maintain_remind, tenureCar.getTenureMaintain(), oldCar.getTenureMaintain());
		}
		if (tenureCar.getTenureWarranty() != null){
			//质保
			dealTaskBySingle(tenureCar.getId(), tenureCar.getAccountId(), tenureCar.getChildId(), pageInfo.getList(), TaskTypeEnums.quality_assurance_remind, tenureCar.getTenureWarranty(), oldCar.getTenureWarranty());
		}
		if (tenureCar.getTenureBusiness() != null){
			//商业保险
			dealTaskBySingle(tenureCar.getId(), tenureCar.getAccountId(), tenureCar.getChildId(), pageInfo.getList(), TaskTypeEnums.commercial_insurance_remind, tenureCar.getTenureBusiness(), oldCar.getTenureBusiness());
		}
		if (tenureCar.getTenureCompulsory() != null){
			//强制保险
			dealTaskBySingle(tenureCar.getId(), tenureCar.getAccountId(), tenureCar.getChildId(), pageInfo.getList(), TaskTypeEnums.compulsory_insurance_remind, tenureCar.getTenureCompulsory(), oldCar.getTenureCompulsory());
		}
	}

	public void runCustomerTask(CarsokCustomer customer, CarsokCustomer oldCustomer){
		if (oldCustomer == null){
			oldCustomer = new CarsokCustomer();
		}
		//先查是否有生日关怀待办
		FilterSQLParam sqlParam = new FilterSQLParam();
		sqlParam.setSqlTemplate(" business_id="+ customer.getId() + " AND module='" + ModuleEnums.retaincustomer + "' AND task_status IN ('"+ TaskStatusEnums.ready +"','"+ TaskStatusEnums.finish +"','"+ TaskStatusEnums.delay +"') AND type='" + TaskTypeEnums.birthday_solicitude + "' AND json_extract(extra_fields,'$.nextIsDone')!=1");
		PageInfo<CarsokTenureTask> pageInfo = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlParam);
		//生日关怀
		if (customer.getBirthday() != null){
			dealTaskBySingle(customer.getId(), customer.getAccountId(), customer.getChildId(), pageInfo.getList(), TaskTypeEnums.birthday_solicitude, customer.getBirthday(), oldCustomer.getBirthday());
		}
	}

	/**
	 * 遍历任务list返回需要类型的任务(判断是否存在该类任务)
	 * @param list
	 * @param type
	 * @return
	 */
	private CarsokTenureTask isTaskExist(List<CarsokTenureTask> list, TaskTypeEnums type) {
		CarsokTenureTask result = new CarsokTenureTask();
		for (CarsokTenureTask ct : list){
			if (type.toString().equals(ct.getType())){
				result = ct;
			}
		}
		return result;
	}

	/**
	 * 处理"纪念日"类的保有任务
	 * @param businessId
	 * @param accountId
	 * @param childId
	 * @param list
	 * @param type
	 * @param newDate
	 */
	private void dealTaskBySingle(int businessId, int accountId, int childId, List<CarsokTenureTask> list, TaskTypeEnums type, Date newDate, Date oldDate){
		DateFormat ymdInt = new SimpleDateFormat("yyyyMMdd");
		DateFormat mdInt = new SimpleDateFormat("MMdd");
		DateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat md = new SimpleDateFormat("MM-dd");
		DateFormat y = new SimpleDateFormat("yyyy");
		String nowYMD = ymd.format(new Date());
		String nowMD = md.format(new Date());
		String nowY = y.format(new Date());
		String newDateYMD = ymd.format(newDate);
		CarsokTenureTask oldTask = isTaskExist(list, type);
		//
		if (oldDate != null){//说明走的更新方法, 要判断是否有修改
			if (newDate.getTime() != oldDate.getTime()){
				//我的天!还得考虑修改的情况!You can't make it right when you enter it?????Retarded child???Do you know how fucking much this bothers me???
				if(TaskStatusEnums.ready.toString().equals(oldTask.getTaskStatus()) || TaskStatusEnums.delay.toString().equals(oldTask.getTaskStatus())){
					//如果是ready或者delay的状态, 直接终止任务, 重新插入一条新的任务
					if(iTaskFacade.deleteTaskById(oldTask.getId(), null, null)){
						if(TaskTypeEnums.boughtday_solicitude.equals(type)){
							//购车纪念日
							TaskInitParam newTask = new TaskInitParam();
							newTask.setBusiness_id(String.valueOf(businessId));
							newTask.setModule(ModuleEnums.retaincustomer);
							String newTime ="";
							if(nowY.equals(y.format(newDate))){// 购车年份是今年
								// 任务的年份是购车的年份+1
								newTime = String.valueOf(Integer.valueOf(y.format(newDate))+1) + "-" + md.format(newDate);
							}else if(Integer.valueOf(nowY) > Integer.valueOf(y.format(newDate))){// 购车年份是今年之前
								// 任务的年份是当前年份
								newTime = nowY + "-" + md.format(newDate);
								// 再判断月日, 购车的月日是当前日期之前, 生成逾期任务
								if(Integer.valueOf(mdInt.format(newDate)) < Integer.valueOf(mdInt.format(new Date()))){
									newTask.setStatusEnums(TaskStatusEnums.delay);
								}
							}else if(Integer.valueOf(nowY) < Integer.valueOf(y.format(newDate))){// 购车年份是今年之后(穿越式购车, Holy fresh)
								// 任务的年份就是购车的年份
								newTime = ymd.format(newDate);
							}
							newTask.setScheduled_time(DateUtil.StringToDate(newTime));
							newTask.setType(type);
							Map map = new HashMap();
							map.put("nextIsDone",0);
							map.put("accountId",accountId);
							map.put("childId",childId);
							newTask.setExtraFields(map);
							iTaskFacade.createTask(newTask);
						}else{
							TaskInitParam newTask = new TaskInitParam();
							newTask.setBusiness_id(String.valueOf(businessId));
							newTask.setModule(ModuleEnums.retaincustomer);
							String newTime = ymd.format(newDate);
							if (Integer.valueOf(y.format(newDate)) < Integer.valueOf(nowY)){
								newTime = nowY + "-" + md.format(newDate);
							}
							Date scheduledTime = DateUtil.StringToDate(newTime);
							if(Integer.valueOf(ymdInt.format(scheduledTime)) < Integer.valueOf(ymdInt.format(new Date()))){
								// 判断这个执行时间是否小于今天, 是, 逾期
								newTask.setStatusEnums(TaskStatusEnums.delay);
							}
							newTask.setScheduled_time(scheduledTime);
							newTask.setType(type);
							Map map = new HashMap();
							map.put("nextIsDone",0);
							map.put("accountId",accountId);
							map.put("childId",childId);
							newTask.setExtraFields(map);
							iTaskFacade.createTask(newTask);
						}
					}
				}else if(TaskStatusEnums.finish.toString().equals(oldTask.getTaskStatus())){
					//如果是finish的状态, 原任务增加nextIsDone=1, 新增一条任务
					Map oldmap = JSON.parseObject(oldTask.getExtraFields());
					if(oldmap.containsKey("nextIsDone")){
						oldmap.put("nextIsDone",1);
					}
					if (iTaskFacade.updateExtraData(oldTask.getId(), oldmap, null, null)){
						if(TaskTypeEnums.boughtday_solicitude.equals(type)){
							//购车纪念日
							TaskInitParam newTask = new TaskInitParam();
							newTask.setBusiness_id(String.valueOf(businessId));
							newTask.setModule(ModuleEnums.retaincustomer);
							String newTime ="";
							if(nowY.equals(y.format(newDate))){// 购车年份是今年
								// 任务的年份是购车的年份+1
								newTime = String.valueOf(Integer.valueOf(y.format(newDate))+1) + "-" + md.format(newDate);
							}else if(Integer.valueOf(nowY) > Integer.valueOf(y.format(newDate))){// 购车年份是今年之前
								// 任务的年份是当前年份
								newTime = nowY + "-" + md.format(newDate);
								// 再判断月日, 购车的月日是当前日期之前, 生成逾期任务
								if(Integer.valueOf(mdInt.format(newDate)) < Integer.valueOf(mdInt.format(new Date()))){
									newTask.setStatusEnums(TaskStatusEnums.delay);
								}
							}else if(Integer.valueOf(nowY) < Integer.valueOf(y.format(newDate))){// 购车年份是今年之后(穿越式购车, Holy fresh)
								// 任务的年份就是购车的年份
								newTime = ymd.format(newDate);
							}
							newTask.setScheduled_time(DateUtil.StringToDate(newTime));
							newTask.setType(type);
							Map map = new HashMap();
							map.put("nextIsDone",0);
							map.put("accountId",accountId);
							map.put("childId",childId);
							newTask.setExtraFields(map);
							iTaskFacade.createTask(newTask);
						}else{
							TaskInitParam newTask = new TaskInitParam();
							newTask.setBusiness_id(String.valueOf(businessId));
							newTask.setModule(ModuleEnums.retaincustomer);
							String newTime = ymd.format(newDate);
							if (Integer.valueOf(y.format(newDate)) < Integer.valueOf(nowY)){
								newTime = nowY + "-" + md.format(newDate);
							}
							Date scheduledTime = DateUtil.StringToDate(newTime);
							if(Integer.valueOf(ymdInt.format(scheduledTime)) < Integer.valueOf(ymdInt.format(new Date()))){
								// 判断这个执行时间是否小于今天, 是, 逾期
								newTask.setStatusEnums(TaskStatusEnums.delay);
							}
							newTask.setScheduled_time(scheduledTime);
							newTask.setType(type);
							Map map = new HashMap();
							map.put("nextIsDone",0);
							map.put("accountId",accountId);
							map.put("childId",childId);
							newTask.setExtraFields(map);
							iTaskFacade.createTask(newTask);
						}
					}
				}
				//备注: 重新插入的修改后任务, 状态都是ready, 等第二天定时任务处理是否delay.
			}else{
				//虽然你没有修改, 但是我还是得写这段的处理, 真的僵...
				//但是好歹我能跟新增走一样, 只是重copy一段看着真的不干净
				//判断任务处于什么状态
				if(TaskStatusEnums.delay.toString().equals(oldTask.getTaskStatus())){
					//逾期
					//判断今天是不是该任务逾期的"最后一天",即今天是不是下一次任务的"例行当天"
					String old = md.format(oldTask.getScheduledTime());
					if(nowMD.equals(old)){
						//是, 原任务终止, 新增下一年/下一次的任务
						if(iTaskFacade.deleteTaskById(oldTask.getId(), null, null)){
							TaskInitParam newTask = new TaskInitParam();
							newTask.setBusiness_id(String.valueOf(businessId));
							newTask.setModule(ModuleEnums.retaincustomer);
							newTask.setScheduled_time(new Date());
							newTask.setType(type);
							Map map = new HashMap();
							map.put("nextIsDone",0);
							map.put("accountId",accountId);
							map.put("childId",childId);
							newTask.setExtraFields(map);
							iTaskFacade.createTask(newTask);
						}
					}else {
						//不是, 逾期, 不处理
					}
				}else if(TaskStatusEnums.finish.toString().equals(oldTask.getTaskStatus())){
					//完成, 原任务增加nextIsDone=1, 新增下一年/下一次的任务
					Map oldmap = JSON.parseObject(oldTask.getExtraFields());
					if(oldmap.containsKey("nextIsDone")){
						oldmap.put("nextIsDone",1);
					}
					if (iTaskFacade.updateExtraData(oldTask.getId(), oldmap, null, null)){
						TaskInitParam newTask = new TaskInitParam();
						newTask.setBusiness_id(String.valueOf(businessId));
						newTask.setModule(ModuleEnums.retaincustomer);
						Calendar c = Calendar.getInstance();
						c.setTime(oldTask.getScheduledTime());
						c.add(Calendar.YEAR, 1);
						newTask.setScheduled_time(c.getTime());
						newTask.setType(type);
						Map map = new HashMap();
						map.put("nextIsDone",0);
						map.put("accountId",accountId);
						map.put("childId",childId);
						newTask.setExtraFields(map);
						iTaskFacade.createTask(newTask);
					}
				}else if(StringUtil.isEmpty(oldTask.getTaskStatus())){
					//之前没有相关任务, 新增本年/本次的任务
					if(TaskTypeEnums.boughtday_solicitude.equals(type)){
						//购车纪念日
						TaskInitParam newTask = new TaskInitParam();
						newTask.setBusiness_id(String.valueOf(businessId));
						newTask.setModule(ModuleEnums.retaincustomer);
						String newTime ="";
						if(nowY.equals(y.format(newDate))){// 购车年份是今年
							// 任务的年份是购车的年份+1
							newTime = String.valueOf(Integer.valueOf(y.format(newDate))+1) + "-" + md.format(newDate);
						}else if(Integer.valueOf(nowY) > Integer.valueOf(y.format(newDate))){// 购车年份是今年之前
							// 任务的年份是当前年份
							newTime = nowY + "-" + md.format(newDate);
							// 再判断月日, 购车的月日是当前日期之前, 生成逾期任务
							if(Integer.valueOf(mdInt.format(newDate)) < Integer.valueOf(mdInt.format(new Date()))){
								newTask.setStatusEnums(TaskStatusEnums.delay);
							}
						}else if(Integer.valueOf(nowY) < Integer.valueOf(y.format(newDate))){// 购车年份是今年之后(穿越式购车, Holy fresh)
							// 任务的年份就是购车的年份
							newTime = ymd.format(newDate);
						}
						newTask.setScheduled_time(DateUtil.StringToDate(newTime));
						newTask.setType(type);
						Map map = new HashMap();
						map.put("nextIsDone",0);
						map.put("accountId",accountId);
						map.put("childId",childId);
						newTask.setExtraFields(map);
						iTaskFacade.createTask(newTask);
					}else{
						TaskInitParam newTask = new TaskInitParam();
						newTask.setBusiness_id(String.valueOf(businessId));
						newTask.setModule(ModuleEnums.retaincustomer);
						String newTime = ymd.format(newDate);
						if (Integer.valueOf(y.format(newDate)) < Integer.valueOf(nowY)){
							newTime = nowY + "-" + md.format(newDate);
						}
						Date scheduledTime = DateUtil.StringToDate(newTime);
						if(Integer.valueOf(ymdInt.format(scheduledTime)) < Integer.valueOf(ymdInt.format(new Date()))){
							// 判断这个执行时间是否小于今天, 是, 逾期
							newTask.setStatusEnums(TaskStatusEnums.delay);
						}
						newTask.setScheduled_time(scheduledTime);
						newTask.setType(type);
						Map map = new HashMap();
						map.put("nextIsDone",0);
						map.put("accountId",accountId);
						map.put("childId",childId);
						newTask.setExtraFields(map);
						iTaskFacade.createTask(newTask);
					}
				}else {
					//就绪状态, 不处理
				}
			}
		}else{//说明走的新增或者定时任务的方法
			//判断任务处于什么状态
			if(TaskStatusEnums.delay.toString().equals(oldTask.getTaskStatus())){
				//逾期
				//判断今天是不是该任务逾期的"最后一天",即今天是不是下一次任务的"例行当天"
				String old = md.format(oldTask.getScheduledTime());
				if(nowMD.equals(old)){
					//是, 原任务终止, 新增下一年/下一次的任务
					if(iTaskFacade.deleteTaskById(oldTask.getId(), null, null)){
						TaskInitParam newTask = new TaskInitParam();
						newTask.setBusiness_id(String.valueOf(businessId));
						newTask.setModule(ModuleEnums.retaincustomer);
						newTask.setScheduled_time(new Date());
						newTask.setType(type);
						Map map = new HashMap();
						map.put("nextIsDone",0);
						map.put("accountId",accountId);
						map.put("childId",childId);
						newTask.setExtraFields(map);
						iTaskFacade.createTask(newTask);
					}
				}else {
					//不是, 逾期, 不处理
				}
			}else if(TaskStatusEnums.finish.toString().equals(oldTask.getTaskStatus())){
				//完成, 原任务增加nextIsDone=1, 新增下一年/下一次的任务
				Map oldmap = JSON.parseObject(oldTask.getExtraFields());
				if(oldmap.containsKey("nextIsDone")){
					oldmap.put("nextIsDone",1);
				}
				if (iTaskFacade.updateExtraData(oldTask.getId(), oldmap, null, null)){
					TaskInitParam newTask = new TaskInitParam();
					newTask.setBusiness_id(String.valueOf(businessId));
					newTask.setModule(ModuleEnums.retaincustomer);
					Calendar c = Calendar.getInstance();
					c.setTime(oldTask.getScheduledTime());
					c.add(Calendar.YEAR, 1);
					newTask.setScheduled_time(c.getTime());
					newTask.setType(type);
					Map map = new HashMap();
					map.put("nextIsDone",0);
					map.put("accountId",accountId);
					map.put("childId",childId);
					newTask.setExtraFields(map);
					iTaskFacade.createTask(newTask);
				}
			}else if(StringUtil.isEmpty(oldTask.getTaskStatus())){
				//之前没有相关任务, 新增本年/本次的任务
				if(TaskTypeEnums.boughtday_solicitude.equals(type)){
					//购车纪念日
					TaskInitParam newTask = new TaskInitParam();
					newTask.setBusiness_id(String.valueOf(businessId));
					newTask.setModule(ModuleEnums.retaincustomer);
					String newTime ="";
					if(nowY.equals(y.format(newDate))){// 购车年份是今年
						// 任务的年份是购车的年份+1
						newTime = String.valueOf(Integer.valueOf(y.format(newDate))+1) + "-" + md.format(newDate);
					}else if(Integer.valueOf(nowY) > Integer.valueOf(y.format(newDate))){// 购车年份是今年之前
						// 任务的年份是当前年份
						newTime = nowY + "-" + md.format(newDate);
						// 再判断月日, 购车的月日是当前日期之前, 生成逾期任务
						if(Integer.valueOf(mdInt.format(newDate)) < Integer.valueOf(mdInt.format(new Date()))){
							newTask.setStatusEnums(TaskStatusEnums.delay);
						}
					}else if(Integer.valueOf(nowY) < Integer.valueOf(y.format(newDate))){// 购车年份是今年之后(穿越式购车, Holy fresh)
						// 任务的年份就是购车的年份
						newTime = ymd.format(newDate);
					}
					newTask.setScheduled_time(DateUtil.StringToDate(newTime));
					newTask.setType(type);
					Map map = new HashMap();
					map.put("nextIsDone",0);
					map.put("accountId",accountId);
					map.put("childId",childId);
					newTask.setExtraFields(map);
					iTaskFacade.createTask(newTask);
				}else{
					TaskInitParam newTask = new TaskInitParam();
					newTask.setBusiness_id(String.valueOf(businessId));
					newTask.setModule(ModuleEnums.retaincustomer);
					String newTime = ymd.format(newDate);
					if (Integer.valueOf(y.format(newDate)) < Integer.valueOf(nowY)){
						newTime = nowY + "-" + md.format(newDate);
					}
					Date scheduledTime = DateUtil.StringToDate(newTime);
					if(Integer.valueOf(ymdInt.format(scheduledTime)) < Integer.valueOf(ymdInt.format(new Date()))){
						// 判断这个执行时间是否小于今天, 是, 逾期
						newTask.setStatusEnums(TaskStatusEnums.delay);
					}
					newTask.setScheduled_time(scheduledTime);
					newTask.setType(type);
					Map map = new HashMap();
					map.put("nextIsDone",0);
					map.put("accountId",accountId);
					map.put("childId",childId);
					newTask.setExtraFields(map);
					iTaskFacade.createTask(newTask);
				}
			}else {
				//就绪状态, 不处理
			}
		}
	}

	@Override
	public void runSingleTask(CarsokCustomerTenureCar tenureCar, CarsokCustomerTenureCar oldCar, CarsokCustomer customer, CarsokCustomer oldCustomer){
		if(oldCar == null){
			oldCar = new CarsokCustomerTenureCar();
		}
		if(oldCustomer == null){
			oldCustomer = new CarsokCustomer();
		}
		runTenureCarTask(tenureCar, oldCar);
		runCustomerTask(customer, oldCustomer);
	}

	@Override
	public BaseResult pushOutDateTaskRemind(Acount acount, ChildAccount childAccount) {
		acount = acountMapper.selectByPrimaryKey(acount.getId());
		int failNum = 0;
		int sucessNum = 0;
		String senderName;
		if (childAccount == null){
			if(StringUtil.isEmpty(!StringUtil.isEmpty(acount.getRealName())?acount.getRealName():acount.getNick())){
				senderName = "主账号";
			}else{
				senderName = !StringUtil.isEmpty(acount.getRealName())?acount.getRealName():acount.getNick();
			}
		}else {
			senderName = childAccount.getChildAccountName();
		}
		OverdueForgModel odfm = overdueForgService.overdue(acount);
		List<OverdueForgModelOne> list = odfm.getRows();
		for (OverdueForgModelOne obj : list){
			if (obj.getPotentialOverdueOne() != 0 || obj.getRetainOverdueOne() != 0){//两个有一个不是0, 说明有逾期的任务要处理, 需要推送
				if (obj.getChildId() != null){//说明是子账号的逾期信息
					String content = "";
					String showContent = "";
					if (obj.getPotentialOverdueOne() != 0 && obj.getRetainOverdueOne() != 0){
						content = senderName + "提醒您有"+ obj.getPotentialOverdueOne() +"项潜客待办任务，"+ obj.getRetainOverdueOne() +"项保有待办任务已逾期，请尽快跟进";
						showContent = senderName + "提醒您," + "有" + obj.getPotentialOverdueOne() + "项潜客待办任务已逾期," + "有" + obj.getRetainOverdueOne() + "项保有待办任务已逾期";
					}else if (obj.getPotentialOverdueOne() == 0 && obj.getRetainOverdueOne() != 0){
						content = senderName + "提醒您有"+ obj.getRetainOverdueOne() +"项保有待办任务已逾期，请尽快跟进";
						showContent = senderName + "提醒您," + "有" + obj.getRetainOverdueOne() + "项保有待办任务已逾期";
					}else if (obj.getPotentialOverdueOne() != 0 && obj.getRetainOverdueOne() == 0){
						content = senderName + "提醒您有"+ obj.getPotentialOverdueOne() +"项潜客待办任务已逾期，请尽快跟进";
						showContent = senderName + "提醒您," + "有" + obj.getPotentialOverdueOne() + "项潜客待办任务已逾期";
					}
					ChildAccount child = new ChildAccount();
					child.setId(obj.getChildId());
					child = childAccountMapper.selectByModel(child);
					boolean pushResult = pushService.SendCustomizedCast(child.getChildAccountMobile(), content,"Outdate");
					MessageCenter mc = new MessageCenter();
					mc.setTitle("公司通知");
					mc.setContent(showContent);
					mc.setCreateTime(new Date());
					mc.setEnable(1);
					mc.setPushTo(child.getChildAccountMobile());
					mc.setPushFrom("systems");
					mc.setContentType("taskOutDate");
					mc.setPushStatus(2);
					messageCenterService.messageCenterAdd(mc);
					if(pushResult){
						messageCenterService.updatePushStatusById(mc.getId(),1);
					}else {
						messageCenterService.updatePushStatusById(mc.getId(),0);
					}
					if (pushResult) {
						sucessNum++;
					} else {
						failNum++;
					}
				}
// 				else {//说明是主账号的逾期信息
//					String content = "";
//					String showContent = "";
//					if (obj.getPotentialOverdueOne() != 0 && obj.getRetainOverdueOne() != 0){
//						content = senderName + "提醒您有"+ obj.getPotentialOverdueOne() +"项潜客待办任务，"+ obj.getRetainOverdueOne() +"项保有待办任务已逾期，请尽快跟进";
//						showContent = senderName + "提醒您," + "有" + obj.getPotentialOverdueOne() + "项潜客待办任务已逾期," + "有" + obj.getRetainOverdueOne() + "项保有待办任务已逾期";
//					}else if (obj.getPotentialOverdueOne() == 0 && obj.getRetainOverdueOne() != 0){
//						content = senderName + "提醒您有"+ obj.getRetainOverdueOne() +"项保有待办任务已逾期，请尽快跟进";
//						showContent = senderName + "提醒您," + "有" + obj.getRetainOverdueOne() + "项保有待办任务已逾期";
//					}else if (obj.getPotentialOverdueOne() != 0 && obj.getRetainOverdueOne() == 0){
//						content = senderName + "提醒您有"+ obj.getPotentialOverdueOne() +"项潜客待办任务已逾期，请尽快跟进";
//						showContent = senderName + "提醒您," + "有" + obj.getPotentialOverdueOne() + "项潜客待办任务已逾期";
//					}
//					boolean pushResult = pushService.SendCustomizedCast(acount.getAccount(), content,"Outdate");
//					MessageCenter mc = new MessageCenter();
//					mc.setTitle("公司通知");
//					mc.setContent(showContent);
//					mc.setCreateTime(new Date());
//					mc.setEnable(1);
//					mc.setPushTo(acount.getAccount());
//					mc.setPushFrom("systems");
//					mc.setContentType("taskOutDate");
//					mc.setPushStatus(2);
//					messageCenterService.messageCenterAdd(mc);
//					if(pushResult){
//						messageCenterService.updatePushStatusById(mc.getId(),1);
//					}else {
//						messageCenterService.updatePushStatusById(mc.getId(),0);
//					}
//					if (pushResult) {
//						return BaseResult.success();
//					} else {
//						return BaseResult.fail(ErrorCode.ExceptionRetCode, "推送失败");
//					}
//				}
			}
		}
		if (failNum != 0){
			if (sucessNum != 0){
				return BaseResult.fail(ErrorCode.ExceptionRetCode, "推送成功"+sucessNum+"条, 推送失败"+failNum+"条");
			}else {
				return BaseResult.fail(ErrorCode.ExceptionRetCode, "推送失败");
			}
		}else {
			return BaseResult.success(ErrorCode.SuccessRetCode, "推送成功");
		}
	}

	@Override
	public BaseResult OneKeyRemindShow(Acount acount, ChildAccount childAccount, String timeParam){
		String pushTo = "";
//		if (childAccount == null){//说明是主账号查看
//			pushTo = acount.getAccount();
//		}else {//说明是有权限的子账号查看
//			pushTo = childAccount.getChildAccountMobile();
//		}
		if(childAccount == null){//说明是主账号查看, 不给看

		}else{//说明是子账号查看, 判断权限
			List<String> roleName = getRoleName(childAccount.getId());
			if(roleName.contains("byyxgw") || roleName.contains("qkyxgw") || roleName.contains("byjlgl") || roleName.contains("qkjlgl") || roleName.contains("bykfdp") || roleName.contains("qkkfdp")){
				pushTo = childAccount.getChildAccountMobile();
			}
		}
		//查找推送给pushTo的所有taskOutDate逾期推送信息
		List<PushContent> pushList = saasTenureCustomerMapper.getOutDatePushList(pushTo, "taskOutDate", timeParam);
		List<OutDatePushResponse> list = new ArrayList<>();
		for (PushContent pushContent : pushList){
			OutDatePushResponse response = new OutDatePushResponse();
			String[] arr = pushContent.getContent().trim().split(",");
			response.setRemindTitle(arr[0]);
			List<Map> info = new ArrayList<>();
			Map map1 = new HashMap();
			map1.put("info",arr[1]);
			if (arr[1].contains("潜客")){
				map1.put("type","potentialcustomer");
			}else {
				map1.put("type","retaincustomer");
			}

			info.add(map1);
			if (arr.length == 3){
				Map map2 = new HashMap();
				map2.put("info",arr[2]);
				if (arr[2].contains("潜客")){
					map2.put("type","potentialcustomer");
				}else {
					map2.put("type","retaincustomer");
				}

				info.add(map2);
			}
			response.setRemindInfoArray(info);
			response.setCreateTime(pushContent.getCreateTime());
			list.add(response);
		}
		return BaseResult.success(list);
	}

	@Override
	public List<ChildAccount> getChildAcountList(){
		List<ChildAccount> list = saasTenureCustomerMapper.getChildAcountList();
		return list;
	}

	@Override
	public List<Acount> getAcountList(){
		List<Acount> list = saasTenureCustomerMapper.getAcountList();
		return list;
	}

	@Override
	public int clearReadyTaskPush(){
		return saasTenureCustomerMapper.clearReadyTaskPush();
	}

	@Override
	public BaseResult canMessageShow(Acount acount, ChildAccount childAccount){
		BaseResult result = BaseResult.success();
		boolean canMessageShow = false;
		if(childAccount == null){//说明是主账号查看, 不给看

		}else{//说明是子账号查看, 判断权限
			List<String> roleName = getRoleName(childAccount.getId());
			if(roleName.contains("byyxgw") || roleName.contains("qkyxgw") || roleName.contains("byjlgl") || roleName.contains("qkjlgl") || roleName.contains("bykfdp") || roleName.contains("qkkfdp")){
				canMessageShow = true;
			}
		}
		result.setData(canMessageShow);
		return result;
	}
	@Override
    public BaseResult getCarServeInfo(CreateCarServeInfoRequest vo) {
        CarsokCustomerTenureCar carsokCustomerTenureCarRe = carsokCustomerTenureCarMapper.selectById(vo.getTenureCarId());
        CarsokCustomer carsokCustomerRe = carsokCustomerMapper.selectById(carsokCustomerTenureCarRe.getCustomerId());
        CarsokTenureCarResponse res = new CarsokTenureCarResponse();
        DozerMapperUtil.getInstance().map(carsokCustomerTenureCarRe, res);
        DozerMapperUtil.getInstance().map(carsokCustomerRe, res);

		//销售经理
		String belongTo = "主账号";
		if (carsokCustomerTenureCarRe.getChildId() != 0){//说明属于子账号
			//查询子账号名
			CarsokChildAccount belongToChild = carsokChildAccountMapper.selectById(carsokCustomerTenureCarRe.getChildId());
			belongTo = belongToChild.getChildAccountName();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("showInfo", res);
		map.put("belongTo", belongTo);
		PageHelper.startPage(0,0);
		List<CarsokCustomerTenureServe> serviceList = carsokCustomerTenureServeMapper.selectList(new EntityWrapper<CarsokCustomerTenureServe>().eq("tenure_carId", vo.getTenureCarId()).orderBy("create_time", false));
		List<CarsokCustomerTenureServeResponse> list = new ArrayList<>();
		for (CarsokCustomerTenureServe req : serviceList){
			CarsokCustomerTenureServeResponse cc = new CarsokCustomerTenureServeResponse();
			cc.setId(req.getId());
			cc.setTenureCarId(req.getTenureCarId());
			String CNStr = turnToCNString(req.getType());
			cc.setType(CNStr);
			cc.setMoney(req.getMoney());
			cc.setInfo(req.getInfo());
			cc.setPicturesUrls(req.getPicturesUrls());
			cc.setAccountId(req.getAccountId());
			cc.setChildId(req.getChildId());
			cc.setServerTime(req.getServerTime());
			cc.setCreateTime(req.getCreateTime());
			if (req.getChildId()==0){
				cc.setRoleName("主账号");
			}else {
				EntityWrapper<CarsokAccountPower> filter = new EntityWrapper<CarsokAccountPower>();
				filter.eq("child_id",req.getChildId());
				List<CarsokAccountPower> CarsokAccountPowerList=carsokAccountPowerMapper.selectList(filter);
				String role="";
				int checkNum=0;
				String[] chooseRole = new String[CarsokAccountPowerList.size()];
				for (CarsokAccountPower v : CarsokAccountPowerList) {
					chooseRole[checkNum] = v.getPowerName();
					checkNum++;
				}
				if (Arrays.asList(chooseRole).contains("byjlgl")) {
					role = "经理";
				} else if (Arrays.asList(chooseRole).contains("bykfdp")) {
					role = "客服";
				} else if (Arrays.asList(chooseRole).contains("byyxgw")) {
					role = "销售";
				}
				cc.setRoleName(role);
			}
			list.add(cc);
		}
		map.put("serviceList", list);
		return BaseResult.success(map);
    }

	private String turnToCNString(String type) {
		String CNStr = "";
		switch (type){
			case "COMPULSORY" : CNStr = "交强险";break;
			case "COMMERCIAL" : CNStr = "商业险";break;
			case "MAINTAIN" : CNStr = "保养";break;
			case "WARRANTY" : CNStr = "质保";break;
			case "INSPECTION" : CNStr = "年检";break;
			case "RESCUE" : CNStr = "救援";break;
			case "CLEANING" : CNStr = "洗车";break;
			case "LACQUER" : CNStr = "钣金喷漆";break;
		}
		return CNStr;
	}

	@Override
    public BaseResult createCarServe(CarsokCustomerTenureServe vo) {
		vo.setCreateTime(new Date());
        carsokCustomerTenureServeMapper.insert(vo);
        CarsokRecord carsokRecord = new CarsokRecord();
        carsokRecord.setOutId(vo.getTenureCarId());
        carsokRecord.setMessage(vo.getInfo());
        carsokRecord.setAccountId(vo.getAccountId());
        carsokRecord.setChildId(vo.getChildId());
        carsokRecord.setModel("2");
        carsokRecord.setType("服务信息");
        carsokRecord.setCreateTime(vo.getCreateTime());
        DateFormat dateType = new SimpleDateFormat("yyyy-MM-dd");
        //汽车服务类型 COMPULSORY:交强险 COMMERCIAL:商业险 MAINTAIN:保养 WARRANTY:质保 INSPECTION:年检 RESCUE:救援 CLEANING:洗车 LACQUER:钣金喷漆
        if (vo.getType().equals("COMPULSORY")) {
        	if(vo.getServerTime() != null){
				carsokRecord.setMessage("本次交强险日期服务为"+dateType.format(vo.getServerTime()));
			}else{
				carsokRecord.setMessage("交强险服务");
			}
        } else if (vo.getType().equals("COMMERCIAL")) {
			if(vo.getServerTime() != null){
				carsokRecord.setMessage("本次商业险日期服务为"+dateType.format(vo.getServerTime()));
			}else{
				carsokRecord.setMessage("商业险服务");
			}
        } else if (vo.getType().equals("MAINTAIN")) {
			if(vo.getServerTime() != null){
				carsokRecord.setMessage("本次保养日期服务为"+dateType.format(vo.getServerTime()));
			}else{
				carsokRecord.setMessage("保养服务");
			}
        } else if (vo.getType().equals("WARRANTY")) {
			if(vo.getServerTime() != null){
				carsokRecord.setMessage("本次质保日期服务为"+dateType.format(vo.getServerTime()));
			}else{
				carsokRecord.setMessage("质保服务");
			}
        } else if (vo.getType().equals("INSPECTION")) {
			if(vo.getServerTime() != null){
				carsokRecord.setMessage("本次年检日期服务为"+dateType.format(vo.getServerTime()));
			}else{
				carsokRecord.setMessage("年检服务");
			}
        }else if (vo.getType().equals("RESCUE")) {
			if(vo.getServerTime() != null){
				carsokRecord.setMessage("本次救援日期服务为"+dateType.format(vo.getServerTime()));
			}else{
				carsokRecord.setMessage("救援服务");
			}
		} else if (vo.getType().equals("CLEANING")) {
			if(vo.getServerTime() != null){
				carsokRecord.setMessage("本次洗车日期服务为"+dateType.format(vo.getServerTime()));
			}else{
				carsokRecord.setMessage("洗车服务");
			}
		} else if (vo.getType().equals("LACQUER")) {
			if(vo.getServerTime() != null){
				carsokRecord.setMessage("本次钣金喷漆服务日期为"+dateType.format(vo.getServerTime()));
			}else{
				carsokRecord.setMessage("钣金喷漆服务");
			}
		}
//		else if (vo.getType().equals("INSPECTION")) {
//			CarsokCustomerTenureCar tenureCarCondition = new CarsokCustomerTenureCar();
//			tenureCarCondition.setId(vo.getTenureCarId());
//			tenureCarCondition.setTenureInspection(vo.getServerTime());
//			Date tenureInspection = customerTenureCar.getTenureInspection();
//			carsokRecord.setMessage("年检到期由"+dateType.format(tenureInspection)+"更改为"+dateType.format(vo.getServerTime()));
//			carsokCustomerTenureCarMapper.updateById(tenureCarCondition);
//			runTenureCarTask(tenureCarCondition, customerTenureCar);
//		}else if (vo.getType().equals("RESCUE")) {
//			CarsokCustomerTenureCar tenureCarCondition = new CarsokCustomerTenureCar();
//			tenureCarCondition.setId(vo.getTenureCarId());
//			tenureCarCondition.setTenureRescue(vo.getServerTime());
//			Date tenureRescue = customerTenureCar.getTenureRescue();
//			carsokRecord.setMessage("救援日期由"+dateType.format(tenureRescue)+"更改为"+dateType.format(vo.getServerTime()));
//			carsokCustomerTenureCarMapper.updateById(tenureCarCondition);
//		} else if (vo.getType().equals("CLEANING")) {
//			CarsokCustomerTenureCar tenureCarCondition = new CarsokCustomerTenureCar();
//			tenureCarCondition.setId(vo.getTenureCarId());
//			tenureCarCondition.setTenureCarwash(vo.getServerTime());
//			Date tenureCarwash = customerTenureCar.getTenureCarwash();
//			carsokRecord.setMessage("洗车日期由"+dateType.format(tenureCarwash)+"更改为"+dateType.format(vo.getServerTime()));
//			carsokCustomerTenureCarMapper.updateById(tenureCarCondition);
//		} else if (vo.getType().equals("LACQUER")) {
//			CarsokCustomerTenureCar tenureCarCondition = new CarsokCustomerTenureCar();
//			tenureCarCondition.setId(vo.getTenureCarId());
//			tenureCarCondition.setTenurePaint(vo.getServerTime());
//			Date tenurePaint = customerTenureCar.getTenurePaint();
//			carsokRecord.setMessage("钣金喷漆日期由"+dateType.format(tenurePaint)+"更改为"+dateType.format(vo.getServerTime()));
//			carsokCustomerTenureCarMapper.updateById(tenureCarCondition);
//		}
        carsokRecordMapper.insert(carsokRecord);

        //获取车辆信息
		CarsokCustomerTenureCar car = carsokCustomerTenureCarMapper.selectById(vo.getTenureCarId());
		//获取客户信息
		CarsokCustomer customer = carsokCustomerMapper.selectById(car.getCustomerId());
		//推送买家端
		if(car.getProductId() != null && car.getIsDone() == 1){//productId不为空 且保有车信息已经推送过才推送
			try{
				Acount acount = acountMapper.selectByPrimaryKey(customer.getAccountId());
				//信息推送给买家端
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String url = buyerSide;
				Map<String, Object> map = new HashedMap();
				BuyerInfo buyerInfo = new BuyerInfo();
				// 车和客户id------------------------------------------------------
				buyerInfo.setCustomerId(String.valueOf(customer.getId()));
				buyerInfo.setTenureCarId(String.valueOf(car.getId()));
				//----------------------------------------------------------------
				buyerInfo.setLicensePlate(car.getTenureCarnum()!=null?car.getTenureCarnum():"");
				buyerInfo.setVinCode(car.getTenureVin()!=null?car.getTenureVin():"");
				buyerInfo.setSellerId(String.valueOf(customer.getAccountId()));
				JSONObject carModel=new JSONObject();
				String carName = car.getTenureCarname();
				String carNames [] = carName.split(" ");
				int length=carNames.length;
				if(length>3){
					carModel.put("carBrand",carNames[0] );
					carModel.put("carSeries",carNames[1] );
					String simpleModel="";
					for(int i=2;i<length;i++){
						simpleModel=simpleModel+carNames[i];
					}
					carModel.put("carModel",simpleModel );
				}
				else{
					carModel.put("carBrand",carNames[0] );
					carModel.put("carModel",carNames[1] );
				}

				Picture pictureQuery = new Picture();
				pictureQuery.setProductId(car.getProductId());
				short pictype = 1;
				pictureQuery.setType(pictype); //只查询主图
				List<Picture> pictureList = pictureMapper.selectByModel(pictureQuery);

				PruductOldcar p = new PruductOldcar();
				p.setPruductId(car.getProductId());
				PruductOldcar pruductOldcar = pruductOldcarMapper.selectByModel(p);
				if(pruductOldcar!=null){
					carModel.put("province",pruductOldcar.getProvince());
					carModel.put("city",pruductOldcar.getCity());
					carModel.put("firstUpTime",pruductOldcar.getFirstUpTime());
				}
				carModel.put("carId",car.getProductId());
				carModel.put("merchantDescription",acount.getMerchantDescr());
				carModel.put("carImage", pictureList.get(0).getPicPath());
				ChildAccount childAccount=new ChildAccount();
				childAccount.setId(customer.getChildId());
				childAccount= childAccountMapper.selectByModel(childAccount);
				carModel.put("sellerName",childAccount==null? (acount.getNick()==null?acount.getMerchantName():acount.getNick()):childAccount.getChildAccountName());
				buyerInfo.setCarModel(carModel);
				buyerInfo.setSalesPrice(car.getTenureCarprice()!=null?car.getTenureCarprice().toString():"");
				buyerInfo.setSalesMileage(car.getCarMiles()!=null?car.getCarMiles():"");
				buyerInfo.setPaymentType(car.getTenureCartype()!=null?car.getTenureCartype():"");
				buyerInfo.setCompulsoryInsuranceDate(car.getTenureCompulsory()!=null?formatter.format(car.getTenureCompulsory()):"");
				buyerInfo.setCommercialInsuranceDate(car.getTenureBusiness()!=null?formatter.format(car.getTenureBusiness()):"");
				buyerInfo.setMaintainDate(car.getTenureMaintain()!=null?formatter.format(car.getTenureMaintain()):"");
				buyerInfo.setWarrantyDueDate(car.getTenureWarranty()!=null?formatter.format(car.getTenureWarranty()):"");
				buyerInfo.setSalesConsultant(childAccount==null? acount.getAccount():childAccount.getChildAccountMobile());
				buyerInfo.setFromApp("CHESHANG_APP");
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setPhone(customer.getMobile());
				customerInfo.setName(customer.getName());
				buyerInfo.setCustomerInfo(customerInfo);
				SellerInfo sellerInfo = new SellerInfo();
				sellerInfo.setThirdId(customer.getAccountId()+"");
				sellerInfo.setFrom("CHESHANG_APP");
				sellerInfo.setInfoUrl(storeUrl + acount.getAccountCode()+".html");
				sellerInfo.setMerchantName(acount.getMerchantName());
				sellerInfo.setName(StringUtils.isEmpty(acount.getRealName())?acount.getNick():acount.getRealName());
				sellerInfo.setAddress(acount.getMerchantAddress());
				sellerInfo.setPhone(acount.getAccount());
				buyerInfo.setSellerInfo(sellerInfo);
				buyerInfo.setBuyerPhone(customer.getMobile());
				//查询该车相关所有的服务
				List<CarsokCustomerTenureServe> serviceList = carsokCustomerTenureServeMapper.selectList(new EntityWrapper<CarsokCustomerTenureServe>().eq("tenure_carId", car.getId()).orderBy("create_time", false));
				List<CarService> list = new ArrayList<>();
				int rescueNum = 0;
				for(CarsokCustomerTenureServe cs : serviceList){
					CarService carService = new CarService();
					carService.setAutoServiceType(cs.getType());
					if("RESCUE".equals(cs.getType())){
						rescueNum++;
					}
					if("INSPECTION".equals(cs.getType()) && StringUtil.isEmpty(buyerInfo.getLastMiantenanceDate())){
						//上次保养日期
						buyerInfo.setLastMiantenanceDate(cs.getCreateTime()!=null?formatter.format(cs.getCreateTime()):"");
					}
					if(cs.getServerTime() != null){
						carService.setAutoServiceDate(formatter.format(cs.getServerTime()));
					}
					if(cs.getMoney() != null){
						carService.setAutoServiceMoney(String.valueOf(cs.getMoney()));
					}
					if(!StringUtil.isEmpty(cs.getInfo())){
						carService.setAutoServiceContent(cs.getInfo());
					}
					if(!StringUtil.isEmpty(cs.getPicturesUrls())){
						carService.setAutoServiceUrl(cs.getPicturesUrls());
					}
					list.add(carService);
				}
				buyerInfo.setCarService(list);
				//救援次数
				buyerInfo.setRescueTime(rescueNum);
				String param = JSONObject.toJSONString(buyerInfo);
//				BaseEvent event = new BaseEvent();
//				event.setData(param);
//				event.setEventName(EventConstants.PUSH_AUTO_INFO_TO_BUYER_APP_EVENT);
//				eventBus.publish(event);
				String flag = HttpClientUtil.sendPostRequestByJava(buyerSide,param);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
		}

        return BaseResult.success();
    }
	

//----------------------------------------------------------------------------------------------------
//	@Override
//	public BaseResult moveOldVersionData(){
//		BaseResult result = BaseResult.success();
//		//查询所有时间区间内的 carsok_customer_trnure_car (旧的保有车辆表)的数据
//		List<moveOldDataRequest> oldList = saasTenureCustomerMapper.getOldData();
//		for (moveOldDataRequest req : oldList){
//			if (req.getCustomerId() != 0){//有客户关联
//				CarsokCustomerTenureCar carsokCustomerTenureCar = new CarsokCustomerTenureCar();
//				DozerMapperUtil.getInstance().map(req, carsokCustomerTenureCar);
//				carsokCustomerTenureCar.setCustomerId(req.getCustomerId());
//				carsokCustomerTenureCar.setIsNewRecord(1);
//				carsokCustomerTenureCar.setIsDone(0);
//				carsokCustomerTenureCar.setCreateTime(new Date());
//				carsokCustomerTenureCarMapper.insert(carsokCustomerTenureCar);
//			}else{//没有客户关联, 现创建一个客户
//				CarsokCustomer carsokCustomer = new CarsokCustomer();
//				carsokCustomer.setAccountId(req.getAccountId());
//				carsokCustomer.setChildId(req.getChildId());
//				carsokCustomer.setRemark(null);
//				carsokCustomer.setCreateTime(new Date());
//				carsokCustomerMapper.insert(carsokCustomer);
//				CarsokCustomerTenureCar carsokCustomerTenureCar = new CarsokCustomerTenureCar();
//				DozerMapperUtil.getInstance().map(req, carsokCustomerTenureCar);
//				carsokCustomerTenureCar.setCustomerId(carsokCustomer.getId());
//				carsokCustomerTenureCar.setIsNewRecord(1);
//				carsokCustomerTenureCar.setIsDone(0);
//				carsokCustomerTenureCar.setCreateTime(new Date());
//				carsokCustomerTenureCarMapper.insert(carsokCustomerTenureCar);
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public BaseResult moveOldVersionQiankeData(){
//		BaseResult result = BaseResult.success();
//		//查询所有旧版潜客数据-carsok_customer_manage表
//		List<moveOldDataCarsokCustomerManage> list = saasTenureCustomerMapper.getOldQiankeData();
//		for (moveOldDataCarsokCustomerManage asd : list) {
//			//潜在客户
//			CarsokCustomer carsokCustomer = new CarsokCustomer();
//			carsokCustomer.setMobile(asd.getCustomerPhone());
//			carsokCustomer.setName(asd.getCustomerName());
//			carsokCustomer.setInTime(asd.getInTime());
//			carsokCustomer.setOutTime(asd.getOutTime());
//			carsokCustomer.setInNumber(asd.getPeopleNum());
//			carsokCustomer.setLevel("N 24小时内回访");
//			carsokCustomer.setSource(asd.getInformationSources());
//			carsokCustomer.setBudget(asd.getCustomerBudget());
//			carsokCustomer.setArea(asd.getCustomerRegion());
//			carsokCustomer.setCreateTime(asd.getCreateTime());
//			carsokCustomer.setUpdateTime(new Date());
//			carsokCustomer.setAccountId(asd.getAccountId());
//			carsokCustomer.setChildId(asd.getChildId());
//			carsokCustomerMapper.insert(carsokCustomer);
//			//意向车型
//			if (carsokCustomer.getId() != null) {
//				CarsokCustomerIntentionCar cc3 = new CarsokCustomerIntentionCar();
//				cc3.setCustomerId(carsokCustomer.getId());
//				cc3.setSeries(asd.getIntentionalVehicle());
//				cc3.insert();
//			}
//			//生成24小时内回访任务
//			if (carsokCustomer.getId() != null) {
//				TaskInitParam taskInitParam = new TaskInitParam();
//				Calendar c = Calendar.getInstance();
//				c.setTime(asd.getInTime());
//				taskInitParam.setType(TaskTypeEnums.oneday_buy);
//				c.add(Calendar.DAY_OF_MONTH, 1);
//				taskInitParam.setScheduled_time(c.getTime());
//				taskInitParam.setBusiness_id(carsokCustomer.getId().toString());
//				taskInitParam.setModule(ModuleEnums.potentialcustomer);
//				Map<String, String> map = new HashMap<>();
//				map.put("account_id", String.valueOf(carsokCustomer.getAccountId()));
//				map.put("child_id", String.valueOf(carsokCustomer.getChildId()));
//				map.put("level", carsokCustomer.getLevel());
//				taskInitParam.setExtraFields(map);
//				taskInitParam.setStatusEnums(TaskStatusEnums.delay);
//				iTaskFacade.createTask(taskInitParam);
//			}
//		}
//		return result;
//	}
//	@Override
//	public BaseResult dealDecBaoyouCustomerTenureData(){
//		BaseResult result = BaseResult.success();
//		//查询出12月份有的旧保有客户信息和对应的新客户id
//		List<DecBaoyouDataComplete> list = saasTenureCustomerMapper.getDecOldBaoyouCustomerData();
//		for (DecBaoyouDataComplete baoyou : list){
//			CarsokCustomer carsokCustomer = carsokCustomerMapper.selectById(baoyou.getCustomerId());
//			if(StringUtil.isEmpty(carsokCustomer.getName())){
//				carsokCustomer.setName(baoyou.getCust_name());
//			}
//			carsokCustomer.setSex(baoyou.getCust_sex());
//			if (!StringUtil.isEmpty(baoyou.getCust_age())){
//				carsokCustomer.setAge(Integer.valueOf(baoyou.getCust_age()));
//			}
//			if (baoyou.getCust_birthday() != null){
//				carsokCustomer.setBirthday(baoyou.getCust_birthday());
//			}
//			if (!StringUtil.isEmpty(baoyou.getCust_vocation())){
//				carsokCustomer.setJob(baoyou.getCust_vocation());
//			}
//			if (!StringUtil.isEmpty(baoyou.getCust_beforecar())){
//				carsokCustomer.setBeforeCar(baoyou.getCust_beforecar());
//			}
//			carsokCustomer.setCreateTime(baoyou.getCreate_time());
//			carsokCustomer.setUpdateTime(new Date());
//			carsokCustomer.setMobile(baoyou.getCust_phone());
////			//这块特殊, 如果潜客那块有这个电话号
////			//判断这一个电话是不是存在
////			CarsokCustomer phoneC = new CarsokCustomer();
////			phoneC.setMobile(baoyou.getCust_phone());
////			phoneC = saasTenureCustomerMapper.selectphoneC(phoneC);
////			if (phoneC == null){
////				//不存在, setPhone
////				carsokCustomer.setMobile(baoyou.getCust_phone());
////			}else {
////				//存在, 不setPhone, 就留个空
////			}
//			saasTenureCustomerMapper.updateCarsokCustomerById(carsokCustomer);
//		}
//		return result;
//	}
//	@Override
//	public BaseResult dealNovBaoyouCustomerTenureData(){
//		BaseResult result = BaseResult.success();
//		//查询11月份需要处理的保有客户和车的数据
//		List<NovBaoyouData> oldList = saasTenureCustomerMapper.getNovOldData();
//		for (NovBaoyouData req : oldList){
//			CarsokCustomer carsokCustomer = new CarsokCustomer();
//			if(!StringUtil.isEmpty(req.getCustName())){
//				carsokCustomer.setName(req.getCustName());
//			}
//			if(!StringUtil.isEmpty(req.getCustPhone())){
//				carsokCustomer.setMobile(req.getCustPhone());
//			}
//			if(!StringUtil.isEmpty(req.getCustSex())){
//				carsokCustomer.setSex(req.getCustSex());
//			}
//			if (!StringUtil.isEmpty(req.getCustAge())){
//				carsokCustomer.setAge(Integer.valueOf(req.getCustAge()));
//			}
//			if (req.getCustBirthday() != null){
//				carsokCustomer.setBirthday(req.getCustBirthday());
//			}
//			if (!StringUtil.isEmpty(req.getCustVocation())){
//				carsokCustomer.setJob(req.getCustVocation());
//			}
//			if (!StringUtil.isEmpty(req.getCustBeforecar())){
//				carsokCustomer.setBeforeCar(req.getCustBeforecar());
//			}
//			if (req.getCreateTime() != null){
//				carsokCustomer.setCreateTime(req.getCreateTime());
//			}else{
//				carsokCustomer.setCreateTime(new Date());
//			}
//			carsokCustomer.setUpdateTime(new Date());

//			carsokCustomer.setAccountId(req.getAccountId());
//			carsokCustomer.setChildId(req.getChildId());
//			carsokCustomer.setRemark(null);
//			carsokCustomerMapper.insert(carsokCustomer);
//			CarsokCustomerTenureCar carsokCustomerTenureCar = new CarsokCustomerTenureCar();
//			DozerMapperUtil.getInstance().map(req, carsokCustomerTenureCar);
//			carsokCustomerTenureCar.setCustomerId(carsokCustomer.getId());
//			carsokCustomerTenureCar.setIsNewRecord(1);
//			carsokCustomerTenureCar.setIsDone(0);
//			carsokCustomerTenureCar.setCreateTime(new Date());
//			carsokCustomerTenureCarMapper.insert(carsokCustomerTenureCar);
//		}
//		return result;
//	}

//	@Override
//	public void clearOldTask(){
//		// 查出所有的客户
//		List<CarsokCustomer> cusList = carsokCustomerMapper.selectList(new EntityWrapper<CarsokCustomer>().orderBy("create_time"));
//		// 循环
//		for (CarsokCustomer cus : cusList){
//			// 先处理客户级别是"FO"的数据, 改为"F0"
//			if (cus.getLevel().contains("FO")){
//				carsokCustomerMapper.updateCustLevelById(cus.getId(), "F0 战败待确认");
//			}
//			// 查出每个客户的潜客待办/逾期任务, 处理, 只保留最新的一条
//			FilterSQLParam sqlStatement = new FilterSQLParam();
//			sqlStatement.setSqlTemplate(" business_id='" + cus.getId() + "' AND module='" +ModuleEnums.potentialcustomer +"' AND (task_status='"+TaskStatusEnums.ready+"' OR task_status='"+TaskStatusEnums.delay+"')");
//			sqlStatement.setOrderByColumn("create_time");
//			sqlStatement.setIsAsc(true);
//			PageInfo<CarsokTenureTask> taskList = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlStatement);
//			// 先去掉多余的任务, 然后再查一遍任务
//			if (taskList.getList().size() > 1){
//				// 循环, 终止最后一个(最新一个)任务之前的所有任务
//				for (int i=0;i<taskList.getList().size()-1;i++){
//					int taskId = taskList.getList().get(i).getId();
//					iTaskFacade.finishTaskById(taskId, "0", null);
//				}
//			}
//			taskList = iTaskFacade.queryTaskBySQLFilter(0, 0, sqlStatement);// 要么一个任务, 要么没有任务
//			// 如果客户级别D 已成交, 那么不能有ready或者delay的任务
//			if ("D 已成交".equals(cus.getLevel())){
//				// 终止任务
//				for (CarsokTenureTask task : taskList.getList()){
//					int taskId = task.getId();
//					iTaskFacade.finishTaskById(taskId, "0", null);
//				}
//			}else{// 如果客户级别是别的, 有一个任务, 则level和child_id要同步成客户表的
//				for (CarsokTenureTask task : taskList.getList()){
//					int taskId = task.getId();
//					Map oldmap = JSON.parseObject(task.getExtraFields());
//					String taskLevel = (String)oldmap.get("level");
//					if (!cus.getLevel().equals(taskLevel)){
//						if(oldmap.containsKey("level")){
//							oldmap.put("level",cus.getLevel());
//						}
//						String level = "";
//						switch (cus.getLevel()) {
//							case "N 24小时内回访":
//								level = "oneday_buy";
//								break;
//							case "H 3天内购买":
//								level = "threedays_buy";
//								break;
//							case "A 7天内购买":
//								level = "sevendays_buy";
//								break;
//							case "B 15天内购买":
//								level = "fifteendays_buy";
//								break;
//							case "C 30天内购买":
//								level = "onemonth_buy";
//								break;
//							case "F 战败":
//								level = "defeat";
//								break;
//							case "F0 战败待确认":
//								level = "defeat_confirm";
//								break;
//						}
//						saasTenureCustomerMapper.updateTaskLevel(taskId, level);
//						if ("F0 战败待确认".equals(cus.getLevel()) || "F 战败".equals(cus.getLevel())){
//							saasTenureCustomerMapper.clearTaskScheduledTime(taskId);
//						}
//					}
//					if(oldmap.containsKey("child_id")){
//						oldmap.put("child_id",String.valueOf(cus.getChildId()));
//					}
//					iTaskFacade.updateExtraData(taskId, oldmap, null, null);
//				}
//			}
//		}
//	}
}
