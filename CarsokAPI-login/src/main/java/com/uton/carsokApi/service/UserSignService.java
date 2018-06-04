package com.uton.carsokApi.service;

import com.uton.carsokApi.model.UserSign;

/**
 * Created by SEELE on 2017/10/20.
 */
public interface UserSignService {
    public int updateUserSign(String accountId);
    public int getSignTimes(String accountId);
    public boolean presentToBolang(String mobile,int times);
    public boolean updatePresentTimes(String accountId, int times);
    public UserSign getUserSign(String accountId);
}
