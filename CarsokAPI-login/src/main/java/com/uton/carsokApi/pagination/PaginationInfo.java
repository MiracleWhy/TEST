package com.uton.carsokApi.pagination;

import java.io.Serializable;

/**
 * 
 * @Title:PaginationInfo.java
 * 
 * @Description:封装分页信息的信息类
 *
 * @author  zhaoyang
 * @date    Jan 17, 2014 10:02:35 AM
 * @version V1.0
 */

public class PaginationInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5596084315954282504L;
	
	//default value
	private static final Integer defaultCurrentPage = 1;
	private static final Integer defaultRecordPerPage = 20;
	
	/**
	 * 当前页，1基址
	 */
	private Integer curPage = 1;
	/**
	 * 每页记录数
	 */
	private Integer pageSize = 0;
	/**
	 * 总页数
	 */
	private Integer totalPage = 1;
	/**
	 * 总记录数
	 */
	private Integer totalRecord = 0;
	
	private int limit=0;
	
	private int offset=0;
	
	
	
	
	private void initialize()
	{
		this.curPage = defaultCurrentPage;
		this.pageSize = defaultRecordPerPage;
	}
	
	public PaginationInfo()
	{
		this.initialize();
	}
	
	public PaginationInfo(Integer curPage, Integer pageSize)
	{
		this.setCurPage(curPage);
		this.setPageSize(pageSize);
	}
	
	public int getOffset()
	{
		return this.getPageSize() * (this.getCurPage() - 1);
	}
	
	public int getLimit()
	{
		return this.getPageSize();
	}
	
	public static PaginationInfo getDefault()
	{
		return new PaginationInfo(defaultCurrentPage, defaultRecordPerPage);
	}
	
	public Integer getCurPage()
	{
		return curPage == null ? defaultCurrentPage : curPage;
	}
	public void setCurPage(Integer curPage)
	{
		this.curPage = (curPage == null || curPage <= 0) ? defaultCurrentPage : curPage;
	}
	public Integer getPageSize()
	{
		return pageSize == null ? defaultRecordPerPage : pageSize;
	}
	public void setPageSize(Integer pageSize)
	{
		this.pageSize = (pageSize == null || pageSize <= 0) ? defaultRecordPerPage : pageSize;
	}
	public Integer getTotalPage()
	{
		return totalPage;
	}
	public void setTotalPage(Integer totalPage)
	{
		this.totalPage = totalPage;
	}
	public Integer getTotalRecord()
	{
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord)
	{
		this.totalRecord = totalRecord;
		if ((this.getCurPage() - 1) * this.getPageSize() >= this.totalRecord)
		{
			this.initialize();
		}
	}

	@Override
	public String toString()
	{
		return this.getCurPage() + "/" + this.getPageSize();
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
}
