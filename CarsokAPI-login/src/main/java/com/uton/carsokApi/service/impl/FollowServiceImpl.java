package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.FollowResponse;
import com.uton.carsokApi.controller.response.FollowVoResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.FollowMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.FollowService;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.util.DateParamUtil;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2017/11/11.
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    ChildAccountMapper childAccountMapper;

    @Resource
    FollowMapper followMapper;

    @Override
    public FollowResponse getFollowPage(Acount acount, List<CarsokChildAccount> childAccounts, ReportDateRequest vo) {
        FollowResponse followResponse=new FollowResponse();
        int followSum=0;//跟进数量总计
        int followTotalCount=0;//跟进总计总计
        int followSumBefore=0;
        //分析时间参数
        Map<String,String> map= DateParamUtil.dateParamHandler(vo.getDate(),vo.getType());
        String startDate=map.get("startDate");
        String endDate=map.get("endDate");
        Map<String,String> mapBefore=DateParamUtil.lastDateParamHandler(startDate,endDate,vo.getType());
        //获取数据列表
        List<FollowVoResponse> rows=new ArrayList<>();//行数据
        for(CarsokChildAccount item:childAccounts){
            FollowVoResponse row=new FollowVoResponse();
            //当前维度跟进次数
            Integer followOneCount=followMapper.getFollowOneCount(acount.getId(),item.getId(),startDate,endDate);
            //上一维度跟进次数
            Integer followOneCountBefore=followMapper.getFollowOneCount(acount.getId(),item.getId(),mapBefore.get("startDate"),mapBefore.get("endDate"));
            //跟进总计
            Integer followOneTotal=followMapper.getFollowOneCount(acount.getId(),item.getId(),null,null);
            //-----计算环比-----
            //箭头判断
            String upOrDown;
            if(followOneCount>followOneCountBefore){
                upOrDown="up";
            }else {
                upOrDown="down";
            }
            //计算环比
            BigDecimal chain =new BigDecimal(0);
            if(followOneCountBefore==0){
                if(followOneCount!=0){
                    chain =new BigDecimal(100);
                }
            }else {
                chain=new BigDecimal(followOneCount-followOneCountBefore)
                        .divide(new BigDecimal(followOneCountBefore),4,BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100));
            }
            row.setConsultant(item.getChildAccountName());
            row.setFollowOneCount(followOneCount);
            row.setFollowOneTotal(followOneTotal);
            row.setChain(chain.abs().doubleValue());
            row.setUpOrDown(upOrDown);
            rows.add(row);

            followSum=followSum+followOneCount;//跟进次数合计
            followSumBefore=followSumBefore+followOneCountBefore;//上一维度跟进次数合计
            followTotalCount=followTotalCount+followOneTotal;//跟进合计合计
        }
        //合计
        followResponse.setFollowTotalCount(followTotalCount);
        followResponse.setFollowSum(followSum);

        if(followSum>followSumBefore){
            followResponse.setUpOrDownSum("up");
        }else {
            followResponse.setUpOrDownSum("down");
        }
        if(followSumBefore!=0){
            BigDecimal chain=new BigDecimal(followSum-followSumBefore).divide(new BigDecimal(followSumBefore),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).abs();
            followResponse.setChainSum(chain.doubleValue());
        }else {
            if(followSum==0){
                followResponse.setChainSum(new Double(0));
            }else {
                followResponse.setChainSum(new Double(100));
            }

        }
        //排名
        rows=toOrderList(rows);
        followResponse.setRows(rows);
        return followResponse;
    }

    private List<FollowVoResponse> toOrderList(List<FollowVoResponse> vos) {
        int max=vos.size();
        FollowVoResponse temp;
        for(int i=0;i<max;i++){
            for(int j=i+1;j<max;j++){
                if(compare(vos.get(i).getFollowOneCount(),vos.get(j).getFollowOneCount())<0){
                    temp=vos.get(i);
                    vos.set(i,vos.get(j));
                    vos.set(j,temp);
                }
            }
        }
        vos.get(0).setRanking(1);
        for(int i=0;i<max-1;i++){
            if(compare(vos.get(i).getFollowOneCount(),vos.get(i+1).getFollowOneCount())==0){
                vos.get(i+1).setRanking(vos.get(i).getRanking());
            }else {
                vos.get(i+1).setRanking(i+2);
            }
        }
        return vos;
    }

    private int compare(int number1,int number2){
        if(number1>number2){
            return 1;
        }
        if(number1<number2){
            return -1;
        }
        return 0;
    }
}
