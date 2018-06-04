package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CarsOkCustomerRequest;
import com.uton.carsokApi.controller.request.LatentCustomerRequest;
import com.uton.carsokApi.controller.response.CarsOkCustmoerResponse;
import com.uton.carsokApi.model.CarsokCustomer;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.service.core.task.FilterSQLParam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
public interface ICarsokCustomerService extends IService<CarsokCustomer> {

    /**  
    * @author zhangdi
    * @date 2017/11/9 11:49
    * @Description: 修改保存/添加潜客信息公用接口
    */
    boolean updateLatentMsg (CarsOkCustomerRequest carsOkCustomerRequest,String accountId,String childId) throws ParseException;


    /**
    * @author zhangdi
    * @date 2017/11/9 16:49
    * @Description: 详情页
    */

    CarsOkCustmoerResponse selectCustMsg(Integer id);


    /**
     * @author day
     * @date 2017/11/9 20:36
     * @Description: 列表页
     */
    Map selectLatentList(int accountId, String accountMobile, LatentCustomerRequest latentCustomerRequest);

    /**
    * @author zhangdi
    * @date 2017/11/10 16:40
    * @Description: 通过手机号查询
    */

    CarsokCustomer selectByMobile(String mobile,Integer accountId);

    BaseResult selectAllSaledPeople(String mobile);

    /**
     * 复购
     * @param accountId
     * @param childId
     * @param level
     * @param customerFlowMessage
     */
    void customerRePurchase(int accountId,int childId,String level,String customerFlowMessage,String custId);


    /**
     * @author zhangdi
     * @date 2017/11/20 11:11
     * @Description: 查询客户所有任务
     */
    List<CarsokTenureTask> selecAllTask(Integer cusId);

    List<Integer> skyCount(int type, int pageCount, int pageSize, FilterSQLParam filterSQLParam);
}
