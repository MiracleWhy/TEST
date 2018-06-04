package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.ModularTransferRequest;
import com.uton.carsokApi.model.TransferAscription;

import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 */
public interface TransferAscriptionService {

    void insertTransferMsg(TransferAscription transferAscription);

    List<TransferAscription> selectTransferAscriptionMsg(String id,String type);

    int selectTransferCount(int accountId,int childId);

    BaseResult transferByModular(ModularTransferRequest vo,int accountId);

    BaseResult transferBychildList(String account,int childId);
}
