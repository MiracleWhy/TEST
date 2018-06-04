package com.uton.carsokApi.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13/013.
 */
public interface SourceForgMapper {
    //查询单个的
    Double oneCount(@Param("id") Integer id,@Param("source") String source,@Param("date")String date,@Param("type") Integer type);
    //查询上一周一月和一年的
    Double chain(@Param("id") Integer id,@Param("source") String source,@Param("date")String date,@Param("type") Integer type);
    //查询所有的来源
    List<String> source();
    //查询单个所有数据和
    Integer countAllOne(@Param("id") Integer id,@Param("date")String date,@Param("type") Integer type);
    //查询主账号的所有数据
    Integer countAll(@Param("id") Integer id,@Param("date")String date,@Param("type") Integer type,@Param("child")List<Integer> child);
    //查询account的数据
    Double accountCount(@Param("id") Integer id,@Param("source") String source,@Param("date")String date,@Param("type") Integer type,@Param("child")List<Integer> child);

    //查询account上一周一月和一年的
    Double accountChains(@Param("id") Integer id,@Param("source") String source,@Param("date")String date,@Param("type") Integer type,@Param("child")List<Integer> child);
    //查询空时
    Double nullCount(@Param("id") Integer id,@Param("date")String date,@Param("type") Integer type,@Param("child")List<Integer> child);
    //查询account上一周一月和一年的
    Double nullChains(@Param("id") Integer id,@Param("date")String date,@Param("type") Integer type,@Param("child")List<Integer> child);


}
