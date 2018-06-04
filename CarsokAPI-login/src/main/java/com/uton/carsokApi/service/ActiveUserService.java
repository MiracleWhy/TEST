package com.uton.carsokApi.service;

import com.uton.carsokApi.model.Acount;

import java.util.Date;
import java.util.List;

/**
 * Created by SEELE on 2018/2/27.
 */
public interface ActiveUserService {
    public List<String> getAllUser();
    public int calActiveUser(Date startDate, Date endDate);
    public void persistentActiveUser(String type);
    public List<Acount> selectSimLogin();
    public void SimLogin();
}
