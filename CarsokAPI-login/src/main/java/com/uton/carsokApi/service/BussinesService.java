package com.uton.carsokApi.service;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.dao.AcountMapper;
import com.uton.carsokApi.dao.BusinesMapper;
import com.uton.carsokApi.dao.ChildAccountMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.util.Base64Util;
import com.uton.carsokApi.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.util.*;

import com.uton.carsokApi.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * Created by xin_mz on 2017/6/28
 * 市场拓展管理
 */
@Service
public class BussinesService {

    @Autowired
    BusinesMapper businesMapper;
    @Autowired
    AcountMapper acountMapper;
    @Autowired
    ChildAccountMapper childAccountMapper;
    @Resource
    CacheService cacheService;

    //临时存储地址
    @Value("${temporary.dir}")
    private String temporary_dir;

//查询数据
    public Pagebean<Bussines> selectBussines(int pr,int pc, int times,String mobile,String month){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<Bussines> pb = new Pagebean<Bussines>();
        List<Integer> listId = selectId(mobile);
        System.out.println("------------------------------------------------------"+listId.get(0)+"@@@@@@@"+listId.get(1)+"-----------------------------------------------------");
        int accountId = listId.get(0);
        int childId = listId.get(1);
        pb.setPc(pc);
        pb.setPr(pr);
        List<Bussines> beanlist= businesMapper.selectBussinesMsg(p1,p2,times,accountId,childId);
        pb.setBeanlist(beanlist);
        return pb;
    }

    //根据月份查询数据
    public Pagebean<Bussines> selectBussinesByMonth(int pr,int pc, int times,String mobile,String month){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<Bussines> pb = new Pagebean<Bussines>();
        List<Integer> listId = selectId(mobile);
        System.out.println("------------------------------------------------------"+listId.get(0)+"@@@@@@@"+listId.get(1)+"-----------------------------------------------------");
        int accountId = listId.get(0);
        int childId = listId.get(1);
        pb.setPc(pc);
        pb.setPr(pr);
        String months = month + "-00";
        List<Bussines> beanlist= businesMapper.selectBussinesMsgByMonth(p1,p2,times,accountId,childId,months);
        pb.setBeanlist(beanlist);
        return pb;
    }


//查询条数
    public Map<String,Object> getCount(int accountId,int childId){

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("daytr",businesMapper.selectDayCount(accountId,childId));
        map.put("weektr",businesMapper.selectWeekCount(accountId,childId));
        map.put("monthtr",businesMapper.selectMonthCount(accountId,childId));
        map.put("selectAll",businesMapper.selectAll(accountId,childId));
        return map;
    }


//月份查询条数
    public Map queryCountByMonth(String month,String mobile){
        Map<String,Object> map = new HashMap<String,Object>();
        Number number;
        List<Integer> listId = selectId(mobile);
        int accountId = listId.get(0);
        int childId = listId.get(1);
        String months = month + "-00";
        number = businesMapper.selectCountByMonth(accountId,childId,months);
        map.put("monthtr",number);
        return map;
    }

    public  Pagebean<Bussines> queryBusinesListBysearchkey(int pr, int pc, String selects, String month, String mobile){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<Bussines> pb = new Pagebean<Bussines>();
        List<Integer> list = selectId(mobile);
        System.out.println("------------------------------------------------------"+list.get(0)+"@@@@@@@"+list.get(1)+"-----------------------------------------------------");
        int accountId = list.get(0);
        int childId = list.get(1);
        Number number = businesMapper.selectBySearchKey(selects,accountId,childId);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);

        List<Bussines> beanlist = businesMapper.queryBusinesListBysearchkey(p1,p2,selects,month,accountId,childId);
        pb.setBeanlist(beanlist);
        return pb;
    }

    //添加拜访信息
    public BaseResult seeInsert(Bussines bussines,HttpServletRequest request) throws IOException {


        Acount acount = cacheService.getAcountInfoFromCache(request);
        String mobile=acount.getAccount();
        Integer childId = 0;
        String childIdPhone = acount.getSubPhone();
        ChildAccount child = new ChildAccount();
        String code = null;
        if(!StringUtil.isEmpty(childIdPhone)){
            ChildAccount childd = new ChildAccount();
            childd.setChildAccountMobile(acount.getSubPhone());
            child = childAccountMapper.selectByModel(childd);
            childId = childAccountMapper.selectIdBymobile(childIdPhone);
        }
        Bussines bu = new Bussines();
        if(bussines==null){
            return BaseResult.fail("0001","市场拓展类参数为空");
        }
        //将图片上传到七牛云
        //临时存储地址
        String name = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
        String path = request.getSession().getServletContext().getRealPath(temporary_dir);
        String pictures = bussines.getShopboorPicture();
        if(null != pictures){
        Base64Util.GenerateImage(pictures,path+"/"+name);
        File file = new File(path);
        if(!file.exists() && !file.isDirectory()){
            file.mkdir();
        }
        UploadUtil u = new UploadUtil();
        code = u.upload(path+"/"+name);
        }
        List<Integer> list = selectId(mobile);
        bu.setAcountId(list.get(0));
        bu.setChilAcountId(childId);
        bu.setRevisit(bussines.getRevisit());
        bu.setCarIndustry(bussines.getCarIndustry());
        bu.setPersonInCharge(bussines.getPersonInCharge());
        bu.setAddress(bussines.getAddress());
        bu.setTelephone(bussines.getTelephone());
        bu.setFirstVisittime(bussines.getFirstVisittime());
        bu.setVehicleScale(bussines.getVehicleScale());
        bu.setInterestLevel(bussines.getInterestLevel());
        bu.setEmpnumber(bussines.getEmpnumber());
        bu.setPurchaseBudget(bussines.getPurchaseBudget());
        bu.setSaas(bussines.getSaas());
        bu.setShopboorPicture(code);
        bu.setIsdealok(bussines.getIsdealok());
        bu.setRemarks(bussines.getRemarks());
        bu.setCreateTime(new Date());
        bu.setUpdateTime(new Date());
        bu.setIsAddChild(bussines.getIsAddChild());
        bu.setIsCommercialMsg(bussines.getIsCommercialMsg());
        bu.setIsdealok(bussines.getIsdealok());
        bu.setIsPos(bussines.getIsPos());
        bu.setIsTps(bussines.getIsTps());
        bu.setCarsokApiFunction(bussines.getCarsokApiFunction());
        bu.setRegion(bussines.getRegion());
        if(StringUtil.isEmpty(acount.getSubPhone())){// 主账号
            bu.setCreater(acount.getNick());
        }else{// 子账号
            bu.setCreater(child.getChildAccountName());
        }
        businesMapper.insertCustomer(bu);
        return BaseResult.success();
    }


    public BaseResult updateBussines(@RequestBody Bussines bussines, HttpServletRequest request) throws Exception{
        if(bussines.getShopboorPicture()!=null&&bussines.getShopboorPicture().length()<=100){
            bussines.setShopboorPicture(null);
        }
        Acount acount = cacheService.getAcountInfoFromCache(request);
        String mobile = acount.getAccount();
        String code = null;
        String name = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
        String path = request.getSession().getServletContext().getRealPath(temporary_dir);
        String pictures = bussines.getShopboorPicture();
        if(null != pictures){
            Base64Util.GenerateImage(pictures,path+"/"+name);
            File file = new File(path);
            if(!file.exists() && !file.isDirectory()){
                file.mkdir();
            }
            UploadUtil u = new UploadUtil();
            code = u.upload(path+"/"+name);
        }
        bussines.setShopboorPicture(code);
        if (bussines == null) {
            return BaseResult.fail("0001", "更新参数为空");
        }
        int up = businesMapper.updateBussines(bussines);
        if (up < 0) {
            BaseResult.fail("0002", "更新失败");
        }
        //        //临时存储地址

             return BaseResult.success();
    }
    public Bussines findBussinesById(int id,HttpServletRequest request)throws Exception{
        Bussines buModel=businesMapper.selecByid(id);
System.out.print("----------------------------------------------------------------"+buModel.getShopboorPicture()+"--------------------------------------------------------------------------------------");
       String picture=null;
        if(buModel.getShopboorPicture()==null){
            buModel.setShopboorPicture(null);
        }else{
            picture="http://pic.utonw.com/"+buModel.getShopboorPicture();
            buModel.setShopboorPicture(picture);}
//        }

        return buModel!=null? buModel:null;
    }

    public String findBussinesPictureById(String id){
        String buModel=businesMapper.selecByPicture(id);
        return buModel;
    }

    public String updatePicture(String code,String id){
        businesMapper.updatePicture(code,id);
        return null;
    }

    //根据电话号查找id
    public List<Integer> selectId(String mobile){
        int accountId = 0;
        int childId = 0;
        Acount record = new Acount();
        record.setAccount(mobile);
        Acount acount = acountMapper.selectByModel(record);
        if(acount==null){
            List<ChildAccount> list = childAccountMapper.selectBymobile(mobile);
            Acount recordc = new Acount();
            recordc.setAccount(list.get(0).getAccountPhone());
            Acount acountc = acountMapper.selectByModel(recordc);
            accountId = acountc.getId();
            childId = list.get(0).getId();
            System.out.print("-----------------------accountId="+acountc.getId()+"***childId="+list.get(0).getId()+"----------------------");
        }else{
            accountId = acount.getId();
            System.out.print("-----------------------accountId="+acount.getId()+"----------------------");
        }
        List<Integer> list = new ArrayList<Integer>();
        list.add(accountId);
        list.add(childId);
        return list;
    }

    public Pagebean<Bussines> queryBusinessList(int pr, int pc, String mobile, String times, String month, String selects){
        int p1 = (pr*pc)-pr;
        int p2 = pr;
        Pagebean<Bussines> pb = new Pagebean<Bussines>();
        List<Integer> listId = selectId(mobile);
        System.out.println("------------------------------------------------------"+listId.get(0)+"@@@@@@@"+listId.get(1)+"-----------------------------------------------------");
        int accountId = listId.get(0);
        int childId = listId.get(1);
        if(!StringUtil.isEmpty(month)){
            month = month + "-00";
        }
        Number number = businesMapper.selectCount(times,accountId,childId,month,selects);
        pb.setTr(number.intValue());
        pb.setPc(pc);
        pb.setPr(pr);
        List<Bussines> beanlist= businesMapper.selectBusinessList(p1,p2,times,accountId,childId,month,selects);
        pb.setBeanlist(beanlist);
        return pb;
    }

    /**
     * 新增跟进信息
     * @param request
     * @param followupInfo
     * @return
     */
    public BaseResult addFollowupInfo(HttpServletRequest request, FollowupInfoModel followupInfo) {
        BaseResult baseResult = BaseResult.success();
        Acount acount = cacheService.getAcountInfoFromCache(request);
        followupInfo.setAccount(acount.getAccount());
        if (!StringUtil.isEmpty(acount.getSubPhone())){// 说明是子账号
            followupInfo.setChildAccount(acount.getSubPhone());
        }
        int flag = businesMapper.addFollowupInfo(followupInfo);
        if (flag != 1){
            baseResult.setRetCode("0001");
            baseResult.setRetMsg("跟进信息保存失败!");
        }
        return baseResult;
    }

    /**
     * 查询跟进信息
     * @param request
     * @param vo
     * @return
     */
    public BaseResult getFollowupInfoList(HttpServletRequest request, FollowupInfoModel vo) {
        BaseResult baseResult = BaseResult.success();
        if (!StringUtil.isEmpty(vo.getFollowupType()) && vo.getBussinesId() != null){
            List<FollowupInfoModel> list = businesMapper.getFollowupInfoList(vo);
            baseResult.setData(list);
        }else {
            baseResult.setRetCode("0002");
            baseResult.setRetMsg("查询参数缺失!");
        }
        return baseResult;
    }

    /**
     * 市场人员离职转移
     * @param userLeaveName
     * @return
     */
    public List<ChildNameId> getBussWorker( String userLeaveName,String mobile) {
        List<ChildNameId> worker=businesMapper.getBussWorker(userLeaveName,mobile);
        for (int i=0;i<worker.size();i++){
            if(worker.get(i).getName()==null){
                worker.remove(i);
            }
        }
        return worker;
    }
    public String updateBussWorker( String mobile,List<String> id,String name) {

        List<Integer> childIds=businesMapper.selectUpdateUserMove(name,mobile);

        if(childIds.size()>0){
            Integer childId=childIds.get(0);
            businesMapper.updateUserMove(childId,id);
            return "true";
        }
     else { return  "false";}
    }

    /**
     *
     * @param id
     * @param pr
     * @param pc
     * @return
     */
    public Pagebean<MarketMove> getmarketMave(int id,int pr,int pc) {
        int p1 = (pr*pc)-pr;
        int p2 = pr;

        Pagebean<MarketMove> pb = new Pagebean<MarketMove>();
        Integer count=businesMapper.getmarketMaveCount(id);
        pb.setPr(pr);
        pb.setTr(count);
        List<MarketMove> list= businesMapper.getmarketMave(id,p1,p2);

        pb.setBeanlist(list);
       return  pb;
    }
    //查詢主賬戶電話
    public String selectZ(String mobile){
        if(businesMapper.selectZ(mobile)!=null){
            return businesMapper.selectZ(mobile);
        }else if(businesMapper.selectZs(mobile)!=null){
            List<String> list=businesMapper.selectZs(mobile);
            return list.get(0);
        }
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<SelectNextFull> selectaddNextVisit(String id){
        List<SelectNextFull> listFull=new ArrayList<SelectNextFull>();
        List<String> followupTypes=businesMapper.selectFowType();
        for(int i=0;i<followupTypes.size();i++){
            SelectNextFull selectNextFull=new SelectNextFull();
            List<AddNextVisit> list=new ArrayList<AddNextVisit>();
            if(followupTypes.get(i).equals("FUService")){
                list=businesMapper.selectaddNextVisit(id,"FUService");
//                for(AddNextVisit t:list){
//                    if(list.get(i).getUpdateTime()==null){
//                        list.get(i).setUpdateTime(list.get(i).getCreateTime());
//                    }
//                }
            selectNextFull.setFollowupType("客服");
                selectNextFull.setList(list);
            }
            else if(followupTypes.get(i).equals("FUConsultant")){
                list=businesMapper.selectaddNextVisit(id,"FUConsultant");
                selectNextFull.setFollowupType("咨询顾问");
                selectNextFull.setList(list);
            }
            else if(followupTypes.get(i).equals("FUTrainer")){
                list=businesMapper.selectaddNextVisit(id,"FUTrainer");
                selectNextFull.setFollowupType("培训师");
                selectNextFull.setList(list);
            }
            else if(followupTypes.get(i).equals("FUDeputy")){
                list=businesMapper.selectaddNextVisit(id,"FUDeputy");
                selectNextFull.setFollowupType("副总监");
                selectNextFull.setList(list);
            }
            else if(followupTypes.get(i).equals("FUDirector")){
                list=businesMapper.selectaddNextVisit(id,"FUDirector");
                selectNextFull.setFollowupType("总监");
                selectNextFull.setList(list);
            }
            listFull.add(selectNextFull);
        }
        return listFull;
    }
}
