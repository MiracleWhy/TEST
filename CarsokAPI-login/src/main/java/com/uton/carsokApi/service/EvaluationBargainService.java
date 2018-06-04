package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.BargainRequest;
import com.uton.carsokApi.controller.request.EvaluationRequest;
import com.uton.carsokApi.controller.response.BargainInfoResponse;
import com.uton.carsokApi.model.Evaluations;

/**
 * Created by Administrator on 2017/10/11.
 */
public interface EvaluationBargainService {
    /**
     * 估价
     * @param evaluationRequest
     * @return
     */
    int evaluationSubmit(EvaluationRequest evaluationRequest);

    /**
     * 砍价
     * @param vo
     * @return
     */
    int bargainSubmit(BargainRequest vo);

    /**
     * 通过id查询估价信息
     * @param id
     * @return
     */
    Evaluations selectEvaluationMsgById(String id);

    /**
     * 查询砍价车辆信息
     * @param productId
     * @return
     */
    BargainInfoResponse selectProductMsgByProductId(String productId);

}
