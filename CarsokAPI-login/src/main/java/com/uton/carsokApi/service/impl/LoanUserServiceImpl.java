package com.uton.carsokApi.service.impl;

import com.github.pagehelper.Page;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.LoanUserMapper;
import com.uton.carsokApi.dao.PosterMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.Poster;
import com.uton.carsokApi.service.LoanUserService;
import com.uton.carsokApi.service.PosterService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoanUserServiceImpl implements LoanUserService {
    @Autowired
    LoanUserMapper loanUserMapper;

    public boolean isLoanUser(String accountId,String role)
    {
        return (loanUserMapper.selectByAccountId(accountId,role)!=0);
    }


}
