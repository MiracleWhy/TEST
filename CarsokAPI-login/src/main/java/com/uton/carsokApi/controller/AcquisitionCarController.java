package com.uton.carsokApi.controller;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.constants.ErrorCode;
import com.uton.carsokApi.model.AcquisitionCar;
import com.uton.carsokApi.model.Pagebean;
import com.uton.carsokApi.service.AcquisitionCarService;
import com.uton.carsokApi.service.CustomerService;
import com.uton.carsokApi.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
@Controller
@RequestMapping("/AcquisitionCar")
public class AcquisitionCarController {

    private final static Logger logger = Logger.getLogger(AcquisitionCarController.class);

	@Resource
	AcquisitionCarService acquisitionCarService;

    @Autowired
    CustomerService customerService;

    @RequestMapping( value = { "/openPage" }, method = { RequestMethod.GET } )
    public String openPage(HttpServletRequest request,String mobile,String otherId){
        request.setAttribute("mobile",mobile);
        if(StringUtil.isEmpty(otherId)){
            otherId = "0";
        }
        request.setAttribute("otherId",otherId);
        return "/recoveryVehicle";
    }

    /**
     * 收车页面重构方法
     * @Arthur zhangyugong
     * @param request
     * @return
     */
    @RequestMapping(value = { "/queryVehicleList" }, method = { RequestMethod.POST })
    public  @ResponseBody Map queryVehicleList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        return map;
    }

    @RequestMapping( value = { "/selectById" }, method = { RequestMethod.POST } )
    @ResponseBody
    public Map selectById(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        String otherIds = request.getParameter("otherId");
        String isDeals = request.getParameter("status");

        int isDeal = Integer.parseInt(isDeals);
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }
        int otherId = 0;
        if(!StringUtil.isEmpty(otherIds)){
            otherId = Integer.parseInt(otherIds);
        }
        List<Integer> listId = customerService.selectId(mobile);
        int accountId = listId.get(0);
        int childId = listId.get(1);
        Pagebean<AcquisitionCar> pb = acquisitionCarService.selectById(pr,pc,mobile,otherId,accountId,childId);
        Map<String,Object> map = new HashMap();
        map.put("count", acquisitionCarService.selectCounts(accountId,childId,isDeal));
        map.put("record",pb);
        return map;
    }

	/**
	 * Filter
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectAcquisition",method = RequestMethod.POST)
	@ResponseBody
	public BaseResult selectAcquisition(HttpServletRequest request, @RequestBody @Valid AcquisitionCar acquisitionCar, BindingResult result){
		try {
			if (result.hasErrors()) {
				return BaseResult.fail(ErrorCode.ParaCheckErrorRetCode, ErrorCode.ParaCheckErrorRetInfo, result.getFieldError().getDefaultMessage());
			}
			return acquisitionCarService.selectAcquisition(acquisitionCar);
		}catch(Exception e){
			logger.error("新增意见信息 insertAdvice e: " + e);
			return BaseResult.exception(e.getMessage());
		}
	}

    /**
     * 添加数据
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping(value = "/insertAcquisitionCar",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult insertAcquisitionCar(HttpServletRequest request,@RequestBody AcquisitionCar vo){
        try{
            BaseResult baseResult = acquisitionCarService.insertMsg(vo,request);
            return baseResult;
        }catch (Exception e) {
            logger.error("insertAcquisitionCar:", e);
            return BaseResult.exception(e.getMessage());
        }
    }


    /**
     * 列表
     */
    @RequestMapping(value = "/selectList",method = RequestMethod.POST)
    @ResponseBody
    public Map selectList(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        String statuses = request.getParameter("status");
        String xxly = request.getParameter("xxly");
        String ycsj = request.getParameter("ycsj");
        String px = request.getParameter("px");
        int status = Integer.parseInt(statuses);
        Map map = pageResult(request,mobile,status,xxly,ycsj,px);
        return map;
    }

    @RequestMapping(value = "/selectScreen",method = RequestMethod.POST)
    @ResponseBody
    public Map selectScreen(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        String xxly = request.getParameter("xxly");
        String ycsj = request.getParameter("ycsj");
        String px = request.getParameter("px");
        String deal = request.getParameter("status");
        int isDeal = 0;

        if(StringUtil.isEmpty(deal)){
            isDeal = 0;
        }else{
            isDeal = Integer.parseInt(deal);
        }
        int pc = getpc(request);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("record",acquisitionCarService.selectScreenMsg(pc,xxly,ycsj,px,mobile,isDeal));
        return map;
    }

    @RequestMapping(value = "/selectListBySearch",method = RequestMethod.POST)
    @ResponseBody
    public Map selectListBySearch(HttpServletRequest request){
        String selects = request.getParameter("selects");
        String mobile = request.getParameter("mobile");
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }
        Pagebean<AcquisitionCar> pb = acquisitionCarService.queryListBySearch(pr,pc,selects,mobile);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("record",pb);
        return map;
    }
    //修改时查詢
    @RequestMapping(value = "/selectMsg",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult selectMsg(HttpServletRequest request){
        try{
            String id = request.getParameter("id");
            BaseResult baseResult = acquisitionCarService.selectMsgById(id);
            return baseResult;
        }catch (Exception e) {
            logger.error("selectMsgById error:", e);
            return BaseResult.exception(e.getMessage());
        }
    }

    public Map pageResult(HttpServletRequest request,String mobile,int isDeal,String xxly,String ycsj,String px){
        int pr = 10;
        int pc = getpc(request);
        if(pc==0){
            pc=1;
        }
        Pagebean<AcquisitionCar> pb = acquisitionCarService.queryAcquisitionList(pr,pc,mobile,isDeal,xxly,ycsj,px);
        List<Integer> listId = customerService.selectId(mobile);
        int accountId = listId.get(0);
        int childId = listId.get(1);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("record",pb);
        map.put("count",acquisitionCarService.selectCounts(accountId,childId,isDeal));
        return map;
    }

    private int getpc(HttpServletRequest request){
        String value = request.getParameter("pc");
        if(value == null || value.trim().isEmpty()){
            return 1;
        }
        return Integer.parseInt(value);
    }

    @RequestMapping(value = { "/followUpShare" }, method = { RequestMethod.GET })
    public String followUpShare(HttpServletRequest request,Model model){
        return "/followUpShare";
    }

    @RequestMapping(value = { "/followUpShareSave" }, method = { RequestMethod.GET })
    public @ResponseBody BaseResult followUpShareSave(HttpServletRequest request){
        BaseResult baseResult = BaseResult.success();
        try{
            String id = request.getParameter("carId");
            String pricingInfo = request.getParameter("pricingInfo");
            String createTime = acquisitionCarService.savePricingInfo(id,pricingInfo);
            if (StringUtil.isEmpty(createTime)){
                baseResult = BaseResult.fail("0002", "提交失败");
            }else {
                baseResult.setData(createTime);
                baseResult.setRetMsg("提交成功");
            }
        }catch (Exception e) {
            logger.error("selectMsgById error:", e);
            baseResult = BaseResult.fail("0001", "提交出错");
        }
        return baseResult;
    }

    @RequestMapping(value = "/selectListNew",method = RequestMethod.POST)
    @ResponseBody
    public Map selectListNew(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            String type = request.getParameter("type");
            String mobile = request.getParameter("mobile");
            String selects = "";// 搜索内容
            String months = ""; // 选择月份
            String xxly = "";   // 信息来源
            String px = "";     // 排序
            String cjzt = "";   // 成交状态
            // 根据type处理传递的参数
            switch (type){
                case "0" : // 搜索
                    selects = request.getParameter("selects");
                    months = request.getParameter("months");
                    break;
                case "1" : // 本日新增
                    //break;
                case "2" : // 本周新增
                    //break;
                case "3" : // 本月新增
                    xxly = request.getParameter("xxly");
                    px = request.getParameter("px");
                    cjzt = request.getParameter("cjzt");
                    break;
                case "4" : // 选择月份
                    months = request.getParameter("months");
                    xxly = request.getParameter("xxly");
                    px = request.getParameter("px");
                    cjzt = request.getParameter("cjzt");
                    break;
            }
            if (!StringUtil.isEmpty(months)){
                months = months + "-00";
            }
            // 分页
            int psize = 10;
            int pnum = getpc(request);
            if(pnum == 0){
                pnum= 1;// 从第 1 页开始
            }
            // 查询列表
            Pagebean<AcquisitionCar> pb = acquisitionCarService.querySelectListNew(pnum, psize, mobile, type, selects, months, xxly, px, cjzt);
            map.put("record", pb);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return map;
    }

    @RequestMapping( value = { "/selectDWMCounts" }, method = { RequestMethod.POST } )
    @ResponseBody
    public Map selectDWMCounts(HttpServletRequest request){
        Map<String,Object> map = new HashMap();
        try {
            String mobile = request.getParameter("mobile");
            List<Integer> listId = customerService.selectId(mobile);
            int accountId = listId.get(0);
            int childId = listId.get(1);
            map.put("counts", acquisitionCarService.selectDWMCounts(accountId,childId));
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return map;
    }
}
