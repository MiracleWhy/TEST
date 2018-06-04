package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.ModularApplyRequest;
import com.uton.carsokApi.model.ModularApply;

/**
 * Created by Administrator on 2017/10/23.
 */
public interface ModularApplyService {
    /**
     * 申请模块审核
     * @param modularApplyRequest
     * @return
     */
    BaseResult applyModular(ModularApplyRequest modularApplyRequest);

    /**
     * 查询模块是否可用
     * @param modularApply
     * @return
     */
    BaseResult selectApplyModular(ModularApply modularApply);

    /**
     * 查询所有模块认证状态
     * @param accountId
     * @return
     */
    BaseResult selectAllApplyStatus(int accountId);
}
