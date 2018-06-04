package com.uton.carsokApi.service.core.task;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by WANGYJ on 2017/11/13.
 */
@Data
public class FilterSQLParam {
    private String sqlTemplate;
    private String orderByColumn;
    private Boolean isAsc;
}
