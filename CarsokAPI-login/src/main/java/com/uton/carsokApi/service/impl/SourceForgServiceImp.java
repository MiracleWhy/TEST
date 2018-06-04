package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.dao.CarForgSaleMapper;
import com.uton.carsokApi.dao.PurposeForgMapper;
import com.uton.carsokApi.dao.SaasAuthorityMapper;
import com.uton.carsokApi.dao.SourceForgMapper;
import com.uton.carsokApi.model.*;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.SaasAuthorityService;
import com.uton.carsokApi.service.SourceForgService;
import com.uton.carsokApi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/11/13/013.
 */
@Service
public class SourceForgServiceImp implements SourceForgService {
    @Autowired
    SourceForgMapper sourceForgMapper;
    @Autowired
    CacheService cacheService;
    @Autowired
    SaasAuthorityMapper authorityMapper;
    @Autowired
    CarForgSaleMapper carForgSaleMapper;
    @Autowired
    PurposeForgMapper purposeForgMapper;
    @Autowired
    SaasAuthorityService saasAuthorityService;

    @Override
    public SourceForgSum salesForg(HttpServletRequest request, SalesForg salesForg) {
        //查询日期和星期的
        SourceForgSum sourceForgSum=new SourceForgSum();
        String date = salesForg.getDate();
        int type = salesForg.getType();
        if (salesForg.getType() == 3) {
            date = salesForg.getDate() + "-1";
        } else if (salesForg.getType() == 2) {
            Calendar c = Calendar.getInstance();
            String[] ast = salesForg.getDate().split(",");
            int week = Integer.parseInt(ast[1]);


            String ym = ast[0] + "-1";
            c.setTime(DateUtil.StringToDate(ym));
//DAY_OF_WEEK获取当前时间是一个星期的第几天，星期日是第一天  星期一是第二天，以此类推
            //Calendar.MONDAY判断是不是星期1
            int t = 1;
            while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                c.set(Calendar.DAY_OF_MONTH, t++);//设置这个月的星期1 为几号
            }
            Date firstMondayDate = c.getTime();
            date = new SimpleDateFormat("YYYY-MM-dd").format(firstMondayDate);
            date = DateUtil.addDay(date, (week - 1) * 7);
        }
        //控制权限
        Acount acount = cacheService.getAcountInfoFromCache(request);
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
                if (stId.get(i).equals("yyglz")) {
                    boo = true;
                }
            }
        } else {
            boo = true;
        }
        //有权限的
        if (boo == true) {
//            List<String> permissionName = new ArrayList<>();
//            permissionName.add("byyxgw");
//            permissionName.add("byjlgl");
//            permissionName.add("qkjlgl");
//            permissionName.add("qkyxgw");
//            List<ChildAccount> childs = saasAuthorityService.queryTargetChild(acount, permissionName,date,type);

            List<ChildAccount> childs=carForgSaleMapper.selectChildId(acount.getAccount());
            List<Integer> childt=new ArrayList<>();
            for(int childm=0;childm<childs.size();childm++){
                childt.add(childs.get(childm).getId());
            }
            childt.add(0);
            Double t=0.0;
            Double o=0.0;
            Integer count =sourceForgMapper.countAll(acount.getId(),date,type,childt);
//           List<String> source= sourceForgMapper.source();
            String[] str={"朋友介绍","朋友圈","58同城","FM调频广播","室外广告牌","同行引荐","文章引导","自到店","转介绍","其他","二手车之家","网络进线","微信录入"};
            List<SourceForgOne> list=new ArrayList<>();
            for(int i=0;i<str.length;i++){
                SourceForgOne sourceForgOne=new SourceForgOne();
                Double n=sourceForgMapper.accountCount( acount.getId(),str[i],date, type,childt);
                Double m=sourceForgMapper.accountChains(acount.getId(),str[i],date, type,childt);
                t=t+n;
                o=o+m;
                if((m==0)&&(n==0)){
                    sourceForgOne.setChain(n);
                    sourceForgOne.setUpOrDown("up");
                }else if(m==0&&n!=0){
                    sourceForgOne.setChain(100.0);
                    sourceForgOne.setUpOrDown("up");
                }
                else if(n-m>=0){
                    sourceForgOne.setChain(new BigDecimal(n-m).divide(new BigDecimal(m),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());

                    sourceForgOne.setUpOrDown("up");

                }else if(n-m<0){
                    sourceForgOne.setChain(new BigDecimal(m-n).divide(new BigDecimal(m),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                    sourceForgOne.setUpOrDown("down");
                }

                sourceForgOne.setPeople((int)Math.round(n));

                if(count==0){
                    sourceForgOne.setRate(0.0);
                }else{
                    sourceForgOne.setRate(new BigDecimal(n).divide(new BigDecimal(count),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                }
                sourceForgOne.setSource(str[i]);
                list.add(sourceForgOne);
                if(i+1==str.length){
                    SourceForgOne sourceForgOnes=new SourceForgOne();
                    sourceForgOnes.setSource("未填写");
                    Double p=sourceForgMapper.nullCount( acount.getId(),date, type,childt);
                    Double e=sourceForgMapper.nullChains(acount.getId(),date, type,childt);
                    if((p==0)&&(e==0)){
                        sourceForgOnes.setChain(0.0);
                        sourceForgOnes.setUpOrDown("up");
                    }
                    else if(e==0&&p!=0){
                        sourceForgOnes.setChain(100.0);
                        sourceForgOnes.setUpOrDown("up");
                    }else if(p-e>=0){
                        sourceForgOnes.setChain(new BigDecimal(p-e).divide(new BigDecimal(e),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                        sourceForgOnes.setUpOrDown("up");
                    }else {
                        sourceForgOnes.setChain(new BigDecimal(e-p).divide(new BigDecimal(e),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                        sourceForgOnes.setUpOrDown("down");
                    }

                    sourceForgOnes.setPeople((int)Math.round(p));
                    if(count==0){
                        sourceForgOnes.setRate(0.0);
                    }else{
                        sourceForgOnes.setRate(new BigDecimal(p).divide(new BigDecimal(count),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                    }
                    list.add(sourceForgOnes);
                    t=t+p;
                    o=o+e;
                }
            }
            if(t==0&&o==0){
                sourceForgSum.setChainSum(0.0);
                sourceForgSum.setUpAndDown("up");
            }else if(o==0&&t!=0){
                sourceForgSum.setChainSum(100.0);
                sourceForgSum.setUpAndDown("up");
            }else if(t-o>=0){
                sourceForgSum.setChainSum(new BigDecimal(t-o).divide(new BigDecimal(o),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                sourceForgSum.setUpAndDown("up");
            }else{
                sourceForgSum.setChainSum(new BigDecimal(o-t).divide(new BigDecimal(o),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                sourceForgSum.setUpAndDown("down");
            }
            sourceForgSum.setRateSum(100.0);
            sourceForgSum.setPeopleSum(count);
            //排序list
            Collections.sort(list, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    SourceForgOne stu1=(SourceForgOne)o1;
                    SourceForgOne stu2=(SourceForgOne)o2;
                    if(stu1.getRate()<stu2.getRate()){
                        return 1;
                    }else if(stu1.getRate()==stu2.getRate()){
                        return 0;
                    }else{
                        return -1;
                    }
                }
            });
            sourceForgSum.setRows(list);
            return sourceForgSum;
        }//没有权限的
        else {

            if(saasAuthorityService.qxName(acount.getSubPhone()).contains("yyyg")){
            Integer count=sourceForgMapper.countAllOne(carForgSaleMapper.selectOneChild(acount.getSubPhone()),date,type);
//            List<String> source= sourceForgMapper.source();
            String[] str={"朋友介绍","朋友圈","58同城","FM调频广播","室外广告牌","同行引荐","文章引导","自到店","转介绍","其他","二手车之家","网络进线","微信录入"};
            List<SourceForgOne> list=new ArrayList<>();
            for(int i=0;i<str.length;i++){
                SourceForgOne sourceForgOne=new SourceForgOne();
                Double n=sourceForgMapper.oneCount( carForgSaleMapper.selectOneChild(acount.getSubPhone()),str[i],date, type);
                Double m=sourceForgMapper.chain(carForgSaleMapper.selectOneChild(acount.getSubPhone()),str[i],date, type);
                if(m==0&&n==0){
                    sourceForgOne.setChain(0.0);
                    sourceForgOne.setUpOrDown("up");
                }else if(m==0&&n!=0){
                    sourceForgOne.setChain(100.0);
                    sourceForgOne.setUpOrDown("up");
                }
                else if(n-m>=0){
                    sourceForgOne.setChain(new BigDecimal(n-m).divide(new BigDecimal(m),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                    sourceForgOne.setUpOrDown("up");

                }
                else {
                    sourceForgOne.setChain(new BigDecimal(m-n).divide(new BigDecimal(m),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                    sourceForgOne.setUpOrDown("down");
                }
                sourceForgOne.setPeople((int)Math.round(n));
                if(count==0){
                    sourceForgOne.setRate(0.0);
                }else{
                    sourceForgOne.setRate((new BigDecimal(n).divide(new BigDecimal(count),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue()));
                }
                sourceForgOne.setSource(str[i]);
                list.add(sourceForgOne);
                if(i+1==str.length){
                    SourceForgOne sourceForgOnes=new SourceForgOne();
                    sourceForgOnes.setSource("未填写");
                    List<Integer> childId=new ArrayList<>();
                    childId.add(purposeForgMapper.childIds(acount.getSubPhone()));
                    Double p=sourceForgMapper.nullCount( acount.getId(),date, type,childId);
                    Double e=sourceForgMapper.nullChains(acount.getId(),date, type,childId);
                    if((p==0)&&(e==0)){
                        sourceForgOnes.setChain(0.0);
                        sourceForgOnes.setUpOrDown("up");
                    }
                    else if(e==0&&p!=0){
                        sourceForgOnes.setChain(100.0);
                        sourceForgOnes.setUpOrDown("up");
                    }else if(p-e>=0){
                        sourceForgOnes.setChain(new BigDecimal(p-e).divide(new BigDecimal(e),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                        sourceForgOnes.setUpOrDown("up");
                    }else {
                        sourceForgOnes.setChain(new BigDecimal(e-p).divide(new BigDecimal(e),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                        sourceForgOnes.setUpOrDown("down");
                    }

                    sourceForgOnes.setPeople((int)Math.round(p));
                    if(count==0){
                        sourceForgOnes.setRate(0.0);
                    }else{
                        sourceForgOnes.setRate(new BigDecimal(p).divide(new BigDecimal(count),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).abs().doubleValue());
                    }
                    list.add(sourceForgOnes);
                }
            }
            sourceForgSum.setChainSum(list.get(0).getChain());
            sourceForgSum.setPeopleSum(count);
            sourceForgSum.setRateSum(list.get(0).getRate());
            //排序list
            Collections.sort(list, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    SourceForgOne stu1=(SourceForgOne)o1;
                    SourceForgOne stu2=(SourceForgOne)o2;
                    if(stu1.getRate()<stu2.getRate()){
                        return 1;
                    }else if(stu1.getRate()==stu2.getRate()){
                        return 0;
                    }else{
                        return -1;
                    }
                }
            });
            sourceForgSum.setRows(list);
            return sourceForgSum;}else{
                return null;
            }
        }
    }
}
