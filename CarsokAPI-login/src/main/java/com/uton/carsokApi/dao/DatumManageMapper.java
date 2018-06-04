package com.uton.carsokApi.dao;

import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.request.DatumManageRequest;
import com.uton.carsokApi.controller.request.NewPictureRequest;
import com.uton.carsokApi.controller.response.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Raytine on 2017/9/7.
 */
public interface DatumManageMapper {

    /**
     * 资料管理列表查询
     * @param datumManageRequest
     * @return
     */
    Page<DatumManageResponse> getDatumList(@Param("param") DatumManageRequest datumManageRequest);

    /**
     * 通过商品id获取车辆图片
     * @param productId
     * @return
     */
    List<CarPictureResponse> getCarPictureById(@Param("productId") String productId);

    /**
     * 通过商品id获取整备图片
     * @param productId
     * @return
     */
    List<ZbPictureResponse> getZbPictureById(@Param("productId") String productId);

    /**
     * 通过商品id获取新增图片
     * @param productId
     * @return
     */
    List<NewPictureResponse> getNewPictureById(@Param("productId") String productId);

    /**
     * 通过商品id获取pdf合同
     * @param productId
     * @return
     */
    List<ContractPictureResponse> getcontractPicListById(@Param("productId") String productId);


    /**
     * 通过商品id获取纸质合同图片
     * @param productId
     * @return
     */
    List<ContractPictureResponse> getNewContractPicList(@Param("productId") String productId);

    /**
     * 新增图片
     * @param newPictureRequest
     * @return
     */
    int addNewPicture(NewPictureRequest newPictureRequest);

    /**
     * 获取各状态数据数量
     * @param datumManageRequest
     * @return
     */
    Integer getEachCount(@Param("param") DatumManageRequest datumManageRequest);

    /**
     * 根据id删除图片
     * @param id
     * @return
     */
    int delNewPic(@Param("id") String id);
}
