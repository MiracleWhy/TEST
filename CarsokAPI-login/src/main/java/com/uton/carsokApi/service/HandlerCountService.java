package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.HandlerCount;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/6/14.
 */
public interface HandlerCountService {
    /**
     * 查询门店、保有、日检待办数量
     * @param request
     * @param vo
     * @return
     */
    BaseResult selectCount(HttpServletRequest request, HandlerCount vo);

    /**
     * 删除门店待办消息
     * @param id
     * @return
     */
    String deleteMendianMsg(String id);

    /**
     * 删除门收车待办消息
     * @param id
     * @return
     */
    String deleteShoucheMsg(String id);
}
