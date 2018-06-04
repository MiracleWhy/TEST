package com.uton.carsokApi.service;

import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.response.DailyCheckerResponse;
import com.uton.carsokApi.model.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
@Service
public interface PosterService {
    public Page<Poster> selectAllByPage(String sort);
    public Map<String, Object> getUserInfoByAccount(int accountId);
    List<Poster> getPosterByDate();
    CarsokCarNews getNewCarNewsLimit(Map param);
    GleefulReport getNewGleefulReport(Integer accountId);
}
