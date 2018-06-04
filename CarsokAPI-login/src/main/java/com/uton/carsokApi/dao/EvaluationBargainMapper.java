package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.BargainAccountResponse;
import com.uton.carsokApi.controller.response.BargainInfoResponse;
import com.uton.carsokApi.model.Bargain;
import com.uton.carsokApi.model.Evaluations;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/10/11.
 */
public interface EvaluationBargainMapper {
    /**
     * 估价
     * @param evaluations
     * @return
     */
    int evaluationInsert(Evaluations evaluations);

    /**
     * 砍价
     * @param bargain
     * @return
     */
    int bargainInsert(Bargain bargain);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    Evaluations selectEvaluationMsgById(@Param("id") int id);

    /**
     * 查询砍价车辆信息
     * @param id
     * @return
     */
    BargainInfoResponse selectProductMsgByProductId(@Param("id")int id);

    /**
     * 通过bargainId查询productId查询account
     * @param id
     * @return
     */
    BargainAccountResponse selectAccountIdByBargainIdProductId(@Param("id")int id);
}
