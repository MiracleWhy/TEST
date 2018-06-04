package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.controller.request.ReportDateRequest;
import com.uton.carsokApi.controller.response.PotCusResponse;
import com.uton.carsokApi.controller.response.PotCusVoResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.dao.CustomerMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.service.SaasPotCusReportService;
import com.uton.carsokApi.util.DateParamUtil;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 潜客报表
 */
@Service
public class SaasPotCusReportServiceImpl implements SaasPotCusReportService {

    @Resource
    ChildAccountMapper childAccountMapper;

    @Resource
    CustomerMapper customerMapper;

    @Override
    public PotCusResponse getPotCusPage(Acount acount, List<CarsokChildAccount> childAccounts, ReportDateRequest vo) {
        int baseTotal = 0;//基盘总和
        int addTotal = 0;//潜客增加总和
        int failTotal = 0;//战败总和
        int rowTotal = 0;//潜客总数
        //分析时间条件
        Map<String, String> map = DateParamUtil.dateParamHandler(vo.getDate(), vo.getType());
        String startDate = map.get("startDate");
        String endDate = map.get("endDate");

        List<PotCusVoResponse> vos = new ArrayList<>();
        for (CarsokChildAccount item : childAccounts) {
            PotCusVoResponse potCusVo = customerMapper.queryPotCusVo(acount.getId(), item.getId(), startDate, endDate, vo.getType());
            if (potCusVo != null) {
                potCusVo.setConsultant(item.getChildAccountName());
                //行：潜客总计=基盘+留档-战败
                int total = potCusVo.getPotCusBaseCount() + potCusVo.getPotCusAddCount() - potCusVo.getPotCusFaillCount();
                potCusVo.setPotCusTotalCount(total);
                vos.add(potCusVo);
                baseTotal = baseTotal + potCusVo.getPotCusBaseCount();
                addTotal = addTotal + potCusVo.getPotCusAddCount();
                failTotal = failTotal + potCusVo.getPotCusFaillCount();
                rowTotal = rowTotal + total;
            }
        }
        //列表排序
        Collections.sort(vos, new PotCusVoResponse());
        toSetRanking(vos);

        PotCusResponse potCusResponse = new PotCusResponse();
        potCusResponse.setRows(vos);
        //获取总和
        potCusResponse.setPotCusBaseSum(baseTotal);
        potCusResponse.setPotCusAddSum(addTotal);
        potCusResponse.setPotCusFaillSum(failTotal);
        potCusResponse.setPotCusTotalSum(rowTotal);
        return potCusResponse;
    }

    private List<PotCusVoResponse> toSetRanking(List<PotCusVoResponse> vos) {
        int max = vos.size();
        vos.get(0).setRanking(1);
        for (int i = 0; i < max - 1; i++) {
            if (vos.get(i).getPotCusAddCount().compareTo(vos.get(i + 1).getPotCusAddCount()) == 0) {
                vos.get(i + 1).setRanking(vos.get(i).getRanking());
            } else {
                vos.get(i + 1).setRanking(i + 2);
            }
        }
        return vos;
    }
}
