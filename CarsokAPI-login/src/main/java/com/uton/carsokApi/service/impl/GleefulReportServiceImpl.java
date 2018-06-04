package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.controller.request.GleefulReportRequest;
import com.uton.carsokApi.controller.response.DailyCheckerResponse;
import com.uton.carsokApi.controller.response.GleefulReportResponse;
import com.uton.carsokApi.controller.response.GleefulSharerResponse;
import com.uton.carsokApi.controller.response.SubUserListResponse;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.*;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2017/7/11.
 */
@Service
public class GleefulReportServiceImpl implements GleefulReportService {
    private final static Logger logger = Logger.getLogger(GleefulReportServiceImpl.class);


    @Autowired
    GleefulReportMapper gleefulReportMapper;

    @Autowired
    GleefulReportDispatcherMapper gleefulReportDispatcherMapper;

    @Autowired
    SubUserService subUserService;

    @Autowired
    SharedRecordMapper sharedRecordMapper;

    @Autowired
    MessageCenterService messageCenterService;

    @Autowired
    PushService pushService;

    @Autowired
    AcountMapper acountMapper;

    @Autowired
    UploadService uploadService;

    @Autowired
    ChildAccountMapper childAccountMapper;

    @Autowired
    private EventBus eventBus;

    @Resource
    ProductService productService;

    /**
     * 获取喜报列表
     * @param accountId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<GleefulReportResponse> getGleefulReportList(String accountId,String sharer, Date startDate, Date endDate)
    {
        List<GleefulReportResponse> gleefulReportResponses = new ArrayList<>();
        try
        {
            gleefulReportResponses = gleefulReportMapper.getGleefulReportList(accountId,sharer,startDate, endDate);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        return gleefulReportResponses;
    }

    /**
     * 获取单条喜报详情
     * @param id
     * @return
     */
    public GleefulReport getGleefulReportDetail(int id)
    {
        GleefulReport gleefulReport = new GleefulReport();
        try
        {
            gleefulReport = gleefulReportMapper.selectByPrimaryKey(id);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
        }

        return gleefulReport;
    }

    /**
     * 删除单条数据
     * @param id
     * @return
     */
    public boolean deleteGleefulReportById(int id)
    {
        boolean result = true;
        try
        {
            gleefulReportMapper.deleteByPrimaryKey(id);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            result=false;
        }
        return result;
    }


    /**
     * 追加/更新喜报
     * @param gleefulReport
     * @return
     */
    public boolean updateGleefulReport(GleefulReport gleefulReport,String subPhone)
    {
        try
        {
            if(gleefulReportMapper.updateByPrimaryKeySelective(gleefulReport)==0) {
                // 获取喜报ID
                gleefulReportMapper.insert(gleefulReport);
                Integer xibaoId = (Integer) gleefulReport.getId();
                // 【喜报代办&推送】
                // 获取分配人员列表
                String accountId = gleefulReport.getAccountId();
                Acount acount = acountMapper.selectByPrimaryKey(Integer.valueOf(accountId));
                String type = gleefulReport.getType();
                List<GleefulReportDispatcher> gleefulReportDispatchers = gleefulReportDispatcherMapper.getGleefulSharedList(accountId, type);


                List<String> sharedList =new ArrayList<>();
                for(GleefulReportDispatcher sharer:gleefulReportDispatchers) {
                    sharedList.add(sharer.getSharer());
                }
                //子账号登录并且当前分配列表中没有此账号时，自动追加
                if(StringUtils.isNotEmpty(subPhone)&&!sharedList.contains(subPhone))
                {
                    sharedList.add(subPhone);
                    updateSharedDispatcher(sharedList,gleefulReport.getAccountId(),gleefulReport.getId().toString(),gleefulReport.getType());
                }

                GleefulAccountType gat = new GleefulAccountType();
                gat.setAccountId(accountId);
                gat.setType(type);
                gat.setId(gleefulReport.getId());
                String param = JSONObject.toJSONString(gat);
                BaseEvent event = new BaseEvent();
                event.setData(param);
                event.setEventName(EventConstants.PUSH_GLEEFUL_REPORT_EVENT);
                eventBus.publish(event);

                for (GleefulReportDispatcher sharer:gleefulReportDispatchers) {
                    MessageCenter mc = new MessageCenter();
                    mc.setTitle("喜报通知");
                    mc.setContent("【喜报】您有一条新的喜报，点击查看详情");
                    mc.setCreateTime(new java.util.Date());
                    mc.setEnable(1);
                    mc.setPushTo(sharer.getSharer());
                    mc.setPushFrom("systems");
                    mc.setContentType("taskXB");
                    mc.setPushStatus(1);
                    mc.setXibaoId(xibaoId);
                    mc.setRoleName("xb");
                    int sf = messageCenterService.messageCenterAdd(mc);
                    logger.info("插入数据"+sf+"条");
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            return false;
        }

        return true;
    }


    /**
     * 更新分配人，以类型区分
     * @param shareds
     * @param accountId
     * @param reportId
     * @param type
     * @return
     */
    public boolean updateSharedDispatcher(List<String>shareds ,String accountId,String reportId,String type)
    {
        try
        {
            gleefulReportDispatcherMapper.updateEnableByAccountId(accountId,type);
            for(String sharer:shareds)
            {
                if(gleefulReportDispatcherMapper.updateEnableBySharer(accountId,sharer,type)==0)
                {
                    GleefulReportDispatcher gleefulReportDispatcher = new GleefulReportDispatcher();
                    gleefulReportDispatcher.setAccountId(accountId);
                    gleefulReportDispatcher.setSharer(sharer);
                    gleefulReportDispatcher.setEnable(1);
                    gleefulReportDispatcher.setReportId(reportId);
                    gleefulReportDispatcher.setType(type);

                    gleefulReportDispatcherMapper.insert(gleefulReportDispatcher);
                }
            }
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * 获取分配列表
     * @param accountId
     * @param type
     * @return
     */
    public List<GleefulSharerResponse> getSharedDispatcherList(String accountId,String type)
    {
        List<GleefulSharerResponse> gleefulSharerResponses = new ArrayList<>();

        try
        {
            List<SubUserListResponse> subUserListResponseList = subUserService.getSubUserList(Integer.parseInt(accountId));
            List<GleefulReportDispatcher> gleefulReportDispatchers = gleefulReportDispatcherMapper.getGleefulSharedList(accountId, type);
            List<String> sharedList = new ArrayList<>();
            for(GleefulReportDispatcher gleefulReportDispatcher:gleefulReportDispatchers)
            {
                sharedList.add(gleefulReportDispatcher.getSharer());
            }

            for(SubUserListResponse subUserListResponse:subUserListResponseList)
            {
                String isCheck = sharedList.contains(subUserListResponse.getChildAccountMobile())?"1":"0";

                GleefulSharerResponse gleefulSharerResponse = new GleefulSharerResponse();
                gleefulSharerResponse.setSharer(subUserListResponse.getChildAccountMobile());
                gleefulSharerResponse.setSharerName(subUserListResponse.getChildAccountName());
                gleefulSharerResponse.setIsCheck(isCheck);

                gleefulSharerResponses.add(gleefulSharerResponse);
            }

        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
        }

        return gleefulSharerResponses;
    }

    /**
     * 插入分享记录
     * @param accountId
     * @param sharer
     * @param reportId
     * @return
     */
    public boolean updateSharedRecord(String accountId,String sharer,String reportId)
    {
        try
        {
            GleefulReport gleefulReport = gleefulReportMapper.selectByPrimaryKey(Integer.parseInt(reportId));
            SharedRecord sharedRecord = new SharedRecord();
            sharedRecord.setSharer(sharer);
            sharedRecord.setAccountId(accountId);
            sharedRecord.setShareTo("朋友圈");
            sharedRecord.setShareType(gleefulReport.getType());
            sharedRecord.setShareId(reportId);
            sharedRecord.setAccountId(accountId);
            sharedRecord.setEnable(1);

            sharedRecordMapper.insert(sharedRecord);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     *
     * @param accountId
     * @param sharedId
     * @return
     */
    public Map<String,Object> getGleefulSharedRecordList(String accountId, String sharedId)
    {
        Map<String,Object> resultMap = new HashMap<>();


        List<SharedRecord> sharedRecords = sharedRecordMapper.getSharedRecordList(accountId, sharedId);

        GleefulReport gleefulReport = gleefulReportMapper.selectByPrimaryKey(Integer.parseInt(sharedId));

        //获取当前type需要分享的人员
        List<GleefulReportDispatcher> gleefulReportDispatchers = gleefulReportDispatcherMapper.getGleefulSharedList(accountId, gleefulReport.getType());

        List<String> sharedList = new ArrayList<>();
        for(SharedRecord sharedRecord:sharedRecords)
        {
            sharedList.add(sharedRecord.getSharer());
        }

        int sharedCount = 0;
        int unSharedCount=0;
        List<String> sharedNameList= new ArrayList<>();
        List<String> unSharedNameList=new ArrayList<>();

        for (GleefulReportDispatcher gleefulReportDispatcher : gleefulReportDispatchers) {
            String name = sharedRecordMapper.getSharerName(accountId,gleefulReportDispatcher.getSharer());
            if (!StringUtil.isEmpty(name)){
                boolean isShared = sharedList.contains(gleefulReportDispatcher.getSharer());
                if (isShared) {
                    sharedCount++;
                    sharedNameList.add(name);
                } else {
                    unSharedCount++;
                    unSharedNameList.add(name);
                }
            }
        }

        resultMap.put("sharedCount",sharedCount);
        resultMap.put("unSharedCount",unSharedCount);
        resultMap.put("sharedList",sharedNameList);
        resultMap.put("unSharedList",unSharedNameList);

        return resultMap;
    }

    public Integer getGleefulSharedRecordCount(String sharer){
        Integer count = gleefulReportMapper.getGleefulSharedRecordCount(sharer);
        return count;
    }

    @Override
    public Map getProductMessage(int accountId,String account) {
        Map<String,Object> map = productService.productMsg(accountId,account);
        return map;
    }


}
