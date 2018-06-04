package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.SaasAuthorityMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEELE on 2017/11/9.
 */
@Service
public class SaasAuthorityServiceImpl implements SaasAuthorityService {


    private final static String MANAGER_PERMISSION = "yyglz";//运营报表管理者
    private final static String STAFF_PERMISSION = "yyyg";//运营报表员工
    private final static String CONSULTANT_ROLE = "qkyxgw";//潜客销售顾问
    private final static String MANAGER_ROLE = "qkjlgl";//潜客经理管理

    @Resource
    SaasAuthorityMapper authorityMapper;

    @Autowired
    ChildAccountMapper childAccountMapper;

    /***
     * 查询账号是否是管理者权限
     * @param childId
     * @return
     */
    @Override
    public Boolean judgeManagerOrNot(Integer childId) {
        boolean haveOrNot = false;
        List<String> authorities =authorityMapper.getAuthorityByUserId(childId);
        if (authorities != null) {
            haveOrNot = authorities.contains(MANAGER_PERMISSION);
        }
        return haveOrNot;
    }

    /**
     * 查询登录账号的报表权限（管理者Manager OR 员工Staff）
     * @param childId
     * @return
     */
    @Override
    public String queryAuthorityOfUser(int childId) {
        List<String> authorities =authorityMapper.getAuthorityByUserId(childId);
        if(authorities!=null&&authorities.size()!=0){
            if(authorities.contains(MANAGER_PERMISSION)){
                return "Manager";
            }
            if(authorities.contains(STAFF_PERMISSION)){
                return "Staff";
            }
        }
        return null;
    }



    /**
     * 获取查询目标子账号
     *
     * @param acount
     * @return
     */
    @Override
    public List<ChildAccount> queryTargetChildAccounts(Acount acount) {
        List<String> powerNames=new ArrayList<>();
        ChildAccount record =new ChildAccount();
        if(acount.getSubPhone() == null){
            //主账号登录
            powerNames.add(MANAGER_PERMISSION);
        }else {
            //子账号登录
            record.setChildAccountMobile(acount.getSubPhone());
            record=childAccountMapper.selectByModel(record);
            powerNames = authorityMapper.getAuthorityByUserId(record.getId());
            if (!powerNames.contains(MANAGER_PERMISSION)&&!powerNames.contains(STAFF_PERMISSION)) {
                //未选择报表权限，不显示任何数据
                return null;
            }
        }
        if (powerNames.contains(MANAGER_PERMISSION)) {
            //只要含有管理者权限就按照管理者权限处理
            //拥有管理者权限,查询经理和销售的业绩
            List<String> permissionName = new ArrayList<>();
            permissionName.add(CONSULTANT_ROLE);//潜客销售顾问权限
            permissionName.add(MANAGER_ROLE);//潜客经理权限
            List<ChildAccount> childAccounts =authorityMapper.queryPrivilegedChildAccount(acount.getAccount(), permissionName);
            //将主账号插入到子账号列表中
            ChildAccount account = new ChildAccount();
            account.setId(0);
            account.setChildAccountName("主账号");
            account.setAccountPhone(acount.getAccount());
            account.setChildAccountMobile(acount.getAccount());
            childAccounts.add(account);
            return childAccounts;
        }
        if (powerNames.contains(STAFF_PERMISSION)) {//只有员工权限，显示自己的数据
            List<ChildAccount> childAccounts = new ArrayList<>();
            childAccounts.add(record);
            return childAccounts;
        }
        return null;
    }
    public List<String> qxName(String childPhone){
        ;//所有权限名称  直接判断有没有该权限即可
        List<String> list= authorityMapper.selectQxName(childPhone);
        return list;
    }

    @Override
    public List<ChildAccount> queryTargetChild(Acount acount, List<String> qxName) {
        return authorityMapper.selectChild(acount.getAccount(),qxName);
    }




}
