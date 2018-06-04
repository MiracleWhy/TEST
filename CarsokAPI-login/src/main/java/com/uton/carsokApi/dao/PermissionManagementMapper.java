package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.PermissionResponse;
import com.uton.carsokApi.model.Permission;
import com.uton.carsokApi.model.PermissionManagement;
import com.uton.carsokApi.model.PowerOfParentChild;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */
public interface PermissionManagementMapper {

    int insertPermission(Permission permission);

    int deletePermission(@Param("childId") int childId);

    List<PermissionResponse> selectPermissionList(@Param("childId") int childId);

    List<PermissionResponse> selectPowerNameByRoleName(@Param("roleName") String roleName);

    List<PermissionManagement> selectParentPowerList(@Param("childId") int childId);

    List<PowerOfParentChild> selectChildPowerList(@Param("childId") int childId,@Param("parentPowerName") String parentPowerName);

    int deleteAuthoritymanage(@Param("childId")Integer childId);

    int deleteRoleManage(@Param("childId")Integer childId);

    int insertxygw(@Param("childId")Integer childId);//加营销顾问权限

    int inserBYtxygw(@Param("childId")Integer childId);//加保有营销顾问权限
}
