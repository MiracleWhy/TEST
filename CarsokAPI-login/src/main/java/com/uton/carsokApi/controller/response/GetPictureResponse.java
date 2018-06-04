package com.uton.carsokApi.controller.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raytine on 2017/9/8.
 */
public class GetPictureResponse implements Serializable {

    /**
     * 车辆照片
     */
    private List<CarPictureResponse> carPicList;

    /**
     * 整备照片
     */
    private List<ZbPictureResponse> zbPicList;

    /**
     * 新增照片
     */
    private List<NewPictureResponse> newPicList;

    /**
     * 合同照片
     */
    private List<ContractPictureResponse> contractPicList;

    /**
     * 纸质合同
     */
    private List<ContractPictureResponse> newContractPicList;

    public List<ContractPictureResponse> getNewContractPicList() {
        return newContractPicList;
    }

    public void setNewContractPicList(List<ContractPictureResponse> newContractPicList) {
        this.newContractPicList = newContractPicList;
    }

    public List<ContractPictureResponse> getContractPicList() {
        return contractPicList;
    }

    public void setContractPicList(List<ContractPictureResponse> contractPicList) {
        this.contractPicList = contractPicList;
    }

    public List<CarPictureResponse> getCarPicList() {
        return carPicList;
    }

    public void setCarPicList(List<CarPictureResponse> carPicList) {
        this.carPicList = carPicList;
    }

    public List<ZbPictureResponse> getZbPicList() {
        return zbPicList;
    }

    public void setZbPicList(List<ZbPictureResponse> zbPicList) {
        this.zbPicList = zbPicList;
    }

    public List<NewPictureResponse> getNewPicList() {
        return newPicList;
    }

    public void setNewPicList(List<NewPictureResponse> newPicList) {
        this.newPicList = newPicList;
    }
}
