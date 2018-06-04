package com.uton.carsokApi.model;


import lombok.Data;
import org.dozer.Mapping;

import java.util.Date;

@Data
public class CarsokTenureTaskRes {
    /**
     * 任务ID
     */
    private int id;
    /**
     *任务类型
     */
    private String type;
    /**
     *任务时间
     */
    @Mapping("scheduledTime")
    private Date taskTime;
}
