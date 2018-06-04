package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhang yugong
 * @since 2017-11-29
 */
@TableName("carsok_sound_recording")
public class CarsokSoundRecording extends Model<CarsokSoundRecording> {

    private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 模块
	 */
	@TableField(value="module")
	private String module;

	/**
	 * 关联外部id
	 */
	@TableField(value="business_id")
	private Integer business_id;

	/**
	 * 录音的七牛云url
	 */
	@TableField(value="url")
	private String url;

	/**
	 *
	 */
	@TableField(value="create_time")
	private Date createTime;

	/**
	 *
	 */
	@TableField(value="update_time")
	private Date updateTime;

	/**
	 * accountId
	 */
	@TableField(value="account_id")
	private Integer accountId;

	/**
	 * childId
	 */
	@TableField(value="child_id")
	private Integer childId;

	/**
	 * 是否可用(1-可用, 0-不可用)
	 */
	private Integer enable;

	/**
	 * 其他信息
	 */
	@TableField(value="remark")
	private String remark;

	/**
	 * 录音转文字
	 */
	@TableField(value="sound_to_word")
	private String soundToWord;

	/**
	 * 录音时长
	 */
	@TableField(value="record_time")
	private String recordTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(Integer business_id) {
		this.business_id = business_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSoundToWord() {
		return soundToWord;
	}

	public void setSoundToWord(String soundToWord) {
		this.soundToWord = soundToWord;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	@Override
	protected Serializable pkVal() {
		return null;
	}
}
