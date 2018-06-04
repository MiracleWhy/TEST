package com.uton.carsokApi.pagination;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Title:PaginationSqlSession.java
 * 
 * @Description:分页SqlSession实现
 *
 * @author  zhaoyang
 * @date    Jan 17, 2014 10:04:05 AM
 * @version V1.0
 */

public class PaginationSqlSession extends SqlSessionTemplate
{

	public PaginationSqlSession(SqlSessionFactory sqlSessionFactory, ExecutorType executorType, PersistenceExceptionTranslator exceptionTranslator)
	{
		super(sqlSessionFactory, executorType, exceptionTranslator);
	}
	public PaginationSqlSession(SqlSessionFactory sqlSessionFactory, ExecutorType executorType)
	{
		super(sqlSessionFactory, executorType);
	}
	public PaginationSqlSession(SqlSessionFactory sqlSessionFactory)
	{
		super(sqlSessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	public PaginationList selectPaginationList(String statement, Object parameter, PaginationInfo paginationInfo)
	{
		PaginationList paginationList = new PaginationList();
		
		if (parameter == null)
		{
			throw new RuntimeException("parameter can not be null");
		}
		if (parameter instanceof Map<?, ?>)
		{
			((Map)parameter).put("paginationInfo", paginationInfo);
		}
		List result = super.selectList(statement, parameter);
		
		paginationList.addAll(result);
		if (paginationInfo == null)
		{
			paginationInfo = new PaginationInfo();
			paginationInfo.setCurPage(1);
			paginationInfo.setPageSize(result.size());
			paginationInfo.setTotalPage(1);
			paginationInfo.setTotalRecord(result.size());
		}
		paginationList.setPaginationInfo(paginationInfo);
		
		return paginationList;
	}

}
