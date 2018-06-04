package com.uton.carsokApi.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.dozer.Mapping;

import java.util.Date;

@Data
public class CarsokFlowmsgResponse {
    private int id;
    @Mapping("message")
    private String message;
    @Mapping("type")
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String creator;
    private String creatorRole;
    /**
     * 录音转文字
     */
    private String soundToWord;

    /**
     * 录音时长
     */
    private String recordTime;
}
