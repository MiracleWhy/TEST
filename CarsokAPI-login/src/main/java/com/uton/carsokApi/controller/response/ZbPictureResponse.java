package com.uton.carsokApi.controller.response;

import java.util.List;

/**
 * Created by Raytine on 2017/9/7.
 */
public class ZbPictureResponse {
    /**
     * 机动车登记证书图片链接
     */
    private String arcPath;

    /**
     * 行驶证图片链接
     */
    private String dlPath;

    /**
     * 保险单图片链接
     */
    private String policyPath;

    /**
     * 身份证图片链接
     */
    private String idcardPath;

    /**
     * 单据图片链接
     */
    List<ZbBillPicResponse> zbBillPicResponse;

    public List<ZbBillPicResponse> getZbBillPicResponse() {
        return zbBillPicResponse;
    }

    public void setZbBillPicResponse(List<ZbBillPicResponse> zbBillPicResponse) {
        this.zbBillPicResponse = zbBillPicResponse;
    }

    public String getArcPath() {
        return arcPath;
    }

    public void setArcPath(String arcPath) {
        this.arcPath = arcPath;
    }

    public String getDlPath() {
        return dlPath;
    }

    public void setDlPath(String dlPath) {
        this.dlPath = dlPath;
    }

    public String getPolicyPath() {
        return policyPath;
    }

    public void setPolicyPath(String policyPath) {
        this.policyPath = policyPath;
    }

    public String getIdcardPath() {
        return idcardPath;
    }

    public void setIdcardPath(String idcardPath) {
        this.idcardPath = idcardPath;
    }
}
