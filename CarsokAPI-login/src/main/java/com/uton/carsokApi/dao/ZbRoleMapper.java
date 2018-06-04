package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.ZbRole;
import org.apache.ibatis.annotations.Param;

public interface ZbRoleMapper {
	List<ZbRole> selectAll();

	List<ZbRole> selectAllByType(@Param("accountId")String accountId);
	
	List<String> selectBychildid(int childId);
}
