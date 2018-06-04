package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.uton.carsokApi.controller.response.CarsokQuoteDetailsResponse;
import com.uton.carsokApi.controller.response.CarsokQuoteListResponse;
import com.uton.carsokApi.model.CarsokQuote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author csw
 * @since 2018-01-17
 */
public interface CarsokQuoteMapper extends BaseMapper<CarsokQuote> {

    /**
     * 通过id获取详细报价信息
     * @param id
     * @return
     */
    CarsokQuoteDetailsResponse getQuoteMessageById(@Param("id") Integer id);
    /**
     * 获取报价信息列表
     * @param accountId
     * @return
     */
    List<CarsokQuoteListResponse> getQuoteMessageList(@Param("accountId") String accountId, @Param("createTime") String createTime);
}