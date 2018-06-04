package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.ChildInfoRequest;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.ChildAndSale;
import com.uton.carsokApi.model.ChildAndSales;
import com.uton.carsokApi.model.SelectSaleCar;
import com.uton.carsokApi.service.ChildInfoService;
import com.uton.carsokApi.service.UploadService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/14.
 */
@Controller
public class ChildInfoController {

    private final static Logger logger = Logger.getLogger(ChildInfoController.class);

    @Autowired
    ChildInfoService childInfoService;

    @Autowired
    UploadService uploadService;

    @RequestMapping(value = {"/getChildInfo"}, method = {RequestMethod.POST})
    public @ResponseBody
    BaseResult getChildInfo(HttpServletRequest request) {
        try {
            return childInfoService.getChildInfoMsg(request);
        } catch (Exception e) {
            logger.error("getChildInfo error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 上传头像
     *
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping(value = {"/uploadChildHead"}, method = {RequestMethod.POST})
    public @ResponseBody
    BaseResult uploadChildHead(@RequestBody ChildInfoRequest vo, HttpServletRequest request) {
        try {
            if (null == vo) {
                logger.error("参数为空");
                return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
            }
            if (StringUtil.isEmpty(vo.getChildHeadPic()) && StringUtil.isEmpty(vo.getChildHeadPicURL())) {
                logger.error("参数为空");
                return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
            }
            String url = "";
            if (vo.getChildHeadPicURL() != null && !vo.getChildHeadPicURL().equals("")) {
                url = vo.getChildHeadPicURL();
            }
            if (vo.getChildHeadPic() != null && !vo.getChildHeadPic().equals("")) {
                url = uploadService.upload(request, vo.getChildHeadPic());
            }
            if (!StringUtil.isEmpty(url)) {
                childInfoService.uploadChildHead(request, url);
                return BaseResult.success();
            } else {
                return BaseResult.fail(ErrorCode.UploadFail, ErrorCode.UploadFailInfo);
            }
        } catch (Exception e) {
            logger.error("upload image error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value = {"/getChildInfoByCustMobile"}, method = {RequestMethod.GET})
    public @ResponseBody
    BaseResult getChildInfoById(HttpServletRequest request) {
        try {
            String mobile = request.getParameter("mobile");
            return childInfoService.getChildInfoMsgById(mobile);
        } catch (Exception e) {
            logger.error("getChildInfoById error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value = "getSellerInfo", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getSellerInfo(HttpServletRequest request) {
        try {
            String productId = request.getParameter("ProductId");
            return childInfoService.getSellerInfo(productId);
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getManagerInsideByCarId", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getManagerInsideByCustMobileAndCarId(HttpServletRequest request) {
        try {
            String carId = request.getParameter("carId");
            return childInfoService.getChildInfoMsgByCarId(carId);
        } catch (Exception e) {
            logger.error("getChildInfoById error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 查询主账户下的所有子账户, 返回该主账号和所有子账号的信息
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/Allchild")
    @ResponseBody
    public BaseResult getChild(HttpServletRequest request, String id) {
        try {
            BaseResult baseResult = BaseResult.success();
            List<ChildAndSale> list = childInfoService.AllMassage(id);
            baseResult.setData(list);
            return baseResult;
        } catch (Exception e) {
            logger.error("getChild error:", e);
            return BaseResult.exception(e.getMessage());
        }

    }

    /**
     * 查询单个账户下的信息
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/oneMessage")
    @ResponseBody
    public BaseResult getOneMessage(HttpServletRequest request, String id) {
        try {
            BaseResult baseResult = BaseResult.success();
            Map map = new HashMap();

            ChildAndSales childAccount = childInfoService.oneMessage(id);
            List<SelectSaleCar> list = childInfoService.selectSaleCar(id);
            map.put("oneMessage", childAccount);
            map.put("SelectSaleCar", list);
            baseResult.setData(map);
            return baseResult;
        } catch (Exception e) {
            logger.error("getOneMessage error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    /**
     * 修改子账户的信息
     *
     * @param request
     * @param childAccount
     * @return
     */
    @RequestMapping(value = {"/updateChild"}, method = {RequestMethod.POST})
    public @ResponseBody
    BaseResult updateMessage(HttpServletRequest request, @RequestBody ChildAccount childAccount) {
        try {
            BaseResult result = childInfoService.updateMessage(childAccount);
            return result;
        } catch (Exception e) {
            logger.error("updateMessage:", e);
            return BaseResult.exception(e.getMessage());
        }
    }
}
