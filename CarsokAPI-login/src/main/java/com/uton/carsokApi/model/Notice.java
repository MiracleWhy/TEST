package com.uton.carsokApi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 搜索消息通知
 * @author xjz
 */

public class Notice implements Serializable{

	private static final long serialVersionUID = -2218531162861250848L;
	private Integer id;
	private String table_name;//表名
	private Integer record_id;//表ID
	private Integer status;//0,创建；1，已消费，2 删除
	private Date create_time;//
	private Date update_time;//
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public Integer getRecord_id() {
		return record_id;
	}
	public void setRecord_id(Integer record_id) {
		this.record_id = record_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	public Notice() {
		super();
	}
	/**
	 * @param table_name 表名
	 * @param record_id	 ID
	 * @param status	 状态值
	 */
	public Notice(String table_name, Integer record_id, Integer status) {
		super();
		this.table_name = table_name;
		this.record_id = record_id;
		this.status = status;
		this.create_time = new Date();
		this.update_time = new Date();
	}
	
	
}
