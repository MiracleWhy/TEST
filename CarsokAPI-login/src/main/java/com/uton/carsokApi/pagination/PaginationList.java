package com.uton.carsokApi.pagination;

import java.util.ArrayList;

/**
 * 
 * @Title:PaginationList.java
 * 
 * @Description:带分页信息的List实现类，作为分页结果返回
 *
 * @author  zhaoyang
 * @date    Jan 17, 2014 10:02:14 AM
 * @version V1.0
 */

public class PaginationList<T> extends ArrayList<T>
{
	private static final long serialVersionUID = -6059628280162549106L;

	private PaginationInfo paginationInfo = null;

	public PaginationInfo getPaginationInfo()
	{
		return paginationInfo;
	}
	public void setPaginationInfo(PaginationInfo paginationInfo)
	{
		this.paginationInfo = paginationInfo;
	}

	public Integer getCurrentPage()
	{
		return this.paginationInfo.getCurPage();
	}
	public Integer getRecordPerPage()
	{
		return this.paginationInfo.getPageSize();
	}
	public Integer getTotalPage()
	{
		return this.paginationInfo.getTotalPage();
	}
	public Integer getTotalRecord()
	{
		return this.paginationInfo.getTotalRecord();
	}
}
