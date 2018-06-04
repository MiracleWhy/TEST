package com.uton.carsokApi.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.model.Product;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.CustomMessageRequest;
import com.uton.carsokApi.event.EventBus;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.model.MessageCenter;
import com.uton.carsokApi.service.CustomMessagePushService;
import com.uton.carsokApi.service.MessageCenterService;
import com.uton.carsokApi.service.PushService;

/**
 * 自定义消息推送
 * Created by Administrator on 2017/3/21 0021.
 */
@Controller
@RequestMapping("/CustomMessagePush")
public class CustomMessagePush {

    private final static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    CustomMessagePushService customMessagePushService;

    @Autowired
    PushService pushService;

    @Autowired
    MessageCenterService messageCenterService;

    @Autowired
    private EventBus eventBus;

    private class SendMessage implements Runnable{
        private String content;
        private String type;

        public SendMessage(String content)
        {
            this.content=content;
            this.type="System";
        }

        public SendMessage(String content,String type)
        {
            this.content=content;
            this.type=type;
        }

        @Override
        public void run() {
            try {
                List<String> accounts = customMessagePushService.selectAll();
                String first = content.substring(0, 1);
                //if (accounts != null && accounts.size() > 0) {
                //for (Acount account : accounts) {
                pushService.sendBroadCast(content,type);
                //}
                //}
            }
            catch(Exception e)
            {
                logger.error("推送消息错误:"+e.getMessage());
            }
        }
    }

    @RequestMapping(value = {"/messagePush"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult messagePush(@RequestBody CustomMessageRequest vo) {
        logger.info("pms信息传输，内容为：" + JSON.toJSONString(vo));
        boolean pushResult = false;
        try {
            String type;
            switch (vo.getContentType()) {
                case "systems":
                    type = "System";
                    break;
                case "advertisement":
                    type = "Advertisement";
                    break;
                default:
                    type = "System";
                    break;
            }
            MessageCenter mc = new MessageCenter();
            mc.setTitle(vo.getTitle());
            mc.setContent(vo.getContent());
            mc.setCreateTime(new Date());
            mc.setEnable(1);
            mc.setPushTo("all");
            mc.setPushFrom("systems");
            mc.setContentType(vo.getContentType());
            mc.setPushStatus(2);
            mc.setPicPath(vo.getPicPath());
            messageCenterService.messageCenterAdd(mc);
            logger.info("推送的信息正常存储，内容为：" + JSON.toJSONString(mc));
            if(vo != null && !StringUtil.isEmpty(vo.getTitle())){
                if("advertisement".equals(vo.getContentType())){
                    pushResult = pushService.sendBroadCast(vo.getTitle(), type);
                }else {
                    pushResult = pushService.sendBroadCast(vo.getTitle(), type);
                }
                if(pushResult){
                    messageCenterService.updatePushStatusById(mc.getId(),1);
                }else {
                    messageCenterService.updatePushStatusById(mc.getId(),0);
                }
                logger.info("推送的信息为：" + vo.getContent().substring(1));
                if (pushResult) {
                    logger.info("-------------推送成功");
                    return BaseResult.success();
                } else {
                    logger.info("-------------推送失败");
                    return BaseResult.fail("0051", "友盟异常，推送失败");
                }
            }else {
                return BaseResult.fail("0055", "信息异常，推送失败");
            }
            //Thread thread = new Thread(new SendMessage(vo.getContent()), "SendMessage");
            //thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.fail(ErrorCode.ExceptionRetCode, "推送失败");
    }

    @RequestMapping(value = {"/messagePush2"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult messagePush2(@RequestBody CustomMessageRequest vo) {
        boolean pushResult = false;
        String type = "review";

        Integer productId = vo.getId();
        String acount = pushService.getAcountPhone(productId);
        pushResult = pushService.SendCustomizedCast(acount, "您有一条车辆审核通知，点击查看详情","");
        MessageCenter mc = new MessageCenter();
        mc.setTitle(vo.getTitle());
        mc.setContent(vo.getContent());
        mc.setCreateTime(new Date());
        mc.setEnable(1);
        mc.setPushTo(acount);
        mc.setPushFrom("systems");
        mc.setContentType(vo.getContentType());
        mc.setPushStatus(2);
        messageCenterService.messageCenterAdd(mc);
        if(pushResult){
            messageCenterService.updatePushStatusById(mc.getId(),1);
        }else {
            messageCenterService.updatePushStatusById(mc.getId(),0);
        }
        logger.info("推送的信息为：" + vo.getContent().substring(1));
        if (pushResult) {
            return BaseResult.success();
        } else {
            return BaseResult.fail(ErrorCode.ExceptionRetCode, "推送失败");
        }
    }

    @RequestMapping(value={"/messageList"} ,method = { RequestMethod.POST })
    public @ResponseBody BaseResult messageList(@RequestBody CustomMessageRequest vo){
        try{
            return pushService.selectMessage(vo.getMobile(),vo.getPr(),vo.getPc());
        }catch (Exception e) {
            logger.error("查询推送消息列表异常  error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value={"/messagePushBymerchant"} ,method = { RequestMethod.POST })
    public @ResponseBody BaseResult messagePushBymerchant(HttpServletRequest request,@RequestBody CustomMessageRequest vo){
        try{
            String ids = vo.getContent().substring(12);
            int id = Integer.parseInt(ids);
            Acount acount = pushService.selecMobile(id);
            //pushService.sendIOSCustomizedcast2(acount.getAccount(),vo.getContent().substring(0,12));
            boolean df = pushService.SendCustomizedCast(acount.getAccount(),vo.getContent().substring(0,12),"");
            MessageCenter mc = new MessageCenter();
            mc.setTitle("商家认证通知");
            mc.setContent(vo.getContent().substring(0,12));
            mc.setCreateTime(new Date());
            mc.setEnable(1);
            mc.setPushTo(acount.getAccount());
            mc.setPushFrom("systems");
            mc.setContentType("systems");
            mc.setPushStatus(1);
            int sf = messageCenterService.messageCenterAdd(mc);
            if("您申请的商家认证已经通过".equals(vo.getContent().substring(0,12))){
                BaseEvent event=new BaseEvent();
                event.setEventName(EventConstants.UPDATE_QUOTIENT_SCORE);
                event.setWeight("10");
                JSONObject obj=new JSONObject();
                obj.put("id", id);
                obj.put("score",20);
                event.setData(obj.toJSONString());
                eventBus.publish(event);
            }
            logger.info("----------商家认证通知:接收人: "+acount.getAccount()+"商家认证推送的信息为: "+vo.getContent().substring(0,12)+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
            return BaseResult.success();
        }catch (Exception e) {
            logger.error("查询推送消息列表异常  error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 新车直供消息推送
     * @param request
     * @return
     */
    @RequestMapping(value={"/newcarMessagePush"} ,method = { RequestMethod.POST })
    public  BaseResult newcarMessagePush(HttpServletRequest request,String content,String pushto){
        try{
            boolean flag = pushService.SendCustomizedCast(pushto,content,"System");
            logger.info("----------新车直供消息推送:接收人: "+pushto+"新车直供消息推送: "+content+", 时间: "+new Date()+", 发送是否成功: "+flag+" ----------");
            return BaseResult.success();
        }catch (Exception e) {
            logger.error("查询推送消息列表异常  error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

}


