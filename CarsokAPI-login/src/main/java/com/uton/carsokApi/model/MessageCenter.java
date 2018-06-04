package com.uton.carsokApi.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class MessageCenter {
    private Integer id;
    private String title;
    private String content;
    private Date createTime;
    private String pushTo;
    private String pushFrom;
    private String contentType;
    private Integer enable;
    private Integer pushStatus;
    private Integer taskId;
    private String roleName;
    private Integer mendianId;
    private Integer baoyouId;
    private Integer shoucheId;
    private Integer xibaoId;
    private  Integer sxyId;
    private Integer productId;
    private Integer quoteId;
    private String vehicleModel;
    private String picPath;

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
    }

    public Integer getSxyId() {
        return sxyId;
    }

    public void setSxyId(Integer sxyId) {
        this.sxyId = sxyId;
    }

    public Integer getXibaoId() {
        return xibaoId;
    }

    public void setXibaoId(Integer xibaoId) {
        this.xibaoId = xibaoId;
    }

    public Integer getMendianId() {
        return mendianId;
    }

    public void setMendianId(Integer mendianId) {
        this.mendianId = mendianId;
    }

    public Integer getBaoyouId() {
        return baoyouId;
    }

    public void setBaoyouId(Integer baoyouId) {
        this.baoyouId = baoyouId;
    }

    public Integer getShoucheId() {
        return shoucheId;
    }

    public void setShoucheId(Integer shoucheId) {
        this.shoucheId = shoucheId;
    }

    private List<String> contentTypes;

    public List<String> getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(List<String> contentTypes) {
        this.contentTypes = contentTypes;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPushTo() {
        return pushTo;
    }

    public void setPushTo(String pushTo) {
        this.pushTo = pushTo;
    }

    public String getPushFrom() {
        return pushFrom;
    }

    public void setPushFrom(String pushFrom) {
        this.pushFrom = pushFrom;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

	public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
