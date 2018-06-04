package com.uton.carsokApi.constants.enums;

/**
 * Created by WANGYJ on 2017/11/8.
 */
public enum DataAuthorityEnums {
    totaldepart(1, "车行权限"),
    self(2, "员工权限"),
    ;
    private Integer authoritycode;
    private String authoritydesc;
    DataAuthorityEnums(Integer rolecode, String roledesc){
        this.authoritycode = rolecode;
        this.authoritydesc = roledesc;
    }
    public Integer getAuthoritycode(){
        return authoritycode;
    }
    public String getAuthoritydesc(){
        return authoritydesc;
    }
}
