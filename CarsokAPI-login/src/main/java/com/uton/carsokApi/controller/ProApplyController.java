package com.uton.carsokApi.controller;

import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.ProApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by SEELE on 2017/9/19.
 */
@Controller
public class ProApplyController {

    @Autowired
    CacheService cacheService;

    @Autowired
    private ProApplyService proApplyService;

    /**
     * 申请专业版账户
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping("applyProAcount")
    public @ResponseBody OperateResult applyProAcount(HttpServletRequest request, @RequestBody Acount vo){
        OperateResult result = new OperateResult();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            vo.setId(acount.getId());
            Integer flag = proApplyService.updateAccountForApplyPro(vo);
            if (flag == 1) {
                result.setSuccess(true);
                result.setMessage("申请成功");
            } else {
                result.setSuccess(false);
                result.setMessage("申请失败，请重试");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("申请出错");
            return result;
        }
        return result;
    }

    @RequestMapping("getApplayStatus")
    public @ResponseBody OperateResult getApplayStatus(HttpServletRequest request){
        OperateResult result=new OperateResult();
        try {
            Acount acount = cacheService.getAcountInfoFromCache(request);
            Integer applyStatus = proApplyService.queryApplyStatus(acount);
            if (applyStatus != null) {
                result.setMessage("获取申请进度成功");
                result.setSuccess(true);
                result.setData(applyStatus);
            } else {
                result.setMessage("获取申请进度失败");
                result.setSuccess(false);
            }
        }catch (Exception e){
            result.setMessage("获取申请进度出错");
            result.setSuccess(false);
            return result;
        }
        return result;
    }
}
