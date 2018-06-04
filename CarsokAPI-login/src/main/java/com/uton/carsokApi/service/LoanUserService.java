package com.uton.carsokApi.service;

import com.github.pagehelper.Page;
import com.uton.carsokApi.model.Poster;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
@Service
public interface LoanUserService {
    public boolean isLoanUser(String accountId,String role);
}
