package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.PermissionRequest;
import com.uton.carsokApi.controller.response.PermissionResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.ChildRoleMapper;
import com.uton.carsokApi.dao.PermissionManagementMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.ICarsokAuthoritymanageService;
import com.uton.carsokApi.service.ICarsokRoleManageService;
import com.uton.carsokApi.service.PermissionManagementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/10/30.
 */
@Service
public class PermissionManagementServiceImpl implements PermissionManagementService {

    private final static Logger logger = Logger.getLogger(PermissionManagementServiceImpl.class);

    @Autowired
    PermissionManagementMapper permissionManagementMapper;

    @Autowired
    ChildRoleMapper roleMapper;

    @Autowired
    ChildAccountMapper childAccountMapper;

    @Resource
    ICarsokRoleManageService carsokRoleManageService;

    @Resource
    ICarsokAuthoritymanageService carsokAuthoritymanageService;

    @Override
    public BaseResult insertPower(int accountId,PermissionRequest permissionRequest) {
        try{
            ChildAccount child = new ChildAccount();
            child.setChildAccountMobile(permissionRequest.getChildMobile());
            ChildAccount childAccount = childAccountMapper.selectByModel(child);
            permissionManagementMapper.deletePermission(childAccount.getId());
            permissionManagementMapper.deleteAuthoritymanage(childAccount.getId());
            permissionManagementMapper.deleteRoleManage(childAccount.getId());
            Permission permission = new Permission();
            for(String per:permissionRequest.getRoleNames()){
                if("glz".equals(per) || "yg".equals(per)){
                    CarsokAuthoritymanage carsokAuthoritymanage = new CarsokAuthoritymanage();
                    carsokAuthoritymanage.setAccountid(accountId);
                    carsokAuthoritymanage.setChildid(childAccount.getId());
                    if("glz".equals(per)){
                        carsokAuthoritymanage.setAuthorityid(1);
                    }else if("yg".equals(per)){
                        carsokAuthoritymanage.setAuthorityid(2);
                    }
                    carsokAuthoritymanageService.insertAuthorityManage(carsokAuthoritymanage);
                }else if("qkyxgw".equals(per) || "qkjlgl".equals(per) || "qkkfdp".equals(per)
                        || "byyxgw".equals(per) || "byjlgl".equals(per) || "bykfdp".equals(per)){
                    CarsokRoleManage carsokRoleManage = new CarsokRoleManage();
                    carsokRoleManage.setAccountid(accountId);
                    carsokRoleManage.setChildid(childAccount.getId());
                    if("qkyxgw".equals(per) || "byyxgw".equals(per)){
                        carsokRoleManage.setRoleid(1);
                    }else if("qkjlgl".equals(per) || "byjlgl".equals(per)){
                        carsokRoleManage.setRoleid(2);
                    }else if("qkkfdp".equals(per) || "bykfdp".equals(per)){
                        carsokRoleManage.setRoleid(3);
                    }
                    carsokRoleManageService.insertCarsokRoleManage(carsokRoleManage);
                }
                permission.setPowerName(per);
                permission.setChildId(childAccount.getId());
                permission.setCreateTime(new Date());
                permissionManagementMapper.insertPermission(permission);
            }
            return BaseResult.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
        }
        return BaseResult.success();
    }

    @Override
    public BaseResult selectPermission(PermissionRequest permissionRequest) {
        BaseResult baseResult = BaseResult.success();
        ChildAccount child = new ChildAccount();
        child.setChildAccountMobile(permissionRequest.getChildMobile());
        ChildAccount childAccount = childAccountMapper.selectByModel(child);

        //查询权限列表
        List<PermissionManagement> permissionManagement = null;
        if(childAccount!=null){
            permissionManagement = permissionManagementMapper.selectParentPowerList(childAccount.getId());
        }else{
            permissionManagement = permissionManagementMapper.selectParentPowerList(0);
        }
        List<PermissionManagement> permissionManagement1 = new ArrayList<>();
        List<PermissionManagement> permissionManagement2 = new ArrayList<>();
        List<PermissionManagement> permissionManagement2_2 = new ArrayList<>();
        List<PermissionManagement> permissionManagement3 = new ArrayList<>();
        Map<Integer,PermissionManagement> firstPermissionManagement=new HashMap<>();
        Map<Integer,PermissionManagement> secondPermissionManagement=new HashMap<>();
        Map<Integer,List<PermissionManagement>> thirdPermissionManagement=new HashMap<>();
        Map<Integer,List<PermissionManagement>> secondPermissionManagements=new HashMap<>();
        for(PermissionManagement power:permissionManagement){
            //如果父级id不为空，说明是子级权限
            if(power.getParentId()!=null){
                //2级3级权限在一起
                permissionManagement2.add(power);
                //把二级权限放进去
                secondPermissionManagement.put(power.getId(),power);
            }else{
                //1级权限
                permissionManagement1.add(power);
                //1级权限放进去
                firstPermissionManagement.put(power.getId(),power);
            }
        }

        //遍历2级3级权限list
        for(PermissionManagement power:permissionManagement2){
            //如果当前对象的ParentId在二级权限的key中说明是3级权限
            if(secondPermissionManagement.get(power.getParentId())!=null){
                //加到3级权限map中
                permissionManagement3.add(power);
            }else{
                //否则加到2级权限中
                permissionManagement2_2.add(power);
            }
        }
        //遍历3级权限list
        for(PermissionManagement power:permissionManagement3){
            List<PermissionManagement> permissionManagements=null;
            //如果power的ParentId 不在3级权限的key中
            if(thirdPermissionManagement.get(power.getParentId())==null){
                permissionManagements=new ArrayList<>();
                //创建新的集合并添加当前对象到list
                permissionManagements.add(power);
                //再把list放到3级权限map
                thirdPermissionManagement.put(power.getParentId(),permissionManagements);
            }else{
                //否则直接取出key为power.getParentId()的list
                permissionManagements=thirdPermissionManagement.get(power.getParentId());
                permissionManagements.add(power);
            }
        }
        //同上
        for(PermissionManagement power:permissionManagement2_2){
            power.setChildPowerList(thirdPermissionManagement.get(power.getId()));
            List<PermissionManagement> permissionManagements=null;
            if(secondPermissionManagements.get(power.getParentId())==null){
                permissionManagements=new ArrayList<>();
                permissionManagements.add(power);
                secondPermissionManagements.put(power.getParentId(),permissionManagements);
            }
            else{
                permissionManagements=secondPermissionManagements.get(power.getParentId());
                permissionManagements.add(power);
            }

        }

        for(PermissionManagement power:permissionManagement1){
            power.setChildPowerList(secondPermissionManagements.get(power.getId()));
        }

        //Start---------------------------------xin_mz 潜客和保有的没有权限的时候默认分配营销顾问------------------------------------------------------------------------
        int flageQK = 0;//判断潜客下面的权限是否选中
            for (int i = 0; i < permissionManagement2_2.size() ; i++) {
               /* if(permissionManagement2_2.get(i).getPowerName().equals("qzkhgl")){//潜在客户*/
                if("qzkhgl".equals(permissionManagement2_2.get(i).getPowerName())){//潜在客户
                    if(permissionManagement2_2.get(i).getPowerIf() == 1){//潜在客户已经选中
                        for (int j = 0; j <permissionManagement2_2.get(i).getChildPowerList().size() ; j++) {
                            if(permissionManagement2_2.get(i).getChildPowerList().get(j).getPowerIf() ==0){//子权限如果等于0,则将flage++;
                                flageQK++;
                            }
                        }
                    }
                }
                if(permissionManagement2_2.get(i).getPowerName().equals("qzkhgl") && flageQK == 3){//2.5.7版本中潜在客户下面只有3个子权限,所以,flage=3。若继续加子权限数量,则flage==子权限数量
                    for (int j = 0; j < permissionManagement2_2.get(i).getChildPowerList().size(); j++) {
                        if(permissionManagement2_2.get(i).getChildPowerList().get(j).getPowerName().equals("qkyxgw")){//潜客营销顾问
                            permissionManagement2_2.get(i).getChildPowerList().get(j).setPowerIf(1);
                            permissionManagementMapper.insertxygw(childAccount.getId());
                        }
                    }
                }
            }
;
        int flageBY = 0;//判断保有下面的权限是否选中
        for (int i = 0; i < permissionManagement2_2.size() ; i++) {
         /*  if(permissionManagement2_2.get(i).getPowerName().equals("bykhgl")){//保有客户*/
              if("bykhgl".equals(permissionManagement2_2.get(i).getPowerName())){//保有客户
                if(permissionManagement2_2.get(i).getPowerIf() == 1){//保有客户已经选中
                    for (int j = 0; j <permissionManagement2_2.get(i).getChildPowerList().size() ; j++) {
                        if(permissionManagement2_2.get(i).getChildPowerList().get(j).getPowerIf() ==0){//子权限如果等于0,则将flage++;
                            flageBY++;
                        }
                    }
                }
            }
            if(permissionManagement2_2.get(i).getPowerName().equals("bykhgl") && flageBY == 3){////2.5.7版本中潜在客户下面只有3个子权限,所以,flage=3。若继续加子权限数量,则flage==子权限数量
                for (int j = 0; j < permissionManagement2_2.get(i).getChildPowerList().size(); j++) {
                    if(permissionManagement2_2.get(i).getChildPowerList().get(j).getPowerName().equals("byyxgw")){//保有营销顾问
                        permissionManagement2_2.get(i).getChildPowerList().get(j).setPowerIf(1);
                        permissionManagementMapper.inserBYtxygw(childAccount.getId());
                    }
                }
            }
        }
        //End---------------------------------xin_mz 潜客和保有的没有权限的时候默认分配营销顾问------------------------------------------------------------------------

        baseResult.setData(permissionManagement1);
        return baseResult;
    }
}
