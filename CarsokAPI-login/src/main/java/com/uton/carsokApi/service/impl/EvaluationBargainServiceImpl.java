package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.controller.request.BargainRequest;
import com.uton.carsokApi.controller.request.EvaluationRequest;
import com.uton.carsokApi.controller.response.BargainAccountResponse;
import com.uton.carsokApi.controller.response.BargainInfoResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.EvaluationBargainMapper;
import com.uton.carsokApi.dao.ProductMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.EvaluationBargainService;
import com.uton.carsokApi.service.MessageCenterService;
import com.uton.carsokApi.service.PushService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */
@Service
public class EvaluationBargainServiceImpl implements EvaluationBargainService {

    @Autowired
    EvaluationBargainMapper evaluationBargainMapper;

    @Autowired
    PushService pushService;

    @Autowired
    MessageCenterService messageCenterService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    AcountMapper acountMapper;

    private class PushMessage implements Runnable{
        private String content;
        private String type;
        private String account;

        public PushMessage(String content){
            this.content=content;
            this.type="System";
        }

        public PushMessage(String content,String type){
            this.content=content;
            this.type=type;
        }

        public PushMessage(String content,String type,String account){
            this.content=content;
            this.type=type;
            this.account=account;
        }


        @Override
        public void run() {
            try {
                boolean df = pushService.SendCustomizedCast(account,content,type);
                System.out.print("推送标识："+df +"----推送给："+account+"----推送内容："+content+"----跳转类型："+type);
            }
            catch(Exception e){
                //logger.error("推送消息错误:"+e.getMessage());
            }
        }
    }


    @Override
    public int evaluationSubmit(EvaluationRequest vo) {
        Evaluations evaluations = new Evaluations();
        String accountId = "";
        if("1".equals(vo.getIsCar())){
            Product pro = productMapper.selectByProductId(vo.getAccountId());
            if(pro == null){
                return 0;
            }else {
                accountId = pro.getAccountId().toString();
            }
        }else {
            accountId = vo.getAccountId();
        }
        evaluations.setAccountId(accountId);
        evaluations.setMobile(vo.getMobile());
        evaluations.setName(vo.getName());
        evaluations.setVehicleModel(vo.getVehicleModel());
        evaluations.setCreateTime(new Date());
        if(evaluationBargainMapper.evaluationInsert(evaluations)>0){
            sendMessage("","车辆信息","Evaluations",accountId,evaluations.getId().toString());
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int bargainSubmit(BargainRequest vo) {
        Bargain bargain = new Bargain();
        bargain.setProductId(vo.getProductId());
        bargain.setMobile(vo.getMobile());
        bargain.setName(vo.getName());
        bargain.setIntentionalPrice(vo.getIntentionalPrice());
        bargain.setCreateTime(new Date());
        if(evaluationBargainMapper.bargainInsert(bargain)>0){
            sendMessage(bargain.getId().toString(),"车辆信息","Bargain","","");
            return 1;
        }else {
            return 0;
        }
    }

    public synchronized void sendMessage(String productId,String contents,String types,String accountId,String evaluationId){
        Acount acount = null;
        String accountPhone = "";
        MessageCenter mc = new MessageCenter();
        if(!StringUtil.isEmpty(productId)){
            BargainAccountResponse bargain = evaluationBargainMapper.selectAccountIdByBargainIdProductId(Integer.parseInt(productId));
            accountPhone = bargain.getAccount();
            mc.setProductId(Integer.parseInt(productId));
        }else if(!StringUtil.isEmpty(accountId)){
            acount = acountMapper.selectByPrimaryKey(Integer.parseInt(accountId));
            accountPhone = acount.getAccount();
            mc.setProductId(Integer.parseInt(evaluationId));
        }
        Acount ac = acountMapper.selectByMobile(accountPhone);
        String acName = ac.getNick()!=null?ac.getNick():(ac.getMerchantName()!=null?ac.getMerchantName():ac.getAccountCode()+"车行");
        if ("Evaluations".equals(types)){
            mc.setTitle(acName + "的卖车信息");
        }else if ("Bargain".equals(types)){
            mc.setTitle(acName + "的砍价信息");
        }else {
            mc.setTitle("待办任务通知");
        }
        mc.setContent(contents);
        mc.setContentType("task"+types);
        mc.setEnable(1);
        mc.setCreateTime(new Date());
        mc.setPushTo(accountPhone);
        mc.setPushFrom("systems");
        mc.setPushStatus(1);
        messageCenterService.messageCenterAdd(mc);
        PushMessage pushMessage = new PushMessage(contents,types,accountPhone);
        new Thread(pushMessage).start();
        //pushMessage.run();
    }

    @Override
    public Evaluations selectEvaluationMsgById(String id) {
        try {
            if(!StringUtil.isEmpty(id)){
                Evaluations evaluations = evaluationBargainMapper.selectEvaluationMsgById(Integer.parseInt(id));
                return evaluations;
            }else {
                return null;
            }
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public BargainInfoResponse selectProductMsgByProductId(String productId) {
        try{
            if(!StringUtil.isEmpty(productId)){
                BargainInfoResponse bargainInfoResponse = evaluationBargainMapper.selectProductMsgByProductId(Integer.parseInt(productId));
                return bargainInfoResponse;
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
