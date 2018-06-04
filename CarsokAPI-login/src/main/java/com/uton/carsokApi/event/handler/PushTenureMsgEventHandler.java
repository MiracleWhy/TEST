package com.uton.carsokApi.event.handler;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.EventStatusEnum;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.PushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2017/5/19 0019.
 */
@EventHandler(name = EventConstants.PUSH_TENURE_MESSAGE_EVENT)
@Service
public class PushTenureMsgEventHandler  extends EventHandle {
    @Autowired
    PushService pushService;
    @Autowired
    AcountMapper acountMapper;
    @Autowired
    ChildAccountMapper childAccountMapper;
    @Override
    public OperateResult handle(BaseEvent event) {
        Acount acount = acountMapper.selectByMobile(event.getData());
        String content = "您有一辆车已经售出，自动添加到保有客户管理中，点击查看详情";
        if(acount == null){
            ChildAccount childAccount = new ChildAccount();
            childAccount.setChildAccountMobile(event.getData());
            ChildAccount child = childAccountMapper.selectByModel(childAccount);
            String exception=event.getException();
            boolean b1=true;
            boolean b2=true;
            if (StringUtils.equals(event.getEventStatus(), EventStatusEnum.WAIT_RETRY.name())) {
                JSONObject info = JSONObject.parseObject(exception);
//                if (!info.getBoolean("accountResult")) {
//                    b1 = pushService.SendCustomizedCast(child.getAccountPhone(), content, "Bussiness");
//                    info.put("accountResult",b1);
//                }
                if (!info.getBoolean("childResult")){
                    b2 = pushService.SendCustomizedCast(child.getAlias(), content,"Bussiness");
                    info.put("childResult",b2);
                }
            }
            else{
                 //b1 = pushService.SendCustomizedCast(child.getAccountPhone(), content, "Bussiness");
                 b2 = pushService.SendCustomizedCast(child.getAlias(), content,"Bussiness");
            }
            if(!b1||!b2){
                JSONObject json=new JSONObject();
                json.put("accountResult",b1);
                json.put("childResult",b2);
                return new OperateResult(false,json.toJSONString());
            }
        }
        else{
//            boolean parent=pushService.SendCustomizedCast(event.getData(), content,"Bussiness");
//            if(!parent){
//                return new OperateResult(false,"主帐号结果"+parent);
//            }
        }
        return new OperateResult(true,"");
    }
}
