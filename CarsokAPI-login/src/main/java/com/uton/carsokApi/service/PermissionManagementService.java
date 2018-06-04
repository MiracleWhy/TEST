package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.PermissionRequest;

/**
 * Created by Administrator on 2017/10/30.
 */
public interface PermissionManagementService {
    BaseResult insertPower(int accountId,PermissionRequest permissionRequest);
    BaseResult selectPermission(PermissionRequest permissionRequest);
}
