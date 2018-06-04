package com.uton.carsokApi.service;

import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SEELE on 2017/11/9.
 */
@Service
public interface SaasAuthorityService {

    //判断是否是运营报表管理者
    Boolean judgeManagerOrNot(Integer childId);

    List<ChildAccount> queryTargetChildAccounts(Acount acount);

    //所有权限名称
    List<String> qxName(String childPhone);

    //查询所有数据子账户
    List<ChildAccount>  queryTargetChild(Acount acount ,List<String> qxName);

    //查询登录账号报表权限
    String queryAuthorityOfUser(int childId);
}
