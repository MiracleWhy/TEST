package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.SaasFollowMessageController;
import com.uton.carsokApi.controller.request.CarsokSoundRecordingRequest;
import com.uton.carsokApi.controller.request.FindMessagePageRequest;
import com.uton.carsokApi.controller.request.FollowMessageRequest;
import com.uton.carsokApi.controller.response.CarsokFlowmsgResponse;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.ITaskFacade;
import com.uton.carsokApi.service.SaasFollowMessageService;
import com.uton.carsokApi.service.SaasSoundRecordingService;
import com.uton.carsokApi.util.DozerMapperUtil;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SaasFollowMessageServiceImpl implements SaasFollowMessageService {

    @Autowired
    private SaasFollowMessageMapper SaasFollowMessageMapper;
    @Autowired
    private CarsokCustomerTenureFollowMapper carsokCustomerTenureFollowMapper;
    @Autowired
    private CarsokCustomerFlowmsgMapper carsokCustomerFlowmsgMapper;
    @Autowired
    private CarsokCustomerMapper carsokCustomerMapper;
    @Autowired
    private CarsokRecordMapper carsokRecordMapper;
    @Autowired
    private ITaskFacade iTaskFacade;
    @Autowired
    private CarsokAcountMapper carsokAcountMapper;
    @Autowired
    private CarsokChildAccountMapper carsokChildAccountMapper;
    @Autowired
    private CarsokAccountPowerMapper carsokAccountPowerMapper;
    @Autowired
    private SaasSoundRecordingService saasSoundRecordingService;

    private final static Logger logger = Logger.getLogger(SaasFollowMessageServiceImpl.class);

    @Override
    public BaseResult saveFollowMessage(FollowMessageRequest vo) {
    /*    if (vo.getType().equals("录音")){
            CarsokSoundRecordingRequest carsokSoundRecording = new CarsokSoundRecordingRequest();
            carsokSoundRecording.setAccountId(vo.getAccountId());
            carsokSoundRecording.setChildId(vo.getChildId());
            carsokSoundRecording.setCreateTime(new Date());
            carsokSoundRecording.setBusiness_id(Integer.parseInt(vo.getOutId()));
            carsokSoundRecording.setEnable(1);
            carsokSoundRecording.setModule(vo.getModel());
            carsokSoundRecording.setUrl(vo.getMessage());
            carsokSoundRecording.setRecordTime(vo.getRecordTime());
            carsokSoundRecording.setSoundToWord(vo.getSoundToWord());
            saasSoundRecordingService.insertRecording(carsokSoundRecording);
        }*/
        try {
            //如果是1的话 就是潜在客户
            if (vo.getModel().equals("1")) {
                //如果是潜在客户，首先对carsokCustomerFlowmsg表进行操作
                CarsokCustomerFlowmsg carsokCustomerFlowmsg = new CarsokCustomerFlowmsg();
                carsokCustomerFlowmsg.setAccountId(vo.getAccountId());
                carsokCustomerFlowmsg.setChildId(vo.getChildId());
                carsokCustomerFlowmsg.setCreateTime(new Date());
                carsokCustomerFlowmsg.setCustomerId(Integer.parseInt(vo.getOutId()));
                carsokCustomerFlowmsg.setCustomerFlowMessage(vo.getMessage());
                carsokCustomerFlowmsgMapper.insert(carsokCustomerFlowmsg);
                //先获取原有的信息
                CarsokCustomer carsokCustomer = carsokCustomerMapper.selectById(vo.getOutId());
                //然后对carsokCustomer表进行操作，将表中的Level更新为等级Type
            if (!(vo.getType().equals("") || vo.getType().equals("录音"))) {
                carsokCustomer.setLevel(vo.getType());
            }
                carsokCustomer.setId(Integer.parseInt(vo.getOutId()));
                carsokCustomerMapper.updateById(carsokCustomer);
                //将数据插入到CarsokRecord表中
                Map<String, String> map = new HashMap<String, String>();
                map.put("soundToWord", vo.getSoundToWord());
                map.put("recordTime", vo.getRecordTime());
                String jsonResult = JSONObject.toJSONString(map);
                CarsokRecord carsokRecord = new CarsokRecord();
                carsokRecord.setCusServiceFollow(jsonResult);
                carsokRecord.setModel(vo.getModel());
                carsokRecord.setType(vo.getType());
                carsokRecord.setMessage(vo.getMessage());
                carsokRecord.setAccountId(vo.getAccountId());
                carsokRecord.setChildId(vo.getChildId());
                carsokRecord.setCreateTime(new Date());
                carsokRecord.setOutId(Integer.parseInt(vo.getOutId()));
                carsokRecordMapper.insert(carsokRecord);
                //结束该任务
                if (vo.getTaskId() != null && vo.getTaskId() != 0) {
                    if (vo.getChildId() == 0) {
                        iTaskFacade.finishTaskById(vo.getTaskId(), vo.getAccountId().toString(), null);
                    } else {
                        iTaskFacade.finishTaskById(vo.getTaskId(), null, vo.getChildId().toString());
                    }
                }
            } else {
                //否则是保有客户
                CarsokCustomerTenureFollow carsokCustomerTenureFollow = new CarsokCustomerTenureFollow();
                carsokCustomerTenureFollow.setFollowType(vo.getType());
                carsokCustomerTenureFollow.setChildId(vo.getChildId());
                carsokCustomerTenureFollow.setAccountId(vo.getAccountId());
                carsokCustomerTenureFollow.setFollowMessage(vo.getMessage());
                carsokCustomerTenureFollow.setTenurecarId(Integer.parseInt(vo.getOutId()));
                carsokCustomerTenureFollow.setCreateTime(new Date());
                carsokCustomerTenureFollowMapper.insert(carsokCustomerTenureFollow);
                //插入记录到CarsokRecord表中
                Map<String, String> map = new HashMap<String, String>();
                map.put("soundToWord", vo.getSoundToWord());
                map.put("recordTime", vo.getRecordTime());
                String jsonResult = JSONObject.toJSONString(map);
                CarsokRecord carsokRecord = new CarsokRecord();
                carsokRecord.setCusServiceFollow(jsonResult);
                carsokRecord.setModel(vo.getModel());
                carsokRecord.setType(vo.getType());
                carsokRecord.setMessage(vo.getMessage());
                carsokRecord.setAccountId(vo.getAccountId());
                carsokRecord.setChildId(vo.getChildId());
                carsokRecord.setCreateTime(new Date());
                carsokRecord.setOutId(Integer.parseInt(vo.getOutId()));
                carsokRecordMapper.insert(carsokRecord);
                //结束该任务
                if (vo.getTaskId() != null && vo.getTaskId() != 0) {
                    if (vo.getChildId() == 0) {
                        iTaskFacade.finishTaskById(vo.getTaskId(), vo.getAccountId().toString(), null);
                    } else {
                        iTaskFacade.finishTaskById(vo.getTaskId(), null, vo.getChildId().toString());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("save follow message error:" + e.getMessage());
        }
        return BaseResult.success();
    }

    @Override
    public Map<String, Object> selectFollowMessage(FindMessagePageRequest vo) {
        Page<CarsokRecord> page = new Page<CarsokRecord>(vo.getPageNum(), vo.getPageSize());
        EntityWrapper<CarsokRecord> ew = new EntityWrapper<CarsokRecord>();
        ew.eq("out_id", vo.getOut_id());
        ew.eq("model", vo.getModel());
        if(!StringUtil.isEmpty(vo.getType())){
            ew.like("type", vo.getType());
        }
        ew.orderBy("create_time",false);
        List<CarsokRecord> CarsokRecordList = carsokRecordMapper.selectPage(page, ew);
        List<CarsokRecord> CarsokRecordTotal = carsokRecordMapper.selectList(ew);
        List<CarsokFlowmsgResponse> CarsokFlowmsgResponseList
                = new ArrayList<CarsokFlowmsgResponse>();
        for (CarsokRecord cr : CarsokRecordList) {
            CarsokFlowmsgResponse re = new CarsokFlowmsgResponse();
            if (cr.getChildId() == 0) {// 主账号
                re.setCreatorRole("主账号");
                CarsokAcount c = carsokAcountMapper.selectById(cr.getAccountId());
                if (!StringUtil.isEmpty(c.getNick())) {
                    re.setCreator(c.getNick());
                }else {
                    re.setCreator("主账号");
                }
            } else {// 子账号, 需要判断跟进记录中的子账号是否被删除
                CarsokChildAccount child = carsokChildAccountMapper.selectById(cr.getChildId());
                if (child != null){
                    re.setCreator(child.getChildAccountName());
                }else {
                    re.setCreator("离职员工");
                }
                EntityWrapper<CarsokAccountPower> filter = new EntityWrapper<CarsokAccountPower>();
                filter.eq("child_id", cr.getChildId());
                List<CarsokAccountPower> CarsokAccountPowerList = carsokAccountPowerMapper.selectList(filter);
                String role = "";
                int checkNum=0;
                String[] chooseRole = new String[CarsokAccountPowerList.size()];
                for (CarsokAccountPower v : CarsokAccountPowerList) {
                    chooseRole[checkNum] = v.getPowerName();
                    checkNum++;
                }
                if (vo.getModel() == 2) {
                    if (Arrays.asList(chooseRole).contains("byjlgl")) {
                        role = "经理";
                    } else if (Arrays.asList(chooseRole).contains("bykfdp")) {
                        role = "客服";
                    } else if (Arrays.asList(chooseRole).contains("byyxgw")) {
                        role = "销售";
                    }
                } else {
                    if (Arrays.asList(chooseRole).contains("qkjlgl")) {
                        role = "经理";
                    } else if (Arrays.asList(chooseRole).contains("qkkfdp")) {
                        role = "客服";
                    } else if (Arrays.asList(chooseRole).contains("qkyxgw")) {
                        role = "销售";
                    }
                }
                re.setCreatorRole(role);
            }
            DozerMapperUtil.getInstance().map(cr, re);
            if (re.getType() != null) {
                if (re.getType().equals("录音")) {
                    String json = cr.getCusServiceFollow();
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    re.setRecordTime(jsonObject.getString("recordTime"));
                    re.setSoundToWord(jsonObject.getString("soundToWord"));
                }
            }
            CarsokFlowmsgResponseList.add(re);
        }
        int total = CarsokRecordTotal.size();
        int isNext = 0;
        if (vo.getPageNum() * vo.getPageSize() > total) {
            isNext = 0;
        } else {
            isNext = 1;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("followMsgPage", CarsokFlowmsgResponseList);
        result.put("pageNum", vo.getPageNum());
        result.put("pageSize", vo.getPageSize());
        result.put("total", total);
        result.put("isNext", isNext);
        return result;
    }

}
