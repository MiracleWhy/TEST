package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyj
 * @since 2017-11-09
 */
@TableName("carsok_tenure_task")
public class CarsokTenureTask extends Model<CarsokTenureTask> {

    private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 任务类型
	 */
	private String type;

	/**
	 * 任务关联Id
	 */
	@TableField(value="business_id")
	private String businessId;

	/**
	 * 预定完成时间
	 */
	@TableField(value="scheduled_time")
	private Date scheduledTime;

	/**
	 * 任务完成状态
	 */
	@TableField(value="task_status")
	private String taskStatus;

	/**
	 * 任务实际完成时间
	 */
	@TableField(value="actual_finish_time")
	private Date actualFinishTime;

	/**
	 * 任务实际完成人
	 */
	@TableField(value="assignee_account")
	private String assigneeAccount;

	/**
	 * 子账号ID(只是为了兼容车商app中的子账号设立的)
	 */
	@TableField(value="assignee_child")
	private String assigneeChild;

	/**
	 * 是否可用
	 */
	private Integer enable;

	/**
	 * 任务创建时间
	 */
	@TableField(value="create_time")
	private Date createTime;

	/**
	 * 任务完成时间
	 */
	@TableField(value="update_time")
	private Date updateTime;

	/**
	 * 能够执行任务的组(如果为空，任何人角色都能签收）
	 */
	@TableField(value="task_execute_group")
	private String taskExecuteGroup;

	/**
	 * 额外filed（JSON）格式存储
	 */
	@TableField(value="extra_fields")
	private String extraFields;

	/**
	 * 模块Id(为了区分不同模块相同的任务)
	 */
	private String module;

	/**
	 * 乐观锁字段
	 */
	@Version
	private Integer version;

	/**
	 * 主账号ID
	 */
	@TableField(value="updateby_accountId")
	private String updatebyAccountid;

	/**
	 * 子账号Id
	 */
	@TableField(value="updateby_childId")
	private String updatebyChildid;




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getActualFinishTime() {
		return actualFinishTime;
	}

	public void setActualFinishTime(Date actualFinishTime) {
		this.actualFinishTime = actualFinishTime;
	}

	public String getAssigneeAccount() {
		return assigneeAccount;
	}

	public void setAssigneeAccount(String assigneeAccount) {
		this.assigneeAccount = assigneeAccount;
	}

	public String getAssigneeChild() {
		return assigneeChild;
	}

	public void setAssigneeChild(String assigneeChild) {
		this.assigneeChild = assigneeChild;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
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

	public String getTaskExecuteGroup() {
		return taskExecuteGroup;
	}

	public void setTaskExecuteGroup(String taskExecuteGroup) {
		this.taskExecuteGroup = taskExecuteGroup;
	}

	public String getExtraFields() {
		return extraFields;
	}

	public void setExtraFields(String extraFields) {
		this.extraFields = extraFields;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


	public String getUpdatebyAccountid() {
		return updatebyAccountid;
	}

	public void setUpdatebyAccountid(String updatebyAccountid) {
		this.updatebyAccountid = updatebyAccountid;
	}

	public String getUpdatebyChildid() {
		return updatebyChildid;
	}

	public void setUpdatebyChildid(String updatebyChildid) {
		this.updatebyChildid = updatebyChildid;
	}


	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return id;
	}

}
