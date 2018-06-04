package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.service.IService;
import com.uton.carsokApi.controller.request.InsertQuoteInfoRequest;
import com.uton.carsokApi.controller.response.CarsokQuoteDetailsResponse;
import com.uton.carsokApi.controller.response.CarsokQuoteListResponse;
import com.uton.carsokApi.model.CarsokQuote;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface ICarsokQuoteService extends IService<CarsokQuote> {

     /**
     * 通过id获取详细报价信息
     * @param id
     * @return
     */
    CarsokQuoteDetailsResponse getQuoteMessageById(Integer id);

    /**
    * @author zhangdi
    * @date 2018/1/23 14:41
    * @Description: 报价
    */

    boolean insertQuoteInfo (InsertQuoteInfoRequest i);

    /**
    * @author zhangdi
    * @date 2018/1/23 16:46
    * @Description: 报价消息列表
    */
    List<CarsokQuoteListResponse> getQuoteMessageList(String accountId, String createTime);

}
