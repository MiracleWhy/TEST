package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.constants.enums.OnSelfStatus;
import com.uton.carsokApi.controller.request.ShareMomentRequest;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ShareRecordMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.push.AndroidNotification;
import com.uton.carsokApi.push.android.AndroidBroadcast;
import com.uton.carsokApi.push.android.AndroidCustomizedcast;
import com.uton.carsokApi.push.ios.IOSBroadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.push.PushClient;
import com.uton.carsokApi.push.ios.IOSCustomizedcast;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static com.uton.carsokApi.push.AndroidNotification.DisplayType.MESSAGE;
import static com.uton.carsokApi.push.AndroidNotification.DisplayType.NOTIFICATION;

@Service("pushservice")
public class PushService {
    @Value("${AppKey}")
    private String appKey;

    @Value("${AppMasterSecret}")
    private String appMasterSecret;

    @Value("${AndriodAppKey}")
    private String andriodAppKey;

    @Value("${AndriodAppMasterSecret}")
    private String andriodAppMasterSecret;

    private boolean testMode = false;

    private PushClient client = new PushClient();

    @Autowired
    ShareRecordMapper shareRecordMapper;

    @Autowired
    AcountMapper acountMapper;

    @Resource
    CacheService cacheService;

    /**
     * 任务消息推送
     *
     * @param alias
     */
    public void sendIOSCustomizedcast(String alias) {
        try {
            IOSCustomizedcast customizedcast = new IOSCustomizedcast(appKey, appMasterSecret);
            customizedcast.setAlias(alias, "subLogin");
            customizedcast.setAlert("您有一项新的待处理任务，点击查看详情");
            customizedcast.setBadge(0);
            customizedcast.setSound("default");
            //正式环境的时候修改为customizedcast.setProductionMode()
            customizedcast.setProductionMode();
            client.send(customizedcast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendIOSCustomizedcast2(String mobile, String content) {
        try {
            IOSCustomizedcast customizedcast = new IOSCustomizedcast(appKey, appMasterSecret);
            customizedcast.setAlias(mobile, "subLogin");
            customizedcast.setAlert(content);
            customizedcast.setBadge(0);
            customizedcast.setSound("default");
            customizedcast.setProductionMode();
            client.send(customizedcast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAndriodCustomizedcast2(String mobile, String content) {
        try {
            AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(andriodAppKey, andriodAppMasterSecret);
            customizedcast.setAlias(mobile, "subLogin");
            customizedcast.setSound("default");
            customizedcast.setProductionMode();
            customizedcast.setDisplayType(NOTIFICATION);
            customizedcast.setTicker("车商APP");
            customizedcast.setText(content);
            customizedcast.setCustomField(content);
            customizedcast.setTitle("车商APP");
            client.send(customizedcast);
        } catch (Exception e) {

        }
    }

    public BaseResult shareMomentCount(ShareMomentRequest vo) {
        ShareRecord shareRecord = new ShareRecord();
        shareRecord.setShareAccountCode(vo.getMobile());
        shareRecord.setShareDate(new Date());
        Integer success = shareRecordMapper.insert(shareRecord);
        if (Integer.valueOf(0) == success) {
            return BaseResult.fail(ErrorCode.InsertShareRecordFail, ErrorCode.InsertShareRecordFailInfo);
        }
        return BaseResult.success();
    }

    /**
     * 查看推送消息
     */
    public BaseResult selectMessage(String mobile, int pr, int pc) {
        String pushTo = mobile;
        int p1 = (pr * pc) - pr;
        int p2 = pr;
        Pagebean<PushContent> pb = new Pagebean<PushContent>();
        Number number = shareRecordMapper.selectMessageCount(pushTo);
        List messageList = shareRecordMapper.selectMessageList(pushTo, p1, p2);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        pb.setBeanlist(messageList);
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(pb);
        return baseResult;
    }

    /**
     * 根据Id查询acount
     */
    public Acount selecMobile(int id) {
        return acountMapper.selectByPrimaryKey(id);
    }


    public boolean sendBroadCast(String content, String type) {
        boolean result =false;

        try {
            IOSBroadcast iosBroadcast = new IOSBroadcast(appKey, appMasterSecret);
            AndroidBroadcast androidBroadcast = new AndroidBroadcast(andriodAppKey, andriodAppMasterSecret);

            if (this.testMode) {
                androidBroadcast.setTestMode();
                iosBroadcast.setTestMode();
            } else {
                iosBroadcast.setProductionMode();
                androidBroadcast.setProductionMode();
            }

            //跳转
            switch (type) {
                case "Bussiness"://待办事项
                    iosBroadcast.setCustomizedField("messageCenter_Skip", "com.UTCar.BusinessMessage");
                    androidBroadcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidBroadcast.setActivity("com.uton.cardealer.activity.message.backlog.BackLogActivity");
                    break;
                case "Other"://其他推广
                    iosBroadcast.setCustomizedField("messageCenter_Skip", "com.UTCar.OtherMessage");
                    androidBroadcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidBroadcast.setActivity("com.uton.cardealer.activity.message.push.PushHistoryMessageActivity");
                    break;
                case "System"://系统消息
                    iosBroadcast.setCustomizedField("messageCenter_Skip", "com.UTCar.SystemMessage_systems");
                    androidBroadcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidBroadcast.setActivity("com.uton.cardealer.activity.message.system.SystemsMessageActivity");
                    break;
                case "Advertisement":
                    iosBroadcast.setCustomizedField("messageCenter_Skip", "com.UTCar.SystemMessage_advertisement");
                    androidBroadcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidBroadcast.setActivity("com.uton.cardealer.activity.message.headline.HeadLineActivity");
                default:
                    //do nothing
                    break;
            }

            androidBroadcast.setSound("default");
            androidBroadcast.setDisplayType(NOTIFICATION);
            androidBroadcast.setTicker("车商APP");
            androidBroadcast.setText(content);
            androidBroadcast.setCustomField(content);
            androidBroadcast.setTitle("车商APP");

            iosBroadcast.setAlert(content);
            iosBroadcast.setBadge(0);
            iosBroadcast.setSound("default");

            if (client.send(iosBroadcast)&&client.send(androidBroadcast)) {
                result=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean SendCustomizedCast(String mobile, String content, String type) {
        boolean result = false;
        try {

            AndroidCustomizedcast androidCustomizedcast = new AndroidCustomizedcast(andriodAppKey, andriodAppMasterSecret);
            IOSCustomizedcast iosCustomizedcast = new IOSCustomizedcast(appKey, appMasterSecret);

            //是否为测试模式
            if (this.testMode) {
                iosCustomizedcast.setTestMode();
                androidCustomizedcast.setTestMode();
            } else {
                iosCustomizedcast.setProductionMode();
                androidCustomizedcast.setProductionMode();
            }

            //跳转
            switch (type) {
                case "Bussiness"://待办事项
                    iosCustomizedcast.setCustomizedField("messageCenter_Skip", "com.UTCar.BusinessMessage");
                    androidCustomizedcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidCustomizedcast.setActivity("com.uton.cardealer.activity.message.backlog.BackLogActivity");
                    break;
                case "Other"://其他推广
                    iosCustomizedcast.setCustomizedField("messageCenter_Skip", "com.UTCar.OtherMessage");
                    androidCustomizedcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidCustomizedcast.setActivity("com.uton.cardealer.activity.message.push.PushHistoryMessageActivity");
                    break;
                case "System"://系统消息
                    iosCustomizedcast.setCustomizedField("messageCenter_Skip", "com.UTCar.SystemMessage_systems");
                    androidCustomizedcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidCustomizedcast.setActivity("com.uton.cardealer.activity.message.system.SystemsMessageActivity");
                    break;
                case "Advertisement":
                    iosCustomizedcast.setCustomizedField("messageCenter_Skip", "com.UTCar.SystemMessage_advertisement");
                    androidCustomizedcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidCustomizedcast.setActivity("com.uton.cardealer.activity.message.headline.HeadLineActivity");
                case "Outdate"://潜客和保有逾期任务提醒
                    iosCustomizedcast.setCustomizedField("messageCenter_Skip", "com.UTCar.OutdateMessage");
                    androidCustomizedcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidCustomizedcast.setActivity("com.uton.cardealer.activity.message.utonw.OutdateActivity");
                    break;
                case "ReadyTask"://潜客和保有代办任务提醒
                    iosCustomizedcast.setCustomizedField("messageCenewcarnter_Skip", "com.UTCar.ReadyTaskMessage");
                    androidCustomizedcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidCustomizedcast.setActivity("com.uton.cardealer.activity.message.utonw.ReadyTaskActivity");
                    break;
                case "Evaluations"://卖车
                    iosCustomizedcast.setCustomizedField("messageCenter_Skip", "com.UTCar.SellCar");
                    androidCustomizedcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidCustomizedcast.setActivity("com.uton.cardealer.activity.message.sellCarInfo.SellCarActivity");
                    break;
                case "Bargain"://砍价
                    iosCustomizedcast.setCustomizedField("messageCenter_Skip", "com.UTCar.Bargaining");
                    androidCustomizedcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_activity);
                    androidCustomizedcast.setActivity("com.uton.cardealer.activity.message.utonw.BargainActivity");
                    break;
                default:
                    //do nothing
                    break;
            }
            //Android
            androidCustomizedcast.setAlias(mobile, "subLogin");
            androidCustomizedcast.setSound("default");
            androidCustomizedcast.setDisplayType(NOTIFICATION);
            androidCustomizedcast.setTicker("车商APP");
            androidCustomizedcast.setText(content);
            androidCustomizedcast.setCustomField(content);
            androidCustomizedcast.setTitle("车商APP");

            //IOS
            iosCustomizedcast.setAlias(mobile, "subLogin");
            iosCustomizedcast.setAlert(content);
            iosCustomizedcast.setBadge(1);
            iosCustomizedcast.setSound("default");

            //Send
            if (client.send(iosCustomizedcast)) {
                result = true;
            } else if (client.send(androidCustomizedcast)) {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getAcountPhone(Integer productId) {
        return shareRecordMapper.getAcountPhone(productId);
    }
}
