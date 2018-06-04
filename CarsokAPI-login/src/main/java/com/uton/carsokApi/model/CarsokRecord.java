package com.uton.carsokApi.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zhangdi
 * @since 2017-11-10
 */
@TableName("carsok_record")
public class CarsokRecord extends Model<CarsokRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 外部id
     */
    @TableField("out_id")
    private Integer outId;
    /**
     * 历史记录
     */
    private String from;
    /**
     * 新记录
     */
    private String to;
    /**
     * 文言
     */
    private String message;
    /**
     * 所属模块
     */
    private String model;
    /**
     * 类型
     */
    private String type;
    /**
     * 主账号id
     */
    @TableField("account_id")
    private Integer accountId;
    /**
     * 子账号id
     */
    @TableField("child_id")
    private Integer childId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 相关Json
     */
    @TableField("cus_service_follow")
    private String cusServiceFollow;

    public String getCusServiceFollow() {return cusServiceFollow; }

    public void setCusServiceFollow(String cusServiceFollow) { this.cusServiceFollow = cusServiceFollow; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOutId() {
        return outId;
    }

    public void setOutId(Integer outId) {
        this.outId = outId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CarsokRecord{" +
                "id=" + id +
                ", outId=" + outId +
                ", from=" + from +
                ", to=" + to +
                ", message=" + message +
                ", model=" + model +
                ", type=" + type +
                ", accountId=" + accountId +
                ", childId=" + childId +
                ", createTime=" + createTime +
                "}";
    }
}
