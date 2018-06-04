package com.uton.carsokApi.controller.request;

import lombok.Data;

import java.util.Date;
@Data
public class CarsokSoundRecordingRequest {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 模块
     */
    private String module;

    /**
     * 关联外部id
     */
    private Integer business_id;

    /**
     * 录音的七牛云url
     */
    private String url;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * accountId
     */
    private Integer accountId;

    /**
     * childId
     */
    private Integer childId;

    /**
     * 是否可用(1-可用, 0-不可用)
     */
    private Integer enable;

    /**
     * 其他信息
     */
    private String remark;

    /**
     * 录音转文字
     */
    private String soundToWord;

    /**
     * 录音时长
     */
    private String recordTime;

    /**
     * 页数
     */
    private int pageNum;

    /**
     * 条数
     */
    private int PageSize;

}
