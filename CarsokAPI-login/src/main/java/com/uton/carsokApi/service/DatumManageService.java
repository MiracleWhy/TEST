package com.uton.carsokApi.service;


import com.github.pagehelper.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.DatumManageRequest;
import com.uton.carsokApi.controller.response.DatumManageResponse;
import com.uton.carsokApi.controller.response.GetPictureResponse;
import com.uton.carsokApi.model.AddPictureRequest;
import com.uton.carsokApi.model.Pagebean;

import java.util.List;
import java.util.Map;

/**
 * Created by Raytine on 2017/9/7.
 */
public interface DatumManageService {

    /**
     * 资料管理列表查询
     * @param datumManageRequest
     * @return
     */
    Page<DatumManageResponse> getDatumList(int pr, int pc, DatumManageRequest datumManageRequest);

    /**
     * 资料详情列表查询
     * @param productId
     * @return
     */
    GetPictureResponse getPictureList(String productId);

    /**
     * 获取各状态数据数量
     * @param datumManageRequest
     * @return
     */
    Map getEachCount(DatumManageRequest datumManageRequest);

    /**
     * 资料管理新增图片
     * @param addPictureRequest
     * @return
     */
    BaseResult insertPic(AddPictureRequest addPictureRequest);

    /**
     * 根据id删除图片
     * @param id
     * @return
     */
    int delNewPic(String id);
}
