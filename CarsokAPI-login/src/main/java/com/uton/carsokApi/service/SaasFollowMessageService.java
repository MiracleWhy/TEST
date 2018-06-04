package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.CustomerTenureFollowRequest;
import com.uton.carsokApi.controller.request.FindMessagePageRequest;
import com.uton.carsokApi.controller.request.FollowMessageRequest;

import java.util.Map;

public interface SaasFollowMessageService {
    /**
     * 添加回访信息接口
     * @param vo
     * @return
     */
     public BaseResult saveFollowMessage(FollowMessageRequest vo);
     public Map<String, Object> selectFollowMessage(FindMessagePageRequest vo);

}
