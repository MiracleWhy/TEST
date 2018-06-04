package com.uton.carsokApi.event.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.GleefulReportDispatcherMapper;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.EventStatusEnum;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.MessageCenterService;
import com.uton.carsokApi.service.PushService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
@EventHandler(name = EventConstants.PUSH_GLEEFUL_REPORT_EVENT)
@Service
public class PushGleefulReportEventHandler extends EventHandle {

    private final static Logger logger = Logger.getLogger(PushGleefulReportEventHandler.class);

    @Autowired
    GleefulReportDispatcherMapper gleefulReportDispatcherMapper;
    @Autowired
    AcountMapper acountMapper;
    @Autowired
    ChildAccountMapper childAccountMapper;
    @Autowired
    MessageCenterService messageCenterService;
    @Autowired
    PushService pushService;

    @Override
    public OperateResult handle(BaseEvent event){
        JSONObject gat = JSON.parseObject(event.getData());
        List<GleefulReportDispatcher> gleefulReportDispatchers = gleefulReportDispatcherMapper.getGleefulSharedList(gat.get("accountId").toString(), gat.get("type").toString());
        for (GleefulReportDispatcher sharer:gleefulReportDispatchers) {
            //获取子账号的alias
            ChildAccount child = new ChildAccount();
            child.setChildAccountMobile(sharer.getSharer());
            child = childAccountMapper.selectByModel(child);
            String mobile = child.getAlias();
            //记录是否推送成功
            boolean df = false;
            if (!StringUtil.isEmpty(mobile)){
                if (StringUtils.equals(event.getEventStatus(), EventStatusEnum.WAIT_RETRY.name())) {
                    String exception = event.getException();
                    JSONObject info = JSONObject.parseObject(exception);
                    if (!info.getBoolean("result")) {
                        df = pushService.SendCustomizedCast(mobile, "【喜报】您有一条新的喜报，点击查看详情", "Bussiness");
                        info.put("result", df);
                    }
                }
                if (!df) {
                    JSONObject json = new JSONObject();
                    json.put("result", df);
                    return new OperateResult(false, json.toJSONString());
                }
            }
            logger.info("----------喜报:接收人: "+sharer.getSharer()+", 时间: "+new java.util.Date()+", 发送是否成功: "+df+" ----------");
        }
        return new OperateResult(true,"");
    }
}
