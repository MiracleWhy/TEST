package com.uton.carsokApi.event.handler;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.constants.enums.PayWayEnum;
import com.uton.carsokApi.event.EventBus;
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
 * Created by Administrator on 2017/4/10 0010.
 */
@EventHandler(name = EventConstants.PUSH_AUTO_INFO_TO_BUYER_APP_EVENT)
@Service
public class PushAutoInfoToBuyerEventHandler  extends EventHandle {

        @Value("${buyerSide}")
        private String buyerSide;
        @Override
        public OperateResult handle(BaseEvent event) {
            String flag = HttpClientUtil.sendPostRequestByJava(buyerSide,event.getData());
            if(StringUtils.indexOf(flag,"Fail")!=-1){
                return new OperateResult(false,flag);
            }
            return new OperateResult(true,"");

        }

    }


