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
 * 子账户
 * </p>
 *
 * @author
 * @since 2017-11-14
 */
@TableName("carsok_child_account")
public class CarsokChildAccount extends Model<CarsokChildAccount> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 子账号手机号
     */
    @TableField("child_account_mobile")
    private String childAccountMobile;
    /**
     * 子账号姓名
     */
    @TableField("child_account_name")
    private String childAccountName;
    /**
     * 主账户登录手机号码
     */
    @TableField("account_phone")
    private String accountPhone;
    /**
     * 消息推送token
     */
    private String alias;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    /**
     * 子帐号头像
     */
    @TableField("child_head_pic")
    private String childHeadPic;
    @TableField("good_evaluate")
    private String goodEvaluate;
    private String describes;
    private Integer star;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChildAccountMobile() {
        return childAccountMobile;
    }

    public void setChildAccountMobile(String childAccountMobile) {
        this.childAccountMobile = childAccountMobile;
    }

    public String getChildAccountName() {
        return childAccountName;
    }

    public void setChildAccountName(String childAccountName) {
        this.childAccountName = childAccountName;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getChildHeadPic() {
        return childHeadPic;
    }

    public void setChildHeadPic(String childHeadPic) {
        this.childHeadPic = childHeadPic;
    }

    public String getGoodEvaluate() {
        return goodEvaluate;
    }

    public void setGoodEvaluate(String goodEvaluate) {
        this.goodEvaluate = goodEvaluate;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CarsokChildAccount{" +
                ", id=" + id +
                ", childAccountMobile=" + childAccountMobile +
                ", childAccountName=" + childAccountName +
                ", accountPhone=" + accountPhone +
                ", alias=" + alias +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", childHeadPic=" + childHeadPic +
                ", goodEvaluate=" + goodEvaluate +
                ", describes=" + describes +
                ", star=" + star +
                "}";
    }
}
