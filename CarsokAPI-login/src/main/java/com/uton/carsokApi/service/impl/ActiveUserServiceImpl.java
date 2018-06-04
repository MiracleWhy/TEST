package com.uton.carsokApi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVUser;
import com.uton.carsokApi.constants.Common;
import com.uton.carsokApi.controller.SimController;
import com.uton.carsokApi.controller.request.RequestAuthority;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.ActiveUserMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ActiveUser;
import com.uton.carsokApi.service.ActiveUserService;
import com.uton.carsokApi.util.DateStyle;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.HttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * Created by SEELE on 2018/2/27.
 */
@Service
public class ActiveUserServiceImpl implements ActiveUserService {
    private final static Logger logger = Logger.getLogger(ActiveUserServiceImpl.class);

    @Resource
    RedisTemplate redisTemplate;

    @Autowired
    ActiveUserMapper activeUserMapper;

    @Autowired
    AcountMapper acountMapper;



    /**
     * 获取所有用户
     * @return
     */
    public List<String> getAllUser()
    {


        List<String> list = new ArrayList<>();
        list.addAll(redisTemplate.keys(Common.USER+"*"));
        return list;
    }

    public int calActiveUser(Date startDate,Date endDate)
    {
        List<String> userList = this.getAllUser();

        int count = 0;

        for(String key:userList)
        {
            RequestAuthority authority = (RequestAuthority) redisTemplate.opsForValue().get(key);

            Date activeDate = DateUtil.StringToDate(authority.getLastTime(),DateStyle.YYYY_MM_DD);

            if(startDate.getTime()<=activeDate.getTime()&&endDate.getTime()>=activeDate.getTime())
            {
                count++;
            }
            logger.debug("count:"+count+" key:"+key+" value:"+ JSON.toJSONString(authority));
        }

        return count;
    }

    public void persistentActiveUser(String type)
    {
        Date nowDate = new Date();
        Date endDate = DateUtil.StringToDate(DateUtil.DateToString(nowDate,DateStyle.YYYY_MM_DD),DateStyle.YYYY_MM_DD);
        Date startDate = endDate;
        String dbType = "";

        switch (type)
        {
            case "MONTH":
                startDate=DateUtil.addDay(startDate,-31);
                dbType="MAU";
                break;
            case "WEEK":
                startDate=DateUtil.addDay(startDate,-7);
                dbType="WAU";
                break;
            case "DAY":
            default:
                startDate=DateUtil.addDay(startDate,-1);
                dbType="DAU";
                break;
        }

        ActiveUser activeUser = new ActiveUser();
        activeUser.setCount(calActiveUser(startDate,endDate));
        activeUser.setType(dbType);
        activeUser.setDate(endDate);

        activeUserMapper.insert(activeUser);
    }


    public List<Acount> selectSimLogin()
    {
        return acountMapper.selectSimLogin();
    }



    public void SimLogin()
    {
        Thread thread = new Thread(new DoSimLogin(), "DoSimLogin");
        thread.start();
    }

    private class DoSimLogin implements Runnable{

        @Override
        public void run()
        {
            String domain="http://carsok.utonw.com/carsokApi";


            List<Acount> acounts = new ArrayList<>();
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            int w = now.get(Calendar.DAY_OF_WEEK);
            int z = now.get(Calendar.WEEK_OF_YEAR);
            if(z%2==0)
            {
                if(w%2==0)
                {
                    //3000
                    acounts = acountMapper.selectSimLogin();
                }
                else
                {
                    //3000-1000
                    acounts = acountMapper.selectSimLogin2();
                }
            }
            else
            {
                if(w%2==0)
                {
                    //3000
                    acounts = acountMapper.selectSimLogin1();
                }
                else
                {
                    //3000-1000
                    acounts = acountMapper.selectSimLogin3();
                }
            }


            for(Acount acount:acounts)
            {
                try {
                    Map<String, String> params = new HashMap<>();
                    params.put("account", acount.getAccount());
                    params.put("accountPassword", acount.getAccountPassword());
                    String result = HttpClient.sendPostRequestByJava(domain + "/login.do", JSONObject.toJSONString(params));
                    System.out.println(result);
                    JSONObject objResult = JSONObject.parseObject(result.split("`")[0]);
                    if(objResult.getString("retCode").equals("0000"))
                    {
                        JSONObject data =objResult.getJSONObject("data");
                        String token = data.getString("token");
                        System.out.println(token);

                        String result1 = HttpClient.sendPostRequestByJavaWithHeader(domain+"/getStoreInfo.do","",token);
                        System.out.println(result1);
                    }

                    Calendar rightNow = Calendar.getInstance();
                    int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                    switch(hour)
                    {
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                            Thread.sleep(((int)(Math.random()*10)+3)*1000);
                            break;
                        case 12:
                        case 18:
                        case 19:
                            Thread.sleep(((int)(Math.random()*5)+50)*1000);
                            break;
                        default:
                            Thread.sleep(10);
                            break;
                    }

                   // Thread.sleep(((int)(Math.random()*5)+5)*1000);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
