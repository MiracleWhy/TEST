package com.uton.carsokApi.pagination.dialect;

/**
 * 
 * @Title:Dialect.java
 * 
 * @Description:数据库Dialect接口
 *
 * @author  zhaoyang
 * @date    Jan 17, 2014 10:09:36 AM
 * @version V1.0
 */
public interface Dialect { 
	
	/**
	 * 是否支持物理分页
	 * 
	 * @return
	 */
	public boolean supportsLimit();
	
	/**
	 * 是否支持物理分页偏移�?
	 * 
	 * @return
	 */
	public boolean supportsLimitOffset();
	
	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param hasOffset
	 * @return
	 */
	public String getLimitString(String sql, boolean hasOffset);
	
	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	public String getLimitString(String sql, int offset, int limit);
	
	/**
	 * 生成查询总和的sql
	 * 
	 * @param sql
	 * @return
	 */
	public String getCountString(String sql);
	
}
