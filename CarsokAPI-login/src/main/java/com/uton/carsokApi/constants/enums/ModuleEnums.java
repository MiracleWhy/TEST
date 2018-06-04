package com.uton.carsokApi.constants.enums;

/**
 * Created by WANGYJ on 2017/11/13.
 */
public enum  ModuleEnums {
    retaincustomer("保有客户"),
    potentialcustomer("潜在客户"),
            ;
    private String module;
    ModuleEnums(String module){
        this.module = module;
    }
    public String getModule(){
        return module;
    }
}
