package com.uton.carsokApi.controller.response;

import java.io.Serializable;

/**
 * Created by Raytine on 2017/10/16.
 */
public class ContractPictureResponse implements Serializable {

    /**
     * 合同地址
     */
    private String contractUrl;

    /**
     * 合同类型
     */
    private Integer contractType;

    /**
     * 纸质合同名称
     */
    private String contractName;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }
}
