package com.uton.carsokApi.constants.enums;

/**
 * Created by WANGYJ on 2017/11/9.
 */
public enum TaskTypeEnums {
    //潜客
    oneday_buy("24小时内回访"),
    threedays_buy("3日内购买"),
    sevendays_buy("7日内购买"),
    fifteendays_buy("15日购买"),
    onemonth_buy("30天内购买"),
    defeat("F 战败"),
    defeat_confirm("F0 战败待确认"),
    re_purchase("复购"),
    //保有
    threedays_followup("3日回访"),
    fifteendays_followup("15日回访"),
    onemonth_flollowup("一个月回访"),
    compulsory_insurance_remind("强制保险"),
    commercial_insurance_remind("商业保险"),
    quality_assurance_remind("质保"),
    maintain_remind("保养"),
    annual_survey_remind("年检"),
    boughtday_solicitude("购车纪念日"),
    birthday_solicitude("生日关怀"),

    ;
    private String taskType;
    TaskTypeEnums(String taskType){
        this.taskType = taskType;
    }
    public String getTaskType(){
        return taskType;
    }
}
