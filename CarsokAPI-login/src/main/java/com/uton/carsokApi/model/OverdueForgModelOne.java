package com.uton.carsokApi.model;

/**
 * Created by Administrator on 2017/12/20/020.
 */
public class OverdueForgModelOne {
    //單人的
    private Integer childId;
    private String nameOne;
    private Integer dayOverdueOne;
    private Integer retainOverdueOne;
    private Integer potentialOverdueOne;

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getNameOne() {
        return nameOne;
    }

    public void setNameOne(String nameOne) {
        this.nameOne = nameOne;
    }

    public Integer getDayOverdueOne() {
        return dayOverdueOne;
    }

    public void setDayOverdueOne(Integer dayOverdueOne) {
        this.dayOverdueOne = dayOverdueOne;
    }

    public Integer getRetainOverdueOne() {
        return retainOverdueOne;
    }

    public void setRetainOverdueOne(Integer retainOverdueOne) {
        this.retainOverdueOne = retainOverdueOne;
    }

    public Integer getPotentialOverdueOne() {
        return potentialOverdueOne;
    }

    public void setPotentialOverdueOne(Integer potentialOverdueOne) {
        this.potentialOverdueOne = potentialOverdueOne;
    }
}
