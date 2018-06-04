package com.uton.carsokApi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.DatumManageRequest;
import com.uton.carsokApi.controller.request.NewPictureRequest;
import com.uton.carsokApi.controller.response.*;
import com.uton.carsokApi.dao.DatumManageMapper;
import com.uton.carsokApi.model.AddPictureRequest;
import com.uton.carsokApi.model.Pagebean;
import com.uton.carsokApi.service.DatumManageService;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Raytine on 2017/9/7.
 */
@Service
public class DatumManageServiceImpl implements DatumManageService {

    @Autowired
    DatumManageMapper datumManageMapper;

    /**
     * 资料管理列表查询
     * @param datumManageRequest
     * @return
     */
    @Override
    public Page<DatumManageResponse> getDatumList(int pr, int pc, DatumManageRequest datumManageRequest) {
        PageHelper.startPage(pc,pr);
        Page<DatumManageResponse> list = datumManageMapper.getDatumList(datumManageRequest);
        for(DatumManageResponse data:list){
            if(!StringUtil.isEmpty(data.getOnShelfTime())){

                data.setOnShelfTime(data.getOnShelfTime().substring(0,10));
            }else{
                data.setOnShelfTime("");
            }
        }
        return list;
    }

    /**
     * 获取各状态数据数量
     * @param datumManageRequest
     * @return
     */
    @Override
    public Map getEachCount(DatumManageRequest datumManageRequest) {
        Map<String,Object> map = new HashMap<>();
        datumManageRequest.setCreateTime("1");
        map.put("dayCount",datumManageMapper.getEachCount(datumManageRequest));
        datumManageRequest.setCreateTime("2");
        map.put("weekCount",datumManageMapper.getEachCount(datumManageRequest));
        datumManageRequest.setCreateTime("3");
        map.put("monthCount",datumManageMapper.getEachCount(datumManageRequest));
        return map;
    }

    /**
     * 资料详情列表查询
     * @param productId
     * @return
     */
    @Override
    public GetPictureResponse getPictureList(String productId) {
        GetPictureResponse getPictureResponse = new GetPictureResponse();
        List<CarPictureResponse> carPicList = datumManageMapper.getCarPictureById(productId);
        List<ZbPictureResponse> zbPicList = datumManageMapper.getZbPictureById(productId);
        List<NewPictureResponse> newPicList = datumManageMapper.getNewPictureById(productId);
        List<ContractPictureResponse> contractPicList = datumManageMapper.getcontractPicListById(productId);
        List<ContractPictureResponse> newContractPicList = datumManageMapper.getNewContractPicList(productId);
        getPictureResponse.setCarPicList(carPicList);
        getPictureResponse.setZbPicList(zbPicList);
        getPictureResponse.setNewPicList(newPicList);
        getPictureResponse.setContractPicList(contractPicList);
        getPictureResponse.setNewContractPicList(newContractPicList);
        return getPictureResponse;
    }

    /**
     * 新增图片
     * @param addPictureRequest
     * @return
     */
    @Override
    public BaseResult insertPic(AddPictureRequest addPictureRequest) {
        int status = 0;
        String picName = addPictureRequest.getPicName();
        Integer productId = addPictureRequest.getProductId();
        List<String> list = addPictureRequest.getPicUrl();
        for(String picUrl : list) {
            NewPictureRequest newPictureRequest = new NewPictureRequest();
            newPictureRequest.setPicUrl(picUrl);
            newPictureRequest.setPicName(picName);
            newPictureRequest.setProductId(productId);
            newPictureRequest.setCreateTime(new Date());
            newPictureRequest.setUpdateTime(new Date());
            status = datumManageMapper.addNewPicture(newPictureRequest);
            if (!(status > 0)){
                break;
            }
        }
        if(status>0){
            return BaseResult.success();
        }else {
            return BaseResult.fail("0005","新增图片失败");
        }
    }

    /**
     * 根据id删除图片
     * @param id
     * @return
     */
    @Override
    public int delNewPic(String id) {
        return datumManageMapper.delNewPic(id);
    }
}
