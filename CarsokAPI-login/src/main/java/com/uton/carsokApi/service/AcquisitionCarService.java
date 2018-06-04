package com.uton.carsokApi.service;

import com.alibaba.fastjson.JSON;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.PublishTaskRequest;
import com.uton.carsokApi.controller.response.CollectIdentityResponse;
import com.uton.carsokApi.controller.response.PermissionResponse;
import com.uton.carsokApi.dao.AcquisitionCarMapper;
import com.uton.carsokApi.model.AcquisitionCar;
import com.uton.carsokApi.model.AcquisitionCarPricing;
import com.uton.carsokApi.model.AcquisitionConsult;
import com.uton.carsokApi.model.Pagebean;
import com.uton.carsokApi.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
@Service
public class AcquisitionCarService {

	@Autowired
	AcquisitionCarMapper acquisitionCarMapper;

    @Autowired
    ZbService zbService;

    @Autowired
    CustomerService customerService;

	public BaseResult selectAcquisition(AcquisitionCar acquisitionCar) {
        BaseResult baseResult = BaseResult.success();
        Map param = new HashMap();
        param.put("infoRecourse", acquisitionCar.getInfoRecourse());
        List<AcquisitionCar> result = acquisitionCarMapper.selectAcquisitionCar(param);
        baseResult.setData(result);
        return baseResult;
    }

    public BaseResult insertMsg(AcquisitionCar vo,HttpServletRequest req) throws Exception {
        int acar = 0;
        int aConsult = 0;
        int aPricing = 0;
        Integer acqcarId = 0;
        if (vo.getId() != null){
            acqcarId = vo.getId();
        }
        if(vo.getCarPic() != null && vo.getCarPic().size() > Integer.valueOf(0)){
            vo.setCarPicString(JSON.toJSONString(vo.getCarPic()));
        }
        //生成单号
        String Num= "TK"+StringUtil.getRandCode();
        if(vo.getId()!=null&&vo.getId()!=0){
            if(vo.getIsDeal()==1){
                vo.setCreateTime(new Date());
            }
            List<Integer> listId = customerService.selectId(vo.getMobile());
            int accountId = listId.get(0);
            int childId = listId.get(1);
            vo.setAccountId(accountId);
            vo.setChildId(childId);
            acar = acquisitionCarMapper.updateAcquisitionCar(vo);
            String childName = acquisitionCarMapper.findNameById(childId);
            vo.setInfoName(childName);
            vo.setTaskNum(Num);

            //与未办表中的Id做判断
            Integer wbId =  acquisitionCarMapper.compareWBId(vo.getId());
            if(null == wbId && vo.getIsDeal() == 1){
                    vo.setEnable(0);
                    acquisitionCarMapper.insertSxyWb(vo);
                    Integer taskid = vo.getId();
                    zbService.SXYTaskInfoTs(req,taskid);
            }


        }else {
            List<Integer> listId = customerService.selectId(vo.getMobile());
            int accountId = listId.get(0);
            int childId = listId.get(1);
            String scrMobile=null;
            if(childId==0){
                scrMobile = acquisitionCarMapper.findByAcountMobileId(accountId);
            }else {
                scrMobile = acquisitionCarMapper.findByChildMobileId(childId);
            }
            vo.setAccountId(accountId);
            vo.setInfoMobile(scrMobile);
            //根据子id查出姓名
            String childName = acquisitionCarMapper.findNameById(childId);
            vo.setInfoName(childName);
            vo.setChildId(childId);
            vo.setCarInspectionTime(new Date());
            if(vo.getIsDeal()==1){
                vo.setCreateTime(new Date());
            }
            //根据子账号id查出子账号姓名,然后set到model中
            acar = acquisitionCarMapper.insertMessage(vo);
            acqcarId = vo.getId();
            //生成单号
            vo.setTaskNum(Num);
            //判断是否成交
            if( vo.getIsDeal()==1){
                vo.setEnable(0);
                acquisitionCarMapper.insertSxyWb(vo);
                Integer sxyId = vo.getId();
                zbService.SXYTaskInfoTs(req,sxyId);
            }

        }
        List<AcquisitionConsult> consultList = vo.getConsultList();
        if (null != consultList && consultList.size() > 0) {
            acquisitionCarMapper.deleteConsult(acqcarId);
            for (AcquisitionConsult consult : consultList) {
                AcquisitionConsult consults = new AcquisitionConsult();
                if(consult.getId()!=null){
                    consults.setId(consult.getId());
                }
                consults.setAcquisitionId(acqcarId);
                consults.setConsultPrice(consult.getConsultPrice());
                consults.setCreateTime(new Date());
                aConsult = acquisitionCarMapper.insertConsult(consults);
            }
        }
        // 定价意见
        if (!StringUtil.isEmpty(vo.getPricingInfo())){
            AcquisitionCarPricing pricingInfo = new AcquisitionCarPricing();
            pricingInfo.setAcquisitionCarId(acqcarId);
            pricingInfo.setPricingInfo(vo.getPricingInfo());
            pricingInfo.setCreateTime(new Date());
            aPricing = acquisitionCarMapper.insertPricingInfo(pricingInfo);
        }

        //if (acar > 0 && aConsult>0) {
        if (acar > 0) {
            return BaseResult.success();
        } else {
            return BaseResult.fail("0001", "操作失败");
        }
    }

    public Pagebean<AcquisitionCar> queryAcquisitionList(int pr,int pc,String mobile,int isDeal,String xxly,String ycsj,String px){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<AcquisitionCar> pb = new Pagebean();
        List<Integer> listId = customerService.selectId(mobile);
        System.out.println("------------------------------------------------------"+listId.get(0)+"@@@@@@@"+listId.get(1)+"-----------------------------------------------------");
        int accountId = listId.get(0);
        int childId = listId.get(1);
        Map screenMap = getScreen(xxly,ycsj,px);
        String xxly1 = screenMap.get("xxly").toString();
        String ycsj1 = screenMap.get("ycsj").toString();
        String px1 = screenMap.get("px").toString();
        int pxs = 0;
        if(!StringUtil.isEmpty(px1)){
            pxs = Integer.parseInt(px1);
        }
        Number number = acquisitionCarMapper.selectCount(accountId,childId,isDeal,xxly1,ycsj1,pxs);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        List<AcquisitionCar> beanList = acquisitionCarMapper.selectAcquisitionMsg(p1,p2,accountId,childId,isDeal,xxly1,ycsj1,pxs);
        pb.setBeanlist(beanList);
        return pb;
    }
    public Pagebean<AcquisitionCar> selectById(int pr,int pc,String mobile,int otherId,int accountId,int childId){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<AcquisitionCar> pb = new Pagebean();
        Number number = acquisitionCarMapper.selectByIdCount(accountId,childId,otherId);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        List<AcquisitionCar> beanlist = acquisitionCarMapper.selectById(p1,p2,accountId,childId,otherId);
        pb.setBeanlist(beanlist);
        return pb;
    }

    public Map<String,Object> selectCounts(int accountId,int childId,int isDeal){
        Map<String,Object> map = new HashedMap();
        map.put("dealY",acquisitionCarMapper.selectdealY(accountId,childId,isDeal));
        map.put("dealN",acquisitionCarMapper.selectdealN(accountId,childId,isDeal));
        map.put("dealF",acquisitionCarMapper.selectdealF(accountId,childId,isDeal));
        return map;
    }

    public Pagebean<AcquisitionCar> selectScreenMsg(int pc,String xxly,String ycsj,String px,String mobile,int isDeal){
        int pr = 10;
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        List<Integer> listMobile = customerService.selectId(mobile);
        int accountId = listMobile.get(0);
        int childId = listMobile.get(1);
        Pagebean<AcquisitionCar> pb = new Pagebean<AcquisitionCar>();
        Number number;
        List<AcquisitionCar> list = new ArrayList<AcquisitionCar>();
        Map screenMap = getScreen(xxly,ycsj,px);
        String xxly1 = screenMap.get("xxly").toString();
        String ycsj1 = screenMap.get("ycsj").toString();
        String px1 = screenMap.get("px").toString();
        int pxs = 0;
        if(!StringUtil.isEmpty(px1)){
            pxs = Integer.parseInt(px1);
        }
        number = acquisitionCarMapper.selectScreenCount(accountId,childId,xxly1,ycsj1,pxs,isDeal);
        list = acquisitionCarMapper.ScreenMessageAll(p1,p2,accountId,childId,xxly1,ycsj1,pxs,isDeal);
        pb.setTr(number.intValue());
        pb.setBeanlist(list);
        pb.setPc(pc);
        pb.setPr(pr);
        return pb;
    }

    public Pagebean<AcquisitionCar> queryListBySearch(int pr,int pc,String selects,String mobile){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<AcquisitionCar> pb = new Pagebean<AcquisitionCar>();
        List<Integer> list = customerService.selectId(mobile);
        System.out.println("------------------------------------------------------"+list.get(0)+"@@@@@@@"+list.get(1)+"-----------------------------------------------------");
        int accountId = list.get(0);
        int childId = list.get(1);
        Number number = acquisitionCarMapper.selectBySearchKey(selects,accountId,childId);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        List<AcquisitionCar> beanlist = acquisitionCarMapper.queryBysearchkey(p1,p2,selects,accountId,childId);
        pb.setBeanlist(beanlist);
        return pb;
    }

    public BaseResult selectMsgById(String ids){
        BaseResult baseResult = BaseResult.success();
        if(StringUtil.isEmpty(ids)){
            return baseResult.fail("0001","您的信息可能有误");
        }else {
            int id = Integer.parseInt(ids);
            AcquisitionCar acquisitionCar = acquisitionCarMapper.selectMsgById(id);
            if(acquisitionCar.getCarPicString() != null && acquisitionCar.getCarPicString().length() > 0){
                acquisitionCar.setCarPic(JSON.parseArray(acquisitionCar.getCarPicString(), String.class));
            }
            List<AcquisitionConsult> acquisitionConsult = acquisitionCarMapper.selectConsultById(id);
            List<AcquisitionCarPricing> pricingList = acquisitionCarMapper.selectPricingInfo(id);
            acquisitionCar.setConsultList(acquisitionConsult);
            acquisitionCar.setPricingList(pricingList);
            Map<String,Object> map = new HashedMap();
            map.put("acquisitionCar",acquisitionCar);
            baseResult.setData(map);
            return baseResult;
        }
    }

    public Map<String,Object> getScreen(String xxly,String ycsj,String px){
        Map<String,Object> map = new HashedMap();
        int pxs = 0;
        int ycsjs = 0;
        if(xxly != null && ycsj != null && px != null){
            if("全部车源".equals(xxly)){
                xxly = "";
            }
            if("验车时间".equals(ycsj)){
                ycsj = "";
            }else if(ycsj.equals("本日")){
                ycsjs = 1;
            }else if(ycsj.equals("本周")){
                ycsjs = 2;
            }else if(ycsj.equals("本月")){
                ycsjs = 3;
            }
            if("默认排序".equals(px)){
                px = "";
            }else if(px.equals("意向价格最高")){
                pxs = 1;
            }else if(px.equals("意向价格最低")){
                pxs = 2;
            }
            map.put("xxly",xxly);
            map.put("ycsj",ycsjs);
            map.put("px",pxs);
        }else{
            map.put("xxly","");
            map.put("ycsj","");
            map.put("px","");
        }

        return map;
    }


    public String savePricingInfo(String id, String pricingInfo) {
        AcquisitionCarPricing acquisitionCarPricing = new AcquisitionCarPricing();
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String createtime = sdf.format(date);
        acquisitionCarPricing.setAcquisitionCarId(Integer.valueOf(id));
        acquisitionCarPricing.setPricingInfo(pricingInfo);
        acquisitionCarPricing.setCreateTime(date);
        acquisitionCarMapper.insertPricingInfo(acquisitionCarPricing);
        return createtime;
    }

    public Pagebean<AcquisitionCar> querySelectListNew(int pnum, int psize, String mobile, String type, String selects, String months, String xxly, String px, String cjzt) {
        int p1 = (pnum-1)*psize;
        int p2 = psize;
        Pagebean<AcquisitionCar> pb = new Pagebean<AcquisitionCar>();
        List<Integer> list = customerService.selectId(mobile);
        //System.out.println("------------------------------------------------------"+list.get(0)+"@@@@@@@"+list.get(1)+"-----------------------------------------------------");
        int accountId = list.get(0);
        int childId = list.get(1);
        Number number = acquisitionCarMapper.selectNumber(type, selects, months, xxly, px, cjzt, accountId, childId);
        pb.setTr(number.intValue());
        pb.setPc(pnum);
        pb.setPr(psize);
        List<CollectIdentityResponse> powerName = acquisitionCarMapper.selectSCSF(childId);
        int flage = 0;//如果子账号是收车主管,则flage=1. 否则为0
        for (int i = 0; i <powerName.size() ; i++) {
            if(powerName.get(i).getPowerName().equals("sczg")){
                flage = 1;
            }
        }
        List<AcquisitionCar> beanlist = acquisitionCarMapper.querySelectList(p1, p2, type, selects, months, xxly, px, cjzt, accountId, childId,flage);
        for (AcquisitionCar car : beanlist){
            List<AcquisitionConsult> acquisitionConsult = acquisitionCarMapper.selectConsultById(car.getId());
            car.setConsultList(acquisitionConsult);
        }
        pb.setBeanlist(beanlist);
        return pb;
    }

    public Object selectDWMCounts(int accountId, int childId) {
        Map<String,Object> map = new HashedMap();
        List<CollectIdentityResponse> powerName = acquisitionCarMapper.selectSCSF(childId);
        int flage = 0;//如果子账号是收车主管,则flage=1. 否则为0
        for (int i = 0; i <powerName.size() ; i++) {
        if(powerName.get(i).getPowerName().equals("sczg")){
            flage = 1;
        }
        }
        map.put("DayNum",acquisitionCarMapper.selectDay(accountId,childId,flage));
        map.put("WeekNum",acquisitionCarMapper.selectWeek(accountId,childId,flage));
        map.put("MonthNum",acquisitionCarMapper.selectMonth(accountId,childId,flage));
        return map;
    }
}
