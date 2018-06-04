package com.uton.carsokApi.constants.enums;

/**
 * Created by WANGYJ on 2017/11/8.
 */
public enum  RoleEnums {
    salemanager(1, "经理管理"),
    saleconsultant(2, "销售顾问"),
    customerservice(3,"客服服务");
    private Integer rolecode;
    private String roledesc;
    RoleEnums(Integer rolecode, String roledesc){
        this.rolecode = rolecode;
        this.roledesc = roledesc;
    }
    public Integer getRolecode(){
        return rolecode;
    }
    public String getRoledesc(){
        return roledesc;
    }
}
