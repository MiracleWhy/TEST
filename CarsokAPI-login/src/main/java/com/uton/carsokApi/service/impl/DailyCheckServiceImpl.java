package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.constants.enums.CarDel;
import com.uton.carsokApi.constants.enums.OnSelfStatus;
import com.uton.carsokApi.constants.enums.SaleStatus;
import com.uton.carsokApi.controller.response.DailyCheckCheckerResult;
import com.uton.carsokApi.controller.response.DailyCheckerResponse;
import com.uton.carsokApi.controller.response.DailycheckCheckerTaskResponse;
import com.uton.carsokApi.controller.response.SubUserListResponse;
import com.uton.carsokApi.dao.*;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.*;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.server.InactiveGroupException;

import java.util.*;

@Service
public class DailyCheckServiceImpl implements DailyCheckService {

    private final static Logger logger = Logger.getLogger(LoginService.class);

    @Autowired
    DailycheckCheckerMapper dailycheckCheckerMapper;
    @Autowired
    DailycheckDispatcherMapper dailycheckDispatcherMapper;
    @Autowired
    DailycheckResultMapper dailycheckResultMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    ChildAccountMapper childAccountMapper;
    @Autowired
    AcountMapper acountMapper;

    @Autowired
    SubUserService subUserService;
    @Autowired
    MessageCenterService messageCenterService;
    @Autowired
    PushService pushService;

    public List<DailyCheckerResponse> getDailyChecker(String accountId) {
        List<DailyCheckerResponse> dailyCheckerResponseList = new ArrayList<>();
        List<SubUserListResponse> subUserListResponseList = subUserService.getSubUserList(Integer.parseInt(accountId));
        List<DailycheckChecker> dailycheckCheckerList = dailycheckCheckerMapper.selectCheckerListByAccount(accountId);
        List<String> checkList= new ArrayList<>();
        for(DailycheckChecker checker:dailycheckCheckerList)
        {
            checkList.add(checker.getChecker());
        }
        for(SubUserListResponse subUserListResponse:subUserListResponseList)
        {
            String isCheck = checkList.contains(subUserListResponse.getChildAccountMobile())?"1":"0";

            DailyCheckerResponse dailyCheckerResponse = new DailyCheckerResponse();
            dailyCheckerResponse.setAccountId(accountId);
            dailyCheckerResponse.setChecker(subUserListResponse.getChildAccountMobile());
            dailyCheckerResponse.setCheckerName(subUserListResponse.getChildAccountName());
            dailyCheckerResponse.setIsCheck(isCheck);

            dailyCheckerResponseList.add(dailyCheckerResponse);
        }
        return dailyCheckerResponseList;
    }

    public boolean updateDailyChecker(String accountId,List<String> checkerList)
    {
        boolean result =false;
        try
        {
            dailycheckCheckerMapper.deleteByAccount(accountId);
            for(String checker:checkerList)
            {
                DailycheckChecker dailycheckChecker = new DailycheckChecker();
                dailycheckChecker.setEnable(1);
                dailycheckChecker.setAccountId(accountId);
                dailycheckChecker.setChecker(checker);

                dailycheckCheckerMapper.insert(dailycheckChecker);
            }

            result=true;
        }
        catch (Exception e )
        {
            throw e;
        }
        return result;
    }

    //日检分配
    public boolean dailyCheckerDispatcher(String accountId,String account) {
        boolean result =false;

        try {
            //在售列表获取
            Map prodcutQueryMap = new HashMap();
            prodcutQueryMap.put("acountId", accountId);
            prodcutQueryMap.put("onShelfStatus", OnSelfStatus.ON_SELF.getCode());
            prodcutQueryMap.put("saleStatus", SaleStatus.ON_SALE.getCode());
            prodcutQueryMap.put("isDel", CarDel.DEL_NO.getCode());
            List<Product> prodcutList = productMapper.selectOnsaleCount(prodcutQueryMap);

            //日检人员获取
            List<DailycheckChecker> dailycheckCheckerList = dailycheckCheckerMapper.selectCheckerListByAccount(accountId);

            //region delete 需求变更。日检分配算法改变

            //

            //删除既有记录
            dailycheckDispatcherMapper.deleteByAccount(accountId);

            //按照星期顺序取索引，保证每天分配的顺序不一样
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int w = cal.get(Calendar.DAY_OF_WEEK);
            //取模，计算开始位置
            w = w % dailycheckCheckerList.size();

            for (int i = 0, j = w; i < prodcutList.size(); i++, j++) {
                //迭代
                if (j >= dailycheckCheckerList.size()) {
                    j = 0;
                }

                DailycheckDispatcher dailycheckDispatcher = new DailycheckDispatcher();
                dailycheckDispatcher.setEnable(1);
                dailycheckDispatcher.setAccountId(accountId);
                dailycheckDispatcher.setProductId(prodcutList.get(i).getId().toString());
                dailycheckDispatcher.setChecker(dailycheckCheckerList.get(j).getChecker());
                dailycheckDispatcherMapper.insert(dailycheckDispatcher);
            }
            //endregion

            /*
            //人车关系KeyList
            List<String> keyList= new ArrayList<>();

            List<PCR> pcrList = new ArrayList<>();


            List<DailycheckDispatcher> dailycheckDispatcherList = dailycheckDispatcherMapper.getDispatcherCheckerInfoList(accountId);

            //遍历之前的分配结果，建立映射。
            for(DailycheckDispatcher dispatcher:dailycheckDispatcherList)
            {
                keyList.add(dispatcher.getChecker()+":"+dispatcher.getProductId());
            }

            //遍历最新人员
            for(DailycheckChecker checker:dailycheckCheckerList)
            {
                //遍历最新在售列表，建立最新人车关系
                for(Product product:prodcutList) {
                    PCR pcr = new PCR();
                    pcr.checker=checker.getChecker();
                    pcr.product=product.getId().toString();
                    pcr.key=checker.getChecker()+":"+product.getId().toString();
                    pcr.status=0;
                    pcrList.add(pcr);
                }
            }

            //已经包含的人车关系，状态置为1
            for(PCR pcr:pcrList)
            {
                if(keyList.contains(pcr.key))
                {
                    pcr.status=1;
                    RemoveByProduct(pcr.product,pcrList);
                }
            }

            //递归调用分配
            Dispatcher(pcrList);

            //删除既有记录
            dailycheckDispatcherMapper.deleteByAccount(accountId);

            for(PCR pcr:pcrList)
            {
                if(pcr.status==1) {
                    DailycheckDispatcher dailycheckDispatcher = new DailycheckDispatcher();
                    dailycheckDispatcher.setEnable(1);
                    dailycheckDispatcher.setAccountId(accountId);
                    dailycheckDispatcher.setProductId(pcr.product);
                    dailycheckDispatcher.setChecker(pcr.checker);
                    dailycheckDispatcherMapper.insert(dailycheckDispatcher);
                }
                else
                {
                    //Do nothing
                    //System.out.println("分配算法错误，此处不应出现未分配的条目");
                }
            }
*/

            //删除当前主账号所有子账号的既有待办消息
            Acount record = new Acount();
            record.setId(Integer.parseInt(accountId));
            Acount acount = acountMapper.selectByModel(record);
            ChildAccount childRecord = new ChildAccount();
            childRecord.setAccountPhone(acount.getAccount());
            List<ChildAccount> list = childAccountMapper.selectListByModel(childRecord);
            for(ChildAccount childAccount:list)
            {
                messageCenterService.deleteMessage(childAccount.getChildAccountMobile(),"taskDC");
            }

            //推送
            List<DailycheckDispatcher> checkerList = dailycheckDispatcherMapper.getDispatcherCheckerList(accountId);
            for(DailycheckDispatcher checker:checkerList)
            {
                MessageCenter mc = new MessageCenter();
                mc.setTitle("待办任务通知");
                mc.setContent("您有一项新的待处理任务，点击查看详情");
                mc.setCreateTime(new Date());
                mc.setEnable(1);
                mc.setPushTo(checker.getChecker());
                mc.setPushFrom("systems");
                mc.setContentType("taskDC");
                mc.setPushStatus(1);
                int sf = messageCenterService.messageCenterAdd(mc);
                boolean df = pushService.SendCustomizedCast(account+checker, "您有一项新的待处理任务，点击查看详情","Bussiness");
                logger.info("----------待办任务通知:接收人: "+checker.getChecker()+", 时间: "+new Date()+", 数据插入是否成功: "+sf+", 发送是否成功: "+df+" ----------");
            }
            result = true;
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    public void DoDispatcherTask()
    {
        List<DailycheckCheckerTaskResponse> accountList = dailycheckCheckerMapper.selectCheckerForTask();
        for(DailycheckCheckerTaskResponse checker:accountList)
        {
            dailyCheckerDispatcher(checker.getAccountId(),checker.getAccount());
        }
    }

    //获取每日车检结果
    public DailycheckResult getCheckResultByProductId(String productId,String date)
    {
        return dailycheckResultMapper.getCheckResultByProductId(productId,date);
    }

    public List<DailycheckResult> getHistoryResult(String productId)
    {
        return dailycheckResultMapper.getHistoryResult(productId);
    }

    public DailyCheckProductInfo getProductInfoByProductId(String productId)
    {
        return dailycheckResultMapper.getProductInfoByProductId(productId);
    }

    public List<Picture> getPicListByProductId(String productId)
    {
        Picture picture=new Picture();
        picture.setProductId(Integer.parseInt(productId));
        return pictureMapper.selectByModel(picture);
    }


    //更新或插入每日车检结果
    public boolean updateCheckResult(String productId,String checker,String date,String result)
    {
        boolean ret = false;
        try {
           DailycheckResult dailycheckResult = dailycheckResultMapper.getCheckResultByProductId(productId, date);
            //DailycheckResult dailycheckResult = dailycheckResultMapper.selectByPrimaryKey(10);
            if (dailycheckResult == null) {
                dailycheckResult=new DailycheckResult();
                dailycheckResult.setEnable(1);
                dailycheckResult.setResult(result);
                dailycheckResult.setIsCheck(1);
                dailycheckResult.setChecker(checker);
                dailycheckResult.setProductId(Integer.parseInt(productId));
                dailycheckResult.setCheckDate(date);

                dailycheckResultMapper.insert(dailycheckResult);
            } else {
                dailycheckResult.setResult(result);
                dailycheckResult.setChecker(checker);
                dailycheckResult.setCheckDate(date);

                dailycheckResultMapper.updateByPrimaryKey(dailycheckResult);
            }

            messageCenterService.deleteMessage(checker,"taskDC");


            ret = true;
        }
        catch (Exception e)
        {
            throw e;
        }
        return ret;
    }


    //isCheck:0 未检 1 已检 2 不关注
    public List<Map<String,Object>> getDispatcherList(String accountId,String checker,String checkDate,int isCheck,HashMap<String,Integer> counts)
    {
        if(StringUtil.isEmpty(checkDate))
        {
            checkDate= DateUtil.formatDate(new Date(),"yyyy-MM-dd");
        }

        List<DailyCheckProductInfo> dailyCheckProductInfoList = dailycheckDispatcherMapper.getDispatcherList(accountId,checker,checkDate);
        Map<String,List<DailyCheckProductInfo>> resultMap = new HashedMap();
        int count=0;

        for(DailyCheckProductInfo dailyCheckProductInfo:dailyCheckProductInfoList)
        {
            //已检，未check的车不计入在内
            if(isCheck==1&&StringUtil.isEmpty(dailyCheckProductInfo.getIsCheck()))
            {
                continue;
            }
            //未检，已check的车不计入在内
            else if(isCheck==0&&!StringUtil.isEmpty(dailyCheckProductInfo.getIsCheck()))
            {
                continue;
            }
            else
            {
                //do nothing
            }
            count++;

            if(resultMap.containsKey(dailyCheckProductInfo.getChecker()))
            {
                resultMap.get(dailyCheckProductInfo.getChecker()).add(dailyCheckProductInfo);
            }
            else
            {
                List<DailyCheckProductInfo> list =new ArrayList<>();
                list.add(dailyCheckProductInfo);
                resultMap.put(dailyCheckProductInfo.getChecker(),list);
            }
        }

        List<Map<String,Object>> resultList = new ArrayList<>();
        Iterator iter = resultMap.entrySet().iterator();
        while(iter.hasNext())
        {
            try {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();

                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("key", key);
                hashMap.put("name", ((List<DailyCheckProductInfo>) val).get(0).getCheckerName());
                hashMap.put("value", val);
                resultList.add(hashMap);
            }
            catch(Exception e)
            {
                //do nothing
            }
        }

        counts.put(isCheck==1?"checkedCount":"uncheckCount",count);

        return resultList;
    }

    public Map<String,Object> getCheckerResult(String accountId,String checker,String checkDate)
    {
        Map<String,Object> resultMap = new HashedMap();
        try
        {
            List<DailyCheckCheckerResult> dailyCheckCheckerResults = dailycheckResultMapper.getCheckerResult(accountId,checker,checkDate,true);
            resultMap.put("checkedList",dailyCheckCheckerResults);
            resultMap.put("checkedCount",dailyCheckCheckerResults.size());
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        return resultMap;
    }

    public Map<String, Object> getCheckedList(String accountId, String checker, String checkDate) {
        Map<String, Object> resultMap = new HashedMap();
        try {
            List<DailyCheckCheckerResult> dailyCheckCheckerResults = dailycheckResultMapper.getCheckerResult(accountId, checker, checkDate, false);
            resultMap.put("checkedCount", dailyCheckCheckerResults.size());
            Map<String, List<DailyCheckCheckerResult>> tempMap = new HashedMap();
            for (DailyCheckCheckerResult dailyCheckCheckerResult : dailyCheckCheckerResults) {
                if (tempMap.containsKey(dailyCheckCheckerResult.getChecker())) {
                    tempMap.get(dailyCheckCheckerResult.getChecker()).add(dailyCheckCheckerResult);
                } else {
                    List<DailyCheckCheckerResult> list = new ArrayList<>();
                    list.add(dailyCheckCheckerResult);
                    tempMap.put(dailyCheckCheckerResult.getChecker(), list);
                }
            }

            List<Map<String,Object>> resultList = new ArrayList<>();
            Iterator iter = tempMap.entrySet().iterator();
            while(iter.hasNext())
            {
                try {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();

                    Map<String, Object> hashMap = new HashMap<>();
                    hashMap.put("key", key);
                    hashMap.put("name", ((List<DailyCheckCheckerResult>) val).get(0).getCheckerName());
                    hashMap.put("value", val);
                    resultList.add(hashMap);
                }
                catch(Exception e)
                {
                    //do nothing
                }
            }

            resultMap.put("checkedList", resultList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return resultMap;
    }

    public List<DailyCheckCheckerResult> getUnCheckList(String accountId,String checkDate)
    {
        List<DailyCheckCheckerResult> dailyCheckCheckerResults = new ArrayList<>();
        try
        {
            dailyCheckCheckerResults=dailycheckResultMapper.getResultList(accountId,checkDate,true);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        return dailyCheckCheckerResults;
    }


    class PCR
    {
        public String key;
        public String checker;
        public String product;
        public Integer status;
    }

    private boolean IsContainKey(String key,List<PCR> pcrList)
    {
        boolean result =false;
        for(PCR pcr:pcrList)
        {
            if(pcr.key.equals(key))
            {
                result=true;
                break;
            }
        }
        return result;
    }

    private boolean IsAllDispatched(List<PCR> pcrList)
    {
        boolean result =true;
        for(PCR pcr:pcrList)
        {
            if(pcr.status==0)
            {
                result=false;
                break;
            }
        }
        return result;

    }

    private String JudgeMinChecker(List<PCR> pcrList)
    {
        HashMap<String,Integer> minMap = new HashMap<>();
        for(PCR pcr:pcrList)
        {
                Integer count = 0;
                Integer step=pcr.status==1?1:0;
                if(minMap.containsKey(pcr.checker))
                {
                    count=minMap.get(pcr.checker);
                }
                count+=step;
                minMap.put(pcr.checker,count);
        }

        Iterator iterator = minMap.entrySet().iterator();
        String minKey=pcrList.get(0).checker;
        Integer minValue=9999;
        while(iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            Integer value = (Integer)entry.getValue();
            if(value<=minValue) {
                minValue=value;
                minKey=entry.getKey().toString();
            }
        }

        return minKey;
    }

    //人员智能分配
    private void Dispatcher(List<PCR> pcrList)
    {
        //全部条目status为true，已全部分配完毕
        if(IsAllDispatched(pcrList))
        {
            return;
        }
        else
        {
            DoDispatcher(pcrList,JudgeMinChecker(pcrList));
            Dispatcher(pcrList);
        }

    }

    //分配，并将冗余条目移除
    private void DoDispatcher(List<PCR> pcrList,String checker)
    {
        for(int i=0;i<pcrList.size();i++)
        {
            if(pcrList.get(i).status==0&&pcrList.get(i).checker.equals(checker))
            {
                pcrList.get(i).status=1;
                RemoveByProduct(pcrList.get(i).product,pcrList);
                break;
            }
        }
    }

    private void RemoveByProduct(String product,List<PCR> pcrList)
    {
        Iterator iterator = pcrList.iterator();
        while(iterator.hasNext())
        {
            PCR pcr = (PCR)iterator.next();
            if (pcr.status == 0 && pcr.product.equals(product)) {
                //iterator.remove();
                pcr.status=2;
            }
        }
    }

}
