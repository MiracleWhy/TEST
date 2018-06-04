package com.uton.carsokApi.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.AppVersion;
import com.uton.carsokApi.pagination.PaginationInfo;
import com.uton.carsokApi.service.AppVersionService;


@Controller
@RequestMapping("/rest")
public class AppVersionRestController {
  private final static Logger logger = Logger.getLogger(AppVersionRestController.class);
  
  @Autowired
  private AppVersionService appVersionService;
  
  @Resource
  private RedisTemplate redisTemplate;
  
  
  /**
   * @Description:版本控制首页列表
   * @param:VersionRestController
   * @return:BaseResult
   * @Authoe:zhangff01
   * @date:2016年4月21日
   */
  @RequestMapping(value = {"/test"}, method = {RequestMethod.GET,RequestMethod.POST})
  public @ResponseBody BaseResult test(){
      logger.info("VersionRestController getAppVersionList begin.");
      return BaseResult.success();
  }
  
  
  /**
   * @Description:版本控制首页列表
   * @param:VersionRestController
   * @return:BaseResult
   * @Authoe:zhangff01
   * @date:2016年4月21日
   */
  @RequestMapping(value = {"/getAppVersionByPage"}, method = {RequestMethod.GET,RequestMethod.POST})
  public @ResponseBody BaseResult getAppVersionByPage(HttpServletRequest request, @RequestBody  AppVersion vo, PaginationInfo paginationInfo){
      logger.info("VersionRestController getAppVersionList begin.");
      try{
        logger.info("getAppVersionListForPage begin.");
        vo.setPaginationInfo(paginationInfo);
        List<AppVersion> rows= appVersionService.selectByPage(vo);
        logger.info("getAppVersionListForPage end.");
        logger.info("VersionRestController getAppVersionList end.");
        return BaseResult.success(rows,paginationInfo);
      }catch(Exception e){
        logger.error("VersionRestController getAppVersionList error:", e);
        logger.info("VersionRestController getAppVersionList end.");
        return BaseResult.exception(e.getMessage());
      }
  }
  /**
   * @Description:版本管理--编辑
   * @param:VersionRestController
   * @return:BaseResult
   * @Authoe:zhangff01
   * @date:2016年4月21日
   */
  @RequestMapping(value = {"/editVersion"}, method = {RequestMethod.GET,RequestMethod.POST})
  public @ResponseBody BaseResult EditVersion(HttpServletRequest request,@RequestBody AppVersion example){
    logger.info("VersionRestController EditVersion begin.");
    try{
      logger.info("getAppVersionInfo begin.");
      String appVersionKey="appVersion:"+example.getId();
      ValueOperations<String, AppVersion> valueOperations=redisTemplate.opsForValue();
      AppVersion vo =valueOperations.get(appVersionKey);
      if(vo == null){
    	  vo =appVersionService.selectByPrimaryKey(example.getId());
    	  valueOperations.set(appVersionKey, vo);
      }
      
      BaseResult baseResult = BaseResult.success();      
      baseResult.setData(vo);
    	      
      logger.info("getAppVersionInfo end.");
      logger.info("VersionRestController EditVersion end.");
      
      return baseResult;
    }catch(Exception e){
      logger.error("VersionRestController EditVersion error:",e);
      logger.info("VersionRestController EditVersion end.");
      return BaseResult.exception(e.getMessage());
    }
  }
  /**
   * @Description:版本管理--编辑保存
   * @param:VersionRestController
   * @return:BaseResult
   * @Authoe:zhangff01
   * @date:2016年4月21日
   */
  @RequestMapping(value = {"/updateAppVserion"}, method = {RequestMethod.GET,RequestMethod.POST})
  public @ResponseBody BaseResult updateAppVserion(HttpServletRequest request,@RequestBody AppVersion example){
    logger.info("VersionRestController updateAppVserion begin.");
    try{
      logger.info("updateAppVersion begin.");
      appVersionService.updateByPrimaryKeySelective(example);
      BaseResult baseResult = BaseResult.success();   
      logger.info("updateAppVersion end.");
      logger.info("VersionRestController updateAppVserion end.");
      return baseResult;
    }catch(Exception e){
      logger.error("VersionRestController updateAppVserion error:" , e);
      logger.info("VersionRestController updateAppVserion end.");
      return BaseResult.exception(e.getMessage());
    }
  }
  /**
   * @Description:版本管理--添加
   * @param:VersionRestController
   * @return:BaseResult
   * @Authoe:zhangff01
   * @date:2016年4月21日
   */
  @RequestMapping(value = {"/addAppVserion"}, method = {RequestMethod.GET,RequestMethod.POST})
  public @ResponseBody BaseResult addAppVserion(HttpServletRequest request,@RequestBody AppVersion example){
    logger.info("VersionRestController addAppVserion begin.");
    try{
      logger.info("checkVersionExist begin.");
      appVersionService.insertSelective(example);
      BaseResult baseResult = BaseResult.success();  
      logger.info("checkVersionExist end.");
      return baseResult;
      
    }catch(Exception e){
      logger.error("VersionRestController addAppVserion error:" ,e);
      logger.info("VersionRestController addAppVserion end.");
      return BaseResult.exception(e.getMessage());
    }
  }
  /**
   * @Description:版本管理--删除
   * @param:VersionRestController
   * @return:BaseResult
   * @Authoe:zhangff01
   * @date:2016年4月22日
   */
  @RequestMapping(value = {"/DelVersions"}, method = {RequestMethod.GET,RequestMethod.POST})
  public @ResponseBody BaseResult DelVersions(HttpServletRequest request,@RequestBody AppVersion example){
    logger.info("VersionRestController DelVersions begin.");
    try{
      logger.info("delAppVersionById begin.");
      appVersionService.deleteByPrimaryKey(example.getId());
      BaseResult baseResult = BaseResult.success(); 
      logger.info("delAppVersionById end.");
      logger.info("VersionRestController DelVersions end.");
      return baseResult;
    }catch(Exception e){
      logger.error("VersionRestController DelVersions error:" , e);
      logger.info("VersionRestController DelVersions end.");
      return BaseResult.exception(e.getMessage());
    }
  }
  
}
