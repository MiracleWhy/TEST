package com.uton.carsokApi.pagination.dialect;



/**
 * 
 * @Title:OracleDialect.java
 * 
 * @Description:Oracle的Dialect实现�?
 *
 * @author  zhaoyang
 * @date    Jan 17, 2014 10:10:59 AM
 * @version V1.0
 */
public class OracleDialect implements Dialect {
	
	protected static final String SQL_END_DELIMITER = ";";
	
	/**
	 * 是否支持物理分页
	 * 
	 * @return
	 */
	public boolean supportsLimit() {

		return true;
	}
	
	/**
	 * 是否支持物理分页偏移�?
	 * 
	 * @return
	 */
	public boolean supportsLimitOffset() {

		return true;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param hasOffset
	 * @return
	 */
	public String getLimitString(String sql, boolean hasOffset) {

		return null;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	public String getLimitString(String sql, int offset, int limit) {

//		if (offset == 1) {
//			offset = 0;
//		}
		StringBuffer pageStr = new StringBuffer();
		pageStr.append("select * from ( select row_limit.*, rownum rownum_ from (");
		pageStr.append(this.trim(sql));
		pageStr.append(" ) row_limit where rownum <= ");
		pageStr.append(limit + offset);
		pageStr.append(" ) where rownum_ >");
		pageStr.append(offset);
		return pageStr.toString();
	}
	
	/**
	 * 生成查询总和的sql
	 * 
	 * @param sql
	 * @return
	 */
	public String getCountString(String sql) {

		String resultSql = "select count(1) from(" + sql;
		if (resultSql.toLowerCase().lastIndexOf("order by") != -1) {
			resultSql = resultSql.substring(0, resultSql.toLowerCase().lastIndexOf("order by"));
		}
		resultSql += ")";
		return resultSql;
	}
	
	private String trim(String sql) {

		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - SQL_END_DELIMITER.length());
		}
		return sql;
	}
	
	public static void main(String[] args) {

		OracleDialect od = new OracleDialect();
		System.out.println(od.getLimitString("select * from tb_ini", 0, 10));
		System.out.println(od.getCountString("select * from tb_ini where a > 1"));
	}
}
