package com.uton.carsokApi.model;

import java.io.Serializable;

/**
 * Created by SEELE on 2017/12/6.
 */
public class KeyValuePair implements Serializable{
    private String keyStr;
    private String val;

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
