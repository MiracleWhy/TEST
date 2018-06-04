package com.uton.carsokApi.controller;

import com.github.pagehelper.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.DatumManageRequest;
import com.uton.carsokApi.controller.response.DatumManageResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.AddPictureRequest;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.DatumManageService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raytine on 2017/9/7.
 */
@Controller
@RequestMapping("/datumManage")
public class DatumManageController {
    private final static Logger logger = Logger.getLogger(DatumManageController.class);

    @Autowired
    DatumManageService datumManageService;

    @Resource
    CacheService cacheService;

    /**
     * 资料管理列表页跳转
     * @param request
     * @return
     */
    @RequestMapping(value = "/dataListPage")
    public String datumListPage(HttpServletRequest request,String token,String subToken){
        token = request.getParameter("token");
        subToken = request.getParameter("subToken");
        if(!StringUtil.isEmpty(token)){
            request.setAttribute("token",token);
        }else{
            request.setAttribute("token","");
        }
        if(!StringUtil.isEmpty(subToken)){
            request.setAttribute("subToken",subToken);
        }else{
            request.setAttribute("subToken","");
        }
        return "/dataList";
    }

    /**
     * 资料详情页跳转
     * @param request
     * @param zlglProductId
     * @return
     */
    @RequestMapping(value = "/datumDetailPage")
    public String datumDetailPage(HttpServletRequest request,String zlglProductId){
        request.setAttribute("productId",zlglProductId);
        return "/dataDetail";
    }

    /**
     * 资料管理列表查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/datumList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getDatumList(HttpServletRequest request){
        DatumManageRequest datumManageRequest = new DatumManageRequest();
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }
        Acount acount = cacheService.getAcountInfoFromCache(request);
        if(acount != null){

            datumManageRequest.setAccountId(acount.getAccountCode().toString());
        }
        if(!StringUtil.isEmpty(request.getParameter("saleStatus"))){

            datumManageRequest.setSaleStatus(request.getParameter("saleStatus"));
        }
        if(!StringUtil.isEmpty(request.getParameter("productName"))){

            datumManageRequest.setProductName(request.getParameter("productName"));
        }
        if(!StringUtil.isEmpty(request.getParameter("createTime"))){

            datumManageRequest.setCreateTime(request.getParameter("createTime"));
        }
        if(!StringUtil.isEmpty(request.getParameter("mouth"))){

            datumManageRequest.setMouth(request.getParameter("mouth"));
        }
        Map<String,Object> map = new HashMap();
        try {
            Page<DatumManageResponse> dataList = datumManageService.getDatumList(pr,pc,datumManageRequest);
            map.put("dataList",dataList.toPageInfo());
            map.put("count",datumManageService.getEachCount(datumManageRequest));
        } catch (Exception e) {
            logger.error("getDatumList error:", e);
        }
        return map;
    }

    /**
     * 资料详情列表查询
     * @param request
     * @return
     */
    @RequestMapping(value = "pictureList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getPictureList(HttpServletRequest request){
        BaseResult result = BaseResult.success();
        try {
            result.setData(datumManageService.getPictureList(request.getParameter("productId")));
        } catch (Exception e) {
            logger.error("getPictureList error:", e);
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }


    private int getpc(HttpServletRequest request){
        String value = request.getParameter("pc");
        if(value == null || value.trim().isEmpty()){
            return 1;
        }
        return Integer.parseInt(value);
    }

    /**
     * 新增图片
     * @param addPictureRequest
     * @return
     */
    @RequestMapping(value = { "/addPic" }, method = { RequestMethod.POST })
    public @ResponseBody BaseResult addPic(@RequestBody AddPictureRequest addPictureRequest) {
        try {
            if(null != addPictureRequest){
                datumManageService.insertPic(addPictureRequest);
                return BaseResult.success();
            }else{
                logger.error("参数为空");
                return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
            }
        } catch (Exception e) {
            logger.error("addPic error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 根据id删除图片
     * @param request
     * @return
     */
    @RequestMapping(value = "/delNewPic")
    @ResponseBody
    public BaseResult delNewPic(HttpServletRequest request){
        try {
            if(null != request.getParameter("id")){
                datumManageService.delNewPic(request.getParameter("id"));
                return BaseResult.success();
            }else{
                logger.error("参数为空");
                return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
            }
        } catch (Exception e) {
            logger.error("delNewPic error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }
}
