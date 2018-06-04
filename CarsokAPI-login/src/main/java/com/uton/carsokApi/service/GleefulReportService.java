package com.uton.carsokApi.service;

import com.uton.carsokApi.controller.response.GleefulReportResponse;
import com.uton.carsokApi.controller.response.GleefulSharerResponse;
import com.uton.carsokApi.model.GleefulReport;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2017/7/11.
 */
@Service
public interface GleefulReportService {
    public List<GleefulReportResponse> getGleefulReportList(String accountId,String sharer, Date startDate, Date endDate);
    public GleefulReport getGleefulReportDetail(int id);
    public boolean updateGleefulReport(GleefulReport gleefulReport,String subPhone);
    public boolean updateSharedDispatcher(List<String>shareds ,String accountId,String reportId,String type);
    public List<GleefulSharerResponse> getSharedDispatcherList(String accountId, String type);
    public boolean updateSharedRecord(String accountId,String sharer,String reportId);
    public Map<String,Object> getGleefulSharedRecordList(String accountId, String sharedId);
    public boolean deleteGleefulReportById(int id);
    public Integer getGleefulSharedRecordCount(String sharer);
    public Map getProductMessage(int accountId,String account);





}
