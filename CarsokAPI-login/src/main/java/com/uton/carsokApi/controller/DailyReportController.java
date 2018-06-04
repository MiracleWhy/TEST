package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.*;
import com.uton.carsokApi.controller.response.DailyReportResponse;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.DailyReportService;
import com.uton.carsokApi.util.DateStyle;
import com.uton.carsokApi.util.DateUtil;
import com.uton.carsokApi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * [今日战报]
 * Created by SEELE on 2017/6/20.
 */
@Controller
@RequestMapping("/dailyReport")
public class DailyReportController {

    private final static Logger logger = Logger.getLogger(DailyReportController.class);

    @Autowired
    DailyReportService dailyReportService;

    @Resource
    CacheService cacheService;

    /**
     * 2.3.8   《今日战报》
     * 进入今日战报---新版本入口在主页，不需要传日期参数
     * 获取卖车、收车、整备、接待列表显示信息。
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/intoDailySaleReportByDate"}, method = {RequestMethod.GET})
    @ResponseBody
    BaseResult intoDailySaleReportByDate(HttpServletRequest request) {
        //获取日期
        String date = request.getParameter("date");
        if (date == null || "".equals(date)) {
            date = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD);
        }
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            if (acount == null) {
                return new BaseResult(ErrorCode.ErrorRetCode, "登录用户token异常", null);
            }
            DailyReportResponse drr = dailyReportService.intoDailySaleReport(acount, date);
            return new BaseResult(ErrorCode.SuccessRetCode, ErrorCode.SuccessRetInfo, drr);
        } catch (Exception e) {
            logger.error("进入今日战报发生错误 error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 获取售车列表2.3.8
     *
     * @param request date 日历筛选参数
     * @return
     */
    @RequestMapping(value = {"/getSelledCarMsgByDate"}, method = {RequestMethod.GET})
    @ResponseBody
    BaseResult getSelledCarMsgByDate(HttpServletRequest request) {
        //获取日期
        String date = request.getParameter("date");
        if (date == null || "".equals(date)) {
            date = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD);
        }
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            List<SelledCarMeg> selledCarMegList = dailyReportService.selledCarMsgTotal(acount, date);
            List<DailyReportZan> zanList = dailyReportService.selectCarIdByAccountPhone(acount.getAccount(), "Product", acount.getAccount());
            if (!StringUtil.isEmpty(acount.getSubPhone())) {//子账号登录
                if (selectZJLIf(acount.getSubPhone()) == 1) {//若是总经理
                    //获取获赞人是登录子账号的列表
                    List<DailyReportZan> zjlZanList = dailyReportService.selectCarIdByAccountPhone(acount.getAccount(), "Product", acount.getSubPhone());
                    for (SelledCarMeg list : selledCarMegList) {
                        if (zjlZanList.size() == 0) {//若点赞列表为空，则设置为未点赞状态
                            list.setZjlZanIf(false);
                        }
                        for (DailyReportZan d : zjlZanList) {
                            List<Integer> zanCarIdList = new ArrayList<Integer>();
                            zanCarIdList.add(d.getCarId());
                            if (zanCarIdList.contains(list.getId())) {
                                list.setZanId(d.getId());
                                list.setZjlZanIf(true);
                                break;
                            } else {
                                list.setZjlZanIf(false);
                            }
                        }
                    }
                }
            } else {//若不是总经理
                for (SelledCarMeg list : selledCarMegList) {
                    if (zanList.size() == 0) {
                        list.setAccountZanIf(false);
                    }
                    for (DailyReportZan d : zanList) {
                        List<Integer> zanCarIdList = new ArrayList<Integer>();
                        zanCarIdList.add(d.getCarId());
                        if (zanCarIdList.contains(list.getId())) {
                            list.setZanId(d.getId());
                            list.setAccountZanIf(true);
                            break;
                        } else {
                            list.setAccountZanIf(false);
                        }
                    }
                }
            }
            result.setData(selledCarMegList);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取收车列表2.3.8
     *
     * @param request date 日历筛选参数
     * @return
     */
    @RequestMapping(value = {"/getAcqCarMsgByDate"}, method = {RequestMethod.GET})
    @ResponseBody
    BaseResult getAcqCarMsgByDate(HttpServletRequest request) {
        //获取日期
        String date = request.getParameter("date");
        if (date == null || "".equals(date)) {
            date = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD);
        }
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            //列表List
            List<AcqCarMsg> acqCarMsgList = dailyReportService.acqCarMsgTotal(Integer.valueOf(acount.getId()), date);
            //点赞表List
            List<DailyReportZan> zanList = dailyReportService.selectCarIdByAccountPhone(acount.getAccount(), "Acquisition", acount.getAccount());
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                if (selectZJLIf(acount.getSubPhone()) == 1) {
                    List<DailyReportZan> zjlZanList = dailyReportService.selectCarIdByAccountPhone(acount.getAccount(), "Acquisition", acount.getSubPhone());
                    for (AcqCarMsg list : acqCarMsgList) {
                        if (zjlZanList.size() == 0) {
                            list.setZjlZanIf(false);
                        }
                        for (DailyReportZan d : zjlZanList) {
                            List<Integer> zanCarIdList = new ArrayList<Integer>();
                            zanCarIdList.add(d.getCarId());
                            if (zanCarIdList.contains(list.getId())) {
                                list.setZanId(d.getId());
                                list.setZjlZanIf(true);
                                break;
                            } else {
                                list.setZjlZanIf(false);
                            }
                        }
                    }
                }
            } else {
                for (AcqCarMsg list : acqCarMsgList) {
                    if (zanList.size() == 0) {
                        list.setAccountZanIf(false);
                    }
                    for (DailyReportZan d : zanList) {
                        List<Integer> zanCarIdList = new ArrayList<Integer>();
                        zanCarIdList.add(d.getCarId());
                        if (zanCarIdList.contains(list.getId())) {
                            list.setZanId(d.getId());
                            list.setAccountZanIf(true);
                            break;
                        } else {
                            list.setAccountZanIf(false);
                        }
                    }
                }
            }
            result.setData(acqCarMsgList);
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取整备列表2.3.8
     *
     * @param request date 日历筛选参数
     * @return
     */
    @RequestMapping(value = {"/getZbMsgByDate"}, method = {RequestMethod.GET})
    @ResponseBody
    BaseResult getZbMsgByDate(HttpServletRequest request) {
        //获取日期
        String date = request.getParameter("date");
        if (date == null || "".equals(date)) {
            date = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD);
        }
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            List<ZbMsg> zbMsgList = dailyReportService.zbMsgTotal(Integer.valueOf(acount.getId()), date);
            List<DailyReportZan> zanList = dailyReportService.selectCarIdByAccountPhone(acount.getAccount(), "TaskZB", acount.getAccount());
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                if (selectZJLIf(acount.getSubPhone()) == 1) {
                    List<DailyReportZan> zjlZanList = dailyReportService.selectCarIdByAccountPhone(acount.getAccount(), "TaskZB", acount.getSubPhone());
                    for (ZbMsg list : zbMsgList) {
                        if (zjlZanList.size() == 0) {
                            list.setZjlZanIf(false);
                        }
                        for (DailyReportZan d : zjlZanList) {
                            List<Integer> zanCarIdList = new ArrayList<Integer>();
                            zanCarIdList.add(d.getCarId());
                            if (zanCarIdList.contains(list.getId())) {
                                list.setZanId(d.getId());
                                list.setZjlZanIf(true);
                                break;
                            } else {
                                list.setZjlZanIf(false);
                            }
                        }
                    }
                }
            } else {
                for (ZbMsg list : zbMsgList) {
                    if (zanList.size() == 0) {
                        list.setAccountZanIf(false);
                    }
                    for (DailyReportZan d : zanList) {
                        List<Integer> zanCarIdList = new ArrayList<Integer>();
                        zanCarIdList.add(d.getCarId());
                        if (zanCarIdList.contains(list.getId())) {
                            list.setZanId(d.getId());
                            list.setAccountZanIf(true);
                            break;
                        } else {
                            list.setAccountZanIf(false);
                        }
                    }
                }
            }
            result.setData(zbMsgList);
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取接待列表2.3.8
     *
     * @param request date 日历筛选参数
     * @return
     */
    @RequestMapping(value = {"/getReceptionByDate"}, method = {RequestMethod.GET})
    @ResponseBody
    BaseResult getReceptionByDate(HttpServletRequest request) {
        //获取日期
        String date = request.getParameter("date");
        if (date == null || "".equals(date)) {
            date = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD);
        }
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            List<DRReceptionResponse> drrList = dailyReportService.getReceptionList(String.valueOf(acount.getId()), date);
            List<DailyReportZan> zanList = dailyReportService.selectCarIdByAccountPhone(acount.getAccount(), "Customer", acount.getAccount());
            if (!StringUtil.isEmpty(acount.getSubPhone())) {
                if (selectZJLIf(acount.getSubPhone()) == 1) {
                    List<DailyReportZan> zjlZanList = dailyReportService.selectCarIdByAccountPhone(acount.getAccount(), "Customer", acount.getSubPhone());
                    for (DRReceptionResponse list : drrList) {
                        if (zjlZanList.size() == 0) {
                            list.setZjlZanIf(false);
                        }
                        for (DailyReportZan d : zjlZanList) {
                            List<Integer> zanCarIdList = new ArrayList<Integer>();
                            zanCarIdList.add(d.getCarId());
                            if (zanCarIdList.contains(list.getId())) {
                                list.setZanId(d.getId());
                                list.setZjlZanIf(true);
                                break;
                            } else {
                                list.setZjlZanIf(false);
                            }
                        }
                    }
                }
            } else {
                for (DRReceptionResponse list : drrList) {
                    if (zanList.size() == 0) {
                        list.setAccountZanIf(false);
                    }
                    for (DailyReportZan d : zanList) {
                        List<Integer> zanCarIdList = new ArrayList<Integer>();
                        zanCarIdList.add(d.getCarId());
                        if (zanCarIdList.contains(list.getId())) {
                            list.setZanId(d.getId());
                            list.setAccountZanIf(true);
                            break;
                        } else {
                            list.setAccountZanIf(false);
                        }
                    }
                }
            }
            result.setData(drrList);
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 获取接待列表详情2.3.8
     *
     * @param request date 日历筛选参数  childId 接待人子账号id
     * @return
     */
    @RequestMapping(value = {"/getReceptionDetailByDate"}, method = {RequestMethod.GET})
    @ResponseBody
    BaseResult getReceptionDetilByDate(HttpServletRequest request) {
        //获取日期
        String date = request.getParameter("date");
        if (date == null || "".equals(date)) {
            date = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD);
        }
        BaseResult result = BaseResult.success();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            String childId = request.getParameter("childId");
            List<DRReceptionDetailResponse> DetailList = dailyReportService.getReceptionDetail(date, String.valueOf(acount.getId()), childId);
            result.setData(DetailList);
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    /**
     * 新增员工日报
     */
    @RequestMapping("/insertDailyMsg")
    public @ResponseBody
    BaseResult insertDailyMsg(HttpServletRequest request, @RequestBody DailyReportRequest vo) {
        try {
            BaseResult result = dailyReportService.dailyReportInsert(vo);
            return result;
        } catch (Exception e) {
            logger.error("insertDailyMsg:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping("/selectDailyList")
    public @ResponseBody
    BaseResult selectDailyList(HttpServletRequest request, @RequestBody DailyReportRequest vo) {
        try {
            BaseResult result = dailyReportService.selectDailyList(request, vo);
            return result;
        } catch (Exception e) {
            logger.error("selectDailyList:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping("/dailyReportReadIf")
    public @ResponseBody
    BaseResult dailyReportReadIf(HttpServletRequest request, @RequestBody DailyReportRequest vo) {
        try {
            BaseResult result = BaseResult.success();
            Acount acount = cacheService.getAcountInfoFromCache(request);
            String childAccount = acount.getSubPhone();// 子账号电话,如果是主账号登录值为null
            if (!StringUtil.isEmpty(childAccount)) {//判断-是子账号

//                if (dailyReportService.selectRoleNameByChildPhone(childAccount).contains("zjl")) {//判断-是总经理角色
//                    result = dailyReportService.dailyReportReadIfZjl(vo,childAccount);
//                }
                List list = dailyReportService.selectQx(childAccount);
                Boolean boo = list.contains("yyglz");
                if (list.contains("yyglz") == true) {//判断-是总经理角色
                    result = dailyReportService.dailyReportReadIfZjl(vo, childAccount);
                } else {
                    result = dailyReportService.dailyReportReadIf(vo);
                }
            } else {
                result = dailyReportService.dailyReportReadIf(vo);
            }
            return result;
        } catch (Exception e) {
            logger.error("dailyReportReadIf:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 员工日报详情
     *
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping("/selectDailyReportMsg")
    public @ResponseBody
    BaseResult selectDailyReportMsg(HttpServletRequest request, @RequestBody DailyReportRequest vo) {
        try {
            BaseResult result = dailyReportService.selectDailyReportMsg(request, vo);
            return result;
        } catch (Exception e) {
            logger.error("selectDailyReportMsg:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping("/dailyReportScreen")
    public @ResponseBody
    BaseResult dailyReportScreen(HttpServletRequest request, @RequestBody DailyReportRequest vo) {
        try {
            BaseResult result = dailyReportService.dailyReportScreen(request, vo);
            return result;
        } catch (Exception e) {
            logger.error("dailyReportScreen:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 员工日报--已发人数。未发人数显示信息
     *
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping("/dailyReportShowInfo")
    public @ResponseBody
    BaseResult dailyReportShowInfo(HttpServletRequest request, @RequestBody DailyReportRequest vo) {
        try {
            BaseResult result = dailyReportService.dailyReportShowInfo(request, vo);
            return result;
        } catch (Exception e) {
            logger.error("dailyReportShowInfo:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 员工日报--角色筛选列表---新版本此接口已经废除
     *
     * @param request
     * @return
     */
    @RequestMapping("/dailyReportCountRoleList")
    public @ResponseBody
    BaseResult dailyReportCountRoleList(HttpServletRequest request) {
        try {
            BaseResult result = dailyReportService.dailyReportCountRoleList(request);
            return result;
        } catch (Exception e) {
            logger.error("dailyReportCountRoleList:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping("/dailyReportZan")
    public @ResponseBody
    BaseResult dailyReportZan(HttpServletRequest request, @RequestBody DailyReportZanRequest vo) {
        try {
            BaseResult result = dailyReportService.dailyReportZan(request, vo);
            return result;
        } catch (Exception e) {
            logger.error("dailyReportZan:", e);
            return BaseResult.exception(e.getMessage());
        }
    }


    public int selectZJLIf(String mobile) {
        List<String> roleList = dailyReportService.selectRoleNameByChildPhone(mobile);
        if (roleList.contains("yyglz")) {
            return 1;
        } else {
            return 0;
        }
    }

    @RequestMapping("/selectZanList")
    public @ResponseBody
    BaseResult selectZanList(HttpServletRequest request, @RequestBody DailyReportZan vo) {
        try {
            BaseResult result = dailyReportService.selectDianZanList(request, vo);
            return result;
        } catch (Exception e) {
            logger.error("selectZanList:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 其他日报（分享统计）
     *
     * @param request
     * @param searchDay
     * @return
     */
    @RequestMapping(value = {"/otherDailyReportCount"}, method = {RequestMethod.GET})
    public @ResponseBody
    BaseResult otherDailyReportCount(HttpServletRequest request, String searchDay) {
        try {
            BaseResult result = dailyReportService.selectOtherDailyReportCount(request, searchDay);
            return result;
        } catch (Exception e) {
            logger.error("otherDailyReportCount:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping("/insertShareMsg")
    public @ResponseBody
    BaseResult insertShareMsg(HttpServletRequest request, @RequestBody SharedRecord vo) {
        try {
            BaseResult result = dailyReportService.insertShare(request, vo);
            return result;
        } catch (Exception e) {
            logger.error("insertShareMsg:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 喜报统计
     *
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping("/selectOtherReportList")
    public @ResponseBody
    BaseResult selectOtherReportList(HttpServletRequest request, @RequestBody OtherReport vo) {
        try {
            BaseResult result = dailyReportService.selectOtherReport(request, vo);
            return result;
        } catch (Exception e) {
            logger.error("selectOtherReportList:", e);
            return BaseResult.exception(e.getMessage());
        }
    }
}
