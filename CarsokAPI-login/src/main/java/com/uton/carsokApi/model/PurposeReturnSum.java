package com.uton.carsokApi.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/10/010.
 */
public class PurposeReturnSum {
    private Integer NSum;
    private Integer HSum;
    private Integer ASum;
    private Integer BSum;
    private Integer CSum;
    private Integer FOSum;
    private Integer FSum;
    private List<PurposeReturn> rows;

    public Integer getNSum() {
        return NSum;
    }

    public void setNSum(Integer NSum) {
        this.NSum = NSum;
    }

    public Integer getHSum() {
        return HSum;
    }

    public void setHSum(Integer HSum) {
        this.HSum = HSum;
    }

    public Integer getASum() {
        return ASum;
    }

    public void setASum(Integer ASum) {
        this.ASum = ASum;
    }

    public Integer getBSum() {
        return BSum;
    }

    public void setBSum(Integer BSum) {
        this.BSum = BSum;
    }

    public Integer getCSum() {
        return CSum;
    }

    public void setCSum(Integer CSum) {
        this.CSum = CSum;
    }

    public Integer getFOSum() {
        return FOSum;
    }

    public void setFOSum(Integer FOSum) {
        this.FOSum = FOSum;
    }

    public Integer getFSum() {
        return FSum;
    }

    public void setFSum(Integer FSum) {
        this.FSum = FSum;
    }

    public List<PurposeReturn> getRows() {
        return rows;
    }

    public void setRows(List<PurposeReturn> rows) {
        this.rows = rows;
    }
}
