package com.uton.carsokApi.constants.enums;

/**
 * Created by WANGYJ on 2017/11/8.
 */
public enum CustomerLevelEnum {
    N("24小时内回访"),
    H("3天内购买"),
    B("15天内购买"),
    C("30天内购买"),
    F0("战败待确认"),
    F("战败"),
    D("已成交"),
    ;
    private String customerlevel;
    CustomerLevelEnum(String customerlevel){
        this.customerlevel = customerlevel;
    }
    public String getCustomerlevel(){
        return customerlevel;
    }
}
