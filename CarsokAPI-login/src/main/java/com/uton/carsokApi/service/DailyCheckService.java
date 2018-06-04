package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.response.DailyCheckCheckerResult;
import com.uton.carsokApi.controller.response.DailyCheckerResponse;
import com.uton.carsokApi.dao.AcquisitionCarMapper;
import com.uton.carsokApi.dao.DailycheckCheckerMapper;
import com.uton.carsokApi.dao.DailycheckDispatcherMapper;
import com.uton.carsokApi.dao.DailycheckResultMapper;
import com.uton.carsokApi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
@Service
public interface DailyCheckService {

    public List<DailyCheckerResponse> getDailyChecker(String accountId);
    public boolean updateDailyChecker(String accountId,List<String> checkerList);
    public boolean dailyCheckerDispatcher(String accountId,String account);
    public DailycheckResult getCheckResultByProductId(String productId, String date);
    public boolean updateCheckResult(String productId,String checker,String date,String result);
    public DailyCheckProductInfo getProductInfoByProductId(String productId);
    public List<Map<String,Object>> getDispatcherList(String accountId,String checker,String checkDate,int isCheck,HashMap<String,Integer> counts);
    public List<DailycheckResult> getHistoryResult(String productId);
    public List<Picture> getPicListByProductId(String productId);
    public void DoDispatcherTask();
    public Map<String,Object> getCheckerResult(String accountId,String checker,String checkDate);
    public Map<String, Object> getCheckedList(String accountId, String checker, String checkDate);
    public List<DailyCheckCheckerResult> getUnCheckList(String accountId, String checkDate);

}
