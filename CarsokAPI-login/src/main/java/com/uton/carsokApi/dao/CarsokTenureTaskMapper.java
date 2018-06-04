package com.uton.carsokApi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.uton.carsokApi.model.CarsokTenureTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author wangyj
 * @since 2017-11-09
 */
public interface CarsokTenureTaskMapper extends BaseMapper<CarsokTenureTask> {
    List<CarsokTenureTask> queryTaskBySqlStatement( @Param("ew") Wrapper<CarsokTenureTask> wrapper);
}