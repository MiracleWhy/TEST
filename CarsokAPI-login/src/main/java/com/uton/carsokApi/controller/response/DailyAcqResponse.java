package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.model.AcqCarMsg;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by SEELE on 2017/6/20.
 */
public class DailyAcqResponse {
    int acqCount;
    BigDecimal acqAmount;
    List<AcqCarMsg> list;

    public int getAcqCount() {
        return acqCount;
    }

    public void setAcqCount(int acqCount) {
        this.acqCount = acqCount;
    }

    public BigDecimal getAcqAmount() {
        return acqAmount;
    }

    public void setAcqAmount(BigDecimal acqAmount) {
        this.acqAmount = acqAmount;
    }

    public List<AcqCarMsg> getList() {
        return list;
    }

    public void setList(List<AcqCarMsg> list) {
        this.list = list;
    }
}
