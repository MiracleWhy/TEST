package com.uton.carsokApi.controller.request;

import lombok.Data;

@Data
public class FollowMessageRequest {
    /**
     * 外部id(例如潜在客户id,保有车辆id)
     */
    private String outId;
    /**
     * 信息内容
     */
    private String message;
    /**
     * 所属模块(1-潜客管理,2-保有客户管理)
     */
    private String model;
    /**
     * 类型(1-N 24小时内回访, 2-H 3天内购买,
     * 3-A 7天内购买, 4-B 15天内购买,
     * 5-C 30天内购买, 6-F0 战败待确认,
     * 7-F 战败, 8-D 已成交, 9-生日关怀,
     * 10-3日回访, 11-15日回访,
     * 12-一个月回访, 13-强制保险,
     * 14-商业保险, 15-质保,
     * 16-保养, 17-年检,
     * 18-购车纪念, 19-编辑信息)
     */
    private String type;
    /**
     * accountId
     */
    private Integer accountId;
    /**
     * childId
     */
    private Integer childId;

    private Integer taskId;
    /**
     * 录音转文字
     */
    private String soundToWord;

    /**
     * 录音时长
     */
    private String recordTime;

}
