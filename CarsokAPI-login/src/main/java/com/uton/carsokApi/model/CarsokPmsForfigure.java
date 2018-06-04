package com.uton.carsokApi.model;


import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2017-10-10
 */

public class CarsokPmsForfigure {



	private Integer id;
    /**
     * 图片储存地址
     */

	private String picUrl;
    /**
     * 类型
     */
	private String types;
    /**
     * 创建时间
     */

	private Date createTime;
    /**
     * 标题
     */
	private String title;
    /**
     * 删除log
     */
	private Integer deletepic;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String gettypes() {
		return types;
	}

	public void settypes(String types) {
		this.types = types;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDeletepic() {
		return deletepic;
	}

	public void setDeletepic(Integer deletepic) {
		this.deletepic = deletepic;
	}


}
