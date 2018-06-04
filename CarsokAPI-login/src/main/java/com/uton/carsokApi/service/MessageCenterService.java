package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.enums.ModuleEnums;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.controller.LoginController;
import com.uton.carsokApi.controller.request.QianKeBaoYouDBRequest;
import com.uton.carsokApi.controller.request.ZbCountRequest;
import com.uton.carsokApi.controller.response.QianKeBaoYouDBResponse;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.MessageCenterMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokZbTaskSxyWb;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.MessageCenter;
import com.uton.carsokApi.service.core.task.FilterSQLParam;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

@Service("messagecenterservice")
public class MessageCenterService {

    private final static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    MessageCenterMapper messageCenterMapper;

    @Resource
    CacheService cacheService;

    @Autowired
    AcountMapper acountMapper;

    @Autowired
    ChildAccountMapper childAccountMapper;

    @Resource
    ITaskFacade iTaskFacade;

    @Autowired
    SaasTenureCustomerService saasTenureCustomerService;


    public int messageCenterAdd(MessageCenter mc){
        return messageCenterMapper.messageCenterAdd(mc);
    }

    public void updatePushStatusById(int id,int status){
        messageCenterMapper.updatePushStatusById(id,status);
    }

    public BaseResult selectByContentType(HttpServletRequest request,MessageCenter vo){
        Acount acount = cacheService.getAcountInfoFromCache(request);
        Acount acounts = new Acount();
        acounts.setAccount(vo.getPushTo());
        Acount acount1 = acountMapper.selectByModel(acounts);
        List<MessageCenter> list = new ArrayList<MessageCenter>();
        //如果contentType为空说明用contentTypes
        if(vo.getContentType()==null){
            List<String> typeList = new ArrayList<>();
            typeList.add("taskPGS");
            typeList.add("taskZBY");
            typeList.add("taskManager");
            typeList.add("taskYYZY");
            typeList.add("taskRemind");
            typeList.add("taskTenure");
            typeList.add("taskDC");
            typeList.add("taskAcquisition");
            typeList.add("taskTenureThree");
            typeList.add("taskSXY");
            typeList.add("taskBargain");
            typeList.add("taskEvaluation");
            if(acount1==null){
                if(vo.getContentTypes().contains("taskZJL")){
                    typeList = roleList();
                    List<String> mobiles = new ArrayList<String>();
                    List<ChildAccount> childList = childAccountMapper.selectAllChild(vo.getPushTo());
                    for(ChildAccount child:childList){
                        mobiles.add(child.getChildAccountMobile());
                    }
                    ChildAccount child = new ChildAccount();
                    child.setChildAccountMobile(vo.getPushTo());
                    ChildAccount childAccount = childAccountMapper.selectByModel(child);
                    mobiles.add(childAccount.getAccountPhone());
                    list = messageCenterMapper.selectByContentTypeOfZjl(mobiles,typeList,vo.getCreateTime());
//                  消息中心，卖车评估列表优化
                    list = msgUpgrade(list);
                }else{
                    list = messageCenterMapper.selectByContentType(vo.getPushTo(),typeList,vo.getCreateTime());
                    list = msgUpgrade(list);
                }
            }else if (acount1 != null){
                list = messageCenterMapper.selectByContentType(acount1.getAccount(),typeList,vo.getCreateTime());
                list = msgUpgrade(list);
            }
        }else{
            List<String> sysList = new ArrayList<String>();
            sysList.add(vo.getContentType());
            if("taskEvaluations".equals(vo.getContentType()) || "taskBargain".equals(vo.getContentType())){
                list = messageCenterMapper.selectByContentType(vo.getPushTo(),sysList,vo.getCreateTime());
                list = msgUpgrade(list);
            }else {
                list = messageCenterMapper.selectByContentType("all",sysList,vo.getCreateTime());
                list = msgUpgrade(list);
                //sysList.add("advertisement");
                if (StringUtil.isEmpty(acount.getSubPhone())){
                    // 说明是主账号登录
                    List<MessageCenter> renzhengList = new ArrayList<MessageCenter>();
                    renzhengList = messageCenterMapper.selectByContentType(acount.getAccount(),sysList,vo.getCreateTime());
                    renzhengList = msgUpgrade(renzhengList);
                    for (MessageCenter renzhengMsg : renzhengList){
                        list.add(renzhengMsg);
                    }
                }
            }
        }
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(list);
        return baseResult;
    }

    /**
     * 消息中心，卖车评估列表优化
     * zhanghaopeng
     * @param list
     * @return
     */
    public List<MessageCenter> msgUpgrade(List<MessageCenter> list){
        List<MessageCenter> lists = new ArrayList<MessageCenter>();
        for (Iterator<MessageCenter> it = list.iterator(); it.hasNext();) {
            MessageCenter ow= it.next();
            if(ow.getProductId()!=null){
                if(!StringUtil.isEmpty(messageCenterMapper.selectByProductId(ow.getProductId()))){
                    ow.setVehicleModel(messageCenterMapper.selectByProductId(ow.getProductId()));
                    lists.add(ow);
                }else{
                    lists.add(ow);
                }
            }else{
                lists.add(ow);
            }
            it.remove();
        }
        return lists;
    }

    public BaseResult selectExtentionByMobile(MessageCenter vo){
        List<Acount> list = acountMapper.selectExtention(vo.getPushTo());
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(list);
        return baseResult;
    }

    public int deleteCenter(int taskId,String roleName){
        if(StringUtil.isEmpty(roleName)){
            return messageCenterMapper.deleteCenterBytaskId(taskId);
        }else {
            return messageCenterMapper.deleteCenter(taskId,roleName);
        }
    }

    public int deleteMessage(String pushTo,String contentType)
    {
        return messageCenterMapper.deleteMessage(pushTo, contentType);
    }

    public void addMsg(String title,String content,String pushTo,String contentType,int mendianId,int baoyouId,int shoucheId){
        MessageCenter mc = new MessageCenter();
        mc.setTitle(title);
        mc.setContent(content);
        mc.setCreateTime(new Date());
        mc.setEnable(1);
        mc.setPushTo(pushTo);
        mc.setPushFrom("systems");
        mc.setContentType(contentType);
        mc.setPushStatus(1);
        mc.setMendianId(mendianId);
        mc.setBaoyouId(baoyouId);
        mc.setShoucheId(shoucheId);
        int sf = messageCenterAdd(mc);
        logger.info("----------推送:接收人: "+pushTo+", 时间: "+new Date()+", 数据插入是否成功: "+sf+" ----------");
    }

    public int selectCountByTime(String mobile,Date time,List<String> contentTypes)
    {
        return messageCenterMapper.selectCountByTime(mobile, time,contentTypes);
    }
    public BaseResult selectHandleCount(HttpServletRequest request,ZbCountRequest vo){
        BaseResult baseResult = BaseResult.success();
        String mobile = vo.getAccountMobile();
        List<String> roleList = vo.getRoleList();
        int zbCount = 0;
        Acount acount = new Acount();
        acount.setAccount(mobile);
        Acount acount1 = acountMapper.selectByModel(acount);
        if(acount1==null){
            if(roleList.contains("taskZJL")){
                roleList = roleList();
                List<String> mobiles = new ArrayList<String>();
                List<ChildAccount> childList = childAccountMapper.selectAllChild(mobile);
                for(ChildAccount child:childList){
                    mobiles.add(child.getChildAccountMobile());
                }
                ChildAccount child = new ChildAccount();
                child.setChildAccountMobile(mobile);
                ChildAccount childAccount = childAccountMapper.selectByModel(child);
                mobiles.add(childAccount.getAccountPhone());
                zbCount = messageCenterMapper.selectHandleCountByZjl(mobiles,roleList);
            }else {
                zbCount = messageCenterMapper.selectHandleCount(mobile,roleList);
            }
        }else {
            //roleList = roleList();
            //zbCount = messageCenterMapper.selectHandleCount(mobile,roleList);
            zbCount = 0;
        }
        baseResult.setData(zbCount);
        return baseResult;
    }
    public List<String> roleList(){
        List<String> roleList = new ArrayList<String>();
        roleList.add("taskPGS");
        roleList.add("taskZBY");
        roleList.add("taskManager");
        roleList.add("taskYYZY");
        roleList.add("taskRemind");
        roleList.add("taskTenure");
        roleList.add("taskDC");
        roleList.add("taskAcquisition");
        roleList.add("taskTenureThree");
        roleList.add("taskSXY");
        return roleList;
    }
    //查询手续员推送内容
    public BaseResult selectsxyts(CarsokZbTaskSxyWb vo){
        CarsokZbTaskSxyWb carsokZbTaskSxyWb = messageCenterMapper.selectsxyts(vo.getTaskId());
        BaseResult baseResult = BaseResult.success();
        baseResult.setData(carsokZbTaskSxyWb);
        return baseResult;
    }

    public Map selectQianKeBaoYouList(Acount acount, QianKeBaoYouDBRequest vo){
        int childId = 0;
        FilterSQLParam qiankeSQLParam = new FilterSQLParam();
        qiankeSQLParam.setOrderByColumn("create_time");
        qiankeSQLParam.setIsAsc(false);
        FilterSQLParam baoyouSQLParam = new FilterSQLParam();
        baoyouSQLParam.setOrderByColumn("create_time");
        baoyouSQLParam.setIsAsc(false);
        String qkSql = "";
        String bySql = "";
        if(acount.getSubPhone()!=null){
            ChildAccount childAccount = childAccountMapper.selectByChildMobile(acount.getSubPhone());
            childId = childAccount.getId();
            List<String> roleName = saasTenureCustomerService.getRoleName(childId);
            if(roleName.contains("byyxgw") || roleName.contains("qkyxgw") || roleName.contains("byjlgl") || roleName.contains("qkjlgl") || roleName.contains("bykfdp") || roleName.contains("qkkfdp")){
                qkSql = "json_extract(extra_fields,'$.child_id')='" + childId + "' AND extra_fields->>'$.level' NOT IN ('F 战败','F0 战败待确认')";
                bySql = "json_extract(extra_fields,'$.childId')=" + childId;
                qiankeSQLParam.setSqlTemplate(qkSql);
                baoyouSQLParam.setSqlTemplate(bySql);
            }
        }else {
            return new HashMap();
        }
        long qiankeDBCount = iTaskFacade.queryTaskByStatusWithSQL(vo.getPage(),vo.getPageSize(), TaskStatusEnums.ready, ModuleEnums.potentialcustomer,qiankeSQLParam).getTotal();
        long baoyouDBCount = iTaskFacade.queryTaskByStatusWithSQL(vo.getPage(),vo.getPageSize(), TaskStatusEnums.ready, ModuleEnums.retaincustomer,baoyouSQLParam).getTotal();
        Map<String,Object> map = new HashMap();
        map.put("qiankeCount",qiankeDBCount);
        map.put("baoyouCount",baoyouDBCount);
        return map;
    }

}
