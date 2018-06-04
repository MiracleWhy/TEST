package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.request.DRReceptionDetailResponse;
import com.uton.carsokApi.controller.request.DRReceptionResponse;
import com.uton.carsokApi.controller.request.DailyReportZanRequest;
import com.uton.carsokApi.controller.response.DailyAcqResponse;
import com.uton.carsokApi.controller.response.DailyReportResponse;
import com.uton.carsokApi.controller.response.DailySelledCarResponse;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.DailyReportRequest;
import com.uton.carsokApi.model.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

/**
 * Created by SEELE on 2017/6/20.
 */
public interface DailyReportService {
    BaseResult dailyReportInsert(DailyReportRequest vo);

    BaseResult selectDailyList(HttpServletRequest request, DailyReportRequest vo);

    BaseResult dailyReportReadIf(DailyReportRequest vo);

    BaseResult dailyReportReadIfZjl(DailyReportRequest vo, String zjlPhone);

    BaseResult selectDailyReportMsg(HttpServletRequest request, DailyReportRequest vo);

    BaseResult dailyReportScreen(HttpServletRequest request, DailyReportRequest vo);

    BaseResult dailyReportShowInfo(HttpServletRequest request, DailyReportRequest vo);

    BaseResult dailyReportCountRoleList(HttpServletRequest request);

    BaseResult employeeDailyReportInsert(DailyReportRequest vo, HttpServletRequest request);

    DailyReportResponse intoDailySaleReport(Acount account, String date);

    List<SelledCarMeg> selledCarMsgTotal(Acount account, String date);

    List<AcqCarMsg> acqCarMsgTotal(int accountId, String date);

    List<ZbMsg> zbMsgTotal(int accountId, String date);

    List<DRReceptionResponse> getReceptionList(String accountId, String date);

    List<DRReceptionDetailResponse> getReceptionDetail(String date, String accountId, String childID);

    BaseResult dailyReportZan(HttpServletRequest request, DailyReportZanRequest vo);

    List<DailyReportZan> selectCarIdByAccountPhone(String accountPhone, String departmentType, String fabulousPhone);

    List<String> selectRoleNameByChildPhone(String mobile);

    BaseResult selectDianZanList(HttpServletRequest request, DailyReportZan vo);

    BaseResult selectOtherDailyReportCount(HttpServletRequest request, String searchDay);

    BaseResult insertShare(HttpServletRequest request, SharedRecord vo);

    BaseResult selectOtherReport(HttpServletRequest request, OtherReport vo);

    List<String> selectQx(String childAccount);
}
