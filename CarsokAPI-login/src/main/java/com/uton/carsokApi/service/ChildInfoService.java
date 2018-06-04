package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.ChildInfoRequest;
import com.uton.carsokApi.controller.request.StoreInfoRequest;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.ChildAndSale;
import com.uton.carsokApi.model.ChildAndSales;
import com.uton.carsokApi.model.SelectSaleCar;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */
public interface ChildInfoService {
    /**
     * 获取子帐号信息
     *
     * @param request
     * @return
     */
    BaseResult getChildInfoMsg(HttpServletRequest request);

    /**
     * 子帐号上传头像
     *
     * @param childHeadPic
     * @param request
     * @return
     */
    BaseResult uploadChildHead(HttpServletRequest request, String childHeadPic);

    /**
     * 根据客户电话查询子帐号信息
     *
     * @return
     */
    BaseResult getChildInfoMsgById(String mobile);

    /**
     * 根据车辆Id查询管家信息
     */
    BaseResult getChildInfoMsgByCarId(String CarId);


    /**
     * 通过电话查询所有子账号
     *
     * @param id
     * @return
     */
    public List<ChildAndSale> AllMassage(String id);

    /**
     * @param id
     * @return 直接通过id查询子账户信息
     */
    public ChildAndSales oneMessage(String id);

    /**
     * 修改子账户的信息
     *
     * @param childAccount
     * @return
     */

    public BaseResult updateMessage(ChildAccount childAccount);

    /**
     * 查出子账户售车情况
     *
     * @param id
     * @return
     */


    public List<SelectSaleCar> selectSaleCar(String id);

    /**
     * @author zhangdi
     * @date 2017/11/9 14:35
     * @Description
     */
    ChildAccount selectByChildAccount(String mobile);

    /**
     * @author zhangzheng
     * @param productId
     * @date 2018年1月29日17:16:55
     * @return
     */
    public BaseResult getSellerInfo(String productId);
}
