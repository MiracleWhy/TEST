package com.uton.carsokApi.dao;

import com.uton.carsokApi.controller.response.TransferCountResponse;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.TransferAscription;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 */
public interface TransferAscriptionMapper {
    int insertTransferAscription(TransferAscription transferAscription);

    List<TransferAscription> selectTransferMsg(@Param("transferId") String id,@Param("transferType")String type);

    /**
     * 查询子帐号是否有订单
     * @param accountId
     * @param childId
     * @return
     */
    TransferCountResponse selectTransferCount(@Param("accountId") int accountId,@Param("childId") int childId);

    /**
     *
     * @param tableName 表名
     * @param idLists
     * @param childId
     * @return
     */
    int transferByModular(@Param("tableName")String tableName,@Param("childId")int childId,@Param("idLists") List<Integer> idLists);

    /**
     * 查询模块信息id
     * @param tableName
     * @param accountId
     * @param childId
     * @return
     */
    List<Integer> selectModularIds(@Param("tableName")String tableName,@Param("accountId")int accountId,@Param("childId")int childId);

    /**
     * 查询出删除人员外的所有子帐号
     * @param accountPhone
     * @param childId
     * @return
     */
    List<ChildAccount>selectChildListByTransfer(@Param("accountPhone") String accountPhone,@Param("childId")int childId);

    TransferCountResponse selectNewTransferCount(@Param("accountId") int accountId,@Param("childId") int childId);
}
