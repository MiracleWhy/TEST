package com.uton.carsokApi.service;

import com.uton.carsokApi.model.Acount;

/**
 * Created by SEELE on 2017/9/19.
 */
public interface ProApplyService {

    Integer updateAccountForApplyPro(Acount acount);

    Integer queryApplyStatus(Acount acount);
}
