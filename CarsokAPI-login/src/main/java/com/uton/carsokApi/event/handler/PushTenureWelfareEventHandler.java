package com.uton.carsokApi.event.handler;

import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.util.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/26.
 */
@EventHandler(name = EventConstants.MESSAGE_UPDATE_CAR_WELFARE)
@Service
public class PushTenureWelfareEventHandler extends EventHandle {

    @Value("${buyerWelfareSide}")
    private String buyerWelfareSide;

    @Override
    public OperateResult handle(BaseEvent event) {
        String flag = HttpClientUtil.sendPostRequestByJava(buyerWelfareSide,event.getData());
        if(StringUtils.indexOf(flag,"Fail")!=-1){
            return new OperateResult(false,flag);
        }
        return new OperateResult(true,"");

    }
}
