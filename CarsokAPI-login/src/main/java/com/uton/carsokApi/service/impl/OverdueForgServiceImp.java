package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.dao.CarForgSaleMapper;
import com.uton.carsokApi.dao.OverdueForgMapper;
import com.uton.carsokApi.dao.SaasAuthorityMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.*;
import com.uton.carsokApi.service.core.task.FilterSQLParam;
import com.uton.carsokApi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/12/20/020.
 */
@Service
public class OverdueForgServiceImp implements OverdueForgService {
    @Autowired
    OverdueForgMapper overdueForgMapper;
    @Autowired
    CacheService cacheService;
    @Autowired
    SaasAuthorityMapper authorityMapper;
    @Autowired
    ICarsokCustomerService iCarsokCustomerService;
    @Autowired
    CarForgSaleMapper carForgSaleMapper;
    @Autowired
    ITaskFacade iTaskFacade;
    @Autowired
    SaasAuthorityService saasAuthorityService;

    public OverdueForgServiceImp() {
    }

    @Override
    public OverdueForgModel overdue(Acount acount) {

        acount.getId();
        boolean boo = false;
        Integer child = 0;
        if (acount.getSubPhone() != null) {
            //查询所以子账户id
            child = carForgSaleMapper.selectOneChild(acount.getSubPhone());
            //排序所有子账户
            List stId = authorityMapper.getAuthorityByUserId(child);
            //循环权限
            for (int i = 0; i < stId.size(); i++) {
                if (stId.get(i).equals("yyglz"))  {
                    boo = true;
                }
            }
        } else {
            boo = true;
        }
        //有权限的

        //保有預期
        String byYq="module='retaincustomer' and task_status='delay'and json_extract(extra_fields,'$.nextIsDone')= 0 ";
        if (boo == true) {
            int countDay=0;
            int qk=0;
            int by=0;

//            List<String> permissionName= new ArrayList<>();
//            permissionName.add("byyxgw");
//            permissionName.add("byjlgl");
//            permissionName.add("qkjlgl");
//            permissionName.add("qkyxgw");
//            List<ChildAccount> list=saasAuthorityService.queryTargetChild(acount,permissionName,null,null);
            List<ChildAccount> list=carForgSaleMapper.selectChildId(acount.getAccount());

            List<OverdueForgModelOne> oneRows=new ArrayList<>();
            OverdueForgModel overdueForgModel=new OverdueForgModel();
            FilterSQLParam filterSQLParam=new FilterSQLParam();
            int count=0;


            for (int i = 0; i < list.size(); i++) {

                OverdueForgModelOne overdueForgModelOne = new OverdueForgModelOne();
                overdueForgModelOne.setChildId(list.get(i).getId());
                overdueForgModelOne.setNameOne(list.get(i).getChildAccountName());
                filterSQLParam.setSqlTemplate(byYq+"and json_extract(extra_fields,'$.accountId')="+acount.getId()+" and json_extract(extra_fields,'$.childId')="+list.get(i).getId());
                //本日逾期
                overdueForgModelOne.setDayOverdueOne(overdueForgMapper.qianke(String.valueOf(acount.getId()),String.valueOf(list.get(i).getId()))+
                        iTaskFacade.queryTaskBySQLFilter(0,0,filterSQLParam).getList().size());
                //潜客逾期
                overdueForgModelOne.setPotentialOverdueOne(overdueForgMapper.qianke(String.valueOf(acount.getId()),String.valueOf(list.get(i).getId())));
                //保有逾期
                overdueForgModelOne.setRetainOverdueOne( iTaskFacade.queryTaskBySQLFilter(0,0,filterSQLParam).getList().size());
                oneRows.add(overdueForgModelOne);
                countDay=countDay+overdueForgModelOne.getDayOverdueOne();
                qk=qk+overdueForgModelOne.getPotentialOverdueOne();
                by=by+overdueForgModelOne.getRetainOverdueOne();
                if(overdueForgModelOne.getDayOverdueOne()>0){
                    count=count+1;
                }
//                if (i + 1 == list.size()) {
//
//                }
            }
            OverdueForgModelOne overdueForgModelOne1 = new OverdueForgModelOne();
            //主账户
            overdueForgModelOne1.setNameOne("主账户");
            //todo
            //本日逾期
            filterSQLParam.setSqlTemplate(byYq+"and json_extract(extra_fields,'$.accountId')="+acount.getId()+" and json_extract(extra_fields,'$.childId')=0");
            overdueForgModelOne1.setDayOverdueOne(
                    overdueForgMapper.qianke(String.valueOf(acount.getId()),String.valueOf(0))+
                            //本日逾期
                            iTaskFacade.queryTaskBySQLFilter(0,0,filterSQLParam).getList().size());
            //保有逾期

            overdueForgModelOne1.setRetainOverdueOne( iTaskFacade.queryTaskBySQLFilter(0,0,filterSQLParam).getList().size());
            //潜客逾期
            overdueForgModelOne1.setPotentialOverdueOne(overdueForgMapper.qianke(String.valueOf(acount.getId()),String.valueOf(0)));
            oneRows.add(overdueForgModelOne1);

//            if(overdueForgModelOne1.getDayOverdueOne()>0)
            if(overdueForgModelOne1.getDayOverdueOne()>0){
                count=count+1;
            }
            countDay=countDay+overdueForgModelOne1.getDayOverdueOne();
            qk=qk+overdueForgModelOne1.getPotentialOverdueOne();
            by=by+overdueForgModelOne1.getRetainOverdueOne();
            overdueForgModel.setCount(count);
            overdueForgModel.setName("合计");
            //排序list
            Collections.sort(oneRows, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    OverdueForgModelOne stu1=(OverdueForgModelOne)o1;
                    OverdueForgModelOne stu2=(OverdueForgModelOne)o2;
                    if(stu1.getDayOverdueOne()<stu2.getDayOverdueOne()){
                        return 1;
                    }else if(stu1.getDayOverdueOne()==stu2.getDayOverdueOne()){
                        return 0;
                    }else{
                        return -1;
                    }
                }
            });
            overdueForgModel.setRows(oneRows);
            filterSQLParam.setSqlTemplate(byYq+"and json_extract(extra_fields,'$.accountId')="+acount.getId());
            overdueForgModel.setDayOverdue(countDay);
            overdueForgModel.setPotentialOverdue(qk);
            filterSQLParam.setSqlTemplate(byYq+"and json_extract(extra_fields,'$.accountId')="+acount.getId());
            overdueForgModel.setRetainOverdue(by);
            return overdueForgModel;
        } else {   if(saasAuthorityService.qxName(acount.getSubPhone()).contains("yg")){
            OverdueForgModel overdueForgModel=new OverdueForgModel();
            FilterSQLParam filterSQLParam=new FilterSQLParam();
            Integer id = carForgSaleMapper.selectOneChild(acount.getSubPhone());
            String name =overdueForgMapper.selectOneChildName(acount.getSubPhone());
            List<OverdueForgModelOne> oneRows=new ArrayList<>();

            OverdueForgModelOne overdueForgModelOne=new OverdueForgModelOne();
            //本日逾期
            filterSQLParam.setSqlTemplate(byYq+"and json_extract(extra_fields,'$.accountId')="+acount.getId()+" and json_extract(extra_fields,'$.childId')="+id);
            overdueForgModelOne.setDayOverdueOne(iTaskFacade.queryTaskBySQLFilter(0,0,filterSQLParam).getList().size()+overdueForgMapper.qianke(String.valueOf(acount.getId()),String.valueOf(id)));
            //保有逾期
            overdueForgModelOne.setRetainOverdueOne(iTaskFacade.queryTaskBySQLFilter(0,0,filterSQLParam).getList().size());
            //潜客逾期
            overdueForgModelOne.setPotentialOverdueOne(overdueForgMapper.qianke(String.valueOf(acount.getId()),String.valueOf(id)));
            overdueForgModel.setName(name);
            oneRows.add(overdueForgModelOne);
            overdueForgModel.setName("合计");
            overdueForgModel.setRetainOverdue(overdueForgModelOne.getRetainOverdueOne());
            overdueForgModel.setPotentialOverdue(overdueForgModelOne.getPotentialOverdueOne());
            overdueForgModel.setDayOverdue(overdueForgModelOne.getDayOverdueOne());
            return overdueForgModel;}else{
            return null;
        }
        }
    }
}
