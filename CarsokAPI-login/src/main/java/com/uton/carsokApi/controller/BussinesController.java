package com.uton.carsokApi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.model.Bussines;
import com.uton.carsokApi.service.BussinesService;
import com.uton.carsokApi.service.ICarsokPmsForfigureService;
import com.uton.carsokApi.service.UploadService;
import com.uton.carsokApi.util.Base64Util;
import com.uton.carsokApi.util.UploadUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xin_mz on 2017/6/28 0013.
 * 市场拓展管理
 */
@Controller
@RequestMapping("/bussinesPage")
public class BussinesController {

    private final static Logger logger = Logger.getLogger(BussinesController.class);
    @Autowired
    ICarsokPmsForfigureService iCarsokPmsForfigureService;
    @Autowired
    BussinesService bussinesService;

    @Autowired
    UploadService uploadService;


    @Value("${pic.host}")
    private String pichost;
    //进入市场拓展管理页面

    //临时存储地址
    @Value("${temporary.dir}")
    private String temporary_dir;

    @RequestMapping(value = { "/goBusiness" }, method = { RequestMethod.GET })
    public String businessDevelopment(HttpServletRequest request,String mobile){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
        request.setAttribute("basePath", basePath);
        request.setAttribute("mobile",mobile);
        System.out.println("------------------------------------------------------进入页面-----------------------------------------------------");
        return "/Bussines";
    }

    //获取 市场拓展管理数据
    @RequestMapping( "/businessDevelopments" )
    @ResponseBody
    public Map upload (HttpServletRequest request){
        String  mobile= request.getParameter("mobile");
        System.out.println("------------------------------------------------------"+mobile+"-----------------------------------------------------");
        Map<String, Object> map = new HashMap();
        List<Integer> listId = bussinesService.selectId(mobile);
        Map maptr = bussinesService.getCount(listId.get(0), listId.get(1));
        map.put("maptr",maptr);
        return map;
    }

    /**
     * 市场页面重构方法
     * @Arthur zhangyugong
     * @param request
     * @return
     */
    @RequestMapping(value = { "/queryBusinessList" }, method = { RequestMethod.POST })
    public  @ResponseBody Map queryBusinessList(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        String times = request.getParameter("times");
        String month = "";
        String selects = "";
        int pr = 10;
        int pc = getpc(request);
        if(pc == 0){
            pc = 1;
        }
        if("0".equals(times)){// 搜索
            month = request.getParameter("month");
            selects = request.getParameter("selects");
        }else if("4".equals(times)){// 选择月份
            month = request.getParameter("month");
            selects = request.getParameter("selects");
        }else {// 本日,本周,本月

        }
        Pagebean<Bussines> pb = bussinesService.queryBusinessList(pr,pc,mobile,times,month,selects);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("record",pb);
        map.put("mobile",mobile);
        map.put("flage","1");
        return map;
    }

    //新增拜访信息
    @RequestMapping( "/seeInsert" )
    public  @ResponseBody BaseResult seeInsert(HttpServletRequest request, @RequestBody Bussines vo) {

        try {
            BaseResult result = bussinesService.seeInsert(vo,request);
            return result;
        } catch (Exception e) {
            logger.error("seeInsert:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    private int getpc(HttpServletRequest request){
        String value = request.getParameter("pc");
        if(value == null || value.trim().isEmpty()){
            return 1;
        }
        return Integer.parseInt(value);
    }

    //修改
    @RequestMapping("/bussinesUpdate")
    public @ResponseBody BaseResult bussinesUpdate(HttpServletRequest request,@RequestBody Bussines vo){
        try{
            System.out.println("------------------------------------------------------aaaaaaaaaaaaaaa-----------------------------------------------------");
            BaseResult result =  bussinesService.updateBussines(vo,request);
            return result;
        }catch (Exception e) {
            logger.error("bussinesUpdate:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    //查询
    @RequestMapping("/getBussine")
    public @ResponseBody BaseResult getBussine(HttpServletRequest request, @RequestBody Bussines vo) throws Exception {

        Bussines bussines = bussinesService.findBussinesById(vo.getId(),request);

        BaseResult baseResult = BaseResult.success();
//        if(null != bussines.getRevisit()) {
//            BussinesDTO bussinesDTO = new BussinesDTO();
//            //将bussines实体中的内容放到bussinesDTO
//            BeanUtils.copyProperties(bussines, bussinesDTO);
//            Date revist = bussines.getRevisit();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String dateNowStr = sdf.format(revist);
//            System.out.println("格式化后的日期：" + dateNowStr);
//            bussinesDTO.setRevisit(dateNowStr);
//            baseResult.setData(bussinesDTO);
//        }else {
            baseResult.setData(bussines);
//        }

        return baseResult;
    }


    //查看图片
    @RequestMapping("/Pictures")
    public @ResponseBody Map Pictures(HttpServletRequest request) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        UploadUtil u = new UploadUtil();
        String ID= request.getParameter("id");
        String shopboorPicture = bussinesService.findBussinesPictureById(ID);
        if(null == shopboorPicture){
            map.put("msg","您没有图片");
            map.put("flage","1");
        }else {
            //数据库中之前有base64的图片,如果是base64,则转换成七牛云地址
            if(shopboorPicture.length()>100){
                shopboorPicture = Code(request,shopboorPicture);
                //将转换后的base64码存在库中
                bussinesService.updatePicture(shopboorPicture,ID);
            }
            map.put("flage","2");
        }
        String picture = pichost + shopboorPicture;
        map.put("pictures",picture);
        return map;
    }


    @RequestMapping("/pic")
    public String pic(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        String picture = request.getParameter("pic");
        request.setAttribute("pic",picture);
        System.out.println("------------------------------------------------------进入页面-----------------------------------------------------");
        return "/pic";
    }

    /**
     * 新增跟进信息method
     * @param request
     * @param vo 跟进信息
     * @return
     */
    @RequestMapping(value = { "/addFollowupInfo" }, method = { RequestMethod.POST })
    public @ResponseBody BaseResult addFollowupInfo(HttpServletRequest request, @RequestBody FollowupInfoModel vo){
        BaseResult baseResult = bussinesService.addFollowupInfo(request, vo);
        return baseResult;
    }

    /**
     * 查询跟进信息method
     * @param request
     * @param vo 查询条件
     * @return
     */
    @RequestMapping(value = { "/gotoFollowupInfoPage" }, method = { RequestMethod.POST })
    public @ResponseBody BaseResult gotoFollowupInfoPage(HttpServletRequest request, @RequestBody FollowupInfoModel vo){
        BaseResult baseResult = bussinesService.getFollowupInfoList(request, vo);
        return baseResult;
    }

    public  String Code(HttpServletRequest request, String picture) throws IOException {
        String name = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
        String path = request.getSession().getServletContext().getRealPath(temporary_dir);
        Base64Util.GenerateImage(picture,path+"/"+name);
        File file = new File(path);
        if(!file.exists() && !file.isDirectory()){
            file.mkdir();
        }
        UploadUtil u = new UploadUtil();
        String code = u.upload(path+"/"+name);
        return code;
    }
    //市场人员离职转移
    @RequestMapping(value = { "/goBussinesMove" }, method = { RequestMethod.
            GET })
    public String businessDevelopmentMove(HttpServletRequest request,String
            mobile){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
        request.setAttribute("basePath", basePath);
        request.setAttribute("mobile",mobile);
        return null;
    }

    /**
     * 查詢市场人员离职转移列表
     * @param request
     * @param userLeaveName
     * @param mobile
     */
    @RequestMapping(value={"/searchserList"},method = { RequestMethod.GET})
    @ResponseBody
    public List<ChildNameId> marketWorkerMove(HttpServletRequest request,String userLeaveName,String mobile){
        String mobileZ=bussinesService.selectZ(mobile);

        List<ChildNameId> list = bussinesService.getBussWorker(userLeaveName,mobileZ);
        request.setAttribute("list",list);
        request.setAttribute("mobile",mobileZ);
        return list;
    }

    /**
     *跳转页面
     * @param request
     * @param mobile
     * @return
     */
    @RequestMapping(value={"/sear"},method = { RequestMethod.GET})
    public String sa(HttpServletRequest request,String mobile){
        request.setAttribute("mobile",mobile);

        return "/searchUserList";
    }
    /**
     * 修改
     * @param  name
     * @param request
     * @param  id
     * @return
     */
    @RequestMapping(value={"/moveWorker"})
    @ResponseBody
    public String marketMove(String id,HttpServletRequest request,String name,String beforeId){
        String mobile = request.getParameter("mobile");

        JSONArray jsonlist=JSONObject.parseArray(id);
        List<String> list = new ArrayList<>();
        for (int i=0;i<jsonlist.size();i++){
            list.add((String)jsonlist.get(i));
        }
        return  bussinesService.updateBussWorker(mobile,list,name);

    }

    /**
     *
     * @param id
     * @param mobile
     * @param request
     * @return
     */
    @RequestMapping(value={"/jumpchange"},method = { RequestMethod.GET})
    @ResponseBody
    public String marketMove(  String id,String mobile,HttpServletRequest request){
        request.setAttribute("mobile",mobile);
        request.setAttribute("id",id);
        return  id;
    }

    /**
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value={"/selectMarket"},method = { RequestMethod.GET})
    @ResponseBody
    public Pagebean<MarketMove> selectMarket( int id,HttpServletRequest request){
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }

        String ma="sa";
        Pagebean<MarketMove> list=bussinesService.getmarketMave(id,pr,pc);
        request.setAttribute("list",list);
        return  list;
    }

    /**
     *
     * @param request
     * @param response

     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/jump")
    public String marketMoveWork(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
       String mobile=request.getParameter("mobile");
        request.setAttribute("mobile",mobile);
        String id= request.getParameter("id");
        request.setAttribute("id",id);
        return "/changeList";
    }

    /**
     * 再次拜訪查詢頁
     * @param request
     * @return
     */
    @RequestMapping(value = { "/addNextVisit" }, method = { RequestMethod.GET })
    @ResponseBody
    public BaseResult selectaddNextVisit(HttpServletRequest request){
        BaseResult baseResult = BaseResult.success();
        String id = request.getParameter("id");
        List<SelectNextFull> listFull=bussinesService.selectaddNextVisit(id);
        baseResult.setData(listFull);
        return baseResult;
    }
    /**
     *  列表跳转页
     * @return
     */
    @RequestMapping(value = "/manager")
    public String manager() {
        return "/forFigure/forFigure";
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult findPage(HttpServletRequest request,int pages ,int sizes) {
        if(pages<=0){
            pages=1;
        }
       BaseResult baseResult = BaseResult.success();
        List<CarsokPmsForfigure> list=iCarsokPmsForfigureService.findPage(pages,sizes);
        baseResult.setData(list);
        return baseResult;

    }
    @RequestMapping(value = "/findPicture", method = RequestMethod.GET)
    @ResponseBody
    public String findPicture(HttpServletRequest request,int id) {


        return  iCarsokPmsForfigureService.findPicture(id);

    }
}
