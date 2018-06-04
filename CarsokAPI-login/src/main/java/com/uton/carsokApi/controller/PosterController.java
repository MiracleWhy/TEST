package com.uton.carsokApi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.CarsokCarNews;
import com.uton.carsokApi.model.GleefulReport;
import com.uton.carsokApi.model.Poster;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.PosterService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PosterController {
    private final static Logger logger = Logger.getLogger(PosterController.class);

    @Autowired
    PosterService posterService;

    @Resource
    CacheService cacheService;


    /**
     * 更新日检人员表
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = {"/getAllPoster"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    BaseResult getAllPoster(HttpServletRequest request) {
        BaseResult result = BaseResult.success();
        try {
            String sort = request.getParameter("sort");
            String curPage = request.getParameter("curPage");
            String pageSize = request.getParameter("pageSize");

            Map<String,Object> resultMap = new HashedMap();

            PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize));
            Page<Poster> page = posterService.selectAllByPage(sort);
            Acount acount = cacheService.getAcountInfoFromCache(request);

            resultMap.put("posterInfo",page.toPageInfo());
            resultMap.put("userInfo",posterService.getUserInfoByAccount(acount.getId()));

            result.setData(resultMap);
        } catch (Exception e) {
            return BaseResult.exception(e.getMessage());
        }
        return result;
    }

    /**
     * 1.爆款文章，将头条当天内容放在爆款文章的下面，同步数据，每天更新一条
     * 2.热点海报，在下面放入最新更新的3条海报，只有图片
     * 3.车行喜报，放入该车行在这一天最早更新的一篇喜报
     */
    @RequestMapping(value = {"/getMarketingInfo"}, method = {RequestMethod.POST})
    @ResponseBody
    public BaseResult getMarketingInfo(HttpServletRequest request){
        Acount account = cacheService.getAcountInfoFromCache(request);
        BaseResult result = BaseResult.success();
        Map data = new HashMap();
        Map param = new HashMap();
        param.put("start",Integer.valueOf(0));
        param.put("end",Integer.valueOf(1));
        // 爆款文章
        CarsokCarNews carNews = posterService.getNewCarNewsLimit(param);
        // 热点海报
        List<Poster> poster = posterService.getPosterByDate();
        // 车行喜报
        GleefulReport gleefulReport = posterService.getNewGleefulReport(account.getId());
        data.put("poster",poster);
        data.put("carNews",carNews);
        data.put("gleefulReport",gleefulReport);
        data.put("userInfo",posterService.getUserInfoByAccount(account.getId()));
        result.setData(data);
        return result;
    }
}
