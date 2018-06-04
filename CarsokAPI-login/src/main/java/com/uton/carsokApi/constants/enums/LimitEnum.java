package com.uton.carsokApi.constants.enums;

/**
 * Created by zhangdi on 2018/1/19.
 * desc:
 */
public enum LimitEnum {

    CARSOURCE("fc"),
    FINDCAR("fbxc");


    private String code;

    LimitEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
