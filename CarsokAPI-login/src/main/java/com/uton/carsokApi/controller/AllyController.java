package com.uton.carsokApi.controller;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.controller.request.AllyRequest;
import com.uton.carsokApi.controller.response.AllyAddFriendResponse;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.service.AllyService;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.util.HttpClientUtil;
import com.uton.carsokApi.util.UploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.utils.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Raytine on 2017/10/18.
 */
@Controller
@RequestMapping(value = "/ally")
public class AllyController {

    private final static Logger logger = Logger.getLogger(AllyController.class);

    @Autowired
    AllyService allyService;

    @Value("${store.url.prefix}")
    private String weiShopUrl;

    @Resource
    CacheService cacheService;

    @Resource
    RedisTemplate redisTemplate;

    /**
     * 加号页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value = "/addNewFriendPage")
    public String addNewFriendPage(HttpServletRequest request,String token,String subToken){
        Acount acount = allyService.getMainAccount(token,subToken);
        request.setAttribute("accountCode",acount.getAccountCode());
        request.setAttribute("weiShopUrl",weiShopUrl);
        return "/addNewFriend";
    }

    /**
     * 好友列表展示页跳转
     * @param request
     * @return
     */
    @RequestMapping(value = "/newFriendList")
    public String newFriendList(HttpServletRequest request,String token,String subToken){
        Acount acount = allyService.getMainAccount(token,subToken);
        request.setAttribute("accountCode",acount.getAccountCode());
        request.setAttribute("weiShopUrl",weiShopUrl);
        return "/allyFriendList";
    }

    /**
     * 消息列表展示页跳转
     * @param request
     * @return
     */
    @RequestMapping(value = "/messageList")
    public String messageList(HttpServletRequest request,String token,String subToken){
        Acount acount = allyService.getMainAccount(token,subToken);
        request.setAttribute("accountCode",acount.getAccountCode());
        return "/allyMessage";
    }

    /**
     * 车商联盟首页好友列表展示查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/allyList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getFriendList(HttpServletRequest request){
        BaseResult result = BaseResult.success();
        try {
            result.setData(allyService.getFriendList(request.getParameter("accountCode")));
        } catch (Exception e) {
            logger.error("getFriendList error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

    /**
     * 按条件搜索好友查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/searchFriendList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult searchFriendList(HttpServletRequest request){
        BaseResult result = BaseResult.success();
        int pageSize = 10;
        int pageNum = getpc(request);
        if(pageNum==0){
            pageNum=1;
        }
        try {
            if("1".equals(request.getParameter("ver"))){//ver不为空，带分页查询
                result.setData(allyService.searchFriendListV1(request.getParameter("searchName"),request.getParameter("accountCode"),pageNum,pageSize));
            }else{
                result.setData(allyService.searchFriendList(request.getParameter("searchName"),request.getParameter("accountCode")));
            }
        } catch (Exception e) {
            logger.error("searchFriendList error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

    /**
     * 加号页面获取数据列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getNewFriendList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getNewFriendList(HttpServletRequest request){
        BaseResult result = BaseResult.success();
        try {
            AllyAddFriendResponse allyAddFrindResponse = new AllyAddFriendResponse();
            allyAddFrindResponse.setNewFriendList(allyService.getNewFriendList(request.getParameter("accountCode")));
            allyAddFrindResponse.setLocalFriendList(allyService.getLocalFriendList(request.getParameter("accountCode")));
            result.setData(allyAddFrindResponse);
        } catch (Exception e) {
            logger.error("getNewFriendList error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

    /**
     * 获取消息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMessageList",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getMessageList(HttpServletRequest request){
        BaseResult result = BaseResult.success();
        try {
            result.setData(allyService.getMessageList(request.getParameter("accountCode")));
        } catch (Exception e) {
            logger.error("getMessageList error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

    /**
     * 添加/拒绝/删除盟友
     * @param request
     * @param allyRequest
     * @return
     */
    @RequestMapping(value = "/addFriend",method = RequestMethod.POST)
    public @ResponseBody
    BaseResult addFriend(HttpServletRequest request, @RequestBody AllyRequest allyRequest){
        try {
            if(null != allyRequest){
                allyService.addNewFriend(allyRequest);
                return BaseResult.success();
            }else{
                logger.error("参数为空");
                return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
            }
        } catch (Exception e) {
            logger.error("addFriend error:", e);
            return BaseResult.exception("系统异常");
        }
    }

    /**
     * 删除消息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateMessageById",method = RequestMethod.POST)
    public @ResponseBody
    BaseResult updateMessageById(HttpServletRequest request,String id){
        try {
            if(null != id){
                allyService.updateMessageById(id);
                return BaseResult.success();
            }else{
                logger.error("参数为空");
                return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo);
            }
        } catch (Exception e) {
            logger.error("updateMessageById error:", e);
            return BaseResult.exception("系统异常");
        }
    }

    /**
     * 消息通知，查询是否有未读消息
     * @param request
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/getMessageCount")
    @ResponseBody
    public BaseResult getMessageCount(HttpServletRequest request, String mobile){
        BaseResult result = BaseResult.success();
        try {
            result.setData(allyService.getMessageCount(mobile));
        } catch (Exception e) {
            logger.error("getMessageCount error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }

    /**
     * 查询是否有新的好友申请
     * @param request
     * @return
     */
    @RequestMapping(value = "/getNewFriendCount")
    @ResponseBody
    public BaseResult getNewFriendCount(HttpServletRequest request,String mobile){
        BaseResult result = BaseResult.success();
        try {
            result.setData(allyService.getNewFriendCount(mobile));
        } catch (Exception e) {
            logger.error("getNewFriendCount error:", e);
            return BaseResult.exception("系统异常");
        }
        return result;
    }
/**
* @author zhangD
* @date 2018/2/6 11:44
* @Description: 获取七牛云token
*/
    @RequestMapping(value = "/getToken")
    @ResponseBody
    public BaseResult getToken(HttpServletRequest request) {

        try {
            String token = request.getHeader("token");
            String subToken = request.getHeader("subToken");
            if(StringUtils.isEmpty(token) && StringUtils.isNotEmpty(subToken)){
                token=subToken;
            }else if (StringUtils.isEmpty(token) && StringUtils.isEmpty(subToken)){
                return BaseResult.exception("请重新登录!");
            }
            boolean isExist = redisTemplate.hasKey(token);
           if(isExist){
               return BaseResult.success(UploadUtil.getUpToken());
           }else{
               return BaseResult.exception("系统异常");
           }
        } catch (Exception e) {
            logger.error("getToken error:", e);
            return BaseResult.exception("系统异常");
        }

    }


    private int getpc(HttpServletRequest request){
        String value = request.getParameter("pageNum");
        if(value == null || value.trim().isEmpty()){
            return 1;
        }
        return Integer.parseInt(value);
    }

}
