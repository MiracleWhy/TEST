package com.uton.carsokApi.controller;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.GleefulReportRequest;
import com.uton.carsokApi.controller.request.SharedDispatcherRequest;
import com.uton.carsokApi.controller.request.SharedRecordRequest;
import com.uton.carsokApi.controller.response.GleefulReportResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.GleefulReport;
import com.uton.carsokApi.pay.alipay.util.StringUtil;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.GleefulReportService;
import com.uton.carsokApi.service.UploadService;
import com.uton.carsokApi.util.DateStyle;
import com.uton.carsokApi.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.uton.carsokApi.model.ChildAccount;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2017/7/17.
 */
@Controller
public class GleefulReportController {

    @Autowired
    CacheService cacheService;

    @Autowired
    GleefulReportService gleefulReportService;

    @Autowired
    UploadService uploadService;

    private final static Logger logger = Logger.getLogger(DailyCheckController.class);

    @RequestMapping(value = {"/getGleefulReportList"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getGleefulReportList(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            Date startDate = new Date(0);
            Date endDate=new Date(System.currentTimeMillis());
            java.util.Date nowDate = new java.util.Date();

            String filter = request.getParameter("filter");
            if(StringUtil.isEmpty(filter))
            {
                filter="";
            }
            switch(filter)
            {
                case "DAY":
                    startDate=new Date(System.currentTimeMillis());
                    break;
                case "WEEK":
                    startDate=new Date(DateUtil.getWeekStartDate().getTime());
                    break;
                case "MONTH":
                    startDate = new Date (DateUtil.getMonthStartDate().getTime());
                    break;
                case "SELECT":
                    String date = request.getParameter("date");//YYYY-MM
                    java.util.Date selectDate = DateUtil.StringToDate(date, DateStyle.YYYY_MM_DD);
                    startDate = new Date(selectDate.getTime());
                    endDate=new Date(selectDate.getTime());
                    break;
                default:
                    break;
            }

            result.setData(gleefulReportService.getGleefulReportList(acount.getId().toString(),acount.getSubPhone(),startDate,endDate));

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = {"/getGleefulReportListNew"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getGleefulReportListNew(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            Date startDate = new Date(0);
            Date endDate=new Date(System.currentTimeMillis());
            java.util.Date nowDate = new java.util.Date();

            String filter = request.getParameter("filter");
            if(StringUtil.isEmpty(filter))
            {
                filter="";
            }
            switch(filter)
            {
                case "DAY":
                    startDate=new Date(System.currentTimeMillis());
                    break;
                case "WEEK":
                    startDate=new Date(DateUtil.getWeekStartDate().getTime());
                    break;
                case "MONTH":
                    startDate = new Date (DateUtil.getMonthStartDate().getTime());
                    break;
                case "SELECT":
                    String date = request.getParameter("date");//YYYY-MM
                    java.util.Date selectDate = DateUtil.StringToDate(date, DateStyle.YYYY_MM_DD);
                    startDate = new Date(selectDate.getTime());
                    endDate=new Date(selectDate.getTime());
                    break;
                default:
                    break;
            }

            List<GleefulReportResponse> list = gleefulReportService.getGleefulReportList(acount.getId().toString(),acount.getSubPhone(),startDate,endDate);

            List<GleefulReportResponse> SharedList = new ArrayList<>();
            List<GleefulReportResponse> NotShareList = new ArrayList<>();

            if (list != null & list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getShareCount() > 0) {
                        SharedList.add(list.get(i));
                    } else {
                        NotShareList.add(list.get(i));
                    }
                }
            }

            Map<String,Object> map = new HashMap<>();
            map.put("SharedList",SharedList);
            map.put("SharedListCount",SharedList.size());
            map.put("NotShareList",NotShareList);
            map.put("NotShareListCount",NotShareList.size());
            result.setData(map);

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = {"/getGleefulReportDetail"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getGleefulReportDetail(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            String id =request.getParameter("id");
            result.setData(gleefulReportService.getGleefulReportDetail(Integer.parseInt(id)));

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = {"/deleteGleefulReport"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult deleteGleefulReport(HttpServletRequest request,@RequestBody GleefulReportRequest gleefulReportRequest) {
        BaseResult result = BaseResult.success();
        try {
            gleefulReportService.deleteGleefulReportById(gleefulReportRequest.getId());
        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }



    @RequestMapping(value = {"/updateGleefulReport"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult updateGleefulReport(HttpServletRequest request, @RequestBody GleefulReportRequest gleefulReportRequest) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            gleefulReportRequest.setAccountId(acount.getId().toString());

            if(gleefulReportRequest.getPicList()==null)
            {
                List<String> imgList = new ArrayList<>();
                for(String imgStr:gleefulReportRequest.getBase64List())
                {
                    String imgurl = uploadService.upload(request, imgStr);
                    imgList.add(imgurl);
                }
                gleefulReportRequest.setPicList(JSON.toJSONString(imgList));
            }
            gleefulReportService.updateGleefulReport(gleefulReportRequest,acount.getSubPhone());

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = {"/updateSharedDispatcher"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult updateSharedDispatcher(HttpServletRequest request, @RequestBody SharedDispatcherRequest sharedDispatcherRequest) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            gleefulReportService.updateSharedDispatcher(sharedDispatcherRequest.getShares(),acount.getId().toString(),"",sharedDispatcherRequest.getType());

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = {"/getSharedDispatcherList"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getSharedDispatcherList(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            String type=request.getParameter("type");
            result.setData(gleefulReportService.getSharedDispatcherList(acount.getId().toString(),type));

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }



    @RequestMapping(value = {"/updateSharedRecord"}, method = {RequestMethod.POST})
    public
    @ResponseBody
    BaseResult updateSharedRecord(HttpServletRequest request,@RequestBody SharedRecordRequest sharedRecordRequest) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);

            String phone = StringUtil.isEmpty(acount.getSubPhone())?acount.getAccount():acount.getSubPhone();

            //String reportId=request.getParameter("reportId");
            gleefulReportService.updateSharedRecord(acount.getId().toString(),phone,sharedRecordRequest.getSharedId());

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }



    @RequestMapping(value = {"/getGleefulSharedRecordList"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getGleefulSharedRecordList(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            String reportId = request.getParameter("reportId");
            result.setData(gleefulReportService.getGleefulSharedRecordList(acount.getId().toString(),reportId));

        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    // 获取喜报待办数量(小红点)
    @RequestMapping(value = {"/getGleefulSharedRecordCount"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getGleefulSharedRecordCount(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Integer count = 0;
            Acount acount = cacheService.getAcountInfoFromCache(request);
            String sharer = acount.getSubPhone();

            if(!StringUtil.isEmpty(sharer)){
                count = gleefulReportService.getGleefulSharedRecordCount(sharer);
            }
            result.setData(count);
        } catch (Exception e) {
            logger.error("areaInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取一些库存数据
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getProductMsg"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getProductMsg(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            Map map = gleefulReportService.getProductMessage(acount.getId(),acount.getAccount());
            result.setData(map);
        } catch (Exception e) {
            logger.error("getProductMsg error:", e);
            e.printStackTrace();
            return BaseResult.fail("0001","系统异常");
        }
        return result;
    }



















}
