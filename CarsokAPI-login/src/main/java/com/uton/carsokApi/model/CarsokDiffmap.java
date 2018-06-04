package com.uton.carsokApi.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyj
 * @since 2017-11-08
 */
@TableName("carsok_diffmap")
public class CarsokDiffmap extends Model<CarsokDiffmap> {

    private static final long serialVersionUID = 1L;

	/**
	 * 字段查分表自增ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 字段属性
	 */
	private String prop;

	/**
	 * 字段名称
	 */
	private String propname;

	/**
	 * 类名称
	 */
	private String classname;

	/**
	 * 适用模块：1:潜客管理，2：保有管理
	 */
	private Integer module;

	/**
	 * 数据类型：默认任务是String
	 */
	private String type;

	/**
	 * 字段的格式描述
	 */
	private String format;

	/**
	 * 结尾
	 */
	private String suffix;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public String getPropname() {
		return propname;
	}

	public void setPropname(String propname) {
		this.propname = propname;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public Integer getModule() {
		return module;
	}

	public void setModule(Integer module) {
		this.module = module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return id;
	}

}
