package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.ChildRole;
import com.uton.carsokApi.model.ChildRoleVo;
import org.apache.ibatis.annotations.Param;

public interface ChildRoleMapper {
	List<ChildRoleVo> selectRoleInfo(String mobile);
	
	int insert(ChildRole chilerole);
	
	int removeById(int id);
	
	List<ChildRole> selectBychildId(int childId);

	List<String> selectRoleNameByChildPhone(@Param("mobile") String mobile);
}
