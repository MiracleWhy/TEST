package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.uton.carsokApi.controller.request.InsertQuoteInfoRequest;
import com.uton.carsokApi.controller.response.CarsokQuoteDetailsResponse;
import com.uton.carsokApi.controller.response.CarsokQuoteListResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.CarsokQuoteMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.ChildInfoService;
import com.uton.carsokApi.service.ICarsokQuoteService;
import com.uton.carsokApi.service.MessageCenterService;
import com.uton.carsokApi.service.PushService;
import com.uton.carsokApi.util.DozerMapperUtil;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
@Service
public class CarsokQuoteServiceImpl extends ServiceImpl<CarsokQuoteMapper, CarsokQuote> implements ICarsokQuoteService {

    @Autowired
    CarsokQuoteMapper carsokQuoteMapper;

    @Autowired
    PushService pushService;

    @Autowired
    MessageCenterService messageCenterService;

    @Autowired
    AcountMapper acountMapper;

    @Autowired
    private ChildInfoService childInfoService;

    private class PushMessage implements Runnable {
        private String content;
        private String type;
        private String account;

        public PushMessage(String content) {
            this.content = content;
            this.type = "System";
        }

        public PushMessage(String content, String type) {
            this.content = content;
            this.type = type;
        }

        public PushMessage(String content, String type, String account) {
            this.content = content;
            this.type = type;
            this.account = account;
        }
        @Override
        public void run() {
            try {
                boolean df = pushService.SendCustomizedCast(account, content, type);
                System.out.print("推送标识：" + df + "----推送给：" + account + "----推送内容：" + content + "----跳转类型：" + type);
            } catch (Exception e) {
                //logger.error("推送消息错误:"+e.getMessage());
            }
        }
    }

    /**
     * 通过id获取详细报价信息
     *
     * @param id
     * @return
     */
    @Override
    public CarsokQuoteDetailsResponse getQuoteMessageById(Integer id) {
        CarsokQuoteDetailsResponse cqr = carsokQuoteMapper.getQuoteMessageById(id);
        BigDecimal b = new BigDecimal(10000);
        if (cqr.getIntentionalPrice() != null) {
            cqr.setIntentionalPrice(cqr.getIntentionalPrice().divide(b));
        }
        return cqr;
    }

    @Override
    public boolean insertQuoteInfo(InsertQuoteInfoRequest i) {
        final BigDecimal bigDecimal = new BigDecimal(10000);
        if (i.getIntentionalPrice() != null) {
            i.setIntentionalPrice(i.getIntentionalPrice().multiply(bigDecimal));
        }
        CarsokQuote carsokQuote = new CarsokQuote();
        DozerMapperUtil.getInstance().map(i, carsokQuote);
        carsokQuote.setCreateTime(new Date());
        CarsokFindCar findCar = new CarsokFindCar().selectById(i.getIntentionalProductId());
        if (findCar == null) {
            return false;
        }
        boolean flag = carsokQuote.insertOrUpdate();
        if (flag == true) {

        }
        if (flag) {
            sendMessage(findCar.getChildId().toString(), "寻车报价消息推送", "quoteMessage",
                    findCar.getAccountId().toString(), carsokQuote.getId().toString());
        }
        return flag;
    }

    public synchronized void sendMessage(String childId, String contents, String types, String accountId, String quoteId) {
        Acount acount = null;
        String accountPhone = "";
        MessageCenter mc = new MessageCenter();
        String pushto = "";

        if (StringUtil.isEmpty(childId) || "0".equals(childId)) {
            if (!StringUtil.isEmpty(accountId)) {
                acount = acountMapper.selectByPrimaryKey(Integer.parseInt(accountId));
                if(acount == null){
                    pushto = "0";
                }else{
                    accountPhone = acount.getAccount();
                    pushto = accountPhone;
                }

            }

        }else{
            //如果是子账号 则推送到alia字段
            CarsokChildAccount childAccount = new CarsokChildAccount().selectById((Integer.parseInt(childId)));
            if (childAccount != null) {
                accountPhone = childAccount.getAccountPhone();
                pushto = childAccount.getAccountPhone();
            }
        }
        mc.setQuoteId(Integer.parseInt(quoteId));
        mc.setTitle("寻车报价消息");
        mc.setContent(contents);
        mc.setContentType(types);
        mc.setEnable(1);
        mc.setCreateTime(new Date());
        mc.setPushTo(accountPhone);
        mc.setPushFrom("systems");
        mc.setPushStatus(1);
        //将数据添加到推送表中
        messageCenterService.messageCenterAdd(mc);
        //（另起线程，防止延迟情款）向app推送消息
        CarsokQuoteServiceImpl.PushMessage pushMessage = new CarsokQuoteServiceImpl.PushMessage(contents, types, pushto);
        pushMessage.run();
    }

    /**
     * 获取报价信息列表
     * @param accountId
     * @return
     */
    @Override
    public List<CarsokQuoteListResponse> getQuoteMessageList(String accountId, String createTime) {
        return carsokQuoteMapper.getQuoteMessageList(accountId,createTime);
    }
}
