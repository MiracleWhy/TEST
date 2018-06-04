package com.uton.carsokApi.service.impl;

import com.uton.carsokApi.dao.CarForgSaleMapper;
import com.uton.carsokApi.dao.PurposeForgMapper;
import com.uton.carsokApi.dao.SaasAuthorityMapper;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.PurposeReturn;
import com.uton.carsokApi.model.PurposeReturnSum;
import com.uton.carsokApi.service.CacheService;
import com.uton.carsokApi.service.PurposeForgService;
import com.uton.carsokApi.service.SaasAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/10/010.
 */
@Service
public class PurposeForgServiceImp implements PurposeForgService {

    @Autowired
    CacheService cacheService;

    @Autowired
    SaasAuthorityMapper authorityMapper;

    @Autowired
    PurposeForgMapper purposeForgMapper;

    @Autowired
    CarForgSaleMapper carForgSaleMapper;
    @Autowired
    SaasAuthorityService saasAuthorityService;

    @Override
    public PurposeReturnSum selectCustomerCount(HttpServletRequest request) {
        PurposeReturnSum purposeReturnSum=new PurposeReturnSum();

        List lic=new ArrayList();
        boolean boo = false;
        Acount acount = cacheService.getAcountInfoFromCache(request);

        Integer childOne=0;
        //权限控制
        if(acount.getSubPhone() != null){
            //查询所以子账户id
            childOne=carForgSaleMapper.selectOneChild(acount.getSubPhone());
            //排序所有子账户

            List<String> stId= authorityMapper.getAuthorityByUserId(childOne);
            //循环权限
            for(int i=0;i<stId.size();i++){
                if(stId.get(i).equals("yyglz")){
                    boo = true;
                }
            }

        } else {
            boo = true;
        }
        if (boo == true) {

//            List<String> permissionName= new ArrayList<>();
//            permissionName.add("byyxgw");
//            permissionName.add("byjlgl");
//            permissionName.add("qkjlgl");
//            permissionName.add("qkyxgw");
//            List<ChildAccount> child=saasAuthorityService.queryTargetChild(acount,permissionName,null,null);

//            List<ChildAccount> child=saasAuthorityService.queryTargetChild(acount,permissionName,null,null);
            ChildAccount childAccount=new ChildAccount();
             List<ChildAccount> child= carForgSaleMapper.selectChildId(acount.getAccount());
            Integer aa=0;

            Integer bb=0;

            Integer cc=0;

            Integer ff=0;

            Integer fofo=0;

            Integer hh=0;

            Integer nn=0;

            for(int ch=0;ch<child.size();ch++){
                PurposeReturn purposeReturn=new PurposeReturn();
                Integer a=purposeForgMapper.selectCustomerCountA(child.get(ch).getId());
                Integer b=purposeForgMapper.selectCustomerCountB(child.get(ch).getId());
                Integer c=purposeForgMapper.selectCustomerCountC(child.get(ch).getId());
                Integer f=purposeForgMapper.selectCustomerCountF(child.get(ch).getId());
                Integer fo=purposeForgMapper.selectCustomerCountFO(child.get(ch).getId());
                Integer h=purposeForgMapper.selectCustomerCountH(child.get(ch).getId());
                Integer n=purposeForgMapper.selectCustomerCountN(child.get(ch).getId());
                purposeReturn.setACount(a);
                purposeReturn.setBCount(b);
                purposeReturn.setCCount(c);
                purposeReturn.setFCount(f);
                purposeReturn.setFOCount(fo);
                purposeReturn.setHCount(h);
                purposeReturn.setNCount(n);
                purposeReturn.setConsultant(purposeForgMapper.selectChildName(child.get(ch).getId()));
                aa=aa+a;
                bb=bb+b;
                cc=cc+c;
                ff=ff+f;
                fofo=fofo+fo;
                hh=hh+h;
                nn=nn+n;
                lic.add(purposeReturn);
            }
            //主账户自己注册时候
            PurposeReturn purposeReturn=new PurposeReturn();
            Integer a=purposeForgMapper.selectAccountCustomerCountA(acount.getId());
            Integer b=purposeForgMapper.selectAccountCustomerCountB(acount.getId());
            Integer c=purposeForgMapper.selectAccountCustomerCountC(acount.getId());
            Integer f=purposeForgMapper.selectAccountCustomerCountF(acount.getId());
            Integer fo=purposeForgMapper.selectAccountCustomerCountFO(acount.getId());
            Integer h=purposeForgMapper.selectAccountCustomerCountH(acount.getId());
            Integer n=purposeForgMapper.selectAccountCustomerCountN(acount.getId());
            purposeReturn.setACount(a);
            purposeReturn.setBCount(b);
            purposeReturn.setCCount(c);
            purposeReturn.setFCount(f);
            purposeReturn.setFOCount(fo);
            purposeReturn.setHCount(h);
            purposeReturn.setNCount(n);
            purposeReturn.setConsultant("主账户");
            purposeReturnSum.setASum(aa+a);
            purposeReturnSum.setBSum(bb+b);
            purposeReturnSum.setCSum(cc+c);
            purposeReturnSum.setFOSum(fofo+fo);
            purposeReturnSum.setFSum(ff+f);
            purposeReturnSum.setHSum(hh+h);
            purposeReturnSum.setNSum(nn+n);
            lic.add(purposeReturn);
            purposeReturnSum.setRows(lic);
            return purposeReturnSum;
        } else {
            if(saasAuthorityService.qxName(acount.getSubPhone()).contains("yyyg")){
            PurposeReturn purposeReturn=new PurposeReturn();
            int a=purposeForgMapper.selectCustomerCountA(purposeForgMapper.childIds( acount.getSubPhone()));
            int b=purposeForgMapper.selectCustomerCountB(purposeForgMapper.childIds( acount.getSubPhone()));
            int c=purposeForgMapper.selectCustomerCountC(purposeForgMapper.childIds( acount.getSubPhone()));
            int f=purposeForgMapper.selectCustomerCountF(purposeForgMapper.childIds( acount.getSubPhone()));
            int fo=purposeForgMapper.selectCustomerCountFO(purposeForgMapper.childIds( acount.getSubPhone()));
            int h=purposeForgMapper.selectCustomerCountH(purposeForgMapper.childIds( acount.getSubPhone()));
            int n=purposeForgMapper.selectCustomerCountN(purposeForgMapper.childIds( acount.getSubPhone()));
            purposeReturn.setACount(a);
            purposeReturn.setBCount(b);
            purposeReturn.setCCount(c);
            purposeReturn.setFCount(f);
            purposeReturn.setFOCount(fo);
            purposeReturn.setHCount(h);
            purposeReturn.setNCount(n);
            purposeReturn.setConsultant(purposeForgMapper.selectChildName(purposeForgMapper.childIds( acount.getSubPhone())));
            lic.add(purposeReturn);
            purposeReturnSum.setRows(lic);
            purposeReturnSum.setNSum(purposeReturn.getNCount());
            purposeReturnSum.setHSum(purposeReturn.getHCount());
            purposeReturnSum.setFSum(purposeReturn.getFCount());
            purposeReturnSum.setFOSum(purposeReturn.getFOCount());
            purposeReturnSum.setCSum(purposeReturn.getCCount());
            purposeReturnSum.setBSum(purposeReturn.getBCount());
            purposeReturnSum.setASum(purposeReturn.getACount());
            return purposeReturnSum;}else{
                return null;
            }
        }

    }
}
